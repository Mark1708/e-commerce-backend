package commerce.app.category.service.impl;

import commerce.app.category.client.ProductServiceClient;
import commerce.app.category.dto.CategoryDto;
import commerce.app.category.dto.ProductDto;
import commerce.app.category.exception.ResourceNotFoundException;
import commerce.app.category.model.Category;
import commerce.app.category.repository.CategoryRepository;
import commerce.app.category.service.CategoryService;
import commerce.app.category.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static commerce.app.category.exception.ErrorMessage.NO_FOUND_BY_ID_ERROR;
import static commerce.app.category.exception.ErrorMessage.NO_FOUND_ERROR;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductServiceClient client;

    public Category getById(Long categoryId) {
        log.debug("Get category from db by id");
        return repository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + categoryId));
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
    public CategoryDto findById(Long categoryId) {
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
        return convertToDto(repository.saveAndFlush(category));
    }

    @Override
    public void delete(Long categoryId) {
        log.debug("Delete products by category");
        client.deleteProductsByCategoryId(categoryId);
        log.debug("Delete category from db");
        repository.deleteById(categoryId);
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        CategoryDto result = new CategoryDto();
        BeanUtils.copyProperties(category, result, "imageUrl", "productList");
        result.setImageUrl("url");
        try {
            result.setProductList(getProductsByCategoryId(category.getId()));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<CategoryDto> convertToDto(List<Category> categories) {
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

//    @CircuitBreaker(name = "categoryService", fallbackMethod = "buildFallbackCategoryList")
//    @RateLimiter(name = "categoryService", fallbackMethod = "buildFallbackCategoryList")
//    @Retry(name = "retryCategoryService", fallbackMethod = "buildFallbackCategoryList")
//    @Bulkhead(name = "bulkheadCategoryService", type= Type.THREADPOOL, fallbackMethod = "buildFallbackCategoryList")
    private List<ProductDto> getProductsByCategoryId(Long categoryId) throws TimeoutException {
        log.debug("getProductsByCategoryId Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
//        randomlyRunLong();
        return client.getAllProducts(categoryId).getBody();
    }

//    @SuppressWarnings("unused")
//    private List<ProductDto> buildFallbackCategoryList(Long categoryId, Throwable t){
//        List<ProductDto> fallbackList = new ArrayList<>();
//        ProductDto productDto = new ProductDto(
//                "0000000-00-00000", categoryId, "No product",
//                "Sorry no product information currently available",
//                0, 0.0,
//                new ArrayList<String>(){{
//                    add("https://cs6.pikabu.ru/post_img/big/2014/06/30/6/1404117274_1958249232.jpg");
//                }}
//        );
//        fallbackList.add(productDto);
//        return fallbackList;
//    }
//
//    private void randomlyRunLong() throws TimeoutException {
//        Random rand = new Random();
//        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
//        if (randomNum==3) sleep();
//    }
//    private void sleep() throws TimeoutException{
//        try {
//            System.out.println("Sleep");
//            Thread.sleep(5000);
//            throw new java.util.concurrent.TimeoutException();
//        } catch (InterruptedException e) {
//            log.error(e.getMessage());
//        }
//    }
}
