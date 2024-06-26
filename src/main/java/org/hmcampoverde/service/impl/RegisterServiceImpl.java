package org.hmcampoverde.service.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hmcampoverde.dto.RegisterDto;
import org.hmcampoverde.entity.Employee;
import org.hmcampoverde.entity.Rol;
import org.hmcampoverde.entity.User;
import org.hmcampoverde.enums.Profile;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.repository.RolRepository;
import org.hmcampoverde.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final RolRepository rolRepository;
    private final BundleMessageHandler bundle;

    @Override
    public Message register(RegisterDto registerDto) {
        String identification = registerDto.getIdentification();
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
                .password(passwordEncoder.encode(registerDto.getNewPassword()))
                .roles(roles)
                .build();

        Employee employee = Employee
                .builder()
                .firstname(registerDto.getFirstname())
                .lastname(registerDto.getLastname())
                .identification(registerDto.getIdentification())
                .emailInstitutional(registerDto.getEmailInstitutional())
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

    private boolean exists(Long id, String identification) {
        Employee employee = employeeRepository.findByIdentification(identification).orElse(null);

        return Objects.nonNull(employee) && !Objects.equals(employee.getId(), id);
    }

    private Rol findRolEmployee() {
        return rolRepository
                .findByNameIgnoreCase(Profile.EMPLOYEE.getDescription())
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("employee.rol.notfound", new Object[] { Profile.EMPLOYEE }),
                        HttpStatus.BAD_REQUEST));

    }

}