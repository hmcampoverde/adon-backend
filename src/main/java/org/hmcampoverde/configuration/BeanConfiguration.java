package org.hmcampoverde.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

	@Bean(name = "messages")
	MessageSource messageResource() {
		ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
		messageBundle.setBasename("messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}
}
