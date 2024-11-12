package org.example.sevices;

import org.example.dto.ProductDTO;
import org.example.models.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(Long id, ProductDTO productDTO);
    void deleteProductById(Long id);
    CompletableFuture<Product> createProductAsync(ProductDTO productDTO);
}
