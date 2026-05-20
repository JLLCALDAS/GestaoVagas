package br.com.jllcaldas.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TestUtils {

    public static String generateToken(UUID companyId, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var token = JWT.create()
            .withIssuer("gestao-vagas")
            .withSubject(companyId.toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .sign(algorithm);

        return "Bearer " + token;
    }
}
