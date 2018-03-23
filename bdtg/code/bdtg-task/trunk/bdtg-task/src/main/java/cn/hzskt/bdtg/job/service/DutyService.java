package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Duty;
import cn.hzskt.bdtg.job.mapper.DutyMapper;
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
public class  DutyService extends BaseService<Duty,DutyMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Duty> query(Map<String, String> paramMap) {
        Example example = new Example(Duty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String memberId = paramMap.get("memberId");
        String tid = paramMap.get("tid");
        if (!StringUtils.isEmpty(memberId)) {
            criteria.andEqualTo("memberId", memberId);
        }
        if (!StringUtils.isEmpty(tid)) {
            criteria.andEqualTo("tid", tid);
        }
        return mapper.selectByExample(example);
    }

    /**
     * 查询当前用户的职责
     * @param mmbid memberid
     * @param tid 任务ID
     * @return List<Duty>
     */
    public List<Duty> getDutyByTid(Integer mmbid,Long tid) {
        Duty duty = new Duty();
        duty.setMemberId(mmbid);
        duty.setTid(tid);
        duty.setValid(1);
        return mapper.select(duty);
    }
}
