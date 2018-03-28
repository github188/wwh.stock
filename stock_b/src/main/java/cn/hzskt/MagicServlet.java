package cn.hzskt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzskt.hsa.domain.MarketData;
import cn.hzskt.util.StockUtil;
import cn.hzskt.util.beanutil.Map2BeanUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;

public class MagicServlet extends DispatcherServlet{

	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		mv.addObject("base", request.getContextPath());
		super.render(mv, request, response);
	}

}
