package com.potato.ecommerce.domain.store;

import static com.potato.ecommerce.domain.store.StoreSteps.createStore;
import static com.potato.ecommerce.domain.store.StoreSteps.createStoreRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
import com.potato.ecommerce.domain.store.service.StoreService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTests {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private RevenueRepository revenueRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void store_signup() {
        // Arrange
        final StoreRequest storeRequest = createStoreRequest();
        StoreEntity store = createStore(passwordEncoder);
        RevenueEntity revenueEntity = new RevenueEntity(new AtomicLong(1));
        String sameBusinessNumber = "1111111111";

        given(storeRepository.existsByEmail(any())).willReturn(false);
        given(revenueRepository.findByNumber(sameBusinessNumber)).willReturn(
            Optional.of(revenueEntity));
        given(revenueRepository.save(revenueEntity)).willReturn(revenueEntity);
        given(storeRepository.save(any())).willReturn(store);

        // Act
        StoreResponse storeResponse = storeService.signup(storeRequest);

        // Assert
        assertThat(storeResponse.getEmail()).isEqualTo(storeRequest.getEmail());
    }

    @Test
    void store_signin() {
        // Arrange
        final LoginRequest loginRequest = new LoginRequest("test@email.com", "123456789");
        final StoreEntity store = createStore(passwordEncoder);
        final String token = "token";

        given(storeRepository.findByEmail(loginRequest.getEmail())).willReturn(Optional.of(store));
        given(jwtUtil.createSellerToken(store.getBusinessNumber())).willReturn(token);

        // Act
        final String response = storeService.signin(loginRequest);

        // Assert
        assertThat(response).isEqualTo(token);
    }

    @Test
    void store_update() {
        // Arrange
        final UpdateStoreRequest updateRequest = new UpdateStoreRequest("updateName",
            "updateDescription", "01022223333");
        final StoreEntity store = createStore(passwordEncoder);
        String businessNumber = "1111111111";

        given(storeRepository.findByBusinessNumber(businessNumber)).willReturn(Optional.of(store));

        // Act
        final StoreResponse response = storeService.updateStore(businessNumber, updateRequest);

        // Assert
        assertThat(response.getName()).isEqualTo(updateRequest.getName());
    }

    @Test
    void store_validate_password() {
        // Arrange
        String password = "123456789";
        final ValidatePasswordRequest validatePasswordRequest = new ValidatePasswordRequest(
            password, password);
        final StoreEntity store = createStore(passwordEncoder);
        String businessNumber = "1111111111";

        given(storeRepository.findByBusinessNumber(businessNumber)).willReturn(Optional.of(store));

        // Act + Assert
        storeService.validatePassword(businessNumber, validatePasswordRequest);
    }

    @Test
    void store_delete() {
        // Arrange
        final DeleteStoreRequest deleteStoreRequest = new DeleteStoreRequest("test@email.com",
            "123456789", "1111111111");
        final StoreEntity store = createStore(passwordEncoder);
        String businessNumber = "1111111111";

        given(storeRepository.findByBusinessNumber(businessNumber)).willReturn(Optional.of(store));

        // Act + Assert
        storeService.deleteStore(businessNumber, deleteStoreRequest);
    }
}
