var path = Man.context + "/3rd/Jcorp";
function ManDrag() {
    //初始化
    this.initialize.apply(this, arguments)
}
ManDrag.prototype = {
    //初始化
    initialize: function (drag, options) {
        this.drag = drag;
        this.setOptions(options);
        this.image = this.options.image;
        this.lblindex = this.options.lblindex;
        this.drgpapa = this.options.drgpapa;
        this.defx = this.options.defx;
        this.defy = this.options.defy;
        this.doxy = this.options.doxy;
        this.drag.draggable(
            {
                cursor: 'move',
                //snap: true,
                opacity: 0.95,
                containment: '#'+this.drgpapa,  //容器
                image: '#' + this.image,        //背景图
                defx: this.defx,        //背景图
                defy: this.defy,        //背景图
                doxy: this.doxy,        //背景图
                lblindex: this.lblindex,        //序号
                start: function (event, ui) {
                },
                stop: function (event, ui) {
                    var divPapa = $($(this).draggable("option","containment"));
                    var imgObj = $($(this).draggable("option","image"));
                    var lblindex = $(this).draggable("option","lblindex");
                    var divTop = parseInt(divPapa.offset().top);
                    var divLeft = parseInt(divPapa.offset().left);
                    var imgTop = parseInt(imgObj.offset().top);
                    var imgLeft = parseInt(imgObj.offset().left);
                    var imgWidth = parseInt(imgObj.width());
                    var imgHeight = parseInt(imgObj.height());
                    var lblWidth = ui.helper.width();
                    var lblHeight = ui.helper.height();
                    var xxx = parseInt(ui.position.left - imgLeft);
                    var yyy = parseInt(ui.position.top + lblHeight - imgTop);
                    $("#pointx" + lblindex).val(Math.round(xxx*10000/imgWidth)/10000);
                    $("#pointy" + lblindex).val(Math.round(yyy*10000/imgHeight)/10000);
                    if (xxx <= 0) {
                        $("#positionflag" + lblindex).val("2");
                        $("#labeldivL" + lblindex).children("img").attr("src", path+"/images/left1.png");
                        $("#labeldivR" + lblindex).children("img").attr("src", path+"/images/right1.png");
                    } else if (xxx + lblWidth >= imgWidth) {
                        $("#positionflag" + lblindex).val("1");
                        $("#labeldivL" + lblindex).children("img").attr("src", path+"/images/left2.png");
                        $("#labeldivR" + lblindex).children("img").attr("src", path+"/images/right2.png");
                    }

                    $("#fordebug").text("X:" + xxx + ",Y:" + yyy + ",IW:" + imgWidth
                        + ",divT:" + divTop
                        + ",divL:" + divLeft
                        + ",imgT:" + imgTop
                        + ",imgL:" + imgLeft
                    );
                },
                create: function (event, ui) {
                    var doxy = $(this).draggable("option","doxy");
                    if (doxy=="1") {
                        var imgObj = $($(this).draggable("option", "image"));
                        var defx = $(this).draggable("option", "defx");
                        var defy = $(this).draggable("option", "defy");
                        var lblindex = $(this).draggable("option", "lblindex");
                        var imgTop = parseInt(imgObj.offset().top);
                        var imgLeft = parseInt(imgObj.offset().left);
                        var imgHeight = parseInt(imgObj.height());
                        var imgWidth = parseInt(imgObj.width());
                        var lblObj = $("#labeldiv"+lblindex);
                        var lblHeight = lblObj.height();
                        var lblWidth = lblObj.width();
                        var xxx = parseInt(defx - imgLeft);
                        var yyy = parseInt(defy + lblHeight - imgTop);
                        var lblTop = lblObj.position().top;
                        var lblLeft = lblObj.position().left;

                        //越界调整
                        var xxx1 = (xxx+lblWidth>imgWidth)?(imgWidth-lblWidth):xxx;
                        var yyy1 = (yyy-lblHeight<0)?lblHeight:yyy;
                        lblObj.css("top",(lblTop+(yyy1-yyy))+"px");
                        lblObj.css("left",(lblLeft+(xxx1-xxx))+"px");

                        $("#pointx" + lblindex).val(Math.round(xxx1*10000/imgWidth)/10000);
                        $("#pointy" + lblindex).val(Math.round(yyy1*10000/imgHeight)/10000);
                    }
                }
            });
    },
    //参数设置
    setOptions: function (options) {
        this.options =
        {
            handle: this.drag  //事件对象
            ,image: 'img1'     //对应图片
            ,lblindex: 1       //对应图片
            ,drgpapa: 'drg'    //所在区域
            ,defx: 100         //初始位置
            ,defy: 100         //初始位置
            ,doxy: 0
        };
        for (var p in options) {
            this.options[p] = options[p];
        }
    }

};