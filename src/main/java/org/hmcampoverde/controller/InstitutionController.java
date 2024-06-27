package org.hmcampoverde.controller;

import java.util.List;

import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.service.InstitutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping("/findAll")
    public ResponseEntity<List<InstitutionDto>> findAll() {
        return ResponseEntity.ok().body(institutionService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<InstitutionDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.institutionService.findById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Message> create(@Valid @RequestBody InstitutionDto institutionDto) {
        return ResponseEntity.ok().body(institutionService.create(institutionDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable("id") Long id,
            @Valid @RequestBody InstitutionDto institutionDto) {
        return ResponseEntity.ok().body(institutionService.update(id, institutionDto));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.institutionService.delete(id));
    }
}
