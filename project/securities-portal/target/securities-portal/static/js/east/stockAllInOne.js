function $(a) {
    return document.getElementById(a)
}
function $(a) {
    return document.getElementById(a)
}
function $(a) {
    return document.getElementById(a)
}
function $C(a) {
    return document.createElement(a)
}
function getMarket(a) {
    var b = a.substring(0, 1)
        , c = a.substring(0, 3);
    return "5" == b || "6" == b || "9" == b ? "01" : "009" == c || "126" == c || "110" == c || "201" == c || "202" == c || "203" == c || "204" == c ? "01" : "02"
}
function $(a) {
    return document.getElementById(a)
}
function $C(a) {
    return document.createElement(a)
}
var note, tixing, allTiXing, dgtl, pages;
!function () {
    window.onworker = function (a, b, c) {
        window.attachEvent ? a.attachEvent("on" + b, c) : "mouseenter" != b && "mouseleave" != b ? a.addEventListener(b, c) : (b = "mouseenter" == b ? "mouseover" : "mouseout",
            a["_E" + b] = function (b) {
                this.contains(b.relatedTarget) || c.call(a)
            }
            ,
            a.addEventListener(b, a["_E" + b]))
    }
        ,
        window.reworker = function (a, b, c) {
            window.detachEvent ? a.detachEvent("on" + b, c) : "mouseenter" != b && "mouseleave" != b ? a.removeEventListener(b, c) : (b = "mouseenter" == b ? "mouseover" : "mouseout",
            a["_E" + b] && a.removeEventListener(b, a["_E" + b]))
        }
}(),
    function () {
        window.EMui = {},
            EMui.confirm = function (a, b, c) {
                var d = this
                    , e = "<div class='win-df-btns'><em class='win-sure-btn'>确定</em><em class='win-cancel-btn'>取消</em></div>";
                d.confirmWin ? (d.confirmWin.updateTitle(a.title),
                    d.confirmWin.updateHtml("<div class='win-df-text'>" + a.html + "</div>" + e),
                    d.sureBtn = d.confirmWin.bodyDom.getElementsByTagName("em")[0],
                    d.cancelBtn = d.confirmWin.bodyDom.getElementsByTagName("em")[1]) : (d.confirmWin = new EMui.wins({
                    title: a.title,
                    html: "<div class='win-df-text'>" + a.html + "</div>" + e,
                    width: 300,
                    overlayer: !0,
                    top: 260
                }),
                    d.sureBtn = d.confirmWin.bodyDom.getElementsByTagName("em")[0],
                    d.cancelBtn = d.confirmWin.bodyDom.getElementsByTagName("em")[1]),
                    d.confirmWin.open(),
                    d.sureBtn.onclick = function () {
                        b && b(),
                            d.confirmWin.destory(),
                            d.confirmWin = null
                    }
                    ,
                    d.cancelBtn.onclick = function () {
                        c && c(),
                            d.confirmWin.destory(),
                            d.confirmWin = null
                    }
            }
            ,
            EMui.prompt = function (a, b, c) {
                var d = this
                    , e = "<div class='win-df-btns'><em class='win-sure-btn'>确定</em><em class='win-cancel-btn'>取消</em></div>";
                d.promptWin ? d.promptWin.updateTitle(a.title) : (d.promptWin = new EMui.wins({
                    title: a.title,
                    html: "<div class='win-df-text'><input type='text' style='padding: 2px; width: 180px' /> </div>" + e,
                    width: 300,
                    overlayer: !0,
                    top: 260
                }),
                    d.sureBtn = d.promptWin.bodyDom.getElementsByTagName("em")[0],
                    d.cancelBtn = d.promptWin.bodyDom.getElementsByTagName("em")[1],
                    d.inputDom = d.promptWin.bodyDom.getElementsByTagName("input")[0]),
                    d.promptWin.open(),
                    d.sureBtn.onclick = function () {
                        var a = d.inputDom.value;
                        b && b(a),
                            d.inputDom.value = "",
                            d.promptWin.destory(),
                            d.promptWin = null
                    }
                    ,
                    d.cancelBtn.onclick = function () {
                        var a = d.inputDom.value;
                        c && c(a),
                            d.inputDom.value = "",
                            d.promptWin.destory(),
                            d.promptWin = null
                    }
            }
            ,
            EMui.tip = function (a, b, c) {
                c = c || 1600;
                var d = this;
                d.tipWin ? (d.tipWin.updateTitle(b),
                    d.tipWin.updateHtml("<div class='win-df-text'>" + a + "</div>")) : d.tipWin = new EMui.wins({
                    title: b,
                    html: "<div class='win-df-text'>" + a + "</div>",
                    width: 240,
                    top: 260,
                    drag: !1
                }),
                    d.tipWin.open(),
                    setTimeout(function () {
                        d.tipWin.closed()
                    }, c)
            }
    }(),
    function (a) {
        function b(a) {
            var b = document.createElement("style");
            b.type = "text/css",
                b.styleSheet ? b.styleSheet.cssText = a : b.innerHTML = a,
                document.getElementsByTagName("head")[0].appendChild(b)
        }
        a.wins = function (a) {
            function p() {
                l.style.left = document.body.clientWidth / 2 - c.width / 2 + "px"
            }
            var d, e, f, g, h, i, j, k, l, m, n, o, q, r, s, v, c = {
                html: "<b>1</b>",
                configCss: "",
                width: 500,
                title: "测试弹窗",
                drag: !0,
                top: 120,
                closed: !0,
                overlayer: !1,
                closeFuc: null
            };
            for (d in a)
                c[d] = a[d];
            return c.configCss && b(c.configCss),
                e = this.cID = parseInt(1e4 * Math.random()),
                f = "div",
                g = document.createElement(f),
                g.className = "win-inner",
                g.style.display = "none",
                g.innerHTML = "<div id='darg" + e + "' class='win-inner-title'></div><div class='win-inner-body' id='" + "body" + e + "'></div><div class='win-closed-btn' id='" + "closed" + e + "'></div>",
                c.overlayer ? (g.style.display = "block",
                    h = document.createElement("div"),
                    h.style.cssText = "display:none;position:absolute;top:0;left:0;width:100%;height:100%;z-index:800",
                    i = document.createElement("div"),
                    i.style.cssText = "position:absolute;top:0;left:0;background:#000;opacity:0.3;filter:alpha(opacity=30);width:100%;height:100%;z-index:810",
                    h.appendChild(i),
                    h.appendChild(g),
                    h.style.height = Math.max(document.body.scrollHeight, document.documentElement.clientHeight) + "px",
                    window.onresize = function () {
                        h.style.height = Math.max(document.body.scrollHeight, document.documentElement.clientHeight) + "px"
                    }
                    ,
                    document.body.appendChild(h)) : document.body.appendChild(g),
                j = this,
                k = document.getElementById("body" + e),
                l = g,
                m = document.getElementById("darg" + e),
                m.innerHTML = c.title,
                n = document.getElementById("closed" + e),
                o = m,
                this.bodyDom = k,
                l.style.width = c.width + "px",
                l.style.top = c.top + "px",
            "string" == typeof c.html && (k.innerHTML = c.html),
                p(),
                this.updateTitle = function (a) {
                    m.innerHTML = a
                }
                ,
                this.updateHtml = function (a) {
                    k.innerHTML = a
                }
                ,
                this.closed = function () {
                    h ? h.style.display = "none" : l.style.display = "none",
                    c.closeFuc && c.closeFuc instanceof Function && c.closeFuc()
                }
                ,
                this.open = function () {
                    h ? h.style.display = "block" : l.style.display = "block",
                        l.style.top = (document.documentElement.scrollTop || document.body.scrollTop) + c.top + "px"
                }
                ,
                this.destory = function () {
                    h ? document.body.removeChild(h) : document.body.removeChild(l)
                }
                ,
                n.onclick = function () {
                    j.closed()
                }
                ,
            c.closed || (h ? h.style.display = "block" : l.style.display = "block"),
            c.drag && (q = 0,
                r = 0,
                document.body.onselectstart = function () {
                    return !1
                }
                ,
                s = o.parentNode,
                document.body.clientWidth,
                document.body.clientHeight,
                v = function (a) {
                    var b = a.clientX
                        , c = a.clientY;
                    (0 >= b || 0 >= c) && (window.attachEvent ? document.body.detachEvent("onmousemove", v, !1) : document.body.removeEventListener("mousemove", v, !1)),
                        s.style.left = b - q + "px",
                        s.style.top = c - r + "px"
                }
                ,
                window.attachEvent ? (o.attachEvent("onmousedown", function (a) {
                    document.body.onselectstart = function () {
                        return !1
                    }
                        ,
                        q = a.clientX - parseInt(s.style.left),
                        r = a.clientY - parseInt(s.style.top),
                        document.body.attachEvent("onmousemove", v, !1)
                }),
                    document.body.attachEvent("onmouseup", function () {
                        document.body.onselectstart = function () {
                            return !0
                        }
                            ,
                            document.body.detachEvent("onmousemove", v, !1)
                    }),
                    document.body.attachEvent("onmouseleave", function () {
                        document.body.detachEvent("onmousemove", v)
                    })) : (o.addEventListener("mousedown", function (a) {
                    document.body.onselectstart = function () {
                        return !1
                    }
                        ,
                        q = a.clientX - parseInt(s.style.left),
                        r = a.clientY - parseInt(s.style.top),
                        document.body.addEventListener("mousemove", v, !1)
                }),
                    document.body.addEventListener("mouseup", function () {
                        document.body.onselectstart = function () {
                            return !0
                        }
                            ,
                            document.body.removeEventListener("mousemove", v, !1)
                    }))),
                this
        }
    }(EMui),
    function (a) {
        function d(a) {
            for (var b = 0; myStock.stockList[b];) {
                if (myStock.stockList[b]._Code == a)
                    return myStock.stockList[b];
                b++
            }
            return 0
        }
        function e(a, b) {
            setCookie(a, b, 365)
        }
        function f(a, b) {
            for (var e, c = document.getElementById("rightTable").getElementsByTagName("i"), d = 0; c[d];) {
                if (c[d].getAttribute("scode") == b) {
                    e = c[d];
                    break
                }
                d++
            }
            e.className = a > 0 ? "note-on" : "note"
        }
        function g(a, b) {
            var d, c = {
                num: 0,
                price: 0,
                fee: 0
            };
            for (d in b)
                c[d] = b[d];
            setCookieD("myStock_Buy_" + a, c.num + "," + c.price + "," + c.fee, 365)
        }
        function h(a) {
            var b = getCookie("myStock_Buy_" + a);
            return b ? b.split(",")[1] : 0
        }
        function i(a) {
            var b = getCookie("myStock_Buy_" + a);
            return b ? b.split(",")[0] : 0
        }
        function k(a) {
            var b = getCookie("myStock_Buy_" + a);
            return b ? b.split(",")[2] : 0
        }
        function l(a) {
            return isNaN(unnoteNums.value) || isNaN(unnoteFee.value) || isNaN(unnotePrice.value) ? (EMui.tip("请输入合理的数字", "操作失败"),
                void 0) : (unnoteNums.value = parseFloat(unnoteNums.value),
                unnoteFee.value = parseFloat(unnoteFee.value),
                unnotePrice.value = parseFloat(unnotePrice.value),
                a._note = 0 == unnoteNums.value && 0 == unnoteFee.value && 0 == unnotePrice.value ? -1 : 1,
                f(a._Note, curUnNoteCode),
                a.parseCommonData(),
                g(curUnNoteCode, {
                    num: unnoteNums.value,
                    fee: unnoteFee.value,
                    price: unnotePrice.value
                }),
                myStock.getStockInfoList(myStock.funcName),
                void 0)
        }
        var b = "emhq_stock"
            , c = null;
        a.getCookie = function (a) {
            var c, d, e, b = document.cookie;
            for (a += "=",
                     c = 0; c < b.length;) {
                if (d = c + a.length,
                    b.substring(c, d) == a)
                    return e = b.indexOf(";", d),
                    -1 == e && (e = b.length),
                        unescape(b.substring(d, e));
                if (c = b.indexOf(" ", c) + 1,
                    0 == c)
                    break
            }
            return null
        }
            ,
            a.setCookie = function (a, b, c) {
                var d = new Date;
                d.setDate(d.getDate() + c),
                    document.cookie = a + "=" + escape(b) + ";domain=.eastmoney.com;path=/;" + (null == c ? "" : "; expires=" + d.toGMTString())
            }
            ,
            a.setCookieD = function (a, b, c) {
                var d = new Date;
                d.setDate(d.getDate() + c),
                    document.cookie = a + "=" + escape(b) + ";path=/;" + (null == c ? "" : "; expires=" + d.toGMTString())
            }
            ,
            a._gzPrice = h,
            a._gzNum = i,
            a._gzFee = k,
            a.unLogFuc = {
                cAddStock: function (a) {
                    var c = getCookie(b);//document.getElementById("myStockList").value;
                    if (c) {
                        if (c.indexOf(a.split("|")[0]) > -1)
                            return addhandlerCallback({
                                data: {
                                    msg: "该股票已被添加"
                                },
                                result: "-1"
                            }),
                                void 0;
                        e(b, a.split("|")[0] + "," + c),
                            addhandlerCallback({
                                data: {
                                    msg: "操作成功"
                                },
                                result: "1"
                            })
                    } else
                        e(b, a.split("|")[0]),
                            addhandlerCallback({
                                data: {
                                    msg: "操作成功"
                                },
                                result: "1"
                            })
                },
                cDelStock: function (a) {
                    var f, g, h, c = [], d = getCookie(b);
                    for (a = a.split(","),
                             j = 0; a[j];)
                        c.push(a[j].split("|")[0]),
                            j++;
                    if (d) {
                        for (f = d.split(","),
                                 g = 0,
                                 h = 0; c[h];) {
                            for (g = 0; f[g];)
                                f[g] == c[h] ? f.splice(g, 1) : g++;
                            h++
                        }
                        e(b, f.join(","))
                    }
                },
                cNotePadShow: function () {
                    null == c && (c = new EMui.wins({
                        title: "交易管理",
                        html: '<div class="unnote"><div class="unnote-header"><h5 id="unnote-name">东方财富</h5><small id="unnote-code">300059</small><b id="unnote-close" class="red">12.38</b></div><table class="unnote-table" cellpadding="0" cellspacing="0"><tr><th>数量</th><th>买入价</th><th>佣金‰</th><th>印花税‰</th></tr><tr><td><input id="unnote-nums" type="text" value="0" /></td><td><input id="unnote-price" type="text" value="0" /></td><td><input id="unnote-fee" type="text" value="0" /></td><td>1</td></tr></table><div class="unnote-btns"><span id="unnote-save">保存</span><span id="unnote-cancel">取消</span></div></div>',
                        overlayer: !0,
                        width: 480
                    }),
                        a.unnoteNums = document.getElementById("unnote-nums"),
                        a.unnotePrice = document.getElementById("unnote-price"),
                        a.unnoteFee = document.getElementById("unnote-fee"),
                        a.unnoteName = document.getElementById("unnote-name"),
                        a.unnoteCode = document.getElementById("unnote-code"),
                        a.unnoteClose = document.getElementById("unnote-close"),
                        document.getElementById("unnote-cancel").onclick = function () {
                            c.closed()
                        }
                        ,
                        document.getElementById("unnote-save").onclick = function () {
                            l(d(curUnNoteCode)),
                                c.closed()
                        }
                    );
                    var b = d(curUnNoteCode);
                    unnoteName.innerHTML = b._Name,
                        unnoteCode.innerHTML = curUnNoteCode,
                        unnoteClose.innerHTML = b.Close1,
                        unnotePrice.value = h(curUnNoteCode),
                        unnoteNums.value = i(curUnNoteCode),
                        unnoteFee.value = k(curUnNoteCode),
                        c.open()
                },
                cGetNote: function () { },
                cSaveList: function () {
                    for (var a = 0, c = []; myStock.allStockArray[a];)
                        c.push(myStock.allStockArray[a]._Code),
                            a++;
                    //e(b, c.join(","))
                    jQuery.ajax({
                        cache: true,
                        type: "POST",
                        url:"/stockeast/selfsave",
                        data:{codeList:c.join(",")}
                    })
                }
            }
    }(window),
    function (a) {
        var b, c, d, e, g, h, i;
        a.TYPE = "01",
            a.defaultKey = "CTALL",
            b = "stKred",
            c = "stKgreen",
            d = window.numFormatRule = {
                "default": function (a, b) {
                    return void 0 == a || a.toString().indexOf("-%") >= 0 ? "-" : (a = a.toString(),
                        a.indexOf("%") > 0 || a.indexOf("-") > 0 ? a : (a = parseFloat(a),
                            isNaN(a) ? "-" : Math.abs(a) > 1e8 ? (a / 1e8).toFixed(2) + "亿" : Math.abs(a) > 1e4 ? (a / 1e4).toFixed(2) + "万" : void 0 == b ? a.toFixed(2) : a.toFixed(b)))
                },
                to100: function (a) {
                    return d["default"](Math.floor(parseInt(a) / 100), 0)
                },
                toW: function (a) {
                    return a = parseFloat(a),
                        isNaN(a) ? "-" : a + "万"
                },
                toInt: function (a) {
                    return d["default"](parseInt(a), 0)
                },
                pass: function (a) {
                    return void 0 == a || isNaN(parseFloat(a) || a.indexOf("-%") >= 0) ? "-" : a
                }
            },
            e = {
                comparePreviousCloseNew: function () {
                    return 0 < parseFloat(this.ChangePercent) ? b : 0 > parseFloat(this.ChangePercent) ? c : ""
                },
                comparePreviousClose: function (a) {
                    return a = parseFloat(a),
                        a > parseFloat(this.PreviousClose.replace(/[^-\d.]/g, "")) ? b : a < parseFloat(this.PreviousClose.replace(/[^-\d.]/g, "")) ? c : ""
                },
                zf: function (a) {
                    return a = parseFloat(a),
                        a > 0 ? b : 0 > a ? c : ""
                },
                Green: function () {
                    return c
                },
                Red: function () {
                    return b
                }
            },
            window.allField = {
                Checkbox: {
                    name: "<input type='checkbox' />",
                    sort: 0
                },
                SuperStar: {
                    name: "标记",
                    sort: "0"
                },
                AboutLinker: {
                    name: "相关链接",
                    sort: 0
                },
                StockNote: {
                    name: "笔记"
                },
                StockAlert: {
                    name: "提醒"
                },
                StockDel: {
                    name: "删除"
                },
                StockGoLeft: {
                    name: "<span id='mainTableSLeft'></span>"
                },
                StockGoRight: {
                    name: "<span id='mainTableSRight'></span>"
                },
                MarketType: {
                    name: "市场",
                    sort: 1
                },
                Code: {
                    name: "代码",
                    sort: 1
                },
                Name: {
                    name: "名称",
                    sort: 0
                },
                Close: {
                    name: "最新",
                    sort: 1,
                    color: "comparePreviousCloseNew",
                    num: "pass"
                },
                ChangePercent: {
                    name: "涨幅",
                    sort: 1,
                    color: "zf"
                },
                Change: {
                    name: "涨跌",
                    sort: 1,
                    color: "zf"
                },
                Volume: {
                    name: "总手",
                    sort: 1,
                    num: "toInt"
                },
                Order: {
                    name: "现手",
                    sort: 1,
                    num: "toInt"
                },
                BuyPrice: {
                    name: "买入价",
                    sort: 1,
                    color: "comparePreviousClose"
                },
                SellPrice: {
                    name: "卖出价",
                    sort: 1,
                    color: "comparePreviousClose"
                },
                TurnoverRate: {
                    name: "换手",
                    sort: 1
                },
                Amount: {
                    name: "金额",
                    sort: 1,
                    color: "toyi"
                },
                PERation: {
                    name: "市盈率(动)",
                    sort: 1
                },
                High: {
                    name: "最高",
                    sort: 1,
                    color: "comparePreviousClose"
                },
                Low: {
                    name: "最低",
                    sort: 1,
                    color: "comparePreviousClose"
                },
                Open: {
                    name: "开盘",
                    sort: 1,
                    color: "comparePreviousClose"
                },
                PreviousClose: {
                    name: "昨收",
                    sort: 1
                },
                Speed: {
                    name: "涨速",
                    sort: 1,
                    color: "zf"
                },
                Amplitude: {
                    name: "振幅",
                    sort: 1
                },
                VolumeRate: {
                    name: "量比",
                    sort: 1
                },
                CommissionRate: {
                    name: "委比",
                    sort: 1,
                    color: "zf"
                },
                CommissionDifference: {
                    name: "委差",
                    sort: 1,
                    num: "to100",
                    color: "zf"
                },
                AveragePrice: {
                    name: "均价",
                    sort: 1,
                    color: "comparePreviousClose"
                },
                SellOrder: {
                    name: "内盘",
                    sort: 1,
                    color: "Green"
                },
                BuyOrder: {
                    name: "外盘",
                    sort: 1,
                    color: "Red"
                },
                OrderRate: {
                    name: "内外比",
                    sort: 1,
                    color: "zf"
                },
                BuyVolume1: {
                    name: "买一量",
                    sort: 1
                },
                SellVolume1: {
                    name: "卖一量",
                    sort: 1
                },
                PB: {
                    name: "市净率",
                    sort: 1
                },
                TotalCapital: {
                    name: "总股本",
                    sort: 1,
                    color: "toyi"
                },
                MarketValue: {
                    name: "总市值",
                    sort: 1,
                    color: "toyi"
                },
                FlowCapital: {
                    name: "流通股本",
                    sort: 1,
                    color: "toyi"
                },
                FlowCapitalValue: {
                    name: "流通市值",
                    sort: 1,
                    color: "toyi"
                },
                ChangePercent3Day: {
                    name: "3日涨幅",
                    sort: 1,
                    color: "zf"
                },
                ChangePercent6Day: {
                    name: "6日涨幅",
                    sort: 1,
                    color: "zf"
                },
                TurnoverRate3Day: {
                    name: "3日换手",
                    sort: 1
                },
                TurnoverRate6Day: {
                    name: "6日换手",
                    sort: 1
                },
                DDX: {
                    parentName: "当日资金流",
                    name: "DDX",
                    sort: 1,
                    color: "zf",
                    num: "pass"
                },
                DDY: {
                    name: "DDY",
                    sort: 1,
                    color: "zf",
                    num: "pass"
                },
                DDZ: {
                    name: "DDZ",
                    sort: 1,
                    num: "pass",
                    color: "zf"
                },
                DDX5: {
                    parentName: "5日资金流",
                    name: "5日DDX",
                    sort: 1,
                    color: "zf",
                    num: "pass"
                },
                DDY5: {
                    name: "5日DDY",
                    sort: 1,
                    color: "zf",
                    num: "pass"
                },
                DDX10: {
                    parentName: "10日资金流",
                    name: "10日DDX",
                    sort: 1,
                    color: "zf",
                    num: "pass"
                },
                DDY10: {
                    name: "10日DDY",
                    sort: 1,
                    color: "zf",
                    num: "pass"
                },
                DDXRed: {
                    parentName: "DDX飘红天数",
                    name: "连续",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                DDXRed5: {
                    name: "5日内",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                DDXRed10: {
                    name: "10日内",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                SBBuy: {
                    name: "特大买入",
                    sort: 1,
                    color: "zf"
                },
                SBSell: {
                    name: "特大卖出",
                    sort: 1,
                    color: "zf"
                },
                SBRate: {
                    name: "特大单净比",
                    sort: 1,
                    color: "zf"
                },
                BBuy: {
                    name: "大单买入",
                    sort: 1,
                    color: "zf"
                },
                BSell: {
                    name: "大单卖出",
                    sort: 1,
                    color: "zf"
                },
                BRate: {
                    name: "大单净比",
                    sort: 1,
                    color: "zf"
                },
                DayFlow: {
                    parentName: "增仓占比",
                    name: "增仓占比",
                    sort: 1,
                    color: "zf"
                },
                Ranking: {
                    name: "排名",
                    sort: 1,
                    num: "toInt"
                },
                RankingChange: {
                    name: "排名变化",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                DayFlow3: {
                    parentName: "3日增仓排名",
                    name: "增仓占比",
                    sort: 1,
                    color: "zf"
                },
                Ranking3: {
                    name: "排名",
                    sort: 1,
                    num: "toInt"
                },
                RankingChange3: {
                    name: "排名变化",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                ChangePercent3Day3: {
                    name: "涨幅",
                    sort: 1,
                    color: "zf"
                },
                DayFlow5: {
                    parentName: "5日增仓排名",
                    name: "增仓占比",
                    sort: 1,
                    color: "zf"
                },
                Ranking5: {
                    name: "排名",
                    sort: 1,
                    num: "toInt"
                },
                RankingChange5: {
                    name: "排名变化",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                ChangePercent5Day: {
                    name: "涨幅",
                    sort: 1,
                    color: "zf"
                },
                DayFlow10: {
                    parentName: "10日增仓排名",
                    name: "增仓占比",
                    sort: 1,
                    color: "zf"
                },
                Ranking10: {
                    name: "排名",
                    sort: 1,
                    num: "toInt"
                },
                RankingChange10: {
                    name: "排名变化",
                    sort: 1,
                    color: "zf",
                    num: "toInt"
                },
                ChangePercent10Day: {
                    name: "涨幅",
                    sort: 1,
                    color: "zf"
                },
                AllNum: {
                    name: "研报数",
                    sort: 1,
                    num: "toInt"
                },
                BNum: {
                    parentName: "机构投资评级家次（近六个月）",
                    name: "买入",
                    sort: 1,
                    num: "toInt"
                },
                HNum: {
                    name: "增持",
                    sort: 1,
                    num: "toInt"
                },
                NeuNum: {
                    name: "中性",
                    sort: 1,
                    num: "toInt"
                },
                RdNum: {
                    name: "减持",
                    sort: 1,
                    num: "toInt"
                },
                SellNum: {
                    name: "卖出",
                    sort: 1,
                    num: "toInt"
                },
                EpsWithLatestShare: {
                    parentName: "2013实际",
                    name: "收益",
                    sort: 1,
                    num: "pass"
                },
                Values2: {
                    parentName: "2014实际",
                    name: "收益",
                    sort: 1,
                    num: "pass"
                },
                PE2: {
                    name: "市盈率",
                    sort: 1,
                    num: "pass"
                },
                Values3: {
                    parentName: "2015预测",
                    name: "收益",
                    sort: 1,
                    num: "pass"
                },
                PE3: {
                    name: "市盈率",
                    sort: 1,
                    num: "pass"
                },
                Values4: {
                    parentName: "2016预测",
                    name: "收益",
                    sort: 1,
                    num: "pass"
                },
                PE4: {
                    name: "市盈率",
                    sort: 1,
                    num: "pass"
                },
                Values5: {
                    parentName: "2017预测",
                    name: "收益",
                    sort: 1,
                    num: "pass"
                },
                PE5: {
                    name: "市盈率",
                    sort: 1,
                    num: "pass"
                },
                LatestReportDate: {
                    name: "更新日期",
                    sort: 1
                },
                TotalShare: {
                    name: "总股本",
                    sort: 1,
                    color: "toyi"
                },
                TradableAShare: {
                    name: "流通A股",
                    sort: 1,
                    color: "toyi"
                },
                AverageShareHolderNumber: {
                    name: "人均持股数",
                    sort: 1,
                    num: "toInt"
                },
                LatestBasicEps: {
                    name: "每股收益",
                    sort: 1
                },
                LatestNetAssetPerShare: {
                    name: "每股净资产",
                    sort: 1
                },
                WeightedYieldOnNetAssets: {
                    name: "加权净资产收益率",
                    sort: 1
                },
                TotalOperatingIncome: {
                    name: "营业收入",
                    sort: 1,
                    color: "toyi"
                },
                IncomeYOYGrowthRate: {
                    name: "营业收入同比",
                    sort: 1
                },
                OperatingProfit: {
                    name: "营业利润",
                    sort: 1,
                    color: "toyi"
                },
                InvestmentIncome: {
                    name: "投资收益",
                    sort: 1,
                    color: "toyi"
                },
                TotalProfit: {
                    name: "利润总额",
                    sort: 1,
                    color: "toyi"
                },
                NetProfitAttributableToEquityHolderst: {
                    name: "净利润",
                    sort: 1,
                    color: "toyi"
                },
                NetProfitAttributableToEquityHoldersYOY: {
                    name: "净利润同比",
                    sort: 1
                },
                UndistributedProfit: {
                    name: "未分配利润",
                    sort: 1,
                    color: "toyi"
                },
                RetainedEarningsPerShare: {
                    name: "每股未分配利润",
                    sort: 1
                },
                SalesGrossMargin: {
                    name: "销售毛利率",
                    sort: 1
                },
                TotalAssets: {
                    name: "总资产",
                    sort: 1,
                    color: "toyi"
                },
                TotalCurrentAssets: {
                    name: "流动资产",
                    sort: 1,
                    color: "toyi"
                },
                FixedAssets: {
                    name: "固定资产",
                    sort: 1,
                    color: "toyi"
                },
                IntangibleAssets: {
                    name: "无形资产",
                    sort: 1,
                    color: "toyi"
                },
                TotalLiabilities: {
                    name: "总负债",
                    sort: 1,
                    color: "toyi"
                },
                TotalCurrentLiabilities: {
                    name: "流动负债",
                    sort: 1,
                    color: "toyi"
                },
                TotalNonCurrentLiabilities: {
                    name: "长期负债",
                    sort: 1,
                    color: "toyi"
                },
                AssetLiabilityRatio: {
                    name: "资产负债比率",
                    sort: 1
                },
                TotalShareholdersEquity: {
                    name: "股东权益",
                    sort: 1,
                    color: "toyi"
                },
                TotalShareholdersEquityDivTotalAssets: {
                    name: "股东权益比",
                    sort: 1
                },
                CapitalReserve: {
                    name: "公积金",
                    sort: 1,
                    color: "toyi"
                },
                LatestCapitalReservePerShare: {
                    name: "每股公积金",
                    sort: 1
                },
                TradableBShare: {
                    name: "流通B股",
                    sort: 1
                },
                HKListedShare: {
                    name: "H股",
                    sort: 1
                },
                IPODate: {
                    name: "上市日期",
                    sort: 1
                },
                BalFlowMain: {
                    name: "主力净流入",
                    sort: 1,
                    color: "zf",
                    num: "toW"
                },
                BalFlowRate: {
                    name: "集合竞价",
                    sort: 1,
                    num: "pass"
                },
                AmtOfBuy3: {
                    parentName: "实时超大单统计",
                    name: "流入",
                    sort: 1,
                    color: "zf"
                },
                AmtOfSel3: {
                    name: "流出",
                    sort: 1,
                    color: "Green"
                },
                AmtNet3: {
                    name: "净额",
                    sort: 1,
                    color: "zf",
                    num: "toW"
                },
                AmtNetRate3: {
                    name: "净占比",
                    sort: 1,
                    color: "zf"
                },
                AmtOfBuy2: {
                    parentName: "实时大单统计",
                    name: "流入",
                    sort: 1,
                    color: "zf"
                },
                AmtOfSel2: {
                    name: "流出",
                    sort: 1,
                    color: "Green"
                },
                AmtNet2: {
                    name: "净额",
                    sort: 1,
                    color: "zf",
                    num: "toW"
                },
                AmtNetRate2: {
                    name: "净占比",
                    sort: 1,
                    color: "zf"
                },
                AmtOfBuy1: {
                    parentName: "实时中单统计",
                    name: "流入",
                    sort: 1,
                    color: "zf"
                },
                AmtOfSel1: {
                    name: "流出",
                    sort: 1,
                    color: "Green"
                },
                AmtNet1: {
                    name: "净额",
                    sort: 1,
                    color: "zf",
                    num: "toW"
                },
                AmtNetRate1: {
                    name: "净占比",
                    sort: 1,
                    color: "zf"
                },
                AmtOfBuy0: {
                    parentName: "实时小单统计",
                    name: "流入",
                    sort: 1,
                    color: "zf"
                },
                AmtOfSel0: {
                    name: "流出",
                    sort: 1,
                    color: "Green"
                },
                AmtNet0: {
                    name: "净额",
                    sort: 1,
                    color: "zf",
                    num: "toW"
                },
                AmtNetRate0: {
                    name: "净占比",
                    sort: 1,
                    color: "zf"
                },
                LastBlankTd: {
                    name: "",
                    sort: 0
                },
                mrjj: {
                    name: "买入均价",
                    sort: 1,
                    title: "股票买入均价&#10;买入成本 ÷ 持有量"
                },
                cyl: {
                    name: "持有量(股)",
                    sort: 1,
                    num: "pass",
                    title: "当前股票持有量"
                },
                mrcb: {
                    name: "买入成本",
                    sort: 1,
                    title: "目前持有股票的买入总成本"
                },
                dqsz: {
                    name: "当前市值",
                    sort: 1,
                    title: "股票当前市值&#10;最新价 × 持有量"
                },
                mgyk: {
                    name: "每股盈亏",
                    sort: 1,
                    color: "zf",
                    title: "股票每股盈亏&#10;最新价 － 买入均价"
                },
                ykl: {
                    name: "盈亏率",
                    sort: 1,
                    color: "zf",
                    title: "股票盈亏率&#10;市值 ÷ 成本-1",
                    num: "pass"
                },
                fdyk: {
                    name: "浮动盈亏",
                    sort: 1,
                    color: "zf",
                    title: "股票浮动盈亏&#10;每股盈亏 × 持有量"
                },
                Close1: {
                    name: "最新",
                    sort: 1,
                    color: "comparePreviousCloseNew",
                    title: "股票最新价"
                }
            };
        for (g in allField)
            "Code" == g ? allField[g].name = "<a sortkey='_Code' href='javascript:void(0)'>" + allField[g].name + "</a>" : 1 == allField[g].sort && (h = allField[g].title,
                allField[g].name = h ? "<a title='" + h + "' sortkey='" + g + "' href='javascript:void(0)'>" + allField[g].name + "</a>" : "<a sortkey='" + g + "' href='javascript:void(0)'>" + allField[g].name + "</a>");
        window.stockClass = function (a) {
            var b = this;
            b._Code = a._Code || "-",
                b.data__Code = a._Code || "-",
                b._MarketType = a._MarketType.slice(1) || "-",
                b._Type = a._Type || "-",
                b.CollectionID = a.CollectionID || "-",
                b.NoteDom = null,
                b.RemindDom = null,
                b.DelDom = null,
                b.WorkID = b._Code + "|0" + b._MarketType + "|" + b._Type,
                b._Name = "-",
                b._star = a._star || -1,
                b._note = a._note || -1,
                b._alert = a._alert || -1,
                b.LastBlankTd = '<div style="width:72px">&nbsp;</div>',
                b.update = function (a) {
                    stockClass.call(this, a)
                }
                ,
                b.parseCommonData = function () {
                    b.SuperStar = "<i workID='" + b._Code + "|0" + b._MarketType + "|" + b._Type + "' class ='" + (1 == b._star ? "star-on" : "star") + "' Scode='" + b._Code + "' Smk='" + b._MarketType + "' Sty='" + b._Type + "'></i>",
                        b.Checkbox = "<input workID='" + b._Code + "|0" + b._MarketType + "|" + b._Type + "' type='checkbox' Scode='" + b._Code + "' Smk='" + b._MarketType + "' Sty='" + b._Type + "' />",
                        b.AboutLinker = "<a target='_blank' href='http://guba.eastmoney.com/list," + b._Code + ".html'>股吧</a> <a target='_blank' href='http://data.eastmoney.com/zjlx/" + b._Code + ".html'>资金</a> <a target='_blank' href='http://data.eastmoney.com/report/" + b._Code + ".html'>研报</a>",
                        b.StockNote = "<i class='" + (1 == b._note ? "note-on" : "note") + "' Scode='" + b._Code + "' Smk='" + b._MarketType + "' Sty='" + b._Type + "'></i>",
                        b.StockAlert = "<i class='" + (1 == b._alert ? "alert-on" : "alert") + "' Scode='" + b._Code + "' Smk='" + b._MarketType + "' Sty='" + b._Type + "'></i>",
                        b.StockDel = "<i class='del' Scode='" + b._Code + "' Smk='" + b._MarketType + "' Sty='" + b._Type + "'></i>",
                        b.Code = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Code + "</a>&nbsp;",
                        b.StockGoLeft = "&nbsp;",
                        b.StockGoRight = "&nbsp;"
                }
                ,
                this.addColor = function () {
                    var b, c, f, a = mainTableField[this._fucName];
                    for (b in a)
                        if ("LastBlankTd" != b && "extend" != b)
                            if (b.indexOf("COLSPAN") >= 0)
                                for (c in a[b])
                                    "extend" != c && (this["data_" + c] = this[c],
                                        f = allField[c].num || "default",
                                        this[c] = allField[c].color && e[allField[c].color] ? '<b class="' + e[allField[c].color].call(this, this[c]) + '">' + d[f](this[c]) + "</b>" : "<b>" + d[f](this[c]) + "</b>");
                            else
                                this["data_" + b] = this[b],
                                    f = allField[b].num || "default",
                                    this[b] = allField[b].color && e[allField[b].color] ? '<b class="' + e[allField[b].color].call(this, this[b]) + '">' + d[f](this[b]) + "</b>" : "<b>" + d[f](this[b]) + "</b>"
                }
                ,
                b.parseData = {
                    CTALL: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close = a[3],
                            b.ChangePercent = a[4],
                            b.Change = a[5],
                            b.Volume = a[6],
                            b.Order = a[7],
                            b.BuyPrice = a[8],
                            b.SellPrice = a[9],
                            b.Speed = a[10],
                            b.TurnoverRate = a[11],
                            b.Amount = a[12],
                            b.PERation = a[13],
                            b.High = a[14],
                            b.Low = a[15],
                            b.Open = a[16],
                            b.PreviousClose = a[17],
                            b.Amplitude = a[18],
                            b.VolumeRate = a[19],
                            b.CommissionRate = a[20],
                            b.CommissionDifference = a[21],
                            b.AveragePrice = a[22],
                            b.SellOrder = a[23],
                            b.BuyOrder = a[24],
                            b.OrderRate = a[25],
                            b.BuyVolume1 = a[26],
                            b.SellVolume1 = a[27],
                            b.PB = a[28],
                            b.TotalCapital = a[29],
                            b.MarketValue = a[30],
                            b.FlowCapital = a[31],
                            b.FlowCapitalValue = a[32],
                            b.ChangePercent3Day = a[33],
                            b.ChangePercent6Day = a[34],
                            b.TurnoverRate3Day = a[35],
                            b.TurnoverRate6Day = a[36],
                            b._fucName = "CTALL",
                            b.addColor()
                    },
                    CTDDE: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b.Code = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Code + "</a>&nbsp;",
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close = a[3],
                            b.ChangePercent = a[4],
                            b.DDX = a[5],
                            b.DDY = a[6],
                            b.DDZ = a[7],
                            b.DDX5 = a[8],
                            b.DDY5 = a[9],
                            b.DDX10 = a[10],
                            b.DDY10 = a[11],
                            b.DDXRed = a[12],
                            b.DDXRed5 = a[13],
                            b.DDXRed10 = a[14],
                            b.SBBuy = a[15],
                            b.SBSell = a[16],
                            b.SBRate = a[17],
                            b.BBuy = a[18],
                            b.BSell = a[19],
                            b.BRate = a[20],
                            b._fucName = "CTDDE",
                            b.addColor()
                    },
                    CTDF: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b.Code = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Code + "</a>&nbsp;",
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close = a[3],
                            b.DayFlow = a[4],
                            b.Ranking = a[5],
                            b.RankingChange = a[6],
                            b.ChangePercent = a[7],
                            b.DayFlow3 = a[8],
                            b.Ranking3 = a[9],
                            b.RankingChange3 = a[10],
                            b.ChangePercent3Day3 = a[11],
                            b.DayFlow5 = a[12],
                            b.Ranking5 = a[13],
                            b.RankingChange5 = a[14],
                            b.ChangePercent5Day = a[15],
                            b.DayFlow10 = a[16],
                            b.Ranking10 = a[17],
                            b.RankingChange10 = a[18],
                            b.ChangePercent10Day = a[19],
                            b._fucName = "CTDF",
                            b.addColor()
                    },
                    CTPF: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b.Code = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Code + "</a>&nbsp;",
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close = a[3],
                            b.ChangePercent = a[4],
                            b.AllNum = a[5],
                            b.BNum = a[6],
                            b.HNum = a[7],
                            b.NeuNum = a[8],
                            b.RdNum = a[9],
                            b.SellNum = a[10],
                            b.EpsWithLatestShare = a[11],
                            b.Values2 = a[12],
                            b.PE2 = a[13],
                            b.Values3 = a[14],
                            b.PE3 = a[15],
                            b.Values4 = a[16],
                            b.PE4 = a[17],
                            b.Values5 = a[18],
                            b.PE5 = a[19],
                            b._fucName = "CTPF",
                            b.addColor()
                    },
                    CTFA: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b.Code = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Code + "</a>&nbsp;",
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.LatestReportDate = a[3],
                            b.TotalShare = a[4],
                            b.TradableAShare = a[5],
                            b.AverageShareHolderNumber = a[6],
                            b.LatestBasicEps = a[7],
                            b.LatestNetAssetPerShare = a[8],
                            b.WeightedYieldOnNetAssets = a[9],
                            b.TotalOperatingIncome = a[10],
                            b.IncomeYOYGrowthRate = a[11],
                            b.OperatingProfit = a[12],
                            b.InvestmentIncome = a[13],
                            b.TotalProfit = a[14],
                            b.NetProfitAttributableToEquityHolderst = a[15],
                            b.NetProfitAttributableToEquityHoldersYOY = a[16],
                            b.UndistributedProfit = a[17],
                            b.RetainedEarningsPerShare = a[18],
                            b.SalesGrossMargin = a[19],
                            b.TotalAssets = a[20],
                            b.TotalCurrentAssets = a[21],
                            b.FixedAssets = a[22],
                            b.IntangibleAssets = a[23],
                            b.TotalLiabilities = a[24],
                            b.TotalCurrentLiabilities = a[25],
                            b.TotalNonCurrentLiabilities = a[26],
                            b.AssetLiabilityRatio = a[27],
                            b.TotalShareholdersEquity = a[28],
                            b.TotalShareholdersEquityDivTotalAssets = a[29],
                            b.CapitalReserve = a[30],
                            b.LatestCapitalReservePerShare = a[31],
                            b.TradableBShare = a[32],
                            b.HKListedShare = a[33],
                            b.IPODate = a[34],
                            b._fucName = "CTFA",
                            b.addColor()
                    },
                    CTBF: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b.Code = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Code + "</a>&nbsp;",
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close = a[3],
                            b.ChangePercent = a[4],
                            b.BalFlowMain = a[5],
                            b.BalFlowRate = a[6],
                            b.AmtOfBuy3 = a[7],
                            b.AmtOfSel3 = a[8],
                            b.AmtNet3 = a[9],
                            b.AmtNetRate3 = a[10],
                            b.AmtOfBuy2 = a[11],
                            b.AmtOfSel2 = a[12],
                            b.AmtNet2 = a[13],
                            b.AmtNetRate2 = a[14],
                            b.AmtOfBuy1 = a[15],
                            b.AmtOfSel1 = a[16],
                            b.AmtNet1 = a[17],
                            b.AmtNetRate1 = a[18],
                            b.AmtOfBuy0 = a[19],
                            b.AmtOfSel0 = a[20],
                            b.AmtNet0 = a[21],
                            b.AmtNetRate0 = a[22],
                            b._fucName = "CTBF",
                            b.addColor()
                    },
                    YKYL: function (a) {
                        a = a.split(","),
                            b._MarketType = a[0],
                            b._Name = a[2],
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close1 = a[3],
                            b.ChangePercent = a[4],
                            b.mrjj = a[5],
                            b.cyl = a[6],
                            b.mrcb = a[7],
                            b.dqsz = a[8],
                            b.mgyk = a[9],
                            b.ykl = a[10],
                            b.fdyk = a[11],
                            b._fucName = "YKYL",
                            b.addColor()
                    },
                    DGTL: function () { },
                    CTF: function (a) {
                        a = a.split(","),
                            b._Name = a[2],
                            b.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + b._Code + "'>" + b._Code + "</a>&nbsp;",
                            b.Name = "<a target='_blank' href='http://quote.eastmoney.com/" + b._Code + ".html'>" + b._Name + "</a>&nbsp;",
                            b.Close = a[3],
                            b.Change = a[4],
                            b.ChangePercent = a[5],
                            b.BuyPrice = a[6],
                            b.SellPrice = a[7],
                            b.Order = a[8],
                            b.Volume = a[9],
                            b.Amount = a[10],
                            b.Open = a[11],
                            b.PreviousClose = a[12],
                            b.High = a[13],
                            b.Low = a[14],
                            b.TurnoverRate = a[15],
                            b.PERation = a[16],
                            b._fucName = "CTF",
                            b.addColor()
                    }
                },
                b.parseCommonData()
        }
            ,
            window.mainTableField = {
                CTALL: {
                    Close: {},
                    ChangePercent: {},
                    Change: {},
                    Volume: {},
                    Order: {},
                    BuyPrice: {},
                    SellPrice: {},
                    TurnoverRate: {},
                    Amount: {},
                    PERation: {},
                    High: {},
                    Low: {},
                    Open: {},
                    PreviousClose: {},
                    Speed: {},
                    Amplitude: {},
                    VolumeRate: {},
                    CommissionRate: {},
                    CommissionDifference: {},
                    AveragePrice: {},
                    SellOrder: {},
                    BuyOrder: {},
                    OrderRate: {},
                    BuyVolume1: {},
                    SellVolume1: {},
                    PB: {},
                    TotalCapital: {},
                    MarketValue: {},
                    FlowCapital: {},
                    FlowCapitalValue: {},
                    ChangePercent3Day: {},
                    ChangePercent6Day: {},
                    TurnoverRate3Day: {},
                    TurnoverRate6Day: {},
                    LastBlankTd: {}
                },
                CTDDE: {
                    Close: {},
                    ChangePercent: {},
                    COLSPAN1: {
                        DDX: {},
                        DDY: {},
                        DDZ: {}
                    },
                    COLSPAN2: {
                        DDX5: {},
                        DDY5: {}
                    },
                    COLSPAN3: {
                        DDX10: {},
                        DDY10: {}
                    },
                    COLSPAN4: {
                        DDXRed: {},
                        DDXRed5: {},
                        DDXRed10: {}
                    },
                    SBBuy: {},
                    SBSell: {},
                    SBRate: {},
                    BBuy: {},
                    BSell: {},
                    BRate: {},
                    LastBlankTd: {}
                },
                CTDF: {
                    Close: {},
                    COLSPAN1: {
                        DayFlow: {},
                        Ranking: {},
                        RankingChange: {},
                        ChangePercent: {}
                    },
                    COLSPAN2: {
                        DayFlow3: {},
                        Ranking3: {},
                        RankingChange3: {},
                        ChangePercent3Day3: {}
                    },
                    COLSPAN3: {
                        DayFlow5: {},
                        Ranking5: {},
                        RankingChange5: {},
                        ChangePercent5Day: {}
                    },
                    COLSPAN4: {
                        DayFlow10: {},
                        Ranking10: {},
                        RankingChange10: {},
                        ChangePercent10Day: {}
                    },
                    LastBlankTd: {}
                },
                CTPF: {
                    Close: {},
                    ChangePercent: {},
                    AllNum: {},
                    COLSPAN1: {
                        BNum: {},
                        HNum: {},
                        NeuNum: {},
                        RdNum: {},
                        SellNum: {}
                    },
                    COLSPAN2: {
                        EpsWithLatestShare: {}
                    },
                    COLSPAN3: {
                        Values2: {},
                        PE2: {}
                    },
                    COLSPAN4: {
                        Values3: {},
                        PE3: {}
                    },
                    COLSPAN5: {
                        Values4: {},
                        PE4: {}
                    },
                    COLSPAN6: {
                        Values5: {},
                        PE5: {}
                    },
                    LastBlankTd: {}
                },
                CTFA: {
                    LatestReportDate: {},
                    TotalShare: {},
                    TradableAShare: {},
                    AverageShareHolderNumber: {},
                    LatestBasicEps: {},
                    LatestNetAssetPerShare: {},
                    WeightedYieldOnNetAssets: {},
                    TotalOperatingIncome: {},
                    IncomeYOYGrowthRate: {},
                    OperatingProfit: {},
                    InvestmentIncome: {},
                    TotalProfit: {},
                    NetProfitAttributableToEquityHolderst: {},
                    NetProfitAttributableToEquityHoldersYOY: {},
                    UndistributedProfit: {},
                    RetainedEarningsPerShare: {},
                    SalesGrossMargin: {},
                    TotalAssets: {},
                    TotalCurrentAssets: {},
                    FixedAssets: {},
                    IntangibleAssets: {},
                    TotalLiabilities: {},
                    TotalCurrentLiabilities: {},
                    TotalNonCurrentLiabilities: {},
                    AssetLiabilityRatio: {},
                    TotalShareholdersEquity: {},
                    TotalShareholdersEquityDivTotalAssets: {},
                    CapitalReserve: {},
                    LatestCapitalReservePerShare: {},
                    TradableBShare: {},
                    HKListedShare: {},
                    IPODate: {},
                    LastBlankTd: {}
                },
                CTBF: {
                    Close: {},
                    ChangePercent: {},
                    BalFlowMain: {},
                    BalFlowRate: {},
                    COLSPAN1: {
                        AmtOfBuy3: {},
                        AmtOfSel3: {},
                        AmtNet3: {},
                        AmtNetRate3: {}
                    },
                    COLSPAN2: {
                        AmtOfBuy2: {},
                        AmtOfSel2: {},
                        AmtNet2: {},
                        AmtNetRate2: {}
                    },
                    COLSPAN3: {
                        AmtOfBuy1: {},
                        AmtOfSel1: {},
                        AmtNet1: {},
                        AmtNetRate1: {}
                    },
                    COLSPAN4: {
                        AmtOfBuy0: {},
                        AmtOfSel0: {},
                        AmtNet0: {},
                        AmtNetRate0: {}
                    },
                    LastBlankTd: {}
                },
                YKYL: {
                    Close1: {},
                    mrjj: {},
                    cyl: {},
                    mrcb: {},
                    dqsz: {},
                    mgyk: {},
                    ykl: {},
                    fdyk: {},
                    LastBlankTd: {}
                },
                CTF: {
                    Close: {},
                    ChangePercent: {},
                    Change: {},
                    Volume: {},
                    Order: {},
                    BuyPrice: {},
                    SellPrice: {},
                    TurnoverRate: {},
                    Amount: {},
                    PERation: {},
                    High: {},
                    Low: {},
                    Open: {},
                    PreviousClose: {},
                    LastBlankTd: {}
                }
            };
        for (i in allField)
            !stockClass.prototype[i] && (stockClass.prototype[i] = "-")
    }(window),
    function (a) {
        function e(a) {
            1 == a.result ? "" != a.data.msg ? EMui.tip(a.data.msg, "操作成功") : EMui.tip("操作成功", "操作成功") : "" != a.data.msg ? EMui.tip(a.data.msg, "操作失败") : EMui.tip("操作失败", "操作失败")
        }
        var b = !0
            , c = !1
            , d = function () {
                if (!c) {
                    c = !0;
                    var a = document.getElementById("addLot");
                    a.innerHTML = "为什么我的自选股不见了",
                        a.setAttribute("id", "n"),
                        //document.getElementById("backToMars").setAttribute("href", "http://quote.eastmoney.com/favor/old1.html"),
                        document.getElementById("loginZX").style.display = "none",
                        document.getElementById("unloginZX").style.display = "block",
                        document.getElementById("unLoginHideLink").style.display = "none",
                        document.getElementById("addCollectionBtn").style.display = "none",
                        document.getElementById("moveLotOfStock").style.display = "none",
                        a.onclick = function () {
                            window.open("http://quote.eastmoney.com/help/favor.html")
                        }
                }
            }
            ;
        a.mySelectCollection = function (c) {
            var e, f, g;
            if (c.result && 1 == c.result) {
                for (usefulDom.tab1.innerHTML = "",
                         usefulDom.moreCollect.innerHTML = "",
                         usefulDom.allSC.innerHTML = "",
                         e = c.data.order.split(","),
                         myStock.collectionList = {},
                         f = 0; f < e.length; f++)
                    c.data[e[f]][0] = decodeURI(c.data[e[f]][0]),
                        myStock.collectionList[e[f]] = {
                            _name: c.data[e[f]][0],
                            _lid: e[f],
                            _num: c.data[e[f]][1]
                        };
                if(("CTALL" == myStock.funcName || "CTF" == myStock.funcName) && (myStock.funcName = defaultKey))
                {
                    myStock.fillCollection();
                    myStock.NobleCollectID = e[0];
                    var ct = getCookie("ct");
                    var uidal = getCookie("uidal");
                    var ut = getCookie("ut");
                    var pi = getCookie("pi");
                    if (ct && uidal && ut && pi) {
                        myStock.loginState = !0;
                    } else {
                        myStock.loginState = 0;
                    }
                }

            } else
                b ? (a.defaultKey = "CTF",
                ("CTALL" == myStock.funcName || "CTF" == myStock.funcName) && (myStock.funcName = defaultKey),
                    d(),
                    g = "001",
                    myStock.collectionList[g] = {
                        _name: "自选股",
                        _lid: "001",
                        _num: 3
                    },
                    myStock.fillCollection(),
                    myStock.NobleCollectID = g) : window.location.href = "http://quote.eastmoney.com/favor/old.html"
        }
            ,
            a.getStockInfo = function (c) {
                var d, e, f, g, h, i, j;
                if (myStock.stockList.splice(0, myStock.stockList.length),
                        myStock.allStockArray.splice(0, myStock.allStockArray.length),
                    c.result && 1 == c.result)
                    if (d = [],
                            c.data.order) {
                        for (d = c.data.order.split(","),
                                 e = 0; e < d.length; e++)
                            f = d[e].split("|"),
                                g = c.data.star && c.data.star.indexOf(d[e]) >= 0 ? 1 : -1,
                                h = c.data.Notes && c.data.Notes.indexOf(d[e]) >= 0 ? 1 : -1,
                                myStock.allStockArray.push(new stockClass({
                                    _Code: f[0],
                                    _MarketType: f[1],
                                    _Type: f[2],
                                    _star: g,
                                    _note: h,
                                    _alert: -1,
                                    CollectionID: myStock.curCollectID
                                }));
                        myStock.pageIndex = 0,
                            myStock.loadPage(),
                            myStock.initPageNums()
                    } else
                        usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>",
                            usefulDom.morePage.style.display = "none";
                else if (b) {
                    //document.getElementById("myStockList").value.split(',')
                    if (d = ["002102", "002610", "002228"],
                            getCookie("emhq_stock"))
                        for (d = [],
                                 i = getCookie("emhq_stock").split(","),
                                 j = 0; i[j];)
                            d.push(i[j]),
                                j++;
                    for (e = 0; e < d.length; e++)
                        d[e] = d[e].replace('[','').replace(']','').trim(),
                        myStock.allStockArray.push(new stockClass({
                            _Code: d[e],
                            _MarketType: a.codeToMarket(d[e]),
                            _Type: "02",
                            _star: -1,
                            _note: -1,
                            _alert: -1,
                            CollectionID: myStock.curCollectID
                        }));
                    myStock.pageIndex = 0,
                        myStock.loadPage(),
                        myStock.initPageNums()
                } else
                    window.location.href = "http://quote.eastmoney.com/favor/old.html"
            }
            ,
            a.getStockFullInfo = function (a, b) {
                var c, d, e, f, g, h, i, j, k, l, m, n, o, p;
                if (!a[0] || void 0 == a[0].stats || a[0].stats) {
                    if ("YKYL" == myStock.funcName) {
                        c = [];
                        for (d in allNote.data)
                            for (e = 0; e < a.length; e++)
                                if (f = a[e].split(","),
                                    d.split("|")[0] == f[1])
                                    if (g = [],
                                        "" == allNote.data[d].node) {
                                        for (h = 0; 5 > h; h++)
                                            g.push(f[h]);
                                        for (h = 0; 7 > h; h++)
                                            g.push(0);
                                        c.push(g.join(","))
                                    } else {
                                        for (i = allNote.data[d].node.split("|"),
                                                 g = [],
                                                 h = 0; 5 > h; h++)
                                            g.push(f[h]);
                                        g.push(i[1]),
                                            g.push(i[0]),
                                            j = parseFloat(i[1] * i[0]) + parseFloat(i[3]) + parseFloat(i[1] * i[0]) * (parseFloat(i[2]) + parseFloat(i[4])) / 1e3,
                                            g.push(j.toFixed(2)),
                                            k = 0 == f[3] ? f[17] : f[3],
                                            l = parseFloat(i[0] * k),
                                            g.push(l.toFixed(2)),
                                            m = parseFloat(k - i[1]),
                                        (0 == i[1] || 0 == i[0]) && (m = 0),
                                            g.push(m.toFixed(2) || 0),
                                            0 == i[1] ? n = 0 : (n = 0 == j ? 0 : (100 * (l / j - 1)).toFixed(2),
                                                n = n || 0),
                                            g.push(n + "%"),
                                            o = m * i[0],
                                            g.push(o.toFixed(2)),
                                            c.push(g.join(","))
                                    }
                        a = c
                    }
                    for (e = 0; e < a.length; e++)
                        for (p = a[e].split(","),
                                 d = 0; d < myStock.stockList.length; d++)
                            if (p[0] == myStock.stockList[d]._MarketType && p[1] == myStock.stockList[d]._Code) {
                                myStock.stockList[d].parseData[myStock.funcName](a[e]);
                                break
                            }
                    void 0 == b && (usefulDom.allTable.scrollLeft = 0)
                }
            }
            ,
            a.addhandlerCallback = function (a) {
                var b, c;
                if (e(a),
                    1 == a.result) {
                    if (myStock.oldStockList.length > 0 && (myStock.stockList = myStock.oldStockList,
                            myStock.oldStockList.splice(0, myStock.oldStockList.length)),
                            b = myStock.toAddStockId.split("|"),
                            myStock.stockList.splice(myStock.starLength, 0, new stockClass({
                                _Code: b[0],
                                _MarketType: b[1],
                                _Type: b[2],
                                _star: -1,
                                _note: -1,
                                _alert: -1,
                                CollectionID: myStock.curCollectID
                            })),
                            myStock.allStockArray.splice(myStock.starLength, 0, new stockClass({
                                _Code: b[0],
                                _MarketType: b[1],
                                _Type: b[2],
                                _star: -1,
                                _note: -1,
                                _alert: -1,
                                CollectionID: myStock.curCollectID
                            })),
                            myStock.sortState = "none",
                            myStock.Drag = !0,
                            myStock.loadPage(),
                            usefulDom.allpics.style.display = "none",
                            usefulDom.allTable.style.display = "block",
                        "DGTL" == myStock.funcName)
                        return dgtl.init(!0),
                            void 0;
                    if (c = b[0].toString() + parseInt(b[1]).toString(),
                        "YKYL" == myStock.funcName)
                        return loadScript("https://myfavor.eastmoney.com/mystock?f=gsb&g=" + myStock.curCollectID + "&sc=" + myStock.toAddStockId + "&cb=NoteAllhandlerCallback&" + Math.random(), function () {
                            loadScript(hpUrl + "type=CT&cmd=" + c + "&sty=" + defaultKey + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function () {
                                myStock.fillAll(),
                                    myStock.getNews()
                            })
                        }),
                            void 0;
                    loadScript(hpUrl + "type=CT&cmd=" + c + "&sty=" + myStock.funcName + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function () {
                        myStock.fillAll(),
                            myStock.getNews()
                    })
                }
            }
            ,
            a.delhandlerCallback = function (a) {
                e(a),
                1 == a.result && (myStock.delElementFormList(myStock.curDelStockID),
                    myStock.sortState = "none",
                    myStock.Drag = !0,
                    "DGTL" == myStock.funcName ? dgtl.init(!0) : (myStock.loadPage(),
                        myStock.getStockInfoList(myStock.funcName)),
                    myStock.getNews())
            }
            ,
            a.NotehandlerCallback = function (a) {
                note.init(),
                1 == a.result && (note.fill(),
                    note.fillTable(a))
            }
            ,
            a.NoteAllhandlerCallback = function (a) {
                allNote = a
            }
            ,
            a.starCallback = function (a) {
                var b;
                if (a.result > 0) {
                    if (1 == a.result && (myStock.stockList[myStock.starLast]._star = myStock.starLastFuc),
                            myStock.stockList[myStock.starLast].parseCommonData(),
                        "none" != myStock.sortState)
                        return myStock.fillAll(),
                            void 0;
                    1 == myStock.starLastFuc ? (b = myStock.stockList[myStock.starLast],
                        myStock.stockList.splice(myStock.starLast, 1),
                        myStock.stockList.unshift(b)) : (b = myStock.stockList[myStock.starLast],
                        myStock.stockList.splice(myStock.starLast, 1),
                        myStock.stockList.push(b)),
                        myStock.fillAll()
                } else
                    e(a)
            }
            ,
            a.alertInit = function (a) {
                var b, c, d;
                if (myStock.firstBlood = !1,
                        myStock.cacheAlertData = a,
                    !document.getElementById("rightTable") || !a.result)
                    return !1;
                for (myStock.alertStock = a.result,
                         b = myStock.alertStock.join(","),
                         c = document.getElementById("leftTable").getElementsByTagName("i"),
                         d = 0; d < c.length; d++)
                    c[d].className.indexOf("alert") >= 0 && b.indexOf(c[d].getAttribute("scode")) >= 0 && (c[d].className = "alert-on")
            }
            ,
            a.alertInit1 = function () {
                var c, a = myStock.alertStock.join(","), b = document.getElementById("rightTable").getElementsByTagName("i");
                for (c = 0; c < b.length; c++)
                    b[c].className.indexOf("alert") >= 0 && a.indexOf(b[c].getAttribute("scode")) >= 0 && (b[c].className = "alert-on")
            }
            ,
            a.movehandlerCallback = function (a) {
                e(a),
                1 == a.result && myStock.initPage()
            }
            ,
            a.addCollectionCallback = function (a) {
                e(a),
                1 == a.result && myStock.initPage()
            }
            ,
            a.deleteCollectionCallback = function (a) {
                e(a),
                1 == a.result && myStock.initPage()
            }
            ,
            a.renameCollectionCallback = function (a) {
                e(a),
                1 == a.result && (myStock.curCollectDom.firstChild.nodeValue = myStock.curCollectDom.firstChild.nodeValue.replace(/[\w\u4e00-\u9fa5]+/, myStock.tryCollectionName.YO(4)))
            }
            ,
            a.saveNoteCallback = function (a) {
                e(a),
                "YKYL" == myStock.funcName && document.getElementById("refreshALLALLALL").onclick()
            }
            ,
            a.singleStock = function (a) {
                tixing.ob = a
            }
            ,
            a.addLothandlerCallback = function (a) {
                e(a),
                    myStock.targetCollectionID = myStock.curCollectID,
                    myStock.initPage()
            }
    }(window),
    function (a) {
        function c(a) {
            function b(a) {
                return 1 == a.length ? "0" + a : a
            }
            var c;
            return a.indexOf("-") > 0 ? (c = /\d{4}-(\d{1,2})-(\d{1,2})/.exec(a),
            b(c[1]) + "-" + b(c[2])) : a.indexOf("/") > 0 ? (c = /(\d{1,2})\/(\d{1,2})\/\d{4}/.exec(a),
            b(c[1]) + "-" + b(c[2])) : "00-00"
        }
        function h(a, b) {
            b = b || "cur";
            for (var c in a.parentNode.children)
                a.parentNode.children[c].className && a.parentNode.children[c].className.indexOf(b) >= 0 && (a.parentNode.children[c].className = a.parentNode.children[c].className.replace(b, ""));
            a.className += " " + b
        }
        function i(a) {
            var b = a.substr(0, 1)
                , c = a.substr(0, 3);
            return "5" == b || "6" == b || "9" == b ? "01" : "009" == c || "126" == c || "110" == c || "201" == c || "202" == c || "203" == c || "204" == c ? "01" : "02"
        }
        function j(a, b) {
            b.splice(0, b.length);
            for (var c = 0; c < a.length; c++)
                b.push(a[c])
        }
        function k(a) {
            switch (a) {
                case "up":
                case "down":
                    usefulDom.wawa.style.display = "block",
                        usefulDom.wawa.setAttribute("title", "升序或降序排序状态下不可拖动自选股");
                    break;
                default:
                    usefulDom.wawa.style.display = "none"
            }
        }
        function m() {
            login4ShowCB(function () {
                window.location.reload()
            })
        }
        var d, e, g, l, b = 465;
        a.getPosition = function (a) {
            if (a) {
                for (index = 0; a = a.previousSibling;)
                    1 == a.nodeType && index++;
                return index
            }
        }
            ,
        Array.indexOf || (Array.prototype.indexOf = function (a) {
            for (var b = 0; b < this.length; b++)
                if (this[b] == a)
                    return b;
            return -1
        }
        ),
            a.newsInterface = function (b) {
                a.parseItemData(b[2]),
                    a.parseItemReport(b[1]),
                    a.parseItemNews(b[0])
            }
            ,
            a.parseItemData = function (a) {
                var b, d;
                if (a && a.length > 0) {
                    for (b = "",
                             d = 0; d < a.length; d++)
                        b += "<li><span class='fr'>" + c(a[d].ShowTime) + "</span><a target='_blank' class='st-a-black' href='http://quote.eastmoney.com/" + a[d].Code + ".html'>[" + a[d].Codename + "]</a><a class='st-a-normal' target='_blank' href='" + a[d].Url + "' title='" + a[d].Title + "'>" + a[d].Title + "</a></li>";
                    usefulDom.newsData.innerHTML = b
                } else
                    usefulDom.newsData.innerHTML = "<li>暂无数据</li>"
            }
            ,
            a.parseItemNews = function (a) {
                var b, d;
                if (a && a.length > 0) {
                    for (b = "",
                             d = 0; d < a.length; d++)
                        b += "<li><span class='fr'>" + c(a[d].ShowTime) + "</span><a target='_blank' class='st-a-black' href='http://quote.eastmoney.com/" + a[d].Code + ".html'>[" + a[d].Codename + "]</a><a class='st-a-normal' target='_blank' href='" + a[d].Url + "' title='" + a[d].Title + "'>" + a[d].Title + "</a></li>";
                    usefulDom.newsNews.innerHTML = b
                } else
                    usefulDom.newsNews.innerHTML = "<li>暂无新闻</li>"
            }
            ,
            a.parseItemReport = function (a) {
                var b, d;
                if (a && a.length > 0) {
                    for (b = "",
                             d = 0; d < a.length; d++)
                        b += "<li><span class='fr'>" + c(a[d].ShowTime) + "</span><a target='_blank' class='st-a-black' href='" + (a[d].Url.indexOf("report") > 0 ? "http://data.eastmoney.com/report/" : "http://data.eastmoney.com/notice/") + a[d].Code + ".html'>[" + a[d].Codename + "]</a><a class='st-a-normal' target='_blank' href='" + a[d].Url + "' title='" + a[d].Title + "'>" + "<i style='border-radius: 2px; height: 18px; padding: 0 4px; display: inline-block; line-height: 18px; font-style: normal; background-color: #3c84e8; color: #fff; vertical-align: middle; margin-right: 6px;text-decoration: none'>" + (a[d].Url.indexOf("report") > 0 ? "研报" : "公告") + "</i>" + a[d].Title + "</a></li>";
                    usefulDom.newsRn.innerHTML = b
                } else
                    usefulDom.newsRn.innerHTML = "<li>暂无公告 / 研报</li>"
            }
            ,
            a.coverbg = function () {
                var a = document.createElement("div");
                a.id = "loader_all_bg",
                    _scrollWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth),
                    _scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight),
                    a.style.width = _scrollWidth + "px",
                    a.style.height = _scrollHeight + "px",
                    document.body.appendChild(a)
            }
            ,
            a.uncoverbg = function () {
                var a = document.getElementById("loader_all_bg");
                null != a && document.body.removeChild(a)
            }
            ,
            a.loadingGIF = document.getElementById("myStockLoadImg").value,
            a.usefulDom = {
                tab1: document.getElementById("myStockCollection"),
                allTable: document.getElementById("stockDataTables"),
                fucBtns: document.getElementById("callBackTypes"),
                newsNews: document.getElementById("newsNews"),
                newsMainPost: document.getElementById("newsMainPost"),
                newsData: document.getElementById("newsData"),
                newsRn: document.getElementById("newsRn"),
                sortInfo: document.getElementById("st-table-sortInfo"),
                allSC: document.getElementById("allSCollections"),
                moreCollect: document.getElementById("moreCollectionSelect"),
                note: document.getElementById("exChangeManage"),
                allpics: document.getElementById("pics"),
                wawa: document.getElementById("userReadUp"),
                morePage: document.getElementById("morePages"),
                totalWar: document.getElementById("gagagc")
            },
            a.stockUrl = "https://myfavor.eastmoney.com/mystock?",
            a.hpUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?ps=500&token=64a483cbad8b666efa51677820e6b21c&",
            a.newUrl = "http://iguba.eastmoney.com/api/MainPostForZxg.aspx?ps=30&codes=",
            a.newUrlMy = "http://183.136.164.50:35792/get.aspx?c=15&a=",
            String.prototype.YO = function (a) {
                return isNaN(a) ? void 0 : this.length > a ? this.slice(0, a) : this
            }
            ,
            d = {
                Checkbox: {},
                StockAlert: {},
                Code: {},
                Name: {},
                AboutLinker: {},
                StockGoLeft: {}
            },
            e = {
                StockGoRight: {},
                StockNote: {},
                StockDel: {}
            },
            a.Browser = {
                ie: /msie/.test(window.navigator.userAgent.toLowerCase()),
                moz: /gecko/.test(window.navigator.userAgent.toLowerCase()),
                opera: /opera/.test(window.navigator.userAgent.toLowerCase()),
                safari: /safari/.test(window.navigator.userAgent.toLowerCase())
            },
            a.loadScript = function (a, b) {
                var c = document.createElement("script");
                c.setAttribute("type", "text/javascript"),
                    c.setAttribute("src", a),
                    c.setAttribute("charset", "utf-8"),
                    document.getElementsByTagName("head")[0].appendChild(c),
                    Browser.ie ? c.onreadystatechange = function () {
                        ("loaded" == this.readyState || "complete" == this.readyState) && (c.parentNode.removeChild(c),
                        b && b())
                    }
                        : Browser.moz || Browser.opera ? c.onload = function () {
                        c.parentNode.removeChild(c),
                        b && b()
                    }
                        : (c.parentNode.removeChild(c),
                    b && b())
            }
            ,
            g = function () {
                function n(a, b) {
                    if (null == a || void 0 == a)
                        return !1;
                    if (null == b || void 0 == b)
                        return !0;
                    a = a.toString(),
                        b = b.toString();
                    var c = 1
                        , d = 1;
                    return isNaN(parseFloat(a)) ? !1 : isNaN(parseFloat(b)) ? !0 : (a = a.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                        b = b.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                    c * parseFloat(a) < d * parseFloat(b))
                }
                function o(a, b) {
                    if (null == a || void 0 == a)
                        return !1;
                    if (null == b || void 0 == b)
                        return !0;
                    a = a.toString(),
                        b = b.toString();
                    var c = 1
                        , d = 1;
                    return isNaN(parseFloat(a)) ? !1 : isNaN(parseFloat(b)) ? !0 : (a = a.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                        b = b.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                    c * parseFloat(a) >= d * parseFloat(b))
                }
                var g, l, c = this;
                c.loginState = !1,
                    c.showPageNum = 9,
                    c.pageSize = 100,
                    c.pageIndex = 0,
                    c.allStockArray = [],
                    c.stockIndex = 0,
                    c.firstBlood = !0,
                    c.collectionList = {},
                    c.stockList = [],
                    c.starLength = 0,
                    c.oldStockList = [],
                    c.funcName = "DGTL" == getCookie("funcName") ? defaultKey : getCookie("funcName") || defaultKey,
                    c.NobleCollectID = 0,
                    c.maxTabli = 6,
                    c.targetCollectionID = getCookie("curCollectID") || 0,
                    c.Drag = !0,
                    c.sortState = "none",
                    c.curCollectID = 0,
                    c.curCollectDom = null,
                    c.curDelStockID = "",
                    c.curNoteStockID = "",
                    c.curTiXingStockID = "",
                    c.lastSortIndex = 0,
                    c.starLast = 0,
                    c.alertStock = [],
                    c.vsIndex = 0,
                    c.jumpToDgtl = getCookie("jumpToDgtl") || !1,
                    c.editCollectDiv = document.createElement("div"),
                    c.editCollectDiv.className = "editCollection",
                    c.reNameCollectionDom = document.createElement("span"),
                    c.reNameCollectionDom.className = "renameCt",
                    c.reNameCollectionDom.innerHTML = "更改名称",
                    c.delCollectionDom = document.createElement("span"),
                    c.delCollectionDom.className = "deleteCt",
                    c.delCollectionDom.innerHTML = "删除组合",
                    c.editCollectDiv.appendChild(c.reNameCollectionDom),
                    c.editCollectDiv.appendChild(c.delCollectionDom),
                    c.fillCollection = function (a) {
                        var b, d;
                        a = a || 0,
                            usefulDom.tab1.innerHTML = "",
                            usefulDom.allSC.innerHTML = "",
                            b = 0,
                            document.getElementById("topmoreCollectionSelect").style.display = "none";
                        for (d in c.collectionList)
                            "extend" != d && (b < c.maxTabli ? (usefulDom.tab1.innerHTML += "<li title='" + c.collectionList[d]._name + "' lid='" + c.collectionList[d]._lid + "'>" + c.collectionList[d]._name.YO(4) + "</li>",
                                usefulDom.allSC.innerHTML += "<span lid='" + c.collectionList[d]._lid + "'>" + c.collectionList[d]._name + "</span>") : (usefulDom.allSC.innerHTML += "<span lid='" + c.collectionList[d]._lid + "'>" + c.collectionList[d]._name + "</span>",
                                usefulDom.moreCollect.innerHTML += "<span title='" + c.collectionList[d]._name + "' index='" + b + "' lid='" + c.collectionList[d]._lid + "'>" + c.collectionList[d]._name.YO(4) + "</span>",
                                document.getElementById("topmoreCollectionSelect").style.display = "block"),
                                b++);
                        return c.selectCollection(a),
                            c.initButtonCollection(),
                            c.initFucNameBtns(),
                            c.initMM(),
                            0 != c.targetCollectionID ? (c.resetLocation(),
                                void 0) : void 0
                    }
                    ,
                    c.initFucNameBtns = function () {
                        for (var a = usefulDom.fucBtns.getElementsByTagName("li"), b = 0; a[b];) {
                            if (a[b].getAttribute("callName") == c.funcName) {
                                h(a[b]);
                                break
                            }
                            b++
                        }
                    }
                    ,
                    c.initMM = function () {
                        var b, a = usefulDom.moreCollect.getElementsByTagName("span");
                        for (b = 0; b < a.length; b++)
                            a[b].onclick = function () {
                                var a = this.innerHTML
                                    , b = this.getAttribute("lid")
                                    , c = this.getAttribute("title")
                                    , d = usefulDom.tab1.lastChild;
                                this.innerHTML = d.innerHTML,
                                    this.setAttribute("lid", d.getAttribute("lid")),
                                    this.setAttribute("title", d.getAttribute("title")),
                                    d.innerHTML = a,
                                    d.setAttribute("lid", b),
                                    d.setAttribute("title", c),
                                    d.click()
                            }
                    }
                    ,
                    c.selectCollection = function (a) {
                        c.curCollectDom && (reworker(c.curCollectDom, "mouseenter", g),
                            reworker(c.curCollectDom, "mouseleave", l));
                        var b = usefulDom.tab1.children[a];
                        c.curCollectID = b.getAttribute("lid"),
                            c.curCollectDom = b,
                            h(c.curCollectDom, "cur"),
                        "" == c.editCollectDiv.innerHTML && (c.reNameCollectionDom = document.createElement("span"),
                            c.reNameCollectionDom.className = "renameCt",
                            c.reNameCollectionDom.innerHTML = "更改名称",
                            c.delCollectionDom = document.createElement("span"),
                            c.delCollectionDom.className = "deleteCt",
                            c.delCollectionDom.innerHTML = "删除组合",
                            c.editCollectDiv.appendChild(c.reNameCollectionDom),
                            c.editCollectDiv.appendChild(c.delCollectionDom)),
                            c.curCollectDom.appendChild(c.editCollectDiv),
                            c.oldStockList.slice(0, c.oldStockList.length),
                            c.Drag = !0,
                            c.sortState = "none",
                            usefulDom.sortInfo.innerHTML = c.textSort.none,
                            c.vsIndex = 0,
                            c.initEditCollection(),
                            c.getCollectionInfoById(c.curCollectID, function () {
                                c.initButtonGroup(),
                                    c.getStockInfoList(c.funcName),
                                    c.getNews()
                            })
                    }
                    ,
                    this.fillAll = function () {
                        var b, d, e;
                        if (0 == c.stockList.length)
                            return usefulDom.newsNews.innerHTML = "<li>暂无新闻</li>",
                                usefulDom.newsMainPost.innerHTML = "<li>暂无帖子</li>",
                                usefulDom.newsRn.innerHTML = "<li>暂无公告 / 研报</li>",
                                usefulDom.newsData.innerHTML = "<li>暂无数据</li>",
                                usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>",
                                void 0;
                        for (usefulDom.allTable.innerHTML = "",
                                 b = [],
                                 d = 0,
                                 d = c.fillMainTable(b, d),
                                 c.fillLeftTable(b, d),
                                 c.fillRightTable(b),
                                 usefulDom.allTable.innerHTML += b.join(""),
                                 this.tableColor(),
                                 c.initStockBtns(),
                                 c.initSortEvent(),
                                 c.vsIndex = 0,
                                 c.initVS(),
                                 c.starLength = 0,
                                 e = 0; e < c.stockList.length; e++)
                            1 == c.stockList[e]._star && c.starLength++;
                        c.curCollectID == c.NobleCollectID && c.initAlertData()
                    }
                    ,
                    this.fillLeftTable = function (a, b) {
                        var e, f, g;
                        a.push("<table id='leftTable' class='leftTable' cellpadding='0' cellspacing='0'><tr>");
                        for (e in d)
                            if ("extend" != e) {
                                if (this.curCollectID != this.NobleCollectID && "StockAlert" == e)
                                    continue; b == c.lastSortIndex ? a.push("<th index='" + b + "'>" + allField[e].name + c.upThSortClass() + "</th>") : 1 == allField[e].sort ? a.push("<th index='" + b + "'>" + allField[e].name + "<i>&nbsp;</i></th>") : a.push("<th index='" + b + "'>" + allField[e].name + "</th>"),
                                    b++
                            }
                        for (a.push("</tr>"),
                                 f = 0; f < c.stockList.length; f++) {
                            a.push("<tr index='" + f + "'>");
                            for (g in d)
                                if ("extend" != g) {
                                    if (this.curCollectID != this.NobleCollectID && "StockAlert" == g)
                                        continue; a.push("<td>" + c.stockList[f][g] + "</td>")
                                }
                            a.push("</tr>")
                        }
                        a.push("</table>")
                    }
                    ,
                    this.fillRightTable = function (a) {
                        var b, d, f;
                        a.push("<table id='rightTable' class='rightTable' cellpadding='0' cellspacing='0'><tr>");
                        for (b in e)
                            if (allField[b].name) {
                                if (this.curCollectID != this.NobleCollectID && "StockAlert" == b)
                                    continue; a.push("<th>" + allField[b].name + "</th>")
                            }
                        for (a.push("</tr>"),
                                 d = 0; d < c.stockList.length; d++) {
                            a.push("<tr>");
                            for (f in e)
                                if ("extend" != f) {
                                    if (this.curCollectID != this.NobleCollectID && "StockAlert" == f)
                                        continue; a.push("<td>" + c.stockList[d][f] + "</td>")
                                }
                            a.push("</tr>")
                        }
                        a.push("</table>")
                    }
                    ,
                    c.fillMainTable = function (a, b) {
                        var d, e, f, g, h, i, j, k, l;
                        a.push("<table id='mainTable' class='mainTable' cellpadding='0' cellspacing='0'><tr>");
                        for (d in mainTableField[c.funcName])
                            if ("extend" != d)
                                if (allField[d])
                                    b == c.lastSortIndex ? a.push("<th rowspan='2' class='k1' index='" + b + "'>" + allField[d].name + c.upThSortClass() + "</th>") : a.push("<th rowspan='2' class='k1' index='" + b + "'>" + allField[d].name + "<i>&nbsp;</i></th>"),
                                        b++;
                                else {
                                    e = "",
                                        f = 0,
                                        g = mainTableField[c.funcName],
                                        g = g[d];
                                    for (h in g)
                                        if ("extend" != h) {
                                            e = allField[h].parentName;
                                            break
                                        }
                                    for (h in g)
                                        "extend" != h && f++;
                                    a.push('<th class="k2" colspan="' + f + '">' + e + "</th>")
                                }
                        a.push("</tr><tr>");
                        for (d in mainTableField[c.funcName])
                            if (d.indexOf("COLSPAN") >= 0)
                                for (i in mainTableField[c.funcName][d])
                                    "extend" != i && (b == c.lastSortIndex ? a.push("<th index='" + b + "' height='20' class='k3'>" + allField[i].name + c.upThSortClass() + "</th>") : a.push("<th index='" + b + "' height='20' class='k3'>" + allField[i].name + "<i>&nbsp;</i></th>"),
                                        b++);
                        for (a.push("</tr>"),
                                 j = 0; j < c.stockList.length; j++) {
                            a.push("<tr workid='" + c.stockList[j].WorkID + "'>");
                            for (k in mainTableField[c.funcName])
                                if ("extend" != k)
                                    if (k.indexOf("COLSPAN") >= 0)
                                        for (l in mainTableField[c.funcName][k])
                                            "extend" != l && ("none" == c.sortState || c.lastKey != l ? a.push("<td>" + c.stockList[j][l] + "</td>") : a.push("<td style='background-color: #dcdcdc;'>" + c.stockList[j][l] + "</td>"));
                                    else
                                        "none" == c.sortState || c.lastKey != k ? a.push("<td>" + c.stockList[j][k] + "</td>") : a.push("<td style='background-color: #dcdcdc;'>" + c.stockList[j][k] + "</td>");
                            a.push("</tr>")
                        }
                        return a.push("</table>"),
                            b
                    }
                    ,
                    this.textSort = {
                        none: "默认排序中，点击表头可更改排序状态（拖动每行可以自定义排序）",
                        up: "升序排序中（非默认状态下不可拖动）",
                        down: "降序排序中（非默认状态下不可拖动）"
                    },
                    this.initSortEvent = function () {
                        function b(b) {
                            for (var g, d = document.getElementById(b).getElementsByTagName("a"), e = [], f = 0; d[f];)
                                d[f].getAttribute("sortkey") && e.push(d[f]),
                                    f++;
                            for (g in e)
                                e[g].onclick = function () {
                                    "none" == c.sortState && j(c.stockList, c.oldStockList),
                                    c.lastSortIndex != this.parentNode.getAttribute("index") && (c.sortState = "none"),
                                        c.sortState = a[c.sortState],
                                        c.Drag = "none" === c.sortState ? !0 : !1,
                                        usefulDom.sortInfo.innerHTML = c.textSort[c.sortState],
                                        k(c.sortState),
                                        c.lastSortIndex = this.parentNode.getAttribute("index"),
                                        c.sortStockListByKey(this.getAttribute("sortkey"))
                                }
                        }
                        var a = {
                            none: "up",
                            up: "down",
                            down: "none"
                        };
                        b("leftTable"),
                            b("rightTable"),
                            b("mainTable")
                    }
                    ,
                    c.initAlertData = function () {
                        c.firstBlood ? loadScript("http://mystock.eastmoney.com/Message.aspx?type=stockprice&cb=alertInit&" + Math.random()) : alertInit(c.cacheAlertData)
                    }
                    ,
                    c.resetLocation = function () {
                        var d, e, f, a = !0, b = !0;
                        if (0 != c.targetCollectionID) {
                            for (d = usefulDom.tab1.getElementsByTagName("li"),
                                     e = 0; e < d.length; e++)
                                d[e].getAttribute("lid") == c.targetCollectionID && (c.targetCollectionID = 0,
                                    d[e].onclick(),
                                    a = !1,
                                    b = !1);
                            if (a)
                                for (f = usefulDom.moreCollect.getElementsByTagName("span"),
                                         e = 0; e < f.length; e++)
                                    if (f[e].getAttribute("lid") == c.targetCollectionID) {
                                        c.targetCollectionID = 0,
                                            f[e].onclick(),
                                            b = !1;
                                        break
                                    }
                            b && (c.targetCollectionID = c.NobleCollectID,
                                usefulDom.tab1.firstChild.onclick())
                        }
                    }
                    ,
                    this.upThSortClass = function () {
                        var a = "<i></i>";
                        return "up" == c.sortState && (a = "<i class='iconupsort'></i>"),
                        "down" == c.sortState && (a = "<i class='icondownsort'></i>"),
                            a
                    }
                    ,
                    this.initVS = function () {
                        function d(a) {
                            var e, b = document.getElementById("mainTable").getElementsByTagName("td"), d = 0;
                            for (e = 0; a > e; e++)
                                d += b[e].offsetWidth;
                            return d
                        }
                        usefulDom.allTable.onselectstart = function () {
                            return !1
                        }
                            ,
                            c.vsIndex = 0,
                            document.getElementById("mainTable"),
                            document.getElementById("mainTableSLeft").onclick = function () {
                                return c.vsIndex <= 0 ? (usefulDom.allTable.scrollLeft = 0,
                                    void 0) : (c.vsIndex--,
                                    usefulDom.allTable.scrollLeft = d(c.vsIndex),
                                    void 0)
                            }
                            ,
                            document.getElementById("mainTableSRight").onclick = function () {
                                var a = document.getElementById("mainTable").getElementsByTagName("th");
                                c.vsIndex >= a.length - 1 || (c.vsIndex++,
                                    d(c.vsIndex + 1) + b >= document.getElementById("mainTable").offsetWidth ? (c.vsIndex--,
                                        usefulDom.allTable.scrollLeft = document.getElementById("mainTable").offsetWidth - b) : usefulDom.allTable.scrollLeft = d(c.vsIndex))
                            }
                    }
                    ,
                    this.sortStockListByKey = function (a) {
                        var d, e, b = null, f = c.stockList.length, g = c.stockList;
                        if ("none" == c.sortState)
                            return j(c.oldStockList, c.stockList),
                                c.sortStar(),
                                c.fillAll(),
                                void 0;
                        if ("up" == c.sortState)
                            for (d = 0; f - 2 >= d; d++)
                                for (e = f - 1; e >= 1; e--)
                                    n(g[e]["data_" + a], g[e - 1]["data_" + a]) && (b = g[e],
                                        g[e] = g[e - 1],
                                        g[e - 1] = b);
                        if ("down" == c.sortState)
                            for (d = f - 1; d >= 1; d--)
                                for (e = d - 1; e >= 0; e--)
                                    o(g[d]["data_" + a], g[e]["data_" + a]) && (b = g[d],
                                        g[d] = g[e],
                                        g[e] = b);
                        c.lastKey = a,
                            c.fillAll()
                    }
                    ,
                    this.tableColor = function () {
                        var a, b, d;
                        if (0 != c.stockList.length && void 0 != document.getElementById("leftTable"))
                            for (a = usefulDom.allTable.getElementsByTagName("table"),
                                     b = a[1].getElementsByTagName("tr").length,
                                     d = 1; b > d; d++)
                                0 == d % 2 ? (a[0].getElementsByTagName("tr")[d + 1].className = "bgcolor",
                                    a[1].getElementsByTagName("tr")[d].className = "bgcolor",
                                    a[2].getElementsByTagName("tr")[d].className = "bgcolor") : (a[0].getElementsByTagName("tr")[d + 1].className = "",
                                    a[1].getElementsByTagName("tr")[d].className = "",
                                    a[2].getElementsByTagName("tr")[d].className = "")
                    }
                    ,
                    this.getNews = function () {
                        if (0 == c.stockList.length)
                            return usefulDom.newsNews.innerHTML = "<li>暂无新闻</li>",
                                usefulDom.newsMainPost.innerHTML = "<li>暂无帖子</li>",
                                usefulDom.newsRn.innerHTML = "<li>暂无公告 / 研报</li>",
                                usefulDom.newsData.innerHTML = "<li>暂无数据</li>",
                                usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>",
                                void 0;
                        for (var b = [], d = 0; c.stockList[d];)
                            b.push(c.stockList[d]._Code),
                                d++;
                        loadScript(a.newUrlMy + b.join("|") + "&callback=newsInterface&" + Math.random()),
                            loadScript(a.newUrl + b.join(",") + "&" + Math.random(), c.getNewsCB)
                    }
                    ,
                    this.getNewsCB = function () {
                        var b, c, a = mainPostForZxg;
                        if (a)
                            if (a.mainPost && a.mainPost.length > 0) {
                                for (b = "",
                                         c = 0; c < a.mainPost.length && (b += "<li><span class='fr'>" + a.mainPost[c].time.slice(5, 10) + "</span><a target='_blank' class='st-a-black' href='http://guba.eastmoney.com/list," + a.mainPost[c].code + ".html'>[" + a.mainPost[c].name + "]</a><a class='st-a-normal' target='_blank' href='" + a.mainPost[c].articleUrl + "' title='" + a.mainPost[c].title + "'>" + a.mainPost[c].title + "</a></li>",
                                14 != c) ; c++)
                                    ;
                                usefulDom.newsMainPost.innerHTML = b
                            } else
                                usefulDom.newsMainPost.innerHTML = "<li>暂无帖子</li>";
                        else
                            alert("获取股吧新闻失败")
                    }
                    ,
                    c.initPage = function (a) {
                        a = a || 0,
                            c.getMyCollections()
                    }
                    ,
                    c.loadingShow = function () {
                        usefulDom.allTable.innerHTML = "<div class='stock-loading-div'><img src='" + a.loadingGIF + "'/></div>"
                    }
                    ,
                    this.initButtonGroup = function () {
                        var b, a = usefulDom.fucBtns.getElementsByTagName("li");
                        for (b in a)
                            a[b].onclick = function () {
                                var a = this.getAttribute("callName");
                                return 0 != c.loginState || "CTDDE" != a && "CTPF" != a && "CTFA" != a ? (c.loadingShow(),
                                    h(this),
                                    this.getAttribute("callName") ? (c.jumpToDgtl = !1,
                                        c.funcName = this.getAttribute("callName"),
                                        setCookie("funcName", c.funcName, 365),
                                        setCookie("jumpToDgtl", c.jumpToDgtl, 365),
                                        c.getStockInfoList(this.getAttribute("callName"))) : (setCookie("funcName", c.funcName, 365),
                                        setCookie("jumpToDgtl", c.jumpToDgtl, 365),
                                        c.jumpToDgtl = !0,
                                        dgtl.init(!0)),
                                    void 0) : (m(),
                                    void 0)
                            }
                    }
                    ,
                    c.initButtonCollection = function () {
                        var b, d, e, a = usefulDom.tab1.getElementsByTagName("li");
                        for (b in a)
                            a[b].onclick = function () {
                                c.loadingShow(),
                                    c.firstBlood = !0,
                                    c.oldStockList.splice(0, c.oldStockList.length),
                                    c.selectCollection(getPosition(this)),
                                    k(c.sortState),
                                    setCookie("curCollectID", c.curCollectID, 365)
                            }
                            ;
                        d = document.getElementById("moveLotOfStock").getElementsByTagName("span");
                        for (e in d)
                            d[e].onclick = function () {
                                c.moveStock(this.getAttribute("lid"), c.getCheckedString())
                            }
                    }
                    ,
                    this.getMyCollections = function () {
                        loadScript(a.stockUrl + "f=gg&cb=mySelectCollection&" + Math.random())
                    }
                    ,
                    this.getCollectionInfoById = function (b, d) {
                        0 == c.targetCollectionID && loadScript(a.stockUrl + "f=gs&cb=getStockInfo&g=" + b + "&" + Math.random(), d)
                    }
                    ,
                    this.getStockInfoList = function (b) {
                        var d, e, f, g, h, i, j, m;
                        if (usefulDom.allpics.style.display = "none",
                                usefulDom.allTable.style.display = "block",
                                a.usefulDom.totalWar.style.display = "none",
                                b = b || defaultKey,
                                c.funcName = b,
                            0 == c.stockList.length)
                            return usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>",
                                void 0;
                        if ("DGTL" == b)
                            return usefulDom.morePage.style.display = "none",
                                dgtl.init(!0),
                                void 0;
                        for (c.sortState = "none",
                                 k(c.sortState),
                             0 != c.oldStockList.length && (c.sortStockListByKey(1),
                                 c.Drag = "none" === c.sortState ? !0 : !1,
                                 usefulDom.sortInfo.innerHTML = c.textSort[c.sortState]),
                                 d = [],
                                 e = 0; e < c.stockList.length; e++)
                            d.push(c.stockList[e]._Code + parseInt(c.stockList[e]._MarketType).toString());
                        if (a.allNote = {
                                data: {}
                            },
                                !c.loginState)
                            for (f = 0; c.allStockArray[f];)
                                g = _gzNum(c.allStockArray[f]._Code),
                                    h = _gzPrice(c.allStockArray[f]._Code),
                                    i = _gzFee(c.allStockArray[f]._Code),
                                    c.allStockArray[f]._note = 0 == g && 0 == h && 0 == i ? -1 : 1,
                                    c.allStockArray[f].parseCommonData(),
                                    a.allNote.data[c.allStockArray[f].WorkID] = {
                                        node: g + "|" + h + "|0|" + i + "|1|0|1"
                                    },
                                    f++;
                        if ("YKYL" == b) {
                            if (c.loginState) {
                                for (j = [],
                                         e = 0; c.stockList[e];)
                                    j.push(c.stockList[e].WorkID),
                                        e++;
                                return m = "https://myfavor.eastmoney.com/mystock?f=gsb&g=" + c.curCollectID + "&sc=" + j + "&cb=NoteAllhandlerCallback&" + Math.random(),
                                    loadScript(m, function () {
                                        loadScript(a.hpUrl + "type=CT&cmd=" + d.join(",") + "&sty=" + defaultKey + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function () {
                                            "DGTL" != c.funcName && (c.fillAll(b),
                                                c.initPageNums(),
                                                c.totalWar(),
                                                a.usefulDom.totalWar.style.display = "block")
                                        })
                                    }),
                                    void 0
                            }
                            return loadScript(a.hpUrl + "type=CT&cmd=" + d.join(",") + "&sty=" + defaultKey + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function () {
                                "DGTL" != c.funcName && (c.fillAll(b),
                                    c.initPageNums(),
                                    c.totalWar(),
                                    a.usefulDom.totalWar.style.display = "block")
                            }),
                                void 0
                        }
                        loadScript(a.hpUrl + "type=CT&cmd=" + d.join(",") + "&sty=" + b + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function () {
                            "DGTL" != c.funcName && (c.fillAll(b),
                            c.curCollectID == c.NobleCollectID && alertInit1(),
                                c.initPageNums())
                        })
                    }
                    ,
                    c.addLot = function () {
                        var d, e, b = getCookie("emhq_stock");
                        if (!b)
                            return EMui.tip("没有股票", "操作失败"),
                                void 0;
                        for (b = b.split(","),
                                 d = "",
                                 e = 0; e < b.length; e++)
                            d += b[e] + "|" + i(b[e]) + "|" + "01,";
                        loadScript(a.stockUrl + "f=aslot&g=" + c.curCollectID + "&sc=" + d + "&cb=addLothandlerCallback&" + Math.random())
                    }
                    ,
                    g = function () {
                        c.editCollectDiv.style.display = "block"
                    }
                    ,
                    l = function () {
                        c.editCollectDiv.style.display = "none"
                    }
                    ,
                    this.initEditCollection = function () {
                        var b, d;
                        c.curCollectDom && (reworker(c.curCollectDom, "mouseenter", g),
                            reworker(c.curCollectDom, "mouseleave", l)),
                            usefulDom.tab1.getElementsByTagName("li"),
                            onworker(c.curCollectDom, "mouseenter", g),
                            onworker(c.curCollectDom, "mouseleave", l),
                            b = usefulDom.tab1.getElementsByTagName("span");
                        for (d in b)
                            b[d].onclick = function (a) {
                                if (a = a || window.event,
                                        window.event ? a.cancelBubble = !0 : a.stopPropagation(),
                                    this.className.indexOf("delete") >= 0) {
                                    if (0 == c.loginState)
                                        return m(),
                                            void 0;
                                    EMui.confirm({
                                        title: "确认删除？",
                                        html: "确认删除该组合"
                                    }, function () {
                                        myStock.delCollection(c.curCollectID)
                                    })
                                } else
                                    this.className.indexOf("rename") >= 0 && EMui.prompt({
                                        title: "请重新输入组合名称"
                                    }, function (a) {
                                        myStock.tryCollectionName = a,
                                            myStock.reNameCollection(c.curCollectID, a)
                                    })
                            }
                    }
                    ,
                    this.initStockBtns = function () {
                        var d, e, f, g, b = document.getElementById("rightTable").getElementsByTagName("i");
                        for (d in b)
                            b[d].onclick = function () {
                                if (this.className.indexOf("del") >= 0)
                                    return c.delStock(this.getAttribute("scode") + "|0" + this.getAttribute("smk") + "|" + this.getAttribute("sty")),
                                        void 0;
                                if (this.className.indexOf("note") >= 0) {
                                    if (c.loginState)
                                        return note.domPostion = a.getPosition(this.parentNode.parentNode),
                                            coverbg(),
                                            c.getNote(this.getAttribute("scode") + "|0" + this.getAttribute("smk") + "|" + this.getAttribute("sty"), this),
                                            void 0;
                                    a.curUnNoteCode = this.getAttribute("scode"),
                                        unLogFuc.cNotePadShow()
                                }
                            }
                            ;
                        e = document.getElementById("leftTable").getElementsByTagName("tr")[0],
                            e.getElementsByTagName("input")[0].onclick = function () {
                                var b, a = document.getElementById("leftTable").getElementsByTagName("input");
                                for (b in a)
                                    a[b].checked = this.checked
                            }
                            ,
                            f = document.getElementById("leftTable").getElementsByTagName("i");
                        for (g in f)
                            f[g].onclick = function () {
                                return this.className.indexOf("alert") >= 0 ? 0 == c.loginState ? (m(),
                                    void 0) : (tixing.domPostion = a.getPosition(this.parentNode.parentNode),
                                    tixing.type = 1,
                                    coverbg(),
                                    c.getTiXing(this.getAttribute("scode"), this.getAttribute("smk"), this),
                                    void 0) : (this.className.indexOf("star") >= 0 && (c.starLast = this.parentNode.parentNode.getAttribute("index"),
                                    loadScript(a.stockUrl + "f=mst&g=" + c.curCollectID + "&sc=" + this.getAttribute("workID") + "&cb=starCallback&" + Math.random()),
                                    c.starLastFuc = this.className.indexOf("on") > 0 ? -1 : 1),
                                    void 0)
                            }
                    }
                    ,
                    this.addCollection = function (b) {
                        loadScript(a.stockUrl + "f=ag&gn=" + encodeURI(b) + "&cb=addCollectionCallback&" + Math.random(), function () {
                            setCookie("curCollectID", c.curCollectID, 365)
                        })
                    }
                    ,
                    this.delCollection = function (b) {
                        loadScript(a.stockUrl + "f=dg&g=" + b + "&cb=deleteCollectionCallback&" + Math.random(), function () {
                            setCookie("curCollectID", c.curCollectID, 365)
                        })
                    }
                    ,
                    this.reNameCollection = function (b, c) {
                        loadScript(a.stockUrl + "f=mg&g=" + b + "&gn=" + encodeURI(c) + "&cb=renameCollectionCallback&" + Math.random())
                    }
                    ,
                    this.addStock = function (b) {
                        c.toAddStockId = b,
                            c.loginState ? loadScript(a.stockUrl + "f=as&g=" + c.curCollectID + "&sc=" + b + "&cb=addhandlerCallback&" + Math.random()) : unLogFuc.cAddStock(b)
                    }
                    ,
                    this.delStock = function (b) {
                        c.curDelStockID = b,
                            EMui.confirm({
                                title: "确认删除",
                                html: "确认删除选中股票？"
                            }, function () {
                                var b, d, e;
                                if ("undefined" == typeof c.curDelStockID || "" == c.curDelStockID || 0 == c.curDelStockID.split(",").length)
                                    return EMui.tip("请选择股票", "操作失败"),
                                        void 0;
                                if (c.curDelStockID.split(",").length > 1) {
                                    if (c.curCollectID == c.NobleCollectID)
                                        for (b = c.curDelStockID.split(","),
                                                 d = 0; d < b.length; d++)
                                            e = myStock.alertStock.indexOf(b[d].split("|")[0]),
                                            -1 != e && myStock.alertStock.splice(e, 1);
                                    c.loginState ? loadScript(a.stockUrl + "f=dslot&g=" + c.curCollectID + "&sc=" + c.curDelStockID + "&cb=delhandlerCallback&" + Math.random()) : (unLogFuc.cDelStock(c.curDelStockID),
                                        delhandlerCallback({
                                            data: {
                                                msg: "删除成功！"
                                            },
                                            result: 1
                                        }))
                                } else
                                    c.curCollectID == c.NobleCollectID && c.loginState && (b = myStock.alertStock.indexOf(c.curDelStockID.split("|")[0]),
                                    -1 != b && myStock.alertStock.splice(b, 1)),
                                        c.loginState ? loadScript(a.stockUrl + "f=ds&g=" + c.curCollectID + "&sc=" + c.curDelStockID + "&cb=delhandlerCallback&" + Math.random()) : (unLogFuc.cDelStock(c.curDelStockID),
                                            delhandlerCallback({
                                                data: {
                                                    msg: "删除成功！"
                                                },
                                                result: 1
                                            }))
                            })
                    }
                    ,
                    this.getNote = function (b, d) {
                        c.curNoteStockID = b,
                            note.code = b.split("|")[0],
                            note.currentDom = d,
                            loadScript(a.stockUrl + "f=gsb&g=" + c.curCollectID + "&sc=" + c.curNoteStockID + "&cb=NotehandlerCallback&" + Math.random())
                    }
                    ,
                    this.getTiXing = function (a, b, c) {
                        b = 2 == b ? 0 : b,
                            loadScript("http://mystock.eastmoney.com/Message.aspx?type=getsettings&code=" + b + "," + a + "&" + Math.random(), function () {
                                b = 0 == b ? 2 : b;
                                var d = hpUrl + "type=CT&cmd=" + a + b + "&sty=" + defaultKey + "&cb=singleStock&js=([(x)])&" + Math.random();
                                loadScript(d, function () {
                                    b = 2 == b ? 0 : b,
                                        tixing.code = a,
                                        tixing.market = b,
                                    c && (tixing.currentDom = c),
                                        tixing.init(),
                                        tixing.fill(data, a, b)
                                })
                            })
                    }
                    ,
                    this.loadPage = function () {
                        var a, b, d;
                        for (c.stockList.splice(0, c.stockList.length),
                                 c.oldStockList.splice(0, c.oldStockList.length),
                                 a = parseInt(c.pageIndex),
                                 b = a * c.pageSize,
                                 d = 0; c.allStockArray[b] && d < c.pageSize;)
                            c.stockList.push(c.allStockArray[b]),
                                b++,
                                d++;
                        c.sortStar()
                    }
                    ,
                    this.initPageNums = function () {
                        var a, b, e, f, g, h, i, j;
                        if (c.maxPageNum = Math.ceil(c.allStockArray.length / c.pageSize),
                                a = "",
                                b = 0,
                                e = !0,
                                f = !0,
                                g = parseInt(c.showPageNum / 2),
                            c.allStockArray.length <= c.pageSize)
                            return usefulDom.morePage.style.display = "none",
                                void 0;
                        for (usefulDom.morePage.style.display = "block",
                             0 == c.pageIndex || (a += "<span page='" + (c.pageIndex - 1) + "'>上一页</span>"),
                                 b = 1; b <= c.maxPageNum; b++)
                            h = b - 1 == c.pageIndex ? "cur" : "",
                                1 != b && b != c.maxPageNum ? b <= c.pageIndex - g ? e && (a += "<span page='none'>...</span>",
                                    e = !1) : b >= c.pageIndex - g && b <= c.pageIndex + g ? a += "<span class='" + h + "' page='" + (b - 1) + "'>" + b + "</span>" : b >= c.maxPageNum - g && f && (a += "<span page='none'>...</span>",
                                    f = !1) : a += "<span class='" + h + "' page='" + (b - 1) + "'>" + b + "</span>";
                        for (c.pageIndex >= c.maxPageNum - 1 || (a += "<span page='" + (c.pageIndex + 1) + "'>下一页</span>"),
                                 usefulDom.morePage.innerHTML = a,
                                 i = usefulDom.morePage.getElementsByTagName("span"),
                                 j = 0; i[j];)
                            i[j].onclick = function () {
                                isNaN(this.getAttribute("page")) || (c.pageIndex = parseInt(this.getAttribute("page")),
                                    c.loadPage(),
                                    c.getStockInfoList(c.funcName))
                            }
                                ,
                                j++
                    }
                    ,
                    this.moveStock = function (b, d) {
                        "" != d && void 0 != d && (c.toCollectID = b,
                            c.targetCollectionID = b,
                            loadScript(a.stockUrl + "f=ms&g1=" + c.curCollectID + "&g=" + b + "&sc=" + d + "&cb=movehandlerCallback&" + Math.random()))
                    }
                    ,
                    this.totalWar = function () {
                        function b(a) {
                            return null == a || void 0 == a ? 0 : (a = a.toString(),
                                isNaN(a) ? 0 : parseFloat(a))
                        }
                        for (var a = c.stockList, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, j = 0; a[d];)
                            e += b(a[d].data_cyl),
                                f += b(a[d].data_mrcb),
                                h += b(a[d].data_mgyk),
                                g += b(a[d].data_dqsz),
                                i += b(a[d].data_ykl),
                                j += b(a[d].data_fdyk),
                                d++;
                        document.getElementById("total_cyl").innerHTML = numFormatRule["default"](e, 0),
                            document.getElementById("total_mrcb").innerHTML = numFormatRule["default"](f),
                            document.getElementById("total_mgyk").innerHTML = numFormatRule["default"](h),
                            document.getElementById("total_dqsz").innerHTML = numFormatRule["default"](g),
                            document.getElementById("total_ykl").innerHTML = 0 == f ? "0%" : 100 * numFormatRule["default"](g / f - 1) + "%",
                            document.getElementById("total_fdyk").innerHTML = numFormatRule["default"](j)
                    }
                    ,
                    this.sortStar = function () {
                        for (var a = this.stockList.length, b = this.stockList, c = [], d = 0, e = []; b[d];)
                            1 == b[d]._star ? c.push(b[d]) : e.push(b[d]),
                                d++;
                        this.stockList.splice(0, a),
                            this.stockList = c.concat(e)
                    }
                    ,
                    this.delElementFormList = function (a) {
                        var b;
                        if (a) {
                            for (b = 0; b < c.allStockArray.length; b++)
                                a.indexOf(c.allStockArray[b].WorkID) >= 0 && (c.allStockArray.splice(b, 1),
                                    b--);
                            for (b = 0; b < c.stockList.length; b++)
                                a.indexOf(c.stockList[b].WorkID) >= 0 && (c.stockList.splice(b, 1),
                                    b--)
                        }
                    }
                    ,
                    this.getCheckedString = function () {
                        var a, b, c;
                        if (document.getElementById("leftTable")) {
                            for (a = document.getElementById("leftTable").getElementsByTagName("input"),
                                     b = [],
                                     c = 0; c < a.length; c++)
                                a[c] && a[c].getAttribute("workID") && a[c].checked && b.push(a[c].getAttribute("workID"));
                            return b.join(",")
                        }
                    }
                    ,
                    c.loadingShow(),
                    c.initPage(),
                    document.getElementById("refreshALLALLALL").onclick = function () {
                        window.location.reload()
                    }
            }
            ,
            a.myStock = new g,
            document.getElementById("delLotOfStock").onclick = function () {
                myStock.delStock(myStock.getCheckedString())
            }
            ,
            document.getElementById("addLot").onclick = function () {
                myStock.addLot()
            }
            ,
            document.getElementById("checkALL").onclick = function () {
                var a = document.getElementById("myOwnInputCode").value
                var dt=jQuery('#filter_dt').datebox('getValue');
                var url = "/stockeast/detail?stockCode=" + a;
                window.open(url,"_self")
                /*    , b = "http://quote.eastmoney.com/search.html?stockcode=" + a;
                window.open(b)*/
            }
            ,
            onworker(document.getElementById("topmoreCollectionSelect"), "mouseenter", function () {
                usefulDom.moreCollect.style.display = "block"
            }),
            onworker(document.getElementById("topmoreCollectionSelect"), "mouseleave", function () {
                usefulDom.moreCollect.style.display = "none"
            }),
            document.getElementById("addCollectionBtn").onclick = function () {
                EMui.prompt({
                    title: "请输入组合名(最多6个汉字或12个字符)"
                }, function (a) {
                    a.replace(/[^\x00-\xff]/g, "xx").length <= 12 && a.replace(/[^\x00-\xff]/g, "xx").length > 0 ? myStock.addCollection(a) : EMui.tip("名称不符合规范", "操作失败")
                })
            }
            ,
            a.codeToMarket = i,
            document.getElementById("directAddStock").onclick = function () {
                var b = document.getElementById("myOwnInputCode").value
                    , c = "01";
                b.match(/^\d{5,6}$/) ? myStock.addStock(b + "|" + i(b) + "|" + c) : EMui.tip("请填写股票代码", "操作失败")
            }
            ,
            onworker(document.getElementById("moveLotOfStock"), "mouseenter", function () {
                document.getElementById("allSCollections").style.display = "block"
            }),
            onworker(document.getElementById("moveLotOfStock"), "mouseleave", function () {
                document.getElementById("allSCollections").style.display = "none"
            }),
            setTimeout(function () {
                var a = {
                    text: "输代码、名称或简拼",
                    autoSubmit: !1,
                    width: 213,
                    header: ["选项", "代码", "名称", "类型"],
                    body: [-1, 1, 4, -2],
                    type: "ABSTOCK",
                    callback: function (a) {
                        myStock.addStock(a.code + "|" + i(a.code) + "|01")
                    }
                };
                new StockSuggest("myOwnInputCode", a)
            }, 500),
            l = 0,
            document.getElementById("navigatorHide").onclick = function () {
                //if (document.getElementById("navigatorHide").style.display == "none") return;
                l %= 2,
                    l % 2 ? (this.innerHTML = "收起导航",
                        this.className = "navi-close",
                        document.getElementById("fir-title").style.display = "block",
                        document.getElementById("sec-title").style.display = "none",
                        /*document.getElementById("stockLeft").style.display = "block",
                        document.getElementById("stock_c_icon").style.display = "none",*/
                        document.getElementById("gpal").style.display = "none",
                        setInterval(function () {
                            document.getElementById("gpal").className = myStock.loginState ? "gpal" : "gpal nlg";
                            if (myStock.loginState) {
                                location.href = 'http://i.eastmoney.com/stock.html';
                            }
                        }, 100),
                        document.getElementById("stockMain").style.width = "815px",
                        a.usefulDom.allTable.style.width = "815px",
                        document.getElementById("wp1").style.width = "402px",
                        document.getElementById("wp2").style.width = "402px",
                        document.getElementById("wp3").style.width = "402px",
                        document.getElementById("wp4").style.width = "402px",
                        b = 464,
                        l++) : (this.innerHTML = "展开导航",
                        this.className = "navi-open",
                        document.getElementById("fir-title").style.display = "none",
                        document.getElementById("sec-title").style.display = "block",
                        /*document.getElementById("stockLeft").style.display = "none",
                        document.getElementById("stock_c_icon").style.display = "block",*/
                        document.getElementById("gpal").style.display = "block",
                        setInterval(function () {
                            document.getElementById("gpal").className = myStock.loginState ? "gpal" : "gpal nlg";
                            if (myStock.loginState) {
                                location.href = 'http://i.eastmoney.com/stock.html';
                            }
                        }, 100),
                        document.getElementById("stockMain").style.width = "1100px",
                        a.usefulDom.allTable.style.width = "1100px",
                        document.getElementById("wp1").style.width = "496px",
                        document.getElementById("wp2").style.width = "496px",
                        document.getElementById("wp3").style.width = "496px",
                        document.getElementById("wp4").style.width = "496px",
                        b = 650,
                        l++)
            }
    }(window),
    function () {
        function a() {
            function t(a) {
                return a = a || window.event,
                    a.pageY ? a.pageY - i - f : a.clientY + document.documentElement.scrollTop - i - f
            }
            function v() {
                if (p.length)
                    for (var a = 0; a < p.length; a++)
                        p[a].style.visibility = "hidden"
            }
            function w() {
                if (p.length)
                    for (var a = 0; a < p.length; a++)
                        p[a].style.visibility = ""
            }
            function x(a, b) {
                var c, d, e, f;
                a--,
                    b--,
                    c = a + myStock.pageIndex * myStock.pageSize,
                    d = b + myStock.pageIndex * myStock.pageSize,
                0 > a || 0 > b || isNaN(a) || isNaN(b) || a == b || void 0 == myStock.stockList[a] || void 0 == myStock.stockList[b] || (e = myStock.stockList.splice(b, 1)[0],
                    myStock.stockList.splice(a, 0, e),
                    f = myStock.allStockArray.splice(d, 1)[0],
                    myStock.allStockArray.splice(c, 0, f))
            }
            function y() {
                var e, a = [], b = p[1].getElementsByTagName("td"), c = p[2].getElementsByTagName("td"), d = p[0].getElementsByTagName("td");
                for (a.push("<table class='mainTable' cellpadding='0' cellspacing='0'><tbody ><tr>"),
                         e = 0; e < c.length; e++)
                    a.push("<td><div style='width:" + (document.getElementById("mainTable").getElementsByTagName("td")[e].offsetWidth - 15) + "px;'>" + c[e].innerHTML + "</div></td>");
                for (a.push("</tr></tbody></table>"),
                         a.push("<table class='leftTable' cellpadding='0' cellspacing='0'><tr>"),
                         e = 0; e < d.length; e++)
                    a.push("<td style='width:" + document.getElementById("leftTable").getElementsByTagName("td")[e].offsetWidth + "px;'>" + d[e].innerHTML + "</td>");
                for (a.push("</tr></table>"),
                         a.push("<table class='rightTable' cellpadding='0' cellspacing='0'><tr>"),
                         e = 0; e < b.length; e++)
                    a.push("<td><div style='width: 32px'>" + b[e].innerHTML + "</div></td>");
                return a.push("</tr></table>"),
                    a
            }
            function z(a) {
                var b = Math.floor(a / h) + 1;
                r != b && A(b)
            }
            function A(b) {
                n = b,
                    a > 0 ? (B(p[0], document.getElementById("leftTable").getElementsByTagName("tr")[b]),
                        B(p[1], document.getElementById("rightTable").getElementsByTagName("tr")[b]),
                        B(p[2], document.getElementById("mainTable").getElementsByTagName("tr")[b + 1])) : 0 > a && (C(p[0], document.getElementById("leftTable").getElementsByTagName("tr")[b]),
                        C(p[1], document.getElementById("rightTable").getElementsByTagName("tr")[b]),
                        C(p[2], document.getElementById("mainTable").getElementsByTagName("tr")[b + 1])),
                    r = b
            }
            function B(a, b) {
                var c = b.parentNode;
                c.lastChild == b ? c.appendChild(a) : c.insertBefore(a, b.nextSibling)
            }
            function C(a, b) {
                var c = b.parentNode;
                c.insertBefore(a, b)
            }
            var g, p, q, r, s, u, D, a = 1, b = 0, c = function (a) {
                    return a.offsetParent.tagName.toLowerCase().match(/^body|html$/) ? a.offsetTop + a.offsetParent.clientTop : a.offsetTop + c(a.offsetParent)
                }
                , d = !0, e = 300, f = 41, h = 30, i = c(usefulDom.allTable), l = 0, m = 0, n = 0, o = document.createElement("div");
            o.className = "myDargDivClass st-table",
                o.style.cursor = "move",
                usefulDom.allTable.parentNode.appendChild(o),
                p = [],
                r = 0,
                s = {
                    star: function (a) {
                        return a > 0 && l > a
                    },
                    no: function (a) {
                        return a > l && a < myStock.stockList.length * h
                    }
                },
                u = function (c) {
                    var d, e;
                    c = c || window.event,
                        d = c.pageY || c.clientY + document.documentElement.scrollTop,
                        a = d - b,
                        e = t(c),
                        o.style.display = "block",
                    s[q](e) && (o.style.top = e - g + f + "px",
                        z(e)),
                        b = c.pageY || c.clientY + document.documentElement.scrollTop
                }
                ,
                onworker(usefulDom.allTable, "mousedown", function (a) {
                    var c, i, j;
                    myStock.Drag && 0 != myStock.stockList.length && (document.body.onselectstart = function () {
                        return !1
                    }
                        ,
                        a = a || window.event,
                        c = a.srcElement || a.target,
                    c && "a" == c.tagName.toLocaleLowerCase() || (b = a.pageY || a.clientY + document.documentElement.scrollTop,
                        l = 30 * myStock.starLength,
                        i = t(a),
                    0 > i || (q = l >= i ? "star" : "no",
                        g = i % h,
                        d = !0,
                        p.splice(0, p.length),
                        j = Math.floor(i / h) + 1,
                        m = j,
                        n = j,
                        o.style.top = f + (m - 1) * h + "px",
                        setTimeout(function () {
                            return d ? (r = j,
                            void 0 != document.getElementById("leftTable").getElementsByTagName("tr")[j] && (p.push(document.getElementById("leftTable").getElementsByTagName("tr")[j]),
                                p.push(document.getElementById("rightTable").getElementsByTagName("tr")[j]),
                                p.push(document.getElementById("mainTable").getElementsByTagName("tr")[j + 1]),
                                v(),
                                o.innerHTML = "",
                                o.innerHTML = y().join(""),
                                onworker(document, "mousemove", u)),
                                void 0) : (p.splice(0, p.length),
                                void 0)
                        }, e))))
                }),
                onworker(document, "mouseup", function () {
                    document.body.onselectstart = function () {
                        return !0
                    }
                        ,
                        LastIndexTr = 0,
                        w(),
                        p.splice(0, p.length),
                        d = !1,
                        usefulDom.allTable.style.cursor = "default",
                        myStock.tableColor(),
                        o.style.display = "none",
                        o.innerHTML = "",
                        reworker(document, "mousemove", u),
                        x(n, m),
                        myStock.loginState ? D(m - n, m, myStock) : (D(m - n, m, myStock),
                            unLogFuc.cSaveList())
                }),
                D = function (a, b, c) {
                    var d, e, f, g, h, i, j, k, l, o, p, q;
                    document.getElementById("rightTable") && 0 != a && (d = document.getElementById("leftTable").getElementsByTagName("tr")[b - a].getElementsByTagName("td")[0].children[0],
                    b - a - 1 > 0 && (e = document.getElementById("leftTable").getElementsByTagName("tr")[b - a - 1].getElementsByTagName("td")[0].children[0]),
                    b - a + 1 < myStock.stockList.length && (f = document.getElementById("leftTable").getElementsByTagName("tr")[b - a + 1].getElementsByTagName("td")[0].children[0]),
                        g = d.getAttribute("scode"),
                        h = "0" + d.getAttribute("smk"),
                        i = d.getAttribute("sty"),
                    e && (j = e.getAttribute("scode"),
                        k = "0" + e.getAttribute("smk"),
                        l = e.getAttribute("sty")),
                    f && (o = f.getAttribute("scode"),
                        p = "0" + f.getAttribute("smk"),
                        q = f.getAttribute("sty")),
                    (e || f) && (e ? f ? loadScript(stockUrl + "f=ss&g=" + c.curCollectID + "&sc=" + g + "|" + h + "|" + i + "&sc1=" + j + "|" + k + "|" + l + "&sc2=" + o + "|" + p + "|" + q + "&var=orderList&" + Math.random()) : loadScript(stockUrl + "f=ss&g=" + c.curCollectID + "&sc=" + g + "|" + h + "|" + i + "&sc1=" + j + "|" + k + "|" + l + "&var=orderList&" + Math.random()) : loadScript(stockUrl + "f=ss&g=" + c.curCollectID + "&sc=" + g + "|" + h + "|" + i + "&sc2=" + o + "|" + p + "|" + q + "&var=orderList&" + Math.random()),
                        m = 0,
                        n = 0))
                }
        }
        window.attachEvent ? (window.attachEvent("onload", a),
            document.getElementById("navigatorHide").onclick()) : (window.addEventListener("load", a),
            document.getElementById("navigatorHide").onclick())
    }(),
    note = new function (a) {
        function l(a, b) {
            var c, d, e, f;
            return "" == a ? !0 : (b.value = b.value.replace(/[^\d-]/g, ""),
                b.value = b.value.replace(/^\-/g, ""),
                c = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/,
                d = a.match(c),
                null == d ? !1 : (e = new Date(d[1], d[3] - 1, d[4]),
                    f = e.getFullYear() + d[2] + (e.getMonth() + 1) + d[2] + e.getDate(),
                f == a))
        }
        var b = "exChangeManage"
            , c = "exChangeStock"
            , d = "exTable"
            , e = "exChangeTips"
            , f = "exChangeNote"
            , g = "exChangeClose"
            , h = "saveExChange"
            , i = "closeExChange"
            , j = "";
        this.init = function () {
            var k, m, n, o, p;
            b = $(b),
            b || (b = document.createElement("div"),
                b.id = "exChangeManage"),
                k = '<div id="exChangeTitle"><div class="exChangeManage">交易管理</div><div id="exChangeClose"></div></div><div class="hr3"></div><div id="exChangeStock"><div class="leftexChange"><span class="big">远望谷</span><span class="small">002136</span><span class="small">当前</span><span class="small">5.86</span></div><div class="rightexChange"><span class="small">我在</span><span class="small">2013-10-27</span><span class="small">当日价</span><span class="small">7.6</span><span class="small">添加该股</span></div></div><div class="hr"></div><table id="exTable" cellpadding="0" cellspacing="0" border="0" style="width: 700px;font-size:12px;text-align:center;border-collapse:collapse;border-spacing: 6px;"><thead><tr id="zhang"><td>数量</td><td>买入价</td><td>目标价</td><td>止损价</td><td>佣金‰</td><td>手续费（元）</td><td>印花税‰</td><td>交易（买入）日期</td></tr></thead><tbody><tr><td><input type="text" class="cssInput" maxlength="6" value="0"></td><td><input type="text"  class="cssInput" maxlength="6" value="0"></td><td><input type="text" class="cssInput" maxlength="6" value="0"></td><td><input type="text"  class="cssInput" maxlength="6" value="0"></td><td><input type="text" class="cssInput"  maxlength="6" value="0"></td><td><input type="text"  class="cssInput" maxlength="5" value="0"></td><td><input type="text"  class="cssInput" maxlength="6" value="0"></td><td><input type="text" class="cssInput"   maxlength="10"  style="width: 89px;"></td></tr></tbody></table><div id="exChangeTips"><span>日期格式：2013-1-1</span></div><div id="exChangeNote"><span class="exChangeNote">投资笔记:</span><span class="exChangewrite">您还可以输入<span>200</span>个字</span><div><textarea maxlength="200"></textarea></div><div id="saveExChange">保存</div><div id="closeExChange">关闭</div></div>',
                b.innerHTML = k,
                document.body.appendChild(b),
                b = $("exChangeManage"),
                m = document.documentElement.scrollTop || document.body.scrollTop,
                b.style.top = m + screen.height / 8 + "px",
                c = $("exChangeStock"),
                d = $("exTable"),
                e = $("exChangeTips"),
                f = $("exChangeNote"),
                g = $("exChangeClose"),
                h = $("saveExChange"),
                i = $("closeExChange"),
                currentStockObj = {},
                j = "",
                g.onclick = function () {
                    uncoverbg(),
                        document.body.removeChild(b)
                }
                ,
                i.onclick = function () {
                    uncoverbg(),
                        document.body.removeChild(b)
                }
                ,
                n = d.getElementsByTagName("input"),
                h.onclick = function () {
                    var c, d, e, g, h;
                    for (c = 0; 6 > c; c++) {
                        if (d = "",
                            "" == n[c].value)
                            return alert("参数不能为空"),
                                void 0;
                        if (isNaN(n[c].value) || n[c].value < 0)
                            return alert("参数不正确"),
                                void 0;
                        if ((n[4].value > 10 || n[4].value < 0) && (d += "佣金必须为小于10的数字，"),
                            (n[6].value > 10 || n[6].value < 0) && (d += "印花税必须为小于10的数字，"),
                            "" != d)
                            return d += "请重新填写。",
                                alert(d),
                                void 0
                    }
                    if (!l(n[7].value, n[7]))
                        return alert("日期输入不正确"),
                            void 0;
                    for (e = f.getElementsByTagName("div")[0].children[0],
                             loadScript(stockUrl + "f=bs&g=" + a.curCollectID + "&sc=" + a.curNoteStockID + "&c=" + n[0].value + "&p=" + n[1].value + "&y=" + n[4].value + "&x=" + n[5].value + "&h=" + n[6].value + "&m=" + n[2].value + "&z=" + n[3].value + "&r=" + n[7].value + "&b=" + encodeURIComponent(e.value) + "&cb=saveNoteCallback&" + Math.random()),
                             uncoverbg(),
                             e = f.getElementsByTagName("div")[0].children[0],
                             g = !0,
                             d = 0,
                             c = 0; c < n.length; c++)
                        if (("" == n[c].value || 0 == n[c].value && "" == e.value) && d++,
                            d == n.length) {
                            g = !1;
                            break
                        }
                    for (c in myStock.stockList)
                        if (myStock.stockList[c]._Code == note.code) {
                            h = myStock.stockList[c];
                            break
                        }
                    g ? (note.currentDom.className = "note-on",
                        h._note = 1,
                        h.parseCommonData()) : (note.currentDom.className = "note",
                        h._note = -1,
                        h.parseCommonData()),
                        document.body.removeChild(b)
                }
                ,
                n[7].onclick = function () {
                    e.style.display = "block"
                }
                ,
                n[7].onblur = function () {
                    e.style.display = "none"
                }
                ,
                o = f.getElementsByTagName("div")[0].children[0],
                p = f.getElementsByTagName("span")[2],
                o.onkeydown = function () {
                    o.value.length > 200 && (o.value = o.value.substring(0, 200)),
                        p.innerHTML = 200 - o.value.length
                }
        }
            ,
            this.fill = function () {
                var a, b, d;
                for (a in myStock.stockList)
                    if (myStock.stockList[a]._Code == note.code) {
                        b = myStock.stockList[a];
                        break
                    }
                d = c.getElementsByTagName("span"),
                    d[0].innerHTML = b._Name,
                    d[1].innerHTML = b._Code,
                    d[3].innerHTML = b.Close
            }
            ,
            this.fillTable = function (a) {
                var e, g, h, i, j, k, b = a.data[myStock.curNoteStockID].date;
                b = b.split("|"),
                    e = c.getElementsByTagName("span"),
                    e[7].innerHTML = "undefined" == typeof b[1] ? "--" : b[1],
                    b = b[0],
                    g = b.substring(0, 4) + "-" + b.substring(4, 6) + "-" + b.substring(6, 8),
                    e[5].innerHTML = g,
                    h = a.data[myStock.curNoteStockID].node,
                h && (i = d.getElementsByTagName("input"),
                    h = h.split("|"),
                    i[0].value = h[0] || "0",
                    i[1].value = h[1] || "0",
                    i[4].value = h[2] || "0",
                    i[5].value = h[3] || "0",
                    i[6].value = h[4] || "0",
                    i[2].value = h[5] || "0",
                    i[3].value = h[6] || "0",
                    i[7].value = h[7],
                    j = f.getElementsByTagName("div")[0].children[0],
                    k = f.getElementsByTagName("span")[2],
                    j.value = h[8],
                    k.innerHTML = 200 - j.value.length)
            }
    }
    (myStock),
    tixing = new function () {
        var n, b = "tixinglight", c = "tixingclose", d = "sastitle", e = "sassubmit", f = "sascancel", g = "sasclear";
        this.init = function () {
            var a, h;
            b = $(b),
            b || (b = document.createElement("div"),
                b.id = "tixinglight",
                b.cssText = ""),
                a = '<div class="tixingclose" id="tixingclose" title="关闭">X</div><div class="tixingtitle"><em class="alarmbell"></em>自选股提醒</div><div class="tixingbody" id="tixingbody"><div id="stockalarmset"><div class="sastitle" id="sastitle"><a href="http://guba.eastmoney.com/list,300356.html target="_blank" class="stockname">--</a>（300356）当前股价：<span id="nowprice" class="red"><strong>3.96</strong></span></div><div class="sasaset"><div class="item"><input type="checkbox" id="sasi1"><label for="sasi1">	当有最新公司公告、数据、研究报告时提醒</label></div></div><div class="sasalarm"><div class="sasalarmtitle">	股价预警</div><div class="item"><input type="checkbox" id="sasi4"><label for="sasi4">股价突破</label><input type="text" id="sasinum1" class="inty">	元 <span id="sasinum1error" class="red"></span></div><div class="item"><input type="checkbox" id="sasi5"><label for="sasi5">	股价跌破</label><input type="text" id="sasinum2" class="inty">	元 <span id="sasinum2error" class="red"></span></div><div class="item"><input type="checkbox" id="sasi6"><label for="sasi6">	日涨跌幅达到</label><input type="text" id="sasinum3" class="inty">	％ <span id="sasinum3error" class="red"></span></div></div><div class="sasbtns"><a href="javascript:;" target="_self" id="sasclear">清空</a><div class="btnsdiv"><div class="btns"><button id="sassubmit">确 定</button><button id="sascancel">取 消</button></div></div></div></div></div>',
                b.innerHTML = a,
                document.body.appendChild(b),
                b = $("tixinglight"),
                h = document.documentElement.scrollTop || document.body.scrollTop,
                b.style.top = h + screen.height / 10 + "px",
                c = $("tixingclose"),
                e = $("sassubmit"),
                f = $("sascancel"),
                d = $("sastitle"),
                g = $("sasclear"),
                _sasinum1 = $("sasinum1"),
                _sasinum1error = $("sasinum1error"),
                _sasinum2 = $("sasinum2"),
                _sasinum2error = $("sasinum2error"),
                _sasinum3 = $("sasinum3"),
                _sasinum3error = $("sasinum3error"),
                n = b.getElementsByTagName("input"),
                _sasinum1.onblur = function () {
                    var a = d.getElementsByTagName("strong")[0].innerHTML;
                    return "" == _sasinum1.value ? (n[1].checked = !1,
                        void 0) : isNaN(_sasinum1.value) ? (_sasinum1error.innerHTML = "必须输入数字",
                        _sasinum1.value = "",
                        n[1].checked = !1,
                        void 0) : _sasinum1.value - a < 0 ? (_sasinum1error.innerHTML = "所填价格不可小于现价",
                        _sasinum1.value = "",
                        n[1].checked = !1,
                        void 0) : _sasinum1.value - a >= 0 ? (_sasinum1error.innerHTML = "此价格较现价的涨幅为" + Math.round(100 * (_sasinum1.value / a) - 100) + "%",
                        n[1].checked = !0,
                        void 0) : void 0
                }
                ,
                _sasinum2.onblur = function () {
                    var a = d.getElementsByTagName("strong")[0].innerHTML;
                    return "" == _sasinum2.value ? (n[3].checked = !1,
                        void 0) : isNaN(_sasinum2.value) ? (_sasinum2error.innerHTML = "必须输入数字",
                        _sasinum2.value = "",
                        n[3].checked = !1,
                        void 0) : _sasinum2.value - a >= 0 ? (_sasinum2error.innerHTML = "所填价格不可大于现价",
                        _sasinum2.value = "",
                        n[3].checked = !1,
                        void 0) : _sasinum2.value - a <= 0 ? (_sasinum2error.innerHTML = "此价格较现价的跌幅为" + Math.round(100 * ((_sasinum2.value - a) / a)) + "%",
                        n[3].checked = !0,
                        void 0) : void 0
                }
                ,
                _sasinum3.onblur = function () {
                    return "" == _sasinum3.value ? (n[5].checked = !1,
                        void 0) : isNaN(_sasinum3.value) ? (_sasinum3error.innerHTML = "必须输入数字",
                        _sasinum3.value = "",
                        n[5].checked = !1,
                        void 0) : void 0
                }
                ,
                c.onclick = function () {
                    return 2 == tixing.type ? ($("fixedTiXing").style.display = "block",
                        uncoverbg(),
                        document.body.removeChild(b),
                        void 0) : (uncoverbg(),
                        document.body.removeChild(b),
                        tixing.type = "",
                        void 0)
                }
                ,
                f.onclick = function () {
                    return 2 == tixing.type ? ($("fixedTiXing").style.display = "block",
                        uncoverbg(),
                        document.body.removeChild(b),
                        void 0) : (uncoverbg(),
                        document.body.removeChild(b),
                        tixing.type = "",
                        void 0)
                }
                ,
                g.onclick = function () {
                    var c, a = b.getElementsByTagName("input");
                    for (c = 0; c < a.length; c++)
                        "" != a[c].value && (a[c].value = ""),
                        1 == a[c].checked && (a[c].checked = !1)
                }
                ,
                e.onclick = function () {
                    var d, e, a = !1, c = "http://mystock.eastmoney.com/Message.aspx?type=set&code=" + tixing.market + "," + tixing.code;
                    for (c += n[0].checked ? "&n=1&r=1&d=1" : "&n=2&r=2&d=2",
                             n[1].checked ? c += "&tp=" + n[2].value : c,
                             n[3].checked ? c += "&bp=" + n[4].value : c,
                             n[5].checked ? c += "&zf=" + n[6].value : c,
                             c += "&incId=" + tixing.incId + "&" + Math.random(),
                             loadScript(c),
                             uncoverbg(),
                             d = 0; d < n.length; d++)
                        if (1 == n[d].checked) {
                            a = !0;
                            break
                        }
                    return tixing.currentDom && (e = myStock.alertStock.indexOf(tixing.code),
                        a ? (tixing.currentDom.className = "alert-on",
                        -1 == e && myStock.alertStock.push(tixing.code)) : (tixing.currentDom.className = "alert",
                        -1 != e && myStock.alertStock.splice(e, 1))),
                        2 == tixing.type ? ($("fixedTiXing").style.display = "block",
                            document.body.removeChild(b),
                            void 0) : (document.body.removeChild(b),
                            tixing.type = "",
                            void 0)
                }
        }
            ,
            this.fill = function (a) {
                var e, f, g, h;
                "undefined" != typeof tixing.ob[0].stats ? (e = tixing.currentDom.getAttribute("scode"),
                    f = '<a href="http://guba.eastmoney.com/list,--.html" target=" _blank"="" class="stockname">--</a>（' + e + '）当前股价：<span id="nowprice">' + "<strong>--</strong></span>") : (g = tixing.ob[0].split(","),
                    h = parseFloat(g[4]) > 0 ? "stKred" : "stKgreen",
                    f = '<a href="http://guba.eastmoney.com/list,' + g[1] + '.html" target=" _blank"="" class="stockname">' + g[2] + "</a>（" + g[1] + '）当前股价：<span id="nowprice" class="' + h + '"><strong>' + g[3] + "</strong></span>"),
                    d.innerHTML = f,
                    tixing.incId = a.incId,
                1 == a.notice && (n[0].checked = !0),
                "" != a.topPrice && (n[1].checked = !0,
                    n[2].value = a.topPrice.replace("*", "")),
                "" != a.bottomPrice && (n[3].checked = !0,
                    n[4].value = a.bottomPrice.replace("*", "")),
                "" != a.amplitude && (n[5].checked = !0,
                    n[6].value = a.amplitude.replace("*", ""))
            }
    }
    (myStock),
    window.onworkerTR = function (a, b, c) {
        window.attachEvent ? a.attachEvent("on" + b, function () {
            c.call(a)
        }) : "mouseenter" != b && "mouseleave" != b ? a.addEventListener(b, c) : (b = "mouseenter" == b ? "mouseover" : "mouseout",
            a["_E" + b] = function (b) {
                this.contains(b.relatedTarget) || c.call(a)
            }
            ,
            a.addEventListener(b, a["_E" + b]))
    }
    ,
    function (b) {
        function c(a) {
            var c, d, b = document.cookie.split(";");
            for (c = 0; c < b.length; c++)
                if (d = b[c].split("="),
                    d[0].replace(" ", "") == a)
                    return unescape(decodeURI(d[1]));
            return ""
        }
        var e, f, g, h, d = c("pi");
        return d && null != d && "" != d ? (e = $("tixing"),
            b.onworker(e, "mouseenter", function () {
                $("tixingTips").style.display = "block",
                    g = !1,
                    clearTimeout(a),
                    showTiXing(!1)
            }),
            b.onworker(e, "mouseleave", function () {
                $("tixingTips").style.display = "none",
                g || (clearTimeout(a),
                    b.a = setTimeout("showTiXing(true)", timeSet))
            }),
            f = !1,
            g = !1,
            b.timeSet = 6e4,
            b.a = setTimeout("showTiXing(true)", timeSet),
            h = [0, 0, 0, 0],
            b.showTiXing = function (c) {
                var d = "http://mystock.eastmoney.com/Message.aspx?type=messagecount&" + Math.random();
                loadScript(d, function () {
                    var k, l, d = data.StockPrice, e = data.Notice, i = data.Report, j = data.Data;
                    for (g = !1,
                             k = $("tixingTips").getElementsByTagName("div"),
                             k[1].getElementsByTagName("span")[1].innerHTML = data.StockPrice + "个股价提醒",
                             k[2].getElementsByTagName("span")[1].innerHTML = data.Notice + "个公告提醒",
                             k[3].getElementsByTagName("span")[1].innerHTML = data.Report + "个研报提醒",
                             k[4].getElementsByTagName("span")[1].innerHTML = data.Data + "个数据提醒",
                             $("tixingTips").getElementsByTagName("div")[0].onclick = function () {
                                 $("tixingTips").style.display = "none",
                                     g = !0,
                                     clearTimeout(a)
                             }
                             ,
                             l = 1; l < k.length; l++)
                        k[l].onclick = function () {
                            var c, d;
                            g = !0,
                                h = [0, 0, 0, 0],
                                $("tixingTips").style.display = "none",
                                clearTimeout(a),
                                c = b.getPosition(this),
                                d = "http://mystock.eastmoney.com/Message.aspx?type=get&p=1&ps=150&cate=&" + Math.random(),
                                coverbg(),
                                loadScript(d, function () {
                                    f = !1,
                                        allTiXing.init(c, !0),
                                        allTiXing.cateGory(data, c)
                                })
                        }
                        ;
                    f = d == h[0] && e == h[1] && i == h[2] && j == h[3] ? !1 : !0,
                    f && (h = [d, e, i, j],
                        $("tixingTips").style.display = "block"),
                    c && (b.a = setTimeout("showTiXing(true)", timeSet))
                })
            }
            ,
            void 0) : ($("tixing").parentNode.removeChild($("tixing")),
            !1)
    }(window),
    allTiXing = new function (b) {
        function j(a) {
            return a.replace(/[^\x00-\xff]/g, "xx").length
        }
        function k(a) {
            return a && a.preventDefault ? a.preventDefault() : window.event.returnValue = !1,
                !1
        }
        var c = "fixedTiXing"
            , d = "fixedClose"
            , e = "fixedTab"
            , f = "fixedTablebody;"
            , g = "alarmpager"
            , h = 0
            , i = 15;
        this.page1 = h,
            this.page2 = i,
            this.gujia = [],
            this.gonggao = [],
            this.yanbao = [],
            this.shuju = [],
            this.allTiXing = [],
            this.position,
            this.currentPage,
            this.init = function (h, i) {
                var j, k, l, m;
                for (c = $(c),
                     c || (c = document.createElement("div"),
                         c.id = "fixedTiXing",
                         c.cssText = ""),
                         j = '<div class="fixedClose" id="fixedClose" title="关闭">X</div><div class="fixedTitle" style="display: none;">使用该功能请先登录</div><div class="fixedTab" id="fixedTab" style="width: 675px;"><ul><li class="">全部提醒</li><li class="">股价提醒</li><li>公告提醒</li><li>研报提醒</li><li>数据提醒</li></ul></div><div class="fixedTBody"><div id="fixedTable"><div class="fixedTablebody" id="fixedTablebody"></div><div class="fixedTablebottom"><div class="sbtext">您可通过自选股提醒图标设置提醒</div><div class="alarmpager" id="alarmpager"></div></div></div></div>',
                         c.innerHTML = j,
                         document.body.appendChild(c),
                         c = $("fixedTiXing"),
                         k = document.documentElement.scrollTop || document.body.scrollTop,
                         c.style.top = k + screen.height / 8 + "px",
                         d = $("fixedClose"),
                         e = $("fixedTab"),
                         f = $("fixedTablebody"),
                         g = $("alarmpager"),
                         this.position = h,
                         pages.currentPage = 0,
                     i && (this.currentPage = 0),
                         l = e.getElementsByTagName("li"),
                         l[allTiXing.position].className = "on",
                         m = 0; m < l.length; m++)
                    l[m].onclick = function () {
                        var a, c;
                        for (a = 0; a < l.length; a++)
                            l[a].className = "";
                        this.className = "on",
                            c = b.getPosition(this),
                            allTiXing.page1 = 0,
                            allTiXing.page2 = 15,
                            allTiXing.currentPage = 0,
                            pages.currentPage = 0,
                            allTiXing.position = c,
                            allTiXing.fillTable(c)
                    }
                    ;
                d.onclick = function () {
                    uncoverbg(),
                        a = setTimeout("showTiXing(true)", timeSet),
                        document.body.removeChild(c)
                }
            }
            ,
            this.cateGory = function (a, b) {
                var c, d;
                for (this.gujia = [],
                         this.gonggao = [],
                         this.yanbao = [],
                         this.shuju = [],
                         this.allTiXing = a.result,
                         c = a.result.length,
                         d = 0; c > d; d++)
                    1 == a.result[d].type ? this.gujia.push(a.result[d]) : 2 == a.result[d].type ? this.gonggao.push(a.result[d]) : 3 == a.result[d].type ? this.yanbao.push(a.result[d]) : 4 == a.result[d].type && this.shuju.push(a.result[d]);
                this.fillTable(b)
            }
            ,
            this.fillTable = function (a) {
                var h, i, k, l, b = [this.allTiXing, this.gujia, this.gonggao, this.yanbao, this.shuju][a], c = ["股价提醒", "公告提醒", "研报提醒", "数据提醒"], d = "";
                if (0 == b.length)
                    return f.innerHTML = '<table class="datatable" id="datatable"><thead ><tr><th>序号</th><th>日期</th><th>类型</th><th>内容</th><th></th></tr></thead><tbody id="tbody"><td colspan="5" style="height:449px;vertical-align: middle;text-align: center; width:570px;">暂时没有提醒</td></tbody></table>',
                        g.innerHTML = "",
                        void 0;
                for (d = "",
                         this.page2 = this.page2 > b.length ? b.length : this.page2,
                         $C("div"),
                         d += '<table class="datatable" id="datatable"><thead ><tr><th>序号</th><th>日期</th><th>类型</th><th>内容</th><th></th></tr></thead><tbody id="tbody">',
                         h = 0; h < b.length; h++)
                    i = b[h].title,
                    j(i) > 50 && (i = i.substring(0, 25) + "..."),
                        d += "<tr><td>" + (h + 1) + "</td><td>" + b[h].datetime.substring(5, 16) + "</td><td>" + c[b[h].type - 1] + "</td>" + '<td class="l">[<a href="http://guba.eastmoney.com/list,' + b[h].code + '.html" target="_blank">' + b[h].name + "</a>]" + '<a href="' + b[h].url + '" title="' + b[h].title + '" target="_blank">' + i + "</a>" + '</td><td class="setup"><a href="javascript:void(0)"  data-code="' + b[h].code + '" target="_self" class="modyone">修改设置</a>' + "</td></tr>";
                for (d += "</tbody></table>",
                         f.innerHTML = d,
                         k = f.getElementsByTagName("tbody")[0].getElementsByTagName("tr"),
                         h = 0; h < b.length; h++)
                    for (k[h].style.display = "none",
                             l = this.page1; l < this.page2; l++)
                        k[l].style.display = "";
                this.operation(),
                    g.innerHTML = "",
                    pages.init(g, 0, 15, b.length),
                    pages.pageTiXing(b)
            }
            ,
            this.operation = function () {
                var d, a = f.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
                for (d = 0; d < a.length; d++)
                    b.onworkerTR(a[d], "mouseenter", function () {
                        this.getElementsByTagName("td")[4].className = "";
                        var b = this.getElementsByTagName("td")[4].children[0];
                        b.onclick = function (a) {
                            var d, e, f, g, h;
                            if (k(a),
                                    d = b.getAttribute("data-code"),
                                    e = getMarket(d),
                                    e = "02" == e ? 0 : 1,
                                    f = usefulDom.allTable.getElementsByTagName("table")[2])
                                for (g = f.getElementsByTagName("i"),
                                         h = 1; h < g.length; h++) {
                                    if (g[h].getAttribute("scode") == d) {
                                        tixing.currentDom = g[h];
                                        break
                                    }
                                    tixing.currentDom = !1,
                                        h += 2
                                }
                            c.style.display = "none",
                                tixing.type = 2,
                                myStock.getTiXing(d, e)
                        }
                    }),
                        b.onworkerTR(a[d], "mouseleave", function () {
                            this.getElementsByTagName("td")[4].className = "setup"
                        })
            }
    }
    (window),
    dgtl = new function () {
        var b = 0
            , c = 9;
        this.page1 = b,
            this.page2 = c,
            this.currentPage,
            this.init = function (a) {
                var d, e, f, g, h, i, j, k,imgurl;
                if (0 != myStock.allStockArray.length) {
                    for (a && (this.page1 = b,
                        this.page2 = c,
                        this.currentPage = 0,
                        pages.currentPage = 0),
                             d = $("pics"),
                             d.innerHTML = "",
                             $("stockDataTables").style.display = "none",
                             d.style.display = "block",
                         this.page2 > myStock.allStockArray.length && (this.page2 = myStock.allStockArray.length),
                             e = this.page1; e < this.page2; e++)
                        f = document.createElement("div"),
                            f.className = "image_single",
                            g = myStock.allStockArray[e]._Code,
                            h = myStock.allStockArray[e]._MarketType,
                            i = myStock.allStockArray[e]._Type,
                            j = g + "|0" + h + "|" + i,
                            imgurl = "0" == g[0] || "3" == g[0] || "6" == g[0] || "9" == g[0] || "2" == g[0] ? '<a href="http://quote.eastmoney.com/' + g + '.html" target="_blank"><img src="http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/Index.aspx?id=' + g + h + "&imageType=RT&token=44c9d251add88e27b65ed86506f6e5da&r=" : '<a href="http://quote.eastmoney.com/' + g + '.html" target="_blank"><img src="http://hqgbpic.eastmoney.com/mrchart/' + g + h + ".gif?",
                            k = imgurl + Math.random() + '">' + '</a><div class="btn_box"><table style="float:right"><tbody><tr><td><a href="http://quote.eastmoney.com/' + g + '.html" target="_blank">'
                            + '<img src="http://hqres.eastmoney.com/EMQuote_A/favor/images/btn_see_big.gif"></a></td><td>' + '<a href="http://guba.eastmoney.com/topic,' + g + '.html" target="_blank">'
                            + '<img src="http://hqres.eastmoney.com/EMQuote_A/favor/images/btn_go_guba.gif"></a></td><td>' + "<a href=\"javascript:myStock.delStock('" + j + '\')"  target="_self">删除</a>' + "</td></tr></tbody></table></div>",
                            f.innerHTML = k,
                            d.appendChild(f);
                    pages.init(d, 0, 9, myStock.allStockArray.length),
                        pages.pageDgtl();
                }
            }
    }
    (window),
    pages = new function () {
        var b, c;
        this.currentPage = 0,
            this.father,
            this.init = function (a, d, e, f) {
                var g, h, i, j;
                if (this.father = a,
                        b = d,
                        c = e,
                        g = $C("div"),
                        g.className = "pages",
                        a.appendChild(g),
                    c > f && (c = f),
                        h = $C("a"),
                        h.innerHTML = "上一页",
                        g.appendChild(h),
                        f = Math.ceil(f / e),
                    5 >= f)
                    for (i = 0; f > i; i++)
                        h = $C("a"),
                            h.innerHTML = i + 1,
                        i == pages.currentPage && (h.className = "acurrent"),
                            g.appendChild(h);
                else
                    for (j = $C("span"),
                             i = this.currentPage - 3; i <= this.currentPage + 3; i++)
                        i >= f || 0 > i || (h = $C("a"),
                            h.innerHTML = i + 1,
                        i == pages.currentPage && (h.className = "acurrent"),
                            g.appendChild(h));
                h = $C("a"),
                    h.innerHTML = "下一页",
                    g.appendChild(h),
                    j = $C("span"),
                    j.innerHTML = "共" + f + "页",
                    g.appendChild(j)
            }
            ,
            this.pageDgtl = function () {
                var d, a = this.father.lastChild.getElementsByTagName("a"), b = a[0], c = a[a.length - 1];
                for (b.onclick = function () {
                    0 != dgtl.page1 && (dgtl.page1 = dgtl.page1 - 9,
                        dgtl.page2 = dgtl.page1 + 9,
                        dgtl.currentPage = dgtl.currentPage - 1,
                        pages.currentPage = dgtl.currentPage,
                        dgtl.init(!1))
                }
                         ,
                         c.onclick = function () {
                             dgtl.page2 != myStock.allStockArray.length && (dgtl.page1 = dgtl.page1 + 9,
                                 dgtl.page2 = dgtl.page2 + 9,
                                 dgtl.currentPage = dgtl.currentPage + 1,
                                 pages.currentPage = dgtl.currentPage,
                                 dgtl.init(!1))
                         }
                         ,
                         d = 1; d < a.length - 1; d++)
                    a[d].onclick = function () {
                        dgtl.page1 = 9 * (this.innerHTML - 1),
                            dgtl.page2 = 9 * this.innerHTML,
                            dgtl.currentPage = this.innerHTML - 1,
                            pages.currentPage = dgtl.currentPage,
                            dgtl.init(!1)
                    }
            }
            ,
            this.pageTiXing = function (a) {
                var e, b = this.father.lastChild.getElementsByTagName("a"), c = b[0], d = b[b.length - 1];
                for (c.onclick = function () {
                    0 != allTiXing.page1 && (allTiXing.page1 = allTiXing.page1 - 15,
                        allTiXing.page2 = allTiXing.page1 + 15,
                        allTiXing.currentPage = pages.currentPage - 1,
                        pages.currentPage = allTiXing.currentPage,
                        allTiXing.fillTable(allTiXing.position, !1))
                }
                         ,
                         d.onclick = function () {
                             allTiXing.page2 != a.length && (allTiXing.page1 = allTiXing.page1 + 15,
                                 allTiXing.page2 = allTiXing.page2 + 15,
                                 allTiXing.currentPage = allTiXing.currentPage + 1,
                                 pages.currentPage = allTiXing.currentPage,
                                 allTiXing.fillTable(allTiXing.position, !1))
                         }
                         ,
                         e = 1; e < b.length - 1; e++)
                    b[e].onclick = function () {
                        allTiXing.page1 = 15 * (this.innerHTML - 1),
                            allTiXing.page2 = 15 * this.innerHTML,
                            allTiXing.currentPage = this.innerHTML - 1,
                            pages.currentPage = allTiXing.currentPage,
                            allTiXing.fillTable(allTiXing.position, !1)
                    }
            }
    }
    ,
    function () {
        function a() {
            var a = new Date;
            return a = a.getHours(),
                a >= 18 || 4 > a ? !1 : !0
        }
        function i(a) {
            for (var b = 0; myStock.stockList[b];) {
                if (myStock.stockList[b].WorkID == a)
                    return myStock.stockList[b];
                b++
            }
            return null
        }
        function j(a) {
            g.push(a)
        }
        function k() {
            var a;
            for (b--,
                     a = 0; g[a];)
                g[a].style.filter ? (g[a].style.fliter = "alpha(opacity=" + 80 / b + 20 + ")",
                    a++) : (g[a].style.opacity = .8 / b + .2,
                    a++);
            if (1 >= b || 0 == g.length) {
                for (a = 0; g[a];)
                    g[a].style.opacity = 1,
                        a++;
                return b = 1,
                    setTimeout(h, c),
                    void 0
            }
            setTimeout(k, 50)
        }
        var f, g, h, b = 10, c = 6e3, d = !1, e = null;
        myStock.updateList = [],
            g = [],
            h = function () {
                var l, m, n;
                for (d = !1,
                         g.splice(0, g.length),
                         f = Math.random().toString(),
                         l = [],
                         m = 0; m < myStock.stockList.length; m++)
                    l.push(myStock.stockList[m]._Code + parseInt(myStock.stockList[m]._MarketType).toString());
                return "DGTL" == myStock.funcName ? (setTimeout(h, c),
                    void 0) : (e = setTimeout(function () {
                    setTimeout(h, c)
                }, 6e4),
                    a() ? (n = "YKYL" == myStock.funcName ? defaultKey : myStock.funcName,
                        loadScript(hpUrl + "type=CT&cmd=" + l.join(",") + "&sty=" + n + "&cb=getStockFullInfo&js=([(x)],true)&" + Math.random(), function () {
                            var a, f, l, m, n, o, p, q;
                            if (d = !0,
                                    clearTimeout(e),
                                "none" != myStock.sortState && myStock.sortStockListByKey(myStock.lastKey),
                                    a = document.getElementById("mainTable"),
                                void 0 == a)
                                return setTimeout(h, c),
                                    void 0;
                            for (g.splice(0, g.length),
                                     f = 0; f < myStock.stockList.length; f++) {
                                l = 0,
                                    m = a.getElementsByTagName("tr")[f + 2],
                                    n = i(m.getAttribute("workid")),
                                    o = m.getElementsByTagName("td");
                                for (p in mainTableField[myStock.funcName])
                                    if ("extend" != p)
                                        if (p.indexOf("COLSPAN") >= 0) {
                                            for (q in mainTableField[myStock.funcName][p])
                                                if ("extend" != q) {
                                                    if (void 0 == o[l] || null == o[l])
                                                        break;
                                                    o[l].innerHTML != n[q] && (o[l].innerHTML = n[q],
                                                        j(o[l])),
                                                        l++
                                                }
                                        } else {
                                            if (void 0 == o[l] || null == o[l])
                                                break;
                                            o[l].innerHTML != n[p] && (o[l].innerHTML = n[p],
                                                j(o[l])),
                                                l++
                                        }
                                o = null,
                                    m = null
                            }
                            b = 10,
                                k()
                        }),
                        void 0) : !1)
            }
            ,
            setTimeout(h, c)
    }(),
    function () {
        var a = document.getElementById("dzCss")
            , b = document.getElementById("xzCss");
        a.onclick = function () {
            setCookie("font_css", "dz", 365),
                usefulDom.allTable.className = "st-table dzb",
                usefulDom.totalWar.className = "st-ht w100p dzb",
                this.className = "fl dzml5 dzbtnSelected",
                b.className = "fl dzml5"
        }
            ,
            b.onclick = function () {
                setCookie("font_css", "xz", 365),
                    usefulDom.allTable.className = "st-table",
                    usefulDom.totalWar.className = "st-ht w100p",
                    this.className = "fl dzml5 dzbtnSelected",
                    a.className = "fl dzml5"
            }
            ,
        "dz" == getCookie("font_css") && a.onclick()
    }();
