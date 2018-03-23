package cn.hzskt.bdtg.task.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.PortalUtil;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.task.domain.Mark;
import cn.hzskt.bdtg.task.service.MarkService;

@Controller
@RequestMapping("owner")
public class MyMaskAction extends BaseMagicAction {

	@Autowired
	private MarkService markService;
	
	@RequestMapping("myMark")
	public String mytask(HttpServletRequest request){
		Map params = bindMap(request);
		User usr = PortalUtil.getUser(request);
		PageInfo<Mark> pageinfo = markService.selectMyMark(String.valueOf(usr.getId()), 0, 10);
		request.setAttribute("pagenation", pageinfo);
		return "task/mytask/myMark";
	}
	
}
