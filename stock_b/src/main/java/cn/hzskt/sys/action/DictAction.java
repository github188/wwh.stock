package cn.hzskt.sys.action;

import cn.hzskt.common.action.MagicAction;
import cn.hzskt.sys.domain.Dict;
import cn.hzskt.sys.service.DictService;
import cn.hzskt.util.StockUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys/dict")
public class DictAction extends MagicAction<Dict,DictService> {

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(String keyName, HttpServletResponse response) {
        try {
            LinkedHashMap<String, String[]> map = StockUtil.getStockList("keyName",null);
            if (map != null & map.size() <= 1) return;
            String[] val;
            for (int i = 1; i < map.size(); i++) {
                val = map.get(String.valueOf(i));

                Dict o = new Dict();
                o.setKeyName(keyName);
                o.setValue(String.valueOf(i));
                o.setContent(val[0]);
                o.setMemo(val[val.length - 1] + "|" + String.valueOf((int) Math.ceil(Double.valueOf(val[1]) / 30)));
/*
                o.setUdAmount(ConvertFactory.convert(Double.TYPE, val[4]));
                o.setFiveWidth(val[5]);
                o.setVolume(ConvertFactory.convert(Double.TYPE, val[6]));
                o.setTurnVolume(ConvertFactory.convert(Double.TYPE, val[7]));
                o.setTurnoverRate(val[8]);
                o.setAmplitude(val[9]);
                o.setVolumeRatio(ConvertFactory.convert(Double.TYPE, val[10]));
                o.setCommittee(ConvertFactory.convert(Double.TYPE, val[11]));
                o.setPeRatio(ConvertFactory.convert(Double.TYPE, val[12]));
*/
                entityService.saveOrUpdate(o);
            }

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

}
