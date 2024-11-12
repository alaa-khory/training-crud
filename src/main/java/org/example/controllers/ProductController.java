package org.example.controllers;


import jakarta.validation.Valid;
import org.example.dto.ProductDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.models.Product;
import org.example.models.generic.ApiResponse;
import org.example.sevices.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(new ApiResponse<>(true, "All products retrieved", productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Product with[" + id + "] is retrieved", productService.getProductById(id)));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product with[" + id + "] is not found", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "New Product created", productService.createProduct(productDTO)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product is not created for " + ex.getStackTrace(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Product with ID[" + id + "] is updated", productService.updateProduct(id, productDTO)));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product with ID[" + id + "] is not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Product with ID[" + id + "] is deleted", "Success"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product with ID[" + id + "] is not found", "Failed"));
        }
    }

    @PostMapping("/async")
    public CompletableFuture<ResponseEntity<ApiResponse<Product>>> createProductAsync(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProductAsync(productDTO)
                .thenApply(product -> ResponseEntity.ok().body(new ApiResponse<>(true, "Product created Asynchronously", product)));
    }
}
