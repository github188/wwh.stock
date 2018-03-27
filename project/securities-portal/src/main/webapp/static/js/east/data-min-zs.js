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

    myselect("select_flsdb", function (a) {
        if (a != "") {
            var rPort = document.getElementsByName("pkdb");
            rPort[0].checked = true
        }
    })
    /*myselect("select2", function (a) {
     var hyid = $x("pylist").getAttribute("value");
     window.phIsFirst = true;
     phrank(a, hyid);
     })*/
    myselect("select3", function (a) {

    })

    LiPic.changeDiv({
        id: "tab_hyzdfb",
        dom: ["cnt_hyzfb", "cnt_hydfb"],
        selectedClass: "cur"
    });

    LiPic.changeDiv({
        id: "tab_gnzdfb",
        dom: ["cnt_gnzfb", "cnt_gndfb"],
        selectedClass: "cur"
    });

    LiPic.changeDiv({
        id: "tab_zjlfb",
        dom: ["cnt_zjl", "cnt_fb"],
        selectedClass: "cur"
    });

    LiPic.changeDiv2({
        id: "tab_fxmb",
        selectedClass: "cur",
        dom: ["cnt_dpfx", "cnt_mb"],
        domt: ["tit_dpfx", "tit_mb"]
    });

    //LiPic.changeDiv({
    //    id: "tab_wyzgzgp",
    //    dom: ["cnt_day3", "cnt_day5", "cnt_day10", "cnt_day20"],
    //    selectedClass: "cur"
    //});
    LiPic.changeDiv({
        id: "tab_dkdc",
        dom: ["dcoxa", "dcoxb", "dcoxc"],
        selectedClass: "cur"
    });

    new LIB.actTab({
        pnode: "tab_picr",
        callBacks: function (a) {
            imgChangeNode1(a);
        }
    });

    new LIB.actTab({
        pnode: "tab_pick",
        callBacks: getImageValue
    });

    function imgChangeNode1(a) {
        var t = parseInt(a.getAttribute("index"));
        if (t == 0) {
            $x("picr").src = PicN.replace("{0}", _this._Code).replace("{1}", _this._Market).replace("{2}", "r") + "&rt=" + formatm();
        } else {
            var md = "M" + t;
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
        if (width >= 0) return;
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
    InitFQImg();
})();