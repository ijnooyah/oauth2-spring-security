package com.yoonji.oauth2.docs;

import com.yoonji.oauth2.dto.request.SignupRequest;
import com.yoonji.oauth2.dto.response.CommonResponse;
import com.yoonji.oauth2.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Auth API")
public interface AuthControllerDocs {

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    public CommonResponse<UserResponse> signup(@RequestBody SignupRequest request);

    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    public CommonResponse<Void> logout(HttpServletRequest request, HttpServletResponse response);
}
