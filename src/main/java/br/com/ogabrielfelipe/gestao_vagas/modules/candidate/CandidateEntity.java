package br.com.ogabrielfelipe.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Fulano Sicrano", requiredMode = Schema.RequiredMode.REQUIRED, description = "nome do Candidato")
    private String name;
    
    @Schema(example = "fulano.sicrano", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username do Candidato")
    @Pattern(regexp = "\\S+", message = "The [username] field must not contain spaces")
    private String username;

    @Schema(example = "fulano.sicrano@mail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email do Candidato")
    @Email(message = "The [email] field is not valid")
    private String email;

    @Schema(example = "fulano.sicrano@95214", minLength = 8, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do Candidato")
    @Length(min= 8, max=100)
    private String password;

    @Schema(example = "Desenvolvedor Java Jr", requiredMode = Schema.RequiredMode.REQUIRED, description = "Breve descrição do Candidato")
    private String description;

    @Schema(example = "cloud-anywhere.com/amz/curriculum.pdf", requiredMode = Schema.RequiredMode.REQUIRED, description = "Link do currículo do Candidato")
    private String curriculum;

    @CreationTimestamp
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Data gerada automaticamente no momento de criação do candidato")
    private LocalDateTime createdAt;
}
