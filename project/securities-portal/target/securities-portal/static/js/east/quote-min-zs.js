var gdomain = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/";
var token = "44c9d251add88e27b65ed86506f6e5da";
var PicR = "http://hqpicr.eastmoney.com/r/"; var PicK = "http://hqpick.eastmoney.com/k/"; var PicN = "http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/Index.aspx?id={0}{1}&imageType={2}&token=" + token;
var $x = function (id) { return "string" == typeof id ? document.getElementById(id) : id; };
function inArray(el, array) { for (var i = 0, n = array.length; i < n; i++) { if (array[i] === el) { return true; } } return false; }
function unique(array) { var i = 0, n = array.length, ret = []; for (; i < n; i++) { if (!inArray(array[i], ret)) { ret.push(array[i]); } } return ret; }
function ForDight(Dight, How) { rDight = parseFloat(Dight).toFixed(How); if (rDight == "NaN") { rDight = "--"; } return rDight; }
//显示完整时间
function GetFullWeekTime(time) {
    var dt = new Date(Date.parse(time.replace(/-/g, "/")));
    var day = dt.getDay();
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    var week = arr_week[day];
    return time.replace(" ", " " + week + " ");
}
//刷新本页
function prefresh() {
    window.location.reload();
}

//涨跌标记
function udt(vs) {
    if (vs > 0) {
        return "↑";
    } else if (vs < 0) {
        return "↓";
    } else { return ""; }
}

//涨跌颜色
function udcls(vsa, vsb) {
    vsa = vsa.replace("%", "");
    if (arguments.length == 1) {
        if (vsa > 0) { return "red"; } else if (vsa < 0) { return "green"; } else { return ""; }
    } else {
        vsb = vsb.replace("%", "");
        if (vsa - vsb > 0) {
            return "red";
        } else if (vsa - vsb < 0) {
            return "green";
        } else {
            return "";
        }
    }
}

//涨跌颜色
function udc(vsa, vsb) {
    vsa = vsa.replace("%", "");
    if (vsb == "" || vsb == null || vsb == "undefined") {
        if (vsa > 0) {
            return "#f00";
        } else if (vsa < 0) {
            return "#090";
        } else {
            return "";
        }
    } else {
        vsb = vsb.replace("%", "");
        if (vsa - vsb > 0) {
            return "#f00";
        } else if (vsa - vsb < 0) {
            return "#090";
        } else {
            return "";
        }
    }
}

//涨跌颜色
function udcolor(vsa, vsb) {
    vsa = vsa.replace("%", "");
    if (vsb == "" || vsb == null || vsb == "undefined") {
        if (vsa > 0) {
            return "color:#f00";
        } else if (vsa < 0) {
            return "color:#090";
        } else {
            return "";
        }
    } else {
        vsb = vsb.replace("%", "");
        if (vsa - vsb > 0) {
            return "color:#f00";
        } else if (vsa - vsb < 0) {
            return "color:#090";
        } else {
            return "";
        }
    }
}

//涨跌平判断
function zdp(Pnum) {
    if (Pnum > 0) {
        return 1;
    } else if (Pnum < 0) {
        return -1;
    } else {
        return 0;
    }
}

//增减标记
function fvc(vs) {
    if (vs == 0 || vs == "") {
        return "";
    } else {
        if (vs > 0) {
            return "+" + vs;
        } else {
            return vs;
        }
    }
}

//数字格式化
function ForDight(Dight, How) {
    rDight = parseFloat(Dight).toFixed(How);
    if (rDight == "NaN") {
        rDight = "--";
    }
    return rDight;
}

//数字格式化
function ForWc(Di) {
    var chu = 1;
    var res = Di;
    if (Di > 0 && Di.length >= 6) {
        chu = 6;
    } if (Di < 0 && Di.length >= 7) {
        chu = 6;
    } if (chu == 6) {
        res = ForDight((Di / 10000), 2) + "万";
    }
    return res;
}

//获取市场
function getmarket(cd) {
    var j = cd.substring(0, 3);
    var i = j.substring(0, 1);
    if (i == "5" || i == "6" || i == "9") {
        return "1";
    } else {
        if (j == "009" || j == "126" || j == "110") {
            return "1";
        } else {
            return "2";
        }
    }
}

//写cookies
function WriteCookie(name, value, hours) {
    var expire = "";
    if (hours != null) {
        expire = new Date((new Date()).getTime() + hours * 3600000);
        expire = "; expires=" + expire.toGMTString() + ";path=/;domain=.eastmoney.com";
    }
    document.cookie = name + "=" + escape(value) + expire;
}
//取cookies
function GetCookie(name) {
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin == -1) {
        begin = dc.indexOf(prefix);
        if (begin != 0) {
            return null;
        };
    } else {
        begin += 2;
    }
    var end = document.cookie.indexOf(";", begin);
    if (end == -1) {
        end = dc.length;
    }
    return unescape(dc.substring(begin + prefix.length, end));
}

function Getcks(key) {
    var result = document.cookie.match(new RegExp(key + "=([^;]*)"));
    return result != null ? unescape(decodeURI(result[1])) : null;
}

//拉长缩短K线
function picklc() {
    KBd.Change('-');
}

//拉长缩短K线
function picksd() {
    KBd.Change('+');
}

