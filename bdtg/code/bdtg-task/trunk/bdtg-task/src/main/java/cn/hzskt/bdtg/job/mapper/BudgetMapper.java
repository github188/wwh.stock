package cn.hzskt.bdtg.job.mapper;

import org.apache.ibatis.annotations.Select;

import cn.hzskt.bdtg.job.domain.Budget;
import tk.mybatis.mapper.common.Mapper;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface BudgetMapper extends Mapper<Budget>{
	
	
	@Select("select sum(b.budget_fare) from job_budget b, job_process p where b.valid=1 and p.valid=1 and p.id=b.process_id and p.tid=#{0} and b.uid=#{1}")
	public double getUsrJobFare(String taskId, String usrId);
	
}
