package com.mailapp.sourceprovider;

import android.content.Context;


import com.mailapp.enumeration.SourceType;
import com.mailapp.sourceop.HttpStreamOP;

import java.util.HashMap;
import java.util.Map;

public class SourceMap {

    public static Map<SourceType,ISource> generateSource(Context context){
        Map<SourceType, ISource> sourceMap = new HashMap<>();
        sourceMap.put(SourceType.SOURCE_FROM_SERVER,new HttpStreamOP());
        return sourceMap;
    }
}
