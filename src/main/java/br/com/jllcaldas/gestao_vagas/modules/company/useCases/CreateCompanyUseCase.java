package br.com.jllcaldas.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jllcaldas.gestao_vagas.exceptions.UserFoundException;
import br.com.jllcaldas.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.jllcaldas.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity company) {

        this.companyRepository
        .findByUsernameOrEmail(company.getUsername(), company.getEmail())
        .ifPresent(user -> {
            throw new UserFoundException();
        });

        String encriptPassword = passwordEncoder.encode(company.getPassword());
        company.setPassword(encriptPassword);
        return this.companyRepository.save(company);
    }

}
