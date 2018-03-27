function HistoryViews(obj,arg){
    this.WriteCookie=function(name,value,hours){
        var expire="";
        if(hours!=null){
            expire=new Date((new Date()).getTime()+hours*3600000);
            expire=";expires="+expire.toGMTString()+";path=/;domain=.eastmoney.com";
        }
        document.cookie=name+"="+escape(value)+expire;
    };

    this.GetCookie=function(name){
        var dc = document.cookie;
        var prefix = name + "=";
        var begin = dc.indexOf("; " + prefix);
        if(begin == -1){
            begin = dc.indexOf(prefix);
            if (begin != 0) {return null};
        }
        else{
            begin += 2;
        }
        var end = document.cookie.indexOf(";", begin);
        if(end == -1){
            end = dc.length;
        }
        return unescape(dc.substring(begin + prefix.length, end));
    };
    this.setvi=1;
    if(arg.def==""){
        arg.def=["a-sh-600000-浦发银行","a-sz-300017-网宿科技","a-sh-600020-中原高速","a-sh-600005-武钢股份","a-sh-600004-白云机场","a-sz-162605-景顺鼎益","a-sz-159901-深100ETF","a-sh-600015-华夏银行","a-sz-002364-中恒电气","a-sh-600128-弘业股份","a-sz-002357-富临运业","a-sz-002363-隆基机械","a-sh-601106-中国一重","a-sz-002013-中航精机","a-sz-000550-江铃汽车"];
    }
    if(arg.def=="0"){
        this.setvi=0;
    }
    this.mb={"a":"/stockeast/detail?stockCode=[#CODE#]","b":"http://guba.eastmoney.com/topic,[#CODE#].html","c":"http://fund.eastmoney.com/[#CODE#].html","d":"http://quote.eastmoney.com/hk/[#CODE#].html","e":"http://quote.eastmoney.com/gzqh/[#CODE#].html","f":"http://quote.eastmoney.com/zs[#CODE#].html","g":"http://quote.eastmoney.com/qihuo/[#CODE#].html","h":"http://quote.eastmoney.com/hk/[#CODE#].html","i":"http://quote.eastmoney.com/hk/zs[#CODE#].html","j":"http://quote.eastmoney.com/us/[#CODE#].html","k":"http://quote.eastmoney.com/forex/[#CODE#].html","l":"http://quote.eastmoney.com/[#MARKET#][#CODE#].html","m":"http://quote.eastmoney.com/gb/zs[#CODE#].html","n":"http://quote.eastmoney.com/gjqh/[#CODE#].html","o":"http://quote.eastmoney.com/qiquan/[#CODE#]_[#MARKET#].html","p":"http://quote.eastmoney.com/3ban/[#MARKET#][#CODE#].html"};
    if(this.GetCookie("em_hq_fls")=="new"){
        this.mb.a="http://quote.eastmoney.com/flash/[#MARKET#][#CODE#].html";
    }
    //[类型(a:行情,b:股吧,c:基金,d:港股,e:股指期货,f:大盘指数,g:期货,h:港股,i:港股指数,j:美股,k:外汇,l:债券,m:全球指数,n:国际期货,o:期权,p:3板),市场(没有为0),代码,名称]
    this.len=arg.lns;
    if(arg.lns==0 || arg.lns>arg.def.length || arg.lns=="" || arg.lns=="undefined"){
        this.len=arg.def.length;
    }
    this.addlen=0;
    this.ret=[];
    this.init=function(){
        if(arg.set==""){
            this.ReadInfo();
        }
        else{
            this.SetInfo();
        }
    };
    this.ReadInfo=function(){
        var Str="<ul>";
        var showlist=new Array();
        var _ckv=this.GetCookie("HAList");
        var ckv;
        if (_ckv!="" && _ckv!=null){
            var ckv=_ckv.split(',');
            if(ckv.length<this.len){
                this.addlen=this.len-ckv.length;
            }
        }
        else{
            ckv=arg.def;
        }
        for(var i=0;i<ckv.length;i++){
            if(i>=this.len){break;}
            var _tmcn=ckv[i];showlist.push(ckv[i]);
            var tmcn=_tmcn.split('-');
            if(tmcn.length==4){
                var tp=tmcn[0];var mk=tmcn[1];var cd=tmcn[2];var nm=tmcn[3];var lk="";
                var _lk=eval("this.mb."+tp);
                var lk=_lk.replace("[#MARKET#]",mk).replace("[#CODE#]",cd);
                Str+="<li><a href=\""+lk+"\" target=\"_blank\">"+nm+"</a></li>";
                this.ret.push(mk+"-"+cd+"-"+nm);
            }
        }
        if(this.addlen>0){
            var ed=0;
            for(var i=0;i<arg.def.length;i++){
                if(showlist.toString().indexOf(arg.def[i])==-1){
                    var tmcn=arg.def[i].split('-');
                    if(tmcn.length==4){
                        var tp=tmcn[0];var mk=tmcn[1];var cd=tmcn[2];var nm=tmcn[3];var lk="";
                        var _lk=eval("this.mb."+tp);
                        var lk=_lk.replace("[#MARKET#]",mk).replace("[#CODE#]",cd);
                        Str+="<li><a href=\""+lk+"\" target=\"_blank\">"+nm+"</a></li>";
                        this.ret.push(mk+"-"+cd+"-"+nm);
                        ed++;
                    }
                }
                if(ed>=this.addlen){break;}
            }
        }
        Str+="</ul>";
        if(this.setvi==1){
            if (document.getElementById(obj) != null) document.getElementById(obj).innerHTML=Str;
        }
    };

    this.SetInfo=function(){
        var _ckv=this.GetCookie("HAList");
        var ckv=new Array();
        if(_ckv!="" && _ckv!=null){
            ckv=_ckv.split(',');
        }
        ckv.unshift(arg.set);
        for(var i=1;i<ckv.length;i++){
            var pattern=new RegExp("([^-]+)-([^-]+)-([^-]+)-([^-]+)","ig");
            var _s = ckv[i].replace(pattern,"$1-$2-$3-***");
            var _o = arg.set.replace(pattern,"$1-$2-$3-***");
            if(_s==_o){
                ckv.splice(i,1);
                break;
            }
            if(i>=this.len){
                ckv.splice(i,1);
            }
        }
        this.WriteCookie("HAList",ckv,99999);
        this.ReadInfo();
    };
    this.init();
}