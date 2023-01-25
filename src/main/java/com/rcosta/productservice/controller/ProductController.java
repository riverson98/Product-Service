package com.rcosta.productservice.controller;

import com.rcosta.productservice.dto.ProductDto;
import com.rcosta.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> allProduct() {
        return this.productService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id) {
        this.simulateRandomException();
        return this.productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.insertProduct(productDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.updateProduct(id, productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id){
        return this.productService.deleteProduct(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("price-range")
    public Flux<ProductDto> rangeOfProduct(@RequestParam("min") int min,
                                           @RequestParam("max") int max){
        return  this.productService.getProductByPriceRange(min, max);
    }

    private void simulateRandomException(){
        int nextInt = ThreadLocalRandom.current().nextInt(1, 10);
        if (nextInt > 5)
            throw new RuntimeException("something is wrong");
    }
}
