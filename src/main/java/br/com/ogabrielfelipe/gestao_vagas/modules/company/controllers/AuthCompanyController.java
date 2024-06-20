package br.com.ogabrielfelipe.gestao_vagas.modules.company.controllers;


import br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ogabrielfelipe.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/company")
@Tag(name = "Autenticacao", description = "Endpoints responsáveis por realizar a autenticação para acessar as funcionalidades")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;
    
    @PostMapping("/auth")
    @Operation(summary = "Autentica o usuário da empresa",
            description = "Essa função é responsável por autenticar o cadastro do usuário permintindo realizar o login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "username/password incorrect")
    })
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO){
        try{
            var result =  this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
