/**
 * Created by Administrator on 2016/5/26.
 */
//上传
$(function(){
    $("#upload").KKUploader({
            accept: {},
            duplicate:true,
            uploadEvents: {
                uploadComplete:function(file){
                    var path =$("#"+file.id).find('.webuploader-pick-file-close').attr('data-fileurl');
                    var name =file.name;
                    var size = file.size;
                    $("#"+file.id).text("");
                    $.ajax({
                        type: 'POST',
                        url: base_url+'/job/file/savefile',
                        async:false,
                        data: {'path': path,
                            'name':name,
                            'size':size,
                            'folderid':folderid,
                            'type':type},
                            success:function(data){
                            if (data.success) {
                                jQuery.messager.show({    // show error message
                                    title: '提示',
                                    msg: '上传成功'
                                });
                            } else {
                                jQuery.messager.show({    // show error message
                                    title: '错误',
                                    msg: data.message
                                });
                            }
                        }
                    });
                    jQuery('#dg').datagrid('reload');    // reload the user data
                }},
            separator:',',			//多文件上传时，指定分隔符，如不指定，默认为","
            fileSingleSizeLimit:100*1024*1024			//上传大小限制
        },
        {
            filename : 'file',//传递给服务端的参数指定上传文本域的name，如果未指定，上传不成功
        });
})