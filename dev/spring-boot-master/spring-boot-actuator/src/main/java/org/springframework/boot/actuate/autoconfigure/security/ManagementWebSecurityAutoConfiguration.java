/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.autoconfigure.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.ManagementContextResolver;
import org.springframework.boot.actuate.autoconfigure.web.ManagementServerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.IgnoredRequestCustomizer;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityPrerequisite;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.endpoint.EndpointInfo;
import org.springframework.boot.endpoint.web.WebEndpointOperation;
import org.springframework.boot.endpoint.web.mvc.WebEndpointServletHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for security of framework endpoints.
 * Many aspects of the behavior can be controller with {@link ManagementServerProperties}
 * via externalized application properties (or via an bean definition of that type to set
 * the defaults)..
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @since 2.0.0
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ EnableWebSecurity.class })
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@AutoConfigureBefore(FallbackWebSecurityAutoConfiguration.class)
@EnableConfigurationProperties(ManagementServerProperties.class)
public class ManagementWebSecurityAutoConfiguration {

	private static final String[] NO_PATHS = new String[0];

	private static final RequestMatcher MATCH_NONE = new NegatedRequestMatcher(
			AnyRequestMatcher.INSTANCE);

	@Bean
	public IgnoredRequestCustomizer managementIgnoredRequestCustomizer(
			ManagementServerProperties management,
			ObjectProvider<ManagementContextResolver> contextResolver) {
		return new ManagementIgnoredRequestCustomizer(management,
				contextResolver.getIfAvailable());
	}

	private class ManagementIgnoredRequestCustomizer implements IgnoredRequestCustomizer {

		private final ManagementServerProperties management;

		private final ManagementContextResolver contextResolver;

		ManagementIgnoredRequestCustomizer(ManagementServerProperties management,
				ManagementContextResolver contextResolver) {
			this.management = management;
			this.contextResolver = contextResolver;
		}

		@Override
		public void customize(IgnoredRequestConfigurer configurer) {
			if (!this.management.getSecurity().isEnabled()) {
				RequestMatcher requestMatcher = LazyEndpointPathRequestMatcher
						.getRequestMatcher(this.contextResolver);
				configurer.requestMatchers(requestMatcher);
			}
		}

	}

	@Configuration
	protected static class ManagementSecurityPropertiesConfiguration
			implements SecurityPrerequisite {

		private final SecurityProperties securityProperties;

		private final ManagementServerProperties managementServerProperties;

		public ManagementSecurityPropertiesConfiguration(
				ObjectProvider<SecurityProperties> securityProperties,
				ObjectProvider<ManagementServerProperties> managementServerProperties) {
			this.securityProperties = securityProperties.getIfAvailable();
			this.managementServerProperties = managementServerProperties.getIfAvailable();
		}

		@PostConstruct
		public void init() {
			if (this.managementServerProperties != null
					&& this.securityProperties != null) {
				this.securityProperties.getUser().getRole()
						.addAll(this.managementServerProperties.getSecurity().getRoles());
			}
		}

	}

	@Configuration
	@ConditionalOnMissingBean(WebSecurityConfiguration.class)
	@Conditional(WebSecurityEnablerCondition.class)
	@EnableWebSecurity
	protected static class WebSecurityEnabler extends AuthenticationManagerConfiguration {

	}

