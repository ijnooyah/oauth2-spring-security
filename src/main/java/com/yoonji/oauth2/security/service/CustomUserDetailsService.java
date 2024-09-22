package com.yoonji.oauth2.security.service;

import com.yoonji.oauth2.entity.User;
import com.yoonji.oauth2.exception.CustomException;
import com.yoonji.oauth2.exception.ErrorCode;
import com.yoonji.oauth2.repository.UserRepository;
import com.yoonji.oauth2.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found", new CustomException(ErrorCode.USER_NOT_FOUND)));
        return new UserPrincipal(user, null);
    }
}
