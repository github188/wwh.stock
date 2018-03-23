package cn.hzskt.bdtg.financial.service;

import cn.hzskt.bdtg.financial.domain.Withdraw;
import cn.hzskt.bdtg.financial.mapper.WithdrawMapper;
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
public class  WithdrawService extends BaseService<Withdraw,WithdrawMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Withdraw> query(Map<String, String> paramMap) {
        Example example = new Example(Withdraw.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String withdrawCash = paramMap.get("withdrawCash");
        if (!StringUtils.isEmpty(withdrawCash)) {
            criteria.andLike("withdrawCash", "%" + withdrawCash+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据条件查询分页
     * @param paramMap
     * @return
     */
     public List<Withdraw> query(Withdraw withdraw) {
         return mapper.select(withdraw);
     }

    public List<?> querywithdraw(Map<String, String> paramMap) {
        Example example = new Example(Withdraw.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid=paramMap.get("userid");
        String ord=paramMap.get("ord");
        criteria.andEqualTo("uid", uid);
        criteria.andIsNotNull("withdrawCash");
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
        List list= this.querywithdraw(paramMap);
        return new PageInfo(list);
    }
}
