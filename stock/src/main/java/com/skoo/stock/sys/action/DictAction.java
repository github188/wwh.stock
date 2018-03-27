package com.skoo.stock.sys.action;

import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.sys.domain.Dict;
import com.skoo.stock.sys.service.DictService;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sys/dict")
public class DictAction extends ManAction<Dict, DictService> {

    /**
     * 分页查询Dictionary列表.
     */
    @RequestMapping(value = "queryPaged")
    @SuppressWarnings("unchecked")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            PageInfo<Dict> page = entityService.queryPaged(condition);
            printText(response, JsonUtils.bean2Json(page, null, "yyyy-MM-dd", Dict.class));
        } catch (Exception e) {
            logger.error("queryPaged", e);
        }
    }

    /**
     * 取得数据字典项
     */
    @RequestMapping(value = "getDictsInfo")
    private void getDictsInfo(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        try {
            String keyName = request.getParameter("keyName");
            List<Dict> lst = entityService.getDictsInfo(keyName);

            printText(response, JsonUtils.bean2JsonArray(lst, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("getInsTags", e);
            printText(response, messageFailureWrap("数据取得失败！"));
        }
    }
}
