package com.kiddoz.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistAddDto {
    private String name;
    private String email;
    private String description;
    private String occupation;
    private String quote;
    private Integer domainId;
    private String image;
    private String domainsOfActivity;
    private Date birthdate;
}
