package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.LoginRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.request.SignupRequestDto;
import com.mrshaikhmuhammad.ridelink.security.AuthService;
import com.mrshaikhmuhammad.ridelink.security.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, "/")
                .header(HttpHeaders.SET_COOKIE, response.accessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshTokenCookie().toString())
                .body(response.loginResponseDto());
    }

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequestDto request){
        try{
            return new ResponseEntity<>(authService.signup(request), HttpStatus.OK);
        } catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}