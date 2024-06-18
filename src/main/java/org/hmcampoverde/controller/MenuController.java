package org.hmcampoverde.controller;

import java.util.List;

import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/findByRoles/{roles}")
	public ResponseEntity<List<MenuDto>> findByRoles(@PathVariable("roles") String roles) {
		return ResponseEntity.ok(menuService.findByRoles(roles));
	}
}
