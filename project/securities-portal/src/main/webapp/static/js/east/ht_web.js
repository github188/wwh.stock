(function(){
    function HashTable(){
        var size = 0;
        var entry = new Object();

        this.add = function (key , value){
            //key=key.toLowerCase();value=value.toLowerCase();
            if(!this.containsKey(key)){
                size ++ ;
            }else{
                console.log("existed("+key+":"+value+")");
            }
            entry[key] = value;
        }

        this.getValue = function (key){
            return this.containsKey(key) ? entry[key] : null;
        }

        this.remove = function ( key ){
            if( this.containsKey(key) && ( delete entry[key] ) ){
                size --;
            }
        }

        this.containsKey = function ( key ){
            //key=key.toLowerCase();
            return (key in entry);
        }

        this.containsValue = function ( value ){
            //value=value.toLowerCase();
            for(var prop in entry){
                if(entry[prop] == value){
                    return true;
                }
            }
            return false;
        }

        this.getValues = function (){
            var values = new Array();
            for(var prop in entry){
                values.push(entry[prop]);
            }
            return values;
        }

        this.ByCheck = function (url,c){
            //var jn=url.indexOf("#");
            //if(jn>0){url=url.substring(0,jn).toLowerCase()+url.substring(jn);}else{url=url.toLowerCase();}
            var isM=this.isMobile();
            var res="";
            if(url!=""){
                if(isM){
                    var r=this.getValue(url);res=r==null?"":r;
                    switch(t){
                        case "01":res="http://wap.eastmoney.com/3g/stock/GlobalFuturesIndex,{0}0.shtml";break;
                        case "02":res="http://wap.eastmoney.com/3g/stock/GlobalFuturesIndex,{0}.shtml";break;
                        case "03":res="http://wap.eastmoney.com/3g/stock/GlobalFuturesIndex,{0}.shtml";break;
                        case "04":res="http://wap.eastmoney.com/3g/stock/FuturesIndex,{0}.shtml";break;
                    }
                    switch(url){
                        case "http://quote.eastmoney.com/{0}.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/qiquan/{0}_SO.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/hk/zs{0}.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/hk/{0}.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/gb/zs{0}.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/forex/{0}.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/gjqh/{0}.html":res=res.replace("{0}",c);break;
                        case "http://quote.eastmoney.com/qihuo/{0}.html":res=res.replace("{0}",c);break;
                        case "http://data.eastmoney.com/zjlx/{0}.html":res=res.replace("{0}",c+this.getMarket_zjlx(c));break;
                        case "http://data.eastmoney.com/bkzj/BK{0}.html":res=res.replace("{0}",c);break;
                    }
                    if(res!=""){location.href=res;}
                }
            }
        }

        this.getKeys = function (){
            var keys = new Array();
            for(var prop in entry){
                keys.push(prop);
            }
            return keys;
        }
        this.getSize = function (){
            return size;
        }
        this.clear = function (){
            size = 0;
            entry = new Object();
        }
        this.isMobile=function(){
            var ua = navigator.userAgent;
            var res=false;
            var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
                isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
                isAndroid = ua.match(/(Android)\s+([\d.]+)/),
                isMobile = isIphone || isAndroid;
            if(isMobile) {
                res=true;
            }else{
                res=false;
            }
            return res;
        }
        this.getMarket=function(StockCode){
            var i = StockCode.substring(0, 1);
            var j = StockCode.substring(0, 3);
            if (i == "5" || i == "6" || i == "9") {
                return "01";
            } else {
                if (j == "009" || j == "126" || j == "110" || j == "201" || j == "202" || j == "203" || j == "204") {
                    return "01";
                } else {
                    return "02";
                }
            }
        }
        this.getMarket_zjlx=function(StockCode){
            var i = StockCode.substring(0, 1);
            var j = StockCode.substring(0, 3);
            if (i == "5" || i == "6" || i == "9") {
                return "1";
            } else {
                if (j == "009" || j == "126" || j == "110" || j == "201" || j == "202" || j == "203" || j == "204") {
                    return "1";
                } else {
                    return "2";
                }
            }
        }
    }

    var map = new HashTable();
    var _location = document.scripts[document.scripts.length - 1].src;var _url = location.href;var _index = (_location.indexOf("?") == -1) ? _location.length: _location.indexOf("?") + 1;var _param = _location.substring(_index, _location.length);
    var t = (_param.match(/t=(.+?)($|\||&)/i) != null) ? _param.match(/t=(.+?)($|\||&)/i)[1].toLowerCase() : "";//类型（01美黄金 02美元指数 03美元欧元 04期货品种）
    var c = (_param.match(/c=(.+?)($|\||&)/i) != null) ? _param.match(/c=(.+?)($|\||&)/i)[1] : "";//可变参数，非必填（默认为空）,如sh600000或600000
    var u = (_param.match(/u=(.+?)($|\||&)/i) != null) ? _param.match(/u=(.+?)($|\||&)/i)[1] : _url;//当前页面(模板)地址，非必填（默认为当前url），如果可变参数不为空，该地址变化区域请用{0}表示，目前仅支持{0}一种。
    map.add("http://quote.eastmoney.com/center/forexlist.html#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml");
    map.add("http://quote.eastmoney.com/center/forexlist.html?bank=abc#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=abc");
    map.add("http://quote.eastmoney.com/center/forexlist.html?bank=icbc#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=icbc");
    map.add("http://quote.eastmoney.com/center/forexlist.html?bank=ccb#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=ccb");
    map.add("http://quote.eastmoney.com/center/forexlist.html?bank=comm#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=comm");
    map.add("http://quote.eastmoney.com/center/forexlist.html?bank=cmb#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=cmb");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex&bank=boc#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex&bank=abc#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=abc");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex&bank=icbc#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=icbc");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex&bank=ccb#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=ccb");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex&bank=comm#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=comm");
    map.add("http://quote.eastmoney.com/center/forexlist.html?from=baiduforex&bank=cmb#3_6","http://wap.eastmoney.com/3g/center/whpj.shtml?bank=cmb");

    map.add("http://quote.eastmoney.com/{0}.html","http://m.quote.eastmoney.com/stock,{0}.shtml");
    map.add("http://data.eastmoney.com/zjlx/{0}.html","http://wap.eastmoney.com/3g/zjlx/{0}.shtml");//*
    map.add("http://data.eastmoney.com/bkzj/BK{0}.html","http://wap.eastmoney.com/3g/zjlx/BK{0}1.shtml");
    map.add("http://i.eastmoney.com/stock.html","http://wap.eastmoney.com/3g/stock/favor.shtml");
    map.add("http://quote.eastmoney.com/center/index.html#zyzs_0_1","http://wap.eastmoney.com/3g/center/List.shtml#0/0/0//");
    map.add("http://quote.eastmoney.com/center/list.html#15_0_1","http://wap.eastmoney.com/3g/center/List.shtml#0/0/1//");
    map.add("http://quote.eastmoney.com/center/list.html#25_0_1","http://wap.eastmoney.com/3g/center/List.shtml#0/0/2//");
    map.add("http://quote.eastmoney.com/center/list.html#33","http://wap.eastmoney.com/3g/center/List.shtml#0/1/0//");
    map.add("http://quote.eastmoney.com/center/list.html#10","http://wap.eastmoney.com/3g/center/List.shtml#0/1/1//");
    map.add("http://quote.eastmoney.com/center/list.html#20","http://wap.eastmoney.com/3g/center/List.shtml#0/1/2//");
    map.add("http://quote.eastmoney.com/center/list.html#11","http://wap.eastmoney.com/3g/center/List.shtml#0/2");
    map.add("http://quote.eastmoney.com/center/list.html#21","http://wap.eastmoney.com/3g/center/List.shtml#0/2/1//");
    map.add("http://quote.eastmoney.com/center/BKList.html#trade_0_0?sortRule=0","http://wap.eastmoney.com/3g/center/List.shtml#0/3");
    map.add("http://quote.eastmoney.com/center/BKList.html#area_0_0?sortRule=0","http://wap.eastmoney.com/3g/center/List.shtml#0/3/1//");
    map.add("http://quote.eastmoney.com/center/BKList.html#notion_0_0?sortRule=0","http://wap.eastmoney.com/3g/center/List.shtml#0/3/2//");
    map.add("http://quote.eastmoney.com/center/list.html#27","http://wap.eastmoney.com/3g/center/List.shtml#0/4");
    map.add("http://quote.eastmoney.com/center/list.html#26","http://wap.eastmoney.com/3g/center/List.shtml#0/5");
    map.add("http://quote.eastmoney.com/center/list.html#285001_0","http://wap.eastmoney.com/3g/center/List.shtml#0/6");
    map.add("http://quote.eastmoney.com/center/list.html#2850022_0","http://wap.eastmoney.com/3g/center/List.shtml#0/7");
    map.add("http://quote.eastmoney.com/center/list.html#40_0_3","http://wap.eastmoney.com/3g/center/List.shtml#0/8/0//");
    map.add("http://quote.eastmoney.com/center/list.html#42_0_3","http://wap.eastmoney.com/3g/center/List.shtml#0/8/1//");
    map.add("http://quote.eastmoney.com/center/list.html#43_0_3","http://wap.eastmoney.com/3g/center/List.shtml#0/8/2//");
    map.add("http://data.eastmoney.com/zjlx/","http://wap.eastmoney.com/3g/zjlx/");
    map.add("http://quote.eastmoney.com/qiquan/{0}_SO.html","http://m.quote.eastmoney.com/stockoption,{0}.shtml");
    map.add("http://quote.eastmoney.com/hk/zs{0}.html","http://wap.eastmoney.com/3g/stock/HKIndex,{0}.shtml");
    map.add("http://quote.eastmoney.com/hk/{0}.html","http://wap.eastmoney.com/3g/stock/HKStock,{0}.shtml");
    map.add("http://quote.eastmoney.com/gb/zs{0}.html","http://wap.eastmoney.com/3g/stock/GlobalIndex,{0}.shtml");
    map.add("http://quote.eastmoney.com/center/qiquan.html#1,510050,0_13_0","http://wap.eastmoney.com/3g/center/StockOptionList,510050,1.shtml");
    map.add("http://quote.eastmoney.com/center/list.html#28003707_12_2","http://wap.eastmoney.com/3g/center/ListAH.shtml#1/0");
    map.add("http://quote.eastmoney.com/center/list.html#mk0144_12","http://wap.eastmoney.com/3g/center/ListAH.shtml#1/1");
    map.add("http://quote.eastmoney.com/center/list.html#ah_12","http://wap.eastmoney.com/3g/center/ListA_H.shtml");
    map.add("http://quote.eastmoney.com/center/hkstock.html#_1","http://wap.eastmoney.com/3g/center/List.shtml#2/0");
    map.add("http://quote.eastmoney.com/center/list.html#50_1","http://wap.eastmoney.com/3g/center/List.shtml#2/1");
    map.add("http://quote.eastmoney.com/center/list.html#28HSCI_1","http://wap.eastmoney.com/3g/center/List.shtml#2/2");
    map.add("http://quote.eastmoney.com/center/list.html#28HSCCI_1","http://wap.eastmoney.com/3g/center/List.shtml#2/3");
    map.add("http://quote.eastmoney.com/center/list.html#28HSCIINDEX_1","http://wap.eastmoney.com/3g/center/List.shtml#2/4");
    map.add("http://quote.eastmoney.com/center/list.html#28HSCEI_1","http://wap.eastmoney.com/3g/center/List.shtml#2/5");
    map.add("http://quote.eastmoney.com/center/list.html#28HSCEIINDEX_1","http://wap.eastmoney.com/3g/center/List.shtml#2/6");
    map.add("http://quote.eastmoney.com/center/list.html#28GEM_1","http://wap.eastmoney.com/3g/center/List.shtml#2/7");
    map.add("http://quote.eastmoney.com/center/list.html#52_1","http://wap.eastmoney.com/3g/center/List.shtml#2/8");
    map.add("http://quote.eastmoney.com/center/usstock.html#_2","http://wap.eastmoney.com/3g/center/List.shtml#3/0");
    map.add("http://quote.eastmoney.com/center/list.html#70_2","http://wap.eastmoney.com/3g/center/List.shtml#3/1");
    map.add("http://quote.eastmoney.com/center/list.html#28CHINA_2","http://wap.eastmoney.com/3g/center/List.shtml#3/2");
    map.add("http://quote.eastmoney.com/center/list.html#28CHINAINTERNET_2","http://wap.eastmoney.com/3g/center/List.shtml#3/3");
    map.add("http://quote.eastmoney.com/center/list.html#286_2","http://wap.eastmoney.com/3g/center/List.shtml#3/4");
    map.add("http://quote.eastmoney.com/center/list.html#283_2","http://wap.eastmoney.com/3g/center/List.shtml#3/5");
    map.add("http://quote.eastmoney.com/center/list.html#287_2","http://wap.eastmoney.com/3g/center/List.shtml#3/6");
    map.add("http://quote.eastmoney.com/center/list.html#284_2","http://wap.eastmoney.com/3g/center/List.shtml#3/7");
    map.add("http://quote.eastmoney.com/center/list.html#281_2","http://wap.eastmoney.com/3g/center/List.shtml#3/8");
    map.add("http://quote.eastmoney.com/center/list.html#285_2","http://wap.eastmoney.com/3g/center/List.shtml#3/9");
    map.add("http://quote.eastmoney.com/center/global.html#global_3","http://wap.eastmoney.com/3g/center/List.shtml#4/0");
    map.add("http://quote.eastmoney.com/center/asia.html#asia_3","http://wap.eastmoney.com/3g/center/List.shtml#4/1");
    map.add("http://quote.eastmoney.com/center/australia.html#australia_3","http://wap.eastmoney.com/3g/center/List.shtml#4/2");
    map.add("http://quote.eastmoney.com/center/africa.html#africa_3","http://wap.eastmoney.com/3g/center/List.shtml#4/3");
    map.add("http://quote.eastmoney.com/center/america.html#america_3","http://wap.eastmoney.com/3g/center/List.shtml#4/4");
    map.add("http://quote.eastmoney.com/center/europe.html#europe_3","http://wap.eastmoney.com/3g/center/List.shtml#4/5");
    map.add("http://quote.eastmoney.com/gjqh/{0}.html","http://wap.eastmoney.com/3g/stock/GlobalFuturesIndex,{0}0.shtml");
    //map.add("http://quote.eastmoney.com/qihuo/{0}.html","http://wap.eastmoney.com/3g/stock/GlobalFuturesIndex,{0}.shtml");
    map.add("http://quote.eastmoney.com/forex/{0}.html","http://wap.eastmoney.com/3g/stock/GlobalFuturesIndex,{0}.shtml");
    map.add("http://quote.eastmoney.com/center/list.html#forex_6","http://wap.eastmoney.com/3g/center/List.shtml#7/0");
    map.add("http://quote.eastmoney.com/center/list.html#1_6","http://wap.eastmoney.com/3g/center/List.shtml#7/1");
    map.add("http://quote.eastmoney.com/center/list.html#2_6","http://wap.eastmoney.com/3g/center/List.shtml#7/2");
    map.add("http://quote.eastmoney.com/center/list.html#rmbzjj_6","http://wap.eastmoney.com/3g/center/List.shtml#7/3");
    map.add("http://quote.eastmoney.com/center/list.html#28RMBRate_6","http://wap.eastmoney.com/3g/center/List.shtml#7/4");
    map.add("http://quote.eastmoney.com/center/GlodList.html#gjgjsqh_7","http://wap.eastmoney.com/3g/center/List.shtml#8/0");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,7,1_7","http://wap.eastmoney.com/3g/center/List.shtml#8/1");
    map.add("http://quote.eastmoney.com/center/GlodList.html#gjgjsxh_7","http://wap.eastmoney.com/3g/center/List.shtml#8/2");
    map.add("http://quote.eastmoney.com/center/GlodList.html#shhjxh_7","http://wap.eastmoney.com/3g/center/List.shtml#8/3");
    map.add("http://quote.eastmoney.com/center/GlodList.html#hbgjs_7","http://wap.eastmoney.com/3g/center/List.shtml#8/4");
    map.add("http://quote.eastmoney.com/center/GlodList.html#yhgjs_7","http://wap.eastmoney.com/3g/center/List.shtml#8/6");
    map.add("http://quote.eastmoney.com/center/list.html#bondIndex_8","http://wap.eastmoney.com/3g/center/List.shtml#9/0");
    map.add("http://quote.eastmoney.com/center/list.html#14.1.1_8_0","http://wap.eastmoney.com/3g/center/List.shtml#9/1");
    map.add("http://quote.eastmoney.com/center/list.html#14.2.1_8_0","http://wap.eastmoney.com/3g/center/List.shtml#9/1/1//");
    map.add("http://quote.eastmoney.com/center/list.html#14.3_8_0","http://wap.eastmoney.com/3g/center/List.shtml#9/1/2//");
    map.add("http://quote.eastmoney.com/center/list.html#24.1_8_1","http://wap.eastmoney.com/3g/center/List.shtml#9/2");
    map.add("http://quote.eastmoney.com/center/list.html#24.2_8_1","http://wap.eastmoney.com/3g/center/List.shtml#9/2/1//");
    map.add("http://quote.eastmoney.com/center/list.html#24.3_8_1","http://wap.eastmoney.com/3g/center/List.shtml#9/2/2//");
    map.add("http://quote.eastmoney.com/center/list.html#2850020_8","http://wap.eastmoney.com/3g/center/List.shtml#9/3");
    map.add("http://quote.eastmoney.com/center/list.html#2850021_8","http://wap.eastmoney.com/3g/center/List.shtml#9/4");
    //map.add("http://quote.eastmoney.com/qihuo/{0}.html","http://wap.eastmoney.com/3g/stock/FuturesIndex,{0}.shtml");
    map.add("http://quote.eastmoney.com/center/futurelist.html#11_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/0//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,0,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/1//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,1,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/2//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,2,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/3//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,3,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/4//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,4,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/5//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,5,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/6//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,6,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/7//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,7,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/8//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,8,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/9//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,9,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/10//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#1,10,1_5_0","http://wap.eastmoney.com/3g/center/List.shtml#5/0/11//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,0,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/1//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,1,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/2//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,2,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/3//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,3,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/4//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,4,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/5//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,5,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/6//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,6,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/7//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,7,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/8//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,8,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/9//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,9,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/10//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,10,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/11//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,11,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/12//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,12,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/13//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#3,13,2_5_1","http://wap.eastmoney.com/3g/center/List.shtml#5/1/14//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,0,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/1//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,1,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/2//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,2,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/3//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,3,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/4//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,4,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/5//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,5,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/6//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,6,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/7//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,7,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/8//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,8,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/9//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,9,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/10//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,10,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/11//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,11,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/12//");
    map.add("http://quote.eastmoney.com/center/futurelist.html#4,12,3_5_2","http://wap.eastmoney.com/3g/center/List.shtml#5/2/13//");
    map.add("http://quote.eastmoney.com/center/list.html#12_5_3","http://wap.eastmoney.com/3g/center/List.shtml#5/3");
    map.add("http://quote.eastmoney.com/center/list.html#12.1_5_3","http://wap.eastmoney.com/3g/center/List.shtml#5/3/1//");
    map.add("http://quote.eastmoney.com/center/list.html#12.2_5_3","http://wap.eastmoney.com/3g/center/List.shtml#5/3/2//");
    map.add("http://quote.eastmoney.com/center/list.html#13_5_3","http://wap.eastmoney.com/3g/center/List.shtml#5/3/3//");
    map.add("http://quote.eastmoney.com/center/list.html#gjqh_5","http://wap.eastmoney.com/3g/center/List.shtml#5/4");
    map.add("http://quote.eastmoney.com/center/list.html#btb_10", "http://wap.eastmoney.com/3g/center/List.shtml#10/0");
    map.add("http://quote.eastmoney.com/us/{0}.html", "http://m2.quote.eastmoney.com/h5stock/{0}7.html");
    map.ByCheck(u,c);
})(window);