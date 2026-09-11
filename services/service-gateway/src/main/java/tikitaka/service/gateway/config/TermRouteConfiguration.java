package tikitaka.service.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration(proxyBeanMethods = false)
@Profile("dev")
public class TermRouteConfiguration {

    @Bean
    public RouterFunction<ServerResponse> termRoute(
            @Value("${SERVICE_TERM_URI:http://127.0.0.1:8084}") String serviceTermUri
    ) {
        return route("service-term")
                .route(path("/term/**"), http())
                .before(uri(serviceTermUri))
                .before(stripPrefix(1))
                .build();
    }
}
