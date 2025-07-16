package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.Voucher;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "voucher_issuer_id")
    private Long voucherIssuerId;

    private String name;

    private String issuerName;

    private Long price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private VoucherEntity(Long voucherIssuerId, String name, String issuerName, Long price) {
        this.voucherIssuerId = voucherIssuerId;
        this.name = name;
        this.issuerName = issuerName;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static VoucherEntity create(Long voucherIssuerId, String name, String issuerName, Long price) {
        return new VoucherEntity(voucherIssuerId, name, issuerName, price);
    }

    public static VoucherEntity from(Voucher voucher) {
        return VoucherEntity.create(
                voucher.getVoucherIssuerId(),
                voucher.getName(),
                voucher.getIssuerName(),
                voucher.getPrice()
        );

    }
}
