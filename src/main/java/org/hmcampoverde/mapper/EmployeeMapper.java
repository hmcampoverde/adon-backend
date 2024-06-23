package org.hmcampoverde.mapper;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto map(Employee employee) {
        return EmployeeDto
                .builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .identification(employee.getIdentification())
                .mobil(employee.getMobil())
                .emailPersonal(employee.getEmailPersonal())
                .emailInstitutional(employee.getEmailInstitutional())
                .address(employee.getAddress())
                .manager(employee.isManager())
                .build();
    }

}
