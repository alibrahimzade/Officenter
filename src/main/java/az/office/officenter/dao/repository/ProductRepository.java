package az.office.officenter.dao.repository;

import az.office.officenter.dao.entity.Product;
import az.office.officenter.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductDto> findAllByStatusTrue();
}