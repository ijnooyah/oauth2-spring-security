# 🔐 OAuth2 소셜 로그인 프로젝트

## 📌 프로젝트 개요
이 프로젝트는 OAuth2를 이용한 소셜 로그인 기능을 구현하고, Spring Security와 Spring Boot를 사용하여 RESTful API 방식의 인증 시스템을 제공합니다. 또한 Custom Exception 처리와 Swagger를 통한 API 문서화를 포함하고 있습니다.

## 🛠 기술 스택
- 🍃 Spring Boot
- 🛡 Spring Security
- 🔑 OAuth2
- 🗄 JPA
- 🌐 RESTful API
- 📚 Swagger
- 🐘 Gradle

## 🌟 주요 기능
1. 🔐 OAuth2를 이용한 소셜 로그인
2. 🔄 RESTful API를 통한 사용자 인증 및 조회
3. 🚨 Custom Exception 처리
4. 📖 Swagger를 이용한 API 문서화

## 📚 API 문서
Swagger UI를 통해 API 문서를 확인할 수 있습니다.
- 🔗 URL: `http://localhost:8080/swagger-ui.html`

## 📁 프로젝트 구조
```
src
└── main
    ├── java
    │   └── com
    │       └── yoonji
    │           └── oauth2
    │               ├── config
    │               │   └── SwaggerConfig.java
    │               ├── controller
    │               │   ├── AuthController.java
    │               │   └── UserController.java
    │               ├── docs
    │               │   ├── AuthControllerDocs.java
    │               │   └── UserControllerDocs.java
    │               ├── dto
    │               │   ├── request
    │               │   │   ├── LoginRequest.java
    │               │   │   └── SignupRequest.java
    │               │   └── response
    │               │       ├── CommonResponse.java
    │               │       └── UserResponse.java
    │               ├── entity
    │               │   ├── BaseTimeEntity.java
    │               │   ├── ProviderType.java
    │               │   ├── RoleType.java
    │               │   └── User.java
    │               ├── exception
    │               │   ├── CustomException.java
    │               │   ├── ErrorCode.java
    │               │   ├── ErrorResponse.java
    │               │   └── GlobalExceptionHandler.java
    │               ├── repository
    │               │   └── UserRepository.java
    │               ├── security
    │               │   ├── configs
    │               │   │   ├── AuthConfig.java
    │               │   │   └── SecurityConfig.java
    │               │   ├── dto
    │               │   │   └── OAuthAttributes.java
    │               │   ├── entrypoint
    │               │   │   └── RestAuthenticationEntryPoint.java
    │               │   ├── filter
    │               │   │   └── RestAuthenticationFilter.java
    │               │   ├── handler
    │               │   │   ├── RestAccessDeniedHandler.java
    │               │   │   ├── RestAuthenticationFailureHandler.java
    │               │   │   └── RestAuthenticationSuccessHandler.java
    │               │   ├── principal
    │               │   │   └── UserPrincipal.java
    │               │   ├── provider
    │               │   │   └── RestAuthenticationProvider.java
    │               │   ├── service
    │               │   │   ├── CustomOAuth2UserService.java
    │               │   │   └── CustomUserDetailsService.java
    │               │   └── token
    │               │       └── RestAuthenticationToken.java
    │               └── service
    │                   ├── AuthService.java
    │                   └── UserService.java
    └── resources
        ├── application-oauth.yml
        └── application.yml
```

## 📦 패키지 구조 설명
- 🔧 `config`: 애플리케이션 설정 클래스
- 🎮 `controller`: API 엔드포인트 정의
- 📝 `docs`: Swagger 문서화를 위한 인터페이스
- 📊 `dto`: 데이터 전송 객체
- 🏛 `entity`: JPA 엔티티 클래스
- 🚨 `exception`: 커스텀 예외 및 글로벌 예외 처리
- 🗃 `repository`: 데이터 접근 계층
- 🔒 `security`: 보안 관련 설정 및 구현
    - 🔧 `configs`: 보안 설정 클래스
    - 📊 `dto`: 데이터 전송 객체
    - 🚪 `entrypoint`: 엔트리 포인트
    - 🚦 `filter`: 필터
    - 🎛 `handler`: 핸들러
    - 👤 `principal`: 사용자 주체 정보
    - 🏭 `provider`: 제공자
    - 🛠 `service`: OAuth 및 사용자 상세 서비스
    - 🎟 `token`: 인증 토큰
- 🛠 `service`: 서비스

## 🔍 주요 클래스 설명
- 🔧 `SwaggerConfig`: Swagger 설정 및 API 문서화 구성
- 🔐 `AuthController`: 로그인, 회원가입 등 인증 관련 API 처리
- 👤 `UserController`: 사용자 정보 조회 API 처리
- 📝 `AuthControllerDocs`, `UserControllerDocs`: API 문서화를 위한 인터페이스
- 📊 `CommonResponse`: 응답 표준 형식
- 🚨 `CustomException`: 애플리케이션 특화 예외 클래스
- 🛡 `SecurityConfig`: Spring Security 설정
- 🔑 `CustomOAuth2UserService`: OAuth2 사용자 정보 처리
- 🛠 `CustomUserDetailsService`: 사용자 상세 정보 서비스
- 🚦 `RestAuthenticationFilter`: REST 인증 필터
- 🎛 `RestAuthenticationSuccessHandler`: 인증 성공 처리
- 🎛 `RestAuthenticationFailureHandler`: 인증 실패 처리
- 👤 `UserPrincipal`: 사용자 주체 정보 클래스
- 🏭 `RestAuthenticationProvider`: REST 인증 제공자