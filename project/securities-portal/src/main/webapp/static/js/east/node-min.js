function getCookie(t) {
    var e = document.cookie;
    t += "=";
    for (var i = 0; i < e.length;) {
        var n = i + t.length;
        if (e.substring(i, n) == t) {
            var o = e.indexOf(";", n);
            return -1 == o && (o = e.length), unescape(e.substring(n, o));
        }
        if (i = e.indexOf(" ", i) + 1, 0 == i)
            break;
    }
    return null;
}
function loginSuccessCallback() {
    self.location.reload();
}
function loginFrame() {
    var t = "https://exaccount.eastmoney.com/Home/WebLogin?request={0}", e = { autoResize: !0, iframeId: "frameLogin", agentPageUrl: "{protocol}//{hostname}:{port}/help/WebLoginAgent.html".format(self.location), redirectUrl: self.location.href, callBack: "loginSuccessCallback", data: { domainName: document.domain, deviceType: "web", productType: "dongci", versionId: "1.0.0" } };
    t = t.format(encodeURIComponent(JSON.stringify(e)));
    var i = '<div class="gbpopboxclose" id="closeG" title="关闭">X</div><div class="gbpopboxtitle">使用该功能请先登录！</div><div class="gbpopboxbody"><iframe id=\'frameLogin\' style=\'border:0;\' height="350" width="380" src="' + t + '"></iframe></div>', n = document.createElement("div");
    n.className = "gbpopbox", n.innerHTML = i, document.body.appendChild(n);
    var o = document.documentElement.scrollTop || document.body.scrollTop, s = (document.body.clientWidth - n.clientWidth) / 2;
    n.style.left = s + "px", n.style.top = o + window.screen.height / 10 + "px", window.onresize = function () {
        var t = document.documentElement.scrollTop || document.body.scrollTop, e = (document.body.clientWidth - n.clientWidth) / 2;
        n.style.left = e + "px", n.style.top = t + window.screen.height / 10 + "px";
    }, $x("closeG").onclick = function () {
        document.body.removeChild(n);
    };
}
function tixing(t, e, i) {
    if ($x(t) == null) return;
    $x(t).onclick = function () {
        var t = new EMNode;
        t.init(e, i);
    };
}
function zhuce(t) {
    $x(t).onclick = function () {
        var t = new login;
        t.init();
    };
}
String.prototype.Trim = function () {
    return this.replace(/(^\s*)|(\s*$x)/g, "");
}, String.prototype.format = function (t) {
    if (arguments.length > 0) {
        var e = this;
        if (1 == arguments.length && "object" == typeof t)
            for (var i in t) {
                var n = new RegExp("({" + i + "})", "g");
                e = e.replace(n, t[i]);
            }
        else
            for (var o = 0; o < arguments.length; o++) {
                if (!arguments[o])
                    return "";
                var n = new RegExp("({[" + o + "]})", "g");
                e = e.replace(n, arguments[o]);
            }
        return e;
    }
    return this;
};
var textBlink = function (t, e) {
    for (var i = { color: ["#fff", "#ffe2d1", "#ffc2a1", "#ffa370", "#ff8340", "#ff630f"], blinktime: 60, circle: 2 }, t = FUC.xcopy(t, i), n = 0, o = 0; o < t.color.length * t.circle; o++)
        setTimeout(function () {
            e.style.backgroundColor = t.color[n], n++, n %= t.color.length;
        }, t.blinktime * o);
}, loadScript = function (t, e, i) {
    var n = document.createElement("script");
    n.type = "text/javascript", n.charset = i || "utf-8", n._fun = "undefined" != typeof e ? e : new Function, n[document.all ? "onreadystatechange" : "onload"] = function () {
        if (!document.all || "loaded" == this.readyState || "complete" == this.readyState) {
            this._fun(this), this._fun = null, this[document.all ? "onreadystatechange" : "onload"] = null;
            var t = this;
            t.parentNode.removeChild(t);
        }
    }, n.src = t, document.getElementsByTagName("head").item(0).appendChild(n);
}, EMNode = function () {
    this.dom = "", this.code = "", this.type = "", this.incId = "", this._tixing = "tixinglight", this._tixingClose = "tixingclose", this._tixingSave = "sassubmit", this._tixingcancel = "sascancel", this._tixingClear = "sasclear", this._getDataUrl = "http://mystock.eastmoney.com/Message.aspx?type=getsettings&code=", this.hqUrl = " http://" + getdomain() + "/EM_Finance2014NumericApplication/JS.aspx?token=" + window.token, this._saveUrl = "http://mystock.eastmoney.com/Message.aspx?type=set&stockadd=1&code=", this.init = function (t, e) {
        return null == getCookie("pi") ? void loginFrame() : (this.dom = $x(this._tixing), this.code = e, this.type = t, this.coverbg(), void (this.dom ? this.getData(t, e) : (this.dom = document.createElement("div"), this.dom.id = "tixinglight", this.getData(t, e))));
    }, this.drawNote = function (t, e, i) {
        {
            var n, o, s, a, l, c, d, r = e.notice, h = e.report;
            e.data;
        }
        this.incId = e.incId, a = 2 == r && 2 == h && 2 == h ? !1 : !0, l = i.notnull(e.topPrice) ? e.topPrice.replace("*", "") : "", c = i.notnull(e.bottomPrice) ? e.bottomPrice.replace("*", "") : "", d = i.notnull(e.amplitude) ? e.amplitude.replace("*", "") : "", n = i.notnull(l) ? !0 : !1, o = i.notnull(c) ? !0 : !1, s = i.notnull(d) ? !0 : !1;
        var u = '<div class="tixingclose" id="tixingclose" title="关闭">X</div><div class="tixingtitle"><em class="alarmbell"></em>自选股提醒</div><iframe class="gbpopboxbody" style="z-index: -1;filter: mask();border: 0;filter: alpha(opacity=0); -moz-opacity: 0;-khtml-opacity:0;opacity: 0;width:458px;height:302px;position: absolute;"></iframe><div class="tixingbody" id="tixingbody"><div id="stockalarmset"><div class="sastitle" id="sastitle"><a href="http://guba.eastmoney.com/list,' + t[1] + '.html" target="_blank" class="stockname">' + t[2] + "</a>（" + t[1] + '）当前股价：<span id="nowprice" class="red"><strong>' + t[3] + '</strong></span></div><div class="sasaset"><div class="item"><input type="checkbox" id="sasi1"><label for="sasi1">	当有最新公司公告、数据、研究报告时提醒</label></div></div><div class="sasalarm"><div class="sasalarmtitle">股价预警</div><div class="item"><input type="checkbox" id="sasi4"><label for="sasi4">股价突破</label><input type="text" id="sasinum1" class="inty" value="' + l + '">  元 <span id="sasinum1error" class="red"></span></div><div class="item"><input type="checkbox" id="sasi5"><label for="sasi5">	股价跌破</label><input type="text" id="sasinum2" class="inty" value="' + c + '">	元 <span id="sasinum2error" class="red"></span></div><div class="item"><input type="checkbox" id="sasi6"><label for="sasi6">	日涨跌幅达到</label><input type="text" id="sasinum3" class="inty" value="' + d + '">	％ <span id="sasinum3error" class="red"></span></div></div><div class="sasbtns"><a href="javascript:;" target="_self" id="sasclear">清空</a><div class="btnsdiv"><div class="btns"><button id="sassubmit">确 定</button><button id="sascancel">取 消</button></div></div></div></div></div>';
        i.dom.innerHTML = u, document.body.appendChild(i.dom), $x("sasi1").checked = a, $x("sasi4").checked = n, $x("sasi5").checked = o, $x("sasi6").checked = s, this.operation();
        var m = document.documentElement.scrollTop || document.body.scrollTop, p = (document.body.clientWidth - this.dom.clientWidth) / 2;
        i.dom.style.left = p + "px", i.dom.style.top = m + screen.height / 10 + "px";
    }, this.getData = function (t, e) {
        var i = this;
        loadScript(i.hqUrl + "&type=CT&cmd=" + e + t + "&sty=CTALL&cb=&js=var hq=[(x)]&" + Math.random(), function () {
            t = 2 == t ? 0 : t;
            var n = hq[0].split(",");
            loadScript(i._getDataUrl + t + "," + e + "&" + Math.random(), function () {
                i.drawNote(n, data, i);
            });
        });
    }, this.saveData = function () {
        var t = this, e = "", i = t.dom.getElementsByTagName("input");
        e = 2 == this.type ? 0 : this.type;
        var n = this._saveUrl + e + "," + this.code;
        n += i[0].checked ? "&n=1&r=1&d=1" : "&n=2&r=2&d=2", i[1].checked ? n += "&tp=" + i[2].value : n, i[3].checked ? n += "&bp=" + i[4].value : n, i[5].checked ? n += "&zf=" + i[6].value : n, n += "&incId=" + this.incId + "&" + Math.random(), loadScript(n, function () {
            t.uncoverbg(), t.remove(), data.result && ($x("addZX1").innerHTML = '<b class="plus"></b>删自选', $x("addZX2").innerHTML = '<b class="plus"></b>删自选', $x("addZX1").onclick = function () {
                OpZX("dsz", t.code, t.type);
            }, $x("addZX2").onclick = function () {
                OpZX("dsz", t.code, t.type);
            });
        });
    }, this.coverbg = function () {
        var t = document.createElement("div");
        t.id = "loader_all_bg", _scrollWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth), _scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight), t.style.width = _scrollWidth + "px", t.style.height = _scrollHeight + "px", document.body.appendChild(t);
    }, this.uncoverbg = function () {
        var t = document.getElementById("loader_all_bg");
        null != t && document.body.removeChild(t);
    }, this.blur = function () {
        var t = $x("nowprice").getElementsByTagName("strong")[0].innerHTML, e = this.nextSibling.nextSibling || this.nextElementSibling.nextElementSibling, i = this.previousSibling.previousSibling || this.previousElementSibling.previousElementSibling;
        if (isNaN(this.value))
            return e.innerHTML = "必须输入数字", this.value = "", i.checked = !1, textBlink({ color: ["#FFDDDD", "#FFEEEE", "#fff"], blinktime: 150 }, this), !1;
        if ("sasinum1" == this.id) {
            if (this.value - t < 0)
                return e.innerHTML = "所填价格不可小于现价", this.value = "", i.checked = !1, textBlink({ color: ["#FFDDDD", "#FFEEEE", "#fff"], blinktime: 150 }, this), !1;
            e.innerHTML = "此价格较现价的涨幅为" + Math.round(this.value / t * 100 - 100) + "%", i.checked = !0;
        }
        else if ("sasinum2" == this.id) {
            if (this.value - t >= 0)
                return e.innerHTML = "所填价格不可大于现价", this.value = "", i.checked = !1, textBlink({ color: ["#FFDDDD", "#FFEEEE", "#fff"], blinktime: 150 }, this), !1;
            e.innerHTML = "此价格较现价的跌幅为" + Math.round((this.value - t) / t * 100) + "%", i.checked = !0;
        }
        else
            "sasinum3" == this.id && "" != this.value && (i.checked = !0);
    }, this.operation = function () {
        var t = this;
        this.close(), this.clearDom(), this.confirm();
        for (var e = $x("tixinglight").getElementsByTagName("input"), i = 0; i < e.length; i++)
            "text" == e[i].type && (e[i].onblur = t.blur);
    }, this.close = function () {
        var t = this;
        $x(this._tixingClose).onclick = function () {
            t.uncoverbg(), t.remove();
        }, $x(this._tixingcancel).onclick = function () {
            t.uncoverbg(), t.remove();
        };
    }, this.confirm = function () {
        var t = this;
        $x(this._tixingSave).onclick = function () {
            t.saveData();
        };
    }, this.clearDom = function () {
        var t = this;
        $x(this._tixingClear).onclick = function () {
            for (var e = t.dom.getElementsByTagName("input"), i = e.length, n = 0; i > n; n++)
                "text" == e[n].type ? e[n].value = "" : "checkbox" == e[n].type && (e[n].checked = !1);
        };
    }, this.remove = function () {
        document.body.removeChild(this.dom);
    }, this.notnull = function (t) {
        return "" == t || "undefined" == typeof t ? !1 : !0;
    };
};
