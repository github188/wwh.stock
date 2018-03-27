var EC_BASE = "../echarts/";

require.config({
	paths:{
		echarts: EC_BASE + 'js',
		'echarts/theme/dreamcoat' : EC_BASE + 'theme/dreamcoat' //主题
	}
});

var needs = [
	'echarts',
	'echarts/theme/dreamcoat',
	'echarts/chart/line',
	'echarts/chart/bar'
];

var TBS = function(id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

function RenderEC(dom,option)
{
	require(
		needs,
		function (ec,theme) {
			var ECharts = ec;
			ECharts.theme = theme;
			var myChart1 = ECharts.init(TBS(dom),ECharts.theme);
			myChart1.setOption(option);
		}
	);
}

/*
[value1;value2;value3]
 */
function barData(dataList)
{
	var cnt = Object.getOwnPropertyNames(dataList[0]).length;
	var arrayObj = []; //创建一维
	for(var i=0;i<dataList.length;i++)
	{
		for(var j=0;j<cnt;j++){
			if (i==0) {
				arrayObj[j]=[];//创建二维
			}
			arrayObj[j][i]=dataList[i]['value'+(j+1)];
		}

	}

	return arrayObj;
}

function lcData(nameList, dataList)
{
	var arrayObj = []; //创建一个数组
	for(var i=0;i<nameList.length;i++)
	{
		var obj = {};//一个js对象
		obj.name = nameList[i];
		obj.value = dataList[i];
		obj.itemStyle = pstyle;
		arrayObj.push(obj);
	}

	return arrayObj;
}