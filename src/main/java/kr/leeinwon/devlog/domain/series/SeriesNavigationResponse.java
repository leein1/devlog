package kr.leeinwon.devlog.domain.series;

import lombok.Getter;

@Getter
public class SeriesNavigationResponse {
    private final PostSeriesResponse prev;
    private final PostSeriesResponse next;

    public SeriesNavigationResponse(PostSeriesResponse prev, PostSeriesResponse next) {
        this.prev = prev;
        this.next = next;
    }
}
