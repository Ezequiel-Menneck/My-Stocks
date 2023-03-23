package com.minhasacoes.DTO;

import lombok.Data;

@Data
public class StocksDTO {

    private String symbol;
    private String longName;
    private String currency;
    private Double regularMarketPrice;

}
