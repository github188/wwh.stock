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

package org.springframework.boot.actuate.autoconfigure.endpoint.jmx;

import org.springframework.boot.actuate.autoconfigure.endpoint.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.endpoint.AuditEventsEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.AuditEventsJmxEndpointExtension;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.endpoint.jmx.JmxEndpointExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for JMX {@link org.springframework.boot.endpoint.Endpoint Endpoints}
 * and JMX-specific {@link JmxEndpointExtension endpoint extensions}.
 *
 * @author Andy Wilkinson
 * @since 2.0.0
 */
@AutoConfigureAfter(EndpointAutoConfiguration.class)
@Configuration
public class JmxEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint
	@ConditionalOnBean(AuditEventsEndpoint.class)
	public AuditEventsJmxEndpointExtension auditEventsJmxEndpointExtension(
			AuditEventsEndpoint auditEventsEndpoint) {
		return new AuditEventsJmxEndpointExtension(auditEventsEndpoint);
	}

}
