package com.mycompany.formationspring.demo.repositoryCustom;


import com.mycompany.formationspring.demo.entity.QUser;
import com.mycompany.formationspring.demo.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<User> findByUsernameAndRoles(String username, String role) {
        QUser user = QUser.user;
        return queryFactory.selectFrom(user)
                .where(user.username.eq(username)
                        .and(user.roles.any().eq(role)))
                .fetch();
    }

    @Override
    public List<User> findByUsername(String username) {
        QUser user = QUser.user;
        return queryFactory.selectFrom(user)
                .where(user.username.eq(username))
                .fetch();
    }

    @Override
    public List<User> findByRole(String role) {
        QUser user = QUser.user;
        return queryFactory.selectFrom(user)
                .where(user.roles.any().eq(role))
                .fetch();
    }
}