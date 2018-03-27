/*
 lihaibin  20141027/  把列表数据转换成图表数据并加载
 参数说明options
 {
 template:图表数据模型 根元素数组填“$$$”
 objid: 加载图表的元素id
 url: 列表数据的请求地址
 funcs: 对应$$$ 的方法数组
 cb：数据转换后的回调函数，函数内加载图表
 }
 */

function chartdata(options) {
    var defaults = {
        template: {
            "categories": "$$$",
            "series": [
                {
                    "data": "$$$"
                }]
        },
        objid: "container",
        url: "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKHY&sty=DCFFPBFM&st=(BalFlowMain)&sr=1&p=1&ps=&ps=&js=&token=894050c76af8597a853f5b408b759f5d",
        funcs: [function (v) { return v[2]; }, function (v) { return { "name": "主力净流入(亿元)", "y": isNaN(parseFloat(v[3])) ? 0 : parseFloat((v[3] / 10000).toFixed(2)), "color": "#ff3f3e", "linkUrl": "http://data.eastmoney.com/bkzj/472.html" }; }, function (v) { return v; }, function (v) { return v; }, function (v) { return v; }, function (v) { return v; }],
        cb: function (data) {
            var chart = emChart.models.BarChart({
                container: "container",
                dataUrl: "/js/Emcharts/examples/3d-column-interactive/data.js",
                type: "link",
                xLableSpace: 1,
                chartdata: data,
                d3enabled: false
            });
        }
    }
    var settings = jQuery.extend(true, defaults, options, {});
    var dks = [];
    var items = [[],[],[],[],[],[],[],[],[],[],[],[],[],[]];
    var recursionJSON = function (data) {
        if (typeof (data) == "object") {
            jQuery.each(data, function (i, n) {
                if (typeof (n) == "object") {
                    recursionJSON(n)
                }
                else if (n == "$$$") {
                    dks.push({ "d": data, "k": i });
                }
            })
        }
    }
    recursionJSON(defaults.template);
    var sjs = Math.random().toString().replace('.', '');
    jQuery.ajax({
        url: defaults.url + "&cb=callback" + sjs,
        dataType: "jsonp",
        scriptCharset:"utf-8",
        jsonpCallback: "callback" + sjs,
        success: function (json) {
            for (var i = 0; i < json.length; i++) {
                var data;
                if (typeof (json[i]) == "object") data = json[i];
                else data = json[i].split(",");
                for (var j = 0; j < dks.length; j++) {
                    var oneline = defaults.funcs[j](data);
                    if (oneline != "NULL") {
                        items[j].push(oneline);
                    }
                }
            }

            for (var i = 0; i < dks.length; i++) {
                dks[i].d[dks[i].k] = items[i];
            }
            defaults.cb(defaults.template);
        }
    });
}