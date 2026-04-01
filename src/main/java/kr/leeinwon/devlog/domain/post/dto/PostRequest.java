package kr.leeinwon.devlog.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    private String content;

    private Long categoryId;
}
