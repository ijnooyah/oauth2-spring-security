package com.yoonji.oauth2.service;



import com.yoonji.oauth2.dto.response.UserResponse;
import com.yoonji.oauth2.entity.User;
import com.yoonji.oauth2.repository.UserRepository;
import com.yoonji.oauth2.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse getUser(UserPrincipal userPrincipal) {
        User findUser = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new RuntimeException("찾을 수 없음"));
        return UserResponse.builder()
                .nickname(findUser.getNickname())
                .email(findUser.getEmail())
                .role(findUser.getRole().name())
                .build();
    }

}
