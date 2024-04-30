package com.potato.ecommerce.domain.s3.service;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.s3.entity.ImageEntity;
import com.potato.ecommerce.domain.s3.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void save(ProductEntity product, String url) {
        imageRepository.save(new ImageEntity(product, url));
    }

}
