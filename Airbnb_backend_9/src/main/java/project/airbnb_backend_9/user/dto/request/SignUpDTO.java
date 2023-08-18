package project.airbnb_backend_9.user.dto.request;

import lombok.*;
import project.airbnb_backend_9.domain.Role;
import project.airbnb_backend_9.domain.Users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class SignUpDTO {


    @NotBlank(message = "이름은 필수 입력사항 입니다")
    @Size(min = 2, max = 5, message = "이름은 2자에서 5자 사이여야 합니다")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글 2자에서 5자 사이로만 구성되어야 하며, 자음 또는 모음만 단독으로 들어올 수 없습니다")
    private String username;

    @NotBlank(message = "이메일은 필수 입력사항 입니다")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항 입니다")
    @Pattern(regexp = "^[a-z0-9]{4,14}$", message = "비밀번호는 4~14자의 숫자와 소문자로만 구성되어야 합니다")
    private String password;

    @NotBlank(message = "생년월은 필수 입력사항 입니다")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월은 yyyy-mm-dd 형식으로 입력해야 합니다")
    private String birth;

    public Users toEntity(String password){
        return Users.builder()
                .username(this.username)
                .email(this.email)
                .password(password)
                .birth(this.birth)
                .role(Role.USER)
                .build();
    }

    @Builder
    public SignUpDTO(String username, String email, String password, String birth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birth = birth;
    }
}
