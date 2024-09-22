package com.yoonji.oauth2.service;

import com.yoonji.oauth2.dto.request.SignupRequest;
import com.yoonji.oauth2.dto.response.UserResponse;
import com.yoonji.oauth2.entity.ProviderType;
import com.yoonji.oauth2.entity.RoleType;
import com.yoonji.oauth2.entity.User;
import com.yoonji.oauth2.exception.CustomException;
import com.yoonji.oauth2.exception.ErrorCode;
import com.yoonji.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(SignupRequest request) {
        checkDuplicateEmail(request.getEmail());

        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(RoleType.ROLE_USER)
                .provider(ProviderType.LOCAL)
                .deleted(false)
                .passwordEncoder(passwordEncoder)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .nickname(savedUser.getNickname())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

}
