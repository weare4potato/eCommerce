package com.potato.ecommerce.domain.receiver.controller;

import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.message.ReceiverMessage;
import com.potato.ecommerce.domain.receiver.service.ReceiverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Receiver API", description = "Receiver API 입니다.")
@RequestMapping("/api/v1/users/receivers")
@Slf4j
public class ReceiverController {

    private final ReceiverService receiverService;

    @PostMapping
    @Operation(summary = "배송지 등록")
    public ResponseEntity<String> createReceiver(
        @RequestBody @Validated ReceiverForm dto,
        HttpServletRequest request
    ) {
        receiverService.createReceiver(dto, getSubject(request));

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ReceiverMessage.CREATE_MESSAGE);
    }

    @GetMapping
    @Operation(summary = "배송지 조회")
    public ResponseEntity<List<ReceiverForm>> findAllReceiver(
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(receiverService.findAllReceiver(getSubject(request)));

    }

    @PutMapping("/{receiverId}")
    @Operation(summary = "배송지 수정")
    public ResponseEntity<ReceiverForm> updateReceiver(
        HttpServletRequest request,
        @PathVariable Long receiverId,
        @RequestBody @Validated ReceiverForm dto
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(receiverService.updateReceiver(getSubject(request), receiverId, dto));
    }

    @DeleteMapping("/{receiverId}")
    @Operation(summary = "배송지 삭제")
    public ResponseEntity<String> deleteReceiver(
        HttpServletRequest request,
        @PathVariable Long receiverId
    ){
        receiverService.deleteMember(getSubject(request), receiverId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ReceiverMessage.DELETE_MESSAGE);
    }
    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }

}
