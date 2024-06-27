package com.mycompany.formationspring.demo.specifications;

import com.mycompany.formationspring.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> hasUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("username"), username);
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, cb) -> cb.isMember(role, root.get("roles"));
    }
}