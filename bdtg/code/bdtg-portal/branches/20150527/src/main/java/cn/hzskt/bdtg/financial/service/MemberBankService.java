package cn.hzskt.bdtg.financial.service;

import cn.hzskt.bdtg.financial.domain.AuthBank;
import cn.hzskt.bdtg.financial.domain.MemberBank;
import cn.hzskt.bdtg.financial.mapper.MemberBankMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
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
public class  MemberBankService extends BaseService<MemberBank,MemberBankMapper> {
	
	@Autowired
	AuthBankService authBankService;
    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<MemberBank> query(Map<String, String> paramMap) {
        Example example = new Example(MemberBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid = paramMap.get("uid");
        if (!StringUtils.isEmpty(uid)) {
            criteria.andLike("uid", "%" + uid+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 查询用户银行卡信息
     * @param paramMap
     * @return
     */
     public List<MemberBank> getaccount(Integer uid) {
    	 MemberBank mb =new MemberBank();
    	 mb.setUid(uid);
         return mapper.select(mb);
     }
     
     /**
      * 根据卡号查询
      * @param paramMap
      * @return
      */
      public List<MemberBank> getaccount(String cardnum) {
     	 MemberBank mb =new MemberBank();
     	 mb.setCardNum(cardnum);
          return mapper.select(mb);
      }
      
      /**
       * 解除绑定
       * @param paramMap
       * @return
       */
      public void removebind(Long id){
		List<AuthBank> ab = authBankService.getbybankid(id.intValue());
      	if(ab.size()<=0){
      	}else{
      	authBankService.logicRemove(ab.get(0).getId());
      	}
      	logicRemove(id);
      }
}
