package com.pintoss.auth.module.voucher.external.persistence;


import static com.pintoss.auth.module.voucher.model.QVoucher.voucher;

import com.pintoss.auth.module.voucher.model.Voucher;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoucherQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Voucher> findAllByIds(List<Long> voucherIds) {
        return queryFactory.selectFrom(voucher)
            .where(voucher.id.in(voucherIds))
            .fetch();
    }
}
