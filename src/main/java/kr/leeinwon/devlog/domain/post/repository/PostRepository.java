package kr.leeinwon.devlog.domain.post.repository;

import kr.leeinwon.devlog.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    // 첫 페이지 - 최신순
    List<Post> findAllByOrderByIdDesc(Pageable pageable);

    // cursor- id기준
    List<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
