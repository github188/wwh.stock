package cn.hzstk.securities.task.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.config.service.IndustryService;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.service.DictService;
import cn.hzstk.securities.task.domain.Task;
import cn.hzstk.securities.task.service.CommentService;
import cn.hzstk.securities.task.service.MarkService;
import cn.hzstk.securities.task.service.TaskBidService;
import cn.hzstk.securities.task.service.TaskFileService;
import cn.hzstk.securities.task.service.TaskService;

import com.alibaba.fastjson.JSON;

@Controller
public class TaskDetailAction extends BaseMagicAction {

	@Autowired
	private AuthSpaceService authSpaceService;
	@Autowired
	private IndustryService industryService;
	@Autowired
	private TaskFileService taskFileService;
	@Autowired
	private TaskBidService taskBidService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MarkService markService;
	@Autowired
	private DictService dictService; 
	
	@RequestMapping("task-detail-work")
	public String work(HttpServletRequest request, String id, String status) {
		Task tk = taskService.get(Long.parseLong(id));
		int indusPid = tk.getIndusPid();
		if(indusPid == 1){	//工程设计
			return "redirect:/gcsj-detail-work?id="+id;
		}
		
		if(indusPid == 5){	//前期咨询
			return "redirect:/qqzx-detail-work?id="+id;
		}
		
		if(indusPid == 17){	//项目建设管理
			return "redirect:/xmjsgl-detail-work?id="+id;
		}
		
		if(indusPid == 29){	//技术服务
			return "redirect:/jsfw-detail-work?id="+id;
		}
		
		if(indusPid == 32){	//设备材料采购
			return "redirect:/sbclcg-detail-work?id="+id;
		}
		return "";
	}
	
	@RequestMapping("task-detail-comment")
	public String comment(HttpServletRequest request, String id) {
		//this.selectDetails(request, id, "comment");
		return "task/detail/detail-comment";
	}
	
	@RequestMapping("task-detail-mark")
	public String mark(HttpServletRequest request, String id) {
		Task tk = taskService.get(Long.parseLong(id));
		int indusPid = tk.getIndusPid();
		if(indusPid == 1){	//工程设计
			return "redirect:/gcsj-detail-mark?id="+id;
		}
		
		if(indusPid == 5){	//前期咨询
			return "redirect:/qqzx-detail-mark?id="+id;
		}
		
		if(indusPid == 17){	//项目建设管理
			return "redirect:/xmjsgl-detail-mark?id="+id;
		}
		
		if(indusPid == 29){	//技术服务
			return "redirect:/jsfw-detail-mark?id="+id;
		}
		
		if(indusPid == 32){	//设备材料采购
			return "redirect:/sbclcg-detail-mark?id="+id;
		}
		return "";
	}

	/**
	 * 更新任务浏览次数
	 * @param taskId
	 * @throws Exception
	 */
	@RequestMapping("taskView")
	public void updateTaskViewNum(HttpServletResponse response, String taskId) throws Exception {
		taskService.updateTaskViewNum(taskId, 1);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("success", true);
		response.getWriter().write(JSON.toJSONString(params));
	}
	
}
