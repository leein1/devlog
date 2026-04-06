package kr.leeinwon.devlog.domain.tag.service;

import jakarta.persistence.EntityManager;
import kr.leeinwon.devlog.domain.tag.dto.TagRequest;
import kr.leeinwon.devlog.domain.tag.dto.TagResponse;
import kr.leeinwon.devlog.domain.tag.entity.Tag;
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
    private final EntityManager entityManager;

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


}
