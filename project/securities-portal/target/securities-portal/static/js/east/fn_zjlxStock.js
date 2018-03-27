var zjlx = null;
jQuery(function () {
    jQuery.ajax({
        url: strUrl,
        type: 'GET',
        dataType: "script",
        scriptCharset: "utf-8",
        success: function () {
            try {
                zjlx = aff_data;
                zjlx.data = zjlx.data.reverse();
                if (zjlx.data.length > 0) {
                    var date = new Date(zjlx.data[0].split(',')[0]);
                    var hhmm = dateFormat(new Date(),"HHmm") * 1;
                    var rq = dateFormat(date,"yyyy-MM-dd");

                    if (hhmm < 1500 && rq == dateFormat(new Date(),"yyyy-MM-dd")) {
                        zjlx.data.splice(0, 1);
                    }
                }
                zjlx.page = 1;
                var Dataphqs = zjlx.data.slice(0, 42).reverse();
                //实时资金
                ZjlxEmIn();
                setInterval(ZjlxEmIn, 60000);//新的JS记载
                //盘后资金流向趋势
                opChart.dataSpilt(Dataphqs, '万元');
                //盘后资金流向统计
                LoadTJ();
                //历史资金流向一览
                DataTable();
            }
            catch (ex) {
            }

        }
    })
});

var url = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?Type=F&jsName=zjlx_hq&id=" + _stockCode + _stockMarke;
var tableCache = '<table cellpadding="0" cellspacing="0" class="t2 ns2"><thead><tr><th>\u4ee3\u7801</th><th>\u540d\u79f0</th><th>\u6700\u65b0\u4ef7</th><th>\u6da8\u8dcc\u989d</th><th>\u6da8\u8dcc\u5e45</th><th>\u632f\u5e45</th><th>\u6210\u4ea4\u91cf(\u624b)</th><th>\u6210\u4ea4\u989d(\u4e07)</th><th>\u6628\u6536</th><th>\u4eca\u5f00</th><th>\u6700\u9ad8</th><th>\u6700\u4f4e</th><th>\u6362\u624b\u7387</th><th>\u91cf\u6bd4</th><th>\u5e02\u76c8\u7387</th><th>\u52a0\u81ea\u9009</th></tr></thead><tbody><tr><td><a href="http://quote.eastmoney.com/{code}.html" target="_blank">{code}</a></td><td><a href="http://quote.eastmoney.com/{code}.html" target="_blank">{name}</a></td><td>{price}</td><td>{change}</td><td>{percent}</td><td><span>{zf}</span></td><td><span>{amount}</span></td><td><span>{volume}</span></td><td>{last}</td><td>{open}</td><td>{height}</td><td>{low}</td><td><span>{hsl}</span></td><td><span>{lb}</span></td><td><span>{syl}</span></td><td><a href="http://quote.eastmoney.com/favor/infavor.aspx?code={code}" title="将{code}({name})加为自选股"><img src="/static/img/east/add.gif"></a></td></tr></tbody></table>';
function hqUpdate() {
    var e = new Date();
    try {
        e = getDate(Now);
    } catch (i) { }
    var k = parseInt(tiny.dateFormat(e, "HHmm") * 1);
    var g = e.getDay();
    //if (!(k <= 924 || (k >= 1145 && k <= 1259) || k >= 1500 || g > 5)) {
    var b = url + "&rt=" + Math.floor((new Date().getTime()) / 30000);
    var c, d;
    var e = function (f, g, a) {
        if (isNaN(g) || isNaN(a)) {
            return "<span>" + f + "</span>"
        }
        if (parseFloat(g) > a) {
            f = '<span class="red">' + f + "</span>"
        } else {
            if (parseFloat(g) < a) {
                f = '<span class="green">' + f + "</span>"
            } else {
                f = "<span>" + f + "</span>"
            }
        }
        return f
    };
    tiny.loadJs(b, "utf-8",
        function () {
            if (!(typeof zjlx_hq == "undefined" || zjlx_hq == null)) {
                c = zjlx_hq.quotation[0].split(",");
                if (c.length < 24) {
                    c = [_stockCode, _stockCode, _stockName, "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", ]
                }
                d = tableCache.replace(/{code}/ig, c[1]);
                d = d.replace(/{name}/ig, c[2]);
                d = d.replace(/{price}/ig, e(c[5], c[10], 0));
                d = d.replace(/{change}/ig, e(c[10], c[10], 0));
                d = d.replace(/{percent}/ig, e(c[11], c[10], 0));
                d = d.replace(/{zf}/ig, c[13]);
                d = d.replace(/{amount}/ig, c[9]);
                d = d.replace(/{volume}/ig, c[8]);
                d = d.replace(/{last}/ig, e(c[3]));
                d = d.replace(/{open}/ig, e(c[4], c[4], c[3]));
                d = d.replace(/{height}/ig, e(c[6], c[6], c[3]));
                d = d.replace(/{low}/ig, e(c[7], c[7], c[3]));
                d = d.replace(/{hsl}/ig, c[23]);
                d = d.replace(/{lb}/ig, c[22]);
                d = d.replace(/{syl}/ig, (c[24] < 0 ? "--" : c[24]));
                $("zjlx_hqcont").innerHTML = d
            }
        },
        true)
    // }
}

