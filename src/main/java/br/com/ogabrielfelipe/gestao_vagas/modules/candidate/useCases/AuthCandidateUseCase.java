package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {    
    
    @Value("${security.token.secret.candidate}")
    private String SecretKey;
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("username/password incorrect");
            });
        
        var passwordMatches = this.passwordEncoder
            .matches( authCandidateRequestDTO.password(), candidate.getPassword());
        
        if(!passwordMatches) throw new AuthenticationException("username/password incorrect");


        Algorithm algorithm = Algorithm.HMAC256(SecretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
            .withIssuer("gestao_vagas")
            .withExpiresAt(expiresIn)
            .withClaim("roles", Arrays.asList("candidate"))
            .withSubject(candidate.getId().toString())
            .sign(algorithm);
        
        var authCandidateResponseDTO = AuthCandidateResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();

        return authCandidateResponseDTO;
    }
}
