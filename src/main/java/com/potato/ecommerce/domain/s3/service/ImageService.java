package com.potato.ecommerce.domain.s3.service;

import com.potato.ecommerce.domain.s3.dto.FileDto;
import com.potato.ecommerce.domain.s3.dto.ResponseFileDto;
import com.potato.ecommerce.domain.s3.entity.ImageEntity;
import com.potato.ecommerce.domain.s3.repository.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void save(FileDto dto){
        imageRepository.save(new ImageEntity(dto.getTitle(), dto.getUrl()));
    }

    public List<ResponseFileDto> findAll(){
        return imageRepository.findAll().stream().map(ResponseFileDto::new).toList();
    }
}
