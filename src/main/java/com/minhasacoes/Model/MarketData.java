package com.minhasacoes.Model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class MarketData {

    private List<Result> results;
    private String requestedAt;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String symbol;
        private String longName;
        private String currency;
        private double regularMarketPrice;
    }
}
