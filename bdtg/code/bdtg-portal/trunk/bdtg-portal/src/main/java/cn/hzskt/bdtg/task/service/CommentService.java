package cn.hzskt.bdtg.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Service;

import cn.hzskt.bdtg.task.domain.Comment;
import cn.hzskt.bdtg.task.mapper.CommentMapper;

@Service
public class CommentService extends BaseService<Comment, CommentMapper> {

	/**
	 * 根据任务id获取 投标信息的评论
	 * @param taskId
	 * @param bid
	 * @return
	 */
	public List<Comment> selectBidComment(String taskId, String bid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objType", "work");
		params.put("originId", taskId);
		params.put("objId", bid);
		return this.sqlSession.selectList("Comment_list", params);
	}
	
}
