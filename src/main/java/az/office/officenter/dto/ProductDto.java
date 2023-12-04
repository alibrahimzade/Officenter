package az.office.officenter.dto;

import az.office.officenter.enums.StatusRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private StatusRole status;
    private String imageOfProduct;
    private Long categoryId;

}