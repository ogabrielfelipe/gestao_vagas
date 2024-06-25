package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.controllers;

import br.com.ogabrielfelipe.gestao_vagas.exceptions.JobNotFoundException;
import br.com.ogabrielfelipe.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.Entity.ApplyJobEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.Entity.CandidateEntity;
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
@Tag(name = "Candidato", description = "Endpoints responsáveis por gerenciar a entidade candidato")
public class CandidateController {

     @Autowired
     private CreateCandidateUseCase createCandidateUseCase;

     @Autowired
     private ProfileCandidateUseCase profileCandidateUseCase;

     @Autowired
     private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

     @Autowired
     private ApplyJobCandidateUseCase applyJobCandidateUseCase;

     @PostMapping("")
     @Operation(summary = "Cadastro de candidato",
             description = "Essa função é responsável por cadastrar o candidato.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", content = {
                     @Content(schema = @Schema(implementation = CandidateEntity.class))
             }),
             @ApiResponse(responseCode = "400", description = "User exists")
     })
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
     @Operation(summary = "Perfil do candidato",
             description = "Essa função é responsável por buscar as informações do perfil do candidato.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", content = {
                     @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
             }),
             @ApiResponse(responseCode = "400", description = "User not found")
     })
     @SecurityRequirement(name = "jwt_auth")
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
     // --- Tags do Swagger ---
     @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa função é responsável por " +
             "listar todas as vagas disponíveis, baseado no filtro.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", content = {
                     @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
             })
     })
     @SecurityRequirement(name = "jwt_auth")
     // ------------
     public List<JobEntity> findJobByFilter(@RequestParam String filter){
          return this.listAllJobsByFilterUseCase.execute(filter);
     }

     @PostMapping("/job/apply")
     @PreAuthorize("hasRole('CANDIDATE')")
     @Operation(summary = "Aplicar candidatura a uma vaga", description = "Esse endpoint é responsável por realizar" +
             "a candidatura de um candidato a vaga selecionada.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", content = {
                     @Content(array = @ArraySchema(schema = @Schema(implementation = ApplyJobEntity.class)))
             }),
             @ApiResponse(responseCode = "404", description = "User not found. \n Job not found.")

     })
     @SecurityRequirement(name = "jwt_auth")
     public ResponseEntity<Object> ApplyJob(HttpServletRequest request, @RequestBody UUID idJob){
          var candidateId = request.getAttribute("candidate_id");
          try {
               var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()) , idJob);
               return ResponseEntity.ok().body(result);
          }catch (UserNotFoundException | JobNotFoundException objectNotFound){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectNotFound.getMessage());
          }
     }

}
