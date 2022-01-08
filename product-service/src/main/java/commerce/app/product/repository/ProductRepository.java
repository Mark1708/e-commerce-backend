package commerce.app.product.repository;

import commerce.app.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findById(String productId);

    List<Product> findAll();

    List<Product> findAllByCategoryId(Long categoryId);

    void deleteById(String productId);

    void deleteAllByCategoryId(Long categoryId);
}
