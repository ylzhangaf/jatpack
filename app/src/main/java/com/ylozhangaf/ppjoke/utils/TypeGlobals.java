package com.ylozhangaf.ppjoke.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ylozhangaf.ppjoke.model.Destination;

import java.lang.reflect.Type;
import java.util.HashMap;

public class TypeGlobals {
    private static HashMap<String, Destination> sDestConfig;

    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            String content = "main_tabs_config.json";
            sDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
            });
        }
        return sDestConfig;
    }
}
