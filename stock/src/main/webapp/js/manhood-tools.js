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
jQuery.dream.closetip=function(str){
  $("#global_loading").remove();
};

function optionNumFun(value,row,index) {
  if (value == '0') return '0.00';
  if (!!value) {
    return Highcharts.numberFormat(value, 2);
  } else {
    return '';
  }
}

function optionColorFun(value,row,index) {
  if (value == '0.00%') return value;
  if (!value) return '';
  if (!!value && value.replace("%","") > 0) {
    return '<span class="red">' + value + '</span>';
  }
  else if (!!value && value.replace("%","") < 0) {
    return '<span class="green">' + value + '</span>';
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

