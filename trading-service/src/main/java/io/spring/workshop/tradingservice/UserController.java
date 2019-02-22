package io.spring.workshop.tradingservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private final TradingUserRepository repo;

    public UserController(TradingUserRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TradingUser> getAllUsers() {
        return repo.findAll();
    }

    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TradingUser> getUserByName(@PathVariable("username") String userName) {
        return repo.findUserByUserName(userName);
    }

}
