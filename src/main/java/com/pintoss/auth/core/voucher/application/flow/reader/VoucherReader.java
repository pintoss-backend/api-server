package com.pintoss.auth.core.voucher.application.flow.reader;

import com.pintoss.auth.core.voucher.application.repository.VoucherRepository;
import com.pintoss.auth.core.voucher.domain.Voucher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * 도메인 객체 조회용 ( 비즈니스 로직용 )
 * 1. 단건 조회 (ID 기준) : findById, findBy...
 * 2. 여러건 조회 (조건) : findAllBy, findBy...
 * 3. 조회 실패 시 예외를 던지는 경우 : getById, getBy...
 * find -> 조회하지 않을 수 없음 ( Optional 반환 )
 * get -> 반드시 존재해야 함 ( 없으면 예외 발생 )
 *
 * */
@Component
@RequiredArgsConstructor
public class VoucherReader {

    private final VoucherRepository voucherRepository;

    public List<Voucher> getAll(List<Long> voucherIds) {
        return voucherRepository.findAllByIds(voucherIds);
    }


}
