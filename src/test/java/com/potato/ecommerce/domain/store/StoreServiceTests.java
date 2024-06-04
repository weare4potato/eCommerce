package com.potato.ecommerce.domain.store;

import com.potato.ecommerce.domain.product.repository.ProductQueryRepository;
import com.potato.ecommerce.domain.product.repository.ProductQueryRepositoryImpl;
import com.potato.ecommerce.domain.revenue.repository.RevenueRepository;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.domain.store.service.StoreService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class StoreServiceTests {
//
//    @InjectMocks
//    private StoreService storeService;
//    @Mock
//    private StoreRepository storeRepository;
//    @Mock
//    private RevenueRepository revenueRepository;
//    @Mock
//    private ProductQueryRepository productQueryRepository;
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//}
