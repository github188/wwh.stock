$(function() {
    flushLoginTicket();
});

// 登录验证函数, 由 onsubmit 事件触发
var loginValidate = function(){
    var msg;
    if ($.trim($('#J_Username').val()).length == 0 ){
        msg = "用户名不能为空。";
    } else if ($.trim($('#J_Password').val()).length == 0 ){
        msg = "密码不能为空。";
    }
    if (msg && msg.length > 0) {
        $('#J_ErrorMsg').fadeOut().text(msg).fadeIn();
        return false;
        // Can't request the login ticket.
    } else if ($('#J_LoginTicket').val().length == 0){
        $('#J_ErrorMsg').text('服务器正忙，请稍后再试..');
        return false;
    } else {
        // 验证成功后，动态创建用于提交登录的 iframe
        $('body').append($('<iframe/>').attr({
            style: "display:none;width:0;height:0",
            id: "ssoLoginFrame",
            name: "ssoLoginFrame",
            src: "javascript:false;"
        }));
        return true;
    }
}

// 由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt
var flushLoginTicket = function(){
    var _services = 'service=' + encodeURIComponent('http://localhost:8081/');
    $.getScript('http://localhost:8080/server/login?'+_services+'&get-lt=true&n='
        + new Date().getTime(),
        function(){
            // 将返回的 _loginTicket 变量设置到  input name="lt" 的value中。
            $('#J_LoginTicket').val(_loginTicket);
        });
}