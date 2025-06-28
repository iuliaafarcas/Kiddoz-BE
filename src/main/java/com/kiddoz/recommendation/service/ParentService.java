package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentService {
    private ApplicationUserRepository applicationUserRepository;


    public ParentService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public Parent addParent(String name, String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var parent = applicationUserRepository.findApplicationUserByEmail(email);
        if (parent != null) throw new RuntimeException("User already has an account!");
        Parent newParent = new Parent(null, name, email, bCryptPasswordEncoder.encode(password), null);
        return applicationUserRepository.save(newParent);
    }

    public void deleteParent(Integer id) {
        //TODO: delete all reg parent first
//        applicationUserRepository.deleteById(id);
    }

    public Parent updateParent(Integer id, String name, String email) {
        var newParent = applicationUserRepository.findById(id).stream()
                .filter(parent -> parent instanceof Parent)
                .map(parent -> (Parent) parent).toList();
        if (newParent.isEmpty()) return null;
        else {
            newParent.get(0).setName(name);

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
