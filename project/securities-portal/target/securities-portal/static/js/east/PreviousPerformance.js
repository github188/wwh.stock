//上榜日期接口
//http://115.236.103.92/EM_DataCenter_V3/api/LHBGGSBLX/GetLHBGGSBLX?tkn=eastmoney&scode=300059.SZ&dayNum=100&startDateTime=2002-12-12&endDateTime=2016-02-01&sortfield=1&sortdirec=1&pageNum=1&pageSize=50&cfg=lhbggsblx
; (function () {
    var $ = jQuery;
    //K线图表变量
    var myChart_k = {};
    //柱状图表变量
    var myChart_bar = {};
    //K线图参数变量
    var option_k = {};
    //柱状图参数变量
    var option_bar = {};
    //股票代码
    var code = $("#codeID").val();

    //配置图表组件路径
    require.config({
        paths: {
            echarts: 'echarts',
            'echarts/chart/k': 'k',
            'echarts/chart/bar': 'bar'
        }
    });
    //动态按需加载图表组件
    require(
        [
            'echarts',
            'echarts/chart/k',
            'echarts/chart/bar'
        ],
            function (ec) {
                //获取初始化k线图表
                var emchart_k_con_id = "emchart-k";
                myChart_k = ec.init(document.getElementById(emchart_k_con_id));

                //显示loading
                myChart_k.showLoading({
                    text: '数据加载中...',
                    animation: false,
                    effect: "bar",
                    textStyle: {
                        fontSize: 20
                    }
                });

                //获取初始化柱状图表
                //var emchart_bar_con_id = "emchart-bar";
                //myChart_bar = ec.init(document.getElementById(emchart_bar_con_id));

                //显示loading
                //myChart_bar.showLoading({
                //    text: '数据加载中...',
                //    animation: false,
                //    effect: "bar",
                //    itemStyle: {
                //        normal: {
                //            shadowColor: "red",
                //            opacity: 0.5
                //        }
                //    },
                //    textStyle: {
                //        fontSize: 20
                //    }
                //});

                var setTimeout_k = setTimeout(function () {
                    //隐藏loading
                    myChart_k.hideLoading();
                    //隐藏loading
                    //myChart_bar.hideLoading();
                    drawKChart([], [], [], []);
                }, 5000);

                window.js = function (dataJ) {
                    //var datas = ["20150924,14.95,15.27,15.3,14.87,14.95,78862576", "20150925,15.15,15.59,15.62,15.15,15.27,136070896", "20150928,15.55,15.92,15.96,15.42,15.59,91425816", "20150929,15.79,15.97,15.99,15.67,15.92,114190256", "20150930,15.95,16.63,16.65,15.9,15.97,211038128", "20151008,16.8,15.87,16.8,15.83,16.63,140057152", "20151009,15.97,15.9,16.15,15.85,15.87,41525836", "20151012,15.9,16.06,16.25,15.73,15.9,93633304", "20151013,15.98,15.97,16.11,15.85,16.06,54698800", "20151014,15.81,15.9,16.09,15.81,15.97,44297812", "20151026,16.03,16.28,16.57,16.03,15.9,101041056", "20151027,16.23,16.32,16.45,16.15,16.28,74934000", "20151028,16.26,16.31,16.54,16.09,16.32,74226288", "20151029,16.3,16.59,16.63,16.23,16.31,75760568", "20151030,16.5,16.39,16.68,16.38,16.59,45465416", "20151102,16.3,16.41,16.52,16.22,16.39,38122792", "20151103,16.4,16.46,16.77,16.37,16.41,60485064", "20151104,16.43,16.84,16.92,16.43,16.46,65053992", "20151105,16.85,17.71,18.07,16.75,16.84,231799696", "20151106,17.52,17.88,18.18,17.51,17.71,127594224", "20151109,17.92,18.15,19.19,17.88,17.88,157820064", "20151110,18,18.37,18.69,17.82,18.15,140427136", "20151111,18.41,17.73,18.49,17.5,18.37,92130224", "20151112,17.8,17.36,17.83,17.15,17.73,77790160", "20151113,17.2,17.58,17.6,17.15,17.36,73433152", "20151116,17.34,17.42,17.58,17.2,17.58,45144664", "20151117,17.46,17.44,17.81,17.42,17.42,63539280", "20151118,17.47,17.39,17.68,17.24,17.44,57584576", "20151119,17.3,17.9,17.99,17.3,17.39,102543448", "20151120,17.81,18.47,18.63,17.78,17.9,148206208", "20151123,18.51,19.06,19.13,18.44,18.47,171203792", "20151124,18.9,19.05,19.12,18.59,19.06,129389144", "20151125,18.9,19.67,19.68,18.44,19.05,326524768", "20151126,19.5,19.37,19.7,19.2,19.67,214944240", "20151127,19.16,18.53,19.42,18.4,19.37,105608736", "20151130,18.44,18.65,18.81,17.6,18.53,97633824", "20151201,18.35,18.66,19,18.08,18.65,63430576", "20151202,18.51,19.85,20,18.48,18.66,153608080", "20151203,19.7,19.25,19.77,19.22,19.85,110346328", "20151204,19.27,18.71,19.27,18.69,19.25,70275848", "20151207,18.71,19.45,19.5,18.71,18.71,83346816", "20151208,19.28,20.1,20.12,18.81,19.45,239884944", "20151209,19.58,19.23,19.58,19.11,20.1,92049464", "20151210,19.34,19.27,19.88,19.11,19.23,57091312", "20151211,19.01,18.6,19.22,18.43,19.27,87066824", "20151214,18.31,18.45,18.63,18.03,18.6,70736960", "20151215,18.44,18.31,18.82,18.17,18.45,51685772", "20151216,18.36,18.18,18.45,18.11,18.31,38700640", "20151217,18.26,18.49,18.61,18.2,18.18,53304072", "20151218,18.49,18.81,19.8,18.43,18.49,92211920", "20151221,18.79,19.14,19.4,18.54,18.81,82490080", "20151222,19.01,18.9,19.29,18.77,19.14,53122744", "20151223,18.91,18.88,19.13,18.61,18.9,66128172", "20151224,18.85,19.04,19.14,18.67,18.88,57171552", "20151225,19.02,19.32,19.48,18.93,19.04,55427896", "20151228,19.37,18.7,19.43,18.62,19.32,49980696", "20151229,18.63,18.74,18.92,18.39,18.7,47288464", "20151230,18.71,18.57,18.78,18.31,18.74,35687384", "20151231,18.52,18.27,18.52,18.26,18.57,27936138", "20160104,18.28,17.8,18.28,17.55,18.27,42240608", "20160105,17.51,17.96,18.06,17.4,17.8,58054792", "20160106,17.9,18.1,18.14,17.69,17.96,46772652", "20160107,17.8,17.51,17.9,17.4,18.1,11350479", "20160108,17.74,17.49,17.88,16.91,17.51,71918296", "20160111,17.21,17.05,17.45,16.96,17.49,90177136", "20160112,17.17,17.45,17.52,17.08,17.05,55374456", "20160113,17.57,17.39,17.9,17.35,17.45,47869312", "20160114,17,17.92,17.99,16.89,17.39,54838832", "20160115,17.8,17.58,18.15,17.52,17.92,46723140", "20160118,17.43,17.68,17.99,17.32,17.58,32729006", "20160119,17.78,17.77,17.88,17.5,17.68,29807160", "20160120,17.61,17.25,17.7,17.08,17.77,35968636", "20160121,17.22,16.58,17.46,16.5,17.25,34197116", "20160122,16.84,17.02,17.11,16.49,16.58,42007320", "20160125,17,17.03,17.17,16.72,17.02,23558972", "20160126,16.96,16.66,17.14,16.4,17.03,38279768", "20160127,16.75,16.68,16.98,16.51,16.66,44291308", "20160128,16.76,16.58,16.82,15.88,16.68,38902180", "20160129,16.52,16.9,17.05,16.41,16.58,47429000", "20160201,16.99,16.5,17,16.41,16.9,29139596"];
                    //日期坐标数组
                    var dates = [];
                    //行情日K线数组
                    var k_datas = [];
                    //成交量数组
                    var v_datas = [];
                    //最低收盘价
                    var least_price = 0;
                    //昨日收盘价
                    var yes_clo_price = 0;
                    var datas = dataJ.data;
                    for (var i in datas) {
                        var str = datas[i];
                        if (typeof str == "string") {
                            var arr = str.split(",");
                            //成交量数据
                            if (arr[2] > arr[1]) {
                                var cl = "#ff4b1f";
                            } else if (arr[2] == arr[1] && arr[2] >= yes_clo_price) {
                                var cl = "#ff4b1f";
                            } else {
                                var cl = "#00aa11";
                            }

                            //成交量变色
                            v_datas.push({
                                value: arr[arr.length - 1],
                                itemStyle: {
                                    normal: {
                                        color: cl,
                                        label: {
                                            show: false
                                        }
                                    }
                                }

                            });
                            arr.pop();
                            arr.pop();
                            //交易日期
                            //dates.push(yjfp_tools.format_date(arr[0]));
                            dates.push(arr[0]);
                            arr.reverse();
                            arr.pop();
                            arr.reverse();
                            arr.pop();
                            //初始化最小收盘价
                            if (i == 0) {
                                least_price = arr[1];
                            }
                            //更小的收盘价替换
                            if (arr[3] < least_price) {
                                least_price = arr[1];
                            }

                            //if (arr[1] > arr[0]) {
                            //    var k_c = "#ff4b1f";
                            //} else if (arr[2] == arr[1] && arr[2] >= yes_clo_price) {
                            //    var k_c = "#ff4b1f";
                            //} else {
                            //    var k_c = "#00aa11";
                            //}

                            //昨日收盘价
                            yes_clo_price = arr[2];

                            //k线图数据
                            k_datas.push({
                                value: arr,
                                itemStyle: {
                                    normal: {
                                        color: cl,           // 阳线填充颜色
                                        color0: cl,          // 阴线填充颜色
                                        lineStyle: {
                                            width: 2,
                                            color: cl,     // 阳线边框颜色
                                            color0: cl     // 阴线边框颜色
                                        }
                                    },
                                    emphasis: {
                                        color: cl,         // 阳线填充颜色
                                        color0: cl         // 阴线填充颜色
                                    }
                                }
                            });
                            //k_datas.push(arr);
                        }
                    }
                    //获取上榜日数组
                    //var point_list2 = getDateList();//旧的方法无法无法判断日期是否是工作日
                    var point_list = dataJ.MarkPoint;
                    var point_arr = [];
                    for (var i in point_list) {
                        var date = point_list[i].TDate;
                        var point = { name: point_list[i].TName + "<br/>" + date + point_list[i].NLine, value: 0, xAxis: date, symbol: point_list[i].Symbol, yAxis: 0 };
                        point_arr.push(point);

                        //{ name: '最高', value: 2262.94, xAxis: '20151218', yAxis: 16 }
                        //point_arr.push({ xAxis: "2015-12-24", yAxis: 16 });
                        //point_arr.push({ xAxis: "2015-12-30", yAxis: 16 });
                    }
                    drawKChart(dates, k_datas, v_datas, point_arr);
                }

                //组合成特定格式的参数  前六位为股票代码  最后一位（1标识沪市，2标识深市）
                if (code) {
                    if (GetMktByCode(code) == "sh") {
                        var param = code + "1";
                    } else if (GetMktByCode(code) == "sz") {
                        var param = code + "2";
                    }
                } else {
                    var param = ""
                }
                var ps = 500;
                var authority = 'FA';
                tiny.loadJs("http://data.eastmoney.com/DataCenter_V3/yjfp/GetHistory.ashx?pagesize=" + ps + "&mkcode=" + param + "&code=" + code + "&authority=" + authority, "GB2312", function (script) {
                });

                //画出k线图
                function drawKChart(dates, k_datas, v_datas, point_arr) {
                    //K线图参数
                    option_k = {
                        title: {
                            text: '日K线走势图',
                            show: false
                        },
                        tooltip: {
                            trigger: 'axis',
                            showDelay: 0,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                            formatter: function (params) {
                                var res = params[0].name;
                                //res += '<br/>' + params[0].seriesName;
                                res += '<br/>  开盘 : ' + params[0].value[0] + '<br/>  收盘 : ' + params[0].value[1];
                                res += '<br/>  最高 : ' + params[0].value[2] + '<br/>  最低 : ' + params[0].value[3];
                                return res;
                            },
                            axisPointer: {
                                type: 'cross',
                                //lineStyle: {
                                //    color: '#48b',
                                //    width: 2,
                                //    type: 'solid'
                                //},
                                crossStyle: {
                                    color: 'grey',
                                    width: 1,
                                    type: 'solid'
                                }
                                //shadowStyle: {
                                //    color: 'rgba(150,150,150,0.3)',
                                //    width: 'auto',
                                //    type: 'default'
                                //}
                            }
                        },
                        legend: {
                            data: ['日K线走势图'],
                            show: true,
                            selectedMode: false
                        },
                        toolbox: {
                            show: false,
                            feature: {
                                mark: { show: true },
                                dataZoom: { show: true },
                                dataView: { show: true, readOnly: false },
                                magicType: { show: true, type: ['line', 'bar'] },
                                restore: { show: true },
                                saveAsImage: { show: true }
                            }
                        },
                        dataZoom: {
                            show: true,
                            realtime: false,
                            start: 0,
                            end: 100,
                            showDetail: false
                        },
                        grid: {
                            x: 80,
                            y: 25,
                            x2: 20,
                            y2: 70
                        },
                        xAxis: [
                            {
                                type: 'category',
                                boundaryGap: true,
                                axisTick: { onGap: false },
                                splitLine: { show: false },
                                data: dates
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value' + "元",
                                scale: true,
                                splitNumber: 9,
                                axisLabel: {
                                    formatter: function (v) {
                                        return v + ''
                                    }
                                },
                                boundaryGap: [0.05, 0.05],
                                splitArea: { show: true }
                            }
                        ],
                        noDataLoadingOption: {
                            text: "暂无数据",
                            effect: function (params) {
                                params.start = function (h) {
                                    //获取图表容器
                                    var chart_container = document.getElementById(emchart_k_con_id);
                                    var chart_container_height = chart_container.offsetHeight;
                                    //无数据时提示信息
                                    var no_data_notice = document.createElement("div");
                                    no_data_notice.className = "no-data-chart";
                                    no_data_notice.innerText = "暂无数据";
                                    no_data_notice.style.paddingTop = chart_container_height / 2 + "px";
                                    //把提示信息添加到图表容器中
                                    chart_container.innerHTML = "";
                                    chart_container.appendChild(no_data_notice);
                                    //隐藏缩放条
                                    option_k.dataZoom.show = false;
                                    //document.getElementById(emchart_bar_con_id).style.display = "none";
                                    //document.getElementById(emchart_bar_con_id).style.visibility = "hidden";
                                    //option_bar.dataZoom.show = false;
                                };
                                params.stop = function () {

                                };
                                return params;
                            },
                            effectOption: {
                                backgroundColor: "#fff"
                            },
                            textStyle: {
                                fontSize: 20
                            }
                        },
                        series: [
                            {
                                name: '日K线走势图',
                                type: 'k',
                                data: k_datas,
                                markPoint: {
                                    //symbol: 'circle',
                                    
                                    //symbolSize: 13,
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                position: 'top', 
                                                formatter: function (param) {
                                                    return '';
                                                },
                                                show:false
                                            },
                                            color: '#333333'
                                        }
                                    },
                                    tooltip: {
                                        trigger: 'item',
                                        formatter: function (params) {
                                            var res = params.name;
                                            return res;
                                        }
                                    },
                                    data: point_arr
                                    //,data: [
                                    //    { name: '最高', value: 2262.94, xAxis: '20151218', yAxis: 16 }
                                    //]
                                }
                            }
                        ]
                    };
                    clearTimeout(setTimeout_k);
                    //设置图表参数
                    myChart_k.setOption(option_k);
                    //隐藏loading
                    myChart_k.hideLoading();
                    //设置图表参数
                    //myChart_bar.setOption(option_bar);
                    //隐藏loading
                    //myChart_bar.hideLoading();
                }

            });

    //图表联动
    //myChart_k.connect([myChart_bar]);
    //myChart_bar.connect([myChart_k]);
    myChart_k.on("click", function (param) {
        var param_name = param.name;
        if (param_name == "上榜日") {
            //http://beta.data.eastmoney.com:8137/stock/lhb,2015-10-16,300059.html
            var url = "/stock/lhb," + param.value + "," + code + ".html";
            window.open(url);
        } else {
            //alert(param.name);
        }
        //setTimeout(function () {
        //    window.onresize = function () {
        //        myChart_k.resize();
        //        myChart_bar.resize();
        //    }
        //}, 200);
    });

})();


