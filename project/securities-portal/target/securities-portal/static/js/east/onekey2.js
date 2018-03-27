function zxgInit(a) {
    localurl = a, jQuery(document.body).prepend('<div id="divContents"></div>');
}
function getCookieSW(a) {
    var b = document.cookie.match(new RegExp(a + "=([^;]*)"));
    return null != b ? unescape(decodeURI(b[1])) : null;
}
function fLen(a) {
    for (var b = 0, c = a.length, d = 0; c > d; d++)
        a.charCodeAt(d) > 255 ? b += 2 : b++;
    return b;
}
function swAlertjsq(a) {
    --a > 0 ? tdjs = setTimeout("swAlertjsq(" + a + ")", 1e3) : swAlertClose();
}
function SetString(a, b) {
    for (var c = 0, d = "", e = 0; e < a.length; e++)
        if (a.charCodeAt(e) > 128 ? c += 2 : c++, d += a.charAt(e), c >= b)
            return d;
    return d;
}
function Sec5Reg() {
    window.open("http://passport.eastmoney.com/PhoneReg.EmUser");
}
function ForgetPwd() {
    window.open("http://passport.eastmoney.com/FindPwd.EmUser");
}
function logOutCB(a) {
    jQuery.getJSON(m_domain + "AjaxAction.ashx?cb=?", { op: "clearcookies", keys: "pi|pw" }, function () {
        a();
    });
}
function Login2(a) {
    jQuery(a + " .mmerr").html(""), jQuery(a + " .yzmerr").html("");
    var b = jQuery(a + " .txtdlm").val(), c = jQuery(a + " .txtmm").val();
    if ("手机/邮箱名/用户名" == b || "" == b)
        return jQuery(a + " .mmerr").html("请输入用户名"), void 0;
    if (c.length < 6 || c.length > 18)
        return jQuery(a + " .mmerr").html("请输入正确的用户名和密码"), void 0;
    if (jQuery(a + " #tryzm").is(":hidden"))
        jQuery.getJSON(m_domain + "AjaxAction.ashx?cb=?", { op: "login", dlm: b, mm: c }, function (b) {
            "true" == b.result ? void 0 != logOK && "" != logOK && null != logOK && logOK() : (jQuery(a + " .mmerr").html("登录名和密码不匹配"), jQuery(a + " .txtmm").select(), "-1" == b.msg && jQuery(a + " #tryzm").is(":hidden") && (jQuery(a + " .divXDDW").css("height", "372px"), jQuery(a + " .divLoginLeft").css("height", "342px"), jQuery(a + " .divLoginRight .loginbtn").css("_left", "0px;"), jQuery(a + " .divLoginRight .loginbtn").css("top", "220px"), jQuery(a + " .divLoginRight .loginbtn").css("_top", "85px"), jQuery(a + " .divLoginRight #tryzm").show(), jQuery(a + " .divLoginRight .txtyzm").keydown(function (b) {
                13 == b.keyCode && Login2(a);
            }), refreshyzm(a + " .loginForm .yzm")));
        });
    else {
        var d = jQuery(a + " .txtyzm").val();
        "" == d ? (jQuery(a + " .yzmerr").html("请先输入验证码"), refreshyzm(a + " .loginForm .yzm")) : jQuery.getJSON(m_domain + "AjaxAction.ashx?cb=?", { op: "login", dlm: b, mm: c, vcode: d }, function (b) {
            "true" == b.result ? void 0 != logOK && "" != logOK && null != logOK && logOK() : ("-2" == b.msg ? jQuery(a + " .yzmerr").html("验证码输入不正确") : (jQuery(a + " .mmerr").html("登录名和密码不匹配"), jQuery(a + " .txtmm").select()), refreshyzm(a + " .loginForm .yzm"));
        });
    }
}
function login4ShowCB(a) {
    logOK = a, login4Show();
}
function login4Show() {
    getCookieSW("pi") ? location.href = localurl : (document.getElementById("divContents").innerHTML = "", getLogin4Html(), jQuery("#login4aspx  .txtdlm").keydown(function (a) {
        13 == a.keyCode && Login2("#login4aspx");
    }), jQuery("#login4aspx  .txtmm").keydown(function (a) {
        13 == a.keyCode && Login2("#login4aspx");
    }), null != getCookieSW("pu") && jQuery("#login4aspx .txtdlm").val(getCookieSW("pu")), jQuery("#login4aspx").show(), divCloseBind());
}
function getLogin4Html() {
    var a = '<div class="divAlertMsg" style="display:none;"><div class="divAlertZheZhao"></div><div class="divMsg"><div id="divClose" class="divClose"><img src="' + m_domain + 'image/close.png" alt="" class="a" /></div><div class="item"></div></div></div>';
    a += '<div class="divRegForm" id="login4aspx">', a += '<div class="divRegZheZhao"></div>', a += '<div class="divRegBox" id="divRegBox">', a += '<div class="divXDDW">', a += '<div id="divClose" class="divClose"><img src="' + m_domain + 'image/close.png" alt="" class="a" /></div>', a += '<div class="divLoginLeft">', a += '<div class="tit1">使用合作网站帐号登录</div>', a += '<div class="tit2" onclick="javascript:LoginQQ();"><div class="item"><em class="qqicon"></em>腾讯QQ帐号登录</div></div>', a += '<div class="tit3" onclick="javascript:LoginSina();"><div class="item"><em class="sinaicon"></em>新浪微博帐号登录</div></div>', a += '<div class="tit7"></div>', a += "</div>", a += '<div class="divLoginRight">', a += '<div class="userLogintit">用户登录<span style="font-size:12px;font-weight:normal;margin-left:156px;"><a href="http://passport.eastmoney.com/PhoneReg.EmUser" target="_blank" style="text-decoration: underline;color:#003598">5秒快速注册</a></span></div>', a += '<hr color="#B3D0E4" width="90%" style="height:1px;border:0px;margin-top:10px;" />', a += '<div class="loginhrtit">注册后可以查看，已有自选股、股吧、博客账号，可直接登录</div>', a += '<div class="loginForm">', a += '<table style="position:absolute;left:30px;">', a += '<tr><td style="text-align:right;">登录名：</td><td><input type="text" class="txtdlm" value="" onfocus="javascript:regckuser(this,\'login4aspx\');" onblur="javascript:regckuser(this,\'login4aspx\');" /></td></tr>', a += '<tr><td>&nbsp;</td><td class="dlmnoerr">输入登录名请注意区分英文大小写</td></tr>', a += '<tr><td style="text-align:right;">密码：</td><td><input type="password" class="txtmm" maxlength="18" /></td></tr>', a += '<tr><td>&nbsp;</td><td class="mmerr"></td></tr>', a += '<tr id="tryzm" style="display:none;"><td style="text-align:right;">验证码：</td><td><input type="text" class="txtyzm" /><img class="yzm" onclick="javascript:refreshyzm(\'#login4aspx .yzm\');" alt="加载中" title="看不清，换一张" /></td></tr>', a += '<tr><td>&nbsp;</td><td class="yzmerr"></td></tr>', a += "</table>", a += "</div>", a += '<div class="loginbtn">', a += '<div class="loginOK" onclick="javascript:Login2(\'#login4aspx\');">', a += '<div class="okItem">登 录</div>', a += "</div>", a += '<div class="wjmm" onclick="javascript:ForgetPwd();">忘记密码</div>', a += "</div>", a += "</div>", a += "</div>", a += "</div>", a += "</div>", jQuery("#divContents").append(a);
}
function LoginSina() {
    location.href = "https://api.weibo.com/oauth2/authorize?client_id=3050999700&redirect_uri=http://passport.eastmoney.com/login_sina.aspx?goem=" + localurl + "&response_type=code&display=default";
}
function LoginQQ() {
    location.href = "http://openapi.qzone.qq.com/oauth/show?which=ConfirmPage&display=pc&response_type=code&client_id=100507134&redirect_uri=http%3a%2f%2fpassport.eastmoney.com%2flogin_qq.aspx%3fgoem%3d" + localurl + "&scope=get_user_info,add_share,list_album,upload_pic,check_page_fans,add_t,add_pic_t,del_t,get_repost_list,get_info,get_other_info,get_fanslist,get_idolist,add_idol,del_idol,add_one_blog,add_topic,get_tenpay_addr";
}
function divCloseBind() {
    jQuery(".divRegForm .divClose").click(function () {
        jQuery(".divRegForm").hide();
    });
}
function refreshyzm(a) {
    jQuery(a).attr("src", "http://yzm.eastmoney.com/VerifyCode.aspx?t=" + Math.random());
}
function regckuser(a, b) {
    "手机/邮箱名/用户名" == jQuery(b + " .txtdlm").val() ? jQuery(b + " .txtdlm").val("") : "" == jQuery(b + " .txtdlm").val() ? jQuery(b + " .txtdlm").val("手机/邮箱名/用户名") : jQuery(b + " #tryzm").is(":hidden") && jQuery.getJSON(m_domain + "AjaxAction.ashx?cb=?", { op: "getloginstatus", dlm: jQuery(b + " .txtdlm").val() }, function (a) {
        "false" == a.result && "-1" == a.msg && (jQuery(b + " .divXDDW").css("height", "372px"), jQuery(b + " .divLoginLeft").css("height", "342px"), jQuery(b + " .divLoginRight .loginbtn").css("_left", "0px;"), jQuery(b + " .divLoginRight .loginbtn").css("top", "220px"), jQuery(b + " .divLoginRight .loginbtn").css("_top", "85px"), jQuery(b + " .divLoginRight #tryzm").show(), jQuery(b + " .divLoginRight .txtyzm").keydown(function (a) {
            13 == a.keyCode && Login2(b);
        }), refreshyzm(b + " .loginForm .yzm"));
    });
}
jQuery.noConflict(), m_domain = "http://passport.eastmoney.com/onekey/";
var localurl = "", logOK, jsregular = { email: function (a) {
    var b = /^(?:[a-zA-Z0-9]+[_\-\+\.\_]?)*[a-zA-Z0-9\_]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/;
    return b.test(a);
}, username: function (a) {
    return fLen(a) < 3 || fLen(a) > 20 ? -1 : /^\w+$/.test(a) ? /^([a-z]|[A-Z])[0-9a-zA-Z_]+$/.test(a) ? /[0-9a-zA-Z]+$/.test(a) ? 1 : -4 : -3 : -2;
}, nickname: function (a) {
    return fLen(a) < 3 || fLen(a) > 16 ? -1 : /^[a-zA-Z0-9_\u4E00-\u9FA5]+$/.test(a) ? /^([a-z]|[A-Z]|[\u4E00-\u9FA5])[\u4E00-\u9FA50-9a-zA-Z_]+$/.test(a) ? /[a-zA-Z0-9\u4E00-\u9FA5]+$/.test(a) ? 1 : -4 : -3 : -2;
}, mobile: function (a) {
    var b = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
    return b.test(a);
}, diyquestion: function (a) {
    return fLen(a) < 6 || fLen(a) > 30 ? !1 : /^[a-zA-Z0-9\u4E00-\u9FA5]+$/.test(a) ? !0 : !1;
}, answer: function (a) {
    return fLen(a) < 3 || fLen(a) > 30 ? -1 : 1;
}, passwordstrong: function (a) {
    if (a.length <= 6)
        return 1;
    var b = /^[0-9]+$/, c = /^[a-z]+$/, d = /^[0-9a-z]+$/, e = /^[0-9A-Z]+$/, f = /^[a-zA-Z]+$/, g = /^[a-zA-Z0-9]+$/;
    return b.test(a) || c.test(a) || c.test(a) ? 1 : d.test(a) || e.test(a) || f.test(a) ? 2 : g.test(a) ? 3 : 3;
} };
