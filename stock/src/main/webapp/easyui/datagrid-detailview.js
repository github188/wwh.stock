var detailview = $.extend({}, $.fn.treegrid.defaults.view, {
	render: function(target, container, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		if (frozen){
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
		}
		
		var rows = state.data.rows;
		var fields = $(target).datagrid('getColumnFields', frozen);
		var table = [];
		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		for(var i=0; i<rows.length; i++) {
			// get the class and style attributes for this row
			var css = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			
			var cls = 'class="datagrid-row ' + (i % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + i;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + i + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
			table.push('</tr>');
			
			table.push('<tr style="display:none;">');
			if (frozen){
				table.push('<td colspan=' + (fields.length+2) + ' style="border-right:0">');
			} else {
				table.push('<td colspan=' + (fields.length) + '>');
			}
			table.push('<div class="datagrid-row-detail">');
			if (frozen){
				table.push('&nbsp;');
			} else {
				table.push(opts.detailFormatter.call(target, i, rows[i]));
			}
			table.push('</div>');
			table.push('</td>');
			table.push('</tr>');
			
		}
		table.push('</tbody></table>');
		
		$(container).html(table.join(''));
	},
	
	renderRow: function(target, fields, frozen, rowIndex, rowData){
		var opts = $.data(target, 'datagrid').options;
		
		var cc = [];
		if (frozen && opts.rownumbers){
			var rownumber = rowIndex + 1;
			if (opts.pagination){
				rownumber += (opts.pageNumber-1)*opts.pageSize;
			}
			cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'+rownumber+'</div></td>');
		}
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $(target).datagrid('getColumnOption', field);
			if (col){
				var value = rowData[field];	// the field value
				var css = col.styler ? (col.styler(value, rowData, rowIndex)||'') : '';
				var classValue = '';
				var styleValue = '';
				if (typeof css == 'string'){
					styleValue = css;
				} else if (cc){
					classValue = css['class'] || '';
					styleValue = css['style'] || '';
				}
				var cls = classValue ? 'class="' + classValue + '"' : '';
				var style = col.hidden ? 'style="display:none;' + styleValue + '"' : (styleValue ? 'style="' + styleValue + '"' : '');
				
				cc.push('<td field="' + field + '" ' + cls + ' ' + style + '>');
				
				if (col.checkbox){
					style = '';
				} else if (col.expander){
					style = "text-align:center;height:16px;";
				} else {
					style = styleValue;
					if (col.align){style += ';text-align:' + col.align + ';'}
					if (!opts.nowrap){
						style += ';white-space:normal;height:auto;';
					} else if (opts.autoRowHeight){
						style += ';height:auto;';
					}
				}
				
				cc.push('<div style="' + style + '" ');
				if (col.checkbox){
					cc.push('class="datagrid-cell-check ');
				} else {
					cc.push('class="datagrid-cell ' + col.cellClass);
				}
				cc.push('">');
				
				if (col.checkbox){
					cc.push('<input type="checkbox" name="' + field + '" value="' + (value!=undefined ? value : '') + '">');
				} else if (col.expander) {
					//cc.push('<div style="text-align:center;width:16px;height:16px;">');
					cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />');
					//cc.push('</div>');
				} else if (col.formatter){
					cc.push(col.formatter(value, rowData, rowIndex));
				} else {
					cc.push(value);
				}
				
				cc.push('</div>');
				cc.push('</td>');
			}
		}
		return cc.join('');
	},
	
	insertRow: function(target, index, row){
		var opts = $.data(target, 'datagrid').options;
		var dc = $.data(target, 'datagrid').dc;
		var panel = $(target).datagrid('getPanel');
		var view1 = dc.view1;
		var view2 = dc.view2;
		
		var isAppend = false;
		var rowLength = $(target).datagrid('getRows').length;
		if (rowLength == 0){
			$(target).datagrid('loadData',{total:1,rows:[row]});
			return;
		}
		
		if (index == undefined || index == null || index >= rowLength) {
			index = rowLength;
			isAppend = true;
			this.canUpdateDetail = false;
		}
		
		$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);
		
		_insert(true);
		_insert(false);
		
		this.canUpdateDetail = true;
		
		function _insert(frozen){
			var v = frozen ? view1 : view2;
			var tr = v.find('tr[datagrid-row-index='+index+']');
			
			if (isAppend){
				var newDetail = tr.next().clone();
				tr.insertAfter(tr.next());
			} else {
				var newDetail = tr.next().next().clone();
			}
			newDetail.insertAfter(tr);
			newDetail.hide();
			if (!frozen){
				newDetail.find('div.datagrid-row-detail').html(opts.detailFormatter.call(target, index, row));
			}
		}
	},
	
	deleteRow: function(target, index){
		var opts = $.data(target, 'datagrid').options;
		var dc = $.data(target, 'datagrid').dc;
		var tr = opts.finder.getTr(target, index);
		tr.next().remove();
		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);
		dc.body2.triggerHandler('scroll');
	},
	
	updateRow: function(target, rowIndex, row){
		var dc = $.data(target, 'datagrid').dc;
		var opts = $.data(target, 'datagrid').options;
		var cls = $(target).datagrid('getExpander', rowIndex).attr('class');
		$.fn.datagrid.defaults.view.updateRow.call(this, target, rowIndex, row);
		$(target).datagrid('getExpander', rowIndex).attr('class',cls);
		
		// update the detail content
		if (this.canUpdateDetail){
			var row = $(target).datagrid('getRows')[rowIndex];
			var detail = $(target).datagrid('getRowDetail', rowIndex);
			detail.html(opts.detailFormatter.call(target, rowIndex, row));
		}
	},
	
	bindEvents: function(target){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var opts = state.options;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var tr = tt.closest('tr.datagrid-row');
			if (!tr.length){return}
			if (tt.hasClass('datagrid-row-expander')){
				var rowIndex = parseInt(tr.attr('datagrid-row-index'));
				if (tt.hasClass('datagrid-row-expand')){
					$(target).datagrid('expandRow', rowIndex);
				} else {
					$(target).datagrid('collapseRow', rowIndex);
				}
				$(target).datagrid('fixRowHeight');
				
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},
	
	onBeforeRender: function(target){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var t = $(target);
		var hasExpander = false;
		var fields = t.datagrid('getColumnFields',true).concat(t.datagrid('getColumnFields'));
		for(var i=0; i<fields.length; i++){
			var col = t.datagrid('getColumnOption', fields[i]);
			if (col.expander){
				hasExpander = true;
				break;
			}
		}
		if (!hasExpander){
			if (opts.frozenColumns && opts.frozenColumns.length){
				opts.frozenColumns[0].splice(0,0,{field:'_expander',expander:true,width:24,resizable:false,fixed:true});
			} else {
				opts.frozenColumns = [[{field:'_expander',expander:true,width:24,resizable:false,fixed:true}]];
			}
			
			var t = dc.view1.children('div.datagrid-header').find('table');
			var td = $('<td rowspan="'+opts.frozenColumns.length+'"><div class="datagrid-header-expander" style="width:24px;"></div></td>');
			if ($('tr',t).length == 0){
				td.wrap('<tr></tr>').parent().appendTo($('tbody',t));
			} else if (opts.rownumbers){
				td.insertAfter(t.find('td:has(div.datagrid-header-rownumber)'));
			} else {
				td.prependTo(t.find('tr:first'));
			}
		}
		
		var that = this;
		setTimeout(function(){
			that.bindEvents(target);
		},0);
	},
	
	onAfterRender: function(target){
		var that = this;
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var opts = state.options;
		var panel = $(target).datagrid('getPanel');
		
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
		
		if (!state.onResizeColumn){
			state.onResizeColumn = opts.onResizeColumn;
		}
		if (!state.onResize){
			state.onResize = opts.onResize;
		}
		function setBodyTableWidth(){
			var columnWidths = dc.view2.children('div.datagrid-header').find('table').width();
			dc.body2.children('table').width(columnWidths);
		}
		
		opts.onResizeColumn = function(field, width){
			setBodyTableWidth();
			var rowCount = $(target).datagrid('getRows').length;
			for(var i=0; i<rowCount; i++){
				$(target).datagrid('fixDetailRowHeight', i);
			}
			
			// call the old event code
			state.onResizeColumn.call(target, field, width);
		};
		opts.onResize = function(width, height){
			setBodyTableWidth();
			state.onResize.call(panel, width, height);
		};
		
		this.canUpdateDetail = true;	// define if to update the detail content when 'updateRow' method is called;
		
		dc.footer1.find('span.datagrid-row-expander').css('visibility', 'hidden');
		$(target).datagrid('resize');
	}
});

