package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.controllers;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/candidate")
@Tag(name = "Autenticacao", description = "Endpoints responsáveis por realizar a autenticação para acessar as funcionalidades")
public class AuthCandidateController {
    
    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Autentica o usuário do candidato",
            description = "Essa função é responsável por autenticar o cadastro do usuário permintindo realizar o login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "username/password incorrect")
    })
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var token = this.authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        
    }
    
}
