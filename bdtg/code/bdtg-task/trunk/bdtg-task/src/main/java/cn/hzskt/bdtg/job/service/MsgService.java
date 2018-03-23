package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Msg;
import cn.hzskt.bdtg.job.mapper.MsgMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.domain.BaseEntity;
import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.DataSourceContextHolder;
import net.ryian.orm.service.support.datasource.annotation.DataSource;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  MsgService extends BaseService<Msg,MsgMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Msg> query(Map<String, String> paramMap) {
        DataSourceContextHolder.setCustomerType("manager_ds");
        Example example = new Example(Msg.class);
        example.setOrderByClause("createDate desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String title = paramMap.get("title");
        if (!StringUtils.isEmpty(title)) {
            criteria.andLike("title", "%" + title+ "%");
        }
        String jobType = paramMap.get("jobType");
        if (!StringUtils.isEmpty(jobType)) {
            criteria.andEqualTo("jobType", jobType);
        }
        String viewStatus = paramMap.get("viewStatus");
        if (!StringUtils.isEmpty(viewStatus)) {
            if(!viewStatus.equals("0")){
                criteria.andEqualTo("viewStatus", viewStatus);
            }
        }
        String toUid = paramMap.get("toUid");
        if (!StringUtils.isEmpty(toUid)) {
            criteria.andEqualTo("toUid", toUid);
        }
        String uid = paramMap.get("uid");
        if (!StringUtils.isEmpty(uid)) {
            criteria.andEqualTo("uid", uid);
        }
        criteria.andEqualTo("tid", paramMap.get("tid"));
        List<Msg> list = new ArrayList<Msg>();
        list = mapper.selectByExample(example);
        DataSourceContextHolder.clearCustomerType();
        return list;
    }
    @DataSource(value = "manager_ds")
    public Msg get(Long id) {
        Msg msg = new Msg();
        msg=  this.mapper.selectByPrimaryKey(id);
        return msg;
    }

    @DataSource(value = "manager_ds")
    public int logicRemove(Long id) {
        try {
            Msg o = new Msg();
            o.setId(id);
            o.setValid(Integer.valueOf(0));
            o.setUpdateDate(new Date());
            int rnum= this.mapper.updateByPrimaryKeySelective(o);
            return rnum;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @DataSource(value = "manager_ds")
    public Long saveOrUpdate(Msg o) {
        if(o.getId() != null && o.getId().longValue() != 0L) {
            o.setUpdateDate(new Date());
            this.mapper.updateByPrimaryKeySelective(o);
        } else {
            o.setCreateDate(new Date());
            this.mapper.insert(o);
        }

        return o.getId();
    }
}
