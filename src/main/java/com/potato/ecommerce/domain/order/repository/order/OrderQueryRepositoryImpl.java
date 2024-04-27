package com.potato.ecommerce.domain.order.repository.order;

import static com.potato.ecommerce.domain.order.entity.QOrderEntity.orderEntity;
import static com.querydsl.core.types.Projections.fields;

import com.potato.ecommerce.domain.order.dto.OrderList;
import com.potato.ecommerce.domain.order.entity.QOrderEntity;
import com.potato.ecommerce.global.util.RestPage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RestPage<OrderList> getOrders(String subject, int page, int size) {
        QOrderEntity order = orderEntity;

        List<OrderList> orders =
            jpaQueryFactory.select(fields(OrderList.class,
                    order.id,
                    order.status,
                    order.orderedAt,
                    order.orderNum))
                .from(order)
                .where(order.member.email.eq(subject))
                .orderBy(order.orderedAt.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();

        long count = jpaQueryFactory.select(order.id.count()).from(order).where(order.member.email.eq(subject)).fetchOne();
        Pageable pageable = PageRequest.of(page, size);

        return new RestPage<>(new PageImpl<>(orders, pageable, count));
    }
}
