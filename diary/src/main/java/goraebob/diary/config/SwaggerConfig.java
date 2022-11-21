package goraebob.diary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Import(BeanValidatorPluginsConfiguration.class) // 1 스프링부트에서 작성했던 bean validation을 문서화하기 위해 BeanValidatorPluginsConfiguration @Import
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()) // 2    API 문서에 대한 정보를 작성
                .select() // 3  select()을 통해 반환되는 ApiSelectorBuilder를 이용하여, API 문서를 작성할 셀렉터를 지정하고 빌드할 수 있음. 컨트룰러 패키지를 지정
                .apis(RequestHandlerSelectors.basePackage("goraebob.diary.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKey())) // 4
                .securityContexts(List.of(securityContext())); // 5
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("diary")
                .description("goraebob diary REST API Documentation")
                .license("ho1121h@gmail.com")
                .licenseUrl("https://github.com/ho1121h/diary_BE.git")
                .version("1.0")
                .build();
    }

    private static ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer Token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .operationSelector(oc -> oc.requestMappingPattern().startsWith("/api/")).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "global access");
        return List.of(new SecurityReference("Authorization", new AuthorizationScope[] {authorizationScope}));
    }
}
/**
 * 간단하게 브라우저에서 테스트할 수 있게 api 문서 작성임
 * 포스트맨 쓰기 귀찮앙...
 *
 */