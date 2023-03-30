package com.minhasacoes.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PriceComparator {

    private String averagePriceOfUserStock;
    private String unitPriceOfMarket;
    private String allPriceBySymbol;
    private String allPriceActualQuote;
    private String priceDiff;

}
