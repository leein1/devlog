package kr.leeinwon.devlog.domain.tag.service;

import kr.leeinwon.devlog.domain.post.entity.Post;
import kr.leeinwon.devlog.domain.post.repository.PostRepository;
import kr.leeinwon.devlog.domain.tag.dto.TagRequest;
import kr.leeinwon.devlog.domain.tag.dto.TagResponse;
import kr.leeinwon.devlog.domain.tag.entity.PostTag;
import kr.leeinwon.devlog.domain.tag.entity.Tag;
import kr.leeinwon.devlog.domain.tag.repository.PostTagRepository;
import kr.leeinwon.devlog.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock TagRepository tagRepository;

    @Mock PostTagRepository postTagRepository;

    @Mock PostRepository postRepository;

    @InjectMocks TagService tagService;

    private TagRequest createTagRequest(String name) {
        TagRequest tagRequest = new TagRequest();

        try{
            var field = TagRequest.class.getDeclaredField("name");
            field.setAccessible(true);
            field.set(tagRequest,name);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return tagRequest;
    }

    private Tag createTag(Long id, String name) {
        Tag tag = Tag.builder()
                .name(name)
                .build();

        try{
            var field = Tag.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(tag,id);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return tag;
    }

    @Test
    void 태그_생성_성공(){
        //given
        TagRequest tagRequest = createTagRequest("Spring");
        Tag savedTag = createTag(1L, "Spring");
        given(tagRepository.existsByName("Spring")).willReturn(false);
        given(tagRepository.save(any(Tag.class))).willReturn(savedTag);

        //when
        TagResponse tagResponse = tagService.createTag(tagRequest);

        //then
        assertThat(tagResponse.getName()).isEqualTo("Spring");
        verify(tagRepository, times(1)).save(any());
    }

    @Test
    void 태그_생성_중복(){
        //given
        TagRequest tagRequest = createTagRequest("Spring");
        Tag savedTag = createTag(1L, "Spring");
        given(tagRepository.existsByName("Spring")).willReturn(true);
        given(tagRepository.save(any(Tag.class))).willReturn(savedTag);

        //when

        assertThatThrownBy(() -> tagService.createTag(tagRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 태그로 실패");
    }

    @Test
    void 태그_목록_조회(){
        Tag tag1 = createTag(1L, "Spring");
        Tag tag2 = createTag(2L, "Java");
        given(tagRepository.findAll()).willReturn(List.of(tag1,tag2));

        //when
        List<TagResponse> tagResponses = tagService.getTags();

        //then
        assertThat(tagResponses).hasSize(2);
        assertThat(tagResponses.get(0).getName()).isEqualTo("Spring");
        assertThat(tagResponses.get(1).getName()).isEqualTo("Java");
    }

    @Test
    void 태그_삭제_성공(){
        Tag tag = createTag(1L, "Spring");
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));

        //when
        tagService.deleteTag(1L);

        //then
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    void 태그_삭제_없는태그(){
        //given
        given(tagRepository.findById(1L)).willReturn(Optional.empty());

        //when/then
        assertThatThrownBy(() -> tagService.deleteTag(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 태그");

        verify(tagRepository, never()).delete(any());
    }

    @Test
    void 게시글에_태그_추가_성공() {
        // given
        Post post = Post.builder().build();
        Tag tag = createTag(1L, "Spring");
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        given(postTagRepository.save(any())).willReturn(
                PostTag.builder().post(post).tag(tag).build()
        );

        // when
        tagService.addTagToPost(1L, 1L);

        // then
        verify(postTagRepository).save(any());
    }

    @Test
    void 게시글에_태그_추갸_게시글없음(){
        //given
        given(postRepository.findById(1L)).willReturn(Optional.empty());

        //when.then
        assertThatThrownBy(() -> tagService.addTagToPost(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 게시글");

        verify(postTagRepository, never()).save(any());
    }

    @Test
    void 게시글에_태그_추가_태그없음(){
        //given
        Post post = Post.builder().build();
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(tagRepository.findById(1L)).willReturn(Optional.empty());

        //when.then
        assertThatThrownBy(() -> tagService.addTagToPost(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 태그");
        verify(postTagRepository, never()).save(any());
    }

    /*
    tag가 여러개일 경우가 있는데 이 부분은 어떻게 해결할것인지?
     */
    @Test
    void 게시글_태그_목록조회(){
        //given
        Tag tag1 = createTag(1L, "Spring");
        Tag tag2 = createTag(2L, "Java");
        PostTag postTag1 = PostTag.builder()
                .post(Post.builder().build())
                .tag(tag1)
                .build();
        PostTag postTag2 = PostTag.builder()
                .post(Post.builder().build())
                .tag(tag2)
                .build();
        given(postTagRepository.findByPostId(1L)).willReturn(List.of(postTag1, postTag2));

        List<TagResponse> tagResponses = tagService.getTagsByPostId(1L);

        assertThat(tagResponses).hasSize(2);
        assertThat(tagResponses.get(0).getName()).isEqualTo("Spring");
        assertThat(tagResponses.get(1).getName()).isEqualTo("Java");
    }

    @Test
    void 게시글에_태그_없으면_빈리스트(){
        //given
        given(postTagRepository.findByPostId(1L)).willReturn(List.of());

        //when
        List<TagResponse> tagResponses = tagService.getTagsByPostId(1L);

        //then
        assertThat(tagResponses).isEmpty();
    }

    @Test
    void 게시글_태그_제거_성공(){
        //when
        tagService.deleteTagsByPostId(1L, 1L);

        //then
        verify(postTagRepository).deleteByPostIdAndTagId(1L, 1L);
    }

}
