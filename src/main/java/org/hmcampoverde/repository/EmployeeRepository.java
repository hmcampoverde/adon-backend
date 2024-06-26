package org.hmcampoverde.repository;

import java.util.List;
import java.util.Optional;

import org.hmcampoverde.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select employee from Employee employee left join fetch employee.institution where employee.identification = :identification and employee.deleted = false")
    Optional<Employee> findByIdentification(@Param("identification") String identification);

    @Query("select employee from Employee employee left join fetch employee.institution where employee.deleted = false")
    public @NonNull List<Employee> findAll();

}
