package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.HomePage;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HomePageEmbeddable {
    private String url;

    public HomePageEmbeddable(HomePage homePage) {
        this.url = homePage.getUrl();
    }

    public HomePage toDomain() {
        return new HomePage(url);
    }
}
