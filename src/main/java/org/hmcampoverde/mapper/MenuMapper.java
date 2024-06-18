package org.hmcampoverde.mapper;

import java.util.Objects;

import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.entity.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

	public MenuDto map(Menu menu) {
		return new MenuDto(
				menu.getId(),
				menu.getName(),
				menu.isLeft(),
				menu.getUrl(),
				menu.getIcon(),
				Objects.nonNull(menu.getParent()) ? map(menu.getParent()) : null);
	}
}
