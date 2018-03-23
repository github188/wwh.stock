package cn.hzskt.bdtg.job.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ryian.orm.domain.BaseEntity;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Budget;
import cn.hzskt.bdtg.job.domain.Process;
import cn.hzskt.bdtg.job.service.BudgetService;
import cn.hzskt.bdtg.job.service.MemberService;
import cn.hzskt.bdtg.job.service.ProcessService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
*
* @description:Budget action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/budget")
public class BudgetAction extends MagicAction<Budget,BudgetService> {
	
	private static String CODENAME = "job_node_name";
	
	@Autowired
	private AuthSpaceService authSpaceService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private DictService dictService;
	
	
	
	@Override
	protected void beforeIndex(HttpServletRequest request, Model model) {
		long tid = WebUtil.getTid(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "2");
		params.put("tid", String.valueOf(tid));
		List<Process> items = processService.query(params);
		request.setAttribute("options", items);
	}
	
	@Override
	protected void beforeAdd(HttpServletRequest request, Model model) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "2");
		long tid = WebUtil.getTid(request);
		params.put("tid", String.valueOf(tid));
		List<Process> items = processService.query(params);
		request.setAttribute("options", items);
	}

	@Override
	protected void beforeEdit(HttpServletRequest request, Model model, BaseEntity entity) {
		
		Budget bean = (Budget) entity;
		
		Process process = processService.get(bean.getProcessId());
		request.setAttribute("processName", process.getContent());
		
		AuthSpace auth = authSpaceService.getAuthSpaceByUsrId(String.valueOf(bean.getUid()));
		request.setAttribute("username", auth.getUserName());
	}

	

	@Override
	public void queryPaged(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageInfo<Budget> page = entityService.queryPaged(getParameterMap(request));
		long tid = WebUtil.getTid(request);
		Map<String, Object> rows = new HashMap<String, Object>();
		rows.put("rows", this.beanBudgetMap(page.getList(), String.valueOf(tid)));
		rows.put("total", page.getTotal());
		this.printJson(response, JSON.toJSONString(rows));
	}

	private List<Map> beanBudgetMap(List<Budget> data, String tid){
		List<Map> items = new ArrayList();
		for(Budget bean : data){
			AuthSpace auth = authSpaceService.getAuthSpaceByUsrId(String.valueOf(bean.getUid()));
			Map item = new HashMap();
			item.put("id", bean.getId());
			item.put("username", (auth!=null)?auth.getUserName():"");
			item.put("budgetFare", bean.getBudgetFare());	//预算费用
			item.put("realFare", bean.getRealFare());		//累计支付
			item.put("totalFare", this.entityService.sumUsrJobFare(String.valueOf(tid), String.valueOf(bean.getUid())));			//总体预算
			items.add(item);
		}
		return items;
	}


	@RequestMapping("/choseProcess")
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long tid = WebUtil.getTid(request);
		request.setAttribute("taskId", tid);
		List<Dict> items = dictService.selectDictByKeyName(CODENAME);
		request.setAttribute("types", items);
		return this.getNameSpace() + "process";
	}
	
	@RequestMapping("/processPage")
	public void processPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = getParameterMap(request);
		long tid = WebUtil.getTid(request);
		params.put("tid", String.valueOf(tid));
		PageInfo<Process> page = processService.queryPaged(params);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", beanMap(page.getList()));
		data.put("total", page.getTotal());
		response.getWriter().print(JSON.toJSONString(data));
	}
	
	private List<Map<String, Object>> beanMap(List<Process> list){
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for(Process process : list){
			Map<String, Object> item = new HashMap<String, Object>();
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
	
	@RequestMapping("/choseMember")
	public String member(HttpServletRequest request, HttpServletResponse response, String processId) throws Exception {
		request.setAttribute("processId", processId);
		return this.getNameSpace() + "member";
	}
	
	@RequestMapping("/memberPaged")
	public void memberPaged(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = this.bindMapObj(request);
		long tid = WebUtil.getTid(request);
		String pid = MapUtils.getString(params, "processId", "");
		
		List<String> usrIds = this.entityService.queryList(pid);	//所属环节已经设置的用户列表
		List<String> mbs = memberService.getJobMemberIds(tid);		//查询当前任务的参与成员
		for(String id : usrIds){
			mbs.remove(id);
		}
		
		Map<String, Object> rows = new HashMap<String, Object>();
		if(mbs.size() < 1){
			rows.put("rows", new ArrayList());
			rows.put("total", 0);
		}
		else {
			params.put("ids", mbs);
			params.put("jobsearch", "1");
			PageInfo page = authSpaceService.queryPaged(params);
			rows.put("rows", page.getList());
			rows.put("total", page.getTotal());
		}
        printJson(response,JSON.toJSONString(rows));
	}
	
	
}
