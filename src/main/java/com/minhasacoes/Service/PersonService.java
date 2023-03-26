package com.minhasacoes.Service;

import com.minhasacoes.DTO.PersonDTO;
import com.minhasacoes.DTO.StocksDTO;
import com.minhasacoes.Model.MarketData;
import com.minhasacoes.Model.Person;
import com.minhasacoes.Model.Stocks;
import com.minhasacoes.Repository.PersonRepository;
import com.minhasacoes.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
