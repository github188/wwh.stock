package cn.hzskt.bdtg.job.service;

import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.annotation.DataSource;
import org.springframework.stereotype.Service;
import cn.hzskt.bdtg.job.domain.Task;
import cn.hzskt.bdtg.job.mapper.TaskMapper;

@Service
@DataSource("manager_ds")
public class TaskService extends BaseService<Task, TaskMapper> {
//
//	@Override
//	public Task get(Long id) {
//		Task bean = super.get(id);
//		return bean;
//	}
//
}
