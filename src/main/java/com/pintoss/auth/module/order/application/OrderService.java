package com.pintoss.auth.module.order.application;

import com.pintoss.auth.module.order.application.flow.OrderItemPurchaseResponseAdder;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.order.application.model.OrderItem;
import com.pintoss.auth.module.order.application.model.OrderItemPurchaseResponse;
import com.pintoss.auth.module.order.integration.PurchaseResponse;
import com.pintoss.auth.module.order.integration.VoucherPurchaseClient;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderReader orderReader;
    private final VoucherPurchaseClient voucherPurchaseClient;
    private final OrderItemPurchaseResponseAdder orderItemPurchaseResponseAdder;

    @Transactional
    public void markAsPaid(String orderNo, Long paymentId) {
        // 주문 번호로 주문을 조회
        Order order = orderReader.getByOrderNo(orderNo);

        // 주문 상태를 결제 완료로 변경
        order.markAsPaid(paymentId);

        // 추가적인 비즈니스 로직 처리 (예: 알림 전송 등)
    }

    @Transactional
    public void assignPinToVoucher(String orderNo, String transId, String mId, PaymentMethodType paymentMethodType) {
        Order order = orderReader.getByOrderNo(orderNo);

        for (OrderItem orderItem : order.getOrderItems()) {
            PurchaseResponse purchaseResponse = voucherPurchaseClient.purchase(
                orderNo,
                transId,
                mId,
                orderItem.getPrice(),
                paymentMethodType,
                orderItem.getPrice(),
                "1104501710200000"
            );
            orderItem.assignPinNum(purchaseResponse.getCardNo());
            OrderItemPurchaseResponse orderItemPurchaseResponse = OrderItemPurchaseResponse.create(
                orderItem.getId(),
                purchaseResponse.getApprovalCode(),
                purchaseResponse.getRemainPrice(),
                purchaseResponse.getItemName()
            );
            orderItemPurchaseResponseAdder.add(orderItemPurchaseResponse);
        }
        order.issued();
    }
}
