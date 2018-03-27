var firstload = 1;
var globalx,globaly;
var labelcount = 0;
var labelheight = 28;

var lbls=new Array()
lbls[0]="willsom";
lbls[1]="万里长城永不倒";
lbls[2]="BMW";
lbls[3]="极品啊";
lbls[4]="世间极品啊";
lbls[5]="醉了";
lbls[6]="Hello World";
lbls[7]="滚滚长江东逝水，浪花淘尽英雄。";
lbls[8]="你最美";
lbls[9]="邓╮(╯_╰)╭❤";
lbls[10]="卡哇伊！！！";
lbls[11]="身骑白马";
lbls[12]="我们";
lbls[13]="范爷也";
lbls[14]="异邦人";
lbls[15]="爱你十分泪七分";
lbls[16]="咪咪流浪记";

function getPosition(e) {
    var x = getX(e);
    var y = getY(e);
    globalx = x;
    globaly = y;
    //$("#inputdiv").html("<input type='text' id='text1'/><input type='button' value='完成' onclick='labelfinish()' class='btn btn-success'>");
}
function getX(e) {
    e = e || window.event;
    return e.pageX || e.clientX + document.body.scroolLeft;
}

function getY(e) {
    e = e|| window.event;
    return e.pageY || e.clientY + document.boyd.scrollTop;
}

function showhiddenlabels(event) {
    getPosition(event);
    if (1 == 1) {
        drawLabel("img1");
        firstload++;
    }
    if ($("#labels").css("visibility") == 'hidden') {
        $("#labels").css("visibility", "");
    } else {
       // $("#labels").css("visibility", "hidden");
    }
}

function drawLabel(img) {
    var labelx = globalx;
    var labely = globaly-labelheight;

    labels(img,labelx,labely, lbls[Math.floor(Math.random()*17)]);
}
function labels(obj, ptX, ptY, lblContent) {
    labels2(obj, ptX, ptY, lblContent, 0);
}

