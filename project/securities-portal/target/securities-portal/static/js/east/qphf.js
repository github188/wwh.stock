function quote_phf() {
    this.Browser = {
        ie: /msie/.test(window.navigator.userAgent.toLowerCase()),
        ie11: /trident/.test(window.navigator.userAgent.toLowerCase()),
        moz: /gecko/.test(window.navigator.userAgent.toLowerCase()),
        opera: /opera/.test(window.navigator.userAgent.toLowerCase()),
        safari: /safari/.test(window.navigator.userAgent.toLowerCase())
    };
}
//收藏
quote_phf.prototype.addBookMark = function () {
    var title = document.title;
    var url = location.href;
    if (window.sidebar) { window.sidebar.addPanel(title, url, ""); }
    else if (document.all) {
        window.external.AddFavorite(url, title);
    } else if (window.opera && window.print) {
        return true;
    }
};
//收藏
quote_phf.prototype.addBookEM = function () {
    var title = "东方财富网——中国财经证券门户网站";
    var url = "http://www.eastmoney.com/";
    if (window.sidebar) { window.sidebar.addPanel(title, url, ""); }
    else if (document.all) {
        window.external.AddFavorite(url, title);
    } else if (window.opera && window.print) {
        return true;
    }
};
//查行情
quote_phf.prototype.toQuote = function () {
    _this = this;
    stockcode_current = _this.trim(this.$(("StockCode")).value);
    if (stockcode_current == "输代码、名称或简拼" || stockcode_current == "") { window.open('http://quote.eastmoney.com/'); return false; }
    var re = /[0-9]{6}/; var re2 = /[0-9]{1,}/; var re3 = /[^0-9]{1,}/;
    bool1 = stockcode_current.match(re); bool2 = stockcode_current.match(re2); bool3 = stockcode_current.match(re3);
    if (bool1 != null && stockcode_current.length == 6) {
        window.open('http://quote.eastmoney.com/search.html?stockcode=' + escape(stockcode_current)); return false;
    }
    else {
        if (bool2 != null && bool3 == null && stockcode_current.length < 3) { alert("股票代码至少输入3位！"); return false; }
        if (bool2 == null && bool3 != null && stockcode_current.length < 2) { alert("模糊查询时关键字至少2位！"); return false; }
        window.open('http://quote.eastmoney.com/search.html?stockcode=' + escape(stockcode_current)); return false;
    }
    return (false);
};
//进股吧
quote_phf.prototype.toGuBa = function () {
    stockcode_current = this.$(("StockCode")).value;
    if (stockcode_current == "输代码、名称或简拼" || stockcode_current == "") { window.open('http://guba.eastmoney.com/'); return false; }
    //var re=/[0-9]{6}/; 
    //bool1=stockcode_current.match(re);
    //if(bool1!=null){window.open('http://guba.eastmoney.com/topic,'+stockcode_current+'.html');}
    //else{window.open('http://quote.eastmoney.com/search.html?toba=1&stockcode='+escape(stockcode_current));}
    window.open('http://quote.eastmoney.com/search.html?toba=1&stockcode=' + escape(stockcode_current));
};
//查资讯
quote_phf.prototype.toNews = function () {
    stockcode_current = this.$(("StockCode")).value;
    if (stockcode_current == "输代码、名称或简拼") { window.open('http://so.eastmoney.com/'); } else { window.open('http://so.eastmoney.com/Search.htm?q=' + escape(stockcode_current) + '&t=2'); }
};
quote_phf.prototype.getIndexQuote = function (RefreshTime) {
    var _this = this;
    _this.init = function () {
        _this.bindCLK();
        _this.display();
        _this.gethgts();
        if (RefreshTime != "" && RefreshTime != "undefined" && RefreshTime != null) { setInterval(_this.display, RefreshTime * 1000); setInterval(_this.gethgts, 50000); }
    };
    _this.display = function () {
        var xvs = parseInt(_this.$("qqgscont").getAttribute("xvs"));
        switch (xvs) {
            case 1:
                _this.JsLoader("http://hq2gjgp.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?jsName=us1i&reference=xml&type=zt&ids=INDU7,ccmp7,nky7&rt=" + Math.random(), 'utf-8', function () {
                    var jnm = eval("us1i");
                    if (jnm != "undefined") {
                        if (jnm.quotation != null && jnm.quotation != "") {
                            var tem_a = jnm.quotation[0].split(','); var tem_b = jnm.quotation[1].split(','); var tem_c = jnm.quotation[2].split(',');
                            var _temstrs = "<a href=\"http://quote.eastmoney.com/gb/zsINDU.html\" target=\"_blank\" class=\"blue\"><strong>道琼斯</strong></a> <span style=\"" + _this.udcolor(tem_a[6]) + "\"><b>" + tem_a[2] + "</b> " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[5] + "</b>  " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[6] + "</b></span>";
                            _temstrs += "<a href=\"http://quote.eastmoney.com/gb/zsCCMP.html\" target=\"_blank\" class=\"blue\"><strong>纳斯达克</strong></a> <span style=\"" + _this.udcolor(tem_b[6]) + "\"><b>" + tem_b[2] + "</b> " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[5] + "</b>  " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[6] + "</b></span>";
                            _temstrs += "<a href=\"http://quote.eastmoney.com/gb/zsNKY.html\" target=\"_blank\" class=\"blue\"><strong>日经</strong></a> <span style=\"" + _this.udcolor(tem_c[6]) + "\"><b>" + tem_c[2] + "</b> " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[5] + "</b>  " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[6] + "</b></span>";
                            _this.gethkindex(_temstrs)
                        }
                    }
                });
                break;
            case 2:
                _this.JsLoader("http://hq2gjgp.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?jsName=us2i&reference=xml&type=zt&ids=dax7,cac7,ukx7,atx7&rt=" + Math.random(), 'utf-8', function () {
                    var jnm = eval("us2i");
                    if (jnm != "undefined") {
                        if (jnm.quotation != null && jnm.quotation != "") {
                            var tem_a = jnm.quotation[0].split(','); var tem_b = jnm.quotation[1].split(','); var tem_c = jnm.quotation[2].split(','); var tem_d = jnm.quotation[3].split(',');
                            var _temstrs = "<a href=\"http://quote.eastmoney.com/gb/zsDAX.html\" target=\"_blank\" class=\"blue\"><strong>德DAX</strong></a> <span style=\"" + _this.udcolor(tem_a[6]) + "\"><b>" + tem_a[2] + "</b> " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[5] + "</b>  " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[6] + "</b></span>";
                            _temstrs += "<a href=\"http://quote.eastmoney.com/gb/zsCAC.html\" target=\"_blank\" class=\"blue\"><strong>法CAC</strong></a> <span style=\"" + _this.udcolor(tem_b[6]) + "\"><b>" + tem_b[2] + "</b> " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[5] + "</b>  " + _this.zdbjt(tem_b[5]) + "<b>" + tem_b[6] + "</b></span>";
                            _temstrs += "<a href=\"http://quote.eastmoney.com/gb/zsUKX.html\" target=\"_blank\" class=\"blue\"><strong>英FT</strong></a> <span style=\"" + _this.udcolor(tem_c[6]) + "\"><b>" + tem_c[2] + "</b> " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[5] + "</b>  " + _this.zdbjt(tem_c[5]) + "<b>" + tem_c[6] + "</b></span>";
                            _temstrs += "<a href=\"http://quote.eastmoney.com/gb/zsATX.html\" target=\"_blank\" class=\"blue\"><strong>奥ATX</strong></a> <span style=\"" + _this.udcolor(tem_d[6]) + "\"><b>" + tem_d[2] + "</b> " + _this.zdbjt(tem_d[5]) + "<b>" + tem_d[5] + "</b>  " + _this.zdbjt(tem_d[5]) + "<b>" + tem_d[6] + "</b></span>";
                            _this.$("qqgscont").innerHTML = "<ul><li>" + _temstrs + "</li></ul>";
                        }
                    }
                });
                break;
            default:
                _this.JsLoader("http://" + _this.gmain() + "/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012&sty=DFPIU&st=z&sr=&p=&ps=&cb=&js=var%20C1Cache={quotation:[(x)]}&token=44c9d251add88e27b65ed86506f6e5da&" + Math.random(), 'utf-8', function () {
                    var jnm = eval("C1Cache");
                    //(0)标识、(1)名称、(2)现价、(3)成交额、(4)涨跌额、(5)涨跌幅%、(6)上海涨跌平家数、(7)深圳涨跌平家数
                    if (jnm != "undefined") {
                        if (jnm.quotation != null && jnm.quotation != "") {
                            var tem_shdp = jnm.quotation[0].split(','); var tem_szdp = jnm.quotation[1].split(',');
                            var tem_shzdp = tem_shdp[6].split('|'); var tem_szzdp = tem_szdp[7].split('|');
                            var _temstrs = "<a href=\"/stockeast/zsdetail?code=000001\" target=\"_self\" class=\"blue\"><strong>上证</strong></a>：<span style=\"" + _this.udcolor(tem_shdp[4]) + "\"><b>" + tem_shdp[2] + "</b> " + _this.zdbjt(tem_shdp[4]) + "<b>" + tem_shdp[4] + "</b>  " + _this.zdbjt(tem_shdp[5]) + "<b>" + tem_shdp[5] + "  " + _this.ForDight(tem_shdp[3] / 100000000, 2) + "</b></span>亿元(涨:<a href=\""+portalurl+"stockeast/marketlist#10_0_0_u?sortType=C&sortRule=-1\" target=\"_self\" class=\"red\"><b>" + tem_shzdp[0] + "</b></a> 平:<b>" + tem_shzdp[1] + "</b> 跌:<a href=\""+portalurl+"stockeast/marketlist#10_0_0_d?sortType=C&sortRule=1\" target=\"_self\" class=\"green\"><b>" + tem_shzdp[2] + "</b></a>)";
                            _temstrs += "&nbsp;&nbsp;&nbsp;<a href=\"/stockeast/zsdetail?code=399001\" target=\"_self\" class=\"blue\"><strong>深证</strong></a>：<span style=\"" + _this.udcolor(tem_szdp[4]) + "\"><b>" + tem_szdp[2] + "</b> " + _this.zdbjt(tem_szdp[4]) + "<b>" + tem_szdp[4] + "</b>  " + _this.zdbjt(tem_szdp[5]) + "<b>" + tem_szdp[5] + "  " + _this.ForDight(tem_szdp[3] / 100000000, 2) + "</b></span>亿元(涨:<a href=\""+portalurl+"stockeast/marketlist#20_0_0_u?sortType=C&sortRule=-1\" target=\"_self\" class=\"red\"><b>" + tem_szzdp[0] + "</b></a> 平:<b>" + tem_szzdp[1] + "</b> 跌:<a href=\""+portalurl+"stockeast/marketlist#20_0_0_d?sortType=C&sortRule=1\" target=\"_self\" class=\"green\"><b>" + tem_szzdp[2] + "</b></a>)";
                            _this.$("qqgscont").innerHTML = "<ul><li>" + _temstrs + "</li></ul>";
                        }
                    }
                });
        }
    };
    _this.gethkindex = function (str) {
        _this.JsLoader("http://hq2hk.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?jsName=us12i&reference=xml&type=zt&ids=1100005&rt=" + Math.random(), 'utf-8', function () {
            var jnm = eval("us12i");
            if (jnm != "undefined") {
                if (jnm.quotation != null && jnm.quotation != "") {
                    var tem_a = jnm.quotation[0].split(',');
                    _this.$("qqgscont").innerHTML = str + "<a href=\"http://quote.eastmoney.com/hk/zs110000.html\" target=\"_blank\" class=\"blue\"><strong>恒生</strong></a> <span style=\"" + _this.udcolor(tem_a[6]) + "\"><b>" + tem_a[2] + "</b> " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[5] + "</b>  " + _this.zdbjt(tem_a[5]) + "<b>" + tem_a[6] + "</b></span>";
                }
            }
        });
    };
    _this.gethgts = function (str) {
        _this.JsLoader("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=P.(x),(x)|0000011|3990012&sty=SHSTD|SZSTD&st=z&sr=&p=&ps=&cb=&js=var%20hgti=[(x)];&token=beb0a0047196124721f56b0f0ff5a27c", 'utf-8', function () {
            var jnm = eval("hgti");
            if (jnm.stats == undefined) {

                var tem_a = jnm[0].split(',');
                var tem_b = jnm[1].split(',');

                _this.$("hgtzj").innerHTML = _this.vsfmt(tem_a[6], 1, 1);
                _this.$("sgtzj").innerHTML = _this.vsfmt(tem_b[6], 1, 1);
                _this.$("ggthzj").innerHTML = _this.vsfmt(tem_a[0], 1, 1);
                _this.$("ggtszj").innerHTML = _this.vsfmt(tem_b[0], 1, 1);

                _this.$("hgtrun").innerHTML = _this.GetGangGuTongPNG(tem_a[9]);
                _this.$("sgtrun").innerHTML = _this.GetGangGuTongPNG(tem_b[9]);
                _this.$("ggthrun").innerHTML = _this.GetGangGuTongPNG(tem_a[3]);
                _this.$("ggtsrun").innerHTML = _this.GetGangGuTongPNG(tem_b[3]);
            }
        });
    };
    _this.bindCLK = function () {
        var obu = _this.$("btn_up"); var obd = _this.$("btn_down"); var oj = _this.$("qqgscont");
        obu.onclick = function () { var xvs = parseInt(oj.getAttribute("xvs")); xvs++; if (xvs >= 3) { xvs = 0; } oj.setAttribute("xvs", xvs); _this.display(); };
        obd.onclick = function () { var xvs = parseInt(oj.getAttribute("xvs")); xvs--; if (xvs < 0) { xvs = 2; } oj.setAttribute("xvs", xvs); _this.display(); };
    };
    _this.init();
};

