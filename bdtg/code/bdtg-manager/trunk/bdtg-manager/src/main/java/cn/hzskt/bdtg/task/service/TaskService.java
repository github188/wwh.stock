package cn.hzskt.bdtg.task.service;

import java.util.HashMap;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.mapper.TaskMapper;

@Service
public class TaskService extends BaseService<Task, TaskMapper> {

	public Map pagenation(Map params){
		
		int page = MapUtils.getInteger(params, "page", 0);
		if(page < 1) page = 1;
			
		int rows = MapUtils.getInteger(params, "rows", 10);
		if(rows < 1) page = 10;
		
		params.put("start", (page-1)*rows);
		params.put("limit", rows);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", this.sqlSession.selectOne("Task_select_nums", params));
		data.put("rows", this.sqlSession.selectList("Task_select_all", params));
		
		return data;
	}
	
	public void pass(String id){
		Task task = new Task();
		task.setId(Long.parseLong(id));
		task.setTaskStatus(2);
		this.saveOrUpdate(task);
	}
	
	
	public void reject(String id){
		Task task = new Task();
		task.setId(Long.parseLong(id));
		task.setTaskStatus(10);
		this.saveOrUpdate(task);
	}
	
	
	
}