var Browser = { ie: /msie/.test(window.navigator.userAgent.toLowerCase()), moz: /gecko/.test(window.navigator.userAgent.toLowerCase()), opera: /opera/.test(window.navigator.userAgent.toLowerCase()), safari: /safari/.test(window.navigator.userAgent.toLowerCase()) };
var Min = new Object(); Min.Loader = {
    load: function (sUrl, sBianMa, fCallback) {
        var _script = document.createElement('script'); _script.setAttribute('charset', sBianMa); _script.setAttribute('type', 'text/javascript'); _script.setAttribute('src', sUrl);
        document.getElementsByTagName('head')[0].appendChild(_script);
        if (Browser.ie) {
            _script.onreadystatechange = function () { if (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') { _script.parentNode.removeChild(_script); fCallback(); } };
        } else if (Browser.moz || Browser.opera) {
            _script.onload = function () { _script.parentNode.removeChild(_script); fCallback(); };
        } else { _script.parentNode.removeChild(_script); fCallback(); }
    }
};

(function () {
    function QaDefault(Code, Market, Market_10, Name, styleid, zjlCode, zdpCode) {
        //代码，市场_12，市场_10，名称，排行对应ID,资金流代码，涨跌平代码,
        _this = this; _this._Code = Code; _this._Market = Market; _this._Market_10 = Market_10; _this._Name = Name; _this.styleid = styleid; _this._zjlCode = zjlCode; _this._zdpCode = zdpCode;
        _this.$ = function (id) { return "string" == typeof id ? document.getElementById(id) : id; };
        _this.sansuoNum = 0; _this.cbian = true; _this.sansuo; _this.tempStatus = {}; _this.ColorStatus = {};
        _this.hongdise = function () {
            var span = [_this.$("price9"), _this.$("km1"), _this.$("km2")];
            for (i = 0; i < 3; i++) {
                _this.ColorStatus[i] = (_this.ColorStatus[i]) ? false : true;
                if (_this.cbian) {
                    if (!_this.ColorStatus[i]) {
                        _this.tempStatus[i] = span[i].style.color; span[i].style.color = "#000000";
                    } else {
                        span[i].style.color = _this.tempStatus[i];
                    }
                }
            }
            _this.sansuoNum++;
            if (_this.sansuoNum > 6) {
                clearInterval(_this.sansuo);
                _this.sansuoNum = 0; _this.tempStatus = {}; _this.ColorStatus = {}; _this.cbian = false;
                _this.$("price9").style.color = ""; _this.$("km1").style.color = ""; _this.$("km2").style.color = "";
            }
        };
    };
    QaDefault.prototype = {
        init: function () {
            window.quoteIsFirst = true; window.quoteRefresh = 12000;//行情
            window.zxgIsFirst = true; window.zxgRefresh = 30000; window.zxgDisNum = 6; window.favorsetInterval = 0;//自选股
            window.GetBkphNUM = 1; window.GetBkzj = 1; window.GetLzBk = 1; window.GetKX = 1; window.GetKXMaxID; window.GetRankNUM = 1; window.GetZjlxNUM = 1; window.GetZdpNUM = 1; window.GetGzqhNUM = 1;

            window.GetTimeZoneInfo = false;
            _this.bindPageEvent();
            _this.Gethis();//最近访问
            createSWF(_this._Code, _this._Market, _this._Name, 678, 276, 678, 415, "1", "0", "1");
            setTimeout(function () { _this.GetFavorList(_this._Code); }, 100);
            setTimeout(function () { loadBkph(); loadBkzj(); LoadLzbk(); LoadZyzs(); Loadgzqh(); loadRank(_this.styleid); loadKuaiXun(); GetZPDT();_this.UpZjlx();}, 200);
            setInterval(function () { LoadLzbk(); LoadZyzs(); Loadgzqh(); loadRank(_this.styleid); GetZPDT(); }, 10000);
            setInterval(function () { loadBkph(); }, 15000);

            //百度隐藏广告
            if (_this.getQueryStringByName("from") == "BaiduAladdin") {
                $("#tbggiframe").hide();
                $("#ifhqheadad").hide();

                $.ajax({
                    url: "http://emres.dfcfw.com/public/js/aldtg.js",
                    method: "GET",
                    scriptCharset: 'UTF-8',
                    dataType: "script"
                });

            } else {
                $.ajax({
                    url: "http://emres.dfcfw.com/public/js/left.js",
                    method: "GET",
                    scriptCharset: 'UTF-8',
                    dataType: "script"
                });

                $.ajax({
                    url: "http://emres.dfcfw.com/public/js/em_news_fixed_right.js",
                    method: "GET",
                    scriptCharset: 'UTF-8',
                    dataType: "script"
                });
            }
        },
        getQueryStringByName: function (name) {
            var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
            if (result == null || result.length < 1) {
                return "";
            }
            return result[1];
        },
        bindPageEvent: function () {//页面事件绑定
            _this.DisQuote();
            setInterval(function () {_this.DisQuote();}, window.quoteRefresh);
            _this.GetTimeZone();setInterval(function () { _this.GetTimeZone(); _this.UpZjlx() }, 30000);//时间戳 资金流向
            setInterval(function () {_this.UpPic(true);}, 180000);//更新R图和K线
            _this.$("RefPR").onclick = function () { prefresh(); }
            _this.$("refgbauls").onclick = function () {
                var dl = _this.$("gbauls").getElementsByTagName("dl");
                var sedl = GetRandomNum(0, dl.length - 1);
                for (var i = 0; i < dl.length; i++) {
                    if (i == sedl) {
                        if (dl[i].hasChildNodes()) {
                            var dd = dl[i].childNodes;
                            for (var j = 0; j < dd.length; j++) {
                                if (dd[j].hasChildNodes()) {
                                    var ddimg = dd[j].childNodes[0].getElementsByTagName('img')[0];
                                    if (ddimg && !ddimg.getAttribute('src'))
                                        ddimg.setAttribute('src', ddimg.getAttribute('data-value'));
                                }
                            }
                        }
                        dl[i].style.display = "";
                    } else {
                        dl[i].style.display = "none";
                    }
                }
            };
            _this.$("kx_fontsize").onclick = function () {
                if ($x("flash_box").style.display == "block") {
                    _this.$("kx_list").className = _this.$("kx_list").className == "kx_list kx_listfls" ? "kx_list kx_fz14 kx_listfls" : "kx_list kx_listfls";
                } else {
                    _this.$("kx_list").className = _this.$("kx_list").className == "kx_list" ? "kx_list kx_fz14" : "kx_list";
                }
                _this.$("kx_fontsize").innerHTML = _this.$("kx_list").className.indexOf("kx_fz14")>0 ? "小字-" : "大字+";
            };
            _this.$("kx_refresh").onclick = function () { loadKuaiXun(); };
            _this.$("kx_auto").onclick = function () {
                if(_this.$("kx_auto").className == "kx_auto kx_autoing"){
                    _this.$("kx_auto").className = "kx_auto"; clearInterval(window.kxst);
                }
                else {
                    _this.$("kx_auto").className = "kx_auto kx_autoing";  loadKuaiXun();
                }
            };
            /*_this.$('lookVote0').onclick = function () { window.open('http://vote.eastmoney.com/vote_look1.asp?action=look'); };
            _this.$('lookVote1').onclick = function () { window.open('http://vote.eastmoney.com/vote_look1.asp?action=look'); };
            _this.$('lookVote2').onclick = function () { window.open('http://vote.eastmoney.com/vote_look2.asp?action=look'); };
            _this.$("hq_cr_close").onclick = function () { _this.$("hq_cr_tips").style.display = "none"; WriteCookie("emhq_cr", "1", 12); };
            _this.$("hq_cr_b").onmouseover = function () { _this.$("hq_cr_tips").style.display = "block"; };
            _this.$("hq_cr_b").onmouseout = function () { _this.$("hq_cr_tips").style.display = "none"; };*/
            _this.$('zjlx_bars').onclick = function () {
                var s1 = _this.$("zjlx_bar").value; var s = escape(s1);
                if (s1 == "输代码、名称或拼音" || s1 == "" || isNaN(parseInt(s1)) || s1.length != 6) {alert("请输入所查询股票的代码！");return false;} else {var url = "http://data.eastmoney.com/rzrq/detail/" + s1 + ".html";window.open(url);}
            };
            var zz = new StockSuggest("zjlx_bar", {
                text: "输代码、名称或拼音",
                type: "ABSTOCK",
                autoSubmit: false,
                width: 190,
                header: ["选项", "代码", "名称", "类型"],
                body: [-1, 1, 4, -2],
                callback: function (ag) {
                    var url = "http://data.eastmoney.com/rzrq/detail/" + ag.code + ".html";
                    window.open(url);
                }
            });

        },
        Bian: function (dt) {//是否在开盘期间
            var res = false;
            var hs = dt.getHours();
            var ms = dt.getMinutes();
            if (hs >= 9 && hs <= 11) {
                res = true;
                if ((hs == 11) && ms >= 30)
                    res = false;
            }
            if (hs >= 13 && hs < 15) { res = true; }
            return res;
        },
        PanQian: function (dt) {//是否在盘前期间
            var res = false;
            var hs = dt.getHours();
            var ms = dt.getMinutes();
            if (hs == 9) { if ((ms >= 14) && ms < 29) { res = true; } }
            return res;
        },
        GetTimeZone: function () {//系统时间
            Min.Loader.load("http://quote.eastmoney.com/timezone.js?" + formatm(), 'utf-8', function () {
                var dt = new Date(window["bjTime"] * 1000);
                window.GetTimeZoneInfo = _this.Bian(dt);
                var Notify = window["Notify"];
                //_this.setPicrTab(dt);
                if (Notify != "undefined" && Notify != "" && !window.Notifyed && _this.IsNotify) {
                    if (Notify == 0) { clearInterval(window.NotifyS); } else { _this.NotifyPage(Notify * 60000); }
                }
            });
        },
        NotifyPage: function (num) {//通知页面刷新
            window.Notifyed = true; window.NotifyS = setInterval("prefresh()", num);
        },
        GetFavorList: function (thisCode) {
            var iscks = false;
            if (GetCookie("pi")) {
                var gcks = Getcks("pi");
                if (gcks.split(';').length >= 3) {
                    var name = gcks.split(';')[2];
                    if (/^[\u4E00-\u9FA5][0-9a-zA-Z]{6}$/g.test(name)) { iscks = true; }
                    else {
                        Min.Loader.load("http://mystock.eastmoney.com/mystock.aspx?f=gsaandcheck&sc=" + _this._Code + "|0" + _this._Market + "|01&c=13&var=asl&rt=" + formatm(), "utf-8", function () {
                            var allstocklist = "";
                            if (asl.result == "1") {
                                var sl = asl.data.list.split(',');
                                for (var i = 0; i < sl.length; i++) {
                                    var item = sl[i].split('|');
                                    if (i == sl.length - 1) {
                                        allstocklist += item[0];
                                    } else {
                                        allstocklist += item[0] + ",";
                                    }
                                }
                            }
                            _this.FavorPad(allstocklist, thisCode);
                        });
                    }
                }
                else {
                    iscks = true;
                }
            } else {
                iscks = true;
            }
            if (iscks) {
                var emhq_stock = GetCookie("emhq_stock");
                if (emhq_stock) {
                    _this.FavorPad(emhq_stock, thisCode);
                } else {
                    Min.Loader.load(portalurl+"/stockeast/selflist", "utf-8", function () {
                        _this.FavorPad(allstocklist, thisCode);
                    });
                    //_this.FavorPad("", thisCode);
                }
            }
        },
        FavorPad: function (initlist, thisCode) {
            var arr = new Array();
            initlist += ",000001,000002,000004,000005,000006,000008,000009,000010,000011,000012,000014,000016,000017,000018";
            var list = unique(initlist.split(","));
            for (var k = 0; k < list.length; k++) {
                if (list[k] != "") {
                    var code = list[k];
                    var market = getmarket(code);
                    if (thisCode != code) {
                        arr.push(code + "" + market);
                    }
                }
            }
            _this.FavorDis(arr);
            window.zxgIsFirst = true;
            window.favorsetInterval = setInterval(function () { _this.FavorDis(arr); }, window.zxgRefresh);
        },
        FavorDis: function (arr) {
            if (window.GetTimeZoneInfo || window.zxgIsFirst) {
                var res = arr.slice(0, window.zxgDisNum);
                Min.Loader.load("http://" + getdomain() + "/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + res + "&sty=FDCS&st=z&sr=1&p=1&ps=1000&cb=&js=var%20jszxg={list:[(x)]};&token=" + window.token + "&v=" + Math.random(), "utf-8", function () {
                    var si = 0;
                    var temzx = [];
                    if (jszxg.list != null) {
                        while (_this.$("favorlist").hasChildNodes()) {
                            _this.$("favorlist").removeChild(_this.$("favorlist").childNodes[0]);
                        }
                        for (var i = 0; i < jszxg.list.length; i++) {
                            if (si > window.zxgDisNum) { break; }
                            if (jszxg.list[i] != "" && si < window.zxgDisNum) {
                                var tem_zx = jszxg.list[i].split(','); var _marketcode = tem_zx[0] == "1" ? "sh" + tem_zx[1] : "sz" + tem_zx[1];
                                var _tr = document.createElement("tr");
                                var _tdname = document.createElement("td"); _tdname.className = "nm"; _tdname.innerHTML = "<span class=\"a\"><a href=\""+portalurl+"stockeast/detail?stockCode=" + tem_zx[1] + "\" target=\"_self\">" + tem_zx[2] + "</a></span>";
                                var _tdprice = document.createElement("td"); _tdprice.className = udcls(tem_zx[4]); _tdprice.innerHTML = tem_zx[3];
                                var _tdzdf = document.createElement("td"); _tdzdf.className = udcls(tem_zx[4]); _tdzdf.innerHTML = tem_zx[5];
                                _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                                _this.$("favorlist").appendChild(_tr);
                                si++;
                            }
                        }
                        for (var i = 0; i < window.zxgDisNum - si; i++) {
                            var _tr = document.createElement("tr");
                            var _tr = document.createElement("tr");
                            var _tdname = document.createElement("td"); _tdname.className = "nm"; _tdname.innerHTML = "<span class=\"a\">-</span>";
                            var _tdprice = document.createElement("td"); _tdprice.innerHTML = "-";
                            var _tdzdf = document.createElement("td"); _tdzdf.innerHTML = "-";
                            _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                            _this.$("favorlist").appendChild(_tr);
                        }
                    }
                });
                window.zxgIsFirst = false;
            }
        },
        Gethis: function () {
            var arg = { def: "", set: "f-0-" + _this._Code + "-" + _this._Name }; var HV = new HistoryViews("historyest", arg);
        },
        Setudclass: function (zd) {
            var hqcr = _this.$("arrowud").getAttribute("xid");
            if (zd > 0) {
                _this.$("arrowud").className = hqcr == "1" ? "cr red" : "red";
                _this.$("arrow-find").className = "xp2 up-arrow";
            } else if (zd < 0) {
                _this.$("arrowud").className = hqcr == "1" ? "cr green" : "green";
                _this.$("arrow-find").className = "xp2 down-arrow";
            } else {
                _this.$("arrowud").className = hqcr == "1" ? "cr" : "";
                _this.$("arrow-find").className = "";
            }
        },
        DisQuote: function () {
            if (window.GetTimeZoneInfo || window.quoteIsFirst) {
                var sjs = Math.random().toString().replace('.', '');
                jQuery.ajax({
                    url: "http://nuff.eastmoney.com/EM_Finance2015TradeInterface/JS.ashx?id=" + _this._Code + "" + _this._Market + "&token=" + window.token + "&cb=callback" + sjs,
                    dataType: "jsonp",
                    scriptCharset: "utf-8",
                    jsonpCallback: "callback" + sjs,
                    success: function (json) {
                        if (json.Value.length > 0) {
                            var skd = json.Value;//基本数据
                            document.title = (skd[2] + "" + skd[25] + " " + skd[27] + "(" + skd[29] + "%)");
                            var temprice = _this.$("price9").innerHTML;
                            _this.$("price9").innerHTML = skd[25]; //最新价
                            _this.$("km1").innerHTML = (skd[27] == "-" ? "" : skd[27]);//涨跌
                            _this.$("km2").innerHTML = (skd[29] == "-" ? "" : skd[29] + "%");//涨幅
                            _this.Setudclass(skd[27]); //_this.hqcr(skd[44], skd[51], skd[52]);
                            getHqStat(skd[44]);
                            _this.$("hqday").innerHTML = "（" + GetFullWeekTime(skd[49]) + "）";//时间
                            _this.$("gt1").innerHTML = skd[28]; _this.$("gt1").className = "txtl " + udcls(skd[28], skd[34]);//今开
                            _this.$("gt2").innerHTML = skd[30]; _this.$("gt2").className = "txtl " + udcls(skd[30], skd[34]);//最高
                            _this.$("gt3").innerHTML = skd[29]+"%"; _this.$("gt3").className = "txtl " + udcls(skd[29], "0");//涨幅
                            _this.$("gt4").innerHTML = (skd[37] == "-" ? "-" : skd[37] + "%");//换手
                            _this.$("gt5").innerHTML = fmtdigw(skd[31], 0,"万手");//成交量
                            _this.$("gt7").innerHTML = skd[34];//昨收
                            _this.$("gt8").innerHTML = skd[32]; _this.$("gt8").className = "txtl " + udcls(skd[32], skd[34]);//最低
                            _this.$("gt9").innerHTML = skd[27]; _this.$("gt9").className = "txtl " + udcls(skd[27]);//涨跌
                            _this.$("gt10").innerHTML = skd[50]+"%";//振幅
                            _this.$("gt11").innerHTML = skd[35]+"元";//成交额

                            _this.$("rgt1").innerHTML = skd[25]; _this.$("rgt1").className = udcls(skd[25], skd[34]);//最新
                            _this.$("rgt2").innerHTML = skd[28]; _this.$("rgt2").className = udcls(skd[28], skd[34]);//开盘
                            _this.$("rgt3").innerHTML = skd[30]; _this.$("rgt3").className = udcls(skd[30], skd[34]);//最高
                            _this.$("rgt4").innerHTML = skd[32]; _this.$("rgt4").className = udcls(skd[32], skd[34]);//最低
                            _this.$("rgt5").innerHTML =skd[29] + "%"; _this.$("rgt5").className = udcls(skd[29]);//涨幅
                            _this.$("rgt6").innerHTML = skd[27]; _this.$("rgt6").className = udcls(skd[27]);//涨跌
                            _this.$("rgt7").innerHTML = fmtdigw(skd[31],0, "万手");//总量
                            _this.$("rgt8").innerHTML = skd[35] + "元";//总额
                            _this.$("rgt9").innerHTML = skd[37] + "%";//换手
                            _this.$("rgt10").innerHTML = fmtdig(skd[45], "-");//流通市值
                            _this.$("rgt11").innerHTML = fmtdig(skd[40], "手");//外盘
                            _this.$("rgt12").innerHTML = fmtdig(skd[39], "手");//内盘
                            _this.$("rgt13").innerHTML = skd[50] + "%";//振幅
                            _this.cbian = (skd[44] == "0" && _this.$("price9").innerHTML != temprice) ? true : false;
                            _this.GetFbFj(_this._Code + "" + _this._Market, skd[34]);
                        }
                    }
                });
                _this.sansuo = setInterval(_this.hongdise, 300);
                window.quoteIsFirst = false;
            }
        },
        GetFbFj: function (cd, jsfbfjzs) {
            Min.Loader.load("http://" + getdomain() + "/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + cd + "&sty=DPTTFD&st=a&sr=1&p=1&ps=10&cb=&js=var%20jsfbfj={list:[(x)]};&token=" + window.token + "&v=" + Math.random(), "utf-8", function () {
                var sia = 0;
                if (jsfbfj.list.length > 0) {
                    if (jsfbfj.list[0] != "-") {
                        while (_this.$("fblist").hasChildNodes()) { _this.$("fblist").removeChild(_this.$("fblist").childNodes[0]); }
                        var tem = jsfbfj.list[0].split('|');
                        for (var i = tem.length - 1; i >= 0; i--) {
                            if (sia <= 11) {
                                var tem_zx = tem[i].split('~');
                                var _tr = document.createElement("tr");
                                var _tdname = document.createElement("td"); _tdname.innerHTML = tem_zx[0];
                                var _tdprice = document.createElement("td"); _tdprice.className = udcls(tem_zx[1], jsfbfjzs); _tdprice.innerHTML = tem_zx[1];
                                var _tdzdf = document.createElement("td"); _tdzdf.className = udcls(tem_zx[3]); _tdzdf.innerHTML = tem_zx[2] + "" + udt(tem_zx[3], tem_zx[2]);
                                _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                                _this.$("fblist").appendChild(_tr);
                                sia++;
                            }
                        }
                        for (var i = 0; i < 11 - sia; i++) {
                            var _tr = document.createElement("tr");
                            var _tr = document.createElement("tr");
                            var _tdname = document.createElement("td"); _tdname.className = "nm"; _tdname.innerHTML = "<span class=\"a\">-</span>";
                            var _tdprice = document.createElement("td"); _tdprice.innerHTML = "-";
                            var _tdzdf = document.createElement("td"); _tdzdf.innerHTML = "-";
                            _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                            _this.$("fblist").appendChild(_tr);
                        }
                    }
                }
            });
        },
        UpZjlx: function () {
            RefZjlx(_this._zjlCode)
        },
        UpPic: function (refk, pq) {//更新图 (refk是否需要更新K图，是否为盘前图)
            var pqtit = _this.$("tab_picr").getElementsByTagName("span");
            for (var i = 0; i < pqtit.length; i++) {
                pqtit[i].className = "";
            }
            if (pq) {
                pqtit[0].className = "cur";
                _this.$("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "r") + "&rt=" + formatm();
            } else {
                pqtit[1].className = "cur";
                _this.$("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "r") + "&rt=" + formatm();
            }
            if (refk) {
                if (_this.Lstng == "0") {
                    _this.$("pick").src = "http://hqres.eastmoney.com/EMQuote_Lib/img/picknotfund.gif?1";
                } else {
                    _this.$("pick").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "KXL") + "&rt=" + formatm();
                }
            }
        },
        hqcr: function (hq_cr_type, _hq_cr_time, hq_cr_cnt) {
            var hq_cr_time = _hq_cr_time.length > 5 ? _hq_cr_time.substring(16, 11) : "-";
            if (hq_cr_type == "8" || hq_cr_type == "9" || hq_cr_type == "10" || hq_cr_type == "11") {
                _this.$("hq_cr").style.display = "block";
                //_this.$("hq_cr_time").innerHTML = hq_cr_time == "-" ? "暂停交易" : "暂停交易至" + hq_cr_time;
                if (hq_cr_type == "8" || hq_cr_type == "10") { _this.$("hq_cr_time").innerHTML = "暂停交易15分钟"; } else { _this.$("hq_cr_time").innerHTML = "暂停交易至15:00"; }
                _this.$("arrowud").setAttribute("xid", "1"); _this.$("arrowud").className = _this.$("arrowud").className.indexOf("cr") == -1 ? "cr "+_this.$("arrowud").className : _this.$("arrowud").className;
                switch (hq_cr_type) {
                    case "8": _this.$("hq_cr_type").innerHTML = "5%熔断"; _this.$("hq_cr_type").className = "hq_cr_a bgr"; break;
                    case "9": _this.$("hq_cr_type").innerHTML = "7%熔断"; _this.$("hq_cr_type").className = "hq_cr_a bgr"; break;
                    case "10": _this.$("hq_cr_type").innerHTML = "-5%熔断"; _this.$("hq_cr_type").className = "hq_cr_a bgg"; break;
                    case "11": _this.$("hq_cr_type").innerHTML = "-7%熔断"; _this.$("hq_cr_type").className = "hq_cr_a bgg"; break;
                }
                var _rdContent = "";
                switch (hq_cr_cnt.toLowerCase()) {
                    case "aa1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "aa2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "aa3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，于9：30起暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "aa4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，于9：30起暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ab1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，11:30前未完成的15分钟指数熔断，延续至13:00后继续进行，直至届满。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "ab2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，11:30前未完成的15分钟指数熔断，延续至13:00后继续进行，直至届满。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "ab3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ab4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ac1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ac2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ac3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ac4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ad1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "ad2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "ad3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ad4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，暂停交易至15：00。熔断期间仅接受撤销申报，不接受其他申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ba1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "ba2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "ba3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，于9：30起暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ba4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，于9：30起暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bb1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，11:30前未完成的15分钟指数熔断，延续至13:00后继续进行，直至届满。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "bb2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，11:30前未完成的15分钟指数熔断，延续至13:00后继续进行，直至届满。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "bb3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，于9：30起暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bb4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，于9：30起暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bc1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，暂停交易至14：57。熔断期间您可以继续申报，也可以撤销申报。熔断结束后进入3分钟的尾盘集合竞价。";
                        break;
                    case "bc2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，暂停交易至14：57。熔断期间您可以继续申报，也可以撤销申报。熔断结束后进入3分钟的尾盘集合竞价。";
                        break;
                    case "bc3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bc4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bd1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bd2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bd3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "bd4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "be1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "be2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "be3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "be4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，暂停交易至15：00。熔断期间您可以继续申报，也可以撤销申报。当日不再进行集合竞价撮合成交。";
                        break;
                    case "ca1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据中金所规定，相关合约于9：30起暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间，期间接受指令申报和撤销。";
                        break;
                    case "ca2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据中金所规定，相关合约于9：30起暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间，期间接受指令申报和撤销。";
                        break;
                    case "ca3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据中金所规定，相关合约暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "ca4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据中金所规定，相关合约暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "cb1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据中金所规定，相关合约暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间（熔断发生在11:15-11:18之间，下午开盘直接进入3分钟集合竞价），期间接受指令申报和撤销。";
                        break;
                    case "cb2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据中金所规定，相关合约暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间（熔断发生在11:15-11:18之间，下午开盘直接进入3分钟集合竞价），期间接受指令申报和撤销。";
                        break;
                    case "cb3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据中金所规定，相关合约暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "cb4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据中金所规定，相关合约暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "cc1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据中金所规定相关合约收市前15分钟内触发5%阈值，暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "cc2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据中金所规定相关合约收市前15分钟内触发5%阈值，暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "cc3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据中金所规定，相关合约暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "cc4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据中金所规定，相关合约暂停交易至当日收市。熔断期间暂停交易，不接受指令申报和撤销。";
                        break;
                    case "taa1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "taa2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "taa3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "taa4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tab1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "tab2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "tab3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tab4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tac1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tac2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tac3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tac4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据上交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tba1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "tba2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，于9：30起暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "tba3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tba4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tbb1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "tbb2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，暂停交易15分钟。熔断期间您可以继续申报，也可以撤销申报。熔断结束后，您可以继续正常交易。";
                        break;
                    case "tbb3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tbb4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tbc1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tbc2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tbc3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tbc4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据深交所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tca1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据中金所规定，相关合约于9：30起暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间，期间接受指令申报和撤销。";
                        break;
                    case "tca2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据中金所规定，相关合约于9：30起暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间，期间接受指令申报和撤销。";
                        break;
                    case "tca3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间不接受指令申报和撤销；熔断结束后，您可正常交易。";
                        break;
                    case "tca4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间不接受指令申报和撤销；熔断结束后，您可正常交易。";
                        break;
                    case "tcb1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据中金所规定，相关合约暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间，期间接受指令申报和撤销。";
                        break;
                    case "tcb2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据中金所规定，相关合约暂停交易12分钟。熔断期间暂停交易，不接受指令申报和撤销。熔断结束后进入3分钟集合竞价指令申报时间，期间接受指令申报和撤销。";
                        break;
                    case "tcb3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间不接受指令申报和撤销；熔断结束后，您可正常交易。";
                        break;
                    case "tcb4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间不接受指令申报和撤销；熔断结束后，您可正常交易。";
                        break;
                    case "tcc1":
                        _rdContent = "沪深300指数上涨触及5%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tcc2":
                        _rdContent = "沪深300指数下跌触及5%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tcc3":
                        _rdContent = "沪深300指数上涨触及7%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                    case "tcc4":
                        _rdContent = "沪深300指数下跌触及7%熔断阈值，根据中金所规定，交易日为股指期货合约交割日的，当日指数熔断时间跨越11:30的，于当日13:00起恢复交易；当日13:00至15:00期间，不实施指数熔断；熔断期间您可以继续申报，也可以撤销申报；熔断结束后，您可正常交易。";
                        break;
                }
                var cookies_cr = GetCookie("emhq_cr");
                _this.$("hq_cr_cnt").innerHTML = _rdContent;
                if (_rdContent == "") { _this.$("hq_cr_tips").style.display = "none"; _this.$("hq_cr_b").style.display = "none"; }
                if ((cookies_cr == "" || cookies_cr == null) && _rdContent != "") { _this.$("hq_cr_tips").style.display = "block"; _this.$("hq_cr_b").style.display = "block"; }
            } else {
                _this.$("hq_cr").style.display = "none"; _this.$("hq_cr_type").className = "hq_cr_a"; _this.$("arrowud").setAttribute("xid", "0"); _this.$("arrowud").className = _this.$("arrowud").className.replace("cr", "");
            }
        }
    };
    window.QaDefault = QaDefault;
})();

function loadBkph() {
    if (window.GetTimeZoneInfo == true || window.GetBkphNUM == 1) {
        //Mini.Loader.load("http://quote.eastmoney.com/hq2data/bk/data/bkquery.js", 'gb2312', function () {
        //    var T_datas_Z = BKCache.Trade[0], T_datas_D = BKCache.Trade[1], N_datas_Z = BKCache.Notion[0], N_datas_D = BKCache.Notion[1];
        //    var html_hyz = [], html_hyd = [], html_gnz = [], html_gnd = [];
        //    for (var i = 0; i < T_datas_Z.length && i < 5; i++) {
        //        var dataRow = String(T_datas_Z[i]).split(",");
        //        html_hyz.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + dataRow[9] + '_0_2" target="_blank">' + dataRow[0] + '</a></td><td style="' + udcolor(dataRow[1]) + '">' + dataRow[1] + '</td><td><a href="http://quote.eastmoney.com/' + dataRow[6] + '.html" target="_blank">' + dataRow[7] + '</td></tr>');
        //    }
        //    for (var i = 0; i < T_datas_D.length && i < 5; i++) {
        //        var dataRow = String(T_datas_D[i]).split(",");
        //        html_hyd.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + dataRow[9] + '_0_2?sortRule=1" target="_blank">' + dataRow[0] + '</a></td><td style="' + udcolor(dataRow[1]) + '">' + dataRow[1] + '</td><td><a href="http://quote.eastmoney.com/' + dataRow[6] + '.html" target="_blank">' + dataRow[7] + '</td></tr>');
        //    }
        //    for (var i = 0; i < N_datas_Z.length && i < 5; i++) {
        //        var dataRow = String(N_datas_Z[i]).split(",");
        //        html_gnz.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28003' + dataRow[9] + '_0_2" target="_blank">' + dataRow[0] + '</a></td><td style="' + udcolor(dataRow[1]) + '">' + dataRow[1] + '</td><td><a href="http://quote.eastmoney.com/' + dataRow[6] + '.html" target="_blank">' + dataRow[7] + '</td></tr>');
        //    }
        //    for (var i = 0; i < N_datas_D.length && i < 5; i++) {
        //        var dataRow = String(N_datas_D[i]).split(",");
        //        html_gnd.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28003' + dataRow[9] + '_0_2?sortRule=1" target="_blank">' + dataRow[0] + '</a></td><td style="' + udcolor(dataRow[1]) + '">' + dataRow[1] + '</td><td><a href="http://quote.eastmoney.com/' + dataRow[6] + '.html" target="_blank">' + dataRow[7] + '</td></tr>');
        //    }
        //    removeAllChild("cnt_hyzfb_list"); $("#cnt_hyzfb_list").append(html_hyz.join(''));
        //    removeAllChild("cnt_hydfb_list"); $("#cnt_hydfb_list").append(html_hyd.join(''));
        //    removeAllChild("cnt_gnzfb_list"); $("#cnt_gnzfb_list").append(html_gnz.join(''));
        //    removeAllChild("cnt_gndfb_list"); $("#cnt_gndfb_list").append(html_gnd.join(''));

        //    window.GetBkphNUM = 0;
        //});

        //板块行业涨幅
        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKHY&sty=FPGBKI&st=(ChangePercent)&sr=-1&p=1&ps=5&cb=&js=var%20hyz=[(x)]%20&token=de1161e2380d231908d46298ae339369", 'gb2312', function () {
            var hyzhtml = [];
            for (var i = 0; i < hyz.length; i++) {
                var item = hyz[i].split(",");
                var marketcode = item[8] == "1" ? "sh" + item[7] : "sz" + item[7];
                var bkcode = item[1].replace("BK0", "");
                hyzhtml.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[3]) + '">' + item[3] + '</td><td><a href="http://quote.eastmoney.com/' + marketcode + '.html" target="_blank">' + item[9] + '</td></tr>');
            }
            removeAllChild("cnt_hyzfb_list");
            $("#cnt_hyzfb_list").append(hyzhtml.join(''));

        });

        //板块行业跌幅
        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKHY&sty=FPGBKI&st=(ChangePercent)&sr=1&p=1&ps=5&cb=&js=var%20hyz=[(x)]%20&token=de1161e2380d231908d46298ae339369", 'gb2312', function () {
            var hyzhtml = [];
            for (var i = 0; i < hyz.length; i++) {
                var item = hyz[i].split(",");
                var marketcode = item[13] == "1" ? "sh" + item[12] : "sz" + item[12];
                var bkcode = item[1].replace("BK0", "");
                hyzhtml.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[3]) + '">' + item[3] + '</td><td><a href="http://quote.eastmoney.com/' + marketcode + '.html" target="_blank">' + item[14] + '</td></tr>');
            }
            removeAllChild("cnt_hydfb_list");
            $("#cnt_hydfb_list").append(hyzhtml.join(''));

        });

        //板块概念涨幅
        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKGN&sty=FPGBKI&st=(ChangePercent)&sr=-1&p=1&ps=5&cb=&js=var%20gnz=[(x)]%20&token=de1161e2380d231908d46298ae339369", 'gb2312', function () {
            var hyzhtml = [];
            for (var i = 0; i < gnz.length; i++) {
                var item = gnz[i].split(",");
                var marketcode = item[8] == "1" ? "sh" + item[7] : "sz" + item[7];
                var bkcode = item[1].replace("BK0", "");
                hyzhtml.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28003' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[3]) + '">' + item[3] + '</td><td><a href="http://quote.eastmoney.com/' + marketcode + '.html" target="_blank">' + item[9] + '</td></tr>');
            }
            removeAllChild("cnt_gnzfb_list");
            $("#cnt_gnzfb_list").append(hyzhtml.join(''));

        });

        //板块概念跌幅
        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKGN&sty=FPGBKI&st=(ChangePercent)&sr=1&p=1&ps=5&cb=&js=var%20gnd=[(x)]%20&token=de1161e2380d231908d46298ae339369", 'gb2312', function () {
            var hyzhtml = [];
            for (var i = 0; i < gnd.length; i++) {
                var item = gnd[i].split(",");
                var marketcode = item[13] == "1" ? "sh" + item[12] : "sz" + item[12];
                var bkcode = item[1].replace("BK0", "");
                hyzhtml.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28003' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[3]) + '">' + item[3] + '</td><td><a href="http://quote.eastmoney.com/' + marketcode + '.html" target="_blank">' + item[14] + '</td></tr>');
            }
            removeAllChild("cnt_gndfb_list");
            $("#cnt_gndfb_list").append(hyzhtml.join(''));

        });
        window.GetBkphNUM = 0;
    }
}

