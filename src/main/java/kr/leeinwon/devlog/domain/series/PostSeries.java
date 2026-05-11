package kr.leeinwon.devlog.domain.series;

import jakarta.persistence.*;
import kr.leeinwon.devlog.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_series")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

    @Column(nullable = false, name = "order_num")
    private int orderNum;

    @Builder
    private PostSeries(Post post, Series series, int orderNum) {
        this.post = post;
        this.series = series;
        this.orderNum = orderNum;
    }
}
