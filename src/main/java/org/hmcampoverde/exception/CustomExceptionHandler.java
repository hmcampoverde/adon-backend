package org.hmcampoverde.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.message.Severity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		return ResponseEntity.internalServerError().body(getMessageDto(exception.getMessage()));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Message> handleCustomException(CustomException exception) {
		return ResponseEntity.status(exception.getStatus()).body(getMessageDto(exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<Message>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<Message> errors = new ArrayList<>();

		e.getBindingResult().getAllErrors().forEach(err -> errors.add(getMessageDto(err.getDefaultMessage())));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	private Message getMessageDto(String detail) {
		return Message
				.builder()
				.severity(Severity.ERROR.getDescription())
				.summary(bundle.getValue("title.fatal"))
				.detail(Objects.nonNull(detail) ? detail : bundle.getValue("exception.unknown"))
				.build();
	}
}
