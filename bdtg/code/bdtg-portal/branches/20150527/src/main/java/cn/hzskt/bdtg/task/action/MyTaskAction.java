package cn.hzskt.bdtg.task.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.task.service.TaskService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("owner")
public class MyTaskAction extends BaseMagicAction {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping("mytask")
	public String mytask(HttpServletRequest request){
		Map params = bindMap(request);
		User usr = PortalUtil.getUser(request);
		PageInfo page = taskService.selectMyTask(String.valueOf(usr.getId()), params);
		request.setAttribute("pagenation", page);
		request.setAttribute("maps", params);
		
		PortalUtil.setUrls(request);
		
		return "task/mytask/mytask";
	}
	
	@RequestMapping("undertake")
	public String undertake(HttpServletRequest request){
		Map params = bindMap(request);
		User usr = PortalUtil.getUser(request);
		PageInfo page = taskService.selectMyUnderTask(String.valueOf(usr.getId()), params);
		request.setAttribute("pagenation", page);
		request.setAttribute("maps", params);
		PortalUtil.setUrls(request);
		return "task/mytask/undertask";
	}
	
	@RequestMapping("bidtask")
	public String bidtask(HttpServletRequest request){
		Map params = bindMap(request);
		User usr = PortalUtil.getUser(request);
		PageInfo page = taskService.selectMyBidTask(String.valueOf(usr.getId()), params);
		request.setAttribute("pagenation", page);
		request.setAttribute("maps", params);
		return "task/mytask/bidtask";
	}
	
}
