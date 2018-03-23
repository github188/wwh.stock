package cn.hzskt.bdtg.job.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ryian.orm.domain.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Process;
import cn.hzskt.bdtg.job.domain.Task;
import cn.hzskt.bdtg.job.service.ProcessService;
import cn.hzskt.bdtg.job.service.TaskService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
*
* @description:Process action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/process")
public class ProcessAction extends MagicAction<Process,ProcessService> {

	private static String CODENAME = "job_node_name";
	
	@Autowired
	private ProcessService processService;
	@Autowired
	private DictService dictService;
	@Autowired
	private TaskService taskService;
	
	@Override
	protected void beforeIndex(HttpServletRequest request, Model model) {
		super.beforeIndex(request, model);
		List<Dict> items = dictService.selectDictByKeyName(CODENAME);
		request.setAttribute("types", items);
	}



	@Override
	protected void beforeAdd(HttpServletRequest request, Model model) {
		super.beforeAdd(request, model);
		long tid = WebUtil.getTid(request);
		request.setAttribute("taskId", tid);
		List<Dict> items = dictService.selectDictByKeyName(CODENAME);
		request.setAttribute("types", items);
	}
	
	@Override
	protected void beforeEdit(HttpServletRequest request, Model model, BaseEntity entity) {
		super.beforeEdit(request, model, entity);
		List<Dict> items = dictService.selectDictByKeyName(CODENAME);
		request.setAttribute("types", items);
	}

	@RequestMapping(value = "queryPaged")
	public void queryPaged(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = getParameterMap(request);
		long tid = WebUtil.getTid(request);
		params.put("tid", String.valueOf(tid));
		PageInfo<Process> page = entityService.queryPaged(params);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", beanMap(page.getList()));
		data.put("total", page.getTotal());
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	private List<Map<String, Object>> beanMap(List<Process> list){
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for(Process process : list){
			Task task = taskService.get(process.getTid());
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("taskTitle", task.getTaskTitle());
			item.put("taskid", process.getTid());
			item.put("type", process.getType());
			item.put("typeName", dictService.selectDictByKeyName(CODENAME, process.getType()).getContent());
			item.put("time", new SimpleDateFormat("yyyy-MM-dd").format(process.getStatusTime()));
			item.put("content", process.getContent());
			item.put("id", process.getId());
			items.add(item);
		}
		return items;
	}

}
