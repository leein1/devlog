package kr.leeinwon.devlog.domain.tag;

import lombok.Getter;

@Getter

public class TagResponse {

    private final Long id;
    private final String name;

    public TagResponse(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
