package cn.hzskt.bdtg.task.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ryian.commons.StringUtils;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.config.service.IndustryService;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.task.domain.CashRange;
import cn.hzskt.bdtg.task.service.CashRangeService;
import cn.hzskt.bdtg.task.service.TaskService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/tasklist")
public class TaskListAction extends BaseMagicAction {

	@Autowired
	private CashRangeService cashRangeService;
	@Autowired
	private IndustryService industryService;
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping
	public String execute(HttpServletRequest request, String status) {
		
		Map params = this.getParameterMap(request);
		String rangeId = MapUtils.getString(params, "rangeId", "");
		if(StringUtils.isNotBlank(rangeId)){
			CashRange m = cashRangeService.get(Long.parseLong(rangeId));
			if(m!=null){
				if(m.getMinVal()!=null) params.put("minCash", m.getMinVal());
				if(m.getMaxVal()!=null) params.put("maxCash", m.getMaxVal());
			}
		}
		
		String orderBy = MapUtils.getString(params, "orderBy", "1");
		String asc = MapUtils.getString(params, "asc", "0");
		
		params.put("orderBy", orderBy);
		params.put("asc", asc);
		
		
		params.put("orderByStr", orderByMap.get(orderBy));
		params.put("ascStr", ascMap.get(asc));
		
		PageInfo pagenation = taskService.selectOnline(params);
		request.setAttribute("paramData", params);
		request.setAttribute("page", pagenation);
		request.setAttribute("status", status);
		List items = industryService.selectAllMap();
		request.setAttribute("classItems", items);
		
		items = industryService.selectByUsrType("0", "1");
		request.setAttribute("industies", items);
		
		items = cashRangeService.query(new HashMap<String, String>());
		request.setAttribute("cashRanges", items);
		
		items = taskService.getlatestBidTask(8);
		request.setAttribute("latestBidTask", items);
		
		items = dictService.selectDictItemsWithOut("task_status", new String[]{"1", "10", "p4", "s4", "5"});
		request.setAttribute("statusDicts", items);
		
		return "task/list/tasklist";
	}
	
	private static Map<String, String> orderByMap = new HashMap<String, String>();
	static {
		orderByMap.put("1", "create_date");
		orderByMap.put("2", "end_time");
		orderByMap.put("3", "work_num");
		orderByMap.put("4", "task_cash");
	}
	
	private static Map<String, String> ascMap = new HashMap<String, String>();
	static {
		ascMap.put("0", "desc");
		ascMap.put("1", "asc");
	}
	
}
