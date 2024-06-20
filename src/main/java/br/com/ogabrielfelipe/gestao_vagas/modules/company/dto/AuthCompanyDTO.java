package br.com.ogabrielfelipe.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AuthCompanyDTO {

    @Schema(example = "company.entertainment")
    private String username;

    @Schema(example = "company*entertainment@123")
    private String password;
    
}
