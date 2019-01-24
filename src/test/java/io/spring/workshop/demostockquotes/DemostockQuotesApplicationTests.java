package io.spring.workshop.demostockquotes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.spring.workshop.demostockquotes.domain.Quote;
import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class DemostockQuotesApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFetchQuotes() {
        webTestClient
                .get()
                .uri("/quotes?size=20")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(Quote.class)
                .hasSize(20)
                .consumeWith(allQuotes ->
                        assertThat(allQuotes.getResponseBody())
                                .allSatisfy(quote -> assertThat(quote.getPrice()).isPositive()));
    }

    @Test
    public void testFetchQuotesAsStream() {
        List<Quote> result = webTestClient.get().uri("/quotes")
                .accept(APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_STREAM_JSON)
                .returnResult(Quote.class)
                .getResponseBody().take(30)
                .collectList()
                .block();

        assertThat(result)
                .allSatisfy(quote -> assertThat(quote.getPrice()).isPositive());
    }

    @Test
    public void testFetchQuotesAsStreamWithStepVerifier() {
        AtomicInteger i = new AtomicInteger(0);
        StepVerifier
                .create(webTestClient.get().uri("/quotes")
                        .accept(APPLICATION_STREAM_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(APPLICATION_STREAM_JSON)
                        .returnResult(Quote.class)
                        .getResponseBody())
                .thenRequest(30)
                .thenConsumeWhile(quote -> quote.getPrice().signum() > 0)
                .expectNextCount(30)
                .thenCancel();
    }
}

