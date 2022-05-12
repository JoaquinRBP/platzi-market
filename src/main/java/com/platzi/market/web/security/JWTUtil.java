package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTUtil {
    private static final String KEY = "pl4tz1";
    public String generateToken(UserDetails userDetails){

        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,KEY ).compact();
    }
    //Validar si el token es correcto
    public boolean validateToken(String token, UserDetails userDetails){
        return userDetails.getUsername().equals(getUsername(token)) && !isTokenExpired(token);
    }
    public String getUsername(String token){
        return getClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        //Si esta antes retorna True, sino False (aun no expira)
        return getClaims(token).getExpiration().before(new Date());
    }
    //Claims objetos de JWT
    private Claims getClaims(String token){
        //Si la firma no es valida devolverá un FORBIDDEN
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
