package kr.leeinwon.devlog.domain.series;

import lombok.Getter;

@Getter
public class SeriesResponse {

    private final Long id;
    private final String name;

    public SeriesResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