	/**
	 * WebSecurityEnabler condition.
	 */
	static class WebSecurityEnablerCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			String managementEnabled = context.getEnvironment()
					.getProperty("management.security.enabled", "true");
			String basicEnabled = context.getEnvironment()
					.getProperty("security.basic.enabled", "true");
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("WebSecurityEnabled");
			if ("true".equalsIgnoreCase(managementEnabled)
					&& !"true".equalsIgnoreCase(basicEnabled)) {
				return ConditionOutcome.match(message.because("security enabled"));
			}
			return ConditionOutcome.noMatch(message.because("security disabled"));
		}

	}

	@Configuration
	@ConditionalOnMissingBean({ ManagementWebSecurityConfigurerAdapter.class })
	@ConditionalOnProperty(prefix = "management.security", name = "enabled", matchIfMissing = true)
	@EnableConfigurationProperties(SecurityProperties.class)
	@Order(ManagementServerProperties.BASIC_AUTH_ORDER)
	protected static class ManagementWebSecurityConfigurerAdapter
			extends WebSecurityConfigurerAdapter {

		private final SecurityProperties security;

		private final ManagementServerProperties management;

		private final ManagementContextResolver contextResolver;

		public ManagementWebSecurityConfigurerAdapter(SecurityProperties security,
				ManagementServerProperties management,
				ObjectProvider<ManagementContextResolver> contextResolver) {
			this.security = security;
			this.management = management;
			this.contextResolver = contextResolver.getIfAvailable();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// secure endpoints
			RequestMatcher matcher = getRequestMatcher();
			if (matcher != null) {
				// Always protect them if present
				if (this.security.isRequireSsl()) {
					http.requiresChannel().anyRequest().requiresSecure();
				}
				AuthenticationEntryPoint entryPoint = entryPoint();
				http.exceptionHandling().authenticationEntryPoint(entryPoint);
				// Match all the requests for actuator endpoints ...
				http.requestMatcher(matcher).authorizeRequests().anyRequest()
						.authenticated();
				http.httpBasic().authenticationEntryPoint(entryPoint).and().cors();
				// No cookies for management endpoints by default
				http.csrf().disable();
				http.sessionManagement()
						.sessionCreationPolicy(asSpringSecuritySessionCreationPolicy(
								this.management.getSecurity().getSessions()));
				SpringBootWebSecurityConfiguration.configureHeaders(http.headers(),
						this.security.getHeaders());
			}
		}

		private SessionCreationPolicy asSpringSecuritySessionCreationPolicy(
				Enum<?> value) {
			if (value == null) {
				return SessionCreationPolicy.STATELESS;
			}
			return SessionCreationPolicy.valueOf(value.name());
		}

		private RequestMatcher getRequestMatcher() {
			if (this.management.getSecurity().isEnabled()) {
				return LazyEndpointPathRequestMatcher
						.getRequestMatcher(this.contextResolver);
			}
			return null;
		}

		private AuthenticationEntryPoint entryPoint() {
			BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
			entryPoint.setRealmName(this.security.getBasic().getRealm());
			return entryPoint;
		}

	}

	private static class EndpointPaths {

		public String[] getPaths(
				WebEndpointServletHandlerMapping endpointHandlerMapping) {
			if (endpointHandlerMapping == null) {
				return NO_PATHS;
			}
			Collection<EndpointInfo<WebEndpointOperation>> endpoints = endpointHandlerMapping
					.getEndpoints();
			Set<String> paths = new LinkedHashSet<>(endpoints.size());
			for (EndpointInfo<WebEndpointOperation> endpoint : endpoints) {
				String path = endpointHandlerMapping.getEndpointPath() + "/"
						+ endpoint.getId();
				paths.add(path);
				paths.add(path + "/**");
				// Add Spring MVC-generated additional paths
				paths.add(path + ".*");
				paths.add(path + "/");
			}
			return paths.toArray(new String[paths.size()]);
		}

	}

	private static class LazyEndpointPathRequestMatcher implements RequestMatcher {

		private final EndpointPaths endpointPaths;

		private final ManagementContextResolver contextResolver;

		private RequestMatcher delegate;

		public static RequestMatcher getRequestMatcher(
				ManagementContextResolver contextResolver) {
			if (contextResolver == null) {
				return null;
			}
			ManagementServerProperties management = contextResolver
					.getApplicationContext().getBean(ManagementServerProperties.class);
			ServerProperties server = contextResolver.getApplicationContext()
					.getBean(ServerProperties.class);
			String path = management.getContextPath();
			if (StringUtils.hasText(path)) {
				AntPathRequestMatcher matcher = new AntPathRequestMatcher(
						server.getServlet().getPath(path) + "/**");
				return matcher;
			}
			// Match all endpoint paths
			return new LazyEndpointPathRequestMatcher(contextResolver,
					new EndpointPaths());
		}

		LazyEndpointPathRequestMatcher(ManagementContextResolver contextResolver,
				EndpointPaths endpointPaths) {
			this.contextResolver = contextResolver;
			this.endpointPaths = endpointPaths;
		}

		@Override
		public boolean matches(HttpServletRequest request) {
			if (this.delegate == null) {
				this.delegate = createDelegate();
			}
			return this.delegate.matches(request);
		}

		private RequestMatcher createDelegate() {
			ServerProperties server = this.contextResolver.getApplicationContext()
					.getBean(ServerProperties.class);
			List<RequestMatcher> matchers = new ArrayList<>();
			WebEndpointServletHandlerMapping endpointHandlerMapping = getRequiredEndpointHandlerMapping();
			for (String path : this.endpointPaths.getPaths(endpointHandlerMapping)) {
				matchers.add(
						new AntPathRequestMatcher(server.getServlet().getPath(path)));
			}
			return (matchers.isEmpty() ? MATCH_NONE : new OrRequestMatcher(matchers));
		}

		private WebEndpointServletHandlerMapping getRequiredEndpointHandlerMapping() {
			WebEndpointServletHandlerMapping endpointHandlerMapping = null;
			ApplicationContext context = this.contextResolver.getApplicationContext();
			if (context.getBeanNamesForType(
					WebEndpointServletHandlerMapping.class).length > 0) {
				endpointHandlerMapping = context
						.getBean(WebEndpointServletHandlerMapping.class);
			}
			if (endpointHandlerMapping == null) {
				// Maybe there are actually no endpoints (e.g. management.port=-1)
				endpointHandlerMapping = new WebEndpointServletHandlerMapping("",
						Collections.emptySet());
			}
			return endpointHandlerMapping;
		}

	}

}
