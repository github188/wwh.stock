/***
 ** 功能：  字符串格式化替换操作
 ** Author: Allen Zhang
 ** RTX：   14002
 ***/
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,
        function (m, i) {
            return args[i];
        });
}

/***
 ** 功能：  去掉字符左右空格
 ** Author: Allen Zhang
 ** RTX：   14002
 ***/
String.prototype.trim = function () {
    return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

/***
 ** 功能：  格式化时间字符串，支持多种时间格式化类型
 ** 参数：  format 日期对象
 ** 示例：  new Date().format("yyyy年MM月dd日 hh:mm:ss");
 ** Author: Allen Zhang
 ** RTX：   14002
 ***/
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/***
 ** 功能：  加载外部JS文件，加载完成后执行回调函数callback
 ** Author: Allen Zhang
 ** RTX：   14002
 ***/
var utools = {
    config: {
        id: "",
        url: "",
        charset: "gb2312",
        callback: function () { }
    },
    merge: function (a, c) {
        for (var b in c) a[b] = c[b];
        return a
    },
    getScript: function (a) {
        var r = Math.floor(Math.random() * 10000);
        this.config = this.merge(this.config, a);
        var callback = this.config.callback;
        var scriptNode = document.createElement("script");
        scriptNode.setAttribute("id", this.config.id);
        scriptNode.setAttribute('charset', this.config.charset);
        scriptNode.setAttribute('type', 'text/javascript');
        scriptNode.setAttribute('src', this.config.url + "?r=" + r);
        var head = document.getElementsByTagName("head")[0];
        head.appendChild(scriptNode);
        scriptNode[document.all ? "onreadystatechange" : "onload"] = function () {
            if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") {
                if (callback) callback();
                scriptNode.onreadystatechange = scriptNode.onload = null;
                scriptNode.parentNode.removeChild(scriptNode)
            }
        };
    },
    loadScript: function (a) {
        var c, r = Math.floor(Math.random() * 10000);
        this.config = this.merge(this.config, a);
        a = document.getElementById(this.config.id)
        a = document.createElement("script");
        a.setAttribute("id", this.config.id);
        a.setAttribute("type", "text/javascript");
        a.setAttribute("charset", this.config.charset);
        a.setAttribute("src", this.config.url + "?r" + r);
        var b = document.getElementsByTagName("script")[0];
        b.parentNode.insertBefore(a, b);
        c = this.config.callback;
        a.onload = a.onreadystatechange = function () {
            ("undefined" == typeof this.readyState || "loaded" == this.readyState || "complete" == this.readyState) && c()
        }
    },
    loadAjax: function (url, data, fn, beforeSend, complete) {
        var isLoading = isLoading || false;
        jQuery.ajax({
            url: url,
            type: "POST",
            data: data || {},
            beforeSend: function () {
                beforeSend && beforeSend();
            },
            success: function (result) {
                fn && fn(result);
            },
            complete: function () {
                complete && complete();
            },
            error: function (result) {
                console && console.log(result);
            }
        });
    },
    parseQueryString: function (url) {
        var reg_url = /^[^\?]+\?([\w\W]+)$/,
            reg_para = /([^&=]+)=([\w\W]*?)(&|$|#)/g,
            arr_url = reg_url.exec(url),
            ret = {};
        if (arr_url && arr_url[1]) {
            var str_para = arr_url[1], result;
            while ((result = reg_para.exec(str_para)) != null) {
                ret[result[1]] = result[2];
            }
        }
        return ret;
    },
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = decodeURI(window.location.search).substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },
    stopEventBubble: function (event) {
        var e = event || window.event;
        if (e && e.stopPropagation) {
            e.stopPropagation();
        }
        else {
            e.cancelBubble = true;
        }
        return false;
    },
    parsePercentToDecimal: function (percent) {
        return Number(percent.replace('%', '')) / 100;
    }
}

/***
 ** 功能：  cookie操作对象
 ** Author: Allen Zhang
 ** RTX：   14002
 ***/
