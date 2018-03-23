package com.jeecms.bbs.action.member;

import static com.jeecms.bbs.Constants.TPLDIR_MEMBER;
import static com.jeecms.bbs.Constants.TPLDIR_TOPIC;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.bbs.entity.BbsCreditExchange;
import com.jeecms.bbs.entity.BbsForum;
import com.jeecms.bbs.entity.BbsUser;
import com.jeecms.bbs.entity.BbsUserExt;
import com.jeecms.bbs.manager.BbsCreditExchangeMng;
import com.jeecms.bbs.manager.BbsForumMng;
import com.jeecms.bbs.manager.BbsUserMng;
import com.jeecms.bbs.web.CmsUtils;
import com.jeecms.bbs.web.FrontUtils;
import com.jeecms.bbs.web.WebErrors;
import com.jeecms.common.security.encoder.PwdEncoder;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.MessageResolver;
import com.jeecms.core.entity.CmsConfigItem;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsConfigItemMng;
import com.jeecms.core.manager.UnifiedUserMng;

@Controller
public class UserPostAct {

	public static final String MEMBER_CENTER = "tpl.memberCenter";
	public static final String MEMBER_INFORM = "tpl.information";
	public static final String MEMBER_TOPIC = "tpl.memberTopic";
	public static final String MEMBER_POST = "tpl.memberPost";
	public static final String SEARCH = "tpl.search";
	public static final String SEARCH_RESULT = "tpl.searchResult";
	public static final String TPL_NO_LOGIN = "tpl.nologin";
	public static final String TPL_CREDIT = "tpl.creditMng";
	public static final String TPL_EDIT_USERIMG = "tpl.edituserimg";
	public static final String TPL_NO_VIEW = "tpl.noview";
	public static final String TPL_PWD = "tpl.memberPassword";

