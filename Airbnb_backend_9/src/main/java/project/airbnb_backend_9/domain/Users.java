package project.airbnb_backend_9.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime birth;
    private String userDescription;
    @Enumerated(EnumType.STRING)
    private Role role;



    @Builder
    public Users(String username, String email, String password, String birth, String userDescription, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birth = LocalDate.parse(birth, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
//        this.birth = birth;
        this.userDescription = userDescription;
        this.role = role;
    }

    //회원가입시 사용자 소개작성 없이 사용
//    @Builder(builderMethodName = "register")

}
