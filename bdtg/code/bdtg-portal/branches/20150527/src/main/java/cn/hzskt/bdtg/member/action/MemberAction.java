package cn.hzskt.bdtg.member.action;

import cn.hzskt.bdtg.api.service.FileUpload;
import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.common.utils.UuidUtil;
import cn.hzskt.bdtg.common.utils.oss.OSSConfig;
import cn.hzskt.bdtg.common.utils.oss.OSSUtil;
import cn.hzskt.bdtg.financial.service.AuthBankService;
import cn.hzskt.bdtg.member.domain.*;
import cn.hzskt.bdtg.member.mapper.AuthCompanyMapper;
import cn.hzskt.bdtg.member.mapper.AuthRealnameMapper;
import cn.hzskt.bdtg.member.mapper.AuthSpaceMapper;
import cn.hzskt.bdtg.member.service.*;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.sun.org.apache.bcel.internal.generic.NEW;
import net.ryian.orm.domain.BaseEntity;
import org.omg.CORBA.portable.ValueOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by allenwc on 16/4/6.
 */
@Controller
@RequestMapping("/member")
public class MemberAction extends BaseMagicAction {
    @Autowired
    UserService userService;
    @Autowired
    AuthCompanyMapper authcompmapper;
    @Autowired
    AuthRealnameMapper authRealnameMapper;
    @Autowired
    AuthCompanyService authcompservice;
    @Autowired
    AuthRealnameService authrealnameservice;
    @Autowired
    AuthSpaceMapper authSpaceMapper;
    @Autowired
    AuthSpaceService authSpaceService;
    @Autowired
    AuthBankService authBankService;
    @Autowired
    AuthEmailService authEmailService;
    @Autowired
    private FileUpload fileUpload;


    @RequestMapping(value="/index")
    public String index(Model model,HttpServletRequest request) {
        return account(model,request);
    }

    /**
     * 账号设置首页
     * @return String
     */
    @RequestMapping(value="account")
    public String account(Model model,HttpServletRequest request) {
        User user = PortalUtil.getUser(request);
        List<AuthSpace> aulist = new ArrayList<AuthSpace>();
        aulist = authSpaceMapper.getlist(user.getId());
        //获取银行认证信息
        boolean authbank_status = authBankService.getauthstatus(PortalUtil.getUser(request).getId());
        if(authbank_status==true){
        	model.addAttribute("authbank", "1");
        }else{
        	model.addAttribute("authbank", "0");
        }
        if(aulist.size()>0){
            BaseEntity entity = authSpaceService.get(aulist.get(0).getId());
            model.addAttribute("model", entity);
            String regtime="";
            if(aulist.get(0).getRegTime()==null ||aulist.get(0).getRegTime().toString().equals("")){
                regtime= "";
            }else{
                regtime= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(aulist.get(0).getRegTime());
            }
            String lastlogintime="";
            if(user.getLastLoginDate()==null ||user.getLastLoginDate().equals("")){
                lastlogintime= "";
            }else{
                lastlogintime= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(user.getLastLoginDate());
            }
            model.addAttribute("regtime", regtime);
            model.addAttribute("lastlogintime", lastlogintime);
        }
        return super.getNameSpace()+"account";
    }
    /**
     * 设置系统头像
     * @return String
     */
    @RequestMapping(value="setsysImg")
    public String setsysImg( Model model,HttpServletRequest request,String imgpath){
        List<AuthSpace> aulist = new ArrayList<AuthSpace>();
        aulist = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
        if(aulist.size()>0){
            AuthSpace authSpace = new AuthSpace();
            authSpace.setId(aulist.get(0).getId());
            authSpace.setUserPic(imgpath);
            authSpaceService.saveOrUpdate(authSpace);
        }
      return  account_headimg(model,request);
    }
    /**
     * 资料完善页面
     * @return String
     */
    @RequestMapping(value="account_manage")
    public String account_manage( Model model,HttpServletRequest request){
        List<AuthSpace> aulist = new ArrayList<AuthSpace>();
        aulist = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
        if(aulist.size()>0){
            BaseEntity entity = authSpaceService.get(Long.parseLong(aulist.get(0).getId().toString()));
            model.addAttribute("model", entity);
            if(aulist.get(0).getUserType() != null && aulist.get(0).getUserType() == 2){
                return super.getNameSpace()+"account_spacecomp";
            }else{
                return super.getNameSpace()+"account_spacereal";
            }
        }else{
            return super.getNameSpace()+ "error";
        }
    }
    /**
     * 邮箱认证页面
     * @return String
     */
    @RequestMapping(value="account_email")
    public String account_email( Model model,HttpServletRequest request){
        String enab = "Y";
        List<AuthEmail> auemail = new ArrayList<AuthEmail>();
        auemail = authEmailService.getByuserid(PortalUtil.getUser(request).getId());
        if(auemail.size()>0) {
            if(auemail.get(0).getEmailStatus()==1){
                enab="N";
            }
            BaseEntity entity = authEmailService.get(Long.parseLong(auemail.get(0).getId().toString()));
            model.addAttribute("model", entity);
        }
        model.addAttribute("enab",enab);
        return super.getNameSpace()+ "account_email";
    }
    /**
     * 手机认证页面
     * @return String
     */
    @RequestMapping(value="account_mobile")
    public String account_mobile( Model model,HttpServletRequest request){
        String enab = "Y";
        List<AuthSpace> authSpaces = new ArrayList<AuthSpace>();
        authSpaces = authSpaceService.querybyuserId(PortalUtil.getUser(request).getId());
        if(authSpaces.size()>0) {
            if(authSpaces.get(0).getMobileStatus()==1){
                enab="N";
            }
            BaseEntity entity = authSpaceService.get(authSpaces.get(0).getId());
            model.addAttribute("model", entity);
        }
        model.addAttribute("enab",enab);
        return super.getNameSpace()+ "account_mobile";
    }