var cookies = {
    /***
     ** 功能：  写入cookie操作
     ** 参数：  name cookie名称
     **         value cookie值
     **         expires 过期时间
     **         path  路径
     **         domain 域
     ***/
    set: function (name, value, expires, path, domain) {
        expires = new Date(new Date().getTime() + (((typeof expires == "undefined") ? 12 * 7200 : expires)) * 1000);
        var tempcookie = name + "=" + escape(value) +
            ((expires) ? "; expires=" + expires.toGMTString() : "") +
            ((path) ? "; path=" + path : "; path=/") +
            ((domain) ? "; domain=" + domain : "");
        (tempcookie.length < 4096) ? document.cookie = tempcookie : alert("The cookie is bigger than cookie lagrest");
    },

    /***
     ** 功能：  获取cookie操作
     ** 参数：  name cookie名称
     ***/
    get: function (name) {
        var xarr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        if (xarr != null)
            return unescape(xarr[2]);
        return null;
    },

    /***
     ** 功能：  删除cookie操作
     ** 参数：  name cookie名称
     **         path  路径
     **         domain 域
     ***/
    del: function (name, path, domain) {
        if (this.get(name))
            document.cookie = name + "=" +
            ((path) ? "; path=" + path : "; path=/") +
            ((domain) ? "; domain=" + domain : "") +
            ";expires=Thu, 01-Jan-1970 00:00:01 GMT";
    },
    day: function (xd) {
        return xd * 24 * 3600;
    },
    hour: function (xh) {
        return xh * 3600;
    }
}
/***
 ** 功能：  选项卡切换
 ** Author: Allen Zhang
 ** RTX：   14002
 ***/
jQuery.fn.Tabs = function (options) {
    var defaults = {
        tabSelector: ".tabs li",
        conSelector: ".tabcontent",
        focusClass: "c",
        moreTrigger: ".tabTitle .more .link",
        events: "mouseover",
        selected: 0,
        delay: 0.2
    };
    var events = ["mouseover", "click"];
    var settings = jQuery.extend({}, defaults, options);
    var that = this;
    var _tabs = jQuery(settings.tabSelector, that);
    var _cons = jQuery(settings.conSelector, that);
    var _more = jQuery(settings.moreTrigger, that);
    var _isDelay = settings.events == events[0] ? !0 : !1;

    void function () {
        var tab = _tabs.eq(settings.selected);
        if (tab && tab.length == 0) {
            tab = _tabs.eq(0);
        }
        tab.addClass(settings.focusClass);
        tab.siblings(settings.tabSelector).removeClass(settings.focusClass);

        var cons = _cons.eq(settings.selected);
        if (cons && cons.length == 0) {
            cons = _cons.eq(0);
        }
        cons.show();
        cons.siblings(settings.conSelector).hide();

        var more = _more.eq(settings.selected);
        if (more && more.length == 0) {
            more = _more.eq(0);
        }
        more.show();
        more.siblings().hide();
    }();

    _tabs.each(function (i, v) {
        jQuery(v).on(settings.events, function () {
            var _this = this;
            delay.apply(this, [settings.delay, function () {
                jQuery(_this).addClass(settings.focusClass);
                jQuery(_this).siblings(settings.tabSelector).removeClass(settings.focusClass);
                jQuery(_cons[i]).show();
                jQuery(_cons[i]).siblings(settings.conSelector).hide();
                jQuery(_more[i]).show();
                jQuery(_more[i]).siblings().hide();
            }, _isDelay])
        });
    });
    //接收两个参数 t延迟时间秒为单位，fn要执行的函数,m是否执行延迟取决于事件的类型
    var delay = function (t, fn, m) {
        if (m) {
            var _this = this,
                d = setInterval(function () {
                    fn.apply(_this);
                }, t * 1000);
            _this.onmouseout = function () {
                clearInterval(d);
            };
        }
        else fn.apply(this);
    }
}

