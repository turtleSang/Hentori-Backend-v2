package com.thanksang.HentoriManager.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class FilerSession extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String device = request.getHeader("User-Agent");
        String deviceSaved = (String) httpSession.getAttribute("Device");
        if (device != null && device.equals(deviceSaved)){
            Authentication authentication = (Authentication) httpSession.getAttribute("ID");
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
        }else {
            httpSession.removeAttribute("ID");
            httpSession.removeAttribute("Device");
        }
        filterChain.doFilter(request, response);
    }
}
