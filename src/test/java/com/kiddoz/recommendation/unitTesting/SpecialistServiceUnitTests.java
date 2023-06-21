package com.kiddoz.recommendation.unitTesting;

import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.DomainCategory;
import com.kiddoz.recommendation.model.DomainInterest;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.DomainCategoryRepository;
import com.kiddoz.recommendation.repository.DomainInterestRepository;
import com.kiddoz.recommendation.repository.RatingSpecialistRepository;
import com.kiddoz.recommendation.service.SpecialistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialistServiceUnitTests {

    @Mock
    private RatingSpecialistRepository ratingSpecialistRepository;
    @InjectMocks
    private SpecialistService service;

    @Mock
    private DomainCategoryRepository domainCategoryRepository;

    @Mock
    private DomainInterestRepository domainInterestRepository;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Test
    void should_add_specialist() {
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password";
        String description = "Specialist in a specific field";
        String occupation = "Specialist";
        String quote = "Stay curious!";
        Integer domainId = 1;
        String image = "specialist.jpg";
        List<Integer> domainsOfInterests = Arrays.asList(1, 2, 3);
        Date birthday = new Date();

        DomainCategory domainCategory = new DomainCategory();
        domainCategory.setId(domainId);

        DomainInterest domainInterest1 = new DomainInterest();
        domainInterest1.setId(1);
        DomainInterest domainInterest2 = new DomainInterest();
        domainInterest2.setId(2);
        DomainInterest domainInterest3 = new DomainInterest();
        domainInterest3.setId(3);

        List<DomainInterest> listOfDomains = Arrays.asList(domainInterest1, domainInterest2, domainInterest3);
        Specialist newSpecialist = new Specialist();
        newSpecialist.setName(name);
        newSpecialist.setEmail(email);
        newSpecialist.setPassword(password);
        newSpecialist.setDescription(description);
        newSpecialist.setOccupation(occupation);
        newSpecialist.setQuote(quote);
        newSpecialist.setDomain(domainCategory);
        newSpecialist.setImage(image);
        newSpecialist.setDomainsInterest(listOfDomains);
        newSpecialist.setBirthday(birthday);

        when(domainCategoryRepository.findById(domainId)).thenReturn(Optional.of(domainCategory));
        when(domainInterestRepository.findById(any(Integer.class))).thenReturn(Optional.of(new DomainInterest()));
        when(applicationUserRepository.save(any(Specialist.class))).thenReturn(newSpecialist);

        Specialist addedSpecialist = service.addSpecialist(name, email, password, description, occupation,
                quote, domainId, image, domainsOfInterests, birthday);

        // Assert
        assertNotNull(addedSpecialist);
        assertEquals(name, addedSpecialist.getName());
        assertEquals(email, addedSpecialist.getEmail());
        assertEquals(password, addedSpecialist.getPassword());
        assertEquals(description, addedSpecialist.getDescription());
        assertEquals(occupation, addedSpecialist.getOccupation());
        assertEquals(quote, addedSpecialist.getQuote());
        assertEquals(domainCategory, addedSpecialist.getDomain());
        assertEquals(image, addedSpecialist.getImage());
        assertEquals(listOfDomains, addedSpecialist.getDomainsInterest());
        assertEquals(birthday, addedSpecialist.getBirthday());

        verify(domainCategoryRepository, times(1)).findById(domainId);
        verify(domainInterestRepository, times(3)).findById(any(Integer.class));
        verify(applicationUserRepository, times(1)).save(any(Specialist.class));
        verifyNoMoreInteractions(domainCategoryRepository, domainInterestRepository, applicationUserRepository);


    }

    @Test
    void testGetSpecialists_ReturnsListOfSpecialists() {
        // Arrange
        Specialist specialist1 = new Specialist();
        Specialist specialist2 = new Specialist();
        Specialist specialist3 = new Specialist();

        List<ApplicationUser> specialistList = Arrays.asList(specialist1, specialist2, specialist3);

        when(applicationUserRepository.findAll()).thenReturn((List<ApplicationUser>) specialistList);

        // Act
        List<Specialist> result = service.getSpecialists();

        // Assert
        assertEquals(specialistList.size(), result.size());
        assertEquals(specialistList, result);
    }

    @Test
    void testGetSpecialistById_ExistingSpecialistId_ReturnsSpecialist() {
        // Arrange
        Integer specialistId = 1;
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);

        when(applicationUserRepository.findById(specialistId)).thenReturn(Optional.of(specialist));

        // Act
        Specialist result = service.getSpecialistById(specialistId);

        // Assert
        assertEquals(specialist, result);
    }

    @Test
    void testGetSpecialistById_NonExistingSpecialistId_ThrowsException() {
        // Arrange
        Integer specialistId = 1;

        when(applicationUserRepository.findById(specialistId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> service.getSpecialistById(specialistId));
    }


}
