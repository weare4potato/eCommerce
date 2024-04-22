package com.potato.ecommerce.domain.s3.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cdn.url}")
    private String cdnUrl;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile file) {
        String fileType = file.getOriginalFilename().split("\\.")[1];
        String uploadImgName = UUID.randomUUID() + fileType;

        String contentType = "";

        switch (fileType) {
            case "jpeg" -> contentType = "image/jpeg";
            case "png" -> contentType = "image/png";
        }

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            amazonS3.putObject(new PutObjectRequest(bucket, uploadImgName, file.getInputStream(),
                metadata).withCannedAcl(
                CannedAccessControlList.PublicRead));
        } catch (IOException | SdkClientException e) {
            throw new RuntimeException(e);
        }

        return cdnUrl + "/" + uploadImgName;
    }
}
