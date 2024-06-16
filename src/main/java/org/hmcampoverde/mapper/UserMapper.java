package org.hmcampoverde.mapper;

import org.hmcampoverde.dto.UserDto;
import org.hmcampoverde.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto map(User user) {
        return UserDto
                .builder()
                .fullname(user.getEmployee().getFullname())
                .username(user.getUsername())
                .build();

    }
}
