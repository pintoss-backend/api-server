package com.pintoss.auth.module.order.application.flow;

import com.pintoss.auth.common.exception.ErrorCode;
import com.pintoss.auth.common.exception.client.BadRequestException;
import com.pintoss.auth.module.order.domain.Order;
import com.pintoss.auth.module.order.domain.OrderDetail;
import com.pintoss.auth.module.order.domain.OrderPageCommand;
import com.pintoss.auth.module.order.domain.OrderSearchResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
* getXxx : 반드시 존재해야하고, 없으면 예외 발생
* findXxx : 존재하지 않을 수도 있으며, Optional 또는 null 처리 가능성 내포
* searchXxx : 리스트 / 조건 기반 조회, 검색 기능
* countXxx : 총 개수 반환, 통계나 페이징용
* */
@Component
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    // 주문 번호로 주문 도메인 객체 조회
    public Order getByOrderNo(String orderNo){
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND));
    }

    // 주문 상세 정보 조회
    public OrderDetail getDetailById(Long orderId) {
        return orderRepository.findDetailByOrderId(orderId).orElseThrow(
            () -> new BadRequestException(ErrorCode.ORDER_NOT_FOUND)
        );
    }

    // 사용자 기준 검색 결과 조회
    public List<OrderSearchResult> searchByUserId(Long userId, OrderPageCommand command) {
        return orderRepository.searchByUserIdAndWithCondition(userId, command);
    }

    // 검색 결과 개수 조회
    public long countByUserId(Long userId, OrderPageCommand command) {
        return orderRepository.countByUserIdWithCondition(userId, command);
    }
}
