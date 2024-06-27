package com.mycompany.formationspring.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Data
public class UserCreationDto {
    private String username;
    private String password;
    private String confirmPassword;
    private Set<String> roles;
    private Boolean enabled;
}
