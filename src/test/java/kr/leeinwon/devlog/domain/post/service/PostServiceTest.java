package kr.leeinwon.devlog.domain.post.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import kr.leeinwon.devlog.domain.category.entity.Category;
import kr.leeinwon.devlog.domain.post.dto.PostRequest;
import kr.leeinwon.devlog.domain.post.dto.PostResponse;
import kr.leeinwon.devlog.domain.post.entity.Post;
import kr.leeinwon.devlog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PostServiceTest.class);
    @Mock
    PostRepository postRepository;

    @Mock
    EntityManager  entityManager;

    @InjectMocks
    PostService postService;

    private Post createPost(Long id, String title, String content, Category category) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();

        try{
            var idField = Post.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(post, id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return post;
    }

    private Category createCategory(Long id, String name) {
        Category category = Category.builder()
                .name(name)
                .build();

        try{
            var idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(category, id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return category;
    }

    private PostRequest createPostRequest(String title, String content, Long categoryId) {

        PostRequest postRequest = PostRequest.builder()
                .title(title)
                .content(content)
                .categoryId(categoryId)
                .build();
        return postRequest;
    }

    @Test
    void 게시글_생성_성공(){
        //given
        PostRequest postRequest = createPostRequest("제목", "내용", null);
        Category category = createCategory(1L, "미분류");
        Post post = createPost(1L, "제목", "내용",  category);

        given(entityManager.getReference(Category.class, 1L)).willReturn(category);
        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        PostResponse postResponse = postService.createPost(postRequest);
        log.info("postResponse_category_id={}", postResponse.getCategoryId());

        //then
        assertThat(postResponse.getTitle()).isEqualTo("제목");
        assertThat(postResponse.getContent()).isEqualTo("내용");
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void 게시글_생성_카테고리_지정(){
        //given
        PostRequest postRequest = createPostRequest("제목", "내용", 2L);
        Category category = createCategory(2L, "백준");
        Post post = createPost(1L, "제목", "내용",  category);

        given(entityManager.getReference(Category.class, 2L)).willReturn(category);
        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        PostResponse postResponse = postService.createPost(postRequest);

        //then
        assertThat(postResponse.getTitle()).isEqualTo("제목");
        assertThat(postResponse.getContent()).isEqualTo("내용");
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void 게시글_단건_조회_성공(){
        //given
        Category category = createCategory(1L, "미분류");
        Post post = createPost(1L, "제목", "내용", category);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        //when
        PostResponse postResponse = postService.getPost(1L);

        //then
        assertThat(postResponse.getId()).isEqualTo(1L);
        assertThat(postResponse.getTitle()).isEqualTo("제목");
    }

    @Test
    void 게시글_단건_조회_예외(){
        //given
        given(postRepository.findById(1L)).willReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> postService.getPost(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("게시물 존재하지 않음");
    }

}
