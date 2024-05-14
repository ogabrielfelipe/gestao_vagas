package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ogabrielfelipe.gestao_vagas.exceptions.UserFoundException;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException(); 
            });

        candidateEntity.setPassword(passwordEncoder.encode(candidateEntity.getPassword()));
        return this.candidateRepository.save(candidateEntity);
    }
}