setInterval(hqUpdate, 10000);
hqUpdate();

//实时资金
function ZjlxEmIn() {
    ZjlxHqUpdate();//新↑
    //折线图
    opChart.initByMtk(_stockCode + _stockMarke);
    //饼状图
    opChart.pieByCode(_stockCode + _stockMarke);
}
//实时资金流向图
var chart = new EmchartsWebLine({
    container: "flash-cont",
    width: 420,
    height: 225,
    showflag: true,
    sepeNum: 6,
    font: {
        fontFamily: 'Microsoft Yahei',
        fontSize: "12",
        color: "#000"
    },
    xaxis: [""],
    series: [""]
});
//实时饼图
var chartPie = new EmchartsPie({
    container: "flash-cont-1",
    width: 430,
    height: 250,
    startOffset: Math.PI / 2,
    onPie: false,
    data: "",
    point: {
        x: 215,
        y: 140
    },
    radius: 90
});
//实时资金列表更新
function ZjlxHqUpdate() {
    var b = 'http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=' + _stockCode + _stockMarke + '&sty=CTBFTA&st=z&sr=&p=&ps=&cb=&js=var tab_data=({data:[(x)]})&token=70f12f2f4f091e459a279469fe49eca5';
    tiny.loadJs(b, "utf-8", function () {
        if (!(typeof tab_data == "undefined" || tab_data == null)) {
            var dataArr = tab_data.data;
            if (dataArr.length == 1) {
                //资金流
                var _dh = dataArr[0].split(',');
                //算和值
                var data = [];
                data[0] = (_dh[5] == "") ? "" : ((parseFloat(_dh[5]))).toFixed(4);//今日主力净流入
                data[1] = (_dh[23] == "") ? "" : ((parseFloat(_dh[23]))).toFixed(2);//主力净比
                data[2] = (_dh[9] == "") ? "" : ((parseFloat(_dh[9]))).toFixed(4);//今日超大单净流入
                data[3] = (_dh[10] == "") ? "" : ((parseFloat(_dh[10]))).toFixed(2);//超大单净比
                data[4] = (_dh[13] == "") ? "" : ((parseFloat(_dh[13]))).toFixed(4);//今日大单净流入
                data[5] = (_dh[14] == "") ? "" : ((parseFloat(_dh[14]))).toFixed(2);//大单净比
                data[6] = (_dh[17] == "") ? "" : ((parseFloat(_dh[17]))).toFixed(4);//今日中单净流入
                data[7] = (_dh[18] == "") ? "" : ((parseFloat(_dh[18]))).toFixed(2);//中单净比
                data[8] = (_dh[21] == "") ? "" : ((parseFloat(_dh[21]))).toFixed(4);//今日小单净流入
                data[9] = (_dh[22] == "") ? "" : ((parseFloat(_dh[22]))).toFixed(2);//小单净比
                data[10] = '';//
                data[11] = '';//
                data[12] = (_dh[7] == "") ? "" : ((parseFloat(_dh[7])) / 10000).toFixed(4);//超大单流入
                data[13] = (_dh[8] == "") ? "" : ((parseFloat(_dh[8])) / 10000).toFixed(4);//超大单流处
                data[14] = (_dh[11] == "") ? "" : ((parseFloat(_dh[11])) / 10000).toFixed(4);//大单流入
                data[15] = (_dh[12] == "") ? "" : ((parseFloat(_dh[12])) / 10000).toFixed(4);//大单流出
                data[16] = (_dh[15] == "") ? "" : ((parseFloat(_dh[15])) / 10000).toFixed(4);//中单流入
                data[17] = (_dh[16] == "") ? "" : ((parseFloat(_dh[16])) / 10000).toFixed(4);//中单流出
                data[18] = (_dh[19] == "") ? "" : ((parseFloat(_dh[19])) / 10000).toFixed(4);//小单流入
                data[19] = (_dh[20] == "") ? "" : ((parseFloat(_dh[20])) / 10000).toFixed(4);//小单流出
                $("updateTime_0").innerHTML = dateFormat(_dh[24], "HH:mm");
                $("updateTime_1").innerHTML = dateFormat(_dh[24], "HH:mm");
                $("data_jlr").innerHTML = data[0];
                $("data_jlr").className = (data[0] > 0) ? "red" : ((data[0] < 0) ? "green" : "");
                $("data_jzb").innerHTML = isNaN(data[1]) ? "0.00%" : data[1] + "%";
                $("data_jzb").className = (data[1] > 0) ? "red" : ((data[1] < 0) ? "green" : "");
                $("data_superjlr").innerHTML = data[2];
                $("data_superjlr").className = (data[2] > 0) ? "red" : ((data[2] < 0) ? "green" : "");
                $("data_superjzb").innerHTML = isNaN(data[3]) ? "0.00%" : data[3] + "%";
                $("data_superjzb").className = (data[3] > 0) ? "red" : ((data[3] < 0) ? "green" : "");
                $("data_ddjlr").innerHTML = data[4];
                $("data_ddjlr").className = (data[4] > 0) ? "red" : ((data[4] < 0) ? "green" : "");
                $("data_ddjzb").innerHTML = isNaN(data[5]) ? "0.00%" : data[5] + "%";
                $("data_ddjzb").className = (data[5] > 0) ? "red" : ((data[5] < 0) ? "green" : "");
                $("data_zdjlr").innerHTML = data[6];
                $("data_zdjlr").className = (data[6] > 0) ? "red" : ((data[6] < 0) ? "green" : "");
                $("data_zdjzb").innerHTML = isNaN(data[7]) ? "0.00%" : data[7] + "%";
                $("data_zdjzb").className = (data[7] > 0) ? "red" : ((data[7] < 0) ? "green" : "");
                $("data_xdjlr").innerHTML = data[8];
                $("data_xdjlr").className = (data[8] > 0) ? "red" : ((data[8] < 0) ? "green" : "");
                $("data_xdjzb").innerHTML = isNaN(data[9]) ? "0.00%" : data[9] + "%";
                $("data_xdjzb").className = (data[9] > 0) ? "red" : ((data[9] < 0) ? "green" : "");
                $("data_superlr").innerHTML = data[12];
                $("data_ddlr").innerHTML = data[14];
                $("data_zdlr").innerHTML = data[16];
                $("data_xdlr").innerHTML = data[18];
                $("data_superlc").innerHTML = data[13];
                $("data_ddlc").innerHTML = data[15];
                $("data_zdlc").innerHTML = data[17];
                $("data_xdlc").innerHTML = data[19];
            }
        }
    }, true)
}
//盘后资金流向趋势
var chartPH = new EmchartsWebLine({
    container: "flash-cont-2",
    width: 420,
    height: 210,
    showflag: true,
    sepeNum: 6,
    font: {
        fontFamily: 'Microsoft Yahei',
        fontSize: "12",
        color: "#000"
    },
    xaxis: [""],
    series: [""]
});
var ChartGeneral = null;
//盘后资金流向统计
function LoadTJ() {
    var width = 430;
    var height = 205;
    var t = 500 * width / 1000;
    var a = ((t - height) / 2 - 30) * -1;
    if (zjlx.data == null || zjlx.data.length <= 0) {
        jQuery("#flash-cont-3").html("");
        jQuery("#flash-cont-3").css("background", 'url("/images/ChartNoData.jpg")  0px ' + a + 'px no-repeat');
        jQuery("#flash-cont-3").css("background-size", width + 'px ' + t + 'px');
        jQuery("#flash-cont-3").css("width", '' + width + 'px');
        jQuery("#flash-cont-3").css("height", '' + height + 'px');
    }
    else {
        var FiveData = {
            //主力净流入
            zljsr: 0,
            //超大单净流入
            cddjlr: 0,
            //大单净流入
            ddjlr: 0,
            //中单净流入
            zdjlr: 0,
            //小单净流入
            xdjlr: 0
        };
        //5日
        var TwentyData = {
            //主力净流入
            zljsr: 0,
            //超大单净流入
            cddjlr: 0,
            //大单净流入
            ddjlr: 0,
            //中单净流入
            zdjlr: 0,
            //小单净流入
            xdjlr: 0
        };
        var phzjtj = {};
        phzjtj.d5zljsr = 0;
        phzjtj.d5cddjlr = 0;
        phzjtj.d5ddjlr = 0;
        phzjtj.d5zdjlr = 0;
        phzjtj.d5xdjlr = 0;
        phzjtj.d20zljsr = 0;
        phzjtj.d20cddjlr = 0;
        phzjtj.d20ddjlr = 0;
        phzjtj.d20zdjlr = 0;
        phzjtj.d20xdjlr = 0;
        for (var i = 0; i < zjlx.data.length; i++) {
            ms = zjlx.data[i].split(',');
            if (i < 5) {
                phzjtj.d5zljsr += parseFloat(ms[1]);
                phzjtj.d5cddjlr += parseFloat(ms[3]);
                phzjtj.d5ddjlr += parseFloat(ms[5]);
                phzjtj.d5zdjlr += parseFloat(ms[7]);
                phzjtj.d5xdjlr += parseFloat(ms[9]);
            }
            if (i < 20) {
                phzjtj.d20zljsr += parseFloat(ms[1]);
                phzjtj.d20cddjlr += parseFloat(ms[3]);
                phzjtj.d20ddjlr += parseFloat(ms[5]);
                phzjtj.d20zdjlr += parseFloat(ms[7]);
                phzjtj.d20xdjlr += parseFloat(ms[9]);
            }
        }
        //5日
        FiveData.zljsr = (phzjtj.d5zljsr / 1).toFixed(4);
        FiveData.cddjlr = (phzjtj.d5cddjlr / 1).toFixed(4);
        FiveData.ddjlr = (phzjtj.d5ddjlr / 1).toFixed(4);
        FiveData.zdjlr = (phzjtj.d5zdjlr / 1).toFixed(4);
        FiveData.xdjlr = (phzjtj.d5xdjlr / 1).toFixed(4);
        //20日
        TwentyData.zljsr = (phzjtj.d20zljsr / 1).toFixed(4);
        TwentyData.cddjlr = (phzjtj.d20cddjlr / 1).toFixed(4);
        TwentyData.ddjlr = (phzjtj.d20ddjlr / 1).toFixed(4);
        TwentyData.zdjlr = (phzjtj.d20zdjlr / 1).toFixed(4);
        TwentyData.xdjlr = (phzjtj.d20xdjlr / 1).toFixed(4);
        series = [{
            prefix: '主力净流入',
            suffix: '亿元',
            color: '#FE3EE1',
            data: [FiveData.zljsr, TwentyData.zljsr]
        }, {
            prefix: '超大单净流入',
            suffix: '亿元',
            color: '#650000',
            data: [FiveData.cddjlr, TwentyData.cddjlr]
        }, {
            prefix: '大单净流入',
            suffix: '亿元',
            color: '#FF1117',
            data: [FiveData.ddjlr, TwentyData.ddjlr]
        }, {
            prefix: '中单净流入',
            suffix: '亿元',
            color: '#FFB83D',
            data: [FiveData.zdjlr, TwentyData.zdjlr]
        }, {
            prefix: '小单净流入',
            suffix: '亿元',
            color: '#94C4EE',
            data: [FiveData.xdjlr, TwentyData.xdjlr]
        }];
        yaxis = [];
        yaxis.push({
            value: "一周(5日)\n资金流入",
            show: true,
            showline: true
        }, {
            value: "一月(20日)\n资金流入",
            show: true,
            showline: true
        });

        if (ChartGeneral == null) {
            ChartGeneral = new EmchartsWebHorizontalGroupBar({
                container: "flash-cont-3",
                'width': width,
                'height': height,
                sepeNum: 5,
                color: "#6890D5",
                hoverColor: "#7EA1DA",
                yaxis: yaxis,
                series: series
            });
        }
        else {
            ChartGeneral.options.yaxis = yaxis;
            ChartGeneral.options.series = series;
        }
        //调用绘图方法
        ChartGeneral.draw();
    }
}
//列表数据
function DataTable() {
    //加载table
    var tablelist = new LoadTable({
        id: "dt_1",
        cells: [
            { "n": "日期", "w": 80 },
            { "n": "收盘价", "w": 40 },
            { "n": "涨跌幅", "w": 40 },
            {
                "n": "主力净流入",
                cells: [
                    { "n": "净额", "w": 80 },
                    { "n": "净占比", "w": 55 },
                ],
                "w": 135
            },
            {
                "n": "超大单净流入<i title='因为存在一笔大额的委托单仅成交小部分的情况，所以会导致超大单或者大单流入/流出的数额极小。'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>",
                cells: [
                    { "n": "净额", "w": 80 },
                    { "n": "净占比", "w": 55 },
                ],
                "w": 135
            },
            {
                "n": "大单净流入",
                cells: [
                    { "n": "净额", "w": 80 },
                    { "n": "净占比", "w": 55 },
                ],
                "w": 135
            },
            {
                "n": "中单净流入",
                cells: [
                    { "n": "净额", "w": 80 },
                    { "n": "净占比", "w": 55 },
                ],
                "w": 135
            },
            {
                "n": "小单净流入",
                cells: [
                    { "n": "净额", "w": 80 },
                    { "n": "净占比", "w": 55 },
                ],
                "w": 135
            }
        ],
        defjson: zjlx,
        autoScrollRun: false,
        // pagesize: 50,
        //dataurl: GetUrl(),
        //param: { "stat_mkt": { cname: "li", type: "mtk", value: "全部股票", cssclass: "at" } },
        cellcount: 13,
        maketr: function (row, data, i, c) {
            var _class = (i % 2 == 0) ? "" : "odd";
            row.className = _class;
            row.onmouseover = function () {
                this.className = "over";
            }
            row.onmouseout = function (o, _c) {
                o.className = _c;
            }.bind(this, row, _class);
            //日期
            var Tdate1 = data[0] != "" ? dateFormat(data[0],"yyyy-MM-dd") : "-";
            var tdatetitle1 = data[0] != "" ? dateFormat(data[0],"yyyy-MM-dd") : "-";
            row.cells[0].innerHTML = "<span title='" + tdatetitle1 + "' >" + Tdate1 + "</span>";
            //收盘价4
            var spj = data[11] == "" ? '-' : (data[11] / 1).toFixed(2);
            row.cells[1].innerHTML = "<span class='" + getColor(data[12].replace("%", "") / 1) + "' style='font-weight:normal'>" + spj + "</span>";
            //涨跌幅12
            var qjzdf = data[12] == "" ? '-' : data[12];
            row.cells[2].innerHTML = "<span class='" + getColor(data[12].replace("%", "") / 1) + "' style='font-weight:normal'>" + qjzdf + "</span>";
            //主力净流入净额
            row.cells[3].innerHTML = data[1] == "0" ? "-" : getcolor_JS((data[1] * 10000).toString(), "w");
            //主力净流入净占比
            var zljlrzb = data[2] == "" ? '-' : data[2];
            row.cells[4].innerHTML = "<span class='" + getColor(data[2].replace("%", "") / 1) + "' style='font-weight:normal'>" + zljlrzb + "</span>";
            //超大单净流入净额
            row.cells[5].innerHTML = data[3] == "0" ? "-" : getcolor_JS((data[3] * 10000).toString(), "w");
            //超大单净流入净占比
            var cdjlrzb = data[3] == "" ? '-' : data[4];
            row.cells[6].innerHTML = "<span class='" + getColor(data[4].replace("%", "") / 1) + "' style='font-weight:normal'>" + cdjlrzb + "</span>";
            //大单净流入净额
            row.cells[7].innerHTML = data[5] == "0" ? "-" : getcolor_JS((data[5] * 10000).toString(), "w");
            //大单净流入净占比
            var ddjlrzb = data[6] == "" ? '-' : data[6];
            row.cells[8].innerHTML = "<span class='" + getColor(data[6].replace("%", "") / 1) + "' style='font-weight:normal'>" + ddjlrzb + "</span>";
            //中单净流入净额
            row.cells[9].innerHTML = data[7] == "0" ? "-" : getcolor_JS((data[7] * 10000).toString(), "w");
            //中单净流入净占比
            var zdjlrzb = data[8] == "" ? '-' : data[8];
            row.cells[10].innerHTML = "<span class='" + getColor(data[8].replace("%", "") / 1) + "' style='font-weight:normal'>" + zdjlrzb + "</span>";
            //小单净流入净额
            row.cells[11].innerHTML = data[9] == "0" ? "-" : getcolor_JS((data[9] * 10000).toString(), "w");
            //小单净流入净占比
            var xdjlrzb = data[10] == "" ? '-' : data[10];
            row.cells[12].innerHTML = "<span class='" + getColor(data[10].replace("%", "") / 1) + "' style='font-weight:normal'>" + xdjlrzb + "</span>";
        }
    });

}

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
            large.data.push(dataArr == "" ? "" : parseFloat(dataArr[3]));
            big.data.push(dataArr == "" ? "" : parseFloat(dataArr[5]));
            middle.data.push(dataArr == "" ? "" : parseFloat(dataArr[7]));
            small.data.push(dataArr == "" ? "" : parseFloat(dataArr[9]));
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

