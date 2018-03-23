/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.web.flow;

import com.zjhcsoft.smartcity.cas.serverX.personal.handler.QuerySystemInfoHandler;
import org.jasig.cas.services.UnauthorizedServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * Class to automatically set the paths for the CookieGenerators.
 * 
 */
public final class SystemValidateWithService extends AbstractAction {

	
	private QuerySystemInfoHandler querySystemInfoHandler;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    public QuerySystemInfoHandler getQuerySystemInfoHandler() {
		return querySystemInfoHandler;
	}


	public void setQuerySystemInfoHandler(
			QuerySystemInfoHandler querySystemInfoHandler) {
		this.querySystemInfoHandler = querySystemInfoHandler;
	}


	protected Event doExecute(final RequestContext context) throws Exception {

		String systemID = null;
    	if ("".equals(context.getFlowScope().get("service")) || context.getFlowScope().get("service") != null) {
    		
			// 查找用户访问的url信息所属那个应用系统
			systemID = querySystemInfoHandler.getSystemIDByService(context.getFlowScope().get("service").toString());
			
			if (systemID == null) {
				throw new UnauthorizedServiceException("系统不存在");
			}
    	}
    	return success();
    }
}
