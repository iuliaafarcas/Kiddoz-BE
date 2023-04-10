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
        return specialistService.addSpecialist(specialistAddDto.getFirstName(), specialistAddDto.getLastName(),
                specialistAddDto.getEmail(), specialistAddDto.getDescription(), specialistAddDto.getOccupation(), specialistAddDto.getQuote(), specialistAddDto.getAge(),
                specialistAddDto.getDomainId(), specialistAddDto.getImage(), specialistAddDto.getDomainsOfActivity());
    }

    @GetMapping
    public List<Specialist> getSpecialists() {
        return specialistService.getSpecialists();
    }

    @GetMapping("/{id}")
    public Specialist getSpecialistById(@PathVariable(value = "id") Integer id) {
        return specialistService.getSpecialistById(id);
    }


}
