package kr.leeinwon.devlog.domain.post;

import jakarta.validation.Valid;
import kr.leeinwon.devlog.domain.series.PostSeriesRepository;
import kr.leeinwon.devlog.domain.series.SeriesResponse;
import kr.leeinwon.devlog.domain.series.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final SeriesService seriesService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getAllPosts(){
//        return ResponseEntity.ok(postService.getAllPost());
//    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getPosts(
//            @RequestParam(required = false) Long cursor,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) Long categoryId,
//            @RequestParam(required = false) String tagName,
//            @RequestParam(required = false) String keyword){
//
//        return  ResponseEntity.ok(postService.getAllPosts(cursor, size,
//                categoryId, tagName, keyword));
//
//    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getAllPosts(pageable, categoryId, tagName, keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePost(id, postRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/series")
    public ResponseEntity<List<SeriesResponse>> getSeries(@PathVariable Long postId) {
        return ResponseEntity.ok(seriesService.getSeriesByPostId(postId));
    }

}
