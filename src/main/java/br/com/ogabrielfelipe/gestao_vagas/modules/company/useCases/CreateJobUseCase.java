package br.com.ogabrielfelipe.gestao_vagas.modules.company.useCases;

import br.com.ogabrielfelipe.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ogabrielfelipe.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ogabrielfelipe.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
    
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity){
        companyRepository.findById(jobEntity.getCompanyId())
                .orElseThrow(CompanyNotFoundException::new);

        return this.jobRepository.save(jobEntity);
    }
}
