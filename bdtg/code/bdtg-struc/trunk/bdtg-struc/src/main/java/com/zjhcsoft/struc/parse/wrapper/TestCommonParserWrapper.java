package com.zjhcsoft.struc.parse.wrapper;

import com.zjhcsoft.struc.parse.CommonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by allenwc on 14/11/19.
 */
public class TestCommonParserWrapper extends CommonParserWrapper{


    public TestCommonParserWrapper(CommonParser commonParser) {
        super(commonParser);
    }

    @Override
    public Map<Object, String> parse(Object object, String url) {
        System.out.println("自定义解析存储");
        return new HashMap<Object,String>();
    }
}
