if (!(window.NavCache)){
    window.NavCache = {};
};
/*
 NavCache.atli		当前页名称
 NavCache.Page		当前页名称

 NavCache.tabObj		类别li
 NavCache.tabNum		类别li位置
 NavCache.tabDef		默认选中的类别li
 NavCache.tabCont	默认显示cont对象

 NavCache.contObj	页面li
 NavCache.contNum	页面li位置
 NavCache.contDef	默认选中的页面li(当前页面)
 */
function initMenu() {
    if (!NavCache.current_n || !(/^\d$/.test(NavCache.current_n)))
        NavCache.current_n = 0;
    var elment_b = $("centerNavTab").getElementsByTagName("li");
    tiny.addClass(elment_b[NavCache.current_n], "at");
    initNavType();
    initGetMore();

    eventReset();

    document.getElementById("centerNavArea").onmouseout = function(e){
        if(checkHover(e,this))
        {
            rollback();
        }

    }
}

// 回滚操作
function rollback(){
    tabReset();
    contReset();
    //eventReset();
}

// 页面li回滚操作
function tabReset(){
    if(NavCache.tabObj){
        tiny.removeClass(NavCache.tabObj,"at");
        getbyid("centerNavCont" + NavCache.tabNum).style.display = "none";
    }
    if(NavCache.tabDef){
        tiny.addClass(NavCache.tabDef,"at");
        NavCache.tabCont.style.display = "block";
        NavCache.tabObj = NavCache.tabDef;
        NavCache.tabNum = NavCache.defNum;

    }
}

// 页面li回滚操作
function contReset(){
    if(NavCache.contObj){
        if(tiny.hasClass(NavCache.contObj,"endat")){
            tiny.removeClass(NavCache.contObj,"endat");
            tiny.addClass(NavCache.contObj,"end");
        }else{
            tiny.removeClass(NavCache.contObj,"at");
        }

    }
    if(NavCache.contDef){
        if(tiny.hasClass(NavCache.contDef,"end")){
            tiny.removeClass(NavCache.contDef,"end");
            tiny.addClass(NavCache.contDef,"endat");
        }else{
            tiny.addClass(NavCache.contDef,"at");
        }
        NavCache.contObj = NavCache.contDef;
    }
}

function eventReset(){
    tabSlider();
    contSlider();

}

function resetMenu(){
    contReset();
    contSlider();
}
function getbyid(id)
{
    return typeof id=="string" ? document.getElementById(id) : id;
}

/* 默认选中 */
function initNavType(){
    var tab = getbyid("centerNavTab"),
        tab_lis = tab.getElementsByTagName("li"),
        cursor = 1;		// 默认为第二项

    for(var i=0;i<tab_lis.length;i++){
        if(tiny.hasClass(tab_lis[i],"at")){
            NavCache.tabDef =  tab_lis[i];
            cursor = i;
            break;
        }
    }

    var cont = getbyid("centerNavCont" + (cursor+1)),
        cont_lis = cont.getElementsByTagName("li");
    cont.style.display ="block";

    NavCache.tabCont = cont;

    for(var i=0;i<cont_lis.length;i++){
        var val = tiny.getTxt(cont_lis[i]);
        if (val == NavCache.Page) {
            obj = cont_lis[i];
            if (tiny.hasClass(obj, "end")) {
                tiny.removeClass(obj, "end");
                tiny.addClass(obj, "endat");
            } else {
                tiny.addClass(obj, "at");
            }
            NavCache.contDef = obj;
            break;
        }
    }
}

// 重写mouseover和mouseout
// 在子元素见切换时不触发事件
function checkHover(e,target){
    if(getEvent(e).type=="mouseover")
        return !contains(target,getEvent(e).relatedTarget||getEvent(e).fromElement) && !((getEvent(e).relatedTarget||getEvent(e).fromElement)===target);
    else
    {
        return !contains(target,getEvent(e).relatedTarget||getEvent(e).toElement) && !((getEvent(e).relatedTarget||getEvent(e).toElement)===target);
    }
}
function getEvent(e){
    return e||window.event;
}

