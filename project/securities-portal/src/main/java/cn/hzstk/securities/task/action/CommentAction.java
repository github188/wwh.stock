package cn.hzstk.securities.task.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.common.utils.beanutil.Map2BeanUtils;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.task.domain.Mark;
import cn.hzstk.securities.task.domain.Task;
import cn.hzstk.securities.task.domain.TaskBid;
import cn.hzstk.securities.task.service.MarkService;
import cn.hzstk.securities.task.service.TaskBidService;
import cn.hzstk.securities.task.service.TaskService;

import com.alibaba.fastjson.JSON;

/**
 * 点评操作Action
 * @author zhangjf
 *
 */
@Controller
@RequestMapping("taskHandle")
public class CommentAction extends BaseMagicAction {

	@Autowired
	private TaskBidService taskBidService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MarkService markService;
	
	@RequestMapping("employComment")
	public String employComment(HttpServletRequest request, String bid){
		TaskBid taskBid = taskBidService.get(Long.parseLong(bid));
		Task task = taskService.selectTaskById(String.valueOf(taskBid.getTaskId()));
		request.setAttribute("task", task);
		request.setAttribute("taskbid", taskBid);
		return "task/comment/employComment";
	}
	
	@RequestMapping("addEmpCmm")
	public void addEmpCmm(HttpServletRequest request, HttpServletResponse response, String bid) throws Exception {
		Map requestMap = this.bindMap(request);
		TaskBid taskBid = taskBidService.get(Long.parseLong(bid));
		Mark mark = mapMarkByBoss(requestMap, PortalUtil.getUser(request), taskBid);
		markService.saveOrUpdate(mark);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status",  "success");
		maps.put("data", "评价成功！！");
		maps.put("url",  "#");
		response.getWriter().print(JSON.toJSONString(maps));
	}
	
	private Mark mapMarkByBoss(Map maps, User usr, TaskBid taskBid){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("markStatus", MapUtils.getInteger(maps, "mark_status", 0));
		data.put("markContent", MapUtils.getString(maps, "tar_content"));
		data.put("markTime", new Date());
		
		data.put("uid", taskBid.getUid());
		data.put("username", taskBid.getUsername());
		
		data.put("byUid", usr.getId());	//评论者编号、名称
		data.put("byUsername", usr.getUserName());
		data.put("aid", "1,2,3");
		data.put("aidStar", MapUtils.getString(maps, "star[1]")+","+MapUtils.getString(maps, "star[2]")+","+MapUtils.getString(maps, "star[3]"));
		data.put("markType", "1");
		
		data.put("objId", taskBid.getTaskId());	
		
		Mark mark = Map2BeanUtils.convert(data, Mark.class);
		mark.setByUid(Integer.parseInt(String.valueOf(usr.getId())));
		mark.setOriginId(Integer.parseInt(String.valueOf(taskBid.getId())));
		
		return mark;
	}
	
	/**
	 * 威客点评雇主
	 * @param request
	 * @param bid
	 * @return
	 */
	@RequestMapping("bidComment")
	public String bidComment(HttpServletRequest request, String bid){
		TaskBid taskBid = taskBidService.get(Long.parseLong(bid));
		Task task = taskService.selectTaskById(String.valueOf(taskBid.getTaskId()));
		request.setAttribute("task", task);
		request.setAttribute("taskbid", taskBid);
		return "task/comment/bidComment";
	}
	
	@RequestMapping("addbsCmm")
	public void addbsCmm(HttpServletRequest request, HttpServletResponse response, String bid) throws Exception {
		Map requestMap = this.bindMap(request);
		TaskBid taskBid = taskBidService.get(Long.parseLong(bid));
		Mark mark = mapMarkByEmp(requestMap, PortalUtil.getUser(request), taskBid);
		markService.saveOrUpdate(mark);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status",  "success");
		maps.put("data", "评价成功！！");
		maps.put("url",  "#");
		response.getWriter().print(JSON.toJSONString(maps));
	}
	
	private Mark mapMarkByEmp(Map maps, User usr, TaskBid taskBid){
		Task task = taskService.selectTaskById(String.valueOf(taskBid.getTaskId()));
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("markStatus", MapUtils.getInteger(maps, "mark_status", 0));
		data.put("markContent", MapUtils.getString(maps, "tar_content"));
		data.put("markTime", new Date());
		
		data.put("uid", task.getUid());
		data.put("username", task.getUsername());
		
		data.put("byUid", usr.getId());	//评论者编号、名称
		data.put("byUsername", usr.getUserName());
		data.put("aid", "4,5");
		data.put("aidStar", MapUtils.getString(maps, "star[4]")+","+MapUtils.getString(maps, "star[5]"));
		data.put("markType", "1");
		
		data.put("objId", taskBid.getTaskId());	
		
		Mark mark = Map2BeanUtils.convert(data, Mark.class);
		mark.setByUid(Integer.parseInt(String.valueOf(usr.getId())));
		mark.setOriginId(Integer.parseInt(String.valueOf(taskBid.getId())));
		
		return mark;
	}
	

}
