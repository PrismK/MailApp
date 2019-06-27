package com.mailapp.enumeration;


//枚举
public enum SourceType {

    SOURCE_FROM_SERVER("SOURCE_FROM_SERVER"),
    SOURCE_FROM_DB("SOURCE_FROM_DB");

    private String type;

    SourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
