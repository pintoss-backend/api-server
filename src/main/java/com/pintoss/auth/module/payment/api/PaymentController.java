package com.pintoss.auth.module.payment.api;

import com.pintoss.auth.common.dto.ApiResponse;
import com.pintoss.auth.module.payment.application.PaymentService;
import com.pintoss.auth.module.payment.integration.PaymentCallbackRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * 결제 승인 콜백을 처리한다.
     * 클라이언트 또는 PG사로부터 콜백 데이터를 수신한 후,
     * 실제 결제 승인 요청을 수행하고 결과를 반환한다.
     *
     * @param request 결제 콜백 요청 데이터
    * */
    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ApiResponse<PaymentResponse> handlePaymentCallback(@ModelAttribute PaymentCallbackRequest request, HttpServletResponse response)
        throws IOException {
        paymentService.purchase(request.toPurchaseCommand());
        response.sendRedirect("https://pin-toss.com/payments/result?isSuccess=true&orderId="+request.getORDER_ID());
        return ApiResponse.ok(PaymentResponse.of(request.getORDER_ID()));
    }
}
