package org.jeecg.modules.newbie.enums;

/**
 * 待办事项种类
 * 1: 样品领用申请单， 2: ...
 */
public enum TodoCategoryEnum {
    SAMPLE_REQUEST(1, "样品领用申请单");

    private final int code;
    private final String desc;

    TodoCategoryEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static TodoCategoryEnum fromCode(Integer code) {
        if (code == null) return null;
        for (TodoCategoryEnum category : values()) {
            if (category.code == code) return category;
        }
        return null;
    }
}
