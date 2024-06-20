package org.hmcampoverde.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

	private Integer id;
	private String name;
	private boolean left;
	private String url;
	private String icon;
	private MenuDto parent;
}
