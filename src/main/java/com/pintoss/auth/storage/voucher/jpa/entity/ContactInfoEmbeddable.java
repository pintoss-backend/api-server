package com.pintoss.auth.storage.voucher.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContactInfoEmbeddable {

    @Column(length = 100)
    private HomePageEmbeddable homePageEmbeddable;

    @Column(length = 20)
    private CsCenterEmbeddable csCenterEmbeddable;

    public ContactInfoEmbeddable(HomePageEmbeddable homePageEmbeddable, CsCenterEmbeddable csCenterEmbeddable) {
        this.homePageEmbeddable = homePageEmbeddable;
        this.csCenterEmbeddable = csCenterEmbeddable;
    }
}
