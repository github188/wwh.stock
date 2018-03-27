//板块资金流弹出层
var Class = {
    create: function () {
        return function () { this.initialize.apply(this, arguments) }
    }
}

Object.extend = function (destination, source) {
    for (property in source) {
        destination[property] = source[property]
    }
    return destination;
};
var BkzjlxPop = Class.create();
Object.extend(
    BkzjlxPop.prototype, {
        // 初始化
        initialize: function (opt) {
            var _t = this;
            Object.extend(_t.options, opt || {});
            _t.popDiv();
        },
        options: {
            obj: "selector",
            objClose: "close",
            objPop: "pop"
        },
        popDiv: function () {
            var _this = this;
            var objId = document.getElementById(_this.options.obj);
            var objClose = document.getElementById(_this.options.objClose);
            var objPop = document.getElementById(_this.options.objPop);
            var links = objPop.getElementsByTagName("a");
            var _this = this;
            var hidePop = function () {
                objPop.style.display = "none";
            }
            var showPop = function () {
                objPop.style.display = "";
            }
            var hoverIt = function (obj, type) {
                if (type == "over") {
                    if (!_this.tools.hasClass(obj, "hover")) {
                        _this.tools.addClass(obj, "hover");
                    }
                }
                else {
                    _this.tools.removeClass(obj, "hover");
                }
            }
            _this.tools.addEvent(objId, "click", showPop);
            _this.tools.addEvent(objClose, "click", hidePop);
            for (var i = 0, len = links.length; i < len; i++) {
                _this.tools.addEvent(links[i], "mouseover", hoverIt.bind(this, links[i], "over"));
                _this.tools.addEvent(links[i], "mouseout", hoverIt.bind(this, links[i], "out"));
                _this.tools.addEvent(links[i], "click", hidePop);
            }
        },
        tools: {
            addEvent: function (elem, eType, fn, useCapture) {
                if (elem.addEventListener) {
                    elem.addEventListener(eType, fn, useCapture);
                    return true;
                } else if (elem.attachEvent) {
                    var r = elem.attachEvent('on' + eType, fn);
                    return r;
                } else {
                    elem['on' + eType] = fn;
                }
            },
            getArrayOfClassNames: function (element) {
                var classNames = [];
                if (element.className) {
                    classNames = element.className.split(' ');
                }
                return classNames;
            },
            addClass: function (element, className) {
                var classNames = this.getArrayOfClassNames(element);
                classNames.push(className);
                element.className = classNames.join(' ');
            },
            removeClass: function (element, className) {
                var classNames = this.getArrayOfClassNames(element);
                var resultingClassName = [];
                for (var index = 0; index < classNames.length; index++) {
                    if (className != classNames[index]) {
                        resultingClassName.push(classNames[index]);
                    }
                }
                if (resultingClassName.length == 0) {
                    element.className = null;
                }
                else {
                    element.className = resultingClassName.join(' ');
                }
            },
            hasClass: function (element, className) {
                var isClassNamePresent = false;
                var classNames = this.getArrayOfClassNames(element);
                for (var index = 0; index < classNames.length; index++) {
                    if (className == classNames[index]) {
                        isClassNamePresent = true;
                    }
                }
                return isClassNamePresent;
            }
        }
    }
);
Function.prototype.bind = function () {
    var __m = this, object = arguments[0], args = new Array();
    for (var i = 1; i < arguments.length; i++) {
        args.push(arguments[i]);
    }
    return function () {
        return __m.apply(object, args);
    }
};