package cn.hzstk.securities.member.action;

import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.member.domain.AuthRealname;
import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.mapper.AuthSpaceMapper;
import cn.hzstk.securities.member.service.AuthRealnameService;
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
* @description:Person action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/member/realname")
@SuppressWarnings("serial")
public class AuthRealnameaction extends MagicAction<AuthRealname, AuthRealnameService> {

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
            AuthRealname rel = entityService.get(Long.parseLong(id));
            rel.setAuthStatus(Integer.valueOf(yn));
            entityService.saveOrUpdate(rel);
            if(yn.equals("1")){
                List<AuthSpace> list = authSpaceMapper.getlist(rel.getUserId());
                AuthSpace aut = authSpaceService.get(list.get(0).getId());
                aut.setName(rel.getName());
                aut.setCode(rel.getCode());
                aut.setIdpic(rel.getIdpic());
                aut.setIdpicDown(rel.getIdpicDown());
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
                AuthRealname rel = entityService.get(Long.parseLong(id));
                rel.setAuthStatus(Integer.valueOf(yn));
                entityService.saveOrUpdate(rel);
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
