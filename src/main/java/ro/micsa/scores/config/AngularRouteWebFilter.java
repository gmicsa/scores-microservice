package ro.micsa.scores.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * This web filter will forward all requests to Angular routes to /index.html
 * so that they are not handled by Spring and no 404 is returned.
 */
@Component
public class AngularRouteWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestPath = exchange.getRequest().getURI().getPath();
        if (isRequestForRoot(requestPath) || isAngularRouteRequest(requestPath)) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
        }

        return chain.filter(exchange);
    }

    private boolean isAngularRouteRequest(String requestPath) {
        return requestPath.startsWith("/scores");
    }

    private boolean isRequestForRoot(String requestPath) {
        return requestPath.equals("/");
    }
}