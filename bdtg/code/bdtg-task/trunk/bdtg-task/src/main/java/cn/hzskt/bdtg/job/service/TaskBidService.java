package cn.hzskt.bdtg.job.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.DataSourceContextHolder;

import org.springframework.stereotype.Service;

import cn.hzskt.bdtg.job.domain.TaskBid;
import cn.hzskt.bdtg.job.mapper.TaskBidMapper;

@Service
public class TaskBidService extends BaseService<TaskBid, TaskBidMapper> {

	private List<TaskBid> selectTaskBid(String taskId, String status){
		DataSourceContextHolder.setCustomerType("manager_ds");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);
		params.put("status", status);
		List<TaskBid> items = this.sqlSession.selectList("TaskBid_select_all", params);
		DataSourceContextHolder.clearCustomerType();
		return items;
	}
	
	public TaskBid getBid(String taskId){
		List<TaskBid> bids = selectTaskBid(taskId, "4");
		return (bids!=null && bids.size() > 0) ? bids.get(0):null;
	}
	
}
