package br.com.jllcaldas.gestao_vagas.modules.candidates.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jllcaldas.gestao_vagas.modules.candidates.CandidateEntity;
import br.com.jllcaldas.gestao_vagas.modules.candidates.dto.ProfileCandidateResponseDTO;
import br.com.jllcaldas.gestao_vagas.modules.candidates.userCases.ApplyJobCandidateUseCase;
import br.com.jllcaldas.gestao_vagas.modules.candidates.userCases.CreateCandidateUseCase;
import br.com.jllcaldas.gestao_vagas.modules.candidates.userCases.ListAllJobsByFilterUseCase;
import br.com.jllcaldas.gestao_vagas.modules.candidates.userCases.ProfileCandidateUseCase;
import br.com.jllcaldas.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Buscar informações do candidato.")
public class CandidateController {
    
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro do candidato", description = "Esta função é responsável por cadastrar um novo candidato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Usuário já existente!")        
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {

        try {
           var result = this.createCandidateUseCase.execute(candidate);
           return ResponseEntity.ok().body(result);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    };
    
    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Lista todas as informações do candidato")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Usuário não encontrado!")        
    })
    @SecurityRequirement(name = "jwt_auth")    
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");
        try {
           var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
           return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }        
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de Vagas Disponíveis por candidato", description = "Lista todas as vagas disponíveis para o candidato")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
    
    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Esta função é responsável por inscrever o candidato em uma vaga")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }    
}
