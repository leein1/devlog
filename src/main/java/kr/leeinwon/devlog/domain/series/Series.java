package kr.leeinwon.devlog.domain.series;

import jakarta.persistence.*;
import kr.leeinwon.devlog.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "series")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Builder
    private Series(User user, String name) {
        this.user = user;
        this.name = name;
    }
}
