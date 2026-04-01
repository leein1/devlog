package kr.leeinwon.devlog.domain.post.service;

import jakarta.persistence.EntityManager;
import kr.leeinwon.devlog.domain.category.entity.Category;
import kr.leeinwon.devlog.domain.post.dto.PostRequest;
import kr.leeinwon.devlog.domain.post.dto.PostResponse;
import kr.leeinwon.devlog.domain.post.entity.Post;
import kr.leeinwon.devlog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final EntityManager entityManager;

    private static final Long DEFAULT_CATEGORY_ID = 1L;

    public PostResponse createPost(PostRequest request){
        Long categoryId = request.getCategoryId() != null
                ? request.getCategoryId()
                : DEFAULT_CATEGORY_ID;

        Category category = entityManager.getReference(Category.class, categoryId);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }
}
