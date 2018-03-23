<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
	    <title>登录界面</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <meta http-equiv="x-ua-compatible" content="ie=7" />
	    <link rel="stylesheet" type="text/css" media="all" href="css/style2.css" />
	    <!--[if gte IE 6]><style type="text/css" media="screen">@import 'css/ie_cas.css';</style><![endif]-->
	    <script type="text/javascript" src="js/common_rosters.js"></script>
	    <link rel="icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/x-icon" />
	    
	    <!--新加入CSS -->
<link href="css/pages.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/base.css" rel="stylesheet" type="text/css" media="all" />
<!--新加入JS -->
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/pages.js" type="text/javascript"></script>
	</head>

	<body scroll=no  id="cas" onload="init();">
	<div id="header">
		<!--logoAndSearch:Start-->
		<div class="">
			<div class="logoAndSearch_bg">
				<div class="w1002 clearfix re">
					<!--tree:Start-->
					<div class="tree pngFix abs"></div>
					<!--tree:end-->


				</div>
			</div>
		</div>
		<!--logoAndSearch:End-->
	</div>
	<div id="container" class="w1002">
			<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			<form:errors path="*" id="status" element="div" 
			    cssStyle="background-color: rgb(255, 238, 221); position: absolute; width: 100%; font-size: 14px; font-weight: bold; color: rgb(255, 0, 0); line-height: 26px; top: -27px;left: 0px;" />
			<div class="logo pngFix mlogo"></div>
			    <div class="title_font">统一认证综合平台用户登录</div>
			<label> 
			<!--  <spring:message code="screen.welcome.label.netid" />-->
				<c:if test="${not empty sessionScope.openIdLocalId}">
					<strong>${sessionScope.openIdLocalId}</strong>
					<input type="hidden" id="username" name="username"
						value="${sessionScope.openIdLocalId}" />
				</c:if> 
				<c:if test="${empty sessionScope.openIdLocalId}">
					<!-- <spring:message code="screen.welcome.label.netid.accesskey"
						var="userNameAccessKey" />-->
					<form:input cssClass="login_input m01" cssErrorClass="login_input m01 error"
						id="username" size="23" tabindex="1"
						accesskey="${userNameAccessKey}" path="username"
						autocomplete="false" htmlEscape="true" />
				</c:if>
			</label>
        	<label> 
				<!-- <spring:message code="screen.welcome.label.password" />
				<spring:message code="screen.welcome.label.password.accesskey"
					var="passwordAccessKey" /> -->
				<form:password cssClass="login_input m02" cssErrorClass="login_input m02 error" id="password"
					size="25" tabindex="2" path="password"
					accesskey="${passwordAccessKey}" htmlEscape="true"
					autocomplete="off" />
			</label>
			
			<!-- verifyCode -->
			<label> 
				<form:input cssErrorClass="login_input error"
						id="verifyCode" size="12" tabindex="3" path="verifyCode"
						autocomplete="false" htmlEscape="true" class="required login_input" style="position:absolute;left:186px;top:194px;"/>
				<a href="#" onclick="<script type='text/javascript'>document.getElementById('vali').src('captcha.htm' + '?' + (new Date()).valueOf())</script>"
						style="width: 180px; height:40px;">
						<span style="position:absolute;left:305px;top:194px;width: 100px; height:40px; float: left;"><img id="vali" src="captcha.htm" />
					</span> </a>
			</label>
			<span class="login_bg"> 
				<input type="hidden" name="lt" value="${flowExecutionKey}" /> 
				<input type="hidden" name="_eventId" value="submit" /> 
				<a href="#" class="login_button" onclick="doSubmit()" style="position:absolute;left:-71px;top:240px;"></a>
			</span>
			<input class="btn-submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />
			</form:form> 
       	</div> 
        <div id="footer" class="mt10">
			<p>主办单位：衢州市环境保护局 浙ICP备05000553号</p>
		</div>  
		<script type="text/javascript">
			function doSubmit(){
				document.getElementById("fm1").submit();
				//$("form").submit();
			}
		
			document.onkeydown = keyDown;
		   	function keyDown(){
			   var ieKey=event.keyCode;
			   if(ieKey == 13){
				   doSubmit();
			   };
		  	};
		  	
		  	function init(){
		  		if(document.forms[1] != null && document.forms[1].elements[0] != null) {
		  			document.forms[1].elements[0].focus();
		  			document.forms[1].elements[0].select();
		  		}
		  	};
		</script> 	
	</body>
</html>
