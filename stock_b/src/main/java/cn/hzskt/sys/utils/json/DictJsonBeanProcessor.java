package cn.hzskt.sys.utils.json;

import java.lang.reflect.Field;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.commons.lang.StringUtils;

import com.zjhcsoft.smartcity.magic.commons.BeanUtils;
import com.zjhcsoft.smartcity.magic.core.utils.JsonUtils;
import com.zjhcsoft.smartcity.magic.core.utils.SpringContextUtil;
import cn.hzskt.sys.domain.Dict;
import cn.hzskt.sys.service.DictService;

public class DictJsonBeanProcessor implements JsonBeanProcessor {

	private String dateFomat;

	private DictService dictService;

	public DictJsonBeanProcessor() {
		dictService = SpringContextUtil
				.getBean(DictService.class);
	}

	public DictJsonBeanProcessor(String dateFomat) {
		dictService = SpringContextUtil.getBean(DictService.class);
		this.dateFomat = dateFomat;
	}

	@Override
	public JSONObject processBean(Object bean, JsonConfig config) {
		JSONObject o = null;
		try {
			o = JsonUtils.bean2JSONObject(BeanUtils.cloneBean(bean));
			for (Field field : bean.getClass().getDeclaredFields()) {
				DictAnnotation annotation = field
						.getAnnotation(DictAnnotation.class);
				if (annotation != null
						&& !StringUtils.isEmpty(annotation.key())) {
					String key = annotation.key();
					String value = BeanUtils.getProperty(bean, field.getName());
					Map<String, Dict> map = dictService
							.getDictsByKey(key);
					if (map != null) {
						Dict dic = map.get(value);
						if (dic != null)
							o.put(field.getName() + annotation.name(),
									dic.getContent());
						else
							o.put(field.getName() + annotation.name(),
									annotation.nullValue());

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}