$.extend($.fn.datagrid.methods, {
	fixDetailRowHeight: function(jq, index){
		return jq.each(function(){
			var opts = $.data(this, 'datagrid').options;
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
			var dc = $.data(this, 'datagrid').dc;
			var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
			var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
			// fix the detail row height
			if (tr2.is(':visible')){
				tr1.css('height', '');
				tr2.css('height', '');
				var height = Math.max(tr1.height(), tr2.height());
				tr1.css('height', height);
				tr2.css('height', height);
			}
			dc.body2.triggerHandler('scroll');
		});
	},
	getExpander: function(jq, index){	// get row expander object
		var opts = $.data(jq[0], 'datagrid').options;
		return opts.finder.getTr(jq[0], index).find('span.datagrid-row-expander');
	},
	// get row detail container
	getRowDetail: function(jq, index){
		var opts = $.data(jq[0], 'datagrid').options;
		var tr = opts.finder.getTr(jq[0], index, 'body', 2);
		return tr.next().find('div.datagrid-row-detail');
	},
	expandRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			console.info(opts);
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-expand')){
				expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.show();
				tr2.show();
				$(this).datagrid('fixDetailRowHeight', index);
				if (opts.onExpandRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onExpandRow.call(this, index, row);
				}
			}
		});
	},
	collapseRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-collapse')){
				expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.hide();
				tr2.hide();
				dc.body2.triggerHandler('scroll');
				if (opts.onCollapseRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onCollapseRow.call(this, index, row);
				}
			}
		});
	}
});


