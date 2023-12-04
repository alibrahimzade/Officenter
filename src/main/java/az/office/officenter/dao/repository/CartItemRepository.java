package az.office.officenter.dao.repository;

import az.office.officenter.dao.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}