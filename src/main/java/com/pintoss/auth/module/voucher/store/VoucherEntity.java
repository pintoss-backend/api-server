package com.pintoss.auth.module.voucher.store;

import com.pintoss.auth.module.voucher.model.Voucher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
