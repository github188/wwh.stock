package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.service.TaskService;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.Status;
import cn.hzskt.bdtg.job.service.StatusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
*
* @description:Status action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/status")
@SuppressWarnings("serial")
public class StatusAction extends MagicAction<Status,StatusService> {
    @Autowired
    TaskService taskService;


    @RequestMapping(value = "queryPaged")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            Map<String,String> map=new HashMap<>();
            map=getParameterMap(request);
            map.put("tid", WebUtil.getTid(request).toString());
            PageInfo<?> page = entityService.queryPaged(map);
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage",o.get("pageNum"));
            printJson(response,o.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void beforeIndex(HttpServletRequest request,Model model) {
        Long progress=taskService.get(WebUtil.getTid(request)).getProgressVal();
        if(progress==null){
            progress=Long.parseLong("0");
        }
        model.addAttribute("progress",progress);
    }
}
