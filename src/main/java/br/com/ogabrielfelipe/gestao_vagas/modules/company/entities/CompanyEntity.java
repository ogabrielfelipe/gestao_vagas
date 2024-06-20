package br.com.ogabrielfelipe.gestao_vagas.modules.company.entities;

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

@Entity(name = "company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "company.entertainment", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da empresa")
    @Pattern(regexp = "\\S+", message = "The [username] field must not contain spaces")
    private String username;

    @Schema(example = "company.entertainment@mail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email da empresa")
    @Email(message = "The [email] field is not valid")
    private String email;

    @Schema(example = "company*entertainment@113123", minLength = 8, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha para login da empresa")
    @Length(min= 8, max=100, message = "password must not be longer than {100} characters and must be at least {8} characters")
    private String password;

    @Schema(example = "company.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Site da empresa")
    private String website;

    @Schema(example = "Company Entertainment", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da empresa")
    private String name;

    @Schema(example = "This company is beautiful", requiredMode = Schema.RequiredMode.REQUIRED, description = "Descrição da empresa")
    private String description;

    @CreationTimestamp
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Campo de preenchimento automatico no momento da criação da empresa")
    private LocalDateTime createdAt;
}
