package com.yoonji.oauth2.docs;

import com.yoonji.oauth2.dto.request.SignupRequest;
import com.yoonji.oauth2.dto.response.CommonResponse;
import com.yoonji.oauth2.dto.response.UserResponse;
import com.yoonji.oauth2.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Auth API")
public interface AuthControllerDocs {

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "회원가입 실패(이미 존재하는 이메일)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public CommonResponse<UserResponse> signup(@RequestBody SignupRequest request);

    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    public CommonResponse<Void> logout(HttpServletRequest request, HttpServletResponse response);
}
