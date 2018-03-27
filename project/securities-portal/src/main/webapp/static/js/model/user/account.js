

$(function(){

	//基本资料
	$('#editBasicForm').scojs_valid({
	    rules: {
            //email  : ['not_empty','email'],
			//mobile : ['not_empty','digit',{min_length:11},{max_length:11}],
			qq : [
				//'not_empty',
				'digit',{max_length:12}],
			phone : ['alpha_dash'],
			province : ['haveprovince'],
			city : ['havecity']
	    },
	    messages: {
			//email: {
			//	not_empty: "请输入邮箱",
			//	email: "请输入正确的邮箱"
			//},
			//mobile:{
			//	not_empty: "请输入手机号码",
			//	digit: "请输入正确的手机号码",
			//	min_length:'请检查手机号码是否输入正确',
			//	max_length:'请检查手机号码是否输入正确'
			//},
			qq:{
				//not_empty: "请输入QQ号码",
				digit: "请输入正确的QQ号码",
				max_length:'输入长度不符'
			},
			phone:{
				alpha_dash: "请输入正确的固定电话号码"
			},
			province:{
				haveprovince: "请选择省份"
			},
			city:{
				havecity: "请选择城市"
			}
	    },
	    wrapper:'.form-group'
	    ,onSuccess: function(response, validator, $form) {
	    	  tipsUser(response.data,response.status);
	    }
	});
	//基本资料--企业资料
	$('#editBasicEnterPriseForm').scojs_valid({
		rules: {
			//name: ['not_empty',{'min_length': 2}],
			//code:['not_empty'],
			staffNum:['digit'],
			runYears: ['digit'],
			url:['url'],
			//email  : ['not_empty','email'],
			//mobile : ['not_empty','digit',{min_length:11},{max_length:11}],
			qq : [
				//'not_empty',
				'digit',{max_length:12}],
			phone : ['alpha_dash'],
			province : ['haveprovince'],
			city : ['havecity']
		},
		messages: {
			//name: {
			//	not_empty: "请输入企业/机构名称",
			//	min_length: "最少输入两位字符"
			//},
			//code: {
			//	not_empty: "请输入营业执照号码"
			//},
			staffNum: {
				digit: "员工人数不能为空"
			},
			runYears: {
				digit: "经营年数不能为空"
			},
			url: {
				url: "请输入正确的URL"
			},
			//email: {
			//	not_empty: "请输入邮箱",
			//	email: "请输入正确的邮箱"
			//},
			//mobile:{
			//	not_empty: "请输入手机号码",
			//	digit: "请输入正确的手机号码",
			//	min_length:'请检查手机号码是否输入正确',
			//	max_length:'请检查手机号码是否输入正确'
			//},
			qq:{
				//not_empty: "请输入QQ号码",
				digit: "请输入正确的QQ号码",
				max_length:'输入长度不符'
			},
			phone:{
				alpha_dash: "请输入正确的固定电话号码"
			},
			province:{
				haveprovince: "请选择省份"
			},
			city:{
				havecity: "请选择城市"
			}
		},
		wrapper:'.form-group'
			,onSuccess: function(response, validator, $form) {
				tipsUser(response.data,response.status);
			}
	});


	//联系方式
	$('#editContactForm').scojs_valid({
		rules: {
			email  : ['not_empty','email'],
			mobile : ['not_empty','digit',{min_length:11},{max_length:11}],
			qq : ['not_empty','digit',{max_length:12}],
			phone : ['alpha_dash']
		},
		messages: {
			email: {
				not_empty: "请输入邮箱",
				email: "请输入正确的邮箱"
			},
			mobile:{
				not_empty: "请输入手机号码",
				digit: "请输入正确的手机号码",
				min_length:'请检查手机号码是否输入正确',
				max_length:'请检查手机号码是否输入正确'
			},
			qq:{
				not_empty: "请输入QQ号码",
				digit: "请输入正确的QQ号码",
				max_length:'输入长度不符'
			},

			phone:{

				alpha_dash: "请输入正确的固定电话号码"
			}
		},
		wrapper:'.form-group'
		,onSuccess: function(response, validator, $form) {
	    	  tipsUser(response.data,response.status);
	    }
	});

	//安全设置  --修改安全码
	$('#editSecurityForm').scojs_valid({
		rules: {
			old_code  : ['not_empty',{'min_length': 6},'paypasswordvalidate'],
			new_code : ['not_empty', {'min_length': 6}],
			confirm_code :[{matches: 'new_code'}]
		},
		messages: {
			old_code: {
				not_empty: "请输入安全密码，区分大小写",
				min_length: "安全密码太短了，至少要6位哦",
				paypasswordvalidate:"原密码不正确"
			},
			new_code:{
				not_empty: "请输入新的安全密码，区分大小写",
				min_length:'安全密码太短了，至少要6位哦'
			},
			confirm_code:{
				matches: "两次输入密码不一致"
			}
		},
		wrapper:'.form-group'
		,onSuccess: function(response, validator, $form) {
		    	  tipsUser(response.data,response.status);
		    	  if(response.url){
						window.location.href=response.url;
		    	  }
			}
		});
	//安全设置--修改登录密码
	$('#editPasswordForm').scojs_valid({
		rules: {
			old_password  : ['not_empty',{'min_length': 5},'passwordvalidate'],
			new_password : ['not_empty', {'min_length': 5}],
			confirm_password :[{matches: 'new_password'}]
		},
		messages: {
			old_password: {
				not_empty: "请输入登录密码，区分大小写",
				min_length: "密码太短了，至少要6位哦",
				passwordvalidate:"原密码不正确"
			},
			new_password:{
				not_empty: "请输入新的登录密码，区分大小写",
				min_length:'密码太短了，至少要6位哦'
			},
			confirm_password:{
				matches: "两次输入密码不一致"
			}
		},
		wrapper:'.form-group'
			,onSuccess: function(response, validator, $form) {
				tipsUser(response.data,response.status);
				if(response.url){
					window.location.href=response.url;
				}
			}
	});
	
	$("#zh-zf-pwd").click(function(){
		
		
    var confirm = $.scojs_confirm({
	        content: "确定要找回支付密码?",
	        action: function() {
	        	$.post('index.php?do=ajax&view=getZfpwd',{opajax:'getzfpwd'},function(json){
	        		tipsOp(json.msg,json.status);
	        	},'json');
	        }
	        });
	        confirm.show();
	});

});





