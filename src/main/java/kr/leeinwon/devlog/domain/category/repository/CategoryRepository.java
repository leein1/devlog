package kr.leeinwon.devlog.domain.category.repository;

import kr.leeinwon.devlog.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);
}
