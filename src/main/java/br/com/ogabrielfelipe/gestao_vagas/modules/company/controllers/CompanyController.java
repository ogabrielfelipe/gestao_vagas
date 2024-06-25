package br.com.ogabrielfelipe.gestao_vagas.modules.company.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import br.com.ogabrielfelipe.gestao_vagas.exceptions.UserFoundException;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/company")
@Tag(name = "Empresa", description = "Endpoints responsáveis por gerenciar a entidade empresa")
public class CompanyController {
    
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("")
    @Operation(summary = "Cadastro de empresa",
            description = "Essa função é responsável por cadastrar a empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CompanyEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "User exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
        try{
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        }catch(UserFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
