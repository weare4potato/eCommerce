package com.potato.ecommerce.domain.receiver.repository;

import com.potato.ecommerce.domain.receiver.model.Receiver;

public interface ReceiverRepository {

    Receiver findBy(Long id);

    void save(Receiver receiver);

    void update(Receiver receiver);

}
