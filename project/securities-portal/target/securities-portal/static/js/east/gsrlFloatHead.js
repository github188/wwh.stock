
var version = navigator.appVersion.split(";");
var trim_Version = version[1] ? version[1].replace(/[ ]/g, "") : "";
var isIE6 = (navigator.appName == "Microsoft Internet Explorer" && trim_Version == "MSIE6.0") ? true : false;
var isMaxthon = window.navigator.userAgent.toLowerCase().indexOf('maxthon') > 0 ? true : false;
var UA = navigator.userAgent;
var is360se = UA.toLowerCase().indexOf('360se') > -1 ? true : false;
var isIE7 = UA.toLowerCase().indexOf('msie 7.0') > -1 ? true : false;
var _id;

function CreateHead(id, tbClass, theadClass, setWidth) {
    _id = id;
    var pos = getElementPos(id);
    var divhead = document.createElement("div");
    divhead.id = "divFloatHead2";
    divhead.className = "maincont";
    divhead.style.position = "absolute";
    var headleft = is360se || (isMaxthon && !isIE6) ? (pos.x - 1) : (pos.x + 1);
    divhead.style.left = "auto";
    divhead.style.top = pos.y + 'px';
    divhead.style.zIndex = "999";
    divhead.style.display = "none";
    var tab = document.getElementById(id);
    var tabhead = tab.getElementsByTagName('thead')[0];
    divhead.innerHTML = '<table id="divFloatHead" cellpadding="0" cellspacing="0" class="' + tbClass + '" style="position:fixed;top:0;display:none;z-index:999;"><thead class="' + theadClass + '">' + tabhead.innerHTML + '</thead></table>';
    var insertPos = document.getElementById("float_head");
    insertPos.innerHTML = divhead.innerHTML;

    //设置浮动表头列的宽度
    SetWidth(_id);
}

function SetWidth(id) {
    if (id) {
        var tab = document.getElementById(id);
        var tabhead = tab.getElementsByTagName('thead')[0];
        //表格头部行总数
        var thead_tr_length = tabhead.getElementsByTagName("tr").length;

        for (var i = 0; i < thead_tr_length; i++) {

            var oldth = tabhead.getElementsByTagName("tr")[i].getElementsByTagName("th");
            var newth = document.getElementById("divFloatHead").getElementsByTagName("thead")[0].getElementsByTagName("tr")[i].getElementsByTagName("th");

            for (var k = 0; k < oldth.length && newth.length; k++) {
                if (isIE6) {
                    newth[k].style.width = oldth[k].clientWidth + "px"
                } else {
                    newth[k].style.width = jQuery(oldth[k]).width() + "px"
                }

            }
        }

    }
    if (document.getElementById("divFloatHead") && document.getElementById("dt_1")) {
        document.getElementById("divFloatHead").style.width = document.getElementById("dt_1").clientWidth + "px";
    }

}


var winWidth = 0;
var winHeight = 0;
function findDimensions() //函数：获取尺寸
{
    //获取窗口宽度
    if (window.innerWidth)
        winWidth = window.innerWidth;
    else if ((document.body) && (document.body.clientWidth))
        winWidth = document.body.clientWidth;
    //获取窗口高度
    if (window.innerHeight)
        winHeight = window.innerHeight;
    else if ((document.body) && (document.body.clientHeight))
        winHeight = document.body.clientHeight;

    //通过深入Document内部对body进行检测，获取窗口大小
    if (document.documentElement && document.documentElement.clientHeight &&
        document.documentElement.clientWidth) {
        winHeight = document.documentElement.clientHeight;
        winWidth = document.documentElement.clientWidth;
    }
    SetFloatHeat();
}

window.onload = findDimensions;
window.onresize = findDimensions;

window.onscroll = SetFloatHeat;

