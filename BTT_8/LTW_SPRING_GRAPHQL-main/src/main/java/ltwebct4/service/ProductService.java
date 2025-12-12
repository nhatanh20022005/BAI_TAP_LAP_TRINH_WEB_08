package ltwebct4.service;

import ltwebct4.entity.Product;
import ltwebct4.entity.User;
import ltwebct4.input.ProductInput;
import ltwebct4.repository.ProductRepository;
import ltwebct4.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    // CREATE
    @Transactional
    public Product createProduct(ProductInput input) {
        User user = findUserOrThrow(input.getUserId());

        Product product = new Product();
        product.setTitle(input.getTitle());
        product.setQuantity(input.getQuantity());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        product.setUser(user);

        return productRepository.save(product);
    }

    // UPDATE
    @Transactional
    public Product updateProduct(Long id, ProductInput input) {
        return productRepository.findById(id).map(product -> {
            
            product.setTitle(input.getTitle());
            product.setQuantity(input.getQuantity());
            product.setDescription(input.getDescription());
            product.setPrice(input.getPrice());

            if (!product.getUser().getId().equals(input.getUserId())) {
                User newUser = findUserOrThrow(input.getUserId());
                product.setUser(newUser);
            }
            
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // DELETE
    @Transactional
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        productRepository.delete(product);
        return product;
    }

    // QUERY ĐẶC BIỆT: Hiển thị tất cả product có price từ thấp đến cao
    public List<Product> findAllProductsByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    // QUERY ĐẶC BIỆT: Lấy tất cả product của 01 category
    public List<Product> findProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}