//将接口返回的json数组转成EMChart折线图接受的json对象
//data : json 源数据， c : x轴标尺间隔， p : 是否显示点， start：截断源数据开始位置
function EMChartDataFormat(data, c, p, names, start, colors) {
    if (c == undefined) c = 1;
    if (p == undefined) p = true;
    if (names == undefined) names = [''];
    if (start == undefined) start = 0;
    if (colors == undefined) colors = ["#ff0000", "#00ff00", "#0000ff"];
    if (data) {
        var i = start;
        var xaxis = new Array();
        var series = new Array();

        if (data.X && data.Y) {
            var x_list = data.X.split(',');
            for (; i < x_list.length; i++) {
                if (i % c == 0) {
                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else if (i == x_list.length - 1) {

                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else {
                    xaxis.push({ value: x_list[i], showline: false, show: false });
                }

            }

            for (var j = 0; j < data.Y.length; j++) {
                var y_list = data.Y[j].split(',');
                if (!colors[j]) colors[j] = colors[0];
                if (!names[j]) names[j] = names[0];
                series.push({ name: names[j], color: colors[j], showpoint: true, data: y_list.slice(start, y_list.length) });

            }

            return { x: xaxis, y: series };
        }
    }
    else {
        return null;
    }
}

//将接口返回的json数据转换成EMChart单组柱状图接受的json对象
//data : json 源数据， c : x轴标尺间隔，start：截断源数据开始位置， colors：颜色， hovercolors：悬停时颜色
function EMChartHistogramFormat(data, c, start, color, hovercolor) {
    if (c == undefined) c = 1;
    if (start == undefined) start = 0;
    if (color == undefined) color = "#f00";
    if (hovercolor == undefined) hovercolor = '#f00';
    if (data) {
        var i = start;
        var xaxis = new Array();
        var series;

        if (data.X && data.Y) {
            var x_list = data.X.split(',');
            for (; i < x_list.length; i++) {
                if (i % c == 0) {
                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else if (i == x_list.length - 1) {

                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else {
                    xaxis.push({ value: x_list[i], showline: false, show: false });
                }

            }

            var y_list = data.Y[0].split(',');
            series = { color: color, hoverColor: hovercolor, data: y_list.slice(start, y_list.length) };

            return { x: xaxis, y: series };
        }
    }
    else {
        return null;
    }
}

//将接口返回的json数据转换成EMChart多组柱状图接受的json对象
//data : json 源数据， c : x轴标尺间隔，start：截断源数据开始位置， colors：颜色
function EMChartGroupBarFormat(data, c, start, colors) {
    if (c == undefined) c = 1;
    if (start == undefined) start = 0;
    if (colors == undefined || !Array.isArray(colors)) colors = ["#f00", "#0f0", "#00f"];
    if (data) {
        var i = start;
        var xaxis = new Array();
        var series = new Array();

        if (data.X && data.Y) {
            var x_list = data.X.split(',');
            for (; i < x_list.length; i++) {
                if (i % c == 0) {
                    xaxis.push({ value: x_list[i], colors: colors, show: true });
                }
                else if (i == x_list.length - 1) {

                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else {
                    xaxis.push({ value: x_list[i], colors: colors, show: false });
                }

            }

            //var y_list = data.Y[0].split(',');
            //series = { data: y_list.slice(start, y_list.length) };

            var y_list = new Array();
            for (var j = 0; j < data.Y.length; j++) {
                y_list.push(data.Y[j].split(','));
            }

            for (var k = 0; k < y_list[0].length; k++) {
                var item_data = new Array();
                for (var l = 0; l < y_list.length; l++) {
                    item_data.push(y_list[l][k]);
                }
                series.push({ name: '名称1', data: item_data });
            }

            return { x: xaxis, y: series };
        }
    }
    else {
        return null;
    }
}

//将接口返回的json数组转成 EMChart折线图 接受的json对象
//data : json 源数据， c : x轴标显示个数， p : 是否显示点
function EMChartLineFormat(data, c, p, names, colors) {
    if (c == undefined) c = 9;
    if (p == undefined) p = true;
    if (names == undefined) names = [''];
    if (colors == undefined) colors = ["#ff0000", "#0000ff", "#00ff00"];
    if (data) {
        var xaxis = new Array();
        var series = new Array();

        if (data.X && data.Y) {
            var x_list = data.X.split(',');
            if (x_list.length < c) c = x_list.length;
            var interval = Math.floor(x_list.length / c);

            for (var i = 0; i < x_list.length; i++) {
                if (i % interval == 0) {
                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else if (i == x_list.length - 1)
                {

                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else {
                    xaxis.push({ value: x_list[i], showline: false, show: false });
                }

            }

            for (var j = 0; j < data.Y.length; j++) {
                var y_list = data.Y[j].split(',');
                if (!colors[j]) colors[j] = colors[0];
                if (!names[j]) names[j] = names[0];
                series.push({ name: names[j], color: colors[j], showpoint: p, data: y_list });

            }

            return { x: xaxis, y: series };
        }
    }
    return null;
}

function EMChartDoubleLineFormat(data, c, p, names, colors) {
    if (c == undefined) c = 9;
    if (p == undefined) p = true;
    if (names == undefined) names = [''];

    if (colors == undefined) colors = ["#ff0000", "#0000ff", "#00ff00"];
    if (data) {
        var xaxis = new Array();
        var series1 = new Array();
        var series2 = new Array();
        if (data.X && data.Y) {
            var x_list = data.X.split(',');
            if (x_list.length < c) c = x_list.length;
            var interval = Math.floor(x_list.length / c);

            for (var i = 0; i < x_list.length; i++) {
                if (i % interval == 0) {
                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else if (i == x_list.length - 1) {

                    xaxis.push({ value: x_list[i], showline: true, show: true });
                }
                else {
                    xaxis.push({ value: x_list[i], showline: false, show: false });
                }

            }

            var y_list1 = data.Y[0].split(',');
            series1.push({ name: names[0], color: colors[0], showpoint: p, data: y_list1 });

            var y_list2 = data.Y[1].split(',');
            series2.push({ name: names[1], color: colors[1], showpoint: p, data: y_list2 });


            return { x: xaxis, y1: series1,y2:series2 };
        }
    }
    return null;
}
