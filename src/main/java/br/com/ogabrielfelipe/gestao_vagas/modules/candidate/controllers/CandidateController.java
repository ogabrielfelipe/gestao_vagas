package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.controllers;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

     @Autowired
     private CreateCandidateUseCase createCandidateUseCase;

     @Autowired
     private ProfileCandidateUseCase profileCandidateUseCase;

     @Autowired
     private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

     @PostMapping("")
     public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
          try {
               var result = createCandidateUseCase.execute(candidateEntity);
               return ResponseEntity.ok().body(result);
          } catch (Exception e) {
               return ResponseEntity.badRequest().body(e.getMessage());
          }
     }

     @GetMapping("/")
     @PreAuthorize("hasRole('CANDIDATE')")
     public ResponseEntity<Object> getCandidate(HttpServletRequest request) {
          var candidateId = request.getAttribute("candidate_id");

          try {
               var profile = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
               return ResponseEntity.ok().body(profile);
          } catch (Exception e) {
               return ResponseEntity.badRequest().body(e.getMessage());
          }
     }

     @GetMapping("/job")
     @PreAuthorize("hasRole('CANDIDATE')")
     public List<JobEntity> findJobByFilter(@RequestParam String filter){
          return this.listAllJobsByFilterUseCase.execute(filter);
     }

}
