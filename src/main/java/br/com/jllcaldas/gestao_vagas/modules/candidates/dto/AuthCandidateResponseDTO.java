package br.com.jllcaldas.gestao_vagas.modules.candidates.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCandidateResponseDTO {
    private String access_token;
    private Long expires_in;
}