function loadBkzj() {
    if (window.GetTimeZoneInfo == true || window.GetBkzj == 1) {
        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKHY&sty=ICFFS&st=(BalFlowMain)&sr=-1&p=1&ps=5&cb=&js=var%20hyzj=[(x)]%20&token=de1161e2380d231908d46298ae339369", 'gb2312', function () {
            var htmlhy = [];
            for (var i = 0; i < hyzj.length; i++) {
                var item = hyzj[i].split(",");
                var marketcode = item[8].substring(6) == "1" ? "sh" + item[8].substring(0, 6) : "sz" + item[8].substring(0, 6);
                var link = "<a href=\"http://quote.eastmoney.com/" + marketcode + ".html\" target=\"_blank\">" + item[9] + "</a>";
                var bkcode = item[1].replace("BK0", "");
                htmlhy.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[4]) + '">' + fmtdig                  (item[4] * 10000, "-") + '</td><td>' + link + '</td></tr>');
            }
            removeAllChild("hyzjlpy");
            $("#hyzjlpy").append(htmlhy.join(''));
        });

        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKGN&sty=ICFFS&st=(BalFlowMain)&sr=-1&p=1&ps=5&cb=&js=var%20bkzj=[(x)]%20&token=de1161e2380d231908d46298ae339369", 'gb2312', function () {
            var htmlgn = [];
            for (var i = 0; i < bkzj.length; i++) {
                var item = bkzj[i].split(",");
                var marketcode = item[8].substring(6) == "1" ? "sh" + item[8].substring(0, 6) : "sz" + item[8].substring(0, 6);
                var link = "<a href=\"http://quote.eastmoney.com/" + marketcode + ".html\" target=\"_blank\">" + item[9] + "</a>";
                var bkcode = item[1].replace("BK0", "");
                htmlgn.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[4]) + '">' + fmtdig(item[4] * 10000, "-") + '</td><td>' + link + '</td></tr>');
            }
            removeAllChild("gnzjlpy");
            $("#gnzjlpy").append(htmlgn.join(''));
        });

        window.GetBkzj = 0;
    }
}

