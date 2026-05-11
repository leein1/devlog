package kr.leeinwon.devlog.domain.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    public final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId
            , @RequestBody @Valid CommentRequest commentRequest) {

        return ResponseEntity.ok(
                commentService.createComment(postId, commentRequest)
        );
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long postId) {

        return ResponseEntity.ok(commentService.getAllComment(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId
            , @PathVariable Long commentId
            , @RequestBody @Valid CommentRequest commentRequest) {

        return ResponseEntity.ok(
                commentService.updateComments(commentId, commentRequest)
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable Long postId
            , @PathVariable Long commentId
    ){
        commentService.deleteComments(commentId);
        return ResponseEntity.noContent().build();
    }


}
