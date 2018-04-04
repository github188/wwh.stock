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

package org.springframework.boot.actuate.autoconfigure;

import org.springframework.core.env.Environment;

public enum ManagementPortType {

	/**
	 * The management port has been disabled.
	 */
	DISABLED,

	/**
	 * The management port is the same as the server port.
	 */
	SAME,

	/**
	 * The management port and server port are different.
	 */
	DIFFERENT;

	static ManagementPortType get(Environment environment) {
		Integer serverPort = getPortProperty(environment, "server.");
		Integer managementPort = getPortProperty(environment, "management.");
		if (managementPort != null && managementPort < 0) {
			return DISABLED;
		}
		return ((managementPort == null)
				|| (serverPort == null && managementPort.equals(8080))
				|| (managementPort != 0 && managementPort.equals(serverPort)) ? SAME
						: DIFFERENT);
	}

	private static Integer getPortProperty(Environment environment, String prefix) {
		return environment.getProperty(prefix + "port", Integer.class);
	}

}
