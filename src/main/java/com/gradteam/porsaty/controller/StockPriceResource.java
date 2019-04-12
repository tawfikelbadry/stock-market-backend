package com.gradteam.porsaty.controller;

import com.gradteam.porsaty.model.StockPrice;
import com.gradteam.porsaty.service.StockPricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/stocksprices")
public class StockPriceResource {

    @Autowired
    private StockPricesService stockPricesService;

    @GetMapping("/{id}")
    public ResponseEntity<List<StockPrice>> getStockPricesWithId(@PathVariable long id){

        List<StockPrice> temp=this.stockPricesService.getStockPricesDesc(id);

        return ResponseEntity.ok(this.stockPricesService.getStockPricesDesc(id));
    }



//    @GetMapping("/{id}")
//    public ResponseEntity<List<StockPrice>> getStockPricesWithIdForChartJs(@PathVariable long id){
//
//        List<StockPrice> temp=this.stockPricesService.getStockPricesDesc(id);
//
//        return ResponseEntity.ok(this.stockPricesService.getStockPricesDesc(id));
//    }

    @PostMapping("/{id}")
    public ResponseEntity addStockPrice(@PathVariable("id") long stockId,@RequestBody StockPrice stockPrice){
        System.out.println("Stock id : "+stockId);
        StockPrice temp=  this.stockPricesService.addStockPrice(stockPrice,stockId);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(temp.getId()).toUri();
        return ResponseEntity.created(location).body(temp);
    }



}
