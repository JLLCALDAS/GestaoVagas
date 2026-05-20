package br.com.jllcaldas.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jllcaldas.gestao_vagas.modules.company.dto.CreateJobDto;
import br.com.jllcaldas.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.jllcaldas.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.jllcaldas.gestao_vagas.utils.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {

        var company = new CompanyEntity();
        company.setUsername("empresa_teste");
        company.setEmail("empresa@teste.com");
        company.setPassword("123456");
        company.setName("Empresa Teste");
        company.setDescription("Empresa de tecnologia");
        company = companyRepository.saveAndFlush(company);

        var createJobDto = CreateJobDto.builder()
            .description("Vaga para desenvolvedor Delphi Junior")
            .benefits("Vale Alimentação, Vale Refeição, Vale Transporte")
            .level("Júnior")
            .build();

        mockMvc.perform(post("/company/job/")
               .content(objectToJson(createJobDto))
               .contentType(MediaType.APPLICATION_JSON)
               .header("Authorization", TestUtils.generateToken(company.getId(), "@GESTAO_VAGAS_SECRET@123#")))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should not be able to create a new job if the company does not exist")
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {

        var createJobDto = CreateJobDto.builder()
            .description("Vaga para desenvolvedor Delphi Junior")
            .benefits("Vale Alimentação, Vale Refeição, Vale Transporte")
            .level("Júnior")
            .build();

        mockMvc.perform(post("/company/job/")
               .content(objectToJson(createJobDto))
               .contentType(MediaType.APPLICATION_JSON)
               .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "@GESTAO_VAGAS_SECRET@123#")))
               .andExpect(status().isBadRequest());
    }

    private static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
