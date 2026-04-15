package kr.leeinwon.devlog.domain.post.controller;

import jakarta.validation.Valid;
import kr.leeinwon.devlog.domain.post.dto.PostRequest;
import kr.leeinwon.devlog.domain.post.dto.PostResponse;
import kr.leeinwon.devlog.domain.post.repository.PostRepository;
import kr.leeinwon.devlog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getAllPosts(){
//        return ResponseEntity.ok(postService.getAllPost());
//    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(
            @RequestParam(required = false) Long cursor
            ,@RequestParam(defaultValue = "10") int size){

        return  ResponseEntity.ok(postService.getPosts(cursor, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.updatePost(id, postRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
