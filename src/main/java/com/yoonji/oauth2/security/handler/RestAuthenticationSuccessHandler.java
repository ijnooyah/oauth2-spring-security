package com.yoonji.oauth2.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonji.oauth2.dto.response.CommonResponse;
import com.yoonji.oauth2.dto.response.UserResponse;
import com.yoonji.oauth2.security.principal.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserResponse userResponse = UserResponse.builder()
                .nickname(userPrincipal.getNickname())
                .email(userPrincipal.getEmail())
                .role(userPrincipal.getRole().name())
                .build();

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .body(userResponse)
                .build();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        mapper.writeValue(response.getWriter(), commonResponse);

        clearAuthenticationAttributes(request);
    }
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
