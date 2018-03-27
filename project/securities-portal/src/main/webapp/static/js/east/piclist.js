function HSTemplate() {
    Template.call(this), this.header = '<ul class="mod-list-ul">', this.footer = "</ul>";
}
function NewHSTemplate() {
    Template.call(this), this.header = '<ul class="mod-list-ul">', this.footer = "</ul>";
}
function IndexTemplate() {
    Template.call(this), this.header = '<ul class = "mod-list-ul index">', this.footer = "</ul>";
}
function PicPageView() {
    this.dispose = function () {
        clearInterval(this.autoUpdate);
    }, PageView.call(this, !0);
}
var cache;
!function () {
    function t() {
        var t = this;
        t.render = function (e) {
            for (var a, l = [], o = 0, i = e.length; i > o;) {
                for (var n = [], s = o + 3; s > o && !(o >= e.length); o++)
                    a = String(e[o]).split(","), n.push(t.getItemTemplate(a));
                l.push(t.header + n.join("") + t.footer);
            }
            cache = e, $("listview").innerHTML = l.join("");
        };
    }
    t.prototype = { getItemTemplate: function () {
        throw "需要重写抽象方法string function getItemTemplate(data)";
    } }, window.Template = t;
}(), HSTemplate.prototype = new Template, HSTemplate.prototype.getItemTemplate = function (t) {
    var e, a, l, o = t[0];
    1 == o.length ? (a = o, l = t[1] + "" + o) : (a = o.substr(6), l = t[0]), e = 1 == a ? "sh" : "sz";
    var i = '<li class="stock"><a target="_blank" href="http://quote.eastmoney.com/' + e + t[1] + '.html"><img width="200" height="139" src="http://hqpicr.dfcfw.com/mrchart/' + l + ".gif?v=" + Math.random() + '" title="' + t[2] + '"></a><div class="buttons"><a class="btn-plus" target="_blank" href="http://quote.eastmoney.com/favor/infavor.aspx?code=' + t[1] + '" title="加自选"></a>&nbsp;<a class="btn-bigimg"  target="_blank" href="http://quote.eastmoney.com/' + e + t[1] + '.html" title="看大图"></a>&nbsp;<a class="btn-gubar"  target="_blank" href="http://guba.eastmoney.com/topic,' + t[1] + '.html" title="进股吧"></a></div></li>';
    return i;
}, NewHSTemplate.prototype = new Template, NewHSTemplate.prototype.getItemTemplate = function (t) {
    var e, a, l, o = t[0];
    1 == o.length ? (a = o, l = t[1] + "" + o) : (a = o.substr(6), l = t[0]), e = 1 == a ? "sh" : "sz";
    var i = '<li class="stock"><a target="_blank" href="http://quote.eastmoney.com/' + e + t[1] + '.html"><img width="200" height="139" src="http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/Index.aspx?id=' + l + "&imageType=rt&token=44c9d251add88e27b65ed86506f6e5da&v=" + Math.random() + '" title="' + t[2] + '"></a><div class="buttons"><a class="btn-plus" target="_blank" href="http://quote.eastmoney.com/favor/infavor.aspx?code=' + t[1] + '" title="加自选"></a>&nbsp;<a class="btn-bigimg"  target="_blank" href="http://quote.eastmoney.com/' + e + t[1] + '.html" title="看大图"></a>&nbsp;<a class="btn-gubar"  target="_blank" href="http://guba.eastmoney.com/topic,' + t[1] + '.html" title="进股吧"></a></div></li>';
    return i;
}, IndexTemplate.prototype = new Template, IndexTemplate.prototype.getItemTemplate = function (t) {
    var e = t[0];
    return zsbar = 1 == e ? "szzs" : "399001", '<li class="stock"><a href="http://quote.eastmoney.com/zs' + t[1] + '.html" target="_blank"><img src="http://hqpicr.dfcfw.com/mrchart/' + t[1] + t[0] + '.gif" title="' + t[2] + '" /></a><div class="buttons"><a class="btn-plus" target="_blank" title="加自选"></a>&nbsp;<a class="btn-bigimg" href="http://quote.eastmoney.com/zs' + t[1] + '.html" target="_blank" title="看大图"></a>&nbsp;<a class="btn-gubar" href="http://guba.eastmoney.com/topic,' + zsbar + '.html" target="_blank" title="进股吧"></a></div></li>';
}, PicPageView.prototype = PageView.prototype, PicPageView.prototype.pageWrapper = new PageNavigation("pagenav"), PicPageView.prototype.pageSize = 9, PicPageView.prototype.hsTemplate = new HSTemplate, PicPageView.prototype.indexTemplate = new IndexTemplate, PicPageView.prototype.newHSTemplate = new NewHSTemplate, document.getElementById("v1").onclick = function () {
    location.href = "list.html#" + getHash() + "?mode=grid&sortType=" + viewstate["sortType"] + "&sortRule=" + viewstate["sortRule"];
}, PicPageView.prototype.setAutoUpdate = function (t) {
    var e = this;
    t.interval = 3e4, e.autoUpdate = setInterval(function () {
        t.delegate() ? e.template.render(cache) : clearInterval(e.autoUpdate);
    }, t.interval);
};
