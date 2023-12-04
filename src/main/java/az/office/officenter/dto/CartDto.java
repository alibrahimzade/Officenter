package az.office.officenter.dto;


import az.office.officenter.dao.entity.CartItem;
import az.office.officenter.dao.entity.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CartDto {

    private Long id;
    private Double totalPrice;
    private Integer totalItems;
    private Customer customer;
    private Long productId;
    private Set<CartItem> cartItems;

}