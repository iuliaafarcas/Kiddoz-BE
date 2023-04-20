package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.DomainCategoryRepository;
import com.kiddoz.recommendation.repository.RatingSpecialistRepository;
import com.kiddoz.recommendation.utils.SpecialistSpecifications;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistService {
//    @Value("${images.path}")
//    private String ImagePath;

    private ApplicationUserRepository applicationUserRepository;
    private DomainCategoryRepository domainCategoryRepository;

    private RatingSpecialistRepository ratingSpecialistRepository;

    public SpecialistService(ApplicationUserRepository applicationUserRepository,
                             DomainCategoryRepository domainCategoryRepository,
                             RatingSpecialistRepository ratingSpecialistRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.domainCategoryRepository = domainCategoryRepository;
        this.ratingSpecialistRepository = ratingSpecialistRepository;
    }


    public Specialist addSpecialist(String name, String email, String description,
                                    String occupation, String quote, Integer domainId,
                                    String image, String domainsOfActivity, Date birthday) {
        var domainCategory = domainCategoryRepository.findById(domainId)
                .orElseThrow(() -> new RuntimeException("Domain not found"));
        Specialist newSpecialist = new Specialist(null, name, email, description, occupation, quote,
                domainCategory, image, domainsOfActivity, birthday);

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

    public List<Object> filter(Integer itemCount, Integer pageNumber, Integer fromAge, Integer toAge,
                               String name, String domainName,
                               Integer starNumber) {
        List<Specification<Specialist>> predicates = new ArrayList<>();
        if (fromAge != null || toAge != null) predicates.add(SpecialistSpecifications.ageBetween(fromAge, toAge));
        if (name != null) predicates.add(SpecialistSpecifications.nameIn(name));
        if (domainName != null) predicates.add(SpecialistSpecifications.categoryEquals(domainName));
        Specification<Specialist> specSpecialist = null;
        for (var next : predicates) {
            if (specSpecialist == null) {
                specSpecialist = next;
            } else {
                specSpecialist = specSpecialist.and(next);
            }
        }
        List<Specialist> resultList;
        if (specSpecialist == null)
            resultList = this.applicationUserRepository.findAll()
                    .stream()
                    .filter(element -> element instanceof Specialist)
                    .map(element -> (Specialist) element)
                    .collect(Collectors.toList());
        else resultList = this.applicationUserRepository.findAll(specSpecialist);
        List<Object> finalList = new ArrayList<>();

        if (starNumber == null) {
            int fromIndex = Math.min((pageNumber - 1) * itemCount, resultList.size());
            int toIndex = Math.min(pageNumber * itemCount, resultList.size());
            finalList.add(resultList.subList(fromIndex, toIndex).size());
            finalList.add(resultList.subList(fromIndex, toIndex));
        } else {
            List<Specialist> list_ = resultList.stream().filter(element -> Math.round(ratingSpecialistRepository
                    .getRatingForSpecialist(element.getId())) >= starNumber).collect(Collectors.toList());
            int fromIndex = Math.min((pageNumber - 1) * itemCount, list_.size());
            int toIndex = Math.min(pageNumber * itemCount, list_.size());
            finalList.add(list_.subList(fromIndex, toIndex).size());
            finalList.add(list_.subList(fromIndex, toIndex));
        }
        return finalList;
    }
}

