package com.minhasacoes.Controller;

import com.minhasacoes.DTO.StocksDTO;
import com.minhasacoes.Entities.Person;
import com.minhasacoes.Model.PriceComparator;
import com.minhasacoes.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addStockToWaller(@PathVariable Integer id, @RequestBody StocksDTO stocksDTO) {
        personService.addStocksToPerson(id, stocksDTO);
    }

    @GetMapping("/compare/{id}/{symbol}")
    public ResponseEntity<PriceComparator> getInfo(@PathVariable Integer id, @PathVariable String symbol) {
        return new ResponseEntity<>(personService.compareActualPriceBySymbol(id, symbol), HttpStatus.OK);
    }

}
