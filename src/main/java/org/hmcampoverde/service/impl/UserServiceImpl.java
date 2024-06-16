package org.hmcampoverde.service.impl;

import java.text.ParseException;

import org.hmcampoverde.dto.LoginDto;
import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.dto.UserDto;
import org.hmcampoverde.entity.User;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.UserMapper;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.repository.UserRepository;
import org.hmcampoverde.security.CustomProvider;
import org.hmcampoverde.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BundleMessageHandler bundle;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomProvider customProvider;

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.map(findUserByUsername(username));
    }

    @Override
    public Message updatePasswordByUsername(String username, UserDto userDto) {
        if (username == null) {
            throw new CustomException(bundle.getValue("exception.unknown"), HttpStatus.BAD_REQUEST);
        }

        boolean validator = userDto.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$");
        if (!validator) {
            throw new CustomException(bundle.getValue("user.password.ivalid"), HttpStatus.BAD_REQUEST);
        }

        findUserByUsername(username);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.updatePasswordByUsername(userDto.getUsername(), userDto.getPassword());

        return Message
                .builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("user.update", new Object[] { username }))
                .build();
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return TokenDto.builder().token(customProvider.buildToken(authentication)).build();

    }

    @Override
    public TokenDto refresh(TokenDto toketDto) throws ParseException {
        toketDto.setToken(customProvider.refreshToken(toketDto));
        return toketDto;
    }

    private User findUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new CustomException(bundle.getValue("user.username.notfound", new Object[] { username }),
                                HttpStatus.NOT_FOUND));
    }

}
