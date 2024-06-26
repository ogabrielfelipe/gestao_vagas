package br.com.ogabrielfelipe.gestao_vagas.modules.company.controllers;

import br.com.ogabrielfelipe.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.ogabrielfelipe.gestao_vagas.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static br.com.ogabrielfelipe.gestao_vagas.utils.TestUtils.objectToJSON;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("company.test@mail.com")
                .password("1231231230")
                .username("COMPANY_USERNAME")
                .name("COMPANY_TEST")
                .build();
        company = companyRepository.saveAndFlush(company);

        var createdJob = CreateJobDTO.builder()
                            .benefits("BENEFITS_TEST")
                            .description("DESCRIPTION_TEST")
                            .level("LEVEL_TEST")
                            .build();

        var result = mvc.perform( MockMvcRequestBuilders.post("/company/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(createdJob))
                .header("Authorization", TestUtils.generateTokenCompany(company.getId(), "JAVAGAS_Company@123541#"))
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
        var createdJob = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        mvc.perform( MockMvcRequestBuilders.post("/company/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(createdJob))
                .header("Authorization", TestUtils.generateTokenCompany(UUID.randomUUID(), "JAVAGAS_Company@123541#"))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }



}
