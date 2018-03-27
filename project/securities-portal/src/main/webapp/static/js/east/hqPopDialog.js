var globalTimeoutHover, globalTimeoutHoverOut;
function initDrawPopHq() {
    initPopDom();
    jQuery(document).on("mouseover", ".hqPopCls", function (event) {
        var originCode = jQuery(this).attr("data_code");
        var code = getCodeWithMarket(jQuery(this).attr("data_code"));
        var name = jQuery(this).attr("data_name");
        var left = jQuery(this).offset().left + jQuery(this).width()+5;
        var top = jQuery(this).offset().top - 20;
        if (!!globalTimeoutHover) {
            clearTimeout(globalTimeoutHover);
        }
        globalTimeoutHover = setTimeout(function () {
            var option = {};
            option.code = code;
            option.name = name;
            option.originCode = originCode;

            if ((left + 600) > jQuery(window).width()) {
                jQuery(".hqPop .arrow").removeClass("arrowTop").addClass("arrowTopRight");
                jQuery(".hqPop").css({ "left": left-675, "top": top });
            } else {
                jQuery(".hqPop .arrow").removeClass("arrowTopRight").addClass("arrowTop");
                jQuery(".hqPop").css({ "left": left, "top": top });
            }

            if ((top + 583) > (jQuery(window).height() + jQuery(window).scrollTop())) {
                jQuery(".hqPop").css({ "top": jQuery(window).height() + jQuery(window).scrollTop()+20 - 583 });
                jQuery(".hqPop .arrow").css({ "top": (top + 583 - jQuery(window).height() - jQuery(window).scrollTop()) })
            } else {
                jQuery(".hqPop").css({ "top": top });
                jQuery(".hqPop .arrow").css({ "top": "20px" })
            }
            jQuery(".hqPop").show();
            drawPopHq(option);
            //event.stopPropagation();
        }, 400)
    });
    jQuery(document).on("mouseout", ".hqPopCls", function (event) {
        if (!!globalTimeoutHoverOut) {
            clearTimeout(globalTimeoutHoverOut);
        }
        globalTimeoutHoverOut = setTimeout(function () {
            if (jQuery(".hqPop").hasClass("cursorHover")) {
            } else {
                jQuery(".hqPop").hide();
                // event.stopPropagation();
            }
        }, 400)
    });
    //jQuery(".hqPopCls").off("hover").hover(function (event) {
    //    var code = getCodeWithMarket(jQuery(this).attr("data_code"));
    //    var name = jQuery(this).attr("data_name");
    //    var left = jQuery(this).offset().left + 55;
    //    var top = jQuery(this).offset().top - 20;
    //    if (!!globalTimeoutHover) {
    //        clearTimeout(globalTimeoutHover);
    //    }
    //    globalTimeoutHover = setTimeout(function () {
    //        var option = {};
    //        option.code = code;
    //        option.name = name;
    //        drawPopHq(option);
    //        jQuery(".hqPop").css({ "left": left, "top": top });
    //        jQuery(".hqPop").show();
    //        //event.stopPropagation();
    //    }, 400)
    //}, function (event) {
    //    if (!!globalTimeoutHoverOut) {
    //        clearTimeout(globalTimeoutHoverOut);
    //    }
    //    globalTimeoutHoverOut=setTimeout(function () {
    //        if (jQuery(".hqPop").hasClass("cursorHover")) {
    //        } else {
    //            jQuery(".hqPop").hide();
    //           // event.stopPropagation();
    //        }
    //    }, 400)
    //});
}
function initPopDom() {
    var $ = jQuery;
    var initHtml = ['<div class="hqPop" style="display:none;" >',
        '                    <i class="arrow arrowTop"></i>',
        '                    <div class="contentWrapper">',
        '                        <div class="content">',
        '                            <div  id="hqStockName" class="hqStockName">',
        '                                <span></span>',
        '                            </div>',
        '                            <div id="" class="" style="float: right;height: 25px;line-height: 25px;font-size: 14px;"><a class="hqmore" href="">更多</a></div>',
        '                            <ul class="tab-list" id="tab_list" style="left:100px;">',
        '                                <li class="tab-item choose" id="fst">分时</li>',
        '                                <li class="tab-item " id="rk">日K</li>',
        '                                <li class="tab-item" id="zk">周K</li>',
        '                                <li class="tab-item" id="yk">月K</li>',
        '                            </ul>',
        '                            <div class="scale">',
        '                                <select class="beforeBackRight" id="beforeBackRight" style="display:none;">',
        '                                    <option value="before" selected="selected">前复权</option>',
        '                                    <option value="">不复权</option>',
        '                                    <option value="back">后复权</option>',
        '                                </select>',
        '                            </div>',
        '                            <div id="emchart" class="emchart" style="padding-top: 30px;">',
        '                                <div id="emchart-fst" class="em-tab">',
        '                                </div>',
        '                                <div id="emchart-rk" class="em-tab">',
        '                                </div>',
        '                                <div id="emchart-zk" class="em-tab">',
        '                                </div>',
        '                                <div id="emchart-yk" class="em-tab">',
        '                                </div>',
        '                            </div>',
        '                        </div>',
        '                    </div>',
        '                </div>'].join("");
    if ($(".hqPop").length <= 0) {
        $("body").prepend(initHtml);
    }
}

