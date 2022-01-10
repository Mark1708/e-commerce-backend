package commerce.app.category.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private Long categoryId;
    private String name;
    private String description;
    private Integer count;
    private Double price;
    private List<String> imageUrls;
}
