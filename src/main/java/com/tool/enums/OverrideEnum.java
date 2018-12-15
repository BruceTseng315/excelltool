package com.tool.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-15
 * Time: 13:09
 */
public enum OverrideEnum {
    outside("宏站"),
    inside("室分"),
    unknown("未知");
    private String type;

    OverrideEnum(String type){

        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OverrideEnum getEnumByName(String type){
        switch (type){
            case "宏站":
                return outside;
            case "室分":
                return inside;
                default:
                    return  unknown;
        }
    }
}
