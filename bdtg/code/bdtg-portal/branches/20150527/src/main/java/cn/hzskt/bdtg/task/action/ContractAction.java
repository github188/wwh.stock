package cn.hzskt.bdtg.task.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.message.ONSConfig;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.common.utils.oss.OSSConfig;
import cn.hzskt.bdtg.common.utils.oss.OSSUtil;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.task.domain.Contract;
import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.domain.TaskBid;
import cn.hzskt.bdtg.task.domain.TaskFile;
import cn.hzskt.bdtg.task.service.ContractService;
import cn.hzskt.bdtg.task.service.TaskBidService;
import cn.hzskt.bdtg.task.service.TaskFileService;
import cn.hzskt.bdtg.task.service.TaskService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;

/**
 * 服务商签署合同操作
 * @author Administrator
 * 业主设置项目金额、分期付款金额及合同文件信息
 * 服务商确认合同
 * 
 */
@Controller
@RequestMapping("taskHandle")
public class ContractAction extends BaseMagicAction {

	@Autowired
	private TaskFileService taskFileService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private TaskBidService taskBidService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProducerBean producer;
	
	@RequestMapping("contractitem")
	public String item(HttpServletRequest request, HttpServletResponse response, String taskId) throws Exception {
		Task task = taskService.get(Long.parseLong(taskId));
		request.setAttribute("model", task);
		String status = task.getTaskStatus();
		int step = 0;
		
		if("p4".equals(status)){
			step = 1;
		}
		else if("s4".equals(status)){
			step = 2;
		}
		else {
			step = 3;
		}
		request.setAttribute("step", step);
		TaskBid bid = taskBidService.getBid(taskId);
		
		User usr = PortalUtil.getUser(request);
		
		request.setAttribute("opera", bid.getUid() == usr.getId());

		if("p4".equals(task.getTaskStatus())){
			return this.execute(request, response, taskId);
		}
		return this.contractitem(request, response, taskId);
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response, String taskId) throws Exception {
		request.setAttribute("taskId", taskId);
		return "task/contract/owcontract";
	}
	
	public String contractitem(HttpServletRequest request, HttpServletResponse response, String taskId){
		List items = contractService.selectItems(taskId);
		request.setAttribute("items", items);
		
		TaskBid bid = taskBidService.getBid(taskId);
		List attach = taskFileService.selectTaskFiles(taskId, "contract", String.valueOf(bid.getId()));
		
		request.setAttribute("files", attach);
		request.setAttribute("taskId", taskId);
		return "task/contract/contractitem";
	}
	
	@RequestMapping("saveContract")
	public void saveOwContract(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User usr = PortalUtil.getUser(request);
		
		String[] data = request.getParameterValues("contractData");
		
		Map params = this.bindMap(request);
		String ids = MapUtils.getString(params, "file_ids", "");
		String tkId = MapUtils.getString(params, "taskId");
		
		Task tsk = taskService.get(Long.parseLong(tkId));
		
		contractService.addTskContract(jsonBean(data, tkId, usr.getId(), tsk.getTaskTitle()));
		
		Task task = new Task();
		task.setId(Long.parseLong(tkId));
		task.setTaskStatus("s4");	//将任务状态置成合同待确认状态
		taskService.saveOrUpdate(task);
		
		TaskBid bid = taskBidService.getBid(tkId);
		taskFileService.updateFieldIds(ids, tkId, tsk.getTaskTitle(), String.valueOf(bid.getId()));
		this.printJson(response, "{}");
	}
	
	private List<Contract> jsonBean(String[] data, String tskId, Long usrId, String taskTitle){
		List<Contract> items = new ArrayList<Contract>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		for(String s : data){
			JSONObject json = JSONObject.parseObject(s);
			try {
				Contract item = new Contract();
				item.setTkId(Long.parseLong(tskId));
				item.setPyCash(json.getDoubleValue("numeric"));	//金额
				item.setPyTime(fmt.parse(json.getString("time")));
				item.setPyContent(json.getString("desc"));
				item.setTaskTitle(taskTitle);
				item.setCreator(usrId);
				items.add(item);
			} 
			catch (Exception e) {
				
			}
		}
		return items;
	}
	
	/**
	 * 合同确认
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("confirmht")
	public void confirmation(String taskId, HttpServletResponse response) throws Exception {
		
		Task task = new Task();
		task.setId(Long.parseLong(taskId));
		task.setTaskStatus("4");	//业主确认合同后进入工作中
		taskService.saveOrUpdate(task);
		
		this.sendMessage(taskId, "4");	//服务商确认合同后，发一条消息
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("success", true);
		this.printJson(response, JSON.toJSONString(params));
	}
	
	private void sendMessage(String taskId, String status){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", Long.parseLong(taskId));
		data.put("status", Integer.parseInt(status));
		
		String topic = ONSConfig.INSTANCE.getTopicTaskStatus();
		String tags = "";
		String body = JSON.toJSONString(data);
		
		Message msg = new Message(topic, tags, body.getBytes()); 
		SendResult result = producer.send(msg);
		
		
		
		
		
	}
	
	/**
	 * 合同文件上传
	 * @param file
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("upContractFile")
	public void uploadFile(@RequestParam(value = "upload") MultipartFile file, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> msg = new HashMap<String, Object>();
		String filename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(filename);
		String fileUrl = "";
		boolean error = false;
		try {
			String bucket = OSSConfig.INSTANCE.getFileBucket();	//文件包
			String key = "task/contract/" + System.currentTimeMillis() + "." + extension;
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
			
			tf.setObjType("contract");	//合同文件
			
			taskFileService.saveOrUpdate(tf);
			
			msg.put("url", fileUrl);
			msg.put("filename", "upload");
			msg.put("name", filename);
			msg.put("fileid", tf.getId());
		} 
		catch (Exception e) {
			error = true;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("error", error);
		data.put("msg", msg);
		
		this.printJson(resp, JSON.toJSONString(data));
	}
	
	@RequestMapping(value = "/rmcontract")
	public void rmContract(HttpServletRequest request, HttpServletResponse resp, String id) throws Exception {
		taskFileService.removeTaskFile(id);
		resp.getWriter().write("{\"status\":1}");
	}

}
