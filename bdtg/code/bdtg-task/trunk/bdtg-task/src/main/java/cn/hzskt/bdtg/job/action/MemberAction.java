package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.common.message.ONSConfig;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.AuMember;
import cn.hzskt.bdtg.job.mapper.MemberMapper;
import cn.hzskt.bdtg.member.mapper.AuthSpaceMapper;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.sys.mapper.DictMapper;
import cn.hzskt.bdtg.sys.service.UserService;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Producer;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.service.MemberService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @description:Member action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/job/member")
@SuppressWarnings("serial")
public class MemberAction extends MagicAction<Member,MemberService> {

    @Autowired
    UserService userService;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    AuthSpaceMapper authSpaceMapper;
    @Autowired
    AuthSpaceService authSpaceService;
    @Autowired
    DictMapper dictMapper;
    @Autowired
    Producer producer;

    @RequestMapping(value = "Findex",method = RequestMethod.GET)
    public String Findex(HttpServletRequest request,Model model) {
        model.addAttribute("dictlist", dictMapper.getdictlist());
        return getNameSpace() + "findex";
    }

    @RequestMapping(value = "Yindex",method = RequestMethod.GET)
    public String Yindex(HttpServletRequest request,Model model) {
        return getNameSpace() + "yindex";
    }
    @RequestMapping(value = "Zindex",method = RequestMethod.GET)
    public String Zindex(HttpServletRequest request,Model model) {
        return getNameSpace() + "zindex";
    }
    /**
     * 服务商
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "pagelist")
    public void pagelist(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        try {
            Map<String,String> map = new HashMap<String,String>();
            map = getParameterMap(request);
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            map.put("tid",tid.toString());
            PageInfo<AuMember> page = entityService.getAumemberList(map);
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

    @RequestMapping(value = "adduser")
    public String adduser(Model model,String type) {
        model.addAttribute("type",type);
        return getNameSpace() + "userlist";
    }

    @RequestMapping(value = "userlist")
    public void userlist(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
            PageInfo<?> page = authSpaceService.queryPaged(getParameterMap(request));
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
    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "saveuser")
    public void saveuser(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="userId")String userId,@RequestParam(value="userName")String userName,@RequestParam(value="type")String type) {
        try {
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            Map<String,String> map = new HashMap<String,String>();
            map.put("userId",userId);
            map.put("tid", tid.toString());
            int namecount =   memberMapper.getcount(map);
            if(namecount==0){
                Member member = new Member();
                member.setType(1);
                member.setUserId(Long.valueOf(userId));
                member.setUserName(userName);
                member.setType(Integer.parseInt(type));
                member.setTid(tid);
                entityService.saveOrUpdate(member);
                ONSConfig.sendJobStatusMessage(producer, request, "添加成员【" + userName+"】", "A");
                printJson(response, messageSuccuseWrap());
            }else{
                printJson(response, messageFailureWrap("该用户已存在！"));
            }

        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }
    @RequestMapping(value = "updatesubmbtype",method = RequestMethod.POST)
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,String id,String submbtype) {
        try {
            Long tid = WebUtil.getTid(request);
            int count=0;
            if(tid==null){
                tid=Long.parseLong("1");
            }
            if(submbtype.equals("4")||submbtype.equals("5")){
                Map<String,String> map = new HashMap<>();
                map.put("submbtype",submbtype);
                map.put("tid",tid.toString());
                count = memberMapper.getsubcount(map);
            }
           if(count>0){
               if(submbtype.equals("5")){
                   printJson(response, messageFailureWrap("该项目中已存在项目主任！"));
               }else{
                   printJson(response, messageFailureWrap("该项目中已存在PM！"));
               }
           }else{
               String submbtypeStr="";
               if("5".equals(submbtype)){
                   submbtypeStr="项目主任";
               }else if("4".equals(submbtype)){
                   submbtypeStr="项目主任";
               }else {
                   submbtypeStr="成员";
               }
               Member mb =  entityService.get(Long.parseLong(id));
               mb.setType(Integer.parseInt(submbtype));
               ONSConfig.sendJobStatusMessage(producer, request, "设置【" + mb.getUserName()+"】为【"+submbtypeStr+"】", "A");
               entityService.saveOrUpdate(mb);
           }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            printJson(response, messageFailureWrap("岗位设置失败！"));
        }
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void delete(HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                Member mb =  entityService.get(Long.parseLong(id));
                entityService.logicRemove(Long.parseLong(id));
                ONSConfig.sendJobStatusMessage(producer, request, "删除成员【" + mb.getUserName()+"】", "A");
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }
}
