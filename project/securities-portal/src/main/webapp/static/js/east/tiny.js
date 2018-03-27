var Class = {
    create: function() {
        return function() {
            this.initialize.apply(this, arguments)
        }
    }
};
Object.extend = function(a, b) {
    for (property in b) {
        a[property] = b[property]
    }
    return a
}
;
var base = Class.create();
Object.extend(base.prototype, {
    initialize: function() {}
});
Function.prototype.bind = function() {
    var b = this
        , c = arguments[0]
        , a = Array.prototype.slice.call(arguments, 1);
    return function() {
        return b.apply(c, a)
    }
}
;
Date.prototype.format = function(b) {
    var c = this, g;
    b = (b == null) ? "yyyy-MM-dd HH:mm:ss" : b;
    var j = c.getFullYear()
        , h = c.getMonth() + 1
        , i = c.getDate()
        , o = c.getHours()
        , e = c.getMinutes()
        , q = c.getSeconds()
        , a = c.getMilliseconds()
        , n = (h > 9) ? h : "0" + h
        , l = (i > 9) ? i : "0" + i
        , k = (o > 9) ? o : "0" + o
        , f = (e > 9) ? e : "0" + e
        , p = (q > 9) ? q : "0" + q;
    a = (a > 9) ? (a > 99) ? a : "0" + a : "00" + a,
        g = b.replace("yyyy", j).replace("MM", n).replace("dd", l).replace("HH", k).replace("mm", f).replace("ms", a).replace("ss", p).replace("M", h).replace("d", i).replace("H", o).replace("m", e).replace("s", q);
    return g
}
;
Array.prototype.del = function(a) {
    if (a < 0) {
        return this
    }
    return this.slice(0, a).concat(this.slice(a + 1, this.length))
}
;
var tiny = new base();
Object.extend(tiny, {
    initialize: function() {
        var a = this
    },
    $: function(a) {
        return typeof (a) == "string" ? document.getElementById(a) : a
    },
    $tag: function(c, b) {
        var a = b || document;
        return typeof (c) == "string" ? a.getElementsByTagName(c) : c
    },
    $create: function(a) {
        return document.createElement(a)
    },
    $parent: function(b, a) {
        while (b.tagName.toLowerCase() != a) {
            b = b.parentNode
        }
        return b
    },
    extend: function(a, d, b) {
        if (b === undefined) {
            b = true
        }
        for (var c in d) {
            if (b || !(c in a)) {
                a[c] = d[c]
            }
        }
        return a
    },
    deepExtend: function(a, c) {
        for (var b in c) {
            var d = c[b];
            if (a === d) {
                continue
            }
            if (typeof d === "object") {
                a[b] = arguments.callee(a[b] || {}, d)
            } else {
                a[b] = d
            }
        }
        return a
    },
    browser: (function() {
        var e = window.navigator.userAgent.toLowerCase();
        var c = {
            msie: /msie/.test(e) && !/opera/.test(e),
            opera: /opera/.test(e),
            safari: /webkit/.test(e) && !/chrome/.test(e),
            firefox: /firefox/.test(e),
            chrome: /chrome/.test(e)
        };
        var a = "";
        for (var d in c) {
            if (c[d]) {
                a = "safari" == d ? "version" : d;
                break
            }
        }
        c.version = a && RegExp("(?:" + a + ")[\\/: ]([\\d.]+)").test(e) ? RegExp.$1 : "0";
        c.ie = c.msie;
        c.ie6 = c.msie && parseInt(c.version, 10) == 6;
        c.ie7 = c.msie && parseInt(c.version, 10) == 7;
        c.ie8 = c.msie && parseInt(c.version, 10) == 8;
        return c
    })(),
    isArray: function(a) {
        return Object.prototype.toString.call(a) === "[object Array]"
    },
    indexOf: function(d, b, c) {
        if (d.indexOf) {
            return isNaN(c) ? d.indexOf(b) : d.indexOf(b, c)
        } else {
            var a = d.length;
            c = isNaN(c) ? 0 : c < 0 ? Math.ceil(c) + a : Math.floor(c);
            for (; c < a; c++) {
                if (d[c] === b) {
                    return c
                }
            }
            return -1
        }
    },
    lastIndexOf: function(d, b, c) {
        if (d.lastIndexOf) {
            return isNaN(c) ? d.lastIndexOf(b) : d.lastIndexOf(b, c)
        } else {
            var a = d.length;
            c = isNaN(c) || c >= a - 1 ? a - 1 : c < 0 ? Math.ceil(c) + a : Math.floor(c);
            for (; c > -1; c--) {
                if (d[c] === b) {
                    return c
                }
            }
            return -1
        }
    },
    getScrollTop: function(a) {
        var b = a ? a.ownerDocument : document;
        return b.documentElement.scrollTop || b.body.scrollTop
    },
    getScrollLeft: function(a) {
        var b = a ? a.ownerDocument : document;
        return b.documentElement.scrollLeft || b.body.scrollLeft
    },
    contains: document.defaultView ? function(d, c) {
        return !!(d.compareDocumentPosition(c) & 16)
    }
        : function(d, c) {
        return d != c && d.contains(c)
    }
    ,
    rect: function(f) {
        var e = 0
            , h = 0
            , i = 0
            , b = 0
            , c = this.browser
            , a = this;
        if (!f.getBoundingClientRect || c.ie8) {
            var d = f;
            while (d) {
                e += d.offsetLeft,
                    h += d.offsetTop;
                d = d.offsetParent
            }
            i = e + f.offsetWidth;
            b = h + f.offsetHeight
        } else {
            var g = f.getBoundingClientRect();
            e = i = a.getScrollLeft(f);
            h = b = a.getScrollTop(f);
            e += g.left;
            i += g.right;
            h += g.top;
            b += g.bottom
        }
        return {
            left: e,
            top: h,
            right: i,
            bottom: b
        }
    },
    clientRect: function(d) {
        var c = this.rect(d)
            , b = this.getScrollLeft(d)
            , a = this.getScrollTop(d);
        c.left -= b;
        c.right -= b;
        c.top -= a;
        c.bottom -= a;
        return c
    },
    curStyle: document.defaultView ? function(a) {
        return document.defaultView.getComputedStyle(a, null)
    }
        : function(a) {
        return a.currentStyle
    }
    ,
    getStyle: document.defaultView ? function(c, a) {
        var b = document.defaultView.getComputedStyle(c, null);
        return a in b ? b[a] : b.getPropertyValue(a)
    }
        : function(e, b) {
        var a = e.style
            , h = e.currentStyle;
        if (b == "opacity") {
            if (/alpha\(opacity=(.*)\)/i.test(h.filter)) {
                var g = parseFloat(RegExp.$1);
                return g ? g / 100 : 0
            }
            return 1
        } else {
            if (b == "float") {
                b = "styleFloat"
            }
        }
        var i = this.curStyle[b] || this.curStyle[this.camelize(b)];
        if (!/^-?\d+(?:px)?$/i.test(i) && /^\-?\d/.test(i)) {
            var d = a.left
                , c = e.runtimeStyle
                , f = c.left;
            c.left = h.left;
            a.left = i || 0;
            i = a.pixelLeft + "px";
            a.left = d;
            c.left = f
        }
        return i
    }
    ,
    setStyle: function(a, c, d) {
        if (!a.length) {
            a = [a]
        }
        if (typeof c == "string") {
            var b = c;
            c = {};
            c[b] = d
        }
        A.forEach(a, function(f) {
            for (var e in c) {
                var g = c[e];
                if (e == "opacity" && B.ie) {
                    f.style.filter = (f.currentStyle.filter || "").replace(/alpha\([^)]*\)/, "") + "alpha(opacity=" + g * 100 + ")"
                } else {
                    if (e == "float") {
                        f.style[B.ie ? "styleFloat" : "cssFloat"] = g
                    } else {
                        f.style[S.camelize(e)] = g
                    }
                }
            }
        })
    },
    hasClass: function(a, c) {
        var b = new RegExp("(\\s|^)" + c + "(\\s|$)");
        return a.className.match(b)
    },
    addClass: function(a, b) {
        if (!this.hasClass(a, b)) {
            a.className += " " + b
        }
    },
    removeClass: function(a, c) {
        if (this.hasClass(a, c)) {
            var b = new RegExp("(\\s|^)" + c + "(\\s|$)");
            a.className = a.className.replace(b, " ")
        }
    },
    camelize: function(a) {
        return a.replace(/-([a-z])/ig, function(b, c) {
            return c.toUpperCase()
        })
    },
    loadJs: function(b, c) {
        var a = document.createElement("script");
        var e = this;
        var d = {
            charset: null,
            callback: function() {},
            remove: true
        };
        e.extend(d, c || {});
        if (!(d.charset == null || d.charset == "")) {
            a.setAttribute("charset", d.charset)
        }
        a.setAttribute("type", "text/javascript");
        a.setAttribute("src", b);
        this.$tag("head")[0].appendChild(a);
        a.onload = a.onreadystatechange = function() {
            if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") {
                d.callback(a);
                if (d.remove == true) {
                    e.removeJs(a)
                }
            }
        }
    },
    removeJs: function(c) {
        var a = (typeof c == "string") ? this.$(c) : c;
        a.onload = a.onreadystatechange = null;
        try {
            a.parentNode.removeChild(a)
        } catch (b) {}
    },
    loadCss: function(a) {
        var b = this.$create("link");
        b.rel = "",
            b.rel = "stylesheet",
            b.type = "text/css",
            b.href = a;
        this.$tag("head")[0].appendChild(b)
    },
    appendCss: function(a) {
        var b = this.$create("style");
        this.$tag("head")[0].appendChild(b);
        b.type = "text/css";
        if (b.styleSheet) {
            b.styleSheet.cssText = a
        } else {
            var c = document.createTextNode(a);
            b.appendChild(c)
        }
    },
    getNodePos: function(b) {
        var a = 0;
        while (b = b.previousSibling) {
            if (b.nodeType == 1) {
                a++
            }
        }
        return a
    },
    getPrevNode: function(b, a) {
        while (b = b.previousSibling) {
            if (b.nodeType == 1 && b.tagName.toLowerCase() == a) {
                break
            }
        }
        return b
    },
    getNextNode: function(b, a) {
        while (b = b.nextSibling) {
            if (b.nodeType == 1 && b.tagName.toLowerCase() == a) {
                break
            }
        }
        return b
    },
    setCookie: function(e, c, b) {
        var a;
        var d = new Date();
        b = b || 0;
        d.setTime(d.getTime() + b * 60 * 60 * 1000);
        a = e + "=" + escape(c) + ";path=/;expires=" + d.toGMTString();
        document.cookie = a
    },
    getCookie: function(c) {
        var a = document.cookie.lastIndexOf(c + "=");
        if (a == -1) {
            return null
        }
        var d = document.cookie.substring(a + c.length + 1);
        var b = d.indexOf(";");
        if (b == -1) {
            b = d.length
        }
        d = d.substring(0, b);
        d = unescape(d);
        return d
    },
    delCookie: function(b) {
        var a = new Date();
        document.cookie = b + "=;path=/;expires=" + a.toGMTString()
    },
    addEvent: function(e, b, c, a) {
        if (e.addEventListener) {
            e.addEventListener(b, c, a);
            return true
        } else {
            if (e.attachEvent) {
                var d = e.attachEvent("on" + b, c);
                return d
            } else {
                e["on" + b] = c
            }
        }
    },
    delEvent: function(e, b, c, a) {
        if (e.removeEventListener) {
            e.removeEventListener(b, c, a);
            return true
        } else {
            if (e.detachEvent) {
                var d = e.detachEvent("on" + b, c);
                return d
            } else {
                e["on" + b] = null;
                return
            }
        }
    },
    fixEvent: function(b) {
        if (b) {
            return b
        }
        b = window.event;
        var c = this;
        b.pageX = b.clientX + c.getScrollLeft(b.srcElement);
        b.pageY = b.clientY + c.getScrollTop(b.srcElement);
        b.target = b.srcElement;
        b.stopPropagation = stopPropagation;
        b.preventDefault = preventDefault;
        var a = {
            mouseout: b.toElement,
            mouseover: b.fromElement
        }[b.type];
        if (a) {
            b.relatedTarget = a
        }
        return b
    },
    getParam: function(b) {
        var a = window.location.search.match(new RegExp("(\\?|&)" + b + "=([^&]*)(&|$)"));
        if (a != null) {
            return unescape(a[2])
        }
        return null
    },
    loseHtml: function(a) {
        return a.replace(/<[^>]*?>/gi, "")
    },
    trimLeft: function(a) {
        return a.replace(/^(\u3000|\s|\t)*/gi, "")
    },
    trimRight: function(a) {
        return a.replace(/(\u3000|\s|\t)*$/gi, "")
    },
    trim: function(a) {
        return this.trimRight(this.trimLeft(a))
    },
    toInt: function(a) {
        return parseInt(a)
    },
    toArray: function(a) {
        if (a) {
            return this.split(a)
        } else {
            return this.split("")
        }
    },
    subTo: function(a, b) {
        return a.substring(0, b)
    },
    getInnerText: function(b) {
        var a = b.innerHTML.replace(/<[^>].*?>/ig, "");
        a = this.trim(a);
        return a
    },
    reUrl: function(a) {
        a = a.replace(/(\&|\\?)rt=([^\&]+)/ig, "");
        a += (a.indexOf("?") >= -1) ? "&rt=" + new Date().getTime() : "?rt=" + new Date().getTime();
        return a
    },
    repairAstyle: function() {
        var c = this.browser;
        if (c.ie) {
            var d = document.getElementsByTagName("a");
            for (var f = 0, e = d.length; f < e; f++) {
                d[f].onfocus = function() {
                    this.blur()
                }
            }
        }
    }
});
function stopPropagation() {
    this.cancelBubble = true
}
function preventDefault() {
    this.returnValue = false
}
function Base64() {
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    this.encode = function(c) {
        var a = "";
        var k, h, f, j, g, e, d;
        var b = 0;
        c = _utf8_encode(c);
        while (b < c.length) {
            k = c.charCodeAt(b++);
            h = c.charCodeAt(b++);
            f = c.charCodeAt(b++);
            j = k >> 2;
            g = ((k & 3) << 4) | (h >> 4);
            e = ((h & 15) << 2) | (f >> 6);
            d = f & 63;
            if (isNaN(h)) {
                e = d = 64
            } else {
                if (isNaN(f)) {
                    d = 64
                }
            }
            a = a + _keyStr.charAt(j) + _keyStr.charAt(g) + _keyStr.charAt(e) + _keyStr.charAt(d)
        }
        return a
    }
    ;
    this.decode = function(c) {
        var a = "";
        var k, h, f;
        var j, g, e, d;
        var b = 0;
        c = c.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (b < c.length) {
            j = _keyStr.indexOf(c.charAt(b++));
            g = _keyStr.indexOf(c.charAt(b++));
            e = _keyStr.indexOf(c.charAt(b++));
            d = _keyStr.indexOf(c.charAt(b++));
            k = (j << 2) | (g >> 4);
            h = ((g & 15) << 4) | (e >> 2);
            f = ((e & 3) << 6) | d;
            a = a + String.fromCharCode(k);
            if (e != 64) {
                a = a + String.fromCharCode(h)
            }
            if (d != 64) {
                a = a + String.fromCharCode(f)
            }
        }
        a = _utf8_decode(a);
        return a
    }
    ;
    _utf8_encode = function(b) {
        b = b.replace(/\r\n/g, "\n");
        var a = "";
        for (var e = 0; e < b.length; e++) {
            var d = b.charCodeAt(e);
            if (d < 128) {
                a += String.fromCharCode(d)
            } else {
                if ((d > 127) && (d < 2048)) {
                    a += String.fromCharCode((d >> 6) | 192);
                    a += String.fromCharCode((d & 63) | 128)
                } else {
                    a += String.fromCharCode((d >> 12) | 224);
                    a += String.fromCharCode(((d >> 6) & 63) | 128);
                    a += String.fromCharCode((d & 63) | 128)
                }
            }
        }
        return a
    }
    ;
    _utf8_decode = function(a) {
        var b = "";
        var d = 0;
        var e = c1 = c2 = 0;
        while (d < a.length) {
            e = a.charCodeAt(d);
            if (e < 128) {
                b += String.fromCharCode(e);
                d++
            } else {
                if ((e > 191) && (e < 224)) {
                    c2 = a.charCodeAt(d + 1);
                    b += String.fromCharCode(((e & 31) << 6) | (c2 & 63));
                    d += 2
                } else {
                    c2 = a.charCodeAt(d + 1);
                    c3 = a.charCodeAt(d + 2);
                    b += String.fromCharCode(((e & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                    d += 3
                }
            }
        }
        return b
    }
}
var help = function() {
    var a = tiny.getCookie("environmentType") || "web";
    if (a == "soft") {
        window.location.href = "help.html"
    } else {
        window.open("help.html")
    }
};
