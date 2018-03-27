/*
 lihaibin  20140606/  动态加载表格
 参数说明
 {
 id:展示数据table 的id
 param: { "参数框id": { cname: "参数存储的类型", type: "参数名称", value: "参数默认值"} } 可多个
 pagenav: 分页控件id 默认为“PageCont”
 miniPageNav: 小分页控件id 默认为 “miniPageNav”
 sort: { id: "排序字段", desc: 是否逆序（true|false） }
 cells: 展示字段{ "n": "表头名称" ,"s":"排序字段"，w:宽度} 可多个
 cellcount 默认可不填，在多行多行表头时使用，传入实际数据的列数
 dataurl: 请求数据地址  "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=FD&sty=SRB&st={sortType}&sr={sortRule}&p={page}&ps={pageSize}&js=var {jsname}={pages:(pc),data:[(x)]}{param}",
 pagesize: 每页行数,
 beforedisplay: function () { }, 加载数据后展示数据前函数
 beforeupdate: function () { },加载数据前函数
 afterdisplay: function () { }，展示数据后函数
 minWidth: 950, 表格最小宽度，默认为950（用于浮动头部左右滚动）
 autoWidit: false 表格是否为自动宽度 默认为false （用于重绘浮动表头的宽度，当表格宽度为自适应时，窗口宽度改变时要重绘浮动表头）
 autoScrollRun:true 加载时自动定位到表格
 fixedThead:false 是否固定表格头部
 }
 */

//表格配置
var load_table_data_config = {
    //是否固定表格头部,所有表头固定的开关
    fixedThead: true
};

