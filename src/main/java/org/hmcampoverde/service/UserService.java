package org.hmcampoverde.service;

import java.text.ParseException;

import org.hmcampoverde.dto.LoginDto;
import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.dto.UserDto;
import org.hmcampoverde.message.Message;

public interface UserService {

	public UserDto findByUsername(String username);

	public Message updatePasswordByUsername(String username, UserDto userDto);

	public Message deleteByUsername(String username);

	public TokenDto login(LoginDto login);

	public TokenDto refresh(TokenDto toketDto) throws ParseException;
}
