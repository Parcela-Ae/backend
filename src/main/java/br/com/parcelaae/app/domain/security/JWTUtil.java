package br.com.parcelaae.app.domain.security;

import br.com.parcelaae.app.domain.security.model.UserSS;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.Objects.nonNull;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserSS userSS) {
        return Jwts.builder()
                .setId(userSS.getId().toString())
                .setSubject(userSS.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (nonNull(claims)) {
            var username = claims.getSubject();
            var expirationDate = claims.getExpiration();
            var now = new Date(System.currentTimeMillis());
            return nonNull(username) && nonNull(expirationDate) && now.before(expirationDate);
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (nonNull(claims))
            return claims.getSubject();
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }
        catch (Exception e) {
            return null;
        }
    }
}
