package com.mrshaikhmuhammad.ridelink.controller;

import lombok.*;
import com.mrshaikhmuhammad.ridelink.service.*;
import com.mrshaikhmuhammad.ridelink.dto.response.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserDetails(){
        UserResponseDto response = userService.getUser();
         return new ResponseEntity<>(response, HttpStatus.OK);
    }
}