var Class = {
    create: function () {
        return function () {
            this.initialize.apply(this, arguments)
        }
    }
};
Object.extend = function (destination, source) {
    for (property in source) {
        destination[property] = source[property]
    }
    return destination
};
var LoadTable = Class.create();
Object.extend(LoadTable.prototype, {
    initialize: function (opt) {
        var _t = this;
        var options = {
            id: "dt_1",
            table: null,
            tbody: null,
            thead: null,
            param: {},
            pagenav: "PageCont",
            miniPageNav: "miniPageNav",
            doZoomNav: "autofontDiv",
            sort: {
                id: "2",
                desc: true
            },
            page: 1,
            cells: 8,
            dataurl: "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=FD&sty=SRB&st={sortType}&sr={sortRule}&p={page}&ps={pageSize}&js=var {jsname}={pages:(pc),data:[(x)]}{param}",
            pagesize: 5000,
            pages: 1,
            mininterval: 100,
            loadtime: 1404194135127,
            theadfloat: true,
            beforedisplay: function () { },
            beforeupdate: function () { },
            afterdisplay: function () { },
            minWidth: 1050,
            autoWidit: false,
            autoScrollRun: true,
            selectiscol: true,
            charset: "utf-8"
        };
        if ('\v' == 'v') {
            document.execCommand("BackgroundImageCache", false, true)
        }
        Object.extend(options, opt || {});
        _t.options = options;
        var _table = document.getElementById(_t.options.id);
        if (!_table) {
            alert("数据显示容器未找到");
            return
        } else { }
        _t.maketr = _t.options.maketr;
        _t.options.table = _table;
        _t.options.tbody = _table.getElementsByTagName("tbody")[0];
        _t.options.thead = _table.getElementsByTagName("thead")[0];
        _t.tableXY = _t.findPos(_table);
        _t.contentY = 0;
        if (!_t.options.cellcount) _t.options.cellcount = _t.options.cells.length;
        _t.initUserUpdate();
        var defJson = opt.defjson;
        var isrr = _t.initparam();
        if (defJson && isrr) {
            _t.options.data = defJson;
            _t.loadThead();
            _t.display()
        } else {
            setTimeout(function () {
                _t.update(),
                    20
            })
        }
    },

    display: function () {
        var _t = this;
        _t.options.beforedisplay(_t);
        if (_t.options.data.pages > 0 && _t.options.page > _t.options.data.pages)
            _t.options.page = _t.options.data.pages;
        var _d = _t.options.data.data,
            _c = _t.options.cellcount,
            _p = _t.options.page || 1,
            _ps = _t.options.pagesize || 50,
            _body = _t.options.tbody;
        var trs = _body.childNodes;
        for (var i = trs.length - 1; i >= 0; i--) {
            _body.removeChild(trs[i])
        }
        var rowTp = _body.insertRow(-1);
        for (var i = 0; i < _c; i++) {
            var cell = rowTp.insertCell(i)
        }
        if (_d && _d.length && _d[0].stats == undefined) {
            for (var i = 0; i < _d.length; i++) {
                var data;
                if (typeof (_d[i]) == "object") data = _d[i];
                else data = _d[i].split(",");
                var row = rowTp.cloneNode(true);
                _body.appendChild(row);
                _t.maketr(row, data, i, ((_p - 1) * _ps + 1 + i));
                if (_t.options.selectiscol && _t.options.sort.rowindex != 'undefined' && _t.options.sort.rowindex >= 0 && _t.options.sort.rowindex < _t.options.cellcount) if (row) row.cells[_t.options.sort.rowindex].className = "col"
            }
        } else {
            _t.showNoData()
        }
        _body.removeChild(rowTp);
        _t.pageit();
        if (_t.options.theadfloat) _t.floatHeader();
        _t.options.afterdisplay(_t)
    },
    showLoading: function () {
        var _t = this;
        var _c = _t.options.theadcount || _t.options.cellcount,
            _body = _t.options.tbody;
        if (_body.innerHTML.indexOf('td') < 0) {
            var rowTp = _body.insertRow(-1);
            var cell = rowTp.insertCell(0);
            cell.setAttribute("colSpan", _c);
            cell.innerHTML = "<div style=\"color:#666;height:200px;line-height:200px;\">数据初始化...</div>";
            _body.appendChild(rowTp)
        }
        if (typeof (_t.options.load_div) == "undefined") {
            var load_div = document.createElement("DIV");
            var nodetemp = document.getElementById("zzdiv");
            if (!nodetemp) {
                nodetemp = _t.options.table;
                while (nodetemp = nodetemp.parentNode) {
                    if (nodetemp.className == "content") break
                }
            }
            _t.contentY = _t.findPos(nodetemp).y;
            load_div.style.width = nodetemp.clientWidth + "px";
            nodetemp.style.position = "relative";
            load_div.innerHTML = '<img src="http://datapic.eastmoney.com/img/loading.gif" style=" padding-top: 100px;">';
            if (_t.tools.browser.ie) {
                load_div.style.cssText = "top:0px;left:0px;position: absolute; width: 100%;background-color: #fff;z-index: 9999;filter: alpha(Opacity=80);-moz-opacity:0.5;opacity: 0.5; text-align: center;min-height: 300px; ";
                load_div.style.height = nodetemp.offsetHeight + "px"
            } else load_div.setAttribute("style", "top:0px;left:0px;position: absolute; width: 100%;height: 100%;background-color: #fff;z-index: 9999;filter: alpha(Opacity=80);-moz-opacity:0.5;opacity: 0.5; text-align: center;min-height: 300px; ");
            nodetemp.insertBefore(load_div, nodetemp.firstChild);
            _t.options.load_div = load_div;
            _t.options.nodetemp = nodetemp
        } else {
            if (_t.tools.browser.ie) {
                _t.options.load_div.style.height = _t.options.nodetemp.offsetHeight + "px"
            }
        }
        _t.options.load_div.style.display = "block"
    },
    showNoData: function () {
        var _t = this,
            _c = _t.options.theadcount || _t.options.cellcount,
            _body = _t.options.tbody;
        var _h = _body.offsetHeight;
        _h = (_h < 200) ? 200 : _h;
        var trs = _body.getElementsByTagName("tr");
        var rowTp = _body.insertRow(-1);
        var cell = rowTp.insertCell(0);
        cell.setAttribute("colSpan", _c);
        cell.innerHTML = "<div style=\"color:#666;height:" + _h + "px;line-height:200px;\">没有相关数据...</div>";
        _body.appendChild(rowTp);
        if (typeof (_t.options.load_div) != "undefined") {
            _t.options.load_div.style.display = "none"
        }
    },
    getCode: function (num) {
        var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        var codes = str.split('');
        num = num || 6;
        var code = "";
        for (var i = 0; i < num; i++) {
            code += codes[Math.floor(Math.random() * 52)]
        }
        return code
    },
    parperUrl: function () {
        var _t = this,
            _param = _t.options.param;
        var param = "";
        for (var key in _param) {
            param += ("&" + _param[key].type + "=" + _param[key].value)
        }
        _url = _t.options.dataurl;
        var _sort = "1";
        _url = _url.replace("{pageSize}", _t.options.pagesize);
        _url = _url.replace("{page}", _t.options.page);
        _url = _url.replace("{sortType}", _t.options.sort.id);
        _url = _url.replace("{sortRule}", _t.options.sort.desc ? "-1" : "1");
        _url = _url.replace("{param}", param);
        return _url
    },
    update: function () {
        var _t = this;
        if (_t.options.beforeupdate(_t))
            return;
        var jsname = _t.getCode(8),
            _url = _t.parperUrl();
        _t.options.code = jsname;
        _url = _url.replace("{jsname}", jsname);
        _url += (_url.indexOf('?') > -1) ? "&rt=" : "?rt=";
        _url += parseInt(parseInt(new Date().getTime()) / 30000);
        _t.loadThead();
        _t.scorllTop();
        _t.showLoading();
        _t.tools.loadJs(_url, _t.options.charset,
            function () {
                if (typeof (_t.options.load_div) != "undefined") {
                    _t.options.load_div.style.display = "none"
                }
                if (!(eval("typeof " + jsname) == "undefined") || eval("typeof " + jsname == null)) {
                    var loaddata = eval(jsname);
                    if (jsname != _t.options.code) {
                        return
                    }
                    _t.options.data = loaddata;
                    _t.display()
                } else {
                    alert("数据加载失败，请刷新页面重新尝试！")
                }
            })
    },
    initUserUpdate: function () {
        var updateBtn = this.options.id + "_update",
            _t = this;
        if (document.getElementById(updateBtn)) {
            document.getElementById(updateBtn).onclick = function () {
                _t.update()
            }
        }
    },
    autoUpdate: function () {
        var _t = this;
        var interval = function () {
            var now = new Date();
            try {
                now = Eastmoney.Time.now()
            } catch (e) { }
            k = parseInt(_t.tools.dataFormat(now, "HHmm") * 1);
            var g = now.getDay();
            if (!(k <= 924 || (k >= 1145 && k <= 1259) || k >= 1515 || g > 5)) {
                try {
                    setTimeout(function () {
                            _t.update()
                        },
                        100)
                } catch (e) { }
            }
        };
        setInterval(interval, 1000 * 60 * 2)
    },
    initparam: function () {
        var _t = this,
            _param = _t.options.param,
            _isp = false;
        var _st = _t.tools.GetQueryStr("st");
        if (_st) {
            _t.options.sort.id = _st;
            _isp = true
        }
        var _sr = _t.tools.GetQueryStr("sr");
        if (_sr) {
            _t.options.sort.desc = (_sr == "0" || _sr == "false");
            _isp = true
        }
        for (var key in _param) {
            var _p = document.getElementById(key);
            var p = _param[key];
            if (_p) {
                if (_t.tools.GetQueryStr(p.type) != null) {
                    _t.options.param[key].value = _t.tools.GetQueryStr(p.type);
                    _isp = true
                }
                if (p.cname == 'option') {
                    _t.tools.OptionValue(_p, _t.options.param[key].value);
                    _p.onchange = function (_p, k) {
                        var _v = _p.options[_p.selectedIndex].value;
                        if (_v.toLowerCase() == _t.options.param[k].value.toLowerCase()) return;
                        _t.options.param[k].value = _v;
                        _t.options.page = 1;
                        _t.update()
                    }.bind(this, _p, key)
                } else {
                    var _list = _p.getElementsByTagName(p.cname);
                    var _totle = _list.length;
                    for (var i = 0; i < _totle; i++) {
                        var _l = _list[i],
                            _v = _l.getAttribute("data");
                        if (_v == null)
                            continue;
                        if (_v.toLowerCase() == _t.options.param[key].value.toLowerCase()) {
                            _l.className = _t.options.param[key].cssclass || "at"
                        } else {
                            _l.className = ""
                        }
                        if (_l.getAttribute("disclick") != "1") _l.onclick = function (o, c, par, index, pdata) {
                            var _ov = o.getAttribute("data");
                            if (_ov == null)
                                return;
                            if (_ov.toLowerCase() == _t.options.param[index].value.toLowerCase()) return;
                            var _lis = par.getElementsByTagName(pdata.cname);
                            for (var j = 0; j < _lis.length; j++) {
                                if (_lis[j] == o) {
                                    _lis[j].className = _t.options.param[index].cssclass || "at";
                                    _t.options.param[index].value = c
                                } else {
                                    _lis[j].className = ""
                                }
                            }
                            _t.options.page = 1;
                            _t.update()
                        }.bind(this, _l, _v, _p, key, p)
                    }
                }
            }
        }
        return !_isp
    },
    loadThead: function () {
        var _t = this,
            _thead = _t.options.thead,
            _cells = _t.options.cells;
        if (_t.options.scrollTable && _t.options.scrollTable.parentNode) _t.options.scrollTable.parentNode.removeChild(_t.options.scrollTable);
        var trs = _thead.getElementsByTagName("tr");
        for (var i = trs.length - 1; i >= 0; i--) {
            _thead.removeChild(trs[i])
        }
        var rowTh = _thead.insertRow(-1),
            ismore = false;
        for (var i = 0; i < _cells.length; i++) {
            if (_cells[i].cells && _cells[i].cells.length) ismore = true;
            var cell = document.createElement("TH");
            cell.innerHTML = _cells[i].n;
            if (_cells[i].w && _cells[i].w > 0) cell.style.width = _cells[i].w + "px";
            if (_cells[i].s) {
                var _img = '';
                if (_t.options.sort.id == _cells[i].s) {
                    var _img = (_t.options.sort.desc) ? "<img src=\"/static/img/east/down.gif\" />" : "<img src=\"/static/img/east/up.gif\" />";
                    _t.options.sort.rowindex = i
                }
                if (/&img/.test(_cells[i].n))
                    cell.innerHTML = "<span class=\"clickspan\">" + _cells[i].n.replace('&img', _img) + "</span>";
                else
                    cell.innerHTML = "<span class=\"clickspan\">" + _cells[i].n + "" + _img + "</span>";
                cell.onclick = function (cellIndex, sortCont) {
                    if (_t.options.sort.id == sortCont) _t.options.sort.desc = !_t.options.sort.desc;
                    else _t.options.sort.id = sortCont,
                        _t.options.sort.desc = true;
                    _t.options.page = 1;
                    _t.update()
                }.bind(this, i, _cells[i].s)
            }
            cell.style.padding = "0px";
            rowTh.appendChild(cell)
        }
        if (ismore) {
            var rowThs = rowTh.getElementsByTagName("th");
            var rowTh1 = _thead.insertRow(1);
            var _rcount = 0;
            for (var i = 0; i < _cells.length; i++) {
                if (_cells[i].cells && _cells[i].cells.length) {
                    for (var j = 0; j < _cells[i].cells.length; j++) {
                        var cell = document.createElement("TH");
                        cell.innerHTML = _cells[i].cells[j].n;
                        if (_cells[i].cells[j].w && _cells[i].cells[j].w > 0) cell.style.width = _cells[i].cells[j].w + "px";
                        if (_cells[i].cells[j].s) {
                            var _img = '';
                            if (_t.options.sort.id == _cells[i].cells[j].s) {
                                var _img = (_t.options.sort.desc) ? "<img src=\"/static/img/east/down.gif\" />" : "<img src=\"/static/img/east/up.gif\" />";
                                _t.options.sort.rowindex = _rcount
                            }
                            if (/&img/.test(_cells[i].cells[j].n))
                                cell.innerHTML = "<span class=\"clickspan\">" + _cells[i].cells[j].n.replace('&img', _img) + "</span>";
                            else
                                cell.innerHTML = "<span class=\"clickspan\">" + _cells[i].cells[j].n + "" + _img + "</span>";
                            cell.onclick = function (cellIndex, sortCont) {
                                if (_t.options.sort.id == sortCont) _t.options.sort.desc = !_t.options.sort.desc;
                                else _t.options.sort.id = sortCont,
                                    _t.options.sort.desc = true;
                                _t.options.page = 1;
                                _t.update()
                            }.bind(this, i, _cells[i].cells[j].s)
                        }
                        cell.style.padding = "0px";
                        rowTh1.appendChild(cell);
                        _rcount++
                    }
                    rowThs[i].colSpan = _cells[i].cells.length
                } else {
                    rowThs[i].rowSpan = "2";
                    if (_t.options.sort.id == _cells[i].s) {
                        _t.options.sort.rowindex = _rcount
                    }
                    _rcount++
                }
            }
        }
    },
    floatHeader: function () {
        var _t = this;
        setTimeout(function () {
                var scroll_div = document.getElementById(_t.options.id + "-scroll-table");
                if (!scroll_div) {
                    var scroll_div = document.createElement("DIV");
                }
                _t.options.scrollTable = _t.options.table.cloneNode(false);
                if (_t.tools.browser.ie) {
                    _t.options.scrollTable.style.zoom = 1
                }
                _t.options.scrollTable.style.display = "none";
                _t.options.scrollTable.style.zIndex = "999";
                _t.options.scrollTableId = _t.options.scrollTable.id = _t.options.id + "-scroll";
                _t.options.scrollTable.style.width = _t.options.table.clientWidth + "px";
                scroll_div.id = _t.options.id + "-scroll-table";
                scroll_div.appendChild(_t.options.scrollTable);
                if (!load_table_data_config.fixedThead) {
                    //隐藏固定浮动的表头
                    scroll_div.style.display = "none";
                }

                //移动设备隐藏浮动表头
                var u = navigator.userAgent;
                if (!!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/) || u.indexOf('Android') > -1 || u.indexOf('iPhone') > -1 || u.indexOf('iPad') > -1)
                    scroll_div.style.display = "none";

                _t.options.table.parentNode.insertBefore(scroll_div, _t.options.table.parentNode.firstChild);
                var scrollTHead = _t.options.thead.cloneNode(true);
                var oldCells = _t.options.thead.getElementsByTagName("th");
                _t.options.scrollTable.appendChild(scrollTHead);
                var scrolth = scrollTHead.getElementsByTagName("th");
                for (var k = 0; k < oldCells.length; k++) {
                    scrolth[k].style.width = oldCells[k].clientWidth + "px";
                    scrolth[k].onclick = oldCells[k].onclick
                }
                _t.setScrollTBTop()
            },
            200)
    },
    pageit: function () {
        var _t = this,
            p = _t.options.page || 1,
            pages = _t.options.data.pages || 1;
        var _pn = _t.options.pagenav;
        if (_pn == null) {
            return
        }
        p = isNaN(p) ? 1 : parseInt(p);
        var _minipn = _t.options.miniPageNav;
        if (document.getElementById(_minipn)) {
            document.getElementById(_minipn).style.display = "block";
            var mini_bs = document.getElementById(_minipn).getElementsByTagName("b");
            if (mini_bs.length == 4) {
                mini_bs[3].innerHTML = "共<span class=\"red\">" + pages + "</span>页";
                mini_bs[1].innerHTML = "上一页";
                mini_bs[2].innerHTML = "下一页";
                if (p <= 1) {
                    mini_bs[1].className = "n1";
                    mini_bs[1].onclick = null;
                    mini_bs[1].setAttribute("title", "")
                } else {
                    mini_bs[1].className = "n2";
                    mini_bs[1].setAttribute("title", "转到第" + (p - 1) + "页");
                    mini_bs[1].onclick = function (n) {
                        _t.go(p - 1)
                    }
                }
                if (p >= pages) {
                    mini_bs[2].className = "n1";
                    mini_bs[2].setAttribute("title", "");
                    mini_bs[2].onclick = null
                } else {
                    mini_bs[2].className = "n2";
                    mini_bs[2].setAttribute("title", "转到第" + (p + 1) + "页");
                    mini_bs[2].onclick = function (n) {
                        _t.go(p + 1)
                    }
                }
                mini_bs[0].innerHTML = "当前第<span class=\"red\">" + p + "</span>页"
            }
            else if (mini_bs.length == 6) {
                mini_bs[5].innerHTML = "共<span class=\"red\">" + pages + "</span>页";
                mini_bs[1].innerHTML = "首页";
                mini_bs[2].innerHTML = "上一页";
                mini_bs[3].innerHTML = "下一页";
                mini_bs[4].innerHTML = "尾页";
                if (p <= 1) {
                    mini_bs[2].className = mini_bs[1].className = "n1";
                    mini_bs[2].onclick = mini_bs[1].onclick = null;
                    mini_bs[2].setAttribute("title", "");
                    mini_bs[1].setAttribute("title", "");
                } else {
                    mini_bs[2].className = mini_bs[1].className = "n2";
                    mini_bs[2].setAttribute("title", "转到第" + (p - 1) + "页");
                    mini_bs[1].setAttribute("title", "转到首页");
                    mini_bs[2].onclick = function (n) {
                        _t.go(p - 1);
                    }
                    mini_bs[1].onclick = function (n) {
                        _t.go(1);
                    }
                }
                if (p >= pages) {
                    mini_bs[3].className = mini_bs[4].className = "n1";
                    mini_bs[3].setAttribute("title", "");
                    mini_bs[4].setAttribute("title", "");
                    mini_bs[3].onclick = mini_bs[4].onclick = null
                } else {
                    mini_bs[3].className = mini_bs[4].className = "n2";
                    mini_bs[3].setAttribute("title", "转到第" + (p + 1) + "页");
                    mini_bs[4].setAttribute("title", "转到尾页");
                    mini_bs[3].onclick = function (n) {
                        _t.go(p + 1);
                    }
                    mini_bs[4].onclick = function (n) {
                        _t.go(pages);
                    }
                }
                mini_bs[0].innerHTML = "当前第<span class=\"red\">" + p + "</span>页";
            }

        }
        _pn = document.getElementById(_pn);
        if (!_pn) return;
        _pn.innerHTML = "";
        if (_t.options.data.pages == 0 || p == pages && pages == 1) {
            _pn.parentNode.style.display = "none";
            return
        } else {
            _pn.parentNode.style.display = ""
        }
        var _a = document.createElement("a");
        _pn.appendChild(_a);
        _a.setAttribute("href", "javascript:void(0);");
        _a.setAttribute("target", "_self");
        _a.innerHTML = "上一页";
        if (p == 1) {
            _a.className = "nolink";
            _a.onclick = function () {
                return false
            }
        } else {
            _a.className = "";
            _a.setAttribute("href", "javascript:void(0);");
            _a.setAttribute("target", "_self");
            _a.setAttribute("title", "转到第" + (p - 1) + "页");
            _a.onclick = function () {
                _t.go(p - 1)
            }
        }
        var start = (p > 3) ? p - 2 : 1;
        start = (p > pages - 3 && pages > 4) ? pages - 4 : start;
        var end = (start == 1) ? 5 : start + 4;
        end = (end > pages) ? pages : end;
        if (start > 1) {
            var pre = ((start - 3) < 1) ? 1 : (start - 3);
            var _pre = ((start - 3) > pages) ? pages : (next + 3);
            _a = document.createElement("a");
            _pn.appendChild(_a);
            _a.setAttribute("href", "javascript:void(0);");
            _a.setAttribute("target", "_self");
            _a.setAttribute("title", "转到第一页");
            _a.onclick = function () {
                _t.go(1)
            };
            _a.innerHTML = 1;
            if (pre > 1) {
                _a = document.createElement("a");
                _pn.appendChild(_a);
                _a.setAttribute("href", "javascript:void(0);");
                _a.setAttribute("target", "_self");
                _a.setAttribute("title", "转到上一组");
                _a.onclick = function () {
                    _t.go(pre)
                };
                _a.className = "next";
                _a.innerHTML = "..."
            }
        }
        for (var i = start; i <= end; i++) {
            if (p == i) {
                _a = document.createElement("span");
                _pn.appendChild(_a);
                _a.className = "at";
                _a.innerHTML = i
            } else {
                _a = document.createElement("a");
                _pn.appendChild(_a);
                _a.setAttribute("href", "javascript:void(0);");
                _a.setAttribute("target", "_self");
                _a.setAttribute("title", "转到第" + i + "页");
                _a.onclick = function (n) {
                    _t.go(n)
                }.bind(this, i);
                _a.innerHTML = i
            }
        }
        if (pages > end) {
            var next = ((end + 3) > pages) ? pages : (end + 3);
            var _nex = ((next + 3) > pages) ? pages : (next + 3);
            _a = document.createElement("a");
            _pn.appendChild(_a);
            _a.setAttribute("href", "javascript:void(0);");
            _a.setAttribute("target", "_self");
            _a.setAttribute("title", "转到下一组");
            _a.onclick = function () {
                _t.go(next)
            };
            _a.className = "next";
            _a.innerHTML = "...";
            if (next < pages) {
                _a = document.createElement("a");
                _pn.appendChild(_a);
                _a.setAttribute("href", "javascript:void(0);");
                _a.setAttribute("target", "_self");
                _a.setAttribute("title", "转到最后一页");
                _a.onclick = function () {
                    _t.go(pages)
                };
                _a.innerHTML = pages
            }
        }
        _a = document.createElement("a");
        _pn.appendChild(_a);
        _a.setAttribute("href", "javascript:void(0);");
        _a.setAttribute("target", "_self");
        if (p == pages) {
            _a.className = "nolink";
            _a.innerHTML = "下一页";
            _a.onclick = function () {
                return false
            }
        } else {
            _a.innerHTML = "下一页";
            _a.onclick = function () {
                _t.go(p + 1)
            };
            _a.setAttribute("title", "转到" + (p + 1) + "页")
        }
        _a = document.createElement("span");
        _pn.appendChild(_a);
        _a.className = "txt";
        _a.innerHTML = "&nbsp;&nbsp;转到";
        _a = document.createElement("input");
        _pn.appendChild(_a);
        _a.className = "txt";
        _a.id = "gopage";
        _a.value = p;
        _a = document.createElement("a");
        _pn.appendChild(_a);
        _a.className = "btn_link";
        _a.onclick = function () {
            if (document.getElementById("gopage")) {
                var p = document.getElementById("gopage").value;
                if (isNaN(p) || parseInt(p) < 0) {
                    p = 1
                }
                _t.go(p)
            }
        };
        _a.innerHTML = "Go"
    },
    go: function (p) {
        var _t = this;
        p = (p > _t.options.data.pages) ? _t.options.data.pages : p;
        p = (p < 1) ? 1 : p;
        _t.options.page = p;
        setTimeout(function () {
                _t.update()
            },
            0)
    },
    scorllTop: function () {
        var next = true,
            _this = this,
            _topnode = document.getElementById("datatitle") || document.body;
        _rect = this.tools.rect(_topnode),
            _top = _rect.top,
            tmp_top = this.tools.getScrollTop();
        _this.tools.wheel(function () {
            next = false
        });
        var s = function (b, e) {
            var _t = this;
            _t.b = b;
            _t.e = e;
            _t.c = _t.e - _t.b;
            _t.d = ('\v' == 'v') ? 30 : 60;
            _t.t = 1;
            _t.w = function (t, b, c, d) {
                return -c * (t /= d) * (t - 2) + b
            };
            function run() {
                _tmp = _t.w(_t.t, _t.b, _t.c, _t.d);
                window.scrollTo(0, _tmp);
                if (_t.t < _t.d) {
                    _t.t++;
                    setTimeout(run, 10)
                } else {
                    _this.tools.wheel(function () {
                        next = true
                    })
                }
            }

            run()
        };
        if (_this.options.autoScrollRun)
            new s(tmp_top, _top)
    },
    setScrollTBTop: function () {
        this.options.scrollTable = document.getElementById(this.options.scrollTableId);
        this.options.table = document.getElementById(this.options.id);
        var _t = this;
        var isIE6 = this.tools.browser.ie6;
        if (isIE6) {
            var _body = document.getElementsByTagName('body')[0];
            var _html = document.getElementsByTagName('html')[0];
            if (_body.style.backgroundAttachment != "fixed") {
                _html.style.backgroundImage = "url(about:blank)";
                _html.style.backgroundAttachment = "fixed"
            }
        }
        if (window.innerWidth) _t.options.winWidth = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth)) _t.options.winWidth = document.body.clientWidth;
        if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
            _t.options.winWidth = document.documentElement.clientWidth
        }
        window.onscroll = function () {

            var scrollXY = _t.getScroll();
            if (scrollXY.y >= _t.tableXY.y) {
                if (isIE6) {
                    var temptop = document.documentElement.scrollTop || document.body.scrollTop;
                    temptop = temptop - _t.contentY;
                    _t.options.scrollTable.style.display = "block";
                    _t.options.scrollTable.style.position = "absolute";
                    _t.options.scrollTable.style.top = (temptop+33).toString() + "px"
                } else {
                    _t.options.scrollTable.style.display = "block";
                    _t.options.scrollTable.style.position = "fixed";
                    if (_t.options.scrolltop) _t.options.scrollTable.style.top = _t.options.scrolltop + "px";
                    else _t.options.scrollTable.style.top = "0px"
                }
            } else {
                _t.options.scrollTable.style.display = "none"
            }
            if (_t.options.winWidth < _t.options.minWidth) {
                if (isIE6) {
                    _t.options.scrollTable.style.left = (-document.body.scrollLeft || -document.documentElement.scrollLeft) / 1000 + "px"
                } else {
                    _t.options.scrollTable.style.left = (-document.body.scrollLeft || -document.documentElement.scrollLeft) + "px"
                }
            } else {
                _t.options.scrollTable.style.left = "auto"
            }
        };
        window.onresize = function () {
            if (window.innerWidth) _t.options.winWidth = window.innerWidth;
            else if ((document.body) && (document.body.clientWidth)) _t.options.winWidth = document.body.clientWidth;
            if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
                _t.options.winWidth = document.documentElement.clientWidth
            }
            window.onscroll();
            if (_t.options.autoWidit) {
                //document.getElementById("dt_1-scroll").style.width = document.getElementById("dt_1").offsetWidth;
                _t.options.scrollTable.style.width = _t.options.table.clientWidth + "px";

                var oldCells = _t.options.thead.getElementsByTagName("th");
                var scrolth = _t.options.scrollTable.getElementsByTagName("thead")[0].getElementsByTagName("th");
                for (var k = 0; k < oldCells.length; k++) {
                    scrolth[k].style.width = oldCells[k].clientWidth + "px"
                }
            }
            if (document.getElementById("mainflow")) {
                if (_t.options.winWidth < _t.options.minWidth)
                    document.getElementById("mainflow").style.width = "100%";
                else{
                    document.getElementById("mainflow").style.width = "100%";
                    if (document.getElementById("dt_1")) {
                        var oldCells = _t.options.thead.getElementsByTagName("th");
                        var scrolth = _t.options.scrollTable.getElementsByTagName("thead")[0].getElementsByTagName("th");
                        for (var k = 0; k < oldCells.length; k++) {
                            scrolth[k].style.width = oldCells[k].clientWidth + "px"
                        }
                        _t.options.scrollTable.style.width = document.getElementById("dt_1").clientWidth + "px";
                    }
                }

            }
        }
    },
    getFromat: function (v0, v1, c0) {
        if (v0 > c0) {
            return "<span class=\"red\">" + v1 + "</span>"
        } else if (v0 < c0) {
            return "<span class=\"green\">" + v1 + "</span>"
        } else {
            return "<span>" + v1 + "</span>"
        }
    },
    getScroll: function (node) {
        var doc = node ? node.ownerDocument : document;
        var top = doc.documentElement.scrollTop || doc.body.scrollTop;
        var left = doc.documentElement.scrollLeft || doc.body.scrollLeft;
        return {
            "x": left,
            "y": top
        }
    },
    findPos: function (obj) {
        var curleft = obj.offsetLeft || 0;
        var curtop = obj.offsetTop || 0;
        while (obj = obj.offsetParent) {
            curleft += eval(obj.offsetLeft);
            curtop += obj.offsetTop
        }
        return {
            "x": curleft,
            "y": curtop
        }
    },
    tools: {
        loadJs: function (url, charset, callback) {
            var _js = document.createElement('script');
            var _this = this;
            if (!(charset == null || charset == '')) {
                _js.setAttribute('charset', charset)
            }
            _js.setAttribute('type', 'text/javascript');
            _js.setAttribute('src', url);
            document.getElementsByTagName('head')[0].appendChild(_js);
            _js.onload = _js.onreadystatechange = function () {
                if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") {
                    callback(_js);
                    _this.removeJs(_js)
                } else { }
            }
        },
        removeJs: function (o) {
            var _js = (typeof o == "string") ? document.getElementById(o) : o;
            _js.onload = _js.onreadystatechange = null;
            try {
                _js.parentNode.removeChild(_js)
            } catch (e) { }
        },
        dataFormat: function (date, part) {
            if (typeof date == "undefined") date = new Date();
            var datecopy;
            var redate = "";
            part = (part == null) ? "yyyy-MM-dd HH:mm:ss" : part;
            var y = date.getFullYear();
            var M = date.getMonth() + 1;
            var d = date.getDate();
            var H = date.getHours();
            var m = date.getMinutes();
            var s = date.getSeconds();
            var MM = (M > 9) ? M : "0" + M;
            var dd = (d > 9) ? d : "0" + d;
            var HH = (H > 9) ? H : "0" + H;
            var mm = (m > 9) ? m : "0" + m;
            var ss = (s > 9) ? s : "0" + s;
            redate = part.replace("yyyy", y).replace("MM", MM).replace("dd", dd).replace("HH", HH).replace("mm", mm).replace("ss", ss).replace("M", M).replace("d", d).replace("H", H).replace("m", m).replace("s", s);
            return redate
        },
        browser: (function () {
            var ua = window.navigator.userAgent.toLowerCase();
            var b = {
                msie: /msie/.test(ua) && !/opera/.test(ua),
                opera: /opera/.test(ua),
                safari: /webkit/.test(ua) && !/chrome/.test(ua),
                firefox: /firefox/.test(ua),
                chrome: /chrome/.test(ua)
            };
            var vMark = "";
            for (var i in b) {
                if (b[i]) {
                    vMark = "safari" == i ? "version" : i;
                    break
                }
            }
            b.version = vMark && RegExp("(?:" + vMark + ")[\\/: ]([\\d.]+)").test(ua) ? RegExp.$1 : "0";
            b.ie = b.msie;
            b.ie6 = b.msie && parseInt(b.version, 10) == 6;
            b.ie7 = b.msie && parseInt(b.version, 10) == 7;
            b.ie8 = b.msie && parseInt(b.version, 10) == 8;
            return b
        })(),
        getScrollTop: function (node) {
            var doc = node ? node.ownerDocument : document;
            return doc.documentElement.scrollTop || doc.body.scrollTop
        },
        getScrollLeft: function (node) {
            var doc = node ? node.ownerDocument : document;
            return doc.documentElement.scrollLeft || doc.body.scrollLeft
        },
        contains: document.defaultView ?
            function (a, b) {
                return !!(a.compareDocumentPosition(b) & 16)
            } : function (a, b) {
            return a != b && a.contains(b)
        },
        rect: function (node) {
            var left = 0,
                top = 0,
                right = 0,
                bottom = 0,
                B = this.browser,
                D = this;
            if (!node.getBoundingClientRect || B.ie8) {
                var n = node;
                while (n) {
                    left += n.offsetLeft,
                        top += n.offsetTop;
                    n = n.offsetParent
                };
                right = left + node.offsetWidth;
                bottom = top + node.offsetHeight
            } else {
                var rect = node.getBoundingClientRect();
                left = right = D.getScrollLeft(node);
                top = bottom = D.getScrollTop(node);
                left += rect.left;
                right += rect.right;
                top += rect.top;
                bottom += rect.bottom
            };
            return {
                "left": left,
                "top": top,
                "right": right,
                "bottom": bottom
            }
        },
        wheel: function (callback) {
            if (window.addEventListener) {
                window.addEventListener('DOMMouseScroll', callback, false)
            } else {
                window.onmousewheel = document.onmousewheel = callback
            }
        },
        GetQueryStr: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURIComponent(r[2]);
            return null
        },
        OptionValue: function (s, v) {
            for (var i = 0; i < s.options.length; i++) {
                if ((s.options[i].value.indexOf(v)) != -1 && v != '') {
                    s.options[i].selected = true;
                    break
                }
            }
        }
    }
});
Function.prototype.bind = function () {
    var __m = this,
        object = arguments[0],
        args = new Array();
    for (var i = 1; i < arguments.length; i++) {
        args.push(arguments[i])
    }
    return function () {
        return __m.apply(object, args)
    }
};