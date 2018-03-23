package cn.hzskt.bdtg.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.hzskt.bdtg.task.domain.TaskFile;
import cn.hzskt.bdtg.task.mapper.TaskFileMapper;

@Service
public class TaskFileService extends BaseService<TaskFile, TaskFileMapper> {

	public List<TaskFile> selectTaskFiles(String taskId){
		return selectTaskFiles(taskId, "task", null);
	}
	
	public List<TaskFile> selectTaskFiles(String taskId, String objType, String workId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", taskId);
		params.put("objType", objType);
		params.put("workId", workId);
		return this.sqlSession.selectList("TaskFile_select_all", params);
	}
	
	public void removeTaskFile(String id){
		this.sqlSession.delete("TaskFile_remove", id);
	}
	
	/**
	 * 更新文件的任务记录
	 * @param ids
	 * @param tskId
	 * @param tskTitle
	 * @param wkId
	 */
	public void updateFieldIds(String ids, String tskId, String tskTitle, String wkId){
		if(StringUtils.isBlank(ids)) return;
		for(String id : ids.split("\\|")){
			TaskFile f = new TaskFile();
			f.setId(Long.parseLong(id));
			f.setTaskId(Integer.parseInt(tskId));
			f.setTaskTitle(tskTitle);
			f.setWorkId(Integer.parseInt(wkId));
			this.saveOrUpdate(f);
		}
	}
	
}
