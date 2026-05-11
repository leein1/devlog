package kr.leeinwon.devlog.domain.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom{

    // 첫 페이지 - 최신순
    @EntityGraph(attributePaths = {"category"})
    List<Post> findAllByOrderByIdDesc(Pageable pageable);

    // cursor- id기준
    @EntityGraph(attributePaths = {"category"})
    List<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);


    @Query("select distinct p from Post p left join fetch p.postTags pt left join fetch pt.tag")
    List<Post> findAllWithTags();

    @Modifying(clearAutomatically = true)
    @Query("update Post p Set p.viewCount = p.viewCount + 1 where p.id=:id")
    void incrementViewCount(@Param("id") Long id);
}
