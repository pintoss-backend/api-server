package com.pintoss.auth.api.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

import com.galaxia.api.util.ChecksumUtil;
import com.pintoss.auth.api.security.SecurityContextUtils;
import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.core.order.application.dto.OrderCreateResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreateResponse {

    private String serviceId;
    private String orderNo;
    private Long ordererId;
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String serviceCode;
    private Long price;
    private String productName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime orderDate;
    private String checkSum;
    private String checkSumHp;
    //    private String returnUrl; TODO : 추후 예정

    public static OrderCreateResponse of(String serviceId, OrderCreateResult result, String serviceCode) {
        String checkSum = null;
        String checkSumHp = null;
        try {
            checkSum = ChecksumUtil.genCheckSum(
                    serviceId + result.getOrderNo() + result.getTotalPrice()
            );
            checkSumHp = ChecksumUtil.genCheckSum(SecurityContextUtils.getPhone());
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.BILLGATE_CHECHKSUM_GENERATION_FAILED);
        }

        return new OrderCreateResponse(
                serviceId,
                result.getOrderNo(),
                SecurityContextUtils.getUserId(),
                SecurityContextUtils.getName(),
                SecurityContextUtils.getEmail(),
                SecurityContextUtils.getPhone(),
                serviceCode,
                result.getTotalPrice(),
                result.getOrderName(),
                result.getCreatedAt(),
                checkSum,
                checkSumHp
        );
    }
}
