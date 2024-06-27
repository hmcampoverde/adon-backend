
package org.hmcampoverde.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.entity.Employee;
import org.hmcampoverde.entity.Rol;
import org.hmcampoverde.entity.User;
import org.hmcampoverde.enums.Profile;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.EmployeeMapper;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.service.EmployeeService;
import org.hmcampoverde.service.RolService;
import org.hmcampoverde.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final RolService rolService;
    private final UserService userService;

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final BundleMessageHandler bundle;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository
                .findAll()
                .stream()
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(Long id) {
        return employeeMapper.map(findByIdEmployee(id));
    }

    @Override
    public Message create(EmployeeDto employeeDto) {
        String identification = employeeDto.getIdentification();
        if (exists(null, identification)) {
            throw new CustomException(
                    bundle.getValue("employee.identification.duplicate", new Object[] { identification }),
                    HttpStatus.BAD_REQUEST);
        }

        Rol rol = rolService.findByName(Profile.EMPLOYEE.getDescription());
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getReferenceById(rol.getId()));

        User user = User
                .builder()
                .username(identification)
                .password(passwordEncoder.encode(employeeDto.getIdentification()))
                .roles(roles)
                .build();

        Employee employee = Employee
                .builder()
                .firstname(employeeDto.getFirstname())
                .lastname(employeeDto.getLastname())
                .identification(employeeDto.getIdentification())
                .mobil(employeeDto.getMobil())
                .emailInstitutional(employeeDto.getEmailInstitutional())
                .emailPersonal(employeeDto.getEmailPersonal())
                .address(employeeDto.getAddress())
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
    public Message update(Long id, EmployeeDto employeeDto) {
        Employee employee;
        String identification = employeeDto.getIdentification();

        if (exists(id, identification)) {
            throw new CustomException(
                    bundle.getValue("employee.identification.duplicate", new Object[] { identification }),
                    HttpStatus.BAD_REQUEST);
        }

        employee = findByIdEmployee(id);
        employee.setFirstname(employeeDto.getFirstname());
        employee.setLastname(employeeDto.getLastname());
        employee.setIdentification(employeeDto.getIdentification());
        employee.setMobil(employeeDto.getMobil());
        employee.setEmailInstitutional(employeeDto.getEmailInstitutional());
        employee.setEmailPersonal(employeeDto.getEmailPersonal());
        employee.setAddress(employeeDto.getAddress());
        employee.setManager(employeeDto.isManager());

        employeeRepository.save(employee);

        return Message.builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("employee.saved", new Object[] { employeeDto.getIdentification() }))
                .build();
    }

    @Override
    public Message delete(Long id) {
        Employee employee = findByIdEmployee(id);

        employee.setDeleted(Boolean.TRUE);
        employeeRepository.save(employee);

        userService.deleteByUsername(employee.getIdentification());

        return Message.builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("employee.deleted", new Object[] { employee.getIdentification() }))
                .build();
    }

    private boolean exists(Long id, String identification) {
        Employee employee = employeeRepository
                .findByIdentification(identification)
                .orElse(null);

        return Objects.nonNull(employee) && !Objects.equals(id, employee.getId());
    }

    private Employee findByIdEmployee(Long id) {
        if (Objects.isNull(id)) {
            throw new CustomException(
                    bundle.getValue("employee.id.notfound", new Object[] { id }),
                    HttpStatus.BAD_REQUEST);
        }

        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new CustomException(
                                bundle.getValue("employee.id.notfound", new Object[] { id }),
                                HttpStatus.NOT_FOUND));
    }

}