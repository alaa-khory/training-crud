package org.example.services;


import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.example.sevices.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        productRepository.deleteAll();
    }

    @Test
    void testGetAllProducts(){
        saveListOfProducts();
        List<Product> resultList = productService.getAllProducts();
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isGreaterThan(0).isEqualTo(4);
    }

    private void saveListOfProducts() {
        List<Product> savedList = List.of(
                new Product(null, "New Product 1", "New Product Description 1", 100.0),
                new Product(null, "New Product 2", "New Product Description 2", 200.0),
                new Product(null, "New Product 3", "New Product Description 3", 300.0),
                new Product(null, "New Product 4", "New Product Description 4", 400.0)
        );
        productRepository.saveAll(savedList);
    }

}
