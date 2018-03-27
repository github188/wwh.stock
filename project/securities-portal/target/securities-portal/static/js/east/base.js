try { document.execCommand('BackgroundImageCache', false, true); } catch (e) { }
var $ = function(o) { return typeof (o) == 'string' ? document.getElementById(o) : o; }
var $E = function (e) { tempObj = e.target ? e.target : event.srcElement; return tempObj; }

jQuery(function () {
    //控制一级标题粗细下划线的宽度
    var $ = jQuery;
    var tits = $(".tit");
    tits.each(function (index, ele) {
        if ($(ele).prev().hasClass("el") && $(ele).parent().next().hasClass("sepe")) {
            var tit_wid = $(ele).width();
            var titbar_wid = $(ele).parent().width();

            if (tit_wid && titbar_wid) {
                $(ele).parent().next().find(".left").width((tit_wid + 30) + "px");
                $(ele).parent().next().find(".right").width((titbar_wid - tit_wid - 30) + "px");
            }

        }
    });

    $(".catemk ul").each(function (index, ele) {
        if (ele) {
            var len = $(ele).find("li").length;
            if (len > 1) {
                ($(ele).find("li")).first().addClass("border_left_1");
            }
        }
    })

    //重写页面导航“数据中心”链接地址
    var data_center_link = $("#Column_Navigation").children("a:eq(1)");
    if (data_center_link && data_center_link.get(0)) {
        if (data_center_link.text().indexOf("数据中心") != -1) {
            data_center_link.attr("href", portalurl+"stockeast/center/");
        }
    }
})

var addEvent = function(elm, evType, fn, useCapture) {
    if (elm.addEventListener) {
        elm.addEventListener(evType, fn, useCapture);
        return true;
    } else if (elm.attachEvent) {
        var r = elm.attachEvent('on' + evType, fn);
        return r;
    } else {
        elm['on' + evType] = fn;
    }
}
var delEvent = function(elm, evType, fn, useCapture) {
    if (elm.removeEventListener) {
        elm.removeEventListener(evType, fn, useCapture);
        return true;
    } else if (elm.detachEvent) {
        var r = elm.detachEvent('on' + evType, fn);
        return r;
    } else {
        elm['on' + evType] = null;
        return;
    }
}

function so_search(bb) {
    var s1 = bb;
    var s = escape(s1);
    if (s1 == "请输入关键字" || s1 == "") {
        window.open("http://so.eastmoney.com/");
        return false;
    } else {
        window.open("http://so.eastmoney.com/Search.aspx?Page=1&t=body&q=" + s);
        return false;
    }
}

function quotesearch(code) {

    stockcode_current = code;
    if (stockcode_current == "请输入代码或简称" || stockcode_current == "") stockcode_current = "600900";
    var re = /[0-9]{6}/;
    var re2 = /[0-9]{1,}/;
    var re3 = /[^0-9]{1,}/;
    bool1 = code.match(re);
    bool2 = code.match(re2);
    bool3 = code.match(re3);

    if (bool1 != null && stockcode_current.length == 6) {
        window.open("http://quote.eastmoney.com/" + stockcode_current + ".html");
    }
    else {
        if (bool2 != null && bool3 == null && stockcode_current.length < 3 || stockcode_current.length == 3 && stockcode_current == "60" || stockcode_current.length == 3 && stockcode_current == "000") { alert("符合条件的股票代码过多，请重新输入关键字！"); return false; }
        if (bool2 == null && bool3 != null && stockcode_current.length < 2) { alert("模糊查询时关键字至少2位！"); return false; }
        window.open("http://quote.eastmoney.com/quote.asp?stockcode=" + stockcode_current);
    }
    return (false);
}

function q_search(bb) {
    var s1 = bb;
    var s = escape(s1);
    if (s1 == "输代码、名称或拼音" || s1 == "") {
        window.open("http://q.eastmoney.com/");
        return false;
    } else {
        window.open("http://q.eastmoney.com/q.aspx?StockCode=" + s);
        return false;
    }
}

function getRadioValue(radio) {
    if (!radio.length && radio.type.toLowerCase() == 'radio') {
        return (radio.checked) ? radio.value : '';
    }
    if (radio[0].tagName.toLowerCase() != 'input' || radio[0].type.toLowerCase() != 'radio') return '';
    var len = radio.length;
    for (i = 0; i < len; i++) {
        if (radio[i].checked) {
            return radio[i].value;
        }
    }
    return '';
}

