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

package org.springframework.boot.actuate.endpoint.mvc;

import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.actuate.autoconfigure.ManagementContextAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.infrastructure.EndpointInfrastructureAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.infrastructure.ServletEndpointAutoConfiguration;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.web.HealthWebEndpointExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicatorFactory;
import org.springframework.boot.actuate.health.HealthStatusHttpMapper;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testsupport.runner.classpath.ClassPathExclusions;
import org.springframework.boot.testsupport.runner.classpath.ModifiedClassPathRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the health endpoint when Spring Security is not available.
 *
 * @author Andy Wilkinson
 * @author Madhura Bhave
 */
@RunWith(ModifiedClassPathRunner.class)
@ClassPathExclusions("spring-security-*.jar")
public class NoSpringSecurityHealthMvcEndpointIntegrationTests {

	private AnnotationConfigWebApplicationContext context;

	@After
	public void closeContext() {
		this.context.close();
	}

	@Test
	public void healthDetailPresent() throws Exception {
		this.context = new AnnotationConfigWebApplicationContext();
		this.context.setServletContext(new MockServletContext());
		this.context.register(TestConfiguration.class);
		TestPropertyValues.of("management.security.enabled:false").applyTo(this.context);
		this.context.refresh();
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		mockMvc.perform(get("/application/health")).andExpect(status().isOk())
				.andExpect(content().string(containsString(
						"\"status\":\"UP\",\"test\":{\"status\":\"UP\",\"hello\":\"world\"}")));
	}

	@ImportAutoConfiguration({ JacksonAutoConfiguration.class,
			HttpMessageConvertersAutoConfiguration.class, WebMvcAutoConfiguration.class,
			DispatcherServletAutoConfiguration.class,
			EndpointInfrastructureAutoConfiguration.class,
			ManagementContextAutoConfiguration.class,
			ServletEndpointAutoConfiguration.class })
	@Configuration
	static class TestConfiguration {

		@Bean
		public HealthIndicator testHealthIndicator() {
			return () -> Health.up().withDetail("hello", "world").build();
		}

		@Bean
		public HealthEndpoint healthEndpoint(
				Map<String, HealthIndicator> healthIndicators) {
			return new HealthEndpoint(new HealthIndicatorFactory().createHealthIndicator(
					new OrderedHealthAggregator(), healthIndicators));
		}

		@Bean
		public HealthWebEndpointExtension healthWebEndpointExtension(
				HealthEndpoint delegate) {
			return new HealthWebEndpointExtension(delegate, new HealthStatusHttpMapper());
		}

	}

}
