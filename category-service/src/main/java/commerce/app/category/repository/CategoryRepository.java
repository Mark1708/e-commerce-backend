package commerce.app.category.repository;

import commerce.app.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findById(String categoryId);

    List<Category> findAll();

    void deleteById(String categoryId);
}
