package com.thanksang.HentoriManager.config;

import com.thanksang.HentoriManager.error.JwtErrors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.JwkSetUriJwtDecoderBuilderCustomizer;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtHelper {
    private SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("1caunoidaidethoadieukiendangkyCaiJwt44nay9jfjwfjjvahv"));


    public String createJwt(String json){
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(30);
        Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        String jwt = Jwts.builder()
                .signWith(key)
                .subject(json)
                .expiration(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())).compact();
        return jwt;
    }

    public String loadJwt(String jwt){
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
            String json = claimsJws.getPayload().getSubject();
            return json;
        } catch (JwtException e){
            throw new JwtErrors(e.getMessage(), e.getCause());
        }
    }
}
