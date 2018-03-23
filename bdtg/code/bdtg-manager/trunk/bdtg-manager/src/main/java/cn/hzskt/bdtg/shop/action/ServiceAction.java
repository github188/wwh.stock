package cn.hzskt.bdtg.shop.action;

import cn.hzskt.bdtg.shop.domain.Shop;
import cn.hzskt.bdtg.shop.service.ShopService;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.UserService;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.shop.domain.Service;
import cn.hzskt.bdtg.shop.service.ServiceService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*
* @description:Service action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/shop/service")
@SuppressWarnings("serial")
public class ServiceAction extends MagicAction<Service,ServiceService> {
    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;
    @RequestMapping(value = "queryPaged")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            PageInfo<?> page = entityService.queryPaged(getParameterMap(request));
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            if (o != null) {
                JSONArray row = o.getJSONArray("list");
                List list = new ArrayList();
                for (int i = 0; i < row.size(); i++) {
                    JSONObject obj = row.getJSONObject(i);
                    Long shopid = obj.getLongValue("shopId");
                    Shop shop = shopService.get(shopid);
                    if(null!=shop){
                    User user = userService.get(Long.valueOf(shop.getUid()));
                    obj.put("username", user.getName());
                    list.add(obj);
                    }
                }
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage",o.get("pageNum"));
                printJson(response,o.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 审核处理
     *
     */
    @RequestMapping(value = "examine",method = RequestMethod.POST)
    public void examine(HttpServletResponse rep,String id,String yn) throws  Exception{
        try {
            Service rel = entityService.get(Long.parseLong(id));
            if(!"5".equals(yn)) {
                rel.setServiceStatus(Integer.valueOf(yn));
                entityService.saveOrUpdate(rel);
            }
            else entityService.logicRemove(rel.getId());
            printJson(rep, messageSuccuseWrap());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            printJson(rep, messageFailureWrap("审核失败！"));
        }
    }



}
