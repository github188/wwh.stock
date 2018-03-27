/*
 Man工具包jquery扩展
 write by xwh
 */
jQuery.extend({
  dream:{}
});

//===========================微组件=========================
/*ajax加载中
 *$.dream.tip(str)在当前窗口显示
 *window.parent.$.dream.tip(str)在父窗口显示
 */
jQuery.dream.tip=function(str){
  var global_loading_set=function(){
    var h=$(window).height();
    $("#global_loading,#global_loading_bg").height(h);
    var top=(h-$("#global_loading_con span").height())/2;
    $("#global_loading_con").css("padding-top",top);
  };
  $("body").append('<div id="global_loading" class="loading_3"><div class="loading_bg" id="global_loading_bg"></div><div class="loading_conout" id="global_loading_con"><span class="loading_con">'+str+'</span></div></div>');
  global_loading_set();
  $(window).resize(function(){
    global_loading_set();
  });
};

//ajax加载完成
jQuery.dream.closetip=function(){
  $("#global_loading").remove();
};

function optionStockFun(value,row,index) {
    var code = value;
    if (!!value && value.substring(0,1) == '6') {
        code='sh'+code;
    } else {
        code='sz'+code;
    }
    return '<a href="http://quote.eastmoney.com/'+code+'.html" target="_blank">' + value + '</a>';
}

function optionDetailFun(value,row,index) {
    return '<a href='+value+' target="_blank">详细</a>';
}

function optionNumFun(value,row,index) {
  //if (value == '0') return '0.00';
  if (!!value && !isNaN(value)) {
    return Highcharts.numberFormat(value, 2);
  } else {
    return value;
  }
}

function optionRoundFun(value,row,index) {
    if (!!value) {
        if ($.isNumeric(value) && value.length > 8) {
            value = Math.round(value/1000000)/100 + '亿';
        } else if ($.isNumeric(value) && value.length > 4) {
            value = Math.round(value/10000) + '万';
        }
        return '<span class="blue">' + value + '</span>';
    } else {
        return '';
    }
}

function optionColorFun(value,row,index) {
    //value = optionNumFun(value,row,index);
    if (!value) return '';
    if (row["changeWidth"] > 0 || row["upWidth"] > 0) {
        return '<span class="red">' + value + '</span>';
    }
    else if (row["changeWidth"] < 0 || row["upWidth"] < 0) {
        return '<span class="green">' + value + '</span>';
    }
    else {
        return '<span>' + value + '</span>';
    }
}

function optionSelfFun(value,row,index) {
    value = optionNumFun(value,row,index);
    if (value > 0) {
        return '<span class="red">' + value + '</span>';
    }
    else if (value < 0) {
        return '<span class="green">' + value + '</span>';
    }
    else {
        return '<span>' + value + '</span>';
    }
}

function optionDiffFun(value,row,index) {
  if (!value) return '0.00';
  if (value > row.yesterdayPrice) {
    return '<span class="red">' + value + '</span>';
  }
  else if (value < row.yesterdayPrice) {
    return '<span class="green">' + value + '</span>';
  }
  else {
    return '<span>' + value + '</span>';
  }
}

