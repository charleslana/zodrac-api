package com.charles.zodrac.repository;

import com.charles.zodrac.enums.StatusEnum;
import com.charles.zodrac.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select (count(u) > 0) from UserEntity u where u.email = ?1")
    Boolean existsByEmail(String email);

    @Query("select u from UserEntity u")
    @NonNull
    Page<UserEntity> findAll(@NonNull Pageable pageable);

    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u where u.email = ?1 and u.status <> ?2")
    Optional<UserEntity> findByEmailAndStatusNot(String email, StatusEnum status);
}
