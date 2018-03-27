package cn.hzstk.securities;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MagicServlet extends DispatcherServlet{

	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		mv.addObject("base", request.getContextPath());
		super.render(mv, request, response);
	}
	
}
