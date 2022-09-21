package com.charles.zodrac.repository;

import com.charles.zodrac.enums.BannedEnum;
import com.charles.zodrac.model.entity.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

    @Query("select count(c) from CharacterEntity c where c.user.id = ?1")
    Long countByUserId(Long userId);

    @Query("select (count(c) > 0) from CharacterEntity c where upper(c.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("select c from CharacterEntity c where c.user.id = ?1 order by c.id")
    List<CharacterEntity> findAllByUserIdOrderById(Long userId);

    @Query("select c from CharacterEntity c where c.id = ?1 and c.user.id = ?2 and c.banned = ?3")
    Optional<CharacterEntity> findByIdAndUserIdAndBanned(Long id, Long userId, BannedEnum banned);

    @Query("select c from CharacterEntity c where c.name like concat('%', :searchTerm, '%')")
    Page<CharacterEntity> findByNameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
}
