package commerce.app.product.controller;

import commerce.app.product.dto.ProductDto;
import commerce.app.product.model.Product;
import commerce.app.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value="/v1/category/{categoryId}/product")
public class ProductController {

    @Autowired
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable("categoryId") Long categoryId) {
        if (categoryId == 0)
            return ResponseEntity.ok(service.findAll());
        return ResponseEntity.ok(service.findAllByCategoryId(categoryId));
    }


    @RolesAllowed({ "ADMIN", "USER" })
    @RequestMapping(value="/{productId}",method = RequestMethod.GET)
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") String productId, @PathVariable String categoryId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value="/{productId}",method = RequestMethod.PUT)
    public ResponseEntity<ProductDto> updateProduct( @PathVariable("productId") String productId, @RequestBody Product product) {
        return ResponseEntity.ok(service.update(product));
    }

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<ProductDto>  saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.create(product));
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value="/{productId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") String productId, @PathVariable String categoryId) {
        service.delete(productId);
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        service.deleteByCategoryId(categoryId);
    }
}
