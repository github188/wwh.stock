(function() {
    var e = {
        bw: 1230,
        sw: 1048
    };
    var c = document.getElementById("app-qr");
    if (c) {
        for (var d in e) {
            var j = c.getAttribute("data-" + d);
            if (j) {
                e[d] = j
            }
        }
    }
    var g;
    var a;
    function k() {
        h("* html,* html body {_background-image:url(about:blank);_background-attachment:fixed;}");
        i();
        f();
        window.onresize = b;
        b()
    }
    setTimeout(function() {
        k()
    });
    function b() {
        var l = document.documentElement.clientWidth;
        if (l >= e.bw && g) {
            g.style.display = "block";
            a.style.display = "none"
        } else {
            if (l >= e.sw) {
                g && (g.style.display = "none");
                a.style.display = "block"
            } else {
                g && (g.style.display = "none");
                a.style.display = "none"
            }
        }
    }
    function i() {
        g = document.createElement("div");
        g.style.cssText = "z-index:999;position:fixed; top:310px; right:15px; display: none;background:url(http://mat1.gtimg.com/finance/images/stock/p/portfolio/app-qr2.jpg) no-repeat; width:120px; height:171px;_position:absolute;_top:expression(eval(document.documentElement.scrollTop+330));";
        g.innerHTML = '<div class="top" style="width:95px; height: 25px; overflow: hidden; cursor: pointer;"></div><div class="close" style="border:0;width:25px; height: 25px; overflow: hidden; cursor: pointer;position: absolute; top: 0px; right: 0px;"></div><a style=" width:120px; height: 146px; overflow: hidden; cursor: pointer; display: block;" href="http://finance.qq.com/products/portfolio/download.htm?pgv_ref=qrcode.pic" target="_blank" id="xiaok-btn-link"></a>';
        document.body.appendChild(g);
        g.onclick = function(n) {
            n = n || window.event;
            var l = n.target || n.srcElement;
            if (l) {
                if (l.className == "top") {
                    window.scrollTo(0, 0);
                    var m = document.documentElement || document.body;
                    m.scrollTop = 0
                } else {
                    if (l.className == "close") {
                        g.onclick = null ;
                        document.body.removeChild(g);
                        g = null
                    }
                }
            }
        }
    }
    function f() {
        a = document.createElement("div");
        var l = "this.style.backgroundPosition";
        var m = "this.parentNode.children[2].style.display";
        a.style.cssText = "z-index:999;display:none; height:140px; position:fixed; right:15px; bottom:45px; width:58px_position:absolute;_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-45));";
        a.innerHTML = '	<a href="javascript:;" onmouseover="' + l + "='-71px 0';" + m + "='block'\" onmouseout=\"" + l + "='0 0';" + m + '=\'none\'" style="background:url(http://mat1.gtimg.com/finance/images/stock/p/cms/8257d2f41ba47e69.png) no-repeat; display:block; height:75px; width:58px;" title="\u624b\u673a\u8bbf\u95ee"></a>	<a href="#" onmouseover="' + l + "='-71px -83px'\" onmouseout=\"" + l + '=\'0 -83px\'" target="_self" style="background:url(http://mat1.gtimg.com/finance/images/stock/p/cms/8257d2f41ba47e69.png) 0 -83px no-repeat; display:block; height:57px; margin-top:8px; width:58px" title="\u8fd4\u56de\u9876\u90e8" onclick="window.scrollTo(0,0);return false"></a>	<div style="background:url(http://mat1.gtimg.com/finance/images/stock/p/cms/54652dd0d7a2e8be.png) no-repeat; display:none; height:212px; position:absolute; top:-120px; right:60px; width:227px;"></div>';
        document.body.appendChild(a)
    }
    function h(o) {
        var r = document;
        var q = r.getElementsByTagName("style");
        q = q && q[0];
        if (!q) {
            var p = r.head || r.getElementsByTagName("head")[0] || r.documentElement;
            var q = r.createElement("style");
            p.appendChild(q)
        }
        if (q.styleSheet) {
            q.styleSheet.cssText = q.styleSheet.cssText + o
        } else {
            q.appendChild(r.createTextNode(o))
        }
    }
})();
/*  |xGv00|70440dc874aba5ec918c129f95934617 */
