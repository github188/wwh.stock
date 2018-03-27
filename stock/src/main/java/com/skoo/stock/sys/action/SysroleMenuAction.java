package com.skoo.stock.sys.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.SysroleMenu;
import com.skoo.stock.sys.service.SysroleMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:SysroleMenu action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/sysrole-menu")
@SuppressWarnings("serial")
public class SysroleMenuAction extends ManAction<SysroleMenu, SysroleMenuService> {


}
