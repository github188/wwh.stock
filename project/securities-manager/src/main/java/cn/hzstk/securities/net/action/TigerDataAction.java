package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.service.StkCommQtz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.net.domain.TigerData;
import cn.hzstk.securities.net.service.TigerDataService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:TigerData action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/tiger-data")
@SuppressWarnings("serial")
public class TigerDataAction extends MagicAction<TigerData,TigerDataService> {
}
