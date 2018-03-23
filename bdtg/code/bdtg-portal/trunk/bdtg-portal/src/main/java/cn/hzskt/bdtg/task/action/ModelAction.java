package cn.hzskt.bdtg.task.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.task.domain.Model;
import cn.hzskt.bdtg.task.service.ModelService;

/**
*
* @description:Model action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/tb/model")
@SuppressWarnings("serial")
public class ModelAction extends MagicAction<Model,ModelService> {


}
