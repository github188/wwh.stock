package com.skoo.stock.pub.action;

import com.skoo.core.utils.JsonUtils;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.pub.domain.Channel;
import com.skoo.stock.pub.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @description:Category action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/pub/channel")
public class ChannelAction extends ManAction<Channel,ChannelService> {
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 栏目树构建
	 */
	@RequestMapping(value = "/tree")
	public void tree(HttpServletRequest request, HttpServletResponse response) {
		try {
			Condition condition = bindCondition(request);
			
			Map<String, String> psdInfo = condition.getMap();
			if (psdInfo.get("id") == null) {
				List<Channel> list = new ArrayList<Channel>();
				list.add(getRoot());
				printJson(response, JsonUtils.bean2JsonArray(list));
				return ;
			} else {
				psdInfo.put("upId", psdInfo.get("id"));
			}
			List<Channel> result = channelService.getSqlSession().selectList("Channel_tree_list", psdInfo);
			printJson(response, JsonUtils.bean2JsonArray(result));
		} catch (Exception e) {
			logger.error("栏目数据获取失败", e);
		}
	}
	
	@RequestMapping(value = "/selfAdd")
	public String selfAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		if (id != null)
			if ("-1".equals(id)) {
				model.addAttribute("model", getRoot());
			} else {
				model.addAttribute("model", entityService.get(Long.valueOf(id)));
			}
		return super.add();
	}
	
	/**
	 * 获取root
	 * 
	 * @return
	 */
	private Channel getRoot() {
		Channel c = new Channel();
		c.setChnName("浙江省商务厅国际投资网站栏目");
		c.setChnCode("root");
		c.setId(-1L);
		c.setUpId(0L);
		return c;
	}
	
	
	/**
	 * 校验重名 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/validate")
	public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> para = bindMap(request);
		Integer count = channelService.getSqlSession().selectOne("Channel_tree_count", para);
		if (count != 0) {
			printText(response, "false");
		}
		printText(response, "true");
	}
	
}
