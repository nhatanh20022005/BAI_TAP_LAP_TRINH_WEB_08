// src/main/java/ltwebct4/controller/CategoryController.java
package ltwebct4.controller;

import ltwebct4.entity.Category;
import ltwebct4.input.CategoryInput;
import ltwebct4.repository.CategoryRepository;
import ltwebct4.service.CategoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    // --- QUERY (READ) ---
    @QueryMapping
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Optional<Category> findCategoryById(@Argument Long id) {
        return categoryRepository.findById(id);
    }

    // --- MUTATION (CRUD) ---
    @MutationMapping
    public Category createCategory(@Argument CategoryInput category) {
        return categoryService.createCategory(category);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput category) {
        return categoryService.updateCategory(id, category);
    }

    @MutationMapping
    public Category deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}