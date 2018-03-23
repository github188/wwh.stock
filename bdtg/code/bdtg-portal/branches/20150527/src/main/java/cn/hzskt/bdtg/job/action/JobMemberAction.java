package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.sys.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
*
* @description:Member action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/jobmember")
@SuppressWarnings("serial")
public class JobMemberAction extends MagicAction<Member,MemberService> {

    @RequestMapping(value="job_manage")
    public String job_manage(HttpServletRequest request) {
        Map<String,String> params = bindMap(request);
        User usr = PortalUtil.getUser(request);
        params.put("userId",String.valueOf(usr.getId()));
        PageInfo page =entityService.selectByUserid(params);
        request.setAttribute("pagenation", page);
        request.setAttribute("maps", params);
        PortalUtil.setUrls(request);
        return "task/mytask/job_manage";
    }

}
