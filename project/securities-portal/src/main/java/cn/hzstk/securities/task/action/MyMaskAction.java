package cn.hzstk.securities.task.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.task.domain.Mark;
import cn.hzstk.securities.task.service.MarkService;

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
