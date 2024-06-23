package org.hmcampoverde.mapper;

import org.hmcampoverde.dto.ProfileDto;
import org.hmcampoverde.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileDto map(Employee employee) {
        return ProfileDto
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
