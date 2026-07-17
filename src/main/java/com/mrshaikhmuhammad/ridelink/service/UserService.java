package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.dto.response.*;
import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.security.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthUtil authUtil;

    public UserResponseDto getUser() {
        User user = authUtil.getAuthenticatedUser();
        return new UserResponseDto(user.getName(), user.getUsername());
    }
}
