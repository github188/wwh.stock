
/* convert string start */

// encode url
function _convert_en(str)
{
	var en="",i=0;
	for(i=0;i<str.length;i++){ 
		if(str.charCodeAt(i)>=0&&str.charCodeAt(i)<=255){ 
			  en=en+escape(str.charAt(i));
			}
			else {
				en=en+str.charAt(i);
			}
	}
  return en;
}

// convert string
function str_reverse(str) {
	var ln = str.length;
	var i=0;
	var temp="";
	for(i=ln-1;i>-1;i--) {
		if(str.charAt(i)==".")
			temp += "#";
		else
			temp += str.charAt(i);
	}

	return temp;
}

/* convert string end */


/* check browse's state start */

// get screen width&height
function _g_screen_pix() {
	if (self.screen) { 
		sr=screen.width+"x"+screen.height;
	}
	else if (self.java) { 
		var j=java.awt.Toolkit.getDefaultToolkit();
		var s=j.getScreenSize();
		sr=s.width+"x"+s.height;
	}
	return sr;
}

//get navigator's appName
function _g_nvapp() {
	var so="";
	var n=navigator;
	if (n.appName) {
		so=n.appName;
	}
	return so;
}

//get screen color depth
function _g_scr_color() { 
	var sc="";
	if (self.screen) {
		sc=screen.colorDepth+"-bit";
	}
	return sc;
}

//get browser Language
function _g_language() {
	var lg="";var n=navigator;if (n.language) {
		lg=n.language.toLowerCase();
	}
	else if (n.browserLanguage) {
		lg=n.browserLanguage.toLowerCase();
	}
	return lg;
}

//get browser userAgent
function _g_agent() {
	var ag="";
	var n=navigator;
	if (n.userAgent) { 
		ag = n.userAgent;
	}
  return ag;
}

//is or not allow java
function _g_javaenabled() { 
	var je="";
	var n=navigator;
	je = n.javaEnabled()?1:0;return je;
}

//get flash version
function _g_flashv() {
	var f="",n=navigator;
	if (n.plugins && n.plugins.length) {
		for (var ii=0;ii<n.plugins.length;ii++) {
			if (n.plugins[ii].name.indexOf('Shockwave Flash')!=-1) {
				f=n.plugins[ii].description.split('Shockwave Flash ')[1];
				break;
			}
		}
	}
  else if (window.ActiveXObject) {
  	for (var ii=10;ii>=2;ii--) {
  		try {
  			var fl=eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash."+ii+"');");
  			if (fl) { f=ii + '.0';
  				break;
  			}
  		}
  		catch(e)
  		{}
  	}
  }
  return f;
}

// is or not allow cookie
function _g_cookieenabled() {
	var c_en = (navigator.cookieEnabled)? 1 : 0;return c_en;
}

/* check browse's state end */

/* get string or int from contains wordkey start */

//get string front of first char'_'
function _g_chr_1(str)
{
	len=str.indexOf("_");
	str=str.substring(0,len);
	return str;
}

// get string after 2 char'_' 
function _g_chr_2(str) {
	len=str.indexOf("_");
	str=str.substring(len+1);
	len=str.indexOf("_");
	str=str.substring(len+1);
	return str;
}

//get int 1
function _g_int_1(str)
{
	len=str.indexOf("_");
	str=str.substring(len+1);
	len=str.indexOf("_");

	str=str.substring(0,len);

	return parseInt(str);
}

//get int 2
function _g_int_2(str)
{
	len=str.indexOf("_");
	str=str.substring(len+1);
	len=str.indexOf("_");
	str=str.substring(len+1);

	return parseInt(str);
}

//get int 3
function _g_int_3(str) {
	len=str.indexOf("_");
	str=str.substring(len+1);
	len=str.indexOf("_");
	str=str.substring(0,len);
	return parseInt(str, 10);
}

//get int 4
function _g_int_4(str) {
	len=str.indexOf("_");
	str=str.substring(0,len);
	return parseInt(str);
}

/* get string or int from contains wordkey end */


/* check ref & ref para start */

//check referrer's page
function _check_ref(ref)
{
	if(ref.indexOf(".baidu.com") > 0){
		return 1;
	}
	if(ref.indexOf(".google.com") > 0){
		return 2;
	}
	if(ref.indexOf("so.eastmoney.com") > 0){
		return 3;
	}
	return 0;
}

/* check ref & ref para end */


/* cookie get or save start */

//get cookie
function _g_cookie(name) {
	var mn=name+"=";
	var b,e;
	var co=document.cookie;
	if (mn=="=") {
		return co;
	}
	b=co.indexOf(mn);
	if (b < 0) {
		return "";
	}
	e=co.indexOf(";", b+name.length);
	if (e < 0) {
		return co.substring(b+name.length + 1);
	}
	else {
		return co.substring(b+name.length + 1, e);
	}
}

