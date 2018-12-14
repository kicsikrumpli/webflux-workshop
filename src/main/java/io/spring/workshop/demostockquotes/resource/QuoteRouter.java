package io.spring.workshop.demostockquotes.resource;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class QuoteRouter {
    @Autowired
    private QuoteHandler handler;

    @Bean
    public RouterFunction<ServerResponse> helloRouterFunction() {
        return RouterFunctions
                .route()
                .GET("/hello-world", accept(TEXT_PLAIN), handler::hello)
                .build();
    }

}
