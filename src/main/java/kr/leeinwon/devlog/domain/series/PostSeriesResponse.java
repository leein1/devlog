package kr.leeinwon.devlog.domain.series;

import lombok.Getter;

@Getter
public class PostSeriesResponse {

    private final Long postId;
    private final String title;
    private final int orderNum;

    public PostSeriesResponse(PostSeries postSeries) {
        this.postId = postSeries.getPost().getId();
        this.title = postSeries.getPost().getTitle();
        this.orderNum = postSeries.getOrderNum();
    }

}
