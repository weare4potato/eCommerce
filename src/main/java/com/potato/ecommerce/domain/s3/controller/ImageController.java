package com.potato.ecommerce.domain.s3.controller;

import com.potato.ecommerce.domain.s3.dto.FileDto;
import com.potato.ecommerce.domain.s3.dto.ResponseFileDto;
import com.potato.ecommerce.domain.s3.service.ImageService;
import com.potato.ecommerce.domain.s3.service.S3Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageService imageService;
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<String> updateImage(
        @RequestBody FileDto dto
    ){
        String url = s3Service.upload(dto.getImage());
        dto.setUrl(url);
        imageService.save(dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("업로드 성공!");
    }

    @GetMapping
    public ResponseEntity<List<ResponseFileDto>> getAllImage(){
        return ResponseEntity
            .status(200)
            .body(imageService.findAll());
    }
}