// 判断父元素是否包含子元素
function contains(parentNode,childNode){
    return parentNode.contains ? parentNode != childNode && parentNode.contains(childNode) : !!(parentNode.compareDocumentPosition(childNode) & 16);
}

// 显示更多
function initGetMore(){
    var obj = document.getElementById("getMoreMenu");
    var box = document.getElementById("getMoreListCont");
    if(obj && box){
        obj.onclick = box.onmouseover = function(){
            tiny.removeClass(box,"hidden");
        }
        box.onmouseout = obj.onmouseout = function(){
            tiny.addClass(box,"hidden");
        }
        box.onmouseover = function(){
            tiny.removeClass(box,"hidden");
        }
    }
}



function stopBubble(e) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if ( e && e.stopPropagation )
    //因此它支持W3C的stopPropagation()方法
        e.stopPropagation();
    else
    //否则，我们需要使用IE的方式来取消事件冒泡
        window.event.cancelBubble = true;
}


// 重写tab切换
// 防止多次绑定事件带来的性能下降
function tabSlider(){
    var classname = "at";
    var showObj = "centerNavCont";
    var	eventType = "mouseover";
    var elements = getbyid("centerNavTab").getElementsByTagName("li");
    var slidingChg = function(obj,num){
        var tempObj = obj;
        while(tempObj.nodeName != "LI"){
            tempObj = tempObj.parentNode;
        }
        if (NavCache.tabObj==null || NavCache.tabObj != tempObj){
            if(NavCache.tabObj){
                tiny.removeClass(NavCache.tabObj,classname);
            }
            if(showObj!=""){
                if(NavCache.tabObj){
                    document.getElementById(showObj + parseInt(NavCache.tabNum)).style.display = "none";
                }
                document.getElementById(showObj + parseInt(num)).style.display = "block";
            }
            tiny.addClass(tempObj,classname);

            NavCache.tabObj = tempObj;
            NavCache.tabNum = num;

            resetMenu();
        }
    };
    for(var i = 0; i < elements.length; i++){
        var fn = function(obj, num, e){
            slidingChg(obj, parseInt(num) + 1);
        };
        tiny.addEvent(elements[i], eventType, fn.binding(this,elements[i],i), false);
        if(tiny.hasClass(elements[i],classname)){
            NavCache.tabObj = elements[i];
            NavCache.tabNum = parseInt(i)+1;
            NavCache.defNum = parseInt(i)+1;
        }
    }
}


// 重写tab切换
// 防止多次绑定事件带来的性能下降
function contSlider(){
    var classname = "at";
    var	eventType = "mouseover";
    var elements = getbyid("centerNavLi").getElementsByTagName("li");
    var slidingChg = function(obj,num){
        var tempObj = obj;
        while(tempObj.nodeName != "LI"){
            tempObj = tempObj.parentNode;
        }
        if (NavCache.contObj==null || NavCache.contObj != tempObj){
            // 移除
            if(NavCache.contObj){
                if(tiny.hasClass(NavCache.contObj,"endat")){
                    tiny.removeClass(NavCache.contObj,"endat");
                    tiny.addClass(NavCache.contObj,"end");
                }else{
                    tiny.removeClass(NavCache.contObj,"at");
                }

            }
            // 选中
            if(tiny.hasClass(tempObj,"end")){
                tiny.removeClass(tempObj,"end");
                tiny.addClass(tempObj,"endat");
            }else{
                tiny.addClass(tempObj,"at");
            }

            NavCache.contObj = tempObj;
            NavCache.contNum = num;
        }
    };
    for(var i = 0; i < elements.length; i++){
        var fn = function(obj, num, e){
            slidingChg(obj, parseInt(num) + 1);
        };
        tiny.addEvent(elements[i], eventType, fn.binding(this,elements[i],i), false);
        if(tiny.hasClass(elements[i],classname) || tiny.hasClass(elements[i],"endat")){
            NavCache.contObj = elements[i];
            NavCache.contNum = parseInt(i)+1;
        }
    }
}
