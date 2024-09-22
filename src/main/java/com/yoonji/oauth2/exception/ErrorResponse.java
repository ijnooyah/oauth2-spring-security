package com.yoonji.oauth2.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Builder
public class ErrorResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}
