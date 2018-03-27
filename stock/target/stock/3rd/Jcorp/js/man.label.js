var firstload = 1;
var globalx,globaly;
var labelcount = 0;
// 定义标签高度
var labelheight = 25;

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

var path = Man.context + "/3rd/Jcorp";
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

function showhiddenMan(showflag) {
    var labelObj = $("#labels");
    if (showflag==undefined) {
        labelObj.css("visibility", "");
    } else {
        if (labelObj.css("visibility") == 'hidden') {
            labelObj.css("visibility", "");
        } else {
            labelObj.css("visibility", "hidden");
        }
    }
}

function showLabel(obj, ptX, ptY, lblContent, positionflag, candrag) {
    var labelcount = parseInt($("#img1count").val()) + 1;
    $("#img1count").val(labelcount);

    var labeldiv = "";
    var picPos;
    if (positionflag=="1") {
        picPos = "2";
    } else {
        picPos = "1";
    }

    // 标签内容
    var labelcontent = lblContent;
    var labelObj = $("#labels");
    labelObj.css("visibility", "");
    // 图片对象
    var jObj = $("#"+obj);

    var imgtop = jObj.offset().top;     // 偏移Y
    var imgleft = jObj.offset().left;   // 偏移X
    var imgwidth = jObj.width();
    var imgheight = jObj.height();

    var pointx = ptX;
    var pointy = ptY;
    var labelx = imgleft + imgwidth * pointx;
    var labely = imgtop + imgheight * pointy - labelheight;

    if (1 == 1) {
        labeldiv += "<div class='move' id='labeldiv"+labelcount+"' style='position:absolute; left:"+labelx+"px; top:"+labely+"px;'>";
        labeldiv += "<div style='float:left;height:"+labelheight+"px;' id='labeldivL"+labelcount+"'><img src='"+path+"/images/left"+picPos+".png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<div contenteditable='false' style='font-size:14px;color:#FFF;float:left;background-image:url("+path+"/images/line.png); height:"+labelheight+"px; line-height:"+labelheight+"px;' id='labeldivM"+labelcount+"'>"+labelcontent+"</div>";
        labeldiv += "<div style='float:left;height:"+labelheight+"px;' id='labeldivR"+labelcount+"'><img src='"+path+"/images/right"+picPos+".png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<input type='hidden' type='text' id='flag"+labelcount+"' name='flag' value='edit'/>";
        labeldiv += "<input type='hidden' type='text' id='labelcontent"+labelcount+"' name='labelcontent' value='"+labelcontent+"'/>";
        labeldiv += "<input type='hidden' type='text' id='longflag"+labelcount+"' name='longflag' value='0'/>";
        labeldiv += "<input type='hidden' type='text' id='pointx"+labelcount+"' value='"+ptX+"' name='pointx'/>";
        labeldiv += "<input type='hidden' type='text' id='pointy"+labelcount+"' value='"+ptY+"' name='pointy'/>";
        labeldiv += "<input type='hidden' type='text' id='positionflag"+labelcount+"' name='positionflag' value='"+positionflag+"'/>";
        labeldiv += "</div>";
    }

    labelObj.append(labeldiv);

    if (candrag=="1") {
        var oBox = $("#labeldiv" + labelcount);
        new ManDrag(oBox, {handle: oBox, lblindex: labelcount, defx:ptX, defy:ptY});
    }
}
function labels2(obj, ptX, ptY, lblContent, addGridFlg) {
    // 标签序号
    var labelcount = parseInt($("#img1count").val()) + 1;
    $("#img1count").val(labelcount);
    // 定义标签高度

    var labeldiv = "";

    // 标签内容
    var labelcontent = lblContent;

    // 图片对象
    var jObj = $("#"+obj);

    var imgtop = jObj.offset().top;     // 偏移Y
    var imgleft = jObj.offset().left;   // 偏移X
    var imgwidth = jObj.width();
    var imgheight = jObj.height();

    var labeldivleft = ptX;
    var labeldivtop = ptY;

    var pointx = (labeldivleft-imgleft)/imgwidth;
    var pointy = (labeldivtop+labelheight-imgtop)/imgheight;

    var labelx = imgleft + imgwidth * pointx;
    var labely = imgtop + imgheight * pointy - labelheight;

    if (1 == 1) {
        labeldiv += "<div class='move' id='labeldiv"+labelcount+"' style='position:absolute; left:"+labelx+"px; top:"+labely+"px;'>";
        labeldiv += "<div style='float:left;height:"+labelheight+"px;' id='labeldivL"+labelcount+"'><img src='"+path+"/images/left2.png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<div contenteditable='false' style='font-size:14px;color:#FFF;float:left;background-image:url("+path+"/images/line.png); height:"+labelheight+"px; line-height:"+labelheight+"px;' id='labeldivM"+labelcount+"'>"+labelcontent+"</div>";
        labeldiv += "<div style='float:left;height:"+labelheight+"px;' id='labeldivR"+labelcount+"'><img src='"+path+"/images/right2.png' style='height:"+labelheight+"px;'/></div>";
        labeldiv += "<input type='hidden' type='text' id='flag"+labelcount+"' name='flag' value='add'/>";
        labeldiv += "<input type='hidden' type='text' id='labelcontent"+labelcount+"' name='labelcontent' value='"+labelcontent+"'/>";
        labeldiv += "<input type='hidden' type='text' id='longflag"+labelcount+"' name='longflag' value='0'/>";
        labeldiv += "<input type='hidden' type='text' id='pointx"+labelcount+"' name='pointx'/>";
        labeldiv += "<input type='hidden' type='text' id='pointy"+labelcount+"' name='pointy'/>";
        labeldiv += "<input type='hidden' type='text' id='positionflag"+labelcount+"' name='positionflag' value='1'/>";
        labeldiv += "</div>";
    } else {
        labeldiv += "<div id='labeldiv" + labelcount + "' style='position:absolute; left:" + labelx + "px; top:" + labely + "px;'>";
        labeldiv += "<div style='float:left' id='labeldivL" + labelcount + "'><img src='./img/left1.png' style='height:" + labelheight + "px;'/></div>";
        labeldiv += "<div style='color:#FFF;float:left;background-image:url(./img/line.png); height:" + labelheight + "px; line-height:" + labelheight + "px;' id='labeldivM" + labelcount + "'>" + labelcontent + "</div>";
        labeldiv += "<div style='float:left' id='labeldivR" + labelcount + "'><img src='./img/right1.png' style='height:" + labelheight + "px;'/></div>";
        labeldiv += "</div>";
    }
    $("#labels").append(labeldiv);

    var oBox = $("#labeldiv"+labelcount);
    new ManDrag(oBox, {handle: oBox, lblindex: labelcount, defx:ptX, defy:ptY, doxy:1});

    if (addGridFlg == 0) {
        $('#dg').datagrid('appendRow',{
            lblContent: labelcontent,
            divId: ""+labelcount
        });
    }

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
