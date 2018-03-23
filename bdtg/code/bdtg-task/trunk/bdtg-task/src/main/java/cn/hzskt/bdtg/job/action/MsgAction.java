package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.common.constants.DictKeys;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.service.MemberService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.ryian.orm.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.Msg;
import cn.hzskt.bdtg.job.service.MsgService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
*
* @description:Msg action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/msg")
@SuppressWarnings("serial")
public class MsgAction extends MagicAction<Msg,MsgService> {

    @Autowired
    DictService dictService;
    @Autowired
    MemberService memberService;
    @Autowired
    AuthSpaceService authSpaceService;

    @RequestMapping(value = "allMsg",method = RequestMethod.GET)
    public String allMsg(HttpServletRequest request,Model model) {
        model.addAttribute("viewStatus", dictService.getDictsByKey(DictKeys.VIEWSTATUS).values());
        return getNameSpace() + "allMsg";
    }

    @RequestMapping(value = "fdMsg",method = RequestMethod.GET)
    public String fdMsg(HttpServletRequest request,Model model) {
        model.addAttribute("viewStatus", dictService.getDictsByKey(DictKeys.VIEWSTATUS).values());
        return getNameSpace() + "fdMsg";
    }

    @RequestMapping(value = "sendMsg",method = RequestMethod.GET)
    public String sendMsg(HttpServletRequest request,Model model) {
        model.addAttribute("viewStatus", dictService.getDictsByKey(DictKeys.VIEWSTATUS).values());
        return getNameSpace() + "sendMsg";
    }

    @RequestMapping(value = "teamMsg",method = RequestMethod.GET)
    public String teamMsg(HttpServletRequest request,Model model) {
        model.addAttribute("viewStatus", dictService.getDictsByKey(DictKeys.VIEWSTATUS).values());
        return getNameSpace() + "teamMsg";
    }
    @RequestMapping(value = "yezhuMsg",method = RequestMethod.GET)
    public String yezhuMsg(HttpServletRequest request,Model model) {
        model.addAttribute("viewStatus", dictService.getDictsByKey(DictKeys.VIEWSTATUS).values());
        return getNameSpace() + "yezhuMsg";
    }
    @RequestMapping(value = "pagelist")
    public void pagelist(HttpServletRequest request,
                           HttpServletResponse response,@RequestParam(value = "jobType")String jobType,Model model) throws Exception {
        try {
            Map<String,String> map = new HashMap<String,String>();
            map=getParameterMap(request);
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            map.put("tid",tid.toString());
            map.put("jobType",jobType);
            map.put("toUid", WebUtil.getUser(request).getId().toString());
            PageInfo<?> page = entityService.queryPaged(map);
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage", o.get("pageNum"));
            printJson(response,o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public String view(HttpServletRequest request,@PathVariable("id") Long id,Model model) throws Exception{
        if (id != null) {
            BaseEntity entity = entityService.get(id);
            Msg msg = new Msg();
            msg.setId(id);
            msg.setViewStatus(2);
            entityService.saveOrUpdate(msg);
            model.addAttribute("model", entity);
        }
        return getNameSpace() + "view";
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void delete(HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                entityService.logicRemove(Long.parseLong(id));
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String add(HttpServletRequest request,Model model) {
        return getNameSpace() + "add";
    }
    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取登录用户基本信息
            User user= WebUtil.getUser(request);
            //获取登录用户的项目信息
            Member member = memberService.getMemberByTaskAndUser(WebUtil.getTid(request),user.getId());
            //获取页面提交的标题和内容
            Msg o = bindEntity(request, entityClass);
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                //获取接收短信人的个人信息
                AuthSpace authSpace = authSpaceService.getAuthSpaceByUsrId(id);
                Msg msg = new Msg();
                msg.setUid(user.getId());
                msg.setUsername(user.getUserName());
                msg.setToUid(Long.parseLong(id));
                msg.setToUsername(authSpace.getUserName());
                msg.setMsgStatus(0);
                msg.setViewStatus(1);
                msg.setTitle(o.getTitle());
                msg.setContent(o.getContent());
                msg.setTid(WebUtil.getTid(request));
                msg.setType(3);
                Integer type=0;
                if(member.getType()==2){
                    type=2;
                }else if(member.getType()==1||member.getType()==4||member.getType()==5){
                    type=1;
                }else{
                    type=member.getType();
                }
                msg.setJobType(type);
                msg.setValid(1);
                entityService.saveOrUpdate(msg);
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }
    @RequestMapping(value = "sendlist")
    public void sendlist(HttpServletRequest request,
                         HttpServletResponse response,@RequestParam(value = "jobType")String jobType,Model model) throws Exception {
        try {
            Map<String,String> map = new HashMap<String,String>();
            map=getParameterMap(request);
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            map.put("tid",tid.toString());
            map.put("jobType",jobType);
            map.put("uid", WebUtil.getUser(request).getId().toString());
            PageInfo<?> page = entityService.queryPaged(map);
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage", o.get("pageNum"));
            printJson(response,o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "sendview/{id}",method = RequestMethod.GET)
    public String sendview(HttpServletRequest request,@PathVariable("id") Long id,Model model) throws Exception{
        if (id != null) {
            BaseEntity entity = entityService.get(id);
            model.addAttribute("model", entity);
        }
        return getNameSpace() + "view";
    }
}