function _loadBkzj() {
    if (window.GetTimeZoneInfo == true || window.GetBkzj == 1) {
        jQuery.ajax({
            url: "http://quote.eastmoney.com/hq2data/bk/data/indexbkzj.js",
            dataType: "jsonp",
            scriptCharset: "gb2312",
            contentType: 'application/x-javascript',
            jsonpCallback: "callbackbkzj",
            success: function (json) {
                var htmlhy = [];
                for (var i = 0; i < json.hy.length; i++) {
                    var item = json.hy[i].split(",");
                    var marketcode = item[4].substring(6) == "1" ? "sh" + item[4].substring(0, 6) : "sz" + item[4].substring(0, 6);
                    var link = "<a href=\"http://quote.eastmoney.com/" + marketcode + ".html\" target=\"_blank\">" + item[5] + "</a>";
                    var bkcode = item[1].replace("BK0", "");
                    htmlhy.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28002' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[3]) + '">' + fmtdig(item[3] * 10000, "-") + '</td><td>' + link + '</td></tr>');
                }
                removeAllChild("hyzjlpy"); $("#hyzjlpy").append(htmlhy.join(''));

                var htmlgn = [];
                for (var i = 0; i < json.gn.length; i++) {
                    var item = json.gn[i].split(",");
                    var marketcode = item[4].substring(6) == "1" ? "sh" + item[4].substring(0, 6) : "sz" + item[4].substring(0, 6);
                    var link = "<a href=\"http://quote.eastmoney.com/" + marketcode + ".html\" target=\"_blank\">" + item[5] + "</a>";
                    var bkcode = item[1].replace("BK0", "");
                    htmlgn.push('<tr><td><a href="http://quote.eastmoney.com/center/list.html#28003' + bkcode + '_0_2" target="_blank">' + item[2] + '</a></td><td style="' + udcolor(item[3]) + '">' + fmtdig(item[3] * 10000, "-") + '</td><td>' + link + '</td></tr>');
                }
                removeAllChild("gnzjlpy"); $("#gnzjlpy").append(htmlgn.join(''));
            }
        });
        window.GetBkzj = 0;
    }
}

