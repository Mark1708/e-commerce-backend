package commerce.app.category.service;

import commerce.app.category.dto.CategoryDto;
import commerce.app.category.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();

    CategoryDto findById(Long categoryId);

    CategoryDto update(Category category);

    CategoryDto create(Category category);

    void delete(Long categoryId);

    CategoryDto convertToDto(Category category);

    List<CategoryDto> convertToDto(List<Category> categories);
}
