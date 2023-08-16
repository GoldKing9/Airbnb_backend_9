package project.airbnb_backend_9.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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
    private LocalDateTime createAt;
}
