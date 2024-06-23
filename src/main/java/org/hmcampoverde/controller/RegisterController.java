package org.hmcampoverde.controller;

import org.hmcampoverde.dto.RegisterDto;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    public final RegisterService registerService;

    @PostMapping("/")
    public ResponseEntity<Message> register(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok().body(registerService.register(registerDto));
    }

}
