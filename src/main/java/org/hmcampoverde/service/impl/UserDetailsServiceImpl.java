package org.hmcampoverde.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.hmcampoverde.entity.User;
import org.hmcampoverde.entity.UserPrincipal;
import org.hmcampoverde.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username Invalid."));

		return new UserPrincipal(user, getRoles(user));
	}

	private Collection<? extends GrantedAuthority> getRoles(User user) {
		return user
				.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().toUpperCase())))
				.collect(Collectors.toList());
	}
}