var Cookies = {
    Get: function(sName) {
        var cookies = document.cookie;
        if (cookies == null || cookies == "undefined") {
            return null;
        }
        var aCookie = cookies.split("; ");
        var value = null;
        for (var i = 0; i < aCookie.length; i++) {
            var aCrumb = aCookie[i].split("=");
            if (sName == aCrumb[0]) {
                value = aCrumb[1];
                break;
            }
        }
        if (value == null || value == "undefined" || value.length == 0) return null;
        return decodeURIComponent(value);
    },
    Delete: function(sName) {
        var date = new Date();
        document.cookie = sName + "=;path=/;domain=eastmoney.com;expires=" + date.toGMTString();
    }
};
var User = {
    init: function(a, b) {
        var _name = Cookies.Get("dcuser_name");
        var _nick = Cookies.Get("dcuser_mingchen");
        if (_name == null || _nick == null) {
            $(a).style.display = "block";
            $(b).style.display = "none";
        } else {
            $(a).style.display = "none";
            $(b).style.display = "block";
            $(b).innerHTML = '欢迎&nbsp;&nbsp;&nbsp;&nbsp;<strong>' + _nick + '</strong>&nbsp;&nbsp;<a href="http://passport.eastmoney.com/UserCenter.EmUser" target="blank">用户中心</a>&nbsp;&nbsp;<a href=\"javascript:;\" onclick=\"User.exit(\'' + a + '\',\'' + b + '\')\" target=\"_top\">退出</a>';
        }
    },
    exit: function(a, b) {
        Cookies.Delete("dcuser_name");
        Cookies.Delete("dcuser_mingchen");
        $(a).style.display = "block";
        $(b).style.display = "none";
    }
};



