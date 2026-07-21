package dev.ecommerce.basketservice.controller.request;

import dev.ecommerce.basketservice.entity.Product;

import java.util.List;

public record BasketRequest(Long clientId, List<ProductRequest> products) {
}
