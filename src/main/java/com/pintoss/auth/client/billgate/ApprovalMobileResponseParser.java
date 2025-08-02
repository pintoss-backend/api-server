package com.pintoss.auth.client.billgate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxia.api.merchant.Message;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.InternalServerException;
import com.pintoss.auth.core.payment.application.dto.PaymentApprovalResponse;
import com.pintoss.auth.core.payment.domain.PaymentMethodType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalMobileResponseParser implements ApprovalResponseParser<ApprovalMobileResponse> {

    private final ObjectMapper objectMapper;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public PaymentApprovalResponse parse(Message message, Map<String, String> data) {
        // 공통 응답 필드
        ApprovalCommonResponse common = ApprovalCommonResponse.from(
            message.getVersion(),
            message.getMerchantId(),
            message.getServiceCode(),
            message.getCommand(),
            message.getOrderId(),
            message.getOrderDate(),
            data.get("1001"),
            data.get("1002"),
            data.get("1003"),
            data.get("1009"),
            data.get("1010"),
            data.get("1005"),
            data.get("1007"),
            data.get("5029"),
            data.get("5030"),
            data.get("5031")
        );
        ApprovalMobileResponse mobileResponse = ApprovalMobileResponse.builder()
            .partCancelType(safeGet(data, "7049"))
            .mobileNumber(safeGet(data, "0007"))
            .build();

        String json ;
        try {
            json = objectMapper.writeValueAsString(mobileResponse);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.JSON_SERIALIZATION_ERROR);
        }
        return PaymentApprovalResponse.builder()
            .isSuccess(common.getIsSuccess())
            .paymentMethodType(PaymentMethodType.PHONE)
            .transactionId(common.getTransactionId())
            .responseCode(common.getResponseCode())
            .responseMessage(common.getResponseMessage())
            .detailResponseCode(common.getDetailResponseCode())
            .detailResponseMessage(common.getDetailResponseMessage())
            .orderId(common.getOrderId())
            .orderDate(safeParseDateOrNull(common.getOrderDate(), formatter))
            .authAmount(safeParseLongOrDefault(common.getAuthAmount(), 0L))
            .authDate(safeParseDateOrNull(common.getAuthDate(), formatter))
            .json(json)
            .build();
    }

    private String safeGet(Map<String, String> data, String key) {
        String value = data.get(key);
        return (value != null && !"null".equalsIgnoreCase(value)) ? value : "";
    }

    private Long safeParseLongOrDefault(String value, long defaultValue) {
        try {
            return value != null && !value.isBlank() ? Long.parseLong(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private LocalDateTime safeParseDateOrNull(String value, DateTimeFormatter formatter) {
        try {
            return value != null && !value.isBlank() ? LocalDateTime.parse(value, formatter) : null;
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
