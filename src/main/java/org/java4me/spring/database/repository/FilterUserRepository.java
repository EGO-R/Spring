package org.java4me.spring.database.repository;

import org.java4me.spring.database.entity.User;
import org.java4me.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter filter);
}
