package cn.hzskt.bdtg.job.service;

import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import cn.hzskt.bdtg.job.domain.Process;
import cn.hzskt.bdtg.job.mapper.ProcessMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  ProcessService extends BaseService<Process,ProcessMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Process> query(Map<String, String> paramMap) {
        Example example = new Example(Process.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String tid = paramMap.get("tid");
        if (!StringUtils.isEmpty(tid)) {
        	criteria.andEqualTo("tid", tid);
        }
        
        String type = paramMap.get("type");
        if (!StringUtils.isEmpty(type)) {
            criteria.andEqualTo("type", type);
        }
        return mapper.selectByExample(example);
    }
    
    
    
    
}
