package org.hmcampoverde.message;

import lombok.Getter;

public enum Severity {
	SUCCESS("success"),
	INFO(" info"),
	WARN("warn"),
	ERROR("error");

	@Getter
	private String description;

	Severity(String description) {
		this.description = description;
	}
}
