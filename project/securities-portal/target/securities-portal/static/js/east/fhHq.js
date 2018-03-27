; (function () {
    var $ = jQuery;
    var self_select_tab = $("#self-select-tab");
    if (self_select_tab.get(0)) {
        var code = $("#codeID").val();
        var matket = $("#market").val();
        var name = $("#codeName").val();
        var dalay_time = 10000;//10秒钟更新一次数据
        //更新自选数据
        var url = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?Type=F&jsName=zjlx_hq&id=" + code + matket;
        var tableCache = '                        <tr><td><a href="http://quote.eastmoney.com/{code}.html" target="_blank">{code}</a></td><td><a href="http://quote.eastmoney.com/{code}.html" target="_blank">{name}</a></td><td>{xg}</td><td>{price}</td><td>{change}</td><td>{percent}</td><td><span>{zf}</span></td><td><span>{amount}</span></td><td><span>{volume}</span></td><td><span>{hsl}</span></td><td><span>{lb}</span></td><td><span>{syl}</span></td><td><a href="http://quote.eastmoney.com/favor/infavor.aspx?code={code}" title="将{code}({name})加为自选股"><img src="/static/img/east/add.gif"></a></td></tr>';
        update_self_data(self_select_tab, url, tableCache, code, matket, name);
        setInterval(function () {
            //更新自选数据
            update_self_data(self_select_tab, url, tableCache, code, matket, name);
        }, dalay_time)
    }
    //更新自选数据
    function update_self_data(self_select_tab, url, tableCache, _stockCode, _stockName) {
        var a = url + "&rt=" + Math.floor((new Date().getTime()) / 30000);
        var data, str;
        var getColorStr = function (val, _val, _val1) {
            if (isNaN(_val) || isNaN(_val1)) {
                return "<span>" + val + "</span>";
            }
            if (parseFloat(_val) > _val1) {
                val = "<span class=\"red\">" + val + "</span>";
            } else if (parseFloat(_val) < _val1) {
                val = "<span class=\"green\">" + val + "</span>";
            } else {
                val = "<span>" + val + "</span>";
            }
            return val;
        }
        tiny.loadJs(a, "utf-8", function () {
            if (!(typeof zjlx_hq == "undefined" || zjlx_hq == null)) {
                data = zjlx_hq.quotation[0].split(',');
                if (data.length > 1) {
                    str = tableCache.replace(/{code}/ig, data[1]);
                    str = str.replace(/{name}/ig, data[2]);
                    str = str.replace(/{xg}/ig, '<a href="http://quote.eastmoney.com/' + data[1] + '.html" target="_blank">行情</a>&nbsp;&nbsp;<a href="http://data.eastmoney.com/notices/stock/' + data[1] + '.html" target="_blank">公告</a>&nbsp;&nbsp;<a href="http://data.eastmoney.com/bbsj/' + data[1] + '.html" target="_blank">报表</a>&nbsp;&nbsp;<a href="http://f10.eastmoney.com/f10_v2/ShareholderResearch.aspx?code=' + (matket == "1" ? "sh" : "sz") + code + '" target="_blank">F10</a>&nbsp;&nbsp;<a href="http://guba.eastmoney.com/list,' + data[1] + '.html" target="_blank">股吧</a>');
                    str = str.replace(/{price}/ig, getColorStr(data[5], data[10], 0));
                    str = str.replace(/{change}/ig, getColorStr(data[10], data[10], 0));
                    str = str.replace(/{percent}/ig, getColorStr(data[11], data[10], 0));
                    str = str.replace(/{zf}/ig, data[13]);
                    str = str.replace(/{amount}/ig, data[9]);
                    str = str.replace(/{volume}/ig, data[8]);
                    str = str.replace(/{last}/ig, getColorStr(data[3]));
                    str = str.replace(/{open}/ig, getColorStr(data[4], data[4], data[3]));
                    str = str.replace(/{height}/ig, getColorStr(data[6], data[6], data[3]));
                    str = str.replace(/{low}/ig, getColorStr(data[7], data[7], data[3]));

                    str = str.replace(/{hsl}/ig, data[23]);
                    str = str.replace(/{lb}/ig, data[22]);
                    str = str.replace(/{syl}/ig, (data[24] < 0 ? "--" : data[24]));
                }
                else if (data.length == 1) {
                    str = tableCache.replace(/{code}/ig, _stockCode);
                    str = str.replace(/{name}/ig, _stockName);

                    str = str.replace(/{price}/ig, "-");
                    str = str.replace(/{change}/ig, "-");
                    str = str.replace(/{percent}/ig, "-");
                    str = str.replace(/{zf}/ig, "-");
                    str = str.replace(/{amount}/ig, "-");
                    str = str.replace(/{volume}/ig, "-");
                    str = str.replace(/{last}/ig, "-");
                    str = str.replace(/{open}/ig, "-");
                    str = str.replace(/{height}/ig, "-");
                    str = str.replace(/{low}/ig, "-");

                    str = str.replace(/{hsl}/ig, "-");
                    str = str.replace(/{lb}/ig, "-");
                    str = str.replace(/{syl}/ig, "-");
                }
                self_select_tab.find("tbody").html(str);
            }
        }, true);
    }
})();
