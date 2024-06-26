package com.potato.ecommerce.domain.receiver.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.MEMBER_NOT_FOUND;
import static com.potato.ecommerce.global.exception.ExceptionMessage.RECEIVER_NOT_FOUND;
import static com.potato.ecommerce.global.exception.ExceptionMessage.RECEIVER_NOT_MATCH;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import com.potato.ecommerce.domain.receiver.repository.ReceiverJpaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiverService {

    private final MemberJpaRepository memberJpaRepository;
    private final ReceiverJpaRepository receiverJpaRepository;

    @Transactional
    public Long createReceiver(ReceiverForm dto, String subject) {
        MemberEntity member = findByEmail(subject);

        String addressName =
            StringUtils.hasText(dto.getAddressName()) ? dto.getAddressName() : dto.getName();

        ReceiverEntity receiver = ReceiverEntity.builder()
            .member(member)
            .name(dto.getName())
            .phone(dto.getPhone())
            .addressName(addressName)
            .city(dto.getCity())
            .street(dto.getStreet())
            .detail(dto.getDetail())
            .zipcode(dto.getZipcode())
            .build();

        return receiverJpaRepository.save(receiver).getId();

    }

    @Transactional
    public ReceiverForm updateReceiver(String subject, Long receiverId, ReceiverForm dto) {
        MemberEntity member = findByEmail(subject);
        ReceiverEntity receiver = findById(receiverId);

        validateMember(receiver, member);

        receiver.update(dto);
        return ReceiverForm.fromEntity(receiver);
    }

    @Transactional
    public void deleteReceiver(String subject, Long receiverId) {
        MemberEntity member = findByEmail(subject);
        ReceiverEntity receiver = findById(receiverId);

        validateMember(receiver, member);

        receiverJpaRepository.delete(receiver);
    }

    public List<ReceiverForm> findAllReceiver(String subject) {
        MemberEntity member = findByEmail(subject);

        return receiverJpaRepository.findAllByMember_Id(member.getId()).stream()
            .map(ReceiverForm::fromEntity)
            .toList();
    }

    public ReceiverForm findOneReceiver(String subject, Long receiverId) {
        MemberEntity member = findByEmail(subject);
        ReceiverEntity receiver = findById(receiverId);

        validateMember(receiver, member);

        return ReceiverForm.fromEntity(receiver);
    }


    private MemberEntity findByEmail(String subject) {
        return memberJpaRepository.findByEmail(subject).orElseThrow(
            () -> new EntityNotFoundException(MEMBER_NOT_FOUND.toString())
        );
    }

    private ReceiverEntity findById(Long receiverId) {
        return receiverJpaRepository.findById(receiverId).orElseThrow(
            () -> new EntityNotFoundException(RECEIVER_NOT_FOUND.toString())
        );
    }

    private void validateMember(ReceiverEntity receiver, MemberEntity member) {
        if (receiver.isMemberNotMatch(member.getId())) {
            throw new EntityExistsException(RECEIVER_NOT_MATCH.toString());
        }
    }


}
