package az.office.officenter.dao.repository;

import az.office.officenter.dao.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomer_Id(Long customerId);

}