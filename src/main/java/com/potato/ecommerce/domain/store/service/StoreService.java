package com.potato.ecommerce.domain.store.service;

import com.potato.ecommerce.domain.revenue.model.Revenue;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.model.Store;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import jakarta.validation.ValidationException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final RevenueRepository revenueRepository;

    @Transactional
    public void signup(StoreRequest storeRequest) {

        if(!storeRequest.getPassword().equals(storeRequest.getValidatePassword())){
            throw new ValidationException("패스워드가 일치하지 않습니다.");
        }

        validationEmail(storeRequest.getEmail());
        validationBusinessNumber(storeRequest.getBusinessNumber());

        Store store = Store.builder()
            .email(storeRequest.getEmail())
            .password(storeRequest.getPassword())
            .name(storeRequest.getName())
            .phone(storeRequest.getPhone())
            .description(storeRequest.getDescription())
            .businessNumber(storeRequest.getBusinessNumber())
            .build();

        storeRepository.save(StoreEntity.fromModel(store));
    }

    private void validationBusinessNumber(String businessNumber){
        Revenue revenue = revenueRepository.findByNumber(businessNumber)
            .orElseThrow(
                () -> new NoSuchElementException("등록된 사업자 번호가 아닙니다.")
            ).toModel();

        if(revenue.isUsed()){
            throw new DataIntegrityViolationException("이미 사용된 사업자 번호입니다.");
        }

        use(revenue);
    }

    private void validationEmail(String email){
        storeRepository.findByEmail(email).ifPresent(
            s -> {
                throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
            });
    }

    private void use(Revenue revenue){
        revenue.use();
        revenueRepository.save(revenue.toEntity());
    }
}
