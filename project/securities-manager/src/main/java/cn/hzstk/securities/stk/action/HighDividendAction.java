package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.stk.domain.HighDividend;
import cn.hzstk.securities.stk.service.HighDividendService;
import cn.hzstk.securities.util.HtmlUnitUtil;
import net.ryian.commons.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;


/**
*
* @description:HighDividend action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/high-dividend")
@SuppressWarnings({"serial", "JavaDoc"})
public class HighDividendAction extends MagicAction<HighDividend,HighDividendService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletResponse response) {
        try {
            HighDividend o;
            String[] val;
            String[] arg = new String[]{Constant.CN_ADDRESS, "#DicList"};

            LinkedHashMap<String, String[]> map = HtmlUnitUtil.getSiteList(arg, "javascript:View(1,1,'GongGaoRiQi',1)");
            for (int i = 0; i < map.size(); i++) {
                val = map.get(String.valueOf(i));
                o = setTurnsendData(val);
                if (o == null) continue;
                entityService.saveOrUpdate(o);
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }

    private static HighDividend setTurnsendData(String[] val) {
        HighDividend o = new HighDividend();
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
}
