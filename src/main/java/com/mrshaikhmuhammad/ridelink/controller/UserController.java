package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping
    public User getUserDetails(){
        return userService.getUser();
    }

}
