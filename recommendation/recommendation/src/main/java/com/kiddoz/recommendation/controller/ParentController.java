package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.ParentAddDto;
import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.service.ParentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parents")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping()
    public Parent addParent(@RequestBody ParentAddDto parentAddDto) {
        return parentService.addParent(parentAddDto.getName(),
                parentAddDto.getEmail());
    }

    @PutMapping()
    public Parent updateParent(@RequestBody Parent parent) {
        return parentService.updateParent(parent.getId(), parent.getName(),
                parent.getEmail());
    }

    @GetMapping()
    public List<Parent> getParents() {
        return parentService.getParents();
    }
}