function showLabel(obj, ptX, ptY, lblContent, positionflag, addGridFlg) {
    var labelcount = parseInt($("#img1count").val()) + 1;
    $("#img1count").val(labelcount);
    // 定义标签高度
    var labelheight = 28;

    var labeldiv = "";

    // 标签内容
    var labelcontent = lblContent;
    $("#labels").css("visibility", "");

    // 图片对象
    var jObj = $("#"+obj);

    var imgtop = jObj.offset().top;     // 偏移Y
    var imgleft = jObj.offset().left;   // 偏移X
    var imgwidth = jObj.width();
    var imgheight = jObj.height();

    var pointx = ptX;
    var pointy = ptY;

    //var pointx = ptX;
    //var pointy = ptY;

    //var labelx = imgleft + imgwidth * pointx;
    //var labely = imgtop + imgheight * pointy - labelheight;

    var labelx = imgleft + imgwidth * pointx - 12;
    var labely = imgtop + imgheight * pointy - labelheight + 12;

    if (1 == 1) {
        labeldiv += "<div id='labeldiv"+labelcount+"' style='position:absolute; left:"+labelx+"px; top:"+labely+"px;'>";
        labeldiv += "<div style='float:left' id='labeldivL"+labelcount+"'><img src='/zcomp/3rd/Jcorp/images/left"+positionflag+".png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<div contenteditable='true' style='font-size:14px;color:#FFF;float:left;background-image:url(/zcomp/3rd/Jcorp/images/line.png); height:"+labelheight+"px; line-height:"+labelheight+"px;' id='labeldivM"+labelcount+"'>"+labelcontent+"</div>";
        labeldiv += "<div style='float:left' id='labeldivR"+labelcount+"'><img src='/zcomp/3rd/Jcorp/images/right"+positionflag+".png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<input type='hidden' type='text' id='flag"+labelcount+"' name='flag' value='edit'/>";
        labeldiv += "<input type='hidden' type='text' id='labelcontent"+labelcount+"' name='labelcontent' value='"+labelcontent+"'/>";
        labeldiv += "<input type='hidden' type='text' id='longflag"+labelcount+"' name='longflag' value='0'/>";
        labeldiv += "<input type='hidden' type='text' id='pointx"+labelcount+"' value='"+ptX+"' name='pointx'/>";
        labeldiv += "<input type='hidden' type='text' id='pointy"+labelcount+"' value='"+ptY+"' name='pointy'/>";
        labeldiv += "<input type='hidden' type='text' id='positionflag"+labelcount+"' name='positionflag' value='"+positionflag+"'/>";
        labeldiv += "</div>";
    } else {
        labeldiv += "<div id='labeldiv" + labelcount + "' style='position:absolute; left:" + labelx + "px; top:" + labely + "px;'>";
        labeldiv += "<div style='float:left' id='labeldivL" + labelcount + "'><img src='/zcomp/3rd/Jcorp/images/left1.png' style='height:" + labelheight + "px;'/></div>";
        labeldiv += "<div style='color:#FFF;float:left;background-image:url(/zcomp/3rd/Jcorp/images/line.png); height:" + labelheight + "px; line-height:" + labelheight + "px;' id='labeldivM" + labelcount + "'>" + labelcontent + "</div>";
        labeldiv += "<div style='float:left' id='labeldivR" + labelcount + "'><img src='/zcomp/3rd/Jcorp/images/right1.png' style='height:" + labelheight + "px;'/></div>";
        labeldiv += "</div>";
    }
    $("#labels").append(labeldiv);

    var labelwidth;// 标签宽度
    if($("#labeldivL"+labelcount).width()==0){
        labelwidth = 12+12+$("#labeldivM"+labelcount).width();
    }else{
        labelwidth = $("#labeldiv"+labelcount).width();
    }

    var labeldivleft = $("#labeldiv"+labelcount).offset().left;
    var labeldivtop = $("#labeldiv"+labelcount).offset().top;

    var pointx = (labeldivleft-imgleft)/imgwidth;
    var pointy = (labeldivtop+labelheight-imgtop)/imgheight;

    $("#pointx"+labelcount).val(pointx);
    $("#pointy"+labelcount).val(pointy);
    var oBox = document.getElementById("labeldiv"+labelcount);
    //alert(imgleft + " *  " + imgtop + " * " + labelwidth);
    //var lockminX = imgleft - imgleft;
    //var lockminY = imgtop - imgtop;
    var lockminX = imgleft;
    var lockminY = imgtop;
    var lockmaxX = imgleft + imgwidth-labelwidth;
    var lockmaxY = imgtop + imgheight-labelheight;
    var oDrag = new Drag(oBox, {handle:oBox, limit:false,
        lockminX:lockminX,lockminY:lockminY,lockmaxX:lockmaxX,lockmaxY:lockmaxY,
        imgwidth:imgwidth,imgheight:imgheight,imgtop:imgtop,imgleft:imgleft,
        labelheight:labelheight,labelwidth:labelwidth});

    if (addGridFlg == 0) {
        $('#dg').datagrid('appendRow',{
            lblContent: labelcontent,
            divId: ""+labelcount
        });
    }
}
function labels2(obj, ptX, ptY, lblContent, addGridFlg) {
    // 标签序号
    var labelcount = parseInt($("#img1count").val()) + 1;
    $("#img1count").val(labelcount);
    // 定义标签高度
    var labelheight = 28;

    var labeldiv = "";

    // 标签内容
    var labelcontent = lblContent;

    // 图片对象
    var jObj = $("#"+obj);

    var imgtop = jObj.offset().top;     // 偏移Y
    var imgleft = jObj.offset().left;   // 偏移X
    var imgwidth = jObj.width();
    var imgheight = jObj.height();

    //var labeldivleft = $("#labeldiv"+labelcount).offset().left;
    //var labeldivtop = $("#labeldiv"+labelcount).offset().top;
    var labeldivleft = ptX;
    var labeldivtop = ptY;

    var pointx = (labeldivleft-imgleft)/imgwidth;
    var pointy = (labeldivtop+labelheight-imgtop)/imgheight;

    //var pointx = ptX;
    //var pointy = ptY;

    //var labelx = imgleft + imgwidth * pointx;
    //var labely = imgtop + imgheight * pointy - labelheight;

    var labelx = imgwidth * pointx;
    var labely = imgheight * pointy - labelheight;

    if (1 == 1) {
        labeldiv += "<div id='labeldiv"+labelcount+"' style='position:absolute; left:"+labelx+"px; top:"+labely+"px;'>";
        labeldiv += "<div style='float:left' id='labeldivL"+labelcount+"'><img src='/zcomp/3rd/Jcorp/images/left2.png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<div contenteditable='true' style='font-size:14px;color:#FFF;float:left;background-image:url(/zcomp/3rd/Jcorp/images/line.png); height:"+labelheight+"px; line-height:"+labelheight+"px;' id='labeldivM"+labelcount+"'>"+labelcontent+"</div>";
        labeldiv += "<div style='float:left' id='labeldivR"+labelcount+"'><img src='/zcomp/3rd/Jcorp/images/right2.png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<input type='hidden' type='text' id='flag"+labelcount+"' name='flag' value='add'/>";
        labeldiv += "<input type='hidden' type='text' id='labelcontent"+labelcount+"' name='labelcontent' value='"+labelcontent+"'/>";
        labeldiv += "<input type='hidden' type='text' id='longflag"+labelcount+"' name='longflag' value='0'/>";
        labeldiv += "<input type='hidden' type='text' id='pointx"+labelcount+"' name='pointx'/>";
        labeldiv += "<input type='hidden' type='text' id='pointy"+labelcount+"' name='pointy'/>";
        labeldiv += "<input type='hidden' type='text' id='positionflag"+labelcount+"' name='positionflag' value='1'/>";
        labeldiv += "</div>";
    } else {
        labeldiv += "<div id='labeldiv" + labelcount + "' style='position:absolute; left:" + labelx + "px; top:" + labely + "px;'>";
        labeldiv += "<div style='float:left' id='labeldivL" + labelcount + "'><img src='/zcomp/3rd/Jcorp/images/left1.png' style='height:" + labelheight + "px;'/></div>";
        labeldiv += "<div style='color:#FFF;float:left;background-image:url(/zcomp/3rd/Jcorp/images/line.png); height:" + labelheight + "px; line-height:" + labelheight + "px;' id='labeldivM" + labelcount + "'>" + labelcontent + "</div>";
        labeldiv += "<div style='float:left' id='labeldivR" + labelcount + "'><img src='/zcomp/3rd/Jcorp/images/right1.png' style='height:" + labelheight + "px;'/></div>";
        labeldiv += "</div>";
    }
    $("#labels").append(labeldiv);


    var labelwidth;// 标签宽度
    if($("#labeldivL"+labelcount).width()==0){
        labelwidth = 12+12+$("#labeldivM"+labelcount).width();
    }else{
        labelwidth = $("#labeldiv"+labelcount).width();
    }

    // 宽度超出后调整位置
    //alert("A" + (labelx+labelwidth) + "B" + (imgleft+imgwidth));
    if(labelx+labelwidth>imgleft+imgwidth){
        // todo
        //$("#labeldiv"+labelcount).css("left",(imgwidth-labelwidth)+"px");
        $("#labeldiv"+labelcount).css("left",(imgleft+imgwidth-labelwidth)+"px");
    }

    $("#debugtext").val((imgleft+imgwidth-labelwidth)+"px");

    var labelwidth2;// 标签宽度
    if($("#labeldivL"+labelcount).width()==0){
        labelwidth2 = 12+12+$("#labeldivM"+labelcount).width();
    }else{
        labelwidth2 = $("#labeldiv"+labelcount).width();
    }

    // 二次调整（遇到div换行的情况下需要调整）
    if (labelwidth2 > labelwidth) {
        $("#labeldiv"+labelcount).css("left",(imgleft+imgwidth-labelwidth+labelwidth-labelwidth2)+"px");
    }

    if(labely<imgtop){
        $("#labeldiv"+labelcount).css("top",imgtop+"px");
    }
    var labeldivleft = $("#labeldiv"+labelcount).offset().left;
    var labeldivtop = $("#labeldiv"+labelcount).offset().top;

    var pointx = (labeldivleft-imgleft)/imgwidth;
    var pointy = (labeldivtop+labelheight-imgtop)/imgheight;

    $("#pointx"+labelcount).val(pointx);
    $("#pointy"+labelcount).val(pointy);
    var oBox = document.getElementById("labeldiv"+labelcount);
    //alert(imgleft + " *  " + imgtop + " * " + labelwidth);
    //var lockminX = imgleft - imgleft;
    //var lockminY = imgtop - imgtop;
    var lockminX = imgleft;
    var lockminY = imgtop;
    var lockmaxX = imgleft + imgwidth-labelwidth;
    var lockmaxY = imgtop + imgheight-labelheight;
    var oDrag = new Drag(oBox, {handle:oBox, limit:false,
        lockminX:lockminX,lockminY:lockminY,lockmaxX:lockmaxX,lockmaxY:lockmaxY,
        imgwidth:imgwidth,imgheight:imgheight,imgtop:imgtop,imgleft:imgleft,
        labelheight:labelheight,labelwidth:labelwidth2});

    if (addGridFlg == 0) {
        $('#dg').datagrid('appendRow',{
            lblContent: labelcontent,
            divId: ""+labelcount
        });
    }

    //var contactslastIndex = $('#dg').datagrid('getRows').length-1;
    //$('#dg').datagrid('selectRow', contactslastIndex);
    //$('#dg').datagrid('beginEdit', contactslastIndex);

    //$("#labeldiv1").draggable()
    //    .click(function() {
    //        Drag.removeHandler($(this),"mousedown",Drag.bind($(this), this.startDrag));
    //    }).dblclick(function() {
    //        this.removeHandler(this.handle, "mousedown", this.bind(this, this.startDrag));
    //    });

}

