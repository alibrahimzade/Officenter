package az.office.officenter.dao.repository;

import az.office.officenter.dao.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}