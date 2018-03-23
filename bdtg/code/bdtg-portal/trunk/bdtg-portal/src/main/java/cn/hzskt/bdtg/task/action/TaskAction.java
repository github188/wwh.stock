package cn.hzskt.bdtg.task.action;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
*
* @description:Task action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/task")
@SuppressWarnings("serial")
public class TaskAction extends MagicAction<Task,TaskService> {

    @RequestMapping(value="pubtask")
    public String pubtask() {
        return super.getNameSpace() + "pubtask";
    }

    @RequestMapping(value="step1")
    public String step1() {
        return super.getNameSpace() + "task-step1";
    }

    @RequestMapping(value="step2")
    public String step2() {
        return super.getNameSpace() + "task-step2";
    }

    @RequestMapping(value="taskcash")
    public void taskcash(String txt_task_cash, String id ,HttpServletResponse res) {

        try {
            String mes = txt_task_cash + "元的任务最大发布时间为400天";
            printText(res, mes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
