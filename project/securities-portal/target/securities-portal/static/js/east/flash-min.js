var TabSwitch = function () {
    this.Init.apply(this, arguments)
};
TabSwitch.prototype = {
    Init: function (tabParent, conParent, type, classNm, fun) {
        this.tabs = [];
        var _this = this;
        var tempNodes = tabParent.childNodes;
        for (var i = 0; i < tempNodes.length; i++) {
            if (tempNodes[i].nodeType == 1) this.tabs.push(tempNodes[i])
        }
        this.activeTab = this.tabs[0];
        if (conParent) {
            this.targets = [];
            var tempATNodes = conParent.childNodes;
            for (var i = 0; i < tempATNodes.length; i++) {
                if (tempATNodes[i].nodeType == 1) this.targets.push(tempATNodes[i])
            }
            this.activeTarget = this.targets[0]
        }
        if (fun) {
            for (var i = 0; i < this.tabs.length; i++) {
                if ((tabParent.id == "actTab2") || tabParent.id == "actTab1") {
                    if (tabParent.id == "actTab1") {
                        this.tabs[i].onclick = this._Bind(this.showTab, i, classNm, fun, "r");
                    } else {
                        this.tabs[i].onclick = this._Bind(this.showTab, i, classNm, fun, "k")
                    }
                }
            }
        }
    },
    showTab: function (num, classNm, fun, rk) {
        if (num <= 7) {
            var aTclass = "cur";
            if (classNm) aTclass = classNm;
            if (this.tabs[num] == this.activeTab) return;
            if (this.activeTab) {
                this.activeTab.className = "";
            }
            this.activeTab = this.tabs[num];
            this.activeTab.className = aTclass;

            if (this.targets) {
                if (this.activeTarget) this.activeTarget.style.display = "none";
                this.activeTarget = this.targets[num];
                this.activeTarget.style.display = ""
            }
            if (fun) {
                this.activeCallback = fun;
                fun(num)
            }
        }
    },
    _Bind: function (fun) {
        var d = fun,
            b = this,
            a = new Array();
        for (var c = 1; c < arguments.length; c++) {
            a.push(arguments[c])
        }
        return function () {
            return d.apply(b, a)
        }
    }
};
var StockAnalyser = function () {
    this.Init.apply(this, arguments)
};
StockAnalyser.prototype = {
    Init: function (mObj, swfsrc, vars, width, height, id, wmode, verson) {
        this.kMT = false;
        this.creatFlash(mObj, swfsrc, vars, width, height, id, wmode, verson)
    },
    creatFlash: function (mObj, swfsrc, vars, width, height, id, wmode, verson) {
        if (swfsrc != null) {
            var temp_str = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=';
            if (verson == null) temp_str += '9,0,0,0"';
            else temp_str += verson + ',0,0,0"'; if (width != null) temp_str += ' width="' + width + '"';
            if (height != null) temp_str += ' height="' + height + '"';
            if (id != null) temp_str += ' id="' + id + '"';
            temp_str += '>\n' + '<param name="movie" value="' + swfsrc + '" />' + '<param name="quality" value="high" /><param name="allowFullScreen" value="true" /><param name="allowScriptAccess" value="always" />';
            if (wmode) temp_str += '<param name="wmode" value="transparent">';
            else temp_str += '<param name="wmode" value="' + wmode + '">';
            temp_str += '<param name="flashvars" value="' + vars + '">';
            temp_str += '<embed src="' + swfsrc + '"';
            if (width != null) temp_str += ' width="' + width + '"';
            if (height != null) temp_str += ' height="' + height + '"';
            temp_str += ' quality="high" allowFullScreen="true" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" allowScriptAccess="always"';
            if (wmode) temp_str += ' wmode="' + wmode + '"';
            if (id != null) temp_str += ' name="' + id + '" id="' + id + '"';
            temp_str += ' flashvars="' + vars + '"';
            temp_str += '></embed></object>';
            $x(mObj).innerHTML = temp_str;
            this.FlashObj = document[id] || window[id]
        }
    },
    selectMainType: function (type) {
        if (this.kMT) this.FlashObj.selectButton(10, type)
    },
    selectMainTech: function (tech) {
        if (this.kMT) this.FlashObj.selectMainTech(tech)
    },
    selectCoordinate: function (type) {
        if (this.kMT) this.FlashObj.selectCoordinate(type)
    },
    selectOffTech: function (tech) {
        if (this.kMT) this.FlashObj.selectOffTech(tech)
    },
    addStock: function (code, market, name) {
        this.FlashObj.addStock(code, market, name)
    },
    zoomTech: function (type) {
        this.FlashObj.zoomTech(type)
    },
    selectDay: function (type) {
        this.FlashObj.selectDay(type)
    }
};
var stock = null,
    stockr = null;
