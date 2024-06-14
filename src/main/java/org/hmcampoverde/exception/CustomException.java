package org.hmcampoverde.exception;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatusCode status;

	public CustomException(String message, @NonNull HttpStatusCode status) {
		super(message);
		this.status = status;
	}
}
