package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.HomePage;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HomePageEmbeddable {

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String url;

    public HomePageEmbeddable(HomePage homePage) {
        this.url = homePage.getUrl();
    }

    public HomePage toDomain() {
        return new HomePage(url);
    }
}
