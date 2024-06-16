package org.hmcampoverde.controller;

import java.text.ParseException;

import org.hmcampoverde.dto.EmployeeRegisterDto;
import org.hmcampoverde.dto.LoginDto;
import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.dto.UserDto;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.service.EmployeeService;
import org.hmcampoverde.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final EmployeeService employeeService;

	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<UserDto> findByUsername(@PathVariable("username") String username) {
		return ResponseEntity.ok(userService.findByUsername(username));
	}

	@PutMapping("/updatePasswordByUsername/{username}")
	public ResponseEntity<Message> updatePasswordByUsername(@PathVariable("username") String username,
			@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok().body(userService.updatePasswordByUsername(username, userDto));
	}

	@PostMapping("/register")
	public ResponseEntity<Message> register(@Valid @RequestBody EmployeeRegisterDto employeeDto) {
		return ResponseEntity.ok().body(employeeService.register(employeeDto));
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(userService.login(loginDto));
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto toketDto) throws ParseException {
		return ResponseEntity.ok(userService.refresh(toketDto));
	}

}
