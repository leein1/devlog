package kr.leeinwon.devlog.domain.tag.dto;

import jakarta.validation.constraints.NotBlank;
import kr.leeinwon.devlog.domain.tag.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class TagResponse {

    private final Long id;
    private final String name;

    public TagResponse(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