function LoadLzbk() {
    if (window.GetTimeZoneInfo == true || window.GetLzBk == 1) {
        Mini.Loader.load(
            "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKHY&sty=ICCS&st=c&sr=-1&p=1&ps=1&cb=&js=var%20bkhylz%20=[(x)]&token=de1161e2380d231908d46298ae339369", 'gb2312', function() {
                var dataRow = String(bkhylz[0]).split(",");
                var marketcode = (dataRow[7] == "1" ? "sh" + dataRow[6] : "sz" + dataRow[6]);
                var zdp = dataRow[5].split('|');
                $x("lzbkinfo").innerHTML = "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td width=\"248\" align=\"center\"><a href=\"http://quote.eastmoney.com/center/list.html#28002" + dataRow[1].replace("BK0", "") + "_0_2\" target=\"_blank\"><img src=\"http://cmsjs.eastmoney.com/data/images/" + dataRow[1].replace("BK0", "") + ".png?" + Math.random() + "\" border=\"0\" /></a></td><td><br><p>上涨家数:<span style=\"color:#f00\">" + zdp[0] + "</span></p><p>下跌家数:<span style=\"color:#090\">" + zdp[2] + "</span></p><p><b>领涨股票：</b></p><p><a href=\"http://quote.eastmoney.com/" + marketcode + ".html\" target=\"_blank\">" + dataRow[8] + "</a></p><p>涨幅:<span style=\"color:" + udc(dataRow[3]) + "\">" + dataRow[3] + "</span></p></td></tr></table>";
            });
        window.GetLzBk = 0;
    }
}

