(function(){	
	/* 动态变量 */
	var fixedurl = "http://g1.dfcfw.com/g2/201607/20160719130149.png";	
	var clickUrl = ["http://acttg.eastmoney.com/pub/web_kh_dcsy_jycdl_01_01_01_1","http://stattg.eastmoney.com/10071","http://acttg.eastmoney.com/pub/web_pc_dcsy_zccpl_01_01_01_1","http://acttg.eastmoney.com/pub/web_pc_dcsy_zccpl_01_02_01_1","http://choice.eastmoney.com/Product/index.html?adid=2035"];
	var fixedBodyWidth = window.emLeftBodyWidth || 1000;
	var fixedLinkPt = 0;
	var fixedLinkMt = [0,0,0,0];
	var fixedLinkHeight = [118,132,86,85,83];;
	var fixedwidth = 135;
	var fixedheight = 85;
	var fixedSoftTop = 43;

    fixedurl = "http://g1.dfcfw.com/g2/201701/20170103110239.gif";clickUrl = ["http://acttg.eastmoney.com/pub/web_cgds03rk_1_36","http://acttg.eastmoney.com/pub/web_kh_dcsy_zccpl_01_01_01_0","http://acttg.eastmoney.com/pub/web_app_dcsy_zccpl_01_01_01_1","http://acttg.eastmoney.com/pub/web_pc_dcsy_zccpl_01_01_01_1","http://acttg.eastmoney.com/pub/web_pc_dcsy_zccpl_01_02_01_1","http://js5.eastmoney.com/tg.aspx?ID=2035"];fixedLinkPt = 0;fixedLinkMt =[6,0,0,0,0];fixedLinkHeight =[163,121,131,84,86,83];fixedwidth = 135;fixedheight = 674;fixedSoftTop = 43;

	var fixedAlign = "left";
	/* end动态变量 */

	var fixedoverurl = "http://";
	var target = "_blank";
	var fixedcreativetype = "picture";
	var fixedidsuffix = Math.round(Math.random()*10000);
	var fixedid = "flow-ad-"+fixedidsuffix;
	var fixed_html;
	var fixed_over_html;
	var flowfixed_ad;

	var browser = {
		isie6: function (){
			return document.all && !window.XMLHttpRequest;
		}
	};

	var isie6 = browser.isie6();
	// News left
	// Get browser width
	var getBrowserWH = function(){
	  var de = document.documentElement;
	  var _width = ( de && de.clientWidth ) || document.body.clientWidth || window.innerWidth;
	  var _height = ( de && de.clientHeight ) || document.body.clientHeight || window.innerHeight;
	  return {w:_width,h:_height};
	};

	var render_fixed_flash_html = function(width, height, url){
		var html = '';
		html += '<div style="height:'+height+'px;width:'+width+'px;">';
		html += '<object id="test" height="'+height+'px" width="'+width+'px" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">';
		html += '<param value="'+url+'" name="movie" />';
		html += '<param value="high" name="quality" />';
		html += '<param value="null" name="bgcolor" />';
		html += '<param value="opaque" name="wmode" />';
		html += '<embed height="'+height+'px" width="'+width+'px" wmode="opaque" bgcolor="null" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+url+'" name="" />';
		html += '</object>';
		html += '</div>'; 
		return html;
	};
	var render_fixed_close_html = function(width, height){
		var html;
		html = '<div style="position:absolute;right:0;top:0;overflow:hidden;text-indent:99px;z-index:999;font-weight:bold;cursor:pointer;line-height:20px;height:18px;width:18px;font-size:9pt;" onClick="closefixed_ad(this);">&#20851;&#38381;</div>';
		return html;
	};
	var render_fixed_html = function(){
		fixed_html = '';
		
		//fixed_html += '<div id="' + fixedid + '" style="display:none; width:' + fixedwidth + 'px;height:' + fixedheight + 'px; position:fixed !important; position:absolute; right:' + ((document.documentElement.clientWidth - fixedBodyWidth) / 2 - fixedwidth - 5) + 'px; top:' + fixedSoftTop + 'px; _top:expression(eval(document.documentElement.scrollTop + fixedSoftTop));">';
		fixed_html += '<div>';
		fixed_html += '<div>';
		switch(fixedcreativetype){
			case "picture":
				if(Object.prototype.toString.apply(clickUrl) === '[object Array]'){
					fixed_html += '<div style="position:relative;width:'+fixedwidth+'px;height:'+fixedheight+'px;background:url('+fixedurl+');">';
					fixed_html += '<div style="position:absolute;top:0;left:0;width:100%;height:'+(fixedheight-fixedLinkPt)+'px;padding-top:'+fixedLinkPt+'px;overflow:hidden;zoom:1;z-index:2;">';
					var urlCount = clickUrl.length;
					var idx = 0;
					for(var i in clickUrl){
						idx++;
						fixed_html += '<a href="'+clickUrl[i]+'" target="'+target+'" style="display:block;width:100%;height:'+fixedLinkHeight[i]+'px;cursor:pointer;';
						fixed_html += 'margin-bottom:'+fixedLinkMt[i]+'px;overflow:hidden;"></a>';
					}
					fixed_html += '</div>';
					fixed_html += '</div>';
				}else{
					fixed_html += '<a href="'+clickUrl+'" target="'+target+'">';
					fixed_html += '<img src="'+fixedurl+'" style="width:'+fixedwidth+'px;height:'+fixedheight+'px;border:none" />';
					fixed_html += '</a>';
				}
				break;
			case "flash":
				fixed_html += render_fixed_flash_html(fixedwidth, fixedheight, fixedurl);
				fixed_html += '<a href="'+clickUrl+'" target="'+target+'" style="margin-top:-'+fixedheight+'px;width:'+fixedwidth+'px;height:'+fixedheight+'px;display:block;"><div style="position:absolute;left:0px;top:0px;cursor:pointer;width:'+fixedwidth+'px;height:'+fixedheight+'px;z-index:999;background:white;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;"></div></a>';
				break;
		}
		fixed_html += "</div>";
		fixed_html += render_fixed_close_html(fixedwidth, fixedheight);
		fixed_html += "</div>";
		//fixed_html += "</div>";
		
		//document.write(fixed_html);
		
		var tempdiv = document.createElement("div");
		tempdiv.id = fixedid;
		tempdiv.style.cssText = 'display:none; width:' + fixedwidth + 'px;height:' + fixedheight + 'px; position:fixed !important; position:absolute; ' + fixedAlign + ':' + ((document.documentElement.clientWidth - fixedBodyWidth) / 2 - fixedwidth - 5) + 'px; top:' + fixedSoftTop + 'px; _top:expression(eval(document.documentElement.scrollTop+' + fixedSoftTop + '));'
			tempdiv.innerHTML = fixed_html;
			document.body.appendChild(tempdiv);
		
		
		flowfixed_ad = document.getElementById(fixedid);
	};
	render_fixed_html();

	var closefixed_ad = function(obj){
		obj.parentNode.removeChild(obj);
		flowfixed_ad.parentNode.removeChild(flowfixed_ad);
	};
	window.closefixed_ad = closefixed_ad;

	var reset_fixedbound = function () {
	    var _wh = getBrowserWH();
	    var _fr = ((document.documentElement.clientWidth - fixedBodyWidth) / 2 - fixedwidth - 5);
	    if (parseFloat(flowfixed_ad.style.left) != _fr)
	        flowfixed_ad.style.left = _fr + 'px';
	    if (_wh.w <= fixedBodyWidth + fixedwidth * 2 + 20) {
	        flowfixed_ad.style.display = "none";
	    } else {
	        flowfixed_ad.style.display = "block";
	    }
	};

	setInterval(reset_fixedbound, 200);
})();