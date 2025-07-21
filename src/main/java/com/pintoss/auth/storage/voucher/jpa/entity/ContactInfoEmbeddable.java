package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.ContactInfo;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContactInfoEmbeddable {

    @Valid
    private HomePageEmbeddable homePageEmbeddable;

    @Valid
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
