package com.minhasacoes.Service;

import com.minhasacoes.DTO.PersonDTO;
import com.minhasacoes.DTO.StocksDTO;
import com.minhasacoes.Entities.Person;
import com.minhasacoes.Entities.Stocks;
import com.minhasacoes.Model.MarketData;
import com.minhasacoes.Model.PriceComparator;
import com.minhasacoes.Repository.PersonRepository;
import com.minhasacoes.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StocksService stocksService;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public Person findById(Integer id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void create(PersonDTO personDTO) {
        personRepository.save(createPerson(personDTO));
    }

    private Person createPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());

        return person;
    }

    @Transactional
    public void addStocksToPerson(Integer id, StocksDTO stocksDTO) {
        Person person = findById(id);
        Stocks stocks = new Stocks();

        MarketData marketData = stocksService.getInfoBySymbol(stocksDTO.getSymbol());

        for (MarketData.Result mkd : marketData.getResults()) {
            stocks.setSymbol(mkd.getSymbol());
            stocks.setLongName(mkd.getLongName());
            stocks.setCurrency(mkd.getCurrency());

            if (stocksDTO.getRegularMarketPrice() == null) {
                stocks.setRegularMarketPrice(mkd.getRegularMarketPrice());
            } else {
                stocks.setRegularMarketPrice(stocksDTO.getRegularMarketPrice());
            }

            stocks.setQuantity(stocksDTO.getQuantity());
        }
        person.getWallet().add(stocks);

        personRepository.save(person);
    }

    public PriceComparator compareActualPriceBySymbol(Integer id, String symbol) {
        double totalValueInStocksByUser = 0;
        int quantityOfStocks = 0;
        double unitPriceOfMarket = 0;
        double actualValueOfUserStocksQuantity = 0;

        PriceComparator priceComparator = new PriceComparator();
        Person person = findById(id);
        List<Stocks> personWallet = person.getWallet().stream().filter(s -> s.getSymbol().equals(symbol)).toList();
        MarketData marketData = stocksService.getInfoBySymbol(symbol);

        for (Stocks s : personWallet) {
            quantityOfStocks += s.getQuantity();
            totalValueInStocksByUser += s.getSubTotal();
        }

        for (MarketData.Result mdk : marketData.getResults()) {
            actualValueOfUserStocksQuantity = mdk.getRegularMarketPrice() * quantityOfStocks;
            unitPriceOfMarket = mdk.getRegularMarketPrice();
        }

        double diff = totalValueInStocksByUser - actualValueOfUserStocksQuantity;
        double averagePriceOfUserStock = totalValueInStocksByUser / quantityOfStocks;

        String formatedSum = decimalFormat.format(totalValueInStocksByUser);
        String formatedActualValueOfUserStocksQuantity = decimalFormat.format(actualValueOfUserStocksQuantity);
        String formatedDiff = decimalFormat.format(diff);
        String formatedUnitPriceOfMarket = decimalFormat.format(unitPriceOfMarket);
        String formatedAveragePriceOfUserStock = decimalFormat.format(averagePriceOfUserStock);
        formatedSum = formatedSum.replace(",", ".");
        formatedActualValueOfUserStocksQuantity = formatedActualValueOfUserStocksQuantity.replace(",", ".");
        formatedDiff = formatedDiff.replace(",", ".");
        formatedUnitPriceOfMarket = formatedUnitPriceOfMarket.replace(",", ".");
        formatedAveragePriceOfUserStock = formatedAveragePriceOfUserStock.replace(",", ".");

        priceComparator.setAllPriceBySymbol(formatedSum);
        priceComparator.setAllPriceActualQuote(formatedActualValueOfUserStocksQuantity);
        priceComparator.setPriceDiff(formatedDiff);
        priceComparator.setAveragePriceOfUserStock(formatedAveragePriceOfUserStock);
        priceComparator.setUnitPriceOfMarket(formatedUnitPriceOfMarket);

        return priceComparator;
    }

}
