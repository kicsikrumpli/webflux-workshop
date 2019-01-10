package io.spring.workshop.demostockquotes.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.spring.workshop.demostockquotes.domain.Quote;
import io.spring.workshop.demostockquotes.generator.QuoteGenerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class QuoteHandler {
    private Flux<Quote> lotsOfQuotes;

    public QuoteHandler(QuoteGenerator quoteGenerator) {
        lotsOfQuotes = quoteGenerator
                .fetchQuoteStream(Duration.ofMillis(5000L))
                .share();
                //.publish().autoConnect();
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(TEXT_PLAIN)
                .syncBody("hello")
                .log();
    }

    public Mono<ServerResponse> echo(ServerRequest request) {
        return ServerResponse
                .ok()
                .body(request.bodyToMono(String.class), String.class);
    }

    public Mono<ServerResponse> streamQuotes(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(lotsOfQuotes, Quote.class);
    }

    public Mono<ServerResponse> streamNQuotes(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(lotsOfQuotes.take(request.queryParam("size").map(Long::valueOf).orElse(10L)), Quote.class);
    }
}