function SetFloatHeat() {
    var sh = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    var divFloatHead_ele = document.getElementById('divFloatHead');
    var pos = getElementPos(_id);
    var i = 0;

    if (sh >= pos.y && divFloatHead_ele) {
        if (isIE6) {
            divFloatHead_ele.style.display = "block";
            divFloatHead_ele.style.position = "absolute";
            var top = "0px";
            if (document.getElementById("alllink")) {
                top = document.documentElement.scrollTop + 35 + "px";
            } else {
                top = document.documentElement.scrollTop + 0 + "px";
            }
            divFloatHead_ele.style.top = top;
        }
        else {
            divFloatHead_ele.style.display = "block";
            divFloatHead_ele.style.position = "fixed";
            divFloatHead_ele.style.top = "0px";
        }

        if (winWidth < 965) {
            if (isIE6) {
                if (divFloatHead_ele.className == "tab1") {
                    divFloatHead_ele.style.left = "auto";
                    //divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft + 1000 || -document.documentElement.scrollLeft + 1000) / 1000 + "px";
                }else {
                    divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft + 1000 || -document.documentElement.scrollLeft + 1000) / 1000 + "px";
                }
            } else if (isIE7) {
                divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft || -document.documentElement.scrollLeft) - 2 + "px";
            }else {
                if (divFloatHead_ele.className == "tab1") {
                    divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft || -document.documentElement.scrollLeft) + "px";
                }
                else {
                    divFloatHead_ele.style.left = (-document.body.scrollLeft || -document.documentElement.scrollLeft) + "px";
                }
            }
        }
        else {
            //divFloatHead_ele.style.left = "auto";
            if (isIE6) {
                divFloatHead_ele.style.left = "auto";
                //divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft + 1000 || -document.documentElement.scrollLeft + 1000) / 1000 + "px";
            } else if (isIE7) {
                divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft || -document.documentElement.scrollLeft) - 2 + "px";
            } else {
                divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft || -document.documentElement.scrollLeft) + "px";
            }
        }
    }
    else if (divFloatHead_ele) {
        divFloatHead_ele.style.display = "none";
    }

    //if (isIE6 || isIE7) {
    //    divFloatHead_ele.style.left = pos.x + (-document.body.scrollLeft || -document.documentElement.scrollLeft) - 2 + "px";
    //}
    //设置浮动表头列的宽度
    SetWidth(_id);
}

function getElementPos(elementId) {
    var ua = navigator.userAgent.toLowerCase();
    var isOpera = (ua.indexOf('opera') != -1);
    var isIE = (ua.indexOf('msie') != -1 && !isOpera); // not opera spoof
    var el = document.getElementById(elementId);
    if (el.parentNode === null || el.style.display == 'none') {
        return false;
    }
    var parent = null;
    var pos = [];
    var box;
    if (el.getBoundingClientRect)    //IE
    {
        box = el.getBoundingClientRect();
        var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
        var scrollLeft = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft);
        return { x: box.left + scrollLeft, y: box.top + scrollTop };
    } else if (document.getBoxObjectFor)    // gecko    
    {
        box = document.getBoxObjectFor(el);
        var borderLeft = (el.style.borderLeftWidth) ? parseInt(el.style.borderLeftWidth) : 0;
        var borderTop = (el.style.borderTopWidth) ? parseInt(el.style.borderTopWidth) : 0;
        pos = [box.x - borderLeft, box.y - borderTop];
    } else    // safari & opera    
    {
        pos = [el.offsetLeft, el.offsetTop];
        parent = el.offsetParent;
        if (parent != el) {
            while (parent) {
                pos[0] += parent.offsetLeft;
                pos[1] += parent.offsetTop;
                parent = parent.offsetParent;
            }
        }
        if (ua.indexOf('opera') != -1 || (ua.indexOf('safari') != -1 && el.style.position == 'absolute')) {
            pos[0] -= document.body.offsetLeft;
            pos[1] -= document.body.offsetTop;
        }
    }
    if (el.parentNode) {
        parent = el.parentNode;
    } else {
        parent = null;
    }
    while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML') { // account for any scrolled ancestors
        pos[0] -= parent.scrollLeft;
        pos[1] -= parent.scrollTop;
        if (parent.parentNode) {
            parent = parent.parentNode;
        } else {
            parent = null;
        }
    }
    return { x: pos[0], y: pos[1] };
}


function isMaxthon() {
    try {
        window.external.max_version;
        return true;
    }
    catch (e) {
        return false;
    }
}


function maxthonVersion() {
    if (window.external && window.external.max_version) {
        return window.external.max_version.substr(0, 1);
    }
    return undefined;
}