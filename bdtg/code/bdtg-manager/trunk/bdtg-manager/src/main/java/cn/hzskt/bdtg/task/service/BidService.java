package cn.hzskt.bdtg.task.service;

import cn.hzskt.bdtg.task.domain.Bid;
import cn.hzskt.bdtg.task.mapper.BidMapper;
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
public class  BidService extends BaseService<Bid,BidMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Bid> query(Map<String, String> paramMap) {
        Example example = new Example(Bid.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String taskId = paramMap.get("taskId");
        if (!StringUtils.isEmpty(taskId)) {
            criteria.andLike("taskId", "%" + taskId+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据tid查询任务中标人
     * 
     * @param paramMap
     * @return
     */
    public Bid getbytid(Integer taskId){
    	Bid bid = new Bid();
    	bid.setBidStatus(4);
    	bid.setValid(1);
    	bid.setTaskId(taskId);
    	return mapper.selectOne(bid);
    }
}
