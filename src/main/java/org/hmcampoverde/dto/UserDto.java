package org.hmcampoverde.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

	private String fullname;
	private String username;
	@Size(max = 75, message = "input.exceeds.chars")
	@NotEmpty(message = "{user.password.required}")
	private String password;

}
