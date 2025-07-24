package com.pintoss.auth.core.payment.application;

import com.galaxia.api.util.ChecksumUtil;
import com.pintoss.auth.core.order.application.flow.validator.OrderValidator;
import com.pintoss.auth.core.order.domain.Order;
import com.pintoss.auth.core.payment.application.dto.PaymentCreateCommand;
import com.pintoss.auth.core.payment.application.dto.PaymentCreateResult;
import com.pintoss.auth.core.payment.application.flow.writer.PaymentAdder;
import com.pintoss.auth.core.payment.domain.PaymentDomain;
import com.pintoss.auth.core.payment.domain.PaymentStatus;
import com.pintoss.auth.core.support.config.BillgateProperties;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.InternalServerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentCreateUsecase {

    private final BillgateProperties billgateProperties;
    private final OrderValidator orderValidator;
    private final PaymentAdder paymentAdder;

    public PaymentCreateUsecase(BillgateProperties billgateProperties, OrderValidator orderValidator, PaymentAdder paymentAdder) {
        this.billgateProperties = billgateProperties;
        this.orderValidator = orderValidator;
        this.paymentAdder = paymentAdder;
    }

    @Transactional
    public PaymentCreateResult createPayment(PaymentCreateCommand command) {
        Order order = orderValidator.getOrThrowIfNotExists(command.getOrderNo());

        PaymentDomain payment = PaymentDomain.create(
                billgateProperties.getServiceId(),
                order.getOrderNo(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                PaymentStatus.PENDING,
                command.getPaymentMethod()
        );

        paymentAdder.add(payment);

        return PaymentCreateResult.from(
            order,
            payment,
            calculateChecksum(billgateProperties.getServiceId() + order.getOrderNo() + order.getTotalPrice()),
            calculateChecksum(order.getOrdererPhone())
        );
    }

    private String calculateChecksum(String value) {
        try{
            return ChecksumUtil.genCheckSum(value);
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.BILLGATE_CHECHKSUM_GENERATION_FAILED);
        }
    }
}
