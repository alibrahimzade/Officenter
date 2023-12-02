package az.office.officenter.dao.repository;

import az.office.officenter.dao.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboutRepository extends JpaRepository<About, Long> {
}
