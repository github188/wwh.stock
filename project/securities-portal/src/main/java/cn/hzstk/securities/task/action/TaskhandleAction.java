package cn.hzstk.securities.task.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.common.utils.beanutil.Map2BeanUtils;
import cn.hzstk.securities.common.utils.oss.OSSConfig;
import cn.hzstk.securities.common.utils.oss.OSSUtil;
import cn.hzstk.securities.config.domain.District;
import cn.hzstk.securities.config.service.DistrictService;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.task.domain.Task;
import cn.hzstk.securities.task.domain.TaskBid;
import cn.hzstk.securities.task.domain.TaskFile;
import cn.hzstk.securities.task.service.TaskBidService;
import cn.hzstk.securities.task.service.TaskFileService;
import cn.hzstk.securities.task.service.TaskService;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;

/**
 * 任务交稿跳转
 * @author zhangjf
 *
 */
@Controller
@RequestMapping("taskHandle")
public class TaskhandleAction extends BaseMagicAction {

	@Autowired
	private DistrictService districtService; 
	@Autowired
	private TaskFileService taskFileService;
	@Autowired
	private TaskBidService taskBidService;
	@Autowired
	private TaskService taskService;
	
	
	/**
	 * 普通招标-投稿页面
	 * @param request
	 * @return
	 */
	@RequestMapping("quote")
	public String execute(HttpServletRequest request, String id) {
		request.setAttribute("model", taskService.selectTaskById(id));
		List<District> items = districtService.selectChildren("0");
		request.setAttribute("areas", items);
		return "task/handle/quote_manuscript";
	}
	
	@Login(action = Action.Skip)
	@RequestMapping("quotesubmit")
	public void submitQuote(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = this.bindMap(request);
		TaskBid bid = Map2BeanUtils.convert(params, TaskBid.class);
		bid.setHasdel(0);
		bid.setBidStatus(0);
		User usr = PortalUtil.getUser(request);
		if(usr!=null){
			bid.setUid(usr.getId());
			bid.setUsername(usr.getUserName());
		}
		taskBidService.saveOrUpdate(bid);
		taskService.updateTaskWorkNum(String.valueOf(bid.getTaskId()), 1);
		
		String fileId = MapUtils.getString(params, "file_ids", "");
		if(StringUtils.isNotBlank(fileId)){
			for(String id : fileId.split("\\|")){
				TaskFile file = new TaskFile();
				file.setId(Long.parseLong(id));
				file.setWorkId(Integer.parseInt(String.valueOf(bid.getId())));
				file.setTaskId(bid.getTaskId());
				taskFileService.saveOrUpdate(file);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	@RequestMapping("chageBidStatus")
	public void chageBidStatus(String taskId, String bid, String status, HttpServletResponse response) throws Exception {
		taskBidService.changeBidStatus(bid, status);
		if("4".equals(status)){	//4 表示中标
			Task task = new Task();
			task.setId(Long.parseLong(taskId));
			task.setTaskStatus("p4");
			taskService.saveOrUpdate(task);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	@RequestMapping("pubAgreement")
	public void pubAgreement(String taskId, HttpServletResponse response) throws Exception {
		Task task = new Task();
		task.setId(Long.parseLong(taskId));
		task.setTaskStatus("5");
		taskService.saveOrUpdate(task);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	@RequestMapping("workOver")
	public void workOver(String taskId, HttpServletResponse response) throws Exception {
		Task task = new Task();
		task.setId(Long.parseLong(taskId));
		task.setTaskStatus("8");
		taskService.saveOrUpdate(task);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	@RequestMapping("areaOps")
	public void selectAreaOptions(HttpServletResponse response, String pid) throws Exception {
		List<District> items = districtService.selectChildren(pid);
		response.getWriter().print(JSON.toJSONString(items));
	}
	
	@RequestMapping(value = "/upload")  
	public void uploader(@RequestParam(value = "upload") MultipartFile file, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> msg = new HashMap<String, Object>();
		
		String filename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(filename);
		String fileUrl = "";
		boolean error = false;
		try {
			String bucket = OSSConfig.INSTANCE.getFileBucket();	//文件包
			String key = "task/work/" + System.currentTimeMillis() + "." + extension;
			OSSUtil.upload(file.getInputStream(), bucket, key);
			String ossDomain = OSSConfig.INSTANCE.getOssDomain("file");
			fileUrl = ossDomain + "/" + key;
			
			TaskFile tf = new TaskFile();
			User user = PortalUtil.getUser(request);
			if(user!=null){
				tf.setUid(Integer.parseInt(String.valueOf(user.getId())));
				tf.setUsername(user.getName());
			}
			tf.setFileName(filename);
			tf.setSaveName(fileUrl);
			tf.setObjType("work");
			
			taskFileService.saveOrUpdate(tf);
			
			msg.put("url", fileUrl);
			msg.put("filename", "upload");
			msg.put("name", filename);
			msg.put("fileid", tf.getId());
		} 
		catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("error", error);
		data.put("msg", msg);
		resp.getWriter().write(JSON.toJSONString(data));
	}
	
	@RequestMapping(value = "/deleteFile")
	public void deleteFile(HttpServletRequest request, HttpServletResponse resp, String id) throws Exception {
		taskFileService.removeTaskFile(id);
		resp.getWriter().write("{\"status\":1}");
	}
	
	public static void main(String[] args) {
		String str = "32|21";
		for(String s:str.split("\\|")){
			
			System.out.println(s);
			
		}
	}
	
}
