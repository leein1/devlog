package kr.leeinwon.devlog.domain.post.repository;

import kr.leeinwon.devlog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
