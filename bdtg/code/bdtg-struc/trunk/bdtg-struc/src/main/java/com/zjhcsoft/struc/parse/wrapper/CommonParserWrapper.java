package com.zjhcsoft.struc.parse.wrapper;

import com.zjhcsoft.struc.parse.CommonParser;

import java.util.Map;

/**
 * Created by allenwc on 14/11/19.
 */
public abstract class CommonParserWrapper {

    protected CommonParser commonParser;

    public CommonParserWrapper(CommonParser commonParser) {
        this.commonParser = commonParser;
    }

    public abstract Map<Object, String> parse(Object object, String url);
}
