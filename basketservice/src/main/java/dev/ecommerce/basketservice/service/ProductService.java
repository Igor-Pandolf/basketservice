package dev.ecommerce.basketservice.service;

import dev.ecommerce.basketservice.client.PlatziStoreClient;
import dev.ecommerce.basketservice.client.response.PlatziProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient platziStoreClient;

    @Cacheable(value = "products")
    public List<PlatziProductResponse> getAllProducts() {
        return platziStoreClient.getAllProducts();
    }

    @Cacheable(value = "product", key = "#productId")
    public PlatziProductResponse getProductById(Long productId) {
        return platziStoreClient.getProductById(productId);
    }
}
