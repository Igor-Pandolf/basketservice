package dev.ecommerce.basketservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "basket")
public class Basket {

    @Id
    private String id;

    private Long client;

    private BigDecimal totalPrice;

    private List<Product> producs;

    private Status status;

    public void calculateTotalPrice() {
        this.totalPrice = producs.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
