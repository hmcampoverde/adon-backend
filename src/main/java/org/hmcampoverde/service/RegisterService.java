package org.hmcampoverde.service;

import org.hmcampoverde.dto.RegisterDto;
import org.hmcampoverde.message.Message;

public interface RegisterService {

    public Message register(RegisterDto employeeDto);

}