    /**
     * 手机认证验证码发送
     *
     */
    @RequestMapping(value="mobile_codesend")
    public void mobile_codesend(HttpServletRequest request,Model model,HttpServletResponse response){
        try {
            String ucode = autocryzm();
            // 将四位数字的验证码保存到Session中。
            HttpSession session = request.getSession();
            session.setAttribute("validateCode", ucode);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "验证码发送成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 手机认证通过
     *
     */
    @RequestMapping(value="mobile_save")
    public void mobile_save(HttpServletRequest request,Model model,HttpServletResponse response,String hidmobile){
        try {
            Long id = authSpaceService.querybyuserId(PortalUtil.getUser(request).getId()).get(0).getId();
            AuthSpace authSpace = new AuthSpace();
            authSpace.setId(id);
            authSpace.setMobile(hidmobile);
            authSpace.setMobileStatus(1);
            authSpaceService.saveOrUpdate(authSpace);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "认证成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 邮箱认证保存
     *
     */
    @RequestMapping(value="email_save")
    public void email_save(HttpServletRequest request,Model model,HttpServletResponse response){
        try {
            AuthEmail auemail =  bindEntity(request, AuthEmail.class);
            String ucode = UuidUtil.getUUID();
            String checkurl="localhost:8081/"+"member/emailcheck?userid="+PortalUtil.getUser(request).getId()+"&ucode="+ucode;
            auemail.setCheckUrl(checkurl);
            if(auemail.getId()==null){
                auemail.setUserId(PortalUtil.getUser(request).getId());
                auemail.setUserName(PortalUtil.getUser(request).getUserName());
                auemail.setEmailStatus(Long.parseLong("0"));
                auemail.setUpdateDate(new Date());
            }
            auemail.setUcode(ucode);
            authEmailService.saveOrUpdate(auemail);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "保存成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 邮箱认证处理
     *
     */
    @RequestMapping(value="emailcheck")
    public String emailcheck(Model model,String userid,String ucode){
        Boolean remsg=false;
        try {
            //邮箱认证时间
            List<AuthEmail> auemail = new ArrayList<AuthEmail>();
            auemail = authEmailService.getByuseridAnducode(userid,ucode);
            if(auemail.size()>0){
                Long reg=auemail.get(0).getUpdateDate().getTime();
                //当前时间毫秒数
                Date date = new Date();
                Long now = date.getTime();
                Long adate = Long.parseLong(String.valueOf(2*60*60*1000));
                if((now-reg)<=adate) {
                    AuthEmail authEmail = new AuthEmail();
                    authEmail.setId(auemail.get(0).getId());
                    authEmail.setEmailStatus(Long.parseLong("1"));
                    authEmailService.saveOrUpdate(authEmail);
                    Long id = authSpaceService.querybyuserId(Long.parseLong(userid)).get(0).getId();
                    AuthSpace authSpace = new AuthSpace();
                    authSpace.setId(id);
                    authSpace.setEmail(auemail.get(0).getEmail());
                    authSpace.setEmailStatus(1);
                    authSpaceService.saveOrUpdate(authSpace);
                    remsg = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            remsg=false;
        }finally {
            model.addAttribute("remsg",remsg);
            return  super.getNameSpace()+ "account_check";
        }
    }
    /**
     * 选择头像
     * @return String
     */
    @RequestMapping(value="account_headimg")
    public String account_headimg(Model model,HttpServletRequest request){
        List<AuthSpace> aulist = new ArrayList<AuthSpace>();
        aulist = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
        if(aulist.size()>0){
            AuthSpace authSpace = authSpaceService.get(aulist.get(0).getId());
            model.addAttribute("model",authSpace);
        }
        List<Integer> list = new ArrayList();
        for (int i=0;i<20;i++){
            list.add(i);
        }
        model.addAttribute("modellist",list);
        return super.getNameSpace()+ "account_headimg";
    }
    /**
     * 上传头像
     * @return String
     */
    @RequestMapping(value="account_uploadimg")
    public String account_uploadimg( ){
        return super.getNameSpace()+ "account_uploadimg";
    }

    @RequestMapping(value="account_upload")
    public void account_upload(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String actionType = request.getParameter("a");
        if (!"uploadavatar".equals(actionType)) return;
        String category = "pic";

        MultipartFile file = ((DefaultMultipartHttpServletRequest) request).getFile(((DefaultMultipartHttpServletRequest) request).getFileNames().next());
        InputStream uploadedStream = file.getInputStream();
        String filename = fileUpload.gettimesName(file.getOriginalFilename());
        String osspath = category + "/" + filename;
        String bucketName = OSSConfig.INSTANCE.getFileBucket();
        if(!category.equals("file")){
            bucketName = OSSConfig.INSTANCE.getImgBucket();
        }
        OSSUtil.upload(uploadedStream, bucketName, osspath);

        //err=0上传成功，否则上传失败
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("err", 0);
        Map<String, Object> map2 = new LinkedHashMap<>();
        String ossDomain = OSSConfig.INSTANCE.getOssDomain(category);
        map2.put("url", ossDomain + "/" + osspath);
        map2.put("name", filename);
        map2.put("fileid", "1111");
        map.put("msg", map2);

        List<AuthSpace> list = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
        AuthSpace aut = authSpaceService.get(list.get(0).getId());
        aut.setUserPic(ossDomain + "/" + osspath);
        authSpaceService.saveOrUpdate(aut);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSONObject.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    /**
     * 资料完善页面保存
     *
     */
    @RequestMapping(value="authspace_save")
    public void authspace(HttpServletRequest request,Model model,HttpServletResponse response){
        try {
            AuthSpace aut =  bindEntity(request, AuthSpace.class);
            authSpaceService.saveOrUpdate(aut);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "保存成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 认证页面
     * @return String
     */
    @RequestMapping(value="account_auth")
    public String account_auth(HttpServletRequest request, Model model){
        //是否能编辑初始值设定
        List<AuthSpace> list = new ArrayList<AuthSpace>();
        list  = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
        if(list.size()>0){
            if(list.get(0).getUserType()!=null&&list.get(0).getUserType()==2){
                //企业认证
                return account_attcomp(request, model);
            }else{
                //个人认证
                return account_attreal(request, model);
            }
        }else {
            return super.getNameSpace()+  "error";
        }
    }
    /**
     * 用户实名认证页面
     * @return String
     */
    @RequestMapping(value="account_attreal")
    public String account_attreal(HttpServletRequest request, Model model){
        //是否能编辑初始值设定
        String enab = "Y";
        String userType="0";
        List<AuthRealname> list = new ArrayList<AuthRealname>();
        list  = authRealnameMapper.getlist(PortalUtil.getUser(request).getId());
        if(list.size()>0){
            BaseEntity entity = authrealnameservice.get(Long.parseLong(list.get(0).getId().toString()));
            //验证改用户是否已经实名认证通过，不通过可编辑，通过，则不能编辑
            if(list.get(0).getAuthStatus()==1){
                enab ="N";
            }
            userType="1";
            model.addAttribute("model", entity);
        }else {
            model.addAttribute("model", "");
        }
        model.addAttribute("userType", userType);
        model.addAttribute("username", PortalUtil.getUser(request).getName());
        model.addAttribute("enab", enab);
        return super.getNameSpace() + "account_attreal";
    }
    /**
     * 用户企业认证页面
     * @return String
     */
    @RequestMapping(value="account_attcomp")
    public String account_attcomp(HttpServletRequest request, Model model){
        //是否能编辑初始值设定
        String enab = "Y";
        String userType="0";
        List<AuthCompany> list = new ArrayList<AuthCompany>();
        list  =authcompmapper.getlist(PortalUtil.getUser(request).getId());
        if(list.size()>0){
            BaseEntity entity = authcompservice.get(Long.parseLong(list.get(0).getId().toString()));
            //验证改用户是否已经实名认证通过，不通过可编辑，通过，则不能编辑
            if(list.get(0).getAuthStatus() ==1){
                enab ="N";
            }
            userType ="2";
            model.addAttribute("model", entity);
        }else {
            model.addAttribute("model", "");
        }
        model.addAttribute("userType", userType);
        model.addAttribute("username",PortalUtil.getUser(request).getName());
        model.addAttribute("enab", enab);
        return super.getNameSpace() + "account_attcomp";
    }
    /**
     * 用户认证保存处理
     * @return String
     */
    @RequestMapping(value="auth_save")
    public void auth_save(HttpServletRequest request, String userType,Model model,HttpServletResponse response){
        String this_url="";
        try {
            if(userType.equals("1")){
                AuthRealname aur =  bindEntity(request, AuthRealname.class);
                if(aur.getId()==null||aur.getId().toString().equals("")){
                    aur.setAuthStatus(0);
                    aur.setUserName(PortalUtil.getUser(request).getName());
                    aur.setUserId(PortalUtil.getUser(request).getId());
                }
                authrealnameservice.saveOrUpdate(aur);
                this_url =  super.getNameSpace() + "account_attreal";
            }else{
                AuthCompany auc =  bindEntity(request, AuthCompany.class);
                if(auc.getId()==null||auc.getId().toString().equals("")){
                    auc.setAuthStatus(0);
                    auc.setUserName(PortalUtil.getUser(request).getName());
                    auc.setUserId(PortalUtil.getUser(request).getId());
                }
                authcompservice.saveOrUpdate(auc);
                this_url  = super.getNameSpace() + "account_attcomp";
            }
            List<AuthSpace> list = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
            AuthSpace aut = authSpaceService.get(list.get(0).getId());
            aut.setUserType(Integer.parseInt(userType));
            aut.setAuthStime(new Date());
            authSpaceService.saveOrUpdate(aut);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "保存成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 验证填写的验证码是否正确
     *
     */
    @Login(action = Action.Skip)
    @RequestMapping(value="sesscodeget")
    public void sesscodeget(HttpServletRequest request ,HttpServletResponse rep,String code) {

        try {
            Map<String, String> param = new HashMap<String, String>();
            String sessioncode = request.getSession().getAttribute("validateCode").toString();
            if(code.equals(sessioncode)){
                param.put("vars", "Y");
            }else{
                param.put("vars", "N");
            }
            this.printText(rep, JSON.toJSONString(param));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用户名唯一性验证
     *
     */
    @Login(action = Action.Skip)
    @RequestMapping(value="name_only")
    public void name_only(HttpServletResponse rep,String name) {

        String namecount = "0";
        Map<String, String> param = new HashMap<String, String>();
        try {
            User user = userService.findUserByUserName(name);
            namecount = user == null ? "0" : "1";
            param.put("namecount", namecount);
            this.printText(rep, JSON.toJSONString(param));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 登录密码修改
     *
     */
    @RequestMapping(value="passwordedit")
    public String passwordedit(){

        return super.getNameSpace()+"account_passedit";
    }
    /**
     * 登录密码修改
     *
     */
    @RequestMapping(value="paypasswordedit")
    public String paypasswordedit(){

        return super.getNameSpace()+"account_paypassedit";
    }
    /**
     * 旧密码确认是否正确
     *
     */
    @RequestMapping(value="passwordvalidate")
    public void passwordvalidate(HttpServletRequest request,HttpServletResponse rep,String pd) {

        String yn = "N";
        Map<String, String> param = new HashMap<String, String>();
        try {
            User user = userService.get(PortalUtil.getUser(request).getId());
            String oldPd = user.getPassword();
            user.encryptUserPassword(pd);
            if(oldPd.equals(user.getPassword())){
                yn="Y";
            }
            param.put("yn", yn);
            this.printText(rep, JSON.toJSONString(param));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 旧支付密码确认是否正确
     *
     */
    @RequestMapping(value="paypasswordvalidate")
    public void paypasswordvalidate(HttpServletRequest request,HttpServletResponse rep,String pd) {
        String yn = "N";
        Map<String, String> param = new HashMap<String, String>();
        try {
            List<AuthSpace> list = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
            AuthSpace aut = authSpaceService.get(list.get(0).getId());
            String oldPd = aut.getSecCode();
            User user = userService.get(PortalUtil.getUser(request).getId());
            user.encryptUserPassword(pd);
            if(oldPd.equals(user.getPassword())){
                yn="Y";
            }
            param.put("yn", yn);
            this.printText(rep, JSON.toJSONString(param));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 登录密码保存
     *
     */
    @RequestMapping(value="passwordsave")
    public void passwordsave(Model model,String new_password,HttpServletRequest request,HttpServletResponse response){
        try {
            User user =  userService.get(PortalUtil.getUser(request).getId());
            user.encryptUserPassword(new_password);
            userService.saveOrUpdate(user);
            //用户扩展表
            List<AuthSpace> list = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
            AuthSpace aut = authSpaceService.get(list.get(0).getId());
            aut.setPassword(user.getPassword());
            authSpaceService.saveOrUpdate(aut);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "保存成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 登录密码保存
     *
     */
    @RequestMapping(value="paypasswordsave")
    public void paypasswordsave(Model model,String new_code,HttpServletRequest request,HttpServletResponse response){
        try {
            User user =  userService.get(PortalUtil.getUser(request).getId());
            user.encryptUserPassword(new_code);
            //用户扩展表
            List<AuthSpace> list = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
            AuthSpace aut = authSpaceService.get(list.get(0).getId());
            aut.setSecCode(user.getPassword());
            authSpaceService.saveOrUpdate(aut);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", "success");
            data.put("data", "保存成功");
            response.getWriter().write(JSON.toJSONString(data));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String autocryzm(){
        String str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String str2[] = str.split(",");//将字符串以,分割

        Random rand = new Random();//创建Random类的对象rand
        int index = 0;
        String randStr = "";//创建内容为空字符串对象randStr
        for (int i=0; i<4; ++i)
        {
            index = rand.nextInt(str2.length-1);//在0到str2.length-1生成一个伪随机数赋值给index
            randStr += str2[index];//将对应索引的数组与randStr的变量值相连接
        }
        return randStr;
    }
}
