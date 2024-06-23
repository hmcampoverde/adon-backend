package org.hmcampoverde.controller;

import org.hmcampoverde.dto.ProfileDto;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/findByIdentification/{identification}")
    public ResponseEntity<ProfileDto> findByIdentification(@PathVariable("identification") String identification) {
        return ResponseEntity.ok(profileService.findByIdentification(identification));
    }

    @PutMapping("/udpdateProfile/{identification}")
    public ResponseEntity<Message> updateProfile(@PathVariable("identification") String identification,
            @Valid @RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok(this.profileService.updateProfile(identification, profileDto));
    }
}
