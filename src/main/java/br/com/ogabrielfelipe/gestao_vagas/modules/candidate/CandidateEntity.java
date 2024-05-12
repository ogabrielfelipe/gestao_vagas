package br.com.ogabrielfelipe.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

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
    
    private String name;
    
    
    @Pattern(regexp = "\\S+", message = "The [username] field must not contain spaces")
    private String username;
    
    @Email(message = "The [email] field is not valid")
    private String email;

    @Length(min= 8, max=100)
    private String password;

    private String description;
    
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
