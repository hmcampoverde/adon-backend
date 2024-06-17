package org.hmcampoverde.service;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.dto.EmployeeRegisterDto;
import org.hmcampoverde.message.Message;

public interface EmployeeService extends Service<EmployeeDto> {

    public Message register(EmployeeRegisterDto employeeDto);

    public EmployeeDto findByIdentification(String id);

}