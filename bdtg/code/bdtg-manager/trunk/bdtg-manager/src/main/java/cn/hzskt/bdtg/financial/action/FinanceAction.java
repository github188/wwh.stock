package cn.hzskt.bdtg.financial.action;

import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.financial.domain.Finance;
import cn.hzskt.bdtg.financial.service.FinanceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*
* @description:Finance action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/financial/finance")
@SuppressWarnings("serial")
public class FinanceAction extends MagicAction<Finance,FinanceService> {

    @RequestMapping(value = "queryPaged")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            Map<String,String> params=getParameterMap(request);
            PageInfo<?> page = entityService.queryPaged(params);
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            JSONArray row=o.getJSONArray("list");
            List list=new ArrayList();
            for(int i=0;i<row.size();i++){
                JSONObject obj=row.getJSONObject(i);
                Long time=obj.getLongValue("finaTime");
                Date dat = new Date(time*1000);
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String date = format.format(dat);
                obj.put("finaTime",date);
                int cash=obj.getIntValue("finaCash")-1;
                obj.put("realCash",cash);
                list.add(obj);
            }
            o.put("rows", list);
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage",o.get("pageNum"));
            printJson(response,o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
