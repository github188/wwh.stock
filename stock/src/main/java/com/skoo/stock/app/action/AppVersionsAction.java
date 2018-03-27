package com.skoo.stock.app.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.app.domain.AppVersions;
import com.skoo.stock.app.service.AppVersionsService;

/**
 * 
 * @description:AppVersions action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/app/app-versions")
@SuppressWarnings("serial")
public class AppVersionsAction extends ManAction<AppVersions,AppVersionsService> {
	

}
