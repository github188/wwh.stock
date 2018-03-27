package cn.hzstk.securities.sys.action;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.common.service.LoadTask;
import cn.hzstk.securities.sys.domain.ScheduleJob;
import cn.hzstk.securities.sys.service.DictService;
import cn.hzstk.securities.sys.service.ScheduleJobService;
import net.ryian.core.utils.SpringContextUtil;
import net.ryian.orm.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @description:ScheduleJob action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/sys/schedule-job")
@SuppressWarnings({"serial", "JavaDoc"})
public class ScheduleJobAction extends MagicAction<ScheduleJob,ScheduleJobService> {
    private static final Logger log = LoggerFactory.getLogger(Constant.LOG_ERROR);
    private final static LoadTask loadJob = SpringContextUtil.getBean(LoadTask.class);
    @Autowired
    DictService dictService;

    /**
     * 调度
     */
    @RequestMapping(value = "setCron", method = RequestMethod.POST)
    public void setCron(HttpServletResponse response) {
        try {
            loadJob.initTask();

            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            log.error("save", e);
            printJson(response, messageFailureWrap("调度失败！"));
        }
    }

    protected void beforeAdd(HttpServletRequest request,Model model) {
        model.addAttribute("jobStatus",dictService.getDictsByKey(DictKeys.JOB_STATUS).values());
    }

    protected void beforeEdit(HttpServletRequest request,Model model, BaseEntity entity) {
        model.addAttribute("jobStatus",dictService.getDictsByKey(DictKeys.JOB_STATUS).values());
    }
}
