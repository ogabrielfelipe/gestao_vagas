package br.com.ogabrielfelipe.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthCandidateRequestDTO(@Schema(example = "fulano.sicrano") String username,
                                      @Schema(example = "fulano*sicrano@123") String password) {

}
