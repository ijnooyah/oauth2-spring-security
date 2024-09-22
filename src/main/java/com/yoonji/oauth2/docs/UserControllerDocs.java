package com.yoonji.oauth2.docs;


import com.yoonji.oauth2.dto.response.CommonResponse;
import com.yoonji.oauth2.dto.response.UserResponse;
import com.yoonji.oauth2.exception.ErrorResponse;
import com.yoonji.oauth2.security.principal.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "User", description = "User API")
public interface UserControllerDocs {

    @Operation(summary = "회원 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "회원 정보 조회 실패(권한 없음)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public CommonResponse<UserResponse> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal);

}
