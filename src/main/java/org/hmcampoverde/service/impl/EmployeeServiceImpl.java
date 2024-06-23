package org.hmcampoverde.service.impl;

import java.util.List;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public List<EmployeeDto> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public EmployeeDto findById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Message create(EmployeeDto entity) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Message update(Long id, EmployeeDto entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Message delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
