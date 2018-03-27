package cn.hzstk.securities.member.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.financial.service.AuthBankService;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.mapper.AuthSpaceMapper;
import cn.hzstk.securities.member.service.AuthSpaceService;
import net.ryian.orm.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allenwc on 16/4/6.
 * 业主管理
 */
@Controller
@RequestMapping("/owner")
public class OwnerAction extends BaseMagicAction {
    @Autowired
    AuthSpaceMapper authSpaceMapper;
    @Autowired
    AuthSpaceService authSpaceService;
    @Autowired
    AuthBankService authBankService;
    @RequestMapping(value="owner_manage")
    public String ownerMng(Model model,HttpServletRequest request) {
        List<AuthSpace> aulist = new ArrayList<AuthSpace>();
        aulist = authSpaceMapper.getlist(PortalUtil.getUser(request).getId());
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
            if(aulist.get(0).getLastLoginTime()==null ||aulist.get(0).getRegTime().toString().equals("")){
                lastlogintime= "";
            }else{
                lastlogintime= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(aulist.get(0).getRegTime());
            }
            model.addAttribute("regtime", regtime);
            model.addAttribute("lastlogintime", lastlogintime);
        }
        return super.getNameSpace() + "owner_manage";
    }
}