var flsfileurl = "http://g1.dfcfw.com/g1/special/emstock.swf?cross=xml&201411111811";
var reInterval = function () {
    return 6
};

function createSWF(stockcode, market, stockname, frw, frh, fkw, fkh, flstt, ispq, lxst) {
    var flscke = checkflash();
    var em_hq_fls = GetCookie("em_hq_fls");
    if (!flscke.f || /\((iPhone|iPad|iPod)/i.test(navigator.userAgent) || em_hq_fls != "new") {
        showfls();//showimg();
    } else {
        $x("flash_box").style.display = "block";
        var c_cn = $x("kx_list").className;
        if (c_cn.indexOf("fz14") > 0) {
            $x("kx_list").className = "kx_list kx_fz14 kx_listfls";
        } else {
            $x("kx_list").className = "kx_list kx_listfls";
        }
    }
    if (fkw != "-" && fkh != "-") {
        stock = new StockAnalyser("flashPK", flsfileurl, "sc=" + stockcode + "&mt=" + market + "&sn=" + encodeURI(stockname) +
        "&linetype=1&slider=1&tt=" + flstt + "&st=" + lxst, fkw, fkh, "stockAnalyser", true);
        var tabCtrl = new TabSwitch($x("actTab2"), null, "click", "cur", function (index) {
            stock.kMT = true;
            stock.FlashObj.selectButton(index+1, 10);
        })
    }
    if (frw != "-" && frh != "-") {
        if (fkw != "-" && fkh != "-") {
            stockr = new StockAnalyser("flashPR", flsfileurl, "sc=" + stockcode + "&mt=" + market + "&sn=" + encodeURI(stockname) + "&linetype="
            + ispq + "&slider=0&tt=" + flstt + "&st=" + lxst, frw, frh, "stockAnalyser_R", true);
        } else {
            stockr = new StockAnalyser("flashPR", flsfileurl, "sc=" + stockcode + "&mt=" + market + "&sn=" + encodeURI(stockname) + "&linetype="
            + ispq + "&slider=1&tt=" + flstt + "&st=" + lxst, frw, frh, "stockAnalyser_R", true);
        }
        var tabrCtrl = new TabSwitch($x("actTab1"), null, "click", "cur", function (index) {
            stockr.selectDay(index);
        })
    }

}
var rkarg = {
    text: "拼音/代码/名称",
    width: 190,
    type: "CNSTOCK",
    autoSubmit: false,
    header: ["选项", "代码", "名称", "类型"],
    body: [-1, 1, 4, -2],
    callback: function (arg) { }
};

function prgetfs() {
    var rPort = document.getElementsByName("prdb");
    rPort[0].checked = true
}

function hyrck(hyw, hyh, flstt, ispq, lxst) {
    stockr = new StockAnalyser("flashPR", flsfileurl, "sc=" + stockcode + "&mt=" + market + "&sn=" + encodeURI(stockname) + "&linetype=" + ispq +
    "&slider=1&tt=" + flstt + "&st=" + lxst, hyw, hyh, "stockAnalyser_R", true);
}

function dbkck() {
    //Mini.Loader.load("http://js1.eastmoney.com/tg.aspx?ID=1978&r=" + Math.random(), "gb2312", function () { });
    var rPort = document.getElementsByName("pkdb");
    var value = document.getElementById("select_flsdb").getElementsByTagName("span")[0].getAttribute("value");
    var kv = document.getElementById("CompareCode").value;
    var sel = "";
    for (var i = 0; i < rPort.length; i++) {
        if (rPort[i].checked) {
            sel = i; break
        }
    }
    switch (sel) {
        case "":
            alert("请先选择对比类型!");return false; break;
        case 1:
            if (kv == "" || kv == "拼音/代码/名称") {alert("请输入要对比的股票!");return false
            } else {
                Mini.Loader.load("http://quote.eastmoney.com/allstocklist.js?r=" + Math.random(), "gb2312", function () {
                    var cz = false;
                    var res = "";
                    for (var i = 0; i < terms.length; i++) {if (terms[i].Code == kv) {res = terms[i].Code + "|" + terms[i].Market + "|" + terms[i].Name;cz = true; break;}}
                    if (!cz) {
                        alert("请输入正确的股票代码!");return false;
                    } else {
                        var skn = res.split("|"); stock.addStock(skn[0], skn[1], skn[2]);
                    }
                })
            }
            break;
        case 0:
            if (value == "A") {
                stock.addStock("000001", "1", "上证指数"); break;
            } else if (value == "B") {
                stock.addStock("399001", "2", "深证成指"); break;
            } else if (value == "C") {
                stock.addStock("000003", "1", "上证B指"); break;
            } else if (value == "D") {
                stock.addStock("399006", "2", "创业板指数"); break;
            }
    }
}

function duibik(stockcode, stockmarket, stockname) {
    stock.addStock(stockcode, stockmarket, stockname)
}

function pkgetfs() {
    var rPort = document.getElementsByName("pkdb");
    rPort[1].checked = true
}

function hykck(code, market, name, hyw, hyh, flstt, lxst) {
    //Mini.Loader.load("http://js1.eastmoney.com/tg.aspx?ID=1979&r=" + Math.random(), "gb2312", function () { });
    stock = new StockAnalyser("flashPK", flsfileurl, "sc=" + code + "&mt=" + market + "&sn=" + encodeURI(name) + "&slider=1&linetype=1&tt=" +
    flstt + "&st=" + lxst, hyw, hyh, "stockAnalyser", true);
    $("CompareCode").value = "拼音/代码/名称"
}

function flyqd(dis) {
    try { stockr.FlashObj.selectButton(dis, 10); } catch (e) { }
}

function gRequest(strName) {
    var strHref = window.document.location.href;
    var intPos = strHref.indexOf("?");
    var strRight = strHref.substr(intPos + 1);
    var arrTmp = strRight.split("&");
    for (var i = 0; i < arrTmp.length; i++) {
        var arrTemp = arrTmp[i].split("=");
        if (arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1]
    }
    return ""
}

function checkflash() {
    var hasFlash = 0;
    var flashVersion = 0;
    if (window.ActiveXObject) {
        try {
            var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
            if (swf) {
                hasFlash = 1;
                VSwf = swf.GetVariable("$version");
                flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0])
            }
        } catch (e) { }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i]))) continue;
                    flashVersion = parseInt(words[i])
                }
            }
        }
    }
    return {
        f: hasFlash,
        v: flashVersion
    }
}

var Mini = new Object();
Mini.Loader = {
    load: function (sUrl, sBianMa, fCallback) {
        var _script = document.createElement('script');
        _script.setAttribute('charset', sBianMa);
        _script.setAttribute('type', 'text/javascript');
        _script.setAttribute('src', sUrl);
        document.getElementsByTagName('head')[0].appendChild(_script);
        if (Browser.ie) {
            _script.onreadystatechange = function () {
                if (this.readyState == 'loaded' || this.readyState == 'complete') {
                    _script.parentNode.removeChild(_script);
                    fCallback()
                }
            }
        } else if (Browser.moz || Browser.opera) {
            _script.onload = function () {
                _script.parentNode.removeChild(_script);
                fCallback()
            }
        } else {
            _script.parentNode.removeChild(_script);
            fCallback()
        }
    }
};

