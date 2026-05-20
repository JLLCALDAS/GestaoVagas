package br.com.jllcaldas.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException() {
        super("Vaga Inexistente.");
    }
}
