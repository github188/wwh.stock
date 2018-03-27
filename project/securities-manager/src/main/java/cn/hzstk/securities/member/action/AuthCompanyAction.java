package cn.hzstk.securities.member.action;

import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.member.domain.AuthCompany;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.mapper.AuthSpaceMapper;
import cn.hzstk.securities.member.service.AuthCompanyService;
import cn.hzstk.securities.member.service.AuthSpaceService;
import cn.hzstk.securities.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
*
* @description:Company action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/member/company")
@SuppressWarnings("serial")
public class AuthCompanyAction extends MagicAction<AuthCompany,AuthCompanyService> {
    @Autowired
    DictService dictService;
    @Autowired
    AuthSpaceMapper authSpaceMapper;
    @Autowired
    AuthSpaceService authSpaceService;
    /**
     * 审核处理
     *
     */
    @RequestMapping(value = "examine",method = RequestMethod.POST)
    public void examine(HttpServletResponse rep,String id,String yn) throws  Exception{
        try {
            AuthCompany comp = entityService.get(Long.parseLong(id));
            comp.setAuthStatus(Integer.valueOf(yn));
            entityService.saveOrUpdate(comp);
            if(yn.equals("1")){
                List<AuthSpace> list = authSpaceMapper.getlist(comp.getUserId());
                AuthSpace aut = authSpaceService.get(list.get(0).getId());
                aut.setName(comp.getName());
                aut.setCode(comp.getCode());
                aut.setIdpic(comp.getIdpic());
                aut.setAuthEtime(new Date());
                aut.setAuthStatus(Integer.parseInt(yn));
                authSpaceService.saveOrUpdate(aut);
            }
            printJson(rep, messageSuccuseWrap());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            printJson(rep, messageFailureWrap("审核失败！"));
        }
    }

    /**
     * 批量审核处理
     *
     */
    @RequestMapping(value = "batexamine",method = RequestMethod.POST)
    public void batexamine(HttpServletRequest req,HttpServletResponse rep,String yn) throws  Exception{
        try {
            String ids = req.getParameter("ids");
            for (String id : ids.split(",")) {
                AuthCompany comp = entityService.get(Long.parseLong(id));
                comp.setAuthStatus(Integer.valueOf(yn));
                entityService.saveOrUpdate(comp);
            }
            printJson(rep, messageSuccuseWrap());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            printJson(rep, messageFailureWrap("审核失败！"));
        }
    }
    @Override
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("authstatus",dictService.getDictsByKey(DictKeys.AUTH_STATUS).values());
    }

}
