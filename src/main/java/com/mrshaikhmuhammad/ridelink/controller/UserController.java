package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.response.UserResponseDto;
import com.mrshaikhmuhammad.ridelink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserDetails(){
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }
}
