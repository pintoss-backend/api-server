package com.pintoss.auth.core.order.application.flow.reader;

import com.pintoss.auth.core.order.application.repository.OrderRepository;
import com.pintoss.auth.core.order.application.dto.OrderPageCommand;
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

    public long countByUserId(Long userId, OrderPageCommand command) {
        return orderRepository.countByUserIdWithCondition(userId, command);
    }
}
