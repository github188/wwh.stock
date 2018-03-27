package cn.hzstk.securities.member.service;

import cn.hzstk.securities.member.domain.AuthSpace;
import cn.hzstk.securities.member.mapper.AuthSpaceMapper;
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
public class  AuthSpaceService extends BaseService<AuthSpace,AuthSpaceMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<AuthSpace> query(Map<String, String> paramMap) {
        Example example = new Example(AuthSpace.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String userId = paramMap.get("userId");
        if (!StringUtils.isEmpty(userId)) {
            criteria.andLike("userId", "%" + userId+ "%");
        }

        return mapper.selectByExample(example);
    }
    
    /**
     * 根据用户Id获取用户认证信息
     * @param usrId
     * @return
     */
    public AuthSpace getAuthByUsrId(Long usrId){
    	if(usrId == null) return null;
        Example example = new Example(AuthSpace.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("userId", usrId);
        List<AuthSpace> items = mapper.selectByExample(example);
        return (items!=null && items.size() > 0) ? items.get(0):null;
    }
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    public List<AuthSpace>  querybyusername(String username) {
        AuthSpace as = new AuthSpace();
        as.setUserName(username);
        as.setValid(1);
        return mapper.select(as);
    }
    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    public List<AuthSpace> querybymobile(String mobile) {
        AuthSpace as = new AuthSpace();
        as.setMobile(mobile);
        as.setMobileStatus(1);
        as.setValid(1);
        return mapper.select(as);
    }
    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    public List<AuthSpace> querybyemail(String email) {
        AuthSpace as = new AuthSpace();
        as.setEmail(email);
        as.setEmailStatus(1);
        as.setValid(1);
        return mapper.select(as);
    }
    /**
     * 根据userId查询
     * @param userId
     * @return
     */
     public List<AuthSpace> querybyuserId(Long userId) {
         AuthSpace as = new AuthSpace();
         as.setUserId(userId);
         as.setValid(1);
         return mapper.select(as);
     }

    /**
     * 根据userId查询余额信息
     * @param userId
     * @return
     */
      public Double getuserbalance(Long userId) {
          AuthSpace as = new AuthSpace();
          as.setUserId(userId);
          as.setValid(1);
          List<AuthSpace> list = mapper.select(as);
          if(list.size()<=0){
        	  return Double.valueOf(0);
          }else{
        	  Double balance = list.get(0).getBalance();
          return balance==null?Double.valueOf(0):balance;
          }
      }
}
