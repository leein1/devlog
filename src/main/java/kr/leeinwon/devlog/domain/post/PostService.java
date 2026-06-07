package kr.leeinwon.devlog.domain.post;

import kr.leeinwon.devlog.domain.category.Category;
import kr.leeinwon.devlog.domain.category.CategoryService;
import kr.leeinwon.devlog.domain.series.PostSeries;
import kr.leeinwon.devlog.domain.series.PostSeriesRepository;
import kr.leeinwon.devlog.domain.series.Series;
import kr.leeinwon.devlog.domain.series.SeriesRepository;
import kr.leeinwon.devlog.domain.tag.PostTag;
import kr.leeinwon.devlog.domain.tag.PostTagRepository;
import kr.leeinwon.devlog.domain.tag.Tag;
import kr.leeinwon.devlog.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;

    // 작성 및 수정시 카테고리, 태그, 시리즈 추가 관련
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final PostSeriesRepository postSeriesRepository;
    private final SeriesRepository seriesRepository;

    private static final Long DEFAULT_CATEGORY_ID = 1L;

    @Transactional
    public PostResponse createPost(PostRequest request){

        /*
        카테고리 DEFAULT값 정책 통일 필요
         */
        Long categoryId = request.getCategoryId() != null
                ? request.getCategoryId()
                : DEFAULT_CATEGORY_ID;

        Category category = categoryService.getCategoryEntity(categoryId);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .build();

        Post savedPost = postRepository.save(post);

        // 태그
        if(request.getTagIdList() != null &&   !request.getTagIdList().isEmpty()){
            for(Long tagId : request.getTagIdList()){
                Tag tag = tagRepository.findById(tagId).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 태그입니다")
                );
                postTagRepository.save(
                        PostTag.builder()
                                .post(savedPost)
                                .tag(tag)
                                .build()
                );
            }
        }
        // 시리즈
        if(request.getSeriesId() != null){
            Series series = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 시리즈 입니다")
            );
            int orderNum = postSeriesRepository.countBySeriesId(request.getSeriesId())+1;
            postSeriesRepository.save(
                    PostSeries.builder()
                            .post(savedPost)
                            .series(series)
                            .orderNum(orderNum)
                            .build()
            );

        }

        return new PostResponse(savedPost);
    }

    @Transactional
    public PostResponse getPost(Long id){
        postRepository.incrementViewCount(id);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        return new PostResponse(post);
    }

    public List<PostResponse> getAllPosts(Long cursor, int size
            ,Long categoryId, String tagName, String keyword){

        return postRepository.searchPosts(cursor,size,categoryId,tagName,keyword)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest request){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );

        Long  categoryId = request.getCategoryId() != null
                ? request.getCategoryId()
                : DEFAULT_CATEGORY_ID;

        Category category = categoryService.getCategoryEntity(categoryId);
        post.update(request.getTitle(), request.getContent(), category);

        //태그 전체 교체
        postTagRepository.deleteByPostId(id);
        if(request.getTagIdList() != null &&  !request.getTagIdList().isEmpty()){
            for(Long tagId : request.getTagIdList()){
                Tag tag = tagRepository.findById(tagId).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 태그입니다")
                );
                postTagRepository.save(PostTag.builder()
                        .post(post)
                        .tag(tag)
                        .build()
                );
            }
        }
        //시리즈 전체 교체
        postSeriesRepository.deleteByPostId(id);
        if(request.getSeriesId() != null) {
            Series series = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 시리즈 입니다" )
            );
            int orderNum = postSeriesRepository.countBySeriesId(request.getSeriesId()) + 1;
            postSeriesRepository.save(
                    PostSeries.builder()
                            .post(post)
                            .series(series)
                            .orderNum(orderNum)
                            .build()
            );
        }
        return new PostResponse(post);
    }

    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        postRepository.delete(post);
    }
}
