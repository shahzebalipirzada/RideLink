package com.mrshaikhmuhammad.ridelink.security.handler;

import com.mrshaikhmuhammad.ridelink.dto.request.SignupRequestDto;
import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import com.mrshaikhmuhammad.ridelink.security.AuthService;
import com.mrshaikhmuhammad.ridelink.security.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private final AuthUtil authUtil;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthService authService;

    @Value("${security.oauth.redirect}")
    private String redirectUrl;

    @Value("${security.jwt.access-token.age}")
    private int accessTokenExpiration;

    @Value("${security.jwt.refresh-token.age}")
    private int refreshTokenExpiration;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String registrationId = token.getAuthorizedClientRegistrationId();

        String providerId = authUtil.getProviderId(oauthUser, registrationId);
        OauthProviderType providerType = authUtil.getProviderType(registrationId);

        String email = oauthUser.getAttribute("email");
        User storedOauthUser = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        User storedEmailUser = userRepository.findByUsername(email).orElse(null);


        if(storedOauthUser == null && storedEmailUser == null){
            String username = authUtil.getUsername(oauthUser, registrationId, providerId );
            SignupRequestDto signupRequestDto = new SignupRequestDto(username, null, null);

            try {
                storedOauthUser = authService.signup(signupRequestDto, providerId, providerType);
            } catch (Exception ex){
                throw new IOException(ex.getMessage());
            }
        }
        else if(storedOauthUser != null){
            if(email != null && !email.isBlank() && email != storedEmailUser.getUsername()) {
                storedOauthUser.setUsername(email);
                userRepository.save(storedOauthUser);
            }
        }
        else{
            throw new BadCredentialsException("this email is already registered with an other provider");
        }

        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", authUtil.generateAccessToken(storedOauthUser))
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMillis(accessTokenExpiration))
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", authUtil.generateRefreshToken(storedOauthUser))
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMillis(refreshTokenExpiration))
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE, accessTokenCookie.toString()
        );
        response.addHeader(
                HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()
        );
        response.sendRedirect(
                UriComponentsBuilder.fromUriString(this.redirectUrl).build().toString()
        );
    }
}