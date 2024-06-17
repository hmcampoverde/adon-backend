package org.hmcampoverde.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.dto.EmployeeRegisterDto;
import org.hmcampoverde.entity.Employee;
import org.hmcampoverde.entity.Rol;
import org.hmcampoverde.entity.User;
import org.hmcampoverde.enums.Profile;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.repository.RolRepository;
import org.hmcampoverde.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RolRepository rolRepository;

    private final BundleMessageHandler bundle;

    private final PasswordEncoder passwordEncoder;

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

    @Override
    public Message register(EmployeeRegisterDto employeeDto) {
        String identification = employeeDto.getIdentification();
        if (exists(null, identification)) {
            throw new CustomException(
                    bundle.getValue("employee.identification.duplicate",
                            new Object[] { identification }),
                    HttpStatus.BAD_REQUEST);
        }

        Rol rol = findRolEmployee();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolRepository.getReferenceById(rol.getId()));

        User user = User
                .builder()
                .username(identification)
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .roles(roles)
                .build();

        Employee employee = Employee
                .builder()
                .firstname(employeeDto.getFirstname())
                .lastname(employeeDto.getLastname())
                .identification(employeeDto.getIdentification())
                .emailInstitutional(employeeDto.getEmailInstitutional())
                .profile(Profile.EMPLOYEE)
                .user(user)
                .build();

        employeeRepository.save(employee);

        return Message
                .builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("employee.saved", new Object[] { identification }))
                .build();
    }

    @Override
    public EmployeeDto findByIdentification(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findByIdentification'");
    }

    private boolean exists(Long id, String identification) {
        Employee employee = employeeRepository.findByIdentificationAndDeletedFalse(identification).orElse(null);

        return Objects.nonNull(employee) && !Objects.equals(employee.getId(), id);
    }

    private Rol findRolEmployee() {
        return rolRepository
                .findByNameIgnoreCase(Profile.EMPLOYEE.getDescription())
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("employee.rol.notfound",
                                new Object[] { Profile.EMPLOYEE }),
                        HttpStatus.BAD_REQUEST));

    }

}
