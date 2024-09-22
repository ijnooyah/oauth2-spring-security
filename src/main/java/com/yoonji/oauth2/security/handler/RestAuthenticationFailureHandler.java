package com.yoonji.oauth2.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonji.oauth2.exception.CustomException;
import com.yoonji.oauth2.exception.ErrorCode;
import com.yoonji.oauth2.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ErrorCode errorCode = getErrorCode(exception);

        log.warn("Authentication Failed: {} - {}", request.getMethod(), request.getRequestURI());
        log.debug("Authentication Exception", exception);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        mapper.writeValue(response.getWriter(), ErrorResponse.of(errorCode));
    }

    private ErrorCode getErrorCode(AuthenticationException exception) {
        ErrorCode errorCode;
        if (exception instanceof BadCredentialsException) {
            errorCode = ErrorCode.INVALID_CREDENTIALS;
        } else if (exception instanceof UsernameNotFoundException) {
            errorCode = ErrorCode.USER_NOT_FOUND;
        } else if (exception.getCause() instanceof CustomException customException) {
            errorCode = customException.getErrorCode();
        } else {
            errorCode = ErrorCode.AUTHENTICATION_FAILED;
        }
        return errorCode;
    }
}
