#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.utils.json;

        import net.ryian.commons.BeanUtils;
        import net.ryian.core.utils.JsonUtils;
        import net.ryian.core.utils.SpringContextUtil;
        import ${package}.sys.domain.Dict;
        import ${package}.sys.service.DictService;
        import net.sf.json.JSONObject;
        import net.sf.json.JsonConfig;
        import net.sf.json.processors.JsonBeanProcessor;
        import org.apache.commons.lang.StringUtils;

        import java.lang.reflect.Field;
        import java.util.Map;

public class DictJsonBeanProcessor implements JsonBeanProcessor {

    private DictService dictService;

    public DictJsonBeanProcessor() {
        dictService = SpringContextUtil
                .getBean(DictService.class);
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