//save cookie
function _s_cookie(name,val,m_expr) 
{ 
	var date=new Date;
	var year=date.getFullYear();
	var hour=date.getHours();

	var cookie="";

	if (m_expr == 0) { 
		cookie=name+"="+val+";";
	}
	else if (m_expr == 1) {
		year=year+10;
		date.setYear(year);
		cookie=name+"="+val+";expires="+date.toGMTString()+";";
	}
	else if (m_expr == 2) { 
		hour=hour+1;
		date.setHours(hour);
		cookie=name+"="+val+";expires="+date.toGMTString()+";";
	}


	var d=_g_domain(document.domain);
	if(d != ""){
		cookie +="domain="+d+";";
	}
	cookie +="path="+"/;";
	document.cookie=cookie;
}


//save cookie time
function _s_cookie_time(name, val, day)
{
	var date=new Date;
	var vDay = date.getDate();

	var cookie="";

	vDay=vDay+day;

	date.setDate(vDay);

	cookie=name+"="+val+";expires="+date.toGMTString()+";";

	var d=_g_domain(document.domain);
	if(d != ""){
		cookie +="domain="+d+";";
	}
	cookie +="path="+"/;";
	document.cookie=cookie;
}

//get domain
function _g_domain(host)
{
	var d=host.replace(/^www\./, "");

	var ss=d.split(".");
	var l=ss.length;

	if(l == 3){
		if(_c_ctry_top_domain(ss[1]) && _c_ctry_domain(ss[2])){
		}
		else{
			d = ss[1]+"."+ss[2];
		}
	}
	else if(l >= 3){
		var ip_pat = "^[0-9]*\.[0-9]*\.[0-9]*\.[0-9]*$";

		if(host.match(ip_pat)){ return d;}

		if(_c_ctry_top_domain(ss[l-2]) && _c_ctry_domain(ss[l-1])){
			d = ss[l-3]+"."+ss[l-2]+"."+ss[l-1];
		}
		else{ d = ss[l-2]+"."+ss[l-1];}
	}
		
	return d;
}

function _c_ctry_top_domain(str)
{ var pattern = "/^aero$|^cat$|^coop$|^int$|^museum$|^pro$|^travel$|^xxx$|^com$|^net$|^gov$|^org$|^mil$|^edu$|^biz$|^info$|^name$|^ac$|^mil$|^co$|^ed$|^gv$|^nt$|^bj$|^hz$|^sh$|^tj$|^cq$|^he$|^nm$|^ln$|^jl$|^hl$|^js$|^zj$|^ah$|^hb$|^hn$|^gd$|^gx$|^hi$|^sc$|^gz$|^yn$|^xz$|^sn$|^gs$|^qh$|^nx$|^xj$|^tw$|^hk$|^mo$|^fj$|^ha$|^jx$|^sd$|^sx$/i";

	if(str.match(pattern)){ return 1;}

	return 0;
}

function _c_ctry_domain(str)
{
	var pattern = "/^ac$|^ad$|^ae$|^af$|^ag$|^ai$|^al$|^am$|^an$|^ao$|^aq$|^ar$|^as$|^at$|^au$|^aw$|^az$|^ba$|^bb$|^bd$|^be$|^bf$|^bg$|^bh$|^bi$|^bj$|^bm$|^bo$|^br$|^bs$|^bt$|^bv$|^bw$|^by$|^bz$|^ca$|^cc$|^cd$|^cf$|^cg$|^ch$|^ci$|^ck$|^cl$|^cm$|^cn$|^co$|^cr$|^cs$|^cu$|^cv$|^cx$|^cy$|^cz$|^de$|^dj$|^dk$|^dm$|^do$|^dz$|^ec$|^ee$|^eg$|^eh$|^er$|^es$|^et$|^eu$|^fi$|^fj$|^fk$|^fm$|^fo$|^fr$|^ly$|^hk$|^hm$|^hn$|^hr$|^ht$|^hu$|^id$|^ie$|^il$|^im$|^in$|^io$|^ir$|^is$|^it$|^je$|^jm$|^jo$|^jp$|^ke$|^kg$|^kh$|^ki$|^km$|^kn$|^kp$|^kr$|^kw$|^ky$|^kz$|^la$|^lb$|^lc$|^li$|^lk$|^lr$|^ls$|^lt$|^lu$|^lv$|^ly$|^ga$|^gb$|^gd$|^ge$|^gf$|^gg$|^gh$|^gi$|^gl$|^gm$|^gn$|^gp$|^gq$|^gr$|^gs$|^gt$|^gu$|^gw$|^gy$|^ma$|^mc$|^md$|^mg$|^mh$|^mk$|^ml$|^mm$|^mn$|^mo$|^mp$|^mq$|^mr$|^ms$|^mt$|^mu$|^mv$|^mw$|^mx$|^my$|^mz$|^na$|^nc$|^ne$|^nf$|^ng$|^ni$|^nl$|^no$|^np$|^nr$|^nu$|^nz$|^om$|^re$|^ro$|^ru$|^rw$|^pa$|^pe$|^pf$|^pg$|^ph$|^pk$|^pl$|^pm$|^pr$|^ps$|^pt$|^pw$|^py$|^qa$|^wf$|^ws$|^sa$|^sb$|^sc$|^sd$|^se$|^sg$|^sh$|^si$|^sj$|^sk$|^sl$|^sm$|^sn$|^so$|^sr$|^st$|^su$|^sv$|^sy$|^sz$|^tc$|^td$|^tf$|^th$|^tg$|^tj$|^tk$|^tm$|^tn$|^to$|^tp$|^tr$|^tt$|^tv$|^tw$|^tz$|^ua$|^ug$|^uk$|^um$|^us$|^uy$|^uz$|^va$|^vc$|^ve$|^vg$|^vi$|^vn$|^vu$|^ye$|^yt$|^yu$|^za$|^zm$|^zr$|^zw$/i";

	if(str.match(pattern)){ return 1;}

	return 0;
}

