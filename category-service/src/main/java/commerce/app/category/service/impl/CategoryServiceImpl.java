package commerce.app.category.service.impl;

import commerce.app.category.dto.CategoryDto;
import commerce.app.category.exception.ResourceNotFoundException;
import commerce.app.category.model.Category;
import commerce.app.category.repository.CategoryRepository;
import commerce.app.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import static commerce.app.category.exception.ErrorMessage.*;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category getById(String categoryId) {
        log.debug("Get category from db by id");
        return repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + categoryId));
    }

    @Override
    public List<CategoryDto> findAll() {
        log.debug("Get categories from db");
        List<Category> all = repository.findAll();
        if (all.size() > 0)
            return convertToDto(all);
        throw new ResourceNotFoundException(NO_FOUND_ERROR);
    }

    @Override
    public CategoryDto findById(String categoryId) {
        log.debug("Get category from db by id");
        return convertToDto(getById(categoryId));
    }

    @Override
    public CategoryDto update(Category category) {
        log.debug("Update category from db");
        if (getById(category.getId()) != null) {
            Category result = repository.saveAndFlush(category);
            return convertToDto(result);
        }
        throw new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + category.getId());
    }

    @Override
    public CategoryDto create(Category category) {
        log.debug("Create category from db");
        category.setId(UUID.randomUUID().toString());
        return convertToDto(repository.saveAndFlush(category));
    }

    @Override
    public void delete(String categoryId) {
        log.debug("Delete category from db");
        repository.deleteById(categoryId);
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        CategoryDto result = new CategoryDto();
        BeanUtils.copyProperties(category, result, "imageUrl", "productList");
        result.setImageUrl("url");
        result.setProductList(new HashSet<>());
        return result;
    }

    @Override
    public List<CategoryDto> convertToDto(List<Category> categories) {
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