quote_phf.prototype.formatm = function () {
    var now = new Date();
    return now.getDate() + "" + now.getHours() + "" + now.getMinutes() + "";
};
//随机数
quote_phf.prototype.GetRandomNum = function (Min, Max) { var Range = Max - Min; var Rand = Math.random(); return (Min + Math.round(Rand * Range)); };
quote_phf.prototype.gmain = function () {
    var _this = this;
    var min = 1; var max = 10;
    var res = "nufm2.dfcfw.com"; var m3 = "nufm3.dfcfw.com";
    var rom = _this.GetRandomNum(min, max);
    if (rom == "1" && rom == "2" && rom == "3" && rom == "4" && rom == "5" && rom == "6") { res = m3; }
    return res;
};
quote_phf.prototype.udcolor = function (vsa, vsb) {
    vsa = vsa.replace("%", "");
    if (vsb == "" || vsb == null || vsb == "undefined") {
        if (vsa > 0) { return "color:#f00"; }
        else if (vsa < 0) { return "color:#090"; }
        else { return ""; }
    }
    else {
        vsb = vsb.replace("%", "");
        if (vsa - vsb > 0) { return "color:#f00"; }
        else if (vsa - vsb < 0) { return "color:#090"; }
        else { return ""; }
    }
};

quote_phf.prototype.udc = function (vs) {
    vs = vs.replace("%", "");
    if (vs > 0) {
        return " style=\"color:#f00\"";
    } else if (vs < 0) {
        return " style=\"color:#090\"";
    } else {
        return "";
    }
};

