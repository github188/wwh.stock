package cn.hzskt.bdtg.task.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.task.service.TaskService;

@Controller
@RequestMapping("/manage/task")
public class TaskAction extends BaseMagicAction {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping
	public String execute() throws Exception {
		return "task/index";
	}
	
	@RequestMapping("/queryPaged")
	public void pagenation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = this.bindMap(request);
		Map page = taskService.pagenation(params);
		response.getWriter().print(JSON.toJSONString(page));
	}
	
	@RequestMapping("/pass")
	public void passtask(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {
		taskService.pass(id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	@RequestMapping("/reject")
	public void reject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = this.bindMap(request);
		
		String taskId = MapUtils.getString(params, "taskId");
		taskService.reject(taskId);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	
}
