package org.example.controllers;

import org.example.models.Product;
import org.example.models.generic.ApiResponse;
import org.example.repositories.ProductRepository;
import org.example.sevices.ProductService;
import org.example.sevices.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @LocalServerPort
    private String port;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testGetProductById() {
        Product savedProduct = productRepository.save(new Product(null, "new product", "new product description", 120.0));

        ResponseEntity<ApiResponse> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/api/products/" + savedProduct.getId(), ApiResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity).isNotNull();
        assertThat(((LinkedHashMap<?,?>) responseEntity.getBody().data()).get("name")).isEqualTo("new product");

    }


}
