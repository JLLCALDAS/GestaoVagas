package br.com.jllcaldas.gestao_vagas.modules.candidates.userCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jllcaldas.gestao_vagas.exceptions.UserNotFoundException;
import br.com.jllcaldas.gestao_vagas.modules.candidates.CandidateRepository;
import br.com.jllcaldas.gestao_vagas.modules.candidates.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.candidateRepository.findById(candidateId)
            .orElseThrow(() -> {
                throw new UserNotFoundException();
            });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
            .id(candidate.getId())
            .username(candidate.getUsername())
            .email(candidate.getEmail())
            .name(candidate.getName())
            .description(candidate.getDescription())
            .build();
        return candidateDTO;
    }
}
