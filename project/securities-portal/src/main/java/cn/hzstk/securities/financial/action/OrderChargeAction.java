package cn.hzstk.securities.financial.action;

import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.financial.domain.OrderCharge;
import cn.hzstk.securities.financial.service.OrderChargeService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*
* @description:OrderCharge action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/financial/order-charge")
@SuppressWarnings("serial")
public class OrderChargeAction extends MagicAction<OrderCharge,OrderChargeService> {
    @Autowired
    AuthSpaceService authSpaceService;
    @RequestMapping(value="/financecharge")
    public String financecharge(HttpServletRequest request, Model model) {
        Map<String,String> params=getParameterMap(request);
        //选择service
        User user= PortalUtil.getUser(request);
        String userid=user.getId().toString();
        params.put("userid",userid);
        String ord=params.get("ord");
        String pageSize=params.get("pageSize");
        PageInfo<?> fina= entityService.queryPaged1(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(fina, new DictFilter()));
        if (o != null) {
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
                list.add(obj);
            }
            model.addAttribute("rows",list);
            model.addAttribute("ord",ord);
            model.addAttribute("pageSize",pageSize);
            model.addAttribute("lastPage",o.get("lastPage"));
            model.addAttribute("currentPage",o.get("pageNum"));
            //获取用户余额
            Double usercash = authSpaceService.getuserbalance(user.getId());
            model.addAttribute("balance", usercash);
        }

        return getNameSpace()+"financecharge";
    }
}
