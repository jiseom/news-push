package com.newspush.newspush.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PushType {
    FCM,APNS;

    public static PushType from(String value) {
        return valueOf(value.toUpperCase());
    }

}
