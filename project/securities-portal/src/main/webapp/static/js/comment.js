/**
	浠诲姟璇勮JS
*/

/**
 * 鍙戝竷璇勮
 */
function addComment(objId,originId,objType,pId){
	var content = $.trim($("#addcontent").val());

	if(!content){
		tipsOp('<div class="alert alert-danger"><i class="fa fa-ban"></i> 鐣欒█涓嶅緱涓虹┖</div>');return false;
	}
	if(content.length > 100){
		tipsOp('<div class="alert alert-danger"><i class="fa fa-ban"></i> 鐣欒█涓嶅緱瓒呰繃100瀛�</div>');return false;
	}
	$.post('index.php?do=taskcomment',{objId:objId,originId:originId,objType:objType,pId:pId,content:content,action:'add'}, function(data){
		if(data === '浣犳搷浣滅殑澶绻佷簡锛岃绋嶅悗鍐嶈瘯!'){

			tipsOp('<div class="alert alert-danger"><i class="fa fa-ban"></i> 浣犳搷浣滅殑澶绻佷簡锛岃绋嶅悗鍐嶈瘯!</div>');
			return false;
		}
		$("#commentArea").after(data);
		$("#addcontent").val('');
	});
}

/**
 * 鏄剧ず/闅愯棌鍥炲杈撳叆妗�
 * @param cid 璇勮ID
 */
function toggleReplyArea(cid){
	$("#replyComment_"+cid).toggleClass('hidden');
}
/**
 * 璇勮鐨勫洖澶�
 * @param cid 璇勮ID
 * @param objId	璇勮瀵硅薄ID
 * @param originId 璇勮瀵硅薄鐨勫璞D
 * @param objType  璇勮瀵硅薄绫诲瀷
 * @param pId  璇勮鐨勪笂绾ц瘎璁篒D
 */
function replyAddComment(cid,objId,originId,objType,pId){
	var content = $.trim($("#replyAddContent_"+cid).val());
	$.post('index.php?do=taskcomment',{objId:objId,originId:originId,objType:objType,pId:pId,content:content,action:'reply'}, function(data){
		$("#replyComment_"+pId).after(data);
		$("#replyAddContent_"+cid).val('');
		$("#replyComment_"+cid).toggleClass('hidden');
	});
}
/**
 * 鍒犻櫎璇勮
 * @param cid 璇勮ID
 */
function delComment(cid){
	$.post('index.php?do=taskcomment',{cid:cid,action:'del'}, function(json){
		if(json.status === 'success'){
			$("#replyCommentList_"+cid).remove();return false;
		}
		tipsOp(json.data);return false;
	},'json');
}