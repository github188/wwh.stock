var SEPARATOR_MARK = "δ";
Date.prototype.pattern=function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "/u65e5",
        "1" : "/u4e00",
        "2" : "/u4e8c",
        "3" : "/u4e09",
        "4" : "/u56db",
        "5" : "/u4e94",
        "6" : "/u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    return pathName.substr(0, index + 1);
}

var contextPath = getContextPath();
var Man = {
    context: contextPath,
    openWin: function (title, url, width, height, fit, target) {
        if (target == null) {
            target = parent;
        }
        var body = target.$('body');
        if (target.$('#win').length != 0) {
            //target.$('#win').hide();
            target.$('#win').remove();
        }

        if (target.$('#win').length == 0)
            body.append('<div id="win" style="overflow:hidden;"><div id="win_loading" class="loading"></div><iframe id="win_frame" scrolling="no" frameborder="0" src="" style="width:100%;height:100%;"/></div>');
        var win = target.$('#win');
        var winFrame = target.$('#win_frame');
        winFrame[0].src = url;
        winFrame[0].onload = function () {
            if (target.parent.$("#win_loading").length > 0) {
                target.parent.$("#win_loading").remove();
            }

            if (self.$("#win_loading").length > 0) {
                self.$("#win_loading").remove();
            }
        };
        win.window({
            title: title,
            modal: true,
            minimizable: false,
            maximizable: true,
            collapsible: false,
            resizable: true,
            fit: fit ? fit : false,
            width: width ? width : 500,
            height: height ? height : 300
        });

        win.window('open');

        setTimeout(
            function() {
                if (target.parent.$("#win_loading").length > 0) {
                    target.parent.$("#win_loading").remove();
                }

                if (self.$("#win_loading").length > 0) {
                    self.$("#win_loading").remove();
                }
            }, 3000);
    },
    isJson: function (obj) {
        return typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
    },
    closeWin: function () {
        parent.$('#win').window('close');
    },
    findpapa: function () {
        // 多TAB定位
        var findstr = '.tabs-panels .panel:visible iframe';//定位到活动IFRAME
        var obj = parent.$.find(findstr)[0];

        // 单个IFRAME定位
        if (!obj) {
            obj = window.parent.document.getElementById("infoframe");
        }

        // 获取IFRAME窗口
        if (obj) {
            obj = obj.contentWindow;
        } else {
            obj = window.parent;
        }

        return obj;
    },
    reload: function (result) {
        // 判断是否是JSON
        if (!Man.isJson(result)) {
            result = jQuery.parseJSON(result);
        }

        // 有错误时弹出错误并返回
        if (!result.success) {
            // 弹出错误提示
            jQuery.messager.alert("错误", result.message, "error");
            return;
        }

        var obj = Man.findpapa();

        // 关闭处理窗口
        parent.$('#win').window('close');
        // 重新加载列表数据
        obj.jQuery('#dg').datagrid('reload');
        obj.jQuery.messager.show({title: '提示', msg: '操作成功，数据同步完毕！'});
    },
    reloadList: function (result) {
        // 判断是否是JSON
        if (!Man.isJson(result)) {
            result = jQuery.parseJSON(result);
        }

        if(result.success) {
            jQuery('#dg').datagrid('reload');    // reload the user data
        } else {
            jQuery.messager.alert("错误", result.message, "error");
        }
    }
};

function commitCheck() {
    var errcnt = 0;
    var index = -1;
    $(".tab").each(function () {
        index = index + 1;
        var tab = $(this);
        if (!tab.form("validate")) {
            //tab.click();
            $(".tabs").find("a")[index].click();
            tab.form("validate");
            errcnt = errcnt + 1;
            return false;
        }
    });

    if (errcnt == 0) {
        return true;
    } else {
        return false;
    }
}