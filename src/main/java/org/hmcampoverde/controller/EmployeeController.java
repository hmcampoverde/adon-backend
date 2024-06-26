package org.hmcampoverde.controller;

import java.util.List;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.service.EmployeeService;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/findAll")
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok().body(this.employeeService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.employeeService.findById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Message> create(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(employeeService.create(employeeDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(employeeService.update(id, employeeDto));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.employeeService.delete(id));
    }

}
