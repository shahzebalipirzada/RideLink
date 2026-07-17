package com.mrshaikhmuhammad.ridelink.security;

import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    AuthUtil authUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = authUtil.extractAccessToken(request);
            String username = (token == null)? null : authUtil.getUsername(token);

            if(username == null || SecurityContextHolder.getContext().getAuthentication() != null){
                doFilter(request,response,filterChain);
                return;
            }
            User user = userRepository.findByUsername(username).orElseThrow();
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities())
            );
            doFilter(request,response,filterChain);
            return;

        } catch (Exception ex){
            handlerExceptionResolver.resolveException(request,response, null,ex);
        }
    }
}
