package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.DomainCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistService {
//    @Value("${images.path}")
//    private String ImagePath;

    private ApplicationUserRepository applicationUserRepository;
    private DomainCategoryRepository domainCategoryRepository;

    public SpecialistService(ApplicationUserRepository applicationUserRepository, DomainCategoryRepository domainCategoryRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.domainCategoryRepository = domainCategoryRepository;
    }

    public Specialist addSpecialist(String firstName, String lastName, String email, String occupation, String quote, Integer age, Integer domainId, String image) {
        var domainCategory = domainCategoryRepository.findById(domainId).orElseThrow(() -> new RuntimeException("Domain not found"));
        Specialist newSpecialist = new Specialist(null, firstName, lastName, email, occupation, quote, age,
                domainCategory, image);

        return applicationUserRepository.save(newSpecialist);
    }

    public List<Specialist> getSpecialists() {
        return this.applicationUserRepository.findAll().stream()
                .filter(user -> user instanceof Specialist)
                .map(user -> (Specialist) user)
                .collect(Collectors.toList());
    }

    public Specialist getSpecialistById(Integer id) {
        var specialist = applicationUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Specialist not found -> getSpecialistById"));
        if (specialist instanceof Specialist) return (Specialist) specialist;
        else throw new RuntimeException("Specialist not found");
    }
}
