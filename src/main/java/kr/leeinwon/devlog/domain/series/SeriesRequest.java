package kr.leeinwon.devlog.domain.series;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeriesRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long userId;

}
