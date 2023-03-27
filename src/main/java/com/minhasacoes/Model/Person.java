package com.minhasacoes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;

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

    @Transient
    @JsonIgnore
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public Double getTotalValueInAcoes() {
        double sum = 0;
        for (Stocks stocks : wallet) {
            sum += stocks.getSubTotal();
        }

        String formatedSum = decimalFormat.format(sum);
        formatedSum = formatedSum.replace(",", ".");

        return Double.parseDouble(formatedSum);
    }

}