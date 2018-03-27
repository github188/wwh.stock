package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.service.StkEastQtz;
import cn.hzstk.securities.net.domain.PerformanceData;
import cn.hzstk.securities.net.service.PerformanceDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:PerformanceData action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/performance-data")
@SuppressWarnings({"serial", "JavaDoc"})
public class PerformanceDataAction extends MagicAction<PerformanceData,PerformanceDataService> {

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            PerformanceData o = bindEntity(request, entityClass);
            StkEastQtz.obtainPerformance();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