var current, k0, k1, k2, kfst;
function drawPopHq(option) {
    var $ = jQuery;
    var code = option.code;
    initDraw(option);
    $("#hqStockName span").text(option.name);
    $("#tab_list li").off("click").on("click", function () {
        $(this).addClass("choose").siblings().removeClass("choose");
        var paramsK = {
            dpr: 1,
            code: code,
            container: "emchart-rk",
            "width": 600,
            "height": 500
        };
        switch ($(this).attr("id")) {
            case "fst":
                $("#beforeBackRight").hide();
                tabFst();
                break;
            case "rk":
                $("#beforeBackRight").show();
                paramsK.type = "k";
                paramsK.container = "emchart-rk";
                tabWebK(k0, paramsK);
                break;
            case "zk":
                $("#beforeBackRight").show();
                paramsK.type = "wk";
                paramsK.container = "emchart-zk";
                tabWebK(k1, paramsK);
                break;
            case "yk":
                $("#beforeBackRight").show();
                paramsK.type = "mk";
                paramsK.container = "emchart-yk";
                tabWebK(k2, paramsK);
                break;
        }
    });
    function initDraw(option) {
        $(".hqPop .hqmore").attr("href", "http://quote.eastmoney.com/" + GetMktByCode(option.originCode) + option.originCode + ".html");
        $(".hqPop").off("mouseenter mouseleave").mouseenter(function () {
            $(this).addClass("cursorHover");
            $(this).show();
        }).mouseleave(function () {
            $(this).removeClass("cursorHover");
            setTimeout(function () {
                if (jQuery(".hqPop").hasClass("cursorHover")) {
                } else {
                    jQuery(".hqPop").hide();
                }
            }, 200)
        });
        $(".beforeBackRight").mouseleave(function () {
            return false;
        });
        switch ($(".hqPop .tab-list .tab-item.choose").attr("id")) {
            case "fst":
                $("#beforeBackRight").hide();
                tabFst();
                break;
            case "rk":
                var paramsK = {
                    dpr: 1,
                    code: code,
                    container: "emchart-rk",
                    "width": 600,
                    "height": 500
                };
                $("#beforeBackRight").show();
                paramsK.type = "k";
                paramsK.container = "emchart-rk";
                tabWebK(k0, paramsK);
                break;
            case "zk":
                var paramsK = {
                    dpr: 1,
                    code: code,
                    container: "emchart-rk",
                    "width": 600,
                    "height": 500
                };
                $("#beforeBackRight").show();
                paramsK.type = "wk";
                paramsK.container = "emchart-zk";
                tabWebK(k1, paramsK);
                break;
            case "yk":
                var paramsK = {
                    dpr: 1,
                    code: code,
                    container: "emchart-rk",
                    "width": 600,
                    "height": 500
                };
                $("#beforeBackRight").show();
                paramsK.type = "mk";
                paramsK.container = "emchart-yk";
                tabWebK(k2, paramsK);
                break;
        }
    }
    function tabFst() {
        $("#emchart .em-tab").css({ "display": "none" });
        $("#emchart-fst").css({ "display": "block" });
        var params = {
            dpr: 1,
            code: code,
            container: "emchart-fst",
            "width": 600,
            "height": 500,
            isCR: true,
            type: "r"
        };
        $('#emchart-fst').html("");

        kfst = new EmchartsWebTime(params);
        kfst.draw();
    }
    function tabWebK(k, params) {
        $("#emchart .em-tab").css({ "display": "none" });
        $("#" + params.container).css({ "display": "block" });
        if (!k) {
            current = k = new EmchartsWebK(params)
            current.options.beforeBackRight = document.getElementById("beforeBackRight").value;
            current.draw();
        } else {
            var v = k.options.beforeBackRight;
            if (window.beforeBackRight == v) {
                current = k;
            } else {
                if (window.beforeBackRight == "before") {
                    k.options.beforeBackRight = "before";
                    k.beforeBackRight(1);
                } else if (window.beforeBackRight == "back") {
                    k.options.beforeBackRight = "back";
                    k.beforeBackRight(2);
                } else {
                    k.options.beforeBackRight = "";
                    k.beforeBackRight(0);
                }
            }
        }
        return k;
    }
    $("#beforeBackRight").change(function () {
        var selectValue = $(this).find("option:selected").val()
        window.beforeBackRight = selectValue;
        current.options.beforeBackRight = selectValue;
        if (selectValue == "before") {
            current.beforeBackRight(1);
        } else if (selectValue == "back") {
            current.beforeBackRight(2);
        } else {
            current.beforeBackRight(0);
        }
    });

}
function getCodeWithMarket(code) {
    if (code.Length < 3)
        return code + "1";
    var one = code.substr(0, 1);
    var three = code.substr(0, 3);
    if (one == "5" || one == "6" || one == "9") {
        return code + "1";
    } else {
        if (three == "009" || three == "126" || three == "110" || three == "201" || three == "202" || three == "203" || three == "204") {
            return code + "1";
        } else {
            return code + "2";
        }
    }
}

function GetMktByCode(code) {
    if (code.Length < 3)
        return "sh";
    var one = code.substr(0, 1);
    var three = code.substr(0, 3);
    if (one == "5" || one == "6" || one == "9") {
        return "sh";
    }
    else {
        if (three == "009" || three == "126" || three == "110" || three == "201" || three == "202" || three == "203" || three == "204") {
            return "sh";
        }
        else {
            return "sz";
        }
    }
}