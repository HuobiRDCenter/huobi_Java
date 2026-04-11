package com.huobi.constant.enums;

import lombok.Getter;

@Getter
public enum AccountActionEnum {

    ACTION_SUB("sub"),

    ACTION_UNSUB("unsub"),
    ;

    private final String code;

    AccountActionEnum(String code) {
        this.code = code;
    }
}
