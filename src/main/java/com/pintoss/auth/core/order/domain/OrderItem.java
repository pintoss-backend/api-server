package com.pintoss.auth.core.order.domain;

import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String pinNum;

    @Column(nullable = true)
    private String approvalCode;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @Column(nullable = false)
    private String productCode;

    private OrderItem(String voucherIssuerName, String voucherName, Long price, String productCode) {
        this.voucherIssuerName = voucherIssuerName;
        this.voucherName = voucherName;
        this.price = price;
        this.status = OrderItemStatus.PENDING;
        this.productCode = productCode;
    }

    public static OrderItem create(String voucherIssuerName, String voucherName, Long price, String productCode) {
        return new OrderItem(voucherIssuerName, voucherName, price, productCode);
    }

    public void assignOrder(Order order) {
        this.order = order;
    }

    public void assignApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public void assignPinNum(String pinNum) {
        this.pinNum = pinNum;
        issued();
    }

    public void issued() {
        this.status = OrderItemStatus.ISSUED;
    }

    public void issueProcessing() {
        if (this.status == OrderItemStatus.ISSUED) {
            throw new BadRequestException(ErrorCode.ORDER_ITEM_ALREADY_ISSUED);
        }
        this.status = OrderItemStatus.PROCESSING;
    }

    public void markAsRefunded() {
        if (this.status != OrderItemStatus.ISSUED) {
            throw new BadRequestException(ErrorCode.ORDER_ITEM_NOT_ISSUED);
        }
        this.status = OrderItemStatus.REFUNDED;
    }

    public void markAsRefundFail() {
        if (this.status != OrderItemStatus.ISSUED) {
            throw new BadRequestException(ErrorCode.ORDER_ITEM_NOT_ISSUED);
        }
        this.status = OrderItemStatus.REFUND_FAIL;

    }

    public boolean isRefunded() {
        return this.status == OrderItemStatus.REFUNDED;
    }

    public boolean isRefundFailed() {
        return this.status == OrderItemStatus.REFUND_FAIL;
    }

    public void issueFailed() {
        this.status = OrderItemStatus.ISSUE_FAILED;
    }

    public void updateStatus(OrderItemStatus status, String pinNum, String approvalCode) {
        this.status = status;
        this.pinNum = pinNum;
        this.approvalCode = approvalCode;
    }
}
