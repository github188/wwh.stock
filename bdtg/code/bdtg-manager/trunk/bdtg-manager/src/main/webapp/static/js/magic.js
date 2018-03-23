var Magic = {
	PAGE_SIZE:10,
	PAGE_LIST:[10,20,30,40,50],
	openWin:function(title, url, width, height,fit) {
		if ($('#win').length == 0)
			$('body')
					.append('<div id="win"><iframe id="win_frame" scrolling="no" frameborder="0" src="" style="width:100%;height:98%;"/></div>');
		$('#win_frame')[0].src = url;
		$('#win').window({
					title : title,
					modal : true,
					minimizable : false,
					maximizable : true,
					collapsible : false,
					resizable:true,
					fit : fit?fit:false,
					width : width ? width : $('#win_frame').width,
					height : height ? height : $('#win_frame').height
				});
		$('#win').window('open');
	},
	closeWin:function() {
		parent.$('#win').window('close');
	}
}