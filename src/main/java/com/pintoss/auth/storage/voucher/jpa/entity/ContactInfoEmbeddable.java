package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.ContactInfo;
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

    public ContactInfoEmbeddable(ContactInfo contactInfo) {
        this.homePageEmbeddable = new HomePageEmbeddable(contactInfo.getHomePage());
        this.csCenterEmbeddable = new CsCenterEmbeddable(contactInfo.getCsCenter());
    }

    public ContactInfo toDomain() {
        return new ContactInfo(
                homePageEmbeddable.toDomain(),
                csCenterEmbeddable.toDomain()
        );
    }
}
