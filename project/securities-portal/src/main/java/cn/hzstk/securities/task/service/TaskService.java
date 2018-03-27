package cn.hzstk.securities.task.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import cn.hzstk.securities.common.utils.beanutil.Map2BeanUtils;
import cn.hzstk.securities.sys.domain.User;
import cn.hzstk.securities.task.domain.Task;
import cn.hzstk.securities.task.mapper.TaskMapper;
import cn.hzstk.securities.task.utils.PageUtils;

import com.github.pagehelper.PageInfo;

@Service
public class TaskService extends BaseService<Task, TaskMapper> {

	
	/**
	 * 发布任务，数据是缓存在httpsession里的
	 * @param session
	 */
	public Task addTask(Map session, User usr){
		Task task = Map2BeanUtils.convert(session, Task.class);
		task.setTaskStatus("1");		//将记录设置成待审核的状态
		task.setUid(Integer.parseInt(String.valueOf(usr.getId())));
		task.setUsername(usr.getUserName());
		try {
			String subtime = MapUtils.getString(session, "subTime");
			Date time = DateUtils.parseDate(subtime, new String[]{"yyyy-MM-dd HH:mm"});
			task.setSubTime(time);
		} 
		catch (Exception e) {
			
		}
		this.saveOrUpdate(task);
		return task;
	}

	/**
	 * 任务大厅，任务列表查询，只查询审核通过的，未提交审核或审核失败的则不显示
	 * @param paramMap
	 * @return
	 */
	public PageInfo selectOnline(Map paramMap) {
		
		int pageNums = MapUtils.getInteger(paramMap, "page", 1);
		int pageSize = MapUtils.getInteger(paramMap, "size", 15);
		
		int page = (pageNums < 1) ? 1 : pageNums;
		int nums = (pageSize < 1) ? 15 : pageSize;
		
		paramMap.put("limit", String.valueOf((page-1) * nums));
		paramMap.put("size", String.valueOf(nums));
		
		List items = this.sqlSession.selectList("Task_select_all", paramMap);
		int num = this.sqlSession.selectOne("Task_select_nums", paramMap);
		return PageUtils.buildPageInfo(items, num, page, nums, 10);
	}
	
	public PageInfo selectMyTask(String usrId, Map<String, String> maps, int pageNum, int size) {
		Map<String, String> params = new HashMap<String, String>();
		if(maps!=null) params.putAll(maps);
		params.put("uid", usrId);
		
		int page = (pageNum < 1) ? 1 : pageNum;
		int nums = (size < 1) ? 15 : size;
		
		params.put("limit", String.valueOf((page-1) * nums));
		params.put("size", String.valueOf(nums));
		List items = this.sqlSession.selectList("MyTask_select_all", params);
		int num = this.sqlSession.selectOne("MyTask_select_nums", params);
		return PageUtils.buildPageInfo(items, num, page, nums, 10);
	}
	
	/**
	 * 我承接的任务，主要查询稿件中标的任务
	 * @param usrId
	 * @param maps
	 * @return
	 */
	public PageInfo selectMyUnderTask(String usrId, Map<String, String> maps, int pageNum, int size) {
		Map<String, String> params = new HashMap<String, String>();
		if(maps!=null) params.putAll(maps);
		params.put("uid", usrId);
		params.put("bidStatus", "4");
		
		int page = (pageNum < 1) ? 1 : pageNum;
		int nums = (size < 1) ? 15 : size;
		
		params.put("limit", String.valueOf((page-1) * nums));
		params.put("size", String.valueOf(nums));
		
		List items = this.sqlSession.selectList("MyTask_bid_select_all", params);
		int num = this.sqlSession.selectOne("MyTask_bid_select_nums", params);
		
		return PageUtils.buildPageInfo(items, num, page, nums, 10);
	}
	
	
	
	/**
	 * 我的投稿记录，不根据投稿记录状态查询
	 * @param usrId
	 * @param maps
	 * @return
	 */
	public PageInfo selectMyBidTask(String usrId, Map<String, String> maps, int pageNum, int size) {
		Map<String, String> params = new HashMap<String, String>();
		if(maps!=null) params.putAll(maps);
		params.put("uid", usrId);
		int page = (pageNum < 1) ? 1 : pageNum;
		int nums = (size < 1) ? 15 : size;
		
		params.put("limit", String.valueOf((page-1) * nums));
		params.put("size", String.valueOf(nums));
		
		List items = this.sqlSession.selectList("MyTask_bid_select_all", params);
		int num = this.sqlSession.selectOne("MyTask_bid_select_nums", params);
		
		return PageUtils.buildPageInfo(items, num, page, nums, 10);
	}
	
	public void updateTaskViewNum(String taskId, int nums){
		Task task = this.selectTaskById(taskId);
		if(task!=null){
			Integer viewNum = task.getViewNum();
			if(viewNum == null) viewNum = 0;
			viewNum = viewNum + nums;
			task.setViewNum(viewNum);
			this.saveOrUpdate(task);
		}
	}
	
	public void updateTaskWorkNum(String taskId, int nums){
		Task task = this.selectTaskById(taskId);
		if(task!=null){
			Integer viewNum = task.getWorkCount();
			if(viewNum == null) viewNum = 0;
			viewNum = viewNum + nums;
			task.setWorkCount(viewNum);
			this.saveOrUpdate(task);
		}
	}
	
	public List getlatestBidTask(int num){
		Map param = new HashMap();
		param.put("size", num);
		return this.sqlSession.selectList("Task_bid_select", param);
	}
	
	public Task selectTaskById(String id){
		return this.get(Long.parseLong(id));
	}
	
}
