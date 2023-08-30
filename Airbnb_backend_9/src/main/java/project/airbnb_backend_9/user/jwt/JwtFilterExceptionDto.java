package project.airbnb_backend_9.user.jwt;

import lombok.Getter;

@Getter
public class JwtFilterExceptionDto {

    private String message;

    public JwtFilterExceptionDto(String message) {
        this.message = message;
    }
}
