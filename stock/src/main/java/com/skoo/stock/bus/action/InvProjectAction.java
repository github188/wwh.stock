package com.skoo.stock.bus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.bus.domain.InvProject;
import com.skoo.stock.bus.service.InvProjectService;

/**
 * 
 * @description:InvProject action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/bus/inv-project")
@SuppressWarnings("serial")
public class InvProjectAction extends ManAction<InvProject,InvProjectService> {
	

}
