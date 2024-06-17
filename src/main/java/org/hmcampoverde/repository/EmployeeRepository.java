package org.hmcampoverde.repository;

import java.util.Optional;

import org.hmcampoverde.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByIdentificationAndDeletedFalse(String identification);

}
