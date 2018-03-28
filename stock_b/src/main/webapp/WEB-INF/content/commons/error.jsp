<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
    <style>
    	pre {
	    	margin: 0;
	        padding: 0;
	    }    
    </style>
	<script language="javascript">
		function toggleDiv() {
			if (document.all.detail.style.display == "none") {
				document.all.detail.style.display= "block";
			} else {
				document.all.detail.style.display= "none";
			}
		}
	</script>
</head>
<body>
<%Exception exception =(Exception)request.getAttribute("exception");%>
    <h2>抱歉，系统出现异常!</h2>
    <p>
		<%=exception.getMessage() %>
    </p>
	<p>
		<input type="button" value="返回" onclick="history.back();"/>
	</p>


<div id="stacktraces">
<hr />
<h3><a href="#" onclick="toggleDiv()">错误详细信息</a></h3>
<div id="detail" class="stacktrace" style="padding-left: 0em;display:none" >

    <strong><%=exception.getCause().getMessage()%></strong>
    <div><pre>
		
    </pre>
    </div>
</div>
</div>
</body>
</html>

 