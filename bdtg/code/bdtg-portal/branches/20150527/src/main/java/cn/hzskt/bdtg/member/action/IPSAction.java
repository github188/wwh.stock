package cn.hzskt.bdtg.member.action;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.financial.service.AuthBankService;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.mapper.AuthSpaceMapper;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
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
 * 服务商管理
 */
@Controller
@RequestMapping("/ips")
public class IPSAction extends BaseMagicAction {
    @Autowired
    AuthSpaceMapper authSpaceMapper;
    @Autowired
    AuthSpaceService authSpaceService;
    @Autowired
    AuthBankService authBankService;
    @RequestMapping(value="ips_manage")
    public String ipsMng(Model model,HttpServletRequest request) {
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
        return super.getNameSpace() + "ips_manage";
    }

}
