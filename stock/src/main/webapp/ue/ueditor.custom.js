//单传图片开始上传,显示等待。
function preUploadSingleImg() {
    if ($("#loading").length > 0) {
        $("#loading").html("<img style='padding: 5px' src='../../ue/themes/default/images/loading.gif'>");
    }

}

//单传图片回调,然后清理内容,在清理内容会自动调用contentChange事件，然后再获取上传文件的文件名。
function uploadSingleImgCallback() {
    UE.getEditor('editor-img').execCommand('cleardoc');
    if ($("#loading").length > 0) {
        $("#loading").empty();
    }
}

//单图片上传
function uploadImg() {
    $($("#editor-img").find("iframe")[0].contentWindow.document).find("input")[0].click();
}

//多图片上传
function uploadImgs() {
    $("#editor-img .edui-for-insertimage .edui-button-body")[0].click();
}

//通过UE文件上传
var single_img_upload;
$(function() {
    //重新实例化一个编辑器，防止在上面的editor编辑器中显示上传的图片或者文件
    single_img_upload = UE.getEditor('editor-img');//参数网站专用，其他页面切勿模仿
    single_img_upload.ready(function () {
        //设置编辑器不可用[ueditor 1.4.X之后不再使用]
        //single_img_upload.setDisabled();
        //隐藏编辑器，因为不会用到这个编辑器实例，所以要隐藏
        single_img_upload.hide();
        //侦听图片上传
        single_img_upload.addListener('beforeinsertimage', function (t, arg) {
            var pictures;
            UE.utils.each(arg, function(value,key){
                pictures = value.src;
            });
            //将地址赋值给相应的input,只去第一张图片的路径
            jQuery("#picture").val(pictures);
            jQuery("#preview").attr({"src":pictures});
            //图片预览
            jQuery("#preview").show();
        });


        //侦听文件上传，取上传文件列表中第一个上传的文件的路径
        single_img_upload.addListener('afterUpfile', function (t, arg) {
            $("#file").attr("value", single_img_upload.options.filePath + arg[0].url);
        });

        single_img_upload.addListener('contentChange', function (editor) {
            //获取编辑器中的内容（html 代码）
            var img = single_img_upload.getPlainTxt();
            if (trim(img) != "") {
                //判断是否是单图片上传，如果是单传不做任何处理，等待回调函数再次调用。
                if (img.indexOf("loadingclass") == -1) {
                    //把编辑器中的内容放到临时div中，然后进行获取文件名称。
                    $("#temp-img-list").html(img);
                    var pic = $("#temp-img-list img").attr("src");
                    //将地址赋值给相应的input,只去第一张图片的路径
                    jQuery("#titleImg").textbox('setValue',pic);
                    jQuery("#preview").attr({"src":pic});
                    //图片预览
                    jQuery("#preview").show();

                    var array = new Array();
                    //循环获取文件名称
                    $("#temp-img-list img").each(function () {
                        var src = $(this).attr("src");
                        var name = src.replace("/upload/image/", "");
                        array.push(name);
                    });
                    //清空编辑器中的内容，以便下一次添加图片。
                    single_img_upload.execCommand('cleardoc');
                    //调用callbackImg获取懂图片名称
                    if (typeof callbackImg === "function") {
                        eval("callbackImg('" + array + "')");
                    }
                }
            }
        });
    });
});