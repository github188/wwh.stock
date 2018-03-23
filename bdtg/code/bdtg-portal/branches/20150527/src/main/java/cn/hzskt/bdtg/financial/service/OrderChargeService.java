package cn.hzskt.bdtg.financial.service;

import cn.hzskt.bdtg.financial.domain.OrderCharge;
import cn.hzskt.bdtg.financial.mapper.OrderChargeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class  OrderChargeService extends BaseService<OrderCharge,OrderChargeMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<OrderCharge> query(Map<String, String> paramMap) {
        Example example = new Example(OrderCharge.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String orderType = paramMap.get("orderType");
        if (!StringUtils.isEmpty(orderType)) {
            criteria.andLike("orderType", "%" + orderType+ "%");
        }
return mapper.selectByExample(example);
    }

    public List<?> querycharge(Map<String, String> paramMap) {
        Example example = new Example(OrderCharge.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid=paramMap.get("userid");
        String ord=paramMap.get("ord");
        criteria.andEqualTo("uid", uid);
        criteria.andIsNotNull("payMoney");
        if(!StringUtils.isEmpty(ord)){
            switch (ord){
                case "3":
                    return  mapper.orderByTimeAllUp(uid);
                case "4" :
                    return  mapper.orderByTimeAllDown(uid);
            }
        }
        return mapper.selectByExample(example);
    }

    public PageInfo<?> queryPaged1(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("pageSize") == null?null:(String)paramMap.get("pageSize");
        String pageStr = paramMap.get("page") == null?null:(String)paramMap.get("page");
        int page = 1;
        int rows = 10;

        try {
            page = pageStr != null?Integer.valueOf(pageStr).intValue():page;
            rows = rowsStr != null?Integer.valueOf(rowsStr).intValue():rows;
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        PageHelper.startPage(page, rows);
        List list= this.querycharge(paramMap);
        return new PageInfo(list);
    }
}
