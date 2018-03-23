package cn.hzskt.bdtg.config.action;

import cn.hzskt.bdtg.config.mapper.IndustryMapper;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.config.domain.Industry;
import cn.hzskt.bdtg.config.service.IndustryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
*
* @description:Industry action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/config/industry")
@SuppressWarnings("serial")
public class IndustryAction extends MagicAction<Industry,IndustryService> {
    @Autowired
    IndustryMapper industryMapper;

    @RequestMapping(value = "load_indus")
    public void load_area(HttpServletRequest request,
                          HttpServletResponse response, String pid) throws Exception {
        String temp="";
        Map<String,String> params = new HashMap<String,String>();
        params.put("indusPid", pid);
        params.put("rows",String.valueOf(industryMapper.getcount(Long.parseLong(pid))));
        PageInfo<?> page = entityService.queryPaged(params);
        if(page.getSize()>0){
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page, new DictFilter()));
            JSONArray list = o.getJSONArray("list");
             temp = list.toJSONString();
        }
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.print(temp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
