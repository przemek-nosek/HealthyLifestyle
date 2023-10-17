package pl.healthylifestyle.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class OpenApiConfig {

    private static final String USERS_PATH = "/users/v3/api-docs";
    private static final String USERS_PATH_URI = "lb://additional-user-data-service";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route.path(USERS_PATH).and().method(HttpMethod.GET).uri(USERS_PATH_URI))
                .build();
    }
}
