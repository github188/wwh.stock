/*
 StockSelector对象
 */
var Class = {
    create: function () {
        return function () { this.initialize.apply(this, arguments) }
    }
}
var click = 'onclick';
if (navigator.userAgent.indexOf('WebKit') >= 0)
    click = 'onclick="external.OnClickEvent(this.rel);" rel';

Object.extend = function (destination, source) {
    for (property in source) {
        destination[property] = source[property]
    }
    return destination;
};

var StockSelector = Class.create();

Object.extend(
    StockSelector.prototype, {
        initialize: function (opt) {
            var _this = this;
            var i = 0;
            if (tiny.browser.ie6) {
                _this.cache.ie6 = true;
            }
            tiny.extend(_this.options, opt || {});
            _this.fixTable(); //处理表格项
            _this.parseUrl();
            _this.callBack(); //自动回调
        },

        //缓存
        cache: {
            data: {
                value: [],
                type: [],
                calssFication: [],
                data: null
            },
            page: {
                page: 1,
                pages: 1,
                totle: 0
            },
            sort: {
                code: "",
                type: ""
            },
            show: null,
            ie6: false,
            watch: null,
            excids: [],	   // 冲突项集合
            code: null,     // 最后一次操作的特征码
            locked: false,   // 更新数据到页面过程中，锁定页面
            callback: null
        },

        //配置信息
        options: {
            max: 10,
            pageSize: 50,
            debug: false,
            dataurl: "http://xuanguapi.eastmoney.com/Stock/JS.aspx?type=xgq&sty=xgq&token=eastmoney&c={codes}&p={page}&jn={jn}&ps={ps}",
            type: 'web', // 'soft','web' 分别为网页版和软件客户端使用
            table: ""
        },
        //随机码
        getCode: function (num) {
            var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            var codes = str.split('');
            num = num || 6;
            var code = "";
            for (var i = 0; i < num ; i++) {
                code += codes[Math.floor(Math.random() * 52)];
            }
            return code;
        },

        fixTable: function () {
            var _this = this;
            var _table = tiny.$("dataslist");
            var _ths = tiny.$tag("th", _table);

            var _start = _ths.length - 2;
            var _end = _ths.length - 4;


            //if (_this.options.type == "soft") {
            //    for (var i = _start; i > _end; i--) {
            //        _ths[i].parentNode.removeChild(_ths[i]);
            //    }
            //    tiny.setCookie("environmentType", "soft", 1);
            //}
        },

        //
        addQueen: function (callback) {
            var _this = this;
            _this.cache.callback = callback;
        },

        //
        callBack: function () {
            var _this = this;
            if (_this.cache.lock == false) {
                _this.cache.callback;
                _this.cache.callback = function () { };
            } else {
                setTimeout(function () { _this.callBack() }, 100);
            }
        },

        //添加项
        addCondition: function (p, c, s) {
            var _this = this;
            var val = p + c;
            s = s || 0;

            //检查锁定
            if (_this.cache.lock == true) {
                addQueen(addCondition(p, c, s));
                return;
            }

            _this.hideChildMenu(p, c);

            if (tiny.indexOf(_this.cache.data.value, val) > -1
                || tiny.indexOf(_this.cache.data.value, "cz_" + val) > -1
                || tiny.indexOf(_this.cache.data.value, p.substr(3) + c) > -1) {
                return;
            } else {
                var state = _this.addCache(p, c);
                if (state) {
                    _this.addTabs(p, c);		// 设置选择痕迹
                    _this.addCols(p, c);		// 设置显示表格
                    _this.addHistory(p, c);	// 设置选择历史
                    _this.updateUrl(s);		// 更新地址栏、数据
                };
            }
        },
        openEdit: function (d, n) {
            tiny.$(d).style.display = "block";
            tiny.addClass(tiny.$(n), "edit_name");
        },
        closeEdit: function (d, n) {
            tiny.$(d).style.display = "none";
            tiny.removeClass(tiny.$(n), "edit_name");
        },
        editCondition: function (p, c, s) {

        },
        //隐藏子菜单
        hideChildMenu: function (p, c) {
            var _this = this;
            var show = _this.cache.show;

            if (!(typeof show == "undefined" || show == null || show == "" || show == p)) {
                tiny.$(show + "_childcondtions").style.display = "none";
                tiny.removeClass(tiny.$(show), "focus");

                //如果以选择本类下的3级子类
                if (tiny.indexOf(_this.cache.data.type, show) == -1
                    && tiny.indexOf(_this.cache.data.type, "cz_" + show) == -1
                    && tiny.indexOf(_this.cache.data.type, show.substr(3)) == -1) {
                    tiny.removeClass(tiny.$(show), "on");
                }
                _this.cache.show = null;
            }
        },

        //添加到缓存数组
        addCache: function (p, c) {
            var _this = this;
            var val = p + c;

            var elem = null;
            if (tiny.$(p))
                elem = tiny.$(p);
            else
                elem = tiny.$(p + c);
            var clf = (elem.getAttribute("clf") != null) ? elem.getAttribute("clf") : "";

            //单选模式
            var otype = false;
            if (tiny.$(p + "_def"))
                otype = (tiny.$(p + "_def").getAttribute("single") == "true") ? true : false;

            if (otype == true) {
                for (var i = _this.cache.data.type.length - 1; i >= 0; i--) {
                    var dataType = _this.cache.data.type[i];
                    if (p == dataType || "cz_" + p == dataType || p.substr(3) == dataType) {
                        var tmpP = _this.cache.data.type[i];
                        var re = new RegExp("^" + tmpP, "ig");
                        var c = _this.cache.data.value[i].replace(re, "");
                        _this.removeCondition(tmpP, c, 1);
                        break;
                    }
                }
            };

            //大类单选模式
            if (clf != "" && clf != "undefined") {
                for (var i = _this.cache.data.type.length - 1; i >= 0; i--) {
                    if (clf == _this.cache.data.calssFication[i]) {
                        var tmpP = _this.cache.data.type[i];
                        var re = new RegExp("^" + tmpP, "i");
                        var c = _this.cache.data.value[i].replace(re, "");
                        _this.removeCondition(tmpP, c, 2);
                        break;
                    }
                }
            };

            //检查筛选条件数
            if (_this.cache.data.value.length >= _this.options.max) {
                alert("您选择的筛选条件太多了，每次最多只能选" + _this.options.max + "项！");
                return false;
            }

            if (tiny.indexOf(_this.cache.data.type, p) == -1) {
                _this.removeDef(p);
            };

            var ptype = p.match(/^.*[a-z]+/i);
            _this.removeDef(ptype[0]);

            // 同类标签不重复传参
            for (var i = _this.cache.data.type.length - 1; i >= 0; i--) {
                var tmpP = _this.cache.data.type[i];
                if (_this.cache.data.value[i] == "cz_" + p + c || _this.cache.data.value[i] == p.substr(3) + c)
                    _this.removeCondition(tmpP, c, 1);
            }

            _this.cache.data.value.push(val);
            _this.cache.data.type.push(p);
            if (clf != "")
                _this.cache.data.calssFication.push(clf);
            else
                _this.cache.data.calssFication.push(val);
            return true;
        },

        //添加到选择列表
        addTabs: function (p, c) {
            if (tiny.$(p))
                tiny.addClass(tiny.$(p), "on");

            if (tiny.$(p + c))
                tiny.addClass(tiny.$(p + c), "on");
            else if (tiny.$(p + "_editLi"))// 自定义
                tiny.addClass(tiny.$(p + "_editLi"), "on");

            // 设置相同标签选中颜色
            if (tiny.$("cz_" + p))
                tiny.addClass(tiny.$("cz_" + p), "on");
            else if (tiny.$(p.substr(3)))
                tiny.addClass(tiny.$(p.substr(3)), "on");

            if (tiny.$("cz_" + p + c))
                tiny.addClass(tiny.$("cz_" + p + c), "on");
            else if (tiny.$((p + c).substr(3)))
                tiny.addClass(tiny.$((p + c).substr(3)), "on");
            else if (tiny.$("cz_" + p + "_editLi"))
                tiny.addClass(tiny.$("cz_" + p + "_editLi"), "on");
            else if (tiny.$(p.substr(3) + "_editLi"))
                tiny.addClass(tiny.$(p.substr(3) + "_editLi"), "on");


            //设置冲突项
            var elem = null;
            if (tiny.$(p))
                elem = tiny.$(p);
            else if (tiny.$(p + c))
                elem = tiny.$(p + c);

            var excid = elem.getAttribute("excid"),
                _this = this;

            if (!(excid == null || excid == "")) {
                var excids = excid.split(',');
                for (var i = 0, j = excids.length; i < j; i++) {
                    var id = excids[i];
                    var obj = tiny.$(id);
                    if (obj) {
                        if (tiny.indexOf(_this.cache.excids, id) > -1) {
                            // 已存在该冲突项
                            // 继续添加用于处理移除时的bug；
                            // 每个冲突项可能被多个选项对应
                            _this.cache.excids.push(id);
                            return;
                        }
                        var a = tiny.$tag("a", obj)[0];
                        tiny.addClass(a, "gray");
                        _this.cache.excids.push(id);
                    }
                }
            }
        },

        //添加选择历史
        addHistory: function (p, c, ptxt, val) {
            var _this = this;
            var hiscont = tiny.$("hiscont");
            var lastDiv = tiny.$("delall");
            var newDiv = tiny.$create("div");

            tiny.$("nohis").style.display = "none";
            tiny.$("delall").style.display = "block";

            hiscont.insertBefore(newDiv, lastDiv);
            newDiv.className = "condition";
            newDiv.id = p + c + "_his";

            var text = tiny.$create("span")
            newDiv.appendChild(text);
            text.className = "text";
            if (!tiny.$(p)) {
                text.innerHTML = tiny.getInnerText(tiny.$(p + c));
            } else {
                var unit = tiny.$(tiny.$(p)).getAttribute("unit");
                if (tiny.$(p + c)) {
                    text.innerHTML = tiny.getInnerText(tiny.$(p)) + "：" +
                    tiny.getInnerText(tiny.$(p + c));
                } else {
                    var str = "";
                    var paramC = _this.rTrim(c, ')');
                    paramC = _this.lTrim(paramC, '(');
                    var params = paramC.split(',');
                    for (var i = 0; i < params.length; i++) {
                        var elem = params[i].split('|');
                        var searchVal = elem[1].replace('w', '');
                        if (unit == "%")
                            searchVal = (searchVal * 100).toFixed(2);

                        switch (elem[0]) {
                            case "1":
                                str += "大于等于" + searchVal;
                                break;
                            case "2":
                                str += "小于等于" + searchVal;
                                break;
                            case "3":
                                str += "等于" + searchVal;
                                break;
                            case "4":
                                str += "大于等于" + searchVal;
                                break;
                            case "5":
                                str += "小于等于" + searchVal;
                                break;
                        }
                        if (unit != '-')
                            str += unit + " ";
                    }
                    text.innerHTML = tiny.getInnerText(tiny.$(p)) + "：" + str;
                }

                var val = p + c;
                if (p != 'hqzb05' && p != 'hqzb06' && p != 'hqzb11' && p != 'hqzb12' && p != 'hqzb13'
                    && val != 'jgcg01' && val != 'jgcg02' && val != 'jgcg03' && val != 'jgcg04' && val != 'jgcg05'
                    && val != 'jgcg06' && val != 'jgcg07' && val != 'jgcg08' && val != 'jgcg09' && val != 'jgcg10'
                    && val != 'jgcg11' && val != 'jgcg12' && val != 'jgcg13' && val != 'jgcg14'
                    && p != 'gfzs6' && p != 'gfzs7' && p != 'gfzs8'
                    && val != 'gdhs01' && val != 'gdhs02' && val != 'gdhs03') {
                    var admin = tiny.$create("span");
                    newDiv.appendChild(admin);
                    admin.className = "admin";
                    admin.innerHTML = "改";
                    var adminbox = tiny.$create("div");
                    var editboxStr = "<div><div class=\"edit_box\"><ul id=\"" + p + "_editBox_g\" class=\"editbody\"><li class=\"w45 unit\">单位：" + unit + "</li>";
                    editboxStr += "<ul class=\"editbody\"><li><ul class=\"editform\"><li class=\"w45\"><input class=\"edit_input\" id=\"" + p + "editBox_GT_g\" /></li><li class=\"w8\">-</li><li class=\"w45\"><input class=\"edit_input\" id=\"" + p + "editBox_LT_g\" /></li><li class=\"w40\"><div class=\"conform\" id=\"" + p + "editBox_T_enter_g\" onclick=\"customParam('" + p + "',1,'g','" + unit + "')\">确认</div></li>\</ul></li>";
                    editboxStr += "<li><ul class=\"editform\"><li class=\"w59\">大于等于:</li><li class=\"w45\"><input class=\"edit_input\" id=\"" + p + "editBox_GE_g\" /></li><li class=\"w40\"><div class=\"conform\" id=\"" + p + "editBox_GE_enter_g\" onclick=\"customParam('" + p + "',2,'g','" + unit + "')\">确认</div></li></ul></li>";
                    editboxStr += "<li>\<ul class=\"editform\">\<li class=\"w59\">小于等于:</li><li class=\"w45\">\<input class=\"edit_input\" id=\"" + p + "editBox_LE_g\" /></li>\<li class=\"w40\"><div class=\"conform\" id=\"" + p + "editBox_LE_enter_g\" onclick=\"customParam('" + p + "',3,'g','" + unit + "')\">确认</div></li></ul></li></ul>";
                    editboxStr += "</ul></div></div>";
                    adminbox.innerHTML = editboxStr;
                    adminbox.style.display = "none";
                    admin.appendChild(adminbox);
                    admin.onmouseover = function () {
                        adminbox.style.display = "block";
                    };
                    admin.onmouseout = function () {
                        adminbox.style.display = "none";
                    };
                }
            }
            var img = tiny.$create("img");
            newDiv.appendChild(img);
            img.src = "/static/img/east/close_1.gif";
            img.setAttribute("align", "absmiddle");
            img.onclick = function () {
                _this.removeCondition(p, c);
            }
            if (tiny.$tag("img", hiscont).length > 1) {
                var add = tiny.$create("b");
                hiscont.insertBefore(add, newDiv);
                add.className = "add";
                add.innerHTML = "+";
            }
        },

        //插入列
        insertCols: function (code, name, sortable) {
            var _this = this;
            var table = tiny.$("dataslist");
            var tbody = tiny.$tag("tbody", table)[0];
            var trs = tiny.$tag("tr", table);

            sortable = (sortable != false) ? true : false;

            if (tiny.$(_this.cache.sort.code + "_sort")) {
                tiny.$(_this.cache.sort.code + "_sort").src = portalurl+"static/img/east/sort.gif";
            }

            _this.cache.sort.code = code;
            _this.cache.sort.type = -1;


            if (!tiny.$(code + "_th")) {
                for (var i = 0, j = trs.length; i < j; i++) {
                    var tds;
                    if (i == 0) {
                        var tds = tiny.$tag("th", trs[0]);
                        var th = tiny.$create("th");

                        //if (_this.options.type == "soft") {
                        //    trs[0].insertBefore(th, tds[tds.length - 1]);
                        //} else {
                        trs[0].insertBefore(th, tds[tds.length - 3]);
                        //}

                        th.style.width = "";
                        th.id = code + "_th";

                        if (sortable) {
                            var a = tiny.$create("a");
                            th.appendChild(a);

                            a.setAttribute("href", "javascript:;");
                            a.innerHTML = name
                            + "<img src=\""+portalurl+"static/img/east/sort_down.gif\" id=\"" + code + "_sort\">";

                            a.onclick = function () {
                                _this.sort(code);
                                return false;
                            }

                            a.onfocus = function () {
                                this.blur();
                            }

                        } else {
                            th.innerHTML = name;
                        }
                    } else {
                        tds = tiny.$tag("td", trs[i]);
                        var td = tiny.$create("td");
                        td.innerHTML = "&nbsp;";

                        //if (_this.options.type == "soft") {
                        //    trs[i].insertBefore(td, tds[tds.length - 1]);
                        //} else {
                        trs[i].insertBefore(td, tds[tds.length - 3]);
                        //}

                    }
                }
            } else {
                tiny.$(code + "_sort").src = portalurl+"static/img/east/sort_down.gif";
            }
        },

        //添加到表格
        addCols: function (p, c) {
            var _this = this;
            var table = tiny.$("dataslist");
            var tbody = tiny.$tag("tbody", table)[0];
            var trs = tiny.$tag("tr", table);

            ////是否默认模式
            //var jmode = false;
            //if (tiny.$(p + c))
            //    jmode = (tiny.$(p + c).getAttribute("colsable") != "false") ? true : false;
            //else if (tiny.$(p + "_editLi"))// 自定义
            //    jmode = true;

            //if (!jmode) {
            //    _this.reBuildDefCols();
            //    return false;
            //} else {
            //    _this.removeCols("sprice");
            //    _this.removeCols("schange");
            //    _this.removeCols("spresent");
            //}

            for (var i = 0, j = trs.length; i < j; i++) {
                var tds;
                //标题
                if (i == 0) {
                    var tds = tiny.$tag("th", trs[0]);
                    var th = tiny.$create("th");
                    //if (_this.options.type == "soft") {
                    //    trs[0].insertBefore(th, tds[tds.length - 0]);
                    //} else {
                    trs[0].insertBefore(th, tds[tds.length - 1]);
                    //}

                    th.style.width = "";
                    th.id = p + c + "_th";

                    var elem = null;
                    if (tiny.indexOf(c, '(') == -1 && tiny.$(p + c))
                        elem = tiny.$(p + c);
                    else if (tiny.$(p))
                        elem = tiny.$(p);

                    var unit = (elem.getAttribute("unit") != null) ? elem.getAttribute("unit") : "";
                    var sortable = '';
                    var sorttype = '';
                    if (tiny.$(p + c)) {
                        sortable = (tiny.$(p + c).getAttribute("sortable") == "true") ? true : false;
                        sorttype = (tiny.$(p + c).getAttribute("sorttype") != null) ? tiny.$(p + c).getAttribute("sorttype") : -1;
                    } else if (tiny.$(p + "_editLi")) {
                        sortable = (tiny.$(p + "_editLi").getAttribute("sortable") == "true") ? true : false;
                        sorttype = (tiny.$(p + "_editLi").getAttribute("sorttype") != null) ? tiny.$(p + "_editLi").getAttribute("sorttype") : -1;
                    } else {
                        sortable = true;
                        sorttype = -1;
                    }

                    sorttype = (sorttype == "1" || sorttype == "0" || sorttype == "-1") ? parseInt(sorttype) : -1;

                    if (sortable) {
                        var a = tiny.$create("a");
                        th.appendChild(a);
                        a.setAttribute("href", "javascript:;");
                        a.onclick = function () {
                            sort(p + c);
                            return false;
                        }

                        a.onfocus = function () {
                            this.blur();
                        }

                        /*2010-10-14 新添加的可对比项默认为倒序排列 -2/2*/
                        if (tiny.$(_this.cache.sort.code + "_sort")) {
                            tiny.$(_this.cache.sort.code + "_sort").src = portalurl+"static/img/east/sort.gif";
                        }

                        _this.cache.sort.code = p + c;
                        _this.cache.sort.type = sorttype;

                        var html = tiny.getInnerText(elem);
                        if (unit == "" || unit == "-"
                            || p == 'gbzb01' || p == 'cz_gbzb01'
                            || p == 'gbzb02' || p == 'cz_gbzb02'
                            || p == 'gbzb03' || p == 'cz_gbzb03'
                            || p == 'cz19' || p == 'cz20')
                            html += "";
                        else
                            html += "(" + unit + ")";

                        if (sorttype == 1) {
                            html += "<img src=\""+portalurl+"static/img/east/sort_up.gif\" id=\"" + p + c + "_sort\">";;
                        } else if (sorttype == 0) {
                            html += "<img src=\""+portalurl+"static/img/east/sort.gif\" id=\"" + p + c + "_sort\">";;
                        } else {
                            html += "<img src=\""+portalurl+"static/img/east/sort_down.gif\" id=\"" + p + c + "_sort\">";;
                        }

                        a.innerHTML = html;
                        /* - end */
                    } else {
                        th.innerHTML = tiny.getInnerText(elem);
                        if (unit == "" || unit == "-"
                            || p == 'gbzb01' || p == 'cz_gbzb01'
                            || p == 'gbzb02' || p == 'cz_gbzb02'
                            || p == 'gbzb03' || p == 'cz_gbzb03'
                            || p == 'cz19' || p == 'cz20')
                            th.innerHTML += "";
                        else
                            th.innerHTML += "(" + unit + ")";
                    }
                } else {
                    tds = tiny.$tag("td", trs[i]);
                    var td = tiny.$create("td");
                    td.innerHTML = "&nbsp;--&nbsp;";
                    //if (_this.options.type == "soft") {
                    //    trs[i].insertBefore(td, tds[tds.length - 0]);
                    //} else {
                    trs[i].insertBefore(td, tds[tds.length - 1]);
                    //}
                }
            }
        },

        //检查选择历史
        checkChooseHis: function () {
            if (this.cache.data.value.length > 0) {
                tiny.$("nohis").style.display = "none";
                tiny.$("delall").style.display = "block";
            } else {
                tiny.$("nohis").style.display = "block";
                tiny.$("delall").style.display = "none";
            }
        },

        //移除条件
        //s=1:不需要隐藏childMenu,不需要更新数据，不检查当前类及父类的不限状态
        //s=2:不需要隐藏childMenu,不需要更新数据，不检查父类的不限状态
        //s=3:不需要更新数据
        removeCondition: function (p, c, s) {
            var _this = this;
            s = (s == null) ? 0 : s;

            //检查锁定
            if (_this.cache.lock == true) {
                addQueen(addCondition(p, c, s));
                return;
            }

            if (!(s == 1 || s == 2)) {
                //隐藏ChildMenu
                _this.hideChildMenu();
            }

            _this.removeCache(p, c, s);
            _this.removeHistory(p, c);
            _this.removeTabs(p, c);
            _this.removeCols(p + c);

            //检查是否回复到默认状态
            _this.reBuildDefCols();

            if (!(s == 1 || s == 2 || s == 3)) {
                _this.updateUrl();
            }
        },

        reBuildDefCols: function () {
            var _this = this;
            var def = true;
            for (var i = 0, j = _this.cache.data.value.length; i < j; i++) {
                if (tiny.$(_this.cache.data.value[i]) && tiny.$(_this.cache.data.value[i]).getAttribute("colsable") != "false") {
                    def = false;
                    break;
                }
            }
            //if (def) {
            //    _this.insertCols("sprice", "最新价(元)", true);
            //    _this.insertCols("schange", "涨跌(元)", true);
            //    _this.insertCols("spresent", "涨跌幅(%)", true);
            //}
        },

        removeCache: function (p, c, s) {
            // 先移除
            var _this = this;
            var index = tiny.indexOf(_this.cache.data.value, p + c);
            _this.cache.data.type = _this.cache.data.type.del(index);
            _this.cache.data.value = _this.cache.data.value.del(index);
            _this.cache.data.calssFication = _this.cache.data.calssFication.del(index);

            // 检查是当前类不限状态
            if (!(s == 1)) {
                //检查本类是否还有其他选项被选中
                if (tiny.indexOf(_this.cache.data.type, p) == -1) {
                    _this.addDef(p);
                    if (s != 1) {
                        var elem = null;
                        if (tiny.$(p))
                            elem = tiny.$(p);
                        else if (tiny.$(p + c))
                            elem = tiny.$(p + c);

                        tiny.removeClass(elem, "on");
                        tiny.removeClass(elem, "focus");

                        // 去除相同标签选中颜色
                        if (tiny.$("cz_" + p))
                            tiny.removeClass(tiny.$("cz_" + p), "on");
                        else if (tiny.$(p.substr(3)))
                            tiny.removeClass(tiny.$(p.substr(3)), "on");

                        if (tiny.$("cz_" + p + c))
                            tiny.removeClass(tiny.$("cz_" + p + c), "on");
                        else if (tiny.$((p + c).substr(3)))
                            tiny.removeClass(tiny.$((p + c).substr(3)), "on");
                    }
                }
            }


            var ptype = "";
            if (tiny.indexOf(p, "cz_") == -1)
                ptype = p.match(/[a-z]*/i)[0];
            else
                ptype = p.match(/_[a-z]*/i)[0].substr(1);
            // 检查父类的不限状态
            if (!(s == 1 || s == 2)) {
                //检查上级目录同级的子目录是否有选中
                if (_this.hasCousinChoose(ptype) == -1) {
                    _this.addDef(ptype);
                }
            }
        },

        hasCousinChoose: function (p) {
            var _this = this;
            var reg = new RegExp("^(cz_)?" + p + "[\\d]*", "ig");
            var index = -1;
            for (var i = 0, j = _this.cache.data.type.length; i < j; i++) {
                if (reg.test(_this.cache.data.type[i])) {
                    index = i;
                    break;
                }
            }
            return index;
        },

        removeHistory: function (p, c) {
            var str = p + c + "_his";
            if (tiny.$(str)) {
                var cont = tiny.$(str), obj = null;
                if (tiny.getNodePos(cont) == 0) {
                    obj = tiny.getNextNode(cont, "b");
                } else {
                    obj = tiny.getPrevNode(cont, "b");
                }
                if (obj != null && tiny.hasClass(obj, "add")) {
                    obj.parentNode.removeChild(obj);
                }
                cont.parentNode.removeChild(cont);
            }
            this.checkChooseHis();
        },

        //从选择列表移除指定项
        removeTabs: function (p, c) {
            var _this = this;

            if (tiny.$(p + c)) {
                tiny.removeClass(tiny.$(p + c), "on");

                // 去除相同标签选中颜色
                if (tiny.$("cz_" + p + c))
                    tiny.removeClass(tiny.$("cz_" + p + c), "on");
                else if (tiny.$((p + c).substr(3)))
                    tiny.removeClass(tiny.$((p + c).substr(3)), "on");
            } else if (tiny.$(p + "_editLi")) {
                tiny.removeClass(tiny.$(p + "_editLi"), "on");

                // 去除相同标签选中颜色
                if (tiny.$("cz_" + p + "_editLi"))
                    tiny.removeClass(tiny.$("cz_" + p + "_editLi"), "on");
                else if (tiny.$((p + "_editLi").substr(3)))
                    tiny.removeClass(tiny.$((p + "_editLi").substr(3)), "on");
            }

            var elem = null;
            if (tiny.$(p))
                elem = tiny.$(p);
            else if (tiny.$(p + c))
                elem = tiny.$(p + c);

            var excid = elem.getAttribute("excid");
            if (!(excid == null || excid == "")) {
                var excids = excid.split(',');
                for (var i = 0, j = excids.length; i < j; i++) {
                    var id = excids[i];
                    if (tiny.$(id)) {
                        var index = tiny.indexOf(_this.cache.excids, id);
                        if (index > -1) {
                            _this.cache.excids = _this.cache.excids.del(index);
                        }


                        // 修复程序
                        // 如果本冲突项是最后一项时回复冲突项的状态
                        if (tiny.indexOf(_this.cache.excids, id) == -1) {
                            var a = tiny.$tag("a", tiny.$(id))[0];
                            tiny.removeClass(a, 'gray');
                        }
                    }
                }
            }

        },

        //移除所有小类后,选中本类的 不限 p-类别
        addDef: function (p) {
            if (tiny.$(p + "_def"))
                tiny.addClass(tiny.$(p + "_def"), "on");

            // 相同标签的选择状态
            if (tiny.$("cz_" + p + "_def"))
                tiny.addClass(tiny.$("cz_" + p + "_def"), "on");
            else if (tiny.$(p.substr(3) + "_def"))
                tiny.addClass(tiny.$(p.substr(3) + "_def"), "on");
        },

        //选取小类后,去掉本类的 不限 p-类别
        removeDef: function (p) {
            if (tiny.$(p + "_def"))
                tiny.removeClass(tiny.$(p + "_def"), "on");

            // 除去相同标签的选择状态
            if (tiny.$("cz_" + p + "_def"))
                tiny.removeClass(tiny.$("cz_" + p + "_def"), "on");
            else if (tiny.$(p.substr(3) + "_def"))
                tiny.removeClass(tiny.$(p.substr(3) + "_def"), "on");
        },

        //从表格移除列
        removeCols: function (id) {
            var _this = this;
            var table = tiny.$("dataslist");
            var trs = tiny.$tag("tr", table);
            var index = -1;

            for (var i = 0, j = trs.length; i < j; i++) {
                var tds;
                //标题
                if (i == 0) {
                    tds = tiny.$tag("th", trs[i]);
                    var _end = (_this.options.type == 'soft') ? tds.length : tds.length - 1;
                    for (var k = 1, l = _end; k < l; k++) {
                        if (tds[k].id == id + "_th") {
                            index = k;
                            tds[index].parentNode.removeChild(tds[index]);
                            break;
                        }
                    }
                } else {
                    if (index == -1) {
                        break;
                    } else {
                        tds = tiny.$tag("td", trs[i]);
                        tds[index].parentNode.removeChild(tds[index]);
                    }
                }
            }
        },
        //显示子类条件 c-父类标识
        //当2级条件有3级子条件可选时调用
        showChildConditions: function (p, c) {
            // 判断是否被冲突
            var _this = this;
            var pId = p + c;
            var index = tiny.indexOf(_this.cache.excids, pId);
            if (index > -1) {
                return;
            }
            //判断是否已选中
            var _this = this;
            var show = _this.cache.show;


            if (!(typeof show == "undefined" || show == null || show == "" || show != pId)) return;

            var child_id = pId + "_childcondtions";
            var cache_id = pId + "_childcache";

            var li = tiny.$(pId);


            //标色
            tiny.addClass(li, "on");
            tiny.addClass(li, "focus");

            //移除其他同级的3级选项
            _this.hideChildMenu(p, c);

            //显示childMenu
            if (tiny.$(child_id)) {
                tiny.$(child_id).style.display = "block";
            } else {
                if (tiny.$(cache_id)) {

                    //获取下一行的第一个节点
                    var insert_li = _this.getInsertLi(li);

                    var new_li = tiny.$create("li");
                    //
                    if (insert_li == null) {
                        li.parentNode.appendChild(new_li);
                    } else {
                        li.parentNode.insertBefore(new_li, insert_li);
                    }
                    new_li.className = "listcont clearfix";
                    new_li.id = child_id;

                    var str = tiny.$(cache_id).innerHTML;

                    if (pId != "gfzs6" && pId != "gfzs7" && pId != "gfzs8" && pId != "hqzb05" && pId != "hqzb06" && pId != "hqzb11" && pId != "hqzb12" && pId != "hqzb13") {
                        var liClassName = "edit";
                        if ((tiny.indexOf(_this.cache.data.type, pId) != -1 && tiny.$(_this.cache.data.value[tiny.indexOf(_this.cache.data.type, pId)]) == null)
                            || (tiny.indexOf(_this.cache.data.type, "cz_" + pId) != -1 && tiny.$(_this.cache.data.value[tiny.indexOf(_this.cache.data.type, "cz_" + pId)]) == null))
                            liClassName += " on";

                        var unit = tiny.$(tiny.$(p + c)).getAttribute("unit");

                        str += "<li id=\"" + pId + "_editLi\" class=\"" + liClassName + "\" sortable=\"true\" onmouseout=\"closeEdit('" + pId + "_editBox','" + pId + "_editLink')\" onmouseover=\"openEdit('" + pId + "_editBox','" + pId + "_editLink')\" sorttype=\"1\">";
                        str += "<span class=\"clkSpan\" id=\"" + pId + "_editLink\">自定义</span>";
                        str += "<ul style=\"display: none;\" id=\"" + pId + "_editBox\" class=\"editbody\">";
                        str += "<li class=\"w45 unit\">单位：" + unit + "</li>";
                        str += "<li><ul class=\"editform\">";
                        str += "<li class=\"w45\"><input class=\"edit_input\" id=\"" + pId + "editBox_GT\" /></li><li class=\"w8\">-</li><li class=\"w45\"><input class=\"edit_input\" id=\"" + pId + "editBox_LT\" /></li><li class=\"w40\">";
                        str += "<div class=\"conform\" id=\"" + pId + "editBox_T_enter\" onclick=\"customParam('" + pId + "','1','z','" + unit + "')\">确认</div></li></ul></li><li><ul class=\"editform\"><li class=\"w59\">大于等于:</li><li class=\"w45\">";
                        str += "<input class=\"edit_input\" id=\"" + pId + "editBox_GE\" /></li><li class=\"w40\"><div class=\"conform\" id=\"" + pId + "editBox_GE_enter\" onclick=\"customParam('" + pId + "',2,'z','" + unit + "')\">确认</div></li></ul></li>";
                        str += "<li><ul class=\"editform\"><li class=\"w59\">小于等于:</li><li class=\"w45\"><input class=\"edit_input\" id=\"" + pId + "editBox_LE\" /></li><li class=\"w40\"><div class=\"conform\" id=\"" + pId + "editBox_LE_enter\" onclick=\"customParam('" + pId + "',3,'z','" + unit + "')\">确认</div></li></ul></li></ul></li>";
                    }
                    new_li.innerHTML = "<ul>" + str + "</ul>";
                }
            }
            _this.cache.show = pId;
        },

        //通过子类激活二级父类选中
        showChildMenu: function (p, c) {
            var state = false;
            if ((/^[a-zA-Z]+[\d]+/i).test(p) == true) {
                var re = new RegExp("^[a-zA-Z]+", "i")
                var tP = p.match(re);
                var tC = p.replace(tP, "");
                if ((/^[\d]+/i).test(tC) == true) {
                    this.showChildConditions(tP, tC);
                    state = true;
                }
            }
            return state;
        },

        //获取本行的最后一个节点
        getInsertLi: function (obj) {
            var offsetTop = tiny.rect(obj).top + obj.offsetHeight;
            var newOffsetTop = 0;
            var index = tiny.getNodePos(obj);
            var i = 0;
            while (newOffsetTop < offsetTop) {
                i++;
                obj = tiny.getNextNode(obj, "li");
                if (obj == null) break;
                newOffsetTop = tiny.rect(obj).top;
            }
            return obj;
        },

        //清除同类所有条件
        clearType: function (p) {
            //是否是二级目录
            var _this = this;

            //移除所有同类节点
            var reg = null;
            if (tiny.indexOf(p, "cz_") == -1)
                reg = new RegExp("^(cz_)?" + p + "[\\d]*$", "i");
            else
                reg = new RegExp("^(cz_)?" + p.substr(3) + "[\\d]*$", "i");
            for (var i = _this.cache.data.type.length - 1; i >= 0; i--) {
                if (reg.test(_this.cache.data.type[i])) {
                    var tmpP = _this.cache.data.type[i];
                    var re = new RegExp("^" + tmpP, "i");
                    var c = _this.cache.data.value[i].replace(re, "");
                    _this.removeCondition(tmpP, c, 3);
                }
            }

            _this.updateUrl();
        },

        //--清除所有条件
        clearAllConditions: function () {
            var _this = this;
            for (var i = _this.cache.data.type.length - 1; i >= 0; i--) {
                var p = _this.cache.data.type[i];
                var re = new RegExp("^" + p, "ig");
                var c = _this.cache.data.value[i].replace(re, "");
                _this.removeCondition(p, c, 3);
            }
            _this.initSortCache();
            _this.updateUrl();

        },

        //构建url
        updateUrl: function (s) {
            var _this = this;
            s = s || 0;
            var url = location.href;
            var index = (url.indexOf("#") == -1) ? url.length : url.indexOf("#");
            var def = url.substring(0, index)
            var str = "";
            for (var i = 0, j = _this.cache.data.type.length; i < j; i++) {
                var p = _this.cache.data.type[i];
                var re = new RegExp("^" + p, "ig");
                var c = _this.cache.data.value[i].replace(re, "");
                str += "[" + p + c + "]";
            }
            if (str != "") {
                var b = new Base64();
                //添加排序规则
                str = "c=" + str;
                if (_this.cache.sort.code != "" && (_this.cache.sort.type == -1 || _this.cache.sort.type == 1)) {
                    if (tiny.indexOf(_this.cache.data.value, _this.cache.sort.code) > -1 || _this.cache.sort.code == "stockcode"
                        || _this.cache.sort.code == "sprice" || _this.cache.sort.code == "spresent" || _this.cache.sort.code == "schange") {
                        str += "|s=" + _this.cache.sort.code + "|st=" + _this.cache.sort.type;
                    } else {
                        _this.initSortCache();
                    }
                }
                document.location.href = def + "#" + b.encode(str);
            } else {
                document.location.href = def + "#def";
            }

            _this.initPageCache();

            if (s == 0) {
                _this.dataUpdate();
            }

        },

        debugClear: function () {
            if (this.options.debug == true) {
                tiny.$("debug1").innerHTML = "-";
                tiny.$("debug2").innerHTML = "-";
                tiny.$("debug3").innerHTML = "-";
                tiny.$("debug4").innerHTML = "-";
                tiny.$("debug5").innerHTML = "-";
                tiny.$("debug6").innerHTML = "-";
                tiny.$("debug7").innerHTML = "-";
                tiny.$("debug8").innerHTML = "-";
            }
        },

        dataUpdate: function () {
            var _this = this;

            if (_this.options.debug == true) {
                _this.debugClear();
                tiny.$("debug1").innerHTML = _this.cache.data.value.toString();
            }

            if (_this.cache.data.type.length == 0) {
                _this.defMode();
                return;
            }
            var tmpUrl = _this.options.dataurl;
            var page = _this.cache.page.page;
            var jsname = _this.getCode(8);
            //jsname = "jn";
            //alert(jsname);
            _this.cache.code = jsname;
            var tmpArr = "";
            for (var i = 0; i < _this.cache.data.value.length; i++) {
                tmpArr += "[" + _this.cache.data.value[i] + "]";
            }
            var dataurl = tmpUrl.replace("{codes}", tmpArr);
            dataurl = dataurl.replace("{page}", page);
            dataurl = dataurl.replace("{jn}", jsname);
            dataurl = dataurl.replace("{ps}", _this.options.pageSize);


            if (_this.cache.sort.code != "" && (_this.cache.sort.type == 1 || _this.cache.sort.type == -1 || _this.cache.sort.type == 0)) {
                dataurl = dataurl + "&s=" + _this.cache.sort.code;
                dataurl = dataurl + "&st=" + _this.cache.sort.type;
            }

            dataurl = dataurl + "&r=" + new Date().getTime();

            var t1 = new Date();

            if (_this.options.debug == true) {
                tiny.$("debug1").innerHTML += " <BR /><font color=\"#008000\">请求地址：</font><a href=\"" + dataurl + "\" target=\"_blank\">" + dataurl + "</a>";
                tiny.$("debug2").innerHTML = t1.format("HH:mm:ss .ms");
            }

            setTimeout(function () {
                var loading = true;
                var timeout = 0;
                tiny.$("loading").innerHTML = "正在请求数据...";
                //tiny.$("loading").style.display = "";
                tiny.$("loading").className = "loading";
                clearInterval(_this.cache.watch);
                _this.cache.watch = setInterval(function () {
                    timeout += 200;
                    if (loading == false) {
                        clearInterval(_this.cache.watch);
                        tiny.$("loading").style.display = "none";
                    } else {
                        if (timeout >= 500) {
                            tiny.$("loading").style.display = "";
                        }
                        if (timeout >= 6000 && timeout < 12000) {
                            tiny.$("loading").innerHTML = "仍在继续...";
                        } else if (timeout >= 12000 && timeout < 18000) {
                            tiny.$("loading").innerHTML = "还在努力...";
                        } else if (timeout >= 18000) {
                            clearInterval(_this.cache.watch);
                            if (loading == true) {
                                tiny.$("loading").className = "loading err";
                                tiny.$("loading").innerHTML = "请求超时，数据加载失败...&nbsp;&nbsp;<span class=\"clkSpan\" onclick=\"window.location.reload()\">刷新?</span>";
                                setTimeout(function () {
                                    tiny.$("loading").style.display = "none";
                                }, 120000);
                            } else {
                                tiny.$("loading").style.display = "none";
                            }
                        }
                    }
                }, 200);
                tiny.loadJs(dataurl, {
                    charset: "utf-8", callback: function (_js) {
                        //alert(jsname);
                        if (!(eval("typeof " + jsname) == "undefined" || eval(jsname) == null || eval(jsname) == "" ||
                            eval("typeof " + jsname + ".Results") == "undefined" || eval(jsname + ".Results") == null)) {
                            /*
                             if(!(typeof jn=="undefined" || jn==null || jn=="" ||
                             typeof jn.Results=="undefined" || jn.Results==null)){
                             */
                            var jn = eval(jsname);

                            var t2 = new Date();

                            if (_this.options.debug == true) {
                                tiny.$("debug3").innerHTML = t2.format("HH:mm:ss .ms");
                                tiny.$("debug4").innerHTML = (t2.getTime() - t1.getTime()) + "ms.";
                                tiny.$("debug5").innerHTML = jn.TimeOut + "ms.";
                                tiny.$("debug7").innerHTML = (t2.getTime() - t1.getTime() - jn.TimeOut) + "ms.";

                                tiny.$("debug8").innerHTML = "<table cellspacing=\"0\" cellpadding=\"0\">"
                                + "<tr><td width=\"50\" align=right><font color=\"#003366\"> Results</font></td><td>&nbsp;" + jn.Results.join('<br>')
                                + "</td></tr><tr><td align=right><font color=\"#003366\"> AllCount</font></td><td>&nbsp;" + jn.AllCount
                                + "</td></tr><tr><td align=right><font color=\"#003366\"> PageCount</font></td><td>&nbsp;" + jn.PageCount
                                + "</td></tr><tr><td align=right><font color=\"#003366\"> AtPage</font></td><td>&nbsp;" + jn.AtPage
                                + "</td></tr><tr><td align=right><font color=\"#003366\"> PageSize</font></td><td>&nbsp;" + jn.PageSize
                                + "</td></tr><tr><td align=right><font color=\"#003366\"> ErrMsg</font></td><td>&nbsp;" + jn.ErrMsg
                                + "</td></tr><tr><td align=right><font color=\"#003366\"> TimeOut</font></td><td>&nbsp;" + jn.TimeOut
                                + "</td></tr></table>";
                            }

                            //本次判断无意义
                            if (jsname != _this.cache.code) {
                                if (_this.options.debug == true) {
                                    tiny.$("debug3").innerHTML += " <BR />请求状态：<font color=\"#484848\">本次请求已过期，请等待新数据！</font>";
                                }
                                return;
                            }

                            if (jn.Results != "") {
                                _this.cache.data.data = jn.Results;
                                _this.cache.data.sign = "" + jn;

                                _this.cache.page.page = jn.AtPage;
                                _this.cache.page.pages = jn.PageCount;
                                _this.cache.page.totle = jn.AllCount;

                                _this.cache.data.update = jn.UpdateTime;

                                loading = false;
                                setTimeout(function () {
                                    _this.dataDisplay();
                                }, 0)

                            } else {
                                if (_this.options.debug == true) {
                                    tiny.$("debug3").innerHTML += " <BR />请求状态：<font color=\"#484848\">请求完成,且返回数据正常，但没有取到有效的列表数据(该条件下无结果?)。</font>";
                                }
                                loading = false;
                                _this.noDataMode();
                            }
                        } else {
                            if (_this.options.debug == true) {
                                tiny.$("debug3").innerHTML += " <BR />请求状态：<font color=\"#484848\">没有返回有效数据！</font>";
                            }
                            loading = false;
                            _this.noDataMode();
                        }

                    }
                });
            }, 0);
        },

        dataDisplay: function (nums) {
            var _this = this;
            nums = (nums === undefined) ? 0 : nums;
            // 锁定
            _this.cache.locked = true;

            var table = tiny.$("dataslist"),
                trs = tiny.$tag("tr", table),
                _datas = _this.cache.data.data;
            //先删除
            var index = -1;

            //alert(_this.cache.data.data);
            for (var i = trs.length - 1; i > 0; i--) {
                trs[i].parentNode.removeChild(trs[i]);
            }
            //alert(jn);
            //alert("检查移除情况！");
            //添加
            //var tmpDatas = _this.cache.data.data;
            var cells = tiny.$tag("th", trs[0]).length;
            var tdcells = cells;

            tdcells -= 1;

            if (_datas.length > 0) {
                _this.getDataMode();
            } else {
                _this.noDataMode();
                return false;
            }

            if (_datas[0].split(',').length - 1 != tdcells) {
                //		    alert("数据格式有误，请刷新重新尝试！\n当前地址:" + href + "\n 当前数据长度:" +
                //					_datas[0].split(',').length + " \n当前表格长度(-3):" + tdcells +
                //					"\n" + _datas.join("<br>"));
                if (nums < 1) {
                    //回调一下
                    _this.dataDisplay(1);
                } else {
                    alert("数据加载失败，请刷新页面重新尝试!");
                    //location.reload();
                }

            } else {
                var str = "";
                var index = -1;
                if (_this.cache.sort.code != "" && (_this.cache.sort.type == -1 || _this.cache.sort.type == 1)) {
                    //alert(_this.cache.sort.code + " " + _this.cache.sort.type);
                    try {
                        index = tiny.getNodePos(tiny.$(_this.cache.sort.code + "_th"));
                    } catch (e) { };
                }
                //alert(index);

                var t = new Date().getTime();

                var ieMode = (tiny.browser.ie) ? true : false;
                //alert(ieMode);
                if (ieMode == false) {
                    var err = 0;
                    for (var i = 0; i < _datas.length; i++) {
                        try {
                            var tmpVals = _datas[i].split(',');
                            var code = tmpVals[1];
                            var codes = tmpVals[0] + "|" + code;
                            var market = (tmpVals[0] == 1) ? "sh" : "sz";
                            var name = tmpVals[2];
                            var link = portalurl + "stockeast/detail?stockCode=" + code;
                            var softlink = "href=\"javascript:;\" " + click + "=\"return(false &amp;&amp; [].push('skip.html#type=01&amp;code=" + market + code + "'));\""
                            var guba = "http://guba.eastmoney.com/topic," + code + ".html";
                            var fav = portalurl + "stockeast/addstock?code=" + code + "";
                            var color = (i % 2) == 0 ? "#ffffff" : "#f2f2f2";

                            str += "<tr onmouseover=\"this.bgColor='#F5FAFC'\" onmouseout=\"this.bgColor='" + color + "'\" bgcolor=\"" + color + "\">";

                            if (_this.options.type != "soft") {
                                if (index == 0) {
                                    str += "<td class=\"code\" bgcolor=\"#F5FAFC\">&nbsp;<a href=\"" + link + "\" target=\"_self\">" + code + "</a>&nbsp;</td>"
                                } else {
                                    str += "<td class=\"code\">&nbsp;<a href=\"" + link + "\" target=\"_self\">" + code + "</a>&nbsp;</td>"
                                }
                                str += "<td class=\"name\" nowrap=\"true\">&nbsp;<a href=\"" + link + "\" target=\"_self\">" + name + "</a>&nbsp;</td>"
                            } else {
                                if (index == 0) {
                                    str += "<td class=\"code\" bgcolor=\"#F5FAFC\">&nbsp;<a " + softlink + "\" target=\"_self\">" + code + "</a>&nbsp;</td>"
                                } else {
                                    str += "<td class=\"code\">&nbsp;<a " + softlink + "\" target=\"_self\">" + code + "</a>&nbsp;</td>"
                                }
                                str += "<td class=\"name\" nowrap=\"true\">&nbsp;<a " + softlink + "\" target=\"_self\">" + name + "</a>&nbsp;</td>"
                            }
                            for (var j = 3; j < tmpVals.length; j++) {
                                var value = tmpVals[j];
                                var unit = '';
                                if (tiny.$(_this.cache.data.type[j - 3])) {
                                    unit = tiny.$(tiny.$(_this.cache.data.type[j - 3])).getAttribute("unit");
                                } else {
                                    unit = tiny.$(tiny.$(_this.cache.data.value[j - 3])).getAttribute("unit");
                                }

                                var reg = new RegExp("\\(\\d+\\/\\d+\\/\\d+\\)$");
                                var valDate = value.match(reg);
                                var tsType = _this.cache.data.type[j - 3];
                                if (valDate != null) {
                                    var dataVal = value.replace(valDate, '');
                                    if (tsType == 'ggxg' || tsType == 'cz_ggxg') {
                                        if (_this.options.type != "soft") {
                                            value = '<a href="http://data.eastmoney.com/notices/stock/' + code + '.html" target="_blank">公告</a>';
                                        }
                                        else {
                                            value = '<a href="http://data.eastmoney.com/soft_new/notice/noticestock.aspx?stockcode=' + code + '" target="_self">公告</a>';
                                        }
                                    } else if (tsType == 'gbzb01' || tsType == 'cz_gbzb01'
                                        || tsType == 'gbzb02' || tsType == 'cz_gbzb02'
                                        || tsType == 'gbzb03' || tsType == 'cz_gbzb03'
                                        || tsType == 'cz19' || tsType == 'cz20') {
                                        dataVal = _this.getcolor_JS(dataVal, "w", dataVal);
                                    } else if (tsType == 'ylnl04') {
                                        if (dataVal == '0' || dataVal == '1')
                                            dataVal = '-';
                                        else
                                            dataVal = (dataVal * 100).toFixed(2);
                                    } else if (tsType == 'cz_gzzb01' || tsType == 'gzzb01') {
                                        if (dataVal <= 0)
                                            dataVal = '-';
                                    } else if (tsType != 'gbzb06' && tsType != 'gbzb08') {
                                        if (unit && tiny.indexOf(unit, '%') != -1 && tsType != 'hqzb11'
                                            && tsType != 'hqzb12' && tsType != 'hqzb13')
                                            dataVal = (dataVal * 100).toFixed(2);
                                        else if (unit && tiny.indexOf(unit, '万') != -1)
                                            dataVal = (dataVal / 10000).toFixed(2);
                                    }

                                    var quarte = valDate[0].replace(/[\\(\\)]/ig, ""),
                                        txt = "一";
                                    if (tiny.indexOf(quarte, '6/30') != -1) {
                                        txt = "二";
                                    } else if (tiny.indexOf(quarte, '9/30') != -1) {
                                        txt = "三";
                                    } else if (tiny.indexOf(quarte, '12/31') != -1) {
                                        txt = "四";
                                    }
                                    value = "<span title=\"" + quarte + "\">" + dataVal + "</span>";
                                } else {
                                    if (tsType == 'ggxg' || tsType == 'cz_ggxg') {
                                        if (_this.options.type != "soft") {
                                            value = '<a href="http://data.eastmoney.com/notices/stock/' + code + '.html" target="_blank">公告</a>';
                                        }
                                        else {
                                            value = '<a href="http://data.eastmoney.com/soft_new/notice/noticestock.aspx?stockcode=' + code + '" target="_self">公告</a>';
                                        }
                                    } else if (tsType == 'gbzb01' || tsType == 'cz_gbzb01'
                                        || tsType == 'gbzb02' || tsType == 'cz_gbzb02'
                                        || tsType == 'gbzb03' || tsType == 'cz_gbzb03'
                                        || tsType == 'cz19' || tsType == 'cz20') {
                                        value = _this.getcolor_JS(value, "w", value);
                                    } else if (tsType == 'ylnl04') {
                                        if (tmpVals[j] == '0' || tmpVals[j] == '1')
                                            value = '-';
                                        else
                                            value = (tmpVals[j] * 100).toFixed(2);
                                    } else if (tsType == 'cz_gzzb01' || tsType == 'gzzb01') {
                                        if (tmpVals[j] <= 0)
                                            value = '-';
                                    } else if (tsType != 'gbzb06' && tsType != 'gbzb08') {
                                        if (unit && tiny.indexOf(unit, '%') != -1 && tsType != 'hqzb11'
                                            && tsType != 'hqzb12' && tsType != 'hqzb13')
                                            value = (tmpVals[j] * 100).toFixed(2);
                                        else if (unit && tiny.indexOf(unit, '万') != -1)
                                            value = (tmpVals[j] / 10000).toFixed(2);
                                    }
                                }

                                if (j == (index + 1)) {
                                    str += "<td bgcolor=\"#F5FAFC\" >&nbsp;" + value + "</td>";
                                } else {
                                    str += "<td>&nbsp;" + value + "</td>";
                                }
                            }

                            //非软件版加上自选股等
                            if (_this.options.type != "soft") {
                                str += "<td class=\"fav\">";
                                str += "<a href=\"" + fav + "\"  target=\"_blank\"><img src=\"/static/img/east/addfav.gif\"  title=\"将" + name + "添加为自选股\" class=\"selector_comp\" /></a></td>";
                            }
                            else {
                                str += "<td class=\"fav\">";
                                str += "<a target=\"_self\" href=\"javascript:;\" " + click + "=\"return(false &amp;&amp; [].push('skip.html#type=02&amp;codes=" + market + code + "'));\" ><img src=\"/images/stock_choice/addfav.gif\"  title=\"将" + name + "添加为自选股\" class=\"selector_comp\" /></a></td>"
                            }
                            str += "</tr>";
                        } catch (e) {
                            err++;
                        }
                    }
                    tiny.$tag("tbody", table)[0].innerHTML = str;
                    if (err > 0) {
                        alert("数据显示完毕,但有" + err + "条数据未能有效显示！")
                    }
                } else {
                    var body = tiny.$tag("tbody", table)[0];
                    var rowTp = body.insertRow(-1);
                    for (var i = 0; i < cells; i++) {
                        var cell = rowTp.insertCell(i);
                        cell.innerHTML = i;
                    }
                    var err = 0;
                    for (var i = 0; i < _datas.length; i++) {
                        try {
                            var row = rowTp.cloneNode(true);
                            body.appendChild(row);

                            var tmpVals = _datas[i].split(',');
                            var code = tmpVals[1];
                            var codes = tmpVals[0] + "|" + code;
                            var market = (tmpVals[0] == 1) ? "sh" : "sz";
                            var name = tmpVals[2];
                            var link = "http://quote.eastmoney.com/" + market + code + ".html";
                            var softlink = "href=\"javascript:;\" " + click + "=\"return(false &amp;&amp; [].push('skip.html#type=01&amp;code=" + market + code + "'));\""
                            var guba = "http://guba.eastmoney.com/topic," + code + ".html";
                            var fav = "http://quote.eastmoney.com/favor/infavor.aspx?code=" + code + "";
                            var color = (i % 2) == 0 ? "#f2f2f2" : "#ffffff";

                            row.style.background = color;
                            row.onmouseover = function () {
                                this.bgColor = '#F5FAFC';
                            }
                            row.onmouseout = function () {
                                this.bgColor = color;
                            }

                            for (var j = 0; j < cells; j++) {
                                var cell = row.cells[j];
                                if (j == 0) {
                                    cell.innerHTML = (_this.options.type != 'soft')
                                        ? "&nbsp;<a href=\"" + link + "\" target=\"_blank\">" + code + "</a>&nbsp;&nbsp;"
                                        : ("&nbsp;" + "<a " + softlink + "\" target=\"_self\">" + code + "</a>" + "&nbsp;");
                                } else if (j == 1) {
                                    cell.innerHTML = (_this.options.type != 'soft')
                                        ? "&nbsp;<a href=\"" + link + "\" target=\"_blank\">" + name + "</a>&nbsp;&nbsp;"
                                        : ("&nbsp;" + "<a " + softlink + "\" target=\"_self\">" + name + "</a>" + "&nbsp;");
                                } else if (j == cells - 1) {
                                    cell.innerHTML = (_this.options.type != 'soft') ? (" <a href=\"" + fav + "\"  target=\"_blank\"><img src=\"/images/stock_choice/addfav.gif\"  title=\"将" + name + "添加为自选股\" class=\"selector_comp\" /></a>") : (" <a target=\"_self\" href=\"javascript:;\" " + click + "=\"return(false &amp;&amp; [].push('skip.html#type=02&amp;codes=" + market + code + "'));\" ><img src=\"/images/stock_choice/addfav.gif\"  title=\"将" + name + "添加为自选股\" class=\"selector_comp\" /></a> ");
                                }
                                //else if (j == cells - 1 && _this.options.type != 'soft') {
                                //    cell.innerHTML = "<a href=\"" + guba + "\" target=\"_blank\">股吧</a>"
                                //}
                                //else if (j == cells - 1) {
                                //    cell.innerHTML = "<img class=\"selector_comp\" src=\"/images/stock_choice/comp_btn.gif\" title=\"将" + name + "添加到对比工具栏\" onclick=\"addCompare('" + codes + "','" + name + "')\" />"
                                //}
                                else {
                                    var value = tmpVals[(j + 1)];
                                    var unit = '';
                                    if (tiny.$(_this.cache.data.type[j - 2])) {
                                        unit = tiny.$(tiny.$(_this.cache.data.type[j - 2])).getAttribute("unit");
                                    } else {
                                        unit = tiny.$(tiny.$(_this.cache.data.value[j - 2])).getAttribute("unit");
                                    }

                                    var reg = new RegExp("\\(\\d+\\/\\d+\\/\\d+\\)$");
                                    var valDate = value.match(reg);
                                    var tsType = _this.cache.data.type[j - 2];
                                    if (valDate != null) {
                                        var dataVal = value.replace(valDate, '');
                                        if (tsType == 'ggxg' || tsType == 'cz_ggxg') {
                                            if (_this.options.type != "soft") {
                                                value = '<a href="http://data.eastmoney.com/notices/stock/' + code + '.html" target="_blank">公告</a>';
                                            }
                                            else {
                                                value = '<a href="http://data.eastmoney.com/soft_new/notice/noticestock.aspx?stockcode=' + code + '" target="_self">公告</a>';
                                            }
                                        } else if (tsType == 'gbzb01' || tsType == 'cz_gbzb01'
                                            || tsType == 'gbzb02' || tsType == 'cz_gbzb02'
                                            || tsType == 'gbzb03' || tsType == 'cz_gbzb03'
                                            || tsType == 'cz19' || tsType == 'cz20') {
                                            dataVal = _this.getcolor_JS(dataVal, "w", dataVal);
                                        } else if (tsType == 'ylnl04') {
                                            if (dataVal == '0' || dataVal == '1')
                                                dataVal = '-';
                                            else
                                                dataVal = (dataVal * 100).toFixed(2);
                                        } else if (tsType == 'cz_gzzb01' || tsType == 'gzzb01') {
                                            if (dataVal <= 0)
                                                dataVal = '-';
                                        } else if (tsType != 'gbzb06' && tsType != 'gbzb08') {
                                            if (unit && tiny.indexOf(unit, '%') != -1 && tsType != 'hqzb11'
                                                && tsType != 'hqzb12' && tsType != 'hqzb13')
                                                dataVal = (dataVal * 100).toFixed(2);
                                            else if (unit && tiny.indexOf(unit, '万') != -1)
                                                dataVal = (dataVal / 10000).toFixed(2);
                                        }

                                        var quarte = valDate[0].replace(/[\\(\\)]/ig, ""),
                                            txt = "(" + "一" + ")";;
                                        if (tiny.indexOf(quarte, '6/30') != -1) {
                                            txt = "(" + "二" + ")";;
                                        } else if (tiny.indexOf(quarte, '9/30') != -1) {
                                            txt = "(" + "三" + ")";;
                                        } else if (tiny.indexOf(quarte, '12/31') != -1) {
                                            txt = "(" + "四" + ")";
                                        }
                                        value = "<span title=\"" + quarte + "\">" + dataVal + "</span>";
                                    } else {
                                        if (tsType == 'ggxg' || tsType == 'cz_ggxg') {
                                            if (_this.options.type != "soft") {
                                                value = '<a href="http://data.eastmoney.com/notices/stock/' + code + '.html" target="_blank">公告</a>';
                                            }
                                            else {
                                                value = '<a href="http://data.eastmoney.com/soft_new/notice/noticestock.aspx?stockcode=' + code + '" target="_self">公告</a>';
                                            }
                                        } else if (tsType == 'gbzb01' || tsType == 'cz_gbzb01'
                                            || tsType == 'gbzb02' || tsType == 'cz_gbzb02'
                                            || tsType == 'gbzb03' || tsType == 'cz_gbzb03'
                                            || tsType == 'cz19' || tsType == 'cz20') {
                                            value = _this.getcolor_JS(value, "w", value);
                                        } else if (tsType == 'ylnl04') {
                                            if (value == '0' || value == '1')
                                                value = '-';
                                            else
                                                value = (value * 100).toFixed(2);
                                        } else if (tsType == 'cz_gzzb01' || tsType == 'gzzb01') {
                                            if (value <= 0)
                                                value = '-';
                                        } else if (tsType != 'gbzb06' && tsType != 'gbzb08') {
                                            if (unit && tiny.indexOf(unit, '%') != -1 && tsType != 'hqzb11'
                                                && tsType != 'hqzb12' && tsType != 'hqzb13')
                                                value = (value * 100).toFixed(2);
                                            else if (unit && tiny.indexOf(unit, '万') != -1)
                                                value = (value / 10000).toFixed(2);
                                        }
                                    }
                                    cell.innerHTML = "&nbsp;" + value;
                                }
                                if (j == index)
                                    cell.style.backgroundColor = "#F5FAFC";
                            }
                        } catch (e) {
                            err++;
                        }
                    }
                    body.removeChild(rowTp);
                    if (err > 0) {
                        alert("数据显示完毕,但有" + err + "条数据未能有效显示！")
                    }
                }
                var t2 = new Date().getTime();

                if (_this.options.debug == true) {
                    tiny.$("debug6").innerHTML = (t2 - t) + "ms.";
                }
                _this.pageDisplay();
            }

            // 解锁
            _this.cache.locked = false;
        },

        //初始化分页，排序，状态显示等
        initPageCache: function () {
            var _this = this;
            _this.cache.page.page = 1;
            _this.cache.page.pages = 1;
            _this.cache.page.totle = 0;
            //updateInfo();
        },

        initSortCache: function () {
            var _this = this;
            _this.cache.sort.code = "";
            _this.cache.sort.type = -1;
        },

        //更新数据状态
        updateInfo: function () {
            var _this = this;
            if (_this.cache.data.type.length == 0) {
                tiny.$("info_count").innerHTML = "";
                tiny.$("info_page").innerHTML = "(您暂无选择证券)";

                _this.defMode(false);
            } else {
                tiny.$("info_count").innerHTML = "选出股票数：<b>" + _this.cache.page.totle + "</b>";
                tiny.$("info_page").innerHTML = "";
                //if (_this.cache.page.totle == 0) {
                //    tiny.$("info_page").innerHTML = "(第0个证券,当前" + _this.cache.page.page
                //                                  + "/" + _this.cache.page.pages + "页)";
                //} else {
                //    var start = (_this.cache.page.page - 1) * _this.options.pageSize + 1;
                //    var end = _this.cache.page.page * _this.options.pageSize;
                //    end = (end > _this.cache.page.totle) ? _this.cache.page.totle : end;
                //    tiny.$("info_page").innerHTML = "(第" + start + "-" + end + "个证券,当前"
                //                                    + _this.cache.page.page + "/" + _this.cache.page.pages + "页)";
                //}
            }
        },

        //分页显示
        pageDisplay: function () {
            var _this = this;
            var p = _this.cache.page.page;
            var pages = _this.cache.page.pages;

            var pagestr = "";
            if (p == 1) {
                pagestr = "<a href=\"javascript:;\" onclick=\"return false;\" target=\"_self\" class=\"nolink\">首页</a>"
            } else {
                pagestr = "<a href=\"javascript:;\" onclick=\"goToPage(1)\" target=\"_self\" title=\"转到首页\">首页</a>"
            }

            var start = (p > 3) ? p - 2 : 1;
            start = (p > pages - 3 && pages > 4) ? pages - 4 : start;
            var end = (start == 1) ? 5 : start + 4;

            end = (end > pages) ? pages : end;
            if (start > 1) {
                var pre = ((start - 3) < 1) ? 1 : (start - 3);
                pagestr += "<a href=\"javascript:;\" onclick=\"goToPage(" + pre + ")\" class=\"next\" target=\"_self\"  title=\"转到上一组\">...</a>";
            }
            for (var i = start; i <= end; i++) {
                if (p == i) {
                    pagestr += "<span class=\"at\">" + i + "</span>";
                } else {
                    pagestr += "<a href=\"javascript:;\" onclick=\"goToPage(" + i + ")\" target=\"_self\" title=\"转到第" + i + "页\">" + i + "</a>";
                }
            }
            if (pages > end) {
                var next = ((end + 3) > pages) ? pages : (end + 3);
                pagestr += "<a href=\"javascript:;\" onclick=\"goToPage(" + next + ")\" target=\"_self\" class=\"next\"  title=\"转到下一组\">...</a>";
            }
            if (p == pages) {
                pagestr += "<a href=\"javascript:;\" onclick=\"return false;\" target=\"_self\" class=\"nolink\">末页</a>"
            } else {
                pagestr += "<a href=\"javascript:;\" onclick=\"goToPage(" + pages + ")\" target=\"_self\" title=\"转到最后一页\">末页</a>"
            }

            tiny.$("toppage").innerHTML = pagestr;
            tiny.$("bottompage").innerHTML = pagestr;

        },

        //页码跳转
        goToPage: function (page) {
            var _this = this;
            if (page < 1) page = 1;
            if (page == _this.cache.page.page) return;
            if (page > _this.cache.page.pages) page = _this.cache.page.pages;
            _this.cache.page.page = page;
            _this.dataUpdate();

        },

        //切换到无数据模式
        noDataMode: function () {
            var _this = this;
            var selectedCondition = tiny.$("selectorConditions");
            selectedCondition.innerHTML = "";

            tiny.$("defMode").style.display = "none";
            tiny.$("noDataMode").style.display = "block";
            tiny.$("getDataMode").style.display = "none";
            tiny.$("toppage").style.display = "none";

            for (var i = 0, j = _this.cache.data.type.length; i < j; i++) {
                var c = _this.cache.data.value[i];
                var str = tiny.getInnerText(tiny.$(c + "_his"));
                if (tiny.indexOf(str, "改") != -1)
                    str = str.substr(0, str.indexOf("改"));
                selectedCondition.innerHTML += str + "；";
            }
            _this.updateInfo();
        },

        //
        getDataMode: function () {
            tiny.$("defMode").style.display = "none";
            tiny.$("noDataMode").style.display = "none";
            tiny.$("getDataMode").style.display = "block";
            tiny.$("toppage").style.display = "block";
            this.updateInfo();
        },

        defMode: function (x) {
            var _this = this;
            tiny.$("defMode").innerHTML = "您可以添加选择条件查找想要的结果！";

            tiny.$("defMode").style.display = "block";
            tiny.$("noDataMode").style.display = "none";
            tiny.$("getDataMode").style.display = "none";
            tiny.$("toppage").style.display = "none";
            try {
                //_this.sort
            } catch (e) { }
            if (x === undefined) {
                this.updateInfo();
            }
        },

        //解析url
        parseUrl: function () {
            var _this = this;
            var url = location.href;
            if (url.indexOf("#") == -1) {
                _this.defMode();
                return true;
            }
            var index = (url.indexOf("#") == -1) ? url.length : url.indexOf("#") + 1;
            var param = url.substring(index, url.length);

            var b = new Base64();
            param = b.decode(param);

            var c = (param.match(/c=(\[.+(\([1-5]\|.+(,[1-5]\|.+)?\))?\])+/i) != null) ? param.match(/c=(\[.+(\([1-5]\|.+(,[1-5]\|.+)?\))?\])+/i)[1] : "";
            var s = (param.match(/s=(.+?)($|\|)/i) != null) ? param.match(/s=(.+?)($|\|)/i)[1] : "";
            var st = (param.match(/st=(.+?)($|\|)/i) != null) ? param.match(/st=(.+?)($|\|)/i)[1] : "";

            if (c != "" && c != null) {
                var datas = c.split('][');
                for (var i = 0, j = datas.length; i < j; i++) {
                    var str = _this.rTrim(datas[i], "]");
                    str = _this.lTrim(str, "[");
                    var tmpDatas = str.split('(');
                    var p = "";
                    var c = "";
                    if (tmpDatas.length == 2) {
                        p = tmpDatas[0];
                        c = "(" + tmpDatas[1];
                    } else {
                        p = tmpDatas[0].match(/[^0-9]+/i)[0];
                        c = tmpDatas[0].replace(p, "");
                    }
                    _this.addCondition(p, c, 1);
                }
            }

            if (!(s == null || s == "") && (st == 1 || st == -1)) {
                if (tiny.$(_this.cache.sort.code + "_sort")) {
                    tiny.$(_this.cache.sort.code + "_sort").src = portalurl+"static/img/east/sort.gif";
                }
                _this.cache.sort.code = s;
                _this.cache.sort.type = st;
                _this.sort(s, true);
            } else {
                _this.dataUpdate();
            }
        },

        //排序
        sort: function (c, s) {

            var _this = this;
            var lastSort = _this.cache.sort.code;
            var sortType = _this.cache.sort.type;
            s = (s != null && s == true) ? true : false;
            if (s) {
                if (sortType == 1) {
                    if (tiny.$(c + "_sort"))
                        tiny.$(c + "_sort").src = portalurl+"static/img/east/sort_up.gif";
                } else if (sortType == -1) {
                    if (tiny.$(c + "_sort"))
                        tiny.$(c + "_sort").src = portalurl+"static/img/east/sort_down.gif";
                }
            } else if (lastSort == c) {
                if (sortType == -1) {
                    _this.cache.sort.type = 1;
                    if (tiny.$(c + "_sort"))
                        tiny.$(c + "_sort").src = portalurl+"static/img/east/sort_up.gif";
                }
                //else if (sortType == 1) {
                //    _this.cache.sort.type = 0;
                //    if (tiny.$(c + "_sort"))
                //        tiny.$(c + "_sort").src = "/images/stock_choice/sort.gif";
                //}
                else {
                    _this.cache.sort.type = -1;
                    if (tiny.$(c + "_sort"))
                        tiny.$(c + "_sort").src = portalurl+"static/img/east/sort_down.gif";
                }
            } else {
                ////修复前一排序对象状态
                //if (tiny.$(lastSort + "_sort")) {
                //    tiny.$(lastSort + "_sort").src = "/images/stock_choice/sort.gif";
                //}
                _this.cache.sort.type = -1;
                _this.cache.sort.code = c;
                if (tiny.$(c + "_sort"))
                    tiny.$(c + "_sort").src = portalurl+"static/img/east/sort_down.gif";

            }
            _this.updateUrl();
        },

        // RTrim
        rTrim: function (str, char) {
            if (str.charAt(str.length - 1) == char) {
                str = str.substring(0, str.length - 1);
            }
            return str;
        },

        // LTrim
        lTrim: function (str, char) {
            if (str.charAt(0) == char) {
                str = str.substring(1, str.length);
            }
            return str;
        },

        // 自定义事件
        customParam: function (p, stat, type, unit) {
            var _this = this;
            var c = "";
            var unitParam = '';
            if (unit != '' && tiny.indexOf(unit, '万') != -1)
                unitParam = "w";
            else if (unit != '' && tiny.indexOf(unit, '%') != -1)
                unitParam = '%';

            if (type == "z") {
                if (stat == 1 && _this.checkCondition(tiny.$(p + "editBox_GT").value, tiny.$(p + "editBox_LT").value)) {
                    if (tiny.$(p + "editBox_GT").value == "") {
                        if (unitParam == "%")
                            c = "(5|" + (tiny.$(p + "editBox_LT").value / 100).toFixed(2) + ")";
                        else
                            c = "(5|" + tiny.$(p + "editBox_LT").value + unitParam + ")";
                    } else if (tiny.$(p + "editBox_LT").value == "") {
                        if (unitParam == "%")
                            c = "(4|" + (tiny.$(p + "editBox_GT").value / 100).toFixed(2) + ")";
                        else
                            c = "(4|" + tiny.$(p + "editBox_GT").value + unitParam + ")";
                    } else {
                        if (unitParam == "%")
                            c = "(4|" + (tiny.$(p + "editBox_GT").value / 100).toFixed(2) + ",5|" + (tiny.$(p + "editBox_LT").value / 100).toFixed(2) + ")";
                        else
                            c = "(4|" + tiny.$(p + "editBox_GT").value + unitParam + ",5|" + tiny.$(p + "editBox_LT").value + unitParam + ")";
                    }

                    tiny.$(p + "editBox_GE").value = "";
                    tiny.$(p + "editBox_LE").value = "";
                } else if (stat == 2 && _this.checkCondition(tiny.$(p + "editBox_GE").value, tiny.$(p + "editBox_GE").value)) {
                    if (unitParam == "%")
                        c = "(4|" + (tiny.$(p + "editBox_GE").value / 100).toFixed(2) + ")";
                    else
                        c = "(4|" + tiny.$(p + "editBox_GE").value + unitParam + ")";
                    tiny.$(p + "editBox_GT").value = "";
                    tiny.$(p + "editBox_LT").value = "";
                    tiny.$(p + "editBox_LE").value = "";
                } else if (stat == 3 && _this.checkCondition(tiny.$(p + "editBox_LE").value, tiny.$(p + "editBox_LE").value)) {
                    if (unitParam == "%")
                        c = "(5|" + (tiny.$(p + "editBox_LE").value / 100).toFixed(2) + ")";
                    else
                        c = "(5|" + tiny.$(p + "editBox_LE").value + unitParam + ")";
                    tiny.$(p + "editBox_GT").value = "";
                    tiny.$(p + "editBox_LT").value = "";
                    tiny.$(p + "editBox_GE").value = "";
                }
            } else if (type == "g") {
                if (stat == 1 && _this.checkCondition(tiny.$(p + "editBox_GT_g").value, tiny.$(p + "editBox_LT_g").value)) {
                    if (tiny.$(p + "editBox_GT_g").value == "") {
                        if (unitParam == "%")
                            c = "(5|" + (tiny.$(p + "editBox_LT_g").value / 100).toFixed(2) + ")";
                        else
                            c = "(5|" + tiny.$(p + "editBox_LT_g").value + unitParam + ")";
                    } else if (tiny.$(p + "editBox_LT_g").value == "") {
                        if (unitParam == "%")
                            c = "(4|" + (tiny.$(p + "editBox_GT_g").value / 100).toFixed(2) + ")";
                        else
                            c = "(4|" + tiny.$(p + "editBox_GT_g").value + unitParam + ")";
                    } else {
                        if (unitParam == "%")
                            c = "(4|" + (tiny.$(p + "editBox_GT_g").value / 100).toFixed(2) + ",5|" + (tiny.$(p + "editBox_LT_g").value / 100).toFixed(2) + ")";
                        else
                            c = "(4|" + tiny.$(p + "editBox_GT_g").value + unitParam + ",5|" + tiny.$(p + "editBox_LT_g").value + unitParam + ")";
                    }

                    tiny.$(p + "editBox_GE_g").value = "";
                    tiny.$(p + "editBox_LE_g").value = "";
                    if (tiny.$(p + "editBox_GT") && tiny.$(p + "editBox_LT")) {
                        tiny.$(p + "editBox_GT").value = tiny.$(p + "editBox_GT_g").value;
                        tiny.$(p + "editBox_LT").value = tiny.$(p + "editBox_LT_g").value;
                    }
                } else if (stat == 2 && _this.checkCondition(tiny.$(p + "editBox_GE_g").value, tiny.$(p + "editBox_GE_g").value)) {
                    if (unitParam == "%")
                        c = "(4|" + (tiny.$(p + "editBox_GE_g").value / 100).toFixed(2) + ")";
                    else
                        c = "(4|" + tiny.$(p + "editBox_GE_g").value + unitParam + ")";
                    tiny.$(p + "editBox_GT_g").value = "";
                    tiny.$(p + "editBox_LT_g").value = "";
                    tiny.$(p + "editBox_LE_g").value = "";
                    if (tiny.$(p + "editBox_GE"))
                        tiny.$(p + "editBox_GE").value = tiny.$(p + "editBox_GE_g").value;
                } else if (stat == 3 && _this.checkCondition(tiny.$(p + "editBox_LE_g").value, tiny.$(p + "editBox_LE_g").value)) {
                    if (unitParam == "%")
                        c = "(5|" + (tiny.$(p + "editBox_LE_g").value / 100).toFixed(2) + ")";
                    else
                        c = "(5|" + tiny.$(p + "editBox_LE_g").value + unitParam + ")";
                    tiny.$(p + "editBox_GT_g").value = "";
                    tiny.$(p + "editBox_LT_g").value = "";
                    tiny.$(p + "editBox_GE_g").value = "";
                    if (tiny.$(p + "editBox_LE"))
                        tiny.$(p + "editBox_LE").value = tiny.$(p + "editBox_LE_g").value;
                }
            }
            if (c != "")
                _this.addCondition(p, c);
        },

        // 检索条件验证
        checkCondition: function (fVal, sVal) {
            var checkResult = true;
            var reg = /[\d]+\.?[\d]*/i;
            if ((fVal == "" && sVal == "")
                || (fVal != "" && !reg.test(fVal))
                || (sVal != "" && !reg.test(sVal))) {
                alert("您输入的数据有误，请重新输入！");
                checkResult = false;
            }

            if (eval(fVal) > eval(sVal)) {
                alert("大于的值不能比小于的值大，请重新输入！");
                checkResult = false;
            }

            return checkResult;
        },

        // 大数字数据单位转换
        getcolor_JS: function (a, b, c) {
            if (a == '-' || a == "" || a == "NaN") return "-";
            var v;
            if (b == "%") v = parseFloat(a).toFixed(2) + b;
            else if (b == "w") {
                var m = Math.abs(a);
                if (m < 10000) {
                    v = (a / 1).toFixed(0);
                } else if (m >= 10000 && m < 1000000) {
                    v = (a / 10000).toFixed(2) + "万";
                } else if (m >= 1000000 && m < 100000000) {
                    v = (a / 10000).toFixed(0) + "万";
                } else if (m >= 100000000 && m < 10000000000) {
                    v = (a / 100000000).toFixed(2) + "亿";
                } else {
                    v = (a / 100000000).toFixed(0) + "亿";
                }
            } else v = (a * 100 / 100).toFixed(2);
            if (c) a = c;
            //if (a.length > 0) {
            //    if (a.substring(0, 1) == '-') return "<span class=\"green\">" + v + "</span>";
            //    else if (a != 0) return "<span class=\"red\">" + v + "</span>";
            //}
            return v;
        }
    });
