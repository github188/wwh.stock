package cn.hzstk.securities.sys.service;

import cn.hzstk.securities.sys.domain.Holiday;
import cn.hzstk.securities.sys.mapper.HolidayMapper;
import cn.hzstk.securities.util.DateUtil;
import net.ryian.commons.DateUtils;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Component
public class  HolidayService extends BaseService<Holiday,HolidayMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Holiday> query(Map<String, String> paramMap) {
        Example example = new Example(Holiday.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (!StringUtils.isEmpty(dt)) {
            criteria.andLike("dt", "%" + dt+ "%");
        }
        String description = paramMap.get("description");
        if (!StringUtils.isEmpty(description)) {
            criteria.andLike("description", "%" + description+ "%");
        }

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(Holiday o) {
        Example example = new Example(Holiday.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());

        List<Holiday> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }

    public boolean chkHoliday(String dt) {
        if (DateUtil.isWeekend(DateUtils.parseDate(dt))) return true;
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date());
        paramMap.put("dt", dt);
        List<Holiday> list = query(paramMap);

        return list != null && list.size() > 0;
    }
}
