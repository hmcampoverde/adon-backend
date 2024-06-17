package org.hmcampoverde.repository;

import java.util.Optional;

import org.hmcampoverde.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNameIgnoreCase(String name);
}
