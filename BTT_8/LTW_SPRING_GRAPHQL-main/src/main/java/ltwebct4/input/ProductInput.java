package ltwebct4.input;

import lombok.Data;

@Data
public class ProductInput {
    private String title;
    private Integer quantity;
    private String description;
    private Double price;
    private Long userId; // ID của User sở hữu (Many-to-One)
}