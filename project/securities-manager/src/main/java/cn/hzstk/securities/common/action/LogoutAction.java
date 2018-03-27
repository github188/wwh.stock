package cn.hzstk.securities.common.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.constants.SystemProperties;
import cn.hzstk.securities.util.FileUtil;
import com.baomidou.kisso.SSOHelper;
import net.ryian.core.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by allenwc on 16/4/20.
 */
@Controller
public class LogoutAction {
    protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String processName = SystemConfig.INSTANCE.getValue(SystemProperties.ZS_MAIN_PROGRAM);
        if (FileUtil.isRunning(processName)) {
            try {
                Runtime.getRuntime().exec("cmd /c taskkill /F /IM " + processName);
            } catch (Exception e) {
                log.error("logout", e);
            }
        }
        SSOHelper.clearLogin(request,response);
        return "redirect:/";
    }

}
