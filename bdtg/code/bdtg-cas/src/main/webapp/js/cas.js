/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var editInnerHTML = "";
var deleteInnerHTML = "";
var currentRow = null;

function swapButtonsForConfirm(rowId, serviceId) {

    resetOldValue();
    var editCell = $("#edit"+rowId);
    var deleteCell = $("#delete"+rowId);

    var row = $("#row" + rowId);
    row.removeClass("over");
    row.addClass("highlightBottom");

    editInnerHTML = editCell.html();
    deleteInnerHTML = deleteCell.html();
    currentRow = rowId;
    
    editCell.html("Really?");
    deleteCell.html("<a id=\"yes\" href=\"deleteRegisteredService.html?id=" + serviceId + "\">Yes</a> <a id=\"no\" href=\"#\" onclick=\"resetOldValue();return false;\">No</a>");
}

function resetOldValue() {
    if (currentRow != null) {
        var curRow = $("#row"+currentRow);
        curRow.removeClass("over");
        curRow.removeClass("highlightBottom");
        var editCell = $("#edit"+currentRow);
        var deleteCell = $("#delete"+currentRow);

        editCell.html(editInnerHTML);
        deleteCell.html(deleteInnerHTML);
       
        editInnerHTML = null;
        deleteInnerHTML = null;
        currentRow = null;
    }
}

//cookie获取
function getCookie(c_name) {
	if (document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start != -1){
			c_start = c_start + c_name.length+1;
			c_end=document.cookie.indexOf(";",c_start);
			if (c_end==-1) c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

// cookie设定
function setCookie(c_name, value, expiredays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie=c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}


/**
 * 用户名输入之后判定是否需要验证码
 */
function userInputBlur(){
	$("#username").blur(function() {
		if ($("#username").val() == "") {
			return false;
		}
		// 是否需要验证码check
		$.ajax({
            type : 'GET',  
            contentType : 'application/json',  
            url : 'inputValidate',
            data: {username:$("#username").val(),check:"1"},
            dataType : 'json',  
            async : false,
            success : function(result) {
            	if (result.needVerfiyCd == true) {
            		$("#vali").parent().removeClass("hidden");
            		refresh();
            	} else {
            		$("#vali").parent().addClass("hidden");
            	}
            },
            error:function(XMLResponse){
            	alert(XMLResponse.responseText);
            }
        });  
	});
}

/**
 * 判定当前页面是否需要验证码项
 */
function curDocNeedDynamicCdCheck(){
	if ($("#msg")[0] != undefined && $("#username").val()!='') {
		$("#vali").parent().removeClass("hidden");
		refresh();
		// 需要验证码
		$.ajax({
            type : 'GET',  
            contentType : 'application/json',  
            url : 'inputValidate',
            data: {username:$("#username").val()},
            dataType : 'json',  
            async : false,
            success : function(result) {
            }
        });
	} else {
		$("#vali").parent().addClass("hidden");
	}
}

$(document).ready(function(){
    //focus username field
    //$("input:visible:enabled:first").focus();
	curDocNeedDynamicCdCheck();
	if ($("#msg")[0]!= undefined && $("#msg")[0].innerText.indexOf("验证码") != -1) {
		$("#verifyCode").focus();
	}
	
	userInputBlur();
	
	// 获取Cookie保存的用户名和密码
	var username = getCookie("cookUser");
	var password = getCookie("cookPass");

	if (username != '' && password != '') {
		$("#username").val(username);
		$("#password").val(password);
		$("#remenberPass").attr("checked", true);
		$("#remenberPass").click(function() {//记住密码
			if ($(this).attr("checked") == 'checked') {
				time = 7;
			} else {
				time = 0;
			}
		});
		time = 7;
	} else {
		$("#remenberPass").attr("checked", false);
		$("#remenberPass").click(function() {//记住密码
			if ($(this).attr("checked") == 'checked') {
				time = 7;
			} else {
				time = 0;
			}
		});
	}
	
    //flash error box
    $('#msg.errors').animate({ backgroundColor: 'rgb(187,0,0)' }, 30).animate({ backgroundColor: 'rgb(255,238,221)' }, 500);

    //flash success box
    $('#msg.success').animate({ backgroundColor: 'rgb(51,204,0)' }, 30).animate({ backgroundColor: 'rgb(221,255,170)' }, 500);
    
});

