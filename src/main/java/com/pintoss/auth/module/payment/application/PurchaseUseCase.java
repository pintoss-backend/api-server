package com.pintoss.auth.module.payment.application;

import com.pintoss.auth.module.order.application.flow.OrderReader;
import com.pintoss.auth.module.order.application.model.Order;
import com.pintoss.auth.module.payment.application.flow.PaymentAdder;
import com.pintoss.auth.module.payment.application.flow.PaymentApprovalService;
import com.pintoss.auth.module.payment.application.model.Payment;
import com.pintoss.auth.module.payment.integration.PaymentApprovalResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseUseCase {

    private final PaymentApprovalService paymentApprovalService;
    private final OrderReader orderReader;
    private final PaymentAdder paymentAdder;

    @Transactional
    public void purchase(PurchaseCommand command) {
        Order order = orderReader.read(command.getOrderNo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        Payment payment = new Payment(
            command.getServiceId(),
            command.getServiceCode(),
            command.getOrderNo(),
            LocalDateTime.parse(command.getOrderDate(),formatter)
        );

        if (command.getMessage() == null || command.getMessage().isEmpty()) {
            payment.fail();
        }
        System.out.println(command.getMessage());
        PaymentApprovalResponse approvalResponse = paymentApprovalService.approval(command.getServiceCode(),
            command.getOrderNo(), command.getMessage());

        if (approvalResponse.isSuccess()) {
            payment.addAuthInfo(
                approvalResponse.getMid(),
                approvalResponse.getTransactionId(),
                approvalResponse.getAuthNumber(),
                Long.parseLong(approvalResponse.getAuthAmount()),
                LocalDateTime.parse(approvalResponse.getAuthDate(), formatter)
            );
            payment.success();
        } else {
            payment.fail();
        }
        paymentAdder.add(payment);
    }
}



// TODO 5 : 상품권 구매 API를 요청한다.
// TODO 5-1 : 서비스 코드를 이용해서 결제 수단 별 전략 패턴을 구현해서 동적으로 상품권 구매 API를 호출한다.
//        PurchaseResponse purchase = voucherPurchaseClient.purchase(command.getOrderNo(),
//            command.getMessage(), command.getTransactionId(), command.getServiceId(),
//            approvalResponse.getAuthAmount());
