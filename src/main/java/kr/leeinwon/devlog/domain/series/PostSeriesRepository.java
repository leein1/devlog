package kr.leeinwon.devlog.domain.series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostSeriesRepository extends JpaRepository<PostSeries, Long> {

    @Query("select ps from PostSeries ps join  fetch ps.post where ps.series.id= :sereisId order by ps.orderNum asc")
    List<PostSeries> findAllBySeriesIdWithPost(@Param("seriesId") Long seriesId);

    int countBySeriesId(Long seriesId);

    boolean existBySeriesIdAndPostId(Long seriesId, Long postId);

}
