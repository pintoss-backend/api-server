package com.pintoss.auth.module.order.application.model;

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

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @OneToMany(
        mappedBy = "order",
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
        orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Order(Long ordererId, String ordererName, String ordererEmail, String ordererPhone, String orderName, List<OrderItem> orderItems, PaymentMethodType paymentMethodType) {
        this.ordererId = ordererId;
        this.ordererName = ordererName;
        this.ordererEmail = ordererEmail;
        this.ordererPhone = ordererPhone;
        this.orderNo = generateOrderNo();
        this.orderName = orderName;
        this.paymentMethodType = paymentMethodType;
        orderItems.forEach(this::addOrderItem); // 연관관계 메서드 사용
        this.orderItems = orderItems;
        this.status = OrderStatus.PENDING;
        this.totalPrice = orderItems.stream().mapToLong(item -> item.getPrice()).sum();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

    public static Order create(Long ordererId, String ordererName, String ordererEmail, String ordererPhone, String orderName, List<OrderItem> orderItems, PaymentMethodType paymentMethodType) {
        return new Order(ordererId, ordererName, ordererEmail, ordererPhone, orderName, orderItems, paymentMethodType);
    }

    public void cancel() {
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 주문 입니다.");
        }
        this.status = OrderStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }
}
