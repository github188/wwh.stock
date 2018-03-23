

$(function(){
	$("#getPasswordCode").focus(function(){
		$("#show_secode_menu_content").removeClass("hidden");
	});
	//注册验证
	$('#registerForm').scojs_valid({
		rules: {
			userName  : ['not_empty',{'min_length': 2},{'max_length': 20},'name_only'],
			//email  : ['not_empty','email'],
			password : ['not_empty', {'min_length': 6}],
			confirmPassword :[{matches: 'password'}],
			//mobile:['not_empty','mobile'],
			code:['not_empty','codeequal'],
			agree:['Idoagree']
		},
		messages: {
			userName: {
				not_empty: "请输入用户名",
				min_length: "账号不少于2个字符",
				max_length: "账号不能超过20个字符",
				name_only:"用户名已存在"
			},
			//email: {
			//	not_empty: "请输入邮箱",
			//	email: "请输入正确的邮箱"
			//},

			password: {
				not_empty: "请输入登陆密码，区分大小写",
				min_length: "密码太短了，至少要6位哦"
			},
			confirmPassword:{
				matches: "两次输入密码不一致"
			},
			//mobile:{
			//	not_empty: "请输入手机号",
			//	mobile:"请输入正确的手机号"
			//},
			code:{
				not_empty: "请输入验证码",
				codeequal: "验证码不正确"
			},
			agree: {
				Idoagree: "请先同意注册协议"
			}
		},
		wrapper:'.form-group',
		onSuccess: function(response, validator, $form) {
				//if(response.data){
				//	tipsOp(response.data,response.status);
				//}
			tipsOp(response.data,response.status);
				setTimeout(function(){window.location.href=response.url},1000);
			}
	});

//登录验证
	$('#loginForm').scojs_valid({
		rules: {
			username  : ['not_empty',{'min_length': 2},{'max_length': 20}],
			password : ['not_empty', {'min_length': 5},'pass_right']
		},
		messages: {
			username: {
				not_empty: "请输入账号",
				min_length: "账号不少于2个字符",
				max_length: "账号不能超过20个字符"
			},
			password: {
				not_empty: "请输入登陆密码，区分大小写",
				min_length: "密码太短了，至少要5位哦",
				pass_right:"用户名或密码不正确"
			}
		},
		wrapper:'.form-group'
		,onSuccess: function(response, validator, $form) {
			tipsOp(response.data,response.status);
			if(response.url){
				//window.location.href=response.url;
				setTimeout(function(){window.location.href=response.url;},1000);
			}
		}
	});
});