function LoadZyzs() {
    var sjs = Math.random().toString().replace('.', '');
    jQuery.ajax({
        url: "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012,3990052,3990062,hsi5,djia7&sty=MPNSBAS&st=&sr=1&p=1&ps=1000&token=" + token + "&cb=callback" + sjs,
        dataType: "jsonp",
        scriptCharset: "utf-8",
        jsonpCallback: "callback" + sjs,
        success: function (json) {
            var html = [];
            for (var i = 0; i < json.length; i++) {
                var item = json[i].split(",");
                var link = 'http://quote.eastmoney.com/zs' + item[1] + '.html';
                if (item[0] == "5") { link = "http://quote.eastmoney.com/hk/zs110000.html"; }
                if (item[0] == "7") { link = "http://quote.eastmoney.com/gb/zsINDU.html"; }
                html.push('<tr><td class="nm"><a href="' + link + '" target="_blank">' + item[2].replace("道琼斯工业平均", "道琼斯指数") + '</a></td><td style="' + udcolor(item[4]) + '">' + item[3] + '</td><td style="' + udcolor(item[4]) + '">' + item[4] + '</td></tr>');
            }
            removeAllChild("zyzslist"); $("#zyzslist").append(html.join(''));
            //$x("zyzslist").innerHTML = html.toString().replace(/,/g, "");
        }
    });
}

function Loadgzqh() {
    if (window.GetTimeZoneInfo == true || window.GetGzqhNUM == 1) {
        var sjs = Math.random().toString().replace('.', '');
        jQuery.ajax({
            url: "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=040120_ZJ,040121_ZJ,060120_ZJ,060121_ZJ,070120_ZJ,070121_ZJ&sty=MPNSBAS&st=a&sr=1&p=1&ps=1000&token=" + token + "&cb=callback" + sjs,
            dataType: "jsonp",
            scriptCharset: "utf-8",
            jsonpCallback: "callback" + sjs,
            success: function (json) {
                var html = [];
                for (var i = 0; i < json.length; i++) {
                    var item = json[i].split(",");
                    var gzname = item[2].replace("连续", "");
                    var gzcode = gzname.replace("当月", "DYLX").replace("下月", "XYLX");
                    if (gzname.indexOf('IF') < 0) {  gzcode = gzcode.replace("LX", ""); }
                    html.push('<tr><td class="nm"><a href="http://quote.eastmoney.com/gzqh/' + gzcode + '.html" target="_blank">' + gzname + '</a></td><td style="' + udcolor(item[4]) + '">' + item[3] + '</td><td style="' + udcolor(item[4]) + '">' + item[4] + '</td></tr>');
                }
                removeAllChild("gzqhlist"); $("#gzqhlist").append(html.join(''));
                //$x("gzqhlist").innerHTML = html.toString().replace(/,/g, "");
            }
        });
        window.GetGzqhNUM = 0;
    }
}

function loadRank(styid) {
    if (window.GetTimeZoneInfo == true || window.GetRankNUM == 1) {
        Mini.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C."+styid+"&sty=E1II&st=c&sr=-1&p=1&ps=25&cb=&js=var%20quote_rank=[(x)]&token=de1161e2380d231908d46298ae339369", 'utf-8', function () {
            var info = quote_rank;
            var html = [];
            for (var i = 0; i < info.length; i++) {
                var item = info[i].split(",");
                var marketcode = (item[1] == "1" ? "sh" : "sz") + "" + item[0];
                html.push('<tr><td class="nm"><a href="'+portalurl+'stockeast/detail?stockCode=' + item[0] + '" target="_self">' + item[2] + '</a></td><td style="' + udcolor(item[4]) + '">' + item[3] + '</td><td style="' + udcolor(item[4]) + '">' + item[4] + '</td></tr>');
            }
            removeAllChild("pylist"); $("#pylist").append(html.join(''));
            //$x("pylist").innerHTML = html.toString().replace(/,/g, "");
        });
        window.GetRankNUM = 0;
    }
}

