package cn.hzstk.securities.financial.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.sys.service.UserService;
import cn.hzstk.securities.sys.utils.json.DictFilter;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.utils.AESPasswordEncoder;
import cn.hzstk.securities.config.service.DistrictService;
import cn.hzstk.securities.financial.domain.AuthBank;
import cn.hzstk.securities.financial.domain.Finance;
import cn.hzstk.securities.financial.domain.MemberBank;
import cn.hzstk.securities.financial.domain.OrderCharge;
import cn.hzstk.securities.financial.domain.PayApi;
import cn.hzstk.securities.financial.domain.Withdraw;
import cn.hzstk.securities.financial.service.AuthBankService;
import cn.hzstk.securities.financial.service.FinanceService;
import cn.hzstk.securities.financial.service.MemberBankService;
import cn.hzstk.securities.financial.service.OrderChargeService;
import cn.hzstk.securities.financial.service.PayApiService;
import cn.hzstk.securities.financial.service.WithdrawService;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.service.AuthSpaceService;


/**
*
* @description:Finance action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/financial")
@SuppressWarnings("serial")
public class FinanceAction extends MagicAction<Finance,FinanceService> {
	@Autowired
    UserService userService;
	@Autowired
	PayApiService payApiService;
	@Autowired
	OrderChargeService orderChargeService;
    @Autowired
    AuthSpaceService authSpaceService;
    @Autowired
    MemberBankService memberBankService;
    @Autowired
    WithdrawService withdrawService;
    @Autowired
    FinanceService financeService;
    @Autowired
    AuthBankService authBankService;
    @Autowired
    DistrictService districtService;
    @RequestMapping(value="/financelist")
    public String financelist(HttpServletRequest request, Model model) {
        Map<String,String> params=getParameterMap(request);
        //选择service
        params.put("key", "0");
        User user= PortalUtil.getUser(request);
        String userid=user.getId().toString();
        params.put("userid",userid);
        String ord=params.get("ord");
        String pageSize=params.get("pageSize");
        PageInfo<?> fina= entityService.queryPaged1(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(fina, new DictFilter()));
        if (o != null) {
            JSONArray row=o.getJSONArray("list");
            List list=new ArrayList();
           for(int i=0;i<row.size();i++){
                JSONObject obj=row.getJSONObject(i);
               Long time=obj.getLongValue("finaTime");
               Date dat = new Date(time*1000);
               java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                       "yyyy-MM-dd HH:mm:ss");
               String date = format.format(dat);
               obj.put("finaTime",date);
               list.add(obj);
            }
            model.addAttribute("rows",list);
            model.addAttribute("ord",ord);
            model.addAttribute("pageSize",pageSize);
			model.addAttribute("lastPage",o.get("lastPage"));
			model.addAttribute("currentPage",o.get("pageNum"));
			//获取用户余额
			Double usercash = authSpaceService.getuserbalance(user.getId());
			model.addAttribute("balance", usercash);
        }

        return getNameSpace()+"financelist";
    }
    @RequestMapping(value="/financein")
    public String financein(HttpServletRequest request, Model model) {
        Map<String,String> params=getParameterMap(request);
        //选择service
        params.put("key", "1");
        User user= PortalUtil.getUser(request);
        String userid=user.getId().toString();
        params.put("userid",userid);
        String ord=params.get("ord");
        String pageSize=params.get("pageSize");
        PageInfo<?> fina= entityService.queryPaged1(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(fina, new DictFilter()));
        if (o != null) {
            JSONArray row=o.getJSONArray("list");
            List list=new ArrayList();
            for(int i=0;i<row.size();i++){
                JSONObject obj=row.getJSONObject(i);
                Long time=obj.getLongValue("finaTime");
                Date dat = new Date(time*1000);
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String date = format.format(dat);
                obj.put("finaTime",date);
                list.add(obj);
            }
            model.addAttribute("rows",list);
            model.addAttribute("ord",ord);
			model.addAttribute("pageSize",pageSize);
			model.addAttribute("lastPage",o.get("lastPage"));
			model.addAttribute("currentPage",o.get("pageNum"));
            //获取用户余额
            Double usercash = authSpaceService.getuserbalance(user.getId());
            model.addAttribute("balance", usercash);
        }

        return getNameSpace()+"financein";
    }
    @RequestMapping(value="/financeout")
    public String financeout(HttpServletRequest request, Model model) {
        Map<String,String> params=getParameterMap(request);
        //选择service
        params.put("key", "2");
        User user= PortalUtil.getUser(request);
        String userid=user.getId().toString();
        params.put("userid",userid);
        String ord=params.get("ord");
        String pageSize=params.get("pageSize");
        PageInfo<?> fina= entityService.queryPaged1(params);
        JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(fina, new DictFilter()));
        if (o != null) {
            JSONArray row=o.getJSONArray("list");
            List list=new ArrayList();
            for(int i=0;i<row.size();i++){
                JSONObject obj=row.getJSONObject(i);
                Long time=obj.getLongValue("finaTime");
                Date dat = new Date(time*1000);
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String date = format.format(dat);
                obj.put("finaTime",date);
                list.add(obj);
            }
            model.addAttribute("rows",list);
            model.addAttribute("ord",ord);
			model.addAttribute("pageSize",pageSize);
			model.addAttribute("lastPage",o.get("lastPage"));
			model.addAttribute("currentPage",o.get("pageNum"));
            //获取用户余额
            Double usercash = authSpaceService.getuserbalance(user.getId());
            model.addAttribute("balance", usercash);
        }

        return getNameSpace()+"financeout";
    }

	@RequestMapping(value="/charge_index")
    public String index() {
        return olineindex();
    }

	@RequestMapping(value="/online_charge")
    public String olineindex() {
        return getNameSpace()+"online_charge";
    }

	@RequestMapping(value="/offline_charge")
    public String offlineindex(HttpServletRequest request, Model model) {
		List<PayApi> offline = payApiService.getoffline();
		JSONArray array = new JSONArray();
		for(PayApi api:offline){
		JSONObject obj = JSON.parseObject(api.getConfig());
		obj.put("payment", api.getPayment());
		array.add(obj);
		}
		model.addAttribute("apis", array);
        return getNameSpace()+"offline_charge";
    }

	@RequestMapping(value="/offline_submit")
	  public void offlinesubmit(HttpServletRequest request, HttpServletResponse response) {
		try {
            OrderCharge o = bindEntity(request, OrderCharge.class);
            User user = PortalUtil.getUser(request);
            
            o.setPayTime(new Date());
            o.setOrderStatus("wait");
            o.setOrderType("offline_charge");
            o.setUsername(user.getName());
            o.setUid(user.getId().toString());
            orderChargeService.saveOrUpdate(o);

        } catch (Exception e) {
            logger.error("save", e);
        }

	}

	@RequestMapping(value="/withdraw")
    public String withdraw() {
		 return getNameSpace()+"withdraw";

    }

	@RequestMapping(value="/withdraw_confirm")
    public void confirm(HttpServletRequest request, HttpServletResponse response) {
		try {
		String pass_word64 = request.getParameter("password");
		User user = PortalUtil.getUser(request);
		List<AuthSpace> ases = authSpaceService.querybyuserId(user.getId());
//		List<AuthSpace> ases = authSpaceService.querybyuserId(1L);

		//password base64解密
		String pass_word = new String(Base64.decodeBase64(pass_word64));
		//对password进行aes加密
		User user_temp =  userService.get(PortalUtil.getUser(request).getId());
		user_temp.encryptUserPassword(pass_word);
		String password_aes = user.getPassword();

		String pw = null;
		if(ases.size()>0){
		AuthSpace as = ases.get(0);
		pw = as.getSecCode();
		}else{
		pw =user.getPassword();
		}

		JSONObject obj =new JSONObject();
		if(pw.equals(password_aes)){
			obj.put("status", "success");

			//支付密码成功后设置支付状态为true
			HttpSession session = request.getSession();
			session.setAttribute("withdraw_permission", "granted");

			response.getWriter().print(obj.toJSONString());
		}else{
			obj.put("status", "failed");
			response.getWriter().print(obj);
		}

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@RequestMapping(value="/withdraw_submit/step/{step}")
    public String submitwithdraw(HttpServletRequest request,@PathVariable("step") Long step, HttpServletResponse response , Model model) {
		HttpSession session = request.getSession();
		String permission = (String) session.getAttribute("withdraw_permission");

		Withdraw withdraw = (Withdraw) session.getAttribute("withdraw");

		//获取用余额信息
		User user = PortalUtil.getUser(request);
		//TODO
		List<AuthSpace> ases = authSpaceService.querybyuserId(user.getId());
//		List<AuthSpace> ases = authSpaceService.querybyuserId(1L);
		Double usercash = ases.get(0).getBalance();
		session.setAttribute("user_cash",usercash);
		
		if(withdraw == null){
			withdraw = new Withdraw();
		}
		if("granted".equals(permission)){
			switch(step.intValue()){
			case 1:
				break;
			case 2:
				String param = request.getParameter("intWithdrawCash");
				if(param!=null){
				Double cash = Double.valueOf(param);
				withdraw.setWithdrawCash(cash);
				session.setAttribute("withdraw", withdraw);
				if(cash>usercash){
					model.addAttribute("notenough", "1");
					return getNameSpace()+"withdraw_step_1";
				}
				}
				
				//获取银行卡信息
//				List<MemberBank> cards = memberBankService.getaccount(1);
				List<AuthBank> cards = authBankService.getaccount(user.getId().intValue());
				model.addAttribute("cards", cards);
				break;
			case 3:
				String cardnum =request.getParameter("offline");
				String bankid =request.getParameter("bankid");
				if(cardnum!=null){
					withdraw.setPayAccount(cardnum);
					session.setAttribute("withdraw",withdraw);
				}else{
					cardnum = withdraw.getPayAccount();
				}
				MemberBank card = memberBankService.get(Long.valueOf(bankid));
				model.addAttribute("card", card);
				break;
			case 4:
				if(session.getAttribute("withdraw")==null){
					return getNameSpace()+"withdraw"; 
				}
				String pay_account =request.getParameter("pay_account");
				String pay_username =request.getParameter("pay_username");
				withdraw.setPayAccount(pay_account);
				withdraw.setPayUsername(pay_username);
				Double banlance = entityService.submit(withdraw, user.getId().intValue(),user.getName());
//				Integer banlance = entityService.submit(withdraw, 1,"admin");
				
				model.addAttribute("withdrawcash", withdraw.getWithdrawCash());
				session.setAttribute("user_cash", banlance);
				session.removeAttribute("withdraw");
				break;
			}

			return getNameSpace()+"withdraw_step_"+step;
		}else{
			//return getNameSpace()+"withdraw";
			try {
				response.sendRedirect("/financial/withdraw");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

    }
	
	@RequestMapping(value="/banklist")
    public String banklist(HttpServletRequest request, Model model) {
		User user = PortalUtil.getUser(request);
		List<MemberBank> mblist = memberBankService.getaccount(user.getId().intValue());
		JSONArray jsarray = new JSONArray();
		for(MemberBank mb:mblist){
			JSONObject obj = new JSONObject();
			obj.put("cardnum", mb.getCardNum());
			obj.put("type", mb.getBankType());
			obj.put("bankname", mb.getBankName());
			obj.put("id", mb.getId());
			
			List<AuthBank> abs = authBankService.getbybankid(mb.getId().intValue());
			if(abs.size()>0){
				AuthBank ab = abs.get(0);
				if(ab.getAuthStatus()==1){
					obj.put("status", "1");
				}else if(ab.getAuthStatus()==2){
					obj.put("status", "2");
				}else{
					obj.put("status", "0");
				}
			}else{
				obj.put("status", "-1");
			}
			
			jsarray.add(obj);
		}
		model.addAttribute("banklist", jsarray);
		
		return getNameSpace()+"banklist";
    }
	
	@RequestMapping(value = "addbank")
    public String addbank(HttpServletRequest request,Model model) {
        return getNameSpace() + "bank_add";
    }
	
	/**
     * 保存银行卡记录
     */
    @RequestMapping(value = "savebank", method = RequestMethod.POST)
    public void savebank(HttpServletRequest request, HttpServletResponse response) {
        try {
        	String province = request.getParameter("province");
        	String city = request.getParameter("city");
        	String area = request.getParameter("area");
        	User user = PortalUtil.getUser(request);
        	//获取企业用户还是个人用户
        	List<AuthSpace> ases = authSpaceService.querybyuserId(user.getId());
        	
        	String address = districtService.get(Long.valueOf(province)).getName();
        	if(!"".endsWith(city)){
        	address = address+","+districtService.get(Long.valueOf(city)).getName();
        	}
        	if(!"".endsWith(area)){
        		address = address+","+districtService.get(Long.valueOf(area)).getName();
        	}
        	MemberBank o = bindEntity(request, MemberBank.class);
        	o.setBankAddress(address);
        	o.setOnTime(new Date());
        	o.setUid(user.getId().intValue());
        	
        	if(ases.size()>0){
        		AuthSpace as = ases.get(0);
        		if(as.getUserType()==1){
        			o.setBankType(1);
        		}
        		if(as.getUserType()==2){
        			o.setBankType(2);
        		}
        	}
            memberBankService.saveOrUpdate(o);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "保存成功");
            data.put("url", "/financial/banklist");
            response.getWriter().write(JSON.toJSONString(data));
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * 提交认证
     */
    @RequestMapping(value = "submitaudit", method = RequestMethod.POST)
    public String submitaudit(HttpServletRequest request, HttpServletResponse response,Model model) {
    	try {
			String id = request.getParameter("id");
			MemberBank mb = memberBankService.get(Long.valueOf(id));
			User user = PortalUtil.getUser(request);
			
			AuthBank ab =new AuthBank();
			ab.setAuthStatus(0);
			ab.setBankAccount(mb.getCardNum());
			ab.setBankId(Integer.valueOf(id));
			ab.setBankName(mb.getBankName());
			ab.setBankSname(mb.getBankFullName());
			ab.setUid(mb.getUid());
			ab.setUsername(user.getName());
			ab.setDepositArea(mb.getBankAddress());
			Date date = new Date();
			ab.setStartTime(date);
			ab.setEndTime(date);
			authBankService.saveOrUpdate(ab);
			
			model.addAttribute("cardnum", ab.getBankAccount());
			model.addAttribute("bankname", ab.getBankName());
			return getNameSpace()+"bankauth_step1";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @RequestMapping(value = "auditbank/id/{id}")
    public String auditbank(HttpServletRequest request,Model model,@PathVariable("id") Integer id) {
    	List<AuthBank> list = authBankService.getbybankid(id);
    	if(list.size()<=0){
    		return null;
    	}else{
    		AuthBank ab = list.get(0);
    		if(ab.getPayToUserCash()!=null&&ab.getPayToUserCash()!=0){
    		return getNameSpace() + "bankauth_step2";	
    		}else{
    		model.addAttribute("bankname", ab.getBankName());
    		model.addAttribute("cardnum", ab.getBankAccount());
    		model.addAttribute("id", ab.getId());
    		return getNameSpace() + "bankauth_step1";
    		}
    	} 
    }
    
    @RequestMapping(value = "cashconfirm")
    public void submitaudit(HttpServletRequest request, HttpServletResponse response){
    	try {
    		Integer cash = Integer.valueOf(request.getParameter("user_get_cash"));
        	String id = request.getParameter("id");
        	
        	List<AuthBank> abs = authBankService.getbybankid(Integer.valueOf(id));
        	JSONObject obj = new JSONObject();
        	if(abs==null||abs.size()<=0){
        		throw new Exception();
        	}else{
        		AuthBank ab = abs.get(0);
				Integer paycash = ab .getPayToUserCash();
        		ab.setUserGetCash(cash);
        		if(paycash==cash){
        			ab.setAuthStatus(1);
        			obj.put("status", "success");
        		}else{
        			ab.setAuthStatus(2);
        			obj.put("status", "fail");
        		}
        		authBankService.saveOrUpdate(ab);
        	}
			response.getWriter().print(obj.toJSONString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 取消认证
     */
    @RequestMapping(value = "removeauth", method = RequestMethod.POST)
    public String removeauth(HttpServletRequest request,Model model) {
    	Integer id = Integer.valueOf(request.getParameter("id"));
		List<AuthBank> ab = authBankService.getbybankid(id );
    	if(ab.size()<=0){
    		return getNameSpace()+"banklist";
    	}else{
    	authBankService.logicRemove(ab.get(0).getId());
    	}
		return getNameSpace()+"banklist";
    	
    }
    
    /**
     * 解除绑定
     */
    @RequestMapping(value = "removebind", method = RequestMethod.POST)
    public String removebind(HttpServletRequest request,Model model) {
    	Long id = Long.valueOf(request.getParameter("id"));
    	memberBankService.removebind(id);
    	return getNameSpace()+"banklist";
    }
    
    /**
     * 支付页面
     */
    @RequestMapping(value = "gotopay")
    public String gotopay(HttpServletRequest request, Model model) {
    	String cash = request.getParameter("cash");
    	String type = request.getParameter("type");
    	
    	User user = PortalUtil.getUser(request);
    	Double balance = authSpaceService.getuserbalance(user.getId());
    	model.addAttribute("cash", cash);
    	model.addAttribute("balance", balance);
    	return getNameSpace()+"pay_for";
    }
    
    @RequestMapping(value = "payconfirm")
    public void payconfirm(HttpServletRequest request, HttpServletResponse response){
    	String pay_method = request.getParameter("paytype");
    	String cash = request.getParameter("cash");
    	String type = request.getParameter("type");
    	String action = request.getParameter("action");
    	
    	User user = PortalUtil.getUser(request);
    	Double cash_double = Double.valueOf(cash);
    	//余额支付
    	if("yue".equals(pay_method)){
    		List<AuthSpace> list = authSpaceService.querybyuserId(user.getId());
    		
    		if(list.size()>0){
    			AuthSpace as = list.get(0);
    			//扣款
    			Double balance = as.getBalance();
    			if("out".equals(type)){
    			balance = balance - cash_double;
    			}else{
    			balance = balance + cash_double;	
    			}
    			//流水
    			
    			Finance finance = new Finance();
    			finance.setFinaAction(action);
    			finance.setFinaCash(cash_double);
    			finance.setFinaType(type);
    			finance.setOrderId(0);
    			finance.setUid(user.getId().intValue());
    			finance.setUsername(user.getName());
    			finance.setObjType(action);
    			Date date =new Date();
    			finance.setFinaTime(date.getTime()/1000);
    			finance.setUserBalance(balance);
    			finance.setFinaMem("");
    			financeService.saveOrUpdate(finance);
    		}
    	}
    }
    
    
    /**
     * 支付页面
     */
    @RequestMapping(value = "passwordconfirm")
    public String passwordconfirm(HttpServletRequest request, Model model) {
    	return getNameSpace()+"/shop/service/pay_confirm";
    }
}
