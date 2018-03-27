package cn.hzstk.securities.task.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.task.domain.Model;
import cn.hzstk.securities.task.service.ModelService;

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
