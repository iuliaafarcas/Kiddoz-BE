package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.SpecialistAddDto;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.service.SpecialistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("specialists")
public class SpecialistController {
    private final SpecialistService specialistService;


    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;

    }

    @PostMapping()
    public Specialist addSpecialist(@RequestBody SpecialistAddDto specialistAddDto) {
        return specialistService.addSpecialist(specialistAddDto.getName(), specialistAddDto.getPassword(),
                specialistAddDto.getEmail(), specialistAddDto.getDescription(), specialistAddDto.getOccupation(),
                specialistAddDto.getQuote(), specialistAddDto.getDomainId(), specialistAddDto.getImage(),
                specialistAddDto.getDomainsOfInterests(), specialistAddDto.getBirthdate());
    }

    @GetMapping
    public List<Specialist> getSpecialists() {
        return specialistService.getSpecialists();
    }

    @GetMapping("/{id}")
    public Specialist getSpecialistById(@PathVariable(value = "id") Integer id) {
        return specialistService.getSpecialistById(id);
    }

    @GetMapping("/paged")
    public List<Object> getSpecialistsPaged(@RequestParam(required = false, defaultValue = "10") Integer itemCount, @RequestParam Integer pageNumber) {
        return specialistService.getSpecialistsPaged(itemCount, pageNumber);
    }

    @GetMapping("/filter")
    public List<Object> filter(
            @RequestParam(required = false, defaultValue = "10") Integer itemCount,
            @RequestParam Integer pageNumber,
            @RequestParam(required = false) Integer fromAge,
            @RequestParam(required = false) Integer toAge,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String domainName,
            @RequestParam(required = false) Integer starCount


    ) {

        return this.specialistService.filter(itemCount, pageNumber, fromAge, toAge, name, domainName, starCount);
    }

}
