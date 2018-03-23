<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:theme code="mobile.custom.css.file" var="mobileCss" text="" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<title>统一认证</title>
<style type="text/css">
.hidden{
	display: none;
}
</style>
<script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/js/cas.js" />"></script>
<script type="text/javascript">
	// 取得登录样式
	var href = window.location.href;
	var paras;
	if (href.indexOf("css") > 0) {
		
		var para = href.substr(href.lastIndexOf("?")+1);
		paras = para.split("&"); 
		
		for (var i =0 ; i< paras.length; i++) {
			if (paras[i].split("=")[0] == "css") {
				var head = document.getElementsByTagName('head').item(0);
				var style = document.createElement('link');
				style.href = paras[i].split("=")[1];
				style.rel = 'stylesheet';
				style.type = 'text/css';
				head.appendChild(style);
			}
		}
	}else {
		var head = document.getElementsByTagName('head').item(0);
		var style = document.createElement('link');
		style.href = "css/login.css";
		style.rel = 'stylesheet';
		style.type = 'text/css';
		head.appendChild(style);
	}
</script>
</head>
<body width="400">
		<div class="box" id="login">
			<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
				  <form:errors path="*" id="msg" cssClass="errors" element="div" />
					<div class="row fl-controls-left">
						<!--<label for="username" class="fl-label-username"><spring:message code="screen.welcome.label.netid" /></label>-->
						<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>
						<c:if test="${empty sessionScope.openIdLocalId}">
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
						<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" placeholder="请输入帐号"/>
						</c:if>
					</div>
					<div class="row fl-controls-left">
						<!--<label for="password" class="fl-label-password"><spring:message code="screen.welcome.label.password" /></label>-->
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
						<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" placeholder="请输入密码"/>
					</div>
					
					<!-- verifyCode -->
					<div class="row fl-controls-left">
						<label for="verifyCode" class="fl-label-verifyCd"><spring:message code="screen.welcome.label.verifycd"/> </label>
						<input class="required" type="text" tabindex="3" id="verifyCode" size="4" name="verifyCode"/>
						<img id="vali" class="code" onclick="refresh();return false;" alt="change it"/> 
						<a href="javacript://" class="changepic" onclick="refresh();return false;">换一张</a>
					</div>
					<div class="row1">
						<input id="remenberPass" type="checkbox" value="" class="check"/>
						<span style="vertical-align: bottom;"> 记住我</span>
						<a href="/forgetPwd.html" class="forget_pass hidden">忘记密码</a>
						<div style="clear: both;"></div>
					</div>
					<div class="row btn-row">
						<input type="hidden" name="lt" value="${loginTicket}" />
						<input type="hidden" name="execution" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
						<input type="button" class="btn-submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" onclick="doLogin();"/>
					</div>
			</form:form>
		  </div>
			<script type="text/javascript">
				function refresh() {
					$("#vali").attr("src", 'captcha.htm' + '?' + (new Date()).valueOf());
				}
				document.onkeydown = keyDown;
				var time = 0;
				function doLogin() {
					var userName = document.getElementById("username").value;
					var pwd = document.getElementById("password");
					if (getCookie('cookUser') == '' || time == 0 || userName != '') {
						setCookie('cookUser', userName, time); //set 获取用户名和密码 传给cookie
						setCookie('cookPass', pwd.value, time);
					}
					$("#fm1").submit();
				}
			   	function keyDown(){
				   var ieKey=event.keyCode;
				   if(ieKey == 13){
					   doLogin();
				   }
			  	}
			</script>
	</body>
</html>