package com.skoo.stock.pub.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.pub.domain.ContentChannel;
import com.skoo.stock.pub.service.ContentChannelService;

/**
 * 
 * @description:ContentChannel action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/pub/content-channel")
@SuppressWarnings("serial")
public class ContentChannelAction extends ManAction<ContentChannel,ContentChannelService> {
	

}
