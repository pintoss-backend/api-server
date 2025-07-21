package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.CsCenter;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CsCenterEmbeddable {

    @Column(length = 20)
    private String tel;

    public CsCenterEmbeddable(CsCenter csCenter) {
        this.tel = csCenter.getTel();
    }

    public CsCenter toDomain() {
        return new CsCenter(tel);
    }
}
