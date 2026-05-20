package br.com.jllcaldas.gestao_vagas.exceptions;

public class CandidateNotFoundException extends RuntimeException{
    public CandidateNotFoundException() {
        super("Candidato Inexistente.");
    }
}
