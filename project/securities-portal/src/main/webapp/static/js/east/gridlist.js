var cache, pageSize = 5000;

function ITemplate(id, header, row, footer, render, tpl) {
    //定义模板接口成员
    this.id = id;
    this.header = header;
    this.footer = footer;
    this.row = row;
    this.render = render;
    this.tpl = tpl;
}

//行情字典2.0数据CSS
function CssClass(dataRow) {
    var css = new StockCssClass(dataRow);
    this.priceCss = css.price; //现价
    this.changeCss = css.change; //涨跌
    this.openCss = css.open; //今开
    this.maxCss = css.high; //最高
    this.minCss = css.low; //最低
}

//三板 css
function SanBanCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[3], dataRow[8]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[4]); //涨跌;
    this.openCss = tools.getCssByPreclose(dataRow[10], dataRow[8]); //今开
    this.maxCss = tools.getCssByPreclose(dataRow[11], dataRow[8]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[12], dataRow[8]); //最低
}

//新人民币 css
function RMBCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[2], dataRow[8]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[3]); //涨跌;
    this.openCss = tools.getCssByPreclose(dataRow[7], dataRow[8]); //今开
    this.maxCss = tools.getCssByPreclose(dataRow[5], dataRow[8]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[6], dataRow[8]); //最低
}

//港股 港股通 css
function NHKCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[3], dataRow[12]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[4]); //涨跌;
    this.openCss = tools.getCssByPreclose(dataRow[11], dataRow[12]); //今开
    this.maxCss = tools.getCssByPreclose(dataRow[13], dataRow[12]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[14], dataRow[12]); //最低
}

//港股 css
function HKCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[2], dataRow[10]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[3]); //涨跌;
    this.openCss = tools.getCssByPreclose(dataRow[7], dataRow[10]); //今开
    this.maxCss = tools.getCssByPreclose(dataRow[8], dataRow[10]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[9], dataRow[10]); //最低
}

//成分股 css
function CFGCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[3], dataRow[9]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[4]); //涨跌;
    this.openCss = tools.getCssByPreclose(dataRow[10], dataRow[9]); //今开
    this.maxCss = tools.getCssByPreclose(dataRow[11], dataRow[9]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[12], dataRow[9]); //最低
}

//债券 银行间 css
function YHJCssClass(dataRow) {
    var tools = quote;
    this.changeCss = tools.getCssByChange(dataRow[4].replace("%", "")); //涨跌;
}

//新股
function XinGuCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[3], dataRow[9]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[4]); //涨跌;
    this.openCss = tools.getCssByPreclose(dataRow[10], dataRow[9]); //今开
    this.maxCss = tools.getCssByPreclose(dataRow[11], dataRow[9]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[12], dataRow[9]); //最低
}

//互联网中国css
function ItchinaCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[2], dataRow[6]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[3]); //涨跌;
    this.maxCss = tools.getCssByPreclose(dataRow[7], dataRow[6]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[8], dataRow[6]); //最低
    this.openCss = tools.getCssByPreclose(dataRow[5], dataRow[6]); //今开

}

//股指期货
function NewFutureCssClass(dataRow) {
    var tools = quote;
    this.priceCss = tools.getCssByPreclose(dataRow[3], dataRow[12]); //现价;
    this.changeCss = tools.getCssByChange(dataRow[5]); //涨跌;
    this.maxCss = tools.getCssByPreclose(dataRow[13], dataRow[12]); //最高
    this.minCss = tools.getCssByPreclose(dataRow[14], dataRow[12]); //最低
    this.openCss = tools.getCssByPreclose(dataRow[11], dataRow[12]); //今开

}


var rowHelper = {

    //行背景
    cssClass: function (index) {
        return index % 2 != 0 ? " class=\"bg\"" : "";
    },

    //序号
    number: function (page, size, index) {
        page = page || 1;
        return ((page - 1) * size) + (index + 1);
    },

    //指定是否闪烁
    jssz: function (tpl, i, dataRow) {
        var jssz = "", cacheRow = [];
        if (typeof cache != "undefined") {
            cacheRow = String(cache[i]).split(",");
            if (cacheRow[5] != dataRow[5]) {
                jssz = "jssz ";
            }
        }
        tpl.set("jssz", jssz);
    },

    //市场前缀SH/SZ
    getMarketPrefix: function (stockId) {
        var market = stockId.substr(6);
        var prefix = market == 1 ? "sh" : "sz";
        return prefix;
    },

    //市场前缀SH/SZ
    getMarketStrPrefix: function (market) {
        var prefix = market == 1 ? "sh" : "sz";
        return prefix;
    }

};

