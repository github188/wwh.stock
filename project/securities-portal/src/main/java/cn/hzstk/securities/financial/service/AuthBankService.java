package cn.hzstk.securities.financial.service;

import cn.hzstk.securities.financial.domain.AuthBank;
import cn.hzstk.securities.financial.mapper.AuthBankMapper;
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
public class  AuthBankService extends BaseService<AuthBank,AuthBankMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthBank> query(Map<String, String> paramMap) {
        Example example = new Example(AuthBank.class);
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
     public List<AuthBank> getaccount(Integer uid) {
    	 AuthBank ab =new AuthBank();
    	 ab.setAuthStatus(1);
    	 ab.setUid(uid);
         return mapper.select(ab);
     }
     
     /**
      * 查询银行卡审核信息
      * @param paramMap
      * @return
      */
      public List<AuthBank> getbybankid(Integer id) {
     	  AuthBank ab =new AuthBank();
     	  ab.setBankId(id);
          return mapper.select(ab);
      }
      
      /**
       * 查询用户银行卡是否认证
       * @param paramMap
       * @return
       */
       public boolean getauthstatus(Long uid) {
      	 AuthBank ab =new AuthBank();
      	 ab.setUid(uid.intValue());
      	 List<AuthBank> abs = mapper.select(ab);
      	 for(AuthBank authbank:abs){
      		 if(authbank.getAuthStatus()==1){
      			 return true;
      		 }
      	 }
         return false;
       }
}
