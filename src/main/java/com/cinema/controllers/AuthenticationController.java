package com.cinema.controllers;

import com.cinema.model.dto.UserRequestDto;
import com.cinema.security.AuthenticationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public void register(@RequestBody @Valid UserRequestDto userRequestDto) {
        authenticationService.register(userRequestDto.getEmail(), userRequestDto.getPassword());
    }
}
