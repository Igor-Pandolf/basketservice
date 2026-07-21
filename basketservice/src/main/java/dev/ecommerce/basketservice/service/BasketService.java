package dev.ecommerce.basketservice.service;

import dev.ecommerce.basketservice.client.response.PlatziProductResponse;
import dev.ecommerce.basketservice.controller.request.BasketRequest;
import dev.ecommerce.basketservice.controller.request.PaymentRequest;
import dev.ecommerce.basketservice.entity.Basket;
import dev.ecommerce.basketservice.entity.Product;
import dev.ecommerce.basketservice.entity.Status;
import dev.ecommerce.basketservice.exceptions.BusinessException;
import dev.ecommerce.basketservice.exceptions.DataNotFoundException;
import dev.ecommerce.basketservice.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Basket getBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Basket not found"));
    }

    public Basket createBasket(BasketRequest basketRequest) {
        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new BusinessException("There is already an open basket for this client");
                });

        List<Product> products = getProducts(basketRequest);

        Basket basket = Basket.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .producs(products)
                .build();

        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    public Basket updateBasket(String basketId, BasketRequest request) {
        Basket savedBasket = getBasketById(basketId);

        List<Product> products = getProducts(request);

        savedBasket.setProducs(products);
        savedBasket.calculateTotalPrice();
        return basketRepository.save(savedBasket);
    }

    public Basket payBasket(String basketId, PaymentRequest request) {
        Basket savedBasket = getBasketById(basketId);
        savedBasket.setPaymentMethod(request.getPaymentMethod());
        savedBasket.setStatus(Status.SOLD);
        return basketRepository.save(savedBasket);
    }

    public void deleteBasket(String basketId) {
        Basket savedBasket = getBasketById(basketId);
        basketRepository.delete(savedBasket);
    }

    private @NonNull List<Product> getProducts(BasketRequest request) {
        List<Product> products = new ArrayList<>();
        request.products().forEach(product -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(product.id());

            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(product.quantity())
                    .build());
        });
        return products;
    }
}
