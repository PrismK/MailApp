package com.mailapp.sourceprovider;

import android.content.Context;

import com.mailapp.enumeration.SourceType;


public class SourceFactory {
    public static ISource sourceCreate(Context context, SourceType type){
        return SourceMap.generateSource(context).get(type);
    }
}