/* cookie get or save end */


/* other function start */

//get para by time
function _g_offset_time() 
{ 
	var date = new Date();
	var yy=date.getFullYear();
	var mm=date.getMonth();
	var dd=date.getDate();
	var hh=date.getHours();
	var ii=date.getMinutes();
	var ss=date.getSeconds();
	var i;
	var tm=0;
	for(i = 1970;i < yy;i++) { 
		if ((i % 4 == 0 && i % 100 != 0) || (i % 100 == 0 && i % 400 == 0)) { 
			tm=tm+31622400;
		}
		else { 
			tm=tm+31536000;
		}
	}
	mm=mm+1;
	
	for(i = 1;i < mm;i++) {
		if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
			tm=tm+2678400;
		}
		else {
			if (i == 2) {
				if ((yy % 4 == 0 && yy % 100 != 0) || (yy % 100 == 0 && yy % 400 == 0)) {
					tm=tm+2505600;
				}
				else {
					tm=tm+2419200;
				}
			}
			else {
				tm=tm+2592000;
			}
		}
	}
	
	tm = tm +  (dd-1) * 86400;
	tm = tm +  hh * 3600;
	tm = tm +  ii * 60;
	tm = tm +  ss;
	return tm;
}

/* other function end */


var _uni_id="emcount";
var _expr_time=1800;
var _url_sn=0;
var _ip="js4.eastmoney.com";
var _dest_path = "/cou"+"nt.asp"+"x?unit_id="+_uni_id;
var _cookie_rndnum_new=0;
var _cookie_rndnum ="";
var _st_ss ="";
var _ref="";

var _url="";
var _clr="";
var _scr="";
var _lng="";
var _agt="";
var _jve="";
var _flv="";
var _nvapp="";
var _rndnum="";
var _st_len=0;
var _domain="";
var _host="";

var _st_loc_ip = "";
_st_loc_ip = "116.228.188.26";

var rand;
var _co_en = _g_cookieenabled();
var _ss_val = 0;

_host=document.location.host;
_domain = _g_domain(_host.toLocaleLowerCase());

var _dom_chrcode = 0;
var i = 0;
for (i=0;i< _domain.length;i++){
        _dom_chrcode += _domain.charCodeAt(i);
}
var _lock_flag = 0;

/* get or set _cookie_rndnum start */

_cookie_rndnum=_g_cookie("emstat_bc_"+String(_uni_id));

if(_cookie_rndnum==""){
	_cookie_rndnum_new=1;

	var rand1 = parseInt( Math.random() * 4000000000 );
	var rand2 = parseInt( Math.random() * 4000000000 );
	_cookie_rndnum = String(rand1)+String(rand2);

	_s_cookie("emstat_bc_"+String(_uni_id), _cookie_rndnum, 1);
}

/* get or set _cookie_rndnum end */


/* get or set _cookie view times start */

