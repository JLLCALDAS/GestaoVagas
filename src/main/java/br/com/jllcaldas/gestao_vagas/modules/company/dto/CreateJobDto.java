package br.com.jllcaldas.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDto {
    @Schema(example = "Vaga para desenvolvedor Delphi Junior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "Vale Alimentação, Vale Refeição, Vale Transporte", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