var setMenuStatus = function () {
    //var href = window.location.href.toLowerCase();
    var $menu = jQuery("a[data-page]", ".top_ul");
    var count = 0;
    var flag = false;
    $menu.each(function (i, ele) {
        var selectedMenu = jQuery(ele).attr("data-page");
        //var index = href.indexOf(selectedMenu);
        //if (index > 0) {

        if (page_type && page_type == selectedMenu) {
            flag = true;
            count++;
            if (count > 1) {
                return;
            }
            var current_a = jQuery("a[data-page='" + selectedMenu + "']");
            var current_mid_li = current_a.parents("li.mid_li");
            var current_top_li = current_a.parents("li.top_li");
            if (current_mid_li && current_mid_li.hasClass("cos")) {
                current_mid_li.removeClass("cos").addClass("exd");
            } else if (current_mid_li && current_mid_li.hasClass("exd")) {
                current_mid_li.removeClass("exd").addClass("cos");
            }
            if (current_top_li && current_top_li.hasClass("cos")) {
                current_top_li.removeClass("cos").addClass("exd");
            } else if (current_top_li && current_top_li.hasClass("exd")) {
                current_top_li.removeClass("exd").addClass("cos");
            }

            //ie6兼容性调整
            if (is_ie6() && !current_mid_li.hasClass("curr")) {
                jQuery(".mid_li.exd h5").css("background-color", "#e5f3ff").css("border", "none");
                jQuery(".mid_li.cos h5").css("background-color", "#e5f3ff").css("border", "none");
                jQuery(".top_li ul").css("border", "none");
            }

            var current_sub_li = current_a.parents("li.sub_li");

            if (current_sub_li.length > 0) {
                current_sub_li.addClass("curr");
            }
            else if (current_mid_li.length > 0) {
                current_mid_li.addClass("curr");

                //ie6兼容性调整
                if (is_ie6() && current_mid_li.hasClass("curr")) {
                    current_mid_li.css("background-color", "#fff");
                    current_mid_li.find("h5").css("background-color", "#fff");
                    //current_mid_li.css("border", "none");
                    if (current_mid_li.hasClass("exd") || current_mid_li.hasClass("cos")) {
                        current_mid_li.find("h5").css("margin-left", "-7px");
                    } else {
                        current_mid_li.css("padding-left", "17px");
                    }
                }
            }

        }

    })
    //ie6兼容性调整
    if (is_ie6() && !flag) {
        jQuery(".mid_li.exd h5").css("background-color", "#e5f3ff").css("border", "none");
        jQuery(".mid_li.cos h5").css("background-color", "#e5f3ff").css("border", "none");
        jQuery(".top_li ul").css("border", "none");
    }
}

var setOpen = function (opend) {
    if (opend < 0) {
        jQuery(".top_li.cos", ".vnav").removeClass("cos").addClass("exd");
    }
}

jQuery.fn.navigator = function () {
    var opend = 100;//全部展开
    var $header = jQuery(".top_li", ".vnav");
    var $content = jQuery(".mid_ul", ".vnav");
    var $trigger = jQuery(".top_li h4, .mid_li h5", ".vnav");
    var config = [];

    void function ($) {
        setOpen(opend);
        //setMenuStatus();
        $trigger.on("click", function (event) {
            if ($(this).parent().hasClass("cos")) {
                if ($(this).parent().hasClass("top_li")) {
                    $('.top_ul .top_li').addClass('cos').first().removeClass("cos");
                }
                $(this).parent().removeClass("cos").addClass("exd");
                //ie6兼容性调整
                if (is_ie6() && $(this).parent().hasClass("curr") && $(this).parent().hasClass("mid_li")) {
                    var current_mid_li = $(this).parent();
                    current_mid_li.find("h5").css("background", "url('/images/data-icon.gif') no-repeat scroll 17px -207px #fff");
                }

            } else if ($(this).parent().hasClass("exd")) {
                $(this).parent().removeClass("exd").addClass("cos");
                //ie6兼容性调整
                if (is_ie6() && $(this).parent().hasClass("curr") && $(this).parent().hasClass("mid_li")) {
                    $(this).parent().find("h5").css("background","url('/images/data-icon.gif') no-repeat scroll 17px -249px #e5f3ff");
                }
            }
            utools.stopEventBubble(event);
        });

        //隐藏左侧导航第一个二级菜单上边框
        var top_lis = $(".navbody>.top_ul>.top_li");
        top_lis.each(function (index, ele) {
            $(ele).find("ul.mid_ul>li.mid_li").first().addClass("remove_top_border");
        })
    }(jQuery);
}


/***
 注册事件
 ***/
function addEvent(obj, type, fn) {
    if (obj.addEventListener) {
        obj.addEventListener(type, fn, false);
    } else if (obj.attachEvent) {
        obj.attachEvent('on' + type, fn);
    }
}