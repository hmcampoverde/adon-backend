package org.hmcampoverde.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

	private String severity;
	private String summary;
	private String detail;

}
