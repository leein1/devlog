package kr.leeinwon.devlog.domain.comment;

import kr.leeinwon.devlog.domain.post.Post;
import kr.leeinwon.devlog.domain.post.PostRepository;
import kr.leeinwon.devlog.domain.user.User;
import kr.leeinwon.devlog.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest commentRequest){

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(commentRequest.getContent())
                .build();

        return new CommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getComments(Long postId){

        return commentRepository.findAllByPostIdWithUser(postId).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public CommentResponse updateComments(Long commentId, CommentRequest commentRequest){

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
        comment.update(commentRequest.getContent());

        return new CommentResponse(comment);
    }

    @Transactional
    public void deleteComments(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
        commentRepository.delete(comment);
    }

}
