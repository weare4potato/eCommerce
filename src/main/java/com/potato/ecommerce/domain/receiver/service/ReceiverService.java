package com.potato.ecommerce.domain.receiver.service;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.member.repository.MemberRepository;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import com.potato.ecommerce.domain.receiver.repository.ReceiverRepository;
import jakarta.persistence.EntityExistsException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiverService {

    private final MemberRepository memberRepository;
    private final ReceiverRepository receiverRepository;

    @Transactional
    public void createReceiver(ReceiverForm dto, String subject) {
        Member member = findMemberBy(subject);

        String addressName =
            StringUtils.hasText(dto.getAddressName()) ? dto.getAddressName() : dto.getName();

        Receiver receiver = Receiver.builder()
            .member(member)
            .name(dto.getName())
            .phone(dto.getPhone())
            .addressName(addressName)
            .city(dto.getCity())
            .street(dto.getStreet())
            .detail(dto.getDetail())
            .zipcode(dto.getZipcode())
            .build();

        receiverRepository.save(receiver);
    }

    @Transactional
    public ReceiverForm updateReceiver(String subject, Long receiverId, ReceiverForm dto) {
        Member member = findMemberBy(subject);
        Receiver receiver = findReceiverBy(receiverId);

        validateMember(receiver, member);

        receiver.update(dto);
        receiverRepository.update(receiver);
        return receiver.createReceiverForm();
    }

    @Transactional
    public void deleteMember(String subject, Long receiverId) {
        Member member = findMemberBy(subject);
        Receiver receiver = findReceiverBy(receiverId);

        validateMember(receiver, member);

        receiverRepository.delete(receiver);
    }

    public List<ReceiverForm> findAllReceiver(String subject) {
        Member member = findMemberBy(subject);

        return receiverRepository.findAll(member.getId()).stream().map(Receiver::createReceiverForm)
            .toList();
    }

    private Member findMemberBy(String subject) {
        return memberRepository.findMemberBy(subject);
    }

    private Receiver findReceiverBy(Long receiverId) {
        return receiverRepository.findBy(receiverId);
    }

    private void validateMember(Receiver receiver, Member member) {
        if (receiver.isMemberNotMatch(member.getId())) {
            throw new EntityExistsException("해당 주소지를 갖고 있지 않습니다.");
        }
    }
}