function loadKuaiXun() {
    clearInterval(window.kxst); countRDown(60);
    var sjs = Math.random().toString().replace('.', '');
    var padRight = window.GetKX == 1 ? "" : "&id="+window.GetKXMaxID;
    jQuery.ajax({
        url: "http://newsapi.eastmoney.com/kuaixun/v2/api/list?column=zhiboall&limit=50" + padRight,
        dataType: "jsonp",
        scriptCharset: "utf-8",
        jsonpCallback: "callback" + sjs,
        success: function (json) {
            if (json.rc = 1) {
                var info = json.news;
                var html = "";
                for (var i = 0; i < info.length; i++) {
                    var TopZbStr = "";
                    if (i == 0) { TopZbStr = '<span class="dt">' + gethhmm(info[i].showtime) + '</span><span class="t">' + info[i].title + '</span>'; }
                    if (info[i].newstype == 1) {//news.
                        html += '<li><span class="kx_itime">' + gethhmm(info[i].showtime) + '</span><span class="kx_itime_end">|</span><span class="bd_i_txt' + kxgetstyle(info[i].titlestyle) + '"><a href="' + info[i].url_w + '" target="_blank">';
                        if (info[i].digest != "") {html += info[i].digest.replace(/\n+/g, "<br />");} else {html += info[i].title;}
                        html += '&nbsp;[点击查看全文]</a></span></li>';
                        if (i == 0) { TopZbStr = '<span class="dt">' + gethhmm(info[i].showtime) + '</span><span class="t"><a href="' + info[i].url_w + '" target="_blank">' + info[i].title + '</a></span>'; }
                    }
                    else if (info[i].newstype == 2) {//zhaiyao.
                        html += '<li><span class="kx_itime">' + gethhmm(info[i].showtime) + '</span><span class="kx_itime_end">|</span><span class="bd_i_txt' + kxgetstyle(info[i].titlestyle) + '">' + info[i].digest + '</span></li>';
                    }
                    else{
                        html += '<li><span class="kx_itime">' + gethhmm(info[i].showtime) + '</span><span class="kx_itime_end">|</span><span class="bd_i_txt' + kxgetstyle(info[i].titlestyle) + '"><a href="' + info[i].url_w + '" target="_blank">' + info[i].title + '&nbsp;>[点击查看全文]</a></span></li>';
                        TopZbStr = '<span class="dt">' + gethhmm(info[i].showtime) + '</span><span class="t"><a href="' + info[i].url_w + '" target="_blank">' + info[i].title + '</a></span>';
                    }
                    if (i == 0) { $x("ScrollMIIRBox").innerHTML = TopZbStr; window.GetKXMaxID = json.MaxID; }
                }
                var tipstr = info.length == 0 ? "暂无新闻更新" : "刚刚为您更新了" + info.length + "条新闻";
                if (html != "") {
                    if (window.GetKX == 1) { $x("kx_lists").innerHTML = html; } else { $("#kx_lists").prepend(html); $x("tipstr").innerHTML = tipstr; setTimeout(function () { $x("tipstr").innerHTML = "直播中..."; }, 3000) }
                } else {
                    $x("tipstr").innerHTML = "暂无新闻更新";setTimeout(function () {$x("tipstr").innerHTML = "直播中...";}, 3000)
                }
                window.GetKX = 0;
            }
        }
    });
}

function kxgetstyle(titlestyle) {
    //0-默认 1-加粗 2-跳红 3-跳红加粗.
    var res="";
    switch (titlestyle) {
        case "1": res = " bold"; break;
        case "2": res = " red"; break;
        case "3": res = " redbold"; break;
    }
    return res;
}

function countRDown(secs){
    $x("kx_auto_sec").innerHTML=secs;
    if (--secs >= 0) {window.kxst=setTimeout("countRDown(" + secs + ")", 1000);}
    else {loadKuaiXun();}
}

//更新资金流数据
function RefZjlx(zjlcode) {
    if (window.GetTimeZoneInfo == true || window.GetZjlxNUM == 1) {
        var rcode = _this._Market == "1" ? "3990012" : "0000011";
        var sjs = Math.random().toString().replace('.', '');
        jQuery.ajax({
            url: "http://nufm3.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=P.(x),(x)|" + zjlcode + "," + _this._Code + _this._Market + "|" + rcode + "&sty=IFDPFI|IFDPFITA&token=" + token + "&cb=callback" + sjs,
            dataType: "jsonp",
            scriptCharset: "utf-8",
            jsonpCallback: "callback" + sjs,
            success: function (json) {
                if (json.length == 3) {
                    var item = json[0].split(',');
                    var ritem = json[2].split(',');
                    var jitem = json[1].split(',');
                    $x("hz_a").innerHTML = fmtdigm(item[3] * 10000, 0, "亿元");
                    $x("hz_b").innerHTML = fmtdigm(item[4].replace('-', '') * 10000, 0, "亿元");
                    $x("hz_c").innerHTML = fmtdigm(ForDight(parseFloat(item[3]) + parseFloat(item[4]), 2) * 10000, 0, "亿元").replace('-', ''); $x("hz_c").className = udcls(ForDight(parseFloat(item[3]) + parseFloat(item[4]), 2));
                    $x("hz_c_t").innerHTML = $x("hz_c").className == "green" ? "主力净流出" : "主力净流入";
                    $x("hz_d").innerHTML = fmtdigm(Math.abs(item[6]), 0, "-"); $x("hz_e").innerHTML = fmtdigm(Math.abs(item[7]), 0, "-");
                    $x("hz_f").innerHTML = fmtdigm(Math.abs(item[8]), 0, "-"); $x("hz_g").innerHTML = fmtdigm(Math.abs(item[9]), 0, "-");
                    $x("hz_h").innerHTML = fmtdigm(Math.abs(item[10]), 0, "-"); $x("hz_i").innerHTML = fmtdigm(Math.abs(item[11]), 0, "-");
                    $x("hz_j").innerHTML = fmtdigm(Math.abs(item[12]), 0, "-"); $x("hz_k").innerHTML = fmtdigm(Math.abs(item[13]), 0, "-");
                    var _l = [[item[6], item[7]], [item[8], item[9]], [item[10], item[11]], [item[12], item[13]]]; ZjlxCek(_l);
                    //
                    $x("rgt17").innerHTML = jitem[22] == "-" ? "-" : jitem[22] + "%"; $x("rgt17").style.color = udc(jitem[22], "0");
                    $x("rgt18").innerHTML = jitem[23] == "-" ? "-" : jitem[23] + "%"; $x("rgt18").style.color = udc(jitem[23], "0");
                    $x("rgt19").innerHTML = jitem[24] == "-" ? "-" : jitem[24] + "%"; $x("rgt19").style.color = udc(jitem[24], "0");
                    $x("rgt20").innerHTML = jitem[25] == "-" ? "-" : jitem[25] + "%"; $x("rgt20").style.color = udc(jitem[25], "0");
                    $x("rstocka").innerHTML = "<a href=\""+portalurl+"stockeast/zsdetail?code=" + ritem[1] + "\" target=\"_self\">" + ritem[2] + "A股行情</a>";
                    $x("rstockb").innerHTML = ritem[3] + "&nbsp;&nbsp;" + ritem[4] + "&nbsp;&nbsp;" + ritem[5]; $x("rstockb").className = udcls(ritem[4]);
                    //
                }
            }
        });
        window.GetZjlxNUM = 0;
    }
}

function GetZPDT() {
    if (window.GetTimeZoneInfo == true || window.GetZdpNUM == 1) {
        var sjs = Math.random().toString().replace('.', '');
        jQuery.ajax({
            url: "http://nufm3.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + _this._zdpCode + "&sty=UDFN&st=z&sr=&p=&ps=&token=" + token + "&cb=callback" + sjs,
            dataType: "jsonp",
            scriptCharset: "utf-8",
            jsonpCallback: "callback" + sjs,
            success: function (json) {
                var info = json[0].split(',');
                if (info[2] != "-") {
                    var item = info[2].split('|');
                    $x("rgt14").innerHTML = item[0] == "-" ? "-" : item[0] + "家"; $x("rgt14").className = "red";
                    $x("rgt15").innerHTML = item[2] == "-" ? "-" : item[2] + "家"; $x("rgt15").className = "green";
                    $x("rgt16").innerHTML = item[1] == "-" ? "-" : item[1] + "家";
                } else {
                    $x("rgt14").innerHTML = "-";
                    $x("rgt15").innerHTML = "-";
                    $x("rgt16").innerHTML = "-";
                }
            }
        });
        window.GetZdpNUM = 0;
    }
}

