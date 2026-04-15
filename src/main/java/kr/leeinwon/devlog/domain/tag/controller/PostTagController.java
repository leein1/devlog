package kr.leeinwon.devlog.domain.tag.controller;

import kr.leeinwon.devlog.domain.tag.dto.TagResponse;
import kr.leeinwon.devlog.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/tags")
@RequiredArgsConstructor
public class PostTagController {

    private final TagService tagService;

    @PostMapping("/{tagId}")
    public ResponseEntity<Void> addTagToPost(@PathVariable Long postId, @PathVariable Long tagId){
        tagService.addTagToPost(postId, tagId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTagsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(tagService.getTagsByPostId(postId));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTagByPostId(@PathVariable Long postId, @PathVariable Long tagId){
        tagService.deleteTagsByPostId(postId,tagId);
        return ResponseEntity.noContent().build();
    }
}
