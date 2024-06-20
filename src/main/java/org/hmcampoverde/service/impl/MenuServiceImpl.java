package org.hmcampoverde.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.mapper.MenuMapper;
import org.hmcampoverde.repository.MenuRepository;
import org.hmcampoverde.service.MenuService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final MenuMapper menuMapper;

	@Override
	@Transactional(readOnly = true)
	public List<MenuDto> findByRoles(String roles) {
		return menuRepository
				.findByRolesNameIn(getRoles(roles), Sort.by(Sort.Direction.ASC, "name"))
				.stream()
				.map(menuMapper::map)
				.toList();
	}

	private List<String> getRoles(String strRoles) {
		List<String> roles = Arrays.asList(strRoles.split(","));
		return roles.stream().map(rol -> rol.substring(5).toLowerCase()).collect(Collectors.toList());
	}
}
