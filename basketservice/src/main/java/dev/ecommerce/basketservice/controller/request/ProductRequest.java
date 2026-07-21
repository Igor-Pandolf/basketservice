package dev.ecommerce.basketservice.controller.request;

import lombok.Builder;

public record ProductRequest(Long id, Integer quantity) {
}