quote_phf.prototype.vsfmt = function (vs, iscl, lk) {
    var res = "&nbsp;&nbsp;-&nbsp;&nbsp;";
    if (vs != "" && vs != "-") {
        var last = vs.substring(vs.length - 2);
        var unit = "";
        if (last == "亿元" || last == "万元") {
            unit = last;
        } else {
            unit = "元";
        }
        var str = vs.replace("亿元", "").replace("万元", "");
        if (iscl == 1) {
            if (lk == 1) {
                res = "<b><a href=\"http://data.eastmoney.com/hsgt/index.html\" target=\"_blank\"" + this.udc(str) + ">" + str + "</a></b>" + unit;
            } else {
                res = "<b" + this.udc(str) + ">" + str + "</b>" + unit;
            }
        } else {
            if (lk == 1) {
                res = "<b><a href=\"http://data.eastmoney.com/hsgt/index.html\" target=\"_blank\">" + str + "</a></b>" + unit;
            } else {
                res = "<b>" + str + "</b>" + unit;
            }
        }
    }
    return res.replace("元", "");
};

quote_phf.prototype.GetGangGuTongPNG = function (number) {
    var typeStr = "";
    var type = parseFloat(number);
    if (type == "NaN" || isNaN(type)) {
        typeStr = '<b></b>正常';
        return typeStr;
    }
    switch (type) {
        case 0: typeStr = '<b></b>有额度'; break;
        case 2: typeStr = '<b></b>午休'; break;
        case 4: typeStr = '<b></b>清空'; break;
        case -2: typeStr = '<b></b>无额度'; break;
        case -1: typeStr = '<b class="off"></b>停牌'; break;
        case 1: typeStr = '<b class="off"></b>收盘'; break;
        case 3: typeStr = '<b class="off"></b>休市'; break;
        case 5: typeStr = '<b></b>限买'; break;
        case 6: typeStr = '<b></b>限卖'; break;
        case 7: typeStr = '<b class="off"></b>暂停'; break;
        case 8: typeStr = '<b class="off"></b>5%熔断'; break;
        case 9: typeStr = '<b class="off"></b>7%熔断'; break;
        case 10: typeStr = '<b class="off"></b>-5%熔断'; break;
        case 11: typeStr = '<b class="off"></b>-7%熔断'; break;
    }
    //    额度可用->有额度
    //    午盘休息->午休
    //    早盘清空 ->清空
    //    额度用完 ->无额度
    //    今日停牌 ->停牌
    //    股市收盘 ->收盘
    //    今日休市 ->休市
    //    限制买入 ->限买
    //    限制卖出 ->限卖
    //    暂停
    return typeStr;
};

