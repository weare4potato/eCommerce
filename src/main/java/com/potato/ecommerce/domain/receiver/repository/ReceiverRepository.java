package com.potato.ecommerce.domain.receiver.repository;

import com.potato.ecommerce.domain.receiver.model.Receiver;
import java.util.List;

public interface ReceiverRepository {

    Receiver findBy(Long id);

    List<Receiver> findAll(Long id);

    void save(Receiver receiver);

    void update(Receiver receiver);

}
