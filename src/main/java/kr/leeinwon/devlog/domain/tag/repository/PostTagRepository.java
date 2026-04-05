package kr.leeinwon.devlog.domain.tag.repository;

import kr.leeinwon.devlog.domain.tag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findByPostId(Long postId);

    void deleteByPostIdAndTagId(Long postId, Long tagId);

}
