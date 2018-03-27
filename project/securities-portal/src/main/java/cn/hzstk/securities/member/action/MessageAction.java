package cn.hzstk.securities.member.action;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.member.domain.Msg;
import cn.hzstk.securities.member.mapper.MsgMapper;
import cn.hzstk.securities.member.service.MsgService;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.ryian.orm.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by allenwc on 16/4/6.
 * 业主管理
 */
@Controller
@RequestMapping("/message")
public class MessageAction extends MagicAction<Msg,MsgService> {
    @Autowired
    MsgMapper msgMapper;
    @RequestMapping(value="msg_manage")
    public String messageMng(Model model,HttpServletRequest request){
        msgsizeget(model,request);
        return super.getNameSpace() + "msg_manage";
    }

    @RequestMapping(value="pagelist")
    public String pagelist(HttpServletRequest request, Model model,@RequestParam(value = "type")String type) {
        Map<String,String> params=getParameterMap(request);
        User user= PortalUtil.getUser(request);
        String userid=user.getId().toString();
        params.put("userid",userid);
        params.put("type",type);
        String pageSize=params.get("pageSize");
        PageInfo<Msg> fina= entityService.queryPaged1(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(fina, new DictFilter()));
        if (o != null) {
            List row=o.getJSONArray("list");
            model.addAttribute("rows",row);
            model.addAttribute("pageSize",pageSize);
            model.addAttribute("lastPage",o.get("lastPage"));
            model.addAttribute("currentPage",o.get("pageNum"));
        }
        model.addAttribute("type",type);
        model.addAttribute("viewStatus",params.get("viewStatus"));
        msgsizeget(model,request);
        return  super.getNameSpace()+ "msglist";
    }
    @RequestMapping(value="detail")
    public String detail(HttpServletRequest request, Model model,@RequestParam(value = "id")String id,@RequestParam(value = "type")String type) {
        BaseEntity entity = entityService.get(Long.parseLong(id));
        //更新成已读状态
        Msg msg = entityService.get(Long.parseLong(id));
        msg.setViewStatus(2);
        entityService.saveOrUpdate(msg);
        model.addAttribute("model", entity);
        model.addAttribute("type",type);
        msgsizeget(model,request);
        return  super.getNameSpace()+ "msgdetail";
    }
    @RequestMapping(value="delete")
    public String delete(HttpServletRequest request, Model model,@RequestParam(value = "id")String id,@RequestParam(value = "type")String type) {
        entityService.logicRemove(Long.parseLong(id));
        msgsizeget(model,request);
        return pagelist(request,model,type);
    }
    @RequestMapping(value="onlinemsglist")
    public String onlinemsglist(HttpServletRequest request, Model model) {
        Map<String,String> params=getParameterMap(request);
        User user= PortalUtil.getUser(request);
        String userid=user.getId().toString();
        params.put("userid",userid);
        params.put("type","3");
        String pageSize=params.get("pageSize");
        PageInfo<Msg> fina= entityService.queryPaged1(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(fina, new DictFilter()));
        if (o != null) {
            List row=o.getJSONArray("list");
            model.addAttribute("rows",row);
            model.addAttribute("pageSize",pageSize);
            model.addAttribute("lastPage",o.get("lastPage"));
            model.addAttribute("currentPage",o.get("pageNum"));
        }
        model.addAttribute("jobType", params.get("jobType"));
        model.addAttribute("viewStatus",params.get("viewStatus"));
        msgsizeget(model,request);
        return  super.getNameSpace()+ "onlinemsglist";
    }
//    @RequestMapping(value="addmsg")
//    public String addmsg(Model model,HttpServletRequest request) {
//        msgsizeget(model,request);
//        return  super.getNameSpace()+ "addmsg-view";
//    }
//    @RequestMapping(value = "userlist")
//    public void userlist(HttpServletRequest request,
//                           HttpServletResponse response,Model model) throws Exception {
//        try {
//            @SuppressWarnings("unchecked")
//            PageInfo<?> page = entityService.queryPaged(getParameterMap(request));
//            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
//            o.put("rows", o.get("list"));
//            o.remove("list");
//            o.put("totalPageCount", o.get("lastPage"));
//            o.put("countPerPage", o.get("pageSize"));
//            o.put("currentPage",o.get("pageNum"));
//            printJson(response, o.toJSONString());
//            msgsizeget(model,request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @RequestMapping(value = "msgsave")
//    public void msgsave(HttpServletRequest request,
//                         HttpServletResponse response,Model model) throws Exception {
//        try {
//            Msg o = bindEntity(request, entityClass);
//            entityService.saveOrUpdate(o);
//            printJson(response, messageSuccuseWrap());
//            msgsizeget(model,request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void msgsizeget(Model model,HttpServletRequest request) {
        try {
            Long userid = PortalUtil.getUser(request).getId();
            //系统通知
            model.addAttribute("xttzsize",msgMapper.getmsgsize(userid, "1")==0?"":msgMapper.getmsgsize(userid, "1"));
            //交易动态
            model.addAttribute("jydtsize",msgMapper.getmsgsize(userid, "2")==0?"":msgMapper.getmsgsize(userid, "2"));
            //在线作业
            model.addAttribute("zxzysize",msgMapper.getmsgsize(userid, "3")==0?"":msgMapper.getmsgsize(userid, "3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
