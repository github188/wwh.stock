$.extend($.fn.validatebox.defaults.rules, {
    /*必须和某个字段相等*/
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    minLength: {
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length: {
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "输入内容长度必须介于{0}和{1}之间."
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    intOrFloatRange: {// 验证整数或小数
        validator: function (value, param) {
            var flg = /^\d+(\.\d+)?$/i.test(value);
            if (flg) {
                return value > param[0] && value <= param[1];
                ;
            }
            return flg;
        },
        message: '请输入数字，并输入值在{0}和{1}之间.'
    },
    intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    integer: {// 验证整数
        validator: function (value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: '请输入正整数'
    },
    zs: {// 验证整数
        validator: function (value) {
            return /^[+-]?[0-9]+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    age: {// 验证年龄
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    },

    chinese: {// 验证中文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    username: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno: {// 验证传真
        validator: function (value) {
//            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value); 
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    date: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            //格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
        },
        message: '清输入合适的日期格式'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致！'
    },
    equalTo: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        },
        message: '字段不匹配'
    },
    selectValueRequired: {
        validator: function (value, param) {
            //console.info($(param[0]).find("option:contains('" + value + "')").val());
            return $(param[0]).find("option:contains('" + value + "')").val() != '';
        },
        message: '该输入项为必输项'
    },
    validPassword: {
        validator: function (value) {
            return /^[\u4e00-\u9fa5a\w]{4,12}$/.test(value);
        },
        message: '密码长度必须在4~12位之间！'
    }

});


/**
 * @author {CaoGuangHui}
 */
$.extend($.fn.tabs.methods, {
    /**
     * 加载iframe内容
     * @param  {jq Object} jq     [description]
     * @param  {Object} params    params.which:tab的标题或者index;params.iframe:iframe的相关参数
     * @return {jq Object}        [description]
     */
    loadTabIframe: function (jq, params) {
        return jq.each(function () {
            var $tab = $(this).tabs('getTab', params.which);
            if ($tab == null) return;

            //var processmsg = 'Processing, please wait ...';
            var processmsg = "";
            var $tabBody = $tab.panel('body');

            //销毁已有的iframe
            var $frame = $('iframe', $tabBody);
            if ($frame.length > 0) {
                try {//跨域会拒绝访问，这里处理掉该异常
                    $frame[0].contentWindow.document.write('');
                    $frame[0].contentWindow.close();
                } catch (e) {
                    //Do nothing
                }
                $frame.remove();
                if ($.browser.msie) {
                    CollectGarbage();
                }
            }
            $tabBody.html('');

            $tabBody.css({'overflow': 'hidden', 'position': 'relative'});
            var $mask = $('<div style="position:absolute;z-index:2;width:100%;height:100%;background:#f4f5ff;z-index:1000;opacity:0.3;filter:alpha(opacity=30);"><div>').appendTo($tabBody);
            var $maskMessage = $('<div class="mask-message" style="z-index:3;width:130px;height:16px;line-height:16px;position:absolute;top:50%;left:50%;margin-top:-20px;margin-left:-92px;border:0px solid #d4d4d4;padding: 12px 5px 10px 30px;background: #ffffff url(\'../images/ajax-loader.gif\') center no-repeat">' + (params.iframe.message || processmsg) + '</div>').appendTo($tabBody);
            var $containterMask = $('<div style="position:absolute;width:100%;height:100%;z-index:1;background:#fff;"></div>').appendTo($tabBody);
            var $containter = $('<div style="position:absolute;width:100%;height:100%;z-index:0;"></div>').appendTo($tabBody);

            var iframe = document.createElement("iframe");
            iframe.id = "infoframe";
            iframe.src = params.iframe.src;
            iframe.frameBorder = params.iframe.frameBorder || 0;
            iframe.height = params.iframe.height || '100%';
            iframe.width = params.iframe.width || '100%';
            if (iframe.attachEvent) {
                iframe.attachEvent("onload", function () {
                    $([$mask[0], $maskMessage[0]]).fadeOut(params.iframe.delay || 'fast', function () {
                        $(this).remove();
                        if ($(this).hasClass('mask-message')) {
                            $containterMask.fadeOut(params.iframe.delay || 'fast', function () {
                                $(this).remove();
                            });
                        }
                    });
                });
            } else {
                iframe.onload = function () {
                    $([$mask[0], $maskMessage[0]]).fadeOut(params.iframe.delay || 'fast', function () {
                        $(this).remove();
                        $.dream.closetip();
                        if ($(this).hasClass('mask-message')) {
                            $containterMask.fadeOut(params.iframe.delay || 'fast', function () {
                                $(this).remove();
                            });
                        }
                    });
                };
            }
            $containter[0].appendChild(iframe);
        });
    },
    /**
     * 增加iframe模式的标签页
     * @param {[type]} jq     [description]
     * @param {[type]} params [description]
     */
    addIframeTab: function (jq, params) {
        return jq.each(function () {
            if (params.tab.href) {
                delete params.tab.href;
            }
            $(this).tabs('add', params.tab);
            $(this).tabs('loadTabIframe', {'which': params.tab.title, 'iframe': params.iframe});
        });
    },
    /**
     * 更新tab的iframe内容
     * @param  {jq Object} jq     [description]
     * @param  {Object} params [description]
     * @return {jq Object}        [description]
     */
    updateIframeTab: function (jq, params) {
        return jq.each(function () {
            params.iframe = params.iframe || {};
            if (!params.iframe.src) {
                var $tab = $(this).tabs('getTab', params.which);
                if ($tab == null) return;
                var $tabBody = $tab.panel('body');
                var $iframe = $tabBody.find('iframe');
                if ($iframe.length === 0) return;
                $.extend(params.iframe, {'src': $iframe.attr('src')});
            }
            $(this).tabs('loadTabIframe', params);
        });
    }
});

/**
 * Created with JetBrains WebStorm.
 * User: shijun
 * Date: 15-7-29
 * To change this template use File | Settings | File Templates.
 */
$.extend($.fn.datagrid.methods, {
    /**
     * 开打提示功能（基于1.3.3+版本）
     * @param {} jq
     * @param {} params 提示消息框的样式
     * @return {}
     */
    doCellTip:function (jq, params) {
        function showTip(showParams, td, e, dg) {
            //无文本，不提示。
            if ($(td).text() == "") return;
            params = params || {};
            var options = dg.data('datagrid');
            var styler = 'style="';
            if(showParams.width){
                styler = styler + "width:" + showParams.width + ";";
            }
            if(showParams.maxWidth){
                styler = styler + "max-width:" + showParams.maxWidth + ";";
            }
            if(showParams.minWidth){
                styler = styler + "min-width:" + showParams.minWidth + ";";
            }
            styler = styler + '"';
            showParams.content = '<div class="tipcontent" ' + styler + '>' + showParams.content + '</div>';
            $(td).tooltip({
                content:showParams.content,
                trackMouse:true,
                position:params.position,
                onHide:function () {
                    $(this).tooltip('destroy');
                },
                onShow:function () {
                    var tip = $(this).tooltip('tip');
                    if(showParams.tipStyler){
                        tip.css(showParams.tipStyler);
                    }
                    if(showParams.contentStyler){
                        tip.find('div.tipcontent').css(showParams.contentStyler);
                    }
                }
            }).tooltip('show');
        };
        return jq.each(function () {
            var grid = $(this);
            var options = $(this).data('datagrid');
            if (!options.tooltip) {
                var panel = grid.datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').each(function () {
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field]', {
                        'mouseover':function (e) {
                            //if($(this).attr('field')===undefined) return;
                            var that = this;
                            var setField = null;
                            if(params.specialShowFields && params.specialShowFields.sort){
                                for(var i=0; i<params.specialShowFields.length; i++){
                                    if(params.specialShowFields[i].field == $(this).attr('field')){
                                        setField = params.specialShowFields[i];
                                    }
                                }
                            }
                            if(setField==null){
                                options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');
                                var factContentWidth = options.factContent.width();
                                params.content = $(this).text();
                                if (params.onlyShowInterrupt) {
                                    if (factContentWidth > $(this).width()) {
                                        showTip(params, this, e, grid);
                                    }
                                } else {
                                    showTip(params, this, e, grid);
                                }
                            }else{
                                panel.find('.datagrid-body').each(function(){
                                    var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');
                                    trs.each(function(){
                                        var td = $(this).find('> td[field="' + setField.showField + '"]');
                                        if(td.length){
                                            params.content = td.text();
                                        }
                                    });
                                });
                                showTip(params, this, e, grid);
                            }
                        },
                        'mouseout':function (e) {
                            if (options.factContent) {
                                options.factContent.remove();
                                options.factContent = null;
                            }
                        }
                    });
                });
            }
        });
    },
    /**
     * 关闭消息提示功能（基于1.3.3版本）
     * @param {} jq
     * @return {}
     */
    cancelCellTip:function (jq) {
        return jq.each(function () {
            var data = $(this).data('datagrid');
            if (data.factContent) {
                data.factContent.remove();
                data.factContent = null;
            }
            var panel = $(this).datagrid('getPanel').panel('panel');
            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
        });
    }
});


$.extend($.fn.combotree.methods, {
    /***使用范围，全部加载数据即非异步加载*功能说明:我们用原始的setValue的方法时，如果选择的元素在第3级的话不会进行展开，所以写了此扩展方法*/
    setExpandValue: function (jq, v) {
        var o = jq[0];
        var cbt = $(o).combotree('tree');
        var node = cbt.tree('find', v);

        // 叶子节点的话进行赋值
        var isLeaf = cbt.tree('isLeaf', node.target);
        if (isLeaf) {
            $(o).combotree('setValue', v);
        } else {
            // 非叶子节点的话，就展开一层
            var cnode = cbt.tree('getChildren', node.target);
            if (cnode[0]) {
                cbt.tree('expandTo', cnode[0].target);
            }
        }

        if (node) {
            cbt.tree('expandTo', node.target);
        }
    },
    setExpandValues: function (jq, v) {
        var o = jq[0];
        var cbt = $(o).combotree('tree');

        for(var i=0;i<v.length;i++){
            var node = cbt.tree('find', v[i]);
            if (node) {
                cbt.tree('expandTo', node.target);
            }
        }

        $(o).combotree('setValues', v);
    }
});

