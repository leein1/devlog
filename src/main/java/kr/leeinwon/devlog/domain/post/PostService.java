package kr.leeinwon.devlog.domain.post;

import kr.leeinwon.devlog.domain.category.Category;
import kr.leeinwon.devlog.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;

    private static final Long DEFAULT_CATEGORY_ID = 1L;

    @Transactional
    public PostResponse createPost(PostRequest request){

        /*
        카테고리 DEFAULT값 정책 통일 필요
         */
        Long categoryId = request.getCategoryId() != null
                ? request.getCategoryId()
                : DEFAULT_CATEGORY_ID;

        Category category = categoryService.getCategoryEntity(categoryId);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    @Transactional
    public PostResponse getPost(Long id){
        postRepository.incrementViewCount(id);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        return new PostResponse(post);
    }

    public List<PostResponse> getAllPosts(Long cursor, int size){
        Pageable pageable = PageRequest.of(0, size);
        List<Post> posts;

        if(cursor == null){
            posts = postRepository.findAllByOrderByIdDesc(pageable);
        } else {
            posts = postRepository.findByIdLessThanOrderByIdDesc(cursor, pageable);
        }

        return posts.stream()
                .map(PostResponse::new)
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

        Category category = categoryService.getCategoryEntity(categoryId);

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