function deletepic(wzcomppicid) {
    if (confirm("确定是否删除吗")) {
        location.href = "/DevMzcomp/admin/wzcompServlet.do?method=wzcomppicdelete&wzcomppicid=" + wzcomppicid;
    } else {

    }
}















var editDataGridParams = [];
var editDataGridEditIndexs = [];

//初始化列表
function initEditDataGrid(params)
{
    editDataGridParams[params.dataGridId] = params;
    var toolbar = [{
        iconCls:'icon-add',
        text:'新增',
        handler:function(){add(params);}
    }];
    var gridId = params.dataGridId;
    var columns = params.dataGridColumns;
    var reqUrl = params.dataGridReqUrl;
    columns[0].unshift({field:'oper',title:'操作',width:60,formatter:function(row , value ,index){
        var s ='保存 ';
        var d = '删除 ';
        return s + d;
    }});

    $("#" + gridId).datagrid({
        fit: true,
        nowrap: false,
        toolbar:toolbar,
        url:reqUrl,
        method: 'get',
        onClickRow: function(index){onClickRow(index , params);},
        striped: true,
        collapsible:true,
        loadMsg:'正在加载....',
        pagination:false,
        pageSize:100,
        rownumbers:true,
        singleSelect:true,
        columns:columns
    });

}

