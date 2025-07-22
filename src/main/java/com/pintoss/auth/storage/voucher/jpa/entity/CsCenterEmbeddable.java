package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.CsCenter;
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
public class CsCenterEmbeddable {

    @NotBlank
    @Size(min = 1, max = 20)
    @Column(length = 20, nullable = false)
    private String tel;

    public CsCenterEmbeddable(CsCenter csCenter) {
        this.tel = csCenter.getTel();
    }

    public CsCenter toDomain() {
        return new CsCenter(tel);
    }
}
