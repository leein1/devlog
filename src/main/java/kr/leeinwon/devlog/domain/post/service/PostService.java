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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final EntityManager entityManager;

    private static final Long DEFAULT_CATEGORY_ID = 1L;

    @Transactional
    public PostResponse createPost(PostRequest request){

        /*
        카테고리 DEFAULT값 정책 통일 필요
         */
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

    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        return new PostResponse(post);
    }

    /*
    페이징 추가 필요
     */
    public List<PostResponse> getAllPost(){
       return postRepository.findAll().stream()
//                .map(PostResponse::new)
               .map(Post -> new PostResponse(Post))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest request){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );

        Long  categoryId = request.getCategoryId() != null
                ? request.getCategoryId()
                : DEFAULT_CATEGORY_ID;

        Category category = entityManager.getReference(Category.class, categoryId);

        post.update(request.getTitle(), request.getContent(), category);
        return new PostResponse(post);
    }

    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        postRepository.delete(post);
    }
}
