package com.skoo.stock.common.domain;

import javax.servlet.http.HttpServletRequest;

public class IP {
	/**
	 * 获得客户端IP地址
	 * 
	 * 经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，服务器端应用也无法直接通过转发请求 的地址返回给客户端。
	 * 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
