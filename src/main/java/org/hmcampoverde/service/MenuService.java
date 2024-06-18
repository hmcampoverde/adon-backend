package org.hmcampoverde.service;

import java.util.List;

import org.hmcampoverde.dto.MenuDto;

public interface MenuService {
	public List<MenuDto> findByRoles(String roles);
}