//日期格式化
function dateFormatter(value , row , index)
{
    if(value === undefined || value === null || value.length <= 10)
        return value;
    return value.substring(0 , 10);
}

//结束编辑状态
function endEditing(params){

    var gridId = params.dataGridId;
    var editIndex = editDataGridEditIndexs[gridId];
    if (editIndex === undefined){
        return true;
    }
    var $datagrid = $("#" + gridId);
    if ($($datagrid).datagrid('validateRow', editIndex)){
        $($datagrid).datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

//点击列表行
function onClickRow(index , params){
    //是否可以编辑过滤函数
    var editEnableFunc = params.editEnableFunc;
    if(editEnableFunc)
    {
        if(editEnableFunc.call(this, index , params) === false)
        {
            return;
        }
    }
    var gridId = params.dataGridId;
    var $datagrid = $("#" + gridId);
    var editIndex = editDataGridEditIndexs[gridId];
    if (editIndex !== index){
        if (endEditing(params)){
            $($datagrid).datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            editIndex = index;
            editDataGridEditIndexs[gridId] = editIndex;
        } else {
            $($datagrid).datagrid('selectRow', editIndex);
        }
    }
}

//新增行
function add(params){
    var gridId = params.dataGridId;
    var $datagrid = $("#" + gridId);
    if (endEditing(params)){
        $($datagrid).datagrid('insertRow',{index:0 , row:{status:'P'}});
        var editIndex = 0;//$($datagrid).datagrid('getRows').length-1;
        editDataGridEditIndexs[gridId] = editIndex;
        $($datagrid).datagrid('selectRow', editIndex)
            .datagrid('beginEdit', editIndex);
    }
}

//刷新列表
function refreshDataGrid(params)
{
    var $datagrid = $("#" + params.dataGridId);
    $($datagrid).datagrid("clearSelections");
    $($datagrid).datagrid("load" , {});
}

//删除列表行及数据库数据
function del(gridId , index , id)
{
    var params = editDataGridParams[gridId];
    var deleteUrl = params.deleteUrl;
    var $datagrid = $("#" + gridId);
    if(id === 'undefined' || id === null || id === '')
    {
        $($datagrid).datagrid("deleteRow" ,index);
        editDataGridEditIndexs[gridId] = undefined;
        return ;
    }
    $.ajax({
        url:deleteUrl,
        data:{ids:id},
        type: 'post',
        dataType: 'json',
        success: function(data) {
            refreshDataGrid(params);
            editDataGridEditIndexs[gridId] = undefined;
            $.messager.alert("提示" , "删除成功" , "info");
        },
        error:function(data){
            $.messager.alert("提示" , "删除失败" , "error");
        }
    });
}

//保存列表行
function save(gridId , index)
{
    var params = editDataGridParams[gridId];
    var saveUrl = params.saveUrl;
    var $datagrid = $("#" + gridId);
    if($($datagrid).datagrid('validateRow', index) === false)
    {
        return ;
    }
    $($datagrid).datagrid('endEdit', index);
    var row = $($datagrid).datagrid("getRows")[index];
    var beforeSaveFilter = params.beforeSaveFilter;
    if(beforeSaveFilter)
    {
        beforeSaveFilter.call(this , row);
    }
    $.ajax({
        url:saveUrl,
        data:row,
        type: 'post',
        dataType: 'json',
        success: function(data) {
            $.messager.alert("提示" , "保存成功" , "info");
            refreshDataGrid(params);
            editDataGridEditIndexs[gridId] = undefined;
        },
        error:function(data){
            $.messager.alert("提示" , "保存失败" , "error");
        }
    });
}
