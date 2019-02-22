package io.spring.workshop.tradingservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface TradingUserRepository extends ReactiveMongoRepository<TradingUser, String> {
    Mono<TradingUser> findUserByUserName(String userName);
}
