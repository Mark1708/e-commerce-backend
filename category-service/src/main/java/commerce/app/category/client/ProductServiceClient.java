package commerce.app.category.client;

import commerce.app.category.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @RequestMapping(value="/v1/category/{categoryId}/product/all",method = RequestMethod.GET)
    ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable("categoryId") Long categoryId);

    @RequestMapping(value = "/v1/category/{categoryId}/product", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductsByCategoryId(@PathVariable("categoryId") Long categoryId);
}
