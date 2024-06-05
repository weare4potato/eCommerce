package com.potato.ecommerce.domain.store.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.BUSINESS_NUMBER_NOT_FOUNT;
import static com.potato.ecommerce.global.exception.ExceptionMessage.BUSINESS_NUMBER_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.DUPLICATE_BUSINESS_NUMBER;
import static com.potato.ecommerce.global.exception.ExceptionMessage.DUPLICATE_EMAIL;
import static com.potato.ecommerce.global.exception.ExceptionMessage.EMAIL_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PASSWORD_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.STORE_NOT_FOUND;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.product.repository.ProductQueryRepositoryImpl;
import com.potato.ecommerce.domain.revenue.entity.RevenueEntity;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import com.potato.ecommerce.domain.store.dto.DeleteStoreRequest;
import com.potato.ecommerce.domain.store.dto.LoginRequest;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.dto.ValidatePasswordRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.global.jwt.JwtUtil;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final RevenueRepository revenueRepository;
    private final ProductQueryRepositoryImpl productQueryRepositoryImpl;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StoreResponse signup(StoreRequest storeRequest) {
        storeRequest.validatePassword();
        validateEmail(storeRequest);

        usingBusinessNumber(storeRequest.getBusinessNumber());

        StoreEntity storeEntity = createStore(storeRequest);

        StoreEntity saveEntity = storeRepository.save(storeEntity);

        return createStoreResponse(saveEntity);
    }

    @Transactional
    public String signin(LoginRequest loginRequest) {
        StoreEntity storeEntity = findByEmail(loginRequest.getEmail());

        storeEntity.passwordMatches(loginRequest.getPassword(), passwordEncoder);

        return jwtUtil.createSellerToken(storeEntity.getBusinessNumber());
    }

    public StoreResponse getStore(String subject) {
        StoreEntity storeEntity = findBySubject(subject);

        return createStoreResponse(storeEntity);
    }

    @Transactional
    public StoreResponse updateStore(String subject, UpdateStoreRequest updateRequest) {
        StoreEntity storeEntity = findBySubject(subject);

        storeEntity.update(updateRequest.getName(), updateRequest.getDescription(),
            updateRequest.getPhone());

        return createStoreResponse(storeEntity);
    }

    @Transactional
    public void validatePassword(String subject, ValidatePasswordRequest validatePasswordRequest) {
        StoreEntity storeEntity = findBySubject(subject);

        validatePasswordRequest.validatePassword();

        storeEntity.passwordMatches(validatePasswordRequest.getFirstPassword(), passwordEncoder);
    }

    @Transactional
    public void deleteStore(String subject, DeleteStoreRequest deleteStoreRequest) {
        StoreEntity storeEntity = findBySubject(subject);

        storeEntity.emailMatches(deleteStoreRequest.getEmail());
        storeEntity.passwordMatches(deleteStoreRequest.getPassword(), passwordEncoder);
        storeEntity.businessNumberMatches(deleteStoreRequest.getBusinessNumber());

        storeRepository.delete(storeEntity);
    }

    public RestPage<ProductListResponse> getProducts(String subject, int page, int size) {
        return productQueryRepositoryImpl.getProducts(subject, page, size);
    }

    private StoreEntity createStore(final StoreRequest storeRequest) {
        return StoreEntity
            .builder()
            .email(storeRequest.getEmail())
            .password(passwordEncoder.encode(storeRequest.getPassword()))
            .name(storeRequest.getName()).phone(storeRequest.getPhone())
            .description(storeRequest.getDescription())
            .businessNumber(storeRequest.getBusinessNumber()).build();
    }

    private static StoreResponse createStoreResponse(final StoreEntity saveEntity) {
        return new StoreResponse(
            saveEntity.getId(),
            saveEntity.getEmail(),
            saveEntity.getName(),
            saveEntity.getDescription(),
            saveEntity.getPhone(),
            saveEntity.getBusinessNumber()
        );
    }

    private void usingBusinessNumber(String businessNumber) {
        RevenueEntity revenueEntity = revenueRepository.findByNumber(businessNumber)
            .orElseThrow(() -> new EntityNotFoundException(BUSINESS_NUMBER_NOT_FOUNT.toString()));

        if (revenueEntity.isUsedChecking()) {
            throw new DataIntegrityViolationException(DUPLICATE_BUSINESS_NUMBER.toString());
        }

        revenueEntity.use();
        revenueRepository.save(revenueEntity);
    }

    private StoreEntity findByEmail(String email) {
        return storeRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND.toString()));
    }

    private StoreEntity findBySubject(String subject) {
        return storeRepository.findByBusinessNumber(subject)
            .orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND.toString()));
    }

    private void validateEmail(final StoreRequest storeRequest) {
        if (storeRepository.existsByEmail(storeRequest.getEmail())) {
            throw new ValidationException(DUPLICATE_EMAIL.toString());
        }
    }

}
