package commerce.app.category.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @Id
    @Column(name = "category_id", unique = true, nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
}
