package br.com.jllcaldas.gestao_vagas.modules.candidates.userCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jllcaldas.gestao_vagas.exceptions.UserFoundException;
import br.com.jllcaldas.gestao_vagas.modules.candidates.CandidateEntity;
import br.com.jllcaldas.gestao_vagas.modules.candidates.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidate) {
        this.candidateRepository
        .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
        .ifPresent((user) -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);
        
        return this.candidateRepository.save(candidate);
    }
}