/**
 * 扩展树表格级联勾选方法：
 * @param {Object} container
 * @param {Object} options
 * @return {TypeName}
 */
$.extend($.fn.treegrid.methods,{
	/**
	 * 级联选择
	 * @param {Object} target
	 * @param {Object} param
	 *      param包括两个参数:
	 *          id:勾选的节点ID
	 *          deepCascade:是否深度级联
	 * @return {TypeName}
	 */
	cascadeCheck : function(target,param){
		var s = '#check_'+param.id;
		var opts = $.data(target[0], "treegrid").options;
		var idField = opts.idField;//这里的idField其实就是API里方法的id参数
		var status = false;//用来标记当前节点的状态，true:勾选，false:未勾选
		// 当前节点选中情况
		if ($(s)[0].checked) {
			status = true;
		}
		//级联选择父节点
		selectChildren(target[0],param.id,idField,param.deepCascade,status);
		selectParent(target[0],param.id,idField,status);
		/**
		 * 级联选择父节点
		 * @param {Object} target
		 * @param {Object} id 节点ID
		 * @param {Object} status 节点状态，true:勾选，false:未勾选
		 * @return {TypeName}
		 */
		function selectParent(target,id,idField,status){
			var parent = $(target).treegrid('getParent',id);
			if(parent){
				var parentId = parent[idField];
				if (status) {
					$(('#check_'+parentId))[0].checked  = true;
				} else {
					// 检查父亲节点下是否有选中项目
					var tmpchild = $(target).treegrid('getChildren',parentId);
					var flag = true;
					for(i=0;i<tmpchild.length;i++){
						if($(('#check_'+tmpchild[i][idField]))[0].checked){
							flag = false;
							break;
						}
					}
					if (flag) {
						$(('#check_' + parentId))[0].checked = false;
					}
				}
				selectParent(target,parentId,idField,status);
			}
		}
		/**
		 * 级联选择子节点
		 * @param {Object} target
		 * @param {Object} id 节点ID
		 * @param {Object} deepCascade 是否深度级联
		 * @param {Object} status 节点状态，true:勾选，false:未勾选
		 * @return {TypeName}
		 */
		function selectChildren(target,id,idField,deepCascade,status){
			//深度级联时先展开节点
			if(!status&&deepCascade)
				$(target).treegrid('expand',id);
			//根据ID获取下层孩子节点
			var children = $(target).treegrid('getChildren',id);
			for(var i=0;i<children.length;i++){
				var childId = children[i][idField];
				if(status) {
					$(('#check_'+childId))[0].checked  = true;
					//$(target).treegrid('select', childId);
				}else {
					$(('#check_'+childId))[0].checked  = false;
					//$(target).treegrid('unselect', childId);
				}
				selectChildren(target,childId,idField,deepCascade,status);//递归选择子节点
			}
		}
	}
});

function removeIcon(){
	//去掉结点前面的文件及文件夹小图标
	$(".tree-icon,.tree-file").removeClass("tree-icon tree-file");
	$(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed");
}

function show(checkid) {
	//级联选择
	$('#grid').treegrid('cascadeCheck',{
		id:checkid, //节点ID
		deepCascade:true //深度级联
	});
}

function inArray(arr, val) {
	var ret = false, i = 0, len = arr.length;
	for (; i < len; i++) {
		if (arr[i] == val) { // 必须用强类型校验
			ret = true;
			break;
		}
	}
	return ret;
}