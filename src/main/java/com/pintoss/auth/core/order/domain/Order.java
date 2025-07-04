package com.pintoss.auth.core.order.domain;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.core.payment.application.PaymentMethodType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNo;

    @Column(nullable = false, name = "orderer_id")
    private Long ordererId;

    @Column(nullable = false, name = "orderer_name")
    private String ordererName;

    @Column(nullable = false, name = "orderer_email")
    private String ordererEmail;

    @Column(nullable = false, name = "orderer_phone")
    private String ordererPhone;

    @Column(nullable = false, name = "order_name")
    private String orderName;

    @Column(nullable = true)
    private Long paymentId;

    @OneToMany(
        mappedBy = "order",
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
        orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private long totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Order(Long ordererId, String ordererName, String ordererEmail, String ordererPhone, String orderName, List<OrderItem> orderItems,
        PaymentMethodType paymentMethod) {
        this.ordererId = ordererId;
        this.ordererName = ordererName;
        this.ordererEmail = ordererEmail;
        this.ordererPhone = ordererPhone;
        this.orderNo = generateOrderNo();
        this.orderName = orderName;
        orderItems.forEach(this::addOrderItem); // 연관관계 메서드 사용
        this.orderItems = orderItems;
        this.status = OrderStatus.PENDING;
        long basePrice = orderItems.stream().mapToLong(item -> item.getPrice()).sum();
        this.totalPrice = applyTaxPolicy(basePrice, paymentMethod);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private long applyTaxPolicy(long basePrice, PaymentMethodType paymentMethodType) {
        if (paymentMethodType == PaymentMethodType.PHONE) {
            return BigDecimal.valueOf(basePrice)
                .multiply(BigDecimal.valueOf(1.1))
                .setScale(0, RoundingMode.UP) // 올림
                .longValue();
        }
        return basePrice; // 다른 결제 수단은 세금 적용 없음
    }

    public void updateItemIssueResult(Long orderItemId, OrderItemStatus status, String pinNo, String approvalCode) {
        OrderItem item = orderItems.stream()
            .filter(i -> i.getId().equals(orderItemId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));
        item.updateStatus(status, pinNo, approvalCode);
    }

    public void syncOverallStatus() {
        long total = orderItems.size();
        long successCount = orderItems.stream().filter(i -> i.getStatus() == OrderItemStatus.ISSUED).count();
        long failedCount = orderItems.stream().filter(i -> i.getStatus() == OrderItemStatus.ISSUE_FAILED).count();
        long pendingCount = orderItems.stream().filter(i -> i.getStatus() == OrderItemStatus.PROCESSING).count();

        if (successCount == 0 && failedCount == 0 && pendingCount == total) {
            this.status = OrderStatus.ISSUE_PROCESSING;
        } else if (successCount == total) {
            this.status = OrderStatus.ISSUED;
        } else if (failedCount == total) {
            this.status = OrderStatus.ISSUE_FAILED;
        } else if (successCount > 0 && failedCount > 0) {
            this.status = OrderStatus.ISSUED_PARTIAL;
        } else {
            this.status = OrderStatus.PENDING;
        }
    }

    private String generateOrderNo() {
        String datePart = LocalDate.now().format(DATE_FORMAT); // "YYYYMMdd"
        int randomPart = 10000000 + RANDOM.nextInt(90000000); // 8자리 랜덤 숫자
        return datePart + randomPart;
    }

    // 개별 OrderItem 추가 메서드 (연관관계 설정)
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.assignOrder(this);
    }

    public static Order create(Long ordererId, String ordererName, String ordererEmail, String ordererPhone, String orderName, List<OrderItem> orderItems,
        PaymentMethodType paymentMethod) {
        return new Order(ordererId, ordererName, ordererEmail, ordererPhone, orderName, orderItems, paymentMethod);
    }

    public void verifyTotalPrice(long taxAmount) {
        if(totalPrice != taxAmount) {
            throw new BadRequestException(ErrorCode.PAYMENT_APPROVED_AMOUNT_MISMATCH);
        }
    }

    public void markAsPaymentFailed(){
        if (this.status == OrderStatus.PAYMENT_FAILED) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_PAYMENT_FAILED);
        }
        this.status = OrderStatus.PAYMENT_FAILED;
        this.updatedAt = LocalDateTime.now();
    }
    public void cancel() {
        if (this.status == OrderStatus.CANCELED) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_CANCELED);
        }
        this.status = OrderStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }

    public void issued() {
        this.status = OrderStatus.ISSUED;
    }

    public void markAsPaid(Long paymentId) {
        if (this.status == OrderStatus.PAID) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_PAID);
        } else if (this.status == OrderStatus.CANCELED) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_CANCELED);
        } else if (this.status == OrderStatus.ISSUED) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_ISSUED);
        }
        this.paymentId = paymentId;
        this.status = OrderStatus.PAID;
        this.updatedAt = LocalDateTime.now();
        for (OrderItem orderItem : orderItems) {
            orderItem.issueProcessing();
        }
    }

    public void assignPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void markAsRefunded() {
        if (this.status != OrderStatus.PAID && this.status != OrderStatus.ISSUED) {
            throw new BadRequestException(ErrorCode.ORDER_NOT_REFUNDABLE);
        }

        boolean allRefunded = this.orderItems.stream().allMatch(OrderItem::isRefunded);
        boolean allFailed = this.orderItems.stream().allMatch(OrderItem::isRefundFailed);

        if (allRefunded) {
            this.status = OrderStatus.REFUNDED;
        } else if (allFailed) {
            this.status = OrderStatus.REFUND_FAILED;
        } else {
            this.status = OrderStatus.PARTIAL_REFUNDED;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void validateRefundable() {
        if (this.status == OrderStatus.REFUNDED || this.status == OrderStatus.REFUND_FAILED || this.status == OrderStatus.PARTIAL_REFUNDED) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_REFUNDED);
        } else if (this.status == OrderStatus.CANCELED) {
            throw new BadRequestException(ErrorCode.ORDER_ALREADY_CANCELED);
        } else if (this.status != OrderStatus.PAID && this.status != OrderStatus.ISSUED) {
            throw new BadRequestException(ErrorCode.ORDER_NOT_REFUNDABLE);
        }
    }
}
