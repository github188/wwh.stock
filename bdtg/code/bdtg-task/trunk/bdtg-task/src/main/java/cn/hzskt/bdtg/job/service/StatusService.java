package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Status;
import cn.hzskt.bdtg.job.mapper.StatusMapper;
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
@Component
public class  StatusService extends BaseService<Status,StatusMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Status> query(Map<String, String> paramMap) {
        Example example = new Example(Status.class);
        example.setOrderByClause("statusTime desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String tid = paramMap.get("tid");
        criteria.andEqualTo("tid",tid);
        return mapper.selectByExample(example);
    }
    /**
     *获取项目进度
     * @param paramMap
     * @return
     */
    public List<Status> getValueByTid(Map<String, String> paramMap) {
        Example example = new Example(Status.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String tid = paramMap.get("tid");
        if (!StringUtils.isEmpty(tid)) {
            criteria.andLike("tid", "%" + tid+ "%");
        }
        return mapper.selectByExample(example);
    }
}
