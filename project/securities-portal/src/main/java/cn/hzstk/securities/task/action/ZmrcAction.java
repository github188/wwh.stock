package cn.hzstk.securities.task.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.common.utils.beanutil.Map2BeanUtils;
import cn.hzstk.securities.config.domain.Industry;
import cn.hzstk.securities.config.service.DistrictService;
import cn.hzstk.securities.config.service.IndustryService;
import cn.hzstk.securities.financial.service.FinanceService;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.domain.Dict;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.service.DictService;
import cn.hzstk.securities.task.domain.Recruitment;
import cn.hzstk.securities.task.service.RecruitmentService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("taskpub")
public class ZmrcAction extends BaseMagicAction {

	@Autowired
	private RecruitmentService recruitmentService;
	@Autowired
	private AuthSpaceService authSpaceService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private IndustryService industryService;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private DictService dictService;
	
	@RequestMapping("zmrc")
	public String step(HttpServletRequest request, HttpServletResponse response, int usrType, int step) throws Exception {
		
		request.setAttribute("usrType", usrType);
		request.setAttribute("taskstep", step);
		
		List<Dict> dicts = dictService.selectDictByKeyName("task_zmrc_xlyq_type");
		request.setAttribute("xlyqdict", dicts);
		
		dicts = dictService.selectDictByKeyName("task_zmrc_xzyq_type");
		request.setAttribute("xzyqdict", dicts);
		
		dicts = dictService.selectDictByKeyName("task_zmrc_gzjy_type");
		request.setAttribute("gzjydict", dicts);
		
		dicts = dictService.selectDictByKeyName("task_zmrc_zwlb_type");
		request.setAttribute("zwlbdict", dicts);
		
		dicts = dictService.selectDictByKeyName("zmrc_yglx_type");
		request.setAttribute("yglxdict", dicts);
		
		dicts = dictService.selectDictByKeyName("zmrc_yjsxz_type");
		request.setAttribute("yjsxzdict", dicts);
		
		request.setAttribute("areas", districtService.selectChildren("0"));
		
		User user = PortalUtil.getUser(request);
		String key = "zmrc" + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		if(data == null){
			data = new HashMap();
			request.getSession().setAttribute(key, data);
		}
		
		int num = MapUtils.getIntValue(data, "step", 0);
		if(num < step){
			data.put("step", step);
		}
		
		request.setAttribute("model", data);
		
		if(step == 1){
			return "task/zmrc/task-chose";
		}
		
		if(usrType == 1){
			return ownstep(request, response, step);
		}
		if(usrType == 2){
			return digntep(request, response, step);
		}
		return "task/publish/zmrc/task-content";
	}
	
	private String ownstep(HttpServletRequest request, HttpServletResponse response, int step) throws Exception {
		if(step == 2){
			List<Industry> items = industryService.selectByUsrType("0", "1");
			request.setAttribute("industies", items);
			return "task/zmrc/own/task-content";
		}
		
		if(step == 3){
			return "task/zmrc/own/task-payment";
		}
		return "";
	}

	private String digntep(HttpServletRequest request, HttpServletResponse response, int step) throws Exception {
		if(step == 2){
			List<Dict> dicts = dictService.selectDictByKeyName("zmrc_sjy_zwxz_type");	//职位性质
			request.setAttribute("yglxdict", dicts);
			
			dicts = dictService.selectDictByKeyName("zmrc_sjy_zwlb_type");				//职位类别
			request.setAttribute("zwlbdict", dicts);
			
			dicts = dictService.selectDictByKeyName("zmrc_sjy_zyxz_type");				//专业选择
			request.setAttribute("zyxzdict", dicts);
			
			return "task/zmrc/sjdw/task-content";
		}
		
		if(step == 3){
			return "task/zmrc/sjdw/task-payment";
		}
		return "";
	}
	
	@RequestMapping("cacheZmrc")
	public void submitchose(HttpServletRequest request, HttpServletResponse reponse, int usrType, int step) throws Exception{
		User user = PortalUtil.getUser(request);
		String key = "zmrc" + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		if(data == null) data = new HashMap();
		Map params = this.getParameterMap(request);
		data.putAll(params);
		
		request.getSession().setAttribute(key, data);
		request.setAttribute("model", data);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "success");
		result.put("typeId", usrType);
		result.put("nextstep", 0);
		reponse.getWriter().print(JSON.toJSONString(result));
	}
	
	
	@RequestMapping("saveZmrc")
	public void savezmrc(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String paystatus = request.getParameter("paystatus");	//是否支付金额
		String cash = request.getParameter("cash");				//支付金额
		String paytype = request.getParameter("paytype");		//支付方式
		User user = PortalUtil.getUser(request);
		
		String key = "zmrc" + user.getId();
		Map data = (Map) request.getSession().getAttribute(key);
		if(data == null) data = new HashMap();
		data.put("paystatus", paystatus);
		data.put("status", "0");								//表示记录处于未审批状态
		
		Map<String, Object> result = new HashMap<String, Object>();
		if("0".equals(paystatus)){
			Recruitment model = Map2BeanUtils.convert(data, Recruitment.class);
			recruitmentService.saveOrUpdate(model);
			request.getSession().removeAttribute(key);
			result.put("success", true);
			this.printJson(reponse, JSON.toJSONString(result));
			return;
		}
		
		if("1".equals(paystatus)){
			if(!NumberUtils.isNumber(cash) || (Double.parseDouble(cash) <= 0)){
				result.put("success", false);
				result.put("message", "金额数字无效！！");
				result.put("error", "1");
				this.printJson(reponse, JSON.toJSONString(result));
				return;
			}
			
			if(StringUtils.isBlank(paytype)){
				result.put("success", false);
				result.put("message", "请选择支付类型！！");
				result.put("error", "2");
				this.printJson(reponse, JSON.toJSONString(result));
				return;
			}
			
			double balance = authSpaceService.getuserbalance(user.getId());
			double money = Double.parseDouble(cash);
			if(money > balance){
				result.put("success", false);
				result.put("message", "账户余额不足");
				result.put("error", "3");
				this.printJson(reponse, JSON.toJSONString(result));
				return;
			}
			
			if("8".equals(paytype)){
				data.put("paycash", cash);
				data.put("paytype", paytype);
				Recruitment model = Map2BeanUtils.convert(data, Recruitment.class);
				recruitmentService.saveOrUpdate(model);
				financeService.balancepay(money, user, "rczm", Integer.parseInt(String.valueOf(model.getId())), "task", "人才招募发布费用：" + money, 0);
				request.getSession().removeAttribute(key);
				result.put("success", true);
				this.printJson(reponse, JSON.toJSONString(result));
				return;
			}
		}
		
	}
	

}
