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
        //TODO: delete all reg parent first
//        applicationUserRepository.deleteById(id);
    }

    public Parent updateParent(Integer id, String firstName, String lastName, String email) {
        var newParent = applicationUserRepository.findById(id).stream()
                .filter(parent -> parent instanceof Parent)
                .map(parent -> (Parent) parent).toList();
        if (newParent.isEmpty()) return null;
        else {
            newParent.get(0).setFirstName(firstName);
            newParent.get(0).setLastName(lastName);
            newParent.get(0).setEmail(email);
            return applicationUserRepository.save(newParent.get(0));

        }

    }

    public List<Parent> getParents() {
        return this.applicationUserRepository.findAll().stream()
                .filter(user -> user instanceof Parent)
                .map(user -> (Parent) user)
                .collect(Collectors.toList());
    }

}
