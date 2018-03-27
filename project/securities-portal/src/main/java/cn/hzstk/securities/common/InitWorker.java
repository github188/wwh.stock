package cn.hzstk.securities.common;

import cn.hzstk.securities.sys.service.DictService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Lazy(false)
public class InitWorker implements InitializingBean {

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

	public static void main(String[] args) {
		System.out.println(new Date(1442791800000l));
	}

}
