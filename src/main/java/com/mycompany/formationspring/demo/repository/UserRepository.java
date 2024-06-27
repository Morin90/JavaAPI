package com.mycompany.formationspring.demo.repository;

import com.mycompany.formationspring.demo.repositoryCustom.UserRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.mycompany.formationspring.demo.entity.User;

import java.util.List;

@Repository

    public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, UserRepositoryCustom {
        List<User> findByUsernameContaining(String username, Sort sort);
        List<User> findByRolesContaining(String role, Sort sort);
        List<User> findByUsernameAndRolesContaining(String username, String role, Sort sort);
    }

