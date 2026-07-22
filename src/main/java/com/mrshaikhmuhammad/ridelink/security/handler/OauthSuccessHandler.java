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

@Component
@RequiredArgsConstructor
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Value("${security.oauth.redirect.homepage}")
    private String redirectUrl;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String registrationId = token.getAuthorizedClientRegistrationId();
        String providerId = authUtil.getProviderId(oauthUser, registrationId);
        OauthProviderType providerType = authUtil.getProviderType(registrationId);

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User storedOauthUser = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        User storedEmailUser = userRepository.findByUsername(email).orElse(null);


        if(storedOauthUser == null && storedEmailUser == null){
            SignupRequestDto signupRequestDto = new SignupRequestDto(name, email, null);
            try {
                storedOauthUser = authService.signup(signupRequestDto, providerId, providerType);
            } catch (Exception ex){
                throw new IOException(ex.getMessage());
            }
        }
        else if (storedEmailUser != null) {
            if (!storedEmailUser.equals(storedOauthUser)) {
                storedEmailUser.setProviderId(providerId);
                storedEmailUser.setProviderType(providerType);
                userRepository.save(storedEmailUser);
                storedOauthUser = storedEmailUser;
            }
        }
        else{
            throw new BadCredentialsException("this email is already registered with an other provider");
        }

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                authUtil.generateAccessTokenCookie(storedOauthUser).toString()
        );
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                authUtil.generateRefreshTokenCookie(storedOauthUser).toString()
        );
        response.sendRedirect(
                UriComponentsBuilder.fromUriString(this.redirectUrl).build().toString()
        );
    }
}