package com.mrshaikhmuhammad.ridelink.controller;

import lombok.*;
import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.dto.response.*;
import com.mrshaikhmuhammad.ridelink.security.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshTokenCookie().toString())
                .body(response.userId());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto request){
        SignupResponseDto response = authService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        LogoutResponseDto response = authService.logout();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshTokenCookie().toString())
                .build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken( @CookieValue(name = "refresh-token") String token){
        RefreshTokenResponseDto response = authService.refreshToken(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshTokenCookie().toString())
                .build();
    }
}