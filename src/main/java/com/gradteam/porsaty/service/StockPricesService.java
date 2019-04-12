package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.Stock;
import com.gradteam.porsaty.model.StockPrice;
import com.gradteam.porsaty.repository.StockPricesRepository;
import com.gradteam.porsaty.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class StockPricesService {

    @Autowired
    private StockPricesRepository stockPricesRepository;

    @Autowired
    private StockRepository stockRepository;

    public List<StockPrice> getStockPricesDesc(long id){
        return this.stockPricesRepository.findByStockIdOrderByDateAsc(id);
    }

    public StockPrice addStockPrice(StockPrice stockPrice,long stockId){
        Stock temp=this.stockRepository.findById(stockId).get();
        if(null==temp){
            return null;
        }
        stockPrice.setStock(temp);
        return this.stockPricesRepository.save(stockPrice);
    }







}
