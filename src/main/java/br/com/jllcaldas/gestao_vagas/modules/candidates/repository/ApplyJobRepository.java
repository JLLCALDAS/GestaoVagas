package br.com.jllcaldas.gestao_vagas.modules.candidates.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jllcaldas.gestao_vagas.modules.candidates.entity.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

}
