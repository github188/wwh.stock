package com.skoo.stock.pub.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.pub.domain.ContentTxt;
import com.skoo.stock.pub.service.ContentTxtService;

/**
 * 
 * @description:ContentTxt action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/pub/content-txt")
@SuppressWarnings("serial")
public class ContentTxtAction extends ManAction<ContentTxt,ContentTxtService> {
	

}
