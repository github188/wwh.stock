/* StockSuggest */
(function () {
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
    var Base = Class.create();
    Object.extend(Function.prototype, {
        bind: function () {
            var __m = this,
                object = arguments[0],
                args = new Array();
            for (var i = 1; i < arguments.length; i++) {
                args.push(arguments[i])
            }
            return function () {
                return __m.apply(object, args)
            }
        }
    });
    Object.extend(Base.prototype, {
        initialize: function () { },
        Browser: {
            IE: !!(window.attachEvent && navigator.userAgent.indexOf('Opera') === -1),
            Opera: navigator.userAgent.indexOf('Opera') > -1,
            WebKit: navigator.userAgent.indexOf('AppleWebKit/') > -1,
            Gecko: navigator.userAgent.indexOf('Gecko') > -1 && navigator.userAgent.indexOf('KHTML') === -1,
            MobileSafari: !!navigator.userAgent.match(/Apple.*Mobile.*Safari/)
        },
        $: function (o) {
            return typeof (o) == 'string' ? document.getElementById(o) : o
        },
        $Tag: function (o) {
            return typeof (o) == 'string' ? document.getElementsByTagName(o) : o
        },
        $C: function (o) {
            return document.createElement(o)
        },
        $E: function (e) {
            tempObj = e.target ? e.target : event.srcElement;
            return tempObj
        },
        $aE: function (elm, evType, fn, useCapture) {
            if (elm) {
                if (elm.addEventListener) {
                    elm.addEventListener(evType, fn, useCapture);
                    return true
                } else if (elm.attachEvent) {
                    var r = elm.attachEvent('on' + evType, fn);
                    return r
                } else {
                    elm['on' + evType] = fn
                }
            }
        },
        $dE: function (elm, evType, fn, useCapture) {
            if (elm.removeEventListener) {
                elm.removeEventListener(evType, fn, useCapture);
                return true
            } else if (elm.detachEvent) {
                var r = elm.detachEvent('on' + evType, fn);
                return r
            } else {
                elm['on' + evType] = null;
                return
            }
        },
        stopBubble: function (e) {
            if (!this.Browser.IE) {
                e.stopPropagation()
            } else {
                window.event.cancelBubble = true
            }
        },
        isNullorEmpty: function (obj) {
            if (obj == null || obj == "" || obj == "undefined") {
                return true
            }
            return false
        },
        getXY: function (obj) {
            var curleft = 0;
            var curtop = 0;
            var border;
            if (obj.offsetParent) {
                do {
                    curleft += obj.offsetLeft;
                    curtop += obj.offsetTop;
                    if (this.getStyle(obj, 'position') == 'relative') {
                        if (border = this.getStyle(obj, 'border-top-width')) curtop += parseInt(border);
                        if (border = this.getStyle(obj, 'border-left-width')) curleft += parseInt(border);
                        break
                    }
                } while (obj = obj.offsetParent)
            } else if (obj.x) {
                curleft += obj.x;
                curtop += obj.y
            }
            return {
                'x': curleft,
                'y': curtop
            }
        },
        getStyle: function (obj, styleProp) {
            if (obj.currentStyle) return obj.currentStyle[styleProp];
            else if (window.getComputedStyle) return document.defaultView.getComputedStyle(obj, null).getPropertyValue(styleProp)
        }
    });
    var Gee = new Base();
    var StockSuggest = Class.create();
    Object.extend(StockSuggest.prototype, {
        initialize: function (obj, arg) {
            this.input = obj;
            this.dataurl = "http://suggest.eastmoney.com/SuggestData/Default.aspx?name={#NAME}&input={#KEY}&type={#TYPE}";
            if (!Gee.isNullorEmpty(arg.dataurl)) this.dataurl = arg.dataurl;
            this.autoSubmit = Gee.isNullorEmpty(arg.autoSubmit) ? false : arg.autoSubmit;
            this.type = Gee.isNullorEmpty(arg.type) ? "" : arg.type;
            this.link = Gee.isNullorEmpty(arg.link) ? "" : arg.link;
            this.width = Gee.isNullorEmpty(arg.width) ? "" : arg.width;
            this.opacity = Gee.isNullorEmpty(arg.opacity) ? 1 : arg.opacity;
            this.className = Gee.isNullorEmpty(arg.className) ? "" : arg.className;
            this.max = Gee.isNullorEmpty(arg.max) ? 10 : arg.max;
            this.text = Gee.isNullorEmpty(arg.text) ? "请输入..." : arg.text;
            this.header = Gee.isNullorEmpty(arg.header) ? ["选项", "代码", "名称"] : arg.header;
            this.body = Gee.isNullorEmpty(arg.body) ? [-1, 1, 4] : arg.body;
            this.callback = (arg.callback == null || arg.callback == "undefined") ? null : arg.callback;
            this.showAd = (arg.showAd == null || arg.showAd == "undefined") ? true : arg.showAd;
            this.moreLink = (arg.moreLink == null || arg.moreLink == "undefined") ? true : arg.moreLink;

            this.results = null;
            this._D = null;
            this._F = null;
            this._R = null;
            this._W = null;
            this._X = {};
            this._Y = {};
            this._hidden = false;
            this.Market = "";
            this.mType = "";
            this.SName = "";
            this._iF = null;
            this._iN = null;
            this._iC = null;
            this._oForm = null;
            this.StockType = {
                "0": "未知",
                "1": "A 股",
                "2": "B 股",
                "3": "权证",
                "4": "期货",
                "5": "债券",
                "10": "基金",
                "11": "开基",
                "12": "ETF",
                "13": "LOF",
                "14": "货基",
                "15": "QDII",
                "16": "封基",
                "21": "港股",
                "22": "窝轮",
                "31": "美股",
                "32": "外期",
                "40": "指数",
                "50": "期指"
            };
            this.ShowType = {
                "ABSTOCK": "1,2,3",
                "CNSTOCK": "1,2,3,10,50",
                "CNFUND": "11,12,13,14,15,16",
                "HKSTOCK": "21,22",
                "USASTOCK": "31",
                "ABSHKSTOCK": "1,2,3,21"
            };
            this.init()
        },
        init: function () {
            this._Y = {};
            this.input = typeof (this.input) == "string" ? Gee.$(this.input) : this.input;
            if (this.input) {
                if (this._F == null) {
                    var FormNode = this.input.parentNode;
                    while (FormNode.nodeName.toLowerCase() == "form" && FormNode.nodeName.toLowerCase() == "body") {
                        FormNode = FormNode.parentNode
                    }
                    if (FormNode.nodeName.toLowerCase() == "form") {
                        this._oForm = {
                            action: FormNode.action,
                            target: FormNode.target,
                            method: FormNode.method,
                            onsubmit: FormNode.onsubmit
                        };
                        this._F = FormNode;
                    } else {
                        this._F = Gee.$C("form");
                        this._F.method = "get";
                        if (this.autoSubmit) {
                            this._F.target = "_blank"
                        } else {
                            this._F.target = "_self";
                            this._F.onsubmit = function () {
                                return false
                            }
                        }
                        this.input.parentNode.insertBefore(this._F, this.input);
                        var _i = this.input;
                        this.input.parentNode.removeChild(this.input);
                        this._F.appendChild(_i)
                    }
                }
                if (this.autoSubmit) {
                    this._F.onsubmit = function () {
                        return false
                    }
                }
                this.input.value = this.text;
                this.input.setAttribute("autocomplete", "off");
                this.input.autoComplete = "off";
                this._iF = this._bd(this.inputFocus);
                this._iN = this._bd(this.Navigate);
                this._iC = this._bd(this.Confirm);
                Gee.$aE(this.input, "focus", this._iF);
                Gee.$aE(this.input, "blur", this._iF);
                Gee.$aE(this.input, "keyup", this._iN);
                if (this.autoSubmit) Gee.$aE(this.input, "keydown", this._iC);

                Gee.$aE(this.input, "mouseup", this._iN)
            }
        },
        BindSearchClick: function (id) {
            if (id != null) {
                var submit1 = this._bd(function (e) {
                    
                    this.nGourl = true;
                    if (this.results != null && this.results.innerHTML != "") {
                        var isErr = false;
                        var _s = this.input.value;
                        var _u = "";
                        if (this._W != null) {
                            if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                                _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";")
                            }
                            if (_u != "" && _u.length > 0) {
                                var obj = Gee.$(_u[0]);
                                this.setLine(obj, e)
                            } else {
                                this.setLine(this._W, e)
                            }
                        } else {
                            if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                                _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";")
                            }
                            if (_u != "" && _u.length > 0) {
                                var obj = Gee.$(_u[0]);
                                this.setLine(obj, e)
                            } else {
                                alert("请输入股票代码！");
                                //alert("您输入的股票代码不存在！");
                                isErr = true
                            }
                        } if (this.callback != null && !isErr) {
                            this.callback({
                                code: this.input.value,
                                type: this.Market,
                                mt: this.mType
                            });
                        }
                    } else {
                        alert("请输入股票代码！");
                    }
                    this.hiddenResults();
                });
                Gee.$aE(document.getElementById(id), "click", submit1);
            }
            else {
                alert("请输入股票代码！");
            }
        },
        dispose: function () {
            this._Y = {};
            this.input = typeof (this.input) == "string" ? Gee.$(this.input) : this.input;
            if (this.input) {
                if (this._oForm != null) {
                    this._F.action = this._oForm.action;
                    this._F.target = this._oForm.target;
                    this._F.method = this._oForm.method;
                    this._F.onsubmit = this._oForm.onsubmit
                }
                Gee.$dE(this.input, "focus", this._iF);
                Gee.$dE(this.input, "blur", this._iF);
                Gee.$dE(this.input, "keyup", this._iN);
                if (this.autoSubmit) Gee.$dE(this.input, "keydown", this._iC);
                Gee.$dE(this.input, "mouseup", this._iN)
            }
        },
        GetShowType: function () {
            if (this.type == "") return "";
            else return this.ShowType[this.type]
        },
        inputFocus: function (e) {
            var _t = e.type;
            if (this.input.value == this.text && _t.indexOf("focus") >= 0) {
                this.input.value = "";
                this._U = "";
                this.Suggest()
            } else if (this.input.value == "" && _t.indexOf("blur") >= 0) {
                this.input.value = this.text;
                this._U = "";
                this.hiddenResults()
            } else if (_t.indexOf("blur") >= 0) {
                this.hiddenResults()
            }
        },
        nGourl: false,
        Navigate: function (e) {
            var _K = this.header == null ? 0 : 1;
            switch (e.keyCode) {
                case 38:
                    this.nGourl = false;
                    if (this.results != null && this.results.innerHTML != "") {
                        this.setLine(this.results.firstChild.rows[(!this._W || this._W.rowIndex == _K) ? this.results.firstChild.rows.length - 2 : this._W.rowIndex - 1])
                    }
                    break;
                case 40:
                    this.nGourl = false;
                    if (this.results != null && this.results.innerHTML != "") {
                        this.setLine(this.results.firstChild.rows[(!this._W || this._W.rowIndex == this.results.firstChild.rows.length - 2) ? _K : this._W.rowIndex + 1])
                    }
                    break;
                case 13:
                    if (!this.autoSubmit) {
                        this.nGourl = true;
                        if (this.results != null && this.results.innerHTML != "") {
                            var _s = this.input.value;
                            var _u = "";
                            if (this._W != null) {
                                if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                                    _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";")
                                }
                                if (_u != "" && _u.length > 0) {
                                    var obj = Gee.$(_u[0]);
                                    if (typeof obj != "undefined") this.setLine(obj, e)
                                } else {
                                    this.setLine(this._W, e)
                                }
                            } else {
                                var err = false;
                                if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                                    _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";")
                                }
                                if (_u != "" && _u.length > 0) {
                                    var obj = Gee.$(_u[0]);
                                    if (typeof obj != "undefined") this.setLine(obj, e)
                                } else {
                                    alert("您输入的股票代码不存在！");
                                    err = true
                                }
                            } if (this.callback != null && !err) {
                                this.callback({
                                    code: this.input.value,
                                    type: this.Market,
                                    mt: this.mType,
                                    cnName: this.SName
                                })
                            }
                        }
                        this.hiddenResults()
                    } else {
                        this.Submit(this.input, false)
                    }
                    break;
                default:
                    this.Suggest();
                    break
            }
        },
        Confirm: function (e) {
            if (e.keyCode == 13) {
                this.nGourl = true;
                if (this.results != null && this.results.innerHTML != "") {
                    var isErr = false;
                    var _s = this.input.value;
                    var _u = "";
                    if (this._W != null) {
                        if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                            _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";")
                        }
                        if (_u != "" && _u.length > 0) {
                            var obj = Gee.$(_u[0]);
                            this.setLine(obj, e)
                        } else {
                            this.setLine(this._W, e)
                        }
                    } else {
                        if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                            _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";")
                        }
                        if (_u != "" && _u.length > 0) {
                            var obj = Gee.$(_u[0]);
                            this.setLine(obj, e)
                        } else {
                            alert("您输入的股票代码不存在！");
                            isErr = true
                        }
                    } if (this.callback != null && !isErr) {
                        this.callback({
                            code: this.input.value,
                            type: this.Market,
                            mt: this.mType
                        });
                    }
                } else {
                    alert("请输入股票代码！")
                }
                this.hiddenResults()
            } else {
                this.Suggest()
            }
        },
        _bd: function (_b, _c) {
            var _d = this;
            return function () {
                var _e = null;
                if (typeof _c != "undefined") {
                    for (var i = 0; i < arguments.length; i++) {
                        _c.push(arguments[i])
                    }
                    _e = _c
                } else {
                    _e = arguments
                }
                return _b.apply(_d, _e)
            }
        },
        _gt: function () {
            return (new Date()).getTime()
        },
        Suggest: function () {
            var _s = this.input.value;
            if (this._U != _s) {
                this._U = _s;
                if (_s != "") {
                    if (("key_" + _s) in this._Y) {
                        this.Tip()
                    } else {
                        this._io(_s, this._bd(this.Tip), this._bd(this.hiddenResults))
                    }
                } else {
                    if (this.results != null && this.results.innerHTML != "") {
                        this._W = null;
                    }
                    this.hiddenResults()
                }
            } else {
                this.setResults()
            }
        },
        setResults: function () {
            if (this.results != null) this.results.style.display = ""
        },
        hiddenResults: function () {
            if (this._hidden == false) {
                if (this.results != null) this.results.style.display = "none"
            }
        },
        _io: function (s, _E, _F) {
            if (this._R == null) {
                this._R = Gee.$C("div");
                this._R.style.display = "none";
                document.body.insertBefore(this._R, document.body.lastChild)
            }
            var dataObjName = "sData_" + this._gt();
            var _H = Gee.$C("script");
            _H.type = "text/javascript";
            _H.charset = "gb2312";
            _H.src = this.dataurl.replace("{#NAME}", dataObjName).replace("{#KEY}", escape(s)).replace("{#TYPE}", this.GetShowType());
            _H._0j = this;
            if (_E) {
                _H._0k = _E
            }
            if (_F) {
                _H._0l = _F
            }
            _H._0m = s;
            _H._0n = dataObjName;
            _H[document.all ? "onreadystatechange" : "onload"] = function () {
                if (document.all && this.readyState != "loaded" && this.readyState != "complete") {
                    return
                }
                var _I = window[this._0n];
                if (typeof _I != "undefined") {
                    this._0j._Y["key_" + this._0m] = _I;
                    this._0k(_I);
                    window[this._0n] = null
                }
                this._0j = null;
                this._0m = null;
                this._0n = null;
                this[document.all ? "onreadystatechange" : "onload"] = null;
                this.parentNode.removeChild(this)
            };
            this._R.appendChild(_H)
        },
        Submit: function (e, isOut) {
            if (typeof isOut == "undefined") isOut = true;
            if (isOut) this._D = null;
            var _u = "";
            if (this._D == null) {
                var _s = this.input.value;
                if (!!this._Y["key_" + _s]) {
                    var _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";");
                    if (_u != "" && _u.length > 0) {
                        var _tD = _u[0].split(",");
                        this._D = _tD
                    }
                } else {
                    var _u = "http://quote.eastmoney.com/"
                }
            }
            if (this._D != null && this._D != "") {
                switch (this._D[2]) {
                    case "1":
                    case "2":
                    case "3":
                    case "5":
                    case "10":
                    case "41":
                        var __mI = "sh";
                        if (this._D[5] == "2") __mI = "sz";
                        _u = "http://quote.eastmoney.com/" + __mI + this._D[1] + ".html";
                        break;
                    case "4":
                        _u = "http://quote.eastmoney.com/qihuo/" + this._D[1] + ".html";
                        break;
                    case "40":
                        _u = "http://quote.eastmoney.com/zs" + this._D[1] + ".html";
                        break;
                    case "11":
                    case "12":
                    case "13":
                    case "14":
                    case "15":
                    case "16":
                        _u = "http://fund.eastmoney.com/" + this._D[1] + ".html";
                        break;
                    case "21":
                    case "22":
                        _u = "http://quote.eastmoney.com/hk/" + this._D[1] + ".html";
                        break;
                    case "31":
                    case "32":
                        _u = "http://quote.eastmoney.com/us/" + this._D[1] + ".html";
                        break;
                    case "50":
                        _u = "http://quote.eastmoney.com/gzqh/" + this._D[1] + ".html";
                        break;
                    default:
                        _u = "http://quote.eastmoney.com/3ban/sz" + this._D[1] + ".html";
                        break
                }
                if (_u != "") {
                    var isEnter = false;
                    var tmpInput = this.input.name;
                    var tempVal = this.input.value;
                    if (typeof e != "undefined") {
                        this.input.name = "";
                        this.input.value = "";
                        if (e.keyCode == 13) isEnter = true
                    } else {
                        var isSixNum = !isNaN(this.input.value) && this.input.value.length == 6;
                        if (!!isOut && !isSixNum) {
                            this.input.name = "stockcode";
                            _u = "http://quote.eastmoney.com/search.html";
                            if (this.text.indexOf(this.input.value) >= 0 || this.input.value == "") {
                                this.input.name = "";
                                this.input.value = "";
                                _u = "http://quote.eastmoney.com/"
                            }
                        }
                    }
                    this.goUrl(_u, "_blank", isEnter);
                    this.input.name = tmpInput;
                    this.input.value = tempVal;
                }
            } else {
                var tmpInput = this.input.name;
                var tempVal = this.input.value;
                this.input.name = "stockcode";
                var urlStr = "http://quote.eastmoney.com/search.html";
                if (this.text.indexOf(this.input.value) >= 0 || this.input.value == "") {
                    this.input.name = "";
                    this.input.value = "";
                    urlStr = "http://quote.eastmoney.com/"
                }
                this.goUrl(urlStr, "_blank", isEnter);
                this.input.name = tmpInput;
                this.input.value = tempVal;
            }
        },
        goUrl: function (url, target, iE) {
            if (this._F != null) {
                this._F.action = url;
                this._F.target = target;
                this._F.method = "get";
                this._F.onsubmit = function () {
                    return true
                };
                if (!iE) this._F.submit();
            } else {
                alert("Error")
            }
            this.hiddenResults()
        },
        setColor: function (o) {
            var _B = "";
            if (o._0f && o._0g) {
                _B = "#F8FBDF"
            } else if (o._0f) {
                _B = "#F1F5FC"
            } else if (o._0g) {
                _B = "#FCFEDF"
            }
            if (o.style.backgroundColor != _B) {
                o.style.backgroundColor = _B
            }
        },
        setLine: function (o, e) {
            var _C = o.id.split(",");
            this._D = _C;
            var _D = _C[1];
            this._U = _D;
            this.Market = _C[2];
            this.mType = _C[5];
            this.SName = _C[4];
            this.input.value = _D;
            if (this._W != null) {
                this._W._0f = false;
                this.setColor(this._W)
            }
            o._0f = true;
            this.setColor(o);
            this._W = o;
            if (this.autoSubmit && this.nGourl) this.Submit(e, false)
        },
        mouseoverLine: function (o) {
            o._0g = true;
            this.setColor(o)
        },
        mouseoutLine: function (o) {
            o._0g = false;
            this.setColor(o)
        },
        setLineMouse: function (o) {
            this.nGourl = true;
            this.setLine(o);
            if (this.callback != null) {
                this.callback({
                    code: this.input.value,
                    type: this.Market,
                    mt: this.mType,
                    cnName: this.SName
                })
            }
        },
        hidepause: function () {
            this._hidden = true
        },
        hideresume: function () {
            this._hidden = false;
            this.hiddenResults()
        },
        setTip: function () {
            var _j = 0;
            var _k = 0;
            var _f = this.input;
            do {
                _j += _f.offsetTop || 0;
                _k += _f.offsetLeft || 0;
                _f = _f.offsetParent
            } while (_f);
            var _l = [this.input.parentNode.style.borderTopWidth.replace("px", "") * 1, this.input.parentNode.style.borderLeftWidth.replace("px", "") * 1];
            var _o = [1, 1];
            if (this.results.style.top != _j + "px") {
                this.results.style.top = _j - _l[0] + _o[0] + "px"
            }
            if (this.results.style.left != _k + "px") {
                this.results.style.left = _k - _l[1] + _o[1] + "px"
            }
            var _p = this.input.style.borderTopWidth;
            var _q = this.input.style.borderBottomWidth;
            var _r = this.input.clientHeight;
            _r += _p != "" ? _p.replace("px", "") * 1 : 2;
            _r += _q != "" ? _q.replace("px", "") * 1 : 2;
            if (this.results.style.marginTop != _r + "px") {
                this.results.style.marginTop = _r + "px"
            }
        },
        Tip: function () {
            var _s = this.input.value;
            if (("key_" + _s) in this._Y && this._Y["key_" + _s] != "") {
                if (this.results == null) {
                    this.results = Gee.$C("div");
                    this.results.id = "jj_suggest_result";
                    this.results.style.cssText = "z-index:9999;width:" + this.width + "px;opacity:" + this.opacity + ";filter:alpha(opacity:" + (this.opacity * 100) + ");position:absolute;display:none;";
                    if (this.className == "") this.results.style.border = "1px solid #ccc";
                    else this.results.className = this.className;
                    this.input.parentNode.insertBefore(this.results, this.input);
                    this.results["suggest"] = this
                }
                this.setTip();
                this.results.innerHTML = "";
                var t = Gee.$C("table");
                t.border = "0";
                t.cellPadding = "0";
                t.cellSpacing = "0";
                t.style.cssText = "line-height:18px;border:1px solid #FFF;background:#FFF;font-size:12px;text-align:center;color:#666;width:100%;";
                var tB = Gee.$C("tbody");
                var _t_h_tr = Gee.$C("tr");
                _t_h_tr.style.cssText = "background:#E6F4F5;height:22px;line-height:22px;overflow:hidden;";
                if (this.header != null) {
                    for (var i = 0; i < this.header.length; i++) {
                        var _t_th = Gee.$C("th");
                        if (this.header[i] == "代码") _t_th.width = 52;
                        if (this.header[i] == "类型") _t_th.width = 40;
                        _t_th.innerHTML = this.header[i];
                        _t_h_tr.appendChild(_t_th)
                    }
                }
                tB.appendChild(_t_h_tr);
                var _u = this._Y["key_" + _s].replace(/&amp;/g, "&").replace(/;$/, "").split(";");
                var _v = _u.length > this.max ? this.max : _u.length;
                for (var i = 0; i < _v; i++) {
                    var _x = _u[i].split(",");
                    _x[-1] = _x[0].replace(_s.toUpperCase(), '<span style="color:#F00;">' + _s.toUpperCase() + '</span>');
                    _x[-2] = _x[2] in this.StockType ? this.StockType[_x[2]] : "--";
                    var _t_tr = Gee.$C("tr");
                    _t_tr.id = _u[i];
                    _t_tr.style.cursor = "pointer";
                    _t_tr._oj = this;
                    _t_tr.onmouseover = function () {
                        this._oj.mouseoverLine(this)
                    };
                    _t_tr.onmouseout = function () {
                        this._oj.mouseoutLine(this)
                    };
                    _t_tr.onmousedown = function () {
                        return this._oj.hidepause(this)
                    };
                    _t_tr.onclick = function () {
                        this._oj.setLineMouse(this);
                        this._oj.hideresume(this)
                    };
                    var _t_td;
                    for (var j = 0; j < this.body.length; j++) {
                        _t_td = Gee.$C("td");
                        _t_td.style.wordBreak = 'break-all';
                        _t_td.hidefocus = "true";
                        _t_td.style.padding = "1px";
                        _t_td.innerHTML = _x[this.body[j]];
                        _t_tr.appendChild(_t_td)
                    }
                    _t_td = null;
                    tB.appendChild(_t_tr)
                }
                var _more_t_tr = Gee.$C("tr");
                _more_t_tr.id = "_AutoSuggest_tip_More_";
                var _more_t_td = Gee.$C("td");
                _more_t_td.colSpan = this.header.length;
                _more_t_td.align = "right";
                _more_t_td.hidefocus = "true";
                if (this.moreLink) {
                    _more_link = Gee.$C("a");
                    _more_link.style.cssText = "color:#C00;float:none;clear:both;background:none;border:0;";
                    _more_link.href = "http://quote.eastmoney.com/search.html?stockcode=" + escape(_s);
                    _more_link.target = "_blank";
                    _more_link.innerHTML = "更多查询结果&gt;&gt;";
                    _more_link._oj = this;
                    _more_link.onmousedown = function () {
                        return this._oj.hidepause(this)
                    };
                    _more_link.onclick = function () {
                        this._oj.hideresume(this)
                    };
                    _more_t_td.appendChild(_more_link);
                }
                _more_t_tr.appendChild(_more_t_td);
                tB.appendChild(_more_t_tr);
                t.appendChild(tB);
                this.results.appendChild(t);
                this.setResults()
            } else {
                this.hiddenResults()
            }
        }
    });
    window.StockSuggest = StockSuggest
})();
/* ScrollPic */
eval(function (p, a, c, k, e, d) {
    e = function (c) {
        return (c < a ? "" : e(parseInt(c / a))) + ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36))
    };
    if (!''.replace(/^/, String)) {
        while (c--) d[e(c)] = k[c] || e(c);
        k = [
            function (e) {
                return d[e]
            }
        ];
        e = function () {
            return '\\w+'
        };
        c = 1;
    };
    while (c--)
        if (k[c]) p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c]);
    return p;
}('(h(){t r={$:h(1D){3(w.1R){k 1F(\'w.1R("\'+1D+\'")\')}7{k 1F(\'w.2p.\'+1D)}},2q:2r.2m.1s("2n")!=-1?J:1t,v:h(l,i,I){3(l.1I){l.1I("1K"+i,I)}7{l.2o(i,I,1t)}},2s:h(l,i,I){3(l.1J){l.1J("1K"+i,I)}7{l.2x(i,I,1t)}},2t:h(O){t o="",l=O+"=";3(w.11.Z>0){t i=w.11.1s(l);3(i!=-1){i+=l.Z;t I=w.11.1s(";",i);3(I==-1)I=w.11.Z;o=2u(w.11.24(i,I))}};k o},2v:h(i,l,o,c){t O="",I="";3(o!=1i){O=1p 1L((1p 1L).2e()+o*2d);O="; 2i="+O.2h()};3(c!=1i){I=";2g="+c};w.11=i+"="+2f(l)+O+I},2k:h(I,l){3(I.u[l]){k I.u[l]}7 3(I.1G){k I.1G[l]}7 3(w.1B&&w.1B.1H){t i=w.1B.1H(I,1i);k i.2M(l)}7{k 1i}}};h j(S,14,15,1y,A,1k){2.S=S;2.14=14;2.15=15;2.A=A;2.1k=1k;2.1v="2N";2.1w="2L";2.13=[];2.B=J;2.F=0;2.q=0;2.Y=10;2.m=10;2.L=0;2.1r=J;2.23=5;t 1g,K,f="y";2.R=w.1n("1z");2.9=w.1n("1z");2.V=w.1n("1z");3(!j.n){j.n=[]};2.p=j.n.Z;j.n.25(2);2.2Q=h(){3(!2.S){1Q 1p 1S("2P.");k};2.6=r.$(2.S);3(!2.6){1Q 1p 1S("2D.(S = \\""+2.S+"\\")");k};3(2.A)2.6.u.1M=2.q+"1P";7 2.6.u.2z=2.q+"1P";2.6.u.1c="1d";2.9.T=2.6.T;2.6.T="";2.6.1o(2.R);2.R.1o(2.9);3(2.B){2.R.1o(2.V);2.V.T=2.9.T};2.R.u.1c="1d";2.R.u.1x="1";3(2.A){2.R.u.1M="2A";2.9.u.1N="1e";2.9.u.1O="1e";2.9.u.1c="1d";2.9.u.1x="1";3(2.B){2.V.u.1N="1e";2.V.u.1O="1e";2.V.u.1c="1d";2.V.u.1x="1"}}7{}r.v(2.6,"2B",x("j.n["+2.p+"].2b()"));r.v(2.6,"16",x("j.n["+2.p+"].1q()"));3(2.14){2.P=r.$(2.14);3(2.P){3(2.A){r.v(2.P,"1f",x("j.n["+2.p+"].2a()"));r.v(2.P,"17",x("j.n["+2.p+"].1b()"));r.v(2.P,"16",x("j.n["+2.p+"].1b()"))}7{r.v(2.P,"1f",x("j.n["+2.p+"].1Y()"));r.v(2.P,"17",x("j.n["+2.p+"].19()"));r.v(2.P,"16",x("j.n["+2.p+"].19()"))}}};3(2.15){2.Q=r.$(2.15);3(2.Q){3(2.A){r.v(2.Q,"1f",x("j.n["+2.p+"].29()"));r.v(2.Q,"17",x("j.n["+2.p+"].18()"));r.v(2.Q,"16",x("j.n["+2.p+"].18()"))}7{r.v(2.Q,"1f",x("j.n["+2.p+"].1X()"));r.v(2.Q,"17",x("j.n["+2.p+"].1a()"));r.v(2.Q,"16",x("j.n["+2.p+"].1a()"))}}};3(2.1y){2.1l=r.$(2.1y);2.1l.T="";3(2.1l){t 1j=0;3(2.A)1j=H.W(2.9.20/2.q+0.4);7 1j=H.W(2.9.1W/2.q+0.4);t i,G;27(i=0;i<1j;i++){G=w.1n("2H");2.1l.1o(G);2.13.25(G);3(i==2.L){G.1m=2.1v}7{G.1m=2.1w};3(2.1k==\'2G\'){G.T=i+1};G.2F="2K"+(i+1)+"2J";r.v(G,"2I",x("j.n["+2.p+"].1h("+i+")"))}}};3(2.1r){2.1q()}};2.29=h(){3(f!="y"){k};f="M";K=12("j.n["+2.p+"].1T()",2.Y)};2.2a=h(){3(f!="y"){k};f="M";K=12("j.n["+2.p+"].1U()",2.Y)};2.1X=h(){3(f!="y"){k};f="M";K=12("j.n["+2.p+"].1V()",2.Y)};2.1Y=h(){3(f!="y"){k};f="M";K=12("j.n["+2.p+"].22()",2.Y)};2.1T=h(){3(2.B){3(2.6.d+2.m>=2.9.D){2.6.d=2.6.d+2.m-2.9.D}7{2.6.d+=2.m}}7{3(2.6.d+2.m>=2.9.D-2.q){2.6.d=2.9.D-2.q;2.18()}7{2.6.d+=2.m}}};2.1U=h(){3(2.B){3(2.6.d-2.m<=0){2.6.d=2.9.D+2.6.d-2.m}7{2.6.d-=2.m}}7{3(2.6.d-2.m<=0){2.6.d=0;2.1b()}7{2.6.d-=2.m}}};2.1V=h(){3(2.B){3(2.6.e+2.m>=2.9.E){2.6.e=2.6.e+2.m-2.9.E}7{2.6.e+=2.m}}7{3(2.6.e+2.m>=2.9.E-2.q){2.6.e=2.9.E-2.q;2.1a()}7{2.6.e+=2.m}}};2.22=h(){3(2.B){3(2.6.e-2.m<=0){2.6.e=2.9.E+2.6.e-2.m}7{2.6.e-=2.m}}7{3(2.6.e-2.m<=0){2.6.e=0;2.19()}7{2.6.e-=2.m}}};2.18=h(){3(f!="M"){k};f="U";X(K);t z=2.F-2.6.d%2.F;2.C(z)};2.1b=h(){3(f!="M"){k};f="U";X(K);t z=-2.6.d%2.F;2.C(z)};2.1a=h(){3(f!="M"){k};f="U";X(K);t z=2.F-2.6.e%2.F;2.C(z)};2.19=h(){3(f!="M"){k};f="U";X(K);t z=-2.6.e%2.F;2.C(z)};2.C=h(N,1C){t 8=N/5;3(!1C){3(8>2.m){8=2.m};3(8<-2.m){8=-2.m}};3(H.1Z(8)<1&&8!=0){8=8>=0?1:-1}7{8=H.W(8)};t 1A=0;3(2.A){1A=2.6.d+8;3(8>0){3(2.B){3(2.6.d+8>=2.9.D){2.6.d=2.6.d+8-2.9.D}7{2.6.d+=8}}7{3(2.6.d+8>=2.9.D-2.q){2.6.d=2.9.D-2.q;f="y";k}7{2.6.d+=8}}}7{3(2.B){3(2.6.d+8<=0){2.6.d=2.9.D+2.6.d+8}7{2.6.d+=8}}7{3(2.6.d-8<=0){2.6.d=0;f="y";k}7{2.6.d+=8}}}}7{1A=2.6.e+8;3(8>0){3(2.B){3(2.6.e+8>=2.9.E){2.6.e=2.6.e+8-2.9.E}7{2.6.e+=8}}7{3(2.6.e+8>=2.9.E-2.q){2.6.e=2.9.E-2.q;f="y";k}7{2.6.e+=8}}}7{3(2.B){3(2.6.e+8<=0){2.6.e=2.9.E+2.6.e+8}7{2.6.e+=8}}7{3(2.6.e-8<=0){2.6.e=0;f="y";k}7{2.6.e+=8}}}}N-=8;3(H.1Z(N)==0){f="y";3(2.1r){2.1q()};2.26();k}7{2E("j.n["+2.p+"].C("+N+","+1C+")",2.Y)}};2.2C=h(){3(f!="y"){k};f="U";2.C(-2.F,J)};2.21=h(1u){3(f!="y"){k};f="U";3(2.B){2.C(2.F,J)}7{3(2.A){3(2.6.d>=2.9.D-2.q){f="y";3(1u){2.1h(0)}}7{2.C(2.F,J)}}7{3(2.6.e>=2.9.E-2.q){f="y";3(1u){2.1h(0)}}7{2.C(2.F,J)}}}};2.1q=h(){3(!2.1r){k};X(1g);1g=12("j.n["+2.p+"].21(J)",2.23*2O)};2.2b=h(){X(1g)};2.1h=h(N){3(f!="y"){k};f="U";t z=0;3(2.A)z=N*2.q-2.6.d;7 z=N*2.q-2.6.e;2.C(z,J)};2.26=h(){3(2.A){2.L=H.W(2.6.d/2.q);3(2.L>H.W(2.9.20/2.q+0.4)-1){2.L=0}}7{2.L=H.W(2.6.e/2.q);3(2.L>H.W(2.9.1W/2.q+0.4)-1){2.L=0}}t i;27(i=0;i<2.13.Z;i++){3(i==2.L){2.13[i].1m=2.1v}7{2.13[i].1m=2.1w}}}};2w.j=j;2y.2l.2j=h(a,b){t s=2.28(/([^\\1E-\\2c])/g,"\\1E$1");k(s.Z<b)?2:s.24(a,b).28(/\\1E/g,\'\')}})();', 62, 177, '||this|if|||scrollContDiv|else|thisMove|listDiv01||||scrollLeft|scrollTop|_state||function||ScrollPic|return||space|childs||ID|frameSet|jEE||var|style|addEvent|document|Function|ready|fill|arrVertical|circularly|move|scrollWidth|scrollHeight|pageSet|tempObj|Math||true|_scrollTimeObj|pageIndex|floating|num||arrLeftUpObj|arrRightDownObj|stripDiv|scrollContId|innerHTML|stoping|listDiv02|round|clearInterval|speed|length||cookie|setInterval|dotObjArr|arrLeftUpId|arrRightDownId|mouseout|mouseup|leftEnd|downEnd|upEnd|rightEnd|overflow|hidden|left|mousedown|_autoTimeObj|pageTo|null|pages|listType|dotListObj|className|createElement|appendChild|new|play|autoPlay|indexOf|false|reStar|dotClassName|dotOnClassName|zoom|dotListId|DIV|temp|defaultView|quick|objName|x00|eval|currentStyle|getComputedStyle|attachEvent|detachEvent|on|Date|width|cssFloat|styleFloat|px|throw|getElementById|Error|moveLeft|moveRight|moveUp|offsetHeight|upMouseDown|downMouseDown|abs|offsetWidth|next|moveDown|autoPlayTime|substring|push|accountPageIndex|for|replace|leftMouseDown|rightMouseDown|stop|xff|3600000|getTime|escape|domain|toGMTString|expires|substr2|readStyle|prototype|appVersion|MSIE|addEventListener|all|isIE|navigator|delEvent|readCookie|unescape|writeCookie|window|removeEventListener|String|height|32766px|mouseover|pre|scrollContId不是正确的对象|setTimeout|title|number|span|click|页|第|dotItemOn|getPropertyValue|dotItem|1000|必须指定scrollContId|initialize'.split('|'), 0, {}))
/* SWFObject.js */
if (typeof deconcept == "undefined") {
    var deconcept = new Object();
}
if (typeof deconcept.util == "undefined") {
    deconcept.util = new Object();
}
if (typeof deconcept.SWFObjectUtil == "undefined") {
    deconcept.SWFObjectUtil = new Object();
}
deconcept.SWFObject = function (_1, id, w, h, _5, c, _7, _8, _9, _a) {
    if (!document.getElementById) {
        return;
    }
    this.DETECT_KEY = _a ? _a : "detectflash";
    this.skipDetect = deconcept.util.getRequestParameter(this.DETECT_KEY);
    this.params = new Object();
    this.variables = new Object();
    this.attributes = new Array();
    if (_1) {
        this.setAttribute("swf", _1);
    }
    if (id) {
        this.setAttribute("id", id);
    }
    if (w) {
        this.setAttribute("width", w);
    }
    if (h) {
        this.setAttribute("height", h);
    }
    if (_5) {
        this.setAttribute("version", new deconcept.PlayerVersion(_5.toString().split(".")));
    }
    this.installedVer = deconcept.SWFObjectUtil.getPlayerVersion();
    if (!window.opera && document.all && this.installedVer.major > 7) {
        deconcept.SWFObject.doPrepUnload = true;
    }
    if (c) {
        this.addParam("bgcolor", c);
    } else {
        this.addParam("wmode", "transparent");
    }
    var q = _7 ? _7 : "high";
    this.addParam("quality", q);
    this.setAttribute("useExpressInstall", false);
    this.setAttribute("doExpressInstall", false);
    var _c = (_8) ? _8 : window.location;
    this.setAttribute("xiRedirectUrl", _c);
    this.setAttribute("redirectUrl", "");
    if (_9) {
        this.setAttribute("redirectUrl", _9);
    }
};
deconcept.SWFObject.prototype = {
    useExpressInstall: function (_d) {
        this.xiSWFPath = !_d ? "expressinstall.swf" : _d;
        this.setAttribute("useExpressInstall", true);
    },
    setAttribute: function (_e, _f) {
        this.attributes[_e] = _f;
    },
    getAttribute: function (_10) {
        return this.attributes[_10];
    },
    addParam: function (_11, _12) {
        this.params[_11] = _12;
    },
    getParams: function () {
        return this.params;
    },
    addVariable: function (_13, _14) {
        this.variables[_13] = _14;
    },
    getVariable: function (_15) {
        return this.variables[_15];
    },
    getVariables: function () {
        return this.variables;
    },
    getVariablePairs: function () {
        var _16 = new Array();
        var key;
        var _18 = this.getVariables();
        for (key in _18) {
            _16[_16.length] = key + "=" + _18[key];
        }
        return _16;
    },
    getSWFHTML: function () {
        var _19 = "";
        if (navigator.plugins && navigator.mimeTypes && navigator.mimeTypes.length) {
            if (this.getAttribute("doExpressInstall")) {
                this.addVariable("MMplayerType", "PlugIn");
                this.setAttribute("swf", this.xiSWFPath);
            }
            _19 = "<embed type=\"application/x-shockwave-flash\" src=\"" + this.getAttribute("swf") + "\" width=\"" + this.getAttribute("width") + "\" height=\"" + this.getAttribute("height") + "\" style=\"" + this.getAttribute("style") + "\"";
            _19 += " id=\"" + this.getAttribute("id") + "\" name=\"" + this.getAttribute("id") + "\" ";
            var _1a = this.getParams();
            for (var key in _1a) {
                _19 += [key] + "=\"" + _1a[key] + "\" ";
            }
            var _1c = this.getVariablePairs().join("&");
            if (_1c.length > 0) {
                _19 += "flashvars=\"" + _1c + "\"";
            }
            _19 += "/>";
        } else {
            if (this.getAttribute("doExpressInstall")) {
                this.addVariable("MMplayerType", "ActiveX");
                this.setAttribute("swf", this.xiSWFPath);
            }
            _19 = "<object id=\"" + this.getAttribute("id") + "\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"" + this.getAttribute("width") + "\" height=\"" + this.getAttribute("height") + "\" style=\"" + this.getAttribute("style") + "\">";
            _19 += "<param name=\"movie\" value=\"" + this.getAttribute("swf") + "\" />";
            var _1d = this.getParams();
            for (var key in _1d) {
                _19 += "<param name=\"" + key + "\" value=\"" + _1d[key] + "\" />";
            }
            var _1f = this.getVariablePairs().join("&");
            if (_1f.length > 0) {
                _19 += "<param name=\"flashvars\" value=\"" + _1f + "\" />";
            }
            _19 += "</object>";
        }
        return _19;
    },
    write: function (_20) {
        if (this.getAttribute("useExpressInstall")) {
            var _21 = new deconcept.PlayerVersion([6, 0, 65]);
            if (this.installedVer.versionIsValid(_21) && !this.installedVer.versionIsValid(this.getAttribute("version"))) {
                this.setAttribute("doExpressInstall", true);
                this.addVariable("MMredirectURL", encodeURIComponent(this.getAttribute("xiRedirectUrl")));
                document.title = document.title.slice(0, 47) + " - Flash Player Installation";
                this.addVariable("MMdoctitle", document.title);
            }
        }
        if (this.skipDetect || this.getAttribute("doExpressInstall") || this.installedVer.versionIsValid(this.getAttribute("version"))) {
            var n = (typeof _20 == "string") ? document.getElementById(_20) : _20;
            n.innerHTML = this.getSWFHTML();
            if (!(navigator.plugins && navigator.mimeTypes.length)) window[this.getAttribute('id')] = document.getElementById(this.getAttribute('id'));
            return true;
        } else {
            if (this.getAttribute("redirectUrl") != "") {
                document.location.replace(this.getAttribute("redirectUrl"));
            }
        }
        return false;
    }
};
deconcept.SWFObjectUtil.getPlayerVersion = function () {
    var _23 = new deconcept.PlayerVersion([0, 0, 0]);
    if (navigator.plugins && navigator.mimeTypes.length) {
        var x = navigator.plugins["Shockwave Flash"];
        if (x && x.description) {
            _23 = new deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/, "").replace(/(\s+r|\s+b[0-9]+)/, ".").split("."));
        }
    } else {
        if (navigator.userAgent && navigator.userAgent.indexOf("Windows CE") >= 0) {
            var axo = 1;
            var _26 = 3;
            while (axo) {
                try {
                    _26++;
                    axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash." + _26);
                    _23 = new deconcept.PlayerVersion([_26, 0, 0]);
                } catch (e) {
                    axo = null;
                }
            }
        } else {
            try {
                var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
            } catch (e) {
                try {
                    var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
                    _23 = new deconcept.PlayerVersion([6, 0, 21]);
                    axo.AllowScriptAccess = "always";
                } catch (e) {
                    if (_23.major == 6) {
                        return _23;
                    }
                }
                try {
                    axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
                } catch (e) { }
            }
            if (axo != null) {
                _23 = new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));
            }
        }
    }
    return _23;
};
deconcept.PlayerVersion = function (_29) {
    this.major = _29[0] != null ? parseInt(_29[0]) : 0;
    this.minor = _29[1] != null ? parseInt(_29[1]) : 0;
    this.rev = _29[2] != null ? parseInt(_29[2]) : 0;
};
deconcept.PlayerVersion.prototype.versionIsValid = function (fv) {
    if (this.major < fv.major) {
        return false;
    }
    if (this.major > fv.major) {
        return true;
    }
    if (this.minor < fv.minor) {
        return false;
    }
    if (this.minor > fv.minor) {
        return true;
    }
    if (this.rev < fv.rev) {
        return false;
    }
    return true;
};
deconcept.util = {
    getRequestParameter: function (_2b) {
        var q = document.location.search || document.location.hash;
        if (_2b == null) {
            return q;
        }
        if (q) {
            var _2d = q.substring(1).split("&");
            for (var i = 0; i < _2d.length; i++) {
                if (_2d[i].substring(0, _2d[i].indexOf("=")) == _2b) {
                    return _2d[i].substring((_2d[i].indexOf("=") + 1));
                }
            }
        }
        return "";
    }
};
deconcept.SWFObjectUtil.cleanupSWFs = function () {
    var _2f = document.getElementsByTagName("OBJECT");
    for (var i = _2f.length - 1; i >= 0; i--) {
        _2f[i].style.display = "none";
        for (var x in _2f[i]) {
            if (typeof _2f[i][x] == "function") {
                _2f[i][x] = function () { };
            }
        }
    }
};
if (deconcept.SWFObject.doPrepUnload) {
    if (!deconcept.unloadSet) {
        deconcept.SWFObjectUtil.prepUnload = function () {
            __flash_unloadHandler = function () { };
            __flash_savedUnloadHandler = function () { };
            window.attachEvent("onunload", deconcept.SWFObjectUtil.cleanupSWFs);
        };
        window.attachEvent("onbeforeunload", deconcept.SWFObjectUtil.prepUnload);
        deconcept.unloadSet = true;
    }
}
if (!document.getElementById && document.all) {
    document.getElementById = function (id) {
        return document.all[id];
    };
}
var getQueryParamValue = deconcept.util.getRequestParameter;
var FlashObject = deconcept.SWFObject;
var SWFObject = deconcept.SWFObject;
/*
	Update:2012.02.09
*/