// 加入收藏
function AddFavor() {
    try {
        window.external.addFavorite(window.location.href, window.document.title);
    } catch (e) {
        try {
            window.sidebar.addPanel(window.document.title, window.location, "");
        } catch (e) {
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}

var selector = function(dom) {
    var select = document.getElementById(dom);
    if (select) {
        var index = select.selectedIndex;
        var value = select.options[index].value;
        return value;
    } else {
        return null;
    }
}



function killErrors() {
    return true;
}
//window.onerror = killErrors;

/* fangliming 2011.01.06
 update: 2011.08.23 @flm
 */
/* 
 new StockSuggest("StockCode_bar", arg);
 通用股票搜索定义
 */

var arg = {
    text : "输代码、名称或拼音",
    autoSubmit : true,
    width : 190,
    header : ["选项", "代码", "名称", "类型"],
    body : [-1, 1, 4, -2],
    callback : function(ag){}
};

function info_search(bb){
    var s1=bb;
    var s = escape(s1);
    if (s1=="输代码、名称或拼音" || s1=="" ){
        window.open("http://so.eastmoney.com/");
    }else{
        window.open("http://so.eastmoney.com/Search.aspx?Page=1&q="+s+"&t=body");
    }
}

function gb_search(code){
    var keywords = (code=="输代码、名称或拼音缩写" || code=="输代码、名称或拼音" || code=="输入代码或简称") ? "" : code;
    window.open('http://quote.eastmoney.com/search.aspx?stockcode=' + escape(keywords) + '&toba=1');
}


var flagStock = false;
var soType = "stock";

function toQuote() {
    window.StockCode.Submit();
    return false;
}

function checkStock(m_flagStock){
    var $=function(id){return document.getElementById(id);}
    flagStock = m_flagStock;
    soType = getRadioValue($("sofrm").stype);
    //var stock_input = $("StockCode_bar");
    var scb = window.StockCodeBar;

    switch (soType) {
        case "stock":
            scb.input.value = "输代码、名称或拼音";
            this.value = "stock";
            scb.autoSubmit = true;
            break;
        case "guba":
            scb.input.value = "输代码、股吧名";
            this.value = "guba";
            scb.autoSubmit = false;
            break;
        case "blog":
            scb.input.value = "请输入查询内容";
            this.value = "blog";
            scb.autoSubmit = false;
            break;
        case "news":
            scb.input.value = "请输入查询内容";
            this.value = "news";
            scb.autoSubmit = false;
            break;
        default:
            scb.input.value = "请输入查询内容";
            this.value = "";
            scb.autoSubmit = false;
            break;
    }

    switch (soType) {
        case "stock":
            ss.value = "输代码、名称或拼音";
            this.value = "stock";
            ss.autoSubmit = false;
            break;
        case "guba":
            ss.value = "输代码、股吧名";
            this.value = "guba";
            ss.autoSubmit = false;
            break;
        case "blog":
            ss.value = "请输入查询内容";
            this.value = "blog";
            ss.autoSubmit = false;
            break;
        case "news":
            ss.value = "请输入查询内容";
            this.value = "news";
            ss.autoSubmit = false;
            break;
        default:
            ss.value = "请输入查询内容";
            this.value = "";
            ss.autoSubmit = false;
            break;
    }
}
function checkso(obj){
    var o = document.getElementById(obj);
    var keywords = o.StockCode_bar.value;
    keywords = (keywords=='输代码、名称或拼音' || keywords=='输代码、股吧名' || keywords=='请输入查询内容' )
        ? ""
        : keywords;

    if((keywords.length<2 && (soType == "news" || soType == "blog"))){
        alert("关键字不能少于两位字符");
        return false;
    }

    if(soType == "news" || soType == "blog"){
        window.open("http://so.eastmoney.com/Search.aspx?q="+escape(keywords)+"&searchclass="+soType);
    }else if(soType == "guba"){
        window.open('http://quote.eastmoney.com/search.aspx?stockcode=' + escape(keywords) + '&toba=1');
    } else {
        window.StockCodeBar.Submit();
        //window.open('http://quote.eastmoney.com/search.aspx?stockcode=' + escape(keywords) + '');
    }
    return false;
}

function getRadioValue(radio){
    if (!radio.length && radio.type.toLowerCase() == 'radio'){
        return (radio.checked)?radio.value:'';
    }
    if (radio[0].tagName.toLowerCase() != 'input' || radio[0].type.toLowerCase() != 'radio') return '';
    var len = radio.length;
    for(i=0; i<len; i++){
        if (radio[i].checked){
            return radio[i].value;
        }
    }
    return '';
}

/*
 微型Dom库
 */
var tiny = function(selector,content){
    return tiny.fn.init(selector,content);
}

tiny.fn = tiny.prototype = {
    init:function(selector,content){
        if (!selector) return this;
        this[0] = (typeof selector==="string") ?
            tiny.selector(selector,content) : selector ;
        return this;
    }
}

tiny.fn.init.prototype = tiny.fn;

// 选择器
tiny.selector = function(selector,content){
    content = content || document;
    // 返回id为selector的Dom对象
    if (/^\w+$/.test(selector)){return content.getElementById(selector)}
}

// 浏览器判断
// @return 浏览器对象
tiny.browser = (function(){
    var ua = window.navigator.userAgent.toLowerCase();
    var b = {
        msie: /msie/.test(ua) && !/opera/.test(ua),
        opera: /opera/.test(ua),
        safari: /webkit/.test(ua) && !/chrome/.test(ua),
        firefox: /firefox/.test(ua),
        chrome: /chrome/.test(ua)
    };
    var vMark = "";
    for (var i in b) {
        if (b[i]) {
            vMark = "safari" == i ? "version": i;
            break;
        }
    }
    b.version = vMark && RegExp("(?:" + vMark + ")[\\/: ]([\\d.]+)").test(ua) ? RegExp.$1: "0";
    b.ie = b.msie;
    b.ie6 = b.msie && parseInt(b.version, 10) == 6;
    b.ie7 = b.msie && parseInt(b.version, 10) == 7;
    b.ie8 = b.msie && parseInt(b.version, 10) == 8;
    return b;
})();

// 遍历对象或数组
// @param {Object}
// @param {Function}
tiny.each = function(obj, fn){
    var name, i = 0,
        length = obj.length,
        isObj = length === undefined;
    if (isObj) {
        // 对象模式
        for (name in obj) {
            if (fn.call(obj[name], name, obj[name]) === false) {
                break;
            };
        };
    } else {
        // 数组模式
        for (var value = obj[0]; i < length &&
        fn.call(value, i, value) !== false; value = obj[++i]) {};
    };
};

//样式处理
tiny.hasClass=function(element, className) {
    var reg = new RegExp('(\\s|^)'+className+'(\\s|$)');
    return element.className.match(reg);
}

tiny.addClass=function (element, className) {
    if (!this.hasClass(element, className))
    {
        element.className += " "+className;
    }
}

tiny.removeClass= function (element, className) {
    if (this.hasClass(element, className)) {
        var reg = new RegExp('(\\s|^)'+className+'(\\s|$)');
        element.className = element.className.replace(reg,'');
    }
}

tiny.getTxt = function(element){
    var val = element.innerHTML;
    var reg = new RegExp('\<[^>]*?\>',"ig");
    val = val.replace(reg,'');
    val = val.replace(/^\s+|\s+$/ig,'');
    return val
}

tiny.fold = function (titleObj, showObj, className) {
    var _this = this;
    var isShow = true;
    var titleObj = _this.selector(titleObj, document);
    var showObj = _this.selector(showObj, document);
    _this.addEvent(titleObj, "click", function () {
        if (isShow) {
            showObj.style.display = "none";
            titleObj.className = "";
        } else {
            showObj.style.display = "";
            titleObj.className = className;
        }
        isShow = !isShow;
    }, false);
}
// 扩展
tiny.extend = function(destination, source, override) {
    if (override === undefined) override = true;
    for (var property in source) {
        if (override || !(property in destination)) {
            destination[property] = source[property];
        }
    }
    return destination;
};


// 附加事件
tiny.addEvent = function(elem, eType, fn, useCapture){
    if (elem.addEventListener) {
        elem.addEventListener(eType, fn, useCapture);
        return true;
    } else if (elem.attachEvent) {
        var r = elem.attachEvent('on' + eType, fn);
        return r;
    } else {
        elem['on' + eType] = fn;
    }
}

// 
$tag = function(selector,content){
    content = content || document;
    return elems = content.getElementsByTagName(selector);
}

tiny.loadJs = function(url,charset,callback,remove){
    var _js=document.createElement('script');
    var _this = this;
    remove = (remove===undefined) ? true : false;
    if (!(charset ==null || charset =='')){ _js.setAttribute('charset',charset);}
    _js.setAttribute('type','text/javascript');
    _js.setAttribute('src',url);
    $tag('head')[0].appendChild(_js);
    if (navigator.userAgent.indexOf("MSIE") > -1) {//IE
        _js.onreadystatechange = function () {
            if (this.readyState == "loaded" || this.readyState == "complete") {
                callback(_js);
                if (remove == true) { _this.removeJs(_js); }
            }
        }
    }
    else {//其他浏览器
        _js.onload = function () {
            callback(_js);
            if (remove == true) { _this.removeJs(_js); }
        }
    }
}

tiny.removeJs = function(o){
    var _js= (typeof o=="string") ? this.tiny(o) : o;
    _js.onload = _js.onreadystatechange = null;
    try{
        _js.parentNode.removeChild(_js);
    }catch(e){}
}

// Tab切换
tiny.tabSliding = function(slidingObj, tagName, classname, showObj, eventType ,callback){
    var sliderCache = {},_this = this;
    var elements = document.getElementById(slidingObj).getElementsByTagName(tagName);
    eventType = (eventType==null || eventType==0) ? "mouseover" : "click";
    var slidingChg = function(obj,num){
        var tempObj = obj;
        while(tempObj.nodeName != tagName.toUpperCase()){
            tempObj = tempObj.parentNode;
        }
        if (sliderCache.obj==null || sliderCache.obj != tempObj){
            if(sliderCache.obj){
                _this.removeClass(sliderCache.obj,classname);
            }
            if(showObj!=""){
                if(sliderCache.obj){
                    document.getElementById(showObj + parseInt(sliderCache.id)).style.display = "none";
                }
                document.getElementById(showObj + parseInt(num)).style.display = "block";
            }
            _this.addClass(tempObj,classname);
            sliderCache.obj = tempObj;
            sliderCache.id = num;
        }
        if(callback===undefined){
        }else{
            callback(num,tempObj);
        }
    };
    for(var i = 0; i < elements.length; i++){
        var fn = function(obj, num, e){
            slidingChg(obj, parseInt(num) + 1);
        };
        _this.addEvent(elements[i], eventType, fn.binding(this,elements[i],i), false);
        if(_this.hasClass(elements[i],classname)) sliderCache = {obj:elements[i],id:parseInt(i)+1};
    }
}

Function.prototype.binding=function(){
    var __m = this, object = arguments[0], args = new Array();
    for(var i = 1; i < arguments.length; i++){
        args.push(arguments[i]);
    }
    return function() {
        return __m.apply(object, args);
    }
};

tiny.addFavor = function () {
    try {
        window.external.addFavorite(window.location.href, window.document.title);
    } catch (e) {
        try {
            window.sidebar.addPanel(window.document.title, window.location, "");
        } catch (e) {
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}

tiny.tool = {
    loadJs: function (url, charset, callback) {
        var _js = document.createElement('script');
        var _this = this;
        if (!(charset == null || charset == '')) { _js.setAttribute('charset', charset); }
        _js.setAttribute('type', 'text/javascript');
        _js.setAttribute('src', url);
        document.getElementsByTagName('head')[0].appendChild(_js);
        _js.onload = _js.onreadystatechange = function () {
            if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") {
                callback(_js);
                _this.removeJs(_js);
            }
        }
    },
    removeJs: function (o) {
        var _js = (typeof o == "string") ? document.getElementById(o) : o;
        _js.onload = _js.onreadystatechange = null;
        try {
            _js.parentNode.removeChild(_js);
        } catch (e) { }
    },
    addEvent: function (element, eventType, callback) {
        var self = this;
        eventType = eventType.toLowerCase();
        if (element.addEventListener) {
            element.addEventListener(eventType, callback, false);
        }
        else if (element.attachEvent) {
            element.attachEvent("on" + eventType, callback);
        }
    },
    getElementByClassName: function (classname, context, tag) {
        var context = context || document;
        var tag = tag || "*";
        var allElements = null;
        var elements = [];
        allElements = context.getElementsByTagName(tag);
        if (!allElements) {
            return;
        } else {
            for (var i = 0, len = allElements.length; i < len; i++) {
                var cNames = allElements[i].className.split(' ');
                for (var index = 0, count = cNames.length; index < count; index++) {
                    if (cNames[index] === classname) {
                        elements.push(allElements[i]);
                        break;
                    }
                }

            }
        }
        return elements;
    },
    GetQueryStr: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURIComponent(r[2]); return '';
    }
}

tiny.go = function (url, id, pagecount) {
    if (!document.getElementById(id)) {
        return;
    }
    var inputObj = document.getElementById(id);
    var page = inputObj.value;
    var reg = /^[1-9]([0-9])*$/;
    if (!reg.test(page)) {
        page = 1;
    }
    if (page * 1 > pagecount * 1) {
        page = pagecount;
    }
    var nUrl = url.replace("{page}", page);
    location.href = nUrl;
}

// 日期格式化
tiny.dateFormat=function(date,part){
    if(typeof date=="undefined") date=new Date();
    var datecopy;
    var redate = "";
    part = (part==null)?"yyyy-MM-dd HH:mm:ss":part;
    var y = date.getFullYear();
    var M = date.getMonth() + 1;
    var d = date.getDate();
    var H = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();
    var MM = (M>9) ? M : "0"+M;
    var dd = (d>9) ? d : "0"+d;
    var HH = (H>9) ? H : "0"+H;
    var mm = (m>9) ? m : "0"+m;
    var ss = (s>9) ? s : "0"+s;
    redate = part.replace("yyyy",y).replace("MM",MM).replace("dd",dd).replace("HH",HH).replace("mm",mm).replace("ss",ss).replace("M",M).replace("d",d).replace("H",H).replace("m",m).replace("s",s);
    return redate;
}

// 内建时钟
var Eastmoney = {};
Eastmoney.Time = {
    cache:{timespan:0,queue:[]},
    now:function(){
        return new Date((new Date().getTime()) + this.cache.timespan);
    },
    push:function(id){
        this.cache.queue.push(tiny(id)[0]);
    },
    show:function(){
        var _this = this;
        tiny.each(_this.cache.queue,function(i,obj){
            if(obj){
                obj.innerHTML = tiny.dateFormat(_this.now());
            }
        })
    },
    init:function(){
        var _this = this,
            _his = new Date().getTime(),
            _stop = true,
            _loading = false;
        var timer = {
            process:null,
            start:function(){
                this.process = window.setInterval(
                    function(){
                        var _now = new Date().getTime();
                        if( Math.abs(_now - _his) < 5000){
                            _his = _now;
                        }else{
                            _his = _now;
                            loadServiceTime(true);
                        }
                        _this.show();
                    },1000);
            },
            clear:function(){
                var _this = this;
                if(_this.process){ window.clearInterval(_this.process);}
            }
        }
        var loadServiceTime = function(){
            if(!(typeof global_deviation == "undefined" || global_deviation==null)){
                _this.cache.timespan = global_deviation;
                _stop = false;
            }else{
                timer.clear();
                var _start = new Date().getTime();
                _loading = true;
                tiny.loadJs("http://blog.eastmoney.com/timezone.aspx?r=" + Math.random(),
                    "gb2312",
                    function(){
                        if(!(typeof(bjTime) == "undefined" || bjTime ==null || bjTime =="")){
                            var _end = new Date().getTime();
                            _this.cache.timespan = bjTime*1000 +  (_end-_start)*0.5 - _end; //时间修正,加上通信时间90%
                            _stop = false;

                        }
                        timer.start();_his = new Date().getTime();_loading = false;
                    });
            }
        }
        loadServiceTime();
    }
}
tiny.addEvent(window,"load",function(){Eastmoney.Time.init();});


/* 
 更新全球行情指数 含切换
 */
function GlobalIndexQuoteUpdate(id,interval){
    var cache={
        fristRun:true,
        displayCont:"",
        eventjs:"",
        updates:0,
        local:{
            update:false,
            url:"http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/CompatiblePage.aspx?Type=C&jsName=localIndex&ino=0000011,3990012&refrence=flm",
            data:[]
        },
        hk:{
            update:false,
            url:"http://hq2hk.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?Type=S&style=32&sortType=A&pageSize=1&jsname=hkIndex&reference=flm",
            data:[]
        },
        global:{
            update:false,
            url:"http://hq2gjqh.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?Type=Z&jsname=globalIndex&IDs=indu7,ccmp7,nky7,dax7,cac7,ukx7,atx7&refrence=flm",
            data:[]
        }

    }

    var loadQuoteData = function(type){
        type = (type===undefined) ? false : true;
        var _time = parseInt(tiny.dateFormat(Eastmoney.Time.now(),"HHmm")*1);
        var _bigturn = (cache.updates%3==0) ? true : false;


        // 国内指数
        if(type==true || (_time>924 && _time<1145) || (_time>1259 && _time<1515)){
            cache.local.update = true;
        }else{
            cache.local.update = false;
        }

        // 港股指数
        if(type==true || (((_time>1000 && _time<1230) || (_time>1400 && _time<1630)) && _bigturn )){
            cache.hk.update = true;
        }else{
            cache.hk.update = false;
        }

        // 欧美指数
        if(type==true || ((_time<800 || _time>1600) && _bigturn )){
            cache.global.update = true;
        }else{
            cache.global.update = false;
        }

        cache.updates++;
        loadLocalData();
    }
    // 获取国内指数
    var loadLocalData = function(){
        if(cache.local.update){
            tiny.loadJs(cache.local.url + "?rt=" + Math.random(),
                "utf-8",
                function(){
                    if(!(typeof localIndex =="undefined" || localIndex==""
                        || typeof localIndex.dpif == "undefined" || localIndex.dpif=="")){
                        cache.local.data = localIndex.dpif;
                        loadHKData();
                    }
                });
        }else{
            loadHKData();
        }
    }

    // 获取香港指数
    var loadHKData = function(){
        if(cache.hk.update){
            tiny.loadJs(cache.hk.url + "?rt=" + Math.random(),
                "utf-8",
                function(){
                    if(!(typeof hkIndex =="undefined" || hkIndex==""
                        || typeof hkIndex.rank == "undefined" || hkIndex.rank=="")){
                        cache.hk.data = hkIndex.rank;
                        loadGlobalData();
                    }
                });
        }else{
            loadGlobalData();
        }
    };

    // 获取全球指数
    var loadGlobalData = function(){
        if(cache.global.update){
            tiny.loadJs(cache.global.url + "?rt=" + Math.random(),
                "utf-8",
                function(){
                    if(!(typeof globalIndex =="undefined" || globalIndex==""
                        || typeof globalIndex.quotation == "undefined" || globalIndex.quotation=="")){
                        cache.global.data = globalIndex.quotation;
                        displayData();
                    }
                });
        }else{
            displayData();
        }
    };

    // 显示到页面
    var displayData = function(){
        var _str = "<ul>";

        //加国内数据
        _str += "<li>";
        tiny.each(cache.local.data,function(i,val){
            var _temp=val.split(','),_class,_arr,_name,_link,_link_up,_link_down;
            if (parseFloat(_temp[4])>0){
                _class = " class=\"red\"";
                _arr   = "↑";
            }else if(parseFloat(_temp[4])==0){
                _class = " class=\"\"";
                _arr = "";
            }else{
                _class = " class=\"green\"";
                _arr   = "↓";
            };
            _name = ( _temp[1] == "000001" ) ? "上证" : "深证";
            _link = ( _temp[1] == "000001" )
                ? "http://quote.eastmoney.com/zs000001.html"
                : "http://quote.eastmoney.com/zs399001.html";
            _link_up = ( _temp[1] == "000001" )
                ? "http://quote.eastmoney.com/center/list.html#10_0_0_u?sortType=C&sortRule=-1"
                : "http://quote.eastmoney.com/center/list.html#20_0_0_u?sortType=C&sortRule=-1";
            _link_down = ( _temp[1] == "000001" )
                ? "http://quote.eastmoney.com/center/list.html#10_0_0_u?sortType=C&sortRule=1"
                : "http://quote.eastmoney.com/center/list.html#20_0_0_u?sortType=C&sortRule=1";
            _str+= (i>0) ? " " : "";
            _str+= "<a href=\"" + _link + "\">" + _name + "</a>：<span" + _class +" ><span class=\"w\">" + _temp[3] + "</span> "
            + _arr +"<span class=\"w\">" + _temp[4] + "</span> <span class=\"w\">" + _temp[5] + "</span> "
            + "<span class=\"w\">"+ parseFloat(parseInt(_temp[6]/100)/100).toFixed(2) + "</span></span>亿 "
            + "(涨:<a href=\"" + _link_up + "\" class=\"red\">"
            + "<span class=\"w\">"+ _temp[7] + "</span></a> 平:<span class=\"w\">" + _temp[8] + "</span> "
            + "跌:<a href=\"" + _link_down + "\" class=\"green\">"
            + "<span class=\"w\">"+ _temp[9] + "</span></a>)";
        });

        _str += "</li><li>";

        //加国际
        tiny.each(cache.global.data,function(i,val){

            var _temp = val.split(","),
                _code = _temp[1].toLowerCase(),
                _name = _temp[2],
                _price = _temp[5],
                _change = parseFloat(_temp[10]),
                _present = _temp[11],
                _space = "";
            _link = "http://stock.eastmoney.com/globalindex/" + _code + ".html",
                _arr = (_change > 0) ? "↑" : "" ,
                _class = (_change > 0) ? "red" : "" ;

            _arr = (_change < 0) ? "↓" : _arr ;
            _class = (_change < 0) ? "green" : _class;

            _space ="&nbsp;";
            if (i>2){
                _space ="&nbsp;&nbsp;"
            }

            switch (_code)
            {
                case "indu" :
                    _name = "道琼斯";
                    break;
                case "ccmp" :
                    _name = "纳斯达克";
                    break;
                case "nky"  :
                    _name = "日经";
                    break;
                case "dax"  :
                    _name = "德DAX";
                    break;
                case "cac"  :
                    _name = "法CAC";
                    break;
                case "ukx"  :
                    _name = "英UKX";
                    break;
                case "atx"  :
                    _name = "奥ATX";
                    break;
            }

            _str += "<a href=\"" + _link + "\">" + _name + "</a> ";
            _str += "<span class=\"" + _class + " w\">" + _price + "</span> ";
            _str += "<span class=\"" + _class + "\">" + _arr +"<span class=\"w\">"+ _change + "</span></span> ";
            _str += "<span class=\"" + _class + " w\">"+ _present + "</span> " + _space;

            if(i==2){
                //塞个港股进来
                _temp = cache.hk.data[0].split(","),
                    _code = _temp[1].toLowerCase(),
                    _name = "恒指",
                    _price = _temp[5],
                    _change = _temp[10],
                    _present = _temp[11],
                    _link = "http://quote.eastmoney.com/hk/zs110000.html",
                    _arr = (_change > 0) ? "↑" : "" ,
                    _class = (_change > 0) ? "red" : "" ;

                _arr = (_change > 0) ? "↓" : _arr ;
                _class = (_change < 0) ? "green" : _class;

                _str += "<a href=\"" + _link + "\">" + _name + "</a> ";
                _str += "<span class=\"" + _class + " w\">" + _price + "</span> ";
                _str += "<span class=\"" + _class + "\">" + _arr +"<span class=\"w\">"+ _change + "</span></span> ";
                _str += "<span class=\"" + _class + " w\">"+ _present + "</span> " + _space;

                _str += "</li><li>";
            }
        });

        _str+="</ul>";

        if (cache.fristRun)
        {
            cache.fristRun = false;
            cache.displayCont.innerHTML = _str;
            attachSrollEvent();
        }else{
            var uls = $tag("ul",cache.displayCont);
            tiny.each(uls,function(i,node){
                node.parentNode.innerHTML = _str;
            })
        }
    }
    var attachSrollEvent = function(){

        var scrollPic_00 = new ScrollPic(id,"btn_down","btn_up",null,null);
        scrollPic_00.arrVertical  = false;
        scrollPic_00.frameSet     = 25;//显示框宽度
        scrollPic_00.pageSet      = 25; //翻页宽度
        scrollPic_00.speed          = 13; //移动速度(单位毫秒，越小越快)
        scrollPic_00.space          = 2; //每次移动像素(单位px，越大越快)
        scrollPic_00.autoPlay       = false; //自动播放
        scrollPic_00.autoPlayTime   = 6; //自动播放间隔时间(秒)
        scrollPic_00.initialize(); //初始化

    };
    var AutoUpdate = {
        state:true,
        process:null,
        start:function(interval){
            var _this = this;
            this.process = window.setInterval(
                function(){
                    if(_this.state){
                        loadQuoteData();
                    }
                },interval);
        },
        stop:function(){
            this.state = false;
        },
        clear:function(){
            var _this = this;
            if(_this.process){ window.clearInterval(_this.process);}
        }
    }
    var init = function(){
        cache.displayCont = tiny(id)[0];
        cache.interval = interval || 30000;
        // 先取数据
        loadQuoteData(true);
        AutoUpdate.start(cache.interval);
    }
    init();
}


var ggcg_arg = {
    text : "输代码、名称或拼音",
    autoSubmit : false,
    width : 202,
    header : ["选项", "代码", "名称", "类型"],
    body : [-1, 1, 4, -2],
    callback : function(a){ }
};

var ggcgSearch = function (type) {
    var url = "";
    var val = "";
    if (type == "code") {
        val = document.getElementById("ggcg_StockCode").value;
        if ((/^\d{6}$/i).test(val)) {
            url = "/executive/" + val + ".html";
        } else {
            url = "/executive/stockinfo.aspx?code=" + val;
        }
    } else {
        val = document.getElementById("ggcg_ChangeMan").value;
        if (val.replace(/\s*?/ig, "") == "输入全名") {
            alert("请先输入变动人全名");
            return;
        }
        url = "/executive/personinfo.aspx?name=" + encodeURI(val);
    }
    window.open(url);
}


var listenkey = function () {
    document.onkeydown = function (event) {
        var evt = window.event || event;
        var obj = evt.srcElement || evt.target;
        if (evt.keyCode == 13) {
            try {
                if (obj) {
                    if (obj.id == 'blogbar') {
                        sobar('blog', 'blogbar', document.forms['blog_search'].t)
                    } else if (obj.id == 'report_bar') {
                        ggzl_search('report_bar');
                    } else if (obj.id == 'ggcg_StockCode') {
                        ggcgSearch('code');
                    } else if (obj.id == 'ggcg_ChangeMan') {
                        ggcgSearch('man');
                    }
                }
            } catch (e) { }
            return false;
        }
    }
}

listenkey();

/* 跳转 */
function go(link, inputid) {
    var page = document.getElementById(inputid).value;
    if (parseInt(page) != page) {
        return;
    }
    link = link.replace('{page}', page);
    window.location.href = link;
}


/* 客户端通知 */
var ClientNoticeCache = {
    turns:1,			// 执行次数
    lastShow:new Date(),	// 上次通知时间
    interval:null
};
function ClientNotice(){
    ClientNoticeCache.interval = setInterval(
        function(){
            tiny.loadJs("/js/clientNotice.js?rt=" + Math.random(),"gb2312");
        }
        ,60000);
}
//ClientNotice();

// 获取沪深股市市场（上证股票1|股证股票2）
tiny.GetMktByCode = function(code)
{
    if (code.Length < 3)
        return "sh";
    var one = code.substr(0, 1);
    var three = code.substr(0, 3);
    if (one == "5" || one == "6" || one == "9")
    {
        return "sh";
    }
    else
    {
        if (three == "009" || three == "126" || three == "110" || three == "201" || three == "202" || three == "203" || three == "204")
        {
            return "sh";
        }
        else
        {
            return "sz";
        }
    }
}

//检测ie6
function is_ie6() {
    try {
        var browser = navigator.appName
        var b_version = navigator.appVersion
        var version = b_version.split(";");
        var trim_Version = version[1].replace(/[ ]/g, "");
        if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE6.0") {
            return true;
        }
    }
    catch (e) {
        return false;
    }
    return false;
}


//js base64编码和解码
(function (win) {

    if (win.btoa && win.atob)
        return;

    var b64chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
    var b64tab = function (bin) {
        var t = {};
        for (var i = 0, l = bin.length; i < l; i++) t[bin.charAt(i)] = i;
        return t;
    }(b64chars);

    var sub_toBase64 = function (m) {
        var n = (m.charCodeAt(0) << 16)
            | (m.charCodeAt(1) << 8)
            | (m.charCodeAt(2));
        return b64chars.charAt(n >>> 18)
            + b64chars.charAt((n >>> 12) & 63)
            + b64chars.charAt((n >>> 6) & 63)
            + b64chars.charAt(n & 63);
    };

    var toBase64 = function (bin) {
        if (bin.match(/[^\x00-\xFF]/)) throw 'unsupported character found';
        var padlen = 0;
        while (bin.length % 3) {
            bin += '\0';
            padlen++;
        };
        var b64 = bin.replace(/[\x00-\xFF]{3}/g, sub_toBase64);
        if (!padlen) return b64;
        b64 = b64.substr(0, b64.length - padlen);
        while (padlen--) b64 += '=';
        return b64;
    };


    var sub_fromBase64 = function (m) {
        var n = (b64tab[m.charAt(0)] << 18)
            | (b64tab[m.charAt(1)] << 12)
            | (b64tab[m.charAt(2)] << 6)
            | (b64tab[m.charAt(3)]);
        return String.fromCharCode(n >> 16)
            + String.fromCharCode((n >> 8) & 0xff)
            + String.fromCharCode(n & 0xff);
    };

    var fromBase64 = function (b64) {
        b64 = b64.replace(/[^A-Za-z0-9\+\/]/g, '');
        var padlen = 0;
        while (b64.length % 4) {
            b64 += 'A';
            padlen++;
        }
        var bin = b64.replace(/[A-Za-z0-9\+\/]{4}/g, sub_fromBase64);
        bin.length -= [0, 0, 2, 1][padlen];
        return bin;
    };

    win.btoa = toBase64;
    win.atob = fromBase64;

})(window);

//seo ga代码
function SeoGa()
{
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date(); a = s.createElement(o),
            m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');
    ga('create', 'UA-87414918-1', 'auto');
    ga('send', 'pageview');
}