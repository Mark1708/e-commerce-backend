package commerce.app.category.dto;

import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String id;
    private String name;
    private String imageUrl;
    private HashSet<String> productList;
}
