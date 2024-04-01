package com.potato.ecommerce.domain.receiver.repository;


import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReceiverRepositoryImpl implements ReceiverRepository {

    private final ReceiverJpaRepository receiverRepository;


    @Override
    public Receiver findBy(Long id) {
        return receiverRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.RECEIVER_NOT_FOUND.toString()))
            .toModel();
    }

    @Override
    public List<Receiver> findAll(Long id) {
        return receiverRepository.findAllByMember_Id(id)
            .stream().map(ReceiverEntity::toModel).toList();
    }

    @Override
    public void save(Receiver receiver) {
        receiverRepository.save(ReceiverEntity.fromModel(receiver));
    }

    @Override
    public void update(Receiver receiver) {
        receiverRepository.save(ReceiverEntity.fromModel(receiver));
    }
}
