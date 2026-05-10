package kr.leeinwon.devlog.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    // n+1 발생
    List<PostTag> findByPostId(Long postId);

//    // 태그까지 한번에칭
//    @Query("select pt from PostTag pt join fetch  pt.tag where pt.post.id = :postId")
//    List<PostTag> findByPostIdWithTag(@Param("postId") Long postId);

    @Query("select pt from PostTag pt join fetch pt.tag where pt.post.id = :postId")
    List<PostTag> findByPostIdWithTag(@Param("postId") Long postId);

    void deleteByPostIdAndTagId(Long postId, Long tagId);

}
