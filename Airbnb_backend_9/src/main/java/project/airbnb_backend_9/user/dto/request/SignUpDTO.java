package project.airbnb_backend_9.user.dto.request;

import lombok.*;
import project.airbnb_backend_9.domain.Role;
import project.airbnb_backend_9.domain.Users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class SignUpDTO {


    @NotBlank(message = "이름은 필수 입력사항 입니다")
    private String username;

    @NotBlank(message = "이메일은 필수 입력사항 입니다")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항 입니다")
//    @Pattern(regexp = "")
    private String password;

    @NotBlank(message = "생년월은 필수 입력사항 입니다")
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
