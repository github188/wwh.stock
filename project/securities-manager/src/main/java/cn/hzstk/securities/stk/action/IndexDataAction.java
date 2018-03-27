package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkEastQtz;
import cn.hzstk.securities.stk.domain.IndexData;
import cn.hzstk.securities.stk.service.IndexDataService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;


/**
*
* @description:IndexData action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/index-data")
@SuppressWarnings({"serial", "JavaDoc"})
public class IndexDataAction extends MagicAction<IndexData,IndexDataService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletResponse response) {
        try {
            StkEastQtz.planIndex();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
