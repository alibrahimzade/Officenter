package az.office.officenter.dao.repository;

import az.office.officenter.dao.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {


}