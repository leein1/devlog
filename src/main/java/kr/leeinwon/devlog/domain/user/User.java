package kr.leeinwon.devlog.domain.user;

import jakarta.persistence.*;
import kr.leeinwon.devlog.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Provider provider;

    @Column(length = 100)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;


//    @Builder
//    private User(String email, String password, String nickname) {
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//    }

    private User(String email, String password, String nickname, Provider provider, String providerId, Role role) {
        // 필드 대입만, 검증은 팩토리에서 끝낸 뒤 돌아옴
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private static void requireText(String value, String message) {
        if (!hasText(value)) {
            throw new IllegalArgumentException(message);
        }
    }


    public static User ofLocal(String email, String encodedPassword, String nickname) {
        //  null/공백 거부
        // private 생성자 호출 - provider=LOCAL, providerId=null, role=USER를 여기서 지정
        requireText(email, "이메일은 필수입니다");
        requireText(encodedPassword, "비밀번호는 필수입니다");
        requireText(nickname, "닉네임은 필수입니다");

        return new User(email, encodedPassword, nickname, Provider.LOCAL, null, Role.USER);
    }

    public static User ofSocial(Provider provider, String providerId, String email, String nickname) {
        // 1) provider null 거부 + provider != LOCAL 검사
        // 2) providerId null/공백 거부
        // 3) nickname 비어 있으면 기본값 생성
        // 4) private 생성자 호출 — password=null, role=USER
        if (provider == null || provider == Provider.LOCAL) {
            throw new IllegalArgumentException("소셜 가입 제공자가 필요합니다");
        }
        requireText(providerId, "소셜 식별자는 필수입니다");

        String resolvedNickname = hasText(nickname) ? nickname : provider.name().toLowerCase() + "_" + providerId;

        return new User(email, null, resolvedNickname, provider, providerId, Role.USER);
    }


}
