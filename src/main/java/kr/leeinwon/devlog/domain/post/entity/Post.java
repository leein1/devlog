package kr.leeinwon.devlog.domain.post.entity;

import jakarta.persistence.*;
import kr.leeinwon.devlog.global.common.BaseTimeEntity;
import kr.leeinwon.devlog.domain.category.entity.Category;
import kr.leeinwon.devlog.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false, name = "view_count")
    private int viewCount;

    @Builder
    private Post(User user, Category category, String title, String content) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.viewCount = 0;
    }

    public void update(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
