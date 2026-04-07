package kr.leeinwon.devlog.domain.tag.service;

import jakarta.persistence.EntityManager;
import kr.leeinwon.devlog.domain.post.entity.Post;
import kr.leeinwon.devlog.domain.post.repository.PostRepository;
import kr.leeinwon.devlog.domain.tag.dto.TagRequest;
import kr.leeinwon.devlog.domain.tag.dto.TagResponse;
import kr.leeinwon.devlog.domain.tag.entity.PostTag;
import kr.leeinwon.devlog.domain.tag.entity.Tag;
import kr.leeinwon.devlog.domain.tag.repository.PostTagRepository;
import kr.leeinwon.devlog.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    @Transactional
    public TagResponse createTag(TagRequest tagRequest) {
        if(tagRepository.existsByName(tagRequest.getName())) {
            throw new IllegalArgumentException(tagRequest.getName() + "은(는) 이미 존재하는 태그 이름입니다");
        }

        Tag tag = Tag.builder()
                .name(tagRequest.getName())
                .build();

        Tag savedTag = tagRepository.save(tag);

        return new TagResponse(savedTag);
    }

    public List<TagResponse> getTags() {
        return tagRepository.findAll().stream()
                .map(TagResponse::new)
                .collect(Collectors.toList());
    }

    /*
    태그 수정의 기능이 현 단계에서 필요한가..?
     */

    @Transactional
    public void deleteTag(Long tagId) {
        if(!tagRepository.existsById(tagId)) {
            throw new IllegalArgumentException("태그가 존재하지 않습니다");
        }

        tagRepository.deleteById(tagId);
    }

    // 포스트 태그 저장 결과를 보여줄 일이 있는지 고민 필요
    @Transactional
    public void addTagToPost(Long postId, Long tagId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 태그입니다"));

        PostTag postTag = PostTag.builder()
                .post(post)
                .tag(tag)
                .build();

        postTagRepository.save(postTag);
    }

    public List<TagResponse> getTagsByPostId(Long postId) {

        return postTagRepository.findByPostId(postId).stream()
                .map(postTag -> new TagResponse(postTag.getTag()))
                .collect(Collectors.toList());

    }

    @Transactional
    public void deleteTagsByPostId(Long postId,Long tagId) {
        postTagRepository.deleteByPostIdAndTagId(postId,tagId);
    }

}
