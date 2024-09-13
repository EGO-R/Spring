package org.java4me.spring.database.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.database.entity.User;
import org.java4me.spring.dto.PersonalInfo;
import org.java4me.spring.dto.PersonalInfoIT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        QuerydslPredicateExecutor<User> {
    @Query("select u from User u " +
            "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(String firstname, String lastname);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    Optional<User> findFirstByOrderByIdDesc();

//    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    //    Collection, Stream
//    Streamable, Slice, Page
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
            countQuery = "select count (distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);

//    List<PersonalInfo> findAllByCompanyId(Integer companyId);
//    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);

    @Query(nativeQuery = true,
    value = "SELECT firstname," +
            "lastname," +
            "birth_date " +
            "FROM users " +
            "WHERE company_id = :companyId")
    List<PersonalInfoIT> findAllByCompanyId(Integer companyId);

    Optional<User> findByUsername(String username);
}
