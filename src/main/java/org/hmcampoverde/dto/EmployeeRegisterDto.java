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
public class EmployeeRegisterDto {

    private Long id;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.firstname.required}")
    private String firstname;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.lastname.required}")
    private String lastname;

    @Size(max = 11, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.identification.required}")
    private String identification;

    @Size(max = 75, message = "{input.exceeds.chars}")
    @NotBlank(message = "{employee.emailInstitutional.required}")
    private String emailInstitutional;

    @Size(max = 75, message = "input.exceeds.chars")
    @NotBlank(message = "{user.password.required}")
    private String password;

}