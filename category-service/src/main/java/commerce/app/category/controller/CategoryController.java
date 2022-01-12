package commerce.app.category.controller;

import commerce.app.category.dto.CategoryDto;
import commerce.app.category.model.Category;
import commerce.app.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value="v1/category")
public class CategoryController {

    @Autowired
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(service.findAll());
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @RequestMapping(value="/{categoryId}",method = RequestMethod.GET)
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(service.findById(categoryId));
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value="/{categoryId}",method = RequestMethod.PUT)
    public ResponseEntity<CategoryDto> updateCategory( @PathVariable("categoryId") Long categoryId, @RequestBody Category category) {
        return ResponseEntity.ok(service.update(category));
    }

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<CategoryDto>  saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok(service.create(category));
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value="/{categoryId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory( @PathVariable("categoryId") Long categoryId) {
        service.delete(categoryId);
    }
}
