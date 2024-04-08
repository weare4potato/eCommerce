package com.potato.ecommerce.domain.receiver.repository;


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
        return Receiver.fromEntity(receiverRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(ExceptionMessage.RECEIVER_NOT_FOUND.toString())));
    }

    @Override
    public List<Receiver> findAll(Long id) {
        return receiverRepository.findAllByMember_Id(id)
            .stream().map(Receiver::fromEntity).toList();
    }

    @Override
    public void save(Receiver receiver) {
        receiverRepository.save(receiver.toEntity());
    }

    @Override
    public void update(Receiver receiver) {
        receiverRepository.save(receiver.toEntity());
    }

    @Override
    public void delete(Receiver receiver) {
        receiverRepository.delete(receiver.toEntity());
    }
}
