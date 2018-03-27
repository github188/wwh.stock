package com.skoo.stock.sys.action;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.SysuserRole;
import com.skoo.stock.sys.service.SysuserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:SysuserRole action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/sys/sysuser-role")
@SuppressWarnings("serial")
public class SysuserRoleAction extends ManAction<SysuserRole, SysuserRoleService> {


}
