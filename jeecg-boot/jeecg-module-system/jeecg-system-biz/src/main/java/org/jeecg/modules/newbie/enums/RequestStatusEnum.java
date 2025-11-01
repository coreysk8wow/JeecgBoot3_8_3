package org.jeecg.modules.newbie.enums;

/**
 * 请求单审批状态
 * '审批状态。0:待办, 1:通过, 2:拒绝',
 */
public enum RequestStatusEnum {
    PENDING(0, "待办"),
    APPROVED(1, "通过"),
    REJECTED(2, "拒绝");

    private final int code;
    private final String name;

    RequestStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RequestStatusEnum fromCode(Integer code) {
        if (code == null) return null;
        for (RequestStatusEnum status : values()) {
            if (status.code == code) return status;
        }
        return null;
    }
}
