package com.pintoss.auth.api.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderCreateResponse {

    private String serviceId;

    private String orderNo;

    private Long ordererId;
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;

//    private String serviceType; TODO : 추후 예정

    private String serviceCode;

    private Long price;

    private String productName;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime orderDate;

    private String checkSum;
    private String checkSumHp;

//    private String returnUrl; TODO : 추후 예정

}
