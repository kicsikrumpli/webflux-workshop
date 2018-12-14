package io.spring.workshop.demostockquotes.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Quote {
    private static final MathContext MATH_CONTEXT = new MathContext(2);

    private String ticker;
    private BigDecimal price;
    private Instant instant;

    public Quote(String ticker, BigDecimal price) {
        this.ticker = ticker;
        this.price = price;
    }
    public Quote(String ticker, Double price) {
        this(ticker, new BigDecimal(price, MATH_CONTEXT));

    }


}
