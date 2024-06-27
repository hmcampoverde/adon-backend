package org.hmcampoverde.repository;

import java.util.List;
import java.util.Optional;

import org.hmcampoverde.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("select institution from Institution institution where institution.deleted = false")
    @NonNull
    List<Institution> findAll();

    @Query("select institution from Institution institution where institution.amie = :amie and institution.deleted = false")
    Optional<Institution> findByAmie(@Param("amie") String amie);

}
