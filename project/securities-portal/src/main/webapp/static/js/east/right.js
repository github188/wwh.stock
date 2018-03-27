(function(){	
	// News right
    var fixedBodyWidth = window.emRightBodyWidth || 1000;
    var fixedwidth = window.emRightAdFixedWidth || 138;
    var adwidth = window.emRightAdWidth || 135;
    var adheight = window.emRightAdHeight || 1741;
    //var adurl = window.emRightAdUrl || 'http://fund.eastmoney.com/trade/Hot_Em_DC.html?spm=100015.ra';
    var adtop = window.emRightAdTop || '43px';
    var adzindex = window.rightAdZindex || 99;
    var addefault = window.emRightadDataType || "default";
    
    // 1显示返回顶部；0不显示返回顶部

    var switchbacktop = (window.switchBackTop == undefined && true) || (window.switchBackTop == 1 && true) || false;
    var backtopheight = window.backTopHeight || 1000;

	var browser = {
		isie6: function (){
			return document.all && !window.XMLHttpRequest;
		}
	};

	var isie6 = browser.isie6();

	function addEvent( obj, type, fn ) {
	  if ( obj.attachEvent ) {
		obj['e'+type+fn] = fn;
		obj[type+fn] = function(){obj['e'+type+fn]( window.event );}
		obj.attachEvent( 'on'+type, obj[type+fn] );
	  } else
		obj.addEventListener( type, fn, false );
	}

	var adurl = function(type) {
		var e = "";
		switch (type) {
			case "quote":
				e = "http://fund.eastmoney.com/trade/Hot_Em.html?spm=100002.rw";
				break;
			case "data":
				e = " http://fund.eastmoney.com/trade/Hot_Em.html?spm=100004.rw";
				break;
			case "guba":
				e = "http://fund.eastmoney.com/trade/Hot_Em.html?spm=100005001.rw";
				break;
			default:
				e = " http://fund.eastmoney.com/trade/Hot_Em.html?spm=001004.rw"
		}
		var n = new Date;
		return e + "&rmd=" + n.getFullYear().toString().substring(2) + (n.getMonth() + 1) + n.getDate() + n.getHours() + Math.round(n.getMinutes() / 5)
	}

    var togo = function () {
        if (!document.getElementById("rightAD")) {
            var html = '<iframe width="' + adwidth + '" height="' + adheight + '" frameborder="0" scrolling="no" marginwidth="0" marginheight="0" src="' + adurl(addefault) + '"></iframe>';
            var divObj = document.createElement("div");
            divObj.className = "rbadbox";
            divObj.id = "rightAD";
            divObj.innerHTML = html;
			divObj.style.height = adheight;
			divObj.style.width = adwidth;
			divObj.style.position = 'absolute';
			divObj.style.top = adtop;
			divObj.style.zIndex = 99;
            document.body.appendChild(divObj);
            //setInterval(reset_fixedbound, 200);
			addEvent(window,'resize',function (){
				reset_fixedbound();
			});
			reset_fixedbound();
        }
    }

    // 获取浏览器的宽度和高度
    var getBrowserWH = function () {
        var de = document.documentElement;
        var _width = (de && de.clientWidth) || document.body.clientWidth || window.innerWidth;
        var _height = (de && de.clientHeight) || document.body.clientHeight || window.innerHeight;
        return { w: _width, h: _height };
    };

    var reset_fixedbound = function () {
        var flowfixed_ad = document.getElementById("rightAD");
        var _wh = getBrowserWH();
        var _fr = ((document.documentElement.clientWidth - fixedBodyWidth) / 2 - fixedwidth - 5);
        if (parseFloat(flowfixed_ad.style.right) != _fr)
            flowfixed_ad.style.right = _fr + 'px';
        if (_wh.w <= fixedBodyWidth + adwidth*2 + 20) {
            flowfixed_ad.style.display = "none";
        } else {
            flowfixed_ad.style.display = "block";
        }
    };
	togo();

	// 返回顶部
	var backtop = {
	    init: function () {
	        this.create();
	        this.bind();
	    },
	    create : function () {
	    	var fixWidth = fixedBodyWidth / 2 + 8;
	    	var backtopcss = '#embacktop {width: 18px;position: fixed;bottom: 250px;left: 50%;margin-left:' + fixWidth +'px;text-align: center;_position: absolute;z-index: 9999;display: none;_position: absolute;}#embacktop #backtophq, #embacktop #backtopyj, #embacktop #gotop {display: block;width: 50px;height: 51px;background: url(http://g1.dfcfw.com/g2/201607/20160728143011.png) no-repeat;font-size: 12px;}#embacktop #backtophq {background-position: 0 0;color: #fff;text-decoration: none;padding-top: 35px;height: 16px;} #embacktop #backtophq:hover {background-position: -50px 0;}#embacktop #backtophq.on {background-position: -102px -2px;background-color: #3A5E95;}  #embacktop #backtopyj {background-position: 0 -115px;color: #fff;text-decoration: none;height: 51px;margin-top: 4px;}#embacktop #backtopyj:hover {background-position: -60px -115px;}#embacktop #gotop {background-position: 0 -50px;margin-top: 4px;display: block;}#embacktop #gotop:hover {background-position: -50px -50px;}#embacktop #backtopsearch {position: absolute;left: -237px;top: 0;background-color: #3A5E95;height: 36px;width: 238px;display: none;padding-top: 15px;}#embacktop #backtopsearch form {margin: 0;padding: 0;display: inline;position: relative;}#embacktop #backtopsearch input {width: 123px;padding: 4px;font-size: 12px;font-family: simsun;border: 0;height: 16px;vertical-align: middle;}#embacktop #backtopsearch #backtopsearchsbm {border-style: none;border-color: inherit;border-width: 0;width: 60px;color: #315895;height: 24px;background: url(http://g1.dfcfw.com/g2/201607/20160728143011.png) 5px -174px no-repeat #BBD4E8;text-align: right;padding-right: 7px;vertical-align: middle;cursor: pointer;}';
	    	this.backtop = '<a href="javascript:;" target="_self" id="backtophq">行情</a><a href="http://corp.eastmoney.com/Lianxi_liuyan.asp" target="_blank" id="backtopyj" title="意见反馈"></a><a href="javascript:;" target="_self" id="gotop" title="回到顶部" onclick="window.scroll(0,0);return false;"></a><div id="backtopsearch"><input type="text" value="" id="backtopsearchinput" /><button id="backtopsearchsbm">查询</button></div>';
	    	var _style = document.createElement("style");
	    	_style.type = "text/css";
	    	if(_style.styleSheet){         //ie下  
				_style.styleSheet.cssText = backtopcss;  
			} else {  
	    		_style.innerHTML = backtopcss;
    		}
	    	var _backtop = document.createElement("div");
	    	_backtop.id = "embacktop";
	    	_backtop.innerHTML = this.backtop;
	    	document.body.appendChild(_style);
	    	document.body.appendChild(_backtop);
	    },
	    open: false,
	    bind: function () {
	    	var _this = this;
	    	// 行情查询
	        this.btnsearch = document.getElementById("backtopsearchsbm");	
	        this.btnsearch.onclick = function(){	 
	        	var backtopsearchinput = document.getElementById("backtopsearchinput");   		
	        	var _value = backtopsearchinput.value;
	            if (_value != "" && _value != "代码/名称/拼音") {
	            	_value = encodeURI(_value);
	                window.open('http://quote.eastmoney.com/search.html?stockcode=' + _value);
	            }
	            else {
	                backtopsearchinput.focus();
	            }
	        };
	
			// 隐藏、显示行情
	        this.backtophq = document.getElementById('backtophq');
	        this.backtopsearch = document.getElementById('backtopsearch');
	        this.backtophq.onclick = function() {
	            if (_this.open) {
	                this.className = "";
	                _this.backtopsearch.style.display = "none";
	                _this.open = false;
	            }
	            else {
	                this.className = "on";
	                _this.backtopsearch.style.display = "block";
	                _this.open = true;
	            }
	
	        };
	        
	        if (typeof StockSuggest != "undefined") {
		        var arg = {
		            text: "代码/名称/拼音",
		            autoSubmit: false,
		            width: 235,
		            type: "CNSTOCK",
		            header: ["选项", "代码", "名称", "类型"],
		            body: [-1, 1, 4, -2],
		            callback: function (ag) {
		                window.open('http://quote.eastmoney.com/' + ag.code + '.html');
		                return false;
		            }
		        };
		        s1001 = new StockSuggest("backtopsearchinput", arg);
	        }
	
	        setInterval(function () {
	            if (isie6) {
	                document.getElementById("embacktop").style.bottom = "auto";
	                document.getElementById("embacktop").style.top = document.documentElement.scrollTop + 500;
	            }
	            if ((document.documentElement.scrollTop + document.body.scrollTop) > backtopheight) {
	                document.getElementById("embacktop").style.display = "block";
	                //_this.backtop.fadeIn();
	            }
	            else {
	                document.getElementById("embacktop").style.display = "none";
	                //_this.backtop.fadeOut();
	            }
	
	            return true;
	        }, 500);
	    }
	}

	switchbacktop && backtop.init();
})();