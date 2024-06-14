package org.hmcampoverde.message;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BundleMessageHandler {

	private final MessageSource messageSource;

	public String getValue(String key) {
		return getValue(key, null);
	}

	public String getValue(String key, Object[] arguments) {
		return messageSource.getMessage(key, arguments, "key does not exist on resource", new Locale("es"));
	}
}
