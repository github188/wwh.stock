function quote_phf() {
    this.Browser = {
        ie: /msie/.test(window.navigator.userAgent.toLowerCase()),
        ie11: /trident/.test(window.navigator.userAgent.toLowerCase()),
        moz: /gecko/.test(window.navigator.userAgent.toLowerCase()),
        opera: /opera/.test(window.navigator.userAgent.toLowerCase()),
        safari: /safari/.test(window.navigator.userAgent.toLowerCase())
    }
}
quote_phf.prototype.addBookMark = function() {
    var t = document.title
        , e = location.href;
    if (window.sidebar)
        window.sidebar.addPanel(t, e, "");
    else if (document.all)
        window.external.AddFavorite(e, t);
    else if (window.opera && window.print)
        return !0
}
    ,
    quote_phf.prototype.addBookEM = function() {
        var t = "东方财富网——中国财经证券门户网站"
            , e = "http://www.eastmoney.com/";
        if (window.sidebar)
            window.sidebar.addPanel(t, e, "");
        else if (document.all)
            window.external.AddFavorite(e, t);
        else if (window.opera && window.print)
            return !0
    }
    ,
    quote_phf.prototype.toQuote = function() {
        if (_this = this,
                stockcode_current = _this.trim(this.$("StockCode").value),
            "输代码、名称或简拼" == stockcode_current || "" == stockcode_current)
            return window.open("http://quote.eastmoney.com/"),
                !1;
        var t = /[0-9]{6}/
            , e = /[0-9]{1,}/
            , s = /[^0-9]{1,}/;
        return bool1 = stockcode_current.match(t),
            bool2 = stockcode_current.match(e),
            bool3 = stockcode_current.match(s),
            null != bool1 && 6 == stockcode_current.length ? (window.open("http://quote.eastmoney.com/search.html?stockcode=" + escape(stockcode_current)),
                !1) : null != bool2 && null == bool3 && stockcode_current.length < 3 ? (alert("股票代码至少输入3位！"),
                !1) : null == bool2 && null != bool3 && stockcode_current.length < 2 ? (alert("模糊查询时关键字至少2位！"),
                !1) : (window.open("http://quote.eastmoney.com/search.html?stockcode=" + escape(stockcode_current)),
                !1)
    }
    ,
    quote_phf.prototype.toGuBa = function() {
        return stockcode_current = this.$("StockCode").value,
            "输代码、名称或简拼" == stockcode_current || "" == stockcode_current ? (window.open("http://guba.eastmoney.com/"),
                !1) : void window.open("http://quote.eastmoney.com/search.html?toba=1&stockcode=" + escape(stockcode_current))
    }
    ,
    quote_phf.prototype.toNews = function() {
        stockcode_current = this.$("StockCode").value,
            window.open("输代码、名称或简拼" == stockcode_current ? "http://so.eastmoney.com/" : "http://so.eastmoney.com/Search.htm?q=" + escape(stockcode_current) + "&t=2")
    }
    ,
    quote_phf.prototype.getIndexQuote = function(RefreshTime) {
        var _this = this;
        _this.init = function() {
            _this.bindCLK(),
                _this.display(),
                _this.gethgts(),
            "" != RefreshTime && "undefined" != RefreshTime && null != RefreshTime && (setInterval(_this.display, 1e3 * RefreshTime),
                setInterval(_this.gethgts, 5e4))
        }
            ,
            _this.display = function() {
                if (_this.$("qqgscont") != null) {
                    var xvs = parseInt(_this.$("qqgscont").getAttribute("xvs"));
                    switch (xvs) {
                        case 1:
                            _this.JsLoader("http://hq2gjgp.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?jsName=us1i&reference=xml&type=zt&ids=INDU7,ccmp7,nky7&rt=" + Math.random(), "utf-8", function () {
                                var jnm = eval("us1i");
                                if ("undefined" != jnm && null != jnm.quotation && "" != jnm.quotation) {
                                    var tem_a = jnm.quotation[0].split(",")
                                        , tem_b = jnm.quotation[1].split(",")
                                        , tem_c = jnm.quotation[2].split(",")
                                        , _temstrs = '<a href="http://quote.eastmoney.com/gb/zsINDU.html" target="_blank" class="blue"><strong>道琼斯</strong></a> <span style="' + _this.udcolor(tem_a[6]) + '"><b>' + tem_a[2] + "</b> " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[5] + "</b>  " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[6] + "</b></span>";
                                    _temstrs += '<a href="http://quote.eastmoney.com/gb/zsCCMP.html" target="_blank" class="blue"><strong>纳斯达克</strong></a> <span style="' + _this.udcolor(tem_b[6]) + '"><b>' + tem_b[2] + "</b> " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[5] + "</b>  " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[6] + "</b></span>",
                                        _temstrs += '<a href="http://quote.eastmoney.com/gb/zsNKY.html" target="_blank" class="blue"><strong>日经</strong></a> <span style="' + _this.udcolor(tem_c[6]) + '"><b>' + tem_c[2] + "</b> " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[5] + "</b>  " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[6] + "</b></span>",
                                        _this.gethkindex(_temstrs)
                                }
                            });
                            break;
                        case 2:
                            _this.JsLoader("http://hq2gjgp.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?jsName=us2i&reference=xml&type=zt&ids=dax7,cac7,ukx7,atx7&rt=" + Math.random(), "utf-8", function () {
                                var jnm = eval("us2i");
                                if ("undefined" != jnm && null != jnm.quotation && "" != jnm.quotation) {
                                    var tem_a = jnm.quotation[0].split(",")
                                        , tem_b = jnm.quotation[1].split(",")
                                        , tem_c = jnm.quotation[2].split(",")
                                        , tem_d = jnm.quotation[3].split(",")
                                        , _temstrs = '<a href="http://quote.eastmoney.com/gb/zsDAX.html" target="_blank" class="blue"><strong>德DAX</strong></a> <span style="' + _this.udcolor(tem_a[6]) + '"><b>' + tem_a[2] + "</b> " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[5] + "</b>  " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[6] + "</b></span>";
                                    _temstrs += '<a href="http://quote.eastmoney.com/gb/zsCAC.html" target="_blank" class="blue"><strong>法CAC</strong></a> <span style="' + _this.udcolor(tem_b[6]) + '"><b>' + tem_b[2] + "</b> " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[5] + "</b>  " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[6] + "</b></span>",
                                        _temstrs += '<a href="http://quote.eastmoney.com/gb/zsUKX.html" target="_blank" class="blue"><strong>英FT</strong></a> <span style="' + _this.udcolor(tem_c[6]) + '"><b>' + tem_c[2] + "</b> " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[5] + "</b>  " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[6] + "</b></span>",
                                        _temstrs += '<a href="http://quote.eastmoney.com/gb/zsATX.html" target="_blank" class="blue"><strong>奥ATX</strong></a> <span style="' + _this.udcolor(tem_d[6]) + '"><b>' + tem_d[2] + "</b> " + _this.zdbjt(tem_d[5]) + "<b>" + tem_d[5] + "</b>  " + _this.zdbjt(tem_d[5]) + "<b>" + tem_d[6] + "</b></span>",
                                        _this.$("qqgscont").innerHTML = "<ul><li>" + _temstrs + "</li></ul>"
                                }
                            });
                            break;
                        default:
                            _this.JsLoader("http://" + _this.gmain() + "/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012&sty=DFPIU&st=z&sr=&p=&ps=&cb=&js=var%20C1Cache={quotation:[(x)]}&token=44c9d251add88e27b65ed86506f6e5da&" + Math.random(), "utf-8", function () {
                                var jnm = eval("C1Cache");
                                if ("undefined" != jnm && null != jnm.quotation && "" != jnm.quotation) {
                                    var tem_shdp = jnm.quotation[0].split(",")
                                        , tem_szdp = jnm.quotation[1].split(",")
                                        , tem_shzdp = tem_shdp[6].split("|")
                                        , tem_szzdp = tem_szdp[7].split("|")
                                        , _temstrs = '<a href="http://quote.eastmoney.com/zs000001.html" target="_blank" class="blue"><strong>上证</strong></a>：<span style="' + _this.udcolor(tem_shdp[4]) + '"><b>' + tem_shdp[2] + "</b> " + _this.zdbjt(tem_shdp[4]) + "<b>" + tem_shdp[4] + "</b>  " + _this.zdbjt(tem_shdp[5]) + "<b>" + tem_shdp[5] + "  " + _this.ForDight(tem_shdp[3] / 1e8, 2) + '</b></span>亿元(涨:<a href="http://quote.eastmoney.com/center/list.html#10_0_0_u?sortType=C&sortRule=-1" target="_blank" class="red"><b>' + tem_shzdp[0] + "</b></a> 平:<b>" + tem_shzdp[1] + '</b> 跌:<a href="http://quote.eastmoney.com/center/list.html#10_0_0_d?sortType=C&sortRule=1" target="_blank" class="green"><b>' + tem_shzdp[2] + "</b></a>)";
                                    _temstrs += '<a href="http://quote.eastmoney.com/zs399001.html" target="_blank" class="blue"><strong>深证</strong></a>：<span style="' + _this.udcolor(tem_szdp[4]) + '"><b>' + tem_szdp[2] + "</b> " + _this.zdbjt(tem_szdp[4]) + "<b>" + tem_szdp[4] + "</b>  " + _this.zdbjt(tem_szdp[5]) + "<b>" + tem_szdp[5] + "  " + _this.ForDight(tem_szdp[3] / 1e8, 2) + '</b></span>亿元(涨:<a href="http://quote.eastmoney.com/center/list.html#20_0_0_u?sortType=C&sortRule=-1" target="_blank" class="red"><b>' + tem_szzdp[0] + "</b></a> 平:<b>" + tem_szzdp[1] + '</b> 跌:<a href="http://quote.eastmoney.com/center/list.html#20_0_0_d?sortType=C&sortRule=1" target="_blank" class="green"><b>' + tem_szzdp[2] + "</b></a>)",
                                        _this.$("qqgscont").innerHTML = "<ul><li>" + _temstrs + "</li></ul>"
                                }
                            })
                    }
                }
            }
            ,
            _this.gethkindex = function(str) {
                _this.JsLoader("http://hq2hk.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?jsName=us12i&reference=xml&type=zt&ids=1100005&rt=" + Math.random(), "utf-8", function() {
                    var jnm = eval("us12i");
                    if ("undefined" != jnm && null != jnm.quotation && "" != jnm.quotation) {
                        var tem_a = jnm.quotation[0].split(",");
                        _this.$("qqgscont").innerHTML = str + '<a href="http://quote.eastmoney.com/hk/zs110000.html" target="_blank" class="blue"><strong>恒生</strong></a> <span style="' + _this.udcolor(tem_a[6]) + '"><b>' + tem_a[2] + "</b> " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[5] + "</b>  " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[6] + "</b></span>"
                    }
                })
            }
            ,
            _this.gethgts = function(str) {
                if (_this.$("hgtla") != null) {
                    _this.JsLoader("http://nufm3.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011&sty=SHSTD&st=z&sr=&p=&ps=&cb=&js=var%20hgti=(x);&token=beb0a0047196124721f56b0f0ff5a27c&rt=" + Math.random(), "utf-8", function () {
                        var jnm = eval("hgti");
                        if (void 0 == jnm.stats) {
                            var tem_a = jnm.split(",");
                            _this.$("hgtla").innerHTML = _this.vsfmt(tem_a[6], 1, 1),
                                _this.$("hgtlb").innerHTML = _this.vsfmt(tem_a[7], 0, 1),
                                _this.$("hgtlc").innerHTML = _this.vsfmt(tem_a[8], 0, 0),
                                _this.$("hgtra").innerHTML = _this.vsfmt(tem_a[0], 1, 1),
                                _this.$("hgtrb").innerHTML = _this.vsfmt(tem_a[1], 0, 1),
                                _this.$("hgtrc").innerHTML = _this.vsfmt(tem_a[2], 0, 0),
                                _this.$("hgtrun").innerHTML = _this.GetGangGuTongPNG(tem_a[9]),
                                _this.$("ggtrun").innerHTML = _this.GetGangGuTongPNG(tem_a[3])
                        }
                    })
                }
            }
            ,
            _this.bindCLK = function() {
                var t = _this.$("btn_up")
                    , e = _this.$("btn_down")
                    , s = _this.$("qqgscont");
                if (t != null) {
                    t.onclick = function () {
                        var t = parseInt(s.getAttribute("xvs"));
                        t++,
                        t >= 3 && (t = 0),
                            s.setAttribute("xvs", t),
                            _this.display()
                    }
                };
                if (e != null) {
                    e.onclick = function () {
                        var t = parseInt(s.getAttribute("xvs"));
                        t--,
                        0 > t && (t = 2),
                            s.setAttribute("xvs", t),
                            _this.display()
                    }
                };
            }
            ,
            _this.init()
    }
    ,
    quote_phf.prototype.formatm = function() {
        var t = new Date;
        return t.getDate() + "" + t.getHours() + t.getMinutes()
    }
    ,
    quote_phf.prototype.GetRandomNum = function(t, e) {
        var s = e - t
            , o = Math.random();
        return t + Math.round(o * s)
    }
    ,
    quote_phf.prototype.gmain = function() {
        var t = this
            , e = 1
            , s = 10
            , o = "nufm2.dfcfw.com"
            , n = "nufm3.dfcfw.com"
            , a = t.GetRandomNum(e, s);
        return "1" == a && "2" == a && "3" == a && "4" == a && "5" == a && "6" == a && (o = n),
            o
    }
    ,
    quote_phf.prototype.udcolor = function(t, e) {
        return t = t.replace("%", ""),
            "" == e || null == e || "undefined" == e ? t > 0 ? "color:#f00" : 0 > t ? "color:#090" : "" : (e = e.replace("%", ""),
                t - e > 0 ? "color:#f00" : 0 > t - e ? "color:#090" : "")
    }
    ,
    quote_phf.prototype.udc = function(t) {
        return t = t.replace("%", ""),
            t > 0 ? ' style="color:#f00"' : 0 > t ? ' style="color:#090"' : ""
    }
    ,
    quote_phf.prototype.vsfmt = function(t, e, s) {
        var o = "&nbsp;&nbsp;-&nbsp;&nbsp;";
        if ("" != t && "-" != t) {
            var n = t.substring(t.length - 2)
                , a = "";
            a = "亿元" == n || "万元" == n ? n : "元";
            var r = t.replace("亿元", "").replace("万元", "");
            o = 1 == e ? 1 == s ? '<b><a href="http://data.eastmoney.com/bkzj/hgt.html" target="_blank"' + this.udc(r) + ">" + r + "</a></b>" + a : "<b" + this.udc(r) + ">" + r + "</b>" + a : 1 == s ? '<b><a href="http://data.eastmoney.com/bkzj/hgt.html" target="_blank">' + r + "</a></b>" + a : "<b>" + r + "</b>" + a
        }
        return o.replace("元", "")
    }
    ,
    quote_phf.prototype.GetGangGuTongPNG = function(t) {
        var e = ""
            , s = parseFloat(t);
        if ("NaN" == s || isNaN(s))
            return e = "<b></b>正常";
        switch (s) {
            case 0:
                e = "<b></b>有额度";
                break;
            case 2:
                e = "<b></b>午休";
                break;
            case 4:
                e = "<b></b>清空";
                break;
            case -2:
                e = "<b></b>无额度";
                break;
            case -1:
                e = '<b class="off"></b>停牌';
                break;
            case 1:
                e = '<b class="off"></b>收盘';
                break;
            case 3:
                e = '<b class="off"></b>休市';
                break;
            case 5:
                e = "<b></b>限买";
                break;
            case 6:
                e = "<b></b>限卖";
                break;
            case 7:
                e = '<b class="off"></b>暂停';
                break;
            case 8:
                e = '<b class="off"></b>5%熔断';
                break;
            case 9:
                e = '<b class="off"></b>7%熔断';
                break;
            case 10:
                e = '<b class="off"></b>-5%熔断';
                break;
            case 11:
                e = '<b class="off"></b>-7%熔断'
        }
        return e
    }
    ,
    quote_phf.prototype.zdbjt = function(t) {
        return t > 0 ? "↑" : 0 > t ? "↓" : ""
    }
    ,
    quote_phf.prototype.ForDight = function(t, e) {
        return rDight = parseFloat(t).toFixed(e),
        "NaN" == rDight && (rDight = "--"),
            rDight
    }
    ,
    quote_phf.prototype.$ = function(t) {
        return "string" == typeof t ? document.getElementById(t) : t
    }
    ,
    quote_phf.prototype.JsLoader = function(t, e, s) {
        var o = document.createElement("script");
        o.setAttribute("charset", e),
            o.setAttribute("type", "text/javascript"),
            o.setAttribute("src", t),
            document.getElementsByTagName("head")[0].appendChild(o),
            this.Browser.ie ? o.onreadystatechange = function() {
                ("loaded" == this.readyState || "complete" == this.readyState) && (o.parentNode.removeChild(o),
                    s())
            }
                : this.Browser.moz || this.Browser.opera ? o.onload = function() {
                o.parentNode.removeChild(o),
                    s()
            }
                : (o.parentNode.removeChild(o),
                s())
    }
    ,
    quote_phf.prototype.trim = function(t) {
        return t.replace(/(^\s*)|(\s*$)/g, "")
    }
;
var qphf = new quote_phf;
