package cn.hzskt.common;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.hzskt.sys.service.DictService;

@Component
@Lazy(false)
public class InitWorker implements InitializingBean{

	@Autowired
	private DictService dictService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// 数据字典存入缓存
		List<String> keys = dictService.getKeyNames();
		for (String key : keys) {
			dictService.getDictsByKey(key);
		}
	}

}
