!function(t) {
    function e(n) {
        if (o[n])
            return o[n].exports;
        var i = o[n] = {
            exports: {},
            id: n,
            loaded: !1
        };
        return t[n].call(i.exports, i, i.exports, e),
        i.loaded = !0,
        i.exports
    }
    var o = {};
    return e.m = t,
    e.c = o,
    e.p = "",
    e(0)
}([function(t, e, o) {
    o(1),
    t.exports = o(17)
}
, function(t, e, o) {
    !function() {
        var t = o(2);
        /*if (null == t.get("ct") || null == t.get("ut") || null == t.get("uidal"))
            return window.location.href = "http://quote.eastmoney.com/favor/default.html",
                !1;*/
        "function" != typeof Array.prototype.indexOf && (Array.prototype.indexOf = function(t, e) {
            var o = -1;
            e = 1 * e || 0;
            for (var n = 0, i = this.length; i > n; n++)
                if (n >= e && this[n] === t) {
                    o = n;
                    break
                }
            return o
        }
        ),
        o(3);
        var e = o(9);
        e.container = $("#header-quote"),
        e.show(),
        setInterval(function() {
            e.show()
        }, 15e3);
        var n = o(12);
        $("#howTimeFly").length > 0 && setInterval(function() {
            $("#howTimeFly").html(n.getLocalString(new Date))
        }, 500);
        var i = !!window.ActiveXObject && !window.XMLHttpRequest;
        if (i) {
            var a = o(13);
            a.container = $(".dropdownli"),
            a.init()
        }
        var s = o(14);
        s.init(function() {
            $("#hdinfo_1").html(s.selectStockCount),
            $("#hdinfo_2").html(s.userFollowingCount),
            $("#hdinfo_3").html(s.fansCount),
            "" == s.intro ? $("#user_intro").html('<span style="color:gray;">暂未设置个性签名</span>') : $("#user_intro").text(s.intro);
            var t = s.user_influ_level / 2;
            t = t.toString().replace(/\./, ""),
            $("#stars").addClass("stars" + t)
        });
        var l = o(16);
        l.init(function() {
            var t = o(8);
            $("#hdico-name").text(l.name),
            $("#header-uid").html(t.TxtLeft(l.name, 8)).attr("title", l.name),
            $("#hdico-img").attr("src", l.icon)
        }),
        $("#logoutlink").click(function() {
            var t = new Date;
            return document.cookie = "pi=;path=/;domain=eastmoney.com;expires=" + t.toGMTString(),
            document.cookie = "ct=;path=/;domain=eastmoney.com;expires=" + t.toGMTString(),
            document.cookie = "ut=;path=/;domain=eastmoney.com;expires=" + t.toGMTString(),
            document.cookie = "uidal=;path=/;domain=eastmoney.com;expires=" + t.toGMTString(),
            window.location.href = "http://quote.eastmoney.com/favor/default.html",
            !1
        }),
        $(".node-section dt a").click(function() {
            var t = $(this).parents(".node-section");
            t.hasClass("js-section-open") ? t.removeClass("js-section-open").addClass("js-section-close") : t.removeClass("js-section-close").addClass("js-section-open")
        });
        var r = !1;
        window.onscroll = function() {
            function t() {
                Browser.ie ? (l.style.filter = "alpha(opacity=" + n + ")",
                n += 10) : (l.style.opacity = n / 100,
                n++),
                100 == n && clearInterval(o)
            }
            function e() {
                Browser.ie ? (l.style.filter = "alpha(opacity=" + a + ")",
                a -= 10) : (l.style.opacity = a / 100,
                a--),
                0 == a && (l.style.display = "none",
                clearInterval(i))
            }
            var o, n, i, a, s = document.documentElement.clientHeight, l = document.getElementById("backtop"), c = document.documentElement.scrollTop || document.body.scrollTop;
            r || c > s / 3 && (r = !0,
            l.style.display = "block",
            o = setInterval(t, 1),
            n = 0),
            r && s / 3 > c && (r = !1,
            i = setInterval(e, 1),
            a = 100)
        }
        ,
        $("#header_alarm_price").click(function() {
            $("#sider_alarm_price").click()
        }),
        $("#header_alarm_notice").click(function() {
            $("#sider_alarm_notice").click()
        }),
        $("#header_alarm_report").click(function() {
            $("#sider_alarm_report").click()
        }),
        $("#header_alarm_data").click(function() {
            $("#sider_alarm_data").click()
        }),
        $("#left_alarm_price").click(function() {
            $("#sider_alarm_price").click()
        }),
        $("#left_alarm_notice").click(function() {
            $("#sider_alarm_notice").click()
        }),
        $("#left_alarm_report").click(function() {
            $("#sider_alarm_report").click()
        }),
        $("#left_alarm_data").click(function() {
            $("#sider_alarm_data").click()
        }),
        setTimeout(function() {
            showTiXing(!1)
        }, 1e3)
    }()
}
, function(t) {
    var e = {
        get: function(t) {
            var e = document.cookie.match(new RegExp("(^| )" + t + "=([^;]*)(;|$)"));
            return null != e ? decodeURIComponent(e[2]) : null
        },
        set: function(t, e, o) {
            var n = encodeURIComponent(t) + "=" + encodeURIComponent(e) + ";"
              , i = new Date;
            i.setDate(i.getDate() + o),
            n += "expires=" + i.toGMTString() + ";path=/;",
            document.cookie = n
        },
        del: function(t) {
            var e = this.get(t)
              , o = new Date;
            o.setDate(o.getDate() - 1),
            e && "" != e && (document.cookie = encodeURIComponent(t) + "=" + escape(e) + ";path=/;expires=" + o.toGMTString())
        }
    };
    t.exports = e
}
, function(t, e, o) {
    function n() {
        function t(t) {
            $.ajax({
                type: "GET",
                url: "http://suggest.eastmoney.com/SuggestData/Default.aspx?name=ss&input=" + escape(t) + "&type=1,2",
                scriptCharset: "gb2312",
                dataType: "script",
                success: function() {
                    for (var e = n(ss), o = '<li class="title">股吧</li>', i = 0; i < e.length && "" != e[i] && (o += '<li class="re" data-dtype="gb" data-code="' + e[i][1] + '"><em>' + e[i][4] + "</em>吧</li>",
                    4 != i); i++)
                        ;
                    o += '<li class="title">行情</li>';
                    for (var i = 0; i < e.length && "" != e[i]; i++) {
                        var s = "";
                        if (s = 1 == e[i][5] ? "sh" : "sz",
                        o += '<li class="re" data-dtype="hq" data-code="' + s + e[i][1] + '"><em>' + e[i][4] + "</em>（" + e[i][0] + "）</li>",
                        4 == i)
                            break
                    }
                    o += '<li class="bot" data-dtype="tl" data-code="' + t + '">与<em>' + a.TxtLeft(t, 14) + '</em>相关的讨论</li><li class="bot" data-dtype="yh" data-code="' + t + '">与<em>' + a.TxtLeft(t, 14) + "</em>相关的用户</li>",
                    $("#topnavsearchre").show(),
                    $("#topnavsearchre ul").html(o),
                    0 == e.length ? $("#topnavsearchre li.title").hide() : $("#topnavsearchre li.title").show()
                }
            })
        }
        function e(t) {
            if (0 == $("#topnavsearchre li.over").length)
                1 == t ? $("#topnavsearchre li:not(.title):first").addClass("over") : $("#topnavsearchre li:not(.title):last").addClass("over");
            else {
                var e = $("#topnavsearchre li:not(.title)")
                  , o = $("#topnavsearchre li.over").index("#topnavsearchre li:not(.title)")
                  , n = o + t;
                n == e.length && (n = 0),
                e.eq(o).removeClass("over"),
                e.eq(n).addClass("over")
            }
        }
        function o(t, e) {
            switch (t) {
            case "gb":
                $("#topnavskey").removeAttr("name"),
                s.attr("action", "http://guba.eastmoney.com/list," + e + ".html");
                break;
            case "hq":
                $("#topnavskey").removeAttr("name"),
                s.attr("action", "http://quote.eastmoney.com/" + e + ".html");
                break;
            case "tl":
                $("#topnavskey").attr("name", "t").val(e),
                s.attr("action", "http://guba.eastmoney.com/search.aspx?t=" + e);
                break;
            case "yh":
                $("#topnavskey").attr("name", "unn").val(e),
                s.attr("action", "http://guba.eastmoney.com/search.aspx?unn=" + e);
                break;
            default:
                return !1
            }
        }
        function n(t) {
            if ("" == t)
                return [];
            for (var e = t.split(";"), o = 0; o < e.length; o++)
                "" != e[o] && (e[o] = e[o].split(","));
            return e
        }
        if ($("#topnavsearch").length <= 0)
            return !1;
        $("#topnavsearch").submit(function() {});
        var i = $("#topnavskey")
          , s = $("#topnavsearch");
        $("#topnavskey").attr("name"),
        i.bind("input propertychange", function() {
            var e = $(this).val();
            "" != e && e != i.attr("placeholder") ? t(e) : $("#topnavsearchre").hide()
        }),
        i.focus(function() {
            var e = $(this).val();
            "" != e && e != i.attr("placeholder") ? t(e) : $("#topnavsearchre").hide()
        }),
        $(document).on("keydown", "#topnavskey", function(t) {
            if (40 == t.which)
                e(1);
            else if (38 == t.which)
                e(-1);
            else if (13 == t.which) {
                if ($("#topnavsearchre li.over").length > 0) {
                    var n = $("#topnavsearchre li.over").eq(0);
                    o(n.data("dtype"), n.data("code"))
                }
            } else
                s.attr("action", "")
        }),
        $(document).on("mouseover", "#topnavsearchre li:not(.title)", function() {
            $(this).addClass("over")
        }),
        $(document).on("mouseleave", "#topnavsearchre li:not(.title)", function() {
            $(this).removeClass("over")
        }),
        $(document).on("click", "#topnavsearchre li:not(.title)", function() {
            o($(this).data("dtype"), $(this).data("code")),
            s.submit()
        }),
        s.submit(function() {
            return "" == i.val() || "搜索 股票/讨论/用户" == i.val() ? !1 : "" == s.attr("action") ? ($("#topnavsearchre li.re").length > 0 ? $("#topnavsearchre li.re:first").click() : $("#topnavsearchre li.bot:first").click(),
            !1) : void 0
        }),
        $(document).click(function(t) {
            $(t.target).parents().is("#topnavsearchre") || $(t.target).is("#topnavskey") || $("#topnavsearchre").hide()
        })
    }
    function i() {
        if ($("#gbhscform").length <= 0)
            return !1;
        var t = null
          , e = $("#gbhsckey").val()
          , o = {
            text: "请输入个股代码或吧名关键字",
            autoSubmit: !1,
            width: 235,
            type: "STOCK",
            header: ["选项", "代码", "名称", "类型"],
            body: [-1, 1, 4, -2],
            callback: function(t) {
                if (0 == $("#gbhsctab li.on").index())
                    switch (t.type) {
                    case "1":
                        window.open("/list," + t.code + ".html");
                        break;
                    case "2":
                        window.open("/list," + t.code + ".html");
                        break;
                    case "11":
                        window.open("/list,of" + t.code + ".html");
                        break;
                    case "21":
                        window.open("/list,hk" + t.code + ".html");
                        break;
                    case "31":
                        window.open("/list,us" + t.code + ".html");
                        break;
                    default:
                        $("#gbhscform").submit()
                    }
                else
                    $("#gbhscform").submit()
            }
        };
        t = new StockSuggest("gbhsckey",o),
        "" != e && $("#gbhsckey").val(e),
        $("#gbhsctab li").click(function() {
            $("#gbhsctab li").removeClass("on"),
            $(this).addClass("on");
            var e = $(this).index()
              , o = $("#gbhsckey")
              , n = o.val();
            switch (e) {
            case 0:
                t.text = "请输入个股代码或吧名关键字";
                break;
            case 1:
                t.text = "请输入关键字";
                break;
            case 2:
                t.text = "请输入作者名称";
                break;
            default:
                t.text = "请输入个股代码或吧名关键字"
            }
            if ("" == n || "请输入个股代码或吧名关键字" == n || "请输入关键字" == n || "请输入作者名称" == n)
                switch (e) {
                case 0:
                    o.val("请输入个股代码或吧名关键字");
                    break;
                case 1:
                    o.val("请输入关键字");
                    break;
                case 2:
                    o.val("请输入作者名称");
                    break;
                default:
                    o.val("请输入个股代码或吧名关键字")
                }
        }),
        "undefined" != typeof searchtype && ($("#gbhsctab li").removeClass("on"),
        "2" == searchtype ? $("#gbhsctab li").eq(2).addClass("on") : "3" == searchtype ? $("#gbhsctab li").eq(0).addClass("on") : $("#gbhsctab li").eq(1).addClass("on")),
        $("#gbhscform").submit(function() {
            var t = $("#gbhsckey").val();
            if ("" == t || "请输入个股代码或吧名关键字" == t || "请输入关键字" == t || "请输入作者名称" == t)
                return $("#gbhsckey").textBlink({
                    color: ["#FFDDDD", "#FFEEEE", "#fff"],
                    blinktime: 150
                }),
                !1;
            t = escape(t);
            var e = a.textContain(location.pathname, "search");
            switch ($("#gbhsctab li.on").index()) {
            case 0:
                e ? self.location = "/search_bar.aspx?search_content=" + t : window.open("/search_bar.aspx?search_content=" + t);
                break;
            case 1:
                e ? self.location = "/search.aspx?t=" + t : window.open("/search.aspx?t=" + t);
                break;
            case 2:
                e ? self.location = "/search.aspx?unn=" + t : window.open("/search.aspx?unn=" + t)
            }
            return !1
        }),
        $("#gbhsckey").keyup(function() {
            var t = $(this).val();
            "" == t || "请输入个股代码或吧名关键字" == t || "请输入关键字" == t || "请输入作者名称" == t ? $(this).css("color", "#6D6D6D") : $(this).css("color", "#181818")
        })
    }
    o(4);
    var a = o(8);
    n(),
    i()
}
, function() {}
, , , , function(t) {
    var e = {
        showface: function(t) {
            var e = ["微笑", "大笑", "鼓掌", "不说了", "为什么", "哭", "不屑", "怒", "滴汗", "拜神", "胜利", "亏大了", "赚大了", "牛", "俏皮", "傲", "好困惑", "兴奋", "赞", "不赞", "摊手", "好逊", "失望", "加油", "困顿", "想一下", "围观", "献花", "大便", "爱心", "心碎", "毛估估", "成交", "财力", "护城河", "复盘", "买入", "卖出", "满仓", "空仓", "抄底", "看多", "看空", "加仓", "减仓"]
              , o = new RegExp("\\[.+?\\]","ig");
            return t = t.replace(o, function(t) {
                for (var o = 0; o < e.length; o++)
                    if ("[" + e[o] + "]" == t)
                        return 9 > o ? '<img title="' + e[o] + '" src="http://gbres.dfcfw.com/face/emot/emot0' + (o + 1) + '.png" alt="' + t + '">' : '<img title="' + e[o] + '" src="http://gbres.dfcfw.com/face/emot/emot' + (o + 1) + '.png" alt="' + t + '">';
                return t
            })
        },
        TxtLeft: function(t, e) {
            if (null == t || "" == t)
                return "";
            for (var o = 0, n = 0, i = 0; i < t.length; i++)
                if (t.charCodeAt(i) > 255 ? o += 2 : o++,
                n++,
                o >= e)
                    return t.substring(0, n) + "...";
            return t
        },
        textContain: function(t, e) {
            return t.indexOf(e) >= 0 ? !0 : !1
        }
    };
    t.exports = e
}
, function(t, e, o) {
    o(10);
    var n = "http://nufm3.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012&sty=NCSFD&st=z&sr=&p=&ps=&cb=?&js=&token=5c46f660fab8722944521b8807de07c0"
      , i = {
        container: null,
        show: function() {
            var t = this;
            t.getdata(function(e) {
                var o = e[0].split(",")
                  , n = e[1].split(",")
                  , i = t.formatdata(o, n);
                t.container.addClass("header_quote").html(i)
            })
        },
        dateurl: n,
        data: null,
        getdata: function(t) {
            $.ajax({
                type: "GET",
                url: this.dateurl,
                dataType: "jsonp",
                success: function(e) {
                    t && t(e)
                },
                error: function() {
                    console.info("头部行情加载失败")
                }
            })
        },
        formatdata: function(t, e) {
            if (0 == t.length || 0 == e.length)
                return "";
            var o = t[7].split("|")
              , n = e[8].split("|");
            t[4] = "-" == t[4] ? "--" : (t[4] / 1e8).toFixed(2),
            e[4] = "-" == e[4] ? "--" : (e[4] / 1e8).toFixed(2);
            var i = "";
            return i += '<a href="'+portalurl+'stockeast/zsdetail?code=000001" target="_self">上证</a>:',
            i += "<span ><b>" + t[3] + "</b></span> ",
            t[5] > 0 ? (i += "<span class='up'><b>" + t[5] + "</b></span>",
            i += "<span class='up'>&uarr;<b>" + t[6] + "</b></span>") : 0 == t[5] ? (i += "<span><b>" + t[5] + "</b></span>",
            i += "<span><b>" + t[6] + "</b></span>") : (i += "<span class='down'><b>" + t[5] + "</b></span>",
            i += "<span class='down'>&darr;<b>" + t[6] + "</b></span>"),
            i += " <span><b>" + t[4] + "</b></span>亿元(涨:<a href='http://quote.eastmoney.com/center/list.html#10_0_0_u?sortType=C&sortRule=-1' target='_blank' class='up'><b>" + o[0] + "</b></a> 平:<span><b>" + o[1] + "</b></span> 跌:<a href='http://quote.eastmoney.com/center/list.html#10_0_0_d?sortType=C&sortRule=1' target='_blank' class='down'><b>" + o[2] + "</b></a>)&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp",
            i += ' <a href="'+portalurl+'stockeast/zsdetail?code=399001" target="_self">深证</a>:',
            i += "<span><b>" + e[3] + "</b></span> ",
            e[5] > 0 ? (i += "<span class='up'><b>" + e[5] + "</b></span>",
            i += "<span class='up'>&uarr;<b>" + e[6] + "</b></span>") : 0 == e[5] ? (i += "<span><b>" + e[5] + "</b></span>",
            i += "<span><b>" + e[6] + "</b></span>") : (i += "<span class='down'><b>" + e[5] + "</b></span>",
            i += "<span class='down'>&darr;<b>" + e[6] + "</b></span>"),
            i += " <span><b>" + e[4] + "</b></span>亿元(涨:<a href='http://quote.eastmoney.com/center/list.html#20_0_0_u?sortType=C&sortRule=-1' target='_blank' class='up'><b>" + n[0] + "</b></a> 平:<span><b>" + n[1] + "</b></span> 跌:<a href='http://quote.eastmoney.com/center/list.html#20_0_0_d?sortType=C&sortRule=1' target='_blank' class='down'><b>" + n[2] + "</b></a>)",
            i += "<hr>"
        }
    };
    t.exports = i
}
, function() {}
, , function(t) {
    var e = {};
    e.dateFormat = function(t, e) {
        void 0 === e && (e = t,
        t = new Date);
        var o = {
            M: t.getMonth() + 1,
            d: t.getDate(),
            h: t.getHours(),
            m: t.getMinutes(),
            s: t.getSeconds(),
            q: Math.floor((t.getMonth() + 3) / 3),
            S: t.getMilliseconds()
        };
        return e = e.replace(/([yMdhmsqS])+/g, function(e, n) {
            var i = o[n];
            return void 0 !== i ? (e.length > 1 && (i = "0" + i,
            i = i.substr(i.length - 2)),
            i) : "y" === n ? (t.getFullYear() + "").substr(4 - e.length) : e
        })
    }
    ,
    e.getLocalString = function(t) {
        function e(t) {
            return 10 > t ? "0" + t : t
        }
        var o = t
          , n = ["日", "一", "二", "三", "四", "五", "六"]
          , i = o.getFullYear() + "年" + (o.getMonth() + 1) + "月" + o.getDate() + "日 星期" + n[o.getDay()] + " " + e(o.getHours()) + ":" + e(o.getMinutes()) + ":" + e(o.getSeconds());
        return i
    }
    ,
    e.getServerTime = function() {
        new Date;
        return new Date((new Date).getTime() - e.servertimespan)
    }
    ,
    e.servertimespan = 0,
    e.getRemoteTime = function(t) {
        $.ajax({
            type: "GET",
            url: "http://quote.eastmoney.com/timezone.js",
            dataType: "script",
            success: function() {
                t && (e.servertimespan = (new Date).getTime() - 1e3 * bjTime,
                t(new Date(1e3 * bjTime)))
            },
            error: function() {
                t && (e.servertimespan = 0,
                t(new Date))
            }
        })
    }
    ,
    e.isDealTime = function(t) {
        return 6 == t.getDay() || 0 == t.getDay() ? !1 : t.getHours() < 9 || t.getHours() >= 16 ? !1 : !0
    }
    ,
    e.nowIsDealTime = function() {
        var t = this.getServerTime();
        return this.isDealTime(t)
    }
    ,
    e.getRemoteTime(),
    t.exports = e
}
, function(t) {
    var e = {
        container: null,
        init: function() {
            var t = this;
            t.container.on("mouseover", function() {
                $(this).find(".dropdownul").show()
            }).on("mouseout", function() {
                $(this).find(".dropdownul").hide()
            })
        }
    };
    t.exports = e
}
, function(t, e, o) {
    var n = o(15)
      , i = {
        init: function(t) {
            var e = this;
            $.ajax({
                type: "GET",
                url: n.userinfo,
                dataType: "jsonp",
                success: function(o) {
                    t && (e.id = o.user_id,
                    e.name = o.user_nickname,
                    e.icon = "http://gbres.dfcfw.com/qface/" + o.user_id + "/120",
                    e.influence = o.user_influ_range,
                    e.selectStockCount = o.user_select_stock_count,
                    e.userFollowingCount = o.user_following_count,
                    e.fansCount = o.user_fans_count,
                    e.intro = o.user_introduction,
                    e.user_influ_level = o.user_influ_level,
                    t())
                },
                error: function() {
                    try {
                        console.info("个人信息加载失败")
                    } catch (t) {}
                }
            })
        }
    };
    t.exports = i
}
, function(t) {
    var e = {
        domain: "http://gubawebapi.eastmoney.com/v3",
        version: "version=100&product=UserCenter&plat=Jsonp",
        init: function() {
            this.articlelist = this.domain + "/read/Custom/Jsonp/FollowGubaPostList.aspx?" + this.version,
            this.addtopicpraise = this.domain + "/write/Article/Like/LikeArticle.aspx?" + this.version,
            this.deltopicpraise = this.domain + "/write/Article/Like/CancelLikePost.aspx?" + this.version,
            this.addtopiccollect = this.domain + "/write/Article/Collect/CollectArticle.aspx?" + this.version,
            this.deltopiccollect = this.domain + "/write/Article/Collect/CancelCollectArticle.aspx?" + this.version,
            this.userinfo = this.domain + "/read/custom/jsonp/UserInfo.aspx?" + this.version,
            this.replylist = this.domain + "/read/Article/Reply/MainPostReplyList.aspx?" + this.version,
            this.sendreply = "/gubaapi/reply.aspx",
            this.newpost = "/gubaapi/post.aspx"
        }
    };
    e.init(),
    t.exports = e
}
, function(t, e, o) {
    var n = o(2)
      , i = {
        id: null,
        name: null,
        init: function(t) {
            var e = this;
            if (t && n.get("uidal")) {
                var o = n.get("uidal");
                o.length > 0 && (e.id = o.substring(0, 16),
                e.name = o.substring(16),
                e.icon = "http://avator.eastmoney.com/qface/" + o.substring(0, 16) + "/120",
                t())
            }
        }
    };
    t.exports = i
}
, function(t, e, o) {
    var n = o(18);
    !function() {
        function t(t) {
            return document.getElementById(t)
        }
        function e(t) {
            return document.createElement(t)
        }
        function s(t) {
            var e = t.substring(0, 1)
              , o = t.substring(0, 3);
            return "5" == e || "6" == e || "9" == e ? "01" : "009" == o || "126" == o || "110" == o || "201" == o || "202" == o || "203" == o || "204" == o ? "01" : "02"
        }
        var l = o(2);
        /*if (null == l.get("ct") || null == l.get("ut") || null == l.get("uidal"))
            return !1;*/
        var r = o(19)
          , c = o(28);
        c.container = $("#info-wrap");
        var d = o(49);
        !function() {
            window.onworker = function(t, e, o) {
                window.attachEvent ? t.attachEvent("on" + e, o) : "mouseenter" != e && "mouseleave" != e ? t.addEventListener(e, o) : (e = "mouseenter" == e ? "mouseover" : "mouseout",
                t["_E" + e] = function(e) {
                    this.contains(e.relatedTarget) || o.call(t)
                }
                ,
                t.addEventListener(e, t["_E" + e]))
            }
            ,
            window.reworker = function(t, e, o) {
                window.detachEvent ? t.detachEvent("on" + e, o) : "mouseenter" != e && "mouseleave" != e ? t.removeEventListener(e, o) : (e = "mouseenter" == e ? "mouseover" : "mouseout",
                t["_E" + e] && t.removeEventListener(e, t["_E" + e]))
            }
        }(),
        function() {
            window.EMui = {},
            EMui.confirm = function(t, e, o) {
                var n = this
                  , i = "<div class='win-df-btns'><em class='win-sure-btn'>确定</em><em class='win-cancel-btn'>取消</em></div>";
                n.confirmWin ? (n.confirmWin.updateTitle(t.title),
                n.confirmWin.updateHtml("<div class='win-df-text'>" + t.html + "</div>" + i),
                n.sureBtn = n.confirmWin.bodyDom.getElementsByTagName("em")[0],
                n.cancelBtn = n.confirmWin.bodyDom.getElementsByTagName("em")[1]) : (n.confirmWin = new EMui.wins({
                    title: t.title,
                    html: "<div class='win-df-text'>" + t.html + "</div>" + i,
                    width: 300,
                    overlayer: !0,
                    top: 260
                }),
                n.sureBtn = n.confirmWin.bodyDom.getElementsByTagName("em")[0],
                n.cancelBtn = n.confirmWin.bodyDom.getElementsByTagName("em")[1]),
                n.confirmWin.open(),
                n.sureBtn.onclick = function() {
                    e && e(),
                    n.confirmWin.destory(),
                    n.confirmWin = null
                }
                ,
                n.cancelBtn.onclick = function() {
                    o && o(),
                    n.confirmWin.destory(),
                    n.confirmWin = null
                }
            }
            ,
            EMui.prompt = function(t, e, o) {
                var n = this
                  , i = "<div class='win-df-btns'><em class='win-sure-btn'>确定</em><em class='win-cancel-btn'>取消</em></div>";
                n.promptWin ? n.promptWin.updateTitle(t.title) : (n.promptWin = new EMui.wins({
                    title: t.title,
                    html: "<div class='win-df-text'><input type='text' style='padding: 2px; width: 180px' /> </div>" + i,
                    width: 300,
                    overlayer: !0,
                    top: 260
                }),
                n.sureBtn = n.promptWin.bodyDom.getElementsByTagName("em")[0],
                n.cancelBtn = n.promptWin.bodyDom.getElementsByTagName("em")[1],
                n.inputDom = n.promptWin.bodyDom.getElementsByTagName("input")[0]),
                n.promptWin.open(),
                n.sureBtn.onclick = function() {
                    var t = n.inputDom.value;
                    e && e(t),
                    n.inputDom.value = "",
                    n.promptWin.destory(),
                    n.promptWin = null
                }
                ,
                n.cancelBtn.onclick = function() {
                    var t = n.inputDom.value;
                    o && o(t),
                    n.inputDom.value = "",
                    n.promptWin.destory(),
                    n.promptWin = null
                }
            }
            ,
            EMui.tip = function(t, e, o) {
                o = o || 1600;
                var n = this;
                n.tipWin ? (n.tipWin.updateTitle(e),
                n.tipWin.updateHtml("<div class='win-df-text'>" + t + "</div>")) : n.tipWin = new EMui.wins({
                    title: e,
                    html: "<div class='win-df-text'>" + t + "</div>",
                    width: 240,
                    top: 260,
                    drag: !1
                }),
                n.tipWin.open(),
                setTimeout(function() {
                    n.tipWin.closed()
                }, o)
            }
        }(),
        function(t) {
            function e(t) {
                var e = document.createElement("style");
                e.type = "text/css",
                e.styleSheet ? e.styleSheet.cssText = t : e.innerHTML = t,
                document.getElementsByTagName("head")[0].appendChild(e)
            }
            t.wins = function(t) {
                function o() {
                    m.style.left = document.body.clientWidth / 2 - n.width / 2 + "px"
                }
                var n = {
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
                for (var i in t)
                    n[i] = t[i];
                n.configCss && e(n.configCss);
                var a = this.cID = parseInt(1e4 * Math.random())
                  , s = "div"
                  , l = document.createElement(s);
                if (l.className = "win-inner",
                l.style.display = "none",
                l.innerHTML = "<div id='darg" + a + "' class='win-inner-title'></div><div class='win-inner-body' id='body" + a + "'></div><div class='win-closed-btn' id='closed" + a + "'></div>",
                n.overlayer) {
                    l.style.display = "block";
                    var r = document.createElement("div");
                    r.style.cssText = "display:none;position:absolute;top:0;left:0;width:100%;height:100%;z-index:800";
                    var c = document.createElement("div");
                    c.style.cssText = "position:absolute;top:0;left:0;background:#000;opacity:0.3;filter:alpha(opacity=30);width:100%;height:100%;z-index:810",
                    r.appendChild(c),
                    r.appendChild(l),
                    r.style.height = Math.max(document.body.scrollHeight, document.documentElement.clientHeight) + "px",
                    window.onresize = function() {
                        r.style.height = Math.max(document.body.scrollHeight, document.documentElement.clientHeight) + "px"
                    }
                    ,
                    document.body.appendChild(r)
                } else
                    document.body.appendChild(l);
                var d = this
                  , u = document.getElementById("body" + a)
                  , m = l
                  , p = document.getElementById("darg" + a);
                p.innerHTML = n.title;
                var h = document.getElementById("closed" + a)
                  , f = p;
                if (this.bodyDom = u,
                m.style.width = n.width + "px",
                m.style.top = n.top + "px",
                "string" == typeof n.html && (u.innerHTML = n.html),
                o(),
                this.updateTitle = function(t) {
                    p.innerHTML = t
                }
                ,
                this.updateHtml = function(t) {
                    u.innerHTML = t
                }
                ,
                this.closed = function() {
                    r ? r.style.display = "none" : m.style.display = "none",
                    n.closeFuc && n.closeFuc instanceof Function && n.closeFuc()
                }
                ,
                this.open = function() {
                    r ? r.style.display = "block" : m.style.display = "block",
                    m.style.top = (document.documentElement.scrollTop || document.body.scrollTop) + n.top + "px"
                }
                ,
                this.destory = function() {
                    document.body.removeChild(r ? r : m)
                }
                ,
                h.onclick = function() {
                    d.closed()
                }
                ,
                n.closed || (r ? r.style.display = "block" : m.style.display = "block"),
                n.drag) {
                    var g = 0
                      , y = 0;
                    document.body.onselectstart = function() {
                        return !1
                    }
                    ;
                    var v = f.parentNode
                      , b = (document.body.clientWidth,
                    document.body.clientHeight,
                    function(t) {
                        var e = t.clientX
                          , o = t.clientY;
                        (0 >= e || 0 >= o) && (window.attachEvent ? document.body.detachEvent("onmousemove", b, !1) : document.body.removeEventListener("mousemove", b, !1)),
                        v.style.left = e - g + "px",
                        v.style.top = o - y + "px"
                    }
                    );
                    window.attachEvent ? (f.attachEvent("onmousedown", function(t) {
                        document.body.onselectstart = function() {
                            return !1
                        }
                        ,
                        g = t.clientX - parseInt(v.style.left),
                        y = t.clientY - parseInt(v.style.top),
                        document.body.attachEvent("onmousemove", b, !1)
                    }),
                    document.body.attachEvent("onmouseup", function() {
                        document.body.onselectstart = function() {
                            return !0
                        }
                        ,
                        document.body.detachEvent("onmousemove", b, !1)
                    }),
                    document.body.attachEvent("onmouseleave", function() {
                        document.body.detachEvent("onmousemove", b)
                    })) : (f.addEventListener("mousedown", function(t) {
                        document.body.onselectstart = function() {
                            return !1
                        }
                        ,
                        g = t.clientX - parseInt(v.style.left),
                        y = t.clientY - parseInt(v.style.top),
                        document.body.addEventListener("mousemove", b, !1)
                    }),
                    document.body.addEventListener("mouseup", function() {
                        document.body.onselectstart = function() {
                            return !0
                        }
                        ,
                        document.body.removeEventListener("mousemove", b, !1)
                    }))
                }
                return this
            }
        }(EMui),
        function() {
            var t = {
                create: function() {
                    return function() {
                        this.initialize.apply(this, arguments)
                    }
                }
            };
            Object.extend = function(t, e) {
                for (property in e)
                    t[property] = e[property];
                return t
            }
            ;
            var e = t.create();
            Object.extend(Function.prototype, {
                bind: function() {
                    for (var t = this, e = arguments[0], o = new Array, n = 1; n < arguments.length; n++)
                        o.push(arguments[n]);
                    return function() {
                        return t.apply(e, o)
                    }
                }
            }),
            Object.extend(e.prototype, {
                initialize: function() {},
                Browser: {
                    IE: !(!window.attachEvent || -1 !== navigator.userAgent.indexOf("Opera")),
                    Opera: navigator.userAgent.indexOf("Opera") > -1,
                    WebKit: navigator.userAgent.indexOf("AppleWebKit/") > -1,
                    Gecko: navigator.userAgent.indexOf("Gecko") > -1 && -1 === navigator.userAgent.indexOf("KHTML"),
                    MobileSafari: !!navigator.userAgent.match(/Apple.*Mobile.*Safari/)
                },
                $: function(t) {
                    return "string" == typeof t ? document.getElementById(t) : t
                },
                $Tag: function(t) {
                    return "string" == typeof t ? document.getElementsByTagName(t) : t
                },
                $C: function(t) {
                    return document.createElement(t)
                },
                $E: function(t) {
                    return tempObj = t.target ? t.target : event.srcElement,
                    tempObj
                },
                $aE: function(t, e, o, n) {
                    if (t.addEventListener)
                        return t.addEventListener(e, o, n),
                        !0;
                    if (t.attachEvent) {
                        var i = t.attachEvent("on" + e, o);
                        return i
                    }
                    t["on" + e] = o
                },
                $dE: function(t, e, o, n) {
                    if (t.removeEventListener)
                        return t.removeEventListener(e, o, n),
                        !0;
                    if (t.detachEvent) {
                        var i = t.detachEvent("on" + e, o);
                        return i
                    }
                    return void (t["on" + e] = null)
                },
                stopBubble: function(t) {
                    this.Browser.IE ? window.event.cancelBubble = !0 : t.stopPropagation()
                },
                isNullorEmpty: function(t) {
                    return null == t || "" == t || "undefined" == t ? !0 : !1
                },
                getXY: function(t) {
                    var e, o = 0, n = 0;
                    if (t.offsetParent) {
                        do
                            if (o += t.offsetLeft,
                            n += t.offsetTop,
                            "relative" == this.getStyle(t, "position")) {
                                (e = this.getStyle(t, "border-top-width")) && (n += parseInt(e)),
                                (e = this.getStyle(t, "border-left-width")) && (o += parseInt(e));
                                break
                            }
                        while (t = t.offsetParent)
                    } else
                        t.x && (o += t.x,
                        n += t.y);
                    return {
                        x: o,
                        y: n
                    }
                },
                getStyle: function(t, e) {
                    return t.currentStyle ? t.currentStyle[e] : window.getComputedStyle ? document.defaultView.getComputedStyle(t, null).getPropertyValue(e) : void 0
                }
            });
            var o = new e
              , n = t.create();
            Object.extend(n.prototype, {
                initialize: function(t, e) {
                    this.input = t,
                    this.dataurl = "http://suggest.eastmoney.com/suggest/Default.aspx?name={#NAME}&input={#KEY}&type={#TYPE}",
                    o.isNullorEmpty(e.dataurl) || (this.dataurl = e.dataurl),
                    this.autoSubmit = o.isNullorEmpty(e.autoSubmit) ? !1 : e.autoSubmit,
                    this.type = o.isNullorEmpty(e.type) ? "" : e.type,
                    this.link = o.isNullorEmpty(e.link) ? "" : e.link,
                    this.width = o.isNullorEmpty(e.width) ? "" : e.width,
                    this.opacity = o.isNullorEmpty(e.opacity) ? 1 : e.opacity,
                    this.className = o.isNullorEmpty(e.className) ? "" : e.className,
                    this.max = o.isNullorEmpty(e.max) ? 10 : e.max,
                    this.text = o.isNullorEmpty(e.text) ? "请输入..." : e.text,
                    this.header = o.isNullorEmpty(e.header) ? ["选项", "代码", "名称"] : e.header,
                    this.body = o.isNullorEmpty(e.body) ? [-1, 1, 4] : e.body,
                    this.callback = null == e.callback || "undefined" == e.callback ? null : e.callback,
                    this.showAd = null == e.showAd || "undefined" == e.showAd ? !0 : e.showAd,
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
                        51: "国债期货"
                    },
                    this.ShowType = {
                        ABSTOCK: "1,2,3",
                        CNSTOCK: "1,2,3,10,50",
                        CNFUND: "11,12,13,14,15,16",
                        HKSTOCK: "21,22",
                        USASTOCK: "31"
                    },
                    this.init()
                },
                init: function() {
                    if (this._Y = {},
                    this.input = "string" == typeof this.input ? o.$(this.input) : this.input,
                    this.input) {
                        if (null == this._F) {
                            for (var t = this.input.parentNode; "form" == t.nodeName.toLowerCase() && "body" == t.nodeName.toLowerCase(); )
                                t = t.parentNode;
                            if ("form" == t.nodeName.toLowerCase())
                                this._oForm = {
                                    action: t.action,
                                    target: t.target,
                                    method: t.method,
                                    onsubmit: t.onsubmit
                                },
                                this._F = t;
                            else {
                                this._F = o.$C("form"),
                                this._F.method = "get",
                                this.autoSubmit ? this._F.target = "_blank" : (this._F.target = "_self",
                                this._F.onsubmit = function() {
                                    return !1
                                }
                                ),
                                this.input.parentNode.insertBefore(this._F, this.input);
                                var e = this.input;
                                this.input.parentNode.removeChild(this.input),
                                this._F.appendChild(e)
                            }
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
                        o.$aE(this.input, "focus", this._iF),
                        o.$aE(this.input, "blur", this._iF),
                        o.$aE(this.input, "keyup", this._iN),
                        this.autoSubmit && o.$aE(this.input, "keydown", this._iC),
                        o.$aE(this.input, "mouseup", this._iN)
                    }
                },
                dispose: function() {
                    this._Y = {},
                    this.input = "string" == typeof this.input ? o.$(this.input) : this.input,
                    this.input && (null != this._oForm && (this._F.action = this._oForm.action,
                    this._F.target = this._oForm.target,
                    this._F.method = this._oForm.method,
                    this._F.onsubmit = this._oForm.onsubmit),
                    o.$dE(this.input, "focus", this._iF),
                    o.$dE(this.input, "blur", this._iF),
                    o.$dE(this.input, "keyup", this._iN),
                    this.autoSubmit && o.$dE(this.input, "keydown", this._iC),
                    o.$dE(this.input, "mouseup", this._iN))
                },
                GetShowType: function() {
                    return "" == this.type ? "" : this.ShowType[this.type]
                },
                inputFocus: function(t) {
                    var e = t.type;
                    this.input.value == this.text && e.indexOf("focus") >= 0 ? (this.input.value = "",
                    this._U = "",
                    this.Suggest()) : "" == this.input.value && e.indexOf("blur") >= 0 ? (this.input.value = this.text,
                    this._U = "",
                    this.hiddenResults()) : e.indexOf("blur") >= 0 && this.hiddenResults()
                },
                nGourl: !1,
                Navigate: function(t) {
                    var e = null == this.header ? 0 : 1;
                    switch (t.keyCode) {
                    case 38:
                        this.nGourl = !1,
                        null != this.results && "" != this.results.innerHTML && this.setLine(this.results.firstChild.rows[this._W && this._W.rowIndex != e ? this._W.rowIndex - 1 : this.results.firstChild.rows.length - 2]);
                        break;
                    case 40:
                        this.nGourl = !1,
                        null != this.results && "" != this.results.innerHTML && this.setLine(this.results.firstChild.rows[this._W && this._W.rowIndex != this.results.firstChild.rows.length - 2 ? this._W.rowIndex + 1 : e]);
                        break;
                    case 13:
                        if (this.autoSubmit)
                            this.Submit(this.input, !1);
                        else {
                            if (this.nGourl = !0,
                            null != this.results && "" != this.results.innerHTML) {
                                var n = this.input.value
                                  , i = "";
                                if (null != this._W)
                                    if ("key_" + n in this._Y && "" != this._Y["key_" + n] && (i = this._Y["key_" + n].replace(/&amp;/g, "&").replace(/;$/, "").split(";")),
                                    "" != i && i.length > 0) {
                                        var a = o.$(i[0]);
                                        "undefined" != typeof a && this.setLine(a, t)
                                    } else
                                        this.setLine(this._W, t);
                                else {
                                    var s = !1;
                                    if ("key_" + n in this._Y && "" != this._Y["key_" + n] && (i = this._Y["key_" + n].replace(/&amp;/g, "&").replace(/;$/, "").split(";")),
                                    "" != i && i.length > 0) {
                                        var a = o.$(i[0]);
                                        "undefined" != typeof a && this.setLine(a, t)
                                    } else
                                        alert("您输入的股票代码不存在！"),
                                        s = !0
                                }
                                null == this.callback || s || this.callback({
                                    code: this.input.value,
                                    type: this.Market,
                                    mt: this.mType,
                                    cnName: this.SName
                                })
                            }
                            this.hiddenResults()
                        }
                        break;
                    default:
                        this.Suggest()
                    }
                },
                Confirm: function(t) {
                    if (13 == t.keyCode) {
                        if (this.nGourl = !0,
                        null != this.results && "" != this.results.innerHTML) {
                            var e = !1
                              , n = this.input.value
                              , i = "";
                            if (null != this._W)
                                if ("key_" + n in this._Y && "" != this._Y["key_" + n] && (i = this._Y["key_" + n].replace(/&amp;/g, "&").replace(/;$/, "").split(";")),
                                "" != i && i.length > 0) {
                                    var a = o.$(i[0]);
                                    this.setLine(a, t)
                                } else
                                    this.setLine(this._W, t);
                            else if ("key_" + n in this._Y && "" != this._Y["key_" + n] && (i = this._Y["key_" + n].replace(/&amp;/g, "&").replace(/;$/, "").split(";")),
                            "" != i && i.length > 0) {
                                var a = o.$(i[0]);
                                this.setLine(a, t)
                            } else
                                alert("您输入的股票代码不存在！"),
                                e = !0;
                            null == this.callback || e || this.callback({
                                code: this.input.value,
                                type: this.Market,
                                mt: this.mType
                            })
                        } else
                            alert("请输入股票代码！");
                        this.hiddenResults()
                    } else
                        this.Suggest()
                },
                _bd: function(t, e) {
                    var o = this;
                    return function() {
                        var n = null;
                        if ("undefined" != typeof e) {
                            for (var i = 0; i < arguments.length; i++)
                                e.push(arguments[i]);
                            n = e
                        } else
                            n = arguments;
                        return t.apply(o, n)
                    }
                },
                _gt: function() {
                    return (new Date).getTime()
                },
                Suggest: function() {
                    var t = this.input.value;
                    this._U != t ? (this._U = t,
                    "" != t ? "key_" + t in this._Y ? this.Tip() : this._io(t, this._bd(this.Tip), this._bd(this.hiddenResults)) : (null != this.results && "" != this.results.innerHTML && (this._W = null),
                    this.hiddenResults())) : this.setResults()
                },
                setResults: function() {
                    null != this.results && (this.results.style.display = "")
                },
                hiddenResults: function() {
                    0 == this._hidden && null != this.results && (this.results.style.display = "none")
                },
                _io: function(t, e, n) {
                    null == this._R && (this._R = o.$C("div"),
                    this._R.style.display = "none",
                    document.body.insertBefore(this._R, document.body.lastChild));
                    var i = "sData_" + this._gt()
                      , a = o.$C("script");
                    a.type = "text/javascript",
                    a.charset = "gb2312",
                    a.src = this.dataurl.replace("{#NAME}", i).replace("{#KEY}", escape(t)).replace("{#TYPE}", this.GetShowType()),
                    a._0j = this,
                    e && (a._0k = e),
                    n && (a._0l = n),
                    a._0m = t,
                    a._0n = i,
                    a[document.all ? "onreadystatechange" : "onload"] = function() {
                        if (!document.all || "loaded" == this.readyState || "complete" == this.readyState) {
                            var t = window[this._0n];
                            "undefined" != typeof t && (this._0j._Y["key_" + this._0m] = t,
                            this._0k(t),
                            window[this._0n] = null),
                            this._0j = null,
                            this._0m = null,
                            this._0n = null,
                            this[document.all ? "onreadystatechange" : "onload"] = null,
                            this.parentNode.removeChild(this)
                        }
                    }
                    ,
                    this._R.appendChild(a)
                },
                Submit: function(t, e) {
                    "undefined" == typeof e && (e = !0),
                    e && (this._D = null);
                    var o = "";
                    if (null == this._D) {
                        var n = this.input.value;
                        if (this._Y["key_" + n]) {
                            var o = this._Y["key_" + n].replace(/&amp;/g, "&").replace(/;$/, "").split(";");
                            if ("" != o && o.length > 0) {
                                var i = o[0].split(",");
                                this._D = i
                            }
                        } else
                            var o = "http://quote.eastmoney.com/"
                    }
                    if (null != this._D && "" != this._D) {
                        switch (this._D[2]) {
                        case "1":
                        case "2":
                        case "3":
                        case "5":
                        case "10":
                        case "41":
                            var a = "sh";
                            "2" == this._D[5] && (a = "sz"),
                            o = "http://quote.eastmoney.com/" + a + this._D[1] + ".html";
                            break;
                        case "4":
                            o = "http://quote.eastmoney.com/qihuo/" + this._D[1] + ".html";
                            break;
                        case "40":
                            o = "http://quote.eastmoney.com/zs" + this._D[1] + ".html";
                            break;
                        case "11":
                        case "12":
                        case "13":
                        case "14":
                        case "15":
                        case "16":
                            o = "http://fund.eastmoney.com/" + this._D[1] + ".html";
                            break;
                        case "21":
                        case "22":
                            o = "http://quote.eastmoney.com/hk/" + this._D[1] + ".html";
                            break;
                        case "31":
                        case "32":
                            o = "http://quote.eastmoney.com/us/" + this._D[1] + ".html";
                            break;
                        case "50":
                        case "51":
                            o = "http://quote.eastmoney.com/gzqh/" + this._D[1] + ".html";
                            break;
                        default:
                            o = "http://quote.eastmoney.com/" + this._D[1] + ".html"
                        }
                        if ("" != o) {
                            var s = !1
                              , l = this.input.name
                              , r = this.input.value;
                            if ("undefined" != typeof t)
                                this.input.name = "",
                                this.input.value = "",
                                13 == t.keyCode && (s = !0);
                            else {
                                var c = !isNaN(this.input.value) && 6 == this.input.value.length;
                                e && !c && (this.input.name = "stockcode",
                                o = "http://quote.eastmoney.com/search.html",
                                (this.text.indexOf(this.input.value) >= 0 || "" == this.input.value) && (this.input.name = "",
                                this.input.value = "",
                                o = "http://quote.eastmoney.com/"))
                            }
                            this.goUrl(o, "_blank", s),
                            this.input.name = l,
                            this.input.value = r
                        }
                    } else {
                        var l = this.input.name
                          , r = this.input.value;
                        this.input.name = "stockcode";
                        var d = "http://quote.eastmoney.com/search.html";
                        (this.text.indexOf(this.input.value) >= 0 || "" == this.input.value) && (this.input.name = "",
                        this.input.value = "",
                        d = "http://quote.eastmoney.com/"),
                        this.goUrl(d, "_blank", s),
                        this.input.name = l,
                        this.input.value = r
                    }
                },
                goUrl: function(t, e, o) {
                    null != this._F ? (this._F.action = t,
                    this._F.target = e,
                    this._F.method = "get",
                    this._F.onsubmit = function() {
                        return !0
                    }
                    ,
                    o || this._F.submit()) : alert("Error"),
                    this.hiddenResults()
                },
                setColor: function(t) {
                    var e = "";
                    t._0f && t._0g ? e = "#F8FBDF" : t._0f ? e = "#F1F5FC" : t._0g && (e = "#FCFEDF"),
                    t.style.backgroundColor != e && (t.style.backgroundColor = e)
                },
                setLine: function(t, e) {
                    var o = t.id.split(",");
                    this._D = o;
                    var n = o[1];
                    this._U = n,
                    this.Market = o[2],
                    this.mType = o[5],
                    this.SName = o[4],
                    this.input.value = n,
                    null != this._W && (this._W._0f = !1,
                    this.setColor(this._W)),
                    t._0f = !0,
                    this.setColor(t),
                    this._W = t,
                    this.autoSubmit && this.nGourl && this.Submit(e, !1)
                },
                mouseoverLine: function(t) {
                    t._0g = !0,
                    this.setColor(t)
                },
                mouseoutLine: function(t) {
                    t._0g = !1,
                    this.setColor(t)
                },
                setLineMouse: function(t) {
                    this.nGourl = !0,
                    this.setLine(t),
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
                    var t = 0
                      , e = 0
                      , o = this.input;
                    do
                        t += o.offsetTop || 0,
                        e += o.offsetLeft || 0,
                        o = o.offsetParent;
                    while (o);var n = [1 * this.input.parentNode.style.borderTopWidth.replace("px", ""), 1 * this.input.parentNode.style.borderLeftWidth.replace("px", "")]
                      , i = [1, 1];
                    this.results.style.top != t + "px" && (this.results.style.top = "hq_stock" == this.input.name ? "0px" : t - n[0] + i[0] + "px"),
                    this.results.style.left != e + "px" && (this.results.style.left = "hq_stock" == this.input.name ? "147px" : e - n[1] + i[1] + "px");
                    var a = this.input.style.borderTopWidth
                      , s = this.input.style.borderBottomWidth
                      , l = this.input.clientHeight;
                    l += "" != a ? 1 * a.replace("px", "") : 2,
                    l += "" != s ? 1 * s.replace("px", "") : 2,
                    this.results.style.marginTop != l + "px" && (this.results.style.marginTop = l + "px")
                },
                Tip: function() {
                    var t = this.input.value;
                    if ("key_" + t in this._Y && "" != this._Y["key_" + t]) {
                        null == this.results && (this.results = o.$C("div"),
                        this.results.id = "jj_suggest_result",
                        this.results.style.cssText = "z-index:9999;width:" + this.width + "px;opacity:" + this.opacity + ";filter:alpha(opacity:" + 100 * this.opacity + ");position:absolute;display:none;",
                        "" == this.className ? this.results.style.border = "1px solid #ccc" : this.results.className = this.className,
                        this.input.parentNode.insertBefore(this.results, this.input),
                        this.results.suggest = this),
                        this.setTip(),
                        this.results.innerHTML = "";
                        var e = o.$C("table");
                        e.border = "0",
                        e.cellPadding = "0",
                        e.cellSpacing = "0",
                        e.style.cssText = "line-height:18px;border:1px solid #FFF;background:#FFF;font-size:12px;text-align:center;color:#666;width:100%;";
                        var n = o.$C("tbody")
                          , i = o.$C("tr");
                        if (i.style.cssText = "background:#E6F4F5;height:22px;line-height:22px;overflow:hidden;",
                        null != this.header)
                            for (var a = 0; a < this.header.length; a++) {
                                var s = o.$C("th");
                                "代码" == this.header[a] && (s.width = 52),
                                "类型" == this.header[a] && (s.width = 40),
                                s.innerHTML = this.header[a],
                                i.appendChild(s)
                            }
                        n.appendChild(i);
                        for (var l = this._Y["key_" + t].replace(/&amp;/g, "&").replace(/;$/, "").split(";"), r = l.length > this.max ? this.max : l.length, a = 0; r > a; a++) {
                            var c = l[a].split(",");
                            c[-1] = c[0].replace(t.toUpperCase(), '<span style="color:#F00;">' + t.toUpperCase() + "</span>"),
                            c[-2] = c[2]in this.StockType ? this.StockType[c[2]] : "--",
                            "A 股" == c[-2] && (2 == c[5] ? c[-2] = "深市" : 1 == c[5] && (c[-2] = "沪市"));
                            var d = o.$C("tr");
                            d.id = l[a],
                            d.style.cursor = "pointer",
                            d._oj = this,
                            d.onmouseover = function() {
                                this._oj.mouseoverLine(this)
                            }
                            ,
                            d.onmouseout = function() {
                                this._oj.mouseoutLine(this)
                            }
                            ,
                            d.onmousedown = function() {
                                return this._oj.hidepause(this)
                            }
                            ,
                            d.onclick = function() {
                                this._oj.setLineMouse(this),
                                this._oj.hideresume(this)
                            }
                            ;
                            for (var u, m = 0; m < this.body.length; m++)
                                u = o.$C("td"),
                                u.style.wordBreak = "break-all",
                                u.hidefocus = "true",
                                u.style.padding = "1px",
                                u.innerHTML = c[this.body[m]],
                                d.appendChild(u);
                            u = null,
                            n.appendChild(d)
                        }
                        var p = o.$C("tr");
                        p.id = "_AutoSuggest_tip_More_";
                        var h = o.$C("td");
                        h.colSpan = this.header.length,
                        h.align = "right",
                        h.hidefocus = "true",
                        _more_link = o.$C("a"),
                        _more_link.style.cssText = "color:#C00;float:none;clear:both;background:none;border:0;",
                        _more_link.href = "http://quote.eastmoney.com/search.html?stockcode=" + escape(t),
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
                        h.appendChild(_more_link),
                        p.appendChild(h),
                        n.appendChild(p),
                        e.appendChild(n),
                        this.results.appendChild(e),
                        this.setResults()
                    } else
                        this.hiddenResults()
                }
            }),
            window.StockSuggest = n
        }(),
        function(t) {
            function e(t) {
                for (var e = 0; myStock.stockList[e]; ) {
                    if (myStock.stockList[e]._Code == t)
                        return myStock.stockList[e];
                    e++
                }
                return 0
            }
            function o(t, e) {
                setCookie(t, e, 365)
            }
            function n(t, e) {
                for (var o, n = document.getElementById("rightTable").getElementsByTagName("i"), i = 0; n[i]; ) {
                    if (n[i].getAttribute("scode") == e) {
                        o = n[i];
                        break
                    }
                    i++
                }
                o.className = t > 0 ? "note-on" : "note"
            }
            function i(t, e) {
                var o = {
                    num: 0,
                    price: 0,
                    fee: 0
                };
                for (var n in e)
                    o[n] = e[n];
                setCookieD("myStock_Buy_" + t, o.num + "," + o.price + "," + o.fee, 365)
            }
            function a(t) {
                var e = getCookie("myStock_Buy_" + t);
                return e ? e.split(",")[1] : 0
            }
            function s(t) {
                var e = getCookie("myStock_Buy_" + t);
                return e ? e.split(",")[0] : 0
            }
            function l(t) {
                var e = getCookie("myStock_Buy_" + t);
                return e ? e.split(",")[2] : 0
            }
            function r(t) {
                return isNaN(unnoteNums.value) || isNaN(unnoteFee.value) || isNaN(unnotePrice.value) ? void EMui.tip("请输入合理的数字", "操作失败") : (unnoteNums.value = parseFloat(unnoteNums.value),
                unnoteFee.value = parseFloat(unnoteFee.value),
                unnotePrice.value = parseFloat(unnotePrice.value),
                t._note = 0 == unnoteNums.value && 0 == unnoteFee.value && 0 == unnotePrice.value ? -1 : 1,
                n(t._Note, curUnNoteCode),
                t.parseCommonData(),
                i(curUnNoteCode, {
                    num: unnoteNums.value,
                    fee: unnoteFee.value,
                    price: unnotePrice.value
                }),
                void myStock.getStockInfoList(myStock.funcName))
            }
            var c = "emhq_stock"
              , d = null;
            t.getCookie = function(t) {
                var e = document.cookie;
                t += "=";
                for (var o = 0; o < e.length; ) {
                    var n = o + t.length;
                    if (e.substring(o, n) == t) {
                        var i = e.indexOf(";", n);
                        return -1 == i && (i = e.length),
                        unescape(e.substring(n, i))
                    }
                    if (o = e.indexOf(" ", o) + 1,
                    0 == o)
                        break
                }
                return null
            }
            ,
            t.setCookie = function(t, e, o) {
                var n = new Date;
                n.setDate(n.getDate() + o),
                document.cookie = t + "=" + escape(e) + ";domain=.eastmoney.com;path=/;" + (null == o ? "" : "; expires=" + n.toGMTString())
            }
            ,
            t.setCookieD = function(t, e, o) {
                var n = new Date;
                n.setDate(n.getDate() + o),
                document.cookie = t + "=" + escape(e) + ";path=/;" + (null == o ? "" : "; expires=" + n.toGMTString())
            }
            ,
            t._gzPrice = a,
            t._gzNum = s,
            t._gzFee = l,
            t.unLogFuc = {
                cAddStock: function(t) {
                    var e = getCookie(c);
                    if (e) {
                        if (e.indexOf(t.split("|")[0]) > -1)
                            return void addhandlerCallback({
                                data: {
                                    msg: "该股票已被添加"
                                },
                                result: "-1"
                            });
                        o(c, t.split("|")[0] + "," + e),
                        addhandlerCallback({
                            data: {
                                msg: "操作成功"
                            },
                            result: "1"
                        })
                    } else
                        o(c, t.split("|")[0]),
                        addhandlerCallback({
                            data: {
                                msg: "操作成功"
                            },
                            result: "1"
                        })
                },
                cDelStock: function(t) {
                    var e = []
                      , n = getCookie(c);
                    for (t = t.split(","),
                    j = 0; t[j]; )
                        e.push(t[j].split("|")[0]),
                        j++;
                    if (n) {
                        for (var i = n.split(","), a = 0, s = 0; e[s]; ) {
                            for (a = 0; i[a]; )
                                i[a] == e[s] ? i.splice(a, 1) : a++;
                            s++
                        }
                        o(c, i.join(","))
                    }
                },
                cNotePadShow: function() {
                    null == d && (d = new EMui.wins({
                        title: "交易管理",
                        html: '<div class="unnote"><div class="unnote-header"><h5 id="unnote-name">东方财富</h5><small id="unnote-code">300059</small><b id="unnote-close" class="red">12.38</b></div><table class="unnote-table" cellpadding="0" cellspacing="0"><tr><th>数量</th><th>买入价</th><th>佣金‰</th><th>印花税‰</th></tr><tr><td><input id="unnote-nums" type="text" value="0" /></td><td><input id="unnote-price" type="text" value="0" /></td><td><input id="unnote-fee" type="text" value="0" /></td><td>1</td></tr></table><div class="unnote-btns"><span id="unnote-save">保存</span><span id="unnote-cancel">取消</span></div></div>',
                        overlayer: !0,
                        width: 480
                    }),
                    t.unnoteNums = document.getElementById("unnote-nums"),
                    t.unnotePrice = document.getElementById("unnote-price"),
                    t.unnoteFee = document.getElementById("unnote-fee"),
                    t.unnoteName = document.getElementById("unnote-name"),
                    t.unnoteCode = document.getElementById("unnote-code"),
                    t.unnoteClose = document.getElementById("unnote-close"),
                    document.getElementById("unnote-cancel").onclick = function() {
                        d.closed()
                    }
                    ,
                    document.getElementById("unnote-save").onclick = function() {
                        r(e(curUnNoteCode)),
                        d.closed()
                    }
                    );
                    var o = e(curUnNoteCode);
                    unnoteName.innerHTML = o._Name,
                    unnoteCode.innerHTML = curUnNoteCode,
                    unnoteClose.innerHTML = o.Close1,
                    unnotePrice.value = a(curUnNoteCode),
                    unnoteNums.value = s(curUnNoteCode),
                    unnoteFee.value = l(curUnNoteCode),
                    d.open()
                },
                cGetNote: function() {},
                cSaveList: function() {
                    for (var t = 0, e = []; myStock.allStockArray[t]; )
                        e.push(myStock.allStockArray[t]._Code),
                        t++;
                    o(c, e.join(","))
                }
            }
        }(window),
        function(t) {
            t.TYPE = "01",
            t.defaultKey = "CTALL";
            var e = "stKred"
              , o = "stKgreen"
              , n = window.numFormatRule = {
                "default": function(t, e) {
                    return void 0 == t || t.toString().indexOf("-%") >= 0 ? "-" : (t = t.toString(),
                    t.indexOf("%") > 0 || t.indexOf("-") > 0 ? t : (t = parseFloat(t),
                    isNaN(t) ? "-" : Math.abs(t) > 1e8 ? (t / 1e8).toFixed(2) + "亿" : Math.abs(t) > 1e4 ? (t / 1e4).toFixed(2) + "万" : t.toFixed(void 0 == e ? 2 : e)))
                },
                to100: function(t) {
                    return n["default"](Math.floor(parseInt(t) / 100), 0)
                },
                toW: function(t) {
                    return t = parseFloat(t),
                    isNaN(t) ? "-" : t + "万"
                },
                toInt: function(t) {
                    return n["default"](parseInt(t), 0)
                },
                pass: function(t) {
                    return void 0 == t || isNaN(parseFloat(t) || t.indexOf("-%") >= 0) ? "-" : t
                }
            }
              , i = {
                comparePreviousCloseNew: function() {
                    return 0 < parseFloat(this.ChangePercent) ? e : 0 > parseFloat(this.ChangePercent) ? o : ""
                },
                comparePreviousClose: function(t) {
                    return t = parseFloat(t),
                    t > parseFloat(this.PreviousClose.replace(/[^-\d.]/g, "")) ? e : t < parseFloat(this.PreviousClose.replace(/[^-\d.]/g, "")) ? o : ""
                },
                zf: function(t) {
                    return t = parseFloat(t),
                    t > 0 ? e : 0 > t ? o : ""
                },
                Green: function() {
                    return o
                },
                Red: function() {
                    return e
                }
            };
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
                    name: "最新价",
                    sort: 1,
                    color: "comparePreviousCloseNew",
                    num: "pass"
                },
                ChangePercent: {
                    name: "涨跌幅",
                    sort: 1,
                    color: "zf"
                },
                Change: {
                    name: "涨跌额",
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
                    name: "最新价",
                    sort: 1,
                    color: "comparePreviousCloseNew",
                    title: "股票最新价"
                }
            };
            for (var a in allField)
                if ("Code" == a)
                    allField[a].name = "<a sortkey='_Code' href='javascript:void(0)'>" + allField[a].name + "</a>";
                else if (1 == allField[a].sort) {
                    var s = allField[a].title;
                    allField[a].name = s ? "<a title='" + s + "' sortkey='" + a + "' href='javascript:void(0)'>" + allField[a].name + "</a>" : "<a sortkey='" + a + "' href='javascript:void(0)'>" + allField[a].name + "</a>"
                }
            window.stockClass = function(t) {
                var e = this;
                e._Code = t._Code || "-",
                e.data__Code = t._Code || "-",
                e._MarketType = t._MarketType.slice(1) || "-",
                e._Type = t._Type || "-",
                e.CollectionID = t.CollectionID || "-",
                e.NoteDom = null,
                e.RemindDom = null,
                e.DelDom = null,
                e.WorkID = e._Code + "|0" + e._MarketType + "|" + e._Type,
                e._Name = "-",
                e._star = t._star || -1,
                e._note = t._note || -1,
                e._alert = t._alert || -1,
                e.LastBlankTd = '<div style="width:72px">&nbsp;</div>',
                e.update = function(t) {
                    stockClass.call(this, t)
                }
                ,
                e.parseCommonData = function() {
                    e.SuperStar = "<i workID='" + e._Code + "|0" + e._MarketType + "|" + e._Type + "' class ='" + (1 == e._star ? "star-on" : "star") + "' Scode='" + e._Code + "' Smk='" + e._MarketType + "' Sty='" + e._Type + "'></i>",
                    e.Checkbox = "<input workID='" + e._Code + "|0" + e._MarketType + "|" + e._Type + "' type='checkbox' Scode='" + e._Code + "' Smk='" + e._MarketType + "' Sty='" + e._Type + "' />",
                    e.AboutLinker = "<a target='_blank' href='http://guba.eastmoney.com/list," + e._Code + ".html'>股吧</a> <a target='_blank' href='http://data.eastmoney.com/zjlx/" + e._Code + ".html'>资金</a> <a target='_blank' href='http://data.eastmoney.com/report/" + e._Code + ".html'>研报</a>",
                    e.StockNote = "<i class='" + (1 == e._note ? "note-on" : "note") + "' Scode='" + e._Code + "' Smk='" + e._MarketType + "' Sty='" + e._Type + "'></i>",
                    e.StockAlert = "<i class='" + (1 == e._alert ? "alert-on" : "alert") + "' Scode='" + e._Code + "' Smk='" + e._MarketType + "' Sty='" + e._Type + "'></i>",
                    e.StockDel = "<i class='del' Scode='" + e._Code + "' Smk='" + e._MarketType + "' Sty='" + e._Type + "'></i>",
                    e.Code = "<a target='_self' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                    e.StockGoLeft = "&nbsp;",
                    e.StockGoRight = "&nbsp;"
                }
                ,
                this.addColor = function() {
                    var t = mainTableField[this._fucName];
                    for (var e in t)
                        if ("LastBlankTd" != e && "extend" != e)
                            if (e.indexOf("COLSPAN") >= 0) {
                                for (var o in t[e])
                                    if ("extend" != o) {
                                        this["data_" + o] = this[o];
                                        var a = allField[o].num || "default";
                                        this[o] = allField[o].color && i[allField[o].color] ? '<b class="' + i[allField[o].color].call(this, this[o]) + '">' + n[a](this[o]) + "</b>" : "<b>" + n[a](this[o]) + "</b>"
                                    }
                            } else {
                                this["data_" + e] = this[e];
                                var a = allField[e].num || "default";
                                this[e] = allField[e].color && i[allField[e].color] ? '<b class="' + i[allField[e].color].call(this, this[e]) + '">' + n[a](this[e]) + "</b>" : "<b>" + n[a](this[e]) + "</b>"
                            }
                }
                ,
                e.parseData = {
                    CTALL: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close = t[3],
                        e.ChangePercent = t[4],
                        e.Change = t[5],
                        e.Volume = t[6],
                        e.Order = t[7],
                        e.BuyPrice = t[8],
                        e.SellPrice = t[9],
                        e.Speed = t[10],
                        e.TurnoverRate = t[11],
                        e.Amount = t[12],
                        e.PERation = t[13],
                        e.High = t[14],
                        e.Low = t[15],
                        e.Open = t[16],
                        e.PreviousClose = t[17],
                        e.Amplitude = t[18],
                        e.VolumeRate = t[19],
                        e.CommissionRate = t[20],
                        e.CommissionDifference = t[21],
                        e.AveragePrice = t[22],
                        e.SellOrder = t[23],
                        e.BuyOrder = t[24],
                        e.OrderRate = t[25],
                        e.BuyVolume1 = t[26],
                        e.SellVolume1 = t[27],
                        e.PB = t[28],
                        e.TotalCapital = t[29],
                        e.MarketValue = t[30],
                        e.FlowCapital = t[31],
                        e.FlowCapitalValue = t[32],
                        e.ChangePercent3Day = t[33],
                        e.ChangePercent6Day = t[34],
                        e.TurnoverRate3Day = t[35],
                        e.TurnoverRate6Day = t[36],
                        e._fucName = "CTALL",
                        e.addColor()
                    },
                    CTDDE: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close = t[3],
                        e.ChangePercent = t[4],
                        e.DDX = t[5],
                        e.DDY = t[6],
                        e.DDZ = t[7],
                        e.DDX5 = t[8],
                        e.DDY5 = t[9],
                        e.DDX10 = t[10],
                        e.DDY10 = t[11],
                        e.DDXRed = t[12],
                        e.DDXRed5 = t[13],
                        e.DDXRed10 = t[14],
                        e.SBBuy = t[15],
                        e.SBSell = t[16],
                        e.SBRate = t[17],
                        e.BBuy = t[18],
                        e.BSell = t[19],
                        e.BRate = t[20],
                        e._fucName = "CTDDE",
                        e.addColor()
                    },
                    CTDF: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close = t[3],
                        e.DayFlow = t[4],
                        e.Ranking = t[5],
                        e.RankingChange = t[6],
                        e.ChangePercent = t[7],
                        e.DayFlow3 = t[8],
                        e.Ranking3 = t[9],
                        e.RankingChange3 = t[10],
                        e.ChangePercent3Day3 = t[11],
                        e.DayFlow5 = t[12],
                        e.Ranking5 = t[13],
                        e.RankingChange5 = t[14],
                        e.ChangePercent5Day = t[15],
                        e.DayFlow10 = t[16],
                        e.Ranking10 = t[17],
                        e.RankingChange10 = t[18],
                        e.ChangePercent10Day = t[19],
                        e._fucName = "CTDF",
                        e.addColor()
                    },
                    CTPF: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close = t[3],
                        e.ChangePercent = t[4],
                        e.AllNum = t[5],
                        e.BNum = t[6],
                        e.HNum = t[7],
                        e.NeuNum = t[8],
                        e.RdNum = t[9],
                        e.SellNum = t[10],
                        e.EpsWithLatestShare = t[11],
                        e.Values2 = t[12],
                        e.PE2 = t[13],
                        e.Values3 = t[14],
                        e.PE3 = t[15],
                        e.Values4 = t[16],
                        e.PE4 = t[17],
                        e.Values5 = t[18],
                        e.PE5 = t[19],
                        e._fucName = "CTPF",
                        e.addColor()
                    },
                    CTFA: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.LatestReportDate = t[3],
                        e.TotalShare = t[4],
                        e.TradableAShare = t[5],
                        e.AverageShareHolderNumber = t[6],
                        e.LatestBasicEps = t[7],
                        e.LatestNetAssetPerShare = t[8],
                        e.WeightedYieldOnNetAssets = t[9],
                        e.TotalOperatingIncome = t[10],
                        e.IncomeYOYGrowthRate = t[11],
                        e.OperatingProfit = t[12],
                        e.InvestmentIncome = t[13],
                        e.TotalProfit = t[14],
                        e.NetProfitAttributableToEquityHolderst = t[15],
                        e.NetProfitAttributableToEquityHoldersYOY = t[16],
                        e.UndistributedProfit = t[17],
                        e.RetainedEarningsPerShare = t[18],
                        e.SalesGrossMargin = t[19],
                        e.TotalAssets = t[20],
                        e.TotalCurrentAssets = t[21],
                        e.FixedAssets = t[22],
                        e.IntangibleAssets = t[23],
                        e.TotalLiabilities = t[24],
                        e.TotalCurrentLiabilities = t[25],
                        e.TotalNonCurrentLiabilities = t[26],
                        e.AssetLiabilityRatio = t[27],
                        e.TotalShareholdersEquity = t[28],
                        e.TotalShareholdersEquityDivTotalAssets = t[29],
                        e.CapitalReserve = t[30],
                        e.LatestCapitalReservePerShare = t[31],
                        e.TradableBShare = t[32],
                        e.HKListedShare = t[33],
                        e.IPODate = t[34],
                        e._fucName = "CTFA",
                        e.addColor()
                    },
                    CTBF: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close = t[3],
                        e.ChangePercent = t[4],
                        e.BalFlowMain = t[5],
                        e.BalFlowRate = t[6],
                        e.AmtOfBuy3 = t[7],
                        e.AmtOfSel3 = t[8],
                        e.AmtNet3 = t[9],
                        e.AmtNetRate3 = t[10],
                        e.AmtOfBuy2 = t[11],
                        e.AmtOfSel2 = t[12],
                        e.AmtNet2 = t[13],
                        e.AmtNetRate2 = t[14],
                        e.AmtOfBuy1 = t[15],
                        e.AmtOfSel1 = t[16],
                        e.AmtNet1 = t[17],
                        e.AmtNetRate1 = t[18],
                        e.AmtOfBuy0 = t[19],
                        e.AmtOfSel0 = t[20],
                        e.AmtNet0 = t[21],
                        e.AmtNetRate0 = t[22],
                        e._fucName = "CTBF",
                        e.addColor()
                    },
                    YKYL: function(t) {
                        t = t.split(","),
                        e._MarketType = t[0],
                        e._Name = t[2],
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close1 = t[3],
                        e.ChangePercent = t[4],
                        e.mrjj = t[5],
                        e.cyl = t[6],
                        e.mrcb = t[7],
                        e.dqsz = t[8],
                        e.mgyk = t[9],
                        e.ykl = t[10],
                        e.fdyk = t[11],
                        e._fucName = "YKYL",
                        e.addColor()
                    },
                    DGTL: function() {},
                    CTF: function(t) {
                        t = t.split(","),
                        e._Name = t[2],
                        e.Code = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Code + "</a>&nbsp;",
                        e.Name = "<a target='_blank' href='/stockeast/detail?stockCode=" + e._Code + "'>" + e._Name + "</a>&nbsp;",
                        e.Close = t[3],
                        e.Change = t[4],
                        e.ChangePercent = t[5],
                        e.BuyPrice = t[6],
                        e.SellPrice = t[7],
                        e.Order = t[8],
                        e.Volume = t[9],
                        e.Amount = t[10],
                        e.Open = t[11],
                        e.PreviousClose = t[12],
                        e.High = t[13],
                        e.Low = t[14],
                        e.TurnoverRate = t[15],
                        e.PERation = t[16],
                        e._fucName = "CTF",
                        e.addColor()
                    }
                },
                e.parseCommonData()
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
            for (var l in allField)
                !stockClass.prototype[l] && (stockClass.prototype[l] = "-")
        }(window),
        function(t) {
            function e(t) {
                1 == t.result ? "" != t.data.msg ? EMui.tip(t.data.msg, "操作成功") : EMui.tip("操作成功", "操作成功") : "" != t.data.msg ? EMui.tip(t.data.msg, "操作失败") : EMui.tip("操作失败", "操作失败")
            }
            var o = !0
              , n = !1
              , i = function() {
                if (!n) {
                    n = !0;
                    var t = document.getElementById("addLot");
                    t.innerHTML = "自选股不见了",
                    t.setAttribute("id", "n"),
                    document.getElementById("loginZX").style.display = "none",
                    document.getElementById("unloginZX").style.display = "block",
                    document.getElementById("unLoginHideLink").style.display = "none",
                    document.getElementById("addCollectionBtn").style.display = "none",
                    document.getElementById("moveLotOfStock").style.display = "none",
                    t.onclick = function() {
                        window.open("http://quote.eastmoney.com/help/favor.html")
                    }
                }
            };
            t.mySelectCollection = function(e) {
                if (e.result && 1 == e.result) {
                    usefulDom.tab1.innerHTML = "",
                    usefulDom.moreCollect.innerHTML = "",
                    usefulDom.allSC.innerHTML = "";
                    var n = e.data.order.split(",");
                    myStock.collectionList = {};
                    for (var a = 0; a < n.length; a++)
                        e.data[n[a]][0] = decodeURI(e.data[n[a]][0]),
                        myStock.collectionList[n[a]] = {
                            _name: e.data[n[a]][0],
                            _lid: n[a],
                            _num: e.data[n[a]][1]
                        };
                    ("CTALL" == myStock.funcName || "CTF" == myStock.funcName) && (myStock.funcName = defaultKey),
                    myStock.fillCollection(),
                    myStock.NobleCollectID = n[0],
                    myStock.loginState = !0
                } else if (o) {
                    t.defaultKey = "CTF",
                    ("CTALL" == myStock.funcName || "CTF" == myStock.funcName) && (myStock.funcName = defaultKey),
                    i();
                    var s = "001";
                    myStock.collectionList[s] = {
                        _name: "自选股",
                        _lid: "001",
                        _num: 3
                    },
                    myStock.fillCollection(),
                    myStock.NobleCollectID = s
                } else
                    window.location.href = "http://quote.eastmoney.com/favor/old.html"
            }
            ,
            t.getStockInfo = function(e) {
                if (myStock.stockList.splice(0, myStock.stockList.length),
                myStock.allStockArray.splice(0, myStock.allStockArray.length),
                e.result && 1 == e.result) {
                    var n = [];
                    if (e.data.order) {
                        n = e.data.order;
                        for (var i = 0; i < n.length; i++) {
                            var a = n[i].split("|")
                              , s = e.data.star && e.data.star.indexOf(n[i]) >= 0 ? 1 : -1
                              , l = e.data.Notes && e.data.Notes.indexOf(n[i]) >= 0 ? 1 : -1;
                            myStock.allStockArray.push(new stockClass({
                                _Code: a[0],
                                _MarketType: t.codeToMarket(a[0]),
                                _Type: "02",
                                _star: s,
                                _note: l,
                                _alert: -1,
                                CollectionID: myStock.curCollectID
                            }))
                        }
                        myStock.pageIndex = 0,
                        myStock.loadPage(),
                        myStock.initPageNums()
                    } else
                        usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>",
                        usefulDom.morePage.style.display = "none"
                } else if (o) {
                    var n = ["300379", "510300", "300059"];
                    if (getCookie("emhq_stock")) {
                        n = [];
                        for (var r = getCookie("emhq_stock").split(","), c = 0; r[c]; )
                            n.push(r[c]),
                            c++
                    }
                    for (var i = 0; i < n.length; i++)
                        myStock.allStockArray.push(new stockClass({
                            _Code: n[i],
                            _MarketType: t.codeToMarket(n[i]),
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
            t.getStockFullInfo = function(t, e) {
                if (!t[0] || void 0 == t[0].stats || t[0].stats) {
                    if ("YKYL" == myStock.funcName) {
                        var o = [];
                        for (var n in allNote.data)
                            for (var i = 0; i < t.length; i++) {
                                var a = t[i].split(",");
                                if (n.split("|")[0] == a[1]) {
                                    var s = [];
                                    if ("" == allNote.data[n].node) {
                                        for (var l = 0; 5 > l; l++)
                                            s.push(a[l]);
                                        for (var l = 0; 7 > l; l++)
                                            s.push(0);
                                        o.push(s.join(","))
                                    } else {
                                        for (var r = allNote.data[n].node.split("|"), s = [], l = 0; 5 > l; l++)
                                            s.push(a[l]);
                                        s.push(r[1]),
                                        s.push(r[0]);
                                        var c = parseFloat(r[1] * r[0]) + parseFloat(r[3]) + parseFloat(r[1] * r[0]) * (parseFloat(r[2]) + parseFloat(r[4])) / 1e3;
                                        if (s.push(c.toFixed(2)),
                                        0 == a[3])
                                            var d = a[17];
                                        else
                                            var d = a[3];
                                        var u = parseFloat(r[0] * d);
                                        s.push(u.toFixed(2));
                                        var m = parseFloat(d - r[1]);
                                        if ((0 == r[1] || 0 == r[0]) && (m = 0),
                                        s.push(m.toFixed(2) || 0),
                                        0 == r[1])
                                            var p = 0;
                                        else {
                                            var p = 0 == c ? 0 : (100 * (u / c - 1)).toFixed(2);
                                            p = p || 0
                                        }
                                        s.push(p + "%");
                                        var h = m * r[0];
                                        s.push(h.toFixed(2)),
                                        o.push(s.join(","))
                                    }
                                }
                            }
                        t = o
                    }
                    for (var i = 0; i < t.length; i++)
                        for (var f = t[i].split(","), n = 0; n < myStock.stockList.length; n++)
                            if (f[0] == myStock.stockList[n]._MarketType && f[1] == myStock.stockList[n]._Code) {
                                myStock.stockList[n].parseData[myStock.funcName](t[i]);
                                break
                            }
                    void 0 == e && (usefulDom.allTable.scrollLeft = 0)
                }
            }
            ,
            t.addhandlerCallback = function(t) {
                if (e(t),
                1 == t.result) {
                    myStock.oldStockList.length > 0 && (myStock.stockList = myStock.oldStockList,
                    myStock.oldStockList.splice(0, myStock.oldStockList.length));
                    var o = myStock.toAddStockId.split("|");
                    if (myStock.stockList.splice(myStock.starLength, 0, new stockClass({
                        _Code: o[0],
                        _MarketType: o[1],
                        _Type: o[2],
                        _star: -1,
                        _note: -1,
                        _alert: -1,
                        CollectionID: myStock.curCollectID
                    })),
                    myStock.allStockArray.splice(myStock.starLength, 0, new stockClass({
                        _Code: o[0],
                        _MarketType: o[1],
                        _Type: o[2],
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
                        return void h.init(!0);

                    var n = o[0].toString() + parseInt(o[1]).toString();
                    if ("YKYL" == myStock.funcName) {
                        return void loadScript("https://myfavor.eastmoney.com/mystock?f=gsb&g=" + myStock.curCollectID + "&sc=" + myStock.toAddStockId + "&cb=NoteAllhandlerCallback&" + Math.random(), function() {
                            loadScript(hpUrl + "type=CT&cmd=" + n + "&sty=" + defaultKey + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function() {
                                myStock.fillAll(),
                                myStock.getNews()
                            })
                        })
                    }
                    loadScript(hpUrl + "type=CT&cmd=" + n + "&sty=" + myStock.funcName + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function() {
                        myStock.fillAll(),
                        myStock.getNews()
                    })
                }
            }
            ,
            t.delhandlerCallback = function(t) {
                e(t),
                1 == t.result && (myStock.delElementFormList(myStock.curDelStockID),
                myStock.sortState = "none",
                myStock.Drag = !0,
                "DGTL" == myStock.funcName ? h.init(!0) : (myStock.loadPage(),
                myStock.getStockInfoList(myStock.funcName)),
                myStock.getNews())
            }
            ,
            t.NotehandlerCallback = function(t) {
                u.init(),
                1 == t.result && (u.fill(),
                u.fillTable(t))
            }
            ,
            t.NoteAllhandlerCallback = function(t) {
                allNote = t
            }
            ,
            t.starCallback = function(t) {
                var o;
                if (t.result > 0) {
                    if (1 == t.result && (myStock.stockList[myStock.starLast]._star = myStock.starLastFuc),
                    myStock.stockList[myStock.starLast].parseCommonData(),
                    "none" != myStock.sortState)
                        return void myStock.fillAll();
                    1 == myStock.starLastFuc ? (o = myStock.stockList[myStock.starLast],
                    myStock.stockList.splice(myStock.starLast, 1),
                    myStock.stockList.unshift(o)) : (o = myStock.stockList[myStock.starLast],
                    myStock.stockList.splice(myStock.starLast, 1),
                    myStock.stockList.push(o)),
                    myStock.fillAll()
                } else
                    e(t)
            }
            ,
            t.alertInit = function(t) {
                if (myStock.firstBlood = !1,
                myStock.cacheAlertData = t,
                !document.getElementById("rightTable") || !t.result)
                    return !1;
                myStock.alertStock = t.result;
                for (var e = myStock.alertStock.join(","), o = document.getElementById("leftTable").getElementsByTagName("i"), n = 0; n < o.length; n++)
                    o[n].className.indexOf("alert") >= 0 && e.indexOf(o[n].getAttribute("scode")) >= 0 && (o[n].className = "alert-on")
            }
            ,
            t.alertInit1 = function() {
                for (var t = myStock.alertStock.join(","), e = document.getElementById("rightTable").getElementsByTagName("i"), o = 0; o < e.length; o++)
                    e[o].className.indexOf("alert") >= 0 && t.indexOf(e[o].getAttribute("scode")) >= 0 && (e[o].className = "alert-on")
            }
            ,
            t.movehandlerCallback = function(t) {
                e(t),
                1 == t.result && myStock.initPage()
            }
            ,
            t.addCollectionCallback = function(t) {
                e(t),
                1 == t.result && myStock.initPage()
            }
            ,
            t.deleteCollectionCallback = function(t) {
                e(t),
                1 == t.result && myStock.initPage()
            }
            ,
            t.renameCollectionCallback = function(t) {
                e(t),
                1 == t.result && (myStock.curCollectDom.firstChild.nodeValue = myStock.curCollectDom.firstChild.nodeValue.replace(/[\w\u4e00-\u9fa5]+/, myStock.tryCollectionName.YO(4)))
            }
            ,
            t.saveNoteCallback = function(t) {
                e(t),
                "YKYL" == myStock.funcName && document.getElementById("refreshALLALLALL").onclick()
            }
            ,
            t.singleStock = function(t) {
                m.ob = t
            }
            ,
            t.addLothandlerCallback = function(t) {
                e(t),
                myStock.targetCollectionID = myStock.curCollectID,
                myStock.initPage()
            }
        }(window),
        function(t) {
            function e(t, e) {
                e = e || "cur";
                for (var o in t.parentNode.children)
                    t.parentNode.children[o].className && t.parentNode.children[o].className.indexOf(e) >= 0 && (t.parentNode.children[o].className = t.parentNode.children[o].className.replace(e, ""));
                t.className += " " + e
            }
            function a(t) {
                var e = t.substr(0, 1)
                  , o = t.substr(0, 3);
                return "5" == e || "6" == e || "9" == e ? "01" : "009" == o || "126" == o || "110" == o || "201" == o || "202" == o || "203" == o || "204" == o ? "01" : "02"
            }
            function s(t, e) {
                e.splice(0, e.length);
                for (var o = 0; o < t.length; o++)
                    e.push(t[o])
            }
            function l(t) {
                switch (t) {
                case "up":
                case "down":
                    usefulDom.wawa.style.display = "block",
                    usefulDom.wawa.setAttribute("title", "升序或降序排序状态下不可拖动自选股");
                    break;
                default:
                    usefulDom.wawa.style.display = "none"
                }
            }
            function p() {
                login4ShowCB(function() {
                    window.location.reload()
                })
            }
            var f = 465;
            t.getPosition = function(t) {
                if (t) {
                    for (index = 0; t = t.previousSibling; )
                        1 == t.nodeType && index++;
                    return index
                }
            }
            ,
            t.coverbg = function() {
                var t = document.createElement("div");
                t.id = "loader_all_bg",
                _scrollWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth),
                _scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight),
                t.style.width = _scrollWidth + "px",
                t.style.height = _scrollHeight + "px",
                document.body.appendChild(t)
            }
            ,
            t.uncoverbg = function() {
                var t = document.getElementById("loader_all_bg");
                null != t && document.body.removeChild(t)
            }
            ,
            t.loadingGIF = document.getElementById("myStockLoadImg").value,
            t.usefulDom = {
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
            //t.stockUrl = "https://myfavor.eastmoney.com/mystock?",
            t.stockUrl = "/stockeast/mystock?",
            t.hpUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?ps=500&token=580f8e855db9d9de7cb332b3990b61a3&",
            t.newUrl = "http://iguba.eastmoney.com/api/MainPostForZxg.aspx?ps=30&codes=",
            t.newUrlMy = "http://183.136.164.50:35792/get.aspx?c=15&a=",
            window._serverUrl = "http://igubacs.eastmoney.com/gubaapi/v3/",
            t.readUrl = _serverUrl + "read/Custom/Jsonp/FollowGubaPostList.aspx?version=100&product=UserCenter&plat=Jsonp&codes=",
            String.prototype.YO = function(t) {
                return isNaN(t) ? void 0 : this.length > t ? this.slice(0, t) : this
            }
            ;
            var g = {
                Checkbox: {},
                StockAlert: {},
                Code: {},
                Name: {},
                AboutLinker: {},
                StockGoLeft: {}
            }
              , y = {
                StockGoRight: {},
                StockNote: {},
                StockDel: {}
            };
            t.Browser = {
                ie: /msie/.test(window.navigator.userAgent.toLowerCase()),
                moz: /gecko/.test(window.navigator.userAgent.toLowerCase()),
                opera: /opera/.test(window.navigator.userAgent.toLowerCase()),
                safari: /safari/.test(window.navigator.userAgent.toLowerCase())
            },
            t.loadScript = function(t, e) {
                var o = document.createElement("script");
                o.setAttribute("type", "text/javascript"),
                o.setAttribute("src", t),
                o.setAttribute("charset", "utf-8"),
                document.getElementsByTagName("head")[0].appendChild(o),
                Browser.ie ? o.onreadystatechange = function() {
                    ("loaded" == this.readyState || "complete" == this.readyState) && (o.parentNode.removeChild(o),
                    e && e())
                }
                : Browser.moz || Browser.opera ? o.onload = function() {
                    o.parentNode.removeChild(o),
                    e && e()
                }
                : (o.parentNode.removeChild(o),
                e && e())
            }
            ;
            var v = function() {
                function d() {
                    var t = {};
                    this.set = function(e, o) {
                        t[e] = o
                    }
                    ,
                    this.get = function(e) {
                        return t[e]
                    }
                    ,
                    this.getAll = function() {
                        var e = [];
                        for (key in t)
                            t.hasOwnProperty(key) ? e.push(t[key]) : void 0;
                        return e
                    }
                    ,
                    this.remove = function(e) {
                        t.hasOwnProperty(e) ? delete t[e] : void 0
                    }
                    ,
                    this.empty = function() {
                        for (key in t)
                            t.hasOwnProperty(key) ? delete t[key] : void 0
                    }
                }
                function v() {
                    window._tpl = "undefined" == typeof _tpl ? jQuery("#info-wrap")[0].innerHTML : _tpl,
                    window._infoItems = new d,
                    window._stocks = [],
                    window._cnt = 10,
                    window._pages = 1,
                    window._currentPage = 1,
                    window._currentType = 0,
                    window._currentTarget = void 0,
                    jQuery("#stInfoTabs").children().removeClass("active"),
                    jQuery("#stInfoTabs").children().first().addClass("active")
                }
                function b(t, e) {
                    var o = jQuery(_tpl);
                    o.attr("data-type", e.post_type);
                    var n = "" == e.user_id.length ? "http://gbfek.dfcfw.com/gubav5/images/nophoto.jpg" : "http://gbres.dfcfw.com/qface/" + e.user_id + "/166";
                    o.find("img").attr("src", n),
                    jQuery(o.find("span")[0]).text("[" + e.stockbar_name + "]"),
                    jQuery(o.find("span")[1]).text(e.post_publish_time),
                    jQuery(o.find("a")[0]).text(e.post_title),
                    jQuery(o.find("a")[0]).attr("href", e.post_url),
                    jQuery(o.find("a")[1]).text(e.user_nickname || e.post_ip),
                    e.user_nickname && "" != e.user_nickname ? jQuery(o.find("a")[1]).attr("href", "http://iguba.eastmoney.com/" + e.user_id) : jQuery(o.find("a")[1]).addClass("unlogin"),
                    o.find("div")[2].innerText = e.post_content,
                    jQuery(o.find("a")[2]).text(e.post_is_like ? "已赞" : "赞"),
                    jQuery(o.find("a")[2]).next().text(e.post_like_count),
                    jQuery(o.find("a")[3]).next().text(e.post_forward_count),
                    jQuery(o.find("a")[4]).text(e.post_is_collected ? "已收藏" : "收藏"),
                    jQuery(o.find("a")[5]).next().text(e.post_click_count),
                    jQuery(o.find("a")[6]).next().text(e.post_comment_count),
                    k(o.find("a"), e),
                    jQuery("#info-wrap").append(o),
                    o.show()
                }
                function k(t, e) {
                    jQuery(t[2]).click(function(t) {
                        _currentTarget = t.target;
                        var o = "赞" == this.innerHTML ? "LikeArticle" : "CancelLikeArticle"
                          , n = _serverUrl + "write/Article/Like/" + o + ".aspx?id=" + e.post_id + "&type=" + e.post_type + "&version=100&product=UserCenter&plat=Jsonp&callback=opCallback";
                        loadScript(n)
                    }),
                    jQuery(t[3]).click(function(t) {
                        _currentTarget = t.target,
                        jQuery("#modal-mask").show(),
                        jQuery("#modal-mask").css("height", jQuery("body").height() + "px"),
                        jQuery("#modal").show();
                        var o = (jQuery(window).width() - jQuery("#modal").width()) / 2 + "px"
                          , n = (jQuery(window).height() - jQuery("#modal").height()) / 2 + jQuery(document).scrollTop() + "px";
                        jQuery("#modal").css({
                            "margin-left": o,
                            "margin-top": n
                        }),
                        jQuery("#modal").find("a").first().click(function() {
                            jQuery("#modal-mask").hide(),
                            jQuery("#modal").hide()
                        }),
                        jQuery("#modal").find("a").last().html(e.user_nickname || e.post_ip),
                        e.user_nickname && "" != e.user_nickname ? (jQuery("#modal").find("a").last().removeClass("unlogin"),
                        jQuery("#modal").find("a").last().attr("href", "http://iguba.eastmoney.com/" + e.user_id)) : (jQuery("#modal").find("a").last().removeAttr("href"),
                        jQuery("#modal").find("a").last().addClass("unlogin")),
                        jQuery("#modal").find("b").html(e.post_title),
                        jQuery("#modal").find("div")[6].innerHTML = e.post_content,
                        jQuery("#modal").find("label").html("同时转发给" + (e.user_nickname || e.post_ip));
                        var i = "" == e.user_id.length ? "http://gbfek.dfcfw.com/gubav5/images/nophoto.jpg" : "http://gbres.dfcfw.com/qface/" + e.user_id + "/100";
                        jQuery("#modal").find("img").attr("src", i),
                        jQuery("#modal").find("input").last().click(function() {
                            var t = (jQuery("#modal").find("input").first().attr("checked"),
                            _serverUrl + "write/Article/Post/Post.aspx?version=100&product=UserCenter&plat=Jsonp&callback=opCallback&text=" + e.post_content + "&yid=" + e.post_id);
                            loadScript(t)
                        })
                    }),
                    jQuery(t[4]).click(function(t) {
                        _currentTarget = t.target;
                        var o = "收藏" == this.innerHTML ? "CollectArticle" : "CancelCollectArticle"
                          , n = _serverUrl + "write/Article/Collect/" + o + ".aspx?postid=" + e.post_id + "&version=100&product=UserCenter&plat=Jsonp&callback=opCallback";
                        loadScript(n)
                    }),
                    jQuery(t[6]).toggle(function(t) {
                        _currentTarget = t.target;
                        var o = jQuery('<div class="guba-comment"><div class="gbreplyarrow"></div><textarea></textarea><div class="comment-reply"><a class="facelist-a" href="javascript:"><em class="facelist-a"></em>表情</a><div class="gbreplysub"></div></div><div class="comment-rplist"><div class="rplist-head"></div><div class="rplist-items"></div></div></div>')
                          , n = o.find("textarea")
                          , i = jQuery(t.target).parent().parent().parent();
                        jQuery(".guba-comment").remove(),
                        i.append(o);
                        var a = jQuery('<div class="shadowbox" id="insertfacediv"><div class="shadowboxtoparrow"></div><table class="shadowboxt" cellspacing="0" cellpadding="0"><tbody><tr><td class="l7"></td><td class="l8"></td><td class="l9"></td></tr><tr><td class="l4">&nbsp;</td><td class="l5"><div class="shadowboxbody"><div id="facediv"><ul id="facelist"><li title="微笑"><img title="微笑" src="http://gbres.dfcfw.com/face/emot/emot01.png" alt="微笑"></li><li title="大笑"><img title="大笑" src="http://gbres.dfcfw.com/face/emot/emot02.png" alt="大笑"></li><li title="鼓掌"><img title="鼓掌" src="http://gbres.dfcfw.com/face/emot/emot03.png" alt="鼓掌"></li><li title="不说了"><img title="不说了" src="http://gbres.dfcfw.com/face/emot/emot04.png" alt="不说了"></li><li title="为什么"><img title="为什么" src="http://gbres.dfcfw.com/face/emot/emot05.png" alt="为什么"></li><li title="哭"><img title="哭" src="http://gbres.dfcfw.com/face/emot/emot06.png" alt="哭"></li><li title="不屑"><img title="不屑" src="http://gbres.dfcfw.com/face/emot/emot07.png" alt="不屑"></li><li title="怒"><img title="怒" src="http://gbres.dfcfw.com/face/emot/emot08.png" alt="怒"></li><li title="滴汗"><img title="滴汗" src="http://gbres.dfcfw.com/face/emot/emot09.png" alt="滴汗"></li><li title="拜神"><img title="拜神" src="http://gbres.dfcfw.com/face/emot/emot10.png" alt="拜神"></li><li title="胜利"><img title="胜利" src="http://gbres.dfcfw.com/face/emot/emot11.png" alt="胜利"></li><li title="亏大了"><img title="亏大了" src="http://gbres.dfcfw.com/face/emot/emot12.png" alt="亏大了"></li><li title="赚大了"><img title="赚大了" src="http://gbres.dfcfw.com/face/emot/emot13.png" alt="赚大了"></li><li title="牛"><img title="牛" src="http://gbres.dfcfw.com/face/emot/emot14.png" alt="牛"></li><li title="俏皮"><img title="俏皮" src="http://gbres.dfcfw.com/face/emot/emot15.png" alt="俏皮"></li><li title="傲"><img title="傲" src="http://gbres.dfcfw.com/face/emot/emot16.png" alt="傲"></li><li title="好困惑"><img title="好困惑" src="http://gbres.dfcfw.com/face/emot/emot17.png" alt="好困惑"></li><li title="兴奋"><img title="兴奋" src="http://gbres.dfcfw.com/face/emot/emot18.png" alt="兴奋"></li><li title="赞"><img title="赞" src="http://gbres.dfcfw.com/face/emot/emot19.png" alt="赞"></li><li title="不赞"><img title="不赞" src="http://gbres.dfcfw.com/face/emot/emot20.png" alt="不赞"></li><li title="摊手"><img title="摊手" src="http://gbres.dfcfw.com/face/emot/emot21.png" alt="摊手"></li><li title="好逊"><img title="好逊" src="http://gbres.dfcfw.com/face/emot/emot22.png" alt="好逊"></li><li title="失望"><img title="失望" src="http://gbres.dfcfw.com/face/emot/emot23.png" alt="失望"></li><li title="加油"><img title="加油" src="http://gbres.dfcfw.com/face/emot/emot24.png" alt="加油"></li><li title="困顿"><img title="困顿" src="http://gbres.dfcfw.com/face/emot/emot25.png" alt="困顿"></li><li title="想一下"><img title="想一下" src="http://gbres.dfcfw.com/face/emot/emot26.png" alt="想一下"></li><li title="围观"><img title="围观" src="http://gbres.dfcfw.com/face/emot/emot27.png" alt="围观"></li><li title="献花"><img title="献花" src="http://gbres.dfcfw.com/face/emot/emot28.png" alt="献花"></li><li title="大便"><img title="大便" src="http://gbres.dfcfw.com/face/emot/emot29.png" alt="大便"></li><li title="爱心"><img title="爱心" src="http://gbres.dfcfw.com/face/emot/emot30.png" alt="爱心"></li><li title="心碎"><img title="心碎" src="http://gbres.dfcfw.com/face/emot/emot31.png" alt="心碎"></li><li title="毛估估"><img title="毛估估" src="http://gbres.dfcfw.com/face/emot/emot32.png" alt="毛估估"></li><li title="成交"><img title="成交" src="http://gbres.dfcfw.com/face/emot/emot33.png" alt="成交"></li><li title="财力"><img title="财力" src="http://gbres.dfcfw.com/face/emot/emot34.png" alt="财力"></li><li title="护城河"><img title="护城河" src="http://gbres.dfcfw.com/face/emot/emot35.png" alt="护城河"></li><li title="复盘"><img title="复盘" src="http://gbres.dfcfw.com/face/emot/emot36.png" alt="复盘"></li><li title="买入"><img title="买入" src="http://gbres.dfcfw.com/face/emot/emot37.png" alt="买入"></li><li title="卖出"><img title="卖出" src="http://gbres.dfcfw.com/face/emot/emot38.png" alt="卖出"></li><li title="满仓"><img title="满仓" src="http://gbres.dfcfw.com/face/emot/emot39.png" alt="满仓"></li><li title="空仓"><img title="空仓" src="http://gbres.dfcfw.com/face/emot/emot40.png" alt="空仓"></li><li title="抄底"><img title="抄底" src="http://gbres.dfcfw.com/face/emot/emot41.png" alt="抄底"></li><li title="看多"><img title="看多" src="http://gbres.dfcfw.com/face/emot/emot42.png" alt="看多"></li><li title="看空"><img title="看空" src="http://gbres.dfcfw.com/face/emot/emot43.png" alt="看空"></li><li title="加仓"><img title="加仓" src="http://gbres.dfcfw.com/face/emot/emot44.png" alt="加仓"></li><li title="减仓"><img title="减仓" src="http://gbres.dfcfw.com/face/emot/emot45.png" alt="减仓"></li></ul><div class="clear"></div></div></div></td><td class="l6"></td></tr><tr><td class="l1"></td><td class="l2"></td><td class="l3"></td></tr></tbody></table></div>');
                        o.find(".comment-reply").append(a),
                        jQuery(".comment-reply a.facelist-a").click(function() {
                            a.show()
                        }),
                        jQuery("body").click(function(t) {
                            "facelist-a" != t.target.className && a.hide(),
                            t.stopPropagation()
                        }),
                        a.find("li").click(function() {
                            var t = n.val();
                            n.val(t + "[" + this.title + "]"),
                            a.hide(),
                            n.focus()
                        });
                        var s = function(t) {
                            if (0 != t.trim().length) {
                                var o = _serverUrl + "write/Article/Reply/ReplyWeb.aspx?tid=" + e.post_id + "&type=1&version=100&product=UserCenter&plat=Jsonp&callback=opCallback&text=" + t;
                                loadScript(o)
                            }
                        };
                        jQuery(".gbreplysub").click(function() {
                            s(n.val()),
                            jQuery(".guba-comment").remove()
                        });
                        var l = _serverUrl + "read/Article/Reply/MainPostReplyList.aspx?id=" + e.post_id + "&ps=20&p=1&sort=1&version=100&product=UserCenter&plat=Jsonp&type=0&callback=loadComment";
                        loadScript(l),
                        window.loadComment = function(t) {
                            t.count > 0 && (o.find(".rplist-head").append("<span>全部评论（" + t.count + "）</span>"),
                            jQuery(t.re).each(function() {
                                var t = jQuery('<div class="rplist-item"><div class="rplist-item-lt"><img src="http://gbres.dfcfw.com/qface/' + this.reply_user.user_id + '/166"></div><div class="rplist-item-rt"><div class="rplist-item-nm"><a style="padding-left:0;" href="http://iguba.eastmoney.com/' + this.reply_user.user_id + '" target="_blank">' + this.reply_user.user_nickname + '</a>：</div><div class="rplist-item-txt">' + this.reply_text + '</div><div class="rplist-item-ft"><div class="rplist-item-tm">2015-08-19 10:33:18</div><div class="rplist-item-rp"><a href="javascript:">评论</a></div></div></div></div>');
                                o.find(".rplist-items").append(t),
                                t.find(".rplist-item-rp a").toggle(function() {
                                    jQuery(".guba-comment.recomment").remove();
                                    var e = jQuery('<div class="guba-comment recomment"><div class="gbreplyarrow"></div><textarea></textarea><div class="comment-reply"><div class="gbreplysub"></div></div></div>');
                                    t.find(".rplist-item-rt").append(e),
                                    e.find("textarea").text("评论@" + t.find("a").first().text() + "："),
                                    e.find("textarea").focus(),
                                    e.find(".gbreplysub").click(function() {
                                        s(e.find("textarea").val()),
                                        jQuery(".guba-comment.recomment").remove()
                                    })
                                }, function() {
                                    jQuery(".guba-comment.recomment").remove()
                                })
                            }),
                            o.find(".rplist-item").last().css("border", "none"))
                        }
                    }, function() {
                        jQuery(".guba-comment").remove()
                    })
                }
                function _(t, e) {
                    if (null == t || void 0 == t)
                        return !1;
                    if (null == e || void 0 == e)
                        return !0;
                    t = t.toString(),
                    e = e.toString();
                    var o = 1
                      , n = 1;
                    return isNaN(parseFloat(t)) ? !1 : isNaN(parseFloat(e)) ? !0 : (t = t.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                    e = e.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                    o * parseFloat(t) < n * parseFloat(e))
                }
                function C(t, e) {
                    if (null == t || void 0 == t)
                        return !1;
                    if (null == e || void 0 == e)
                        return !0;
                    t = t.toString(),
                    e = e.toString();
                    var o = 1
                      , n = 1;
                    return isNaN(parseFloat(t)) ? !1 : isNaN(parseFloat(e)) ? !0 : (t = t.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                    e = e.replace(/(\d{4})-(\d{2})-(\d{2})/g, "$1$2$3"),
                    o * parseFloat(t) >= n * parseFloat(e))
                }
                var T = this;
                T.loginState = !1,
                T.showPageNum = 9,
                T.pageSize = 100,
                T.pageIndex = 0,
                T.allStockArray = [],
                T.stockIndex = 0,
                T.firstBlood = !0,
                T.collectionList = {},
                T.stockList = [],
                T.starLength = 0,
                T.oldStockList = [],
                T.funcName = "DGTL" == getCookie("funcName") ? defaultKey : getCookie("funcName") || defaultKey,
                T.NobleCollectID = 0,
                T.maxTabli = 9,
                T.targetCollectionID = getCookie("curCollectID") || 0,
                T.Drag = !0,
                T.sortState = "none",
                T.curCollectID = 0,
                T.curCollectDom = null,
                T.curDelStockID = "",
                T.curNoteStockID = "",
                T.curTiXingStockID = "",
                T.lastSortIndex = 0,
                T.starLast = 0,
                T.alertStock = [],
                T.vsIndex = 0,
                T.jumpToDgtl = getCookie("jumpToDgtl") || !1,
                T.editCollectDiv = document.createElement("div"),
                T.editCollectDiv.className = "editCollection",
                T.reNameCollectionDom = document.createElement("span"),
                T.reNameCollectionDom.className = "renameCt",
                T.reNameCollectionDom.innerHTML = "更改名称",
                T.delCollectionDom = document.createElement("span"),
                T.delCollectionDom.className = "deleteCt",
                T.delCollectionDom.innerHTML = "删除组合",
                T.editCollectDiv.appendChild(T.reNameCollectionDom),
                T.editCollectDiv.appendChild(T.delCollectionDom),
                T.fillCollection = function(t) {
                    t = t || 0,
                    usefulDom.tab1.innerHTML = "",
                    usefulDom.allSC.innerHTML = "";
                    var e = 0;
                    document.getElementById("topmoreCollectionSelect").style.display = "none";
                    for (var o in T.collectionList)
                        "extend" != o && (e < T.maxTabli ? (usefulDom.tab1.innerHTML += "<li title='" + T.collectionList[o]._name + "' lid='" + T.collectionList[o]._lid + "'>" + T.collectionList[o]._name.YO(4) + "</li>",
                        usefulDom.allSC.innerHTML += "<span lid='" + T.collectionList[o]._lid + "'>" + T.collectionList[o]._name + "</span> ") : (usefulDom.allSC.innerHTML += "<span lid='" + T.collectionList[o]._lid + "'>" + T.collectionList[o]._name + "</span> ",
                        usefulDom.moreCollect.innerHTML += "<span title='" + T.collectionList[o]._name + "' index='" + e + "' lid='" + T.collectionList[o]._lid + "'>" + T.collectionList[o]._name.YO(4) + "</span> ",
                        document.getElementById("topmoreCollectionSelect").style.display = "block"),
                        e++);
                    return T.selectCollection(t),
                    T.initButtonCollection(),
                    T.initFucNameBtns(),
                    T.initMM(),
                    0 != T.targetCollectionID ? void T.resetLocation() : void 0
                }
                ,
                T.initFucNameBtns = function() {
                    for (var t = usefulDom.fucBtns.getElementsByTagName("li"), o = 0; t[o]; ) {
                        if (t[o].getAttribute("callName") == T.funcName) {
                            e(t[o]);
                            break
                        }
                        o++
                    }
                }
                ,
                T.initMM = function() {
                    for (var t = usefulDom.moreCollect.getElementsByTagName("span"), e = 0; e < t.length; e++)
                        t[e].onclick = function() {
                            var t = this.innerHTML
                              , e = this.getAttribute("lid")
                              , o = this.getAttribute("title")
                              , n = usefulDom.tab1.lastChild;
                            this.innerHTML = n.innerHTML,
                            this.setAttribute("lid", n.getAttribute("lid")),
                            this.setAttribute("title", n.getAttribute("title")),
                            n.innerHTML = t,
                            n.setAttribute("lid", e),
                            n.setAttribute("title", o),
                            n.click()
                        }
                }
                ,
                T.selectCollection = function(t) {
                    T.curCollectDom && (reworker(T.curCollectDom, "mouseenter", w),
                    reworker(T.curCollectDom, "mouseleave", S));
                    var o = usefulDom.tab1.children[t];
                    T.curCollectID = o.getAttribute("lid"),
                    T.curCollectDom = o,
                    e(T.curCollectDom, "cur"),
                    "" == T.editCollectDiv.innerHTML && (T.reNameCollectionDom = document.createElement("span"),
                    T.reNameCollectionDom.className = "renameCt",
                    T.reNameCollectionDom.innerHTML = "更改名称",
                    T.delCollectionDom = document.createElement("span"),
                    T.delCollectionDom.className = "deleteCt",
                    T.delCollectionDom.innerHTML = "删除组合",
                    T.editCollectDiv.appendChild(T.reNameCollectionDom),
                    T.editCollectDiv.appendChild(T.delCollectionDom)),
                    T.curCollectDom.appendChild(T.editCollectDiv),
                    T.oldStockList.slice(0, T.oldStockList.length),
                    T.Drag = !0,
                    T.sortState = "none",
                    usefulDom.sortInfo.innerHTML = T.textSort.none,
                    T.vsIndex = 0,
                    T.initEditCollection(),
                    T.getCollectionInfoById(T.curCollectID, function() {
                        T.initButtonGroup(),
                        T.getStockInfoList(T.funcName),
                        T.getNews()
                    })
                }
                ,
                this.fillAll = function() {
                    usefulDom.allTable.innerHTML = "";
                    var t = []
                      , e = 0;
                    e = T.fillMainTable(t, e),
                    T.fillLeftTable(t, e),
                    T.fillRightTable(t),
                    usefulDom.allTable.innerHTML += t.join(""),
                    this.tableColor(),
                    T.initStockBtns(),
                    T.initSortEvent(),
                    T.vsIndex = 0,
                    T.initVS(),
                    T.starLength = 0;
                    for (var i = 0; i < T.stockList.length; i++)
                        1 == T.stockList[i]._star && T.starLength++;
                    T.curCollectID == T.NobleCollectID && T.initAlertData();
                    var a = document.all && !document.querySelector;
                    if (a)
                        ;
                    else {
                        var s = o(50);
                        s.bindtableheader()
                    }
                    n.resetpar(),
                    n.bindmethod(),
                    n.start()
                }
                ,
                this.fillLeftTable = function(t, e) {
                    t.push("<table id='leftTable' class='leftTable' cellpadding='0' cellspacing='0'><tr>");
                    for (var o in g)
                        if ("extend" != o) {
                            if (this.curCollectID != this.NobleCollectID && "StockAlert" == o)
                                continue;
                            t.push(e == T.lastSortIndex ? "<th index='" + e + "'>" + allField[o].name + T.upThSortClass() + "</th>" : 1 == allField[o].sort ? "<th index='" + e + "'>" + allField[o].name + "<i>&nbsp;</i></th>" : "<th index='" + e + "'>" + allField[o].name + "</th>"),
                            e++
                        }
                    t.push("</tr>");
                    for (var n = 0; n < T.stockList.length; n++) {
                        t.push("<tr index='" + n + "'>");
                        for (var i in g)
                            if ("extend" != i) {
                                if (this.curCollectID != this.NobleCollectID && "StockAlert" == i)
                                    continue;
                                t.push("<td>" + T.stockList[n][i] + "</td>")
                            }
                        t.push("</tr>")
                    }
                    t.push("</table>")
                }
                ,
                this.fillRightTable = function(t) {
                    t.push("<table id='rightTable' class='rightTable' cellpadding='0' cellspacing='0'><tr>");
                    for (var e in y)
                        if (allField[e].name) {
                            if (this.curCollectID != this.NobleCollectID && "StockAlert" == e)
                                continue;
                            t.push("<th>" + allField[e].name + "</th>")
                        }
                    t.push("</tr>");
                    for (var o = 0; o < T.stockList.length; o++) {
                        t.push("<tr>");
                        for (var n in y)
                            if ("extend" != n) {
                                if (this.curCollectID != this.NobleCollectID && "StockAlert" == n)
                                    continue;
                                t.push("<td>" + T.stockList[o][n] + "</td>")
                            }
                        t.push("</tr>")
                    }
                    t.push("</table>")
                }
                ,
                T.fillMainTable = function(t, e) {
                    t.push("<table id='mainTable' class='mainTable' cellpadding='0' cellspacing='0'><tr>");
                    for (var o in mainTableField[T.funcName])
                        if ("extend" != o)
                            if (allField[o])
                                t.push(e == T.lastSortIndex ? "<th rowspan='2' class='k1' index='" + e + "'>" + allField[o].name + T.upThSortClass() + "</th>" : "<th rowspan='2' class='k1' index='" + e + "'>" + allField[o].name + "<i>&nbsp;</i></th>"),
                                e++;
                            else {
                                var n = ""
                                  , i = 0
                                  , a = mainTableField[T.funcName];
                                a = a[o];
                                for (var s in a)
                                    if ("extend" != s) {
                                        n = allField[s].parentName;
                                        break
                                    }
                                for (var s in a)
                                    "extend" != s && i++;
                                t.push('<th class="k2" colspan="' + i + '">' + n + "</th>")
                            }
                    t.push("</tr><tr>");
                    for (var o in mainTableField[T.funcName])
                        if (o.indexOf("COLSPAN") >= 0)
                            for (var l in mainTableField[T.funcName][o])
                                "extend" != l && (t.push(e == T.lastSortIndex ? "<th index='" + e + "' height='20' class='k3'>" + allField[l].name + T.upThSortClass() + "</th>" : "<th index='" + e + "' height='20' class='k3'>" + allField[l].name + "<i>&nbsp;</i></th>"),
                                e++);
                    t.push("</tr>");
                    for (var r = 0; r < T.stockList.length; r++) {
                        t.push("<tr workid='" + T.stockList[r].WorkID + "'>");
                        for (var c in mainTableField[T.funcName])
                            if ("extend" != c)
                                if (c.indexOf("COLSPAN") >= 0)
                                    for (var d in mainTableField[T.funcName][c])
                                        "extend" != d && t.push("none" == T.sortState || T.lastKey != d ? "<td>" + T.stockList[r][d] + "</td>" : "<td style='background-color: #dcdcdc;'>" + T.stockList[r][d] + "</td>");
                                else
                                    t.push("none" == T.sortState || T.lastKey != c ? "<td>" + T.stockList[r][c] + "</td>" : "<td style='background-color: #dcdcdc;'>" + T.stockList[r][c] + "</td>");
                        t.push("</tr>")
                    }
                    return t.push("</table>"),
                    e
                }
                ,
                this.textSort = {
                    none: "默认排序中，点击表头可更改排序状态（拖动每行可以自定义排序）",
                    up: "升序排序中（非默认状态下不可拖动）",
                    down: "降序排序中（非默认状态下不可拖动）"
                },
                this.initSortEvent = function() {
                    function t(t) {
                        for (var o = document.getElementById(t).getElementsByTagName("a"), n = [], i = 0; o[i]; )
                            o[i].getAttribute("sortkey") && n.push(o[i]),
                            i++;
                        for (var a in n)
                            n[a].onclick = function() {
                                "none" == T.sortState && s(T.stockList, T.oldStockList),
                                T.lastSortIndex != this.parentNode.getAttribute("index") && (T.sortState = "none"),
                                T.sortState = e[T.sortState],
                                T.Drag = "none" === T.sortState ? !0 : !1,
                                usefulDom.sortInfo.innerHTML = T.textSort[T.sortState],
                                l(T.sortState),
                                T.lastSortIndex = this.parentNode.getAttribute("index"),
                                T.sortStockListByKey(this.getAttribute("sortkey"))
                            }
                    }
                    var e = {
                        none: "up",
                        up: "down",
                        down: "none"
                    };
                    t("leftTable"),
                    t("rightTable"),
                    t("mainTable")
                }
                ,
                T.initAlertData = function() {
                    T.firstBlood ? loadScript("http://mystock.eastmoney.com/Message.aspx?type=stockprice&cb=alertInit&" + Math.random()) : alertInit(T.cacheAlertData)
                }
                ,
                T.resetLocation = function() {
                    var t = !0
                      , e = !0;
                    if (0 != T.targetCollectionID) {
                        for (var o = usefulDom.tab1.getElementsByTagName("li"), n = 0; n < o.length; n++)
                            o[n].getAttribute("lid") == T.targetCollectionID && (T.targetCollectionID = 0,
                            o[n].onclick(),
                            t = !1,
                            e = !1);
                        if (t)
                            for (var i = usefulDom.moreCollect.getElementsByTagName("span"), n = 0; n < i.length; n++)
                                if (i[n].getAttribute("lid") == T.targetCollectionID) {
                                    T.targetCollectionID = 0,
                                    i[n].onclick(),
                                    e = !1;
                                    break
                                }
                        e && (T.targetCollectionID = T.NobleCollectID,
                        usefulDom.tab1.firstChild.onclick())
                    }
                }
                ,
                this.upThSortClass = function() {
                    var t = "<i></i>";
                    return "up" == T.sortState && (t = "<i class='iconupsort'></i>"),
                    "down" == T.sortState && (t = "<i class='icondownsort'></i>"),
                    t
                }
                ,
                this.initVS = function() {
                    function t(t) {
                        for (var e = document.getElementById("mainTable").getElementsByTagName("td"), o = 0, n = 0; t > n; n++)
                            o += e[n].offsetWidth;
                        return o
                    }
                    usefulDom.allTable.onselectstart = function() {
                        return !1
                    }
                    ,
                    T.vsIndex = 0;
                    document.getElementById("mainTable");
                    document.getElementById("mainTableSLeft").onclick = function() {
                        return T.vsIndex <= 0 ? void (usefulDom.allTable.scrollLeft = 0) : (T.vsIndex--,
                        void (usefulDom.allTable.scrollLeft = t(T.vsIndex)))
                    }
                    ,
                    document.getElementById("mainTableSRight").onclick = function() {
                        var e = document.getElementById("mainTable").getElementsByTagName("th");
                        T.vsIndex >= e.length - 1 || (T.vsIndex++,
                        t(T.vsIndex + 1) + f >= document.getElementById("mainTable").offsetWidth ? (T.vsIndex--,
                        usefulDom.allTable.scrollLeft = document.getElementById("mainTable").offsetWidth - f) : usefulDom.allTable.scrollLeft = t(T.vsIndex))
                    }
                }
                ,
                this.sortStockListByKey = function(t) {
                    var e, o, n = null, i = T.stockList.length, a = T.stockList;
                    if ("none" == T.sortState)
                        return s(T.oldStockList, T.stockList),
                        T.sortStar(),
                        void T.fillAll();
                    if ("up" == T.sortState)
                        for (e = 0; i - 2 >= e; e++)
                            for (o = i - 1; o >= 1; o--)
                                _(a[o]["data_" + t], a[o - 1]["data_" + t]) && (n = a[o],
                                a[o] = a[o - 1],
                                a[o - 1] = n);
                    if ("down" == T.sortState)
                        for (e = i - 1; e >= 1; e--)
                            for (o = e - 1; o >= 0; o--)
                                C(a[e]["data_" + t], a[o]["data_" + t]) && (n = a[e],
                                a[e] = a[o],
                                a[o] = n);
                    T.lastKey = t,
                    T.fillAll()
                }
                ,
                this.tableColor = function() {
                    if (0 != T.stockList.length && void 0 != document.getElementById("leftTable"))
                        for (var t = usefulDom.allTable.getElementsByTagName("table"), e = t[1].getElementsByTagName("tr").length, o = 1; e > o; o++)
                            o % 2 == 0 ? (t[0].getElementsByTagName("tr")[o + 1].className = "bgcolor",
                            t[1].getElementsByTagName("tr")[o].className = "bgcolor",
                            t[2].getElementsByTagName("tr")[o].className = "bgcolor") : (t[0].getElementsByTagName("tr")[o + 1].className = "",
                            t[1].getElementsByTagName("tr")[o].className = "",
                            t[2].getElementsByTagName("tr")[o].className = "")
                }
                ,
                this.getNews = function() {
                    if (0 != T.stockList.length) {
                        for (v(),
                        _stocks = [],
                        i = 0; T.stockList[i]; )
                            _stocks.push(T.stockList[i]._Code),
                            i++;
                        c.show(_stocks.join(","), _currentType)
                    } else {
                        usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>";
                        try {
                            _stocks = []
                        } catch (t) {}
                        switch (_currentType) {
                        case 0:
                            $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无帖子</div>');
                            break;
                        case "1":
                            $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无新闻</div>');
                            break;
                        case "3":
                            $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无公告</div>');
                            break;
                        case "2":
                            $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无研报</div>')
                        }
                    }
                }
                ,
                window.getNewsData = function() {
                    return !1
                }
                ,
                window.appendNews = function() {}
                ,
                window.appendNews2 = function(t) {
                    t.rc ? (_infoItems.set(_currentType, t.re),
                    initPages()) : (jQuery("#info-wrap").html("暂无数据"),
                    EMui.tip(t.me, "提示"))
                }
                ,
                window.initPages = function() {
                    try {
                        jQuery("#info-wrap").empty()
                    } catch (t) {
                        jQuery("#info-wrap")[0].innerHTML = ""
                    }
                    _pages = Math.ceil(_infoItems.get(_currentType).length / _cnt);
                    var e = jQuery("select");
                    e.empty().show();
                    for (var o = 0; o < _pages; o++)
                        e.append("<option>第" + (o + 1) + "页</option>");
                    goPage()
                }
                ,
                window.goPage = function() {
                    var t = jQuery("#st-info-pager").children().first()[0]
                      , e = jQuery("#st-info-pager").children().last()[0]
                      , o = jQuery("select");
                    if (_currentPage > _pages)
                        return void (_currentPage = _pages);
                    jQuery("#info-wrap").empty();
                    for (var n = (_currentPage - 1) * _cnt; n < _currentPage * _cnt; n++)
                        n < _infoItems.get(_currentType).length && b(_tpl, _infoItems.get(_currentType)[n]);
                    o.val("第" + _currentPage + "页"),
                    t.style.display = _currentPage > 1 ? "" : "none",
                    e.style.display = _currentPage > _pages - 1 ? "none" : "",
                    _currentPage > 1 && window.scrollTo(0, document.getElementById("stockInfo").offsetTop - 10)
                }
                ,
                window.opCallback = function(t) {
                    if (!t.rc)
                        return void EMui.tip(t.me, "操作提示");
                    switch (_currentTarget.innerHTML) {
                    case "赞":
                        var e = jQuery(_currentTarget).next().html();
                        jQuery(_currentTarget).next().html(parseInt(e) + 1),
                        _currentTarget.innerHTML = "已赞";
                        break;
                    case "已赞":
                        var e = jQuery(_currentTarget).next().html();
                        jQuery(_currentTarget).next().html(parseInt(e) - 1),
                        _currentTarget.innerHTML = "赞";
                        break;
                    case "转发":
                        jQuery("#modal-mask").hide(),
                        jQuery("#modal").hide(),
                        jQuery("#modal textarea").val("");
                        var e = jQuery(_currentTarget).next().html();
                        jQuery(_currentTarget).next().html(parseInt(e) + 1),
                        EMui.tip("转发成功！", "操作提示");
                        break;
                    case "收藏":
                        _currentTarget.innerHTML = "已收藏",
                        EMui.tip("收藏成功！", "操作提示");
                        break;
                    case "已收藏":
                        _currentTarget.innerHTML = "收藏",
                        EMui.tip("已取消收藏！", "操作提示");
                        break;
                    case "评论":
                        var e = jQuery(_currentTarget).next().html();
                        jQuery(_currentTarget).next().html(parseInt(e) + 1),
                        EMui.tip(t.me, "操作提示")
                    }
                }
                ,
                this.getNewsCB = function() {
                    var t = mainPostForZxg;
                    if (t)
                        if (t.mainPost && t.mainPost.length > 0) {
                            for (var e = "", o = 0; o < t.mainPost.length && (e += "<li><span class='fr'>" + t.mainPost[o].time.slice(5, 10) + "</span><a target='_blank' class='st-a-black' href='http://guba.eastmoney.com/list," + t.mainPost[o].code + ".html'>[" + t.mainPost[o].name + "]</a><a class='st-a-normal' target='_blank' href='" + t.mainPost[o].articleUrl + "' title='" + t.mainPost[o].title + "'>" + t.mainPost[o].title + "</a></li>",
                            14 != o); o++)
                                ;
                            usefulDom.newsMainPost.innerHTML = e
                        } else
                            usefulDom.newsMainPost.innerHTML = "<li>暂无帖子</li>";
                    else
                        alert("获取股吧新闻失败")
                }
                ,
                T.initPage = function(t) {
                    t = t || 0,
                    T.getMyCollections()
                }
                ,
                T.loadingShow = function() {
                    usefulDom.allTable.innerHTML = "<div class='stock-loading-div'><img src='" + t.loadingGIF + "'/></div>"
                }
                ,
                this.initButtonGroup = function() {
                    var t = usefulDom.fucBtns.getElementsByTagName("li");
                    for (var o in t)
                        t[o].onclick = function() {
                            var t = this.getAttribute("callName");
                            return 0 != T.loginState || "CTDDE" != t && "CTPF" != t && "CTFA" != t ? (T.loadingShow(),
                            e(this),
                            this.getAttribute("callName") ? (T.jumpToDgtl = !1,
                            T.funcName = this.getAttribute("callName"),
                            setCookie("funcName", T.funcName, 365),
                            setCookie("jumpToDgtl", T.jumpToDgtl, 365),
                            T.getStockInfoList(this.getAttribute("callName"))) : (setCookie("funcName", T.funcName, 365),
                            setCookie("jumpToDgtl", T.jumpToDgtl, 365),
                            T.jumpToDgtl = !0,
                            h.init(!0)),
                            void n.resetpar()) : void p()
                        }
                }
                ,
                T.initButtonCollection = function() {
                    var t = usefulDom.tab1.getElementsByTagName("li");
                    for (var e in t)
                        t[e].onclick = function() {
                            T.loadingShow(),
                            T.firstBlood = !0,
                            T.oldStockList.splice(0, T.oldStockList.length),
                            T.selectCollection(getPosition(this)),
                            l(T.sortState),
                            setCookie("curCollectID", T.curCollectID, 365)
                        }
                        ;
                    var o = document.getElementById("moveLotOfStock").getElementsByTagName("span");
                    for (var n in o)
                        o[n].onclick = function() {
                            T.moveStock(this.getAttribute("lid"), T.getCheckedString())
                        }
                }
                ,
                this.getMyCollections = function() {
                    loadScript(t.stockUrl + "f=gg&cb=mySelectCollection&" + Math.random())
                }
                ,
                this.getCollectionInfoById = function(e, o) {
                    0 == T.targetCollectionID && loadScript("/stockeast/mystock?f=gs&cb=getStockInfo&g=" + e + "&" + Math.random(), o)
                }
                ,
                this.getStockInfoList = function(e) {
                    if (usefulDom.allpics.style.display = "none",
                    usefulDom.allTable.style.display = "block",
                    t.usefulDom.totalWar.style.display = "none",
                    e = e || defaultKey,
                    T.funcName = e,
                    0 == T.stockList.length)
                        return void (usefulDom.allTable.innerHTML = "<div class='st-blank f14'>该组合暂无股票，请从上方添加</div>");
                    if ("DGTL" == e)
                        return usefulDom.morePage.style.display = "none",
                        void h.init(!0);
                    T.sortState = "none",
                    l(T.sortState),
                    0 != T.oldStockList.length && (T.sortStockListByKey(1),
                    T.Drag = "none" === T.sortState ? !0 : !1,
                    usefulDom.sortInfo.innerHTML = T.textSort[T.sortState]);
                    for (var o = [], n = 0; n < T.stockList.length; n++)
                        o.push(T.stockList[n]._Code + parseInt(T.stockList[n]._MarketType).toString());
                    if (t.allNote = {
                        data: {}
                    },
                    !T.loginState)
                        for (var i = 0; T.allStockArray[i]; ) {
                            var a = _gzNum(T.allStockArray[i]._Code)
                              , s = _gzPrice(T.allStockArray[i]._Code)
                              , r = _gzFee(T.allStockArray[i]._Code);
                            T.allStockArray[i]._note = 0 == a && 0 == s && 0 == r ? -1 : 1,
                            T.allStockArray[i].parseCommonData(),
                            t.allNote.data[T.allStockArray[i].WorkID] = {
                                node: a + "|" + s + "|0|" + r + "|1|0|1"
                            },
                            i++
                        }
                    if ("YKYL" == e) {
                        if (T.loginState) {
                            for (var c = [], n = 0; T.stockList[n]; )
                                c.push(T.stockList[n].WorkID),
                                n++;
                            var d = "https://myfavor.eastmoney.com/mystock?f=gsb&g=" + T.curCollectID + "&sc=" + c + "&cb=NoteAllhandlerCallback&" + Math.random();
                            return void loadScript(d, function() {
                                loadScript(t.hpUrl + "type=CT&cmd=" + o.join(",") + "&sty=" + defaultKey + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function() {
                                    "DGTL" != T.funcName && (T.fillAll(e),
                                    T.initPageNums(),
                                    T.totalWar(),
                                    t.usefulDom.totalWar.style.display = "block")
                                })
                            })
                        }
                        return void loadScript(t.hpUrl + "type=CT&cmd=" + o.join(",") + "&sty=" + defaultKey + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function() {
                            "DGTL" != T.funcName && (T.fillAll(e),
                            T.initPageNums(),
                            T.totalWar(),
                            t.usefulDom.totalWar.style.display = "block")
                        })
                    }
                    loadScript(t.hpUrl + "type=CT&cmd=" + o.join(",") + "&sty=" + e + "&cb=getStockFullInfo&js=([(x)])&" + Math.random(), function() {
                        "DGTL" != T.funcName && (T.fillAll(e),
                        T.curCollectID == T.NobleCollectID && alertInit1(),
                        T.initPageNums())
                    })
                }
                ,
                T.addLot = function() {
                    var e = getCookie("emhq_stock");
                    if (!e)
                        return void EMui.tip("没有股票", "操作失败");
                    e = e.split(",");
                    for (var o = "", n = 0; n < e.length; n++)
                        o += e[n] + "|" + a(e[n]) + "|01,";
                    loadScript(t.stockUrl + "f=aslot&g=" + T.curCollectID + "&sc=" + o + "&cb=addLothandlerCallback&" + Math.random())
                }
                ;
                var w = function() {
                    T.editCollectDiv.style.display = "block"
                }
                  , S = function() {
                    T.editCollectDiv.style.display = "none"
                };
                this.initEditCollection = function() {
                    T.curCollectDom && (reworker(T.curCollectDom, "mouseenter", w),
                    reworker(T.curCollectDom, "mouseleave", S));
                    usefulDom.tab1.getElementsByTagName("li");
                    onworker(T.curCollectDom, "mouseenter", w),
                    onworker(T.curCollectDom, "mouseleave", S);
                    var t = usefulDom.tab1.getElementsByTagName("span");
                    for (var e in t)
                        t[e].onclick = function(t) {
                            if (t = t || window.event,
                            window.event ? t.cancelBubble = !0 : t.stopPropagation(),
                            this.className.indexOf("delete") >= 0) {
                                if (0 == T.loginState)
                                    return void p();
                                EMui.confirm({
                                    title: "确认删除？",
                                    html: "确认删除该组合"
                                }, function() {
                                    myStock.delCollection(T.curCollectID)
                                })
                            } else
                                this.className.indexOf("rename") >= 0 && EMui.prompt({
                                    title: "请重新输入组合名称"
                                }, function(t) {
                                    myStock.tryCollectionName = t,
                                    myStock.reNameCollection(T.curCollectID, t)
                                })
                        }
                }
                ,
                this.initStockBtns = function() {
                    var e = document.getElementById("rightTable").getElementsByTagName("i");
                    for (var o in e)
                        e[o].onclick = function() {
                            if (this.className.indexOf("del") >= 0)
                                return void T.delStock(this.getAttribute("scode") + "|0" + this.getAttribute("smk") + "|" + this.getAttribute("sty"));
                            if (this.className.indexOf("note") >= 0) {
                                if (T.loginState)
                                    return u.domPostion = t.getPosition(this.parentNode.parentNode),
                                    coverbg(),
                                    void T.getNote(this.getAttribute("scode") + "|0" + this.getAttribute("smk") + "|" + this.getAttribute("sty"), this);
                                t.curUnNoteCode = this.getAttribute("scode"),
                                unLogFuc.cNotePadShow()
                            }
                        }
                        ;
                    var n = document.getElementById("leftTable").getElementsByTagName("tr")[0];
                    n.getElementsByTagName("input")[0].onclick = function() {
                        var t = document.getElementById("leftTable").getElementsByTagName("input");
                        for (var e in t)
                            t[e].checked = this.checked
                    }
                    ;
                    var i = document.getElementById("leftTable").getElementsByTagName("i");
                    for (var a in i)
                        i[a].onclick = function() {
                            return this.className.indexOf("alert") >= 0 ? 0 == T.loginState ? void p() : (m.domPostion = t.getPosition(this.parentNode.parentNode),
                            m.type = 1,
                            coverbg(),
                            void T.getTiXing(this.getAttribute("scode"), this.getAttribute("smk"), this)) : void (this.className.indexOf("star") >= 0 && (T.starLast = this.parentNode.parentNode.getAttribute("index"),
                            loadScript(t.stockUrl + "f=mst&g=" + T.curCollectID + "&sc=" + this.getAttribute("workID") + "&cb=starCallback&" + Math.random()),
                            T.starLastFuc = this.className.indexOf("on") > 0 ? -1 : 1))
                        }
                }
                ,
                this.addCollection = function(e) {
                    loadScript(t.stockUrl + "f=ag&gn=" + encodeURI(e) + "&cb=addCollectionCallback&" + Math.random(), function() {
                        setCookie("curCollectID", T.curCollectID, 365)
                    })
                }
                ,
                this.delCollection = function(e) {
                    loadScript(t.stockUrl + "f=dg&g=" + e + "&cb=deleteCollectionCallback&" + Math.random(), function() {
                        setCookie("curCollectID", T.curCollectID, 365)
                    })
                }
                ,
                this.reNameCollection = function(e, o) {
                    loadScript(t.stockUrl + "f=mg&g=" + e + "&gn=" + encodeURI(o) + "&cb=renameCollectionCallback&" + Math.random())
                }
                ,
                this.addStock = function(e) {
                    T.toAddStockId = e,
                    T.loginState ? loadScript(t.stockUrl + "f=as&g=" + T.curCollectID + "&sc=" + e + "&cb=addhandlerCallback&" + Math.random()) : unLogFuc.cAddStock(e)
                }
                ,
                this.delStock = function(e) {
                    T.curDelStockID = e;
                    var o = new r({
                        title: "确认删除",
                        content: "确认删除选中股票？",
                        chooseyes: function() {
                            if ("undefined" == typeof T.curDelStockID || "" == T.curDelStockID || 0 == T.curDelStockID.split(",").length)
                                return void EMui.tip("请选择股票", "操作失败");
                            if (T.curDelStockID.split(",").length > 1) {
                                if (T.curCollectID == T.NobleCollectID)
                                    for (var e = T.curDelStockID.split(","), o = 0; o < e.length; o++) {
                                        var n = myStock.alertStock.indexOf(e[o].split("|")[0]);
                                        -1 != n && myStock.alertStock.splice(n, 1)
                                    }
                                T.loginState ? loadScript(t.stockUrl + "f=dslot&g=" + T.curCollectID + "&sc=" + T.curDelStockID + "&cb=delhandlerCallback&" + Math.random()) : (unLogFuc.cDelStock(T.curDelStockID),
                                delhandlerCallback({
                                    data: {
                                        msg: "删除成功！"
                                    },
                                    result: 1
                                }))
                            } else {
                                if (T.curCollectID == T.NobleCollectID && T.loginState) {
                                    var e = myStock.alertStock.indexOf(T.curDelStockID.split("|")[0]);
                                    -1 != e && myStock.alertStock.splice(e, 1)
                                }
                                T.loginState ? loadScript(t.stockUrl + "f=ds&g=" + T.curCollectID + "&sc=" + T.curDelStockID + "&cb=delhandlerCallback&" + Math.random()) : (unLogFuc.cDelStock(T.curDelStockID),
                                delhandlerCallback({
                                    data: {
                                        msg: "删除成功！"
                                    },
                                    result: 1
                                }))
                            }
                        }
                    });
                    o.show()
                }
                ,
                this.getNote = function(e, o) {
                    T.curNoteStockID = e,
                    u.code = e.split("|")[0],
                    u.currentDom = o,
                    loadScript(t.stockUrl + "f=gsb&g=" + T.curCollectID + "&sc=" + T.curNoteStockID + "&cb=NotehandlerCallback&" + Math.random())
                }
                ,
                this.getTiXing = function(t, e, o) {
                    e = 2 == e ? 0 : e,
                    loadScript("http://mystock.eastmoney.com/Message.aspx?type=getsettings&code=" + e + "," + t + "&" + Math.random(), function() {
                        e = 0 == e ? 2 : e;
                        var n = hpUrl + "type=CT&cmd=" + t + e + "&sty=" + defaultKey + "&cb=singleStock&js=([(x)])&" + Math.random();
                        loadScript(n, function() {
                            e = 2 == e ? 0 : e,
                            m.code = t,
                            m.market = e,
                            o && (m.currentDom = o),
                            m.init(),
                            m.fill(data, t, e)
                        })
                    })
                }
                ,
                this.loadPage = function() {
                    T.stockList.splice(0, T.stockList.length),
                    T.oldStockList.splice(0, T.oldStockList.length);
                    for (var t = parseInt(T.pageIndex), e = t * T.pageSize, o = 0; T.allStockArray[e] && o < T.pageSize; )
                        T.stockList.push(T.allStockArray[e]),
                        e++,
                        o++;
                    T.sortStar()
                }
                ,
                this.initPageNums = function() {
                    T.maxPageNum = Math.ceil(T.allStockArray.length / T.pageSize);
                    var t = ""
                      , e = 0
                      , o = !0
                      , n = !0
                      , i = parseInt(T.showPageNum / 2);
                    if (T.allStockArray.length <= T.pageSize)
                        return void (usefulDom.morePage.style.display = "none");
                    usefulDom.morePage.style.display = "block",
                    0 == T.pageIndex || (t += "<span page='" + (T.pageIndex - 1) + "'>上一页</span>");
                    for (var e = 1; e <= T.maxPageNum; e++) {
                        var a = e - 1 == T.pageIndex ? "cur" : "";
                        1 != e && e != T.maxPageNum ? e <= T.pageIndex - i ? o && (t += "<span page='none'>...</span>",
                        o = !1) : e >= T.pageIndex - i && e <= T.pageIndex + i ? t += "<span class='" + a + "' page='" + (e - 1) + "'>" + e + "</span>" : e >= T.maxPageNum - i && n && (t += "<span page='none'>...</span>",
                        n = !1) : t += "<span class='" + a + "' page='" + (e - 1) + "'>" + e + "</span>"
                    }
                    T.pageIndex >= T.maxPageNum - 1 || (t += "<span page='" + (T.pageIndex + 1) + "'>下一页</span>"),
                    usefulDom.morePage.innerHTML = t;
                    for (var s = usefulDom.morePage.getElementsByTagName("span"), l = 0; s[l]; )
                        s[l].onclick = function() {
                            isNaN(this.getAttribute("page")) || (T.pageIndex = parseInt(this.getAttribute("page")),
                            T.loadPage(),
                            T.getStockInfoList(T.funcName))
                        }
                        ,
                        l++
                }
                ,
                this.moveStock = function(e, o) {
                    "" != o && void 0 != o && (T.toCollectID = e,
                    T.targetCollectionID = e,
                    loadScript(t.stockUrl + "f=ms&g1=" + T.curCollectID + "&g=" + e + "&sc=" + o + "&cb=movehandlerCallback&" + Math.random()))
                }
                ,
                this.totalWar = function() {
                    function t(t) {
                        return null == t || void 0 == t ? 0 : (t = t.toString(),
                        isNaN(t) ? 0 : parseFloat(t))
                    }
                    for (var e = T.stockList, o = 0, n = 0, i = 0, a = 0, s = 0, l = 0, r = 0; e[o]; )
                        n += t(e[o].data_cyl),
                        i += t(e[o].data_mrcb),
                        s += t(e[o].data_mgyk),
                        a += t(e[o].data_dqsz),
                        l += t(e[o].data_ykl),
                        r += t(e[o].data_fdyk),
                        o++;
                    document.getElementById("total_cyl").innerHTML = numFormatRule["default"](n, 0),
                    document.getElementById("total_mrcb").innerHTML = numFormatRule["default"](i),
                    document.getElementById("total_mgyk").innerHTML = numFormatRule["default"](s),
                    document.getElementById("total_dqsz").innerHTML = numFormatRule["default"](a),
                    document.getElementById("total_ykl").innerHTML = 0 == i ? "0%" : 100 * numFormatRule["default"](a / i - 1) + "%",
                    document.getElementById("total_fdyk").innerHTML = numFormatRule["default"](r)
                }
                ,
                this.sortStar = function() {
                    for (var t = this.stockList.length, e = this.stockList, o = [], n = 0, i = []; e[n]; )
                        1 == e[n]._star ? o.push(e[n]) : i.push(e[n]),
                        n++;
                    this.stockList.splice(0, t),
                    this.stockList = o.concat(i)
                }
                ,
                this.delElementFormList = function(t) {
                    if (t) {
                        for (var e = 0; e < T.allStockArray.length; e++)
                            t.indexOf(T.allStockArray[e].WorkID) >= 0 && (T.allStockArray.splice(e, 1),
                            e--);
                        for (var e = 0; e < T.stockList.length; e++)
                            t.indexOf(T.stockList[e].WorkID) >= 0 && (T.stockList.splice(e, 1),
                            e--)
                    }
                }
                ,
                this.getCheckedString = function() {
                    if (document.getElementById("leftTable")) {
                        for (var t = document.getElementById("leftTable").getElementsByTagName("input"), e = [], o = 0; o < t.length; o++)
                            t[o] && t[o].getAttribute("workID") && t[o].checked && e.push(t[o].getAttribute("workID"));
                        return e.join(",")
                    }
                }
                ,
                T.loadingShow(),
                T.initPage(),
                document.getElementById("refreshALLALLALL").onclick = function() {
                    window.location.reload()
                }
            };
            t.myStock = new v,
            document.getElementById("delLotOfStock").onclick = function() {
                myStock.delStock(myStock.getCheckedString())
            }
            ,
            document.getElementById("addLot").onclick = function() {
                myStock.addLot()
            }
            ,
            document.getElementById("checkALL").onclick = function() {
                var t = document.getElementById("myOwnInputCode").value
                  , e = "http://quote.eastmoney.com/search.html?stockcode=" + t;
                window.open(e)
            }
            ,
            onworker(document.getElementById("topmoreCollectionSelect"), "mouseenter", function() {
                usefulDom.moreCollect.style.display = "block"
            }),
            onworker(document.getElementById("topmoreCollectionSelect"), "mouseleave", function() {
                usefulDom.moreCollect.style.display = "none"
            }),
            document.getElementById("addCollectionBtn").onclick = function() {
                EMui.prompt({
                    title: "请输入组合名(最多6个汉字或12个字符)"
                }, function(t) {
                    t.replace(/[^\x00-\xff]/g, "xx").length <= 12 && t.replace(/[^\x00-\xff]/g, "xx").length > 0 ? myStock.addCollection(t) : EMui.tip("名称不符合规范", "操作失败")
                })
            }
            ,
            t.codeToMarket = a,
            document.getElementById("directAddStock").onclick = function() {
                var t = document.getElementById("myOwnInputCode").value
                  , e = "01";
                t.match(/^\d{5,6}$/) ? myStock.addStock(t + "|" + a(t) + "|" + e) : EMui.tip("请填写股票代码", "操作失败")
            }
            ,
            onworker(document.getElementById("moveLotOfStock"), "mouseenter", function() {
                document.getElementById("allSCollections").style.display = "block"
            }),
            onworker(document.getElementById("moveLotOfStock"), "mouseleave", function() {
                document.getElementById("allSCollections").style.display = "none"
            }),
            setTimeout(function() {
                {
                    var t = {
                        text: "输代码、名称或简拼",
                        autoSubmit: !1,
                        width: 213,
                        header: ["选项", "代码", "名称", "类型"],
                        body: [-1, 1, 4, -2],
                        type: "ABSTOCK",
                        callback: function(t) {
                            myStock.addStock(t.code + "|" + a(t.code) + "|01")
                        }
                    };
                    new StockSuggest("myOwnInputCode",t)
                }
            }, 500);
            var b = 0;
            document.getElementById("navigatorHide").onclick = function() {
                b % 2 ? (this.innerHTML = "收起导航",
                this.className = "navi-close",
                jQuery("#fir-title").css("margin-left", "10px"),
                jQuery("#myOwnInputCode").css("margin-left", "55px"),
                document.getElementById("stockLeft").style.display = "block",
                document.getElementById("stockMain").style.width = "1015px",
                t.usefulDom.tab1.style.width = "1015px",
                t.usefulDom.allTable.style.width = "1015px",
                f = 464,
                b++,
                d.del("hqzk")) : (this.innerHTML = "展开导航",
                this.className = "navi-open",
                jQuery("#myOwnInputCode").css("margin-left", "150px"),
                document.getElementById("stockLeft").style.display = "none",
                document.getElementById("stockMain").style.width = "1100px",
                t.usefulDom.tab1.style.width = "1100px",
                t.usefulDom.allTable.style.width = "1100px",
                f = 650,
                b++,
                d.save("hqzk", "1", 180))
            }
            ,
            "1" == d.get("hqzk") && (document.getElementById("navigatorHide").innerHTML = "展开导航",
            document.getElementById("navigatorHide").className = "navi-open",
            jQuery("#myOwnInputCode").css("margin-left", "150px"),
            document.getElementById("stockLeft").style.display = "none",
            document.getElementById("stockMain").style.width = "1100px",
            t.usefulDom.tab1.style.width = "1100px",
            t.usefulDom.allTable.style.width = "1100px",
            f = 650,
            b++)
        }(window),
        function() {
            function t() {
                function t(t) {
                    return t = t || window.event,
                    t.pageY ? t.pageY - y - f : t.clientY + document.documentElement.scrollTop - y - f
                }
                function e() {
                    if (T.length)
                        for (var t = 0; t < T.length; t++)
                            T[t].style.visibility = "hidden"
                }
                function o() {
                    if (T.length)
                        for (var t = 0; t < T.length; t++)
                            T[t].style.visibility = ""
                }
                function n(t, e) {
                    t--,
                    e--;
                    var o = t + myStock.pageIndex * myStock.pageSize
                      , n = e + myStock.pageIndex * myStock.pageSize;
                    if (!(0 > t || 0 > e || isNaN(t) || isNaN(e) || t == e || void 0 == myStock.stockList[t] || void 0 == myStock.stockList[e])) {
                        var i = myStock.stockList.splice(e, 1)[0];
                        myStock.stockList.splice(t, 0, i);
                        var a = myStock.allStockArray.splice(n, 1)[0];
                        myStock.allStockArray.splice(o, 0, a)
                    }
                }
                function i() {
                    var t = []
                      , e = T[1].getElementsByTagName("td")
                      , o = T[2].getElementsByTagName("td")
                      , n = T[0].getElementsByTagName("td");
                    t.push("<table class='mainTable' cellpadding='0' cellspacing='0'><tbody ><tr>");
                    for (var i = 0; i < o.length; i++)
                        t.push("<td><div style='width:" + (document.getElementById("mainTable").getElementsByTagName("td")[i].offsetWidth - 15) + "px;'>" + o[i].innerHTML + "</div></td>");
                    t.push("</tr></tbody></table>"),
                    t.push("<table class='leftTable' cellpadding='0' cellspacing='0'><tr>");
                    for (var i = 0; i < n.length; i++)
                        t.push("<td style='width:" + document.getElementById("leftTable").getElementsByTagName("td")[i].offsetWidth + "px;'>" + n[i].innerHTML + "</td>");
                    t.push("</tr></table>"),
                    t.push("<table class='rightTable' cellpadding='0' cellspacing='0'><tr>");
                    for (var i = 0; i < e.length; i++)
                        t.push("<td><div style='width: 32px'>" + e[i].innerHTML + "</div></td>");
                    return t.push("</tr></table>"),
                    t
                }
                function a(t) {
                    var e = Math.floor(t / g) + 1;
                    w != e && s(e)
                }
                function s(t) {
                    k = t,
                    d > 0 ? (l(T[0], document.getElementById("leftTable").getElementsByTagName("tr")[t]),
                    l(T[1], document.getElementById("rightTable").getElementsByTagName("tr")[t]),
                    l(T[2], document.getElementById("mainTable").getElementsByTagName("tr")[t + 1])) : 0 > d && (r(T[0], document.getElementById("leftTable").getElementsByTagName("tr")[t]),
                    r(T[1], document.getElementById("rightTable").getElementsByTagName("tr")[t]),
                    r(T[2], document.getElementById("mainTable").getElementsByTagName("tr")[t + 1])),
                    w = t
                }
                function l(t, e) {
                    var o = e.parentNode;
                    o.lastChild == e ? o.appendChild(t) : o.insertBefore(t, e.nextSibling)
                }
                function r(t, e) {
                    var o = e.parentNode;
                    o.insertBefore(t, e)
                }
                var c, d = 1, u = 0, m = function(t) {
                    return t.offsetParent.tagName.toLowerCase().match(/^body|html$/) ? t.offsetTop + t.offsetParent.clientTop : t.offsetTop + m(t.offsetParent)
                }, p = !0, h = 300, f = 41, g = 30, y = m(usefulDom.allTable), v = 0, b = 0, k = 0, _ = document.createElement("div");
                _.className = "myDargDivClass st-table",
                _.style.cursor = "move",
                usefulDom.allTable.parentNode.appendChild(_);
                var C, T = [], w = 0, S = {
                    star: function(t) {
                        return t > 0 && v > t
                    },
                    no: function(t) {
                        return t > v && t < myStock.stockList.length * g
                    }
                }, x = function(e) {
                    e = e || window.event;
                    var o = e.pageY || e.clientY + document.documentElement.scrollTop;
                    d = o - u;
                    var n = t(e);
                    _.style.display = "block",
                    S[C](n) && (_.style.top = n - c + f + "px",
                    a(n)),
                    u = e.pageY || e.clientY + document.documentElement.scrollTop
                };
                onworker(usefulDom.allTable, "mousedown", function(o) {
                    if (myStock.Drag && 0 != myStock.stockList.length) {
                        document.body.onselectstart = function() {
                            return !1
                        }
                        ,
                        o = o || window.event;
                        var n = o.srcElement || o.target;
                        if (!n || "a" != n.tagName.toLocaleLowerCase()) {
                            u = o.pageY || o.clientY + document.documentElement.scrollTop,
                            v = 30 * myStock.starLength;
                            var a = t(o);
                            if (!(0 > a)) {
                                C = v >= a ? "star" : "no",
                                c = a % g,
                                p = !0,
                                T.splice(0, T.length);
                                var s = Math.floor(a / g) + 1;
                                b = s,
                                k = s,
                                _.style.top = f + (b - 1) * g + "px",
                                setTimeout(function() {
                                    return p ? (w = s,
                                    void (void 0 != document.getElementById("leftTable").getElementsByTagName("tr")[s] && (T.push(document.getElementById("leftTable").getElementsByTagName("tr")[s]),
                                    T.push(document.getElementById("rightTable").getElementsByTagName("tr")[s]),
                                    T.push(document.getElementById("mainTable").getElementsByTagName("tr")[s + 1]),
                                    e(),
                                    _.innerHTML = "",
                                    _.innerHTML = i().join(""),
                                    onworker(document, "mousemove", x)))) : void T.splice(0, T.length)
                                }, h)
                            }
                        }
                    }
                }),
                onworker(document, "mouseup", function() {
                    document.body.onselectstart = function() {
                        return !0
                    }
                    ,
                    LastIndexTr = 0,
                    o(),
                    T.splice(0, T.length),
                    p = !1,
                    usefulDom.allTable.style.cursor = "default",
                    myStock.tableColor(),
                    _.style.display = "none",
                    _.innerHTML = "",
                    reworker(document, "mousemove", x),
                    n(k, b),
                    myStock.loginState ? N(b - k, b, myStock) : (N(b - k, b, myStock),
                    unLogFuc.cSaveList())
                });
                var N = function(t, e, o) {
                    if (document.getElementById("rightTable") && 0 != t) {
                        var n = document.getElementById("leftTable").getElementsByTagName("tr")[e - t].getElementsByTagName("td")[0].children[0];
                        if (e - t - 1 > 0)
                            var i = document.getElementById("leftTable").getElementsByTagName("tr")[e - t - 1].getElementsByTagName("td")[0].children[0];
                        if (e - t + 1 < myStock.stockList.length)
                            var a = document.getElementById("leftTable").getElementsByTagName("tr")[e - t + 1].getElementsByTagName("td")[0].children[0];
                        var s = n.getAttribute("scode")
                          , l = "0" + n.getAttribute("smk")
                          , r = n.getAttribute("sty");
                        if (i)
                            var c = i.getAttribute("scode")
                              , d = "0" + i.getAttribute("smk")
                              , u = i.getAttribute("sty");
                        if (a)
                            var m = a.getAttribute("scode")
                              , p = "0" + a.getAttribute("smk")
                              , h = a.getAttribute("sty");
                        (i || a) && (loadScript(i ? a ? stockUrl + "f=ss&g=" + o.curCollectID + "&sc=" + s + "|" + l + "|" + r + "&sc1=" + c + "|" + d + "|" + u + "&sc2=" + m + "|" + p + "|" + h + "&var=orderList&" + Math.random() : stockUrl + "f=ss&g=" + o.curCollectID + "&sc=" + s + "|" + l + "|" + r + "&sc1=" + c + "|" + d + "|" + u + "&var=orderList&" + Math.random() : stockUrl + "f=ss&g=" + o.curCollectID + "&sc=" + s + "|" + l + "|" + r + "&sc2=" + m + "|" + p + "|" + h + "&var=orderList&" + Math.random()),
                        b = 0,
                        k = 0)
                    }
                }
            }
            window.attachEvent ? window.attachEvent("onload", t) : window.addEventListener("load", t)
        }();
        var u = new function(e) {
            function o(t, e) {
                if ("" == t)
                    return !0;
                e.value = e.value.replace(/[^\d-]/g, ""),
                e.value = e.value.replace(/^\-/g, "");
                var o = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/
                  , n = t.match(o);
                if (null == n)
                    return !1;
                var i = new Date(n[1],n[3] - 1,n[4])
                  , a = i.getFullYear() + n[2] + (i.getMonth() + 1) + n[2] + i.getDate();
                return a == t
            }
            var n = "exChangeManage"
              , i = "exChangeStock"
              , a = "exTable"
              , s = "exChangeTips"
              , l = "exChangeNote"
              , r = "exChangeClose"
              , c = "saveExChange"
              , d = "closeExChange"
              , m = "";
            this.init = function() {
                n = t(n),
                n || (n = document.createElement("div"),
                n.id = "exChangeManage");
                var p = '<div id="exChangeTitle"><div class="exChangeManage">交易管理</div><div id="exChangeClose"></div></div><div class="hr3"></div><div id="exChangeStock"><div class="leftexChange"><span class="big">远望谷</span><span class="small">002136</span><span class="small">当前</span><span class="small">5.86</span></div><div class="rightexChange"><span class="small">我在</span><span class="small">2013-10-27</span><span class="small">当日价</span><span class="small">7.6</span><span class="small">添加该股</span></div></div><div class="hr"></div><table id="exTable" cellpadding="0" cellspacing="0" border="0" style="width: 700px;font-size:12px;text-align:center;border-collapse:collapse;border-spacing: 6px;"><thead><tr id="zhang"><td>数量</td><td>买入价</td><td>目标价</td><td>止损价</td><td>佣金‰</td><td>手续费（元）</td><td>印花税‰</td><td>交易（买入）日期</td></tr></thead><tbody><tr><td><input type="text" class="cssInput" maxlength="6" value="0"></td><td><input type="text"  class="cssInput" maxlength="6" value="0"></td><td><input type="text" class="cssInput" maxlength="6" value="0"></td><td><input type="text"  class="cssInput" maxlength="6" value="0"></td><td><input type="text" class="cssInput"  maxlength="6" value="0"></td><td><input type="text"  class="cssInput" maxlength="5" value="0"></td><td><input type="text"  class="cssInput" maxlength="6" value="0"></td><td><input type="text" class="cssInput"   maxlength="10"  style="width: 89px;"></td></tr></tbody></table><div id="exChangeTips"><span>日期格式：2013-1-1</span></div><div id="exChangeNote"><span class="exChangeNote">投资笔记:</span><span class="exChangewrite">您还可以输入<span>200</span>个字</span><div><textarea maxlength="200"></textarea></div><div id="saveExChange">保存</div><div id="closeExChange">关闭</div></div>';
                n.innerHTML = p,
                document.body.appendChild(n),
                n = t("exChangeManage");
                var h = document.documentElement.scrollTop || document.body.scrollTop;
                n.style.top = h + screen.height / 8 + "px",
                i = t("exChangeStock"),
                a = t("exTable"),
                s = t("exChangeTips"),
                l = t("exChangeNote"),
                r = t("exChangeClose"),
                c = t("saveExChange"),
                d = t("closeExChange"),
                currentStockObj = {},
                m = "",
                r.onclick = function() {
                    uncoverbg(),
                    document.body.removeChild(n)
                }
                ,
                d.onclick = function() {
                    uncoverbg(),
                    document.body.removeChild(n)
                }
                ;
                var f = a.getElementsByTagName("input");
                c.onclick = function() {
                    for (var t = 0; 6 > t; t++) {
                        var i = "";
                        if ("" == f[t].value)
                            return void alert("参数不能为空");
                        if (isNaN(f[t].value) || f[t].value < 0)
                            return void alert("参数不正确");
                        if ((f[4].value > 10 || f[4].value < 0) && (i += "佣金必须为小于10的数字，"),
                        (f[6].value > 10 || f[6].value < 0) && (i += "印花税必须为小于10的数字，"),
                        "" != i)
                            return i += "请重新填写。",
                            void alert(i)
                    }
                    if (!o(f[7].value, f[7]))
                        return void alert("日期输入不正确");
                    var a = l.getElementsByTagName("div")[0].children[0];
                    loadScript(stockUrl + "f=bs&g=" + e.curCollectID + "&sc=" + e.curNoteStockID + "&c=" + f[0].value + "&p=" + f[1].value + "&y=" + f[4].value + "&x=" + f[5].value + "&h=" + f[6].value + "&m=" + f[2].value + "&z=" + f[3].value + "&r=" + f[7].value + "&b=" + encodeURIComponent(a.value) + "&cb=saveNoteCallback&" + Math.random()),
                    uncoverbg();
                    for (var a = l.getElementsByTagName("div")[0].children[0], s = !0, i = 0, t = 0; t < f.length; t++)
                        if (("" == f[t].value || 0 == f[t].value && "" == a.value) && i++,
                        i == f.length) {
                            s = !1;
                            break
                        }
                    for (var t in myStock.stockList)
                        if (myStock.stockList[t]._Code == u.code) {
                            var r = myStock.stockList[t];
                            break
                        }
                    s ? (u.currentDom.className = "note-on",
                    r._note = 1,
                    r.parseCommonData()) : (u.currentDom.className = "note",
                    r._note = -1,
                    r.parseCommonData()),
                    document.body.removeChild(n)
                }
                ,
                f[7].onclick = function() {
                    s.style.display = "block"
                }
                ,
                f[7].onblur = function() {
                    s.style.display = "none"
                }
                ;
                var g = l.getElementsByTagName("div")[0].children[0]
                  , y = l.getElementsByTagName("span")[2];
                g.onkeydown = function() {
                    g.value.length > 200 && (g.value = g.value.substring(0, 200)),
                    y.innerHTML = 200 - g.value.length
                }
            }
            ,
            this.fill = function() {
                for (var t in myStock.stockList)
                    if (myStock.stockList[t]._Code == u.code) {
                        var e = myStock.stockList[t];
                        break
                    }
                var o = i.getElementsByTagName("span");
                o[0].innerHTML = e._Name,
                o[1].innerHTML = e._Code,
                o[3].innerHTML = e.Close
            }
            ,
            this.fillTable = function(t) {
                var e = t.data[myStock.curNoteStockID].date;
                e = e.split("|");
                var o = i.getElementsByTagName("span");
                o[7].innerHTML = "undefined" == typeof e[1] ? "--" : e[1],
                e = e[0];
                var n = e.substring(0, 4) + "-" + e.substring(4, 6) + "-" + e.substring(6, 8);
                o[5].innerHTML = n;
                var s = t.data[myStock.curNoteStockID].node;
                if (s) {
                    var r = a.getElementsByTagName("input");
                    s = s.split("|"),
                    r[0].value = s[0] || "0",
                    r[1].value = s[1] || "0",
                    r[4].value = s[2] || "0",
                    r[5].value = s[3] || "0",
                    r[6].value = s[4] || "0",
                    r[2].value = s[5] || "0",
                    r[3].value = s[6] || "0",
                    r[7].value = s[7];
                    var c = l.getElementsByTagName("div")[0].children[0]
                      , d = l.getElementsByTagName("span")[2];
                    c.value = s[8],
                    d.innerHTML = 200 - c.value.length
                }
            }
        }
        (myStock)
          , m = new function() {
            var e, o = "tixinglight", n = "tixingclose", i = "sastitle", a = "sassubmit", s = "sascancel", l = "sasclear";
            this.init = function() {
                o = t(o),
                o || (o = document.createElement("div"),
                o.id = "tixinglight",
                o.cssText = "");
                var r = '<div class="tixingclose" id="tixingclose" title="关闭">X</div><div class="tixingtitle"><em class="alarmbell"></em>自选股提醒</div><div class="tixingbody" id="tixingbody"><div id="stockalarmset"><div class="sastitle" id="sastitle"><a href="http://guba.eastmoney.com/list,300356.html target="_blank" class="stockname">--</a>（300356）当前股价：<span id="nowprice" class="red"><strong>3.96</strong></span></div><div class="sasaset"><div class="item"><input type="checkbox" id="sasi1"><label for="sasi1">	当有最新公司公告、数据、研究报告时提醒</label></div></div><div class="sasalarm"><div class="sasalarmtitle">	股价预警</div><div class="item"><input type="checkbox" id="sasi4"><label for="sasi4">股价突破</label><input type="text" id="sasinum1" class="inty">	元 <span id="sasinum1error" class="red"></span></div><div class="item"><input type="checkbox" id="sasi5"><label for="sasi5">	股价跌破</label><input type="text" id="sasinum2" class="inty">	元 <span id="sasinum2error" class="red"></span></div><div class="item"><input type="checkbox" id="sasi6"><label for="sasi6">	日涨跌幅达到</label><input type="text" id="sasinum3" class="inty">	％ <span id="sasinum3error" class="red"></span></div></div><div class="sasbtns"><a href="javascript:;" target="_self" id="sasclear">清空</a><div class="btnsdiv"><div class="btns"><button id="sassubmit">确 定</button><button id="sascancel">取 消</button></div></div></div></div></div>';
                o.innerHTML = r,
                document.body.appendChild(o),
                o = t("tixinglight");
                var c = document.documentElement.scrollTop || document.body.scrollTop;
                o.style.top = c + screen.height / 10 + "px",
                n = t("tixingclose"),
                a = t("sassubmit"),
                s = t("sascancel"),
                i = t("sastitle"),
                l = t("sasclear"),
                _sasinum1 = t("sasinum1"),
                _sasinum1error = t("sasinum1error"),
                _sasinum2 = t("sasinum2"),
                _sasinum2error = t("sasinum2error"),
                _sasinum3 = t("sasinum3"),
                _sasinum3error = t("sasinum3error"),
                e = o.getElementsByTagName("input"),
                _sasinum1.onblur = function() {
                    var t = i.getElementsByTagName("strong")[0].innerHTML;
                    return "" == _sasinum1.value ? void (e[1].checked = !1) : isNaN(_sasinum1.value) ? (_sasinum1error.innerHTML = "必须输入数字",
                    _sasinum1.value = "",
                    void (e[1].checked = !1)) : _sasinum1.value - t < 0 ? (_sasinum1error.innerHTML = "所填价格不可小于现价",
                    _sasinum1.value = "",
                    void (e[1].checked = !1)) : _sasinum1.value - t >= 0 ? (_sasinum1error.innerHTML = "此价格较现价的涨幅为" + Math.round(_sasinum1.value / t * 100 - 100) + "%",
                    void (e[1].checked = !0)) : void 0
                }
                ,
                _sasinum2.onblur = function() {
                    var t = i.getElementsByTagName("strong")[0].innerHTML;
                    return "" == _sasinum2.value ? void (e[3].checked = !1) : isNaN(_sasinum2.value) ? (_sasinum2error.innerHTML = "必须输入数字",
                    _sasinum2.value = "",
                    void (e[3].checked = !1)) : _sasinum2.value - t >= 0 ? (_sasinum2error.innerHTML = "所填价格不可大于现价",
                    _sasinum2.value = "",
                    void (e[3].checked = !1)) : _sasinum2.value - t <= 0 ? (_sasinum2error.innerHTML = "此价格较现价的跌幅为" + Math.round((_sasinum2.value - t) / t * 100) + "%",
                    void (e[3].checked = !0)) : void 0
                }
                ,
                _sasinum3.onblur = function() {
                    return "" == _sasinum3.value ? void (e[5].checked = !1) : isNaN(_sasinum3.value) ? (_sasinum3error.innerHTML = "必须输入数字",
                    _sasinum3.value = "",
                    void (e[5].checked = !1)) : void 0
                }
                ,
                n.onclick = function() {
                    return 2 == m.type ? (t("fixedTiXing").style.display = "block",
                    uncoverbg(),
                    void document.body.removeChild(o)) : (uncoverbg(),
                    document.body.removeChild(o),
                    void (m.type = ""))
                }
                ,
                s.onclick = function() {
                    return 2 == m.type ? (t("fixedTiXing").style.display = "block",
                    uncoverbg(),
                    void document.body.removeChild(o)) : (uncoverbg(),
                    document.body.removeChild(o),
                    void (m.type = ""))
                }
                ,
                l.onclick = function() {
                    for (var t = o.getElementsByTagName("input"), e = 0; e < t.length; e++)
                        "" != t[e].value && (t[e].value = ""),
                        1 == t[e].checked && (t[e].checked = !1)
                }
                ,
                a.onclick = function() {
                    var n = !1
                      , i = "http://mystock.eastmoney.com/Message.aspx?type=set&code=" + m.market + "," + m.code;
                    i += e[0].checked ? "&n=1&r=1&d=1" : "&n=2&r=2&d=2",
                    e[1].checked ? i += "&tp=" + e[2].value : i,
                    e[3].checked ? i += "&bp=" + e[4].value : i,
                    e[5].checked ? i += "&zf=" + e[6].value : i,
                    i += "&incId=" + m.incId + "&" + Math.random(),
                    loadScript(i),
                    uncoverbg();
                    for (var a = 0; a < e.length; a++)
                        if (1 == e[a].checked) {
                            n = !0;
                            break
                        }
                    if (m.currentDom) {
                        var s = myStock.alertStock.indexOf(m.code);
                        n ? (m.currentDom.className = "alert-on",
                        -1 == s && myStock.alertStock.push(m.code)) : (m.currentDom.className = "alert",
                        -1 != s && myStock.alertStock.splice(s, 1))
                    }
                    return 2 == m.type ? (t("fixedTiXing").style.display = "block",
                    void document.body.removeChild(o)) : (document.body.removeChild(o),
                    void (m.type = ""))
                }
            }
            ,
            this.fill = function(t) {
                if ("undefined" != typeof m.ob[0].stats)
                    var o = m.currentDom.getAttribute("scode")
                      , n = '<a href="http://guba.eastmoney.com/list,--.html" target=" _blank"="" class="stockname">--</a>（' + o + '）当前股价：<span id="nowprice"><strong>--</strong></span>';
                else {
                    var a = m.ob[0].split(",");
                    if (parseFloat(a[4]) > 0)
                        var s = "stKred";
                    else
                        var s = "stKgreen";
                    var n = '<a href="http://guba.eastmoney.com/list,' + a[1] + '.html" target=" _blank"="" class="stockname">' + a[2] + "</a>（" + a[1] + '）当前股价：<span id="nowprice" class="' + s + '"><strong>' + a[3] + "</strong></span>"
                }
                i.innerHTML = n,
                m.incId = t.incId,
                1 == t.notice && (e[0].checked = !0),
                "" != t.topPrice && (e[1].checked = !0,
                e[2].value = t.topPrice.replace("*", "")),
                "" != t.bottomPrice && (e[3].checked = !0,
                e[4].value = t.bottomPrice.replace("*", "")),
                "" != t.amplitude && (e[5].checked = !0,
                e[6].value = t.amplitude.replace("*", ""))
            }
        }
        (myStock);
        window.onworkerTR = function(t, e, o) {
            window.attachEvent ? t.attachEvent("on" + e, function() {
                o.call(t)
            }) : "mouseenter" != e && "mouseleave" != e ? t.addEventListener(e, o) : (e = "mouseenter" == e ? "mouseover" : "mouseout",
            t["_E" + e] = function(e) {
                this.contains(e.relatedTarget) || o.call(t)
            }
            ,
            t.addEventListener(e, t["_E" + e]))
        }
        ,
        function(e) {
            var o = t("tixing");
            e.onworker(o, "mouseenter", function() {
                t("tixingTips").style.display = "block",
                i = !1,
                clearTimeout(a),
                showTiXing(!1)
            }),
            e.onworker(o, "mouseleave", function() {
                t("tixingTips").style.display = "none",
                i || (clearTimeout(a),
                e.a = setTimeout("showTiXing(true)", timeSet))
            });
            var n = !1
              , i = !1;
            e.timeSet = 6e4,
            e.a = setTimeout("showTiXing(true)", timeSet);
            var s = [0, 0, 0, 0];
            e.showTiXing = function(o) {
                var l = "http://mystock.eastmoney.com/Message.aspx?type=messagecount&" + Math.random();
                loadScript(l, function() {
                    var l = data.StockPrice
                      , r = data.Notice
                      , c = data.Report
                      , d = data.Data;
                    i = !1;
                    var u = t("tixingTips").getElementsByTagName("div");
                    u[1].getElementsByTagName("span")[1].innerHTML = data.StockPrice + "个股价提醒",
                    u[2].getElementsByTagName("span")[1].innerHTML = data.Notice + "个公告提醒",
                    u[3].getElementsByTagName("span")[1].innerHTML = data.Report + "个研报提醒",
                    u[4].getElementsByTagName("span")[1].innerHTML = data.Data + "个数据提醒",
                    t("tixingTips").getElementsByTagName("div")[0].onclick = function() {
                        t("tixingTips").style.display = "none",
                        i = !0,
                        clearTimeout(a)
                    }
                    ;
                    for (var m = 1; m < u.length; m++)
                        u[m].onclick = function() {
                            i = !0,
                            s = [0, 0, 0, 0],
                            t("tixingTips").style.display = "none",
                            clearTimeout(a);
                            var o = e.getPosition(this)
                              , l = "http://mystock.eastmoney.com/Message.aspx?type=get&p=1&ps=150&cate=&" + Math.random();
                            coverbg(),
                            loadScript(l, function() {
                                n = !1,
                                p.init(o, !0),
                                p.cateGory(data, o)
                            })
                        }
                        ;
                    n = l == s[0] && r == s[1] && c == s[2] && d == s[3] ? !1 : !0,
                    n && (s = [l, r, c, d],
                    t("tixingTips").style.display = "block"),
                    o && (e.a = setTimeout("showTiXing(true)", timeSet))
                })
            }
        }(window);
        var p = new function(o) {
            function n(t) {
                return t.replace(/[^\x00-\xff]/g, "xx").length
            }
            function i(t) {
                return t && t.preventDefault ? t.preventDefault() : window.event.returnValue = !1,
                !1
            }
            var l = "fixedTiXing"
              , r = "fixedClose"
              , c = "fixedTab"
              , d = "fixedTablebody;"
              , u = "alarmpager"
              , h = 0
              , g = 15;
            this.page1 = h,
            this.page2 = g,
            this.gujia = [],
            this.gonggao = [],
            this.yanbao = [],
            this.shuju = [],
            this.allTiXing = [],
            this.position,
            this.currentPage,
            this.init = function(e, n) {
                l = t(l),
                l || (l = document.createElement("div"),
                l.id = "fixedTiXing",
                l.cssText = "");
                var i = '<div class="fixedClose" id="fixedClose" title="关闭">X</div><div class="fixedTitle" style="display: none;">使用该功能请先登录</div><div class="fixedTab" id="fixedTab" style="width: 675px;"><ul><li class="">全部提醒</li><li class="">股价提醒</li><li>公告提醒</li><li>研报提醒</li><li>数据提醒</li></ul></div><div class="fixedTBody"><div id="fixedTable"><div class="fixedTablebody" id="fixedTablebody"></div><div class="fixedTablebottom"><div class="sbtext">您可通过自选股提醒图标设置提醒</div><div class="alarmpager" id="alarmpager"></div></div></div></div>';

                l.innerHTML = i,
                document.body.appendChild(l),
                l = t("fixedTiXing");
                var s = document.documentElement.scrollTop || document.body.scrollTop;
                l.style.top = s + screen.height / 8 + "px",
                r = t("fixedClose"),
                c = t("fixedTab"),
                d = t("fixedTablebody"),
                u = t("alarmpager"),
                this.position = e,
                f.currentPage = 0,
                n && (this.currentPage = 0);
                var m = c.getElementsByTagName("li");
                m[p.position].className = "on";
                for (var h = 0; h < m.length; h++)
                    m[h].onclick = function() {
                        for (var t = 0; t < m.length; t++)
                            m[t].className = "";
                        this.className = "on";
                        var e = o.getPosition(this);
                        p.page1 = 0,
                        p.page2 = 15,
                        p.currentPage = 0,
                        f.currentPage = 0,
                        p.position = e,
                        p.fillTable(e)
                    }
                    ;
                r.onclick = function() {
                    uncoverbg(),
                    a = setTimeout("showTiXing(true)", timeSet),
                    document.body.removeChild(l)
                }
            }
            ,
            this.cateGory = function(t, e) {
                this.gujia = [],
                this.gonggao = [],
                this.yanbao = [],
                this.shuju = [],
                this.allTiXing = t.result;
                for (var o = t.result.length, n = 0; o > n; n++)
                    1 == t.result[n].type ? this.gujia.push(t.result[n]) : 2 == t.result[n].type ? this.gonggao.push(t.result[n]) : 3 == t.result[n].type ? this.yanbao.push(t.result[n]) : 4 == t.result[n].type && this.shuju.push(t.result[n]);
                this.fillTable(e)
            }
            ,
            this.fillTable = function(t) {
                var o = [this.allTiXing, this.gujia, this.gonggao, this.yanbao, this.shuju][t]
                  , i = ["股价提醒", "公告提醒", "研报提醒", "数据提醒"]
                  , a = "";
                if (0 == o.length)
                    return d.innerHTML = '<table class="datatable" id="datatable"><thead ><tr><th>序号</th><th>日期</th><th>类型</th><th>内容</th><th></th></tr></thead><tbody id="tbody"><td colspan="5" style="height:449px;vertical-align: middle;text-align: center; width:570px;">暂时没有提醒</td></tbody></table>',
                    void (u.innerHTML = "");
                a = "",
                this.page2 = this.page2 > o.length ? o.length : this.page2;
                e("div");
                a += '<table class="datatable" id="datatable"><thead ><tr><th>序号</th><th>日期</th><th>类型</th><th>内容</th><th></th></tr></thead><tbody id="tbody">';
                for (var s = 0; s < o.length; s++) {
                    var l = o[s].title;
                    n(l) > 50 && (l = l.substring(0, 25) + "..."),
                    a += "<tr><td>" + (s + 1) + "</td><td>" + o[s].datetime.substring(5, 16) + "</td><td>" + i[o[s].type - 1] + '</td><td class="l">[<a href="http://guba.eastmoney.com/list,' + o[s].code + '.html" target="_blank">' + o[s].name + '</a>]<a href="' + o[s].url + '" title="' + o[s].title + '" target="_blank">' + l + '</a></td><td class="setup"><a href="javascript:void(0)"  data-code="' + o[s].code + '" target="_self" class="modyone">修改设置</a></td></tr>'
                }
                a += "</tbody></table>",
                d.innerHTML = a;
                for (var r = d.getElementsByTagName("tbody")[0].getElementsByTagName("tr"), s = 0; s < o.length; s++) {
                    r[s].style.display = "none";
                    for (var c = this.page1; c < this.page2; c++)
                        r[c].style.display = ""
                }
                this.operation(),
                u.innerHTML = "",
                f.init(u, 0, 15, o.length),
                f.pageTiXing(o)
            }
            ,
            this.operation = function() {
                for (var t = d.getElementsByTagName("tbody")[0].getElementsByTagName("tr"), e = 0; e < t.length; e++)
                    o.onworkerTR(t[e], "mouseenter", function() {
                        this.getElementsByTagName("td")[4].className = "";
                        var t = this.getElementsByTagName("td")[4].children[0];
                        t.onclick = function(e) {
                            i(e);
                            var o = t.getAttribute("data-code")
                              , n = s(o);
                            n = "02" == n ? 0 : 1;
                            var a = usefulDom.allTable.getElementsByTagName("table")[2];
                            if (a)
                                for (var r = a.getElementsByTagName("i"), c = 1; c < r.length; c++) {
                                    if (r[c].getAttribute("scode") == o) {
                                        m.currentDom = r[c];
                                        break
                                    }
                                    m.currentDom = !1,
                                    c += 2
                                }
                            l.style.display = "none",
                            m.type = 2,
                            myStock.getTiXing(o, n)
                        }
                    }),
                    o.onworkerTR(t[e], "mouseleave", function() {
                        this.getElementsByTagName("td")[4].className = "setup"
                    })
            }
        }
        (window)
          , h = new function() {
            var e = 0
              , o = 9;
            this.page1 = e,
            this.page2 = o,
            this.currentPage,
            this.init = function(n) {
                if (0 != myStock.allStockArray.length) {
                    n && (this.page1 = e,
                    this.page2 = o,
                    this.currentPage = 0,
                    f.currentPage = 0);
                    var i = t("pics");
                    i.innerHTML = "",
                    t("stockDataTables").style.display = "none",
                    i.style.display = "block",
                    this.page2 > myStock.allStockArray.length && (this.page2 = myStock.allStockArray.length);
                    for (var a = this.page1; a < this.page2; a++) {
                        var s = document.createElement("div");
                        s.className = "image_single";
                        var l = myStock.allStockArray[a]._Code
                          , r = myStock.allStockArray[a]._MarketType
                          , c = myStock.allStockArray[a]._Type
                          , d = l + "|0" + r + "|" + c
                          , u = '<a href="http://quote.eastmoney.com/' + l + '.html" target="_blank"><img src="http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/Index.aspx?id=' + l + r + "&imageType=rt&token=3a965a43f705cf1d9ad7e1a3e429d622&r=" + Math.random() + '"></a><div class="btn_box"><table style="float:right"><tbody><tr><td><a href="http://quote.eastmoney.com/' + l + '.html" target="_blank"><img src="http://hqres.eastmoney.com/EMQuote_A/favor/images/btn_see_big.gif"></a></td><td><a href="http://guba.eastmoney.com/topic,' + l + '.html" target="_blank"><img src="http://hqres.eastmoney.com/EMQuote_A/favor/images/btn_go_guba.gif"></a></td><td><a href="javascript:myStock.delStock(\'' + d + '\')"  target="_self">删除</a></td></tr></tbody></table></div>';
                        s.innerHTML = u,
                        i.appendChild(s)
                    }
                    f.init(i, 0, 9, myStock.allStockArray.length),
                    f.pageDgtl()
                }
            }
        }
        (window)
          , f = new function() {
            var t, o;
            this.currentPage = 0,
            this.father,
            this.init = function(n, i, a, s) {
                this.father = n,
                t = i,
                o = a;
                var l = e("div");
                l.className = "pages",
                n.appendChild(l),
                o > s && (o = s);
                var r = e("a");
                r.innerHTML = "上一页",
                l.appendChild(r);
                var s = Math.ceil(s / a);
                if (5 >= s)
                    for (var c = 0; s > c; c++)
                        r = e("a"),
                        r.innerHTML = c + 1,
                        c == f.currentPage && (r.className = "acurrent"),
                        l.appendChild(r);
                else
                    for (var d = e("span"), c = this.currentPage - 3; c <= this.currentPage + 3; c++)
                        c >= s || 0 > c || (r = e("a"),
                        r.innerHTML = c + 1,
                        c == f.currentPage && (r.className = "acurrent"),
                        l.appendChild(r));
                r = e("a"),
                r.innerHTML = "下一页",
                l.appendChild(r);
                var d = e("span");
                d.innerHTML = "共" + s + "页",
                l.appendChild(d)
            }
            ,
            this.pageDgtl = function() {
                var t = this.father.lastChild.getElementsByTagName("a")
                  , e = t[0]
                  , o = t[t.length - 1];
                e.onclick = function() {
                    0 != h.page1 && (h.page1 = h.page1 - 9,
                    h.page2 = h.page1 + 9,
                    h.currentPage = h.currentPage - 1,
                    f.currentPage = h.currentPage,
                    h.init(!1))
                }
                ,
                o.onclick = function() {
                    h.page2 != myStock.allStockArray.length && (h.page1 = h.page1 + 9,
                    h.page2 = h.page2 + 9,
                    h.currentPage = h.currentPage + 1,
                    f.currentPage = h.currentPage,
                    h.init(!1))
                }
                ;
                for (var n = 1; n < t.length - 1; n++)
                    t[n].onclick = function() {
                        h.page1 = 9 * (this.innerHTML - 1),
                        h.page2 = 9 * this.innerHTML,
                        h.currentPage = this.innerHTML - 1,
                        f.currentPage = h.currentPage,
                        h.init(!1)
                    }
            }
            ,
            this.pageTiXing = function(t) {
                var e = this.father.lastChild.getElementsByTagName("a")
                  , o = e[0]
                  , n = e[e.length - 1];
                o.onclick = function() {
                    0 != p.page1 && (p.page1 = p.page1 - 15,
                    p.page2 = p.page1 + 15,
                    p.currentPage = f.currentPage - 1,
                    f.currentPage = p.currentPage,
                    p.fillTable(p.position, !1))
                }
                ,
                n.onclick = function() {
                    p.page2 != t.length && (p.page1 = p.page1 + 15,
                    p.page2 = p.page2 + 15,
                    p.currentPage = p.currentPage + 1,
                    f.currentPage = p.currentPage,
                    p.fillTable(p.position, !1))
                }
                ;
                for (var i = 1; i < e.length - 1; i++)
                    e[i].onclick = function() {
                        p.page1 = 15 * (this.innerHTML - 1),
                        p.page2 = 15 * this.innerHTML,
                        p.currentPage = this.innerHTML - 1,
                        f.currentPage = p.currentPage,
                        p.fillTable(p.position, !1)
                    }
            }
        }
        ;
        !function() {
            setCookie = function(t, e, o, n) {
                var i = new Date;
                i.setDate(i.getDate() + o),
                document.cookie = t + "=" + encodeURIComponent(e) + ";expires=" + i.toGMTString() + ";path=/;domain=" + n
            }
            ,
            getCookie = function(t) {
                var e = document.cookie.match(new RegExp("(^| )" + t + "=([^;]*)(;|$)"));
                return null != e ? decodeURIComponent(e[2]) : null
            }
            ,
            delCookie = function(t) {
                var e = getCookie(t)
                  , o = new Date;
                o.setDate(o.getTime() - 1),
                e && "" != e && (document.cookie = t + "=" + escape(e) + ";path=/;expires=" + o.toGMTString())
            }
        }(),
        function() {
            function t() {
                var t = new Date;
                return t = t.getHours(),
                t >= 18 || 4 > t ? !1 : !0
            }
            function e(t) {
                for (var e = 0; myStock.stockList[e]; ) {
                    if (myStock.stockList[e].WorkID == t)
                        return myStock.stockList[e];
                    e++
                }
                return null
            }
            function o(t) {
                c.push(t)
            }
            function n() {
                a--;
                for (var t = 0; c[t]; )
                    c[t].style.filter ? (c[t].style.fliter = "alpha(opacity=" + 80 / a + "20)",
                    t++) : (c[t].style.opacity = .8 / a + .2,
                    t++);
                if (1 >= a || 0 == c.length) {
                    for (var t = 0; c[t]; )
                        c[t].style.opacity = 1,
                        t++;
                    return a = 1,
                    void setTimeout(d, s)
                }
                setTimeout(n, 50)
            }
            var i, a = 10, s = 6e3, l = !1, r = null;
            myStock.updateList = [];
            var c = []
              , d = function() {
                l = !1,
                c.splice(0, c.length),
                i = Math.random().toString();
                for (var u = [], m = 0; m < myStock.stockList.length; m++)
                    u.push(myStock.stockList[m]._Code + parseInt(myStock.stockList[m]._MarketType).toString());
                if ("DGTL" == myStock.funcName)
                    return void setTimeout(d, s);
                if (r = setTimeout(function() {
                    setTimeout(d, s)
                }, 6e4),
                !t())
                    return !1;
                var p = "YKYL" == myStock.funcName ? defaultKey : myStock.funcName;
                loadScript(hpUrl + "type=CT&cmd=" + u.join(",") + "&sty=" + p + "&cb=getStockFullInfo&js=([(x)],true)&" + Math.random(), function() {
                    l = !0,
                    clearTimeout(r),
                    "none" != myStock.sortState && myStock.sortStockListByKey(myStock.lastKey);
                    var t = document.getElementById("mainTable");
                    if (void 0 == t)
                        return void setTimeout(d, s);
                    c.splice(0, c.length);
                    for (var i = 0; i < myStock.stockList.length; i++) {
                        var u = 0
                          , m = t.getElementsByTagName("tr")[i + 2]
                          , p = e(m.getAttribute("workid"))
                          , h = m.getElementsByTagName("td");
                        for (var f in mainTableField[myStock.funcName])
                            if ("extend" != f)
                                if (f.indexOf("COLSPAN") >= 0) {
                                    for (var g in mainTableField[myStock.funcName][f])
                                        if ("extend" != g) {
                                            if (void 0 == h[u] || null == h[u])
                                                break;
                                            h[u].innerHTML != p[g] && (h[u].innerHTML = p[g],
                                            o(h[u])),
                                            u++
                                        }
                                } else {
                                    if (void 0 == h[u] || null == h[u])
                                        break;
                                    h[u].innerHTML != p[f] && (h[u].innerHTML = p[f],
                                    o(h[u])),
                                    u++
                                }
                        h = null,
                        m = null
                    }
                    a = 10,
                    n()
                })
            };
            setTimeout(d, s)
        }(),
        function() {
            var t = document.getElementById("dzCss")
              , e = document.getElementById("xzCss");
            t.onclick = function() {
                setCookie("font_css", "dz", 365),
                usefulDom.allTable.className = "st-table dzb",
                usefulDom.totalWar.className = "st-ht w100p dzb",
                this.className = "fl dzml5 dzbtnSelected",
                e.className = "fl dzml5"
            }
            ,
            e.onclick = function() {
                setCookie("font_css", "xz", 365),
                usefulDom.allTable.className = "st-table",
                usefulDom.totalWar.className = "st-ht w100p",
                this.className = "fl dzml5 dzbtnSelected",
                t.className = "fl dzml5"
            }
            ,
            "dz" == getCookie("font_css") && t.onclick()
        }(),
        jQuery("#stInfoTabs").children().click(function() {
            if (jQuery("#stInfoTabs").children().removeClass("active"),
            jQuery(this).addClass("active"),
            _currentType = jQuery(this).attr("data-id"),
            _currentPage = 1,
            0 == _stocks.length) {
                switch (_currentType) {
                case "0":
                    $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无帖子</div>');
                    break;
                case "1":
                    $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无新闻</div>');
                    break;
                case "3":
                    $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无公告</div>');
                    break;
                case "2":
                    $("#info-wrap").html('<div class="st-blank f14">该组合因无股票，暂无研报</div>')
                }
                return !1
            }
            window.scrollTo(0, $("#stockInfo").offset().top - 20),
            c.show(_stocks.join(","), _currentType)
        })
    }()
}
, function(t) {
    function e(t) {
        s.css(t + c > r && $("body").height() > 2e3 ? t + c + 400 > d ? {
            bottom: t + c + 412 - d,
            position: "fixed",
            width: 175
        } : {
            bottom: 0,
            position: "fixed",
            width: 175
        } : {
            position: "static"
        })
    }
    function o(t) {
        m.css(t > p ? {
            top: 0,
            position: "fixed",
            width: $("#stockInfo").width(),
            zIndex: 10
        } : {
            position: "static"
        })
    }
    function n() {
        return u ? !1 : (c = $(window).height(),
        d = $("body").height(),
        void (p = m.offset().top - 0))
    }
    function i() {
        return u ? !1 : h ? !1 : ($(window).on("resize scroll", function() {
            var t = $(window).scrollTop();
            e(t),
            o(t)
        }),
        $(window).on("resize", function() {
            c = $(window).height()
        }),
        void (h = !0))
    }
    function a() {
        if (u)
            return !1;
        var t = $(window).scrollTop();
        e(t)
    }
    var s = $("#treemenu")
      , l = s.height()
      , r = l + 70
      , c = $(window).height()
      , d = $("body").height()
      , u = document.all && !window.XMLHttpRequest
      , m = $("#stInfoTabs")
      , p = m.offset().top - 0
      , h = !1;
    t.exports = {
        resetpar: n,
        bindmethod: i,
        start: a
    }
}
, function(t, e, o) {
    o(20);
    var n = o(22)
      , i = function() {
        function t(t) {
            var e = {
                title: "",
                content: "",
                chooseyes: function() {}
            };
            this.options = $.extend(e, t)
        }
        return t.prototype.show = function() {
            var t = this
              , e = ['<div class="confirmblock">', '    <div class="csb_body">', "        ", t.options.content, "    </div>", '    <div class="csb_btns">', '        <button class="btn btn-primary confirmyes">确定</button> ', '        <button class="btn btn-default confirmno">取消</button>', "    </div>", "</div>"];
            e = $(e.join(""));
            var o = new n({
                title: t.options.title,
                content: e
            });
            this.modal = o,
            this.modalhtml = e,
            o.show(),
            this.bind()
        }
        ,
        t.prototype.bind = function() {
            var t = this;
            this.modalhtml.on("click", ".confirmno", function() {
                t.modal.close()
            }),
            this.modalhtml.on("click", ".confirmyes", function() {
                t.options.chooseyes(),
                t.modal.close()
            })
        }
        ,
        t
    }();
    t.exports = i
}
, function() {}
, , function(t, e, o) {
    o(23);
    var n = function() {
        function t(t) {
            var e = {
                title: "",
                content: "",
                showmask: !0,
                showclose: !0,
                titlecolor: 1,
                titletype: "text",
                titlelist: [],
                onclose: function() {}
            };
            this.options = $.extend(e, t)
        }
        return t.prototype.show = function() {
            var t = this
              , e = $('<div class="gbmask"></div>')
              , o = "";
            t.options.titlecolor > 1 && (o = "c" + t.options.titlecolor);
            var n = $('<div class="gbpopbox"><a href="javascript:;" target="_self" class="gbpopboxclose ' + o + '" title="关闭"></a><div class="gbpopboxtitle ' + o + '"></div><div class="gbpopboxttab"></div><div class="gbpopboxbody"></div></div>')
              , i = $('<div class="gubamodal"></div>');
            i.append(n),
            this.alldiv = i,
            this.modaldiv = n,
            this.options.showmask && (i.append(e),
            e.height($(document).height()));
            var a = $(".gbpopbox").length;
            $(".gbpopbox", i).css({
                "z-index": 80001 + a
            }),
            $(".gbmask", i).css({
                "z-index": 8e4 + a
            }),
            $("body").append(i),
            "text" == this.options.titletype ? ($(".gbpopboxtitle", n).html(this.options.title),
            $(".gbpopboxttab", n).hide()) : "list" == this.options.titletype && ($(".gbpopboxtitle", n).hide(),
            $(".gbpopboxttab", n).html('<ul><li class="on">' + this.options.titlelist.join("</li><li>") + "</li></ul>")),
            $(".gbpopboxbody", n).append(this.options.content),
            this.reposition(),
            $(".gbpopboxclose", n).click(function() {
                t.options.onclose(),
                i.remove()
            })
        }
        ,
        t.prototype.reposition = function() {
            this.modaldiv.css({
                left: $(document).width() / 2 - this.modaldiv.width() / 2,
                top: $(window).height() / 2 - this.modaldiv.height() / 2 + $(window).scrollTop()
            })
        }
        ,
        t.prototype.getSelf = function() {
            return this.alldiv
        }
        ,
        t.prototype.getmodal = function() {
            return this.modaldiv
        }
        ,
        t.prototype.close = function() {
            this.alldiv.remove()
        }
        ,
        t.prototype.closeAllModal = function() {
            $(".gubamodal").remove()
        }
        ,
        t
    }();
    t.exports = n
}
, function() {}
, , , , , function(t, e, o) {
    function n(t, e, o) {
        return "" == t || "" == e ? o : '<a href="http://iguba.eastmoney.com/' + t + '" target="_blank">' + e + "</a>"
    }
    function i(t) {
        return 0 >= t ? "" : "(<span>" + t.toString() + "</span>)"
    }
    function a(t, e, o) {
        return t ? '<a href="javascript:;" class="delpraiselink" data-topicid="' + e + '">已赞' + i(o) + "</a>" : '<a href="javascript:;" class="praiselink" data-topicid="' + e + '">赞' + i(o) + "</a>"
    }
    function s(t, e) {
        return t ? '<a href="javascript:;" class="delcollectlink" data-topicid="' + e + '">已收藏</a>' : '<a href="javascript:;" class="collectlink" data-topicid="' + e + '">收藏</a>'
    }
    o(29);
    var l = o(8)
      , r = o(15)
      , c = o(31)
      , d = o(32)
      , u = o(33)
      , m = o(40)
      , p = {
        container: null,
        show: function(t, e) {
            var n = this;
            this.container.html('<div class="stock-loading-div"><img src="http://cmsjs.eastmoney.com/mystock/img/loading2.gif"></div>'),
            this.getdata(t, e, function(t) {
                var e = n.formatdata(t);
                n.container.html(e);
                var i = o(18);
                i.resetpar(),
                i.bindmethod(),
                i.start(),
                n.bind(e)
            }, function(t) {
                alert(t)
            })
        },
        getdata: function(t, e, o, n) {
            $.ajax({
                type: "GET",
                url: r.articlelist + "&codes=" + t + "&type=" + e,
                dataType: "jsonp",
                success: function(t) {
                    1 == t.rc ? o && o(t.re) : n && n(t.me)
                },
                error: function() {
                    n && n("接口获取失败")
                }
            })
        },
        formatdata: function(t) {
            if (0 == t.length)
                return "";
            for (var e = null, o = [], r = "", c = document.all && !document.querySelector, d = "", u = 0; u < t.length; u++)
                e = t[u],
                d = "",
                c && (d = ' style="z-index:' + (103 - u).toString() + ';"'),
                r = '<div class="item clearfix"' + d + '>  <div class="stockcode">    <a href="http://guba.eastmoney.com/list,' + e.stockbar_code + '.html" target="_blank">' + e.stockbar_code + '</a>  </div>  <div class="info">    <div class="title"><span>[<a href="http://guba.eastmoney.com/list,' + e.stockbar_code + '.html" target="_blank">' + e.stockbar_name + '</a>]</span> <a target="_blank" href="' + e.post_url + '">' + e.post_title + '</a></div>    <div class="content">' + l.showface(e.post_content) + '</div>    <div class="itemfoot">      <div class="time">' + e.post_publish_time + "&nbsp; 作者：" + n(e.user_id, e.user_nickname, e.post_ip) + '</div>      <div class="nums">        ' + a(e.post_is_like, e.post_id, e.post_like_count) + ' |        <a href="javascript:;" target="_blank" data-topicid="' + e.post_id + '" class="forwardlink">转发' + i(e.post_forward_count) + "</a> |        " + s(e.post_is_collected, e.post_id) + ' |        <a href="' + e.post_url + '" target="_blank">阅读' + i(e.post_click_count) + '</a> |        <a href="javascript:;" class="replylistlink" data-topicid="' + e.post_id + '" data-topicurl="' + e.post_url + '">评论' + i(e.post_comment_count) + "</a>      </div>    </div>  </div></div>",
                o.push(r);
            return $(o.join(""))
        },
        bind: function(t) {
            return "" == t ? !1 : (t.on("click", ".praiselink", function() {
                var t = $(this)
                  , e = t.data("topicid")
                  , o = new c(t,e);
                return o.add(),
                !1
            }),
            t.on("click", ".delpraiselink", function() {
                var t = $(this)
                  , e = t.data("topicid")
                  , o = new c(t,e);
                return o.del(),
                !1
            }),
            t.on("click", ".collectlink", function() {
                var t = $(this)
                  , e = t.data("topicid")
                  , o = new d(t,e);
                return o.add(),
                !1
            }),
            t.on("click", ".delcollectlink", function() {
                var t = $(this)
                  , e = t.data("topicid")
                  , o = new d(t,e);
                return o.del(),
                !1
            }),
            t.on("click", ".forwardlink", function() {
                var t = $(this)
                  , e = t.data("topicid")
                  , o = new u(e);
                return o.show(),
                !1
            }),
            void t.on("click", ".replylistlink", function() {
                var t = $(this)
                  , e = t.data("topicid")
                  , o = t.data("topicurl")
                  , n = t.parents(".item")
                  , i = new m(e,n,o);
                return i.show(),
                !1
            }))
        }
    };
    t.exports = p
}
, function() {}
, , function(t, e, o) {
    var n = o(15)
      , i = function() {
        function t(t, e) {
            this.thisbtn = t,
            this.topicid = e,
            a = this
        }
        function e(t) {
            return t.indexOf("(") < 0 ? 0 : t.substring(t.indexOf("(") + 1, t.indexOf(")"))
        }
        function o(t, e, o) {
            return $.ajax({
                type: "GET",
                url: n.addtopicpraise,
                dataType: "jsonp",
                data: {
                    id: t,
                    type: 0
                },
                success: function(t) {
                    1 == t.rc ? e() : o("已赞过" == t.message ? "您已经赞过此条评论了！" : t.message)
                },
                error: function() {
                    o("服务器忙，请稍候再试！")
                }
            }),
            !1
        }
        function i(t, e, o) {
            return $.ajax({
                type: "GET",
                url: n.deltopicpraise,
                dataType: "jsonp",
                data: {
                    postid: t
                },
                success: function(t) {
                    1 == t.rc ? e() : o(t.message)
                },
                error: function() {
                    o("服务器忙，请稍候再试！")
                }
            }),
            !1
        }
        var a;
        return t.prototype.add = function() {
            return o(a.topicid, function() {
                var t = e(a.thisbtn.text());
                t++,
                a.thisbtn.replaceWith('<a href="javascript:;" class="delpraiselink" data-topicid="' + a.topicid + '" target="_self">已赞(<span>' + t.toString() + "</span>)</a>")
            }, function() {}),
            !1
        }
        ,
        t.prototype.del = function() {
            return i(a.topicid, function() {
                var t = e(a.thisbtn.text())
                  , o = "";
                t--,
                t > 0 && (o = "(<span>" + t.toString() + "</span>)"),
                a.thisbtn.replaceWith('<a href="javascript:;" class="praiselink" data-topicid="' + a.topicid + '" target="_self">赞' + o + "</a>")
            }, function() {}),
            !1
        }
        ,
        t
    }();
    t.exports = i
}
, function(t, e, o) {
    var n = o(15)
      , i = function() {
        function t(t, e) {
            this.thisbtn = t,
            this.topicid = e,
            i = this
        }
        function e(t, e, o) {
            return $.ajax({
                type: "GET",
                url: n.addtopiccollect,
                dataType: "jsonp",
                data: {
                    postid: t
                },
                success: function(t) {
                    console.info(t),
                    1 == t.rc ? e() : o(t.message)
                },
                error: function() {
                    o("服务器忙，请稍候再试！")
                }
            }),
            !1
        }
        function o(t, e, o) {
            return $.ajax({
                type: "GET",
                url: n.deltopiccollect,
                dataType: "jsonp",
                data: {
                    postid: t
                },
                success: function(t) {
                    1 == t.rc ? e() : o(t.message)
                },
                error: function() {
                    o("服务器忙，请稍候再试！")
                }
            }),
            !1
        }
        var i;
        return t.prototype.add = function() {
            return e(i.topicid, function() {
                i.thisbtn.replaceWith('<a href="javascript:;" class="delcollectlink" data-topicid="' + i.topicid + '" target="_self">已收藏</a>')
            }, function() {}),
            !1
        }
        ,
        t.prototype.del = function() {
            return o(i.topicid, function() {
                i.thisbtn.replaceWith('<a href="javascript:;" class="collectlink" data-topicid="' + i.topicid + '" target="_self">收藏</a>')
            }, function() {}),
            !1
        }
        ,
        t
    }();
    t.exports = i
}
, function(t, e, o) {
    var n = o(15)
      , i = o(22)
      , a = o(34);
    o(38);
    var s, l = function() {
        function t(t) {
            this.topicid = t,
            s = this
        }
        return t.prototype.show = function() {
            var t = $('<div class="gbloadingbox"><img src="images/loading.gif" />加载数据中...</div>')
              , e = new i({
                title: "转发",
                content: t
            });
            e.show();
            var o = $('<div id="zwzfdiv"><form target="_self" id="zwzfform"><div class="l title zwzfreply"></div><div class="l"><textarea id="zwzftt" placeholder="请输入转发理由"></textarea></div><div class="zwzfyw clearfix"><div class="photo"><img src="" alt="" /></div><div class="zwzfcontent"><div class="zwzfname"></div><div class="zwzftitle"></div></div></div><div class="zwzftext"></div><div class="zwzfbtns clearfix"><div class="btnsl"><label><input type="checkbox" checked id="zfzwisreply" />同时评论给<span id="ywauthor"></span></label></div><div class="btnsr"><button type="submit" id="zwzfsubmit">转发</button></div></div></form></div>');
            $.ajax({
                type: "GET",
                url: "http://iguba.eastmoney.com/interf/webapi.aspx",
                dataType: "jsonp",
                data: {
                    action: "zf_article",
                    id: s.topicid
                },
                success: function(t) {
                    if (1 == t.rc) {
                        if (null == t.post)
                            return alert(t.me),
                            !1;
                        e.close(),
                        s.zfmodal = new i({
                            title: "转发",
                            content: o
                        }),
                        s.zfmodal.show(),
                        s.zfmodal.reposition();
                        var a = !0;
                        "" != t.post.source_post_id && null != t.post.source_post_id && 0 != t.post.source_post_id && (a = !1);
                        var l = ""
                          , r = "";
                        if (a)
                            "" == t.post.post_user.user_id ? ($("#zwzfform .zwzfyw .photo img").attr("src", "http://gbfek.dfcfw.com/gubav5/images/nophoto.jpg"),
                            $("#zwzfdiv .zwzfname").html(t.post.post_ip),
                            $("#zwzfdiv #ywauthor").html(t.post.post_ip)) : ($("#zwzfform .zwzfyw .photo img").attr("src", "http://avator.eastmoney.com/qface/" + t.post.post_user.user_id + "/50"),
                            $("#zwzfdiv .zwzfname").html('<a href="http://iguba.eastmoney.com/' + t.post.post_user.user_id + '">' + t.post.post_user.user_nickname + "</a>"),
                            $("#zwzfdiv #ywauthor").html(t.post.post_user.user_nickname)),
                            $("#zwzfdiv .zwzftitle").html(t.post.post_title),
                            $("#zwzfdiv .zwzftext").html(t.post.post_content),
                            $("#zwzfdiv .zwzfreply").hide();
                        else {
                            $("#zwzfdiv .zwzftitle").html(t.post.source_post_title),
                            $("#zwzfdiv .zwzftext").html(t.post.source_post_content);
                            var c = "<strong>@";
                            c += "" == t.post.post_user.user_id ? t.post.post_ip : t.post.post_user.user_nickname,
                            "" == t.post.source_post_user_id ? ($("#zwzfform .zwzfyw .photo img").attr("src", "http://gbfek.dfcfw.com/gubav5/images/nophoto.jpg"),
                            $("#zwzfdiv .zwzfname").html(t.post.source_post_ip),
                            $("#zwzfdiv #ywauthor").html(t.post.post_ip),
                            l = t.post.source_post_ip) : ($("#zwzfform .zwzfyw .photo img").attr("src", "http://avator.eastmoney.com/qface/" + t.post.source_post_user_id + "/50"),
                            $("#zwzfdiv .zwzfname").html('<a href="http://iguba.eastmoney.com/' + t.post.source_post_user_id + '">' + t.post.source_post_user_nickname + "</a>"),
                            $("#zwzfdiv #ywauthor").html(t.post.post_user.user_nickname),
                            l = t.post.source_post_user_nickname),
                            c += "：</strong>",
                            r = t.post.post_content,
                            $("#zwzfdiv .zwzfreply").html(c + t.post.post_content)
                        }
                        $("#zwzftt").focus(),
                        $("#zwzfform").submit(function() {
                            $("#zwzfsubmit").attr("disabled", !0).text("转发中...");
                            var t = $("#zwzftt").val();
                            return $.ajax({
                                type: "POST",
                                url: n.newpost,
                                dataType: "json",
                                data: {
                                    yid: s.topicid,
                                    text: t,
                                    is_reply: $("#zfzwisreply").is(":checked")
                                },
                                success: function(t) {
                                    if (clickone = !0,
                                    $("#zwzfsubmit").attr("disabled", !1).text("转发"),
                                    "1" == t.rc) {
                                        s.zfmodal.close();
                                        var e = "http://iguba.eastmoney.com/myart"
                                          , o = parseInt($("#zfnums").text());
                                        $("#zfnums").text((o + 1).toString()),
                                        s.zfsendok(e)
                                    } else
                                        alert("2" == t.rc ? "请输入验证码" : "3" == t.rc ? "验证码输入错误！" : t.me)
                                },
                                error: function(t) {
                                    $("#zwzfsubmit").attr("disabled", !1).text("转发"),
                                    alert("失败 " + t)
                                }
                            }),
                            !1
                        })
                    } else
                        alert(t.me)
                }
            })
        }
        ,
        t.prototype.zfsendok = function(t) {
            if ($("#sendoknew").length > 0)
                return !1;
            var e = $('<div id="sendoknew">已转发成功！</div>')
              , o = new a({
                content: e,
                closetime: 3,
                onclose: function() {
                    return !1
                }
            });
            o.show()
        }
        ,
        t
    }();
    t.exports = l
}
, function(t, e, o) {
    o(35);
    var n = function() {
        function t(t) {
            var e = {
                content: "",
                showclose: !0,
                closetime: 0,
                onclose: function() {}
            };
            this.options = $.extend(e, t)
        }
        return t.prototype.show = function() {
            var t = this
              , e = "";
            this.options.showclose && (e = '<a href="javascript:;" target="_self" class="close" title="关闭"><em class="emclose"></em></a>');
            var o = $('<div class="gbmodal2">' + e + '<div class="gbmodal2b"></div></div>');
            this.modaldiv = o,
            $("body").append(o),
            $(".gbmodal2b", o).append(this.options.content),
            o.css({
                left: $(document).width() / 2 - o.width() / 2,
                top: $(window).height() / 2 - o.height() / 2 + $(window).scrollTop()
            }),
            $(".close", o).click(function() {
                t.close()
            }),
            this.options.closetime > 0 && (this.settime = setTimeout(function() {
                t.close(),
                t.settime = null
            }, 1e3 * this.options.closetime))
        }
        ,
        t.prototype.getSelf = function() {
            return this.modaldiv
        }
        ,
        t.prototype.close = function() {
            this.options.onclose(),
            this.modaldiv.remove()
        }
        ,
        t.prototype.closeAllModal = function() {
            $(".gbmodal2").remove()
        }
        ,
        t
    }();
    t.exports = n
}
, function() {}
, , , function() {}
, , function(t, e, o) {
    function n(t, e) {
        return "" != t ? '<a href="http://iguba.eastmoney.com/' + t + '" target="_blank"><img src="http://avator.eastmoney.com/qface/' + t + '/30" alt="' + e + '"></a>' : '<img src="http://gbfek.dfcfw.com/gubav5/images/nophoto.jpg">'
    }
    function i(t, e, o) {
        return "" != t ? '<a href="http://iguba.eastmoney.com/' + t + '" target="_blank">' + e + "</a>" : '<span class="gray">' + o + "</span>"
    }
    o(41);
    var a = o(15)
      , s = o(8)
      , l = o(44);
    o(48);
    var r = o(34)
      , c = function() {
        function t(t, e, o) {
            this.topicid = t,
            this.thisitem = e,
            this.topicurl = o
        }
        return t.prototype.show = function() {
            var t = this;
            if ($(".replylist", this.thisitem).length > 0)
                return $(".replylist", this.thisitem).remove(),
                !1;
            $(".replylist").remove();
            var e = ['<div class="replylist">', '    <div class="gbreplyarrowd"><div class="gbreplyarrow"></div></div>', '    <textarea class="replyinput" id="gbtainput"></textarea>', '    <div class="gbreplybtns clearfix">', '        <div class="editorfuns"><a href="javascript:;" id="gbtainpubtn4" data-fun="face" target="_self"><em class="iconface"></em>表情</a>', "        </div>", '        <div class="gbreplysub" id="replysubmitbtn">发 布</div>', "    </div>", '    <div class="gbreplybar">', '        <div class="gbreplybart">全部评论（<span id="replycount"></span>）</div>', '        <div class="gbreplybarsort"></div>', "    </div>", '    <div class="gbreplylist clearfix">', "    </div>", '    <div class="gbreplymore">后面还有<span id="lastcount"></span>条评论，<a href="', t.topicurl, '" target="_blank">点击查看&gt;&gt;</a></div>', "</div>"];
            e = $(e.join("")),
            this.replyhtml = e,
            this.thisitem.append(e),
            this.bind(),
            this.getreplylist()
        }
        ,
        t.prototype.bind = function() {
            var t = this
              , e = new l($("#gbtainput", t.replyhtml));
            e.bindInsertFace($("#gbtainpubtn4", t.replyhtml)),
            $("#replysubmitbtn", t.replyhtml).click(function() {
                var e = $.trim($("#gbtainput", t.replyhtml).val());
                return "" == e ? ($("#gbtainput", t.replyhtml).textBlink({
                    color: ["#FFDDDD", "#FFEEEE", "#fff"],
                    blinktime: 150
                }),
                !1) : void $.ajax({
                    type: "POST",
                    url: a.sendreply,
                    dataType: "json",
                    data: {
                        tid: t.topicid,
                        type: 1,
                        text: e
                    },
                    success: function(e) {
                        if (1 == e.rc) {
                            var o = new r({
                                content: "发布成功！",
                                closetime: 2
                            });
                            o.show(),
                            $("#gbtainput", t.replyhtml).val("")
                        } else
                            alert(e.me)
                    },
                    error: function() {
                        alert("系统忙，请稍候再试！")
                    }
                })
            })
        }
        ,
        t.prototype.sendokfill = function() {}
        ,
        t.prototype.getreplylist = function() {
            var t = this;
            this.getreplydata(function(e) {
                var o = t.madereplyhtml(e.re);
                t.replyreplyhtml = o,
                $(".gbreplylist", t.replyhtml).html(o),
                t.replybind(),
                e.re.length > 0 && ($(".gbreplybar", t.replyhtml).show(),
                $("#replycount", t.replyhtml).text(e.count.toString())),
                e.count > 5 && ($(".gbreplymore", t.replyhtml).show(),
                $("#lastcount", t.replyhtml).text((e.count - 5).toString()).show())
            })
        }
        ,
        t.prototype.madereplyhtml = function(t) {
            for (var e = null, o = [], a = [], l = 0; l < t.length; l++)
                e = t[l],
                a = ['<div class="gbreplyitem" data-replyid="', e.reply_id, '">', '    <div class="gbreplyphoto">', "        ", n(e.reply_user.user_id, e.reply_user.user_nickname), "    </div>", '    <div class="gbreplycon">', '        <div class="gbreplyname">', i(e.reply_user.user_id, e.reply_user.user_nickname, e.reply_ip), "：</div>", '        <div class="gbreplytext">', s.showface(e.reply_text), "</div>", '        <div class="gbreplybot clearfix">', '            <div class="gbreplytime">', e.reply_publish_time, "</div>", '            <div class="gbreplybbtns"><a href="javascript:;" data-replyid="', e.reply_id, '" class="replyreplybtn">评论</a></div>', "        </div>", "    </div>", "</div>"],
                o.push(a.join(""));
            return $(o.join(""))
        }
        ,
        t.prototype.replybind = function() {
            var t = this;
            $(".replyreplybtn", t.replyreplyhtml).click(function() {
                t.replyreplyshow($(this).parents(".gbreplyitem"), $(this).data("replyid"))
            })
        }
        ,
        t.prototype.replyreplyshow = function(t, e) {
            if ($(".sendreplyreply", t).length > 0)
                return $(".sendreplyreply", t).remove(),
                !1;
            $(".sendreplyreply").remove();
            var o = ['<div class="sendreplyreply">', '    <div class="gbreplyarrowd"><div class="gbreplyarrow"></div></div>', '    <textarea class="replyinput" id="gbtainput_replyreply"></textarea>', '    <div class="gbreplybtns clearfix">', '        <div class="gbreplysub" id="replyreplysubmitbtn">发 布</div>', "    </div>", "</div>"];
            o = $(o.join("")),
            this.sendreplyreply = o,
            t.append(o),
            this.replyreplybind(o, e)
        }
        ,
        t.prototype.replyreplybind = function(t, e) {
            var o = this;
            $("#replyreplysubmitbtn", t).click(function() {
                var n = $.trim($("#gbtainput_replyreply", t).val());
                return "" == n ? ($("#gbtainput_replyreply", t).textBlink({
                    color: ["#FFDDDD", "#FFEEEE", "#fff"],
                    blinktime: 150
                }),
                !1) : void $.ajax({
                    type: "POST",
                    url: a.sendreply,
                    dataType: "json",
                    data: {
                        tid: o.topicid,
                        huifuid: e,
                        type: 1,
                        text: n
                    },
                    success: function(e) {
                        if (1 == e.rc) {
                            var o = new r({
                                content: "发布成功！",
                                closetime: 2
                            });
                            o.show(),
                            t.remove()
                        } else
                            alert(e.me)
                    },
                    error: function() {
                        alert("系统忙，请稍候再试！")
                    }
                })
            })
        }
        ,
        t.prototype.getreplydata = function(t) {
            $.ajax({
                type: "GET",
                url: a.replylist,
                dataType: "jsonp",
                data: {
                    id: this.topicid,
                    ps: 5,
                    p: 1,
                    sort: 1
                },
                success: function(e) {
                    1 == e.rc ? t && t(e) : alert(e.me)
                },
                error: function() {
                    try {
                        console.info("获取评论数据失败")
                    } catch (t) {}
                }
            })
        }
        ,
        t
    }();
    t.exports = c
}
, function() {}
, , , function(t, e, o) {
    o(45),
    o(46);
    var n = function() {
        function t(t) {
            this.textinput = t
        }
        function e() {
            for (var t = ["微笑", "大笑", "鼓掌", "不说了", "为什么", "哭", "不屑", "怒", "滴汗", "拜神", "胜利", "亏大了", "赚大了", "牛", "俏皮", "傲", "好困惑", "兴奋", "赞", "不赞", "摊手", "好逊", "失望", "加油", "困顿", "想一下", "围观", "献花", "大便", "爱心", "心碎", "毛估估", "成交", "财力", "护城河", "复盘", "买入", "卖出", "满仓", "空仓", "抄底", "看多", "看空", "加仓", "减仓"], e = [], o = "", n = 0; n < t.length; n++)
                o = 10 > n + 1 ? "0" + (n + 1).toString() : (n + 1).toString(),
                e.push('<li title="' + t[n] + '"><img src="http://gbres.dfcfw.com/face/emot/emot' + o + '.png" alt="' + t[n] + '"></li>');
            return e.join("")
        }
        function o(t, e) {
            var o, n, i = t.caret("pos");
            o = t.val().substring(0, i),
            n = t.val().substring(i),
            t.val(o + e + n)
        }
        return t.prototype.bindInsertFace = function(t) {
            var n = this
              , i = !1;
            return t.click(function() {
                if (i) {
                    var a = $("#listinsertfacediv");
                    a.is(":visible") ? a.hide() : a.show()
                } else {
                    var s = '<div class="shadowbox" id="listinsertfacediv"><div class="shadowboxtoparrow"></div><table class="shadowboxt" cellspacing="0" cellpadding="0"><tr><td class="l7"></td><td class="l8"></td><td class="l9"></td></tr><tr><td class="l4">&ensp;</td><td class="l5"><div class="shadowboxbody"><div id="facediv"><ul id="facelist">' + e() + '</ul><div class="clear"></div></div></div></td><td class="l6"></td></tr><tr><td class="l1"></td><td class="l2"></td><td class="l3"></td></tr></table></div>';
                    t.after(s);
                    var a = $("#listinsertfacediv");
                    a.css({
                        left: t.position().left,
                        top: t.position().top + 20
                    }),
                    $("li", a).click(function() {
                        o(n.textinput, "[" + $(this).attr("title") + "]"),
                        a.hide()
                    }),
                    $(document).click(function(t) {
                        var e = $(t.target);
                        e.is("#listinsertfacediv") || e.parents().is("#listinsertfacediv") || e.is("#gbtainpubtn4") || e.parents().is("#gbtainpubtn4") || $("#listinsertfacediv").hide()
                    }),
                    i = !0
                }
            }),
            !1
        }
        ,
        t
    }();
    t.exports = n
}
, function() {
    !function(t) {
        "use strict";
        var e, o, n, i, a, s, l, r, c, d, u;
        d = "caret",
        e = function() {
            function e(t) {
                this.$inputor = t,
                this.domInputor = this.$inputor[0]
            }
            return e.prototype.setPos = function() {
                return this.domInputor
            }
            ,
            e.prototype.getIEPosition = function() {
                return this.getPosition()
            }
            ,
            e.prototype.getPosition = function() {
                var t, e;
                return e = this.getOffset(),
                t = this.$inputor.offset(),
                e.left -= t.left,
                e.top -= t.top,
                e
            }
            ,
            e.prototype.getOldIEPos = function() {
                var t, e;
                return e = l.selection.createRange(),
                t = l.body.createTextRange(),
                t.moveToElementText(this.domInputor),
                t.setEndPoint("EndToEnd", e),
                t.text.length
            }
            ,
            e.prototype.getPos = function() {
                var t, e, o;
                return (o = this.range()) ? (t = o.cloneRange(),
                t.selectNodeContents(this.domInputor),
                t.setEnd(o.endContainer, o.endOffset),
                e = t.toString().length,
                t.detach(),
                e) : l.selection ? this.getOldIEPos() : void 0
            }
            ,
            e.prototype.getOldIEOffset = function() {
                var t, e;
                return t = l.selection.createRange().duplicate(),
                t.moveStart("character", -1),
                e = t.getBoundingClientRect(),
                {
                    height: e.bottom - e.top,
                    left: e.left,
                    top: e.top
                }
            }
            ,
            e.prototype.getOffset = function() {
                var e, o, n, i, a;
                return c.getSelection && (n = this.range()) ? (n.endOffset - 1 > 0 && n.endContainer === !this.domInputor && (e = n.cloneRange(),
                e.setStart(n.endContainer, n.endOffset - 1),
                e.setEnd(n.endContainer, n.endOffset),
                i = e.getBoundingClientRect(),
                o = {
                    height: i.height,
                    left: i.left + i.width,
                    top: i.top
                },
                e.detach()),
                o && 0 !== (null != o ? o.height : void 0) || (e = n.cloneRange(),
                a = t(l.createTextNode("|")),
                e.insertNode(a[0]),
                e.selectNode(a[0]),
                i = e.getBoundingClientRect(),
                o = {
                    height: i.height,
                    left: i.left,
                    top: i.top
                },
                a.remove(),
                e.detach())) : l.selection && (o = this.getOldIEOffset()),
                o && (o.top += t(c).scrollTop(),
                o.left += t(c).scrollLeft()),
                o
            }
            ,
            e.prototype.range = function() {
                var t;
                if (c.getSelection)
                    return t = c.getSelection(),
                    t.rangeCount > 0 ? t.getRangeAt(0) : null
            }
            ,
            e
        }(),
        o = function() {
            function e(t) {
                this.$inputor = t,
                this.domInputor = this.$inputor[0]
            }
            return e.prototype.getIEPos = function() {
                var t, e, o, n, i, a, s;
                return e = this.domInputor,
                a = l.selection.createRange(),
                i = 0,
                a && a.parentElement() === e && (n = e.value.replace(/\r\n/g, "\n"),
                o = n.length,
                s = e.createTextRange(),
                s.moveToBookmark(a.getBookmark()),
                t = e.createTextRange(),
                t.collapse(!1),
                i = s.compareEndPoints("StartToEnd", t) > -1 ? o : -s.moveStart("character", -o)),
                i
            }
            ,
            e.prototype.getPos = function() {
                return l.selection ? this.getIEPos() : this.domInputor.selectionStart
            }
            ,
            e.prototype.setPos = function(t) {
                var e, o;
                return e = this.domInputor,
                l.selection ? (o = e.createTextRange(),
                o.move("character", t),
                o.select()) : e.setSelectionRange && e.setSelectionRange(t, t),
                e
            }
            ,
            e.prototype.getIEOffset = function(t) {
                var e, o, n, i;
                return o = this.domInputor.createTextRange(),
                t || (t = this.getPos()),
                o.move("character", t),
                n = o.boundingLeft,
                i = o.boundingTop,
                e = o.boundingHeight,
                {
                    left: n,
                    top: i,
                    height: e
                }
            }
            ,
            e.prototype.getOffset = function(e) {
                var o, n, i;
                return o = this.$inputor,
                l.selection ? (n = this.getIEOffset(e),
                n.top += t(c).scrollTop() + o.scrollTop(),
                n.left += t(c).scrollLeft() + o.scrollLeft(),
                n) : (n = o.offset(),
                i = this.getPosition(e),
                n = {
                    left: n.left + i.left - o.scrollLeft(),
                    top: n.top + i.top - o.scrollTop(),
                    height: i.height
                })
            }
            ,
            e.prototype.getPosition = function(t) {
                var e, o, i, a, s, l, r;
                return e = this.$inputor,
                a = function(t) {
                    return t = t.replace(/<|>|`|"|&/g, "?").replace(/\r\n|\r|\n/g, "<br/>"),
                    /firefox/i.test(navigator.userAgent) && (t = t.replace(/\s/g, "&nbsp;")),
                    t
                }
                ,
                void 0 === t && (t = this.getPos()),
                r = e.val().slice(0, t),
                i = e.val().slice(t),
                s = "<span style='position: relative; display: inline;'>" + a(r) + "</span>",
                s += "<span id='caret' style='position: relative; display: inline;'>|</span>",
                s += "<span style='position: relative; display: inline;'>" + a(i) + "</span>",
                l = new n(e),
                o = l.create(s).rect()
            }
            ,
            e.prototype.getIEPosition = function(t) {
                var e, o, n, i, a;
                return n = this.getIEOffset(t),
                o = this.$inputor.offset(),
                i = n.left - o.left,
                a = n.top - o.top,
                e = n.height,
                {
                    left: i,
                    top: a,
                    height: e
                }
            }
            ,
            e
        }(),
        n = function() {
            function e(t) {
                this.$inputor = t
            }
            return e.prototype.css_attr = ["borderBottomWidth", "borderLeftWidth", "borderRightWidth", "borderTopStyle", "borderRightStyle", "borderBottomStyle", "borderLeftStyle", "borderTopWidth", "boxSizing", "fontFamily", "fontSize", "fontWeight", "height", "letterSpacing", "lineHeight", "marginBottom", "marginLeft", "marginRight", "marginTop", "outlineWidth", "overflow", "overflowX", "overflowY", "paddingBottom", "paddingLeft", "paddingRight", "paddingTop", "textAlign", "textOverflow", "textTransform", "whiteSpace", "wordBreak", "wordWrap"],
            e.prototype.mirrorCss = function() {
                var e, o = this;
                return e = {
                    position: "absolute",
                    left: -9999,
                    top: 0,
                    zIndex: -2e4
                },
                "TEXTAREA" === this.$inputor.prop("tagName") && this.css_attr.push("width"),
                t.each(this.css_attr, function(t, n) {
                    return e[n] = o.$inputor.css(n)
                }),
                e
            }
            ,
            e.prototype.create = function(e) {
                return this.$mirror = t("<div></div>"),
                this.$mirror.css(this.mirrorCss()),
                this.$mirror.html(e),
                this.$inputor.after(this.$mirror),
                this
            }
            ,
            e.prototype.rect = function() {
                var t, e, o;
                return t = this.$mirror.find("#caret"),
                e = t.position(),
                o = {
                    left: e.left,
                    top: e.top,
                    height: t.height()
                },
                this.$mirror.remove(),
                o
            }
            ,
            e
        }(),
        i = {
            contentEditable: function(t) {
                return !(!t[0].contentEditable || "true" !== t[0].contentEditable)
            }
        },
        s = {
            pos: function(t) {
                return t || 0 === t ? this.setPos(t) : this.getPos()
            },
            position: function(t) {
                return l.selection ? this.getIEPosition(t) : this.getPosition(t)
            },
            offset: function(t) {
                var e;
                return e = this.getOffset(t)
            }
        },
        l = null,
        c = null,
        r = null,
        u = function(t) {
            var e;
            return (e = null != t ? t.iframe : void 0) ? (r = e,
            c = e.contentWindow,
            l = e.contentDocument || c.document) : (r = void 0,
            c = window,
            l = document)
        }
        ,
        a = function(t) {
            var e;
            l = t[0].ownerDocument,
            c = l.defaultView || l.parentWindow;
            try {
                return r = c.frameElement
            } catch (o) {
                e = o
            }
        }
        ,
        t.fn.caret = function(n, a, l) {
            var r;
            return s[n] ? (t.isPlainObject(a) ? (u(a),
            a = void 0) : u(l),
            r = i.contentEditable(this) ? new e(this) : new o(this),
            s[n].apply(r, [a])) : t.error("Method " + n + " does not exist on jQuery.caret")
        }
        ,
        t.fn.caret.EditableCaret = e,
        t.fn.caret.InputCaret = o,
        t.fn.caret.Utils = i,
        t.fn.caret.apis = s
    }(jQuery)
}
, function() {}
, , function() {
    !function(t) {
        t.fn.textBlink = function(e) {
            var o = {
                color: ["#fff", "#ffe2d1", "#ffc2a1", "#ffa370", "#ff8340", "#ff630f"],
                blinktime: 60,
                circle: 2
            }
              , e = t.extend(o, e)
              , n = 0;
            this.each(function() {
                for (var o = t(this), i = 0; i < e.color.length * e.circle; i++)
                    setTimeout(function() {
                        o.css("background-color", e.color[n]),
                        n++,
                        n %= e.color.length
                    }, e.blinktime * i)
            })
        }
    }(jQuery)
}
, function(t, e, o) {
    var n = o(2)
      , i = {
        compatible: function() {
            return window.localStorage && "function" == typeof localStorage.setItem && "function" == typeof localStorage.getItem && "function" == typeof localStorage.removeItem
        },
        get: function(t) {
            return this.compatible() ? localStorage.getItem(t) : n.get(t)
        },
        save: function(t, e) {
            this.compatible() ? localStorage.setItem(t, e) : n.set(t, e, 30)
        },
        del: function(t) {
            this.compatible() ? localStorage.removeItem(t) : n.del(t)
        }
    };
    t.exports = i
}
, function(t) {
    function e() {
        function t(t) {
            if (t > l && l + r > t) {
                var e = t - l;
                o.css({
                    top: e
                }),
                i.css({
                    top: e
                }),
                s.css({
                    top: e
                })
            } else
                o.css({
                    top: 0
                }),
                i.css({
                    top: 0
                }),
                s.css({
                    top: 0
                })
        }
        var e = $("#leftTable")
          , o = $("th", e);
        o.css({
            position: "relative",
            backgroundColor: "#fff",
            zIndex: 5
        });
        var n = $("#mainTable")
          , i = $("th", n);
        i.css({
            position: "relative",
            backgroundColor: "#fff",
            zIndex: 5
        });
        var a = $("#rightTable")
          , s = $("th", a);
        s.css({
            position: "relative",
            backgroundColor: "#fff",
            zIndex: 5
        });
        var l = n.offset().top
          , r = n.height() - 40
          , c = $(window).scrollTop();
        t(c),
        $(window).on("resize scroll", function() {
            var e = $(window).scrollTop();
            t(e)
        })
    }
    t.exports = {
        bindtableheader: e
    }
}
]);
//# sourceMappingURL=index.js.map
