package org.hmcampoverde.service.impl;

import org.hmcampoverde.dto.ProfileDto;
import org.hmcampoverde.entity.Employee;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.ProfileMapper;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfleServiceImpl implements ProfileService {

    private final ProfileMapper profileMapper;
    private final EmployeeRepository employeeRepository;
    private final BundleMessageHandler bundle;

    @Override
    public ProfileDto findByIdentification(String identification) {
        return employeeRepository
                .findByIdentification(identification)
                .map(profileMapper::map)
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("exception.notfound", new Object[] { identification }),
                        HttpStatus.NOT_FOUND));
    }

    @Override
    public Message updateProfile(String identification, ProfileDto profileDto) {
        Employee employee = this.employeeRepository.findByIdentification(identification).orElseThrow(
                () -> new CustomException(
                        bundle.getValue("exception.notfound ", new Object[] { identification }),
                        HttpStatus.NOT_FOUND));

        employee.setFirstname(profileDto.getFirstname());
        employee.setLastname(profileDto.getLastname());
        employee.setMobil(profileDto.getMobil());
        employee.setEmailInstitutional(profileDto.getEmailInstitutional());
        employee.setEmailPersonal(profileDto.getEmailPersonal());
        employee.setAddress(profileDto.getAddress());
        employee.setManager(profileDto.isManager());

        employeeRepository.save(employee);

        return Message
                .builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("employee.saved", new Object[] { identification }))
                .build();
    }

}
