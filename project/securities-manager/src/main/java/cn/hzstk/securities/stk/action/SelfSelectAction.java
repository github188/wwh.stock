package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.common.service.StkCommQtz;
import cn.hzstk.securities.common.service.StkEastQtz;
import cn.hzstk.securities.stk.domain.SelfSelect;
import cn.hzstk.securities.stk.service.SelfSelectService;
import cn.hzstk.securities.sys.service.DictService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
*
* @description:SelfSelect action
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Controller
@RequestMapping("/stk/self-select")
public class SelfSelectAction extends MagicAction<SelfSelect,SelfSelectService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);
    @Autowired
    DictService dictService;

    @Autowired
    private StkCommQtz stkCommQtz;

    /**
     * 导入记录.
     */
    @RequestMapping(value = "dataImport", method = RequestMethod.POST)
    public void dataImport(HttpServletResponse response) {
        try {
            stkCommQtz.executeZx();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataImport", e);
            printJson(response, messageFailureWrap("导入失败！"));
        }
    }

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Map<String, String> paramMap = getParameterMap(request);
            StkEastQtz.planStock(paramMap);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("siteUrl",dictService.getDictsByKey(DictKeys.SITE_URL).values());
    }
}
