package pl.healthylifestyle.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class OpenApiRouteLocatorConfig {

    private static final String USERS_API_DOCS_PATH = "/users/v3/api-docs";
    private static final String USERS_PATH_URI = "lb://additional-user-data-service";
    private static final String MEASUREMENTS_API_DOCS_PATH = "/measurements/v3/api-docs";
    private static final String MEASUREMENTS_PATH_URI = "lb://measurement-service";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route.path(USERS_API_DOCS_PATH).and().method(HttpMethod.GET).uri(USERS_PATH_URI))
                .route(route -> route.path(MEASUREMENTS_API_DOCS_PATH).and().method(HttpMethod.GET).uri(MEASUREMENTS_PATH_URI))
                .build();
    }
}
