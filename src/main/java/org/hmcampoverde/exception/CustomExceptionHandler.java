package org.hmcampoverde.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

	private final BundleMessageHandler bundle;

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Message> handleException(Exception exception) {
		return ResponseEntity
				.badRequest()
				.body(getMessageDto(exception.getMessage()));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Message> handleCustomException(CustomException exception) {
		return ResponseEntity
				.status(exception.getStatus())
				.body(getMessageDto(exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<Message>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		List<Message> errors = new ArrayList<>();

		exception
				.getBindingResult()
				.getAllErrors()
				.forEach(err -> errors.add(getMessageDto(err.getDefaultMessage())));

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(errors);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Message> handleBadCredentialsException(BadCredentialsException exception) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(getMessageDto(bundle.getValue("user.username.invalid")));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Message> handleAccessDeniedException(AccessDeniedException exception) {
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(getMessageDto(bundle.getValue("user.username.unauthorized")));
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Message> handleDisabledException(DisabledException exception) {
		return ResponseEntity
				.badRequest()
				.body(getMessageDto(bundle.getValue("user.username.disabled")));
	}

	private Message getMessageDto(String detail) {
		return Message
				.builder()
				.severity("error")
				.summary(bundle.getValue("title.fatal"))
				.detail(Objects.nonNull(detail) ? detail : bundle.getValue("exception.unknown"))
				.build();
	}
}
