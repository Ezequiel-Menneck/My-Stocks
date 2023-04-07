package com.minhasacoes.Service;

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

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StocksService stocksService;

    public Person findById(Integer id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void createPerson(Person person) {
        personRepository.save(person);
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

        priceComparator.setAllPriceBySymbol(totalValueInStocksByUser);
        priceComparator.setAllPriceActualQuote(actualValueOfUserStocksQuantity);
        priceComparator.setPriceDiff(diff);
        priceComparator.setAveragePriceOfUserStock(averagePriceOfUserStock);
        priceComparator.setUnitPriceOfMarket(unitPriceOfMarket);

        return priceComparator;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public void deletePerson(Integer id) {
        personRepository.deleteById(id);
    }

}
