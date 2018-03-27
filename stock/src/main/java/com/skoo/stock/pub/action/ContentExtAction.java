package com.skoo.stock.pub.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.pub.domain.ContentExt;
import com.skoo.stock.pub.service.ContentExtService;

/**
 * 
 * @description:ContentExt action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/pub/content-ext")
@SuppressWarnings("serial")
public class ContentExtAction extends ManAction<ContentExt,ContentExtService> {
	

}
