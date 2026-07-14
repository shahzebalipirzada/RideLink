package com.mrshaikhmuhammad.ridelink.security;


import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.dto.response.*;
import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public LoginResponseDto login(LoginRequestDto request){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) auth.getPrincipal();
        String jwtSecurityToken = authUtil.generateAccessToken(user);
        return new LoginResponseDto(user.getId(), jwtSecurityToken);
    }

    public SignupResponseDto signup(SignupRequestDto request) throws Exception{
        User user = signup(request, null, null);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    public User signup(SignupRequestDto request, String providerId, OauthProviderType providerType) throws Exception{
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        if(user != null){
            throw new Exception("user already exists");
        }
        else{
            user = User.builder()
                    .username(request.getUsername())
                    .providerId(providerId)
                    .providerType(providerType)
                    .build();

            String password = request.getPassword();
            if(password != null){
                user.setPassword(passwordEncoder.encode(password));
            }

            return userRepository.save(user);
        }
    }
}
