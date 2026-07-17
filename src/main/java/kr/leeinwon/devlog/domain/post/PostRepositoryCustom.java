package kr.leeinwon.devlog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> searchPostsOld(Long cursor, int size, Long categoryId, String tagName, String keyword);

    Page<Post> searchPosts(Pageable pageable, Long categoryId, String tagName, String keyword);

    List<Post> findNearbyPosts(Long id, int n);
}
