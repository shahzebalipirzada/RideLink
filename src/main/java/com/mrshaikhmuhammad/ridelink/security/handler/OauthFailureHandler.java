package com.mrshaikhmuhammad.ridelink.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class OauthFailureHandler implements AuthenticationFailureHandler {

    @Value("${security.oauth.redirect.login}")
    private String redirectUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error(exception.getMessage());

        String targetUrl = UriComponentsBuilder.fromUriString(this.redirectUrl)
                .queryParam("error", URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8))
                .build().toUriString();

        response.sendRedirect(targetUrl);
    }

}
