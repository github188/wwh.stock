package cn.hzstk.securities.task.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.sys.domain.Dict;
import cn.hzstk.securities.sys.service.DictService;
import cn.hzstk.securities.task.service.RecruitmentService;

@Controller
public class ZmrcListAction extends BaseMagicAction {

	@Autowired
	private RecruitmentService recruitmentService;
	@Autowired
	private DictService dictService;
	
	@RequestMapping("zmrclist")
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map requestMap = this.bindMap(request);
		request.setAttribute("paramData", requestMap);
		this.requestDict(request);
		
		PageInfo page = recruitmentService.selectPaged(requestMap);
		request.setAttribute("page", page);

		request.setAttribute("finishItems", recruitmentService.selectFinished(8));
		
		return "task/zmrc/zmrclist";
	}
	
	private void requestDict(HttpServletRequest request){
		List<Dict> dicts = dictService.selectDictByKeyName("zmrc_sjy_zwxz_type");	//职位性质
		request.setAttribute("yglxdict", dicts);
		
		dicts = dictService.selectDictByKeyName("zmrc_sjy_zwlb_type");				//职位类别
		request.setAttribute("zwlbdict", dicts);
		
		dicts = dictService.selectDictByKeyName("zmrc_sjy_zyxz_type");				//专业选择
		request.setAttribute("zyxzdict", dicts);
		
		dicts = dictService.selectDictByKeyName("task_zmrc_gzjy_type");				//工作经验
		request.setAttribute("gzjydict", dicts);
		
		dicts = dictService.selectDictByKeyName("task_zmrc_xzyq_type");				//薪资要求
		request.setAttribute("xzyqdict", dicts);
		
		dicts = dictService.selectDictByKeyName("task_zmrc_xlyq_type");				//学历要求
		request.setAttribute("xlyqdict", dicts);
		
		dicts = dictService.selectDictItemsWithOut("zmrc_status", new String[]{"0"});
		request.setAttribute("statusDicts", dicts);
	}

}
