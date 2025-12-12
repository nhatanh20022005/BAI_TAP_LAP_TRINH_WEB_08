package ltwebct4.controller;

import ltwebct4.entity.Product;
import ltwebct4.input.ProductInput;
import ltwebct4.repository.ProductRepository;
import ltwebct4.service.ProductService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    // --- QUERY (READ) ---

    // Yêu cầu 2: Hiển thị tất cả product có price từ thấp đến cao
    @QueryMapping
    public List<Product> allProductsByPriceAsc() {
        return productService.findAllProductsByPriceAsc();
    }

    // Yêu cầu 2: Lấy tất cả product của 01 category
    @QueryMapping
    public List<Product> productsByCategoryId(@Argument Long categoryId) {
        return productService.findProductsByCategoryId(categoryId);
    }
    
    // CRUD: Get All
    @QueryMapping
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    
    // CRUD: Get By Id
    @QueryMapping
    public Optional<Product> findProductById(@Argument Long id) {
        return productRepository.findById(id);
    }
    
    // --- MUTATION (CRUD) ---

    @MutationMapping
    public Product createProduct(@Argument ProductInput product) {
        return productService.createProduct(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductInput product) {
        return productService.updateProduct(id, product);
    }

    @MutationMapping
    public Product deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}