<%@ page import="com.zjhcsoft.smartcity.cas.server.SystemConfig" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:theme code="mobile.custom.css.file" var="mobileCss" text="" />
<html xmlns="http:www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="keywords" content="" />
    <meta http-equiv="description" content="" />
    <script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/js/cas.js" />"></script>
    <style type="text/css">
        .hidden{
            display: none;
        }
    </style>
    <link type="text/css" rel="stylesheet" href="css/login.css" />
    <style type="text/css">
        ul.auto-tip li:hover{background-color:#eee;
        }</style>
    <title><%=SystemConfig.INSTANCE.getSystemName()%></title>
</head>
<body>
<div class="wrap clearfix">
    <!--header-->
    <div class="login_header clearfix">
        <span><%=SystemConfig.INSTANCE.getSystemName()%></span>
    </div>
    <!--header end-->
    <!--主体内容--->
    <div class="container clearfix">
        <div class="box_l login_box">
            <div class="box" id="login">
                <form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
                    <form:errors path="*" id="msg" cssClass="errors" element="div" />
                    <div class="row fl-controls-left">
                        <label for="username" class="fl-label-username"><spring:message code="screen.welcome.label.netid" /></label>
                        <c:if test="${not empty sessionScope.openIdLocalId}">
                            <strong>${sessionScope.openIdLocalId}</strong>
                            <input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
                        </c:if>
                        <c:if test="${empty sessionScope.openIdLocalId}">
                            <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
                            <form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
                        </c:if>
                    </div>
                    <div class="row fl-controls-left">
                        <label for="password" class="fl-label-password"><spring:message code="screen.welcome.label.password" /></label>
                        <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
                        <form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
                    </div>

                    <!-- verifyCode -->
                    <div class="row fl-controls-left">
                        <label for="verifyCode" class="fl-label-verifyCd"><spring:message code="screen.welcome.label.verifycd"/> </label>
                        <input class="required" type="text" tabindex="3" id="verifyCode" size="4" name="verifyCode"/>
                        <img id="vali" class="code" onclick="refresh();return false;" alt="change it"/>
                        <a href="javacript://" class="changepic" onclick="refresh();return false;">换一张</a>
                    </div>
                    <div class="row1">
                        <label>&nbsp;</label>
                        <input id="remenberPass" type="checkbox" value="" class="check"/>
                        <span style="vertical-align: bottom;"> 记住用户名和密码</span>
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
        </div>
        <div class="img_r">
            <!--<img src="resources/images/img_r.jpg" width="458" height="308" />-->
        </div>
    </div>
    <!--主体内容 end--->
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