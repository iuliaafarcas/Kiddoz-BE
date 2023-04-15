package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.DomainCategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Specialist addSpecialist(String firstName, String lastName, String email, String description, String occupation, String quote, Integer age, Integer domainId, String image, String domainsOfActivity) {
        var domainCategory = domainCategoryRepository.findById(domainId).orElseThrow(() -> new RuntimeException("Domain not found"));
        Specialist newSpecialist = new Specialist(null, firstName, lastName, email, description, occupation, quote, age,
                domainCategory, image, domainsOfActivity);

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

    public List<Object> getSpecialistsPaged(Integer itemCount, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1, itemCount);
        List<Object> result = new ArrayList();
        List<ApplicationUser> list_ = applicationUserRepository.findAll(page).stream().filter(element -> element instanceof Specialist).collect(Collectors.toList());
        result.add(applicationUserRepository.findAll().size());
        result.add(list_);
        return result;
    }
}
