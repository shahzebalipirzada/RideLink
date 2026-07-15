package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null){
            throw new AuthenticationCredentialsNotFoundException("user not authentication");
        }
        return userRepository.findByUsername(user.getUsername()).orElse(null);
    }
}
