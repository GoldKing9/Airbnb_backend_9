package project.airbnb_backend_9.user.jwt;

public interface JwtProperties {
    String SECRET = "kyeongseon"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000 * 60 * 24; // 1일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
