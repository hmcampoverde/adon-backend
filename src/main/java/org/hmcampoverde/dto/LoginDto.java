package org.hmcampoverde.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	@Size(max = 75, message = "input.exceeds.chars")
	@NotEmpty(message = "{user.username.required}")
	private String username;

	@Size(max = 75, message = "input.exceeds.chars")
	@NotEmpty(message = "{user.password.required}")
	private String password;
}
