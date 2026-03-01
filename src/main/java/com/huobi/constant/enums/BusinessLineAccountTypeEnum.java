package com.huobi.constant.enums;

import lombok.Getter;

@Getter
public enum BusinessLineAccountTypeEnum {
    SPOT("spot"),
    LINEARSWAP("linear-swap"),
    FUTURES("futures"),
    SWAP("swap")
    ;


    private final String type;

    BusinessLineAccountTypeEnum(String type) {
        this.type = type;
    }

    public static BusinessLineAccountTypeEnum find(String type) {
        for (BusinessLineAccountTypeEnum typeEnum : BusinessLineAccountTypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
