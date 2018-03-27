var centerDef = {
    timeout: false,
    hy_interval:new Date().getTime(),
    gg_interval:new Date().getTime(),
    dp_interval: new Date().getTime(),
    time_stamp: Math.floor((new Date().getTime()) / 60000),
    interval:120 * 1000,
    initialize: function () {
        this.dpzjlUpdate();
        this.bindEvent();

        setInterval(this.bkzjlInterval, this.interval);
        setInterval(this.dpzjlInterval, this.interval);
        setInterval(this.ggzjlInterval, this.interval);
    },
    ggzjlUpdate: function () {
        var self = this;
        var url = "http://data.eastmoney.com/zjlx/data.aspx?type=detail&cate=0&day=1&sortType=6&sortRule=-1&pageSize=5&page=1&jsname=";
        var jsname = self.getCode(10);
        url += jsname;

        var tablecache = '<tr class="{odd}" onmouseover="this.className=\'over\'" onmouseout="this.className=\'{odd}\'"><td>{i}</td><td><a href="http://quote.eastmoney.com/{market}{code}.html">{code}</a></td><td><a href="http://quote.eastmoney.com/{market}{code}.html">{name}</a></td><td><a class="red" href="http://data.eastmoney.com/zjlx/{code}.html">大单详情</a> <a href="http://guba.eastmoney.com/topic,{code}.html">股吧</a> <a href="http://data.eastmoney.com/report/{code}.html">研报</a></td><td>{price}</td><td>{precent}</td><td>{zljlr}</td><td>{zljzb}</td><td>{cddjlr}</td><td>{cddjzb}</td><td>{ddjlr}</td><td>{ddjzb}</td><td>{zdjlr}</td><td>{zdjzb}</td><td>{xdjlr}</td><td>{xdjzb}</td></tr>';
        var b = url + "&rt=" + Math.floor((new Date().getTime()) / 30000);
        var c, h, d;
        tiny.loadJs(b, "gb2312", function () {
            if (!(eval("typeof " + jsname) == "undefined") || eval("typeof " + jsname == null)) {
                c = eval(jsname).data;
                var _totle = c.length;
                var _trs = [];
                var _tr = "";
                var odd = "odd";
                _totle = _totle > 5 ? 5 : _totle;
                for (var i = 0; i < _totle; i++) {
                    var data = c[i].split(",");
                    var _type = data[2],
                        _price = data[4],
                        odd = odd == "odd" ? "" : "odd";

                    var zljlr = data[6],
                        zljzb = data[7],
                        cddjlr = data[8],
                        cddjzb = data[9],
                        ddjlr = data[10],
                        ddjzb = data[11],
                        zdjlr = data[12],
                        zdjzb = data[13],
                        xdjlr = data[14],
                        xdjzb = data[15],
                        _present = data[5];

                    _price = (_price == "-" || isNaN(_price)) ? _price : (_type > 4) ? parseFloat(_price).toFixed(2) : parseFloat(_price).toFixed(2);

                    var _color = (_present != "-" && parseFloat(_present) > 0) ? "red" : "";
                    _color = (_present != "-" && parseFloat(_present) < 0) ? "green" : _color;
                    _present = (isNaN(_present)) ? _present : parseFloat(_present).toFixed(2) + "%";

                    var c_zljlr = (zljlr != "-" && parseFloat(zljlr) > 0) ? "red" : "";
                    c_zljlr = (zljlr != "-" && parseFloat(zljlr) < 0) ? "green" : c_zljlr;

                    var c_zljzb = (zljzb != "-" && parseFloat(zljzb) > 0) ? "red" : "";
                    c_zljzb = (zljzb != "-" && parseFloat(zljzb) < 0) ? "green" : c_zljzb;
                    zljzb = (isNaN(zljzb)) ? zljzb : parseFloat(zljzb).toFixed(2) + "%";

                    var c_cddjlr = (cddjlr != "-" && parseFloat(cddjlr) > 0) ? "red" : "";
                    c_cddjlr = (cddjlr != "-" && parseFloat(cddjlr) < 0) ? "green" : c_cddjlr;

                    var c_cddjzb = (cddjzb != "-" && parseFloat(cddjzb) > 0) ? "red" : "";
                    c_cddjzb = (cddjzb != "-" && parseFloat(cddjzb) < 0) ? "green" : c_cddjzb;
                    cddjzb = (isNaN(cddjzb)) ? cddjzb : parseFloat(cddjzb).toFixed(2) + "%";

                    var c_ddjlr = (ddjlr != "-" && parseFloat(ddjlr) > 0) ? "red" : "";
                    c_ddjlr = (ddjlr != "-" && parseFloat(ddjlr) < 0) ? "green" : c_ddjlr;

                    var c_ddjzb = (ddjzb != "-" && parseFloat(ddjzb) > 0) ? "red" : "";
                    c_ddjzb = (ddjzb != "-" && parseFloat(ddjzb) < 0) ? "green" : c_ddjzb;
                    ddjzb = (isNaN(ddjzb)) ? ddjzb : parseFloat(ddjzb).toFixed(2) + "%";

                    var c_zdjlr = (zdjlr != "-" && parseFloat(zdjlr) > 0) ? "red" : "";
                    c_zdjlr = (zdjlr != "-" && parseFloat(zdjlr) < 0) ? "green" : c_zdjlr;

                    var c_zdjzb = (zdjzb != "-" && parseFloat(zdjzb) > 0) ? "red" : "";
                    c_zdjzb = (zdjzb != "-" && parseFloat(zdjzb) < 0) ? "green" : c_zdjzb;
                    zdjzb = (isNaN(zdjzb)) ? zdjzb : parseFloat(zdjzb).toFixed(2) + "%";

                    var c_xdjlr = (xdjlr != "-" && parseFloat(xdjlr) > 0) ? "red" : "";
                    c_xdjlr = (xdjlr != "-" && parseFloat(xdjlr) < 0) ? "green" : c_xdjlr;

                    var c_xdjzb = (xdjzb != "-" && parseFloat(xdjzb) > 0) ? "red" : "";
                    c_xdjzb = (xdjzb != "-" && parseFloat(xdjzb) < 0) ? "green" : c_xdjzb;
                    xdjzb = (isNaN(xdjzb)) ? xdjzb : parseFloat(xdjzb).toFixed(2) + "%";

                    _tr = tablecache.replace(/{odd}/ig, odd)
                        .replace(/{i}/ig, 1 + i)
                        .replace(/{code}/ig, data[0])
                        .replace(/{market}/ig, (data[1] == 1) ? "sh" : "sz")
                        .replace(/{name}/ig, data[3].replace(/\s+/, ""))
                        .replace(/{price}/ig, "<span class=\"" + _color + "\">" + _price + "</span>")
                        .replace(/{precent}/ig, "<span class=\"" + _color + "\">" + _present + "</span>")
                        .replace(/{zljlr}/ig, "<span class=\"" + c_zljlr + "\">" + zljlr + "</span>")
                        .replace(/{zljzb}/ig, "<span class=\"" + c_zljzb + "\">" + zljzb + "</span>")
                        .replace(/{cddjlr}/ig, "<span class=\"" + c_cddjlr + "\">" + cddjlr + "</span>")
                        .replace(/{cddjzb}/ig, "<span class=\"" + c_cddjzb + "\">" + cddjzb + "</span>")
                        .replace(/{ddjlr}/ig, "<span class=\"" + c_ddjlr + "\">" + ddjlr + "</span>")
                        .replace(/{ddjzb}/ig, "<span class=\"" + c_ddjzb + "\">" + ddjzb + "</span>")
                        .replace(/{zdjlr}/ig, "<span class=\"" + c_zdjlr + "\">" + zdjlr + "</span>")
                        .replace(/{zdjzb}/ig, "<span class=\"" + c_zdjzb + "\">" + zdjzb + "</span>")
                        .replace(/{xdjlr}/ig, "<span class=\"" + c_xdjlr + "\">" + xdjlr + "</span>")
                        .replace(/{xdjzb}/ig, "<span class=\"" + c_xdjzb + "\">" + xdjzb + "</span>")
                    _trs.push(_tr);
                }
                var re = /(<tbody[^>]*?>)([\s\S]*)?(<\/tbody>)/ig;
                var _table = document.getElementById("ggzjl_dt");
                var tableHtml = _table.innerHTML;

                var tbodyCont;
                tableHtml.replace(re, function ($0, $1, $2, $3) {
                    tbodyCont = $2;
                });

                if (_table.outerHTML)
                    _table.outerHTML = _table.outerHTML.replace(tbodyCont, _trs.join(''));
                else
                    _table.innerHTML = _table.innerHTML.replace(tbodyCont, _trs.join(''));
                self.gg_interval = new Date().getTime();
            }
        }, true)
    },
    ggzjlInterval: function () {
        var self = this;
        var e = new Date();
        try {
            e = Eastmoney.Time.now()
        }
        catch (g) {
        }
        var h = parseInt(tiny.dateFormat(e, "HHmm") * 1);
        var f = e.getDay();
        if (!(h <= 914 || (h >= 1145 && h <= 1259) || h >= 1515 || f > 5)) {
            if (self.isVisible("ggzjl_dt") && new Date().getTime() - self.gg_interval >= self.interval) {
                try {
                    self.ggzjlUpdate()
                }
                catch (g) {
                }
            }
        }
    },
    bkzjlUpdate: function () {
        var self = this;
        var rt = Math.random();
        var url = "http://data.eastmoney.com/Bkzj/data.aspx?category=2&type=BKFundflow&day=1&page=1&pageSize=5&sortType=B&sortRule=-1&jsname=";
        var jsname = self.getCode(10);
        url += jsname;
        var tablecache = '<tr class="{odd}" onmouseover="this.className=\'over\'" onmouseout="this.className=\'{odd}\'"><td>{i}</td><td><a href="http://quote.eastmoney.com/center/list.html#28002{bkCode}_0_2">{bkName}</a></td><td><a href="http://data.eastmoney.com/bkzj/{bkCode}.html" class="red">大单详情</a> <a href="http://guba.eastmoney.com/type,{bkCode}.html">股吧</a></td><td class=\"tdnumber\">{jrzdf}</td><td class=\"tdnumber\">{zljlr}</td><td class=\"tdnumber\">{zljzb}</td><td>{zljlrzdg}</td></tr>';
        var b = url + "&rt=" + Math.floor((new Date().getTime()) / 30000);
        var c, h, d;
        tiny.loadJs(b, "gb2312", function () {
            if (!(eval("typeof " + jsname) == "undefined") || eval("typeof " + jsname == null)) {
                c = eval(jsname).data;
                var _totle = c.length;
                var _trs = [];
                var _tr = "";
                var odd = "odd";
                _totle = _totle > 5 ? 5 : _totle;
                var bkcode = c[0].split(",")[0];
                var bkname = c[0].split(",")[1];
                document.getElementById("hqli").onclick = function () { window.open("http://quote.eastmoney.com/center/list.html#28002" + bkcode + "_0_2") };
                document.getElementById("ybli").onclick = function () { window.open("http://data.eastmoney.com/report/" + bkcode + "yb.html") };
                document.getElementById("zxli").onclick = function () { window.open("http://stock.eastmoney.com/hangye/" + bkcode + ".html") };
                document.getElementById("gbli").onclick = function () { window.open("http://guba.eastmoney.com/type," + bkcode + ".html") };

                document.getElementById("hyimg").src = "http://hq2qt.eastmoney.com/EM_CapitalPictureProducter/Picture/" + bkcode + "rs.png?rt=" + rt;
                for (var i = 0; i < _totle; i++) {
                    var data = c[i].split(",");
                    if (data.length < 15)
                        continue;
                    odd = odd == "odd" ? "" : "odd";
                    var _bkCode = data[0],
                        _bkName = data[1],
                        __percent = data[2],
                        _percent = data[2], //最新价
                        _jlr = data[3], //涨跌幅
                        _jlrzb = data[4],
                        _superjlr = data[5],
                        _superjzb = data[6],                              // 流入
                        _maxStockCode = data[13],
                        _maxStockName = data[14],
                        _zljlrzdg;

                    if (_maxStockCode == "-" || _maxStockName == "-" || _maxStockCode == "" || _maxStockName == "") {
                        _zljlrzdg = "--";
                    } else {
                        _zljlrzdg = "<a href=\"http://data.eastmoney.com/zjlx/" + _maxStockCode + ".html\">" + _maxStockName + "</a>";
                    }
                    if (isNaN(_percent) || _percent == "") {
                        _percent = "--";
                    } else {
                        _percent = _percent.indexOf("%") == -1 ? _percent + "%" : _percent;
                        __percent = parseFloat(__percent);
                        _percent = (__percent > 0) ? "<span class=\"red\">" + _percent + "</span>" : ((__percent < 0) ? "<span class=\"green \">" + _percent + "</span>" : "<span class=\"\">" + _percent + "</span>");
                    }
                    _jlr = (parseFloat(_jlr) > 0) ? "<span class=\"red \">" + _jlr + "</span>" : ((parseFloat(_jlr) < 0) ? "<span class=\"green \">" + _jlr + "</span>" : "<span class=\"\">" + _jlr + "</span>");
                    _jlrzb = (_jlrzb > 0) ? "<span class=\"red \">" + _jlrzb + "%</span>" : ((_jlrzb < 0) ? "<span class=\"green tnumber\">" + _jlrzb + "%</span>" : "<span class=\"\">" + _jlrzb + "%</span>");
                    _superjlr = (parseFloat(_superjlr) > 0) ? "<span class=\"red \">" + _superjlr + "</span>" : ((parseFloat(_superjlr) < 0) ? "<span class=\"green \">" + _superjlr + "</span class=\"\">" : "<span>" + _superjlr + "</span>");
                    _superjzb = (_superjzb > 0) ? "<span class=\"red \">" + _superjzb + "%</span>" : ((_superjzb < 0) ? "<span class=\"green \">" + _superjzb + "%</span class=\"\">" : "<span>" + _superjzb + "%</span>");

                    _tr = tablecache.replace(/{odd}/ig, odd)
                        .replace(/{i}/ig, 1 + i)
                        .replace(/{bkCode}/ig, _bkCode)
                        .replace(/{bkName}/ig, _bkName)
                        .replace(/{_bkName}/ig, escape(_bkName))
                        .replace(/{jrzdf}/ig, _percent)
                        .replace(/{zljlr}/ig, _jlr)
                        .replace(/{zljzb}/ig, _jlrzb)
                        .replace(/{zljlrzdg}/ig, _zljlrzdg);

                    _trs.push(_tr);
                }
                var re = /(<tbody[^>]*?>)([\s\S]*)?(<\/tbody>)/ig;
                var _table = document.getElementById("hyzjl_dt");
                var tableHtml = _table.innerHTML;

                var tbodyCont;
                tableHtml.replace(re, function ($0, $1, $2, $3) {
                    tbodyCont = $2;
                });

                if (_table.outerHTML)
                    _table.outerHTML = _table.outerHTML.replace(tbodyCont, _trs.join(''));
                else
                    _table.innerHTML = _table.innerHTML.replace(tbodyCont, _trs.join(''));
                self.hy_interval = new Date().getTime();
            }
        }, true)
    },
    bkzjlInterval: function () {
        var self = this;
        var e = new Date();
        try {
            e = Eastmoney.Time.now()
        }
        catch (g) {
        }
        var h = parseInt(tiny.dateFormat(e, "HHmm") * 1);
        var f = e.getDay();
        if (!(h <= 914 || (h >= 1145 && h <= 1259) || h >= 1515 || f > 5)) {
            if (self.isVisible("hyzjl_dt") && new Date().getTime() - self.hy_interval >= self.interval) {
                try {
                    self.bkzjlUpdate()
                }
                catch (g) {
                }
            }
        }
    },
    dpzjlUpdate: function () {
        var self = this;
        var rt = Math.random();
        document.getElementById("dpimg").src = "http://cmsjs.eastmoney.com/data/images/A%E8%82%A1%E6%B5%81%E5%85%A5%E8%B5%84%E9%87%91.png?rt=" + rt
        //var b = "http://s1.dfcfw.com/js/index.js?rt=" + Math.random();  //by zhaoyanhua 2012.12.07//old 修改于20161209
        var b = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=0000011,3990012&sty=CTBFTA&st=z&sr=&p=&ps=&cb=&js=var tab_data=({data:[(x)]})&token=70f12f2f4f091e459a279469fe49eca5";
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
                    data[1] = (data[0] * 100 / total).toFixed(2);//主力净比


                    data[2] = (_dh[9] == "" || _ds[9] == "") ? "" : ((parseFloat(_dh[9]) + parseFloat(_ds[9])) / 10000).toFixed(4);//今日超大单净流入
                    data[3] = (data[2] * 100 / total).toFixed(2);//主力净比


                    data[4] = (_dh[13] == "" || _ds[13] == "") ? "" : ((parseFloat(_dh[13]) + parseFloat(_ds[13])) / 10000).toFixed(4);//今日大单净流入
                    data[5] = (data[4] * 100 / total).toFixed(2);//主力净比

                    data[6] = (_dh[17] == "" || _ds[17] == "") ? "" : ((parseFloat(_dh[17]) + parseFloat(_ds[17])) / 10000).toFixed(4);//今日中单净流入
                    data[7] = (data[6] * 100 / total).toFixed(2);//主力净比

                    data[8] = (_dh[21] == "" || _ds[21] == "") ? "" : ((parseFloat(_dh[21]) + parseFloat(_ds[21])) / 10000).toFixed(4);//今日小单净流入
                    data[9] = (data[8] * 100 / total).toFixed(2);//主力净比

                    data[10] = ((parseFloat(_dh[7]) + parseFloat(_dh[11]) + parseFloat(_ds[7]) + parseFloat(_ds[11])) / 100000000).toFixed(4);//主力流入
                    data[11] = ((parseFloat(_dh[8]) + parseFloat(_dh[12]) + parseFloat(_ds[8]) + parseFloat(_ds[12])) / 100000000).toFixed(4);//主力流入
                    data[12] = (_dh[7] == "" || _ds[7] == "") ? "" : ((parseFloat(_dh[7]) + parseFloat(_ds[7])) / 100000000).toFixed(4);//超大单流入
                    data[13] = (_dh[8] == "" || _ds[8] == "") ? "" : ((parseFloat(_dh[8]) + parseFloat(_ds[8])) / 100000000).toFixed(4);//超大单流处
                    data[14] = (_dh[11] == "" || _ds[11] == "") ? "" : ((parseFloat(_dh[11]) + parseFloat(_ds[11])) / 100000000).toFixed(4);//大单流入
                    data[15] = (_dh[12] == "" || _ds[12] == "") ? "" : ((parseFloat(_dh[12]) + parseFloat(_ds[12])) / 100000000).toFixed(4);//大单流出
                    data[16] = (_dh[15] == "" || _ds[15] == "") ? "" : ((parseFloat(_dh[15]) + parseFloat(_ds[15])) / 100000000).toFixed(4);//中单流入
                    data[17] = (_dh[16] == "" || _ds[16] == "") ? "" : ((parseFloat(_dh[16]) + parseFloat(_ds[16])) / 100000000).toFixed(4);//中单流出
                    data[18] = (_dh[19] == "" || _ds[19] == "") ? "" : ((parseFloat(_dh[19]) + parseFloat(_ds[19])) / 100000000).toFixed(4);//小单流入
                    data[19] = (_dh[20] == "" || _ds[20] == "") ? "" : ((parseFloat(_dh[20]) + parseFloat(_ds[20])) / 100000000).toFixed(4);//小单流出


                    //data[0] = ((parseFloat(_dh[12]) + parseFloat(_ds[12])) / 10000).toFixed(4);//今日主力净流入
                    //data[1] = ((parseFloat(_dh[17]) + parseFloat(_ds[17]))).toFixed(2);//主力净比
                    //data[2] = ((parseFloat(_dh[13]) + parseFloat(_ds[13])) / 10000).toFixed(4);//今日超大单净流入
                    //data[3] = ((parseFloat(_dh[18]) + parseFloat(_ds[18]))).toFixed(2);//超大单净比
                    //data[4] = ((parseFloat(_dh[14]) + parseFloat(_ds[14])) / 10000).toFixed(4);//今日大单净流入
                    //data[5] = ((parseFloat(_dh[19]) + parseFloat(_ds[19]))).toFixed(2);//大单净比
                    //data[6] = ((parseFloat(_dh[15]) + parseFloat(_ds[15])) / 10000).toFixed(4);//今日中单净流入
                    //data[7] = ((parseFloat(_dh[20]) + parseFloat(_ds[20]))).toFixed(2);//中单净比
                    //data[8] = ((parseFloat(_dh[16]) + parseFloat(_ds[16])) / 10000).toFixed(4);//今日小单净流入
                    //data[9] = ((parseFloat(_dh[21]) + parseFloat(_ds[21]))).toFixed(2);//小单净比

                    //data[10] = ((parseFloat(_dh[2]) + parseFloat(_ds[2])) / 10000).toFixed(4);;//主力流入
                    //data[11] = ((parseFloat(_dh[3]) + parseFloat(_ds[3])) / 10000).toFixed(4);;//主力留出
                    //data[12] = ((parseFloat(_dh[4]) + parseFloat(_ds[4])) / 100000000).toFixed(4);//超大单流入
                    //data[13] = ((parseFloat(_dh[5]) + parseFloat(_ds[5])) / 100000000).toFixed(4);//超大单流处
                    //data[14] = ((parseFloat(_dh[6]) + parseFloat(_ds[6])) / 100000000).toFixed(4);//大单流入
                    //data[15] = ((parseFloat(_dh[7]) + parseFloat(_ds[7])) / 100000000).toFixed(4);//大单流出
                    //data[16] = ((parseFloat(_dh[8]) + parseFloat(_ds[8])) / 100000000).toFixed(4);//中单流入
                    //data[17] = ((parseFloat(_dh[9]) + parseFloat(_ds[9])) / 100000000).toFixed(4);//中单流出
                    //data[18] = ((parseFloat(_dh[10]) + parseFloat(_ds[10])) / 100000000).toFixed(4);//小单流入
                    //data[19] = ((parseFloat(_dh[11]) + parseFloat(_ds[11])) / 100000000).toFixed(4);//小单流出
                    $("data_zljlr").innerHTML = isNaN(data[0]) ? "" : data[0];
                    $("data_zljlr").className = (data[0] > 0) ? "red" : ((data[0] < 0) ? "green" : "");
                    $("data_zljzb").innerHTML = isNaN(data[1]) ? "0.00%" : data[1] + "%";
                    $("data_zljzb").className = (data[1] > 0) ? "red" : ((data[1] < 0) ? "green" : "");
                    $("data_cdjlr").innerHTML = isNaN(data[2]) ? "" : data[2];
                    $("data_cdjlr").className = (data[2] > 0) ? "red" : ((data[2] < 0) ? "green" : "");
                    $("data_cdjzb").innerHTML = isNaN(data[3]) ? "0.00%" : data[3] + "%";
                    $("data_cdjzb").className = (data[3] > 0) ? "red" : ((data[3] < 0) ? "green" : "");
                    $("data_ddjlr").innerHTML = isNaN(data[4]) ? "" : data[4];
                    $("data_ddjlr").className = (data[4] > 0) ? "red" : ((data[4] < 0) ? "green" : "");
                    $("data_ddjzb").innerHTML = isNaN(data[5]) ? "0.00%" : data[5] + "%";
                    $("data_ddjzb").className = (data[5] > 0) ? "red" : ((data[5] < 0) ? "green" : "");
                    $("data_zdjlr").innerHTML = isNaN(data[6]) ? "" : data[6];
                    $("data_zdjlr").className = (data[6] > 0) ? "red" : ((data[6] < 0) ? "green" : "");
                    $("data_zdjzb").innerHTML = isNaN(data[7]) ? "0.00%" : data[7] + "%";
                    $("data_zdjzb").className = (data[7] > 0) ? "red" : ((data[7] < 0) ? "green" : "");
                    $("data_xdjlr").innerHTML = isNaN(data[8]) ? "" : data[8];
                    $("data_xdjlr").className = (data[8] > 0) ? "red" : ((data[8] < 0) ? "green" : "");
                    $("data_xdjzb").innerHTML = isNaN(data[9]) ? "0.00%" : data[9] + "%";
                    $("data_xdjzb").className = (data[9] > 0) ? "red" : ((data[9] < 0) ? "green" : "");
                    $("data_cdlr").innerHTML = isNaN(data[12]) ? "" : data[12];
                    $("data_ddlr").innerHTML = isNaN(data[14]) ? "" : data[14];
                    $("data_zdlr").innerHTML = isNaN(data[16]) ? "" : data[16];
                    $("data_xdlr").innerHTML = isNaN(data[18]) ? "" : data[18];
                    $("data_cdlc").innerHTML = isNaN(data[13]) ? "" : data[13];
                    $("data_ddlc").innerHTML = isNaN(data[15]) ? "" : data[15];
                    $("data_zdlc").innerHTML = isNaN(data[17]) ? "" : data[17];
                    $("data_xdlc").innerHTML = isNaN(data[19]) ? "" : data[19];
                    $("data_zllr").innerHTML = isNaN(data[10]) ? "" : data[10];
                    $("data_zllc").innerHTML = isNaN(data[11]) ? "" : data[11];

                    self.dp_interval = new Date().getTime();
                }
            }
        }, true)
    },
    dpzjlInterval: function () {
        var self = this;
        var e = new Date();
        try {
            e = Eastmoney.Time.now()
        }
        catch (g) {
        }
        var h = parseInt(tiny.dateFormat(e, "HHmm") * 1);
        var f = e.getDay();
        if (!(h <= 914 || (h >= 1145 && h <= 1259) || h >= 1515 || f > 5)) {
            if (self.isVisible("dpzjl_dt") && new Date().getTime() - self.dp_interval >= self.interval) {
                try {
                    self.dpzjlUpdate()
                }
                catch (g) {
                }
            }
        }
    },
    getCode: function (num) {
        var self = this;
        var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        var codes = str.split('');
        num = num || 6;
        var code = "";
        for (var i = 0; i < num; i++) {
            code += codes[Math.floor(Math.random() * 52)];
        }
        return code;
    },
    getAbsPoint: function (id) {
        var self = this;
        var e = document.getElementById(id);
        var x = e.offsetLeft;
        var y = e.offsetTop;
        while (e = e.offsetParent) {
            x += e.offsetLeft;
            y += e.offsetTop;
        }
        return { "x": x, "y": y };
    },
    isVisible: function (id) {
        var self = this;
        var heightdiff = self.getAbsPoint(id).y - document.body.scrollTop - document.documentElement.scrollTop;
        if (heightdiff + document.getElementById(id).clientHeight > 5 && document.documentElement.clientHeight - heightdiff > 35) {
            return true;
        }
        return false;
    },
    bindEvent: function () {
        var self = this;
        window.onscroll = function () {
            if (self.timeout) {
                clearTimeout(self.timeout);
            }
            self.timeout = setTimeout(function () {
                if (self.isVisible("ggzjl_dt") && new Date().getTime() - self.gg_interval > self.interval) {
                    setTimeout(self.ggzjlInterval, 10);
                }
                if (self.isVisible("hyzjl_dt") && new Date().getTime() - self.hy_interval > self.interval) {
                    setTimeout(self.bkzjlInterval, 10);
                }
                if (self.isVisible("dpzjl_dt") && new Date().getTime() - self.dp_interval > self.interval) {
                    setTimeout(self.dpzjlInterval, 10);
                }
            }, 100);
        }
    }
}

centerDef.initialize();