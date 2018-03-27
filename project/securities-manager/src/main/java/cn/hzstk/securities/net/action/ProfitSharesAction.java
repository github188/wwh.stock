package cn.hzstk.securities.net.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.net.domain.ProfitShares;
import cn.hzstk.securities.net.service.ProfitSharesService;
import cn.hzstk.securities.util.DateUtil;
import cn.hzstk.securities.util.HtmlUnitUtil;
import cn.hzstk.securities.util.JsoupUtil;
import net.ryian.commons.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;


/**
*
* @description:ProfitShares action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/net/profit-shares")
@SuppressWarnings({"serial", "JavaDoc"})
public class ProfitSharesAction extends MagicAction<ProfitShares,ProfitSharesService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProfitShares o = bindEntity(request, entityClass);
            String dt = o.getDt();
            int no = 0;
            if (StringUtils.isNotEmpty(dt)) {
                no = DateUtil.getDiffSemiYear(dt);
            } else {
                dt = DateUtil.getSemiYear();
            }
            String[] val;

            String[] arg = new String[]{Constant.CN_ADDRESS, null};
            arg[1] = ".monthlist";
            List<String[]> list = JsoupUtil.getSiteHref(arg);
            String[] href = list.get(no);

            arg[1] = "#DicList";
            LinkedHashMap<String, String[]> map = HtmlUnitUtil.getSiteList(arg, href[1]);
            for (int i = 0; i < map.size(); i++) {
                val = map.get(String.valueOf(i));
                o = setTurnsendData(val, dt);
                if (o == null) continue;
                entityService.saveOrUpdate(o);
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    private static ProfitShares setTurnsendData(String[] val, String dt) {
        ProfitShares o = new ProfitShares();
        o.setDt(dt);
        o.setStockCode(StringUtils.stripStart(val[1], "\t  "));
        o.setStockName(StringUtils.stripStart(val[2], "\t  "));
        o.setPlanDate(val[4]);
        o.setSendScale(val[5]);
        o.setTurnScale(val[6]);
        o.setCashScale(val[7]);
        o.setPassDate(val[8]);
        o.setRegisterDate(val[9]);
        o.setDividendDate(val[10]);
        return o;
    }

    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("currentDate", DateUtil.getSemiYear());
    }
}
