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
import org.hmcampoverde.repository.RolRepository;
import org.hmcampoverde.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
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
        return employeeRepository
                .findById(id)
                .map(employeeMapper::map)
                .orElseThrow(
                        () -> new CustomException(
                                bundle.getValue("exception.notfound", new Object[] { id }),
                                HttpStatus.NOT_FOUND));

    }

    @Override
    public Message create(EmployeeDto employeeDto) {
        String identification = employeeDto.getIdentification();
        if (exists(null, identification)) {
            throw new CustomException(
                    bundle.getValue("employee.identification.duplicate", new Object[] { identification }),
                    HttpStatus.BAD_REQUEST);
        }

        Rol rol = findRolEmployee();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolRepository.getReferenceById(rol.getId()));

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
        String identification;

        employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new CustomException(
                                bundle.getValue("exception.notfound", new Object[] { id }),
                                HttpStatus.NOT_FOUND));

        identification = employeeDto.getIdentification();
        if (exists(id, identification)) {
            throw new CustomException(
                    bundle.getValue("employee.identification.duplicate", new Object[] { identification }),
                    HttpStatus.BAD_REQUEST);
        }

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
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("exception.notfound", new Object[] { id }),
                        HttpStatus.NOT_FOUND));

        User user = userRepository
                .findByUsername(employee.getIdentification())
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("exception.notfound", new Object[] { employee.getIdentification() }),
                        HttpStatus.NOT_FOUND));

        employee.setDeleted(Boolean.TRUE);
        employeeRepository.save(employee);

        user.setDeleted(Boolean.TRUE);
        userRepository.save(user);

        return Message.builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("employee.deleted", new Object[] { employee.getIdentification() }))
                .build();
    }

    private boolean exists(Long id, String identification) {
        Employee employee = employeeRepository
                .findByIdentification(identification).orElse(null);

        return Objects.nonNull(employee) && !Objects.equals(id, employee.getId());
    }

    private Rol findRolEmployee() {
        return rolRepository
                .findByNameIgnoreCase(Profile.EMPLOYEE.getDescription())
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("employee.rol.notfound", new Object[] { Profile.EMPLOYEE }),
                        HttpStatus.BAD_REQUEST));

    }

}
