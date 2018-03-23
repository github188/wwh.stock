package cn.hzskt.bdtg.task.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.task.service.TaskService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("owner")
public class MyTaskAction extends BaseMagicAction {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping("mytask")
	public String mytask(HttpServletRequest request){
		Map params = bindMap(request);
		int pageNums = MapUtils.getInteger(params, "page", 1);
		int pageSize = MapUtils.getInteger(params, "size", 15);
		params.put("page", pageNums);
		params.put("size", pageSize);
		
		User usr = PortalUtil.getUser(request);
		PageInfo page = taskService.selectMyTask(String.valueOf(usr.getId()), params, pageNums, pageSize);
		request.setAttribute("pagenation", page);
		request.setAttribute("maps", params);
		
		request.setAttribute("items", dictService.selectDictByKeyName("task_status"));
		
		PortalUtil.setUrls(request);
		return "task/mytask/mytask";
	}
	
	@RequestMapping("undertake")
	public String undertake(HttpServletRequest request){
		Map params = bindMap(request);
		
		int pageNums = MapUtils.getInteger(params, "page", 1);
		int pageSize = MapUtils.getInteger(params, "size", 15);
		params.put("page", pageNums);
		params.put("size", pageSize);
		
		request.setAttribute("items", dictService.selectDictByKeyName("task_status"));
		
		User usr = PortalUtil.getUser(request);
		PageInfo page = taskService.selectMyUnderTask(String.valueOf(usr.getId()), params, pageNums, pageSize);
		request.setAttribute("pagenation", page);
		request.setAttribute("maps", params);
		PortalUtil.setUrls(request);
		return "task/mytask/undertask";
	}
	
	@RequestMapping("bidtask")
	public String bidtask(HttpServletRequest request){
		Map params = bindMap(request);
		
		request.setAttribute("items", dictService.selectDictByKeyName("task_status"));
		
		int pageNums = MapUtils.getInteger(params, "page", 1);
		int pageSize = MapUtils.getInteger(params, "size", 15);
		params.put("page", pageNums);
		params.put("size", pageSize);
		
		User usr = PortalUtil.getUser(request);
		PageInfo page = taskService.selectMyBidTask(String.valueOf(usr.getId()), params, pageNums, pageSize);
		request.setAttribute("pagenation", page);
		request.setAttribute("maps", params);
		return "task/mytask/bidtask";
	}
	
	
	
}
