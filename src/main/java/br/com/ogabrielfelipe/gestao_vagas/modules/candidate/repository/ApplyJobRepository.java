package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.repository;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.Entity.ApplyJobEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.Entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
