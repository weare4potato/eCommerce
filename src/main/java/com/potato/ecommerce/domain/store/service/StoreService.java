package com.potato.ecommerce.domain.store.service;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.product.repository.ProductQueryRepository;
import com.potato.ecommerce.domain.revenue.model.Revenue;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import com.potato.ecommerce.domain.store.dto.DeleteStoreRequest;
import com.potato.ecommerce.domain.store.dto.LoginRequest;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.dto.ValidatePasswordRequest;
import com.potato.ecommerce.domain.store.model.Store;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.global.jwt.JwtUtil;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
    private final ProductQueryRepository productQueryRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(StoreRequest storeRequest) {

        if (storeRepository.existsByEmail(storeRequest.getEmail())) {
            throw new ValidationException("이미 존재하는 email 입니다.");
        }

        if (!storeRequest.getPassword().equals(storeRequest.getValidatePassword())) {
            throw new ValidationException("패스워드가 일치하지 않습니다.");
        }

        validationBusinessNumber(storeRequest.getBusinessNumber());

        Store store = Store.builder()
            .email(storeRequest.getEmail())
            .password(passwordEncoder.encode(storeRequest.getPassword()))
            .name(storeRequest.getName())
            .phone(storeRequest.getPhone())
            .description(storeRequest.getDescription())
            .businessNumber(storeRequest.getBusinessNumber())
            .build();

        storeRepository.save(store);
    }

    @Transactional
    public String signin(LoginRequest loginRequest) {
        Store store = findByEmail(loginRequest.getEmail());

        if (!store.passwordMatches(loginRequest.getPassword(), passwordEncoder)) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createSellerToken(store.getBusinessNumber());
    }

    public StoreResponse getStore(String subject) {
        Store store = findBySubject(subject);

        return StoreResponse.builder()
            .email(store.getEmail())
            .name(store.getName())
            .description(store.getDescription())
            .phone(store.getPhone())
            .businessNumber(store.getBusinessNumber())
            .build();
    }

    @Transactional
    public StoreResponse updateStore(String subject, UpdateStoreRequest updateRequest) {
        Store store = findBySubject(subject);

        store.update(
            updateRequest.getName(),
            updateRequest.getDescription(),
            updateRequest.getPhone()
        );

        storeRepository.save(store);

        return StoreResponse.builder()
            .email(store.getEmail())
            .name(store.getName())
            .description(store.getDescription())
            .phone(store.getPhone())
            .businessNumber(store.getBusinessNumber())
            .build();
    }

    @Transactional
    public void validatePassword(String subject, ValidatePasswordRequest validatePasswordRequest) {
        Store store = findBySubject(subject);

        if (!store.passwordMatches(validatePasswordRequest.getFirstPassword(), passwordEncoder)) {
            throw new ValidationException("비밀번호가 회원 정보와 일치하지 않습니다.");
        }

        if (!validatePasswordRequest.getFirstPassword()
            .equals(validatePasswordRequest.getSecondPassword())) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void deleteStore(String subject, DeleteStoreRequest deleteStoreRequest) {
        Store store = findBySubject(subject);

        if (!store.emailMatches(deleteStoreRequest.getEmail())) {
            throw new ValidationException("이메일이 일치하지 않습니다.");
        }

        if (!store.passwordMatches(deleteStoreRequest.getPassword(), passwordEncoder)) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }

        if (!store.businessNumberMatches(deleteStoreRequest.getBusinessNumber())) {
            throw new ValidationException("사업자 등록 번호가 일치하지 않습니다.");
        }

        storeRepository.delete(store);
    }

    @Cacheable(cacheNames = "getProducts", key = "#subject", cacheManager = "rcm")
    public RestPage<ProductListResponse> getProducts(String subject, int page, int size) {
        return productQueryRepository.getProducts(subject, page, size);
    }

    private void validationBusinessNumber(String businessNumber) {
        Revenue revenue = revenueRepository.findByNumber(businessNumber);

        if (revenue.isUsedChecking()) {
            throw new DataIntegrityViolationException("이미 사용된 사업자 번호입니다.");
        }

        use(revenue);
    }

    private void use(Revenue revenue) {
        revenue.use();
        revenueRepository.save(revenue);
    }

    private Store findByEmail(String email) {
        return storeRepository.findByEmail(email);
    }

    private Store findBySubject(String subject) {
        return storeRepository.findBySubject(subject);
    }

}