_st_ss=_g_cookie("emstat_ss_"+String(_uni_id));
if (_st_ss == "") {
	_url_sn = 0;
	rand = parseInt( Math.random() * 4000000000 );
	_st_ss="0_"+_g_offset_time()+"_"+String(rand);
	_s_cookie("emstat_ss_"+String(_uni_id), _st_ss, 0);
}
else {
	if (_g_offset_time() - _g_int_3(_st_ss) > _expr_time) {
		_url_sn = 0;
		rand = parseInt( Math.random() * 4000000000 );
		_st_ss="0_"+_g_offset_time()+"_"+String(rand);
	}
	else{
		_url_sn = _g_int_4(_st_ss) + 1;
		_ss_val = _g_chr_2(_st_ss);
		_st_ss = String(_url_sn)+"_"+_g_offset_time()+"_"+_ss_val;
	}
	_s_cookie("emstat_ss_"+String(_uni_id), _st_ss, 1);
}

/* get or set _cookie view times end */


/* get or set _cookie add expires start */
var _ade_cookie = _g_cookie("emstat_ade_"+String(_uni_id));


var _ade_value = "0";
var _ade_sttime = 0;
var _ade_adtime = 0;


if(_ade_cookie){
	_ade_value = _g_chr_1(_ade_cookie);
	_ade_sttime = _g_int_1(_ade_cookie);
//_ade_adtime = _g_int_2(_ade_cookie);
}


_rndnum=String(Math.random());
_st_len=_st_ss.indexOf("_");
_st_ss=_g_chr_2(_st_ss);

_ref=document.referrer;
_ref=_convert_en(String(_ref));

/* ref handle end */

/* browser handle start */

_url=document.URL;
_url=_convert_en(String(_url));
_clr=_g_scr_color();
_clr=_convert_en(String(_clr));
_scr=_g_screen_pix();
_scr=_convert_en(String(_scr));
_lng=_g_language();
_lng=_convert_en(String(_lng));
_agt=_g_agent();
_agt=_convert_en(String(_agt));
_jve=_g_javaenabled();
_jve=_convert_en(String(_jve));
_flv=_g_flashv();
_flv=_convert_en(String(_flv));
_nvapp=_g_nvapp();
_nvapp=_convert_en(String(_nvapp));

/* kill domain and locked ip start */

// the domain reverse string list
var fds = new Array();

fds[0] = "gro#tra763";
fds[1] = "moc#olzd";
fds[2] = "moc#tra763";
fds[3] = "moc#df571";
fds[4] = "ten#oog1";
fds[5] = "nc#ppk1";
fds[6] = "nc#osnaknak";
fds[7] = "nc#emocwww";
fds[8] = "nc#psalla";
fds[9] = "moc#oesii";
fds[10] = "moc#kh0083";
fds[11] = "nc#kpwww";
fds[12] = "nc#moc#zw001";
fds[13] = "nc#kpemoc";
fds[14] = "moc#eyiq063";
fds[15] = "moc#qqa4";
fds[16] = "ten#aboakoak";
fds[17] = "moc#ecilliw";
fds[18] = "nc#moc#ibeea";
fds[19] = "moc#ibeea";
fds[20] = "nc#tra763";
fds[21] = "moc#025sns";
fds[22] = "moc#og321oah";
fds[23] = "moc#9zznc";
fds[24] = "nc#9zznc";
fds[25] = "moc#d135";
fds[26] = "moc#ridzoog";
fds[27] = "ten#rqrq";
fds[28] = "moc#195nak";
fds[29] = "moc#uynijtn";
fds[30] = "moc#falwen";
fds[31] = "nc#moc#njyhxj";

var _temp_domain = str_reverse(_domain);

var i = 0;
for (i in fds){
        if(fds[i] == _temp_domain)
		_lock_flag = 1;	
}

var fips = new Array();

var i = 0;
for (i in fips){
  if(fips[i] == _st_loc_ip){
		_lock_flag = 1;
		break;
  }
}
/* kill domain and locked ip end */

_st_dest="http://"+_ip+_dest_path+"&ck="+_cookie_rndnum+"&cookie_rndnum_new="+_cookie_rndnum_new+"&ade="+_ade_value+"&adtm="+_ade_adtime+"&sttm="+_ade_sttime+"&ss="+_st_ss+"&uvt="+_url_sn+"&co_enabled="+_co_en+"&UrlReferrer="+_ref+"&urlCurrent="+_url+"&dom="+_domain+"&dom_charcode="+_dom_chrcode+"&lock_flag="+_lock_flag+"&nvapp="+_nvapp+"&agt="+_agt+"&clr="+_clr+"&scr="+_scr+"&lng="+_lng+"&jve="+_jve+"&flv="+_flv+"&cpyno="+_cpyno+"&rndnum="+_rndnum;

document.open();
document.write("<script language=\"JavaScript\" type=\"text/javascript\" src=\""+_st_dest+"\"></script>");
document.close();
