package cn.hzskt.bdtg.task.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ryian.core.utils.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.task.domain.Recruitment;
import cn.hzskt.bdtg.task.service.RecruitmentService;

import com.alibaba.fastjson.JSON;

/**
*
* @description:Recruitment action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/task/zmrc")
public class RecruitmentAction extends MagicAction<Recruitment, RecruitmentService> {

	@Autowired
	private DictService dictService;
	
	@RequestMapping("dictOpt")
	public void dictOpt(HttpServletRequest request, HttpServletResponse response){
		List<Dict> dicts = dictService.selectDictByKeyName("zmrc_status");
		this.printJson(response, JSON.toJSONString(dicts));
	}
	
	@RequestMapping("passZmrc")
	public void passZmrc(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		this.entityService.passZmrc(id);
		Map data = new HashMap();
		this.printJson(response, JSON.toJSONString(data));
	}
	
	@RequestMapping("rejectZmrc")
	public void rejectZmrc(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		this.entityService.rejectZmrc(id);
		Map data = new HashMap();
		this.printJson(response, JSON.toJSONString(data));
	}
	
	

}
