package com.uplus.ureka.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi boardGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("gooham") // group 설정 (API들을 그룹화시켜 그룹에 속한 API들만 확인할 수 있도록 도와줌)
                .pathsToMatch(  // group에 포함될 API endpoint 경로
                        "/gooham/users/**",
                        "/gooham/posts/**",
                        "/gooham/comments/**",
                        "/gooham/participants/**",
                        "/gooham/notifications/**"
                ) // 여러 개의 경로 추가
                .addOpenApiCustomizer(
                        openApi ->
                                openApi
                                        .setInfo(
                                                new Info()
                                                        .title("GooHam API") // API 제목
                                                        .description("구함(GooHam) API") // API 설명
                                                        .version("1.0.0") // API 버전
                                        )
                )
                .build();
    }
}