function getColor(data, flag) {
    var colorClass = "";
    if (flag == "sell") {
        if (data > 0)
            colorClass = "green";
        return colorClass;
    } else {
        if (data > 0)
            colorClass = "red";
        else if (data < 0)
            colorClass = "green";
        return colorClass;
    }
}
function getcolor_JS(a, b, c) {
    if (a == '-' || a == "" || a == "NaN") return "<span>-</span>";
    var v;
    if (b == "%") v = parseFloat(a).toFixed(2) + b;
    else if (b == "w") {
        var m = Math.abs(a);
        if (m < 10000) {
            v = (a / 1).toFixed(0);
        } else if (m >= 10000 && m < 1000000) {
            v = (a / 10000).toFixed(2) + "万";
        } else if (m >= 1000000 && m < 100000000) {
            v = (a / 10000).toFixed(0) + "万";
        } else if (m >= 100000000 && m < 10000000000) {
            v = (a / 100000000).toFixed(2) + "亿";
        } else {
            v = (a / 100000000).toFixed(0) + "亿";
        }
    } else v = (a * 100 / 100).toFixed(2);
    if (c) a = c;
    if (a.length > 0) {
        if (a.substring(0, 1) == '-') return "<span class=\"green\">" + v + "</span>";
        else if (a != 0) return "<span class=\"red\">" + v + "</span>";
    }
    return "<span>" + v + "</span>";
}