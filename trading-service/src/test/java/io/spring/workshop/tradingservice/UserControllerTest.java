package io.spring.workshop.tradingservice;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
public class UserControllerTest {

    @MockBean
    private TradingUserRepository mockRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testGetAllUsers() {
        var users = List.of(
                new TradingUser("wabbit_one", "Sebastien Wabbit"),
                new TradingUser("not_a_wabbit", "Not a Wabbit")
        );

        given(mockRepository.findAll())
                .willReturn(Flux.fromIterable(users));

        webClient.get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TradingUser.class)
                .isEqualTo(users);
    }

    @Test
    public void getUserByName() {
        var user = new TradingUser("wabbit_one", "Sebastien Wabbit");

        given(mockRepository.findUserByUserName("wabbit_one"))
                .willReturn(Mono.just(user));

        webClient.get()
                .uri("/users/wabbit_one")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TradingUser.class)
                .isEqualTo(user);
    }
}