quote_phf.prototype.zdbjt = function (vs) {
    if (vs > 0) { return "↑"; }
    else if (vs < 0) { return "↓"; }
    else { return ""; }
};

quote_phf.prototype.ForDight = function (Dight, How) {//四舍五入
    rDight = parseFloat(Dight).toFixed(How);
    if (rDight == "NaN") { rDight = "--"; }
    return rDight;
};

quote_phf.prototype.$ = function (id) {
    return "string" == typeof id ? document.getElementById(id) : id;
};

quote_phf.prototype.JsLoader = function (sUrl, sBianMa, fCallback) {
    var _script = document.createElement('script');
    _script.setAttribute('charset', sBianMa);
    _script.setAttribute('type', 'text/javascript');
    _script.setAttribute('src', sUrl);
    document.getElementsByTagName('head')[0].appendChild(_script);
    if (this.Browser.ie) {
        _script.onreadystatechange = function () {
            if (this.readyState == 'loaded' || this.readyState == 'complete') {
                _script.parentNode.removeChild(_script);
                fCallback();
            }
        };
    } else if (this.Browser.moz || this.Browser.opera) {
        _script.onload = function () {
            _script.parentNode.removeChild(_script);
            fCallback();
        };
    } else {
        _script.parentNode.removeChild(_script);
        fCallback();
    }
};
quote_phf.prototype.trim = function (str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
};
var qphf = new quote_phf();