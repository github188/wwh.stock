package com.skoo.stock.zs.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.zs.domain.RxData;
import com.skoo.stock.zs.service.RxDataService;

/**
 * 
 * @description:RxData action
 * @author: autoCode
 * @history:
 */
 @Controller
@RequestMapping("/zs/rx-data")
@SuppressWarnings("serial")
public class RxDataAction extends ManAction<RxData,RxDataService> {
	

}
