package kr.leeinwon.devlog.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수입니다")
    @Size(max = 100, message = "이메일은 100자 이하입니다")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    // Pattern과 size가 같이 있어ㅑㅇ 64바이트 이하가 보장. Bcrypt 72바이트 상한.
    @Pattern(regexp =  "^[\\x21-\\x7E]+$", message = "비밀번호는 영문, 숫자, 특수문자만 사용할 수 있습니다")
    @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하입니다")
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다")
    @Size(max = 50, message = "닉네임은 50자 이하입니다")
    private String nickname;
}
