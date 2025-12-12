package ltwebct4.service;

import ltwebct4.entity.Category;
import ltwebct4.input.CategoryInput;
import ltwebct4.repository.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // CREATE
    @Transactional
    public Category createCategory(CategoryInput input) {
        Category category = new Category();
        category.setName(input.getName());
        category.setImages(input.getImages());
        return categoryRepository.save(category);
    }

    // UPDATE
    @Transactional
    public Category updateCategory(Long id, CategoryInput input) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(input.getName());
            category.setImages(input.getImages());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // DELETE
    @Transactional
    public Category deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        
        // Xóa mối quan hệ Many-to-Many với User trước khi xóa Category
        category.getUsers().forEach(user -> user.getCategories().remove(category));
        
        categoryRepository.delete(category);
        return category;
    }
}