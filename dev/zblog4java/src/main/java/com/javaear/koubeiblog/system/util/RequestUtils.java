package com.javaear.koubeiblog.system.util;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {

    public static <T> T copyProperties(HttpServletRequest request, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            //获取requestParameterMap里边的参数，过滤空值属性，并且组装成map，若值为string[]，则取值最后一个元素
            Map<String, String> paramterMap = request.getParameterMap().entrySet().stream().filter(e -> !StringUtils.isEmpty(e.getValue()[0]))
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().length > 0 ? e.getValue()[e.getValue().length - 1] : e.getValue()[0]));
            BeanUtils.copyProperties(bean, paramterMap);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
