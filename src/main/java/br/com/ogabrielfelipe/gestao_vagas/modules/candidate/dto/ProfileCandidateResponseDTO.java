package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    private UUID id;

    @Schema(example = "Desenvolvedor Java")
    private String description;

    @Schema(example = "fulano.sicrano")
    private String username;

    @Schema(example = "fulano.sicrano@mail.com")
    private String email;

    @Schema(example = "Fulano Sicrano")
    private String name;
}
