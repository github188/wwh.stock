function Drag() {
    //初始化
    this.initialize.apply(this, arguments)
}
var startx, starty;
var path = Man.context + "/3rd/Jcorp";
Drag.prototype = {
    //初始化
    initialize: function (drag, options) {
        this.drag = this.$(drag);
        this._x = this._y = 0;
        this._moveDrag = this.bind(this, this.moveDrag);
        this._stopDrag = this.bind(this, this.stopDrag);

        this.setOptions(options);

        this.handle = this.$(this.options.handle);
        this.maxContainer = this.$(this.options.maxContainer);

        this.maxTop = Math.max(this.maxContainer.clientHeight, this.maxContainer.scrollHeight) - this.drag.offsetHeight;
        this.maxLeft = Math.max(this.maxContainer.clientWidth, this.maxContainer.scrollWidth) - this.drag.offsetWidth;

        this.limit = this.options.limit;
        this.lockX = this.options.lockX;
        this.lockY = this.options.lockY;
        this.lock = this.options.lock;

        this.lockminX = this.options.lockminX;
        this.lockminY = this.options.lockminY;
        this.lockmaxX = this.options.lockmaxX;
        this.lockmaxY = this.options.lockmaxY;

        this.labelheight = this.options.labelheight;
        this.labelwidth = this.options.labelwidth;
        this.imgwidth = this.options.imgwidth;
        this.imgheight = this.options.imgheight;
        this.imgleft = this.options.imgleft;
        this.imgtop = this.options.imgtop;

        this.onStart = this.options.onStart;
        this.onMove = this.options.onMove;
        this.onStop = this.options.onStop;

        this.handle.style.cursor = "move";

        this.changeLayout();

        this.addHandler(this.handle, "mousedown", this.bind(this, this.startDrag))
    },
    changeLayout: function () {
        this.drag.style.top = this.drag.offsetTop + "px";
        this.drag.style.left = this.drag.offsetLeft + "px";
        this.drag.style.position = "absolute";
        this.drag.style.margin = "0"
    },
    startDrag: function (event) {
        var event = event || window.event;
        startx = this.drag.offsetLeft;
        starty = this.drag.offsetTop;
        this._x = event.clientX - this.drag.offsetLeft;
        this._y = event.clientY - this.drag.offsetTop;

        this.addHandler(document, "mousemove", this._moveDrag);
        this.addHandler(document, "mouseup", this._stopDrag);

        event.preventDefault && event.preventDefault();
        this.handle.setCapture && this.handle.setCapture();

        this.onStart()
    },
    moveDrag: function (event) {
        var event = event || window.event;

        var iTop = event.clientY - this._y;
        var iLeft = event.clientX - this._x;

        // todo
        this.lockmaxX = this.imgwidth - $(this.drag).width() + this.lockminX;

        var dragleft = iLeft;
        var dragtop = iTop;
        if (this.lock) return;

        this.limit && (iTop < 0 && (iTop = 0), iLeft < 0 && (iLeft = 0), iTop > this.maxTop && (iTop = this.maxTop), iLeft > this.maxLeft && (iLeft = this.maxLeft));

        if (this.lockminX > iLeft) {
            this.drag.style.left = this.lockminX + "px";
            $($($(this.drag).children()[0]).children()[0]).attr("src", path + "/images/left1.png");
            $($($(this.drag).children()[2]).children()[0]).attr("src", path + "/images/right1.png");
            $($(this.drag).children()[8]).val("2");
        } else if (this.lockmaxX < iLeft) {
            this.drag.style.left = this.lockmaxX + "px";
            $($($(this.drag).children()[0]).children()[0]).attr("src", path + "/images/left2.png");
            $($($(this.drag).children()[2]).children()[0]).attr("src", path + "/images/right2.png");
            $($(this.drag).children()[8]).val("1");
        } else {
            this.drag.style.left = iLeft + "px";
        }

        if (this.lockminY > iTop) {
            this.drag.style.top = this.lockminY + "px";
        } else if (this.lockmaxY < iTop) {
            this.drag.style.top = this.lockmaxY + "px";
        } else {
            this.drag.style.top = iTop + "px";
        }

        var pointx, pointy;
        // X坐标过界调整
        if (dragleft < this.imgleft) {
            dragleft = this.imgleft;
        } else if (dragleft + this.labelwidth > this.imgleft + this.imgwidth) {
            dragleft = this.imgleft + this.imgwidth - this.labelwidth;
        }

        // Y坐标过界调整
        if (dragtop + this.labelheight < this.imgtop) {
            dragtop = this.imgtop + this.labelheight;
        } else if (dragtop + this.labelheight > this.imgtop + this.imgheight) {
            dragtop = this.imgtop + this.imgheight - this.labelheight;
        }

        pointx = (dragleft - this.imgleft) / this.imgwidth;
        pointy = (dragtop + labelheight - this.imgtop) / this.imgheight;

        //var maxpointy =  (this.imgwidth - this.labelwidth) / this.imgwidth;
        //if (pointx > maxpointx) {
        //    pointx = maxpointx;
        //}

        $($(this.drag).children()[6]).val(pointx);
        $($(this.drag).children()[7]).val(pointy);

        //this.lockY || (this.drag.style.top = iTop + "px");
        //this.lockX || (this.drag.style.left = iLeft + "px");

        event.preventDefault && event.preventDefault();

        this.onMove()
    },
    stopDrag: function () {

        //if (this.drag.offsetLeft == startx && this.drag.offsetTop == starty) {
        //    if (confirm("确定删除标签吗?")) {
        //        $(this.drag).remove();
        //    }
        //}
        this.removeHandler(document, "mousemove", this._moveDrag);
        this.removeHandler(document, "mouseup", this._stopDrag);

        this.handle.releaseCapture && this.handle.releaseCapture();

        this.onStop()
    },
    //参数设置
    setOptions: function (options) {
        this.options =
        {
            handle: this.drag, //事件对象
            limit: true, //锁定范围
            lock: false, //锁定位置
            lockX: false, //锁定水平位置
            lockY: false, //锁定垂直位置
            lockminX: 0,
            lockminY: 0,
            lockmaxX: 0,
            lockmaxY: 0,
            imgheight: 0,
            imgwidth: 0,
            labelheight: 0,
            labelwidth: 0,
            imgtop: 0,
            imgleft: 0,
            maxContainer: document.documentElement || document.body, //指定限制容器
            onStart: function () {
            }, //开始时回调函数
            onMove: function () {
            }, //拖拽时回调函数
            onStop: function () {
            }  //停止时回调函数
        };
        for (var p in options) this.options[p] = options[p]
    },
    //获取id
    $: function (id) {
        return typeof id === "string" ? document.getElementById(id) : id
    },
    //添加绑定事件
    addHandler: function (oElement, sEventType, fnHandler) {
        return oElement.addEventListener ? oElement.addEventListener(sEventType, fnHandler, false) : oElement.attachEvent("on" + sEventType, fnHandler)
    },
    //删除绑定事件
    removeHandler: function (oElement, sEventType, fnHandler) {
        return oElement.removeEventListener ? oElement.removeEventListener(sEventType, fnHandler, false) : oElement.detachEvent("on" + sEventType, fnHandler)
    },
    //绑定事件到对象
    bind: function (object, fnHandler) {
        return function () {
            return fnHandler.apply(object, arguments)
        }
    }
};