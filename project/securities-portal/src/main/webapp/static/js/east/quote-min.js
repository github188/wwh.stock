var gdomain = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/";
var PicK = "http://hqpick.eastmoney.com/k/"; var PicN = "http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/Index.aspx?id={0}{1}&imageType={2}&token=44c9d251add88e27b65ed86506f6e5da";
var token = "beb0a0047196124721f56b0f0ff5a27c";//44c9d251add88e27b65ed86506f6e5da
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
function getmarket10(cd) {
    return cd.substring(0, 1) == "6" ? "1" : "0";
}
function getmarketjc(cd) {
    return (cd.substring(0, 1) == "6" ? "sh" : "sz") + cd;
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
    return decodeURI(dc.substring(begin + prefix.length, end));
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
    function QaDefault(Code, Market, Market_10, Name, HyId, RType, RCode, RMarket, tz, isag, lstng, cektp, ssrq, isxg, tfpxx) {
        //代码，市场_12，市场_10，名称，行业ID，关联类型，关联代码，关联市场, 通知页面刷新, 是否A股, 股票状态, 停牌检查, 上市日期, 是否新股, 停复牌信息
        _this = this; _this._Code = Code; _this._Market = Market; _this._Market_10 = Market_10; _this._Name = Name; _this._HyId = HyId; _this._RType = RType; _this._RCode = RCode; _this._RMarket = RMarket; _this.IsNotify = tz; _this.IsAGu = isag; _this.Lstng = lstng; _this.CekTp = cektp; _this.Ssrq = ssrq; _this.IsXg = isxg; _this.tfpxx = tfpxx;
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
        code: "-",
        market: "-",
        init: function () {
            window.quoteIsFirst = true; window.quoteRefresh = 12000;//行情
            window.zxgIsFirst = true; window.zxgRefresh = 30000; window.zxgDisNum = 8; window.favorsetInterval = 0;//自选股

            //window.quoteIsFirst = true; window.quoteRefresh = 12000;//行情
            //window.zxgIsFirst = true; window.zxgRefresh = 15000; window.zxgDisNum = 13; window.favorsetInterval = 0;//自选股

            window.GetTimeZoneInfo = false; window.phIsFirst = true;//行业排名
            _this.bindPageEvent();
            _this.Gethis();//最近访问
            _this.code = _this._Code;
            _this.market = _this._Market;
            tixing("addTX1", _this._Market, _this._Code); tixing("addTX2", _this._Market, _this._Code);
            createSWF(_this._Code, _this._Market, _this._Name, 678, 276, 678, 415, "1", "0", "");
            setTimeout(function () { _this.GetFavorList(_this._Code); }, 100);

            setTimeout(_this.UpZjlx, 500); setInterval(_this.UpZjlx, 300000);//资金流向
            if (_this._RType != "") { _this.DISRSI(); setInterval(_this.DISRSI, 20000); }//关联股票

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

            _this.ZJLBind();
            //熔断，沪港通标志
            _this.IconBind();
        },
        IconBind: function() {
            var _this = this;
            $.getScript("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + _this.code + _this.market + "&sty=MCSS&st=a&sr=1&p=1&ps=1000&cb=&js=var%20hgticon=(x)&token=de1161e2380d231908d46298ae339369", function () {

                if (window.hgticon && window.hgticon.stats == false) {
                    $("#hgt_icon").hide();
                    $("#sgt_icon").hide();
                    $(".rongi").hide();
                    return;
                }
                
                var item = window.hgticon;

                var itemlist = item.split(",");
                $("#hgt_icon").hide();
                $("#sgt_icon").hide();
                if (itemlist.length < 6) {
                    return;
                }
                var hgt = itemlist[4];
                var rongzi = itemlist[5];
        
                if (hgt == "沪股通") {
                    $("#hgt_icon").show();
                } else if (hgt == "深股通") {
                    $("#sgt_icon").show();
                }

                if (rongzi == "True") {
                    $(".rongi").show();
                } else {
                    $(".rongi").hide();
                }

            });
        },
        //资金流JS图
        ZJLBind: function() {
            var isIE8 = !+'\v1';

            if (isIE8) {
                $("#Iframezjlx").hide();
                $("#zjlximg").show();

                $("#iframe_phzjxl").hide();
                $("#img_phzjxl").show();

                $("#iframebingtu").hide();
                $("#img_bingtu").show();
            } else {
                $("#zjlximg").hide();
                $("#Iframezjlx").show();

                $("#img_phzjxl").hide();
                $("#iframe_phzjxl").show();

                $("#iframebingtu").show();
                $("#img_bingtu").hide();
            }
        },
        //根据QueryString参数名称获取值
        getQueryStringByName: function(name) {
            var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
            if (result == null || result.length < 1) {
                return "";
            }
            return result[1];
        },
        bindPageEvent: function () {//页面事件绑定
            if (_this.Lstng == "1") {
                _this.DisQuote();
                phrankS();//行业个股排行
                setInterval(function () {
                    _this.DisQuote();
                }, window.quoteRefresh);

                //盘口异动
                //bindPKYD();
                //setInterval(function () {
                //    bindPKYD();
                //}, 40000);

                setInterval(function () {
                    phrankS();//行业个股排行
                }, 45000);

                _this.GetTimeZone(); setInterval(function () { _this.GetTimeZone(); }, 30000);//时间戳
                //setInterval(function () {_this.UpPic(true);}, 180000);//更新R图和K线
            } else {
                var lststr = "";
                switch (_this.Lstng) { case "0": lststr = "未上市"; break; case "2": lststr = "已退市"; break; case "3": lststr = "暂停上市"; break; case "4": lststr = "终止上市"; break; }
                _this.$("price9").style.width = "130px";
                _this.$("price9").innerHTML = "<span class=\"lstng\">" + lststr + "</span>";
                _this.$("km1").innerHTML = ""; _this.$("km1").className = "xp3";
                _this.$("km2").innerHTML = ""; _this.$("km2").className = "xp4";
            }
            _this.$("RefPR").onclick = function () { prefresh(); }
            _this.$("hq_cr_close").onclick = function () { _this.$("hq_cr_tips").style.display = "none"; WriteCookie("emhq_cr", "1", 12); };
            _this.$("hq_cr_b").onmouseover = function () { _this.$("hq_cr_tips").style.display = "block"; };
            _this.$("hq_cr_b").onmouseout = function () { _this.$("hq_cr_tips").style.display = "none"; };

            $(".showRedTips").mouseover(function () {
                $(".sfwsx_title").hide();
                if ($(this).find(".sfwsx_title").length != 0) {
                    $(this).find(".sfwsx_title").show();
                } else {
                    $(this).parent().find(".sfwsx_title").show();
                }

            });

            $(".showRedTips").mouseout(function () {
                $(".sfwsx_title").hide();
            });

            if (_this.$("refgbauls") != null) {
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
            };

            //二维码
            $(".mobile-container, .mobile-icon").mouseenter(function () {
                $(this).find("img").attr("src", "http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/RC.ashx?url=http://m.quote.eastmoney.com/stock," + window.Def._Code + ".shtml");
                $(".QRcode-div").show();
            });

            $(".mobile-container").mouseout(function () {
                $(".QRcode-div").hide();
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
        setPicrTab: function (iftm) {
            var izpz = _this.Bian(iftm);
            if (izpz) {
                var res = _this.PanQian(iftm);
                if (res) {
                    if (_this.$("image_box").style.display != "none") {
                        _this.UpPic(false, true);
                    } else {
                        setTimeout(function () {
                            flyqd(8);
                        }, 5000);
                    }
                }
                else {
                    if (_this.$("image_box").style.display != "none") {
                        _this.UpPic(false, false);
                    } else {
                        setTimeout(function () {
                            flyqd(0);
                        }, 5000);
                    }
                }
            }
        },
        GetTimeZone: function () {//系统时间
            //Min.Loader.load("http://quote.eastmoney.com/timezone.js?" + formatm(), 'utf-8', function () {
            var dt = new Date($("#systemTime").val());
            window.GetTimeZoneInfo = _this.Bian(dt);
            //var Notify = window["Notify"];
            _this.setPicrTab(dt);
            //if (Notify != "undefined" && Notify != "" && !window.Notifyed && _this.IsNotify) {
            //    if (Notify == 0) {
            //        clearInterval(window.NotifyS);
            //    } else {
            //        //_this.NotifyPage(Notify * 60000);
            //    }
            //}
            //if (_this.tfpxx != "") { _this.DisTfpxx(); }
            //});
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
                            if (asl.data.check == "True") {
                                _this.$("addZX1").innerHTML = "<b class=\"plus\"></b>删自选"; _this.$("addZX2").innerHTML = "<b class=\"plus\"></b>删自选";
                                _this.$("addZX1").onclick = function () { OpZX("dsz", _this._Code, _this._Market); }; _this.$("addZX2").onclick = function () { OpZX("dsz", _this._Code, _this._Market); };
                            } else {
                                _this.$("addZX1").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>"; _this.$("addZX2").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>";
                                _this.$("addZX1").onclick = function () { OpZX("asz", _this._Code, _this._Market); }; _this.$("addZX2").onclick = function () { OpZX("asz", _this._Code, _this._Market); };
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
                    if (emhq_stock.indexOf(_this._Code) >= 0) {
                        _this.$("addZX1").innerHTML = "<b class=\"plus\"></b>删自选"; _this.$("addZX2").innerHTML = "<b class=\"plus\"></b>删自选";
                        _this.$("addZX1").onclick = function () { OpZXToOld("dsz", _this._Code, _this._Market); }; _this.$("addZX2").onclick = function () { OpZXToOld("dsz", _this._Code, _this._Market); };
                    } else {
                        _this.$("addZX1").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>"; _this.$("addZX2").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>";
                        _this.$("addZX1").onclick = function () { OpZXToOld("asz", _this._Code, _this._Market); }; _this.$("addZX2").onclick = function () { OpZXToOld("asz", _this._Code, _this._Market); };
                    }
                    _this.FavorPad(emhq_stock, thisCode);
                } else {
                    //if (_this.$("addZX2") == null) return;
                    _this.$("addZX1").onclick = function () { OpZXToOld("asz", _this._Code, _this._Market); }; //_this.$("addZX2").onclick = function () { OpZXToOld("asz", _this._Code, _this._Market); };
                    Min.Loader.load(portalurl+"stockeast/selflist", "utf-8", function () {
                        _this.FavorPad(allstocklist, thisCode);
                    });
                }
            }
        },
        FavorPad: function (initlist, thisCode) {
            var arr = new Array();
            (!initlist) && (initlist += ",000001,000002,000004,000005,000006,000008,000009,000010,000011,000012,000014,000016,000017,000018");
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
                if (_this.$("image_box").style.display != "none") {
                    window.zxgDisNum = 11; $x("flsrmt7").className = "title1 mt6";
                    $("#flgshxsj").addClass("SPtitle1");
                } else {
                    window.zxgDisNum = 11; $x("flsrmt7").className = "title1 mt13";
                    $("#flgshxsj").removeClass("SPtitle1");
                }
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
            var _hismarket = "1";
            if (_this._Market_10 == "1") { _hismarket = "sh"; } else { _hismarket = "sz"; }
            var arg = { def: "", set: "a-" + _hismarket + "-" + _this._Code + "-" + _this._Name, lns: 11 }; var HV = new HistoryViews("historyest", arg);
        },
        GetFbFj: function (cd, jsfbfjzs) {
            if (_this.$("image_box").style.display != "none") {
                window.fbfjDisNum = 25; _this.$("fblist").className = "line22";
            } else {
                window.fbfjDisNum = 25; _this.$("fblist").className = "line22";
            }

            Min.Loader.load("http://" + getdomain() + "/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + cd + "&sty=DPTTFD&st=z&sr=1&p=1&ps=&cb=&js=var%20jsfbfj={list:[(x)]};&token=" + window.token + "&v=" + Math.random(), "utf-8", function () {
                var sia = 0; var sib = 0;
                if (jsfbfj.list.length > 0) {
                    while (_this.$("fblist").hasChildNodes()) { _this.$("fblist").removeChild(_this.$("fblist").childNodes[0]); }
                    var tem = jsfbfj.list[0].split(',');
                    if (tem[0] != "" && tem[0] != "-") {
                        var tema = tem[0].split('|');
                        for (var i = tema.length - 1; i >= 0; i--) {
                            if (sia > window.fbfjDisNum) { break; }
                            if (sia < window.fbfjDisNum) {
                                var tem_zx = tema[i].split('~');
                                var _tr = document.createElement("tr");
                                var _tdname = document.createElement("td"); _tdname.innerHTML = tem_zx[0];
                                var _tdprice = document.createElement("td"); _tdprice.className = udcls(tem_zx[1], jsfbfjzs); _tdprice.innerHTML = tem_zx[1];
                                var _tdzdf = document.createElement("td"); _tdzdf.className = udcls(tem_zx[3]); _tdzdf.innerHTML = tem_zx[2] + "" + udt(tem_zx[3], tem_zx[2]);
                                _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                                _this.$("fblist").appendChild(_tr);
                                sia++;
                            }
                        }
                    }
                    for (var i = 0; i < window.fbfjDisNum - sia; i++) {
                        var _tr = document.createElement("tr");
                        var _tr = document.createElement("tr");
                        var _tdname = document.createElement("td"); _tdname.className = "nm"; _tdname.innerHTML = "<span class=\"a\">-</span>";
                        var _tdprice = document.createElement("td"); _tdprice.innerHTML = "-";
                        var _tdzdf = document.createElement("td"); _tdzdf.innerHTML = "-";
                        _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                        _this.$("fblist").appendChild(_tr);
                    }
                }
            });
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
            if ((window.GetTimeZoneInfo || window.quoteIsFirst) && _this.Lstng == "1") {
                var sjs = Math.random().toString().replace('.', '');
                jQuery.ajax({
                    url: "http://nuff.eastmoney.com/EM_Finance2015TradeInterface/JS.ashx?id=" + _this._Code + "" + _this._Market + "&token=" + window.token + "&cb=callback" + sjs,
                    dataType: "jsonp",
                    scriptCharset: "utf-8",
                    jsonpCallback: "callback" + sjs,
                    success: function (json) {
                        if (json.Value.length > 0) {
                            var isSus = false;
                            var skd = json.Value;//基本数据
                            if (skd[44] == "-1") {//停牌
                                isSus = true;
                                if (_this.CheckBeforeNine()) {
                                    document.title = (skd[2] + "" + skd[25] + " " + skd[27] + "(" + skd[29] + "%)");
                                } else {
                                    document.title = ((skd[2] + "(停牌)"));
                                }
                            }
                            else {
                                document.title = (skd[2] + "" + skd[25] + " " + skd[27] + "(" + skd[29] + "%)");
                                $("#name").html(skd[2]);
                            }
                            var temprice = _this.$("price9").innerHTML;
                            _this.$("price9").innerHTML = isSus ? "-" : skd[25]; //最新价
                            _this.$("km1").innerHTML = isSus ? "-" : (skd[27] == "-" ? "" : skd[27]);//涨跌
                            _this.$("km2").innerHTML = isSus ? "-" : (skd[29] == "-" ? "" : skd[29] + "%");//涨幅
                            if (!isSus) { _this.Setudclass(skd[27]); }//涨跌
                            _this.hqcr(skd[44], skd[51], skd[52]);
                            _this.$("day").innerHTML = "（" + GetFullWeekTime(skd[49]) + "）";//时间
                            if (_this.$("zjlxuptc") != null) _this.$("zjlxuptc").innerHTML = "（" + GetFullWeekTime(skd[49]) + "）";//时间
                            _this.$("gt1").innerHTML = isSus ? "-" : skd[28]; _this.$("gt1").className = isSus ? "txtl" : "txtl " + udcls(skd[28], skd[34]);//今开
                            _this.$("gt2").innerHTML = isSus ? "-" : skd[30]; _this.$("gt2").className = isSus ? "txtl" : "txtl " + udcls(skd[30], skd[34]);//最高
                            _this.$("gt3").innerHTML = skd[23]; _this.$("gt3").className = "txtl red";//涨停
                            _this.$("gt4").innerHTML = isSus ? "-" : (skd[37] == "-" ? "-" : skd[37] + "%");//换手
                            _this.$("gt5").innerHTML = isSus ? "-" : fmtdig(skd[31], 1, 2, "", true) + "手";//成交量
                            _this.$("gt6").innerHTML = skd[38];//市盈
                            _this.$("gt6_2").innerHTML = skd[38];//市盈
                            _this.$("gt7").innerHTML = fmtdig(skd[46], 1, 2, "", true);//总市值
                            _this.$("gt7_2").innerHTML = fmtdig(skd[46], 1, 2, "", true);//总市值
                            _this.$("gt8").innerHTML = skd[34];//昨收
                            _this.$("gt9").innerHTML = isSus ? "-" : skd[32]; _this.$("gt9").className = isSus ? "txtl" : "txtl " + udcls(skd[32], skd[34]);//最低
                            _this.$("gt10").innerHTML = skd[24]; _this.$("gt10").className = "txtl green";//跌停
                            _this.$("gt11").innerHTML = isSus ? "-" : skd[36];//量比
                            _this.$("gt12").innerHTML = isSus ? "-" : skd[35];//成交额
                            _this.$("gt13").innerHTML = skd[43];//市净
                            _this.$("gt13_2").innerHTML = skd[43];//市净
                            _this.$("gt14").innerHTML = fmtdig(skd[45], 1, 2, "", true);//流通市值
                            _this.$("gt14_2").innerHTML = fmtdig(skd[45], 1, 2, "", true);//流通市值
                            _this.$("irwb").innerHTML = isSus ? "-" : skd[41] + "%"; _this.$("irwb").className = udcls(skd[41]);//委比
                            _this.$("irwc").innerHTML = skd[42]; _this.$("irwc").className = udcls(skd[42]);//委差
                            _this.$("rgt1").innerHTML = isSus ? "-" : skd[25]; _this.$("rgt1").className = isSus ? "" : udcls(skd[25], skd[34]);//最新价
                            _this.$("rgt2").innerHTML = isSus ? "-" : skd[26]; _this.$("rgt2").className = isSus ? "" : udcls(skd[26], skd[34]);//均价
                            /*_this.$("rgt3").innerHTML = isSus ? "-" : skd[29] + "%"; _this.$("rgt3").className = isSus ? "" : udcls(skd[29]);//涨幅
                            _this.$("rgt4").innerHTML = isSus ? "-" : skd[27]; _this.$("rgt4").className = isSus ? "" : udcls(skd[27]);//涨跌
                            _this.$("rgt5").innerHTML = isSus ? "-" : fmtdig(skd[31], 1, 2, "", true) + "手";//总手
                            _this.$("rgt6").innerHTML = isSus ? "-" : skd[35];//金额
                            _this.$("rgt7").innerHTML = isSus ? "-" : skd[37] == "" ? "-" : (skd[37] == "-" ? "-" : skd[37] + "%");//换手
                            _this.$("rgt8").innerHTML = isSus ? "-" : skd[36];//量比
                            _this.$("rgt9").innerHTML = isSus ? "-" : skd[30]; _this.$("rgt9").className = isSus ? "" : udcls(skd[30], skd[34]);//最高
                            _this.$("rgt10").innerHTML = isSus ? "-" : skd[32]; _this.$("rgt10").className = isSus ? "" : udcls(skd[32], skd[34]);//最低
                            _this.$("rgt11").innerHTML = isSus ? "-" : skd[28]; _this.$("rgt11").className = isSus ? "" : udcls(skd[28], skd[34]);//今开
                            _this.$("rgt12").innerHTML = skd[34];//昨收
                            _this.$("rgt13").innerHTML = skd[23]; _this.$("rgt13").className = "red";//涨停
                            _this.$("rgt14").innerHTML = skd[24]; _this.$("rgt14").className = "green";//跌停*/
                            _this.$("rgt15").innerHTML = isSus ? "-" : skd[39]; _this.$("rgt15").className = "red";//外盘
                            _this.$("rgt16").innerHTML = isSus ? "-" : skd[40]; _this.$("rgt16").className = "green";//内盘
                            if (isSus) {
                                _this.$("arrow-find").className = "";
                                _this.$("km1").innerHTML = ""; _this.$("km1").className = "xp3";
                                _this.$("km2").innerHTML = ""; _this.$("km2").className = "xp4";
                                if (_this.CheckBeforeNine()) {
                                    _this.$("price9").innerHTML = "--";//早上9点前停牌处理
                                } else {
                                    _this.$("price9").innerHTML = "<span class=\"lstng\"><a href=\"http://data.eastmoney.com/tfpxx/\" target=\"_blank\" class=\"red wz\">停牌</a></span>";
                                }
                            }
                            _this.cbian = (skd[44] == "0" && _this.$("price9").innerHTML != temprice) ? true : false;

                            //b5s5
                            var bdt = skd[47].split('|');
                            var sdt = skd[48].split('|');
                            _this.$("gts5a").innerHTML = skd[12]; _this.$("gts5b").innerHTML = skd[22]; _this.$("gts5c").innerHTML = sdt[4] == "0" ? "" : sdt[4]; _this.$("gts5a").className = udcls(skd[12], skd[34]); _this.$("gts5c").className = udcls(sdt[4]);
                            _this.$("gts4a").innerHTML = skd[11]; _this.$("gts4b").innerHTML = skd[21]; _this.$("gts4c").innerHTML = sdt[3] == "0" ? "" : sdt[3]; _this.$("gts4a").className = udcls(skd[11], skd[34]); _this.$("gts3c").className = udcls(sdt[3]);
                            _this.$("gts3a").innerHTML = skd[10]; _this.$("gts3b").innerHTML = skd[20]; _this.$("gts3c").innerHTML = sdt[2] == "0" ? "" : sdt[2]; _this.$("gts3a").className = udcls(skd[10], skd[34]); _this.$("gts3c").className = udcls(sdt[2]);
                            _this.$("gts2a").innerHTML = skd[9]; _this.$("gts2b").innerHTML = skd[19]; _this.$("gts2c").innerHTML = sdt[1] == "0" ? "" : sdt[1]; _this.$("gts2a").className = udcls(skd[9], skd[34]); _this.$("gts2c").className = udcls(sdt[1]);
                            _this.$("gts1a").innerHTML = skd[8]; _this.$("gts1b").innerHTML = skd[18]; _this.$("gts1c").innerHTML = sdt[0] == "0" ? "" : sdt[0]; _this.$("gts1a").className = udcls(skd[8], skd[34]); _this.$("gts1c").className = udcls(sdt[0]);
                            _this.$("gtb1a").innerHTML = skd[3]; _this.$("gtb1b").innerHTML = skd[13]; _this.$("gtb1c").innerHTML = bdt[0] == "0" ? "" : bdt[0]; _this.$("gtb1a").className = udcls(skd[3], skd[34]); _this.$("gtb1c").className = udcls(bdt[0]);
                            _this.$("gtb2a").innerHTML = skd[4]; _this.$("gtb2b").innerHTML = skd[14]; _this.$("gtb2c").innerHTML = bdt[1] == "0" ? "" : bdt[1]; _this.$("gtb2a").className = udcls(skd[4], skd[34]); _this.$("gtb2c").className = udcls(bdt[1]);
                            _this.$("gtb3a").innerHTML = skd[5]; _this.$("gtb3b").innerHTML = skd[15]; _this.$("gtb3c").innerHTML = bdt[2] == "0" ? "" : bdt[2]; _this.$("gtb3a").className = udcls(skd[5], skd[34]); _this.$("gtb3c").className = udcls(bdt[2]);
                            _this.$("gtb4a").innerHTML = skd[6]; _this.$("gtb4b").innerHTML = skd[16]; _this.$("gtb4c").innerHTML = bdt[3] == "0" ? "" : bdt[3]; _this.$("gtb4a").className = udcls(skd[6], skd[34]); _this.$("gtb4c").className = udcls(bdt[3]);
                            _this.$("gtb5a").innerHTML = skd[7]; _this.$("gtb5b").innerHTML = skd[17]; _this.$("gtb5c").innerHTML = bdt[4] == "0" ? "" : bdt[4]; _this.$("gtb5a").className = udcls(skd[7], skd[34]); _this.$("gtb5c").className = udcls(bdt[4]);
                            _this.GetFbFj(_this._Code + "" + _this._Market, skd[34]);
                        } else { var GetDPINFO = new GetDP(); GetDPINFO.dis(); }
                    }
                });
                _this.sansuo = setInterval(_this.hongdise, 300);
                window.quoteIsFirst = false;
            }
        },
        DISRSI: function () {//关联股票
            switch (_this._RType)//1A股 B股 2港股
            {
                case "1":
                    Min.Loader.load(gdomain + "CompatiblePage.aspx?Type=ZT&jsName=js_skr&fav=" + _this._RCode + "" + _this._RMarket + "&Reference=xml&rt=" + formatm(), "utf-8", function () {
                        var jnm = eval("js_skr");
                        if (jnm.favif != null && jnm.favif != "") {
                            var _MarketCode = _this._RMarket == "1" ? "sh" + _this._RCode : "sz" + _this._RCode; var temgl = jnm.favif[0].split(',');
                            for (var i = 0; i < temgl.length; i++) {
                                _this.$("rstocka").innerHTML = "<a href=\"http://quote.eastmoney.com/" + _MarketCode + ".html\" target=\"_blank\">" + temgl[2].replace("A股", "").replace("a股", "").replace("Ａ股", "").replace("a", "").replace("A", "").replace("Ａ", "") + " A股行情</a>";
                                _this.$("rstockb").innerHTML = temgl[3] + "&nbsp;&nbsp;" + temgl[4]; _this.$("rstockb").className = udcls(temgl[4]);
                                break;
                            }
                        }
                    });
                    break;
                case "2":
                    Min.Loader.load("http://hq2hk.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?reference=xml&Type=z&sortType=A&sortRule=1&jssort=1&jsname=gl_data&ids=" + _this._RCode + "5&math=" + formatm(), "utf-8", function () {
                        if (gl_data.quotation != null && gl_data.quotation != "") {
                            var dataRow = String(gl_data.quotation).split(",");
                            _this.$("rstocka").innerHTML = "<a href=\"http://quote.eastmoney.com/hk/" + dataRow[1] + ".html\" target=\"_blank\">" + dataRow[2].substr(0, 6) + " H股行情</a>";
                            _this.$("rstockb").innerHTML = dataRow[5] + "&nbsp;&nbsp;" + dataRow[10] + "&nbsp;&nbsp;" + dataRow[11]; _this.$("rstockb").className = udcls(dataRow[10]);
                        }
                    });
                    break;
                case "3":
                    Min.Loader.load(gdomain + "CompatiblePage.aspx?Type=ZT&jsName=js_skr&fav=" + _this._RCode + "" + _this._RMarket + "&Reference=xml&rt=" + formatm(), "utf-8", function () {
                        var jnm = eval("js_skr");
                        if (jnm.favif != null && jnm.favif != "") {
                            var _MarketCode = _this._RMarket == "1" ? "sh" + _this._RCode : "sz" + _this._RCode;
                            var temgl = jnm.favif[0].split(',');
                            for (var i = 0; i < temgl.length; i++) {
                                _this.$("rstocka").innerHTML = "<a href=\"http://quote.eastmoney.com/" + _MarketCode + ".html\" target=\"_blank\">" + temgl[2].replace("B股", "").replace("b股", "").replace("Ｂ股", "").replace("b", "").replace("B", "").replace("Ｂ", "") + "B股行情</a>";
                                _this.$("rstockb").innerHTML = temgl[3] + "&nbsp;&nbsp;" + temgl[4]; _this.$("rstockb").style.color = udcls(temgl[4]);
                                break;
                            }
                        }
                    });
                    break;
            }
        },
        Gethis: function () {
            var _hismarket = "1";
            if (_this._Market_10 == "1") { _hismarket = "sh"; } else { _hismarket = "sz"; }
            var arg = { def: "", set: "a-" + _hismarket + "-" + _this._Code + "-" + _this._Name, lns: 11 }; var HV = new HistoryViews("historyest", arg);
        },
        UpZjlx: function () {
            NewZJL(_this._Code);
        },
        UpPic: function (refk, pq) {//更新图 (refk是否需要更新K图，是否为盘前图)
            var pqtit = _this.$("actTab4").getElementsByTagName("span");
            for (var i = 0; i < pqtit.length; i++) {
                pqtit[i].className = "";
            }
            if (pq) {
                pqtit[0].className = "cur";
                if (_this.Lstng == "0") {
                    _this.$("picr").src = "http://hqres.eastmoney.com/EMQuote_Lib/img/picrnotfund.gif";
                } else {
                    _this.$("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "rc") + "&rt=" + formatm();
                }
            } else {
                pqtit[1].className = "cur";
                if (_this.Lstng == "0") {
                    _this.$("picr").src = "http://hqres.eastmoney.com/EMQuote_Lib/img/picrnotfund.gif";
                } else {
                    if (_this.IsAGu == 1) {
                        _this.$("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "r") + "&rt=" + formatm();
                    } else {
                        _this.$("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "rc") + "&rt=" + formatm();
                    }
                }
            }
            if (refk) {
                if (_this.Lstng == "0") {
                    _this.$("pick").src = "http://hqres.eastmoney.com/EMQuote_Lib/img/picknotfund.gif?1";
                } else {
                    if (_this.CekNSSs()) {
                        _this.$("pick").src = "http://hqres.eastmoney.com/EMQuote_Lib/img/picknotfund.gif?2";
                    } else {
                        _this.$("pick").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "KXL") + "&rt=" + formatm();
                    }
                }
            }
        },
        CekNSSs: function () {
            var res = false;
            var dt = new Date(window["bjTime"] * 1000);
            var ys = dt.getFullYear(); var ms = dt.getMonth() + 1; var ds = dt.getDate();
            var hs = dt.getHours(); var mms = dt.getMinutes();
            if (_this.Ssrq != "") {
                var _dt = new Date(_this.Ssrq);
                var _ys = _dt.getFullYear(); var _ms = _dt.getMonth() + 1; var _ds = _dt.getDate();
                if (ys == _ys && ms == _ms && ds == _ds) {
                    if (hs < 9 || (hs == 9 && mms < 28)) { res = true; }
                }
            } else {
                if (hs < 9 || (hs == 9 && mms < 28)) { res = true; }
            }
            return res;
        },
        DisTfpxx: function () {
            var __dt = new Date(window["bjTime"] * 1000);
            var __hs = __dt.getHours(); var __mms = __dt.getMinutes();
            if (__hs < 9 || (__hs <= 9 && __mms <= 14)) {
                _this.$("price9").innerHTML = "<span class=\"lstng\"><a href=\"http://data.eastmoney.com/tfpxx/\" target=\"_blank\" class=\"red tp\">停牌</a></span>";
                _this.$("arrow-find").className = "";
                _this.$("km1").innerHTML = ""; _this.$("km1").className = "xp3";
                _this.$("km2").innerHTML = ""; _this.$("km2").className = "xp4";
            }
        },
        hqcr: function (hq_cr_type, _hq_cr_time, hq_cr_cnt) {
            var hq_cr_time = _hq_cr_time.length > 5 ? _hq_cr_time.substring(16, 11) : "-";
            if (hq_cr_type == "8" || hq_cr_type == "9" || hq_cr_type == "10" || hq_cr_type == "11") {
                _this.$("hq_cr").style.display = "block";
                //_this.$("hq_cr_time").innerHTML = hq_cr_time == "-" ? "暂停交易" : "暂停交易至" + hq_cr_time;
                if (hq_cr_type == "8" || hq_cr_type == "10") { _this.$("hq_cr_time").innerHTML = "暂停交易15分钟"; } else { _this.$("hq_cr_time").innerHTML = "暂停交易至15:00"; }
                _this.$("arrowud").setAttribute("xid", "1");
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
        },
        CheckBeforeNine: function () {
            var res = false;
            var __dt = new Date(window["bjTime"] * 1000);
            var __hs = __dt.getHours(); var __mms = __dt.getMinutes();
            if (__hs < 8 && __hs < 9) {
                res = true;
            }
            return res;
        }
    };
    window.QaDefault = QaDefault;
})();

function phrankS() {
    var def = "C";
    var sed = $x("select2").getElementsByTagName("span")[0].getAttribute("value");
    var hyid = $x("pylist").getAttribute("value");
    if (sed != null) {
        def = sed;
    }
    phrank(def, hyid);
}

function bindPKYD() {
    var url = "http://nuyd.eastmoney.com/EM_UBG_PositionChangesInterface/api/js?style=top&num=5&ac=normal&check=itntcd&js=[(x)]&cb=var%20pkyd=&unIncloudType=1,2,8195,8196,8205,8206";
    Min.Loader.load(url, "utf-8", function () {
        if (pkyd != null && pkyd != "" && pkyd != "undefined") {

            var html = '';
            for (var i = 0; i < 5; i++) {
                if (pkyd[i]) {
                    var item = pkyd[i].split(',');
                    var stockId = item[0];
                    var code = stockId.substring(0, 6);
                    var stockName = item[2];
                    var type = item[3];
                    var count = item[4];
                    var color, textClass;
                    var market = stockId[stockId.length - 1] == "1" ? "sh" : "sz";
                    if (item[5] == "1") {
                        color = "red";
                        textClass = "text_twinkle_red";
                    } else {
                        color = "green";
                        textClass = "text_twinkle_green";
                    }
                    var oldValue = $("#pkyd_table tr").eq(i).find("td").last().text();
                    if (count == oldValue) {
                        textClass = "";
                    }
                    html += '<tr>' +
                    '<td>' + item[1] + '</td>' +
                    '<td style="width:50px;"><a href="http://quote.eastmoney.com/' + market + code + '.html" target="_blank">' + stockName + '</a></td>' +
                    '<td class="' + color + ' ' + textClass + '">' + type + '</td>' +
                    '<td class="' + color + ' ' + textClass + '">' + count + '</td>' +
                    '</tr>';
                } else {
                    html += "<tr><td></td><td></td><td></td><td></td></tr>";
                }
            }
            $("#pkyd_table").html(html);

            $("#pkyd_table tr").click(function () {
                window.open($(this).find("a").attr("href"));
            });

            $("#pkyd_table tr").mouseover(function() {
                $(this).addClass("over");
            });

            $("#pkyd_table tr").mouseout(function () {
                $(this).removeClass("over");
            });

            setTimeout(function() {
                $("#pkyd_table td").removeClass("text_twinkle_red");
                $("#pkyd_table td").removeClass("text_twinkle_green");
            },2500);
        }
    });
}

//行业个股排行数据
function phrank(a, _hyid) {
    if (window.GetTimeZoneInfo || window.phIsFirst) {
        var sytn = { "C": "涨跌幅", "D": "涨跌", "E": "成交额", "F": "成交量", "G": "涨跌幅", "H": "量比" };
        var sytv = { "C": 5, "D": 4, "E": 7, "F": 6, "G": 9, "H": 8 };
        $x("pytitnme").innerHTML = sytn[a];
        var url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C.BK0" + _hyid + "1&sty=FDCS&st=" + a + "&sr=-1&p=1&ps=8&lvl=&cb=&js=var%20jspy=[(x)];&token=" + token + "&v=" + Math.random();
        Min.Loader.load(url, "utf-8", function () {
            if (jspy != null && jspy != "" && jspy != "undefined") {
                var si = 0; var xggp = [];
                if (jspy.length > 0) {
                    while ($x("pylist").hasChildNodes()) {
                        $x("pylist").removeChild($x("pylist").childNodes[0]);
                    }
                }
                for (var i = 0; i < 8; i++) {
                    var _tr = '';
                    if (jspy[i]) {
                        var tem_zx = jspy[i].split(','); var _marketcode = tem_zx[0] == "1" ? "sh" + tem_zx[1] : "sz" + tem_zx[1];
                        _tr = document.createElement("tr");
                        var _tdname = document.createElement("td"); _tdname.className = "nm"; _tdname.innerHTML = "<span class=\"a\"><a href=\""+portalurl+"stockeast/detail?stockCode=" + tem_zx[1] + "\" target=\"_self\">" + tem_zx[2] + "</a></span>";
                        var _tdprice = document.createElement("td"); _tdprice.className = udcls(tem_zx[4]); _tdprice.innerHTML = tem_zx[3];
                        var _tdzdf = document.createElement("td"); _tdzdf.className = (sytv[a] != 7 && sytv[a] != 6 && sytv[a] != 8) ? udcls(tem_zx[sytv[a]]) + " ar" : "ar"; _tdzdf.innerHTML = ((a == "E" || a == "F") ? fmtdig(tem_zx[sytv[a]], 1, 2, "", true) : tem_zx[sytv[a]]);
                        _tr.appendChild(_tdname); _tr.appendChild(_tdprice); _tr.appendChild(_tdzdf);
                        $x("pylist").appendChild(_tr);
                        if (i <= 5) { xggp.push("<li><a href=\""+portalurl+"stockeast/detail?stockCode=" + tem_zx[1] + "\" target=\"_self\">" + tem_zx[2] + "</a>(" + tem_zx[3] + " <span style=\"" + udcolor(tem_zx[4]) + "\">" + tem_zx[5] + "</span>)</li>"); }
                        si++;
                    } else {
                        _tr = '<tr><td>-</td><td>-</td><td>-</td></tr>';
                        $("#pylist").append(_tr);
                    }
                }
                $x("xggp").innerHTML = xggp.toString().replace(/,/g, "");
            }
        });
        window.phIsFirst = false;
    }
}

function OpZX(op, code, market) {
    Min.Loader.load("http://mystock.eastmoney.com/mystock.aspx?f=" + op + "&sc=" + code + "|0" + market + "|01&var=opfavres&rt=" + formatm(), "utf-8", function () {
        if (opfavres.result == "1") {
            if (op == "dsz") {
                $x("addZX1").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>"; $x("addZX2").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>";
                $x("addZX1").onclick = function () { OpZX("asz", _this._Code, _this._Market); }; $x("addZX2").onclick = function () { OpZX("asz", _this._Code, _this._Market); };
                $x("zxgz1").innerHTML = parseInt($x("zxgz1").innerHTML) - 1;
                $x("zxgz2").innerHTML = parseInt($x("zxgz2").innerHTML) - 1;
            } else {
                $x("addZX1").innerHTML = "<b class=\"plus\"></b>删自选"; $x("addZX2").innerHTML = "<b class=\"plus\"></b>删自选";
                $x("addZX1").onclick = function () { OpZX("dsz", _this._Code, _this._Market); }; $x("addZX2").onclick = function () { OpZX("dsz", _this._Code, _this._Market); };
                $x("zxgz1").innerHTML = parseInt($x("zxgz1").innerHTML) + 1;
                $x("zxgz2").innerHTML = parseInt($x("zxgz2").innerHTML) + 1;
            }
        }
    });
}
function OpZXToOld(op, code) {
    var cookiesValue = GetCookie("emhq_stock");
    if (op == "dsz") {
        if (cookiesValue != null) {
            cookiesValue = decodeURIComponent(cookiesValue);
            var codeitemlist = cookiesValue.split(",");
            var str = "";
            for (var i = 0; i < codeitemlist.length; i++) {
                if (!codeitemlist[i] || codeitemlist[i] == code || codeitemlist[i] == null) {
                    continue;;
                }

                str += codeitemlist[i] + ",";
            }

            cookiesValue = str.substring(0, str.length - 1);

            $x("addZX1").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>"; $x("addZX2").innerHTML = "<a href=\"http://quote.eastmoney.com/favor/\" target=\"_blank\"><b></b>加自选</a>";
            $x("addZX1").onclick = function () { OpZXToOld("asz", _this._Code, _this._Market); }; $x("addZX2").onclick = function () { OpZXToOld("asz", _this._Code, _this._Market); };
            //$x("zxgz1").innerHTML = parseInt($x("zxgz1").innerHTML) - 1;
            //$x("zxgz2").innerHTML = parseInt($x("zxgz2").innerHTML) - 1;
        }
    } else {
        if (cookiesValue == "" || cookiesValue == null) {
            cookiesValue = code;
        }
        else {
            if (cookiesValue.indexOf(code) == -1) {
                cookiesValue = decodeURIComponent(cookiesValue);
                if (cookiesValue != "") {
                    cookiesValue = code + "," + cookiesValue;
                } else {
                    cookiesValue = code;
                }
            }
        }
        $x("addZX1").innerHTML = "<b class=\"plus\"></b>删自选"; $x("addZX2").innerHTML = "<b class=\"plus\"></b>删自选";
        $x("addZX1").onclick = function () { OpZXToOld("dsz", _this._Code, _this._Market); }; $x("addZX2").onclick = function () { OpZXToOld("dsz", _this._Code, _this._Market); };
        //$x("zxgz1").innerHTML = parseInt($x("zxgz1").innerHTML) + 1;
        //$x("zxgz2").innerHTML = parseInt($x("zxgz2").innerHTML) + 1;
    }
    WriteCookie("emhq_stock", cookiesValue, 8760);
}

//大盘
function GetDP() {
    window.dpzs = 1;
    this.dis = function () {
        if (window.GetTimeZoneInfo == true || window.dpzs == 1) {
            Min.Loader.load(gdomain + "CompatiblePage.aspx?Type=C&jsName=js_dp&ino=0000011,3990012&Reference=xml&rt=" + Math.random(), 'utf-8', function () {
                var jnm = eval("js_dp");
                if (jnm.dpif != null && jnm.dpif != "") {
                    var tem_shdp = jnm.dpif[0].split(','); var tem_szdp = jnm.dpif[1].split(',');
                    $x("qqgs1").innerHTML = "<p><a href=\"http://quote.eastmoney.com/zs000001.html\" target=\"_blank\" class=\"blue\">上证</a>：<span style=\"" + udcolor(tem_shdp[4]) + "\"><b>" + tem_shdp[3] + "</b> " + udt(tem_shdp[4]) + "<b>" + tem_shdp[4] + "</b>  " + udt(tem_shdp[4]) + "<b>" + tem_shdp[5] + "  " + ForDight(tem_shdp[6] / 10000, 2) + "</b></span>亿元&nbsp;(涨:<a href=\"http://quote.eastmoney.com/center/list.html#10_0_0_u?sortType=C&sortRule=-1\" target=\"_blank\" class=\"red\"><b>" + tem_shdp[7] + "</b></a> 平:<b>" + tem_shdp[8] + "</b> 跌:<a href=\"http://quote.eastmoney.com/center/list.html#10_0_0_d?sortType=C&sortRule=1\" target=\"_blank\" class=\"green\"><b>" + tem_shdp[9] + "</b></a>)</p><p><a href=\"http://quote.eastmoney.com/zs399001.html\" target=\"_blank\" class=\"blue\">深证</a>：<span style=\"" + udcolor(tem_szdp[4]) + "\"><b>" + tem_szdp[3] + "</b> " + udt(tem_szdp[4]) + "<b>" + tem_szdp[4] + "</b>  " + udt(tem_szdp[4]) + "<b>" + tem_szdp[5] + "  " + ForDight(tem_szdp[6] / 10000, 2) + "</b></span>亿元&nbsp;(涨:<a href=\"http://quote.eastmoney.com/center/list.html#20_0_0_u?sortType=C&sortRule=-1\" target=\"_blank\" class=\"red\"><b>" + tem_szdp[7] + "</b></a> 平:<b>" + tem_szdp[8] + "</b> 跌:<a href=\"http://quote.eastmoney.com/center/list.html#20_0_0_d?sortType=C&sortRule=1\" target=\"_blank\" class=\"green\"><b>" + tem_szdp[9] + "</b></a>)</p>";
                }
                window.dpzs = 0;
            });
        }
    }
}

function NewZJL(code) {
    Min.Loader.load("http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + code + _this._Market + "&sty=DCFF&st=z&sr=&p=&ps=&cb=&js=var%20zjlx_detail=(x)&token=7bc05d0d4c3c22ef9fca8c2a912d779c", "utf-8", function () {
        if (zjlx_detail != null && zjlx_detail.stats != false) {
            var rd = zjlx_detail.split(',');
            $x("hz_a").innerHTML = ForDight(parseFloat(rd[2]), 2);
            $x("hz_a").className = udcls(rd[2]);
            $x("hz_b").innerHTML = ForDight(parseFloat(rd[3]), 2).replace("-", "");
            $x("hz_b").className = udcls(rd[3]);
            $x("hz_c").innerHTML = rd[12];
            $x("hz_c").className = udcls(rd[12]);

            $x("hz_d").innerHTML = (rd[4] / 10000).toFixed(2) || "";
            $x("hz_e").innerHTML = (rd[5] / 10000).toFixed(2).replace("-", "") || "";
            $x("hz_f").innerHTML = (rd[6] / 10000).toFixed(2) || "";
            $x("hz_g").innerHTML = (rd[7] / 10000).toFixed(2).replace("-", "") || "";
            $x("hz_h").innerHTML = (rd[8] / 10000).toFixed(2) || "";
            $x("hz_i").innerHTML = (rd[9] / 10000).toFixed(2).replace("-", "") || "";
            $x("hz_j").innerHTML = (rd[10] / 10000).toFixed(2) || "";
            $x("hz_k").innerHTML = (rd[11] / 10000).toFixed(2).replace("-", "") || "";

            var zjlxcjfw = "";
            if (rd[4] != "-") { zjlxcjfw += "<li class='cdlr'><b></b>超大单(<span class='red'>" + (rd[4]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[5] != "-") { zjlxcjfw += "<li class='cdlc'><b></b>超大单(<span class='green'>" + (rd[5]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[6] != "-") { zjlxcjfw += "<li class='ddlr'><b></b>大单(<span class='red'>" + (rd[6]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[7] != "-") { zjlxcjfw += "<li class='ddlc'><b></b>大单(<span class='green'>" + (rd[7]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[8] != "-") { zjlxcjfw += "<li class='zdlr'><b></b>中单(<span class='red'>" + (rd[8]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[9] != "-") { zjlxcjfw += "<li class='zdlc'><b></b>中单(<span class='green'>" + (rd[9]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[10] != "-") { zjlxcjfw += "<li class='xdlr'><b></b>小单(<span class='red'>" + (rd[10]/10000).toFixed(2) + "</span>万元)</li>"; }
            if (rd[11] != "-") { zjlxcjfw += "<li class='xdlc'><b></b>小单(<span class='green'>" + (rd[11]/10000).toFixed(2) + "</span>万元)</li>"; }
            /*$x("zjlxcjfbt").innerHTML = zjlxcjfw;

            if(rd[12]!="-"){$x("zjlxa").innerHTML = rd[12] + "万元"; $x("zjlxb").innerHTML = rd[17]; $x("zjlxa").style.color = udc(rd[12], "0"); $x("zjlxb").style.color = udc(rd[17], "0");}
            if(rd[13]!="-"){$x("zjlxc").innerHTML = rd[13] + "万元"; $x("zjlxd").innerHTML = rd[18]; $x("zjlxc").style.color = udc(rd[13], "0"); $x("zjlxd").style.color = udc(rd[18], "0");}
            if(rd[14]!="-"){$x("zjlxe").innerHTML = rd[14] + "万元"; $x("zjlxf").innerHTML = rd[19]; $x("zjlxe").style.color = udc(rd[14], "0"); $x("zjlxf").style.color = udc(rd[19], "0");}
            if(rd[15]!="-"){$x("zjlxg").innerHTML = rd[15] + "万元"; $x("zjlxh").innerHTML = rd[20]; $x("zjlxg").style.color = udc(rd[15], "0"); $x("zjlxh").style.color = udc(rd[20], "0");}
            if (rd[16] != "-") { $x("zjlxi").innerHTML = rd[16] + "万元"; $x("zjlxj").innerHTML = rd[21]; $x("zjlxi").style.color = udc(rd[16], "0"); $x("zjlxj").style.color = udc(rd[21], "0"); }*/

            var total = Math.abs(parseFloat(rd[13])) + Math.abs(parseFloat(rd[14])) + Math.abs(parseFloat(rd[15])) + Math.abs(parseFloat(rd[16]));
            //资金流柱状图
            $(".picNum ul").each(function(index, elm) {
                if (rd[13 + index] > 0) {
                    $(this).find("div").eq(0).html("<div class=\"box\" id=\"hz_" + index + "h\"></div><span class=\"red\">" + parseFloat(rd[13 + index]).toFixed(0) + "</span>");
                    $(this).find("div").eq(0).css("border-bottom", "1px solid #ccc");
                }
                if (rd[13 + index] < 0) {
                    $(this).find("div").eq(1).html("<div class=\"box\" id=\"hz_" + index + "h\"></div><span class=\"green\">" + parseFloat(rd[13 + index]).toFixed(0) + "</span>");
                    $(this).find("div").eq(1).css("border-top", "1px solid #ccc");
                }

                $x("hz_" + index + "h").style.height = Math.abs(parseFloat(rd[13 + index])) / total * 36 + "px";
            });

            //$x("zjlxupta").innerHTML = "更新时间 " + rd[22];
            //$x("zjlxuptb").innerHTML = "更新时间 " + rd[22];
        }
    });
}

function showMore(tp, obj) {
    var over = $x("xh" + tp + obj).style.display = "block";
}
function hideall(tp, obj) {
    var over = $x("xh" + tp + obj).style.display = "none";
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

function fmtdig(Data, Mat, F, Unit, AutoF) {
    var res = Data;
    if (Data != "" && Data != "--" && Data != "-") {
        var _temp = parseFloat(Data);
        if (AutoF) {
            if (_temp > 1000000000000)//万亿
            {
                Mat = 100000000; Unit = "亿"; F = "0";
            }
            else if (_temp > 100000000000)//千亿
            {
                Mat = 100000000; Unit = "亿"; F = "0";
            }
            else if (_temp > 10000000000)//百亿
            {
                Mat = 100000000; Unit = "亿"; F = "1";
            }
            else if (_temp > 1000000000)//十亿
            {
                Mat = 100000000; Unit = "亿"; F = "2";
            }
            else if (_temp > 100000000)//亿
            {
                Mat = 100000000; Unit = "亿"; F = "2";
            }
            else if (_temp > 10000000)//千万
            {
                Mat = 10000; Unit = "万"; F = "0";
            }
            else if (_temp > 1000000)//百万
            {
                Mat = 10000; Unit = "万"; F = "1";
            }
            else if (_temp > 100000)//十万
            {
                Mat = 10000; Unit = "万"; F = "2";
            }
            else if (_temp > 10000) {
                Mat = 10000; Unit = "万"; F = "2";
            }
            else if (_temp > 1000) {
                Mat = 1; Unit = ""; F = "2";
            }
            else if (_temp > 100) {
                Mat = 1; Unit = ""; F = "2";
            }
            else if (_temp > 10) {
                Mat = 1; Unit = ""; F = "2";
            }
            else {
                Mat = 1; Unit = ""; F = "3";
            }
        }
        res = ForDight((_temp / Mat), F);
    }
    return res + Unit;
}

//时间随机数
function formatm() {
    var now = new Date();
    return now.getDate() + "" + now.getHours() + "" + now.getMinutes() + "";
}

//随机数
function GetRandomNum(Min, Max) { var Range = Max - Min; var Rand = Math.random(); return (Min + Math.round(Rand * Range)); }

function showimg() {
    $x("flash_box").style.display = "none";
    $x("image_box").style.display = "block"; window.zxgIsFirst = true; window.quoteIsFirst = true; Def.DisQuote();
    //$x("ov3").className = "emhqbov3 mb10";
    $x("flsrmt7").className = "title1 mt6";
    WriteCookie("em_hq_fls", "old", 99999);
    _this.GetFavorList(_this._Code);
}
function showfls() {
    $x("flash_box").style.display = "block";
    $x("image_box").style.display = "none"; window.zxgIsFirst = true; window.quoteIsFirst = true; Def.DisQuote();
    //$x("ov3").className = "emhqbov3 mb9";
    $x("flsrmt7").className = "title1 mt13";
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
    _this.GetFavorList(_this._Code);
}

function smivckNew(code, znum, dnum) {
    var pi = "";
    var islow = $('input:radio[name="hqvote"]:checked').val();
    if (!islow) { alert("请先选择投票方向！"); return; };
    if (GetCookie("pi")) { pi = GetCookie("pi"); }
    var url = "http://hqstat.eastmoney.com/vote/QuoteGuBaLookUpOrDown.aspx?code=" + code + "&islow=" + islow + "&pi=" + pi + "&cb=var%20res=[{0}]&&num=1&rt=" + formatm();
    Mini.Loader.load(url, "gb2312", function () {
        alert(res[0].me);
        if (res[0].rc == 1) {
            switch (islow) { case "false": znum++; break; case "true": dnum++; break; }
            zdpc = znum + dnum;
            zhang = (znum / zdpc * 100).toFixed(1);
            die = (dnum / zdpc * 100).toFixed(1);
            $x("ivap").innerHTML = zhang + "%";
            $x("ivbp").innerHTML = die + "%";
            $x("ivra").style.width = (zhang / 110 * 100).toFixed(1) + "px";
            $x("ivrb").style.width = (die / 110 * 100).toFixed(1) + "px";
            //$x("kzps").innerHTML = znum;
            //$x("kdps").innerHTML = dnum;
            $x("ivbv").innerHTML = '<span style="color: #A1A1A1;background-color:#E4E4E4;display: inline-block;height: 20px;line-height: 20px;padding: 0 6px;border: 0 none;text-align: center;">已投票</span>';
        } else if (res[0].rc == 0) {
            $x("ivbv").innerHTML = '<span style="color: #A1A1A1;background-color:#E4E4E4;display: inline-block;height: 20px;line-height: 20px;padding: 0 6px;border: 0 none;text-align: center;">已投票</span>';
        }
    });
}

function smivck(code, islow, znum, dnum, el) {
    var pi = "";
    if (GetCookie("pi")) { pi = GetCookie("pi"); }
    var url = "http://hqstat.eastmoney.com/vote/QuoteGuBaLookUpOrDown.aspx?code=" + code + "&islow=" + islow + "&pi=" + pi + "&cb=var%20res=[{0}]&&num=1&rt=" + formatm();
    Mini.Loader.load(url, "gb2312", function () {
        alert(res[0].me);
        if (res[0].rc == 1) {
            switch (islow) { case "false": znum++; break; case "true": dnum++; break; }
            zdpc = znum + dnum;
            zhang = (znum / zdpc * 100).toFixed(1);
            die = (dnum / zdpc * 100).toFixed(1);
            $x("ivap").innerHTML = zhang + "%";
            $x("ivbp").innerHTML = die + "%";
            $x("ivra").style.width = (zhang / 110 * 100).toFixed(1) + "px";
            $x("ivrb").style.width = (die / 110 * 100).toFixed(1) + "px";
        }
    });
}

function getdomain(min, max) {
    var min = 1; var max = 10;
    var res = "nufm3.dfcfw.com"; var m2 = "nufm2.dfcfw.com";
    var rom = GetRandomNum(min, max);
    if (rom != "1" && rom != "2" && rom != "3") { res = m2; }//80% ->nufm2
    //if (rom == "1") { res = m2; }//10% ->nufm2
    return res;
}