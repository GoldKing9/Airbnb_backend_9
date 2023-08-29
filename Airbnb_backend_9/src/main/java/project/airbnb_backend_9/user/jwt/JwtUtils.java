package project.airbnb_backend_9.user.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.airbnb_backend_9.user.jwt.auth.PrincipalDetails;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;
    @Value("${jwt.token-validity-in-seconds}")
    private long EXPIRATION_TIME;

    //암호화
    public SecretKey decode(){
        byte[] secretBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    //토큰생성
    public String createToken(PrincipalDetails principalDetails,SecretKey secretKey){
        return Jwts.builder()
                .setSubject(principalDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", principalDetails.getUsers().getUserId())
                .claim("email", principalDetails.getUsers().getEmail())
                .signWith(secretKey)
                .compact();
    }
}
