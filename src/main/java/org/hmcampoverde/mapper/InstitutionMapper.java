package org.hmcampoverde.mapper;

import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.entity.Institution;
import org.springframework.stereotype.Component;

@Component
public class InstitutionMapper {

    public InstitutionDto map(Institution institution) {
        return InstitutionDto
                .builder()
                .id(institution.getId())
                .name(institution.getName())
                .amie(institution.getAmie())
                .phone(institution.getPhone())
                .email(institution.getEmail())
                .address(institution.getAddress())
                .build();
    }
}
