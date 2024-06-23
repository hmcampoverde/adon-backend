package org.hmcampoverde.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String fullname;
	private String username;
	private String password;
	@Size(max = 75, message = "input.exceeds.chars")
	@NotEmpty(message = "{user.password.required}")
	private String newPassword;

}
