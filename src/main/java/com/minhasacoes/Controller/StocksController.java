package com.minhasacoes.Controller;

import com.minhasacoes.Model.MarketData;
import com.minhasacoes.Service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @GetMapping("/{symbol}")
    public ResponseEntity<MarketData> getInfoBySymbol(@PathVariable String symbol) {
        return new ResponseEntity<>(stocksService.getInfoBySymbol(symbol), HttpStatus.OK);
    }

}
