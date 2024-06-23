package org.hmcampoverde.repository;

import java.util.Optional;

import org.hmcampoverde.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select employee from Employee employee left join fetch employee.institution where employee.identification = :identification and employee.deleted = false")
    Optional<Employee> findByIdentification(@Param("identification") String identification);

}
