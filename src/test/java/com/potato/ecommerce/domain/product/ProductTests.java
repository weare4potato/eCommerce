package com.potato.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.global.exception.custom.OutOfStockException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTests {


    @Nested
    class Product_remove_stock {

        @Test
        void success_stock_remove() {
            // Arrange
            ProductEntity product = ProductSteps.createProductWithStoreAndCategory(10, 1000L);

            // Act
            product.removeStock(5);

            // Assert
            assertThat(product.getStock()).isEqualTo(5);
        }

        @ParameterizedTest
        @ValueSource(ints = {11, 100, 1000})
        void fail_stock_remove(int stock) {
            // Arrange
            ProductEntity product = ProductSteps.createProductWithStoreAndCategory(10, 1000L);

            // Act + Assert
            assertThatThrownBy(() -> {
                product.removeStock(stock);
            }).isInstanceOf(OutOfStockException.class)
                .hasMessageContaining("상품의 재고가 부족합니다. 현재 재고: " + product.getStock());
        }
    }

    @Test
    void Product_add_stock() {
        // Arrange
        ProductEntity product = ProductSteps.createProductWithStoreAndCategory(10, 1000L);

        // Act
        product.addStock(5);

        // Assert
        assertThat(product.getStock()).isEqualTo(15);
    }

    @Test
    void Product_calculate_total_price() {
        // Arrange
        ProductEntity product = ProductSteps.createProductWithStoreAndCategory(5, 1000L);

        // Act
        final Long totalPrice = product.getTotalPrice(2);

        // Assert
        assertThat(totalPrice).isEqualTo(2000);
    }

}