function dtformat(format) {
    var dt = new Date();
    var o = {
        "M+": dt.getMonth() + 1, //month
        "d+": dt.getDate(),    //day
        "h+": dt.getHours(),   //hour
        "m+": dt.getMinutes(), //minute
        "s+": dt.getSeconds(), //second
        "q+": Math.floor((dt.getMonth() + 3) / 3),  //quarter
        "S": dt.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
    (dt.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
        RegExp.$1.length == 1 ? o[k] :
        ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

function peRation(val) {
    val = parseFloat(val);
    if (isNaN(val) || val <= 0)
        return "-";
    else
        return val;
}

function HSTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow;
        var rowIndex = 0, rowNumber = 0;
        var rowCssClass, dataCss;
        var prefix, zsbar;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            if (dataRow[1] == "582027") continue;
            dataCss = new CssClass(dataRow);
            prefix = rowHelper.getMarketPrefix(dataRow[0]);
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            if (parameters.style === "21" || parameters.style === "11") {
                tpl.set("yanbao", "");
            } else {
                tpl.set("yanbao", ' <a href="http://data.eastmoney.com/report/' + dataRow[1] + '.html" target="_blank">研报</a>');
            }
            tpl.set("dataRow", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("prefix", prefix); //市场前缀,sh/sz
            tpl.set("zsbar", zsbar); //指数吧
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function NewHSTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow;
        var rowIndex = 0, rowNumber = 0;
        var rowCssClass, dataCss;
        var prefix, zsbar;
        var datacjl;
        var datacje;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            if (dataRow[1] == "582027") continue;
            if (dataRow[3] == "-") continue;
            dataCss = new CFGCssClass(dataRow);
            prefix = dataRow[0] == "1" ? "sh" : "sz";
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            datacjl = FixAmt(dataRow[7]) || "-";
            datacje = FixAmt((dataRow[8] / 10000).toFixed(2)) || "-";
            if (parameters.style === "21" || parameters.style === "11") {
                tpl.set("yanbao", "");
            } else {
                tpl.set("yanbao", ' <a href="http://data.eastmoney.com/report/' + dataRow[1] + '.html" target="_blank">研报</a>');
            }
            tpl.set("dataRow", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("prefix", prefix); //市场前缀,sh/sz
            tpl.set("datacjl", datacjl);
            tpl.set("datacje", datacje);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}


function NewIndexTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow;
        var rowIndex = 0, rowNumber = 0;
        var rowCssClass, dataCss;
        var prefix;
        var datacjl;
        var datacje;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            dataCss = new CFGCssClass(dataRow);
            prefix = dataRow[0] == "1" ? "sh" : "sz";
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            datacjl = FixAmt(dataRow[7]/100) || "-";
            datacje = FixAmt(dataRow[8] / 10000) || "-";
            tpl.set("dataRow", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("prefix", prefix); //市场前缀,sh/sz
            tpl.set("datacjl", datacjl);
            tpl.set("datacje", datacje);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}


function XGTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow;
        var rowIndex = 0, rowNumber = 0;
        var rowCssClass, dataCss;
        var prefix, zsbar, zfvalue, cjltotalvalue, cjetotalvalue;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            if (dataRow[1] == undefined || dataRow[1] == "582027") continue;
            zfvalue = dataRow[6] == "-" ? "-" : dataRow[6] + "%";
            cjltotalvalue = FixAmt(dataRow[7]) || "-";
            cjetotalvalue = FixAmt(dataRow[8]) || "-";
            dataCss = new XinGuCssClass(dataRow);
            prefix = rowHelper.getMarketStrPrefix(dataRow[0]);
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            if (parameters.style === "21" || parameters.style === "11") {
                tpl.set("yanbao", "");
            } else {
                tpl.set("yanbao", ' <a href="http://data.eastmoney.com/report/' + dataRow[1] + '.html" target="_blank">研报</a>');
            }
            tpl.set("dataRow", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("prefix", prefix); //市场前缀,sh/sz
            tpl.set("zsbar", zsbar); //指数吧
            tpl.set("zfv", zfvalue);
            tpl.set("cjlv", cjltotalvalue);
            tpl.set("cjev", cjetotalvalue);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function HKTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var rowCssClass = "", dataCss = {};
        var rowIndex = 0, rowNumber = 0;
        var dataRow = [];
        var datacjl;
        var datacje;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            if (dataRow[0] >= 110001 && dataRow[0] <= 110004) continue;
            dataCss = new HKCssClass(dataRow);
            datacjl = FixAmt(dataRow[5]) || "-";
            datacje = FixAmt(dataRow[6]) || "-";
            if (datacjl != datacje && datacje == "0") { datacje = "-"; }
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            tpl.set("data", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("datacjl", datacjl);
            tpl.set("datacje", datacje);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function NHKTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var rowCssClass = "", dataCss = {};
        var rowIndex = 0, rowNumber = 0;
        var dataRow = [];
        var datacjl;
        var datacje;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            if (dataRow[1] >= 110001 && dataRow[1] <= 110004) continue;
            dataCss = new NHKCssClass(dataRow);
            datacjl = FixAmt(dataRow[9]) || "-";
            datacje = FixAmt(dataRow[10]) || "-";
            if (datacjl != datacje && datacje == "0") { datacje = "-"; }
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            tpl.set("data", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("datacjl", datacjl);
            tpl.set("datacje", datacje);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function NYSPGPQHTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var rowCssClass = "", dataCss = {};
        var rowIndex = 0, rowNumber = 0;
        var dataRow = [];
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            tpl.set("data", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function ZSQHTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var rowCssClass = "", dataCss = {};
        var rowIndex = 0, rowNumber = 0;
        var dataRow = [];
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            tpl.set("data", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function USTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow, dataCss, totalvalue, stock_code;
        var rowCssClass, rowNumber;
        for (var i = 0, n = dt.length; i < n; i++) {
            dataRow = dt[i].split(",");
            dataCss = new CssClass(dataRow);
            totalvalue = dataRow[21];
            totalvalue = Math.forDight(totalvalue / 100000000, 2);
            rowCssClass = rowHelper.cssClass(i);
            rowNumber = rowHelper.number(parameters.page, pageSize, i);
            tpl.set("data", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("totalvalue", totalvalue);
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function ItchinaTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow, dataCss, totalvalue;
        var rowCssClass, rowNumber, volume;
        if (this.jsSortable) {
            this.jsSort(dt);
        }
        for (var i = 0, n = dt.length; i < n; i++) {
            dataRow = dt[i].split(",");
            if (dataRow.length < 15) continue;
            if (dataRow[1] == "") continue;
            dataCss = new ItchinaCssClass(dataRow);
            volume = parseInt(dataRow[14]) / 10000;
            rowCssClass = rowHelper.cssClass(i);
            tpl.set("data", dataRow);
            tpl.set("volume", volume);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function AHTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var rowCssClass, dataRow, hColor, aColor, cacheRow;
        var prefix, zsbar;
        var rowIndex = 0, rowNumber = 0;
        for (var i = 0; i < dt.length; i++) {
            rowNumber = rowHelper.number(parameters.page, pageSize, i);
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dataRow = String(dt[i]).split(",");
            hColor = Quote.Tool.className(parseFloat(dataRow[4]));
            aColor = Quote.Tool.className(parseFloat(dataRow[9]));
            prefix = rowHelper.getMarketStrPrefix(dataRow[6]);
            dataRow[4] = dataRow[4] == "-" ? "-" : dataRow[4] + "%";
            dataRow[9] = dataRow[9] == "-" ? "-" : dataRow[9] + "%";
            dataRow[15] = dataRow[15] == "-" ? "-" : (dataRow[15]*100).toFixed(2) + "%";
            tpl.set("num", rowNumber);
            tpl.set("data", dataRow);
            tpl.set("hColor", hColor);
            tpl.set("aColor", aColor);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("prefix", prefix);
            //if (typeof cache != "undefined") {
            //    cacheRow = String(cache).split(",");
            //    if (cacheRow[5] != dataRow[5]) {
            //        tpl.set("jssz", "jssz ");
            //    }
            //}
            //else {
            //    tpl.set("jssz", "");
            //}
            tpl.parse();
            rowIndex++;
        }
        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function BondTemplate(singleLink, priceText) {
    var header = [
		            '<table class="table-data" width="1100"><tr>',
		            '<th>序号</th>',
		            '<th><a>代码</a></th>',
		            '<th>名称</th>',
		            '<th><a>' + priceText + '</a></th>',
		            '<th><a>涨跌额</a></th>',
		            '<th><a>涨跌幅</a></th>',
		            '<th>今开</th>',
		            '<th>最高</th>',
		            '<th>最低</th>',
		            '<th>昨收</th>',
		            '<th><a>成交量(手)</a></th>',
		            '<th><a>成交额(万)</a></th>',
		            '</tr>'];
    var row = ['<tr{$rowCssClass}>',
                      '<td>{$num}</td>',
		              '<td><a href="' + singleLink + '" target="_blank">{$data[1]}</a></td>',
		              '<td><a href="' + singleLink + '" target="_blank">{$data[2]}</a></td>',
		              '<td><span class="{$priceCss}">{$data[3]}</span></td>',
		              '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		              '<td><span class="{$changeCss}">{$data[5]}</span></td>',
		              '<td><span class="{$openCss}">{$data[10]}</span></td>',
		              '<td><span class="{$maxCss}">{$data[11]}</span></td>',
		              '<td><span class="{$minCss}">{$data[12]}</span></td>',
		              '<td><span>{$data[9]}</span></td>',
		              '<td><span class="numeric">{$data[7]}</span></td>',
		              '<td><span class="numeric">{$datacje}</span></td>',
		            '</tr>'];
    var footer = '</table>';
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var _this = this;
        var page = parameters.page || 1;
        var dataRow, dataCss, datacje;
        if (this.jsSortable == 1) {
            this.jsSort(dt);
        }

        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            if (dt[i] != "") {
                dataRow = String(dt[i]).split(",");
                dataCss = new CssClass(dataRow);
                datacje = parseInt(dataRow[8] / 10000) || 0;
                dataRow[7] = parseInt(dataRow[7] / 10) || 0;
                tpl.set("data", dataRow);
                tpl.set("num", ((page - 1) * pageSize) + (i + 1));
                tpl.set("rowCssClass", rowCssClass);
                tpl.set("priceCss", dataCss.priceCss);
                tpl.set("changeCss", dataCss.changeCss);
                tpl.set("maxCss", dataCss.maxCss);
                tpl.set("minCss", dataCss.minCss);
                tpl.set("openCss", dataCss.openCss);
                tpl.set("datacje", datacje);
                tpl.set("prefix", rowHelper.getMarketStrPrefix(dataRow[0]));
                tpl.parse();
            }
        }
        $("listview").innerHTML = tpl.toHTML();
    }

    ITemplate.call(this, "listview", header, row, footer, render, tpl);
    var template = new SortWrapper(this);
    template.sortFiledDic = { "A": 2, "B": 4, "C": 6, "D": 5, "E": 12, "F": 11 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [4, "B"], "最新": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(手)": [11, "F"], "成交额(万)": [12, "E"] };
    return template;
}

function BondYhjTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var rowCssClass = "", dataCss = {};
        var rowIndex = 0, rowNumber = 0;
        var dataRow = [];
        var datacjl;
        var datacje;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            dataCss = new YHJCssClass(dataRow);
            if (datacjl != datacje && datacje == "0") { datacje = "-"; }
            rowCssClass = rowHelper.cssClass(rowIndex);
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            tpl.set("data", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function FuturesTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow, gzbarid;
        var itemCss = {};
        var page = parameters.page || 1;
        if (this.jsSortable) {
            this.jsSort(dt);
        }
        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dt[i] = dt[i].replace(/Infinity/g, "-");
            dataRow = String(dt[i]).split(",");
            itemCss = new NewFutureCssClass(dataRow);
            gzbarid = "gzqh";//"if" + dataRow[1].substring(4, 6);
            var _gzname2 = dataRow[1].substring(0, 2); var _gzname3 = dataRow[1].substr(2);
            var gzname = "沪深" + _gzname3;
            if (_gzname2 == "IH") { gzname = "上证" + _gzname3; } if (_gzname2 == "IC") { gzname = "中证" + _gzname3; }
            dataRow[15] = (dataRow[15] / 10000).toFixed(2);
            tpl.set("data", dataRow);
            tpl.set("num", ((page - 1) * pageSize) + (i + 1));
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("gzbarid", gzbarid);
            tpl.set("gzqhname", gzname.replace("DJ", "当季").replace("XJ", "下季").replace("GJ", "隔季").replace("DY", "当月").replace("XY", "下月").replace("LX", "连续"));
            tpl.set("priceCss", itemCss.priceCss);
            tpl.set("changeCss", itemCss.changeCss);
            tpl.set("maxCss", itemCss.maxCss);
            tpl.set("minCss", itemCss.minCss);
            tpl.set("openCss", itemCss.openCss);
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
    var template = new SortWrapper(this);
    template.sortFiledDic = { "A": 2, "B": 4, "C": 6, "D": 5, "E": 11, "F": 12 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(手)": [11, "E"], "买量(手)": [12, "F"] };
    return template;
}

function gzqh2Template(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow, gzbarid;
        var itemCss = {};
        var page = parameters.page || 1;
        if (this.jsSortable) {
            this.jsSort(dt);
        }
        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dt[i] = dt[i].replace(/Infinity/g, "-");
            dataRow = String(dt[i]).split(",");
            var sub2 = dataRow[1].toLowerCase().substring(0, 2);
            var cje = dataRow[15] == "-" ? "-" : (dataRow[15] / 10000).toFixed(2);
            if (sub2 == "tf") {
                itemCss = new NewFutureCssClass(dataRow);
                gzbarid = "gzqh";//"if" + dataRow[1].substring(4, 6);
                var gzname = "5年期国债" + dataRow[1].substr(2);
                tpl.set("data", dataRow);
                tpl.set("num", ((page - 1) * pageSize) + (i + 1));
                tpl.set("rowCssClass", rowCssClass);
                tpl.set("gzbarid", gzbarid);
                tpl.set("gzqhname", gzname.replace("DJ", "当季").replace("XJ", "下季").replace("GJ", "隔季").replace("DY", "当月").replace("XY", "下月").replace("LX", "连续").replace("GZ", ""));
                tpl.set("priceCss", itemCss.priceCss);
                tpl.set("changeCss", itemCss.changeCss);
                tpl.set("maxCss", itemCss.maxCss);
                tpl.set("minCss", itemCss.minCss);
                tpl.set("openCss", itemCss.openCss);
                tpl.set("cje", cje);
                tpl.parse();
            }
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
    var template = new SortWrapper(this);
    template.sortFiledDic = { "A": 1, "B": 4, "C": 6, "D": 5, "E": 11, "F": 12 };
    template.sortTypeDic = { "代码": [1, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(手)": [11, "E"], "成交额(万元)": [12, "F"] };
    return template;
}
function gzqh3Template(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow, gzbarid;
        var itemCss = {};
        var page = parameters.page || 1;
        if (this.jsSortable) {
            this.jsSort(dt);
        }
        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dt[i] = dt[i].replace(/Infinity/g, "-");
            dataRow = String(dt[i]).split(",");
            var sub2 = dataRow[1].toLowerCase().substring(0, 2);
            var cje = dataRow[15] == "-" ? "-" : (dataRow[15] / 10000).toFixed(2);
            if (sub2 != "tf" && sub2 != "tt") {
                itemCss = new NewFutureCssClass(dataRow);
                gzbarid = "gzqh";//"if" + dataRow[1].substring(4, 6);
                var gzname = "10年期国债" + dataRow[1].substr(1);
                tpl.set("data", dataRow);
                tpl.set("num", ((page - 1) * pageSize) + (i + 1));
                tpl.set("rowCssClass", rowCssClass);
                tpl.set("gzbarid", gzbarid);
                tpl.set("gzqhname", gzname.replace("DJ", "当季").replace("XJ", "下季").replace("GJ", "隔季").replace("DY", "当月").replace("XY", "下月").replace("LX", "连续"));
                tpl.set("priceCss", itemCss.priceCss);
                tpl.set("changeCss", itemCss.changeCss);
                tpl.set("maxCss", itemCss.maxCss);
                tpl.set("minCss", itemCss.minCss);
                tpl.set("openCss", itemCss.openCss);
                tpl.set("cje", cje);
                tpl.parse();
            }
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
    var template = new SortWrapper(this);
    template.sortFiledDic = { "A": 1, "B": 4, "C": 6, "D": 5, "E": 11, "F": 12 };
    template.sortTypeDic = { "代码": [1, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(手)": [11, "E"], "成交额(手)": [12, "F"] };
    return template;
}

function BTBFTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow, gzbarid;
        var itemCss = {};
        var page = parameters.page || 1;
        if (this.jsSortable) {
            this.jsSort(dt);
        }
        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dt[i] = dt[i].replace(/Infinity/g, "-");
            dataRow = String(dt[i]).split(",");
            rowbzfh = "￥";
            if (dataRow[1] == "MTGOX" || dataRow[1] == "BTSTP" || dataRow[1] == "BTCEB" || dataRow[1] == "796F") { rowbzfh = "$"; }
            itemCss = new FuturesCss(dataRow);
            gzbarid = "if" + dataRow[1].substring(4, 6);
            tpl.set("data", dataRow);
            tpl.set("num", ((page - 1) * pageSize) + (i + 1));
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("gzbarid", gzbarid);
            tpl.set("priceCss", itemCss.priceCss);
            tpl.set("changeCss", itemCss.changeCss);
            tpl.set("maxCss", itemCss.maxCss);
            tpl.set("minCss", itemCss.minCss);
            tpl.set("openCss", itemCss.openCss);
            tpl.set("bzfh", rowbzfh);
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
    var template = new SortWrapper(this);
    template.sortFiledDic = { "A": 2, "B": 4 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [4, "B"] };
    return template;
}

function ForexTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt) {
        var dataRow;
        var itemCss = {}, rowCssClass;
        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dataRow = String(dt[i]).split(",");
            itemCss = new FuturesCss(dataRow);
            tpl.set("data", dataRow);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", itemCss.priceCss);
            tpl.set("changeCss", itemCss.changeCss);
            tpl.set("maxCss", itemCss.maxCss);
            tpl.set("minCss", itemCss.minCss);
            tpl.set("openCss", itemCss.openCss);
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function NewForexTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt) {
        var dataRow;
        var itemCss = {}, rowCssClass;
        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            dataRow = String(dt[i]).split(",");
            itemCss = new RMBCssClass(dataRow);
            tpl.set("data", dataRow);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", itemCss.priceCss);
            tpl.set("changeCss", itemCss.changeCss);
            tpl.set("maxCss", itemCss.maxCss);
            tpl.set("minCss", itemCss.minCss);
            tpl.set("openCss", itemCss.openCss);
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML();
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function ABTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);

    function render(dt, parameters) {
        var rowCssClass, dataRow, bcolor, acolor, rowNumber = 0;
        var priceUnit = "";
        if (parameters.cmd === "C._ABPCSHZ") {
            priceUnit = "美元";
        } else {
            priceUnit = "港元";
        }

        for (var i = 0; i < dt.length; i++) {
            rowNumber = rowHelper.number(parameters.page, pageSize, i);
            rowCssClass = rowHelper.cssClass(i);;
            dataRow = String(dt[i]).split(",");
            bcolor = Quote.Tool.className(parseFloat(dataRow[4]));
            acolor = Quote.Tool.className(parseFloat(dataRow[9]));

            var bpriceForex, bijia = 0;
            if (dataRow[10] == "-") {
                bpriceForex = "-";
                bijia = "-";
            } else {
                if (parameters.cmd === "C._ABPCSHZ") {
                    bpriceForex = dataRow[10] == "-" ? "-" : parseFloat(dataRow[10]);
                    bijia = parseFloat(dataRow[12]);
                } else {
                    bpriceForex = dataRow[11] == "-" ? "-" : parseFloat(dataRow[11]);
                    bijia = parseFloat(dataRow[13]);
                }
            }
            
            var prefixA = rowHelper.getMarketStrPrefix(dataRow[0]);
            var prefixB = rowHelper.getMarketStrPrefix(dataRow[6]);


            tpl.set("num", rowNumber);
            tpl.set("data", dataRow);
            tpl.set("bcolor", bcolor);
            tpl.set("acolor", acolor);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("bijia", bijia);
            tpl.set("bpriceForex", bpriceForex);
            tpl.set("prefixA", prefixA);
            tpl.set("prefixB", prefixB);
            tpl.set("unit", priceUnit);
            
            tpl.parse();
        }
        $("listview").innerHTML = tpl.toHTML({ unit: priceUnit });
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

function SBTemplate(header, row, footer) {
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var dataRow;
        var rowIndex = 0, rowNumber = 0;
        var rowCssClass, dataCss;
        var prefix, zsbar, datacjl, datacje;
        for (var i = 0, length = dt.length; i < length; i++) {
            dataRow = String(dt[i]).split(",");
            if (dataRow[1] == "582027") continue;
            dataCss = new SanBanCssClass(dataRow);
            prefix = rowHelper.getMarketStrPrefix(dataRow[0]);
            rowCssClass = rowHelper.cssClass(rowIndex);
            datacjl = FixAmt(dataRow[7]) || "-";
            datacje = FixAmt(dataRow[8] / 10000) || "-";
            rowNumber = rowHelper.number(parameters.page, pageSize, rowIndex);
            rowHelper.jssz(tpl, i, dataRow);
            if (parameters.style === "21" || parameters.style === "11") {
                tpl.set("yanbao", "");
            } else {
                tpl.set("yanbao", ' <a href="http://data.eastmoney.com/report/' + dataRow[1] + '.html" target="_blank">研报</a>');
            }
            tpl.set("dataRow", dataRow);
            tpl.set("num", rowNumber);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("priceCss", dataCss.priceCss);
            tpl.set("changeCss", dataCss.changeCss);
            tpl.set("openCss", dataCss.openCss);
            tpl.set("maxCss", dataCss.maxCss);
            tpl.set("minCss", dataCss.minCss);
            tpl.set("rowCssClass", rowCssClass);
            tpl.set("prefix", prefix); //市场前缀,sh/sz
            tpl.set("zsbar", zsbar); //指数吧
            tpl.parse();
            rowIndex++;
        }

        $("listview").innerHTML = tpl.toHTML();
        cache = dt;
    }
    ITemplate.call(this, "listview", header, row, footer, render, tpl);
}

var abTemplate = function () {
    var header = [
		    '<table class="table-data" id="fixed" width="1100"><tr>',
		      '<th>序号</th>',
		      '<th><a>B股代码</a></th>',
		      '<th>B股名称</th>',
		      '<th>相关链接</th>',
		      '<th><a>最新价({$unit})</a></th>',
		      '<th><a>最新价(人民币)</a></th>',
		      '<th><a>涨跌幅</a></th>',
		      '<th><a>A股代码</a></th>',
		      '<th>A股名称</th>',
		      '<th><a>最新价</a></th>',
		      '<th><a data-arg="(RMB)">涨跌幅</a></th>',
			  '<th><a>比价(A/B)</a></th>',
		    '</tr>'];

    var row = ['<tr{$rowCssClass}>',
		      '<td>{$num}</td>',
		      '<td><a href="http://quote.eastmoney.com/{$prefixB}{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		      '<td><a href="http://quote.eastmoney.com/{$prefixB}{$data[1]}.html" target="_blank">{$data[2]}</a></td>',
		      '<td><a href="http://guba.eastmoney.com/topic,{$data[1]}.html" target="_blank">股吧</a> ' +
			  		'<a href="http://data.eastmoney.com/zjlx/{$data[1]}.html" target="_blank">资金流</a></td>',
		      '<td><span class="{$bcolor}">{$data[3]}</span></td>',
              '<td><span class="{$bcolor}">{$bpriceForex}</span></td>',
		      '<td><span class="{$bcolor}">{$data[4]}%</span></td>',
			  '<td><a href="http://quote.eastmoney.com/{$prefixA}{$data[5]}.html" target="_blank">{$data[5]}</a></td>',
			  '<td><a href="http://quote.eastmoney.com/{$prefixA}{$data[5]}.html" target="_blank">{$data[7]}</a></td>',
		      '<td><span class="{$acolor}">{$data[8]}</span></td>',
			  '<td><span class="{$acolor}">{$data[9]}%</span></td>',
			   '<td><span>{$bijia}</span></td>',
		    '</tr>'];
    var footer = '</table><div class=\"note\"><span style=\"float:right;\">更新时间：' + dtformat('yyyy-MM-ddd hh:mm') + '</span></div>';

    var template = new ABTemplate(header, row, footer);

    template = new SortWrapper(template);
    template.sortTypeDic = { "B股代码": [1, "(ABAHCode)"], "最新价(美元)": [5, "(CloseCrossUSD)"], "最新价(港元)": [5, "(CloseCrossHKD)"], "最新价(人民币)": [5, "(CloseCrossUSD)"], "A股代码": [7, "(Code)"], "最新价": [9, "(Close)"], "涨跌幅": [6, "(ChangePercent)"], "涨跌幅(RMB)": [10, "(ABAHCP)"], "比价(A/B)": [11, "(AB/AH/USD)"] };
    template.sortFiledDic = { "(ABAHCode)": 2, "(ABAHClose)": 5, "(ABAHClose)": 6, "(ChangePercent)": 7, "(Code)": 8, "(Close)": 10, "(ABAHCP)": 11, "(AB/AH/USD)": 12 };
    return template;
};

var hsTemplate = function () {
    var header = [
			'<table class="table-data"  id="fixed" width="1100"><tr>',
			 '<th>序号</th>',
			  '<th><a>代码</a></th>',
			  '<th>名称</th>',
			  '<th>相关链接</th>',
			  '<th><a>最新价</a></th>',
			  '<th><a>涨跌额</a></th>',
			  '<th><a>涨跌幅</a></th>',
			  '<th><a>振幅</a></th>',
			  '<th><a>成交量(手)</a></th>',
			  '<th><a>成交额(万)</a></th>',
			  '<th>昨收</th>',
			  '<th>今开</th>',
			  '<th>最高</th>',
			  '<th>最低</th>',
			  '<th><a>5分钟涨跌</a></th>',
			  '<th>加自选</th>',
			'</tr>'];
    var row = ['<tr{$rowCssClass}>',
			  '<td>{$num}</td>',
			  '<td><a href="http://quote.eastmoney.com/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[1]}</a></td>',
			  '<td><a href="http://quote.eastmoney.com/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[2]}</a></td>',
			  '<td><a href="http://guba.eastmoney.com/topic,{$dataRow[1]}.html" target="_blank">股吧</a> <a href="http://data.eastmoney.com/zjlx/{$dataRow[1]}.html" target="_blank">资金流</a>{$yanbao}</td>',
			  '<td><span class="{$jssz}{$priceCss}">{$dataRow[5]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[10]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[11]}</span></td>',
			  '<td><span>{$dataRow[13]}</span></td>',
			  '<td><span>{$dataRow[9]}</span></td>',
			  '<td><span>{$dataRow[8]}</span></td>',
			  '<td><span>{$dataRow[3]}</span></td>',
			  '<td><span class="{$openCss}">{$dataRow[4]}</span></td>',
			  '<td><span class="{$maxCss}">{$dataRow[6]}</span></td>',
			  '<td><span class="{$minCss}">{$dataRow[7]}</span></td>',
			  '<td><span>{$dataRow[21]}</span></td>', //动态列
			  '<td><a class="icon-plus" href="/stockeast/addstock?code={$dataRow[1]}" target="_blank" /></td>',
			'</tr>'];
    var footer = '</table>';
    var template = new HSTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 10, "F": 9, "J": 15, "I": 15, "G": 15, "H": 15, "K": 8 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量(手)": [9, "F"], "成交额(万)": [10, "E"], "换手率": [15, "J"], "市盈率": [15, "I"], "量比": [15, "H"], "5分钟涨跌": [15, "G"], "振幅": [8, "K"] };
    template = new SelectWrapper(template);
    template.opts = [["换手率", "{$dataRow[23]}"], ["市盈率", "{$dataRow[24]|peRation}"], ["量比", "{$dataRow[22]}"], ["5分钟涨跌", "{$dataRow[21]}"]];
    template.selectColumn = 15;
    return template;
};

//新版AB股
var newHSTemplate = function () {
    var header = [
             '<table class="table-data"  id="fixed" width="1100"><tr>',
              '<th>序号</th>',
               '<th><a>代码</a></th>',
               '<th>名称</th>',
               '<th>相关链接</th>',
               '<th><a>最新价</a></th>',
               '<th><a>涨跌额</a></th>',
               '<th><a>涨跌幅</a></th>',
               '<th><a>振幅</a></th>',
               '<th><a>成交量(手)</a></th>',
               '<th><a>成交额(万)</a></th>',
               '<th>昨收</th>',
               '<th>今开</th>',
               '<th>最高</th>',
               '<th>最低</th>',
               '<th><a>5分钟涨跌</a></th>',
               '<th>加自选</th>',
             '</tr>'];
    var row = ['<tr{$rowCssClass}>',
			  '<td>{$num}</td>',
			  '<td><a href="'+portalurl+'stockeast/detail?stockCode={$dataRow[1]}" target="_self">{$dataRow[1]}</a></td>',
			  '<td><a href="'+portalurl+'stockeast/detail?stockCode={$dataRow[1]}" target="_self">{$dataRow[2]}</a></td>',
			  '<td><a href="http://guba.eastmoney.com/topic,{$dataRow[1]}.html" target="_blank">股吧</a> <a href="'+portalurl+'stockeast/zjlx/detail?code={$dataRow[1]}" target="_self">资金流</a>{$yanbao}</td>',
			  '<td><span class="{$jssz}{$priceCss}">{$dataRow[3]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[4]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[5]}</span></td>',
			  '<td><span>{$dataRow[6]}%</span></td>',
			  '<td><span>{$datacjl}</span></td>',
			  '<td><span>{$datacje}</span></td>',
			  '<td><span>{$dataRow[9]}</span></td>',
			  '<td><span class="{$openCss}">{$dataRow[10]}</span></td>',
			  '<td><span class="{$maxCss}">{$dataRow[11]}</span></td>',
			  '<td><span class="{$minCss}">{$dataRow[12]}</span></td>',
			  '<td><span>{$dataRow[21]}</span></td>', //动态列
			  '<td><a class="icon-plus" href="'+portalurl+'stockeast/addstock?code={$dataRow[1]}" target="_self" /></td>',
			'</tr>'];
    var footer = '</table>';
    var template = new NewHSTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 10, "F": 9, "J": 15, "I": 15, "G": 15, "H": 15, "K": 8 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量(手)": [9, "F"], "成交额(万)": [10, "E"], "换手率": [15, "J"], "市盈率": [15, "I"], "量比": [15, "H"], "5分钟涨跌": [15, "G"], "振幅": [8, "K"] };
    template = new SelectWrapper(template);
    template.opts = [["换手率", "{$dataRow[23]}"], ["市盈率", "{$dataRow[24]|peRation}"], ["量比", "{$dataRow[22]}"], ["5分钟涨跌", "{$dataRow[21]}"]];
    template.selectColumn = 15;
    return template;
};

//新股
var xgTemplate = function () {
    var header = [
			'<table class="table-data"  id="fixed" width="1100"><tr>',
			 '<th>序号</th>',
			  '<th><a>代码</a></th>',
			  '<th>名称</th>',
			  '<th>相关链接</th>',
			  '<th><a>最新价</a></th>',
			  '<th><a>涨跌额</a></th>',
			  '<th><a>涨跌幅</a></th>',
			  '<th><a>振幅</a></th>',
			  '<th><a>成交量(手)</a></th>',
			  '<th><a>成交额</a></th>',
			  '<th>昨收</th>',
			  '<th>今开</th>',
			  '<th>最高</th>',
			  '<th>最低</th>',
			  '<th><a>5分钟涨跌</a></th>',
			  '<th>加自选</th>',
			'</tr>'];
    var row = ['<tr{$rowCssClass}>',
			  '<td>{$num}</td>',
			  '<td><a href="http://quote.eastmoney.com/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[1]}</a></td>',
			  '<td><a href="http://quote.eastmoney.com/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[2]}</a></td>',
			  '<td><a href="http://guba.eastmoney.com/topic,{$dataRow[1]}.html" target="_blank">股吧</a> <a href="http://data.eastmoney.com/zjlx/{$dataRow[1]}.html" target="_blank">资金流</a>{$yanbao}</td>',
			  '<td><span class="{$jssz}{$priceCss}">{$dataRow[3]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[4]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[5]}</span></td>',
			  '<td><span>{$zfv}</span></td>',
			  '<td><span>{$cjlv}</span></td>',
			  '<td><span>{$cjev}</span></td>',
			  '<td><span>{$dataRow[9]}</span></td>',
			  '<td><span class="{$openCss}">{$dataRow[10]}</span></td>',
			  '<td><span class="{$maxCss}">{$dataRow[11]}</span></td>',
			  '<td><span class="{$minCss}">{$dataRow[12]}</span></td>',
			  '<td><span>{$dataRow[21]}</span></td>', //动态列
			  '<td><a class="icon-plus" href="/stockeast/addstock?code={$dataRow[1]}" target="_blank" /></td>',
			'</tr>'];
    var footer = '</table>';
    var template = new XGTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 10, "F": 9, "J": 15, "I": 15, "G": 15, "H": 15, "K": 8 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量(手)": [9, "F"], "成交额": [10, "E"], "换手率": [15, "J"], "市盈率": [15, "I"], "量比": [15, "H"], "5分钟涨跌": [15, "G"], "振幅": [8, "K"] };
    template = new SelectWrapper(template);
    template.opts = [["换手率", "{$dataRow[23]}"], ["市盈率", "{$dataRow[24]|peRation}"], ["量比", "{$dataRow[22]}"], ["5分钟涨跌", "{$dataRow[21]}"]];
    template.selectColumn = 15;
    return template;
};

var indexTemplate = function () {
    var header = [
			'<table class="table-data"  id="fixed" width="1100"><tr>', //0
			  '<th>序号</th>',
			  '<th><a>代码</a></th>', //1
			  '<th>名称</th>', //2
			  '<th><a>最新价</a></th>', //5
			  '<th><a>涨跌额</a></th>', //6
			  '<th><a>涨跌幅</a></th>', //7
			  '<th><a>振幅</a></th>', //8
			  '<th><a>成交量(手)</a></th>',
			  '<th><a>成交额(万)</a></th>',
			  '<th>昨收</th>',
			  '<th>今开</th>',
			  '<th>最高</th>',
			  '<th>最低</th>',
			  '<th><a>5分钟涨跌</a></th>',
			'</tr>'];
    var row = ['<tr{$rowCssClass}>', //0
			  '<td>{$num}</td>',
			  '<td><a href="http://quote.eastmoney.com/zs{$dataRow[1]}.html" target="_blank">{$dataRow[1]}</a></td>', //1
			  '<td><a href="http://quote.eastmoney.com/zs{$dataRow[1]}.html" target="_blank">{$dataRow[2]}</a></td>', //2
			  '<td><span class="{$jssz}{$priceCss}">{$dataRow[3]}</span></td>',
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[4]}</span></td>', //7
			  '<td><span class="{$jssz}{$changeCss}">{$dataRow[5]}</span></td>', //8
			  '<td><span>{$dataRow[6]}</span></td>',
			  '<td><span>{$datacjl}</span></td>', //10
			  '<td><span>{$datacje}</span></td>',
			  '<td><span>{$dataRow[9]}</span></td>',
			  '<td><span class="{$openCss}">{$dataRow[10]}</span></td>',
			  '<td><span class="{$maxCss}">{$dataRow[11]}</span></td>',
			  '<td><span class="{$minCss}">{$dataRow[12]}</span></td>',
			  '<td><span>{$dataRow[21]}</span></td>', //动态列
			'</tr>'];
    var footer = '</table>';
    var template = new NewIndexTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 4, "C": 6, "D": 5, "E": 9, "F": 8, "J": 14, "I": 14, "K": 7, "G": 14 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(手)": [8, "F"], "成交额(万)": [9, "E"], "换手率": [14, "J"], "市盈率": [14, "I"], "5分钟涨跌": [14, "G"], "振幅": [7, "K"] };
    template = new SelectWrapper(template);
    template.opts = [["换手率", "{$dataRow[23]}"], ["市盈率", "{$dataRow[24]}"], ["5分钟涨跌", "{$dataRow[21]}"]];
    template.selectColumn = 14;
    return template;
};

//成分股
var cfgTemplate = function () {
    var header = [
			'<table class="table-data"  id="fixed" width="1100"><tr>', //0
			  '<th>序号</th>',
			  '<th><a>代码</a></th>', //1
			  '<th>名称</th>', //2
              '<th>相关链接</th>',
			  '<th><a>最新价</a></th>', //5
			  '<th><a>涨跌额</a></th>', //6
			  '<th><a>涨跌幅</a></th>', //7
			  '<th><a>振幅</a></th>', //8
			  '<th><a>成交量(手)</a></th>',
			  '<th><a>成交额(万)</a></th>',
			  '<th>昨收</th>',
			  '<th>今开</th>',
			  '<th>最高</th>',
			  '<th>最低</th>',
			  '<th><a>5分钟涨跌</a></th>',
			'</tr>'];
    var row = ['<tr{$rowCssClass}>', //0
			  '<td>{$num}</td>',
			  '<td><a href="http://quote.eastmoney.com/zs{$dataRow[1]}.html" target="_blank">{$dataRow[1]}</a></td>', //1
			  '<td><a href="http://quote.eastmoney.com/zs{$dataRow[1]}.html" target="_blank">{$dataRow[2]}</a></td>', //2
              '<td><a href="http://quote.eastmoney.com/center/list.html#{$dataRow[1]}{$dataRow[0]}_0_6" target="_blank">成分股</a></td>',
			  '<td><span class="{$priceCss}">{$dataRow[3]}</span></td>',
			  '<td><span class="{$changeCss}">{$dataRow[4]}</span></td>', //7
			  '<td><span class="{$changeCss}">{$dataRow[5]}</span></td>', //8
			  '<td><span>{$dataRow[6]}%</span></td>',
			  '<td><span>{$datacjl}</span></td>', //10
			  '<td><span>{$datacje}</span></td>',
			  '<td><span>{$dataRow[9]}</span></td>',
			  '<td><span class="{$openCss}">{$dataRow[10]}</span></td>',
			  '<td><span class="{$maxCss}">{$dataRow[11]}</span></td>',
			  '<td><span class="{$minCss}">{$dataRow[12]}</span></td>',
			  '<td><span>{$dataRow[21]}</span></td>', //动态列
			'</tr>'];
    var footer = '</table>';
    var template = new NewIndexTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 10, "F": 9, "J": 15, "I": 15, "K": 8, "G": 15 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量(手)": [9, "F"], "成交额(万)": [10, "E"], "换手率": [15, "J"], "市盈率": [15, "I"], "5分钟涨跌": [15, "G"], "振幅": [8, "K"] };
    return template;
};


//涡轮
var wolunTemplate = function () {
    var header = [
		        '<table class="table-data"  id="fixed" width="1100"><tr>',
		          '<th>序号</th>',
		          '<th><a>代码</a></th>',
		          '<th>名称</th>',
		          '<th><a>最新价</a></th>',
		          '<th><a>涨跌额</a></th>',
		          '<th><a>涨跌幅</a></th>',
		          '<th>今开</th>',
		          '<th>最高</th>',
		          '<th>最低</th>',
		          '<th>昨收</th>',
		          '<th><a>成交量(股)</a></th>',
		          '<th><a>成交额(港元)</a></th>',
		        '</tr>'];
    var row = ['<tr{$rowCssClass}>',
                  '<td>{$num}</td>',
		          '<td>{$data[0]}</td>',
		          '<td>{$data[1]}</td>',
		          '<td><span class="{$priceCss}">{$data[2]}</span></td>',
		          '<td><span class="{$changeCss}">{$data[3]}</span></td>',
		          '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		          '<td><span class="{$openCss}">{$data[7]}</span></td>',
		          '<td><span class="{$maxCss}">{$data[8]}</span></td>',
		          '<td><span class="{$minCss}">{$data[9]}</span></td>',
		          '<td>{$data[10]}</td>',
		          '<td><span>{$datacjl}</span></td>',
			      	'<td><span>{$datacje}</span></td>',
		        '</tr>'];
    var footer = '</table>';
    var wolun = new HKTemplate(header, row, footer);
    var sort = new SortWrapper(wolun);
    sort.sortFiledDic = { "A": 2, "B": 4, "C": 6, "D": 5, "E": 12, "F": 11 };
    sort.sortTypeDic = { "代码": [2, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(股)": [11, "F"], "成交额(港元)": [12, "E"] };
    return sort;
};

//港股指数
var hkIndexTemplate = function () {
    var header = [
		        '<table class="table-data" id="fixed" width="1100"><tr>',
		          '<th><a>代码</a></th>',
		          '<th>名称</th>',
		          '<th><a>最新价</a></th>',
		          '<th><a>涨跌额</a></th>',
		          '<th><a>涨跌幅</a></th>',
		          '<th>今开</th>',
		          '<th>最高</th>',
		          '<th>最低</th>',
		          '<th>昨收</th>',
		          '<th>成交额(港元)</th>',
		        '</tr>'];
    var row = ['<tr{$rowCssClass}>',
		          '<td>{$data[0]}</td>',
		          '<td>{$data[1]}</a></td>',
		          '<td><span class="{$priceCss}">{$data[2]}</span></td>',
		          '<td><span class="{$changeCss}">{$data[3]}</span></td>',
		          '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		          '<td><span class="{$openCss}">{$data[7]}</span></td>',
		          '<td><span class="{$maxCss}">{$data[8]}</span></td>',
		          '<td><span class="{$minCss}">{$data[9]}</span></td>',
		          '<td>{$data[10]}</td>',
		          '<td><span>{$datacje}</span></td>',
		        '</tr>'];
    var footer = '</table>';
    //return new HKTemplate(header, row, footer);

    var template = new HKTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 1, "B": 3, "C": 5, "D": 4 };
    template.sortTypeDic = { "代码": [1, "A"], "最新价": [3, "B"], "涨跌幅": [5, "C"], "涨跌额": [4, "D"] };
    return template;
};

var hkTemplate = function () {

    var header = [
			    '<table class="table-data" id="fixed" width="1100"><tr>',
			      '<th>序号</th>',
			      '<th><a>代码</a></th>',
			      '<th>名称</th>',
			      '<th>相关链接</th>',
			      '<th><a>最新价(HKD)</a></th>',
			      '<th><a>涨跌额</a></th>',
			      '<th><a>涨跌幅</a></th>',
			      '<th>今开</th>',
			      '<th>最高</th>',
			      '<th>最低</th>',
			      '<th>昨收</th>',
			      '<th><a>成交量(股)</a></th>',
			      '<th><a>成交额(港元)</a></th>',
			       '<th>加自选</th>',
			    '</tr>'];
    var row = ['<tr{$rowCssClass}>',
			      '<td>{$num}</td>',
			      '<td><a target="_blank" href="http://quote.eastmoney.com/hk/{$data[0]}.html">{$data[0]}</a></td>',
			      '<td><a target="_blank" href="http://quote.eastmoney.com/hk/{$data[0]}.html">{$data[1]}</a></td>',
			      '<td><a target="_blank" href="http://guba.eastmoney.com/hk/list,hk{$data[0]}.html">股吧</a> <a href="http://so.eastmoney.com/Search.aspx?q=({$data[0]})({$data[1]|encodeURIComponent})&t=body" target="_blank">资讯</a> <a target="_blank" href="http://f10.eastmoney.com/hkf10/gsjs.aspx?code={$data[0]}">档案</a></td>',
			      '<td><span class="{$priceCss}">{$data[2]}</span></td>',
			      '<td><span class="{$changeCss}">{$data[3]}</span></td>',
			      '<td><span class="{$changeCss}">{$data[4]}</span></td>',
			      '<td><span class="{$openCss}">{$data[7]}</span></td>',
			      '<td><span class="{$maxCss}">{$data[8]}</span></td>',
			      '<td><span class="{$minCss}">{$data[9]}</span></td>',
			      '<td><span>{$data[3]}</span></td>',
			      '<td><span>{$datacjl}</span></td>',
			      '<td><span>{$datacje}</span></td>',
			      '<td><a target="_blank" class="icon-plus" href="http://quote.eastmoney.com/hk/favor/infavor.html?code={$data[0]}"></a></td>',
			    '</tr>'];
    var footer = "</table><div class=\"note\"></div>";
    var hk = new HKTemplate(header, row, footer);
    var sort = new SortWrapper(hk);
    sort.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 13, "F": 12 };
    sort.sortTypeDic = { "代码": [2, "A"], "最新价(HKD)": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量(股)": [12, "F"], "成交额(港元)": [13, "E"] };
    return sort;

};
//新港股(沪港通)
var nhkTemplate = function () {
    var header = [
			    '<table class="table-data" id="fixed" width="1100"><tr>',
			      '<th>序号</th>',
			      '<th><a>代码</a></th>',
			      '<th>名称</th>',
			      '<th>相关链接</th>',
			      '<th><a>最新价(HKD)</a></th>',
			      '<th><a>涨跌额</a></th>',
			      '<th><a>涨跌幅</a></th>',
			      '<th>今开</th>',
			      '<th>最高</th>',
			      '<th>最低</th>',
			      '<th>昨收</th>',
			      '<th><a>成交量(股)</a></th>',
			      '<th><a>成交额(港元)</a></th>',
			       '<th>加自选</th>',
			    '</tr>'];
    var row = ['<tr{$rowCssClass}>',
			      '<td>{$num}</td>',
			      '<td><a target="_blank" href="http://quote.eastmoney.com/hk/{$data[1]}.html">{$data[1]}</a></td>',
			      '<td><a target="_blank" href="http://quote.eastmoney.com/hk/{$data[1]}.html">{$data[2]}</a></td>',
			      '<td><a target="_blank" href="http://guba.eastmoney.com/hk/list,hk{$data[1]}.html">股吧</a> <a href="http://so.eastmoney.com/Search.aspx?q={$data[1]} {$data[2]|encodeURIComponent}&t=body" target="_blank">资讯</a> <a target="_blank" href="http://f10.eastmoney.com/hkf10/gsjs.aspx?code={$data[1]}">档案</a></td>',
			      '<td><span class="{$priceCss}">{$data[3]}</span></td>',
			      '<td><span class="{$changeCss}">{$data[4]}</span></td>',
			      '<td><span class="{$changeCss}">{$data[5]}</span></td>',
			      '<td><span class="{$openCss}">{$data[11]}</span></td>',
			      '<td><span class="{$maxCss}">{$data[13]}</span></td>',
			      '<td><span class="{$minCss}">{$data[14]}</span></td>',
			      '<td><span>{$data[12]}</span></td>',
			      '<td><span>{$datacjl}</span></td>',
			      '<td><span>{$datacje}</span></td>',
			      '<td><a target="_blank" class="icon-plus" href="http://quote.eastmoney.com/hk/favor/infavor.html?code={$data[1]}"></a></td>',
			    '</tr>'];
    var footer = "</table><div class=\"note\"></div>";
    var hk = new NHKTemplate(header, row, footer);
    var sort = new SortWrapper(hk);
    sort.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 13, "F": 12 };
    sort.sortTypeDic = { "代码": [2, "A"], "最新价(HKD)": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交额(港元)": [13, "E"], "成交量(股)": [2, "F"] };
    return sort;

};
//港交所衍生品 股票期货
var nyspgpqhTemplate = function () {
    var header = [
			    '<table class="table-data" id="fixed" width="1100"><tr>',
			      '<th><a>代码</a></th>',
			      '<th>股票期货名称</th>',
			      '<th><a>合约成交量</a></th>',
			      '<th><a>昨日持仓量</a></th>',
			    '</tr>'];
    var row = ['<tr{$rowCssClass}>',
			      '<td><a target="_blank" href="hkysp.html#yspgpqh_1?code=HSF_{$data[0]}">{$data[0]}</a></td>',
			      '<td><a target="_blank" href="hkysp.html#yspgpqh_1?code=HSF_{$data[0]}">{$data[2]}</a></td>',
			      '<td>{$data[3]}</td>',
			      '<td>{$data[4]}</td>',
			    '</tr>'];
    var footer = "</table><div class=\"note\"></div>";
    var hk = new NYSPGPQHTemplate(header, row, footer);
    var sort = new SortWrapper(hk);
    sort.sortFiledDic = { "A": 1, "(Volume)": 3, "(PreviousOpenInterest)": 4 };
    sort.sortTypeDic = { "代码": [1, "A"], "合约成交量": [3, "(Volume)"], "昨日持仓量": [4, "(PreviousOpenInterest)"] };
    return sort;

};

//港交所衍生品 指数期货
var zsqhTemplate = function () {
    var header = [
			    '<table class="table-data" id="fixed" width="1100"><tr>',
			      '<th><a>代码</a></th>',
			      '<th>指数期货名称</th>',
			      '<th><a>合约成交量</a></th>',
			      '<th><a>昨日持仓量</a></th>',
			    '</tr>'];
    var row = ['<tr{$rowCssClass}>',
			      '<td><a target="_blank" href="hkysp.html#zsqh_1?code={$data[1]}_{$data[0]}">{$data[0]}</a></td>',
			      '<td><a target="_blank" href="hkysp.html#zsqh_1?code={$data[1]}_{$data[0]}">{$data[2]}</a></td>',
			      '<td>{$data[3]}</td>',
			      '<td>{$data[4]}</td>',
			    '</tr>'];
    var footer = "</table><div class=\"note\"></div>";
    var hk = new ZSQHTemplate(header, row, footer);
    var sort = new SortWrapper(hk);
    sort.sortFiledDic = { "A": 1, "(Volume)": 3, "(PreviousOpenInterest)": 4 };
    sort.sortTypeDic = { "代码": [1, "A"], "合约成交量": [3, "(Volume)"], "昨日持仓量": [4, "(PreviousOpenInterest)"] };
    return sort;

};

//ah股模板
var ahTemplate = function () {
    var header = [
		    '<table class="table-data" id="fixed" width="1100"><tr>',
		      '<th>序号</th>',
		      '<th>名称</th>',
		      '<th><a>H股代码</a></th>',
		      '<th><a>最新价(HKD)</a></th>',
		      '<th><a>涨跌幅</a></th>',
		      '<th>港股吧</th>',
		      '<th><a>A股代码</a></th>',
		      '<th><a>最新价(RMB)</a></th>',
		      '<th><a data-arg="(RMB)">涨跌幅</a></th>',
		      '<th>A股吧</th>',
		      '<th><a>比价(A/H)</a></th>',
		      '<th><a>溢价(A/H)</a></th>',
		    '</tr>'];
    var row = ['<tr{$rowCssClass}>',
		      '<td>{$num}</td>',
		      '<td><a href="http://quote.eastmoney.com/hk/{$data[1]}.html" target="_blank">{$data[2]}</a></td>',
		      '<td><a href="http://quote.eastmoney.com/hk/{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		      '<td><span class="{$hColor}">{$data[3]}</span></td>',
		      '<td><span class="{$hColor}">{$data[4]}</span></td>',
		      '<td><a href="http://guba.eastmoney.com/hk/list,hk{$data[1]}.html" target="_blank">港股吧</a></td>',
		      '<td><a href="http://quote.eastmoney.com/{$prefix}{$data[5]}.html" target="_blank">{$data[5]}</a></td>',
		      '<td><span class="{$aColor}">{$data[8]}</span></td>',
		      '<td><span class="{$aColor}">{$data[9]}</span></td>',
		      '<td><a href="http://guba.eastmoney.com/topic,{$data[5]}.html" target="_blank">A股吧</a></td>',
		      '<td><span>{$data[13]}</span></td>',
		      '<td><span>{$data[15]}</span></td>',
		    '</tr>'];
    var footer = '</table><div class=\"note\"><span style=\"float:right;\">更新时间：' + dtformat('yyyy-MM-ddd hh:mm') + '</span></div>';
    var template = new AHTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "(Code)": 3, "(Close)": 4, "(ChangePercent)": 5, "(ABAHCode)": 7, "(ABAHClose)": 8, "(ABAHCP)": 9, "(AB/AH/HKD)": 11, "(ABHPremium)": 12 };
    template.sortTypeDic = { "H股代码": [3, "(Code)"], "最新价(HKD)": [4, "(Close)"], "涨跌幅": [5, "(ChangePercent)"], "A股代码": [7, "(ABAHCode)"], "最新价(RMB)": [8, "(ABAHClose)"], "涨跌幅(RMB)": [9, "(ABAHCP)"], "比价(A/H)": [11, "(AB/AH/HKD)"], "溢价(A/H)": [12, "(ABHPremium)"] };
    return template;
};

//美股模板
var usTemplate = function () {
    var header = [
		            '<table class="table-data" id="fixed" width="1100"><tr>',
		              '<th>序号</th>',
		              '<th>名称</th>',
		              '<th width="89"><a>最新价(美元)</a></th>',
		              '<th><a>涨跌额</a></th>',
		              '<th><a>涨跌幅</a></th>',
		              '<th>今开</th>',
		              '<th>最高</th>',
		              '<th>最低</th>',
		              '<th>昨收</th>',
		              '<th>市值(亿美元)</th>',
		              '<th>市盈率</th>',
		            '</tr>'];
    var row = ['<tr{$rowCssClass}>',
                      '<td>{$num}</td>',
		              '<td><a href="http://quote.eastmoney.com/us/{$data[1]}.html" target="_blank">{$data[2]}</a></td>',
		              '<td><span class="{$priceCss}">{$data[5]}</span></td>',
		              '<td><span class="{$changeCss}">{$data[6]}</span></td>',
		              '<td><span class="{$changeCss}">{$data[7]}</span></td>',
		              '<td><span class="{$openCss}">{$data[3]}</span></td>',
		              '<td><span class="{$maxCss}">{$data[9]}</span></td>',
		              '<td><span class="{$minCss}">{$data[10]}</span></td>',
		              '<td><span>{$data[4]}</span></td>',
		              '<td><span>{$totalvalue}</span></td>',
		              '<td><span>{$data[18]|peRation}</span></td>',
		            '</tr>'];
    var footer = '</table>';
    var template = new USTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "B": 3, "C": 5, "D": 4 };
    template.sortTypeDic = { "最新价(美元)": [3, "B"], "涨跌幅": [5, "C"], "涨跌额": [4, "D"] };
    return template;
};

//互联网中国
var itchinaTemplate = function () {
    var header = ['<table class="table-data" id="fixed" width="1100"><tr>',
		                  '<th>代码</th>',
		                  '<th>名称</th>',
		                  '<th width="89"><a>最新价(美元)</a></th>',
		                  '<th><a>涨跌额</a></th>',
		                  '<th><a>涨跌幅</a></th>',
		                  '<th>今开</th>',
		                  '<th>最高</th>',
		                  '<th>最低</th>',
		                  '<th>昨收</th>',
		                  '<th>52周股价幅度</th>',
		                  '<th>成交量(万股)</th>',
		                  '<th><a>市值(亿美元)</a></th>',
		                  '<th>市盈率</th>',
		                '</tr>'];

    var row = ['<tr{$rowCssClass}>',
		                  '<td>{$data[0]}</td>',
		                  '<td>{$data[1]}</td>',
		                  '<td><span class="{$priceCss}">{$data[2]}</span></td>',
		                  '<td><span class="{$changeCss}">{$data[3]}</span></td>',
		                  '<td><span class="{$changeCss}">{$data[4]}%</span></td>',
		                  '<td><span class="{$openCss}">{$data[5]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[7]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[8]}</span></td>',
		                  '<td><span>{$data[6]}</span></td>',
		                  '<td><span  class="{$changeCss}">{$data[9]}-{$data[10]}</span></td>',
		                  '<td><span>{$volume}</span></td>',
		                  '<td><span>{$data[11]}</span></td>',
		                  '<td><span>{$data[12]|peRation}</span></td>',
		                '</tr>'];
    var footer = '</table>';

    var template = new ItchinaTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 3, "B": 4, "C": 5, "D": 12 };
    template.sortTypeDic = { "最新价(美元)": [3, "A"], "涨跌额": [4, "B"], "涨跌幅": [5, "C"], "市值(亿美元)": [12, "D"] };
    return template;
};

//债券模板
var bondIndexTemplate = function () {
    var singleLink = "http://quote.eastmoney.com/zs{$data[1]}.html";
    var header = [
		            '<table class="table-data" width="1100"><tr>',
		            '<th>序号</th>',
		            '<th><a>代码</a></th>',
		            '<th>名称</th>',
		            '<th><a>最新价</a></th>',
		            '<th><a>涨跌额</a></th>',
		            '<th><a>涨跌幅</a></th>',
		            '<th>今开</th>',
		            '<th>最高</th>',
		            '<th>最低</th>',
		            '<th>昨收</th>',
		            '<th><a>成交量(手)</a></th>',
		            '<th><a>成交额(万)</a></th>',
		            '</tr>'];
    var row = ['<tr{$rowCssClass}>',
                      '<td>{$num}</td>',
		              '<td><a href="' + singleLink + '" target="_blank">{$data[1]}</a></td>',
		              '<td><a href="' + singleLink + '" target="_blank">{$data[2]}</a></td>',
		              '<td><span class="{$priceCss}">{$data[5]}</span></td>',
		              '<td><span class="{$changeCss}">{$data[6]}</span></td>',
		              '<td><span class="{$changeCss}">{$data[7]}</span></td>',
		              '<td><span class="{$openCss}">{$data[3]}</span></td>',
		              '<td><span class="{$maxCss}">{$data[9]}</span></td>',
		              '<td><span class="{$minCss}">{$data[10]}</span></td>',
		              '<td><span>{$data[4]}</span></td>',
		              '<td><span class="numeric">{$data[11]}</span></td>',
		              '<td><span class="numeric">{$data[12]}</span></td>',
		            '</tr>'];
    var footer = '</table>';
    var tpl = new JSTemplate(header.join(''), row.join(''), footer);
    function render(dt, parameters) {
        var _this = this;
        var page = parameters.page || 1;
        var dataRow, dataCss, datacje;
        if (this.jsSortable == 1) {
            this.jsSort(dt);
        }

        for (var i = 0, n = dt.length; i < n; i++) {
            rowCssClass = i % 2 != 0 ? " class=\"bg\"" : "";
            if (dt[i] != "") {
                dataRow = String(dt[i]).split(",");
                dataCss = new CssClass(dataRow);
                dataRow[12] = parseInt(dataRow[12] / 10000) || 0;
                dataRow[11] = parseInt(dataRow[11] / 100) || 0;
                tpl.set("data", dataRow);
                tpl.set("num", ((page - 1) * pageSize) + (i + 1));
                tpl.set("rowCssClass", rowCssClass);
                tpl.set("priceCss", dataCss.priceCss);
                tpl.set("changeCss", dataCss.changeCss);
                tpl.set("maxCss", dataCss.maxCss);
                tpl.set("minCss", dataCss.minCss);
                tpl.set("openCss", dataCss.openCss);
                tpl.set("datacje", datacje);
                tpl.set("prefix", rowHelper.getMarketStrPrefix(dataRow[0]));
                tpl.parse();
            }
        }
        $("listview").innerHTML = tpl.toHTML();
    }

    ITemplate.call(this, "listview", header, row, footer, render, tpl);
    var template = new SortWrapper(this);
    template.sortFiledDic = { "A": 2, "B": 4, "C": 6, "D": 5, "E": 12, "F": 11 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [4, "B"], "最新": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交量(手)": [11, "F"], "成交额(万)": [12, "E"] };
    return template;

};
var bondYhjTemplate = function () {
    var header = [
			    '<table class="table-data" id="fixed" width="100%"><tr>',
			      '<th><a>代码</a></th>',
			      '<th><a>名称</a></th>',
			      '<th><a>最新价</a></th>',
			      '<th><a>涨跌幅</a></th>',
			      '<th><a>最新YTM</a></th>',
			      '<th><a>YTM涨跌BP</a></th>',
			      '<th><a>加权YTM</a></th>',
			      '<th>债券评级</th>',
			      '<th><a>剩余期限(年)</a></th>',
			      '<th><a>票面利率</a></th>',
			    '</tr>'];
    var row = ['<tr{$rowCssClass}>',
			      '<td>{$data[1]}</td>',
			      '<td>{$data[2]}</td>',
			      '<td><span class="{$changeCss}">{$data[3]}</span></td>',
			      '<td><span class="{$changeCss}">{$data[4]}</span></td>',
			      '<td><span>{$data[5]}</span></td>',
			      '<td><span>{$data[6]}</span></td>',
			      '<td><span>{$data[7]}</span></td>',
			      '<td>{$data[8]}</td>',
			      '<td><span>{$data[9]}</span></td>',
			      '<td><span>{$data[10]}</span></td>',
			    '</tr>'];
    var footer = "</table><div class=\"note\"></div>";
    var hk = new BondYhjTemplate(header, row, footer);
    var sort = new SortWrapper(hk);
    sort.sortFiledDic = { "(Code)": 1, "(Name)": 2, "(Close)": 3, "(ChangePercent)": 4, "(CLOSEYTM)": 5, "(CHANGEYTM)": 6, "(CLOSEWEIGHTYTM)": 7, "(IBREMAINPERIOD)": 9, "(IBNNIR)": 10 };
    sort.sortTypeDic = { "代码": [1, "(Code)"], "名称": [2, "(Name)"], "最新价": [3, "(Close)"], "涨跌幅": [4, "(ChangePercent)"], "最新YTM": [5, "(CLOSEYTM)"], "YTM涨跌BP": [6, "(CHANGEYTM)"], "加权YTM": [7, "(CLOSEWEIGHTYTM)"], "剩余期限(年)": [9, "(IBREMAINPERIOD)"], "票面利率": [10, "(IBNNIR)"] };
    return sort;
};
var bondTemplate = function () {
    var singleLink = "http://quote.eastmoney.com/{$prefix}{$data[1]}.html";
    return new BondTemplate(singleLink, "最新价");
};

//股指期货
var gzTemplate = function () {
    var header = [
		            '<table class="table-data" width="1100"><tr>',
		            '<th>序号</th>',
		            '<th><a>代码</a></th>',
		            '<th>名称</th>',
		            '<th>走势资讯</th>',
		            '<th><a>最新价</a></th>',
		            '<th><a>涨跌额</a></th>',
		            '<th><a>涨跌幅</a></th>',
		            '<th>今开</th>',
		            '<th>最高</th>',
		            '<th>最低</th>',
		            '<th>昨结</th>',
		            '<th><a>成交量(手)</a></th>',
		            '<th><a>成交额(万元)</a></th>',
		            '<th>持仓量(手)</th>',
		            '</tr>'];
    var row = ['<tr{$rowCssClass}>',
                      '<td>{$num}</td>',
		              '<td><a href="http://quote.eastmoney.com/gzqh/{$data[1]}.html" target="_blank">{$data[1]}</td>',
		              '<td><a href="http://quote.eastmoney.com/gzqh/{$data[1]}.html" target="_blank">{$gzqhname}</td>',
		              '<td><a href="http://guba.eastmoney.com/topic,{$gzbarid}.html" target="_blank">期指吧</a> <a href="http://data.eastmoney.com/IF/Data/Contract.html" target="_blank">龙虎榜</a> <a href="http://so.eastmoney.com/Search.aspx?q={$data[1]}%20{$data[1]}&t=body" target="_blank">资讯</a></td>',
		              '<td><span class="{$priceCss}">{$data[3]}</span></td>',
		               '<td><span class="{$changeCss}">{$data[5]}</span></td>',
		                '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		              '<td><span class="{$openCss}">{$data[11]}</span></td>',
		              '<td><span class="{$maxCss}">{$data[13]}</span></td>',
		              '<td><span class="{$minCss}">{$data[14]}</span></td>',
		              '<td><span>{$data[8]}</span></td>',
		              '<td><span>{$data[10]}</span></td>',
		              '<td><span>{$data[15]}</span></td>',
		              '<td><span>{$data[9]}</span></td>',
		            '</tr>'];
    var footer = "</table>";
    var template = new FuturesTemplate(header, row, footer);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 13, "F": 12 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交额(万元)": [13, "E"], "成交量(手)": [12, "F"] };
    return template;
};
//5年期国债期货
var gz2Template = function () {
    var header = [
		            '<table class="table-data" width="1100"><tr>',
		            '<th><a>代码</a></th>',
		            '<th>名称</th>',
		            '<th>走势资讯</th>',
		            '<th><a>最新价</a></th>',
		            '<th><a>涨跌额</a></th>',
		            '<th><a>涨跌幅</a></th>',
		            '<th>今开</th>',
		            '<th>最高</th>',
		            '<th>最低</th>',
		            '<th>昨结</th>',
		            '<th><a>成交量(手)</a></th>',
		            '<th><a>成交额(万元)</a></th>',
		            '<th>持仓量(手)</th>',
		            '</tr>'];
    var row = ['<tr{$rowCssClass}>',
                  '<td><a href="http://quote.eastmoney.com/gzqh/{$data[1]}.html" target="_blank">{$data[1]}</td>',
		              '<td><a href="http://quote.eastmoney.com/gzqh/{$data[1]}.html" target="_blank">{$gzqhname}</td>',
		              '<td><a href="http://guba.eastmoney.com/topic,gzqh2.html" target="_blank">国债期指吧</a> <a href="http://futures.eastmoney.com/news/cgzqhzx.html" target="_blank">资讯</a></td>',
		              '<td><span class="{$priceCss}">{$data[3]}</span></td>',
		               '<td><span class="{$changeCss}">{$data[5]}</span></td>',
		                '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		              '<td><span class="{$openCss}">{$data[11]}</span></td>',
		              '<td><span class="{$maxCss}">{$data[13]}</span></td>',
		              '<td><span class="{$minCss}">{$data[14]}</span></td>',
		              '<td><span>{$data[8]}</span></td>',
		              '<td><span>{$data[10]}</span></td>',
		              '<td><span>{$cje}</span></td>',
		              '<td><span>{$data[9]}</span></td>',
		            '</tr>'];
    var footer = "</table>";
    var template = new gzqh2Template(header, row, footer);
    template.sortFiledDic = { "A": 1, "B": 4, "C": 6, "D": 5, "E": 12, "F": 11 };
    template.sortTypeDic = { "代码": [1, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交额(万元)": [12, "E"], "成交量(手)": [11, "F"] };
    return template;
};
//10年期国债期货
var gz3Template = function () {
    var header = [
		            '<table class="table-data" width="1100"><tr>',
		            '<th><a>代码</a></th>',
		            '<th>名称</th>',
		            '<th>走势资讯</th>',
		            '<th><a>最新价</a></th>',
		            '<th><a>涨跌额</a></th>',
		            '<th><a>涨跌幅</a></th>',
		            '<th>今开</th>',
		            '<th>最高</th>',
		            '<th>最低</th>',
		            '<th>昨结</th>',
		            '<th><a>成交量(手)</a></th>',
		            '<th><a>成交额(万元)</a></th>',
		            '<th>持仓量(手)</th>',
		            '</tr>'];
    var row = [
        '<tr{$rowCssClass}>',
        '<td><a href="http://quote.eastmoney.com/gzqh/{$data[1]}.html" target="_blank">{$data[1]}</td>',
        '<td><a href="http://quote.eastmoney.com/gzqh/{$data[1]}.html" target="_blank">{$gzqhname}</td>',
        '<td><a href="http://guba.eastmoney.com/topic,gzqh2.html" target="_blank">国债期指吧</a> <a href="http://futures.eastmoney.com/news/cgzqhzx.html" target="_blank">资讯</a></td>',
        '<td><span class="{$priceCss}">{$data[3]}</span></td>',
        '<td><span class="{$changeCss}">{$data[5]}</span></td>',
        '<td><span class="{$changeCss}">{$data[4]}</span></td>',
        '<td><span class="{$openCss}">{$data[11]}</span></td>',
        '<td><span class="{$maxCss}">{$data[13]}</span></td>',
        '<td><span class="{$minCss}">{$data[14]}</span></td>',
        '<td><span>{$data[8]}</span></td>',
        '<td><span>{$data[10]}</span></td>',
        '<td><span>{$cje}</span></td>',
        '<td><span>{$data[9]}</span></td>',
        '</tr>'
    ];
    var footer = "</table>";
    var template = new gzqh3Template(header, row, footer);
    template.sortFiledDic = { "A": 1, "B": 4, "C": 6, "D": 5, "E": 12, "F": 11 };
    template.sortTypeDic = { "代码": [1, "A"], "最新价": [4, "B"], "涨跌幅": [6, "C"], "涨跌额": [5, "D"], "成交额(万元)": [12, "E"], "成交量(手)": [11, "F"] };
    return template;
};

//国际，国内期货，现货泛亚
var futures = function () {
    var header = ['<table class="table-data" id="fixed"  width="1100"><tr>',
                '<th>序号</th>',
                '<th><a>代码</a></th>',
                '<th>名称</th>',
                '<th><a>最新价</a></th>',
                '<th><a>涨跌额</a></th>',
                '<th><a>涨跌幅</a></th>',
                '<th>今开</th>',
                '<th>最高</th>',
                '<th>最低</th>',
                '<th>昨结</th>',
                '<th><a>成交量(手)</a></th>',
                '<th><a>买量(手)</a></th>',
                '<th>卖量(手)</th>',
                '<th>持仓(手)</th>',
                '</tr>'
    ];
    var footer = '</table>';

    function gn() {
        var row = ['<tr{$rowCssClass}>',
                          '<td>{$num}</td>',
		                  '<td><a href="http://quote.eastmoney.com/qihuo/{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/qihuo/{$data[1]}.html" target="_blank">{$data[2]}</a></td>',
		                  '<td><span class="{$priceCss}">{$data[5]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[17]}</span></td>',
		                    '<td><span class="{$changeCss}">{$data[18]}</span></td>',
		                  '<td><span class="{$openCss}">{$data[4]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[6]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[7]}</span></td>',
		                  '<td><span>{$data[13]}</span></td>',
		                  '<td><span>{$data[9]}</span></td>',
		                  '<td><span>{$data[21]}</span></td>',
		                  '<td><span>{$data[22]}</span></td>',
		                  '<td><span>{$data[16]}</span></td>',
		                '</tr>'];
        return new FuturesTemplate(header, row, footer);
    }

    function gj() {
        var row = ['<tr{$rowCssClass}>',
                          '<td>{$num}</td>',
		                  '<td><a href="http://quote.eastmoney.com/gjqh/{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/gjqh/{$data[1]}.html" target="_blank">{$data[2]}</a></td>',
		                  '<td><span class="{$priceCss}">{$data[5]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[17]}</span></td>',
		                    '<td><span class="{$changeCss}">{$data[18]}</span></td>',
		                  '<td><span class="{$openCss}">{$data[4]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[6]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[7]}</span></td>',
		                  '<td><span>{$data[13]}</span></td>',
		                  '<td><span>{$data[9]}</span></td>',
		                  '<td><span>{$data[21]}</span></td>',
		                  '<td><span>{$data[22]}</span></td>',
		                  '<td><span>{$data[16]}</span></td>',
		                '</tr>'];
        return new FuturesTemplate(header, row, footer);
    }

    //现货泛亚
    function xhfy() {
        header = ['<table class="table-data" id="fixed"  width="1100"><tr>',
                '<th>序号</th>',
                '<th><a>代码</a></th>',
                '<th>名称</th>',
                '<th><a>最新价</a></th>',
                '<th><a>涨跌额</a></th>',
                '<th><a>涨跌幅</a></th>',
                '<th>今开</th>',
                '<th>最高</th>',
                '<th>最低</th>',
                '<th>昨结</th>',
                '<th><a>成交量(手)</a></th>',
                '<th><a>买量(手)</a></th>',
                '<th>卖量(手)</th>',
                '<th>结算价</th>',
                '</tr>'
        ];
        var row = ['<tr{$rowCssClass}>',
                          '<td>{$num}</td>',
		                  '<td>{$data[1]}</td>',
		                  '<td>{$data[2]}</td>',
		                  '<td><span class="{$priceCss}">{$data[5]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[17]}</span></td>',
		                    '<td><span class="{$changeCss}">{$data[18]}</span></td>',
		                  '<td><span class="{$openCss}">{$data[4]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[6]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[7]}</span></td>',
		                  '<td><span>{$data[13]}</span></td>',
		                  '<td><span>{$data[9]}</span></td>',
		                  '<td><span>{$data[21]}</span></td>',
		                  '<td><span>{$data[22]}</span></td>',
		                  '<td><span>{$data[16]}</span></td>',
		                '</tr>'];
        return new FuturesTemplate(header, row, footer);
    }

    function btb() {
        header = ['<table class="table-data" id="fixed"  width="1100"><tr>',
                '<th>序号</th>',
                '<th><a>代码</a></th>',
                '<th>名称</th>',
                '<th><a>最近成交价</a></th>',
                '<th>买一价</th>',
                '<th>卖一价</th>',
                '<th>最高价</th>',
                '<th>最低价</th>',
                '<th>成交量</th>',
                '</tr>'
        ];
        var row = ['<tr{$rowCssClass}>',
                          '<td>{$num}</td>',
		                  '<td>{$data[1]}</td>',
		                  '<td>{$data[2]}</td>',
		                  '<td><span class="{$priceCss}">{$bzfh}{$data[5]}</span></td>',
		                   '<td><span class="{$changeCss}">{$bzfh}{$data[25]}</span></td>',
		                    '<td><span class="{$changeCss}">{$bzfh}{$data[26]}</span></td>',
		                  '<td><span class="{$maxCss}">{$bzfh}{$data[6]}</span></td>',
		                  '<td><span class="{$minCss}">{$bzfh}{$data[7]}</span></td>',
		                  '<td><span>{$data[9]} BTC</span></td>',
		                '</tr>'];
        var footer = "</table><div class=\"note\"><span style=\"float:right;\">" + dtformat('yyyy-MM-ddd hh:mm:ss') + "</span>注：以上行情数据来源于网络，仅供参考。</div>";
        var template = new BTBFTemplate(header, row, footer);
        template.sortFiledDic = { "A": 2, "B": 4 };
        template.sortTypeDic = { "代码": [2, "A"], "最近成交价": [4, "B"] };
        return template;
    }

    return {
        gjTemplate: gj,
        gnTemplate: gn,
        xhfyTemplate: xhfy,
        btbTemplate: btb

    };
}();


//新版国内国际期货模板
var newFuturesTemplate = function () {
    var header = ['<table class="table-data" id="fixed"  width="1100"><tr>',
                '<th>序号</th>',
                '<th><a>代码</a></th>',
                '<th>名称</th>',
                '<th><a>最新价</a></th>',
                '<th><a>涨跌额</a></th>',
                '<th><a>涨跌幅</a></th>',
                '<th>今开</th>',
                '<th>最高</th>',
                '<th>最低</th>',
                '<th>昨结</th>',
                '<th><a>成交量(手)</a></th>',
                '<th><a>买量(手)</a></th>',
                '<th>卖量(手)</th>',
                '<th>持仓(手)</th>',
                '</tr>'
    ];
    var url = "";
    if (viewstate.style == "gjqh") {
        url = "http://quote.eastmoney.com/globalfuture/{$data[1]}.html";
    } else {
        url = "http://quote.eastmoney.com/qihuo/{$data[1]}.html";
    }
    var row = [
        '<tr{$rowCssClass}>',
        '<td>{$num}</td>',
        '<td><a href="' + url + '" target="_blank">{$data[1]}</a></td>',
        '<td><a href="' + url + '" target="_blank">{$data[2]}</a></td>',
        '<td><span class="{$priceCss}">{$data[3]}</span></td>',
        '<td><span class="{$changeCss}">{$data[5]}</span></td>',
        '<td><span class="{$changeCss}">{$data[4]}</span></td>',
        '<td><span class="{$openCss}">{$data[11]}</span></td>',
        '<td><span class="{$maxCss}">{$data[13]}</span></td>',
        '<td><span class="{$minCss}">{$data[14]}</span></td>',
         '<td><span>{$data[8]}</span></td>',
        '<td><span>{$data[10]}</span></td>',
        '<td><span>{$data[6]}</span></td>',
        '<td><span>{$data[7]}</span></td>',
        '<td><span>{$data[9]}</span></td>',
        '</tr>'
    ];
    var footer = "</table>";
    var template = new FuturesTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortTypeDic = { "代码": [1, "A"], "最新价": [3, "B"], "涨跌幅": [4, "C"], "涨跌额": [5, "D"], "成交量(手)": [10, "(Volume)"], "买量(手)": [10, "(BuyOrder)"] };
    return template;
};

//基金模板
var fundTemplate = function () {
    var header = ['<table class="table-data" id="fixed"  width="1100"><tr>',
                         '<th>序号</th>',
                        '<th><a>代码</a></th>',
                        '<th>名称</th>',
                        '<th>相关链接</th>',
                        '<th><a>最新价</a></th>',
                        '<th><a>涨跌额</a></th>',
                        '<th><a>涨跌幅</a></th>',
                        '<th><a>成交量</a></th>',
                        '<th><a>成交额</a></th>',
                        '<th>开盘价</th>',
                        '<th>最高价</th>',
                        '<th>最低价</th>',
                        '<th>昨收</th>',
                        '</tr>'
    ];
    var row = ['<tr{$rowCssClass}>',
                          '<td>{$num}</td>',
                          '<td><a href="http://quote.eastmoney.com/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[2]}</a></td>',
		                  '<td><a href="http://fund.eastmoney.com/{$dataRow[1]}.html" target="_blank">估算图</a> <a href="http://jijinba.eastmoney.com/list,{$dataRow[1]}.html" target="_blank">基金吧</a> <a href="http://fund.eastmoney.com/f10/{$dataRow[1]}.html" target="_blank">档案</a></td>',
		                  '<td><span class="{$jssz}{$priceCss}">{$dataRow[3]}</span></td>',
		                  '<td><span class="{$jssz}{$changeCss}">{$dataRow[4]}</span></td>',
		                  '<td><span class="{$jssz}{$changeCss}">{$dataRow[5]}</span></td>',
		                  '<td><span>{$dataRow[7]}</span></td>',
		                  '<td><span>{$dataRow[8]}</span></td>',
		                  '<td><span class="{$openCss}">{$dataRow[10]}</span></td>',
		                  '<td><span class="{$maxCss}">{$dataRow[11]}</span></td>',
		                  '<td><span class="{$minCss}">{$dataRow[12]}</span></td>',
		                  '<td><span>{$dataRow[9]}</span></td>',
		                '</tr>'];
    var footer = "</table>";
    var template = new NewHSTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 9, "F": 8 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量": [8, "F"], "成交额(万)": [9, "E"] };
    return template;
};

//外汇模板
var forexTemplate = function () {
    var header = ['<table id="fixed" class="table-data" width="1100"><tr>',
                    '<th>代码</th>',
                    '<th>名称</th>',
                    '<th>最新价</th>',
                    '<th>涨跌额</th>',
                    '<th>涨跌幅</th>',
                    '<th>开盘</th>',
                    '<th>最高</th>',
                    '<th>最低</th>',
                    '<th>昨收</th>',
                    '</tr>'
    ];

    var row = ['<tr{$rowCssClass}>',
                          '<td><a href="http://quote.eastmoney.com/forex/{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/forex/{$data[1]}.html" target="_blank">{$data[2]}</a></td>',
		                  '<td><span class="{$priceCss}">{$data[5]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[17]}</span></td>',
		                    '<td><span class="{$changeCss}">{$data[18]}</span></td>',
		                  '<td><span class="{$openCss}">{$data[4]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[6]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[7]}</span></td>',
		                  '<td><span>{$data[3]}</span></td>',
		                '</tr>'];
    var footer = "</table>";
    return new ForexTemplate(header, row, footer);
};


//外汇中间价模板
var forexMiddleTemplate = function () {
    var header = ['<table id="fixed" class="table-data" width="1100"><tr>',
                      '<th>代码</th>',
                    '<th>名称</th>',
                    '<th>最新价</th>',
                    '<th>涨跌额</th>',
                    '<th>涨跌幅</th>',
                    '<th>开盘</th>',
                    '<th>最高</th>',
                    '<th>最低</th>',
                    '<th>昨收</th>',
                    '</tr>'
    ];

    var row = ['<tr{$rowCssClass}>',
		                  '<td><a href="http://quote.eastmoney.com/cnyrate/{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/cnyrate/{$data[1]}.html" target="_blank">{$data[0]}</a></td>',
		                  '<td><span class="{$priceCss}">{$data[2]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[3]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		                  '<td><span class="{$openCss}">{$data[7]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[5]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[6]}</span></td>',
		                  '<td><span>{$data[8]}</span></td>',
		                '</tr>'];
    var footer = "</table>";
    return new NewForexTemplate(header, row, footer);
};

//新版人民币模板
var newRMBTemplate = function () {
    var header = ['<table id="fixed" class="table-data" width="1100"><tr>',
                    '<th>代码</th>',
                    '<th>名称</th>',
                    '<th>最新价</th>',
                    '<th>涨跌额</th>',
                    '<th>涨跌幅</th>',
                    '<th>开盘</th>',
                    '<th>最高</th>',
                    '<th>最低</th>',
                    '<th>昨收</th>',
                    '</tr>'
    ];

    var row = ['<tr{$rowCssClass}>',
                          '<td><a href="http://quote.eastmoney.com/forex/{$data[1]}.html" target="_blank">{$data[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/forex/{$data[1]}.html" target="_blank">{$data[0]}</a></td>',
		                  '<td><span class="{$priceCss}">{$data[2]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[3]}</span></td>',
		                   '<td><span class="{$changeCss}">{$data[4]}</span></td>',
		                  '<td><span class="{$openCss}">{$data[7]}</span></td>',
		                  '<td><span class="{$maxCss}">{$data[5]}</span></td>',
		                  '<td><span class="{$minCss}">{$data[6]}</span></td>',
		                  '<td><span>{$data[8]}</span></td>',
		                '</tr>'];
    var footer = "</table>";
    return new NewForexTemplate(header, row, footer);
};

//三板模板；
var sanbanTemplate = function () {
    var header = ['<table id="fixed" class="table-data" width="1100"><tr>',
                    '<th>序号</th>',
                    '<th><a>代码</a></th>',
                    '<th>名称</th>',
                    '<th>相关链接</th>',
                    '<th><a>最新价</a></th>',
                    '<th><a>涨跌额</a></th>',
                    '<th><a>涨跌幅</a></th>',
                    '<th><a>成交量</a></th>',
                    '<th><a>成交额</a></th>',
                    '<th>昨收</th>',
                    '<th>今开</th>',
                    '<th>最高</th>',
                    '<th>最低</th>',
                    '<th>委比</th>',
                    '</tr>'];

    var row = ['<tr{$rowCssClass}>',
                          '<td>{$num}</td>',
                          '<td><a href="http://quote.eastmoney.com/3ban/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[1]}</a></td>',
		                  '<td><a href="http://quote.eastmoney.com/3ban/{$prefix}{$dataRow[1]}.html" target="_blank">{$dataRow[2]}</a></td>',
		                  '<td><a href="http://guba.eastmoney.com/topic,{$dataRow[1]}.html" target="_blank">股吧</a> <a href="http://so.eastmoney.com/Search.aspx?q={$dataRow[1]}&t=body" target="_blank">资讯</a></td>',
		                  '<td><span class="{$priceCss}">{$dataRow[3]}</span></td>',
		                  '<td><span class="{$changeCss}">{$dataRow[4]}</span></td>',
		                  '<td><span class="{$changeCss}">{$dataRow[5]}</span></td>',
		                  '<td><span>{$dataRow[13]}</span></td>',
		                  '<td><span>{$dataRow[14]}</span></td>',
		                  '<td><span>{$dataRow[8]}</span></td>',
		                  '<td><span class="{$openCss}">{$dataRow[9]}</span></td>',
		                  '<td><span class="{$maxCss}">{$dataRow[10]}</span></td>',
		                  '<td><span class="{$minCss}">{$dataRow[11]}</span></td>',
		                  '<td><span>{$dataRow[12]}</span></td>',
		                '</tr>'];
    var footer = "</table>";
    var template = new SBTemplate(header, row, footer);
    template = new SortWrapper(template);
    template.sortFiledDic = { "A": 2, "B": 5, "C": 7, "D": 6, "E": 9, "F": 8, "H": 14 };
    template.sortTypeDic = { "代码": [2, "A"], "最新价": [5, "B"], "涨跌幅": [7, "C"], "涨跌额": [6, "D"], "成交量": [8, "F"], "成交额": [9, "E"] };


    return template;
};

var gif = new Gif($("listview"));
function GridPageView(update) {
    this.dispose = function () {
        this.list.dispose();
    };
    PageView.call(this, update);
}

GridPageView.prototype = PageView.prototype;
GridPageView.prototype.pageWrapper = new PageNavigation("pagenav");
GridPageView.prototype.pageSize = pageSize;
GridPageView.prototype.abTemplate = abTemplate;
GridPageView.prototype.hsTemplate = hsTemplate;
GridPageView.prototype.newHSTemplate = newHSTemplate;
GridPageView.prototype.xgTemplate = xgTemplate;
GridPageView.prototype.indexTemplate = indexTemplate;
GridPageView.prototype.cfgTemplate = cfgTemplate;
GridPageView.prototype.ahTemplate = ahTemplate;
GridPageView.prototype.hkIndexTemplate = hkIndexTemplate;
GridPageView.prototype.wolunTemplate = wolunTemplate;
GridPageView.prototype.hkTemplate = hkTemplate;
GridPageView.prototype.nhkTemplate = nhkTemplate;
GridPageView.prototype.nyspgpqhTemplate = nyspgpqhTemplate;
GridPageView.prototype.zsqhTemplate = zsqhTemplate;
GridPageView.prototype.usTemplate = usTemplate;
GridPageView.prototype.bondIndexTemplate = bondIndexTemplate;
GridPageView.prototype.bondYhjTemplate = bondYhjTemplate;
GridPageView.prototype.bondTemplate = bondTemplate;
GridPageView.prototype.futuresTemplate = futures.gnTemplate;
GridPageView.prototype.newFuturesTemplate = newFuturesTemplate;
GridPageView.prototype.futuresTemplate_gj = futures.gjTemplate;
GridPageView.prototype.futuresTemplate_xhfy = futures.xhfyTemplate;
GridPageView.prototype.futuresTemplate_btb = futures.btbTemplate;
GridPageView.prototype.futuresTemplate_gzqh = gzTemplate;
GridPageView.prototype.futuresTemplate_gzqh2 = gz2Template;
GridPageView.prototype.futuresTemplate_gzqh3 = gz3Template;
GridPageView.prototype.forexTemplate = forexTemplate;
GridPageView.prototype.fundTemplate = fundTemplate;
GridPageView.prototype.sanbanTemplate = sanbanTemplate;
GridPageView.prototype.itchinaTemplate = itchinaTemplate;
GridPageView.prototype.forexMiddleTemplate = forexMiddleTemplate;
GridPageView.prototype.newRMBTemplate = newRMBTemplate;
GridPageView.prototype.setAutoUpdate = function (upateArgs) {
    if (this.update) {
        this.list.autoUpdate = true;
        this.list.updateInterval = upateArgs.interval;
        this.list.isTimeZone = upateArgs.delegate;
        this.list.onUpdateCompleted = function () {
            gif.init();
        };
    }
};
var v2 = document.getElementById("v2");
if (v2) {
    v2.onclick = function () {
        location.href = "marketlist#" + getHash() + "?mode=pic&sortType=" + viewstate["sortType"] + "&sortRule=" + viewstate["sortRule"];
        if (getHash() == "285001_0") { window.location.reload(); }
    };
}
