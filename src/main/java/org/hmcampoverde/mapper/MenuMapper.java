package org.hmcampoverde.mapper;

import java.util.Objects;

import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.entity.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

	public MenuDto map(Menu menu) {
		return MenuDto
				.builder()
				.id(menu.getId())
				.name(menu.getName())
				.left(menu.isLeft())
				.url(menu.getUrl())
				.icon(menu.getIcon())
				.parent(Objects.nonNull(menu.getParent()) ? map(menu.getParent()) : null)
				.build();
	}
}
