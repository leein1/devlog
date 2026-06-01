package kr.leeinwon.devlog.domain.series;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostSeriesRepository extends JpaRepository<PostSeries, Long> {

    @Query("select ps from PostSeries ps join  fetch ps.post where ps.series.id= :seriesId order by ps.orderNum asc")
    List<PostSeries> findAllBySeriesIdWithPost(@Param("seriesId") Long seriesId);

    int countBySeriesId(Long seriesId);

    boolean existsBySeriesIdAndPostId(Long seriesId, Long postId);

    Optional<PostSeries> findBySeriesIdAndPostId(Long seriesId, Long postId);

    @Query("select  ps from PostSeries  ps join fetch ps.series where ps.post.id = :postId")
    List<PostSeries> findAllByPostIdWithSeries(@Param("postId") Long postId);


    @Query("select ps from PostSeries ps join fetch ps.post where ps.series.id = :seriesId and ps.orderNum < :orderNum order by ps.orderNum desc")
    List<PostSeries> findPrevPost(@Param("seriesId") Long seriesId, @Param("orderNum") int orderNum, Pageable pageable);

    @Query("select ps from PostSeries ps join fetch ps.post where ps.series.id = :seriesId and ps.orderNum > :orderNum order by ps.orderNum asc")
    List<PostSeries> findNextPost(@Param("seriesId") Long seriesId, @Param("orderNum") int orderNum, Pageable pageable);

   void deleteByPostId(Long postId);

}
