package com.skoo.stock.bus.action;

import com.skoo.stock.bus.domain.GoutEnterprises;
import com.skoo.stock.bus.service.GoutEnterprisesService;
import com.skoo.stock.common.action.ManAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:GoutEnterprises action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/bus/gout-enterprises")
@SuppressWarnings("serial")
public class GoutEnterprisesAction extends ManAction<GoutEnterprises, GoutEnterprisesService> {


    @RequestMapping(value = "addg")
    public String add(HttpServletRequest request) {
        List list = entityService.getProvinces();
        request.setAttribute("provinces", list);


        //获得国家
  /*String countries = entityService.getCountries();
  JSONObject jo = JSONObject.fromObject(countries);
  JSONObject location = jo.getJSONObject("location");
  JSONArray crs = location.getJSONArray("countryregion");
  for(int i=0;i<crs.size();i++){
   JSONObject j = crs.getJSONObject(i);
   j.discard("state");
  }
  request.setAttribute("countries",crs);*/

        return getNameSpace() + "add";
    }

    @RequestMapping(value = "edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            GoutEnterprises ge = entityService.get(id);
            model.addAttribute("model", ge);
        }
        List list = entityService.getProvinces();
        model.addAttribute("provinces", list);
        return getNameSpace() + "edit1";
    }

    @RequestMapping("getCities")
    @ResponseBody
    public String getCitiesByPid(HttpServletRequest req) {
        String pid = req.getParameter("pid");
        List list = entityService.getCitiesByPid(pid);
        JSONArray ja = new JSONArray();
        ja = ja.fromObject(list);
        return ja.toString();
    }

    @RequestMapping("getDistricts")
    @ResponseBody
    public String getDistrictsByCid(HttpServletRequest req) {
        String cid = req.getParameter("cid");
        List list = entityService.getDistrictsByCid(cid);
        JSONArray ja = new JSONArray();
        ja = ja.fromObject(list);
        return ja.toString();
    }

    @RequestMapping("getCountries")
    @ResponseBody
    public String getCountries(HttpServletRequest req) {
        String countries = entityService.getCountries();
        JSONObject jo = JSONObject.fromObject(countries);
        JSONObject location = jo.getJSONObject("location");
        JSONArray crs = location.getJSONArray("countryregion");
        for (int i = 0; i < crs.size(); i++) {
            JSONObject j = crs.getJSONObject(i);
            j.discard("state");
        }
        return crs.toString();
    }

    @RequestMapping("getStatesAndCities")
    @ResponseBody
    public String getStatesAndCities(HttpServletRequest req) {
        String oicCode = req.getParameter("oicCode");
        String countries = entityService.getCountries();
        JSONObject jo = JSONObject.fromObject(countries);
        JSONObject location = jo.getJSONObject("location");
        JSONArray crs = location.getJSONArray("countryregion");
        for (int i = 0; i < crs.size(); i++) {
            JSONObject j = crs.getJSONObject(i);
            String code = j.getString("code");
            if (code.equals(oicCode)) {
                if (j.containsKey("state")) {
                    Object ja = j.get("state");
                    return ja.toString();
                }
            }
        }
        return "{}";
    }

    @RequestMapping("getRegion")
    @ResponseBody
    public String getRegion(HttpServletRequest req){
        String id = req.getParameter("id");
        String s = entityService.getRegionById(id);
        return s;
    }
}
