<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>协会管理云平台</title>
    <link type="text/css" rel="stylesheet" href="css/portal.css"/>
    <link type="text/css" rel="stylesheet" href="css/login.css"/>
    <!--[if lt IE 9]>
    <script>
        // html5shiv MIT @rem remysharp.com/html5-enabling-script
        // iepp v1.6.2 MIT @jon_neal iecss.com/print-protector
        /*@cc_on(function(m,c){var z="abbr|article|aside|audio|canvas|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video";function n(d){for(var a=-1;++a<o;)d.createElement(i[a])}function p(d,a){for(var e=-1,b=d.length,j,q=[];++e<b;){j=d[e];if((a=j.media||a)!="screen")q.push(p(j.imports,a),j.cssText)}return q.join("")}var g=c.createElement("div");g.innerHTML="<z>i</z>";if(g.childNodes.length!==1){var i=z.split("|"),o=i.length,s=RegExp("(^|\\s)("+z+")",
         "gi"),t=RegExp("<(/*)("+z+")","gi"),u=RegExp("(^|[^\\n]*?\\s)("+z+")([^\\n]*)({[\\n\\w\\W]*?})","gi"),r=c.createDocumentFragment(),k=c.documentElement;g=k.firstChild;var h=c.createElement("body"),l=c.createElement("style"),f;n(c);n(r);g.insertBefore(l,
         g.firstChild);l.media="print";m.attachEvent("onbeforeprint",function(){var d=-1,a=p(c.styleSheets,"all"),e=[],b;for(f=f||c.body;(b=u.exec(a))!=null;)e.push((b[1]+b[2]+b[3]).replace(s,"$1.iepp_$2")+b[4]);for(l.styleSheet.cssText=e.join("\n");++d<o;){a=c.getElementsByTagName(i[d]);e=a.length;for(b=-1;++b<e;)if(a[b].className.indexOf("iepp_")<0)a[b].className+=" iepp_"+i[d]}r.appendChild(f);k.appendChild(h);h.className=f.className;h.innerHTML=f.innerHTML.replace(t,"<$1font")});m.attachEvent("onafterprint",
         function(){h.innerHTML="";k.removeChild(h);k.appendChild(f);l.styleSheet.cssText=""})}})(this,document);@*/
        }
    </script>
    <![endif]-->
</head>
<body>
<div>

    <div class="topNav wrap clearfix">
        <img src="images/logologin.png" class="logo"><img src="images/login/zhdl.png" style="float:left;margin:40px 0 0 10px;">
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
            <input type="hidden" name="goURL" value="${param.goURL }"/>
            <input type="hidden" name="uType" id="uType" value="${param.uType }"/>
            <div class="loginbox">
                <div class="title">
                    <a href="javascript:void(0);" class="current" for="1">员工通道</a>
                    <a href="javascript:void(0);" for="2">会员通道</a>
                </div>
                <div class="ibox">
                    <p class="name">登录名：</p>
                    <div style="position:relative;">
                        <input id="username" name="username" value="${param.username }" class="inputtext" type="text" tabindex="99">
                        <i class="user_icon"></i>
                    </div>
                    <p class="name">登录密码：<a href="#" class="forgetpassword">忘记密码？</a></p>
                    <div style="position:relative;">
                        <input id="password" name="password" value="${param.password }" class="inputtext" type="password" tabindex="100">
                        <i class="user_password"></i>
                    </div>
                    <input type="button" onclick="doLogin()" value="登录" class="loginBtn">
                    <p id="error-tips" class="errtip">${param.errorInfo }</p><a href="#" class="registLink">免费注册</a>
                </div>
                <div class="boxshadow"></div>
            </div>
        </form>
    </div>
</div>
<div class="footer">
    <p class="helps"><a href="#">新手指南</a>|<a href="#">服务与支持</a>|<a href="#">常见问题</a>|<a href="#">用户中心</a></p>
    <p class="copyright">gomiten@163.com&nbsp;2015版权所有&nbsp;|&nbsp;浙ICP备050000009号</p>
</div>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/login/login.js"></script>
<script>
    jQuery(function() {
        if(self.frameElement.tagName=="IFRAME"){
            parent.$("#win_loading").remove();
            parent.$("#win_frame").show();
            //parent.window.location.href='/assoc';
        }
    });
</script>
</body>
</html>
