package com.minhasacoes.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String symbol;
    private String longName;
    private String currency;
    private Double regularMarketPrice;
    private Double quantity;

    public Double getSubTotal() {
        return regularMarketPrice * quantity;
    }

}
