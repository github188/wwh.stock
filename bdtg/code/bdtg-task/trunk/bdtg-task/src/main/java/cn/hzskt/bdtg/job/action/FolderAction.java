package cn.hzskt.bdtg.job.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.service.FolderService;

import java.util.HashMap;
import java.util.Map;

/**
*
* @description:Folder action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/folder")
@SuppressWarnings("serial")
public class FolderAction extends MagicAction<Folder,FolderService> {

}