	@RequestMapping("/member/index.jhtml")
	public String index(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		model.put("user", user);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, MEMBER_CENTER);
	}

	@RequestMapping("/member/information.jhtml")
	public String information(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		List<CmsConfigItem>items=cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
		model.addAttribute("items", items);
		model.put("user", user);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, MEMBER_INFORM);
	}
	
	
	@RequestMapping("/member/editUserImg.jhtml")
	public String editUserImg(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		model.put("user", user);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, TPL_EDIT_USERIMG);
	}
	
	@RequestMapping("/member/updateUserImg.jhtml")
	public String updateUserImg(String email,
			String newPassword, String signed, String avatar, BbsUserExt ext,
			HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		if(!user.getId().equals(ext.getId())){
			model.put("msg", "更新错误");
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_VIEW);
		}
		manager.updateMember(user.getId(), email, newPassword, null, signed,
				avatar, ext, null,null);
		model.put("user", user);
		FrontUtils.frontPageData(request, model);
		return information(request, model);
	}

	@RequestMapping("/member/update.jspx")
	public String informationSubmit(String email,
			String newPassword, String signed, String avatar, BbsUserExt ext,
			HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = WebErrors.create(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		if(!user.getId().equals(ext.getId())){
			model.put("msg", "更新错误");
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_VIEW);
		}
		if (errors.ifNotEmail(email, "email", 100)) {
			return FrontUtils.showError(request, response, model, errors);
		}
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
		user = manager.updateMember(user.getId(), email, newPassword, null, signed,
				avatar, ext,attrs, null);
		List<CmsConfigItem>items=cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
		model.put("user", user);
		model.addAttribute("items", items);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, MEMBER_INFORM);
	}

	@RequestMapping("/member/mytopic*.jhtml")
	public String mytopic(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		model.put("user", user);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, MEMBER_TOPIC);
	}

	@RequestMapping("/member/mypost*.jhtml")
	public String mypost(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		model.put("user", user);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, MEMBER_POST);
	}

	@RequestMapping(value = "/member/inputSearch*.jhtml", method = RequestMethod.GET)
	public String search(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, SEARCH);
	}

	@RequestMapping(value = "/member/search*.jhtml")
	public String searchSubmit( Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		String keywords = RequestUtils.getQueryParam(request, "keywords");
		
		Integer forumId=Integer.parseInt(RequestUtils.getQueryParam(request, "forumId"));
		
		model.put("keywords", keywords);
		model.put("forumId", forumId);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, SEARCH_RESULT);
	}

	@RequestMapping("/member/creditManager.jhtml")
	public String creditManager(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsCreditExchange creditExchangeRule = creditExchangeMng.findById(site
				.getId());
		Boolean exchangeAvailable = false;
		if (creditExchangeRule.getPointinavailable()
				&& creditExchangeRule.getPrestigeoutavailable()
				|| creditExchangeRule.getPointoutavailable()
				&& creditExchangeRule.getPrestigeinavailable()) {
			exchangeAvailable = true;
		} else {
			exchangeAvailable = false;
		}
		if (exchangeAvailable) {
			if (!creditExchangeRule.getExpoint().equals(0)
					&& !creditExchangeRule.getExprestige().equals(0)) {
				exchangeAvailable = true;
			} else {
				exchangeAvailable = false;
			}
		}
		List<BbsForum> forums = forumMng.getList(site.getId());
		model.put("user", user);
		model.put("exchangeAvailable", exchangeAvailable);
		model.put("pointInAvail", creditExchangeRule.getPointinavailable());
		model.put("pointOutAvail", creditExchangeRule.getPointoutavailable());
		model.put("prestigeInAvail", creditExchangeRule
				.getPrestigeinavailable());
		model.put("prestigeOutAvail", creditExchangeRule
				.getPrestigeoutavailable());
		model.put("forums", forums);
		model.put("creditExchangeRule", creditExchangeRule);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site,
				TPLDIR_MEMBER, TPL_CREDIT);
	}

	@RequestMapping(value = "/member/getCreditOutValue.jspx")
	public void getCreditOutValue(Integer creditIn, Integer creditInType,
			Integer creditOutType, double exchangetax,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Long creditOutValue = null;
		Double temp = 0.0;
		BbsCreditExchange rule = CmsUtils.getSite(request).getCreditExchange();
		if (creditInType.equals(1) && creditOutType.equals(2)) {
			// 积分换取威望
			temp = creditIn * rule.getExpoint() * 1.0 / rule.getExprestige()
					* (1.0 + exchangetax);
		} else if (creditInType.equals(2) && creditOutType.equals(1)) {
			// 威望换积分
			temp = creditIn * rule.getExprestige() * 1.0 / rule.getExpoint()
					* (1.0 + exchangetax);
		}
		creditOutValue = Long.valueOf((long) Math.ceil(temp));
		try {
			object.put("creditOutValue", creditOutValue);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	@RequestMapping(value = "/member/creditExchange.jspx")
	public void creditExchange(Integer creditIn, Integer creditOut,
			Integer creditOutType,  String password,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		BbsUser user = CmsUtils.getUser(request);
		Boolean result=true;
		String message=MessageResolver.getMessage(request, "cmspoint.success");
		BbsCreditExchange rule = CmsUtils.getSite(request).getCreditExchange();
		Integer miniBalance=rule.getMiniBalance();
		//验证密码
		if (!pwdEncoder.isPasswordValid(unifiedUserMng.getByUsername(
				user.getUsername()).getPassword(), password)) {
			result = false;
			message=MessageResolver.getMessage(request, "cmscredit.passworderror");
		}else{
			//验证兑换规则
			int valid=validExchange(rule, user, creditIn, creditOut, creditOutType);
			if(valid==1){
				result = false;
				message=MessageResolver.getMessage(request, "cmscredit.pointisnotenough",miniBalance);
			}else if(valid==2){
				result = false;
				message=MessageResolver.getMessage(request, "cmscredit.prestigeisnotenough",miniBalance);
			}else if(valid==3){
				result = false;
				message=MessageResolver.getMessage(request, "operate.faile");
			}else{
				if(creditOutType.equals(1)){
					user.setPoint(user.getPoint()-creditOut);
					user.setPrestige(user.getPrestige()+creditIn);
				}else if(creditOutType.equals(2)){
					user.setPrestige(user.getPrestige()-creditOut);
					user.setPoint(user.getPoint()+creditIn);
				}
				//此处更新用户积分威望信息
				manager.updatePwdEmail(user.getId(), password, user.getEmail());
			}
		}
		try {
			object.put("message", message);
			object.put("result", result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseUtils.renderJson(response, object.toString());
	}
	
	@RequestMapping(value = "/member/pwd.jhtml", method = RequestMethod.GET)
	public String pwd(HttpServletRequest request,ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		return FrontUtils.getTplPath(request, site,TPLDIR_MEMBER, TPL_PWD);
	}
	
	@RequestMapping(value = "/member/pwd.jhtml", method = RequestMethod.POST)
	public String pwd_update(String origPwd,String password,HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site,
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		if(!pwdEncoder.isPasswordValid(unifiedUserMng.getByUsername(user.getUsername()).getPassword(), origPwd)){
			WebErrors errors =WebErrors.create(request);
			errors.addErrorCode("member.update.pwd.error");
			return FrontUtils.showError(request, response, model, errors);
		}
		manager.updatePwdEmail(user.getId(), password, null);
		return pwd(request, model);
	}
	
	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/member/checkPwd.jhtml")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		BbsUser user = CmsUtils.getUser(request);
		boolean pass = manager.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}
	
	private int validExchange(BbsCreditExchange rule,BbsUser user,Integer creditIn, Integer creditOut,
			Integer creditOutType){
		//兑出的积分是否充足
		if(user!=null&&creditOutType.equals(1)){
			if(user.getPoint()-creditOut<rule.getMiniBalance()){
				return 1;
			}
		}
		//兑出的威望是否充足
		if(user!=null&&creditOutType.equals(2)){
			if(user.getPrestige()-creditOut<rule.getMiniBalance()){
				return 2;
			}
		}
		Integer creditOutValue = null;
		Double temp = 0.0;
		if (creditOutType.equals(2)) {
			// 兑威望
			temp = creditIn * rule.getExpoint() * 1.0 / rule.getExprestige()
					* (1.0 + rule.getExchangetax());
		} else if (creditOutType.equals(1)) {
			// 兑积分
			temp = creditIn * rule.getExprestige() * 1.0 / rule.getExpoint()
					* (1.0 + rule.getExchangetax());
		}
		creditOutValue = Integer.valueOf((int) Math.ceil(temp));
		//creditOut兑换需要的积分或者威望被恶意修改
		if(!creditOutValue.equals(creditOut)){
			return 3;
		}
		return 0;
	}

	@Autowired
	private BbsUserMng manager;
	@Autowired
	private BbsCreditExchangeMng creditExchangeMng;
	@Autowired
	private BbsForumMng forumMng;
	@Autowired
	private PwdEncoder pwdEncoder;
	@Autowired
	private UnifiedUserMng unifiedUserMng;
	@Autowired
	private CmsConfigItemMng cmsConfigItemMng;
}
