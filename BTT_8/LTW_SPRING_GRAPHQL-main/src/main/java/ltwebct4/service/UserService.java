package ltwebct4.service;

import ltwebct4.entity.Category;
import ltwebct4.entity.User;
import ltwebct4.input.UserInput;
import ltwebct4.repository.CategoryRepository;
import ltwebct4.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public UserService(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    private void setCategoriesForUser(User user, List<Long> categoryIds) {
        // Xóa các liên kết cũ trước
        user.getCategories().clear(); 
        
        if (categoryIds != null && !categoryIds.isEmpty()) {
            List<Category> categoriesList = categoryRepository.findAllById(categoryIds);
            Set<Category> categories = new HashSet<>(categoriesList);
            
            user.setCategories(categories);
            
            // Cập nhật ngược lại phía Category
            categories.forEach(category -> category.getUsers().add(user));
        }
    }

    // CREATE
    @Transactional
    public User createUser(UserInput input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword()); // Cần mã hóa trong thực tế
        user.setPhone(input.getPhone());
        
        setCategoriesForUser(user, input.getCategoryIds());

        return userRepository.save(user);
    }

    // UPDATE
    @Transactional
    public User updateUser(Long id, UserInput input) {
        return userRepository.findById(id).map(user -> {
            user.setFullName(input.getFullName());
            user.setEmail(input.getEmail());
            user.setPhone(input.getPhone());
            
            if (input.getPassword() != null && !input.getPassword().isEmpty()) {
                 user.setPassword(input.getPassword());
            }

            setCategoriesForUser(user, input.getCategoryIds());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // DELETE
    @Transactional
    public User deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Xử lý mối quan hệ Many-to-Many trước
        user.getCategories().forEach(category -> category.getUsers().remove(user));
        user.getCategories().clear();
        
        userRepository.delete(user);
        return user;
    }
}