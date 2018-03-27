// 图形处理代码
var good="";
var zoom=1;
// 变量创建
var jcrop_api, boundx, boundy;
function bytesToSize(bytes) {
    var sizes = ['Bytes', 'KB', 'MB'];
    if (bytes == 0) return 'n/a';
    var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
    return (bytes / Math.pow(1024, i)).toFixed(1) + ' ' + sizes[i];
};

// check for selected crop region
function checkForm() {
    $('#rew').val($('.jcrop-holder').width());
    $('#reh').val($('.jcrop-holder').height());
    if (parseInt($('#w').val())) return true;
    $('.error').html('请在图片上选择一个区域进行处理').show();
    return false;
};

// update info by cropping (onChange and onSelect events handler)
function updateInfo(e) {
    $('#x1').val(e.x);
    $('#y1').val(e.y);
    $('#x2').val(e.x2);
    $('#y2').val(e.y2);
    $('#w').val(e.w);
    $('#h').val(e.h);
    $('#shw').val(Math.ceil(e.w * zoom));//显示用
    $('#shh').val(Math.ceil(e.h * zoom));//显示用
    $('#rew').val($('.jcrop-holder').width());
    $('#reh').val($('.jcrop-holder').height());
};

// 清除信息
function clearInfo() {
    $('.info #w').val('');
    $('.info #h').val('');
};

function fileSelectHandler(add) {
    if (add == "1"){
        $("#step222").attr('class', "step2");
        $("#preview").attr('class', "imgstyle_add");
    } else if (add == "2") {
        $("#step222").attr('class', "step2");
        $("#preview").attr('class', "imgstyle_wide");
    } else if (add == "face") {
        $("#step222").attr('class', "step2");
        $("#preview").attr('class', "imgstyle_face");
    } else {
        $("#preview").attr('class', "imgstyle");
    }
    // 获取选择文件
    var oFile = $('#image_file')[0].files[0];

    if (good =="") {
        good = $(".step2").html();
    }
    $(".step2").empty();
    $(".step2").html(good);

    // 隐藏错误
    $('.error').hide();

    // 检查文件格式
    var rFilter = /^(image\/jpeg|image\/png)$/i;
    if (! rFilter.test(oFile.type)) {
        $('.error').html('请选择有效的图形文件 (jpg 或 png 格式)').show();
        return;
    }

    // 检查文件大小
    if (oFile.size > 7000 * 1024) {
        $('.error').html('文件过大，请选择稍小的文件').show();
        return;
    }

    // 预览元素
    var oImage = document.getElementById('preview');

    // HTML5文件读取
    var oReader = new FileReader();
        oReader.onload = function(e) {

        oImage.src = e.target.result;
        oImage.onload = function () { // 加载控制

            $('.step2').fadeIn(500);

            // 显示图片基础信息
            var sResultFileSize = bytesToSize(oFile.size);
            $('#filesize').val(sResultFileSize);
            $('#filetype').val(oFile.type);
            $('#filedim').val(oImage.naturalWidth + ' x ' + oImage.naturalHeight);
            $('#oriw').val(oImage.naturalWidth);
            $('#orih').val(oImage.naturalHeight);

            // 销毁存在的api
            if (typeof jcrop_api != 'undefined') {
                jcrop_api.destroy();
                jcrop_api = null;
            }

            setTimeout(function(){
                // 初始化 Jcrop
                $('#preview').Jcrop({
                    minSize: [290, 290], // 最小拉取框
                    aspectRatio : 1, // keep aspect ratio 1:1
                    bgFade: true, // use fade effect
                    bgOpacity: .3, // fade opacity
                    onChange: updateInfo,
                    onSelect: updateInfo,
                    onRelease: clearInfo
                }, function(){

                    // 获取图片实际大小
                    var bounds = this.getBounds();
                    boundx = bounds[0];
                    boundy = bounds[1];

                    // 自动获取选择框
                    jcrop_api = this;

                    zoom = oImage.naturalWidth / $('.jcrop-holder').width();

                    var xxx;
                    var yyy;

                    // 应用于宽屏封面
                    var ratio = 1;
                    if (add == "2") {
                        ratio = 1182/566;

                        if (boundx/boundy > 1182/566) {
                            yyy = boundy;
                            xxx = parseInt(boundy*1182/566);
                            zoom = oImage.naturalHeight / $('.jcrop-holder').height();
                        } else {
                            xxx = boundx;
                            yyy = parseInt(boundx*566/1182);
                            zoom = oImage.naturalWidth / $('.jcrop-holder').width();
                        }

                        if (parseInt(xxx*zoom) > 1182) {
                            xxx = parseInt(1182/zoom);
                            yyy = parseInt(566/zoom);
                        }
                    } else if (add == "face"){
                        if (boundx < boundy) {
                            xxx = yyy = boundx;
                        } else {
                            xxx = yyy = boundy;
                        }

                        if (parseInt(xxx*zoom) > 240) {
                            xxx = yyy = parseInt(240/zoom);
                        }
                    } else {
                        if (boundx < boundy) {
                            xxx = yyy = boundx;
                        } else {
                            xxx = yyy = boundy;
                        }

                        if (parseInt(xxx*zoom) > 1242) {
                            xxx = yyy = parseInt(1242/zoom);
                        }
                    }

                    jcrop_api.setOptions({
                        minSize: [xxx, yyy], // 最小拉取框
                        aspectRatio : ratio, // keep aspect ratio 1:1
                        bgFade: true, // use fade effect
                        bgOpacity: .3, // fade opacity
                        bgColor: 'transparent',
                        onChange: updateInfo,
                        onSelect: updateInfo,
                        onRelease: clearInfo
                    });

                    var duang = 0;
                    var duangh = 0;
                    var duangx1 = 0;
                    var duangy1 = 0;
                    var duangx2 = 0;
                    var duangy2 = 0;
                    if (add == "2") {
                        if (boundx/boundy > 1182/566) {
                            duangh = $('.jcrop-holder').height();
                            duang = parseInt(duangh*1182/566);
                        } else {
                            duang = $('.jcrop-holder').width();
                            duangh = parseInt(duang*566/1182);
                        }
                        duangx1 = parseInt(($('.jcrop-holder').width() - duang) / 2);
                        duangy1 = parseInt(($('.jcrop-holder').height() - duangh) / 2);

                        duangx2 = duangx1 + duang;
                        duangy2 = duangy2 + duangh;
                    } else {
                        if ($('.jcrop-holder').width() > $('.jcrop-holder').height()) {
                            duang = $('.jcrop-holder').height();
                        } else {
                            duang = $('.jcrop-holder').width();
                        }
                        duangx1 = parseInt(($('.jcrop-holder').width() - duang) / 2);
                        duangy1 = parseInt(($('.jcrop-holder').height() - duang) / 2);

                        duangx2 = duangx1 + duang;
                        duangy2 = duangy2 + duang;
                    }

                    if ($("#preview").attr('class')=="imgstyle_add loaded"
                        || $("#preview").attr('class')=="imgstyle_wide loaded") {
                        jcrop_api.disable();
                    } else {
                        jcrop_api.animateTo([duangx1, duangy1, duangx2+1, duangy2+1 ]);
                    }
                });


            },500);

        };
    };

    // 选择文件数据读取
    oReader.readAsDataURL(oFile);
}