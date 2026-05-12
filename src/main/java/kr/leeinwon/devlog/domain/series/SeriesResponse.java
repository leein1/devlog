package kr.leeinwon.devlog.domain.series;

import lombok.Getter;

@Getter
public class SeriesResponse {

    private final Long id;
    private final String name;

    public SeriesResponse(Series series) {
        this.id = series.getId();
        this.name = series.getName();
    }
}
