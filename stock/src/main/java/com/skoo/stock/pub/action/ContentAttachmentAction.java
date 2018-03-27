package com.skoo.stock.pub.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.pub.domain.ContentAttachment;
import com.skoo.stock.pub.service.ContentAttachmentService;

/**
 * 
 * @description:ContentAttachment action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/pub/content-attachment")
@SuppressWarnings("serial")
public class ContentAttachmentAction extends ManAction<ContentAttachment,ContentAttachmentService> {
	

}
