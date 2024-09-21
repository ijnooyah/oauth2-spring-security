package com.yoonji.oauth2.controller;



import com.yoonji.oauth2.dto.response.CommonResponse;
import com.yoonji.oauth2.dto.response.UserResponse;
import com.yoonji.oauth2.security.principal.UserPrincipal;
import com.yoonji.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public CommonResponse<UserResponse> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new CommonResponse<>(HttpStatus.OK, userService.getUser(userPrincipal));
    }

}
