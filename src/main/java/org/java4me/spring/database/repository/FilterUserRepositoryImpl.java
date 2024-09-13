package org.java4me.spring.database.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.java4me.spring.database.entity.QUser;
import org.java4me.spring.database.entity.User;
import org.java4me.spring.database.querydsl.QPredicates;
import org.java4me.spring.dto.UserFilter;

import java.util.ArrayList;
import java.util.List;

import static org.java4me.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    private final EntityManager entityManager;
    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
