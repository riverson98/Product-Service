package com.rcosta.productservice.service;

import com.rcosta.productservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("4k-tv", 2500);
        ProductDto p2 = new ProductDto("Ps5", 4999);
        ProductDto p3 = new ProductDto("Rtx 4080", 18000);
        ProductDto p4 = new ProductDto("iphone 13", 5500);

        Flux.just(p1, p2 ,p3 ,p4)
                .concatWith(newProducts())
                .flatMap(p -> this.service.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }

    private Flux<ProductDto> newProducts(){
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductDto("product-" + i,
                        ThreadLocalRandom.current().nextInt(10,100)));
    }

}
