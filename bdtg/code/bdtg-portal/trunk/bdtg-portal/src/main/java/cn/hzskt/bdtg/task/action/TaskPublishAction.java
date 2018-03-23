package cn.hzskt.bdtg.task.action;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.common.utils.oss.OSSConfig;
import cn.hzskt.bdtg.common.utils.oss.OSSUtil;
import cn.hzskt.bdtg.config.domain.Industry;
import cn.hzskt.bdtg.config.service.DistrictService;
import cn.hzskt.bdtg.config.service.IndustryService;
import cn.hzskt.bdtg.financial.service.FinanceService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.domain.TaskFile;
import cn.hzskt.bdtg.task.service.TaskFileService;
import cn.hzskt.bdtg.task.service.TaskService;

import com.alibaba.fastjson.JSON;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("taskpub")
public class TaskPublishAction extends BaseMagicAction {
	
	private static final String SESSIONKEY = "taskModel";
	
	@Autowired
	private IndustryService industryService;
	@Autowired
	private TaskFileService taskFileService;
	@Autowired
	private AuthSpaceService authSpaceService;
	@Autowired
	private DistrictService districtService; 
	@Autowired
	private FinanceService financeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private DictService dictService;
	
	@RequestMapping("pub")
	public String pub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "redirect:/taskpub/step?usrType=1&step=2";
	}
	
	@RequestMapping("step")
	public String step(HttpServletRequest request, HttpServletResponse response, int usrType, int step) throws Exception {
		request.setAttribute("usrType", usrType);
		request.setAttribute("taskstep", step);
		
		User user = PortalUtil.getUser(request);
		String key = SESSIONKEY + user.getId();
		
		//request.getSession().removeAttribute(key);
		
		Map data = (Map) request.getSession().getAttribute(key);
		String classId = MapUtils.getString(data, "indusPid", "");
		this.sessionData(request, step);
		
		if(usrType == 1){	//我是业主
			if(step == 1){	//角色类型和任务类型选择
				return "task/publish/task-chose";
			}
			
			if(step == 2){
				AuthSpace auth = this.authSpaceService.getAuthByUsrId(user.getId());
				if(auth!=null){
					request.setAttribute("auth", auth);
				}
				
				List<Industry> items = industryService.selectByUsrType("0", String.valueOf(usrType));
				request.setAttribute("industies", items);
				
				Map params = this.bindMap(request);
				String indusPid = MapUtils.getString(params, "indusPid", "");
				if(StringUtils.isNotBlank(indusPid)){
					classId = indusPid;
				}
				
				if(StringUtils.isBlank(classId) && items!=null && items.size() > 0){
					if(StringUtils.isBlank(classId)){
						classId = String.valueOf(items.get(0).getId());
					}
				}
				List<Industry> children = industryService.selectChildren(classId);
				request.setAttribute("industChildren", children);
				request.setAttribute("indusPid", classId);
				if("1".equals(classId)){
					List<Dict> dicts = dictService.selectDictByKeyName("task_gcsj_zzyq_type");
					request.setAttribute("zzyqdict", dicts);
					dicts = dictService.selectDictByKeyName("task_gcsj_yjyq_type");
					request.setAttribute("yjyqdict", dicts);
					
					String fileMapKey = key + "_task";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					//工程设计
					return "task/publish/gcsj/task-content";
				}
				
				if("5".equals(classId)){
					List<Dict> dicts = dictService.selectDictByKeyName("task_qqzx_zzyq_type");
					request.setAttribute("zzyqdict", dicts);
					dicts = dictService.selectDictByKeyName("task_qqzx_yjyq_type");
					request.setAttribute("yjyqdict", dicts);
					
					String fileMapKey = key + "_task";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					//前期咨询
					return "task/publish/qqzx/task-content";
				}
				
				if("17".equals(classId)){
					//项目建设管理
					List<Dict> dicts = dictService.selectDictByKeyName("task_xmjsgl_yjyq_type");
					request.setAttribute("yjyqdict", dicts);
					String fileMapKey = key + "_task";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					return "task/publish/xmjsgl/task-content";
				}
				
				if("29".equals(classId)){
					//技术服务
					String fileMapKey = key + "_task";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					List<Dict> dicts = dictService.selectDictByKeyName("task_jsfw_yjyq_type");
					request.setAttribute("yjyqdict", dicts);
					return "task/publish/jsfw/task-content";
				}
				
				if("32".equals(classId)){
					//设备材料采购
					String fileMapKey = key + "_task";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					return "task/publish/sbclcg/task-content";
				}
				
				if("35".equals(classId)){
					//招募人才
					List<Dict> dicts = dictService.selectDictByKeyName("task_zmrc_xlyq_type");
					request.setAttribute("xlyqdict", dicts);
					
					dicts = dictService.selectDictByKeyName("task_zmrc_xzyq_type");
					request.setAttribute("xzyqdict", dicts);
					
					dicts = dictService.selectDictByKeyName("task_zmrc_gzjy_type");
					request.setAttribute("gzjydict", dicts);
					
					dicts = dictService.selectDictByKeyName("task_zmrc_zwlb_type");
					request.setAttribute("zwlbdict", dicts);
					
					request.setAttribute("areas", districtService.selectChildren("0"));
					return "task/publish/zmrc/task-content";
				}
			}
			
			if(step == 3){
				if("1".equals(classId)){
					//工程设计
					String fileMapKey = key + "_task-biddoc";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					return "task/publish/gcsj/task-config";
				}
				
				if("5".equals(classId)){
					//前期咨询
					String fileMapKey = key + "_task-biddoc";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					return "task/publish/qqzx/task-config";
				}
				
				if("17".equals(classId)){
					//项目建设管理
					String fileMapKey = key + "_task-biddoc";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					return "task/publish/xmjsgl/task-config";
				}
				
				if("29".equals(classId)){
					//技术服务
					String fileMapKey = key + "_task-biddoc";
					Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
					if(attachMap!=null){
						List<TaskFile> attaches = new ArrayList<TaskFile>();
						Iterator<String> it = attachMap.keySet().iterator();
						while(it.hasNext()){
							String id = it.next();
							attaches.add(taskFileService.get(Long.parseLong(id)));
						}
						request.setAttribute("attaches", attaches);
					}
					return "task/publish/jsfw/task-config";
				}
				
				if("32".equals(classId)){
					//设备材料采购
					String orderNum = MapUtils.getString(data, "orderNum");
					if(StringUtils.isBlank(orderNum)){
						data.put("orderNum", makeOrderNum());
					}
					Industry a = industryService.getIndustry(MapUtils.getString(data, "indusPid"));
					Industry b = industryService.getIndustry(MapUtils.getString(data, "indusId"));
					
					StringBuffer name = new StringBuffer();
					if(a != null && StringUtils.isNotBlank(a.getIndusName())){
						name.append(a.getIndusName());
						if(b != null && StringUtils.isNotBlank(b.getIndusName())){
							name.append("-");
							name.append(b.getIndusName());
						}
					}
					request.setAttribute("industryName", name);
					return "task/publish/sbclcg/task-confirm";
				}
				
				if("35".equals(classId)){
					//招募人才
					return "task/publish/zmrc/task-payment";
				}
			}
			
			if(step == 4){
				if("1".equals(classId)){
					//工程设计
					//业主承诺 数字字典
					request.setAttribute("yzcn", this.dictService.selectDictByKeyName("task_yzcn_type"));
					//其他服务 数字字典
					request.setAttribute("qtfw", this.dictService.selectDictByKeyName("task_qtfw_type"));
					return "task/publish/gcsj/task-security";
				}
				
				if("5".equals(classId)){
					//前期咨询
					String zzyq = MapUtils.getString(data, "zzyq", "");
					request.setAttribute("zzyq", this.dictService.selectDict("task_qqzx_zzyq_type", zzyq));
					
					String yjyq = MapUtils.getString(data, "yjyq", "");
					request.setAttribute("yjyq", this.dictService.selectDict("task_qqzx_yjyq_type", yjyq));
					
					this.setAttribute(request, true);
					
					Industry a = industryService.getIndustry(MapUtils.getString(data, "indusPid"));
					Industry b = industryService.getIndustry(MapUtils.getString(data, "indusId"));
					
					StringBuffer name = new StringBuffer();
					if(a != null && StringUtils.isNotBlank(a.getIndusName())){
						name.append(a.getIndusName());
						if(b != null && StringUtils.isNotBlank(b.getIndusName())){
							name.append("-");
							name.append(b.getIndusName());
						}
					}
					request.setAttribute("industryName", name);
					
					String product = MapUtils.getString(data, "product", "");
					List<Dict> dicts = this.dictService.selectDictItems("task_product_type", product.split(","));
					int len = dicts.size();
					name = new StringBuffer();
					for(int i=0; i<len; i++){
						Dict dict = dicts.get(i);
						name.append(dict.getContent());
						if(i < len-1) name.append("+");
					}
					request.setAttribute("productName", name);
					return "task/publish/qqzx/task-confirm";
				}
				
				if("17".equals(classId)){
					//项目建设管理
					String orderNum = MapUtils.getString(data, "orderNum");
					if(StringUtils.isBlank(orderNum)){
						data.put("orderNum", makeOrderNum());
					}
					Industry a = industryService.getIndustry(MapUtils.getString(data, "indusPid"));
					Industry b = industryService.getIndustry(MapUtils.getString(data, "indusId"));
					
					StringBuffer name = new StringBuffer();
					if(a != null && StringUtils.isNotBlank(a.getIndusName())){
						name.append(a.getIndusName());
						if(b != null && StringUtils.isNotBlank(b.getIndusName())){
							name.append("-");
							name.append(b.getIndusName());
						}
					}
					request.setAttribute("industryName", name);
					
					String product = MapUtils.getString(data, "product", "");
					List<Dict> dicts = this.dictService.selectDictItems("task_product_type", product.split(","));
					int len = dicts.size();
					name = new StringBuffer();
					for(int i=0; i<len; i++){
						Dict dict = dicts.get(i);
						name.append(dict.getContent());
						if(i < len-1) name.append("+");
					}
					request.setAttribute("productName", name);
					
					String yjyq = MapUtils.getString(data, "yjyq", "");
					request.setAttribute("yjyq", this.dictService.selectDict("task_xmjsgl_yjyq_type", yjyq));
					
					return "task/publish/xmjsgl/task-confirm";
				}
				
				if("29".equals(classId)){
					//技术服务
					String orderNum = MapUtils.getString(data, "orderNum");
					if(StringUtils.isBlank(orderNum)){
						data.put("orderNum", makeOrderNum());
					}
					Industry a = industryService.getIndustry(MapUtils.getString(data, "indusPid"));
					Industry b = industryService.getIndustry(MapUtils.getString(data, "indusId"));
					
					StringBuffer name = new StringBuffer();
					if(a != null && StringUtils.isNotBlank(a.getIndusName())){
						name.append(a.getIndusName());
						if(b != null && StringUtils.isNotBlank(b.getIndusName())){
							name.append("-");
							name.append(b.getIndusName());
						}
					}
					request.setAttribute("industryName", name);
					
					String product = MapUtils.getString(data, "product", "");
					List<Dict> dicts = this.dictService.selectDictItems("task_product_type", product.split(","));
					int len = dicts.size();
					name = new StringBuffer();
					for(int i=0; i<len; i++){
						Dict dict = dicts.get(i);
						name.append(dict.getContent());
						if(i < len-1) name.append("+");
					}
					request.setAttribute("productName", name);
					
					String yjyq = MapUtils.getString(data, "yjyq", "");
					request.setAttribute("yjyq", this.dictService.selectDict("task_jsfw_yjyq_type", yjyq));
					return "task/publish/jsfw/task-confirm";
				}
				
				if("32".equals(classId)){
					//设备材料采购
					
				}
				
				if("35".equals(classId)){
					//招募人才
				}
				
			}
			
			if(step == 5){
				if("1".equals(classId)){
					//工程设计
					
					String orderNum = MapUtils.getString(data, "orderNum");
					if(StringUtils.isBlank(orderNum)){
						data.put("orderNum", makeOrderNum());
					}
					
					request.setAttribute("yzcn", this.dictService.selectDictByKeyName("task_yzcn_type"));	//业主承诺 数字字典
					request.setAttribute("qtfw", this.dictService.selectDictByKeyName("task_qtfw_type"));	//其他服务 数字字典
					
					String zzyq = MapUtils.getString(data, "zzyq", "");
					request.setAttribute("zzyq", this.dictService.selectDict("task_gcsj_zzyq_type", zzyq));
					
					String yjyq = MapUtils.getString(data, "yjyq", "");
					request.setAttribute("yjyq", this.dictService.selectDict("task_gcsj_yjyq_type", yjyq));
					
					Industry a = industryService.getIndustry(MapUtils.getString(data, "indusPid"));
					Industry b = industryService.getIndustry(MapUtils.getString(data, "indusId"));
					
					StringBuffer name = new StringBuffer();
					if(a != null && StringUtils.isNotBlank(a.getIndusName())){
						name.append(a.getIndusName());
						if(b != null && StringUtils.isNotBlank(b.getIndusName())){
							name.append("-");
							name.append(b.getIndusName());
						}
					}
					request.setAttribute("industryName", name);
					
					String product = MapUtils.getString(data, "product", "");
					List<Dict> dicts = this.dictService.selectDictItems("task_product_type", product.split(","));
					int len = dicts.size();
					name = new StringBuffer();
					for(int i=0; i<len; i++){
						Dict dict = dicts.get(i);
						name.append(dict.getContent());
						if(i < len-1) name.append("+");
					}
					request.setAttribute("productName", name);
					
					return "task/publish/gcsj/task-confirm";
				}
			}
			
			if(step == 6){
				if("1".equals(classId)){
					return "task/publish/gcsj/task-payment";
				}
			}
		}
		return "task/publish/task-ordered";
	}
	
	@RequestMapping("pubsuc")
	public String pubsuc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classId = request.getParameter("indusPid");
		if("1".equals(classId)){
			//工程设计
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("step", 7);
			maps.put("link", "0");
			request.setAttribute("model", maps);
			return "task/publish/gcsj/task-ordered";
		}
		
		if("5".equals(classId)){
			//前期咨询
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("step", 7);
			maps.put("link", "0");
			request.setAttribute("model", maps);
			return "task/publish/qqzx/task-ordered";
		}
		
		
		if("17".equals(classId)){
			//项目建设管理
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("step", 5);
			maps.put("link", "0");
			request.setAttribute("model", maps);
			return "task/publish/xmjsgl/task-ordered";
		}
		
		if("29".equals(classId)){
			//技术服务
			//项目建设管理
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("step", 5);
			maps.put("link", "0");
			request.setAttribute("model", maps);
			return "task/publish/jsfw/task-ordered";
		}
		
		if("32".equals(classId)){
			//设备材料采购
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("step", 4);
			maps.put("link", "0");
			request.setAttribute("model", maps);
			return "task/publish/sbclcg/task-ordered";
		}
		
		if("35".equals(classId)){
			//招募人才
		}
		return "task/publish/gcsj/task-ordered";
	}
	
	/**
	 * 类目选择
	 * @return
	 */
	@RequestMapping("chose")
	public String chose(HttpServletRequest request){
		this.sessionData(request, -1);
		List items = industryService.selectChildren("0");
		request.setAttribute("industies", items);
		return "task/publish/task-chose";
	}
	
	@RequestMapping("industry")
	public void industry(String pid, HttpServletResponse reponse) throws Exception {
		List items = industryService.selectChildren(pid);
		reponse.getWriter().print(JSON.toJSONString(items));
	}
	
	@RequestMapping("pubsubmit")
	public void submitchose(HttpServletRequest request, HttpServletResponse reponse, int usrType, int step) throws Exception{
		User user = PortalUtil.getUser(request);
		String key = SESSIONKEY + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		if(data == null) data = new HashMap();
		Integer num = MapUtils.getInteger(data, "step", 0);
		
		setAttribute(request, false);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "success");
		result.put("typeId", usrType);
		result.put("nextstep", 0);
		reponse.getWriter().print(JSON.toJSONString(result));
	}
	
	@RequestMapping("saveQqzx")
	public void saveQqzx(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		User user = PortalUtil.getUser(request);
		String key = SESSIONKEY + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		
		String requirement = StringUtils.defaultString(request.getParameter("age_requirement"), "0");
		data.put("ageRequirement", Integer.parseInt(requirement));
		
		Task task = taskService.addTask(data, user);
		long id = task.getId();
		
		Map<String, Object> fileMap = (Map) MapUtils.getObject(data, "fileMap", new HashMap<String, Object>());
		Iterator<String> it = fileMap.keySet().iterator();
		while(it.hasNext()){
			String fileId = it.next();
			TaskFile file = new TaskFile();
			file.setId(Long.parseLong(fileId));
			file.setTaskId(Integer.parseInt(String.valueOf(id)));
			file.setTaskTitle(task.getTaskTitle());
			taskFileService.saveOrUpdate(file);
		}
		request.getSession().removeAttribute(key);
		request.getSession().removeAttribute(key+"_task");
		request.getSession().removeAttribute(key+"_task-biddoc");
		data = (Map) request.getSession().getAttribute(key);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "success");
		result.put("indusPid", task.getIndusPid());
		reponse.getWriter().print(JSON.toJSONString(result));
	}
	
	/**
	 * 需求描述
	 * @return
	 */
	@RequestMapping("content")
	public String content(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		List items = industryService.selectChildren("0");
		request.setAttribute("industies", items);
		this.sessionData(request, 0);
		this.attacheFiles(request);
		return "task/publish/task-content";
	}
	
	private void attacheFiles(HttpServletRequest request){
		User usr = PortalUtil.getUser(request);
		String sessionKey = SESSIONKEY + usr.getId();
		Map data = (Map) request.getSession().getAttribute(sessionKey);
		if(data == null) data = new HashMap();
		Map<String, Object> fileMap = (Map) MapUtils.getObject(data, "fileMap", new HashMap<String, Object>());
		List<TaskFile> items = new ArrayList<TaskFile>();
		
		Iterator<String> it = fileMap.keySet().iterator();
		while(it.hasNext()){
			String id = it.next();
			items.add(taskFileService.get(Long.parseLong(id)));
		}
		request.setAttribute("attaches", items);
	}
	
	/**
	 * 任务配置
	 * @return
	 */
	@RequestMapping("config")
	public String config(HttpServletRequest request){
		this.sessionData(request, 1);
		return "task/publish/task-config";
	}
	
	/**
	 * 担保金设置
	 * @return
	 */
	@RequestMapping("security")
	public String security(HttpServletRequest request){
		this.sessionData(request, 2);
		return "task/publish/task-security";
	}
	
	/**
	 * 需求确认
	 * @return
	 */
	@RequestMapping("confirm")
	public String confirm(HttpServletRequest request){
		Map maps = this.sessionData(request, 3);
		String orderNum = MapUtils.getString(maps, "orderNum");
		if(StringUtils.isBlank(orderNum)){
			maps.put("orderNum", makeOrderNum());
		}
		return "task/publish/task-confirm";
	}
	
	@RequestMapping("payment")
	public String payment(HttpServletRequest request){
		this.sessionData(request, 4);
		return "task/publish/task-payment";
	}
	
	@RequestMapping("orderPay")
	public void orderPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = PortalUtil.getUser(request);
		String key = SESSIONKEY + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		
		double balance = authSpaceService.getuserbalance(user.getId());
		Double guarantee = MapUtils.getDouble(data, "guarantee");
		if(guarantee == null){
			result.put("success", false);
			result.put("type", "1");
			response.getWriter().write(JSON.toJSONString(result));
			return;
		}
		
		if(guarantee > balance){
			result.put("success", false);
			result.put("type", "2");
			response.getWriter().write(JSON.toJSONString(result));
			return;
		}
		
		Task task = taskService.addTask(data, user);
		long id = task.getId();
		double blanace = MapUtils.getDoubleValue(data, "guarantee", 0);
		if(blanace > 0){
			financeService.balancepay(blanace, user, "pub_task", Integer.parseInt(String.valueOf(id)), "task", "担保金支付：" + blanace, 0);
		}
		
		Map<String, Object> fileMap = (Map) MapUtils.getObject(data, "fileMap", new HashMap<String, Object>());
		Iterator<String> it = fileMap.keySet().iterator();
		while(it.hasNext()){
			String fileId = it.next();
			TaskFile file = new TaskFile();
			file.setId(Long.parseLong(fileId));
			file.setTaskId(Integer.parseInt(String.valueOf(id)));
			file.setTaskTitle(task.getTaskTitle());
			taskFileService.saveOrUpdate(file);
		}
		request.getSession().removeAttribute(key);
		data = (Map) request.getSession().getAttribute(key);
		result.put("success", true);
		result.put("indusPid", task.getIndusPid());
		response.getWriter().write(JSON.toJSONString(result));
	}
	
	@RequestMapping("ordered")
	public String ordered(HttpServletRequest request){
		this.sessionData(request, 5);
		return "task/publish/task-ordered";
	}
	
	@RequestMapping("task-chose-product")
	public String choseProduct(HttpServletRequest request, String code, String type){
		Map<String, String> params = new HashMap<String, String>();
		params.put("keyName", code);
		List<Dict> items = dictService.query(params);
		
		request.setAttribute("items", items);
		request.setAttribute("type", type);
		return "task/publish/task-chose-product";
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
			String key = "task/pub/" + System.currentTimeMillis() + "." + extension;
			OSSUtil.upload(file.getInputStream(), bucket, key);
			String ossDomain = OSSConfig.INSTANCE.getOssDomain("file");
			fileUrl = ossDomain + "/" + key;
			
			TaskFile tf = new TaskFile();
			User user = PortalUtil.getUser(request);
			if(user!=null){
				tf.setUid(Integer.parseInt(String.valueOf(user.getId())));
				tf.setUsername(user.getName());
			}
			
			String objType = request.getParameter("objType");
			if(StringUtils.isBlank(objType)) objType = "task";
			
			tf.setFileName(filename);
			tf.setSaveName(fileUrl);
			tf.setObjType(objType);
			
			taskFileService.saveOrUpdate(tf);
			
			String sessionKey = SESSIONKEY + user.getId();
			Map data = (Map) request.getSession().getAttribute(sessionKey);
			if(data == null) data = new HashMap();
			
			Map<String, Object> fileMap = (Map) MapUtils.getObject(data, "fileMap", new HashMap<String, Object>());
			fileMap.put(String.valueOf(tf.getId()), null);
			
			data.put("fileMap", fileMap);
			request.getSession().setAttribute(sessionKey, data);
			
			String fileMapKey = sessionKey + "_" + objType;
			
			Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
			if(attachMap == null) attachMap = new HashMap();
			attachMap.put(String.valueOf(tf.getId()), null);
			request.getSession().setAttribute(fileMapKey, attachMap);

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
		
		User user = PortalUtil.getUser(request);
		String sessionKey = SESSIONKEY + user.getId();
		Map data = (Map) request.getSession().getAttribute(sessionKey);
		if(data == null) data = new HashMap();
		
		Map<String, Object> fileMap = (Map) MapUtils.getObject(data, "fileMap", new HashMap<String, Object>());
		fileMap.remove(String.valueOf(id));
		
		String objType = request.getParameter("objType");
		if(StringUtils.isBlank(objType)) objType = "task";
		String fileMapKey = sessionKey + "_" + objType;
		
		Map attachMap = (Map) request.getSession().getAttribute(fileMapKey);
		if(attachMap!=null) attachMap.remove(id);
		
		resp.getWriter().write("{\"status\":1}");
	}
	
	private Map sessionData(HttpServletRequest request, int num){
		User user = PortalUtil.getUser(request);
		String key = SESSIONKEY + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		if(data == null) {
			data = new HashMap();
		}
		Integer step = MapUtils.getInteger(data, "step", 0);
		if(step < num){
			data.put("step", num);
			request.getSession().setAttribute(key, data);
		}
		request.setAttribute("model", data);
		return data;
	}
	
	private void setAttribute(HttpServletRequest request, boolean makeOrderNum){
		User user = PortalUtil.getUser(request);
		String key = SESSIONKEY + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		if(data == null) data = new HashMap();
		Map params = this.getParameterMap(request);
		data.putAll(params);
		request.getSession().setAttribute(key, data);
		request.setAttribute("model", data);
		
		if(makeOrderNum){
			String orderNum = MapUtils.getString(data, "orderNum");
			if(StringUtils.isBlank(orderNum)){
				data.put("orderNum", makeOrderNum());
			}
		}
	}
	
	private static String makeOrderNum(){
		String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
		NumberFormat fmt = NumberFormat.getInstance();
		fmt.setMinimumIntegerDigits(3);
		return time + fmt.format(new Random().nextInt(1000))+fmt.format(new Random().nextInt(1000));
	}
	
}
