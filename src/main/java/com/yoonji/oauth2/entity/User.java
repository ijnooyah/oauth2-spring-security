package com.yoonji.oauth2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;
    private String email;
    private String picture;
    private String password;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean deleted;

    private LocalDateTime deletedAt;

    @Builder
    public User (String nickname, String email, String password, RoleType role, ProviderType provider, String picture, boolean deleted, PasswordEncoder passwordEncoder) {
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.encodePassword(password, passwordEncoder);
        this.role = role;
        this.deleted = deleted;
    }

    private void encodePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if (rawPassword != null && !rawPassword.isEmpty()) {
            this.password = passwordEncoder.encode(rawPassword);
        }
    }

    public User update(String nickname, String picture) {
        this.nickname = nickname;
        this.picture = picture;
        return this;
    }
}