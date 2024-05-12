package br.com.ogabrielfelipe.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {  
    
    @Value("${security.token.secret}")
    private String SecretKey;
    
    
    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(SecretKey);
        
        try{
            var subject = JWT.require(algorithm)
            .build()
            .verify(token);
            return subject;
        }catch(JWTVerificationException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
