package com.gradteam.porsaty.service;

import com.gradteam.porsaty.model.Stock;
import com.gradteam.porsaty.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by tawfik on 3/20/2018.
 */

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks(){
        return (List<Stock>) this.stockRepository.findAll();
    }

    public Optional<Stock> getStock(long id){
        return this.stockRepository.findById(id);
    }

    public List<Stock> getSectorStocks(int id){
        return this.stockRepository.findAllByCompanyCompanySectorId(id);
    }

    public Stock save(Stock stock){
        return this.stockRepository.save(stock);
    }


    public String getStockNameByStockId(long stockId){
        return this.stockRepository.findStockNameByStockId(stockId);
    }


    public Double getStockPriceByStockId(long stockId) {
        return this.stockRepository.findStockPriceById(stockId);
    }
}
