package cn.hzstk.securities.config.action;

import cn.hzstk.securities.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.xsom.impl.ListSimpleTypeImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.config.domain.District;
import cn.hzstk.securities.config.service.DistrictService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value = "index_2")
    public String selectdown(Model model,
                             @RequestParam(value = "upid")String upid) {
        model.addAttribute("upid",upid);
        return "config/district/index_2";
    }
    @RequestMapping(value = "test")
    public String test() {
        return "config/district/test";
    }
    @RequestMapping(value = "index_3")
    public String selectdown2(Model model,
                             @RequestParam(value = "upid")String upid) {
        String id=entityService.getTopId(upid);
        model.addAttribute("backid",id);
        model.addAttribute("upid",upid);
        return "config/district/index_3";
    }
    @RequestMapping(value = "level2")
    public String level2() {
        return "config/district/level2";
    }
    @RequestMapping(value = "level3")
    public String level3() {
        return "config/district/level3";
    }

    @RequestMapping(value = "getDistrict",method = RequestMethod.GET)
    public void getDistrict(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "upid")String upid) throws Exception {
        try {
            Map<String,String> map= getParameterMap(request);
//            if(upid.contains("b")){
//               upid=upid.replace("b","");
//               String id=entityService.getTopId(upid);
//                map.put("upid",id);
//
//            }
                @SuppressWarnings("unchecked")
                PageInfo<?> twoPage = entityService.queryPaged(map);
                JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(twoPage, new DictFilter()));
                List   row=o.getJSONArray("list");
                JSONArray rowlist=(JSONArray)entityService.addUpName(row);
                o.put("rows", rowlist);
                o.remove("list");
                o.put("totalPageCount", o.get("lastPage"));
                o.put("countPerPage", o.get("pageSize"));
                o.put("currentPage", o.get("pageNum"));
                printJson(response, o.toJSONString());
            } catch (Exception e) {
        e.printStackTrace();
        }
    }
    @RequestMapping(value = "getDistrict2",method = RequestMethod.GET)
    public void getDistrict2(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "upid")String upid) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            PageInfo<?> threePage = entityService.queryPaged(getParameterMap(request));
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(threePage, new DictFilter()));
            List   row=o.getJSONArray("list");
            JSONArray rowlist=(JSONArray)entityService.addTopName(row);
            o.put("rows", rowlist);
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage", o.get("pageNum"));
            printJson(response, o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "queryPaged")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            PageInfo<?> page = entityService.queryPaged(getParameterMap(request));
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page, new DictFilter()));
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
    @RequestMapping(value = "queryPaged2")
    public void queryPaged2(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            PageInfo<?> twoPage = entityService.getTwoByType(getParameterMap(request));
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(twoPage, new DictFilter()));
            List   row=o.getJSONArray("list");
            JSONArray rowlist=(JSONArray)entityService.addUpName(row);
            o.put("rows", rowlist);
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage", o.get("pageNum"));
            printJson(response, o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "queryPaged3")
    public void queryPaged3(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            PageInfo<?> threePage = entityService.getThreeByType(getParameterMap(request));
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(threePage, new DictFilter()));
            List   row=o.getJSONArray("list");
            JSONArray rowlist=(JSONArray)entityService.addTopName(row);
            o.put("rows", rowlist);
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage", o.get("pageNum"));
            printJson(response, o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "load_area")
    public void load_area(HttpServletRequest request,
                          HttpServletResponse response, String pid) throws Exception {
        Map params = new HashMap();
        params.put("upid", pid);
        params.put("rows", "100");
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
