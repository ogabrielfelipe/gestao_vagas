package br.com.ogabrielfelipe.gestao_vagas.modules.company.useCases;

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

import br.com.ogabrielfelipe.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.repositories.CompanyRepository;


@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String SecretKey;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername())
            .orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("username/password incorrect");
                }
            );
        
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
     
        if (!passwordMatches){
            throw new AuthenticationException("username/password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(SecretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("gestao_vagas")
            .withExpiresAt(expiresIn)
            .withSubject(company.getId().toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
            .expires_in(expiresIn.toEpochMilli())
            .access_token(token)
            .build();

        return authCompanyResponseDTO;
    }
}
