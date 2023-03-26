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

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Stocks> wallet;

    public Double getTotalValueInAcoes() {
        double sum = 0;
        for (Stocks stocks : wallet) {
            sum += stocks.getSubTotal();
        }
        return sum;
    }

    public Double getTotalByAcao(Stocks stocks) {
        double sum = 0;
        for (Stocks stocksF : wallet) {
            if (Objects.equals(stocksF.getSymbol(), stocks.getSymbol())) {
                sum += stocksF.getSubTotal();
            }
        }
        return sum;
    }

}
