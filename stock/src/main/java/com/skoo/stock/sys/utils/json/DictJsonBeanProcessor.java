package com.skoo.stock.sys.utils.json;

import com.skoo.commons.BeanUtils;
import com.skoo.core.utils.JsonUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.sys.domain.Dict;
import com.skoo.stock.sys.service.DictService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public class DictJsonBeanProcessor implements JsonBeanProcessor {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DictJsonBeanProcessor.class);

    private String dateFomat;
    private DictService dictService;

    public DictJsonBeanProcessor() {
        dictService = SpringContextUtil.getBean(DictService.class);
    }

    public DictJsonBeanProcessor(String dateFomat) {
        dictService = SpringContextUtil.getBean(DictService.class);
        this.dateFomat = dateFomat;
    }

    @Override
    public JSONObject processBean(Object bean, JsonConfig config) {
        JSONObject o = null;
        try {
            o = JsonUtils.bean2JSONObject(BeanUtils.cloneBean(bean), dateFomat);
            for (Field field : bean.getClass().getDeclaredFields()) {
                DictAnnotation annotation = field
                        .getAnnotation(DictAnnotation.class);
                if (annotation != null
                        && !StringUtils.isEmpty(annotation.key())) {
                    String key = annotation.key();
                    String value = BeanUtils.getProperty(bean, field.getName());
                    Map<String, Dict> map = dictService.getDictsByKey(key);
                    Dict dic = map.get(value);
                    if (dic != null) {
                        o.put(field.getName() + annotation.name(),
                                dic.getContent());
                    } else {
                        o.put(field.getName() + annotation.name(),
                                annotation.nullValue());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("processBean", e);
        }
        return o;
    }
}