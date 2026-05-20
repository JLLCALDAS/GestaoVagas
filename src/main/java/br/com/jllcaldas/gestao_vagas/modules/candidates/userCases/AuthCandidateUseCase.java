package br.com.jllcaldas.gestao_vagas.modules.candidates.userCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jllcaldas.gestao_vagas.modules.candidates.CandidateRepository;
import br.com.jllcaldas.gestao_vagas.modules.candidates.dto.AuthCandidateRequestDTO;
import br.com.jllcaldas.gestao_vagas.modules.candidates.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;    
    
    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = candidateRepository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("User Name/Password incorrect!");
            });
        
        var passwordMatches = passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        };

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofMinutes(2));

        var token = JWT.create()
            .withIssuer("gestao-vagas")
            .withSubject(candidate.getId().toString())
            .withClaim("roles", Arrays.asList("CANDIDATE"))
            .withExpiresAt(expiresIn)
            .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();
        
        return authCandidateResponse;

    };
}
