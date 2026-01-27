package ssurent.ssurentbe.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidity,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity,
            @Lazy UserDetailsService userDetailsService) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.userDetailsService = userDetailsService;
    }

    public String createAccessToken(String studentNum) {
        return createToken(studentNum, accessTokenValidity, "access");
    }

    public String createRefreshToken(String studentNum) {
        return createToken(studentNum, refreshTokenValidity, "refresh");
    }

    private String createToken(String studentNum, long validity, String tokenType) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);

        return Jwts.builder()
                .subject(studentNum)
                .claim("type", tokenType)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String studentNum = getStudentNum(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(studentNum);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getStudentNum(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            String type = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("type", String.class);
            return "refresh".equals(type);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
