	// 资讯类信息渲染
	function doDraw(id , url) {
		$.getJSON(url,function(result){
			var html = "";
		    $.each(result.rows, function(i, field){
		    	if (i == 0) {
		    		html = '<li class="first_news"><a href="detailSample.html?id='+ field.id +'" target="_blank">'+ field.contentTile+'</a></li>';
		    	} else {
		    	html = html + '<li><a href="detailSample.html?id='+ field.id +'" target="_blank">'+ field.contentTile+'</a></li>';}
		    });
		    if (html != "") {
		       $(id).append(html);
		    }
		});
	};
	
	// 公共观察资讯渲染
	function doPublicVisionDraw(id , url) {
		
		$.getJSON(url,function(result){
			var html = "";
		    $.each(result.rows, function(i, field){
		    	html = html + '<li><h2 class="gc_tit"><a href="#">'+ field.text+'</a></h2>'
		    	+'<p class="item_detail"><span class="fn-right">'+field.links+'</span>'+ field.time+'</p></li>';
		    });
		    if (html != "") {
		       $(id).append(html);
		    }
		});
	};
	
	// 首页信息渲染
	function doHomePageDraw(id , url) {
		$.getJSON(url,function(result){
			var html = "";
		    $.each(result.rows, function(i, field){
		    	if (i == 0) {
		    		html = html + '<li class="first_one">'+
		                '<a href="'+field.url+'" target="_blank" class="tit_news" title="'+ field.title.substr(0,5)+'">'+ field.title+'</a>'+
		                '<p class="news_from">'+field.postTime+'&nbsp;&nbsp;来源：'+ field.comeFrom+'</p>'+
		                '<a  href="'+field.url+'" target="_blank" class="news_detail">'+ field.mainText.substr(0,30)+'</a>'+
	                '</li>';
		    	} else {
		    		html = html + '<li><a href="'+field.url+'" target="_blank" class="tit_news">'+ field.title+'</a></li>';
		    	}
		    });
		    if (html != "") {
		       $(id).append(html);
		    }
		});
	};
	
	// 首页图片信息渲染
	function doHomePagePictureDraw(id , url) {
		
		// TODO
		$.getJSON(url,function(result){
			var html = "";
		    $.each(result.rows, function(i, field){
		    });
		    if (html != "") {
		       $(id).append(html);
		    }
		});
	};
	
	// 空气指数信息渲染
	function doAirInfoDraw(id , url) {
		
		$.getJSON(url,function(result){
			var html = "";
			var html1 = "";
		    $.each(result.rows, function(i, field){
		    	if (i == 0) {
		    		html = '<em>'+ field.aqi +'</em>'+field.content;
		    		html1 = '<div class="weather_a"><h1>健康影响：'+ field.influence_health + 
                    '</h1><p>'+field.influence+'</p></div>';
		    	}
		    });
		    if (html != "") {
		       $(id).append(html);
		       $("#con_" + id.substr(id.length-1) + " div.weather_advice").append(html1);
		    }
		});
	};
	
	// 环保知识信息渲染
	function doEnviromentInfoDraw(id , url) {
		$.getJSON(url,function(result){
			var html = "";
		    $.each(result.rows, function(i, field){
		    	html = html + '<li>'+
            	'<a href="#">'+
                	'<img src="'+field.picture+'" width="80" height="60" class="img8060" />'+
                    '<div class="know_detail">'+
                    // <span class="category">所属分类：<em>'+'大自然'+'</em></span>
                    	'<h2>'+field.contentTile+'</h2>'+
                        '<p class="know_det">'+field.contentAbstract+'</p>'+
                    '</div>'+
                '</a>'+
                '</li>';
		    });
		    if (html != "") {
		       $(id).append(html);
		    }
		});
	};
	
	// 公益活动信息渲染
	function doSociallyInfoDraw(id , url) {
		$.getJSON(url,function(result){
			var html = "";
		    $.each(result.rows, function(i, field){
		    	html = html +'<li>'+
            	'<h2 class="litit_r "><a href="#" class="greenLink">'+field.contentAbstract+'</a></h2>'+
                '<div class="det_c ">'+
                	'<div class="jr_gy"><p>25人参加</p><a href="#" class="btn_join">+加入</a></div>'+
                	'<p>招募：'+field.startDate+'-'+field.endDate+'</p>'+
                    '<p>举办单位：'+field.organizer+'</p>'+
                    '<p>活动地点：'+field.address+'</p>'+
                '</div>'+
                '</li>';
		    });
		    if (html != "") {
		       $(id).append(html);
		    }
		});
	};
	