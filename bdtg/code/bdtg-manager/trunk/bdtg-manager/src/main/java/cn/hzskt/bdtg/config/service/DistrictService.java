package cn.hzskt.bdtg.config.service;

import cn.hzskt.bdtg.config.domain.District;
import cn.hzskt.bdtg.config.mapper.DistrictMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import sun.rmi.transport.ObjectTable;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  DistrictService extends BaseService<District,DistrictMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<District> query(Map<String, String> paramMap) {
        Example example = new Example(District.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String upid = paramMap.get("upid");
        if (!StringUtils.isEmpty(upid)) {
            criteria.andEqualTo("upid", "" + upid + "");
        }else {
            criteria.andEqualTo("upid", "0");
        }
return mapper.selectByExample(example);
    }


    /**
     * 查询二级列表
     * @param paramMap
     * @return
     */
    public PageInfo<?> getTwoByType(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("rows") == null?null:(String)paramMap.get("rows");
        String pageStr = paramMap.get("page") == null?null:(String)paramMap.get("page");
        int page = 1;
        int rows = 10;
        try {
            page = pageStr != null?Integer.valueOf(pageStr).intValue():page;
            rows = rowsStr != null?Integer.valueOf(rowsStr).intValue():rows;
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        PageHelper.startPage(page, rows);
        List list = mapper.getTwoByType();
        return new PageInfo(list);

    }
    /**
     * 查询三级列表
     * @param paramMap
     * @return
     */
    public PageInfo<?> getThreeByType(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("rows") == null?null:(String)paramMap.get("rows");
        String pageStr = paramMap.get("page") == null?null:(String)paramMap.get("page");
        int page = 1;
        int rows = 10;

        try {
            page = pageStr != null?Integer.valueOf(pageStr).intValue():page;
            rows = rowsStr != null?Integer.valueOf(rowsStr).intValue():rows;
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        PageHelper.startPage(page, rows);
        List list = mapper.getThreeByType();
        return new PageInfo(list);

    }

    /**
     * 添加上级名称
     * @param list
     * @return
     */
    public List addUpName(List list){
        List  row=list;
        JSONArray json = new JSONArray();
        for(Object a : row){
            JSONObject jo = new JSONObject();
            jo.put("id", ((JSONObject) a).get("id"));
            jo.put("upid",((JSONObject) a).get("upid"));
            jo.put("name", ((JSONObject) a).get("name"));
            jo.put("type",((JSONObject) a).get("type"));
            jo.put("displayorder",  ((JSONObject) a).get("displayorder"));
            List up=mapper.getUp(((JSONObject) a).get("upid").toString());
            jo.put("upname",up.get(0).toString());
            json.add(jo);
        }
        return json;
    }
    /**
     * 添加顶级名称
     * @param list
     * @return
     */
    public List addTopName(List list){
        List  row=list;
        JSONArray json = new JSONArray();
        for(Object a : row){
            JSONObject jo = new JSONObject();
            jo.put("id", ((JSONObject) a).get("id"));
            jo.put("upid",((JSONObject) a).get("upid"));
            jo.put("name", ((JSONObject) a).get("name"));
            jo.put("type",((JSONObject) a).get("type"));
            jo.put("displayorder",  ((JSONObject) a).get("displayorder"));
            List up=mapper.getUp(((JSONObject) a).get("upid").toString());
            List top=mapper.getTop(((JSONObject) a).get("upid").toString());
            jo.put("upname",up.get(0).toString());
            jo.put("topname",top.get(0).toString());
            json.add(jo);
        }
        return json;
    }
    /**
     * 查询顶级id
     * @param
     * @return
     */
    public String getTopId(String upid){
        List up=mapper.getTopId(upid);
        return up.get(0).toString();
    }
}
