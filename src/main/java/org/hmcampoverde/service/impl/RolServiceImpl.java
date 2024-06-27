package org.hmcampoverde.service.impl;

import org.hmcampoverde.entity.Rol;
import org.hmcampoverde.enums.Profile;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.repository.RolRepository;
import org.hmcampoverde.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final BundleMessageHandler bundle;

    @Override
    public Rol findByName(String name) {
        return rolRepository
                .findByNameIgnoreCase(name)
                .orElseThrow(() -> new CustomException(
                        bundle.getValue("rol.notfound", new Object[] { Profile.EMPLOYEE }),
                        HttpStatus.BAD_REQUEST));
    }

    @Override
    public Rol getReferenceById(Long id) {
        return rolRepository.getReferenceById(id);
    }

}
