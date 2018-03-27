//
function IsPanQian_pie(obj) {
    try {
        var res = true;
        for (var i in obj) {
            if (parseFloat(obj[i].value) > 0) {
                res = false;
                break;
            }
        }
        return res;
    } catch (e) {
        return true;
    }
}
//
function FalshToImg() {

}
var today = new Date();
var QuerySpan = "";
today.setDate(today.getDate() - 42);
QuerySpan = today.getFullYear() + "-" + (parseInt(today.getMonth()) + 1) + "-" + today.getDate();
var opChart = {
    draw: function (data, obj) {
        obj.options.xaxis = data.xaxis;
        obj.options.series = data.series;
        //调用绘图方法
        obj.draw(function (error) {
            if (error == null) {
                obj.reDraw();//使用上次的数据数据重绘
            }
        });
    },
    drawPie: function (data, obj) {
        obj.options.data = data;
        //调用绘图方法
        obj.draw(function (error) {
            if (error == null) {
                obj.reDraw();//使用上次的数据数据重绘
            }
        });
    },
    dataTransfer: function (obj, danwei) {
        var value = {
            xaxis: [],
            series: []
        }
        var main = {
            name: "主力净流入",
            color: "#FE3EE1",
            data: [],
            suffix: danwei
        };
        var large = {
            name: "超大单净流入",
            color: "#650000",
            data: [],
            suffix: danwei
        };
        var big = {
            name: "大单净流入",
            color: "#FF1117",
            data: [],
            suffix: danwei
        };
        var middle = {
            name: "中单净流入",
            color: "#FFB83D",
            data: [],
            suffix: danwei
        };
        var small = {
            name: "小单净流入",
            color: "#94C4EE",
            data: [],
            suffix: danwei
        };

        var xArr = obj.xa.split(",");
        for (var i = 0; i < xArr.length; i++) {
            var time = xArr[i];
            if (time != "") {
                var isShow = false;
                if (time == "09:31" || time == "10:30" || time == "11:30" || time == "14:00" || time == "15:00") {
                    isShow = true;
                }
                value.xaxis.push({
                    value: time,
                    showline: isShow,
                    show: isShow
                });
            }
        }
        var yArr = obj.ya;
        document.getElementById("flash-cont").style.backgroundImage = '';
        for (var i = 0; i < yArr.length; i++) {
            if (i == 0 && yArr[i] == ',,,,') {
                if (chart.options.series.length == 1) {
                    document.getElementById("flash-cont").style.backgroundImage = 'url(/images/panqianqingkong.jpg)';
                    document.getElementById("flash-cont").style.backgroundRepeat = 'no-repeat';
                }
                return false;
            }
            var subArray = yArr[i].split(',');
            main.data.push(subArray[0]);
            large.data.push(subArray[1]);
            big.data.push(subArray[2]);
            middle.data.push(subArray[3])
            small.data.push(subArray[4]);
        }

        value.series.push(main);
        value.series.push(large);
        value.series.push(big);
        value.series.push(middle);
        value.series.push(small);
        this.draw(value, chart);
    },
    initTotal: function (code) {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://ff.eastmoney.com/EM_CapitalFlowInterface/api/js?id=" + code + "&type=ff&check=MLBMS&cb=var%20aff_data=&js={(x)}&rtntype=3",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataTransfer(aff_data, '亿元');
            }
        })
    },
    initByMtk: function (code) {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://ff.eastmoney.com/EM_CapitalFlowInterface/api/js?id=" + code + "&type=ff&check=MLBMS&cb=var%20aff_data=&js={(x)}&rtntype=3",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataTransfer(aff_data, '万元');
            }
        })
    },
    dataSpilt: function (data, danwei) {
        var value = {
            xaxis: [],
            series: []
        }
        var main = {
            name: "主力净流入",
            color: "#FE3EE1",
            data: [],
            suffix: danwei
        };
        var large = {
            name: "超大单净流入",
            color: "#650000",
            data: [],
            suffix: danwei
        };
        var big = {
            name: "大单净流入",
            color: "#FF1117",
            data: [],
            suffix: danwei
        };
        var middle = {
            name: "中单净流入",
            color: "#FFB83D",
            data: [],
            suffix: danwei
        };
        var small = {
            name: "小单净流入",
            color: "#94C4EE",
            data: [],
            suffix: danwei
        };
        var _x = [];
        var _y = data.length;
        var is100 = 101;
        var mo = _y == 100 ? (is100 = 0, 25) : (_y / 25) > 3 ? 25 : ((_y / 20) > 3 ? 20 : ((_y / 15) > 3 ? 15 : ((_y / 10) > 3 ? 10 : 5)));//满100条数据的情况下25
        for (var i = 0; i < data.length; i++) {
            var dataArr = data[i].split(',');
            var isShow = false;
            var f = _y - (i + 1);//反序对应数
            //console.log(f, dataArr[0], i,mo);
            if (f % mo == 0 || i == is100) {
                isShow = true;
            }
            value.xaxis.push({
                value: dateFormat(dataArr[0], 'MM-dd'),
                showline: isShow,
                show: isShow
            });
            main.data.push(dataArr == "" ? "" : parseFloat(dataArr[1]));
            large.data.push(dataArr == "" ? "" : parseFloat(dataArr[2]));
            big.data.push(dataArr == "" ? "" : parseFloat(dataArr[3]));
            middle.data.push(dataArr == "" ? "" : parseFloat(dataArr[4]));
            small.data.push(dataArr == "" ? "" : parseFloat(dataArr[5]));
        }
        value.series.push(main);
        value.series.push(large);
        value.series.push(big);
        value.series.push(middle);
        value.series.push(small);
        this.draw(value, chartPH);
    },
    phTotal: function (code) {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://ff.eastmoney.com/EM_CapitalFlowInterface/api/js?id=" + code + "&type=hff&rtntype=2&js=(x)&check=TMLBMS&cb=var%20ph_data=&style=top&num=42&_=1482756720594",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataSpilt(ph_data, '亿元');
            }
        })
    },
    phByMtk: function (code) {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://ff.eastmoney.com/EM_CapitalFlowInterface/api/js?id=" + code + "&type=hff&rtntype=2&js=(x)&check=TMLBMS&cb=var%20ph_data=&style=top&num=42&_=1482756720594",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataSpilt(ph_data, '万元');
            }
        })
    },
    flash: function (id, ph) {
        //生成盘中折线图img
        var ifm_pz = document.createElement('img');
        ifm_pz.id = 'zljx_img';
        ifm_pz.src = 'http://jspi.eastmoney.com/EM_CapitalFlowConvasPic/api/js?id=' + id + '&type=fr&sty=S420225&jr=' + getCode();
        document.getElementById("flash-cont").appendChild(ifm_pz);
        //生成盘中饼图img
        var ifm_pi = document.createElement('img');
        ifm_pi.id = 'cjfb_img';
        ifm_pi.src = 'http://jspi.eastmoney.com/EM_CapitalFlowConvasPic/api/js?id=' + id + '&type=pie&sty=S43025090&jr=' + getCode();
        document.getElementById("flash-cont-1").appendChild(ifm_pi);
        if (ph) {
            //生成盘后数据
            var ifm_ph = document.createElement('img');
            ifm_ph.src = 'http://jspi.eastmoney.com/EM_CapitalFlowConvasPic/api/js?id=' + id + '&type=hff&sty=S420210&jr=' + getCode();
            document.getElementById("flash-cont-2").appendChild(ifm_ph);
        }
    },
    IntsZjlImg: function (id) {
        //实时刷资金流向图--非两市
        var e = new Date();
        try {
            e = Eastmoney.Time.now()
        }
        catch (i) {
        }
        var k = parseInt(tiny.dateFormat(e, "HHmm") * 1);
        var g = e.getDay();
        if (!(k <= 914 || (k >= 1145 && k <= 1259) || k >= 1515 || g > 5)) {
            try {
                var zljx_img = document.getElementById("zljx_img");
                zljx_img.src = 'http://jspi.eastmoney.com/EM_CapitalFlowConvasPic/api/js?id=' + id + '&type=fr&sty=S420225&jr=' + getCode();
                var cjfb_img = document.getElementById("cjfb_img");
                cjfb_img.src = 'http://jspi.eastmoney.com/EM_CapitalFlowConvasPic/api/js?id=' + id + '&type=pie&sty=S43025090&jr=' + getCode();
            }
            catch (i) {
            }
        }
    },
    pieByTotal: function () {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012&sty=CTBF&st=z&sr=&p=&ps=&cb=var%20pie_data=&js=[(x)]&token=28758b27a75f62dc3065b81f7facb365",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataTransferPieTotal(pie_data);
            }
        })
    },
    pieByCode: function (code) {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + code + "&sty=CTBF&st=z&sr=&p=&ps=&cb=var%20pie_data=&js=(x)&token=28758b27a75f62dc3065b81f7facb365",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataTransferPie(pie_data, 10000);
            }
        })
    },
    pieByMtk: function (code) {
        var _this = this;
        jQuery.ajax({
            type: "get",
            url: "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=" + code + "&sty=CTBF&st=z&sr=&p=&ps=&cb=var%20pie_data=&js=(x)&token=28758b27a75f62dc3065b81f7facb365",
            dataType: "script",
            scriptCharset: "utf-8",
            success: function () {
                _this.dataTransferPie(pie_data, 100000000);
            }
        })
    },
    dataTransferPie: function (obj, pd) {
        var value = [];
        if (obj == "" || obj == undefined) {
            return false;
        }
        document.getElementById("flash-cont-1").style.backgroundImage = '';
        var array = obj.replace("\"", "").split(",");
        value.push({
            name: "小单流入",
            color: "#ff8080",
            value: array[19] == "" ? "0" : (parseFloat(array[19]) / pd).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "中单流入",
            color: "#f83434",
            value: array[15] == "" ? "0" : (parseFloat(array[15]) / pd).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "大单流入",
            color: "#ae0000",
            value: array[11] == "" ? "0" : (parseFloat(Number(array[11])) / pd).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "超大单流入",
            color: "#650000",
            value: array[7] == "" ? "0" : (parseFloat(Number(array[7])) / pd).toFixed(4),
            showInfo: true
        });

        value.push({
            name: "超大单流出",
            color: "#004800",
            value: array[8] == "" ? "0" : (parseFloat(Math.abs(Number(array[8]))) / pd).toFixed(4),
            show: true,
            showInfo: true
        });
        value.push({
            name: "大单流出",
            color: "#0a820a",
            value: array[12] == "" ? "0" : (parseFloat(Math.abs(Number(array[12]))) / pd).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "中单流出",
            color: "#27b729",
            value: array[16] == "" ? "0" : (parseFloat(Math.abs(array[16])) / pd).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "小单流出",
            color: "#77e97a",
            value: array[20] == "" ? "0" : (parseFloat(Math.abs(array[20])) / pd).toFixed(4),
            showInfo: true
        });
        if (IsPanQian_pie(value)) {
            if (chartPie.options.data.length == 0) {
                document.getElementById("flash-cont-1").style.backgroundImage = 'url(/images/panqianqingkong_bing.jpg)';
                document.getElementById("flash-cont-1").style.backgroundRepeat = 'no-repeat';
            }
            return false;
        }
        this.drawPie(value, chartPie);
    },
    dataTransferPieTotal: function (obj) {
        var value = [];
        if (obj == "" || obj == undefined) {
            return;
        }
        document.getElementById("flash-cont-1").style.backgroundImage = '';
        var array = obj[0].replace("\"", "").split(",");
        var array2 = obj[1].replace("\"", "").split(",");
        value.push({
            name: "小单流入",
            color: "#ff8080",
            value: (array[19] == "" || array2[19] == "") ? "0" : ((parseFloat(array[19]) + parseFloat(array2[19])) / 100000000).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "中单流入",
            color: "#f83434",
            value: (array[15] == "" || array2[15] == "") ? "0" : ((parseFloat(array[15]) + parseFloat(array2[15])) / 100000000).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "大单流入",
            color: "#ae0000",
            value: (array[11] == "" || array2[11] == "") ? "0" : ((parseFloat(Number(array[11])) + parseFloat(Number(array2[11]))) / 100000000).toFixed(4),
            showInfo: true
        });

        value.push({
            name: "超大单流入",
            color: "#650000",
            value: (array[7] == "" || array2[7] == "") ? "0" : ((parseFloat(Number(array[7])) + parseFloat(Number(array2[7]))) / 100000000).toFixed(4),
            showInfo: true
        });

        value.push({
            name: "超大单流出",
            color: "#004800",
            value: (array[8] == "" || array2[8] == "") ? "0" : ((parseFloat(Math.abs(Number(array[8]))) + parseFloat(Math.abs(Number(array2[8])))) / 100000000).toFixed(4),
            show: true,
            showInfo: true
        });

        value.push({
            name: "大单流出",
            color: "#0a820a",
            value: (array[12] == "" || array2[12] == "") ? "0" : ((parseFloat(Math.abs(Number(array[12]))) + parseFloat(Math.abs(Number(array2[12])))) / 100000000).toFixed(4),
            showInfo: true
        });
        value.push({
            name: "中单流出",
            color: "#27b729",
            value: (array[16] == "" || array2[16] == "") ? "0" : ((parseFloat(Math.abs(array[16])) + parseFloat(Math.abs(array2[16]))) / 100000000).toFixed(4),
            showInfo: true
        });

        value.push({
            name: "小单流出",
            color: "#77e97a",
            value: (array[20] == "" || array2[20] == "") ? "0" : ((parseFloat(Math.abs(array[20])) + parseFloat(Math.abs(array2[20]))) / 100000000).toFixed(4),
            showInfo: true
        });
        if (IsPanQian_pie(value)) {
            if (chartPie.options.data.length == 0) {
                document.getElementById("flash-cont-1").style.backgroundImage = 'url(/images/panqianqingkong_bing.jpg)';
                document.getElementById("flash-cont-1").style.backgroundRepeat = 'no-repeat';
            }
            return false;
        }
        this.drawPie(value, chartPie);
    }
};
function dateFormat(dateS, part) {
    if (dateS == "-" || typeof dateS == "undefined") {
        return '-';
    }
    if (dateS.length > 10) {
        dateS = dateS.split('T')[0].replace(/-/g, "/");
    }
    var date = new Date(dateS);
    var datecopy;
    var redate = "";
    part = (part == null) ? "yyyy-MM-dd HH:mm:ss" : part;
    var y = date.getFullYear();
    var M = date.getMonth() + 1;
    var d = date.getDate();
    var H = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();
    var MM = (M > 9) ? M : "0" + M;
    var dd = (d > 9) ? d : "0" + d;
    var HH = (H > 9) ? H : "0" + H;
    var mm = (m > 9) ? m : "0" + m;
    var ss = (s > 9) ? s : "0" + s;
    redate = part.replace("yyyy", y).replace("MM", MM).replace("dd", dd).replace("HH", HH).replace("mm", mm).replace("ss", ss).replace("M", M).replace("d", d).replace("H", H).replace("m", m).replace("s", s);
    return redate;
}
function getCode(num) {
    var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    var codes = str.split('');
    num = num || 6;
    var code = "";
    for (var i = 0; i < num; i++) {
        code += codes[Math.floor(Math.random() * 52)];
    }
    return code;
}