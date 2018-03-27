; (function ($) {
    var emChart = window.emChart || {};
    emChart.version = '1.1.0';
    window.emChart = emChart;
    emChart.utils = emChart.utils || {}; // Utility subsystem
    emChart.models = emChart.emChart || {}; //stores all the possible models/components
    emChart.charts = {}; //stores all the ready to use charts
    emChart.addGraph = function (options) {

    }
    emChart.utils.addSpace = function (str, space) {
        if (str) return str.replace(/\s+/g, "").replace(eval('/.{' + space + '}(?!$)/g'), '$& '); else return "";
    }

    emChart.utils.addMarkLogo = function (container, enabled) {
        if (enabled) {
            $(".highcharts-container", "#" + container).append("<div class=\"highcharts-logo\" style=\"color: #909090;font-size: 10px;background: no-repeat scroll 0 0;width:110px;height: 25px;fill: #909090;position:absolute;z-index:-1;\"></div>");
            var logoBox = $("svg>text", "#" + container);
            if (!$.isEmptyObject(logoBox) && logoBox.length !== 0) {
                if ($("svg>text", "#" + container)) {
                    $(".highcharts-logo", "#" + container).css({
                        top: ($("svg>text", "#" + container).attr("y")) + "px",
                        left: ($("svg>text", "#" + container).attr("x")) + "px"
                    });
                    $(".highcharts-background").css({ "fill": "transparent" });
                }
            } else {
                var mark = $(".highcharts-data-labels", "#" + container).next("span");
                if (mark && !mark.length)
                    mark = $(".highcharts-series-group", "#" + container).next("span");
                $(".highcharts-logo", "#" + container).css({
                    top: (+(typeof (mark.css("top")) == "undefined" ? '0' : mark.css("top")).replace("px", "") + 12) + "px",
                    left: mark.css("left"),
                    "z-index": 0
                });
            }
        }
    }

    emChart.models.BarChart = function (options) {
        var defaults = {
            container: "",
            dataUrl: "",
            type: "",
            xLableSpace: 1,
            d3enabled: true,
            duration: 0,
            tooltip: {
                shared: false,
                useHTML: true,
                followPointer: false,
                valueSuffix: ''
            },
            yAxis: {
                enabled: true,
                tickInterval: null,
                opposite: false,
                labels: {
                    enabled: true
                }
            },
            xAxis: {
                labels: {
                    width: "13px"
                },
                tickInterval: 1
            },
            credits: {
                enabled: true,
                position: {
                    x: -150,
                    y: -290
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {},
                    animation: false
                }
                , column: {
                    pointWidth: 20
                }
            },
            legend: {
                enabled: true
            }
        }
        var type = { link: "link" };

        var settings = $.extend({}, defaults, options);

        if (settings.type == type.link) {
            if ($.isPlainObject(settings.plotOptions.series.dataLabels)) {
                settings.plotOptions.series.dataLabels = {
                    enabled: true,
                    rotation: 0,
                    color: '#000',
                    align: 'center',
                    //x: 0,
                    y: -5,
                    style: {
                        fontSize: '9px',
                        fontFamily: 'Arial'
                    }
                }
            }
            settings.yAxis.enabled = true;
            settings.yAxis.labels.enabled = false;
            settings.legend.enabled = false;
        }

        if (settings.xLableSpace) {
            switch (settings.xLableSpace) {
                case 1:
                    settings.xAxis.labels.width = "13px";
                    break;
                case 2:
                    settings.xAxis.labels.width = "24px";
                    break;
                case 3:
                    settings.xAxis.labels.width = "26px";
                    break;
                default:
                    settings.xAxis.labels.width = "auto";
                    break;
            }
        }

        void function () {
            loadData();
            if (settings.duration) update();
        }()

        function loadData() {
            if (settings.chartdata) {
                var result = settings.chartdata;
                var categories = [], series = [];
                if (settings.type != type.link) {
                    $.each(result.categories, function (key, value) {
                        categories.push(emChart.utils.addSpace(value, settings.xLableSpace));
                    });
                }
                else {
                    $.each(result.series, function (key, value) {
                        var seriesData = value.data;
                        $.each(seriesData, function (i, v) {
                            categories.push('<a target="_self" href="' + v.linkUrl + '">' + emChart.utils.addSpace(result.categories[i], settings.xLableSpace) + '</a>');
                        });
                    })
                }
                series = result.series;
                createChart(categories, series);
            }
            else {
                $.getJSON(settings.dataUrl + "?t=" + (new Date()).getTime().toString(), function (result) {
                    var categories = [], series = [];
                    if (settings.type != type.link) {
                        $.each(result.categories, function (key, value) {
                            categories.push(emChart.utils.addSpace(value, settings.xLableSpace));
                        });
                    }
                    else {
                        $.each(result.series, function (key, value) {
                            var seriesData = value.data;
                            $.each(seriesData, function (i, v) {
                                categories.push('<a target="_self" href="' + v.linkUrl + '">' + emChart.utils.addSpace(result.categories[i], settings.xLableSpace) + '</a>');
                            });
                        })
                    }
                    series = result.series;
                    createChart(categories, series);
                })
            }
        }

        function update() {
            setInterval(function () {
                loadData();
            }, settings.duration);
        }

        function createChart(categories, series) {
            var options = ({
                chart: {
                    type: 'column',
                    marginTop: 15,
                    renderTo: settings.container,
                    options3d: {
                        enabled: settings.d3enabled,
                        alpha: 10,
                        beta: 0,
                        depth: 100,
                        viewDistance: 0
                    }
                },
                colors: ['#ff3f3e', 'blue', 'green'],
                title: {
                    text: ''
                },
                xAxis: {
                    gridLineDashStyle: 'solid',
                    gridLineColor: '#d0d0d0',
                    gridLineWidth: 0.5,
                    categories: categories,
                    tickInterval: settings.xAxis.tickInterval,
                    labels: {
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Arial',
                            color: "#000",
                            width: settings.xAxis.labels.width,
                            textAlign: "center"
                        },
                        x: 0,
                        useHTML: true
                    }
                },
                yAxis: {
                    enabled: settings.yAxis.enabled,
                    gridLineDashStyle: 'solid',
                    gridLineColor: '#d0d0d0',
                    gridLineWidth: 0.5,
                    tickInterval: settings.yAxis.tickInterval,
                    opposite: settings.yAxis.opposite || false,
                    title: {
                        text: ''
                    },
                    labels: {
                        enabled: settings.yAxis.labels.enabled
                    }
                },
                legend: {
                    enabled: settings.legend.enabled,
                    padding: 0

                },
                credits: {
                    enabled: settings.credits.enabled,
                    href: 'http://eastmoney.com/',
                    position: {
                        x: settings.credits.position.x,
                        y: settings.credits.position.y
                    },
                    style: {
                        cursor: 'pointer',
                        color: '#909090',
                        fontSize: '10px',
                        width: '114px',
                        height: '25px'
                    },
                    text: ''
                },
                tooltip: {
                    shared: settings.tooltip.shared,
                    useHTML: settings.tooltip.useHTML,
                    followPointer: settings.tooltip.followPointer,
                    formatter: function () {
                        var str;
                        var valueSuffix = settings.tooltip.valueSuffix || '';
                        if (this.point) {
                            var lableName = "", suffix = "", color = "#000";
                            if (this.series.name.indexOf("Series") < 0) {
                                lableName = this.series.name.replace(/[\( \（].*[\)\）]/gi, "");
                                suffix = /[\( \（](.*)[\)\）]/gi.exec(this.series.name);
                                color = this.series.color;
                            } else {
                                lableName = this.key.replace(/[\( \（].*[\)\）]/gi, "");
                                suffix = /[\( \（](.*)[\)\）]/gi.exec(this.key);
                                color = this.point.color;
                            }
                            suffix && suffix.length === 2 && (suffix = suffix[1]) || (suffix = "");
                            str = '<b style="font-size:11px">' + this.x.replace(/\s+/g, "") + '</b><br/>'
                            str += '<p style="margin:0;padding: 0px 5px;font-size:11px;line-height:18px;" ><span style="color:' + color + ';">' + lableName + '</span>';
                            str += ':<b>' + this.y + valueSuffix + '</b> <span>' + suffix + '</span></p>';
                        }
                        else {
                            str = '<b style="font-size:11px">' + this.x.replace(/\s+/g, "") + '</b><br/>'
                            for (var i = 0; i < this.points.length; i++) {
                                var lableName = "", suffix = "", color = "#000";
                                if (this.points[i].series.name.indexOf("Series") < 0) {
                                    lableName = this.points[i].series.name.replace(/[\( \（].*[\)\）]/gi, "");
                                    suffix = /[\( \（](.*)[\)\）]/gi.exec(this.points[i].series.name);
                                    color = this.points[i].series.color;
                                } else {
                                    lableName = this.points[i].key.replace(/[\( \（].*[\)\）]/gi, "");
                                    suffix = /[\( \（](.*)[\)\）]/gi.exec(this.points[i].key);
                                    color = this.points[i].point.color;
                                }
                                suffix && suffix.length === 2 && (suffix = suffix[1]) || (suffix = "");
                                str += '<p style="margin:0;padding: 0px 5px;font-size:11px;line-height:18px;" ><span style="color:' + color + ';">' + lableName + '</span>';
                                str += ':<b>' + this.points[i].y + valueSuffix + '</b> <span>' + suffix + '</span></p>';
                            }
                        }
                        return str;
                    }
                },
                plotOptions: {
                    series: {
                        dataLabels: settings.plotOptions.series.dataLabels,
                        animation: settings.plotOptions.series.animation
                    }
                },
                series: series
            });
            var chart = new Highcharts.Chart(options);
            emChart.utils.addMarkLogo(settings.container, settings.credits.enabled);
            if (settings.type == type.link) {
                setCustomStyle();
            }
        }
        function setCustomStyle() {
            $("#" + settings.container).find("span a").css({ color: "#00298f" });
        }
    }

    emChart.models.LineChart = function (options) {
        var defaults = {
            container: "",
            dataUrl: "",
            duration: 0,
            credits: {
                enabled: true,
                position: {
                    x: -150,
                    y: -230
                }
            },
            yAxis: {
                opposite: false
            },
            xAxis: {
            }
        }
        var settings = $.extend({}, defaults, options);

        void function () {
            loadData();
            if (settings.duration) update();
        }()

        function loadData() {
            if (settings.chartdata) {
                var result = settings.chartdata;
                var categories = [], series = [];
                categories = result.categories;
                series = result.series;
                createChart(categories, series);
            }
            else {
                $.getJSON(settings.dataUrl + "?t=" + (new Date()).getTime().toString(), function (result) {
                    var categories = [], series = [];
                    categories = result.categories;
                    series = result.series;
                    createChart(categories, series);
                })
            }
        }

        function update() {
            setInterval(function () {
                loadData();
            }, settings.duration);
        }

        function createChart(categories, series) {
            var options = ({
                chart: {
                    zoomType: 'x',
                    spacingRight: 20,
                    renderTo: settings.container
                },
                colors: ["#FF0033", "#0000FF", "#00CC00"],
                title: {
                    text: ''
                },
                xAxis: {
                    type: 'datetime',
                    gridLineColor: '#d0d0d0',
                    gridLineWidth: 1,
                    categories: categories,
                    tickInterval: settings.xAxis.tickInterval || null,
                    labels: {
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Arial',
                            color: "#000"
                        }
                    }
                },
                yAxis: {
                    //gridLineDashStyle: 'dash',
                    gridLineColor: '#d0d0d0',
                    gridLineWidth: 1,
                    //lineWidth: 1,
                    //lineColor: '#C0D0E0',
                    //minorTickInterval: 'auto',
                    title: {
                        text: ''
                    },
                    labels: {
                        enabled: true,
                        fontSize: '12px'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#666666'
                    }],
                    opposite: settings.yAxis.opposite || false
                },
                legend: {
                    enabled: true,
                    padding: 0
                },
                plotOptions: {
                    series: {
                        lineWidth: 0.6,
                        radius: 0,
                        animation: false,
                        marker: {
                            enabled: false,
                            hover: {
                                radius: 0,
                                radiusPlus: 0
                            },
                            radius: 2,
                            symbol: 'circle'
                        }
                    }
                },
                credits: {
                    enabled: settings.credits.enabled,
                    href: 'http://eastmoney.com/',
                    position: {
                        x: settings.credits.position.x,
                        y: settings.credits.position.y
                    },
                    style: {
                        cursor: 'pointer',
                        color: '#909090',
                        fontSize: '10px',
                        width: '114px',
                        height: '25px'
                    },
                    text: ''
                },
                tooltip: {
                    borderWidth: 1,
                    crosshairs: {
                        width: 1,
                        color: '#3399CC'
                    },
                    //shared: true,
                    formatter: function () {
                        var s, str;
                        if (this.point.series.name) {
                            if (/\d{8,20}/.test(this.x)) {
                                var tdate = new Date(this.x - 28800000);
                                s = "<b>";
                                s += this.point.series.name.replace(/[\( \（].*[\)\）]/gi, "") + '<br/> ' + this.y + ' [' + tdate.getHours() + ":" + (tdate.getMinutes() < 10 ? ('0' + tdate.getMinutes()) : tdate.getMinutes()) + "]";
                                s += "</b><br/>";
                            }
                            else {
                                s = "<b>"
                                s = this.point.series.name.replace(/[\( \（].*[\)\）]/gi, "") + '<br/> ' + this.y + ' [' + this.x + "]";
                                s += "</b><br/>";
                            }
                        }
                        else { s = '' + this.x + ': ' + this.y; }
                        return s;
                    },
                    headerFormat: '',
                    style: {
                        fontSize: '11px',
                        zIndex: '2000'
                    }
                },
                series: series
            });
            var chart = new Highcharts.Chart(options);
            emChart.utils.addMarkLogo(settings.container, settings.credits.enabled);
        }
    }

    emChart.models.pieChart = function (options) {
        var defaults = {
            container: "",
            dataUrl: "",
            duration: 0,
            credits: {
                enabled: false
            },
            options3d: {
                enabled: false
            },
            pie: {
                size: "115%",
                showInLegend: true,
                innerSize: 0,
                dataLabels: {
                    enabled: true,
                    color: "#000",
                    distance: -30
                }
            },
            legend: {
                enabled: true,
                x: 0,
                margin: 10
            }
        }
        var settings = $.extend({}, defaults, options);

        if (settings.options3d.enabled) {

        }


        void function () {
            loadData();
            if (settings.duration) update();
        }()

        function loadData() {
            if (settings.chartdata) {
                var result = settings.chartdata;
                var categories = [], series = [];
                categories = result.categories;
                series = result.series;
                createChart(categories, series);
            }
            else {
                $.getJSON(settings.dataUrl + "?t=" + (new Date()).getTime().toString(), function (result) {
                    var categories = [], series = [];
                    categories = result.categories;
                    series = result.series;
                    createChart(categories, series);
                })
            }

        }

        function update() {
            setInterval(function () {
                loadData();
            }, settings.duration);
        }

        function createChart(categories, series) {
            var options = ({
                chart: {
                    type: 'pie',
                    options3d: {
                        enabled: settings.options3d.enabled,
                        alpha: 50,
                        beta: 0
                    },
                    renderTo: settings.container
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: "",
                    pointFormat: '<b style="font-weight:bold;" >{point.name}:</b><span style="color:#000">{point.percentage:.1f}% ({point.y})</span>'
                },
                legend: {
                    enabled: settings.legend.enabled,
                    align: "right",
                    floating: false,
                    borderColor: "#d3d3d3",
                    borderWidth: 1,
                    layout: "vertical",
                    verticalAlign: "middle",
                    padding: 8,
                    itemMarginTop: 1,
                    itemMarginBottom: 1,
                    itemStyle: {
                        fontWeight: "normal",
                        fontSize: "12px"
                    },
                    x: settings.legend.x,
                    margin: settings.legend.margin
                },
                plotOptions: {
                    pie: {
                        size: settings.pie.size,
                        borderWidth: 0,
                        allowPointSelect: true,
                        innerSize: settings.pie.innerSize,
                        showInLegend: settings.pie.showInLegend,
                        depth: 30,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: settings.pie.dataLabels.enabled,
                            distance: settings.pie.dataLabels.distance,
                            color: settings.pie.dataLabels.color,
                            style: {
                                fontSize: '10px',
                                lineHeight: '10px'
                            },
                            format: '<span>{point.percentage:.1f}%</span>'
                        },
                        padding: 0,
                        x: 0
                    },
                    series: {
                        animation: false
                    }
                },
                series: series
            });
            var chart = new Highcharts.Chart(options);
        }
    }

    emChart.models.cobChart = function (options) {
        var defaults = {
            container: "",
            dataUrl: "",
            duration: 0,
            yAxisLableSuffix: [],
            credits: {
                enabled: true,
                position: {
                    x: -150,
                    y: -230
                }
            }
        }
        var settings = $.extend({}, defaults, options);

        void function () {
            loadData();
            if (settings.duration) update();
        }()

        function loadData() {
            if (settings.chartdata) {
                var result = settings.chartdata;
                var categories = [], series = [];
                categories = result.categories;
                series = result.series;
                createChart(categories, series);
            }
            else {
                $.getJSON(settings.dataUrl + "?t=" + (new Date()).getTime().toString(), function (result) {
                    var categories = [], series = [];
                    categories = result.categories;
                    series = result.series;
                    createChart(categories, series);
                })
            }
        }
        function update() {
            setInterval(function () {
                loadData();
            }, settings.duration);
        }
        function createChart(categories, series) {
            var options = ({
                chart: {
                    zoomType: 'xy',
                    renderTo: settings.container
                },
                colors: ["#FF0033", "#0000FF", "#00CC00", "#89A54E"],
                title: {
                    text: ''
                },
                xAxis: {
                    categories: categories
                },
                yAxis: [{
                    gridLineColor: '#d0d0d0',
                    gridLineWidth: 1,
                    title: {
                        text: ''
                    },
                    labels: {
                        enabled: true,
                        fontSize: '12px',
                        format: '{value} ' + (typeof settings.yAxisLableSuffix[1] != "undefined" && settings.yAxisLableSuffix[1] || "")
                    }
                },
                    {
                        gridLineColor: '#d0d0d0',
                        gridLineWidth: 1,
                        title: {
                            text: ''
                        },
                        labels: {
                            enabled: true,
                            fontSize: '12px',
                            format: '{value} ' + (typeof settings.yAxisLableSuffix[0] != "undefined" && settings.yAxisLableSuffix[0] || "")
                        },
                        opposite: true
                    }],
                legend: {
                    enabled: true,
                    padding: 0
                },
                plotOptions: {
                    series: {
                        lineWidth: 1,
                        radius: 0,
                        animation: false,
                        marker: {
                            fillColor: 'white',
                            lineWidth: 1,
                            lineColor: "#89A54E"
                        }
                    }
                },
                credits: {
                    enabled: settings.credits.enabled,
                    href: 'http://eastmoney.com/',
                    position: {
                        x: settings.credits.position.x,
                        y: settings.credits.position.y
                    },
                    style: {
                        cursor: 'pointer',
                        color: '#909090',
                        fontSize: '10px',
                        width: '114px',
                        height: '25px'
                    },
                    text: ''
                },
                tooltip: {
                    shared: true,
                    borderWidth: 1,
                    crosshairs: {
                        width: 1,
                        color: '#3399CC'
                    },
                    shared: true,
                    headerFormat: '',
                    style: {
                        fontSize: '11px',
                        zIndex: '2000'
                    }
                },
                series: series
            });
            var chart = new Highcharts.Chart(options);
            emChart.utils.addMarkLogo(settings.container, settings.credits.enabled);
        }
    }
})(jQuery);