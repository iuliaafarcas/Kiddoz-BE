package com.kiddoz.recommendation.integrationTesting;

import com.kiddoz.recommendation.model.DomainCategory;
import com.kiddoz.recommendation.model.DomainInterest;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.DomainCategoryRepository;
import com.kiddoz.recommendation.repository.DomainInterestRepository;
import com.kiddoz.recommendation.service.SpecialistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SpecialistServiceIntegrationTest {

    @InjectMocks
    private SpecialistService specialistService;

    @Mock
    private DomainCategoryRepository domainCategoryRepository;

    @Mock
    private DomainInterestRepository domainInterestRepository;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Test
    void testAddSpecialist() {
        // Arrange
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password";
        String description = "Specialist in a specific field";
        String occupation = "Specialist";
        String quote = "Stay curious!";
        Integer domainId = 1;
        String image = "specialist.jpg";
        List<Integer> domainsOfInterests = List.of(1, 2, 3);
        Date birthday = new Date();

        DomainCategory domainCategory = new DomainCategory();
        domainCategory.setId(domainId);

        DomainInterest domainInterest1 = new DomainInterest();
        domainInterest1.setId(1);

        DomainInterest domainInterest2 = new DomainInterest();
        domainInterest2.setId(2);

        DomainInterest domainInterest3 = new DomainInterest();
        domainInterest3.setId(3);

        Specialist expectedSpecialist = new Specialist();
        expectedSpecialist.setId(1); // Set the ID to a non-null value

        // Act
        Specialist addedSpecialist = specialistService.addSpecialist(name, email, password, description, occupation,
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
        assertEquals(domainsOfInterests.size(), addedSpecialist.getDomainsInterest().size());
        assertEquals(birthday, addedSpecialist.getBirthday());
    }

//    @Test
//    void testGetSpecialists() {
//        // Arrange
//        // Create and save specialist objects using the applicationUserRepository
//        Specialist specialist1 = new Specialist();
//        Specialist specialist2 = new Specialist();
//        when(applicationUserRepository.findAll()).thenReturn(Arrays.asList(specialist1, specialist2));
//
//        // Act
//        List<Specialist> specialists = specialistService.getSpecialists();
//
//        // Assert
//
//        assertTrue(specialists.contains(specialist1));
//        assertTrue(specialists.contains(specialist2));
//    }
}
