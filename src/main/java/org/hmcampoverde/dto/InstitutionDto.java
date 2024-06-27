package org.hmcampoverde.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDto {

    private Long id;

    @Size(max = 8, message = "{input.exceeds.chars}")
    @NotBlank(message = "{institution.amie.required}")
    private String amie;

    @Size(max = 100, message = "{input.exceeds.chars}")
    @NotBlank(message = "{institution.name.required}")
    private String name;

    @Size(max = 17, message = "{input.exceeds.chars}")
    @NotBlank(message = "{institution.phone.required}")
    private String phone;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.emailPersonal.required}")
    private String email;

    @Size(max = 100, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.address.required}")
    private String address;

}