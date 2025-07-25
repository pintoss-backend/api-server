package com.pintoss.auth.core.voucher.domain;

import lombok.Getter;

@Getter
public class HomePage {

    private String url;

    public HomePage(String url) {
        this.url = url;
    }
}
