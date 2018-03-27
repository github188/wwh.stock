//图片切换
function tab1(tab_title,tab_text,column,auto_css,hover_css,layout_type,default_type){
	
	// 空气质量指数
	var tab_url = ["json/air.json",
	               "json/air.json"];
		
/* 普通TAB： tab_title,tab_text,column(TAB个数),auto_css(普通项样式),hover_css(当前项样式),layout_type(触发手段),default_type(默认热点) */
	if(!hover_css){var hover_css="";}
	if(!auto_css){var auto_css="";}
	if(!layout_type){var layout_type="mouseover";}
	if(!default_type){var default_type=0;}
	type_hover(default_type, tab_url[0]);
	for(var i = 0 ;i<column;i++){
		(function(i){
			$("#"+tab_title+i).bind(layout_type, function(){type_hover(i, tab_url[i]);});
		})(i);
	}
	function type_hover(i, url){
		for(var id = 0;id<column;id++){
			$("#"+tab_text+id).hide();
			$("#"+tab_title+id).addClass(auto_css);
			$("#"+tab_title+id).removeClass(hover_css);
		};
		$("#"+tab_text+i).show();
		$("#"+tab_title+i).removeClass(auto_css);
		$("#"+tab_title+i).addClass(hover_css);
		
		$("#ari_t_info_" + i).empty();
		$("#con_" + i + " div.weather_advice").empty();
		doAirInfoDraw("#ari_t_info_" + i, url);
		
	};
};

function tab(tab_title,tab_text,column,auto_css,hover_css,layout_type,default_type){
	var tab_url = ["json/homePage.json",
	               "json/homePage_air.json",
	               "json/homePage_water.json",
	               "json/homePage_noise.json",
	               "json/homePage_other.json"];
	
	// 热点信息
/*	var tab_url = ["http://10.80.11.33:8080/dream/content-review!queryPaged.do?",
	"http://10.80.11.33:8080/dream/content-review!queryPaged.do?label=air",
	"http://10.80.11.33:8080/dream/content-review!queryPaged.do?label=water",
	"http://10.80.11.33:8080/dream/content-review!queryPaged.do?label=noise",
	"http://10.80.11.33:8080/dream/content-review!queryPaged.do?label=other"];*/
	
/* 普通TAB： tab_title,tab_text,column(TAB个数),auto_css(普通项样式),hover_css(当前项样式),layout_type(触发手段),default_type(默认热点) */
	if(!hover_css){var hover_css="";}
	if(!auto_css){var auto_css="";}
	if(!layout_type){var layout_type="mouseover";}
	if(!default_type){var default_type=0;}
	type_hover(default_type, tab_url[0]);
	for(var i = 0 ;i<column;i++){
		(function(i){
			$("#"+tab_title+i).bind(layout_type, function(){type_hover(i, tab_url[i]);});
		})(i);
	}
	function type_hover(i, url){
		for(var id = 0;id<column;id++){
			$("#"+tab_text+id).hide();
			$("#"+tab_title+id).addClass(auto_css);
			$("#"+tab_title+id).removeClass(hover_css);
		};
		$("#"+tab_text+i).show();
		$("#"+tab_title+i).removeClass(auto_css);
		$("#"+tab_title+i).addClass(hover_css);
		$("#homeInfo_" + (i+1)).empty();
		homePageDraw("#homeInfo_" + (i+1), url);
	};
};
//图片轮播
function Slide(items,items_tool,type,Trigger,up_btn,down_btn){
		var items_arr = items.find("li"), //获得包含图片的对象
			items_ul = items.find("ul");//获得包含图片对象的ul层
		var item_height = items_arr.first().height(), 
			item_width = items_arr.first().width();//滚动块的宽高
		var select_point_innerHTML = "", 
			run_interval = null, 
			now = 0, 
			img_count = items_arr.length;//滚动里需要的几个参数
		var slide_func = {
			auto_run : function(){
				var _this = this;
				run_interval = setInterval(function(){
					now++;
					if(now > (img_count-1)){ now = 0; }
					_this.select_point_show(now);
					_this.slide_run(now);
				},3000);
			},
			slide_run : function(n){
				switch (type){
					case "opacity":
						items_arr.fadeOut(1000);items_arr.eq(n).fadeIn(1000);break;
					case "l_r":
						items_ul.stop().animate({"left" : n*item_width*-1+"px"},"slow");break;
					case "t_b":
						items_ul.stop().animate({"top" : n*item_height*-1+"px"},"slow");break;
				};
			},
			select_point_show : function(n){
				items_tool_a.removeClass("hover");
				items_tool_a.eq(n).addClass("hover");
			}
		};
		
		if (items_tool.find('img').size() == 0) {
			items_arr.each(function(i){/* 生成触发的1，2，3，4那种热点。并排列好图片的位置。 */
				if(type == "l_r"){$(this).css("left",i*item_width);items_arr.show();}//横向排列大图片
				if(type == "t_b"){$(this).css("top",i*item_height);items_arr.show();}//纵向排列大图片
				select_point_innerHTML += '<a href="javascript:void(0);">'+(i+1)+'</a>';
			});
			items_tool.html(select_point_innerHTML);
		}
		
		var items_tool_a = items_tool.find("a");//获得很有热点LINK的对象集合
		items_tool_a.first().addClass("hover");
		slide_func.auto_run();//启动自动轮播
		items_tool_a.each(function(i){//触发图片热点切换图片
			$(this).bind(Trigger, function(){
				clearInterval(run_interval);
				now = i;
				slide_func.select_point_show(now);
				slide_func.slide_run(now);
				slide_func.auto_run();
			});
		});
		
		if(up_btn){
			up_btn.click(function(){
				clearInterval(run_interval);
				now--;
				if(now<0){now=(img_count-1);}
				slide_func.select_point_show(now);
				slide_func.slide_run(now);
				slide_func.auto_run();
			});
		}
		if(down_btn){
			down_btn.click(function(){
				clearInterval(run_interval);
				now++;
				if(now > (img_count-1)){ now = 0; }
				slide_func.select_point_show(now);
				slide_func.slide_run(now);
				slide_func.auto_run();
			});
		}
	};

