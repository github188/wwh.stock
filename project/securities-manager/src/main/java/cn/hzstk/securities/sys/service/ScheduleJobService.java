package cn.hzstk.securities.sys.service;

import cn.hzstk.securities.sys.domain.ScheduleJob;
import cn.hzstk.securities.sys.mapper.ScheduleJobMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Component
public class  ScheduleJobService extends BaseService<ScheduleJob,ScheduleJobMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<ScheduleJob> query(Map<String, String> paramMap) {
        Example example = new Example(ScheduleJob.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String jobId = paramMap.get("jobId");
        if (!StringUtils.isEmpty(jobId)) {
            criteria.andLike("jobId", "%" + jobId+ "%");
        }
        String jobName = paramMap.get("jobName");
        if (!StringUtils.isEmpty(jobName)) {
            criteria.andLike("jobName", "%" + jobName+ "%");
        }

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(ScheduleJob o) {
        Example example = new Example(ScheduleJob.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("jobId", o.getJobId());
        criteria.andEqualTo("jobName", o.getJobName());

        List<ScheduleJob> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
            if (StringUtils.isEmpty(o.getJobStatus())) o.setJobStatus(StringUtils.EMPTY);
        }

        return super.saveOrUpdate(o);
    }
}
