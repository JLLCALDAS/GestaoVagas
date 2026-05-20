package br.com.jllcaldas.gestao_vagas.modules.candidates;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Data
@Entity(name = "candidate")
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(description = "Nome do candidato", example = "João da Silva", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O campo [username] não pode  conter espaços.")
    @Schema(description = "Username do candidato", example = "joao_silva", requiredMode = RequiredMode.REQUIRED)
    private String username;
    
    @Email (message = "O campo [email] deve ser um email válido")
    @Schema(description = "Email do candidato", example = "joao.silva@example.com", requiredMode = RequiredMode.REQUIRED)
    private String email;   
    
    @Length(min=6, max=50, message = "O campo [password] deve ter entre 6 e 50 caracteres")
    @Schema(description = "Senha do candidato", example = "senha123", requiredMode = RequiredMode.REQUIRED)
    private String password;
    
    @Schema(description = "Descrição do candidato", example = "Desenvolvedor Java", requiredMode = RequiredMode.NOT_REQUIRED)
    private String description;
    
    private String curriculum;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
