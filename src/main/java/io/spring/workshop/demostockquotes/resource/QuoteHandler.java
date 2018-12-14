package io.spring.workshop.demostockquotes.resource;

import static org.springframework.http.MediaType.TEXT_PLAIN;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class QuoteHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(TEXT_PLAIN)
                .syncBody("hello")
                .log();
    }

}
