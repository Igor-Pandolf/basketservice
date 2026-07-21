package dev.ecommerce.basketservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Product {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
