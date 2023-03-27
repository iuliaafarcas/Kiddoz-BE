package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentService {
    private ApplicationUserRepository applicationUserRepository;


    public ParentService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public Parent addParent(String firstName, String lastName, String email) {
        Parent newParent = new Parent(null, firstName, lastName, email);
        return applicationUserRepository.save(newParent);
    }

    public void deleteParent(Integer id) {
        //TODO
    }

    public void updateParent(Integer id, String firstName, String lastName, String email) {
        //TODO
    }

    public List<Parent> getParents() {
        return this.applicationUserRepository.findAll().stream()
                .filter(user -> user instanceof Parent)
                .map(user -> (Parent) user)
                .collect(Collectors.toList());
    }

}
