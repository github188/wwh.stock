package cn.hzskt.bdtg.task.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.config.service.IndustryService;
import cn.hzskt.bdtg.task.service.TaskService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/tasklist")
public class TaskListAction extends BaseMagicAction {

	@Autowired
	private IndustryService industryService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping
	public String execute(HttpServletRequest request, String status) {
		PageInfo pagenation = taskService.selectOnline(this.getParameterMap(request));
		request.setAttribute("page", pagenation);
		request.setAttribute("status", status);
		List items = industryService.selectAllMap();
		request.setAttribute("classItems", items);
		return "task/list/tasklist";
	}
	
}
