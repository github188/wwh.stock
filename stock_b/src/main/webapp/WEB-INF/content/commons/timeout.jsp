<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>连接超时</title>
</head>
<%String ctx = request.getContextPath();%>
<body>
    <div>你没有登陆系统或登陆超时,请重新<a href="${ctx}/login.jsp" target="_top">登陆</a></div>
</body>
</html>