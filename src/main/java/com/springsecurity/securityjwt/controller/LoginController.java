package com.springsecurity.securityjwt.controller;

import com.springsecurity.securityjwt.payloaddto.LoginRequest;
import com.springsecurity.securityjwt.payloaddto.LoginResponse;
import com.springsecurity.securityjwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;

    // Authentication == Logging
    @PostMapping({"/authentication"})
    public LoginResponse createJwtTokenAndLogin(@RequestBody LoginRequest loginRequest) throws Exception{
        System.out.println(loginRequest);
        return jwtService.createJwtToken(loginRequest);

    }
}
