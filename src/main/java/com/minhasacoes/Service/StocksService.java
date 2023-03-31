package com.minhasacoes.Service;

import com.minhasacoes.Model.MarketData;
import com.minhasacoes.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class StocksService {

    @Autowired
    private RestTemplate restTemplate;

    public MarketData getInfoBySymbol(String symbol) {
        String url = "https://brapi.dev/api/quote/{symbol}";

        try {
            return restTemplate.getForObject(url, MarketData.class, symbol);
        } catch (HttpClientErrorException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

}


