(function ($) {
    var isIE6 = !-[1, ] && !window.XMLHttpRequest;
    $.fn.hoverForIE6 = function (option) {
        var s = $.extend({ current: "hover", delay: 10 }, option || {});
        $.each(this, function () {
            var timer1 = null, timer2 = null, flag = false;
            $(this).bind("mouseover", function () {
                if (isIE6) {
                    //$(".framecontent").hide;
                }
                if (flag) {
                    clearTimeout(timer2);
                } else {
                    var _this = $(this);
                    timer1 = setTimeout(function () {
                        $("#alllink").text("数据全景图");
                        _this.addClass(s.current);
                        flag = true;
                    }, s.delay);
                }
            }).bind("mouseout", function () {
                if (isIE6) {
                    //$(".framecontent").show();
                }
                if (flag) {
                    var _this = $(this); timer2 = setTimeout(function () {
                        _this.removeClass(s.current);
                        $("#alllink").text("数据全景图");
                        flag = false;
                    }, s.delay);
                } else {
                    clearTimeout(timer1);
                }
            })
        })
    }
})(jQuery);

(function (dtct) {
    window.DTCT = dtct || {};
    window.DTCT = {
        Module: function () {
            this.selectBox = function (fn) {
                var create = function (selector) {
                    var selects = jQuery(selector);
                    function createSelect(select_container, index) {
                        //创建select容器，class为select_box，插入到select标签前
                        var tag_select = jQuery('<div></div>');//div相当于select标签
                        tag_select.attr('class', 'select_box');
                        tag_select.insertBefore(select_container);
                        //显示框class为select_showbox,插入到创建的tag_select中
                        var select_showbox = jQuery('<div></div>');//显示框
                        select_showbox.css('cursor', 'pointer').attr('class', 'select_showbox').appendTo(tag_select);
                        //创建option容器，class为select_option，插入到创建的tag_select中
                        var ul_option = jQuery('<ul></ul>');//创建option列表
                        ul_option.attr('class', 'select_option');
                        ul_option.appendTo(tag_select);
                        createOptions(index, ul_option);//创建option
                        //点击显示框
                        tag_select.toggle(function () {
                            ul_option.show();
                        }, function () {
                            ul_option.hide();
                        });
                        var li_option = ul_option.find('li');
                        li_option.on('click', function () {
                            jQuery(this).addClass('selected').siblings().removeClass('selected');
                            var text = jQuery(this).text();
                            var value = jQuery(this).attr("data-value");
                            var data_control = select_showbox.parent(".select_box").attr("data-control");
                            select_showbox.text(text);
                            select_showbox.attr("data-value", value);
                            ul_option.hide();
                            data_control && jQuery("#" + data_control).val(value);//给原来的控件赋值
                            fn && fn(value);
                        });
                        li_option.hover(function () {
                            jQuery(this).addClass('hover').siblings().removeClass('hover');
                        }, function () {
                            li_option.removeClass('hover');
                        });
                        jQuery("body").click(function (e) {
                            jQuery(this).find(".select_option").hide();
                        });
                    }
                    function createOptions(index, ul_list) {
                        //获取被选中的元素并将其值赋值到显示框中
                        var options = selects.eq(index).find('option'),
                            selected_option = options.filter(':selected'),
                            selected_index = selected_option.index();
                        ul_list.parent(".select_box").attr("data-control", selects.eq(index)[0].id || selects.eq(index)[0].name);
                        showbox = ul_list.prev();
                        showbox.attr("data-value", selected_option.val());
                        showbox.text(selected_option.text());
                        //为每个option建立个li并赋值
                        for (var n = 0; n < options.length; n++) {
                            var tag_option = jQuery('<li></li>'),//li相当于option
                                txt_option = options.eq(n).text(),
                                val_option = options.eq(n).val();
                            tag_option.attr("data-value", val_option);
                            tag_option.text(txt_option).css('cursor', 'pointer').appendTo(ul_list);
                            //为被选中的元素添加class为selected
                            if (n == selected_index) {
                                tag_option.attr('class', 'selected');
                            }
                        }
                    }
                    if (selects && selects.length > 0) {
                        for (var i = 0; i < selects.length; i++) {
                            createSelect(selects[i], i);
                        }
                    }
                }
                jQuery(".form-group select") && create(".form-group select");
            }
            this.navigate = function () {
                jQuery(".vnav").navigator({ itemSelector: ".top_li", contSelector: ".mid_ul" });
                this.navWiew();
            }
            this.navWiew = function () {
                jQuery(".qjbox").hoverForIE6({ delay: 150 });
            }
            this.resetLayout = function (fn) {
                this.fn = fn;
                _this = this;
                var getBrowserWH = function () {
                    var de = document.documentElement;
                    var _width = (de && de.clientWidth) || document.body.clientWidth || window.innerWidth;
                    var _height = (de && de.clientHeight) || document.body.clientHeight || window.innerHeight;
                    return { w: _width, h: _height };
                };
                var resetCss = function () {
                    var width = getBrowserWH().w;
                    var page = document.getElementById("page");
                    var fixMenuBar = document.getElementById("fixMenuBar");
                    if (width > 1200) {
                        page.className = "page page-1200";
                        fixMenuBar.style.width = "1200px";
                        _this.fn && _this.fn();
                    } else {
                        page.className = "page page-1000";
                        fixMenuBar.style.width = "1000px";
                        _this.fn && _this.fn();
                    }
                }
                addEvent(window, 'load', function () {
                    resetCss();
                });
                addEvent(window, 'resize', function () {
                    resetCss();
                });
            }
            this.loadSuggest = function () {
                var arg = {
                    text: "输代码、名称或简拼",
                    autoSubmit: true,
                    width: 271,
                    header: ["选项", "代码", "名称", "类型"],
                    body: [-1, 1, 4, -2],
                    callback: function (arg) { }
                };
                window.StockCode = new StockSuggest("StockCode", arg);
            }
            this.searchBox = function () {
                function InitData() {
                    $('sim-select').onclick = function () {
                        $('define-select').style.display = "";
                    }
                    var isOut = true;
                    var isHiden = false;
                    var dom = $("sim-select");

                    document.onclick = function () {
                        if (isOut || isHiden) {
                            $('define-select').style.display = "none";
                        }
                        isOut = true;
                        isHiden = false;
                    }

                    $('sim-select').onclick = function () {

                        isOut = false;
                        $('define-select').style.display = "";
                    }

                    for (var i in $('define-select').getElementsByTagName("li")) {

                        $('define-select').getElementsByTagName("li")[i].onmouseover = function () {
                            this.className += " over";
                        }

                        $('define-select').getElementsByTagName("li")[i].onmouseout = function () {

                            this.className = this.className.replace(" over", "");
                        }


                        $('define-select').getElementsByTagName("li")[i].onclick = function () {

                            var valName = this.innerHTML.replace("<a>", "").replace("</a>", "");
                            valName = valName.replace("<A>", "").replace("</A>", "");
                            valName = valName.replace(" ", "");
                            $('h3Name').innerHTML = valName;

                            PutValue(valName);
                            isHiden = true;

                            if (valName == "行情" || valName == "股吧") {
                                checkStock(true);
                            }
                            else
                                checkStock(false);
                        }

                    }
                }
                function PutValue(valName) {
                    var val = "";

                    switch (valName) {
                        case '行情':
                            val = "stock";
                            break;
                        case '股吧':
                            val = "guba";
                            break;
                        case '博客':
                            val = "blog";
                            break;
                        case '资讯':
                            val = "news";
                            break;
                    }

                    $('stypeId').value = val;

                }
                var arg = {
                    text: "输代码、名称或拼音",
                    autoSubmit: true,
                    width: 271,
                    header: ["选项", "代码", "名称", "类型"],
                    body: [-1, 1, 4, -2],
                    callback: function (a) { }
                };
                window.StockCodeBar = new StockSuggest("StockCode_bar", arg);

                /*InitData();

                document.getElementById("sofrm").action = "";
                document.getElementById("sofrm").onsubmit = function () { return checkso("sofrm"); };
                document.getElementById("sofrm").StockCode_bar.onclick = function () {
                    if (this.value == '输代码、名称或拼音' || this.value == '输代码、股吧名' || this.value == '请输入查询内容')
                        this.value = '';
                }
                document.getElementById("sofrm").StockCode_bar.onblur = function () {
                    var soType = getRadioValue(document.getElementById("sofrm").stype);
                    switch (soType) {
                        case "stock":
                            if (this.value == "输代码、名称或拼音") {
                                this.value = "输代码、名称或拼音";
                            }
                            break;
                        case "guba":
                            if (this.value == "输代码、名称或拼音") {
                                this.value = "输代码、股吧名";
                            }
                            break;
                        default:
                            if (this.value == "输代码、名称或拼音") {
                                this.value = "请输入查询内容";
                            }
                            break;
                    }
                }*/
            }
            return this;
        },
        Common: function () {
            this.HBAddFavor = function () {
                try {
                    window.external.addFavorite(window.location.href, window.document.title)
                } catch (e) {
                    try {
                        window.sidebar.addPanel(window.document.title, window.location, "")
                    } catch (e) {
                        alert("加入收藏失败，请使用Ctrl+D进行添加");

                    }
                }
            }
            this.HBSetHome = function (e) {
                try {
                    e.style.behavior = "url(#default#homepage)", e.setHomePage(window.location)
                } catch (t) {
                    if (window.netscape) {
                        try {
                            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect")
                        } catch (t) {
                            alert("此操作被浏览器拒绝！请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'")
                        }
                        var n = Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefBranch);
                        n.setCharPref("browser.startup.homepage", window.location)
                    }
                }
            }
            this.alertMsg = function (options, fn) {
                var defaults = {
                    dialogClass: 'js-info',
                    maskClass: 'js-info-mask',
                    typeClass: "",  //info ,error, success, loading
                    typeText: "",
                    width: "auto",
                    height: 50,
                    zIndex: 999,
                    hasMask: true
                }
                var settings = jQuery.extend({}, defaults, options);
                var alert = new Info(null, {
                    typeClass: settings.typeClass,
                    typeText: settings.typeText,
                    width: "auto",
                    height: 50,
                    zIndex: 999,
                    hasMask: true,
                    callback: function () {
                        fn && fn();
                    }
                });
            }
            this.Pager = function (sumcount, pagecount, thispage, method) { //分页js用 不跳转 总数量，每页数量，当前是第几页 特殊状态
                if (sumcount < 10) {
                    return "";
                }
                var sumpage = Math.ceil(sumcount / pagecount); //总页数
                maxpage = sumpage;

                var temphtml = '<a href="javascript:;" data-page="1" target="_self">首页</a>';

                if (thispage > 1) {
                    temphtml += '<a href="javascript:;" data-page="' + (thispage - 1) + '" target="_self">上一页</a>';
                }

                if (thispage > 6 && thispage < sumpage - 5) {
                    for (i = thispage - 5; i <= thispage + 5; i++) {
                        temphtml += '<a href="javascript:;" ' + ((thispage == i) ? 'class="on"' : '') + ' data-page="' + i + '" target="_self">' + i + '</a>';
                    }
                } else if (thispage <= 6) {
                    for (i = 1; i <= 11; i++) {
                        temphtml += '<a href="javascript:;" ' + ((thispage == i) ? 'class="on"' : '') + ' data-page="' + i + '" target="_self">' + i + '</a>';
                        if (i == sumpage) break;
                    }
                } else {
                    var starpage = 1;
                    if (sumpage - 11 > 0) {
                        starpage = sumpage - 11;
                    }
                    for (i = starpage; i <= sumpage; i++) {
                        temphtml += '<a href="javascript:;" ' + ((thispage == i) ? 'class="on"' : '') + ' data-page="' + i + '" target="_self">' + i + '</a>';
                    }
                }
                if (thispage < sumpage) {
                    temphtml += '<a href="javascript:;" data-page="' + (thispage + 1) + '" target="_self">下一页</a>';
                }

                if (typeof (method) != "undefined" && method == "nolast") {

                }
                else {
                    temphtml += '<a href="javascript:;" data-page="' + sumpage + '" target="_self">尾页</a>';
                }

                temphtml += ' 共' + sumpage + '页';
                return temphtml;
            }
            this.Pager2 = function (sumpage, pagecount, thispage, method) { //分页js用 不跳转 总页数，每页数量，当前是第几页 特殊状态
                if (sumpage == 1)
                    return;

                var temphtml = '<a href="javascript:;" data-page="1" target="_self">首页</a>';

                if (thispage > 1) {
                    temphtml += '<a href="javascript:;" data-page="' + (thispage - 1) + '" target="_self">上一页</a>';
                }

                if (thispage > 6 && thispage < sumpage - 5) {
                    for (i = thispage - 5; i <= thispage + 5; i++) {
                        temphtml += '<a href="javascript:;" ' + ((thispage == i) ? 'class="on"' : '') + ' data-page="' + i + '" target="_self">' + i + '</a>';
                    }
                } else if (thispage <= 6) {
                    for (i = 1; i <= 11; i++) {
                        temphtml += '<a href="javascript:;" ' + ((thispage == i) ? 'class="on"' : '') + ' data-page="' + i + '" target="_self">' + i + '</a>';
                        if (i == sumpage) break;
                    }
                } else {
                    var starpage = 1;
                    if (sumpage - 11 > 0) {
                        starpage = sumpage - 11;
                    }
                    for (i = starpage; i <= sumpage; i++) {
                        temphtml += '<a href="javascript:;" ' + ((thispage == i) ? 'class="on"' : '') + ' data-page="' + i + '" target="_self">' + i + '</a>';
                    }
                }
                if (thispage < sumpage) {
                    temphtml += '<a href="javascript:;" data-page="' + (thispage + 1) + '" target="_self">下一页</a>';
                }

                if (typeof (method) != "undefined" && method == "nolast") {

                }
                else {
                    temphtml += '<a href="javascript:;" data-page="' + sumpage + '" target="_self">尾页</a>';
                }

                temphtml += ' 共' + sumpage + '页';
                return temphtml;
            }
            return this;
        }
    }
    window.module = new DTCT.Module();
    //window.module.resetLayout();
    window.module.loadSuggest();
    //window.module.navigate();
    window.module.searchBox();

})(window.DTCT)