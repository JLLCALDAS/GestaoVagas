package br.com.jllcaldas.gestao_vagas.modules.candidates.userCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jllcaldas.gestao_vagas.exceptions.CandidateNotFoundException;
import br.com.jllcaldas.gestao_vagas.exceptions.JobNotFoundException;
import br.com.jllcaldas.gestao_vagas.modules.candidates.CandidateRepository;
import br.com.jllcaldas.gestao_vagas.modules.candidates.entity.ApplyJobEntity;
import br.com.jllcaldas.gestao_vagas.modules.candidates.repository.ApplyJobRepository;
import br.com.jllcaldas.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId){
        
        this.candidateRepository.findById(candidateId)
            .orElseThrow(() -> {
                throw new CandidateNotFoundException();
            });
        
        this.jobRepository.findById(jobId)
            .orElseThrow(() -> {
                throw new JobNotFoundException();
            });

        var applyJob = ApplyJobEntity.builder()
            .candidateId(candidateId)
            .jobId(jobId)
            .build();

        applyJob = this.applyJobRepository.save(applyJob);
        return applyJob;
    }

}
