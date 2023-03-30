package com.minhasacoes.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PriceComparator {

    private Double averagePriceOfUserStock;
    private Double unitPriceOfMarket;
    private Double allPriceBySymbol;
    private Double allPriceActualQuote;
    private Double priceDiff;

}
