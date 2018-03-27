package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.stk.domain.IndexData;
import cn.hzstk.securities.stk.mapper.IndexDataMapper;
import cn.hzstk.securities.util.DigitFormat;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class  IndexDataService extends BaseService<IndexData,IndexDataMapper> {
    private static final Logger logger = LoggerFactory.getLogger(Constant.LOG_REAL);

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<IndexData> query(Map<String, String> paramMap) {
        Example example = new Example(IndexData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String code = paramMap.get("code");
        if (!StringUtils.isEmpty(code)) {
            criteria.andLike("code", "%" + code+ "%");
        }
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(IndexData o) {
        Example example = new Example(IndexData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("code", o.getCode());
        //criteria.andEqualTo("name", o.getName());
        criteria.andEqualTo("dt", o.getDt());

        List<IndexData> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
            o.setChangeRate(DigitFormat.calcWidth(o.getPreClose(), o.getPrice(), list.get(0).getPrice()));
            logger.info(o.getCode() + "|" + o.getPrice() + "|" + o.getChangeWidth() + "|" + o.getChangeAmount() + "|" + o.getAmount()
                    + "|" + o.getChangeRate());
            if (StringUtils.isNotEmpty(o.getRiseCnt())) logger.info(o.getRiseCnt() + "|" + o.getFlatCnt() + "|" + o.getFallCnt());
        }

        return super.saveOrUpdate(o);
    }
}
