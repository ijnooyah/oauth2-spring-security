package com.yoonji.oauth2.config;

import com.yoonji.oauth2.exception.ErrorCode;
import com.yoonji.oauth2.security.filter.RestAuthenticationFilter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.Optional;

@Configuration
public class SwaggerConfig {

    private static final String LOGIN_WITH_EMAIL = "/api/v1/auth/login";
    private final ApplicationContext applicationContext;

    public SwaggerConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public OpenAPI openAPI() {
        Info info = new Info()
                .version("1.0")
                .title("OAuth2 & Spring Security 6")
                .description("스프링 시큐리티 버전 6으로 OAuth2 구현하기");

        return new OpenAPI()
                .info(info);
    }

    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointCustomiser() {
        FilterChainProxy filterChainProxy = applicationContext.getBean(
                AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<RestAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(RestAuthenticationFilter.class::isInstance)
                                .map(RestAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    RestAuthenticationFilter restAuthenticationFilter = optionalFilter.get();
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperty(restAuthenticationFilter.getEmailParameter(), new StringSchema())
                            .addProperty(restAuthenticationFilter.getPasswordParameter(),
                                    new StringSchema());
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                    new MediaType().schema(schema)));
                    operation.requestBody(requestBody);
                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description("로그인 성공"));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                            new ApiResponse().description("인증 실패"));
                    operation.responses(apiResponses);
                    operation.addTagsItem("email-login-endpoint");
                    PathItem pathItem = new PathItem().post(operation);
                    openAPI.getPaths().addPathItem(LOGIN_WITH_EMAIL, pathItem);
                }
            }
        };
    }
}
