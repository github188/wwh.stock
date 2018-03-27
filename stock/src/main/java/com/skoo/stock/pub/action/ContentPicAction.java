package com.skoo.stock.pub.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.pub.domain.ContentPic;
import com.skoo.stock.pub.service.ContentPicService;

/**
 * 
 * @description:ContentPic action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/pub/content-pic")
@SuppressWarnings("serial")
public class ContentPicAction extends ManAction<ContentPic,ContentPicService> {
	

}
