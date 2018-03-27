<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <title>后台管理系统</title>
    <link type="text/css" rel="stylesheet" href="css/portal.css"/>
    <link type="text/css" rel="stylesheet" href="css/login.css"/>
</head>
<body>
<div>

    <div class="topNav wrap clearfix">
        <img src="images/logologin.png" class="logo">
        <ul class="topTab">
            <li><a href="#">首页</a></li>
            <li><a href="#">产品服务</a></li>
            <li><a href="#">帮助与支持</a></li>
            <li><a href="#">关于我们</a></li>
        </ul>
    </div>
    <div class="loginbg">
        <input type="hidden" id="loginlogin" value="loginlogin"/>
        <form action="${pageContext.request.contextPath }/SSOAuth" method="post">
            <input type="hidden" name="act" value="login"/>
            <input type="hidden" name="cn" value="${param.cn }"/>
            <input type="hidden" name="goURL" value=""/>
            <input type="hidden" name="uType" id="uType" value="${param.uType }"/>
            <div class="loginbox">
                <div class="title">
                    <a href="javascript:void(0);" class="current" for="1">商务厅</a>
                    <a href="javascript:void(0);" for="2">地市局</a>
                </div>
                <div class="ibox ${param.display }box">
                    <p class="name">登录名：</p>
                    <div style="position:relative;">
                        <input id="username" name="username" value="${param.username }" class="inputtext ${param.display }text" type="text" tabindex="99">
                        <i class="user_icon"></i>
                    </div>
                    <p class="name">登录密码：<a href="#" class="forgetpassword">忘记密码？</a></p>
                    <div style="position:relative;">
                        <input id="password" name="password" value="${param.password }" class="inputtext ${param.display }text" type="password" tabindex="100">
                        <i class="user_password"></i>
                    </div>
                    <div style="position:relative; top:-6px; display: ${param.display }">
                        <input id="verifycode" name="verifycode" maxlength="4"  value="${param.password }" style="padding-right:4px;width: 60px" class="inputtext ${param.display }text" tabindex="101">
                        <img id="verifyimg" src="${pageContext.request.contextPath }/verify" class="verifyimg" onclick="this.src='${pageContext.request.contextPath }/verify?t='+Math.random();">
                        <a href="javascript:void(0);" onclick="document.getElementById('verifyimg').src='${pageContext.request.contextPath }/verify?t='+Math.random();" class="changeimg">换一张</a>
                    </div>
                    <input type="button" onclick="doLogin()" value="登录" class="loginBtn ${param.display }verify">
                    <p id="error-tips" class="errtip">${param.errorInfo }</p><a href="#" class="registLink">免费注册</a>
                </div>
                <div class="boxshadow"></div>
            </div>
        </form>
    </div>
</div>
<div class="footer">
    <p class="helps"><a href="#">新手指南</a>|<a href="#">服务与支持</a>|<a href="#">常见问题</a>|<a href="#">用户中心</a></p>
    <p class="copyright">©2013-2015 浙江省商务厅 版权所有 | 浙ICP备050000009号</p>
</div>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/login/login.js"></script>
<script type="text/javascript">
    jQuery(function () {
        if (window != top) {
            top.location.href = location.href;
        }
    });
</script>
</body>
</html>
