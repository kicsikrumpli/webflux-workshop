package io.spring.workshop.demostockquotes.resource;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class QuoteRouter {

    @Bean
    public RouterFunction<ServerResponse> helloRouterFunction(QuoteHandler handler) {
        return RouterFunctions
                .route()
                .GET("/hello-world", accept(TEXT_PLAIN), handler::hello)
                .POST("/echo", accept(TEXT_PLAIN), handler::echo)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> quoteVomitter(QuoteHandler handler) {
        return RouterFunctions
                .route()
                .GET("/quotes", accept(APPLICATION_STREAM_JSON), handler::streamQuotes)
                .build();
    }

}
