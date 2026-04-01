package kr.leeinwon.devlog.domain.post.dto;

import kr.leeinwon.devlog.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final int veiwCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.veiwCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
