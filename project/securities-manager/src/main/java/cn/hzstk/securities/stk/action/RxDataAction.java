package cn.hzstk.securities.stk.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.common.service.StkCommQtz;
import cn.hzstk.securities.stk.domain.RxData;
import cn.hzstk.securities.stk.service.RxDataService;
import cn.hzstk.securities.sys.service.ParamService;
import cn.hzstk.securities.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
*
* @description:RxData action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/stk/rx-data")
@SuppressWarnings({"serial", "JavaDoc"})
public class RxDataAction extends MagicAction<RxData,RxDataService> {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    @Autowired
    private StkCommQtz stkCommQtz;
    @Autowired
    ParamService paramService;

    @Override
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> paramMap = getParameterMap(request);
            String stockCode = paramMap.get("stockCode");
            if (StringUtils.isEmpty(stockCode) || !StringUtils.isNumeric(stockCode) || "0".equals(stockCode)) {
                paramMap.put("stockCode", paramService.getbyName(ParamKeys.RX_DEFAULT_CODE));
            }
            PageInfo<RxData> page = entityService.queryPaged(paramMap);
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage",o.get("pageNum"));
            printJson(response, o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成记录.
     */
    @RequestMapping(value = "dataRefresh", method = RequestMethod.POST)
    public void dataRefresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            RxData o = bindEntity(request, entityClass);
            String stockCode = o.getStockCode();
            String startCode = paramService.getbyName(ParamKeys.RX_START_CODE);
            startCode = stkCommQtz.executeRx(stockCode, startCode);
            if (StringUtils.isNotEmpty(stockCode)) paramService.updatebyName(ParamKeys.RX_START_CODE, startCode);

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("dataRefresh", e);
            printJson(response, messageFailureWrap("刷新失败！"));
        }
    }
}
