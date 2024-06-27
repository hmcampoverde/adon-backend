
package org.hmcampoverde.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.entity.Institution;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.InstitutionMapper;
import org.hmcampoverde.message.BundleMessageHandler;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.repository.InstitutionRepository;
import org.hmcampoverde.service.InstitutionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;
    private final BundleMessageHandler bundle;

    @Override
    public List<InstitutionDto> findAll() {
        return institutionRepository
                .findAll()
                .stream()
                .map(institutionMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public InstitutionDto findById(Long id) {
        return institutionMapper.map(findByIdInstitution(id));
    }

    @Override
    public Message create(InstitutionDto institutionDto) {
        Institution institution;
        String amie = institutionDto.getAmie();
        if (exists(null, amie)) {
            throw new CustomException(
                    bundle.getValue("institution.amie.duplicate", new Object[] { amie }),
                    HttpStatus.BAD_REQUEST);
        }

        institution = Institution
                .builder()
                .name(institutionDto.getName())
                .amie(institutionDto.getAmie())
                .phone(institutionDto.getPhone())
                .email(institutionDto.getEmail())
                .address(institutionDto.getAddress())
                .build();

        institutionRepository.save(institution);

        return Message
                .builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("institution.saved", new Object[] { amie }))
                .build();
    }

    @Override
    public Message update(Long id, InstitutionDto institutionDto) {
        Institution institution;
        String amie = institutionDto.getAmie();
        if (exists(id, amie)) {
            throw new CustomException(
                    bundle.getValue("institution.amie.duplicate", new Object[] { amie }),
                    HttpStatus.BAD_REQUEST);
        }

        institution = findByIdInstitution(id);
        institution.setName(institutionDto.getName());
        institution.setAmie(institutionDto.getAmie());
        institution.setPhone(institutionDto.getPhone());
        institution.setEmail(institutionDto.getEmail());
        institution.setAddress(institutionDto.getAddress());

        institutionRepository.save(institution);

        return Message
                .builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("institution.saved", new Object[] { amie }))
                .build();
    }

    @Override
    public Message delete(Long id) {
        Institution institution = findByIdInstitution(id);

        institution.setDeleted(Boolean.TRUE);
        institutionRepository.save(institution);

        return Message.builder()
                .severity("success")
                .summary(bundle.getValue("title.information"))
                .detail(bundle.getValue("institution.deleted", new Object[] { institution.getAmie() }))
                .build();
    }

    private boolean exists(Long id, String amie) {
        Institution institution = institutionRepository
                .findByAmie(amie).orElse(null);

        return Objects.nonNull(institution) && !Objects.equals(id, institution.getId());
    }

    private Institution findByIdInstitution(Long id) {
        if (Objects.isNull(id)) {
            throw new CustomException(bundle.getValue("institution.id.notfound", new Object[] { id }),
                    HttpStatus.BAD_REQUEST);
        }

        return institutionRepository
                .findById(id)
                .orElseThrow(
                        () -> new CustomException(
                                bundle.getValue("institution.id.notfound", new Object[] { id }),
                                HttpStatus.NOT_FOUND));
    }

}