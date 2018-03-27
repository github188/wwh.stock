package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkEastQtz;
import cn.hzstk.securities.stk.domain.SuspendedInfo;
import cn.hzstk.securities.stk.service.SuspendedInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;


/**
*
* @description:SuspendedInfo action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/suspended-info")
@SuppressWarnings({"serial", "JavaDoc"})
public class SuspendedInfoAction extends MagicAction<SuspendedInfo,SuspendedInfoService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletResponse response) {
        try {
            StkEastQtz.setSuspended();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
