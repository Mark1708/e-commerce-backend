package commerce.app.product.services.impl;

import commerce.app.product.dto.ProductDto;
import commerce.app.product.exception.ResourceNotFoundException;
import commerce.app.product.model.Product;
import commerce.app.product.repository.ProductRepository;
import commerce.app.product.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static commerce.app.product.exception.ErrorMessage.NO_FOUND_BY_ID_ERROR;
import static commerce.app.product.exception.ErrorMessage.NO_FOUND_ERROR;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    public Product getById(String productId) {
        log.debug("Get product from db by id");
        return repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + productId));
    }

    @Override
    public List<ProductDto> findAll() {
        log.debug("Get products from db");
        List<Product> all = repository.findAll();
        if (all.size() > 0)
            return convertToDto(all);
        throw new ResourceNotFoundException(NO_FOUND_ERROR);
    }

    @Override
    public List<ProductDto> findAllByCategoryId(Long categoryId) {
        log.debug("Get products from db by categoryId");
        List<Product> all = repository.findAllByCategoryId(categoryId);
        if (all.size() > 0)
            return convertToDto(all);
        throw new ResourceNotFoundException(NO_FOUND_ERROR);
    }

    @Override
    public ProductDto findById(String productId) {
        log.debug("Get product from db by id");
        return convertToDto(getById(productId));
    }

    @Override
    public ProductDto update(Product product) {
        log.debug("Update product from db");
        if (getById(product.getId()) != null) {
            Product result = repository.saveAndFlush(product);
            return convertToDto(result);
        }
        throw new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + product.getId());
    }

    @Override
    public ProductDto create(Product product) {
        log.debug("Create product from db");
        return convertToDto(repository.saveAndFlush(product));
    }

    @Override
    public void delete(String productId) {
        log.debug("Delete product from db");
        repository.deleteById(productId);
    }

    @Override
    public void deleteByCategoryId(Long categoryId) {
        log.debug("Delete product from db by categoryId");
        repository.deleteAllByCategoryId(categoryId);
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto result = new ProductDto();
        BeanUtils.copyProperties(product, result, "imageUrls");
        result.setImageUrls(new ArrayList<>());
        return result;
    }

    @Override
    public List<ProductDto> convertToDto(List<Product> products) {
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
