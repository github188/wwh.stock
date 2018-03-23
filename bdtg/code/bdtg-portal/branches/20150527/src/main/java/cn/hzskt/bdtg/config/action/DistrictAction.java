package cn.hzskt.bdtg.config.action;

import cn.hzskt.bdtg.config.mapper.DistrictMapper;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.config.domain.District;
import cn.hzskt.bdtg.config.service.DistrictService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
*
* @description:District action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/config/district")
@SuppressWarnings("serial")
public class DistrictAction extends MagicAction<District,DistrictService> {

    @Autowired
    DistrictMapper districtMapper;

    @RequestMapping(value = "load_area")
    public void load_area(HttpServletRequest request,
                          HttpServletResponse response, String pid) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        params.put("upid", pid);
        params.put("rows",String.valueOf(districtMapper.getcount(Long.parseLong(pid))));
        PageInfo<?> page = entityService.queryPaged(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page, new DictFilter()));
        JSONArray list = o.getJSONArray("list");
        String temp = list.toJSONString();
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
