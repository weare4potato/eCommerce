package com.potato.ecommerce.domain.receiver;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

import com.potato.ecommerce.domain.member.MemberSteps;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import com.potato.ecommerce.domain.receiver.repository.ReceiverJpaRepository;
import com.potato.ecommerce.domain.receiver.service.ReceiverService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ReceiverServiceTests {

    @InjectMocks
    private ReceiverService receiverService;

    @Mock
    private ReceiverJpaRepository receiverJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void Receiver_create() {
        // Arrange
        final ReceiverForm receiverForm = ReceiverSteps.createReceiverForm();
        final MemberEntity member = MemberSteps.createMember(passwordEncoder);
        final ReceiverEntity receiver = ReceiverSteps.createReceiverWithRequestAndMember(member, receiverForm);
        String subject = member.getEmail();

        given(memberJpaRepository.findByEmail(subject)).willReturn(Optional.of(member));
        given(receiverJpaRepository.save(any())).willReturn(receiver);
        // Act
        receiverService.createReceiver(receiverForm, subject);

        // Assert
        assertThat(receiver.getName()).isEqualTo(receiverForm.getName());
    }

    @Test
    void Receiver_find_all() {
        // Arrange
        String subject = "test@email.com";
        final MemberEntity member = Mockito.spy(MemberSteps.createMember(passwordEncoder));
        final ReceiverEntity receiver1 = ReceiverSteps.createReceiverWithMember(member);
        final ReceiverEntity receiver2 = ReceiverSteps.createReceiverWithMember(member);
        List<ReceiverEntity> receiverList = new ArrayList<>();
        receiverList.add(receiver1);
        receiverList.add(receiver2);

        given(memberJpaRepository.findByEmail(subject)).willReturn(Optional.of(member));
        given(receiverJpaRepository.findAllByMember_Id(1L)).willReturn(receiverList);
        given(member.getId()).willReturn(1L);

        // Act
        final List<ReceiverForm> response = receiverService.findAllReceiver(subject);

        // Assert
        assertThat(receiverList.size()).isEqualTo(2);
        assertThat(response.get(0).getName()).isEqualTo(receiver1.getName());

    }

}
