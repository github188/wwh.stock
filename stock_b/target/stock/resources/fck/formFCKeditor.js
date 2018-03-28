var sFCKeditorToolbar = 'Default';
var sFCKeditorBasePath = '../';
//var sFCKeditorBaseHref = '../';
//var sFCKeditorBaseHref = parent.document.getElementById("baseHref").value;
var sFCKeditorBaseHref = parent.document.getElementById("root").value;

var sFCKeditorSkinPath = '../editor/skins/office2003/';


Ext.form.FCKeditor = function(config){
	Ext.form.FCKeditor.superclass.constructor.call(this, config);
	this.FCKid=0;
	this.MyisLoaded=false;
	this.MyValue='';
};

Ext.extend(Ext.form.FCKeditor, Ext.form.TextArea,  {
	onRender : function(ct, position){
        if(!this.el){
            this.defaultAutoCreate = {
                tag: "textarea",
                style:"width:100px;height:60px;",
                autocomplete: "off"
            };
        }
        Ext.form.TextArea.superclass.onRender.call(this, ct, position);
        if(this.grow){
            this.textSizeEl = Ext.DomHelper.append(document.body, {
                tag: "pre", cls: "x-form-grow-sizer"
            });
            if(this.preventScrollbars){
                this.el.setStyle("overflow", "hidden");
            }
            this.el.setHeight(this.growMin);
        }
		if (this.FCKid==0) this.FCKid=get_FCKeditor_id_value()
		setTimeout("loadFCKeditor('"+this.name+"',"+this.height+","+this.width+");",100);
    },
    setValue : function(value){
    	this.MyValue=value;
    	if (this.FCKid==0) this.FCKid=get_FCKeditor_id_value();
    	FCKeditorSetValue(this.FCKid,this.name,value);
    	Ext.form.TextArea.superclass.setValue.apply(this,[value]);
    },
    
   
    
    getValue : function(){
    	if (this.MyisLoaded){
    		value=FCKeditorGetValue(this.name);
    		Ext.form.TextArea.superclass.setValue.apply(this,[value]);
			return Ext.form.TextArea.superclass.getValue(this);
    	}else{
    		return this.MyValue;
    	}
    },
    
    getRawValue : function(){
    	if (this.MyisLoaded){
    		value=FCKeditorGetValue(this.name);
    		Ext.form.TextArea.superclass.setRawValue.apply(this,[value]);
			return Ext.form.TextArea.superclass.getRawValue(this);
    	}else{
    		return this.MyValue;
    	}
    }
});
Ext.reg('fckeditor', Ext.form.FCKeditor);


function loadFCKeditor(element,width,height){
	oFCKeditor = new FCKeditor( element ) ;
	oFCKeditor.ToolbarSet = sFCKeditorToolbar ;
	oFCKeditor.Config['SkinPath'] = sFCKeditorSkinPath ;
	oFCKeditor.Config['PreloadImages'] = sFCKeditorSkinPath + 'images/toolbar.start.gif' + ';' +
				sFCKeditorSkinPath + 'images/toolbar.end.gif' + ';' +
				sFCKeditorSkinPath + 'images/toolbar.bg.gif' + ';' +
				sFCKeditorSkinPath + 'images/toolbar.buttonarrow.gif' ;
	oFCKeditor.BasePath	= sFCKeditorBasePath ;
	oFCKeditor.Config['BaseHref']	= sFCKeditorBaseHref ;
	if(height){ oFCKeditor.Height = height ;}
	else{	oFCKeditor.Height = 260 ;}
	oFCKeditor.ReplaceTextarea() ;

}

var FCKeditor_value = new Array();
var fist_load_fck = true;
var fck_name_value_bug,fck_id_value_bug;
function FCKeditorSetValue(id,name,value){
	if ((id!=undefined)&&(name!=undefined)){
		if (value!=undefined) FCKeditor_value[id]=value;
		else if (FCKeditor_value[id]==undefined) FCKeditor_value[id]='';
		fck_name_value_bug = name;
		fck_id_value_bug = id;
		if(fist_load_fck){
			setTimeout("waitForSetFck()",1000);
			fist_load_fck = false;
		}else{
			waitForSetFck();
		}
	}
}
function waitForSetFck(){
	if(typeof(FCKeditorAPI)!="object"){
		setTimeout("waitForSetFck()",10);
	}else{
		var oEditor = FCKeditorAPI.GetInstance(fck_name_value_bug);
		if(oEditor!=undefined) oEditor.SetHTML(FCKeditor_value[fck_id_value_bug]);
	}
}
function FCKeditorGetValue(name){
	if ((id!=undefined)&&(name!=undefined)){
		var oEditor = FCKeditorAPI.GetInstance(name);
		data='';
		if(oEditor!=undefined) data=oEditor.GetXHTML();
		data=Ext.form.TextArea.superclass.getValue(this);
		return data;
	}
}
var FCKeditor_id_value;
function get_FCKeditor_id_value(){
	if (!FCKeditor_id_value){
		FCKeditor_id_value=0;
	}
	FCKeditor_id_value=FCKeditor_id_value+1;
	return FCKeditor_id_value;
}