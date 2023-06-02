package com.kiddoz.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles implements GrantedAuthority {
    public static final String USER_PARENT = "USER_PARENT";
    public static final String USER_SPECIALIST = "USER_SPECIALIST";

    private String authority;
}
