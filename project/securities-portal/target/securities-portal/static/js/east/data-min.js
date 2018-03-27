/**
 * Created by zbc on 14-5-6.
 */
"use strict";
window.allUri = {
    imageURL: "http://pifm3.eastmoney.com/EM_Finance2014PictureInterface/Index.aspx?ID=3000592&UnitWidth=-6&imageType=KXL&EF=&Formula=RSI&AT=&&type=m5&token=44c9d251add88e27b65ed86506f6e5da"
};
window.imageOpition = {
    ID: kLine.stockCode + kLine.stockMarket,
    type: "",
    Formula: "RSI",
    EF: "",
    UnitWidth: -6,
    AT: ""
};

(function () {
    function myselect(ap, func) {
        if (FUC.$(ap) == undefined) return;
        FUC.addEvent(FUC.$(ap), "mouseenter", function (e) {
            this.getElementsByTagName("dl")[0].style.display = "block";
        }, false)

        FUC.addEvent(FUC.$(ap), "mouseleave", function (e) {
            this.getElementsByTagName("dl")[0].style.display = "none";
        }, false)

        var b = FUC.$(ap).getElementsByTagName("dl")[0]
        var a = b.children;

        for (var i = 0; i < a.length; i++) {
            a[i].onclick = function () {
                var is = i
                FUC.$(ap).getElementsByTagName("span")[0].innerHTML = this.innerHTML;
                FUC.$(ap).getElementsByTagName("span")[0].setAttribute("value", this.getAttribute("value"));
                b.style.display = 'none';
                func && func(this.getAttribute("value"))
            }
        }
    }

    myselect("select1", function (a) {

    })
    myselect("select2", function (a) {
        var hyid = $x("pylist").getAttribute("value");
        window.phIsFirst = true;
        phrank(a, hyid);
    })
    myselect("select3", function (a) {

    })
    myselect("select4", function (a) {
        switch (a) {
            case "0": {
                imageOpition.AT = "";
                break;
            }
            case "1": {
                imageOpition.AT = "1";
                break;
            }
            case "2": {
                imageOpition.AT = "2";
                break;
            }
        }
        WriteCookie("emhq_picfq", a, 8760);
        changeImage();
    })

    LiPic.changeDiv({
        id: "tab1",
        dom: ["hqbj", "zjl"],
        selectedClass: "cur"
    });

    /*LiPic.changeDiv({
     id: "tab2",
     dom: ["vvcc", "fj"],
     selectedClass: "cur"
     });*/
    LiPic.changeDiv2({
        id:"tab3",
        selectedClass:"cur",
        dom: ["cggy1", "cggy2"],
        domt: ["cgyyt1", "cgyyt2"]
    });
    LiPic.changeDiv2({
        id: "tab4",
        dom: ["sszj", "phzj"],
        domt: ["zjlxuptb", "zjlxuptc"],
        selectedClass: "cur"
    });
    LiPic.changeDiv({
        id: "tab5",
        dom: ["wahaha", "jdzfox"],
        selectedClass: "cur",
        callBacks: function () {
            if ($("#tab5 .cur h3").html() == "阶段涨幅") {
                getzdzf();
            }
        }
    });
    new LIB.actTab({
        pnode: "actTab4",
        callBacks: function (a) {
            imgChangeNode1(a);
        }
    });

    new LIB.actTab({
        pnode: "pictit",
        callBacks: getImageValue
    });
    //LiPic.changeDiv({
    //    id: "tab6",
    //    dom: ["cyggzh", "fxgs"],
    //    selectedClass: "cur"
    //});
    //LiPic.changeDiv({
    //    id: "tab7",
    //    dom: ["zbList", "mnmmList"],
    //    selectedClass: "cur"
    //});
    /*new LIB.actTab({
     pnode: "actTab3"
     });*/

    function getzdzf() {
        Min.Loader.load("http://183.136.160.92/EM_UBG_Finance2016TransferExtendInterface/js.ashx?type=BKJDZF&cmd=" + Def.code + Def.market + "&js=var bkjdzf=", "utf-8", function () {
            while ($x("jdzf").hasChildNodes()) { $x("jdzf").removeChild($x("jdzf").childNodes[0]); }
            var zjltitnm = ["今日", "3日", "5日", "6日", "10日"];
            if (bkjdzf[0] != null) {
                for (var i = 0; i < zjltitnm.length; i++) {
                    var _tr = document.createElement("tr"); var _tr_ = document.createElement("tr");
                    var _td_a = document.createElement("td"); _td_a.className = "nm"; _td_a.innerHTML = zjltitnm[i];
                    var _td_b = document.createElement("td"); _td_b.className = udcls(bkjdzf[0][i][0]); _td_b.innerHTML = bkjdzf[0][i][0]; _td_b.style.textAlign = "center";
                    var _td_c = document.createElement("td"); _td_c.className = udcls(bkjdzf[0][i][1]); _td_c.innerHTML = bkjdzf[0][i][1]; _td_c.style.textAlign = "center";
                    _tr_.appendChild(_td_a); _tr_.appendChild(_td_b); _tr_.appendChild(_td_c);
                    $x("jdzf").appendChild(_tr_);
                }
            }
        });
    }

    function imgChangeNode1(a) {
        var t = parseInt(a.getAttribute("index"));
        if (t == 0) {
            $x("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "rc") + "&rt=" + formatm();
        } else if (t == 1) {
            if (_this.IsAGu == "1") {
                $x("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "r") + "&rt=" + formatm();
            } else {
                $x("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "rc") + "&rt=" + formatm();
            }
        } else {
            var md = "M" + (t - 1);
            $x("picr").src = "http://pifm.dfcfw.com/EM_Finance2014PictureInterface/Index.aspx?imagetype=t&type=" + md + "&id=" + _this._Code + "" + _this._Market + "&token=" + window.token + "&rt=" + formatm();
        }
    }
    function changeImage() {
        allUri.imageURL = FUC.changeURL(allUri.imageURL, imageOpition);
        var PicUrl = allUri.imageURL + "&" + Math.random();
        FUC.$("pick").setAttribute("src", PicUrl);
    }
    function InitFQImg() {
        var num = GetCookie("emhq_picfq");
        var itemName = "不复权";
        var itemNum = num == "" ? "0" : num;
        switch (num) {
            case "0": {
                window.imageOpition.FA = "";
                window.imageOpition.BA = "";
                break;
            }
            case "1": {
                window.imageOpition.FA = true;
                window.imageOpition.BA = "";
                itemName = "前复权";
                break;
            }
            case "2": {
                window.imageOpition.FA = "";
                window.imageOpition.BA = true;
                itemName = "后复权";
                break;
            }
        }
        FUC.$("select4").getElementsByTagName("span")[0].innerHTML = itemName;
        changeImage();
    }
    function getImageValue(arr) {
        imageOpition.type = arr.getAttribute("value");
        changeImage();
        return;
    }
    new LiPic.change({
        dom1: FUC.$("zkeyb"), dom2: FUC.$("zkeyc"), callbacks: function (arg) {
            imageOpition.Formula = arg.getAttribute("value");
            changeImage();
        }
    });
    new LiPic.change({
        dom1: FUC.$("zkeyc"), dom2: FUC.$("zkeyb"), callbacks: function (arg) {
            imageOpition.Formula = arg.getAttribute("value");
            changeImage();
        }
    });
    new LiPic.change({
        dom1: FUC.$("zkeya"), callbacks: function (arg) {
            imageOpition.EF = arg.getAttribute("value");
            changeImage();
        }
    });

    //拉长 缩短线
    FUC.addEvent(FUC.$("picklc"), "click", function (e) {
        var width = imageOpition.UnitWidth;
        if (width <= -8) return;
        imageOpition.UnitWidth = width - 1;
        changeImage();
    }, false);
    FUC.addEvent(FUC.$("picksd"), "click", function (e) {
        var width = imageOpition.UnitWidth;
        if (width >=0) return;
        imageOpition.UnitWidth = width + 1;
        changeImage();
    }, false);
    FUC.addEvent(FUC.$("flash_show"), "click", function (e) {
        if (/\((iPhone|iPad|iPod)/i.test(navigator.userAgent)) {
            showimg();
        } else {
            showfls();
        }
    }, false);
    FUC.addEvent(FUC.$("image_show"), "click", function (e) {
        showimg();
    }, false);
    FUC.addEvent(FUC.$("image_show2"), "click", function (e) {
        showimg();
    }, false);
    InitFQImg();
})();