package com.tool.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-15
 * Time: 13:35
 */
public enum AddEnum {
    add("新增"),
    save("存量"),
    unknown("未知");
    private String type;

    AddEnum(String type){

        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AddEnum getEnumByName(String type){
        switch (type){
            case "新增":
                return add;
            case "存量":
                return save;
            default:
                return  unknown;
        }
    }
}
