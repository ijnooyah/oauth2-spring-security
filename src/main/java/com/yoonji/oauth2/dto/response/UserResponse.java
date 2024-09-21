package com.yoonji.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String email;
    private String nickname;
    private String role;

    @Builder
    public UserResponse(String email, String nickname, String role) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }
}
