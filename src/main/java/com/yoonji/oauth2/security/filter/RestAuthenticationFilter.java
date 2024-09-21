package com.yoonji.oauth2.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonji.oauth2.dto.request.LoginRequest;
import com.yoonji.oauth2.security.token.RestAuthenticationToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String emailParameter = "email";
    private String passwordParameter = "password";

    public RestAuthenticationFilter(HttpSecurity http) {
        super(new AntPathRequestMatcher("/api/v1/auth/login", "POST"));
        setSecurityContextRepository(getSecurityContextRepository(http));
    }

    private SecurityContextRepository getSecurityContextRepository(HttpSecurity http) {
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository == null) {
            securityContextRepository = new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
        }
        return securityContextRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

        if (!StringUtils.hasText(loginRequest.getEmail()) || !StringUtils.hasText(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("email or Password not provided");
        }
        String email = this.obtainEmail(loginRequest.getEmail());
        email = email != null ? email.trim() : "";
        String password = this.obtainPassword(loginRequest.getPassword());
        password = password != null ? password : "";
        RestAuthenticationToken token = new RestAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());

        return getAuthenticationManager().authenticate(token);
    }

    @Nullable
    protected String obtainPassword(String password) {
        return password;
    }

    @Nullable
    protected String obtainEmail(String email) {
        return email;
    }

    public void setEmailParameter(String emailParameter) {
        Assert.hasText(emailParameter, "email parameter must not be empty or null");
        this.emailParameter = emailParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public final String getEmailParameter() {
        return this.emailParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }
}
