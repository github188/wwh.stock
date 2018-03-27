function Marquee()
{
    this.ID = document.getElementById(arguments[0]);
    if(!this.ID)
    {
        this.ID = -1;
        return;
    }
    this.Direction = this.Width = this.Height = this.DelayTime = this.WaitTime = this.CTL = this.StartID = this.Stop = this.MouseOver = 0;
    this.Step = 1;
    this.Timer = 30;
    this.DirectionArray = {"top":0 , "up":0 , "bottom":1 , "down":1 , "left":2 , "right":3};
    if(typeof arguments[1] == "number" || typeof arguments[1] == "string")this.Direction = arguments[1];
    if(typeof arguments[2] == "number")this.Step = arguments[2];
    if(typeof arguments[3] == "number")this.Width = arguments[3];
    if(typeof arguments[4] == "number")this.Height = arguments[4];
    if(typeof arguments[5] == "number")this.Timer = arguments[5];
    if(typeof arguments[6] == "number")this.DelayTime = arguments[6];
    if(typeof arguments[7] == "number")this.WaitTime = arguments[7];
    if(typeof arguments[8] == "number")this.ScrollStep = arguments[8];
    this.ID.style.overflow = this.ID.style.overflowX = this.ID.style.overflowY = "hidden";
    this.ID.noWrap = true;
    this.IsNotOpera = (navigator.userAgent.toLowerCase().indexOf("opera") == -1);
    if(arguments.length >= 7)this.Start();
}

Marquee.prototype.Start = function()
{
    if(this.ID == -1)return;
    if(this.WaitTime < 800)this.WaitTime = 800;
    if(this.Timer < 20)this.Timer = 20;
    if(this.Width == 0)this.Width = parseInt(this.ID.style.width);
    if(this.Height == 0)this.Height = parseInt(this.ID.style.height);
    if(typeof this.Direction == "string")this.Direction = this.DirectionArray[this.Direction.toString().toLowerCase()];
    this.HalfWidth = Math.round(this.Width / 2);
    this.HalfHeight = Math.round(this.Height / 2);
    this.BakStep = this.Step;
    this.ID.style.width = this.Width + "px";
    this.ID.style.height = this.Height + "px";
    if(typeof this.ScrollStep != "number")this.ScrollStep = this.Direction > 1 ? this.Width : this.Height;
    var templateLeft = "<table cellspacing='0' cellpadding='0' style='border-collapse:collapse;display:inline;'><tr><td noWrap=true style='white-space: nowrap;word-break:keep-all;'>MSCLASS_TEMP_HTML</td><td noWrap=true style='white-space: nowrap;word-break:keep-all;'>MSCLASS_TEMP_HTML</td></tr></table>";
    var templateTop = "<table cellspacing='0' cellpadding='0' style='border-collapse:collapse;'><tr><td>MSCLASS_TEMP_HTML</td></tr><tr><td>MSCLASS_TEMP_HTML</td></tr></table>";
    var msobj = this;
    msobj.tempHTML = msobj.ID.innerHTML;
    if(msobj.Direction <= 1)
    {
        msobj.ID.innerHTML = templateTop.replace(/MSCLASS_TEMP_HTML/g,msobj.ID.innerHTML);
    }
    else
    {
        if(msobj.ScrollStep == 0 && msobj.DelayTime == 0)
        {
            msobj.ID.innerHTML += msobj.ID.innerHTML;
        }
        else
        {
            msobj.ID.innerHTML = templateLeft.replace(/MSCLASS_TEMP_HTML/g,msobj.ID.innerHTML);
        }
    }
    var timer = this.Timer;
    var delaytime = this.DelayTime;
    var waittime = this.WaitTime;
    msobj.StartID = function(){msobj.Scroll();}
    msobj.Continue = function()
    {
        if(msobj.MouseOver == 1)
        {
            setTimeout(msobj.Continue,delaytime);
        }
        else
        {	clearInterval(msobj.TimerID);
            msobj.CTL = msobj.Stop = 0;
            msobj.TimerID = setInterval(msobj.StartID,timer);
        }
    }

    msobj.Pause = function()
    {
        msobj.Stop = 1;
        clearInterval(msobj.TimerID);
        setTimeout(msobj.Continue,delaytime);
    }

    msobj.Begin = function()
    {
        msobj.ClientScroll = msobj.Direction > 1 ? msobj.ID.scrollWidth / 2 : msobj.ID.scrollHeight / 2;
        if((msobj.Direction <= 1 && msobj.ClientScroll <= msobj.Height + msobj.Step) || (msobj.Direction > 1 && msobj.ClientScroll <= msobj.Width + msobj.Step))			{
            msobj.ID.innerHTML = msobj.tempHTML;
            delete(msobj.tempHTML);
            return;
        }
        delete(msobj.tempHTML);
        msobj.TimerID = setInterval(msobj.StartID,timer);
        if(msobj.ScrollStep < 0)return;
        msobj.ID.onmousemove = function(event)
        {
            if(msobj.ScrollStep == 0 && msobj.Direction > 1)
            {
                var event = event || window.event;
                if(window.event)
                {
                    if(msobj.IsNotOpera)
                    {
                        msobj.EventLeft = event.srcElement.id == msobj.ID.id ? event.offsetX - msobj.ID.scrollLeft : event.srcElement.offsetLeft - msobj.ID.scrollLeft + event.offsetX;
                    }
                    else
                    {
                        msobj.ScrollStep = null;
                        return;
                    }
                }
                else
                {
                    msobj.EventLeft = event.layerX - msobj.ID.scrollLeft;
                }
                msobj.Direction = msobj.EventLeft > msobj.HalfWidth ? 3 : 2;
                msobj.AbsCenter = Math.abs(msobj.HalfWidth - msobj.EventLeft);
                msobj.Step = Math.round(msobj.AbsCenter * (msobj.BakStep*2) / msobj.HalfWidth);
            }
        }
        msobj.ID.onmouseover = function()
        {
            if(msobj.ScrollStep == 0)return;
            msobj.MouseOver = 1;
            clearInterval(msobj.TimerID);
        }
        msobj.ID.onmouseout = function()
        {
            if(msobj.ScrollStep == 0)
            {
                if(msobj.Step == 0)msobj.Step = 1;
                return;
            }
            msobj.MouseOver = 0;
            if(msobj.Stop == 0)
            {
                clearInterval(msobj.TimerID);
                msobj.TimerID = setInterval(msobj.StartID,timer);
            }
        }
    }
    setTimeout(msobj.Begin,waittime);
}

