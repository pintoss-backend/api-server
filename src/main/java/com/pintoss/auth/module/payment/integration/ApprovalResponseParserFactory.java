package com.pintoss.auth.module.payment.integration;

import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.payment.application.PaymentMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalResponseParserFactory {

    private final ApprovalCreditResponseParser approvalCreditResponseParser;
    private final ApprovalMobileResponseParser approvalMobileResponseParser;

    public ApprovalResponseParser<?> getParser(PaymentMethodType paymentMethod) {
        switch (paymentMethod) {
            case CARD:
                return approvalCreditResponseParser;
            case PHONE:
                return approvalMobileResponseParser;
            default:
                throw new BadRequestException("Unsupported payment method: " + paymentMethod);
        }
    }
}
