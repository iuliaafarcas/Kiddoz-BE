package com.kiddoz.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistAddDto {
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;
    private String quote;
    private Integer age;
    private Integer domainId;
    private String image;
}
