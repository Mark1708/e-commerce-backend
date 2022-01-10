package commerce.app.product.services;

import commerce.app.product.dto.ProductDto;
import commerce.app.product.model.Product;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    List<ProductDto> findAllByCategoryId(Long categoryId);

    ProductDto findById(String productId);

    ProductDto update(Product product);

    ProductDto create(Product product);

    void delete(String productId);

    void deleteByCategoryId(Long categoryId);

    ProductDto convertToDto(Product product);

    List<ProductDto> convertToDto(List<Product> products);
}
