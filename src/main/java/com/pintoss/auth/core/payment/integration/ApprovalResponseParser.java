package com.pintoss.auth.core.payment.integration;

import com.galaxia.api.merchant.Message;
import com.pintoss.auth.core.payment.application.PaymentApprovalResponse;
import java.util.Map;

public interface ApprovalResponseParser<T> {

    PaymentApprovalResponse parse(Message message, Map<String, String> data);

}
