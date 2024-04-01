package com.potato.ecommerce.domain.receiver.service;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.member.repository.MemberRepository;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import com.potato.ecommerce.domain.receiver.repository.ReceiverRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ReceiverService {

    private final MemberRepository memberRepository;
    private final ReceiverRepository receiverRepository;

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

    public List<ReceiverForm> findAllReceiver(String subject) {
        Member member = findMemberBy(subject);

        return receiverRepository.findAll(member.getId()).stream().map(Receiver::createReceiverForm)
            .toList();
    }

    private Member findMemberBy(String subject) {
        return memberRepository.findMemberBy(subject);
    }

}