Marquee.prototype.Scroll = function()
{
    switch(this.Direction)
    {
        case 0:
            this.CTL += this.Step;
            if(this.CTL >= this.ScrollStep && this.DelayTime > 0)
            {
                this.ID.scrollTop += this.ScrollStep + this.Step - this.CTL;
                this.Pause();
                return;
            }
            else
            {
                if(this.ID.scrollTop >= this.ClientScroll)
                {
                    this.ID.scrollTop -= this.ClientScroll;
                }
                this.ID.scrollTop += this.Step;
            }
            break;

        case 1:
            this.CTL += this.Step;
            if(this.CTL >= this.ScrollStep && this.DelayTime > 0)
            {
                this.ID.scrollTop -= this.ScrollStep + this.Step - this.CTL;
                this.Pause();
                return;
            }
            else
            {
                if(this.ID.scrollTop <= 0)
                {
                    this.ID.scrollTop += this.ClientScroll;
                }
                this.ID.scrollTop -= this.Step;
            }
            break;

        case 2:
            this.CTL += this.Step;
            if(this.CTL >= this.ScrollStep && this.DelayTime > 0)
            {
                this.ID.scrollLeft += this.ScrollStep + this.Step - this.CTL;
                this.Pause();
                return;
            }
            else
            {
                if(this.ID.scrollLeft >= this.ClientScroll)
                {
                    this.ID.scrollLeft -= this.ClientScroll;
                }
                this.ID.scrollLeft += this.Step;
            }
            break;

        case 3:
            this.CTL += this.Step;
            if(this.CTL >= this.ScrollStep && this.DelayTime > 0)
            {
                this.ID.scrollLeft -= this.ScrollStep + this.Step - this.CTL;
                this.Pause();
                return;
            }
            else
            {
                if(this.ID.scrollLeft <= 0)
                {
                    this.ID.scrollLeft += this.ClientScroll;
                }
                this.ID.scrollLeft -= this.Step;
            }
            break;
    }
}
jQuery.noConflict();
jQuery.fn.Tabs = function (options) {
    var defaults = {
        tabSelector: ".tabs li",
        conSelector: ".tabcontent",
        focusClass: "c",
        moreLink: { trigger: "", links: [] },
        events: "mouseover",
        delay: 0.2
    };
    var events = ["mouseover", "click"];
    var settings = jQuery.extend({}, defaults, options);
    var that = this;
    var _tabs = jQuery(settings.tabSelector, that);
    var _cons = jQuery(settings.conSelector, that);
    var _isDelay = settings.events == events[0] ? !0 : !1;
    _tabs.each(function (i, v) {
        jQuery(v).on(settings.events, function () {
            var _this = this;
            delay.apply(this, [settings.delay, function () {
                jQuery(_this).addClass(settings.focusClass);
                jQuery(_this).siblings(settings.tabSelector).removeClass(settings.focusClass);
                jQuery(_cons[i]).fadeIn();
                jQuery(_cons[i]).siblings(settings.conSelector).hide();
                if (settings.moreLink.links && settings.moreLink.links.length > 0) {
                    jQuery(that).find(settings.moreLink.trigger).attr("href", settings.moreLink.links[i]);
                }
            }, _isDelay])
        });
    });
    //接收两个参数 t延迟时间秒为单位，fn要执行的函数,m是否执行延迟取决于事件的类型
    var delay = function (t, fn, m) {
        if (m) {
            var _this = this,
                d = setInterval(function () {
                    fn.apply(_this);
                }, t * 1000);
            _this.onmouseout = function () {
                clearInterval(d);
            };
        }
        else fn.apply(this);
    }
}
; (function ($) {
    var StockZL = {
        init: function () {
            StockZL.bindTabs();
        },
        //绑定Tab切换标签
        bindTabs: function () {
            $("#tabs").Tabs({ tabSelector: ".tabs li", conSelector: ".listdata", focusClass: "c" });
        }
    };
    StockZL.init();
})(jQuery);


function SetHome(obj, url) {
    try {
        obj.style.behavior = 'url(#default#homepage)';
        obj.setHomePage(url);
    } catch (e) {
        if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
            }
        } else {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
        }
    }
}

function AddFavorite(title, url) {
    try {
        window.external.addFavorite(url, title);
    }
    catch (e) {
        try {
            window.sidebar.addPanel(title, url, "");
        }
        catch (e) {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请进入新网站后使用Ctrl+D进行添加");
        }
    }
}