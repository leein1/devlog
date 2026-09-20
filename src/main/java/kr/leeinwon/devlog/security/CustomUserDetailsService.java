package kr.leeinwon.devlog.security;

import kr.leeinwon.devlog.domain.user.Provider;
import kr.leeinwon.devlog.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 조회
        return userRepository.findByEmailAndProvider(username, Provider.LOCAL)
                .map(UserPrincipal::from)
                .orElseThrow(
                        () -> new UsernameNotFoundException("이메일 또는 비밀번호가 올바르지 않습니다")
                );


    }
}
