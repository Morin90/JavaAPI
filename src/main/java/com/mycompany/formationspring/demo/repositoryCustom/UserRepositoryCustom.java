package com.mycompany.formationspring.demo.repositoryCustom;

import com.mycompany.formationspring.demo.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByUsernameAndRoles(String username, String role);
    List<User> findByUsername(String username);
    List<User> findByRole(String role);
}
