package com.mrshaikhmuhammad.ridelink.security;


import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.dto.response.*;
import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import com.mrshaikhmuhammad.ridelink.error.InvalidRefreshTokenException;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Duration;


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
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) auth.getPrincipal();
        return new LoginResponseDto(
                user.getId(),
                authUtil.generateAccessTokenCookie(user),
                authUtil.generateRefreshTokenCookie(user)
        );
    }

    public SignupResponseDto signup(SignupRequestDto request){
        User user = signup(request, null, OauthProviderType.EMAIL);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    public User signup(SignupRequestDto request, String providerId, OauthProviderType providerType) {
        User user = userRepository.findByUsername(request.username()).orElse(null);

        if(user != null){
            throw new IllegalStateException("user already exists");
        }
        else{
            user = User.builder()
                    .name(request.name())
                    .username(request.username())
                    .providerId(providerId)
                    .providerType(providerType)
                    .build();

            String password = request.password();
            if(password != null){
                user.setPassword(passwordEncoder.encode(password));
            }

            return userRepository.save(user);
        }
    }

    public RefreshTokenResponseDto refreshToken(String token){
        String username = authUtil.getUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (user.getRefreshToken() == null) {
            throw new InvalidRefreshTokenException("No refresh token found for user; please log in again.");
        }

        token = authUtil.sha256(token);
        if (!token.equals(user.getRefreshToken()) ) {
            authUtil.invalidateRefreshToken(user);
            throw new InvalidRefreshTokenException("Refresh token mismatch: provided token does not match the value stored in the database.");
        }

        return new RefreshTokenResponseDto(
                authUtil.generateAccessTokenCookie(user),
                authUtil.generateRefreshTokenCookie(user)
        );
    }

    public LogoutResponseDto logout(){
        User user = authUtil.getAuthenticatedUser();
        authUtil.invalidateRefreshToken(user);

        ResponseCookie accessToken = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMillis(0))
                .build();

        ResponseCookie refreshToken = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMillis(0))
                .build();

        return new LogoutResponseDto(accessToken, refreshToken);
    }
}