function getHqStat(stat) {
    var str = "";
    switch (stat) {
        case "-2": str = '<b class="off" title="' + stat + '"></b>已收盘'; break;
        case "-1": str = '<b class="red on" title="' + stat + '"></b>交易中'; $x("hqstat").className = "hqstat red"; break;
        case "0": str = '<b class="red on" title="' + stat + '"></b>交易中'; $x("hqstat").className = "hqstat red"; break;
        case "1": str = '<b class="off" title="' + stat + '"></b>已收盘'; break;
        case "2": str = '<b class="off" title="' + stat + '"></b>午间休市'; break;
        case "3": str = '<b class="off" title="' + stat + '"></b>已休市'; break;
        case "4": str = '<b class="off" title="' + stat + '"></b>未开盘'; break;
        case "5": str = '<b class="off" title="' + stat + '"></b>已收盘'; break;
        case "6": str = '<b class="off" title="' + stat + '"></b>已收盘'; break;
        case "7": str = '<b class="off" title="' + stat + '"></b>已收盘'; break;
        case "8": str = '<b class="off" title="' + stat + '"></b>暂停交易'; break;
        case "9": str = '<b class="off" title="' + stat + '"></b>暂停交易'; break;
        case "10": str = '<b class="off" title="' + stat + '"></b>暂停交易'; break;
        case "11": str = '<b class="off" title="' + stat + '"></b>暂停交易'; break;
    }
    $x("hqstat").innerHTML = str;
}

//资金流向数据更新
function ZjlxCek(ls) {
    var _in = 0; var _out = 0; var items = [];
    for (var i = 0; i <= 3; i++) {
        var _l = parseFloat(ls[i][0]) + parseFloat(ls[i][1]);
        $x("hz_" + i + "l").innerHTML = ""; $x("hz_" + i + "r").innerHTML = "";
        if (_l > 0) {
            _in += Math.abs(parseFloat(_l));
            $x("hz_" + i + "l").innerHTML = "<div class=\"box\" id=\"hz_" + i + "h\"></div><span class=\"red\">" + fmtdigm(_l, 1,"-") + "</span>";
            $x("hz_" + i + "l").style.borderBottom = "1px solid #ccc";
        }
        if (_l < 0) {
            _out += Math.abs(parseFloat(_l));
            $x("hz_" + i + "r").innerHTML = "<div class=\"box\" id=\"hz_" + i + "h\"></div><span class=\"green\">" + fmtdigm(_l, 1, "-") + "</span>";
            $x("hz_" + i + "r").style.borderTop = "1px solid #ccc";
        }
        items[i] = Math.abs(_l);
    }
    var total = _out + _in;
    for (var i = 0; i <= 3; i++) {
        if (items[i] != 0) {
            $x("hz_" + i + "h").style.height = items[i] / total * 36 + "px";
        }
    }
}

function showMore(tp, obj) {var over = $x("xh" + tp + obj).style.display = "block";}
function hideall(tp, obj) {var over = $x("xh" + tp + obj).style.display = "none";}

function fmtdig(Data, _Unit) {//数值,阀值，小数位，单位后尾随字符(为-表示不需要)
    var isfs = false;
    if (parseFloat(Data) < 0) { isfs = true; }
    var res = Data; var Unit = "万"; var Fix = 0;var Mat = 10000;
    if (Data != "" && Data != "--" && Data != "-") {
        var _temp = parseFloat(Math.abs(Data));
        if (_temp > 100000000000)//大于千亿
        {
            Mat = 1000000000000; Unit = "万亿"; Fix = 2;
        }
        else if (_temp > 100000000000)//等于千亿
        {
            Mat = 100000000; Unit = "亿"; Fix = 0;
        }
        else if (_temp > 100000000)//大于等于亿
        {
            Mat = 100000000; Unit = "亿"; Fix = 2;
        }
        else if (_temp ==0)//等于0
        {
            Mat = 1; Unit = ""; Fix = 0; _Unit = "-"
        }
        res = ForDight((_temp / Mat), Fix);
    }
    return (isfs ? "-" : "") + res + Unit + (_Unit == "-" ? "" : _Unit);
}

function fmtdigw(Data, Fix, padRight) {//数值 单位固定亿
    var isfs = false;
    if (parseFloat(Data) < 0) { isfs = true; }
    var res = Data; var Mat = 10000;
    if (Data != "" && Data != "--" && Data != "-") {
        var _temp = parseFloat(Math.abs(Data));
        res = ForDight((_temp / Mat), Fix);
    }
    return (isfs ? "-" : "") + res + (padRight == "-" ? "" : padRight);
}

function fmtdigm(Data, Fix, padRight) {//数值 单位固定亿
    var isfs = false;
    if (parseFloat(Data) < 0) { isfs = true; }
    var res = Data;var Mat = 100000000;
    if (Data != "" && Data != "--" && Data != "-") {
        var _temp = parseFloat(Math.abs(Data));
        res = ForDight((_temp / Mat), Fix);
    }
    return (isfs ? "-" : "") + res + (padRight == "-" ? "" : padRight);
}

//时间随机数
function formatm() {
    var now = new Date();
    return now.getDate() + "" + now.getHours() + "" + now.getMinutes() + "";
}

function gethhmm(time){return time.substr(11,5);}

//随机数
function GetRandomNum(Min, Max) { var Range = Max - Min; var Rand = Math.random(); return (Min + Math.round(Rand * Range)); }

function showimg() {
    $x("flash_box").style.display = "none";
    var c_cn = $x("kx_list").className;
    if (c_cn.indexOf("fz14") > 0) {
        $x("kx_list").className = "kx_list kx_fz14";
    } else {
        $x("kx_list").className = "kx_list";
    }
    $x("image_box").style.display = "block"; window.zxgIsFirst = true; window.quoteIsFirst = true; Def.DisQuote();
    WriteCookie("em_hq_fls", "old", 99999);
}
function showfls() {
    $x("flash_box").style.display = "block";
    var c_cn = $x("kx_list").className;
    if (c_cn.indexOf("fz14") > 0) {
        $x("kx_list").className = "kx_list kx_fz14 kx_listfls";
    } else {
        $x("kx_list").className = "kx_list kx_listfls";
    }
    $x("image_box").style.display = "none"; window.zxgIsFirst = true; window.quoteIsFirst = true; Def.DisQuote();
    WriteCookie("em_hq_fls", "new", 99999);
    var picrtr = $x("actTab1").getElementsByTagName("span");
    var picrtk = $x("actTab2").getElementsByTagName("span");
    for (var i = 2; i < picrtr.length; i++) {
        if (picrtr[i].className == "cur") {
            setTimeout(function () { stockr.selectDay(i - 1); }, 200);
            break;
        }
    }
    for (var j = 0; j < picrtk.length; j++) {
        if (picrtk[j].className == "cur") {
            setTimeout(function () { stock.kMT = false; stock.FlashObj.selectButton(j + 1, 10); }, 2000);
            break;
        }
    }
}

function hotpersonafp(uid, oid, marketcode) {
    var iscks = false;
    if (GetCookie("pi")) {
        var gcks = Getcks("pi");
        if (gcks.split(';').length >= 3) {
            var name = gcks.split(';')[2];
            if (/^[\u4E00-\u9FA5][0-9a-zA-Z]{6}$/g.test(name)) { iscks = true; }
            else {
                var url = "http://iguba.eastmoney.com/action.aspx?callback=&action=oaddfollowperson&uid2=" + uid;
                Min.Loader.load(url + "&v=" + formatm(), "utf-8", function () {
                    oid.className = "allow"; oid.innerHTML = ""; oid.onclick = null;
                });
            }
        }
        else { iscks = true; }
    }
    else { iscks = true; }
    if (iscks) {
        location.href = "http://guba.eastmoney.com/login.aspx?url=http://quote.eastmoney.com/" + marketcode + ".html";
    }
}


function getdomain(min, max) {
    var min = 1; var max = 10;
    var res = "nufm3.dfcfw.com"; var m2 = "nufm2.dfcfw.com";
    var rom = GetRandomNum(min, max);
    if (rom != "1" && rom != "2" && rom != "3") { res = m2; }//80% ->nufm2
    //if (rom == "1") { res = m2; }//10% ->nufm2
    return res;
}

function removeAllChild(obj) {
    var div = $x(obj);
    while (div.hasChildNodes()) //当div下还存在子节点时 循环继续
    {
        div.removeChild(div.firstChild);
    }
}