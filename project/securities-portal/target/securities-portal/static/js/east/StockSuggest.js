!function() {
    var b, c, d, a = {
        create: function() {
            return function() {
                this.initialize.apply(this, arguments)
            }
        }
    };
    Object.extend = function(a, b) {
        for (property in b)
            a[property] = b[property];
        return a
    }
        ,
        b = a.create(),
        Object.extend(Function.prototype, {
            bind: function() {
                var d, a = this, b = arguments[0], c = new Array;
                for (d = 1; d < arguments.length; d++)
                    c.push(arguments[d]);
                return function() {
                    return a.apply(b, c)
                }
            }
        }),
        Object.extend(b.prototype, {
            initialize: function() {},
            Browser: {
                IE: !(!window.attachEvent || -1 !== navigator.userAgent.indexOf("Opera")),
                Opera: navigator.userAgent.indexOf("Opera") > -1,
                WebKit: navigator.userAgent.indexOf("AppleWebKit/") > -1,
                Gecko: navigator.userAgent.indexOf("Gecko") > -1 && -1 === navigator.userAgent.indexOf("KHTML"),
                MobileSafari: !!navigator.userAgent.match(/Apple.*Mobile.*Safari/)
            },
            $: function(a) {
                return "string" == typeof a ? document.getElementById(a) : a
            },
            $C: function(a) {
                return document.createElement(a)
            },
            $aE: function(a, b, c, d) {
                if (a.addEventListener)
                    return a.addEventListener(b, c, d),
                        !0;
                if (a.attachEvent) {
                    var e = a.attachEvent("on" + b, c);
                    return e
                }
                a["on" + b] = c
            },
            $dE: function(a, b, c, d) {
                if (a.removeEventListener)
                    return a.removeEventListener(b, c, d),
                        !0;
                if (a.detachEvent) {
                    var e = a.detachEvent("on" + b, c);
                    return e
                }
                return a["on" + b] = null,
                    void 0
            },
            isNullorEmpty: function(a) {
                return null == a || "" == a || "undefined" == a ? !0 : !1
            },
            getStyle: function(a, b) {
                return a.currentStyle ? a.currentStyle[b] : window.getComputedStyle ? document.defaultView.getComputedStyle(a, null).getPropertyValue(b) : void 0
            }
        }),
        c = new b,
        d = a.create(),
        Object.extend(d.prototype, {
            initialize: function(a, b) {
                this.input = a,
                    this.dataurl = "http://suggest.eastmoney.com/suggest/default.aspx?name={#NAME}&input={#KEY}&type={#TYPE}",
                c.isNullorEmpty(b.dataurl) || (this.dataurl = b.dataurl),
                    this.autoSubmit = c.isNullorEmpty(b.autoSubmit) ? !1 : b.autoSubmit,
                    this.type = c.isNullorEmpty(b.type) ? "" : b.type,
                    this.link = c.isNullorEmpty(b.link) ? "" : b.link,
                    this.width = c.isNullorEmpty(b.width) ? "" : b.width,
                    this.opacity = c.isNullorEmpty(b.opacity) ? 1 : b.opacity,
                    this.className = c.isNullorEmpty(b.className) ? "" : b.className,
                    this.max = c.isNullorEmpty(b.max) ? 10 : b.max,
                    this.text = c.isNullorEmpty(b.text) ? "请输入..." : b.text,
                    this.header = ["代码", "名称", "简拼", "类型"],
                    this.body = [1, 4, 3, -2],
                    this.callback = null == b.callback || "undefined" == b.callback ? null : b.callback,
                    this.showAd = null == b.showAd || "undefined" == b.showAd ? !0 : b.showAd,
                    this.results = null,
                    this._D = null,
                    this._F = null,
                    this._R = null,
                    this._W = null,
                    this._X = {},
                    this._Y = {},
                    this._hidden = !1,
                    this.Market = "",
                    this.mType = "",
                    this.SName = "",
                    this._iF = null,
                    this._iN = null,
                    this._iC = null,
                    this._oForm = null,
                    this.StockType = {
                        0: "未知",
                        1: "A 股",
                        2: "B 股",
                        3: "权证",
                        4: "期货",
                        5: "债券",
                        10: "基金",
                        11: "开基",
                        12: "ETF",
                        13: "LOF",
                        14: "货基",
                        15: "QDII",
                        16: "封基",
                        21: "港股",
                        22: "窝轮",
                        31: "美股",
                        32: "外期",
                        40: "指数",
                        50: "期指",
                        51: "国债期货",
                        55: "期权",
                        56: "期权",
                        60: "三板"
                    },
                    this.ShowType = {
                        ABSTOCK: "1,2,3",
                        CNSTOCK: "1,2,3,10,50,55,56,60",
                        CNFUND: "11,12,13,14,15,16",
                        HKSTOCK: "21,22",
                        USASTOCK: "31",
                        STOCK: "1,2,3,10,11,12,13,14,15,16,21,22,31,40,50,51,55,56,60"
                    },
                    this.init()
            },
            init: function() {
                var a, b;
                if (this._Y = {},
                        this.input = "string" == typeof this.input ? c.$(this.input) : this.input,
                        this.input) {
                    if (null == this._F) {
                        for (a = this.input.parentNode; "form" != a.nodeName.toLowerCase() && "body" != a.nodeName.toLowerCase(); )
                            a = a.parentNode;
                        "form" == a.nodeName.toLowerCase() ? (this._oForm = {
                            action: a.action,
                            target: a.target,
                            method: a.method,
                            onsubmit: a.onsubmit
                        },
                            this._F = a) : (this._F = c.$C("form"),
                            this._F.method = "get",
                            this.autoSubmit ? this._F.target = "_blank" : (this._F.target = "_self",
                                this._F.onsubmit = function() {
                                    return !1
                                }
                            ),
                            this.input.parentNode.insertBefore(this._F, this.input),
                            b = this.input,
                            this.input.parentNode.removeChild(this.input),
                            this._F.appendChild(b))
                    }
                    this.autoSubmit && (this._F.onsubmit = function() {
                        return !1
                    }
                    ),
                        this.input.value = this.text,
                        this.input.setAttribute("autocomplete", "off"),
                        this.input.autoComplete = "off",
                        this._iF = this._bd(this.inputFocus),
                        this._iN = this._bd(this.Navigate),
                        this._iC = this._bd(this.Confirm),
                        c.$aE(this.input, "focus", this._iF),
                        c.$aE(this.input, "blur", this._iF),
                        c.$aE(this.input, "keyup", this._iN),
                    this.autoSubmit && c.$aE(this.input, "keydown", this._iC),
                        c.$aE(this.input, "mouseup", this._iN)
                }
            },
            dispose: function() {
                this._Y = {},
                    this.input = "string" == typeof this.input ? c.$(this.input) : this.input,
                this.input && (null != this._oForm && (this._F.action = this._oForm.action,
                    this._F.target = this._oForm.target,
                    this._F.method = this._oForm.method,
                    this._F.onsubmit = this._oForm.onsubmit),
                    c.$dE(this.input, "focus", this._iF),
                    c.$dE(this.input, "blur", this._iF),
                    c.$dE(this.input, "keyup", this._iN),
                this.autoSubmit && c.$dE(this.input, "keydown", this._iC),
                    c.$dE(this.input, "mouseup", this._iN))
            },
            GetShowType: function() {
                return "" == this.type ? "" : this.ShowType[this.type]
            },
            inputFocus: function(a) {
                var b = a.type;
                this.input.value == this.text && b.indexOf("focus") >= 0 ? (this.input.value = "",
                    this._U = "",
                    this.Suggest()) : "" == this.input.value && b.indexOf("blur") >= 0 ? (this.input.value = this.text,
                    this._U = "",
                    this.hiddenResults()) : b.indexOf("blur") >= 0 && this.hiddenResults()
            },
            nGourl: !1,
            Navigate: function(a) {
                var c, d, e, f, b = null == this.header ? 0 : 1;
                switch (a.keyCode) {
                    case 38:
                        this.nGourl = !1,
                        null != this.results && "" != this.results.innerHTML && this.setLine(this.results.firstChild.rows[this._W && this._W.rowIndex != b ? this._W.rowIndex - 1 : this.results.firstChild.rows.length - 2]);
                        break;
                    case 40:
                        this.nGourl = !1,
                        null != this.results && "" != this.results.innerHTML && this.setLine(this.results.firstChild.rows[this._W && this._W.rowIndex != this.results.firstChild.rows.length - 2 ? this._W.rowIndex + 1 : b]);
                        break;
                    case 13:
                        this.autoSubmit ? this.Submit(this.input, !1) : (this.nGourl = !0,
                        null != this.results && "" != this.results.innerHTML && (c = this.input.value,
                            d = "",
                            null != this._W ? ("key_" + c in this._Y && "" != this._Y["key_" + c] && (d = this._Y["key_" + c].replace(/&amp;/g, "&").split(";")),
                                "" != d && d.length > 0 ? (e = this.results.firstChild.rows[1],
                                "undefined" != typeof e && this.setLine(e, a)) : this.setLine(this._W, a)) : (f = !1,
                            "key_" + c in this._Y && "" != this._Y["key_" + c] && (d = this._Y["key_" + c].replace(/&amp;/g, "&").split(";")),
                                "" != d && d.length > 0 ? (e = this.results.firstChild.rows[1],
                                "undefined" != typeof e && this.setLine(e, a)) : (alert("您输入的股票代码不存在！"),
                                    f = !0)),
                        null == this.callback || f || this.callback({
                            code: this.input.value,
                            type: this.Market,
                            mt: this.mType,
                            cnName: this.SName
                        })),
                            this.hiddenResults());
                        break;
                    default:
                        this.Suggest()
                }
            },
            Confirm: function(a) {
                var b, c, d, e;
                13 == a.keyCode ? (this.nGourl = !0,
                    null != this.results && "" != this.results.innerHTML ? (b = !1,
                        c = this.input.value,
                        d = "",
                        null != this._W ? ("key_" + c in this._Y && "" != this._Y["key_" + c] && (d = this._Y["key_" + c].replace(/&amp;/g, "&").split(";")),
                            "" != d && d.length > 0 ? (e = this.results.firstChild.rows[1],
                                this.setLine(e, a)) : this.setLine(this._W, a)) : ("key_" + c in this._Y && "" != this._Y["key_" + c] && (d = this._Y["key_" + c].replace(/&amp;/g, "&").split(";")),
                            "" != d && d.length > 0 ? (e = this.results.firstChild.rows[1],
                                this.setLine(e, a)) : (alert("您输入的股票代码不存在！"),
                                b = !0)),
                    null == this.callback || b || this.callback({
                        code: this.input.value,
                        type: this.Market,
                        mt: this.mType
                    })) : alert("请输入股票代码！"),
                    this.hiddenResults()) : this.Suggest()
            },
            _bd: function(a, b) {
                var c = this;
                return function() {
                    var e, d = null;
                    if ("undefined" != typeof b) {
                        for (e = 0; e < arguments.length; e++)
                            b.push(arguments[e]);
                        d = b
                    } else
                        d = arguments;
                    return a.apply(c, d)
                }
            },
            _gt: function() {
                return (new Date).getTime()
            },
            Suggest: function() {
                var a = this.input.value;
                this._U != a ? (this._U = a,
                    "" != a ? "key_" + a in this._Y ? this.Tip() : this._io(a, this._bd(this.Tip), this._bd(this.hiddenResults)) : (null != this.results && "" != this.results.innerHTML && (this._W = null),
                        this.hiddenResults())) : this.setResults()
            },
            setResults: function() {
                null != this.results && (this.results.style.display = "")
            },
            hiddenResults: function() {
                0 == this._hidden && null != this.results && (this.results.style.display = "none")
            },
            _io: function(a, b, d) {
                var e, f;
                null == this._R && (this._R = c.$C("div"),
                    this._R.style.display = "none",
                    document.body.insertBefore(this._R, document.body.lastChild)),
                    e = "sData",
                    f = c.$C("script"),
                    f.type = "text/javascript",
                    f.charset = "gb2312",
                    f.src = this.dataurl.replace("{#NAME}", e).replace("{#KEY}", escape(a)).replace("{#TYPE}", this.GetShowType()).replace("{#RND}", this._gt()),
                    f._0j = this,
                b && (f._0k = b),
                d && (f._0l = d),
                    f._0m = a,
                    f._0n = e,
                    f[document.all ? "onreadystatechange" : "onload"] = function() {
                        if (!document.all || "loaded" == this.readyState || "complete" == this.readyState) {
                            var a = window[this._0n];
                            "undefined" != typeof a && (this._0j._Y["key_" + this._0m] = a,
                                this._0k(a),
                                window[this._0n] = null),
                                this._0j = null,
                                this._0m = null,
                                this._0n = null,
                                this[document.all ? "onreadystatechange" : "onload"] = null,
                                this.parentNode.removeChild(this)
                        }
                    }
                    ,
                    this._R.appendChild(f)
            },
            Submit: function(a, b) {
                var c, d, e, f, g, h, i, j, k;
                if ("undefined" == typeof b && (b = !0),
                    b && (this._D = null),
                        c = "",
                    null == this._D && (d = this.input.value,
                        this._Y["key_" + d] ? (c = this._Y["key_" + d].replace(/&amp;/g, "&").replace(/;$/, "").split(";"),
                        "" != c && c.length > 0 && (e = c[0].split(","),
                            this._D = e)) : c = "http://quote.eastmoney.com/"),
                    null != this._D && "" != this._D) {
                    switch (this._D[2]) {
                        case "1":
                        case "2":
                        case "3":
                        case "5":
                        case "10":
                        case "41":
                            f = "sh",
                            "2" == this._D[5] && (f = "sz"),
                                c = "/stockeast/detail?stockCode=" + this._D[1];
                            break;
                        case "4":
                            c = "http://quote.eastmoney.com/qihuo/" + this._D[1] + ".html";
                            break;
                        case "40":
                            c = "http://quote.eastmoney.com/zs" + this._D[1] + ".html";
                            break;
                        case "11":
                        case "12":
                        case "13":
                        case "14":
                        case "15":
                        case "16":
                            c = "http://fund.eastmoney.com/" + this._D[1] + ".html";
                            break;
                        case "21":
                        case "22":
                            c = "http://quote.eastmoney.com/hk/" + this._D[1] + ".html";
                            break;
                        case "31":
                        case "32":
                            c = "http://quote.eastmoney.com/us/" + this._D[1] + ".html";
                            break;
                        case "50":
                        case "51":
                            c = "http://quote.eastmoney.com/gzqh/" + this._D[1] + ".html";
                            break;
                        case "55":
                            c = "http://quote.eastmoney.com/qiquan/" + this._D[1] + "_SO.html";
                            break;
                        case "56":
                            c = "http://quote.eastmoney.com/qiquan/" + this._D[1] + "_FO.html";
                            break;
                        case "60":
                            c = "http://quote.eastmoney.com/3ban/sz" + this._D[1] + ".html";
                            break;
                        default:
                            c = "http://quote.eastmoney.com/" + this._D[1] + ".html"
                    }
                    "" != c && (g = !1,
                        h = this.input.name,
                        i = this.input.value,
                        "undefined" != typeof a ? (this.input.name = "",
                            this.input.value = "",
                        13 == a.keyCode && (g = !0)) : (j = !isNaN(this.input.value) && 6 == this.input.value.length,
                        b && !j && (this.input.name = "stockcode",
                            c = "http://quote.eastmoney.com/search.html",
                        (this.text.indexOf(this.input.value) >= 0 || "" == this.input.value) && (this.input.name = "",
                            this.input.value = "",
                            c = "http://quote.eastmoney.com/"))),
                        this.goUrl(c, "_blank", g),
                        this.input.name = h,
                        this.input.value = i)
                } else
                    h = this.input.name,
                        i = this.input.value,
                        this.input.name = "stockcode",
                        k = "http://quote.eastmoney.com/search.html",
                    (this.text.indexOf(this.input.value) >= 0 || "" == this.input.value) && (this.input.name = "",
                        this.input.value = "",
                        k = "http://quote.eastmoney.com/"),
                        this.goUrl(k, "_blank", g),
                        this.input.name = h,
                        this.input.value = i
            },
            goUrl: function(a, b, c) {
                null != this._F ? (this._F.action = a,
                    this._F.target = b,
                    this._F.method = "get",
                    this._F.onsubmit = function() {
                        return !0
                    }
                    ,
                c || this._F.submit()) : alert("Error"),
                    this.hiddenResults()
            },
            setColor: function(a) {
                var b = "";
                a._0f && a._0g ? b = "#F8FBDF" : a._0f ? b = "#F1F5FC" : a._0g && (b = "#FCFEDF"),
                a.style && (a.style.backgroundColor || a.style.backgroundColor != b) && (a.style.backgroundColor = b)
            },
            setLine: function(a, b) {
                var d, c = a.id.split(",");
                this._D = c,
                    d = c[1],
                    this._U = d,
                    this.Market = c[2],
                    this.mType = c[5],
                    this.SName = c[4],
                    this.input.value = d,
                null != this._W && (this._W._0f = !1,
                    this.setColor(this._W)),
                    a._0f = !0,
                    this.setColor(a),
                    this._W = a,
                this.autoSubmit && this.nGourl && this.Submit(b, !1)
            },
            mouseoverLine: function(a) {
                a._0g = !0,
                    this.setColor(a)
            },
            mouseoutLine: function(a) {
                a._0g = !1,
                    this.setColor(a)
            },
            setLineMouse: function(a) {
                this.nGourl = !0,
                    this.setLine(a),
                null != this.callback && this.callback({
                    code: this.input.value,
                    type: this.Market,
                    mt: this.mType,
                    cnName: this.SName
                })
            },
            hidepause: function() {
                this._hidden = !0
            },
            hideresume: function() {
                this._hidden = !1,
                    this.hiddenResults()
            },
            setTip: function() {
                var e, f, g, h, i, a = 0, b = 0, d = this.input;
                if ("relative" != c.getStyle(d.parentNode, "position").toLowerCase()) {
                    do {
                        if (a += d.offsetTop || 0,
                                b += d.offsetLeft || 0,
                            "relative" == c.getStyle(d, "position").toLowerCase())
                            break;
                        d = d.offsetParent
                    } while (d);e = [1 * this.input.parentNode.style.borderTopWidth.replace("px", ""), 1 * this.input.parentNode.style.borderLeftWidth.replace("px", "")],
                        f = [1, 1],
                    this.results.style.top != a + "px" && (this.results.style.top = a - e[0] + f[0] + "px"),
                    this.results.style.left != b + "px" && (this.results.style.left = b - e[1] + f[1] + "px")
                } else
                    this.results.style.top = "0",
                        this.results.style.left = "0";
                g = this.input.style.borderTopWidth,
                    h = this.input.style.borderBottomWidth,
                    i = this.input.clientHeight,
                    i += "" != g ? 1 * g.replace("px", "") : 2,
                    i += "" != h ? 1 * h.replace("px", "") : 2,
                this.results.style.marginTop != i + "px" && (this.results.style.marginTop = i + "px")
            },
            compare: function(a) {
                return function(b, c) {
                    var d = b[a]
                        , e = c[a];
                    return e > d ? -1 : d > e ? 1 : 0
                }
            },
            Tip: function() {
                var b, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, a = this.input.value;
                if ("key_" + a in this._Y && this._Y["key_" + a] && "undefined" != this._Y["key_" + a] && "" != this._Y["key_" + a]) {
                    if (null == this.results && (this.results = c.$C("div"),
                            this.results.className = "suggest-result",
                            this.results.style.cssText = "z-index:9999;width:" + this.width + "px;opacity:" + this.opacity + ";filter:alpha(opacity:" + 100 * this.opacity + ");position:absolute;display:none;",
                            "" == this.className ? this.results.style.border = "1px solid #ccc" : this.results.className = this.className,
                            this.input.parentNode.insertBefore(this.results, this.input),
                            this.results.suggest = this),
                            this.setTip(),
                            this.results.innerHTML = "",
                            b = c.$C("table"),
                            b.border = "0",
                            b.cellPadding = "0",
                            b.cellSpacing = "0",
                            b.style.cssText = "line-height:18px;border:1px solid #FFF;background:#FFF;font-size:12px;text-align:center;color:#666;width:100%;",
                            d = c.$C("tbody"),
                            e = c.$C("tr"),
                            e.style.cssText = "background:#E6F4F5;height:22px;line-height:22px;overflow:hidden;",
                        null != this.header)
                        for (f = 0; f < this.header.length; f++)
                            g = c.$C("th"),
                            "代码" == this.header[f] && (g.width = 52),
                            "类型" == this.header[f] && (g.width = 40),
                                g.innerHTML = this.header[f],
                                e.appendChild(g);
                    for (d.appendChild(e),
                             h = this._Y["key_" + a].replace(/&amp;/g, "&").replace(/;$/, "").split(";"),
                             i = h.length > this.max ? this.max : h.length,
                             j = a.split("").reverse().join(""),
                             k = [],
                             f = 0; f < h.length; f++) {
                        switch (l = h[f].split(","),
                            m = {},
                            m.source = l,
                            m.opt = l[0].replace(a.toUpperCase(), '<span style="color:#F00;">' + a.toUpperCase() + "</span>"),
                            m.code = l[1],
                            m.scode = l[1],
                            m.stype = l[2],
                            m.stypeName = l[2]in this.StockType ? this.StockType[l[2]] : "--",
                        1 == l[2] && (m.stypeName = "2" == l[5] ? "深市" : "沪市"),
                            m.py = l[3],
                            m.name = l[4],
                            m.market = l[5],
                            m.search = l[6],
                            m.top = 1,
                        l[0].toUpperCase() == a.toUpperCase() && (m.top = 0),
                            m.search) {
                            case "0":
                                m.code = m.code.split("").reverse().join(""),
                                    m.code = m.code.replace(j.toUpperCase(), "#" + j.toUpperCase() + "#"),
                                    m.code = m.code.split("").reverse().join(""),
                                    m.code = m.code.replace("#" + a.toUpperCase() + "#", '<span style="color:#F00;">' + a.toUpperCase() + "</span>");
                                break;
                            case "1":
                                m.code = m.code.replace(a.toUpperCase(), '<span style="color:#F00;">' + a.toUpperCase() + "</span>");
                                break;
                            case "2":
                            case "3":
                                m.py = m.py.replace(a.toUpperCase(), '<span style="color:#F00;">' + a.toUpperCase() + "</span>");
                                break;
                            case "4":
                                m.name = m.name.replace(a.toUpperCase(), '<span style="color:#F00;">' + a.toUpperCase() + "</span>")
                        }
                        k.push(m)
                    }
                    for (k = k.sort(this.compare("market")),
                             f = 0; i > f; f++) {
                        for (l = [],
                                 l[-1] = k[f].opt,
                                 l[-2] = k[f].stypeName,
                                 l[0] = k[f].opt,
                                 l[1] = k[f].code,
                                 l[2] = k[f].stypeName,
                                 l[3] = k[f].py,
                                 l[4] = k[f].name,
                                 l[5] = k[f].market,
                                 l[6] = k[f].search,
                                 n = c.$C("tr"),
                                 o = k[f].source,
                                 p = [f, o[1], o[2], o[3], o[4], o[5], o[6]],
                                 n.id = p.join(","),
                                 n.style.cursor = "pointer",
                                 n._oj = this,
                                 n.onmouseover = function() {
                                     this._oj.mouseoverLine(this)
                                 }
                                 ,
                                 n.onmouseout = function() {
                                     this._oj.mouseoutLine(this)
                                 }
                                 ,
                                 n.onmousedown = function() {
                                     return this._oj.hidepause(this)
                                 }
                                 ,
                                 n.onclick = function() {
                                     this._oj.setLineMouse(this),
                                         this._oj.hideresume(this)
                                 }
                                 ,
                                 r = 0; r < this.body.length; r++)
                            q = c.$C("td"),
                                q.style.wordBreak = "break-all",
                                q.hidefocus = "true",
                                q.style.padding = "1px",
                                q.innerHTML = l[this.body[r]],
                                n.appendChild(q);
                        q = null,
                            d.appendChild(n)
                    }
                    s = c.$C("tr"),
                        s.id = "_AutoSuggest_tip_More_",
                        t = c.$C("td"),
                        t.colSpan = this.header.length,
                        t.align = "right",
                        t.hidefocus = "true",
                        _more_link = c.$C("a"),
                        _more_link.style.cssText = "color:#C00;float:none;clear:both;background:none;border:0;",
                        _more_link.href = "http://quote.eastmoney.com/search.html?stockcode=" + escape(a),
                        _more_link.target = "_blank",
                        _more_link.innerHTML = "更多查询结果&gt;&gt;",
                        _more_link._oj = this,
                        _more_link.onmousedown = function() {
                            return this._oj.hidepause(this)
                        }
                        ,
                        _more_link.onclick = function() {
                            this._oj.hideresume(this)
                        }
                        ,
                        t.appendChild(_more_link),
                        s.appendChild(t),
                        d.appendChild(s),
                        b.appendChild(d),
                        this.results.appendChild(b),
                        this.setResults()
                } else
                    this.hiddenResults()
            }
        }),
        window.StockSuggest = d
}();
