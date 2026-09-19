package kr.leeinwon.devlog.security;

import kr.leeinwon.devlog.domain.user.Role;
import kr.leeinwon.devlog.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails, CredentialsContainer {

    private final Long id;
    private final String email;
    private String password;
    private final Role role;

    private UserPrincipal(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // DB에서 읽은 사용자. 검증용
    public static UserPrincipal from(User user) {
        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }

    // 토큰에서 복원한 사요자, 검증된 요청 - 비밀번호 누락시킴
    public static UserPrincipal of(Long id, String email, Role role) {
        return new UserPrincipal(id, email, null, role);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }


}
