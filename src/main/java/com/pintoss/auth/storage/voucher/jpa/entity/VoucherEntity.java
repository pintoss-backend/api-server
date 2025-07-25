package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.Voucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "voucher_issuer_id")
    private Long voucherIssuerId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String issuerName;

    @Min(0)
    @NotNull
    @Column(nullable = false)
    private Long price;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static VoucherEntity create(Long voucherIssuerId, String name, String issuerName, Long price) {
        return VoucherEntity.builder()
                .voucherIssuerId(voucherIssuerId)
                .name(name)
                .issuerName(issuerName)
                .price(price)
                .build();
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
