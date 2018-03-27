package com.skoo.stock.common;

import com.skoo.stock.sys.service.DictService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class InitWorker implements InitializingBean {

    @Autowired
    private DictService dictService;

    @Override
    public void afterPropertiesSet() throws Exception {
//        // 数据字典存入缓存
//        List<String> keys = dictService.getKeyNames();
//        for (String key : keys) {
//            dictService.getDictsByKey(key);
//        }
    }

}
