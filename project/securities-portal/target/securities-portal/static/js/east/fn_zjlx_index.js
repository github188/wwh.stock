var tableCache = '<table cellpadding="0" cellspacing="0" class="t2 ns2"><thead><tr><th>\u4ee3\u7801</th><th>\u540d\u79f0</th><th>\u6700\u65b0\u4ef7</th><th>\u6da8\u8dcc\u989d</th><th>\u6da8\u8dcc\u5e45(%)</th><th>\u632f\u5e45</th><th>\u6210\u4ea4\u91cf(\u624b)</th><th>\u6210\u4ea4\u989d(\u4e07)</th><th>\u6628\u6536</th><th>\u4eca\u5f00</th><th>\u6700\u9ad8</th><th>\u6700\u4f4e</th></tr></thead><tbody><tr><td><a href="http://quote.eastmoney.com/{code}.html" target="_blank">{codes}</a></td><td><a href="http://quote.eastmoney.com/{code}.html" target="_blank">{name}</a></td><td>{price}</td><td>{change}</td><td>{percent}</td><td><span>{zf}</span></td><td><span>{amount}</span></td><td><span>{volume}</span></td><td>{last}</td><td>{open}</td><td>{height}</td><td>{low}</td></tr></tbody></table>';
function ZjlxUpdate() {
    time_stamp = Math.floor((new Date().getTime()) / 60000);
    //var b = data_baseurl + "js/" + _dpzs + ".js?rt=" + Math.random();
    var b = "http://s1.dfcfw.com/js/" + _dpzs + ".js?rt=" + Math.random();
    tiny.loadJs(b, "utf-8", function () {
        if (!(typeof zjlx_detail == "undefined" || zjlx_detail == null)) {
            var data = zjlx_detail.data.split(",");
            $("updateTime_0").innerHTML = zjlx_detail.update;
            $("updateTime_1").innerHTML = zjlx_detail.update;
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
            $("data_xdlc").innerHTML = data[19] + "";
        }
    }, true)
}
function ZjlxInterval() {
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
            ZjlxUpdate();
            var j = new Date().getTime();
            var h = new SWFObject(swf_line, "so10", "468", "225", "8", "");
            h.addVariable("path", "/");
            h.addVariable("settings_file", encodeURIComponent("../settings/dpzjlx_line_settings_csv.xml"));
            h.addVariable("data_file", encodeURIComponent(data_baseurl + "allXML/" + _dpzs + ".xml?rt=" + j));
            h.write("flash-cont");
            var f = new SWFObject(swf_pie, "so30", "468", "250", "8", "");
            f.addVariable("path", "/");
            f.addVariable("settings_file", encodeURIComponent("../settings/dpzjlx_pie.xml"));
            f.addVariable("data_file", encodeURIComponent(data_baseurl + "allXML/xml/" + _dpzs + ".xml?rt=" + j));
            f.write("flash-cont-1")
        }
        catch (i) {
        }
    }
}
//分解ZjlxInterval，大盘页面独占--旧的折线图
function zjlInterval()
{
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
            ZjlxHqUpdateTotal();
            ZjlxInterval_CF();
            var j = new Date().getTime();
            var h = new SWFObject(swf_line, "so10", "430", "225", "8", "");
            h.addVariable("path", "/");
            h.addVariable("settings_file", encodeURIComponent("../settings/dpzjlx_line_settings_csv.xml"));
            h.addVariable("data_file", encodeURIComponent(data_baseurl + "allXML/" + _dpzs + ".xml?rt=" + j));
            h.write("flash-cont");
        }
        catch (i) {
        }
    }
}
//分解ZjlxInterval，大盘页面独占--旧的饼状图
function ZjlxInterval_CF() {
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
            //ZjlxUpdate();
            ZjlxHqUpdateTotal();
            var j = new Date().getTime();
            var f = new SWFObject(swf_pie, "so30", "430", "250", "8", "");
            f.addVariable("path", "/");
            f.addVariable("settings_file", encodeURIComponent("../settings/dpzjlx_pie.xml"));
            f.addVariable("data_file", encodeURIComponent(data_baseurl + "allXML/xml/" + _dpzs + ".xml?rt=" + j));
            f.write("flash-cont-1")
        }
        catch (i) {
        }
    }
}
//新的资金流向列表--大盘
function ZjlxHqUpdateTotal() {
    time_stamp = Math.floor((new Date().getTime()) / 60000);
    var b = 'http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012&sty=CTBFTA&st=z&sr=&p=&ps=&cb=&js=var tab_data=({data:[(x)]})&token=70f12f2f4f091e459a279469fe49eca5';
    //var b = 'http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=P.(x),(x)|0000011,3990012|0000011,3990012&sty=DCSFF|CTBF&st=z&sr=&p=&ps=&cb=&js=var tab_data=({data:[(x)]})&token=70f12f2f4f091e459a279469fe49eca5';
    tiny.loadJs(b, "utf-8", function () {
        if (!(typeof tab_data == "undefined" || tab_data == null)) {
            var dataArr = tab_data.data;
            if (dataArr.length == 2) {
                //资金流
                var _dh = dataArr[0].split(',');
                var _ds = dataArr[1].split(',');
                //算和值
                var data = [];
                var total = (parseFloat(_dh[7]) + parseFloat(_dh[11]) + parseFloat(_dh[15]) + parseFloat(_dh[19]) +
                    parseFloat(_ds[7]) + parseFloat(_ds[11]) + parseFloat(_ds[15]) + parseFloat(_ds[19])) / 100000000;

                data[0] = (_dh[5] == "" || _ds[5] == "") ? "" : ((parseFloat(_dh[5]) + parseFloat(_ds[5])) / 10000).toFixed(4);//今日主力净流入
                data[1] = (data[0]*100 / total).toFixed(2);//主力净比


                data[2] = (_dh[9] == "" || _ds[9] == "") ? "" : ((parseFloat(_dh[9]) + parseFloat(_ds[9])) / 10000).toFixed(4);//今日超大单净流入
                data[3] = (data[2] * 100 / total).toFixed(2);//主力净比


                data[4] = (_dh[13] == "" || _ds[13] == "") ? "" : ((parseFloat(_dh[13]) + parseFloat(_ds[13])) / 10000).toFixed(4);//今日大单净流入
                data[5] = (data[4] * 100 / total).toFixed(2);//主力净比

                data[6] = (_dh[17] == "" || _ds[17] == "") ? "" : ((parseFloat(_dh[17]) + parseFloat(_ds[17])) / 10000).toFixed(4);//今日中单净流入
                data[7] = (data[6] * 100 / total).toFixed(2);//主力净比

                data[8] = (_dh[21] == "" || _ds[21] == "") ? "" : ((parseFloat(_dh[21]) + parseFloat(_ds[21])) / 10000).toFixed(4);//今日小单净流入
                data[9] = (data[8] * 100 / total).toFixed(2);//主力净比

                data[10] = '';//
                data[11] = '';//
                data[12] = (_dh[7] == "" || _ds[7] == "") ? "" : ((parseFloat(_dh[7]) + parseFloat(_ds[7])) / 100000000).toFixed(4);//超大单流入
                data[13] = (_dh[8] == "" || _ds[8] == "") ? "" : ((parseFloat(_dh[8]) + parseFloat(_ds[8])) / 100000000).toFixed(4);//超大单流处
                data[14] = (_dh[11] == "" || _ds[11] == "") ? "" : ((parseFloat(_dh[11]) + parseFloat(_ds[11])) / 100000000).toFixed(4);//大单流入
                data[15] = (_dh[12] == "" || _ds[12] == "") ? "" : ((parseFloat(_dh[12]) + parseFloat(_ds[12])) / 100000000).toFixed(4);//大单流出
                data[16] = (_dh[15] == "" || _ds[15] == "") ? "" : ((parseFloat(_dh[15]) + parseFloat(_ds[15])) / 100000000).toFixed(4);//中单流入
                data[17] = (_dh[16] == "" || _ds[16] == "") ? "" : ((parseFloat(_dh[16]) + parseFloat(_ds[16])) / 100000000).toFixed(4);//中单流出
                data[18] = (_dh[19] == "" || _ds[19] == "") ? "" : ((parseFloat(_dh[19]) + parseFloat(_ds[19])) / 100000000).toFixed(4);//小单流入
                data[19] = (_dh[20] == "" || _ds[20] == "") ? "" : ((parseFloat(_dh[20]) + parseFloat(_ds[20])) / 100000000).toFixed(4);//小单流出
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
function ZjlxHqUpdate() {
    time_stamp = Math.floor((new Date().getTime()) / 60000);
    var b = 'http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=' + _code + '&sty=CTBFTA&st=z&sr=&p=&ps=&cb=&js=var tab_data=({data:[(x)]})&token=70f12f2f4f091e459a279469fe49eca5';
    //var b = 'http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=P.(x),(x)|' + _code + '|' + _code + '&sty=DCSFF|CTBF&st=z&sr=&p=&ps=&cb=&js=var tab_data=({data:[(x)]})&token=70f12f2f4f091e459a279469fe49eca5';
    tiny.loadJs(b, "utf-8", function () {
        if (!(typeof tab_data == "undefined" || tab_data == null)) {
            var dataArr = tab_data.data;
            if (dataArr.length == 1) {
                //资金流
                var _dh = dataArr[0].split(',');
                //算和值
                var data = [];
                data[0] = (_dh[5] == "") ? "" : ((parseFloat(_dh[5])) / 10000).toFixed(4);//今日主力净流入
                data[1] = (_dh[23] == "") ? "" : ((parseFloat(_dh[23]))).toFixed(2);//主力净比
                data[2] = (_dh[9] == "") ? "" : ((parseFloat(_dh[9])) / 10000).toFixed(4);//今日超大单净流入
                data[3] = (_dh[10] == "") ? "" : ((parseFloat(_dh[10]))).toFixed(2);//超大单净比
                data[4] = (_dh[13] == "") ? "" : ((parseFloat(_dh[13])) / 10000).toFixed(4);//今日大单净流入
                data[5] = (_dh[14] == "") ? "" : ((parseFloat(_dh[14]))).toFixed(2);//大单净比
                data[6] = (_dh[17] == "") ? "" : ((parseFloat(_dh[17])) / 10000).toFixed(4);//今日中单净流入
                data[7] = (_dh[18] == "") ? "" : ((parseFloat(_dh[18]))).toFixed(2);//中单净比
                data[8] = (_dh[21] == "") ? "" : ((parseFloat(_dh[21])) / 10000).toFixed(4);//今日小单净流入
                data[9] = (_dh[22] == "") ? "" : ((parseFloat(_dh[22]))).toFixed(2);//小单净比
                data[10] = '';//
                data[11] = '';//
                data[12] = (_dh[7] == "") ? "" : ((parseFloat(_dh[7])) / 100000000).toFixed(4);//超大单流入
                data[13] = (_dh[8] == "") ? "" : ((parseFloat(_dh[8])) / 100000000).toFixed(4);//超大单流处
                data[14] = (_dh[11] == "") ? "" : ((parseFloat(_dh[11])) / 100000000).toFixed(4);//大单流入
                data[15] = (_dh[12] == "") ? "" : ((parseFloat(_dh[12])) / 100000000).toFixed(4);//大单流出
                data[16] = (_dh[15] == "") ? "" : ((parseFloat(_dh[15])) / 100000000).toFixed(4);//中单流入
                data[17] = (_dh[16] == "") ? "" : ((parseFloat(_dh[16])) / 100000000).toFixed(4);//中单流出
                data[18] = (_dh[19] == "") ? "" : ((parseFloat(_dh[19])) / 100000000).toFixed(4);//小单流入
                data[19] = (_dh[20] == "") ? "" : ((parseFloat(_dh[20])) / 100000000).toFixed(4);//小单流出
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
function hqUpdate() {
    if (!$("shhq") || !$("szhq")) {
        zsUpdate();
        return;
    }
    var url = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/cache.aspx?Type=c1&Reference=flm";
    var spanCache = '<b>{dqzs}</b>{zdqs}<b>{zdzs}</b>{zdqs}<b>{zdf}&nbsp;&nbsp;{cje}</b> ';
    var b = url + "&rt=" + Math.floor((new Date().getTime()) / 30000);
    var c, h, d, type;
    var e = function (g, a) {
        var f;
        if (isNaN(g) || isNaN(a)) {
            return "-"
        }
        if (parseFloat(g) > a) {
            f = "red"
        }
        else {
            if (parseFloat(g) < a) {
                f = "green"
            }
            else {
                f = "" + g + ""
            }
        }
        return f
    };
    tiny.loadJs(b, "utf-8", function () {
        if (!(typeof C1Cache == "undefined" || C1Cache == null)) {
            c = C1Cache.quotation[0].split(",");
            if (c.length < 8) {
                c = ["-", "-", "-", "-", "-", "-", "-"]
            }
            type = e(c[5], 0);
            d = spanCache.replace(/{dqzs}/ig, c[2]);
            d = d.replace(/{zdqs}/ig, type == "green" ? " <span class=\"arr\">↓</span>" : " <span class=\"arr\">↑</span>");
            d = d.replace(/{zdzs}/ig, c[5]);
            d = d.replace(/{zdf}/ig, c[6]);
            d = d.replace(/{cje}/ig, !isNaN(c[3]) ? (parseFloat(c[3]) / 10000).toFixed(2) : c[3]);
            $("shhq").className = type;
            $("shhq").innerHTML = d;
            $("shz").innerHTML = C1Cache.record[0].split(",")[0];
            $("shp").innerHTML = C1Cache.record[0].split(",")[1];
            $("shd").innerHTML = C1Cache.record[0].split(",")[2];

            c = C1Cache.quotation[1].split(",");
            if (c.length < 8) {
                c = ["-", "-", "-", "-", "-", "-", "-"]
            }
            type = e(c[5], 0);
            d = spanCache.replace(/{dqzs}/ig, c[2]);
            d = d.replace(/{zdqs}/ig, type == "green" ? " <span class=\"arr\">↓</span>" : " <span class=\"arr\">↑</span>");
            d = d.replace(/{zdzs}/ig, c[5]);
            d = d.replace(/{zdf}/ig, c[6]);
            d = d.replace(/{cje}/ig, !isNaN(c[3]) ? (parseFloat(c[3]) / 10000).toFixed(2) : c[3]);
            $("szhq").className = type;
            $("szhq").innerHTML = d;
            $("szz").innerHTML = C1Cache.record[1].split(",")[0];
            $("szp").innerHTML = C1Cache.record[1].split(",")[1];
            $("szd").innerHTML = C1Cache.record[1].split(",")[2];
        }
    }, true)
}

function zsUpdate() {
    var url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&sty=DCARQRQB&st=z&sr=&p=&ps=&cb=&js=var zjlx_hq = (x)&token=1942f5da9b46b069953c873404aad4b5&cmd=" + _dpzs.replace("zs", "") + (_dpzs.substr(0, 5) == "zs399" ? "2" : "1");
    var b = url + "&rt=" + Math.floor((new Date().getTime()) / 30000); var c, d;
    var e = function (f, g, a) { if (isNaN(g) || isNaN(a)) { return "<span>" + f + "</span>" } if (parseFloat(g) > a) { f = '<span class="red">' + f + "</span>" } else { if (parseFloat(g) < a) { f = '<span class="green">' + f + "</span>" } else { f = "<span>" + f + "</span>" } } return f };
    tiny.loadJs(b, "utf-8", function () {
        if (!(typeof zjlx_hq == "undefined" || zjlx_hq == null)) {
            c = zjlx_hq.split(",");
            if (c.length < 16) { c = [_stockMarke, _stockCode, _stockName, "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"] }
            d = tableCache.replace(/{code}/ig, _dpzs).replace(/{codes}/ig, _dpzs.replace("zs", ""));
            d = d.replace(/{name}/ig, c[2])
                .replace(/{price}/ig, e(c[3], c[4], 0))
                .replace(/{change}/ig, e(c[4], c[4], 0))
                .replace(/{percent}/ig, e(c[5], c[4], 0))
                .replace(/{zf}/ig, c[8])
                .replace(/{amount}/ig, c[6] == "-" ? "-" : (c[6] / 100).toFixed(0))
                .replace(/{volume}/ig, c[7] == "-" ? "-" : (c[7] / 10000).toFixed(0))
                .replace(/{last}/ig, e(c[9]))
                .replace(/{open}/ig, e(c[10], c[10], c[9]))
                .replace(/{height}/ig, e(c[11], c[11], c[9]))
                .replace(/{low}/ig, e(c[12], c[12], c[9]))
                .replace(/{hsl}/ig, c[13])
                .replace(/{lb}/ig, c[14])
                .replace(/{syl}/ig, (c[15] < 0 ? "--" : c[15]));
            $("zjlx_zshqcont").innerHTML = d;
        }
    }, true);
}
function hqInterval() {
    var e = new Date();
    try {
        e = Eastmoney.Time.now()
    }
    catch (g) {
    }
    var h = parseInt(tiny.dateFormat(e, "HHmm") * 1);
    var f = e.getDay();
    if (!(h <= 914 || (h >= 1145 && h <= 1259) || h >= 1515 || f > 5)) {
        try {
            hqUpdate();
        }
        catch (g) {
        }
    }
}