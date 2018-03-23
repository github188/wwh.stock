package cn.hzskt.bdtg.task.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.config.service.IndustryService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.task.domain.Mark;
import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.domain.TaskBid;
import cn.hzskt.bdtg.task.service.CommentService;
import cn.hzskt.bdtg.task.service.MarkService;
import cn.hzskt.bdtg.task.service.TaskBidService;
import cn.hzskt.bdtg.task.service.TaskFileService;
import cn.hzskt.bdtg.task.service.TaskService;

/**
 * @author 工程设计-项目详情
 *
 */
@Controller
public class QqzxDetailAction extends BaseMagicAction {

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
	
	@RequestMapping("qqzx-detail-work")
	public String work(HttpServletRequest request, String id, String status){
		Task tk = taskService.get(Long.parseLong(id));
		this.requestBidData(request, tk, status);
		List<TaskBid> items = taskBidService.queryTaskBid(id, status);
		this.addComment(request, items, id);
		return "task/detail/qqzx/detail-work";
	}
	
	@RequestMapping("qqzx-detail-mark")
	public String mark(HttpServletRequest request, String id, String status){
		Task tk = taskService.get(Long.parseLong(id));
		this.requestBidData(request, tk, status);
		List items = markMap(markService.selectTaskMask(id));
		request.setAttribute("items", items);
		return "task/detail/qqzx/detail-mask";
	}
	
	private void requestBidData(HttpServletRequest request, Task tk, String status){
		
		String id = String.valueOf(tk.getId());
		
		request.setAttribute("model", tk);
		
		AuthSpace sp = this.authSpaceService.getAuthByUsrId(Long.parseLong(String.valueOf(tk.getUid())));
		request.setAttribute("yz", sp);
		
		Map<String, Object> indusmap = new HashMap<String, Object>();
		indusmap.put("indus", industryService.getIndustry(String.valueOf(tk.getIndusId())));
		indusmap.put("pindus", industryService.getIndustry(String.valueOf(tk.getIndusPid())));
		request.setAttribute("indusmap", indusmap);
		
		List files = taskFileService.selectTaskFiles(String.valueOf(tk.getId()));
		if(files == null) files = new ArrayList();
		request.setAttribute("attaches", files);
		request.setAttribute("attachNum", files.size());
		request.setAttribute("attachBidfiles", taskFileService.selectTaskBidFiles(String.valueOf(tk.getId())));
		
		request.setAttribute("type", "");
		
		request.setAttribute("bidNum", taskBidService.selectBidNum(id, null));
		request.setAttribute("maskNum", markService.selectTaskMask(id).size());
		
		User usr = PortalUtil.getUser(request);
		if(usr!=null){
			request.setAttribute("usr", usr);
			request.setAttribute("isbid", taskBidService.selectBidNum(id, String.valueOf(usr.getId())) > 0);
		}
		
		String zzyqName = "无要求";
		Dict zzyq = dictService.selectDict("task_qqzx_zzyq_type", tk.getZzyq());
		if(zzyq!=null){
			zzyqName = StringUtils.defaultIfEmpty(zzyq.getContent(), "");
		}
		request.setAttribute("zzyqName", zzyqName);
		
		String yjyqName = "无要求";
		Dict yjyq = dictService.selectDict("task_qqzx_yjyq_type", tk.getYjyq());
		if(yjyq!=null){
			yjyqName = StringUtils.defaultIfEmpty(yjyq.getContent(), "");
		}
		request.setAttribute("yjyqName", yjyqName);
		request.setAttribute("productName", dictService.selectDictNameByArray("task_product_type", tk.getProduct(), ",", "+"));
	}
	
	private void addComment(HttpServletRequest request, List<TaskBid> items, String taskId){
		User usr = PortalUtil.getUser(request);
		List<Map> data = new ArrayList<Map>();
		for(TaskBid bid : items){
			String id = String.valueOf(bid.getId());
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("model", bid);
			map.put("comments", commentService.selectBidComment(taskId, id));
			map.put("mark", markService.hasMark(taskId, id, usr==null?null:String.valueOf(usr.getId())));
			List attache = taskFileService.selectTaskFiles(taskId, "work", id);
			map.put("attaches", attache);
			
			if(bid.getUid()!=null){
				AuthSpace auth = authSpaceService.getAuthByUsrId(bid.getUid());
				if(auth!=null){
					map.put("usrpic", auth.getUserPic());
				}
			}
			data.add(map);
		}
		request.setAttribute("items", data);
	}
	
	private List<Map> markMap(List<Mark> data){
		List<Map> items = new ArrayList<Map>();
		
		for(Mark mark : data){
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("byUid", mark.getByUid());
			item.put("byUname", mark.getByUsername());
			item.put("uid", mark.getUid());
			item.put("uname", mark.getUsername());
			item.put("status", mark.getMarkStatus());
			item.put("content", mark.getMarkContent());
			item.put("time", mark.getMarkTime());
			String[] aids = StringUtils.defaultIfEmpty(mark.getAid(), "").split(",");
			String[] star = StringUtils.defaultIfEmpty(mark.getAidStar(), "").split(",");
			List<Map> maps = new ArrayList<Map>();
			for(int i=0; i<aids.length; i++){
				Map map = new HashMap();
				map.put("name", name(aids[i]));
				map.put("val", star[i]);
				maps.add(map);
			}
			item.put("score", maps);
			items.add(item);
		}
		return items;
	}
	
	private static String name(String name){
		
		if("1".equals(name)){
			return "工作速度";
		}
		
		if("2".equals(name)){
			return "工作质量";
		}
		
		if("3".equals(name)){
			return "工作态度";
		}
		
		if("4".equals(name)){
			return "付款及时性";
		}
		
		if("5".equals(name)){
			return "合作愉快度";
		}
		return "";
	}
	
}
