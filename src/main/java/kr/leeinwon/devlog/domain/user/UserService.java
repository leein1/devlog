package kr.leeinwon.devlog.domain.user;


import kr.leeinwon.devlog.global.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(SignupRequest signupRequest){
        String email = signupRequest.getEmail();

        if( userRepository.existsByEmailAndProvider(email,Provider.LOCAL)){
            throw new ConflictException("이미 가입된 이메일입니다");
        }
        String encoded = passwordEncoder.encode(signupRequest.getPassword());
        User user = User.ofLocal(email,encoded,signupRequest.getNickname());
        return new UserResponse(userRepository.save(user));
    }
}
