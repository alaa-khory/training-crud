package org.example.sevices;

import org.example.dto.ProductDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.models.Product;
import org.example.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ExecutorService executorService;

    public ProductServiceImpl(ProductRepository productRepository, ExecutorService executorService) {
        this.productRepository = productRepository;
        this.executorService = executorService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID[" + id + "] is not not found"));
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID[" + id + "] is not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Product with ID[" + id + "] is not found");
        }
    }

    @Override
    public CompletableFuture<Product> createProductAsync(ProductDTO productDTO) {
        return CompletableFuture.supplyAsync(()->{
            Product product = Product.builder()
                    .name(productDTO.getName())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .build();
            return productRepository.save(product);
        }, executorService);

    }
}
