package kr.leeinwon.devlog.domain.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    private String content;

    private Long categoryId;

    private Long seriesId;

    private List<Long> tagIdList;
}
