package com.potato.ecommerce.domain.order.repository.history;

import com.potato.ecommerce.domain.order.model.History;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepository {

    private final HistoryJpaRepository historyJpaRepository;

    @Override
    public void save(History history) {
        historyJpaRepository.save(history.toEntity());
    }

    @Override
    public void update(History history) {
        historyJpaRepository.saveAndFlush(history.toEntity());
    }

    @Override
    public void delete(History history) {
        historyJpaRepository.delete(history.toEntity());
    }

    @Override
    public List<History> findAllByOrderId(Long orderId) {
        return historyJpaRepository.findAllByOrderId(orderId)
            .stream().map(History::fromEntity).toList();
    }
}
