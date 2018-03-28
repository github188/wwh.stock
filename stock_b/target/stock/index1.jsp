<!DOCTYPE html PUBLIC "-W3CDTD XHTML 1.0 TransitionalEN" "http:www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.zjhcsoft.smartcity.magic.common.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http:www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="keywords" content="" />
<meta http-equiv="description" content="" />
<link type="text/css" rel="stylesheet" href="css/login.css" />
<style type="text/css">
ul.auto-tip li:hover{background-color:#eee;
}</style>
<title><%=SystemConfig.INSTANCE.getValue("application_name") %></title>
</head>
<body>
<div class="wrap clearfix">
<!--header-->
<div class="login_header clearfix">
    <span><%=SystemConfig.INSTANCE.getValue("application_name") %></span>
</div>
<!--header end-->
<!--主体内容--->
		<div class="container clearfix">
			<div class="box_l login_box">
				<!-- 嵌入cas的login页面 -->
                <iframe id="J_loginIframe" scrolling="no" frameborder="no" border="0" width="378" height="400"></iframe>
                <script type="text/javascript">
                	var redirectUrl = '<%=request.getContextPath()%>/portal';
                    (function(){
                        var iframeNode = document.getElementById('J_loginIframe'),
                               foo = redirectUrl || '',
                               loginUrl = '<%=SystemConfig.INSTANCE.getValue("casServerUrl")%>/cas/login?service=<%=SystemConfig.INSTANCE.getValue("serverUrl")%>/blank.jsp&css=<%=SystemConfig.INSTANCE.getValue("serverUrl")%>/css/login.css',
                               iframeUrl = loginUrl ;

                        iframeNode.setAttribute('src', iframeUrl);
                        if(iframeNode.attachEvent){
                            iframeNode.attachEvent("onload", iframeLoad);
                        }else{
                            iframeNode.onload = iframeLoad;
                        }

                        function iframeLoad(){
                        }
                    })();
                    </script> 
			</div>
			<div class="img_r">
				<img src="images/img_r.jpg" width="458" height="308" />
			</div>
		</div>
		<!--主体内容 end--->
</div>
</body>
</html>