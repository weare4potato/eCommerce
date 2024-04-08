package com.potato.ecommerce.domain.order.repository.history;

import com.potato.ecommerce.domain.order.model.History;
import java.util.List;

public interface HistoryRepository {

    void save(History history);

    void update(History history);

    void delete(History history);

    List<History> findAllByOrderId(Long orderId);
}
