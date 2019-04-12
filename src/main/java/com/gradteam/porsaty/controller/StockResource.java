package com.gradteam.porsaty.controller;

import com.gradteam.porsaty.model.Stock;
import com.gradteam.porsaty.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by tawfik on 3/20/2018.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockResource {

    @Autowired
    private StockService stockService;

    @GetMapping("")
    public List<Stock> getAllStocks(){
        return this.stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Optional<Stock> getStock(@PathVariable("id") long id){

        return this.stockService.getStock(id);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity getStockNameById(@PathVariable("id") long stockId){
        Map<String,String> name=new HashMap<>();
        name.put("companyName",this.stockService.getStockNameByStockId(stockId));
        return ResponseEntity.ok(name);
    }

    @GetMapping("/{id}/price")
    public ResponseEntity getStockPriceById(@PathVariable("id") long stockId){
        Map<String,Double> price=new HashMap<>();
        price.put("companyprice",this.stockService.getStockPriceByStockId(stockId));
        return ResponseEntity.ok(price);
    }
}
