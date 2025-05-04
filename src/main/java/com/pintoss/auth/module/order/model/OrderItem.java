package com.pintoss.auth.module.order.model;

import com.pintoss.auth.common.exception.client.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @Column(nullable = false, name = "vocuher_issuer_name")
    private String voucherIssuerName;

    @Column(nullable = false, name = "voucher_name")
    private String voucherName;

    private Long price;

    private OrderItem(String voucherIssuerName, String voucherName, Long price) {
        this.voucherIssuerName = voucherIssuerName;
        this.voucherName = voucherName;
        this.price = price;
    }

    public static OrderItem create(String voucherIssuerName, String voucherName, Long price) {
        return new OrderItem(voucherIssuerName, voucherName, price);
    }

    public void assignOrder(Order order) {
        this.order = order;
    }

    public void validatePriceMatch(Long price) {
        if(!this.price.equals(price)) {
            throw new BadRequestException("가격 불일치");
        }
    }
}
