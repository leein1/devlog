package kr.leeinwon.devlog.domain.series;

import kr.leeinwon.devlog.domain.post.Post;
import kr.leeinwon.devlog.domain.post.PostRepository;
import kr.leeinwon.devlog.domain.user.User;
import kr.leeinwon.devlog.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final PostSeriesRepository postSeriesRepository;
    private final PostRepository postRepository;

    @Transactional
    public SeriesResponse createSeries(SeriesRequest seriesRequest) {

        User user = userRepository.findById(seriesRequest.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Series series = Series.builder()
                .user(user)
                .name(seriesRequest.getName())
                .build();

        return new SeriesResponse(seriesRepository.save(series));
    }

    public List<SeriesResponse> getAllSeries() {
        return seriesRepository.findAll().stream()
                .map(SeriesResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSeries(Long id) {
        Series series = seriesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("시리즈가 존재하지 않습니다")
        );
        seriesRepository.delete(series);
    }


    // 나중에 시리즈의 순서를 바꾸고 싶을때의 상황이 현재 없음
    @Transactional
    public void addPostToSeries(Long seriesId, Long postId) {
        Series series = seriesRepository.findById(seriesId).orElseThrow(
                () -> new IllegalArgumentException("시리즈가 존재하지 않습니다")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습ㄴ디ㅏ")
        );
        if(postSeriesRepository.existsBySeriesIdAndPostId(seriesId, postId)){
            throw new IllegalArgumentException("이미 시리즈에 등록된 게시글입니다");
        }

        int nextOrder = postSeriesRepository.countBySeriesId(seriesId)+1;

        PostSeries postSeries = PostSeries.builder()
                .series(series)
                .post(post)
                .orderNum(nextOrder)
                .build();
        postSeriesRepository.save(postSeries);
    }

    public List<PostSeriesResponse> getAllPostInSeries(Long seriesId) {
        return postSeriesRepository.findAllBySeriesIdWithPost(seriesId).stream()
                .map(PostSeriesResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removePostFromSeries(Long seriesId, Long postId) {
        PostSeries postSeries = postSeriesRepository.findBySeriesIdAndPostId(seriesId, postId).orElseThrow(
                () -> new IllegalArgumentException("시리즈에 등록되지 않은 게시글이빈다")
        );

        postSeriesRepository.delete(postSeries);
    }

    public SeriesNavigationResponse getNavigation(Long seriesId, Long postId){
        PostSeries current = postSeriesRepository.findBySeriesIdAndPostId(seriesId, postId).orElseThrow(
                () -> new IllegalArgumentException("시리즈에 등록되지 않은 게시글입니다")
        );

        List<PostSeries> prevList =
                postSeriesRepository.findPrevPost(seriesId, current.getOrderNum(), PageRequest.of(0,1));
        List<PostSeries> nextList =
                postSeriesRepository.findNextPost(seriesId, current.getOrderNum(), PageRequest.of(0, 1));

        PostSeriesResponse prev = prevList.isEmpty() ? null : new PostSeriesResponse(prevList.get(0));
        PostSeriesResponse next = nextList.isEmpty() ? null : new PostSeriesResponse(nextList.get(0));

        return new SeriesNavigationResponse(prev, next);
    }


}
