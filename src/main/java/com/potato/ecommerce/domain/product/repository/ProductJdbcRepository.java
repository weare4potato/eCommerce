package com.potato.ecommerce.domain.product.repository;

import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.global.util.RestPage;
import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ProductJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public RestPage<ProductSimpleResponse> findAllByKeyword(String keyword, int page, int size) {
        log.info("DB 접근");

        String sql = "SELECT product_id, name, price FROM products WHERE MATCH(name) AGAINST(? IN BOOLEAN MODE) LIMIT ?, ?";
        String countSql  = "SELECT count(product_id) FROM products WHERE MATCH(name) AGAINST(? IN BOOLEAN MODE)";

        List<ProductSimpleResponse> dtos = jdbcTemplate.query(sql, new Object[]{keyword, page * size, size},
            (ResultSet rs, int rowNum) -> new ProductSimpleResponse(
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getLong("price"))
        );

        long total = jdbcTemplate.queryForObject(countSql, new Object[]{keyword}, Long.class);

        Pageable pageable = PageRequest.of(page, size);
        return new RestPage<>(new PageImpl<>(dtos, pageable, total));
    }
}
