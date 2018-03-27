package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.service.StkCommQtz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.net.domain.CoreInfo;
import cn.hzstk.securities.net.service.CoreInfoService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:CoreInfo action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/core-info")
@SuppressWarnings("serial")
public class CoreInfoAction extends MagicAction<CoreInfo,CoreInfoService> {

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            CoreInfo o = bindEntity(request, entityClass);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
