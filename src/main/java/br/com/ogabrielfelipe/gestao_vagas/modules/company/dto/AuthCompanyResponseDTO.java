package br.com.ogabrielfelipe.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCompanyResponseDTO {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnZXN0YW9fdmFnYXMiLCJleHAiOjE3MTg4NTAwODYsInJvbG" +
            "VzIjpbIkNBTkRJREFURSJdLCJzdWIiOiIzMWMzOTFlNC05ODE2LTRjZDAtYjc4Ni0yYTYxYzk0MWU2ZTgifQ.9CdZqdCihQ" +
            "nIlnaQYiNCVXdHdrHEfRcyKD8Zz6h4ibM", description = "Retorna o token para realizar autenticação " +
            "nas APIs protegidas")
    private String access_token;

    @Schema(example = "1718850086042", description = "Retorna o tempo de duração do token.")
    private Long expires_in;

}
