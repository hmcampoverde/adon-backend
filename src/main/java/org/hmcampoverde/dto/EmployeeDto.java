package org.hmcampoverde.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private Long id;

    @Size(max = 100, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.firstname.required}")
    private String firstname;

    @Size(max = 100, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.lastname.required}")
    private String lastname;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.identification.required}")
    private String identification;

    @Size(max = 18, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.mobil.required}")
    private String mobil;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.emailPersonal.required}")
    private String emailPersonal;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.emailInstitutional.required}")
    private String emailInstitutional;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.address.required}")
    private String address;

    private boolean manager;

}