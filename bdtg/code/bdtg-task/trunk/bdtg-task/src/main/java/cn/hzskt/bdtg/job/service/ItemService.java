package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Item;
import cn.hzskt.bdtg.job.mapper.ItemMapper;
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
public class  ItemService extends BaseService<Item,ItemMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Item> query(Map<String, String> paramMap) {
        Example example = new Example(Item.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
        String tid = paramMap.get("tid");
        criteria.andEqualTo("task",tid);
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据任务id查询
     * @param
     * @return
     */
     public List<Item> query(Long task) {
    	 Item item = new Item();
    	 item.setTask(task);
         item.setValid(1);
    	 return mapper.select(item);
     }

    /**
     * 根据任务id和主项名称获取itemid
     * @param task 任务ID
     * @param name 主项名称
     * @return Item
     */
    public Item getItemByName(Long task,String name) {
        Item item = new Item();
        item.setTask(task);
        item.setValid(1);
        item.setName(name);
        return mapper.selectOne(item);
    }

     /**
      * 根据任务id和专业查询
      * @param paramMap
      * @return
      */
      public List<Item> queryitems(Long task,String major) {
    	  Example example = new Example(Item.class);
          Example.Criteria criteria = example.createCriteria();
          criteria.andEqualTo("valid","1");
          criteria.andEqualTo("task", task);
          criteria.andLike("major", "%" + major+ "%");
          return mapper.selectByExample(example);
      }
}
