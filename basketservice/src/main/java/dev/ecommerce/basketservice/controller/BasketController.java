package dev.ecommerce.basketservice.controller;

import dev.ecommerce.basketservice.controller.request.BasketRequest;
import dev.ecommerce.basketservice.controller.request.PaymentRequest;
import dev.ecommerce.basketservice.entity.Basket;
import dev.ecommerce.basketservice.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable String id) {
        return ResponseEntity.ok(basketService.getBasketById(id));
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody BasketRequest basketRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(basketService.createBasket(basketRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable String id, @RequestBody BasketRequest basketRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(basketService.updateBasket(id, basketRequest));
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Basket> payBasket(@PathVariable String id, @RequestBody PaymentRequest basketRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(basketService.payBasket(id, basketRequest));
    }
}
