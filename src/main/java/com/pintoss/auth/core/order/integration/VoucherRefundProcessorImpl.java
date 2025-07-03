package com.pintoss.auth.core.order.integration;

import com.pintoss.auth.common.client.billgate.RefundApiClient;
import com.pintoss.auth.common.client.billgate.RefundRequestBuilder;
import com.pintoss.auth.common.client.billgate.RefundResponse;
import com.pintoss.auth.core.order.application.flow.VoucherRefundProcessor;
import com.pintoss.auth.core.order.domain.RefundResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoucherRefundProcessorImpl implements VoucherRefundProcessor {

    private final RefundApiClient refundApiClient;

    @Override
    public RefundResult refund(String orderNo, String approvalCode) {
        String requestHeader = RefundRequestBuilder.buildHeader(orderNo);
        String requestBody = RefundRequestBuilder.buildBody(approvalCode);

        RefundResponse refundResponse = refundApiClient.execute(requestHeader, requestBody);
        if (refundResponse.getResponseCode().equals("0000")) {
            log.info("[환불 성공] orderNo: {}, approvalCode: {}, response: {}", orderNo, approvalCode, refundResponse);
            return RefundResult.success();
        } else {
            log.error("[환불 실패] orderNo: {}, approvalCode: {}, response: {}", orderNo, approvalCode, refundResponse);
            return RefundResult.failure(refundResponse.getPrintMsg1());
        }
    }
}
