package cn.hzskt.bdtg.sys.utils.json;

import cn.hzskt.bdtg.sys.service.DictService;
import com.alibaba.fastjson.serializer.AfterFilter;
import net.ryian.commons.BeanUtils;
import net.ryian.commons.StringUtils;
import net.ryian.core.utils.SpringContextUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by allenwc on 16/2/18.
 */
public class DictFilter extends AfterFilter {

    private DictService dictService;

	public DictFilter() {
		dictService = SpringContextUtil
				.getBean(DictService.class);
	}

    @Override
    public void writeAfter(Object bean) {
        for (Field field : bean.getClass().getDeclaredFields()) {
				Dict annotation = field
						.getAnnotation(Dict.class);
				if (annotation != null
						&& !StringUtils.isEmpty(annotation.key())) {
					String key = annotation.key();
                    String value = null;
                    try {
                        value = BeanUtils.getProperty(bean, field.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    Map<String, cn.hzskt.bdtg.sys.domain.Dict> map = dictService
							.getDictsByKey(key);
					if (map != null) {
						cn.hzskt.bdtg.sys.domain.Dict dic = map.get(value);
						if (dic != null) {
                            writeKeyValue(field.getName() + annotation.name(),dic.getContent());
                        } else {
                            writeKeyValue(field.getName() + annotation.name(),annotation.nullValue());
                        }
					}
				}
        }

    }


}
