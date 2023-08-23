package project.airbnb_backend_9.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class LoginUserDTO {
    private String email;
    private String password;


}
