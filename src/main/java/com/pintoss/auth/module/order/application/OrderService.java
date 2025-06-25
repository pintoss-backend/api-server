package com.pintoss.auth.module.order.application;

import com.pintoss.auth.common.client.billgate.PurchaseApiClient;
import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.domain.Order;
import com.pintoss.auth.module.order.domain.OrderItem;
import com.pintoss.auth.module.order.integration.PurchaseResult;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderReader orderReader;
    private final PurchaseApiClient purchaseApiClient;

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
            PurchaseResult purchaseResult = purchaseApiClient.purchase(
                orderNo,
                transId,
                mId,
                orderItem.getPrice(),
                paymentMethodType,
                orderItem.getPrice(),
                orderItem.getProductCode()
//                "1104501710200000"
            );
            if (purchaseResult.isSuccess()) {
                orderItem.assignPinNum(purchaseResult.getPinNo());
                orderItem.assignApprovalCode(purchaseResult.getApprovalCode());
                orderItem.issued();
            }else{
                orderItem.issueFailed();
            }
        }
        order.issued();
    }
}
