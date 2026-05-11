package kr.leeinwon.devlog.domain.post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> searchPosts(Long cusor, int size, Long categoryId, String tagName, String keyword);
}
