package com.potato.ecommerce.domain.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.potato.ecommerce.domain.revenue.model.Revenue;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import com.potato.ecommerce.domain.store.dto.DeleteStoreRequest;
import com.potato.ecommerce.domain.store.dto.LoginRequest;
import com.potato.ecommerce.domain.store.dto.StoreRequest;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.dto.UpdateStoreRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.domain.store.service.StoreService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest implements StoreTestUtil {

    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    RevenueRepository revenueRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @Test
    @Transactional
    void 상점을_등록할_수_있다() {
        // given
        StoreRequest storeRequest = TEST_STORE_REQUEST;

        StoreEntity storeEntity = TEST_STORE;

        Revenue revenue = new Revenue(1L);

        given(storeRepository.existsByEmail(storeEntity.getEmail())).willReturn(false);
        given(revenueRepository.findByNumber(TEST_BUSINESS_NUMBER)).willReturn(revenue);

        // when
        storeService.signup(storeRequest);

        // then
        verify(storeRepository, times(1)).save(any(StoreEntity.class));

    }


    @Test
    @Transactional
    void 상점등록은_이메일_중복을_허용하지_않는다() {
        // given
        StoreRequest storeRequest = TEST_STORE_REQUEST;

        StoreEntity storeEntity = TEST_STORE;

        given(storeRepository.existsByEmail(storeEntity.getEmail())).willReturn(true);

        // when + then
        assertThrows(ValidationException.class, () -> storeService.signup(storeRequest));
    }

    @Test
    @Transactional
    void 상점등록은_두_패스워드가_일치_해야만_한다() {
        StoreRequest storeRequest = new StoreRequest(
            TEST_STORE_EMAIL,
            TEST_STORE_PASSWORD,
            TEST_ANOTHER_STORE_PASSWORD,
            TEST_STORE_NAME,
            TEST_STORE_DESCRIPTION,
            TEST_STORE_PHONE,
            TEST_BUSINESS_NUMBER
        );

        StoreEntity storeEntity = TEST_STORE;

        Revenue revenue = new Revenue(1L);

        given(storeRepository.existsByEmail(storeEntity.getEmail())).willReturn(false);

        // when + then
        assertThrows(ValidationException.class, () -> storeService.signup(storeRequest));
    }

    @Test
    @Transactional
    void 상점_등록에_성공하면_revenue를_isUsed로_변경한다() {
        StoreRequest storeRequest = TEST_STORE_REQUEST;

        StoreEntity storeEntity = TEST_STORE;

        Revenue revenue = new Revenue(1L);

        given(storeRepository.existsByEmail(storeEntity.getEmail())).willReturn(false);
        given(revenueRepository.findByNumber(TEST_BUSINESS_NUMBER)).willReturn(revenue);

        // when
        storeService.signup(storeRequest);

        // then
        assertThat(revenue.isUsedChecking()).isTrue();
    }

    @Test
    @Transactional
    void 상점_로그인에_성공하면_토큰을_반환한다() {
        // given
        LoginRequest loginRequest = new LoginRequest(
            TEST_STORE_EMAIL,
            TEST_STORE_PASSWORD
        );

        StoreEntity store = StoreEntity.builder()
            .name(TEST_STORE_NAME)
            .email(TEST_STORE_EMAIL)
            .password(passwordEncoder.encode(TEST_STORE_PASSWORD))
            .description(TEST_STORE_DESCRIPTION)
            .phone(TEST_STORE_PHONE)
            .businessNumber(TEST_BUSINESS_NUMBER)
            .build();

        given(storeRepository.findByEmail(loginRequest.getEmail())).willReturn(store);
        given(passwordEncoder.matches(loginRequest.getPassword(), store.getPassword())).willReturn(
            true);
        given(jwtUtil.createSellerToken(store.getBusinessNumber())).willReturn("token");

        // when + then
        assertThat(storeService.signin(loginRequest)).isEqualTo("token");
    }

    @Test
    @Transactional
    void 상점_로그인은_비밀번호가_일치해야_한다() {
        // given

        LoginRequest loginRequest = new LoginRequest(
            TEST_STORE_EMAIL,
            TEST_STORE_PASSWORD
        );

        StoreEntity store = StoreEntity.builder()
            .name(TEST_STORE_NAME)
            .email(TEST_STORE_EMAIL)
            .password(passwordEncoder.encode(TEST_ANOTHER_STORE_PASSWORD))
            .description(TEST_STORE_DESCRIPTION)
            .phone(TEST_STORE_PHONE)
            .businessNumber(TEST_BUSINESS_NUMBER)
            .build();

        given(storeRepository.findByEmail(loginRequest.getEmail())).willReturn(store);
        given(passwordEncoder.matches(loginRequest.getPassword(), store.getPassword())).willReturn(
            false);

        // when + then
        assertThrows(ValidationException.class, () -> storeService.signin(loginRequest));
    }

    @Test
    @Transactional
    void UpdateStoreReqeust로_update가_가능하다() {
        // given
        UpdateStoreRequest updateStoreRequest = new UpdateStoreRequest(
            TEST_STORE_NAME,
            TEST_STORE_DESCRIPTION,
            TEST_STORE_PHONE
        );
        String subject = TEST_BUSINESS_NUMBER;

        StoreEntity store = TEST_STORE;

        given(storeRepository.findBySubject(subject)).willReturn(store);

        // when
        StoreResponse storeResponse = storeService.updateStore(subject, updateStoreRequest);

        // then
        assertThat(storeResponse.getName()).isEqualTo(updateStoreRequest.getName());
    }

    @Test
    @Transactional
    void DeleteStoreRequest로_delete가_가능하다() {
        // given
        DeleteStoreRequest deleteStoreRequest = new DeleteStoreRequest(
            TEST_STORE_EMAIL,
            TEST_STORE_PASSWORD,
            TEST_BUSINESS_NUMBER
        );

        String subject = TEST_BUSINESS_NUMBER;

        StoreEntity storeEntity = TEST_STORE;

        given(storeRepository.findBySubject(subject)).willReturn(storeEntity);
        given(passwordEncoder.matches(deleteStoreRequest.getPassword(),
            storeEntity.getPassword())).willReturn(true);

        // when
        storeService.deleteStore(subject, deleteStoreRequest);

        // then
        verify(storeRepository, times(1)).delete(storeEntity);
    }

//    @Test
//    @Transactional
//    void getProducts는_store의_등록한_상품을_조회한다(){
//        String subject = TEST_BUSINESS_NUMBER;
//        int page = 0;
//        int size = 10;
//    }
}
