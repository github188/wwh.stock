var username = $("#username");
var password = $("#password");
var verifycode = $("#verifycode");
var errorTips = $("#error-tips");
function doLogin() {
    if (username.val() == "") {
        errorTips.html("登录名不能为空！");
        username.focus();
        return false;
    }

    if (password.val() == "") {
        errorTips.html("密码不能为空！");
        password.focus();
        return false;
    }

    if (!verifycode.parent().is(":hidden")) {
        if (verifycode.val() == "") {
            errorTips.html("验证码不能为空！");
            verifycode.focus();
            return false;
        }
    }

    $("form").submit();
}

$(window).load(function() {
    if (username.val() == "") {
        username.focus();
    } else if (errorTips.html()==""){
        if (password.val()=="") {
            password.focus();
        } else {
            verifycode.focus();
        }
    } else {
        password.focus();
    }

    if (errorTips.html().indexOf('验证码') != -1) {
        verifycode.val("");
        verifycode.focus();
    }
});

$(function () {
    var cookie_login_type = 'cocoda_ass_login_type'; //login_type

    if ($.cookie(cookie_login_type)) {
        if ($.cookie(cookie_login_type) == '2') {
            $(".title a:first-child").removeClass("current").siblings().addClass("current");
        } else {
            $(".title a:first-child").addClass("current").siblings().removeClass("current");
        }

        $("#uType").val($.cookie(cookie_login_type));
    }

    $('.title').on('click', 'a',
        function () {
            $(this).addClass("current");
            $(this).siblings().removeClass("current");
            var curtitle = $(".title .current");
            $("#uType").val(curtitle.attr("for"));

            $.cookie(cookie_login_type, curtitle.attr("for"), {
                path: '/',
                expires: 800
            });
        });

    username.keyup(function (evnet) {
        if (evnet.keyCode == '13') {
            if (username.val() != "") {
                password.focus();
            }
        }
    });

    password.keyup(function (evnet) {
        if (verifycode.parent("div").is(":visible")) {
            if (evnet.keyCode == '13') {
                if (password.val() != "") {
                    verifycode.focus();
                }
            }
        } else {
            if (evnet.keyCode == '13') {
                doLogin();
            }
        }
    });

    verifycode.keyup(function (evnet) {
        if (evnet.keyCode == '13') {
            doLogin();
        }
    });
});