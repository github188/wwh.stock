package cn.hzstk.securities.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.hzstk.securities.common.utils.beanutil.Map2BeanUtils;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.task.domain.TaskBid;
import cn.hzstk.securities.task.mapper.TaskBidMapper;

@Service
public class TaskBidService extends BaseService<TaskBid, TaskBidMapper> {

	
	/**
	 * 投标方交稿
	 * @param taskId
	 * @param params
	 * @param usr
	 */
	public void addTaskBid(String taskId, Map params, User usr){
		TaskBid bid = Map2BeanUtils.convert(params, TaskBid.class);
		bid.setHasdel(0);
		if(usr!=null){
			bid.setUid(usr.getId());
			bid.setUsername(usr.getUserName());
		}
		this.saveOrUpdate(bid);
	}
	
	/**
	 * 查询投标记录数
	 * @param taskId
	 * @return
	 */
	public int selectBidNum(String taskId, String usrId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);
		params.put("uid", usrId);
		return this.sqlSession.selectOne("TaskBid_select_nums", params);
	}
	
	public List<TaskBid> queryTaskBid(String taskId, String status){
		List<TaskBid> items = new ArrayList<TaskBid>();
		if(StringUtils.isBlank(status)){
			items.addAll(selectTaskBid(taskId, "4"));	//中标结果集增加
			items.addAll(selectTaskBid(taskId, "7"));	//淘汰结果集增加
			items.addAll(selectTaskBid(taskId, "0"));	//
			return items;
		}
		else{
			return selectTaskBid(taskId, status);
		}
	}
	
	private List<TaskBid> selectTaskBid(String taskId, String status){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);
		params.put("status", status);
		return this.sqlSession.selectList("TaskBid_select_all", params);
	}
	
	public void changeBidStatus(String bid, String status){
		TaskBid bean = new TaskBid();
		bean.setId(Long.parseLong(bid));
		bean.setBidStatus(Integer.parseInt(status));
		this.saveOrUpdate(bean);
	}
	
	public TaskBid getBid(String taskId){
		List<TaskBid> bids = selectTaskBid(taskId, "4");
		return (bids!=null && bids.size() > 0) ? bids.get(0):null;
	}
	
}
