/*
 * Created by zbc on 14-5-6
 */

(function () {
    var Browser = window.Browser = { ie: /msie/.test(window.navigator.userAgent.toLowerCase()), moz: /gecko/.test(window.navigator.userAgent.toLowerCase()), opera: /opera/.test(window.navigator.userAgent.toLowerCase()), safari: /safari/.test(window.navigator.userAgent.toLowerCase()) };

    var FUC = window.FUC = {
        add: function (a, fuc) {
            FUC[a] = fuc;
        },
        $: function (a) {
            return document.getElementById(a);
        },
        xcopy: function (a, b) {
            for (var i in a) {
                b[i] !== undefined && (b[i] = a[i])
            }
            return b;
        },
        addClass: function (a, t) {
            if (t.className.indexOf(a) < 0) {
                t.className = t.className + " " + a;
            }
        },
        removeClass: function (a, t) {
            if (t.className.indexOf(a) > -1) {
                a = new RegExp("\s{0,}" + a);
                t.className = t.className.replace(a, "");
            }
        },
        botherAddClass: function (a, t) {

        },
        ajax: function (opt) {
            var opts = {
                url: "",
                method: "post",
                success: function () {

                },
                error: function () {

                }
            }
            var http = new XMLHttpRequest() || new ActiveXObject("Microsoft.XMLHTTP");
            http.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    opts.success();
                    return;
                }
            }

        },
        dataJsLoader: function (uri, cb, charset) {
            var _script = document.createElement("script");
            _script.type = "text/javascript";
            _script.charset = charset || "utf-8";
            _script._fun = typeof cb != "undefined" ? cb : new Function();
            _script[document.all ? "onreadystatechange" : "onload"] = function () {
                if (document.all && this.readyState != "loaded" && this.readyState != "complete") {
                    return;
                }
                this._fun(this);
                this._fun = null;
                this[document.all ? "onreadystatechange" : "onload"] = null;
                var _t = this;
                _t.parentNode.removeChild(_t);
            };
            _script.src = uri;
            document.getElementsByTagName("head").item(0).appendChild(_script);


        },
        addEvent: function (el, type, handler, capture) {

            function withinElement(handler) {
                return function (e) {
                    var parent = e.relatedTarget;
                    while (parent && parent != this) {
                        try {
                            parent = parent.parentNode;
                        }
                        catch (e) {
                            break;
                        }
                    }
                    if (parent != this)
                        handler.call(this, e);
                };
            };

            if (typeof el.addEventListener != 'undefined') {
                if (type === 'mouseenter') {
                    el.addEventListener('mouseover', withinElement(handler), capture);
                } else if (type === 'mouseleave') {
                    el.addEventListener('mouseout', withinElement(handler), capture);
                } else {
                    el.addEventListener(type, handler, capture);
                }
            } else if (typeof el.attachEvent != 'undefined') {
                el['on' + type] = function () {
                    handler.call(this);
                };
            }
        },
        vcodeC: function (code) {
            var one = code.substr(0, 1);
            var three = code.substr(0, 3);
            if (one == "5" || one == "6" || one == "9") {
                //上证股票
                return "1";
            }
            else {
                if (three == "009" || three == "126" || three == "110" || three == "201" || three == "202" || three == "203" || three == "204") {
                    //上证股票
                    return "1";
                }
                else {
                    //深圳股票
                    return "2";
                }
            }
        },
        getCookie: function (name) {
            var bikky = document.cookie;
            name += "=";
            var i = 0;
            while (i < bikky.length) {
                var offset = i + name.length;
                if (bikky.substring(i, offset) == name) {
                    var endstr = bikky.indexOf(";", offset);
                    if (endstr == -1) endstr = bikky.length;
                    return unescape(bikky.substring(offset, endstr));
                }
                i = bikky.indexOf(" ", i) + 1;
                if (i == 0) break;
            }
            return null;
        },
        setCookie: function (c_name, value, expiredays) {
            var exdate = new Date();
            exdate.setDate(exdate.getDate() + expiredays);
            document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : "; expires=" + exdate.toGMTString());
        },
        insertText: function (obj, text) {
            if (document.selection) {
                obj.focus();
                var sel = document.selection.createRange();
                sel.text = text;
            }
            else {
                var prefix, main, suffix;
                prefix = obj.value.substring(0, obj.selectionStart);
                main = obj.value.substring(obj.selectionStart, obj.selectionEnd);
                suffix = obj.value.substring(obj.selectionEnd);
                if (main == "") {
                    obj.value = prefix + text + suffix;
                }
                else {
                    obj.value = prefix + text + suffix;
                }
            }
        },
        insertAfter: function (newElement, targetElement) {
            var parent = targetElement.parentNode;
            if (parent.lastChild == targetElement) {
                parent.appendChild(newElement);
            }
            else {
                parent.insertBefore(newElement, targetElement.nextSibling);
            }
        },
        createElement: function (option) {
            var obj = document.createElement(option.dom);
            obj.id ? obj.id = option.id : "";
            obj.className ? obj.className = option.className : "";
            obj.innerHTML = option.html;
            return obj;
        },
        changeURL: function (url, urlDom) {
            var _url = url;
            for (var i in urlDom) {
                var pattern = i + '=([^&]*)';
                var replaceText = i + '=' + urlDom[i];
                if (url.match(pattern)) {
                    var tmp = _url.match(pattern)[0];
                    var tmp1 = _url.match(pattern)[1];
                    if (urlDom[i] != tmp1) {
                        _url = _url.replace(tmp, replaceText);
                    }
                }
            }
            url = _url;
            return url;
        }
    }


    var LIB = window.LIB = {
        tab: function (opts) {
            var _opts = {
                pnode: "",
                selectedClass: "cur",
                contentID: "",
                callBacks: []
            }
            _opts = FUC.xcopy(opts, _opts);

            _opts.pnode = FUC.$(_opts.pnode);
            var cn = FUC.$(_opts.contentID);
            var arr = _opts.pnode.children;
            var carr = cn.children;
            for (var i = 0; i < arr.length; i++) {
                arr[i].setAttribute("index", i);
                arr[i].onmouseover = function (event) {
                    var t = this.getAttribute("index");
                    for (var j = 0; j < carr.length; j++) {
                        carr[j].style.display = 'none';
                    }
                    for (j = 0; j < arr.length; j++) {
                        FUC.removeClass(_opts.selectedClass, arr[j])
                    }
                    FUC.addClass(_opts.selectedClass, this);
                    carr[t].style.display = 'block';
                    _opts.callBacks[t] && _opts.callBacks[t]();
                }
            }
        },
        actTab: function (opts) {
            var _opts = {
                pnode: "",
                selectedClass: "cur",
                callBacks: "",
                eventKind: "click"
            }
            _opts = FUC.xcopy(opts, _opts);
            _opts.pnode = FUC.$(_opts.pnode);
            var arr = _opts.pnode.children;

            var handler = function () {
                var t = this.getAttribute("index");
                for (j = 0; j < arr.length; j++) {
                    FUC.removeClass(_opts.selectedClass, arr[j])
                }
                FUC.addClass(_opts.selectedClass, this);
                if (_opts.callBacks) {
                    _opts.callBacks(this);
                }
            }

            for (var i = 0; i < arr.length; i++) {
                arr[i].setAttribute("index", i);
                if (window.attachEvent) {
                    arr[i]["on" + _opts.eventKind] = function () {
                        handler.call(this);
                    }
                } else {
                    arr[i].addEventListener(_opts.eventKind, handler, false);
                }
            }
        }
    };

    var LiPic = window.LiPic = {
        change: function (opts) {
            var _opts = {
                dom1: "",
                dom2: "",
                type: "",
                selectedClass: "at",
                callbacks: ""
            }
            _opts = FUC.xcopy(opts, _opts);
            var li = _opts.dom1.children;
            for (var i = 0; i < li.length; i++) {
                li[i].onclick = function (i) {
                    return function () {
                        for (j = 0; j < li.length; j++) {
                            FUC.removeClass(_opts.selectedClass, li[j])
                        }
                        FUC.addClass(_opts.selectedClass, this);
                        if (_opts.dom2) {
                            var li2 = _opts.dom2.children;
                            for (j = 0; j < li2.length; j++) {
                                FUC.removeClass(_opts.selectedClass, li2[j])
                            }
                            FUC.addClass(_opts.selectedClass, li2[i]);
                            _opts.callbacks(this);
                            return;
                        }
                        _opts.callbacks(this);
                    }
                }(i)
            }
        },
        changeDiv: function (opts) {
            var _opts = {
                id: "",
                dom: [],
                selectedClass: "",
                callBacks: ""
            }
            _opts = FUC.xcopy(opts, _opts);
            var li = document.getElementById(_opts.id).children;
            for (var i = 0; i < li.length; i++) {
                li[i].onmouseover = function () {
                    for (j = 0; j < li.length; j++) {
                        FUC.removeClass(_opts.selectedClass, li[j])
                    }
                    FUC.addClass(_opts.selectedClass, this);
                    for (k = 0; k < _opts.dom.length; k++) {
                        var value = document.getElementById(_opts.dom[k]).getAttribute("value");
                        if (this.getAttribute("value") == value) {
                            document.getElementById(_opts.dom[k]).style.display = "block";
                        } else {
                            document.getElementById(_opts.dom[k]).style.display = "none";
                        }
                    }
                    if (_opts.callBacks) {
                        _opts.callBacks(this);
                    }
                }
            }
        },
        changeDiv2: function (opts) {
            var _opts = {
                id: "",
                dom: [],
                domt: [],
                selectedClass: "",
                callBacks: ""
            }
            _opts = FUC.xcopy(opts, _opts);
            var li = (document.getElementById(_opts.id) == null) ? "" : document.getElementById(_opts.id).children;
            for (var i = 0; i < li.length; i++) {
                li[i].onmouseover = function () {
                    for (j = 0; j < li.length; j++) {
                        FUC.removeClass(_opts.selectedClass, li[j])
                    }
                    FUC.addClass(_opts.selectedClass, this);
                    for (k = 0; k < _opts.dom.length; k++) {
                        var value = document.getElementById(_opts.dom[k]).getAttribute("value");
                        if (this.getAttribute("value") == value) {
                            document.getElementById(_opts.dom[k]).style.display = "block";
                            document.getElementById(_opts.domt[k]).style.display = "block";
                        } else {
                            document.getElementById(_opts.dom[k]).style.display = "none";
                            document.getElementById(_opts.domt[k]).style.display = "none";
                        }
                    }
                    if (_opts.callBacks) {
                        _opts.callBacks(this);
                    }
                }
            }
        }
    };

    var numParser = window.numParser = {
        def: function (a) {
            if (a.indexOf("-") >= 0) {
                return a;
            }
            a = parseFloat(a)
            if (a > 100000000) {
                return (a / 100000000).toFixed(2) + "亿";
            } else {
                return a > 10000 ? ((a / 10000).toFixed(2) + "万") : a;
            }
        },
        tos: function (a) {
            a = parseFloat(a) / 100
            return numParser.def(a) + "手"
        }
    }

    function formatm() {
        var now = new Date();
        return now.getDate() + "" + now.getHours() + "" + now.getMinutes() + "";
    }

})()
