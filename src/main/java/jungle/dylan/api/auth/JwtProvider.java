package jungle.dylan.api.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jungle.dylan.api.error.errorcode.AuthErrorCode;
import jungle.dylan.api.error.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static jungle.dylan.api.constant.auth.JwtConst.ACCESS_TOKEN_EXPIRE_TIME;
import static jungle.dylan.api.error.errorcode.AuthErrorCode.EXPIRED_TOKEN;
import static jungle.dylan.api.error.errorcode.AuthErrorCode.TOKEN_INVALID;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {

        Date now = new Date();
        Date accessTokenExpireIn = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME.getTime());

        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("-------1");
            throw new ApiException(TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            log.info("-------2");
            throw new ApiException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("-------3");
            throw new ApiException(TOKEN_INVALID);
        } catch (IllegalArgumentException e) {
            log.info("-------4");
            throw new ApiException(TOKEN_INVALID);
        }
    }

}
