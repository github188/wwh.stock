/*
 尚青工具包jquery扩展
 write by xwh
 */
jQuery.extend({
  sunking:{}
});

//===========================尚青组件=========================
/*ajax加载中
 *$.sunking.tip(str)在当前窗口显示
 *window.parent.$.sunking.tip(str)在父窗口显示
 */
jQuery.sunking.tip=function(str){
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
jQuery.sunking.closetip=function(str){
  $("#global_loading").remove();
};