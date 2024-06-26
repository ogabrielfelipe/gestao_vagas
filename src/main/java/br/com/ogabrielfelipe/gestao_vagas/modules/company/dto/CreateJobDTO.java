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
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora Jr.")
    private String description;
    @Schema(example = "GYMPass, Plano de Saúde, day off, etc.")
    private String benefits;
    @Schema(example = "JUNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
