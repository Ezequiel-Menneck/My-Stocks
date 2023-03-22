package com.minhasacoes.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany
    @ToString.Exclude
    private List<Stocks> wallet;

    public Double getTotalValueInAcoes() {
        double sum = 0;
        for (Stocks a : wallet) {
            sum += a.getRegularMarketPrice();
        }
        return sum;
    }

    public Double getTotalByAcao(Stocks stocks) {
        double sum = 0;
        for (Stocks a : wallet) {
            if (Objects.equals(a.getSymbol(), stocks.getSymbol())) {
                sum = a.getRegularMarketPrice();
            }
        }
        return sum;
    }

}
