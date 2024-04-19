package com.potato.ecommerce.domain.s3.dto;

import com.potato.ecommerce.domain.s3.entity.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseFileDto {
    private String title;
    private String url;

    public ResponseFileDto(ImageEntity data) {
        this.title = data.getTitle();
        this.url =data.getUrl();
    }
}
