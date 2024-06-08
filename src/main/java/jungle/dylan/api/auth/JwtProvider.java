package jungle.dylan.api.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jungle.dylan.api.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static jungle.dylan.api.constant.auth.JwtConst.ACCESS_TOKEN_EXPIRE_TIME;
import static jungle.dylan.api.constant.auth.JwtConst.REFRESH_TOKEN_EXPIRE_TIME;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Token generateToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date accessTokenExpireIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME.getTime());
        Date refreshTokenExpireIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME.getTime());

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("role", authorities)
                .setExpiration(accessTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return Token.builder()
//                .grantType("Bearer")
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
                .build();
    }

    public String generateToken(String username) {


        long now = new Date().getTime();
        Date accessTokenExpireIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME.getTime());
        Date refreshTokenExpireIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME.getTime());

        String accessToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(accessTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

//    public Authentication getAuthentication(String accessToken) {
//        Claims claims = parseClaims(accessToken);
//
//        if(claims.get("role") == null)
//            throw new RuntimeException("There are no auth information");
//
//        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        UserDetails principal = User.builder()
//                .username(claims.getSubject())
//                .password("")
//                .build();
//
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
