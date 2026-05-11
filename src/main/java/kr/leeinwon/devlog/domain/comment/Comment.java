package kr.leeinwon.devlog.domain.comment;

import jakarta.persistence.*;
import kr.leeinwon.devlog.domain.post.Post;
import kr.leeinwon.devlog.domain.user.User;
import kr.leeinwon.devlog.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String content;

    @Builder
    private Comment(Post post, User user, String content) {

        this.post = post;
        this.user = user;
        this.content = content;
    }

    public void update(String content){
        this.content = content;
    }
}
